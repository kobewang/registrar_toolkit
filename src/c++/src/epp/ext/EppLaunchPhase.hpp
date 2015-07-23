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

#if ! defined(EPPLAUNCHPHASE_HPP)    /* { */
#define       EPPLAUNCHPHASE_HPP 1
#include <time.h>
#include "EppEntity.hpp"

class EPP_EXPORT EppLaunchPhase : public EppEntity
{
public:
	EppLaunchPhase();/*ctor*/
	EppLaunchPhase(const EppLaunchPhase&);/*copy ctor*/
	virtual ~EppLaunchPhase();/*d'tor*/
	EppLaunchPhase& operator=(const EppLaunchPhase&);/*assigment*/
	/* getter(s) | setter(s) */
	DOMString phase(void);
	void phase(const DOMString &);
	DOMString subPhase(void);
	void subPhase(const DOMString &);
	virtual DOMElement* toXML(DOMDocument &doc, const DOMString &tag);
	static EppLaunchPhase* fromXML( const DOMNode& root );
	virtual DOMString toString();
private:
	DOMString _phase;
	DOMString _sub_phase;
};

#endif
