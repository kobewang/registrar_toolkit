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

#if ! defined(EPPCOMMANDDELETESVCSUB_HPP)    /* { */
#define       EPPCOMMANDDELETESVCSUB_HPP        1

#include "EppCommandDelete.hpp"
#include "EppAuthInfo.hpp"

/**
 * This <code>EppCommandDelete</code> class implements EPP Command Delete
 * entity for EPP Svcsub objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $
 */
class EPP_EXPORT EppCommandDeleteSvcsub : public EppCommandDelete
{
private:
	DOMString	  id;
	DOMString	  userid;
	EppAuthInfo	* authInfo;
	DOMString	  service;

public:

	/**
	 * Creates an <code>EppCommandDeleteSvcsub</code> object for
	 */
	EppCommandDeleteSvcsub()
	{
		this->id = NULLXS;
		this->userid = NULLXS;
		this->clTRID = NULLXS;
		this->authInfo = null;
		this->service = NULLXS;
	};

	/**
	 * Creates an <code>EppCommandDeleteSvcsub</code> object for
	 * deleting a subscription object based on its id
	 */
	EppCommandDeleteSvcsub( DOMString id )
	{
		this->id = id;
		this->userid = NULLXS;
		this->clTRID = NULLXS;
		this->authInfo = null;
		this->service = NULLXS;
	};

	/**
	 * Creates an <code>EppCommandDeleteSvcsub</code> object for
	 * deleting a subscription object based on its id, given a client
	 * transaction id associated with the operation
	 */
	EppCommandDeleteSvcsub( DOMString id, DOMString xid )
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
	~EppCommandDeleteSvcsub()
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
		return EppEntity::TYPE_EppCommandDeleteSvcsub;
	};

	/**
	 * Gets the id of the subscription object to be deleted
	 */
	DOMString getId()
	{
		return this->id;
	};

	/**
	 * Sets the id of the subscription object to be deleted
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
	 * Gets the authorization info for the delete operation
	 */
	EppAuthInfo * getAuthInfo()
	{
		return this->authInfo;
	};

	/**
	 * Sets the authorization info for the delete operation
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
	 * Converts the <code>EppCommandDeleteSvcsub</code> object into an XML
	 * element
	 *
	 * @param doc the XML <code>DOMElement</code> object
	 * @param tag the tag/element name for the
	 *            <code>EppCommandDeleteSvcsub</code> object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );

	/**
	 * Converts an XML element into an <code>EppCommandDeleteSvcsub</code>
	 * object. The caller of this method must make sure that the root node
	 * is of an EPP Command Delete entity for EPP subscription object.
	 *
	 * @param root root node for an <code>EppCommandDeleteSvcsub</code>
	 *             object in XML format
	 *
	 * @return an <code>EppCommandDeleteSvcsub</code> object, or null
	 *         if the node is invalid
	 */
	static EppCommandDeleteSvcsub * fromXML( const DOMNode& root );
};

#endif     /* EPPCOMMANDDELETESVCSUB_HPP */  /* } */
