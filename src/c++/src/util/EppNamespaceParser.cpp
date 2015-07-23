/*******************************************************************************
 * The MIT License (MIT)
 *  
 * Copyright (c)2015 Neustar Inc.
 *  
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *  
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *  
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *******************************************************************************/
#include <iostream>
#include "XercesString.hpp"
#include <cstddef>
#include <xercesc/util/ValueVectorOf.hpp>
#include <xercesc/util/KeyValuePair.hpp>

#include <xercesc/util/XMLString.hpp>
#include <xercesc/parsers/XercesDOMParser.hpp>
#include <xercesc/framework/LocalFileInputSource.hpp>
#include <xercesc/sax/ErrorHandler.hpp>
#include <xercesc/sax/SAXParseException.hpp>
#include <xercesc/validators/common/Grammar.hpp>

#include<stdio.h>
#include<cstdlib>
#include<iostream>
#include<string.h>
#include<fstream>
#include<sstream>
#include<dirent.h>
#include <sys/stat.h>

#include <xercesc/util/XMLUni.hpp>
#include <xercesc/util/PlatformUtils.hpp>

#include <xercesc/dom/DOM.hpp>

#include <xercesc/validators/common/Grammar.hpp>
#include <xercesc/framework/XMLGrammarPoolImpl.hpp>

using namespace std;
using namespace xercesc;

bool verboseOutput    = false;
bool xsdValidationOn  = false;
bool namespaceParsing = false;

void printDash(int i)
{
	for ( int j = 0; j <= i; j++ )
	{
		cout<<"    ";
	}
}

bool isDirExist(char* dirName)
{
	struct stat sb;

	if ( stat(dirName, &sb) == 0 && S_ISDIR(sb.st_mode) )
	{
		return true;
	}
	return false;
}

class ParserErrorHandler : public ErrorHandler
{
public:

	ParserErrorHandler()
	{
		errors = 0;
	}
private:

	void reportParseException(const SAXParseException& ex)
	{
		errors++;
		char* msg = XMLString::transcode(ex.getMessage());
		fprintf(stderr, "at line %llu column %llu, %s\n",
			ex.getColumnNumber(), ex.getLineNumber(), msg);
		XMLString::release(&msg);

	}

public:

	void warning(const SAXParseException& ex)
	{
		errors++;
		if ( verboseOutput )
			reportParseException(ex);
	}

	void error(const SAXParseException& ex)
	{
		errors++;
		if ( verboseOutput )
			reportParseException(ex);
	}

	void fatalError(const SAXParseException& ex)
	{
		errors++;
		if ( verboseOutput )
			reportParseException(ex);
	}

	void resetErrors()
	{
		errors = 0;
	}
	int errors;

};

void browseTree(DOMNode* root)
{
	static int indent = 1;
	indent++;

	DOMNodeList* list = root->getChildNodes();
	for( int i = 0; i < list->getLength(); i++ )
	{
		DOMNode* node = list->item(i);
		DOMString name = node->getLocalName();
		if( name.isNull() )
		{
			name = node->getNodeName();
		}
		if( name.isNull() )
		{
			continue;
		}
		if ( name.equals("#text") )
			continue;
		printDash(indent);
		cout<<name<<endl;
		browseTree(node);
	}
	indent--;
}

bool  validateXMLs(char* xmlDirPath, XercesDOMParser* parser)
{
	DIR *pDIR;
	struct dirent *entry;

	cout<<endl<<"Starting validateXMLs... "<<endl;
	if( pDIR = opendir(xmlDirPath) )
	{
		while( entry = readdir(pDIR) )
		{
			if( strcmp(entry->d_name, ".") != 0 && strcmp(entry->d_name, "..") != 0 )
			{
				static int index = 0;
				if ( entry->d_type == DT_DIR )
				{
					continue;
				}

				string fullPath = string(xmlDirPath) + entry->d_name;

				if ( fullPath.find(".xml")==string::npos )
				{
					continue;
				}

				parser->parse(fullPath.c_str());
				if ( verboseOutput )
				{
					DOMDocument* doc = parser->getDocument();
					DOMNodeList *lst = doc->getElementsByTagName(XS("epp"));
					browseTree(lst->item(0));
				}

				int rc = parser->getErrorCount();
				if ( rc == 0 )
				{
					cout<<"PASSED:: FILE: "<<fullPath;
				}
				else
				{
					cout<<"FAILED:: FILE: "<<fullPath;
				}
				cout<<endl;
			}
		}
		closedir(pDIR);
	}
	cout<<endl<<"Starting validateXMLs...done "<<endl;
	return true;
}

int loadXSD(char* xsdDirPath, XercesDOMParser* parser)
{
	string path(xsdDirPath);

	DIR *pDIR;
	struct dirent *entry;

	cout<<endl<<"Loading XSDs... "<<endl;
	if( pDIR = opendir(xsdDirPath) )
	{
		while( entry = readdir(pDIR) )
		{
			if( strcmp(entry->d_name, ".") != 0 && strcmp(entry->d_name, "..") != 0 )
			{
				static int index = 0;
				if ( entry->d_type==DT_DIR )
				{
					continue;
				}

				string fullPath = string(xsdDirPath) + entry->d_name;
				if ( fullPath.find(".xsd")==string::npos )
				{
					continue;
				}
				if ( parser->loadGrammar(fullPath.c_str(), Grammar::SchemaGrammarType, true) == NULL )
				{
					cerr<<endl<<"Failed to load schema file: "<<fullPath;
				}

			}
		}
	}
	cout<<endl<<"Load XSD done"<<endl;
	closedir(pDIR);
}

int myEppParser (char* xsdPath, char* xmlPath)
{
	XMLPlatformUtils::Initialize();  // Initialize Xerces infrastructure
	XercesDOMParser parser;

	ParserErrorHandler parserErrorHandler;

	parser.setErrorHandler(&parserErrorHandler);

	// Enable schema processing.
	parser.setDoSchema(xsdValidationOn);

	// parser.setErrorHandler(&parserErrorHandler);
	parser.setValidationScheme(XercesDOMParser::Val_Auto);
	parser.setDoNamespaces(namespaceParsing);

	parser.cacheGrammarFromParse(true);
	// Let's preparse the schema grammar (.xsd) and cache it.
	loadXSD(xsdPath, &parser);
	validateXMLs(xmlPath, &parser);
	return 0;
}

int usage(char* execname)
{
	cout<<endl<<"USAGE: "<<execname<<" {Absolute XSD PATH} [Absolute PATH to XML files] [XSD - to XSD validation][NAMESPACE - to do namespace parsing] [VERBOSE - for verbose output]"<<endl;
}

void handleOptionalOptions(char* option)
{
	if ( !strcmp("VERBOSE", option) )
	{
		verboseOutput = true;
	}

	if ( !strcmp("XSD", option) )
	{
		xsdValidationOn = true;
	}
	if ( !strcmp("NAMESPACE", option) )
	{
		namespaceParsing = true;
	}

}

bool validateArguments(int argc, char*argv[])
{
	bool rc = false;
	if ( argc == 1 )
	{
		usage(argv[0]);
		return rc;
	}

	for ( int i = 0; i < argc; i++ )
	{
		switch ( i )
		{
			case 0:
				break;
			case 1: //XSD path
				if ( !isDirExist(argv[i]) )
				{
					cout<<endl<<"XSD path ["<<argv[i]<<"] does not exists"<<endl;
					usage(argv[0]);
					return rc;
				}
				break;
			case 2: // XML path
				if ( !isDirExist(argv[i]) )
				{
					cout<<endl<<"XML path ["<<argv[i]<<"] does not exists"<<endl;
					usage(argv[0]);
					return rc;
				}
				rc = true;
				break;
			case 3:
			case 4:
			case 5:
				handleOptionalOptions(argv[i]);
				break;
		}
	}

	return rc;
}

int main (int argc, char* argv[])
{
	if ( !validateArguments(argc, argv) )
	{
		return -1;
	}

	myEppParser(argv[1], argv[2]);
}

