/* This file is in the public domain.
 * 
 * File:   XercesString.hpp
 * Author: tjoppen
 *
 * Created on February 12, 2010, 5:43 PM
 */

#ifndef _XERCESSTRING_HPP
#define _XERCESSTRING_HPP

#include <xercesc/util/XMLString.hpp>
#include <xercesc/util/KeyValuePair.hpp>

#include <ostream>

using namespace xercesc;


#define MEMMANAGER (XMLPlatformUtils::fgMemoryManager)

class XercesString
{
	XMLCh *xstr;
public:

	XercesString() : xstr(NULL)
	{
	};
	//	XercesString(long int i) : xstr(0L) { };
	XercesString(const char *str);
	XercesString(const std::string& str);

	XercesString(XMLCh *wstr);
	XercesString(const XMLCh *wstr);
	XercesString(const XercesString &copy);

	~XercesString();

	bool append(const XercesString &copy);
	bool append(const XMLCh *tail);
	bool append(const char *tail);
	bool append(const XMLCh inCh);

	bool erase(const XMLCh *head, const XMLCh *tail);
	const XMLCh* begin() const;
	const XMLCh* end() const;

	int size() const;
	int length() const;

	bool operator!= (const XercesString& str) const;
	bool operator!= (const XMLCh* str) const;
	bool operator!= (const char* str) const;

	bool operator== (const XercesString& str) const;
	bool operator== (const XMLCh* str) const;
	bool operator== (const char* str) const;

	const XMLCh operator [] (const int i) const;
	//XMLCh operator [] (int i);
	//XMLCh & operator [] (const int i);

	operator const XMLCh * () const
	{
		return xstr;
	};

	XMLCh charAt(unsigned int index) const;
	char* transcode() const;
	//char* c_str() const;

	static void Delete(char* toRelease)
	{
		if ( NULL == toRelease )
		{
			return;
		}
		XMLPlatformUtils::fgMemoryManager->deallocate(toRelease);
	}

	bool isNull() const;
	bool isNotNull() const;

	bool equals(const XercesString& str) const;
	bool equals(const XMLCh* str) const;
	bool equals(const char* str) const;

	XercesString substringData(unsigned int startIndex, unsigned int count) const;
	XercesString& operator= (const XercesString &in);
	XercesString& operator= (const char* in);

	XercesString operator+ (const XercesString &in);
	XercesString operator+ (const char* in);

	void operator+= (const XercesString &in);
	void operator+= (const XMLCh* in);
	void operator+= (const char* in);
	void operator+= (const XMLCh inCh);

	void trim()
	{
		XMLString::trim(xstr);
	}


	//TODO: Santosh, find out some good place for this. May be EppUtil.

	static int stricmp(const char* str1, const char*  str2)
	{
		return strcasecmp(str1, str2);
	}
};

std::ostream& operator<< (std::ostream& os, const XercesString& str);
std::ostream& operator<< (std::ostream& os, const XMLCh* str);


typedef XercesString XS;
typedef XercesString DOMString;


//#define NULLXS (0L)
#define NULLXS ((XMLCh*)0L)

typedef KeyValuePair<DOMString*, DOMString*> DOMStringPtrPair;
#endif

