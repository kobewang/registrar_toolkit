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
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include "EppParser.hpp"
#include <xercesc/framework/MemBufInputSource.hpp>

void EppParser::init()
{
	countWarnings = 0;
	countErrors = 0;
	countFatals = 0;
	errorMessages->removeAllElements();
	parser.reset();
	parser.setExitOnFirstFatalError(false);
	parser.setValidationScheme(XercesDOMParser::Val_Auto);
	parser.setDoNamespaces(false);
	//parser.setDoNamespaces(true);
	parser.setDoSchema(false);
	parser.setErrorHandler(this);
	//parser.setCreateEntityReferenceNodes(false);
	//parser.setToCreateXMLDeclTypeNode(true);
	document = null;
}

void EppParser::addMessage( const DOMString& msg )
{
	errorMessages->addElement(msg);
}

EppParser::EppParser()
{
	errorMessages = new OldValueVectorOf<DOMString>(MAX_NUM_OF_ERRORS);
	init();
}

EppParser::EppParser( const DOMString& string )
{
	errorMessages = new OldValueVectorOf<DOMString>(MAX_NUM_OF_ERRORS);
	parse(string);
}

EppParser::EppParser( const char * string )
{
	errorMessages = new OldValueVectorOf<DOMString>(MAX_NUM_OF_ERRORS);
	parse(string);
}

EppParser::EppParser( const char * string, int length )
{
	errorMessages = new OldValueVectorOf<DOMString>(30);
	parse(string, length);
}

EppParser::~EppParser()
{
	if( errorMessages != null )
	{
		delete errorMessages;
	}
}

XercesDOMParser& EppParser::getParser()
{
	return parser;
}

void EppParser::parse( const DOMString& string )
{
	char * p = string.transcode();
	parse((const char *) p);
	XercesString::Delete(p);
}

void EppParser::parse( const char * string )
{
	parse(string, strlen(string));
}

void EppParser::parse( const char * string, int length )
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

void EppParser::parseFile( const char * file )
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
}

DOMDocument& EppParser::getDocument()
{
	return *document;
}

bool EppParser::hasError()
{
	return (countWarnings != 0) ||
		   (countErrors != 0) || (countFatals != 0);
}

EppResult * EppParser::getResult()
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

DOMNode* EppParser::getRootNode()
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

void EppParser::warning( const SAXParseException& e )
{
	countWarnings++;
	addMessage(DOMString("[Warning] ") + getLoc(e) + e.getMessage());
}

void EppParser::error( const SAXParseException& e )
{
	countErrors++;
	addMessage(DOMString("[Error] ") + getLoc(e) + e.getMessage());
}

void EppParser::fatalError( const SAXParseException& e )
{
	countFatals++;
	addMessage(DOMString("[Fatal] ") + getLoc(e) + e.getMessage());
}

void EppParser::resetErrors()
{
	countWarnings = 0;
	countErrors = 0;
	countFatals = 0;
}

DOMString EppParser::getLoc( const SAXParseException& e )
{
	DOMString s = DOMString(e.getSystemId());
	char buf[64];
	(void) ::sprintf(buf, "line:%d column:%d ", e.getLineNumber(), e.getColumnNumber());
	s += buf;

	return s;
}

DOMString EppParser::toString()
{
	DOMString str = DOMString("EppParser:");
	//	str += " warnings=" + countWarnings;
	//	str += " errors=" + countErrors;
	//	str += " fatals=" + countFatals;
	char buf[128];
	sprintf(buf, " warnings=%d errors=%d fatals=%d",
		countWarnings, countErrors, countFatals);
	str += buf;
	return str;
}
