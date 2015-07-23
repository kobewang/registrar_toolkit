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

#if ! defined(EPPCOMMANDRENEW_HPP)    /* { */
#define       EPPCOMMANDRENEW_HPP        1

#include <time.h>
#include "EppCommand.hpp"
#include "EppPeriod.hpp"

/**
 * This <code>EppCommandRenew</code> class implements EPP Command Renew
 * entity.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:37 $
 */
class EPP_EXPORT EppCommandRenew : public EppCommand
{
protected:
	/**
	 * The current expiration date of the registration for the EPP object
	 * to be renewed
	 */
	time_t      curExpDate;
	/**
	 * The new registration period for the the EPP object to be renewed
	 */
	EppPeriod * period;

public:

	/**
	 * Creates an <code>EppCommandRenew</code> object
	 */
	EppCommandRenew()
	{
		this->curExpDate = null;
		this->period = null;
	};

	/**
	 * Destructor
	 */
	virtual ~EppCommandRenew()
	{
		if( this->period != null )
		{
			delete this->period;
			this->period = null;
		}
	};

	/**
	 * Returns the run-time type of an EppEntity object
	 */
	virtual int getEntityType()
	{
		return EppEntity::TYPE_EppCommandRenew;
	};

	/**
	 * Gets the current expiration date of the object to be renewed
	 */
	time_t getCurrentExpireDate()
	{
		return this->curExpDate;
	};

	/**
	 * Sets the current expiration date of the object to be renewed
	 */
	void setCurrentExpireDate( time_t curExpDate )
	{
		this->curExpDate = curExpDate;
	};

	/**
	 * Gets the <code>EppPeriod</code> object containing the period
	 * to be renewed for the object
	 */
	EppPeriod * getPeriod()
	{
		return this->period;
	};

	/**
	 * Sets the <code>EppPeriod</code> object containing the period
	 * to be renewed for the object
	 */
	void setPeriod( EppPeriod& period )
	{
		if( this->period == null )
		{
			this->period = new EppPeriod();
		}
		*(this->period) = period;
	};

	/**
	 * Converts an XML element into an <code>EppCommandInfo</code> object.
	 * The caller of this method must make sure that the root node is of an
	 * EPP Command Info entity.
	 *
	 * @param root root node for an <code>EppCommandInfo</code> object
	 *             in XML format
	 *
	 * @return an <code>EppCommandInfo</code> object, or null if the node
	 *         is invalid
	 */
	static EppCommandRenew * fromXML( const DOMNode& root );

	DOMString toString()
	{
		return EppEntity::toString(DOMString("renew"));
	};
};

#endif     /* EPPCOMMANDRENEW_HPP */  /* } */
