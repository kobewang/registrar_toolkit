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

#if ! defined(EPPHOST_HPP)    /* { */
#define       EPPHOST_HPP        1

#include "EppObject.hpp"
#include "EppIpAddress.hpp"
#include "EppCommandDeleteHost.hpp"
#include "EppCommandInfoHost.hpp"
#include "EppCommandCheckHost.hpp"
#include "EppCommandUpdateHost.hpp"

#define	MAX_NUM_OF_IPADDRESSES	10

/**
 * This <code>EppHost</code> class implements EPP Host objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:37 $
 */
class EPP_EXPORT EppHost : public EppObject
{
private:
	DOMString                     name;
	OldValueVectorOf<EppIpAddress> * addr;

public:
	/**
	 * Host status - clientDeleteProhibited
	 */
	static const char * STATUS_CLIENT_DELETE_PROHIBITED;
	/**
	 * Host status - clientUpdateProhibited
	 */
	static const char * STATUS_CLIENT_UPDATE_PROHIBITED;
	/**
	 * Host status - linked
	 */
	static const char * STATUS_LINKED;
	/**
	 * Host status - ok
	 */
	static const char * STATUS_OK;
	/**
	 * Host status - pendingCreate (EPP-1.0)
	 */
	static const char * STATUS_PENDING_CREATE;
	/**
	 * Host status - pendingDelete
	 */
	static const char * STATUS_PENDING_DELETE;
	/**
	 * Host status - pendingTransfer
	 */
	static const char * STATUS_PENDING_TRANSFER;
	/**
	 * Host status - pendingUpdate (EPP-1.0)
	 */
	static const char * STATUS_PENDING_UPDATE;
	/**
	 * Host status - serverDeleteProhibited
	 */
	static const char * STATUS_SERVER_DELETE_PROHIBITED;
	/**
	 * Host status - serverUpdateProhibited
	 */
	static const char * STATUS_SERVER_UPDATE_PROHIBITED;

	/**
	 * Creates an <code>EppHost</code> object
	 */
	EppHost()
	{
		this->name = NULLXS;
		this->addr = new OldValueVectorOf<EppIpAddress>(MAX_NUM_OF_IPADDRESSES);
	};

	/**
	 * Creates an <code>EppHost</code> object with a domain name
	 */
	EppHost( DOMString name )
	{
		this->name = name;
		this->addr = new OldValueVectorOf<EppIpAddress>(MAX_NUM_OF_IPADDRESSES);
	};

	/**
	 * Destructor
	 */
	~EppHost()
	{
		EppObject::freeCommon();
		if( this->addr != null )
		{
			delete this->addr;
			this->addr = null;
		}
	};

	/**
	 * Returns the run-time type of an EppEntity object
	 */
	virtual int getEntityType()
	{
		return EppEntity::TYPE_EppHost;
	};

	/**
	 * Gets the name of the host
	 */
	DOMString getName()
	{
		return this->name;
	};

	/**
	 * Sets the name of the host
	 */
	void setName( DOMString name )
	{
		this->name = name;
	};

	/**
	 * Gets the list of IP addresses associated with the host
	 */
	OldValueVectorOf<EppIpAddress> * getAddress()
	{
		return this->addr;
	};

	/**
	 * Adds an IP address into the list of the IP addresses associated with
	 * the host
	 */
	void addAddress( EppIpAddress address )
	{
		this->addr->addElement(address);
	};

	/**
	 * Converts the <code>EppHost</code> object into an XML element
	 *
	 * @param doc the XML <code>DOMElement</code> object
	 * @param tag the tag/element name for the <code>EppHost</code> object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );

	/**
	 * Converts an XML element into an <code>EppHost</code> object.
	 * The caller of this method must make sure that the root node is of
	 * the EPP Host type.
	 *
	 * @param root root node for an <code>EppHost</code> object in
	 *             XML format
	 *
	 * @return an <code>EppHost</code> object, or null if the node
	 *         is invalid
	 */
	static EppHost * fromXML( const DOMNode& root );

	DOMString toString()
	{
		return EppEntity::toString(DOMString("host"));
	};

	/**
	 * Creates an <code>EppCommandDeleteHost</code> object for
	 * deleting an EPP Host object from the registry.
	 *
	 * @param name the name of the host object to be deleted
	 * @param xid  the client transaction id associated with the operation
	 */
	static EppCommandDeleteHost remove( DOMString name, DOMString xid )
	{
		return EppCommandDeleteHost(name, xid);
	};

	/**
	 * Creates an <code>EppCommandInfoHost</code> object for
	 * querying the details of an EPP Host object
	 *
	 * @param name the name of the host object to be queried
	 * @param xid  the client transaction id associated with the operation
	 */
	static EppCommandInfoHost info( DOMString name, DOMString xid )
	{
		return EppCommandInfoHost(name, xid);
	};

	/**
	 * Creates an <code>EppCommandCheckHost</code> object for
	 * checking the existance of EPP Host objects in the registry.
	 * Names of EPP Host objects can be added via the
	 * <code>add</code> or <code>addName</code> methods.
	 *
	 * @param xid  the client transaction id associated with the operation
	 */
	static EppCommandCheckHost check( DOMString xid )
	{
		return EppCommandCheckHost(xid);
	};

	/**
	 * Creates an <code>EppCommandUpdateHost</code> object for
	 * updating an EPP Host object in the registry. The actual update
	 * information should be specified via the various methods defined
	 * for the <code>EppCommandUpdateHost</code> object.
	 *
	 * @param name the name of the host object to be updated
	 * @param xid  the client transaction id associated with the operation
	 */
	static EppCommandUpdateHost update( DOMString name, DOMString xid )
	{
		return EppCommandUpdateHost(name, xid);
	};
};

#endif     /* EPPHOST_HPP */  /* } */
