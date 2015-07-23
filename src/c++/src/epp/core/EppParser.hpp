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

#if ! defined(EPPPARSER_HPP)    /* { */
#define       EPPPARSER_HPP        1

#include <xercesc/dom/DOM.hpp>
#include <xercesc/sax/ErrorHandler.hpp>
#include <xercesc/sax/SAXException.hpp>
#include <xercesc/sax/SAXParseException.hpp>
#include <xercesc/parsers/XercesDOMParser.hpp>

#include "EppResult.hpp"

#define	MAX_NUM_OF_ERRORS	30

/**
 * This <code>EppParser</code> class is designed to parse and validate
 * EPP XML messages against EPP XML Schema. Apache Xerces 1.5.0 for C++
 * is required.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:37 $
 */
class EPP_EXPORT EppParser : public ErrorHandler
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
	 * Creates an <code>EppParser</code> object
	 */
	EppParser();

	/**
	 * Creates an <code>EppParser</code> object, given an EPP XML payload
	 * string
	 *
	 * @param string a string in DOMString format
	 */
	EppParser( const DOMString& string );

	/**
	 * Creates an <code>EppParser</code> object, given an EPP XML payload
	 * string
	 *
	 * @param string a null terminated byte stream
	 */
	EppParser( const char * string );

	/**
	 * Creates an <code>EppParser</code> object, given an EPP XML payload
	 * string and its length
	 *
	 * @param string a minated byte stream
	 * @param length the length of the byte stream
	 */
	EppParser( const char * string, int length );

	/**
	 * Destructor for an <code>EppParser</code> object
	 */
	~EppParser();

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
	void parseFile( const char * file );

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

#endif     /* EPPPARSER_HPP */  /* } */
