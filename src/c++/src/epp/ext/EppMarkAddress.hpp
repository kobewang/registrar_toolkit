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

#if ! defined(EPPEPPMARKADDRESS_HPP)    /* { */
#define       EPPEPPMARKADDRESS_HPP 1
#include <time.h>
#include "EppEntity.hpp"
#include "EppMarkNameSpace.hpp"

class EPP_EXPORT EppMarkAddress: public EppEntity, public EppMarkNameSpace
{
public:
	EppMarkAddress(const char* ns = "mark");/*ctor*/
	EppMarkAddress(const EppMarkAddress&);/*copy ctor*/
	virtual ~EppMarkAddress();/*d'tor*/
	EppMarkAddress& operator=(const EppMarkAddress&);/*assigment*/
	/* getter(s) | setter(s) */
	DOMString streetLine1();/*getter street line1*/
	void streetLine1(DOMString &line1);/*setter street line1*/
	DOMString streetLine2();/*getter street line1*/
	void streetLine2(DOMString &line1);/*setter street line1*/
	DOMString streetLine3();/*getter street line1*/
	void streetLine3(const DOMString &line1);/*setter street line1*/
	DOMString city(void);
	void city(const DOMString&);
	DOMString stateProvince(void);
	void stateProvince(const DOMString &);
	DOMString postalCode(void);
	void postalCode(const DOMString &);
	DOMString countryCode(void);
	void countryCode(const DOMString &);
	virtual DOMElement* toXML(DOMDocument &doc, const DOMString &tag);
	static EppMarkAddress* fromXML( const DOMNode& root, const char* ns = "mark" );
	virtual DOMString toString();
	virtual int getEntityType();

private:
	DOMString _street[3]; /*one, two or three ; optional street*/
	DOMString _city;
	DOMString _sp;/* optional state/province*/
	DOMString _pc;/*postal code*/
	DOMString _cc;/*country code*/
};

#endif
