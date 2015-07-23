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

#if ! defined(EPPXMLVALIDATOR_HPP)    /* { */
#define       EPPXMLVALIDATOR_HPP        1

#include <xercesc/dom/DOM.hpp>
#include <xercesc/sax/ErrorHandler.hpp>
#include <xercesc/sax/SAXException.hpp>
#include <xercesc/sax/SAXParseException.hpp>
#include <xercesc/parsers/XercesDOMParser.hpp>
#include <string.h>
#include <stdio.h>
#include <stdlib.h>

#include<dirent.h>
#include <sys/stat.h>


#include <xercesc/framework/MemBufInputSource.hpp>

#include "EppResult.hpp"

#define	MAX_NUM_OF_ERRORS	30

class EPP_EXPORT EppXmlValidator : public ErrorHandler
{
private:
	int          countWarnings;
	int          countErrors;
	int          countFatals;
	OldValueVectorOf<DOMString>* errorMessages;
	XercesDOMParser    parser;
	DOMDocument* document;

	void init();
	void addMessage( const DOMString& msg );
	DOMString getLoc( const SAXParseException& e );

public:
	/**
	 * Creates an <code>EppXmlValidator</code> object
	 */
	EppXmlValidator();

	/**
	 * Creates an <code>EppXmlValidator</code> object, given an EPP XML payload
	 * string
	 *
	 * @param string a string in DOMString format
	 */
	EppXmlValidator( const DOMString& string );

	/**
	 * Creates an <code>EppXmlValidator</code> object, given an EPP XML payload
	 * string
	 *
	 * @param string a null terminated byte stream
	 */
	EppXmlValidator( const char * string );

	/**
	 * Creates an <code>EppXmlValidator</code> object, given an EPP XML payload
	 * string and its length
	 *
	 * @param string a minated byte stream
	 * @param length the length of the byte stream
	 */
	EppXmlValidator( const char * string, int length );
	
	/**
	 * Destructor for an <code>EppXmlValidator</code> object
	 */
	~EppXmlValidator();

	void loadXSD( char* xsdDirPath )
	{
		string path( xsdDirPath );

		DIR *pDIR;
		struct dirent *entry;

		cout << endl << "Loading ALL XSDs from path["<<xsdDirPath<<"]... " << endl;
		if ( pDIR = opendir( xsdDirPath ) )
		{
			while ( entry = readdir( pDIR ) )
			{
				if ( strcmp( entry->d_name, "." ) != 0 && strcmp( entry->d_name, ".." ) != 0 )
				{
					static int index = 0;
					if ( entry->d_type == DT_DIR )
					{
						continue;
					}

					string fullPath = string( xsdDirPath ) +"/" + entry->d_name;
					if ( fullPath.find( ".xsd" ) == string::npos )
					{
						continue;
					}
					//if (verboseOutput)
					{
						cout << endl << "Loading XSD: " << fullPath;
					}
					if ( parser.loadGrammar( fullPath.c_str( ), Grammar::SchemaGrammarType, true ) == NULL )
					{
						cerr << endl << "Failed to load schema file: " << fullPath;
					}

				}
			}
		}
		cout << endl << "Load XSD done" << endl;
		closedir( pDIR );
	}

	/**
	 * Gets the DOM Parser used for parsing EPP messages
	 */
	XercesDOMParser& getParser();

	/**
	 * Parses an EPP message string
	 *
	 * @param string a string in DOMString format
	 */
	void parse( const DOMString& string );

	/**
	 * Parses an EPP message string
	 *
	 * @param string a null terminated byte stream
	 */
	void parse( const char * string );

	/**
	 * Parses an EPP message string, given its length
	 *
	 * @param string a minated byte stream
	 * @param length the length of the byte stream
	 */
	void parse( const char * string, int length );

	/**
	 * Parses an EPP message stored in a file
	 *
	 * @param file file name containing an EPP message
	 */
	bool parseFile( const char * file );

	/**
	 * Gets <code>DOMDocument</code> object after parsing
	 */
	DOMDocument& getDocument();

	/**
	 * Checks if there is any errors after parsing
	 */
	bool hasError();

	/**
	 * Gets parsing result as an <code>EppResult</code> object
	 */
	EppResult * getResult();

	/**
	 * Gets the root "epp" node of parsing result as an <code>Node</code>
	 * object. If there is any error, a null DOMNode is returned.
	 */
	DOMNode* getRootNode();

	/**
	 * <code>ErrorHandler</code> method - Warnings
	 */
	void warning( const SAXParseException& e );

	/**
	 * <code>ErrorHandler</code> method - Errors
	 */
	void error( const SAXParseException& e );

	/**
	 * <code>ErrorHandler</code> method - Fatal errors
	 */
	void fatalError( const SAXParseException& e );

	/**
	 * <code>ErrorHandler</code> method - Reset errors
	 */
	void resetErrors( void );

	DOMString toString();
};




void EppXmlValidator::init()
{
	countWarnings = 0;
	countErrors = 0;
	countFatals = 0;
	errorMessages->removeAllElements();
	parser.reset();
	parser.setExitOnFirstFatalError(false);
	parser.setValidationScheme(XercesDOMParser::Val_Auto);

	parser.setDoNamespaces(true);
	parser.setDoSchema(true);
	parser.cacheGrammarFromParse( true );

	parser.setErrorHandler(this);
//	parser.setCreateEntityReferenceNodes(false);
//	parser.setToCreateXMLDeclTypeNode(true);
	document = null;
}

void EppXmlValidator::addMessage( const DOMString& msg )
{
	errorMessages->addElement(msg);
	std::cout<<"\n - PARSER REPORTED: " << msg;
}

EppXmlValidator::EppXmlValidator()
{
	errorMessages = new OldValueVectorOf<DOMString>(MAX_NUM_OF_ERRORS);
	init();
}

EppXmlValidator::EppXmlValidator( const DOMString& string )
{
	errorMessages = new OldValueVectorOf<DOMString>(MAX_NUM_OF_ERRORS);
	parse(string);
}

EppXmlValidator::EppXmlValidator( const char * string )
{
	errorMessages = new OldValueVectorOf<DOMString>(MAX_NUM_OF_ERRORS);
	parse(string);
}

EppXmlValidator::EppXmlValidator( const char * string, int length )
{
	errorMessages = new OldValueVectorOf<DOMString>(30);
	parse(string, length);
}

EppXmlValidator::~EppXmlValidator()
{
	if( errorMessages != null )
	{
		delete errorMessages;
	}
}

XercesDOMParser& EppXmlValidator::getParser()
{
	return parser;
}

void EppXmlValidator::parse( const DOMString& string )
{
	char * p = string.transcode();
	parse((const char *) p);
	XercesString::Delete(p);
}

void EppXmlValidator::parse( const char * string )
{
	parse(string, strlen(string));
}

void EppXmlValidator::parse( const char * string, int length )
{
	MemBufInputSource membuf((const XMLByte *) string, length, "epp", false);
	try
	{
		init();
		parser.parse(membuf);
		document = parser.getDocument();
	}
	catch( const XMLException& e )
	{
		countFatals++;
		addMessage(DOMString("[Unknown Error] XMLException message is:\n")
			+ e.getMessage());
	}
	catch( const DOMException& e )
	{
		countFatals++;
		
		char cCode [33];
		sprintf(cCode, "%d", e.code);
		char* pMsg = XMLString::transcode(e.msg);

		addMessage((XS("[Unknown Error] DOMException code is: ") + XS(cCode) + XS(", message is:\n") + XS(pMsg)));
		
		XercesString::Delete(pMsg);
	}
	catch( ... )
	{
		countFatals++;
		addMessage(DOMString("[Unknown Error] Unknown Exception"));
	}
}

bool EppXmlValidator::parseFile( const char * file )
{
	try
	{
		init();
		parser.parse(file);
		document = parser.getDocument();
	}
	catch( const XMLException& e )
	{
		countFatals++;
		addMessage(DOMString("[Unknown Error] XMLException message is:\n")
			+ e.getMessage());
	}
	catch( const DOMException& e )
	{
		countFatals++;
		
		char cCode [32];
		sprintf(cCode, "%d", e.code);
		char* pMsg = XMLString::transcode(e.msg);

		addMessage((XS("[Unknown Error] DOMException code is: ") + XS(cCode) + XS(", message is:\n") + XS(pMsg)));
		
		XercesString::Delete(pMsg);
		
	}
	catch( ... )
	{
		countFatals++;
		addMessage(DOMString("[Unknown Error] Unknown Exception"));
	}
	return hasError();
}

DOMDocument& EppXmlValidator::getDocument()
{
	return *document;
}

bool EppXmlValidator::hasError()
{
	return (countWarnings != 0) ||
	       (countErrors != 0) || (countFatals != 0);
}

EppResult * EppXmlValidator::getResult()
{
	if( ! this->hasError() )
	{
		return new EppResult(EppError::CODE_NO_ERROR);
	}

	EppResult * result = new EppResult(EppError::CODE_COMMAND_SYNTAX_ERROR);
	if( result == null )
	{
		return result;
	}

	if( this->errorMessages != null )
	{
		for( unsigned int i = 0; i < this->errorMessages->size(); i++ )
		{
			DOMString v = this->errorMessages->elementAt(i);
			result->addValue(v);
		}
	}

	return result;
}

DOMNode* EppXmlValidator::getRootNode()
{
	if( hasError() )
	{
		return NULL;
	}
	DOMDocument* doc = &getDocument();
	DOMNodeList* list = doc->getElementsByTagName(XS("epp"));
	if( (list == null) || (list->getLength() == 0) )
	{
		return NULL;
	}
	return list->item(0);
}

void EppXmlValidator::warning( const SAXParseException& e )
{
	countWarnings++;
	addMessage(DOMString("[Warning] ") + getLoc(e) + e.getMessage());
}

void EppXmlValidator::error( const SAXParseException& e )
{
	countErrors++;
	addMessage(DOMString("[Error] ") + getLoc(e) + e.getMessage());
}

void EppXmlValidator::fatalError( const SAXParseException& e )
{
	countFatals++;
	addMessage(DOMString("[Fatal] ") + getLoc(e) + e.getMessage());
}

void EppXmlValidator::resetErrors()
{
	countWarnings = 0;
	countErrors = 0;
	countFatals = 0;
}

DOMString EppXmlValidator::getLoc( const SAXParseException& e )
{
	DOMString s = DOMString(e.getSystemId());
	char buf[64];
	(void) ::sprintf(buf, "line:%d column:%d ", e.getLineNumber(), e.getColumnNumber());
	s += buf;

	return s;
}

DOMString EppXmlValidator::toString()
{
	DOMString str = DOMString("EppXmlValidator:");
//	str += " warnings=" + countWarnings;
//	str += " errors=" + countErrors;
//	str += " fatals=" + countFatals;
	char buf[128];
	sprintf(buf, " warnings=%d errors=%d fatals=%d",
		countWarnings, countErrors, countFatals);
	str += buf;
	return str;
}


#endif     /* EPPXMLVALIDATOR_HPP */  /* } */

