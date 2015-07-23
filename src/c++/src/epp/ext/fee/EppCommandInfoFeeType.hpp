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

#ifndef EPPCOMMANDINFOFEETYPE_HPP
#define	EPPCOMMANDINFOFEETYPE_HPP

#include "EppEntity.hpp"
#include "EppPeriod.hpp"
#include "EppFeeCommand.hpp"

/**
 * This <code>EppCommandInfoFeeType</code> class is data holder class used by domain info command 
 * by registry fee extension 
 */

class EPP_EXPORT EppCommandInfoFeeType
{
public:

	EppCommandInfoFeeType()
	: currency()
	{
		command = NULL;
		period = NULL;
	}

	virtual ~EppCommandInfoFeeType()
	{
		if ( NULL != command )
		{
			delete command;
			command = NULL;
		}
		if ( NULL != period )
		{
			delete period;
			period = NULL;
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

	EppPeriod* getPeriod() const
	{
		return this->period;
	}

	void setPeriod( EppPeriod* period )
	{
		this->period = period;
	}

	EppFeeCommand* getCommand() const
	{
		return this->command;
	}

	void setCommand( EppFeeCommand* command )
	{
		this->command = command;
	}

	static EppCommandInfoFeeType* fromXML( const DOMNode& root );
	void fromXMLCommon( const DOMNode& root, EppCommandInfoFeeType* data );

	virtual DOMElement* toXML( DOMDocument& doc, const DOMString& tag );
	virtual void toXMLCommon( DOMDocument& doc, const DOMString& tag, DOMElement& inElement );

protected:
	DOMString currency;

	EppFeeCommand *command;
	EppPeriod *period;
};

#endif	/* EPPCOMMANDINFOFEETYPE_HPP */

