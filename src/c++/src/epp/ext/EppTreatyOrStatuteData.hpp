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

#if ! defined(EPPTREATYORSTATUTEDATA_HPP)    /* { */
#define       EPPTREATYORSTATUTEDATA_HPP 1
#include <time.h>
#include "EppEntity.hpp"
#include "EppMarkData.hpp"
#include "EppLaunchProtection.hpp"

class EPP_EXPORT EppTreatyOrStatuteData : public EppMarkData
{
public:
	EppTreatyOrStatuteData(const char* ns );
	EppTreatyOrStatuteData(const EppTreatyOrStatuteData&);
	virtual ~EppTreatyOrStatuteData();
	EppTreatyOrStatuteData& operator=(const EppTreatyOrStatuteData&);

	void refNum(const DOMString&);
	DOMString refNum(void);

	void proDate(time_t);
	void proDate(const DOMString &);
	time_t proDate(void);

	void title(const DOMString& _cc);
	DOMString title(void);

	void exDate(time_t);
	void exDate(const DOMString &);
	time_t exDate(void);

	void addRegion(const EppLaunchProtection& _r);
	ValueVectorOf<EppLaunchProtection> region(void);

	virtual DOMElement* toXML(DOMDocument &doc, const DOMString &tag);
	static EppTreatyOrStatuteData* fromXML( const DOMNode& root, const char* ns );
	virtual DOMString toString();

private:
	ValueVectorOf<EppLaunchProtection> _region;
	DOMString _refNum; /*registration number of TM*/
	time_t _proDate; /*optional*/
	DOMString _title;
	time_t _exDate; /*optional*/
};
#endif
