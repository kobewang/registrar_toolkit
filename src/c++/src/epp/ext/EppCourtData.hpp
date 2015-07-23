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

#if ! defined(EPPEPPCOURTDATA_HPP)    /* { */
#define       EPPEPPCOURTDATA_HPP 1
#include <time.h>
#include "EppEntity.hpp"
#include "EppMarkData.hpp"

class EPP_EXPORT EppCourtData : public EppMarkData
{
public:
	EppCourtData(const char* ns = "mark");
	EppCourtData(const EppCourtData&);
	virtual ~EppCourtData();
	EppCourtData& operator=(const EppCourtData&);

	void refNum(const DOMString&);
	DOMString refNum(void);

	void proDate(const DOMString &);
	time_t proDate(void);

	void countryCode(const DOMString& _cc);
	DOMString countryCode(void);

	void addRegion(const DOMString& _r);
	ValueVectorOf<DOMString> region(void);

	void courtName(const DOMString&);
	DOMString courtName(void);

	virtual DOMElement* toXML(DOMDocument &doc, const DOMString &tag);
	static EppCourtData* fromXML( const DOMNode& root, const char* ns );
	virtual DOMString toString();

private:
	DOMString _refNum; /*registration number of TM*/
	time_t _proDate; /*optional*/
	DOMString _cc;
	ValueVectorOf<DOMString> _region;
	DOMString _courtName;
};
#endif
