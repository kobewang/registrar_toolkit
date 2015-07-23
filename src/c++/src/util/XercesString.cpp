/* This file is in the public domain.
 * 
 * File:   XercesString.cpp
 * Author: tjoppen
 * 
 * Created on February 12, 2010, 5:43 PM
 */

#include "XercesString.hpp"
#include <xercesc/dom/DOMException.hpp>
#include <stdlib.h>

using namespace std;

XercesString::XercesString( const char *str )
: xstr( NULL )
{
	if ( str )
	{
		xstr = XMLString::transcode(str);
	}
}

XercesString::XercesString( const std::string &str )
: xstr( NULL )
{
	if ( str.length() > 0 )
	{
		xstr = XMLString::transcode(str.c_str());
	}
}

XercesString::XercesString( XMLCh *wstr )
: xstr( NULL )
{
	xstr = XMLString::replicate(wstr);
};

XercesString::XercesString( const XMLCh *wstr )
: xstr( NULL )
{
	xstr = XMLString::replicate(wstr);
}

XercesString::XercesString( const XercesString &right )
: xstr( NULL )
{
	xstr = XMLString::replicate(right.xstr);
}

XercesString::~XercesString( )
{
	if ( xstr )
	{
		XMLString::release(&xstr);
	}
}

bool XercesString::append( const XercesString &copy )
{
	return append(copy.operator const XMLCh * ( ));
}

bool XercesString::append( const XMLCh *tail )
{
	int iTailLen = XMLString::stringLen(tail);
	int iWorkLen = XMLString::stringLen(xstr);
	//XMLCh *result = new XMLCh[ iWorkLen + iTailLen + 1 ];

	XMLCh* result = ( XMLCh* ) XMLPlatformUtils::fgMemoryManager->allocate(( iWorkLen + iTailLen + 1 ) * sizeof (XMLCh ));

	bool bOK = result != NULL;

	if ( bOK )
	{
		XMLCh *target = result;
		XMLString::moveChars(target, xstr, iWorkLen);
		target += iWorkLen;
		XMLString::moveChars(target, tail, iTailLen);
		target += iTailLen;
		*target++ = 0;
		XMLString::release(&xstr);
		xstr = result;
	}
	return bOK;
}

bool XercesString::append( const char *tail )
{
	XMLCh *result = XMLString::transcode(tail);
	bool rc = append(result);
	XMLString::release(&result);

	return rc;
}

bool XercesString::append( const XMLCh inCh )
{
	int iTailLen = 1;
	int iWorkLen = XMLString::stringLen(xstr);

	XMLCh* result = ( XMLCh* ) XMLPlatformUtils::fgMemoryManager->allocate(( iWorkLen + iTailLen + 1 ) * sizeof (XMLCh ));

	bool bOK = result != NULL;

	if ( bOK )
	{
		XMLCh *target = result;
		XMLString::moveChars(target, xstr, iWorkLen);
		target += iWorkLen;
		XMLString::moveChars(target, &inCh, iTailLen);
		target += iTailLen;
		*target++ = 0;
		XMLString::release(&xstr);
		xstr = result;
	}
	return bOK;
}

bool XercesString::erase( const XMLCh *head, const XMLCh *tail )
{
	bool bOK = head <= tail && head >= begin() && tail <= end();

	if ( bOK )
	{
		XMLCh *result = new XMLCh[ size() - ( tail - head ) + 1 ];
		XMLCh *target = result;
		bOK = target != NULL;

		if ( bOK )
		{
			const XMLCh *cursor = begin();

			while ( cursor != head )
			{
				*target++ = *cursor++;
			}
			cursor = tail;
			while ( cursor != end() )
			{
				*target++ = *cursor++;
			}
			*target++ = 0;

			XMLString::release(&xstr);
			xstr = result;
		}
	}
	return bOK;
}

const XMLCh* XercesString::begin( ) const
{
	return xstr;
}

const XMLCh* XercesString::end( ) const
{
	return xstr + size();
}

int XercesString::size( ) const
{
	return XMLString::stringLen(xstr);
}

int XercesString::length( ) const
{
	return size();
}

//XMLCh & XercesString::operator [] (const int i)
//{
//	std::cout<<"\n"<<"XMLCh & XercesString::operator []"<<"\n";  	
//	return xstr[i];
//}

const XMLCh XercesString::operator [] ( const int i ) const
{
	if ( i <= size() )
	{
		return xstr[i];
	}

	return 0L;
}

//XMLCh XercesString::operator [] (int i)
//{
//	return xstr[i];	
//}

bool XercesString::operator !=( const char* str ) const
{
	XercesString in(str);
	return !XMLString::equals(xstr, in.operator const XMLCh * ( ));
}

bool XercesString::operator !=( const XercesString& str ) const
{
	return !XMLString::equals(xstr, str.xstr);
}

bool XercesString::operator !=( const XMLCh* str ) const
{
	return !XMLString::equals(xstr, str);
}

bool XercesString::operator ==( const char* str ) const
{
	XercesString in(str);
	return XMLString::equals(xstr, in.operator const XMLCh * ( ));
}

bool XercesString::operator==( const XercesString& str ) const
{
	return XMLString::equals(xstr, str.xstr);
}

bool XercesString::operator==( const XMLCh* str ) const
{
	return XMLString::equals(xstr, str);
}

XMLCh XercesString::charAt( unsigned int index ) const
{
	return xstr[index];
}

char* XercesString::transcode( ) const
{
	if ( 0 == size() )
	{
		return NULL;
	}

	char* t = XMLString::transcode(xstr);
	return t;
}

//char* XercesString::c_str() const
//{
//	return transcode();
//}

bool XercesString::isNull( ) const
{
	return (NULL == xstr );
	//return (0 == size());
}

bool XercesString::isNotNull( ) const
{
	//return (0 != size());
	return (NULL != xstr );
}

bool XercesString::equals( const XercesString& str ) const
{
	return operator==( str );
}

bool XercesString::equals( const XMLCh* str ) const
{
	return operator==( str );
}

bool XercesString::equals( const char* str ) const
{
	return operator==( str );
}

XercesString XercesString::substringData( unsigned int startIndex, unsigned int count ) const
{
	if ( count == 0 )
	{
		return NULLXS;
	}

	unsigned int thisLen = size();

	if ( startIndex >= thisLen )
	{
		throw DOMException(DOMException::INDEX_SIZE_ERR, 0);
	}

	// Cap count to the string length to eliminate overflow
	// problems when we get passed bogus values, like -1.
	if ( count > thisLen )
	{
		count = thisLen;
	}

	// If the count extends past the end of the string, cut it
	// back so that the returned string will stop at the end
	// of the source string.
	if ( startIndex + count >= thisLen )
	{
		count = thisLen - startIndex;
	}

	XMLCh tStr [count + 1];

	XMLString::subString(tStr, xstr, startIndex, startIndex + count);

	return tStr;
}

XercesString XercesString::operator+( const XercesString &in )
{
	XercesString first = *this;
	first.append(in);
	return first;
}

XercesString XercesString::operator+( const char* in )
{
	XercesString first = *this;
	first.append(in);
	return first;
}

XercesString& XercesString::operator=( const char* in )
{
	if ( xstr )
	{
		XMLString::release(&xstr);
	}
	xstr = NULL;
	if ( in != NULL )
	{
		xstr = XMLString::transcode(in);
	}
	return *this;
}

XercesString& XercesString::operator=( const XercesString &in )
{
	if ( xstr )
	{
		XMLString::release(&xstr);
	}

	xstr = XMLString::replicate(in.xstr);
	return *this;
}

void XercesString::operator+=( const XercesString &in )
{
	this->append(in);
}

void XercesString::operator+=( const XMLCh* in )
{
	this->append(in);
}

void XercesString::operator+=( const char* in )
{
	this->append(in);
}

void XercesString::operator+=( const XMLCh inCh )
{
	this->append(inCh);
}

ostream& operator<<( ostream& os, const XercesString& str )
{
	if ( 0 == str.size() )
	{
		//return os;		
		return os << "(NULL)";
	}

	char* t = XMLString::transcode(str);
	os << t;
	XMLString::release(&t);
	return os;
}

ostream& operator<<( ostream& os, const XMLCh* str )
{
	if ( !str )
	{
		//return os;
		return os << "(null)";
	}

	return os << XercesString(str);
}
