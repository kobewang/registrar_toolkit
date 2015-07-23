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

#if ! defined(EPPCOMMANDRENEWDOMAIN_HPP)    /* { */
#define       EPPCOMMANDRENEWDOMAIN_HPP        1

#include "EppCommandRenew.hpp"

/**
 * This <code>EppCommandRenewDomain</code> class implements EPP Command Renew
 * entity for EPP Domain objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:37 $
 */
class EPP_EXPORT EppCommandRenewDomain : public EppCommandRenew
{
private:
	DOMString    name;

public:

	/**
	 * Creates an <code>EppCommandRenewDomain</code> object
	 */
	EppCommandRenewDomain()
	{
		this->name = NULLXS;
	};

	/**
	 * Creates an <code>EppCommandRenewDomain</code> object with a
	 * default expiration period, specified by the registry
	 */
	EppCommandRenewDomain( DOMString name, time_t curExpDate )
	{
		this->name = name;
		this->curExpDate = curExpDate;
	};

	/**
	 * Creates an <code>EppCommandRenewDomain</code> object with a
	 * client transaction id associated with the operation. The current
	 * date of expiration would be the current date.
	 */
	EppCommandRenewDomain( DOMString name, DOMString xid )
	{
		this->name = name;
		this->curExpDate = time(0);
		this->clTRID = xid;
	};

	/**
	 * Creates an <code>EppCommandRenewDomain</code> object with a
	 * default expiration period, specified by the registry, and a
	 * client transaction id associated with the operation
	 */
	EppCommandRenewDomain( DOMString name, time_t curExpDate, DOMString xid )
	{
		this->name = name;
		this->curExpDate = curExpDate;
		this->clTRID = xid;
	};

	/**
	 * Creates an <code>EppCommandRenewDomain</code> object with a
	 * specified expiration period, and a client transaction id associated
	 * with the operation
	 */
	EppCommandRenewDomain( DOMString name, time_t curExpDate, EppPeriod& period, DOMString xid )
	{
		this->name = name;
		this->curExpDate = curExpDate;
		this->clTRID = xid;
		this->setPeriod(period);
	}

	/**
	 * Destructor
	 */
	~EppCommandRenewDomain() {};

	/**
	 * Returns the run-time type of an EppEntity object
	 */
	virtual int getEntityType()
	{
		return EppEntity::TYPE_EppCommandRenewDomain;
	};

	/**
	 * Gets the name of the domain to be renewed
	 */
	DOMString getName()
	{
		return this->name;
	};

	/**
	 * Sets the name of the domain to be renewed
	 */
	void setName( DOMString name )
	{
		this->name = name;
	};

	/**
	 * Converts the <code>EppCommandRenewDomain</code> object into
	 *		an XML element
	 *
	 * @param doc the XML <code>DOMElement</code> object
	 * @param tag the tag/element name for the
	 *		<code>EppCommandRenewDomain</code> object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );

	/**
	 * Converts an XML element into an <code>EppCommandRenewDomain</code>
	 * object. The caller of this method must make sure that the root node
	 * is of an EPP Command Renew entity for EPP domain object
	 *
	 * @param root root node for an <code>EppCommandRenewDomain</code>
	 *             object in XML format
	 *
	 * @return an <code>EppCommandRenewDomain</code> object, or null
	 *         if the node is invalid
	 */
	static EppCommandRenewDomain * fromXML( const DOMNode& root );
};

#endif     /* EPPCOMMANDRENEWDOMAIN_HPP */  /* } */
