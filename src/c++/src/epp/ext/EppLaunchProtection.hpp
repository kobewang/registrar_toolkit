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

#if ! defined(EPPLAUNCHPROTECTION_HPP)    /* { */
#define       EPPLAUNCHPROTECTION_HPP 1
#include <time.h>
#include "EppEntity.hpp"
#include "EppMarkNameSpace.hpp"

class EPP_EXPORT EppLaunchProtection : public EppEntity, public EppMarkNameSpace
{
public:
	EppLaunchProtection(const char* ns = "mark" );/*ctor*/
	EppLaunchProtection(const EppLaunchProtection&);/*copy ctor*/
	virtual ~EppLaunchProtection();/*d'tor*/
	EppLaunchProtection& operator=(const EppLaunchProtection&);/*assigment*/
	/* getter(s) | setter(s) */
	DOMString region(void);
	void region(const DOMString &);
	DOMString countryCode(void);
	void countryCode(const DOMString &);
	DOMString ruling(void);
	void ruling(const DOMString &);
	virtual DOMElement* toXML(DOMDocument &doc, const DOMString &tag);
	static EppLaunchProtection* fromXML( const DOMNode& root, const char* ns );
	virtual DOMString toString();
private:
	DOMString _cc;/*country code*/
	DOMString _ruling;/*attribute length 2*/
	DOMString _region;
};

#endif
