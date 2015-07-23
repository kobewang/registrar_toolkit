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
#include <xercesc/framework/MemBufInputSource.hpp>
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

bool verboseOutput = false;
bool xsdValidationOn = false;
bool namespaceParsing = false;
bool XMLON = false ;

class Utility
{
public:

	static void browseTree( DOMNode* root )
	{
		static int indent = 1;
		indent++;

		DOMNodeList* list = root->getChildNodes( );
		for ( int i = 0; i < list->getLength( ); i++ )
		{
			DOMNode* node = list->item( i );
			DOMString name = node->getLocalName( );
			if ( name.isNull( ) )
			{
				name = node->getNodeName( );
			}
			if ( name.isNull( ) )
			{
				continue;
			}
			if ( name.equals( "#text" ) )
				continue;
			Utility::seperator( indent );
			cout << name << endl;
			Utility::browseTree( node );
		}
		indent--;
	}

	static void seperator( int i )
	{
		for ( int j = 0; j <= i; j++ )
		{
			cout << "    ";
		}
	}

	static bool isDirExist( char* dirName )
	{
		struct stat sb;

		if ( stat( dirName, &sb ) == 0 && S_ISDIR( sb.st_mode ) )
		{
			return true;
		}
		return false;
	}
};

int usage( char* execname )
{
	cout << endl << "USAGE: " << execname << " {Absolute XSD PATH}" << endl;
}

void handleOptionalOptions( char* option )
{
	if ( !strcmp( "VERBOSE", option ) )
	{
		verboseOutput = true;
	}

	if ( !strcmp( "XSD", option ) )
	{
		xsdValidationOn = true;
	}
	if ( !strcmp( "NAMESPACE", option ) )
	{
		namespaceParsing = true;
	}
	if ( !strcmp( "XMLON", option ) )
	{
		XMLON = true;
	}
}

bool validateArguments( int argc, char*argv[] )
{
	bool rc = false;
	if ( argc == 1 )
	{
		usage( argv[0] );
		return rc;
	}

	for ( int i = 0; i < argc; i++ )
	{
		switch ( i )
		{
			case 0:
				break;
			case 1: //XSD path
				if ( !Utility::isDirExist( argv[i] ) )
				{
					cout << endl << "XSD path [" << argv[i] << "] does not exists" << endl;
					usage( argv[0] );
					return rc;
				}
				rc=true;
				break;
			case 2: // XML path
//				if ( !Utility::isDirExist( argv[i] ) )
//				{
//					cout << endl << "XML path [" << argv[i] << "] does not exists" << endl;
//					usage( argv[0] );
//					return rc;
//				}
//				rc = true;
//				break;
			case 3:
			case 4:
			case 5:
			case 6:
				handleOptionalOptions( argv[i] );
				break;
		}
	}

	return rc;
}
