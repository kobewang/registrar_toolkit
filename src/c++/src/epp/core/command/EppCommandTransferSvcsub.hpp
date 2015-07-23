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

#if ! defined(EPPCOMMANDTRANSFERSVCSUB_HPP)    /* { */
#define       EPPCOMMANDTRANSFERSVCSUB_HPP        1

#include "EppCommandTransfer.hpp"
#include "EppPeriod.hpp"

/**
 * This <code>EppCommandTransferSvcsub</code> class implements EPP Command
 * Transfer entity for EPP Svcsub objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $
 */
class EPP_EXPORT EppCommandTransferSvcsub : public EppCommandTransfer
{
private:
	DOMString id;
	DOMString userid;
	EppPeriod * period;
	DOMString service;

public:

	/**
	 * Creates an <code>EppCommandTransferSvcsub</code> object
	 */
	EppCommandTransferSvcsub()
	{
		this->id = NULLXS;
		this->userid = NULLXS;
		this->period = null;
		this->service = NULLXS;
	};

	/**
	 * Creates an <code>EppCommandTransferSvcsub</code> object for
	 * transferring a subscription object based on its id
	 */
	EppCommandTransferSvcsub( DOMString id )
	{
		this->id = id;
		this->userid = NULLXS;
		this->period = null;
		this->service = NULLXS;
	};

	/**
	 * Creates an <code>EppCommandTransferSvcsub</code> object for
	 * transfering a subscription object based on its id, given a client
	 * transaction id associated with operation
	 */
	EppCommandTransferSvcsub( DOMString id, DOMString xid )
	{
		this->id = id;
		this->userid = NULLXS;
		this->period = null;
		this->clTRID = xid;
		this->service = NULLXS;
	};

	/**
	 * Creates an <code>EppCommandTransferSvcsub</code> object for
	 * transfering a subscription object based on its id and with an
	 * extended expiration period
	 */
	EppCommandTransferSvcsub( DOMString id, EppPeriod& period )
	{
		this->id = id;
		this->userid = NULLXS;
		this->period = null;
		this->setPeriod(period);
		this->service = NULLXS;
	};

	/**
	 * Creates an <code>EppCommandTransferSvcsub</code> object for
	 * transfering a subscription object based on its id and with an
	 * extended expiration period and a client transaction id
	 * associated with operation
	 */
	EppCommandTransferSvcsub( DOMString id, EppPeriod& period, DOMString xid )
	{
		this->id = id;
		this->userid = NULLXS;
		this->period = null;
		this->clTRID = xid;
		this->setPeriod(period);
		this->service = NULLXS;
	};

	/**
	 * Destructor
	 */
	~EppCommandTransferSvcsub()
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
		return EppEntity::TYPE_EppCommandTransferSvcsub;
	};

	/**
	 * Gets the id of the subscription object to be transferred
	 */
	DOMString getId()
	{
		return this->id;
	};

	/**
	 * Sets the id of the subscription object to be transferred
	 */
	void setId( DOMString id )
	{
		this->id = id;
	};

	/**
	 * Gets the registration period of the domain object to be transferred
	 */
	EppPeriod * getPeriod()
	{
		return this->period;
	};

	/**
	 * Sets the registration period of the domain object to be transferred
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
	 * Gets the user id that can be used for authorization purpose
	 */
	DOMString getUserId()
	{
		return this->userid;
	};

	/**
	 * Sets the user id that can be used for authorization purpose
	 */
	void setUserId( DOMString userid )
	{
		this->userid = userid;
	};

	/**
	 * Gets the service name
	 */
	DOMString getService()
	{
		return this->service;
	};

	/**
	 * Sets the service name
	 */
	void setService( DOMString service )
	{
		this->service = service;
	};

	/**
	 * Converts the <code>EppCommandTransferSvcsub</code> object into
	 * an XML element for an <code>EppPollable</code> object
	 *
	 * @param doc the XML <code>DOMElement</code> object
	 * @param tag the tag/element name for the
	 *            <code>EppCommandTransferSvcsub</code> object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXMLPoll( DOMDocument& doc, const DOMString& tag );

	/**
	 * Converts the <code>EppCommandTransferSvcsub</code> object into
	 * an XML element
	 *
	 * @param doc the XML <code>DOMElement</code> object
	 * @param tag the tag/element name for the
	 *            <code>EppCommandTransferSvcsub</code> object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );

	/**
	 * Converts an XML element into an
	 * <code>EppCommandTransferSvcsub</code> object. The caller of this
	 * method must make sure that the root node is of an EPP Command
	 * Transfer entity for EPP Svcsub object.
	 *
	 * @param root root node for an <code>EppCommandTransferSvcsub</code>
	 *             object in XML format
	 *
	 * @return an <code>EppCommandTransferSvcsub</code> object, or null
	 *         if the node is invalid
	 */
	static EppCommandTransferSvcsub * fromXML( const DOMNode& root );
};

#endif     /* EPPCOMMANDTRANSFERSVCSUB_HPP */  /* } */
