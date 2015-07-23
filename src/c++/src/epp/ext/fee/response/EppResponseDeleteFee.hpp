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

#if ! defined(EPPRESPONSEDELETEFEE_HPP)    /* { */
#define       EPPRESPONSEDELETEFEE_HPP 1

#include "EppExtension.hpp"
#include "EppFeeCredit.hpp"
#include "EppFeeDefs.hpp"

/**
 * This <code>EppResponseDeleteFee</code> class implements domain delete response specified in the
 * registry fee extension.
 */

class EPP_EXPORT EppResponseDeleteFee : public EppExtension//, public EppResponseFeeType
{
public:

	EppResponseDeleteFee()
	: currency()
	, balance()
	, creditLimit()
	{
		credit = NULL;
	}

	virtual ~EppResponseDeleteFee()
	{
		if ( NULL != credit )
		{
			for ( unsigned int i = 0; i < credit->size(); i++ )
			{
				EppFeeCredit* obj = credit->elementAt( i );
				if ( NULL != obj )
				{
					delete obj;
				}
			}
			delete credit;
			credit = NULL;
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

	void addCredit( EppFeeCredit* cr )
	{
		if ( NULL == cr )
		{
			return;
		}
		if ( NULL == this->credit )
		{
			credit = new OldValueVectorOf <EppFeeCredit*>( MAX_NUM_FEE_FEE );
		}

		this->credit->addElement( cr );
	}

	OldValueVectorOf<EppFeeCredit *> * getCredit() const
	{
		return this->credit;
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

	int getEntityType()
	{
		return EppEntity::TYPE_EppResponseDeleteFee;
	}

	DOMString toString()
	{
		return EppEntity::toString( DOMString( "delData" ) );
	}

	static EppResponseDeleteFee* fromXML( const DOMNode& root );
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );

protected:
	DOMString currency;
	OldValueVectorOf<EppFeeCredit*> *credit;
	DOMString balance;
	DOMString creditLimit;

};

#endif

