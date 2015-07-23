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

#if ! defined(EPPEPPTRADEMARKDATA_HPP)    /* { */
#define       EPPEPPTRADEMARKDATA_HPP 1
#include <time.h>
#include "EppEntity.hpp"
#include "EppMarkData.hpp"

class EPP_EXPORT EppTrademarkData : public EppMarkData
{
public:
	EppTrademarkData(const char* ns = "mark");
	EppTrademarkData(const EppTrademarkData&);
	virtual ~EppTrademarkData();
	EppTrademarkData& operator=(const EppTrademarkData&);

	void jurisdiction(const DOMString&);
	DOMString jurisdiction(void);

	void tmClass(int);
	ValueVectorOf<int> tmClass(void);


	void apId(const DOMString &);
	DOMString apId(void);

	void apDate(const DOMString &);
	time_t apDate(void);

	void regNum(const DOMString &);
	DOMString regNum(void);

	void regDate(const DOMString &);
	time_t regDate(void);

	void exDate(const DOMString &);
	time_t exDate(void);

	virtual DOMElement* toXML(DOMDocument &doc, const DOMString &tag);
	static EppTrademarkData* fromXML( const DOMNode& root, const char* ns = "mark" );
	virtual DOMString toString();

private:
	DOMString _jurisdiction;
	ValueVectorOf<int> _class; /* * */
	DOMString _apId; /*optional*/
	time_t _apDate; /*optional*/
	DOMString _regNum; /*registration number of TM*/
	time_t _regDate; /*registration date*/
	time_t _exDate;/*optional: expiration date*/
};
#endif
