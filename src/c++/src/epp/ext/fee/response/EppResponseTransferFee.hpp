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

#if ! defined(EPPRESPONSETRANSFERFEE_HPP)    /* { */
#define       EPPRESPONSETRANSFERFEE_HPP 1

#include "EppExtension.hpp"
#include "EppResponseFeeType.hpp"
#include "EppPeriod.hpp"

/**
 * This <code>EppResponseTransferFee</code> class implements domain transfer response specified in the
 * registry fee extension.
 */

class EPP_EXPORT EppResponseTransferFee : public EppExtension, public EppResponseFeeType
{
public:

	EppResponseTransferFee()
	{
		period = NULL;
	}

	virtual ~EppResponseTransferFee()
	{
		if ( NULL != period )
		{
			delete period;
			period = NULL;
		}
	}

	EppPeriod* getPeriod() const
	{
		return this->period;
	}

	void setPeriod( EppPeriod* period )
	{
		this->period = period;
	}

	int getEntityType()
	{
		return EppEntity::TYPE_EppResponseTransferFee;
	}

	DOMString toString()
	{
		return EppEntity::toString( DOMString( "trnData" ) );
	}

	static EppResponseTransferFee* fromXML( const DOMNode& root );
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );

protected:
	EppPeriod* period;

};

#endif

