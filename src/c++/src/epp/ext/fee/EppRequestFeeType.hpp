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

#ifndef EPPREQUESTFEETYPE
#define	EPPREQUESTFEETYPE

#include "EppEntity.hpp"
#include "EppFeeFee.hpp"
#include "EppFeeDefs.hpp"
#include <xercesc/util/ValueVectorOf.hpp>

/**
 * This <code>EppRequestFeeType</code> class is base class for most of transformation command request 
 * used by registry fee extension.
 */

class EPP_EXPORT EppRequestFeeType
{
public:

	EppRequestFeeType()
	: currency()
	{
		fee = NULL;
	}

	virtual ~EppRequestFeeType()
	{
		if ( NULL != fee )
		{
			for ( unsigned int i = 0; i < fee->size(); i++ )
			{
				EppFeeFee* obj = fee->elementAt( i );
				if ( NULL != obj )
				{
					delete obj;
				}
			}
			delete fee;
			fee = NULL;
		}
	}

	DOMString getCurrency() const
	{
		return this->currency;
	}

	void setCurrency( const DOMString& currency )
	{
		this->currency = currency;
	}

	void addFee( EppFeeFee* fe )
	{
		if ( NULL == fe )
		{
			return;
		}
		if ( NULL == this->fee )
		{
			fee = new OldValueVectorOf <EppFeeFee*>( MAX_NUM_FEE_FEE );
		}

		this->fee->addElement( fe );
	}

	OldValueVectorOf<EppFeeFee *> * getFee() const
	{
		return this->fee;
	}

	static void fromXMLCommon( const DOMNode& root, EppRequestFeeType* data );
	virtual void toXML( DOMDocument& doc, DOMElement& element );

protected:
	DOMString currency;
	OldValueVectorOf<EppFeeFee*> *fee;

};

#endif	/* EPPREQUESTFEETYPE */

