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

#if ! defined(EPPEPPMARKHOLDER_HPP)    /* { */
#define       EPPEPPMARKHOLDER_HPP 1
#include <time.h>
#include "EppEntity.hpp"
#include "EppMarkAddress.hpp"
#include "EppE164.hpp"

enum holderType
{
	EPPMARK_HOLDER, EPPMARK_CONTACT
};

class EPP_EXPORT EppMarkHolder : public EppEntity, public EppMarkNameSpace
{
public:
	EppMarkHolder( holderType _t = EPPMARK_HOLDER, const char* ns = "mark");
	EppMarkHolder( const EppMarkHolder &);
	virtual ~EppMarkHolder();
	EppMarkHolder& operator=(const EppMarkHolder&);

	void name(const DOMString&);
	DOMString name(void);
	void org(const DOMString&);
	DOMString org(void);
	void address(const EppMarkAddress &);
	EppMarkAddress address(void);
	void voice(const DOMString&);
	DOMString voice(void);
	void fax(const DOMString&);
	DOMString fax(void);
	void email(const DOMString&);
	DOMString email(void);
	void type(holderType _t);
	holderType type(void);
	void holderParam(const DOMString &);
	DOMString holderParam(void);

	/* NOTE: need to keep _type field in consideration as attribute type/entitlement depends on it.*/
	virtual DOMElement* toXML(DOMDocument &doc, const DOMString &tag);
	/* NOTE: need to keep _type field in consideration as attribute type/entitlement depends on it.*/
	static EppMarkHolder* fromXML( const DOMNode& root, const char* ns );
	virtual DOMString toString();

private:
	DOMString _name;/* optional unless org is specified */
	DOMString _org;/* optional if _name is specified */
	EppMarkAddress _addr;/* address of the entiry */
	EppE164 _voice; /* optional phone number */
	EppE164 _fax; /* optional fax number */
	DOMString _email; /* optional email */
	DOMString _addParam; /*some TM data can specify type/entitlement param to oject*/
	holderType _type; /*identifies if it's type of holder or contact*/
};
#endif
