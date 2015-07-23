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

#if ! defined(EPPCOMMANDTRANSFERDOMAIN_HPP)    /* { */
#define       EPPCOMMANDTRANSFERDOMAIN_HPP        1

#include "EppCommandTransfer.hpp"
#include "EppPeriod.hpp"

/**
 * This <code>EppCommandTransferDomain</code> class implements EPP Command
 * Transfer entity for EPP Domain objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:37 $
 */
class EPP_EXPORT EppCommandTransferDomain : public EppCommandTransfer
{
private:
	DOMString   name;
	EppPeriod * period;

public:

	/**
	 * Creates an <code>EppCommandTransferDomain</code> object
	 */
	EppCommandTransferDomain()
	{
		this->name = NULLXS;
		this->period = null;
	};

	/**
	 * Creates an <code>EppCommandTransferDomain</code> object for
	 * transfering a domain object based on its name
	 */
	EppCommandTransferDomain( DOMString name )
	{
		this->name = name;
		this->period = null;
	};

	/**
	 * Creates an <code>EppCommandTransferDomain</code> object for
	 * transfering a domain object based on its name, given a client
	 * transaction id associated with operation
	 */
	EppCommandTransferDomain( DOMString name, DOMString xid )
	{
		this->name = name;
		this->period = null;
		this->clTRID = xid;
	};

	/**
	 * Creates an <code>EppCommandTransferDomain</code> object for
	 * transfering a domain object based on its name and with an
	 * extended expiration period
	 */
	EppCommandTransferDomain( DOMString name, EppPeriod& period )
	{
		this->name = name;
		this->period = null;
		this->setPeriod(period);
	};

	/**
	 * Creates an <code>EppCommandTransferDomain</code> object for
	 * transfering a domain object based on its name and with an
	 * extended expiration period and a client transaction id
	 * associated with operation
	 */
	EppCommandTransferDomain( DOMString name, EppPeriod& period, DOMString xid )
	{
		this->name = name;
		this->period = null;
		this->clTRID = xid;
		this->setPeriod(period);
	};

	/**
	 * Destructor
	 */
	~EppCommandTransferDomain()
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
		return EppEntity::TYPE_EppCommandTransferDomain;
	};

	/**
	 * Gets the name of the domain object to be transferred
	 */
	DOMString getName()
	{
		return this->name;
	};

	/**
	 * Sets the name of the domain object to be transferred
	 */
	void setName( DOMString name )
	{
		this->name = name;
	};

	/**
	 * Gets the extended registration period of the domain object to be transferred
	 */
	EppPeriod * getPeriod()
	{
		return this->period;
	};

	/**
	 * Sets the extended registration period of the domain object to be transferred
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
	 * Converts the <code>EppCommandTransferDomain</code> object into
	 * an XML element for an <code>EppPollable</code> object
	 *
	 * @param doc the XML <code>DOMElement</code> object
	 * @param tag the tag/element name for the
	 *            <code>EppCommandTransferDomain</code> object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXMLPoll( DOMDocument& doc, const DOMString& tag );

	/**
	 * Converts the <code>EppCommandTransferDomain</code> object into
	 * an XML element
	 *
	 * @param doc the XML <code>DOMElement</code> object
	 * @param tag the tag/element name for the
	 *            <code>EppCommandTransferDomain</code> object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );

	/**
	 * Converts an XML element into an
	 * <code>EppCommandTransferDomain</code> object. The caller of this
	 * method must make sure that the root node is of an EPP Command
	 * Transfer entity for EPP Domain object.
	 *
	 * @param root root node for an <code>EppCommandTransferDomain</code>
	 *             object in XML format
	 *
	 * @return an <code>EppCommandTransferDomain</code> object, or null
	 *         if the node is invalid
	 */
	static EppCommandTransferDomain * fromXML( const DOMNode& root );
};

#endif     /* EPPCOMMANDTRANSFERDOMAIN_HPP */  /* } */
