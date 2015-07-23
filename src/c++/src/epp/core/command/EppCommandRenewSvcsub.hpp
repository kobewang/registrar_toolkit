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

#if ! defined(EPPCOMMANDRENEWSVCSUB_HPP)    /* { */
#define       EPPCOMMANDRENEWSVCSUB_HPP        1

#include "EppCommandRenew.hpp"
#include "EppAuthInfo.hpp"

/**
 * This <code>EppCommandRenewSvcsub</code> class implements EPP Command Renew
 * entity for EPP Svcsub objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $
 */
class EPP_EXPORT EppCommandRenewSvcsub : public EppCommandRenew
{
private:
	DOMString	  id;
	DOMString	  userid;
	EppAuthInfo	* authInfo;
	DOMString	  service;

public:

	/**
	 * Creates an <code>EppCommandRenewSvcsub</code> object
	 */
	EppCommandRenewSvcsub()
	{
		this->id = NULLXS;
		this->userid = NULLXS;
		this->authInfo = null;
		this->service = NULLXS;
	};

	/**
	 * Creates an <code>EppCommandRenewSvcsub</code> object with a
	 * default expiration period, specified by the registry
	 */
	EppCommandRenewSvcsub( DOMString id, time_t curExpDate )
	{
		this->id = id;
		this->userid = NULLXS;
		this->curExpDate = curExpDate;
		this->authInfo = null;
		this->service = NULLXS;
	};

	/**
	 * Creates an <code>EppCommandRenewSvcsub</code> object with a
	 * client transaction id associated with the operation. The current
	 * date of expiration would be the current date.
	 */
	EppCommandRenewSvcsub( DOMString id, DOMString xid )
	{
		this->id = id;
		this->userid = NULLXS;
		this->curExpDate = time(0);
		this->clTRID = xid;
		this->authInfo = null;
		this->service = NULLXS;
	};

	/**
	 * Creates an <code>EppCommandRenewSvcsub</code> object with a
	 * default expiration period, specified by the registry, and a
	 * client transaction id associated with the operation
	 */
	EppCommandRenewSvcsub( DOMString id, time_t curExpDate, DOMString xid )
	{
		this->id = id;
		this->userid = NULLXS;
		this->curExpDate = curExpDate;
		this->clTRID = xid;
		this->authInfo = null;
		this->service = NULLXS;
	};

	/**
	 * Creates an <code>EppCommandRenewSvcsub</code> object with a
	 * specified expiration period, and a client transaction id associated
	 * with the operation
	 */
	EppCommandRenewSvcsub( DOMString id, time_t curExpDate, EppPeriod& period, DOMString xid )
	{
		this->id = id;
		this->userid = NULLXS;
		this->curExpDate = curExpDate;
		this->clTRID = xid;
		this->setPeriod(period);
		this->authInfo = null;
		this->service = NULLXS;
	}

	/**
	 * Destructor
	 */
	~EppCommandRenewSvcsub()
	{
		if( this->authInfo != null )
		{
			delete this->authInfo;
			this->authInfo = null;
		}
	};

	/**
	 * Returns the run-time type of an EppEntity object
	 */
	virtual int getEntityType()
	{
		return EppEntity::TYPE_EppCommandRenewSvcsub;
	};

	/**
	 * Gets the id of the subscription object to be renewed
	 */
	DOMString getId()
	{
		return this->id;
	};

	/**
	 * Sets the id of the subscription object to be renewed
	 */
	void setId( DOMString id )
	{
		this->id = id;
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
	 * Gets the authorization info for the renew operation
	 */
	EppAuthInfo * getAuthInfo()
	{
		return this->authInfo;
	};

	/**
	 * Sets the authorization info for the renew operation
	 */
	void setAuthInfo( EppAuthInfo& authInfo )
	{
		if( this->authInfo == null )
		{
			this->authInfo = new EppAuthInfo();
		}
		*(this->authInfo) = authInfo;
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
	 * Converts the <code>EppCommandRenewSvcsub</code> object into
	 *		an XML element
	 *
	 * @param doc the XML <code>DOMElement</code> object
	 * @param tag the tag/element name for the
	 *		<code>EppCommandRenewSvcsub</code> object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );

	/**
	 * Converts an XML element into an <code>EppCommandRenewSvcsub</code>
	 * object. The caller of this method must make sure that the root node
	 * is of an EPP Command Renew entity for EPP subscription object
	 *
	 * @param root root node for an <code>EppCommandRenewSvcsub</code>
	 *             object in XML format
	 *
	 * @return an <code>EppCommandRenewSvcsub</code> object, or null
	 *         if the node is invalid
	 */
	static EppCommandRenewSvcsub * fromXML( const DOMNode& root );
};

#endif     /* EPPCOMMANDRENEWSVCSUB_HPP */  /* } */
