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

#ifndef EPPFEECREDIT_HPP
#define	EPPFEECREDIT_HPP

#include "EppEntity.hpp"
#include "EppUtil.hpp"

/**
 * This <code>EppFeeCredit</code> class implements fee:credit element of registry fee extension.
 */

class EPP_EXPORT EppFeeCredit : public EppEntity
{
public:

	EppFeeCredit()
	: description()
	, credit()// -negative
	{
	};

	virtual ~EppFeeCredit(){
		// Do nothing
	}

	const DOMString& getDescription() const
	{
		return description;
	}

	void setDescription( const DOMString& description )
	{
		this->description = description;
	}

	const DOMString& getCredit() const
	{
		return credit;
	}

	void setCredit( const DOMString& fee )
	{
		this->credit = fee;
	}

	virtual DOMString toString()
	{
		return EppEntity::toString( DOMString( "credit" ) );
	}

	virtual DOMElement* toXML( DOMDocument &doc, const DOMString &tag );
	static EppFeeCredit* fromXML( const DOMNode& root );

protected:
	DOMString description;
	DOMString credit; // -negative
};


#endif	/* EPPFEEFEE_HPP */

