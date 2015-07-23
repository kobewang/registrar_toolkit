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

#if ! defined(EPPRESPONSECHECKFEE_HPP)    /* { */
#define       EPPRESPONSECHECKFEE_HPP 1

#include "EppExtension.hpp"
#include "EppResponseCheckFeeType.hpp"
#include <xercesc/util/ValueVectorOf.hpp>

/**
 * This <code>EppResponseCheckFee</code> class implements domain check response specified in the
 * registry fee extension.
 */

class EPP_EXPORT EppResponseCheckFee : public EppExtension
{
public:

	EppResponseCheckFee()
	{
		feeRspDomains = new OldValueVectorOf <EppResponseCheckFeeType*>( MAX_NUM_OF_CHECKS );
	}

	virtual ~EppResponseCheckFee()
	{
		if ( NULL != this->feeRspDomains )
		{
			for ( unsigned int i = 0; i < this->feeRspDomains->size(); i++ )
			{
				EppResponseCheckFeeType *obj = this->feeRspDomains->elementAt( i );

				if ( NULL != obj )
				{
					delete obj;
				}
			}
			delete this->feeRspDomains;
			this->feeRspDomains = NULL;
		}
	}

	void add( EppResponseCheckFeeType* in )
	{
		if ( NULL == feeRspDomains )
		{
			feeRspDomains = new OldValueVectorOf <EppResponseCheckFeeType*>( MAX_NUM_OF_CHECKS );
		}
		feeRspDomains->addElement( in );
	}

	OldValueVectorOf<EppResponseCheckFeeType *> * get() const
	{
		return this->feeRspDomains;
	}

	int getEntityType()
	{
		return EppEntity::TYPE_EppResponseCheckFee;
	}

	DOMString toString()
	{
		return EppEntity::toString( DOMString( "chkData" ) );
	}

	static EppResponseCheckFee* fromXML( const DOMNode& root );
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );

private:
	OldValueVectorOf <EppResponseCheckFeeType*>* feeRspDomains;
};

#endif

