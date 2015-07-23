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

#ifndef EPPRESPONSECHECKFEETYPE_HPP
#define	EPPRESPONSECHECKFEETYPE_HPP

#include "EppEntity.hpp"
#include "EppFeeFee.hpp"
#include "EppFeeDefs.hpp"
#include "EppCommandCheckFeeType.hpp"

#include <xercesc/util/ValueVectorOf.hpp>

/**
 * This <code>EppResponseCheckFeeType</code> class is data holder class used by domain check response
 * by registry fee extension  
 */

class EPP_EXPORT EppResponseCheckFeeType : public EppCommandCheckFeeType
{
public:

	EppResponseCheckFeeType()
	: fClass()
	{
		fee = NULL;
	}

	virtual ~EppResponseCheckFeeType()
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

	DOMString getFeeClass() const
	{
		return this->fClass;
	}

	void setFeeClass( const DOMString& fClas )
	{
		this->fClass = fClas;
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

	static EppResponseCheckFeeType* fromXML( const DOMNode& root );
	void fromXMLCommon( const DOMNode& root, EppResponseCheckFeeType* data );

	virtual DOMElement* toXML( DOMDocument& doc, const DOMString& tag );
	void toXMLCommon( DOMDocument &doc, const DOMString &tag, DOMElement& inElement );

protected:
	DOMString fClass;
	OldValueVectorOf<EppFeeFee*> *fee;

};

#endif	/* EPPRESPONSECHECKFEETYPE_HPP */

