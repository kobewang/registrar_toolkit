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

#if ! defined(EPPEPPMARKDATA_HPP)    /* { */
#define       EPPEPPMARKDATA_HPP 1
#include <time.h>
#include "EppEntity.hpp"
#include "EppMarkHolder.hpp"
#include "EppMarkNameSpace.hpp"

#include <xercesc/util/ValueVectorOf.hpp>

class EPP_EXPORT EppMarkData: public EppEntity, public EppMarkNameSpace
{
public:
	EppMarkData(const char* ns = "mark");
	EppMarkData(const EppMarkData&);
	virtual ~EppMarkData();
	EppMarkData& operator=(const EppMarkData&);
	void id(const DOMString &_id);
	DOMString id(void);
	void markName(const DOMString& _name);
	DOMString markName(void);
	void addHolder(const EppMarkHolder&);
	OldValueVectorOf<EppMarkHolder*> holder(void);
	void addContact(const EppMarkHolder&);
	OldValueVectorOf<EppMarkHolder*> contact(void);
	void addLabel(const DOMString&);
	OldValueVectorOf<DOMString> labels(void);
	void goodsServices(const DOMString&);
	DOMString goodsServices(void);
	virtual DOMElement* toXML(DOMDocument &doc, const DOMString &tag);
	void fromXml( const DOMNode& root );/*check the name of the function.it's not by accident.*/
	/*virtual DOMString toString(); by design we have commented it out*/
protected:
	DOMString Id;
	DOMString _markName;
	OldValueVectorOf<EppMarkHolder*> _holder; /* ? */
	OldValueVectorOf<EppMarkHolder*> _contact; /* * */
	OldValueVectorOf<DOMString> _label; /* * */
	DOMString goodsAndServices;
};
#endif
