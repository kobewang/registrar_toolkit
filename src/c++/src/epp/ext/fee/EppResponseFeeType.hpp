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

#ifndef EPPRESPONSEFEETYPE
#define	EPPRESPONSEFEETYPE

#include "EppEntity.hpp"
#include "EppFeeFee.hpp"
#include "EppRequestFeeType.hpp"
#include <xercesc/util/ValueVectorOf.hpp>

/**
 * This <code>EppResponseFeeType</code> class is base class for most of transformation command response 
 * used by registry fee extension. 
 */

class EPP_EXPORT EppResponseFeeType : public EppRequestFeeType
{
public:

	EppResponseFeeType()
	{
	}

	virtual ~EppResponseFeeType()
	{
	}

	DOMString getBalance() const
	{
		return this->balance;
	}

	void setBalance( const DOMString& balance )
	{
		this->balance = balance;
	}

	DOMString getCreditLimit() const
	{
		return this->creditLimit;
	}

	void setCreditLimit( const DOMString& creditLimit )
	{
		this->creditLimit = creditLimit;
	}

	static void fromXMLCommon( const DOMNode& root, EppResponseFeeType* data );
	virtual void toXML( DOMDocument& doc, DOMElement& element );

protected:
	DOMString balance;
	DOMString creditLimit;
};

#endif	/* EPPRESPONSEFEETYPE */

