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

#if ! defined(EPPCOMMANDINFOSVCSUB_HPP)    /* { */
#define       EPPCOMMANDINFOSVCSUB_HPP        1

#include "EppCommandInfo.hpp"
#include "EppAuthInfo.hpp"

/**
 * This <code>EppCommandInfo</code> class implements EPP Command Info
 * entity for EPP Svcsub objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $
 */
class EPP_EXPORT EppCommandInfoSvcsub : public EppCommandInfo
{
private:
	DOMString	  id;
	DOMString	  userid;
	EppAuthInfo	* authInfo;
	DOMString	  service;

public:

	/**
	 * Creates an <code>EppCommandInfoSvcsub</code> object
	 */
	EppCommandInfoSvcsub()
	{
		this->id = NULLXS;
		this->userid = NULLXS;
		this->authInfo = null;
		this->service = NULLXS;
	};

	/**
	 * Creates an <code>EppCommandInfoSvcsub</code> object for
	 * querying a subscription object based on its id
	 */
	EppCommandInfoSvcsub( DOMString id )
	{
		this->id = id;
		this->userid = NULLXS;
		this->authInfo = null;
		this->service = NULLXS;
	};

	/**
	 * Creates an <code>EppCommandInfoSvcsub</code> object for
	 * querying a subscription object based on its id, given a client
	 * transaction id assoicated with the operation
	 */
	EppCommandInfoSvcsub( DOMString id, DOMString xid )
	{
		this->id = id;
		this->userid = NULLXS;
		this->clTRID = xid;
		this->authInfo = null;
		this->service = NULLXS;
	};

	/**
	 * Destructor
	 */
	~EppCommandInfoSvcsub()
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
		return EppEntity::TYPE_EppCommandInfoSvcsub;
	};

	/**
	 * Gets the id of the subscription object to be queried
	 */
	DOMString getId()
	{
		return this->id;
	};

	/**
	 * Sets the id of the subscription object to be queried
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
	 * Gets the authorization info for the query operation
	 */
	EppAuthInfo * getAuthInfo()
	{
		return this->authInfo;
	};

	/**
	 * Sets the authorization info for the query operation
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
	 * Converts the <code>EppCommandInfoSvcsub</code> object into an XML
	 * element
	 *
	 * @param doc the XML <code>DOMElement</code> object
	 * @param tag the tag/element name for the
	 *            <code>EppCommandInfoSvcsub</code> object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );

	/**
	 * Converts an XML element into an <code>EppCommandInfoSvcsub</code>
	 * object. The caller of this method must make sure that the root node
	 * is of an EPP Command Info entity for EPP subscription object.
	 *
	 * @param root root node for an <code>EppCommandInfoSvcsub</code>
	 *             object in XML format
	 *
	 * @return an <code>EppCommandInfoSvcsub</code> object, or null
	 *         if the node is invalid
	 */
	static EppCommandInfoSvcsub * fromXML( const DOMNode& root );
};

#endif     /* EPPCOMMANDINFOSVCSUB_HPP */  /* } */
