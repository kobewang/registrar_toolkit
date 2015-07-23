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

#if ! defined(EPPSIGNEDMARKDATA_HPP)    /* { */
#define       EPPSIGNEDMARKDATA_HPP 1
#if 0
#include <time.h>
#endif
#include "EppEntity.hpp"
#if 0
#include "EppSignedMarkIssuerInfo.hpp"
#include "EppMarkData.hpp"
#include "EppSignature.hpp"
#endif

class EPP_EXPORT EppSignedMarkData: public EppEntity
{
public:
	EppSignedMarkData();
	EppSignedMarkData(const EppSignedMarkData&);
	EppSignedMarkData& operator=(const EppSignedMarkData&);
	virtual ~EppSignedMarkData();
#if 0
	void id(const std::string&);
	std::string id();

	void issuer(const EppSignedMarkIssuerInfo&);
	EppSignedMarkIssuerInfo issuer(void);

	void mark(const EppMarkData&);
	EppMarkData mark(void);

	void signature(const EppSignature&);
	EppSignature signature(void);

	void attribID(const std::string&);
	std::string attribID(void);
#endif
	void rawXml(char*);
	const std::string& rawXml(void);
	bool hasSMD();
	void hasSMD(bool);
#if 0
	void notBefore(time_t);
	void notBefore(const std::string&);
	time_t notBefore(void);

	void notAfter(time_t);
	void notAfter(const std::string&);
	time_t notAfter(void);
#endif
	virtual DOMElement* toXML(DOMDocument &doc, const DOMString &tag);
	virtual DOMString toString();
	static EppSignedMarkData* fromXML( const DOMNode& root );
	virtual int getEntityType();
private:
#if 0
	/*we shall be keeping only the raw XML and shall be passed to APP as is*/
	std::string id;
	EppSignedMarkIssuerInfo _issuer;
	time_t _notBefore;
	time_t _notAfter;
	EppMarkData _mark;
	EppSignature _sig;
	std::string _attributeID;/*required*/
#endif
	std::string _rawXmlString; /*shall only be used in C++ RTK*/
	bool _hasElement;
};
#endif
