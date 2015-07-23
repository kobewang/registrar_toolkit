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

#if ! defined(EPPCOMMANDCREATELAUNCHREGISTRATION_HPP)    /* { */
#define       EPPCOMMANDCREATELAUNCHREGISTRATION_HPP 1
#include "EppExtension.hpp"
#include "EppSignedMarkData.hpp"
#include "EppLaunchPhase.hpp"
#include <xercesc/util/ValueVectorOf.hpp>
#include "EppEncodedSignedMarkData.hpp"

class EPP_EXPORT EppCommandCreateLaunchRegistration : public EppExtension
{
public:
	EppCommandCreateLaunchRegistration();
	EppCommandCreateLaunchRegistration(const EppCommandCreateLaunchRegistration&);
	EppCommandCreateLaunchRegistration& operator=(const EppCommandCreateLaunchRegistration&);
	virtual ~EppCommandCreateLaunchRegistration();
	void phase(const EppLaunchPhase &_p);
	EppLaunchPhase phase(void);
	void signedMark(const EppSignedMarkData&);
	EppSignedMarkData signedMark(void);
	void type(const DOMString &);
	DOMString type(void);
	static EppCommandCreateLaunchRegistration* fromXML( const DOMNode& root );
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );
	virtual DOMString toString();
	virtual int getEntityType();
	void encodedSignedMark(const EppEncodedSignedMarkData&);
	EppEncodedSignedMarkData encodedSignedMark(void);
	DOMString noticeID();
	void noticeID(const DOMString &);
	DOMString notAfter();
	void notAfter(const DOMString &);
	DOMString acceptedDate();
	void acceptedDate(const DOMString &);
private:
	EppLaunchPhase _phase;
	DOMString _type;
	EppSignedMarkData _signedMark;
	EppEncodedSignedMarkData _encSignedMark;
	DOMString _noticeID;
	DOMString _notAfter;
	DOMString _acceptedDate;
};

#endif
