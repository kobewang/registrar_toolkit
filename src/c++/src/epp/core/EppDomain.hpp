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

#if ! defined(EPPDOMAIN_HPP)    /* { */
#define       EPPDOMAIN_HPP        1

#include "EppObject.hpp"
#include "EppPeriod.hpp"
#include "EppContactType.hpp"
#include "EppHostAttribute.hpp"
#include "EppCommandDeleteDomain.hpp"
#include "EppCommandInfoDomain.hpp"
#include "EppCommandCheckDomain.hpp"
#include "EppCommandTransferDomain.hpp"
#include "EppCommandUpdateDomain.hpp"
#include "EppCommandRenewDomain.hpp"

#define MAX_NUM_OF_HOSTS	13
#define MAX_NUM_OF_NAMESERVERS	13
#define	MAX_NUM_OF_CONTACTS	5

/**
 * This <code>EppDomain</code> class implements EPP Domain objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:37 $
 */
class EPP_EXPORT EppDomain : public EppObject
{
private:
	DOMString                           name;
	OldValueVectorOf<DOMString>       * host;
	OldValueVectorOf<DOMString>       * ns;
	OldValueVectorOf<EppHostAttribute *> * nsHostAttr;
	OldValueVectorOf<EppContactType>  * contact;
	EppPeriod                         * period;
	DOMString                           registrant;

	/**
	 * Frees the host attribute list
	 */
	void freeHostAttribute()
	{
		if( this->nsHostAttr == null )
		{
			return;
		}
		for( unsigned int i = 0; i < this->nsHostAttr->size(); i++ )
		{
			EppHostAttribute * h = this->nsHostAttr->elementAt(i);
			if( h != null )
			{
				delete h;
			}
		}
		delete this->nsHostAttr;
		this->nsHostAttr = null;
	};

public:
	/**
	 * Contact type for administration contact ("admin");
	 */
	static const char * CONTACT_TYPE_ADMIN;
	/**
	 * Contact type for billing contact ("billing");
	 */
	static const char * CONTACT_TYPE_BILLING;
	/**
	 * Contact type for technical contact ("tech")
	 */
	static const char * CONTACT_TYPE_TECH;

	/**
	 * Domain status - clientDeleteProhibited
	 */
	static const char * STATUS_CLIENT_DELETE_PROHIBITED;
	/**
	 * Domain status - clientHold
	 */
	static const char * STATUS_CLIENT_HOLD;
	/**
	 * Domain status - clientRenewProhibited
	 */
	static const char * STATUS_CLIENT_RENEW_PROHIBITED;
	/**
	 * Domain status - clientTransferProhibited
	 */
	static const char * STATUS_CLIENT_TRANSFER_PROHIBITED;
	/**
	 * Domain status - clientUpdateProhibited
	 */
	static const char * STATUS_CLIENT_UPDATE_PROHIBITED;
	/**
	 * Domain status - inactive
	 */
	static const char * STATUS_INACTIVE;
	/**
	 * Domain status - ok
	 */
	static const char * STATUS_OK;
	/**
	 * Domain status - pendingCreate (EPP-1.0)
	 */
	static const char * STATUS_PENDING_CREATE;
	/**
	 * Domain status - pendingDelete
	 */
	static const char * STATUS_PENDING_DELETE;
	/**
	 * Domain status - pendingTransfer
	 */
	static const char * STATUS_PENDING_TRANSFER;
	/**
	 * Domain status - pendingUpdate (EPP-1.0)
	 */
	static const char * STATUS_PENDING_UPDATE;
	/**
	 * Domain status - pendingVerification (EPP-04)
	 */
	static const char * STATUS_PENDING_VERIFICATION;
	/**
	 * Domain status - serverDeleteProhibited
	 */
	static const char * STATUS_SERVER_DELETE_PROHIBITED;
	/**
	 * Domain status - serverHold
	 */
	static const char * STATUS_SERVER_HOLD;
	/**
	 * Domain status - serverRenewProhibited
	 */
	static const char * STATUS_SERVER_RENEW_PROHIBITED;
	/**
	 * Domain status - serverTransferProhibited
	 */
	static const char * STATUS_SERVER_TRANSFER_PROHIBITED;
	/**
	 * Domain status - serverUpdateProhibited
	 */
	static const char * STATUS_SERVER_UPDATE_PROHIBITED;

	/**
	 * Creates an <code>EppDomain</code> object
	 */
	EppDomain()
	{
		this->host    = new OldValueVectorOf<DOMString>(MAX_NUM_OF_HOSTS);
		this->ns      = new OldValueVectorOf<DOMString>(MAX_NUM_OF_NAMESERVERS);
		this->contact = new OldValueVectorOf<EppContactType>(MAX_NUM_OF_CONTACTS);
		this->name    = NULLXS;
		this->period  = null;
		this->registrant = NULLXS;
		this->nsHostAttr = null;
	};

	/**
	 * Creates an <code>EppDomain</code> object with a domain name
	 */
	EppDomain( DOMString name )
	{
		this->host    = new OldValueVectorOf<DOMString>(MAX_NUM_OF_HOSTS);
		this->ns      = new OldValueVectorOf<DOMString>(MAX_NUM_OF_NAMESERVERS);
		this->contact = new OldValueVectorOf<EppContactType>(MAX_NUM_OF_CONTACTS);
		this->name    = name;
		this->period  = null;
		this->registrant = NULLXS;
		this->nsHostAttr = null;
	};

	/**
	 * Destructor
	 */
	~EppDomain()
	{
		EppObject::freeCommon();
		if( this->host != null )
		{
			delete this->host;
			this->host = null;
		}
		if( this->ns != null )
		{
			delete this->ns;
			this->ns = null;
		}
		if( this->nsHostAttr != null )
		{
			freeHostAttribute();
		}
		if( this->contact != null )
		{
			delete this->contact;
			this->contact = null;
		}
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
		return EppEntity::TYPE_EppDomain;
	};

	/**
	 * Gets the domain name
	 */
	DOMString getName()
	{
		return this->name;
	};

	/**
	 * Sets the domain name
	 */
	void setName( DOMString name )
	{
		this->name = name;
	};

	/**
	 * Gets a list of host names associated with the domain
	 */
	OldValueVectorOf<DOMString> * getHost()
	{
		return this->host;
	};

	/**
	 * Adds a host name associated with the domain
	 *
	 * @note this method should be only called by the EPP server, and
	 *       an EPP client should not perform this operation.
	 */
	void addHost( DOMString host )
	{
		this->host->addElement(host);
	};

	/**
	 * Gets a list of name servers associated with the domain
	 */
	OldValueVectorOf<DOMString> * getNameServer()
	{
		return this->ns;
	};

	/**
	 * Add a name server associated with the domain
	 */
	void addNameServer( DOMString nameServer )
	{
		if( this->nsHostAttr != null )
		{
			freeHostAttribute();
		}
		if( this->ns == null )
		{
			this->ns = new OldValueVectorOf<DOMString>(MAX_NUM_OF_NAMESERVERS);
		}
		this->ns->addElement(nameServer);
	};

	/**
	 * Gets a list of name servers associated with the domain,
	 * specified via host attributes
	 */
	OldValueVectorOf<EppHostAttribute *> * getNameServerHostAttribute()
	{
		return this->nsHostAttr;
	};

	/**
	 * Add a name server associated with the domain,
	 * specified via a host attribute
	 */
	void addNameServer( EppHostAttribute * nsHostAddr )
	{
		if( this->ns != null )
		{
			delete this->ns;
			this->ns = null;
		}
		if( this->nsHostAttr == null )
		{
			this->nsHostAttr = new OldValueVectorOf<EppHostAttribute *>(MAX_NUM_OF_NAMESERVERS);
		}
		this->nsHostAttr->addElement(nsHostAddr);
	};

	/**
	 * Gets a list of contacts associated with the domain
	 */
	OldValueVectorOf<EppContactType> * getContact()
	{
		return this->contact;
	};

	/**
	 * Adds a contact for the domain
	 */
	void addContact( EppContactType contact )
	{
		this->contact->addElement(contact);
	};

	/**
	 * Adds a contact for the domain, given a contact id and its type
	 */
	void addContact( DOMString id, DOMString type )
	{
		this->contact->addElement(EppContactType(id, type));
	};

	/**
	 * Gets registration period for the domain name
	 */
	EppPeriod * getPeriod()
	{
		return this->period;
	};

	/**
	 * Sets registration period for the domain name
	 */
	void setPeriod( EppPeriod period )
	{
		if( this->period == null )
		{
			this->period = new EppPeriod();
		}
		*(this->period) = period;
	};

	/**
	 * Gets the contact id of the registrant which owns the domain
	 */
	DOMString getRegistrant()
	{
		return this->registrant;
	};

	/**
	 * Sets the contact id of the registrant which owns the domain
	 */
	void setRegistrant( DOMString registrant )
	{
		this->registrant = registrant;
	};

	/**
	 * Converts the <code>EppDomain</code> object into an XML element
	 *
	 * @param doc the XML <code>DOMElement</code> object
	 * @param tag the tag/element name for the <code>EppDomain</code> object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );

	/**
	 * Converts an XML element into an <code>EppDomain</code> object.
	 * The caller of this method must make sure that the root node is of
	 * the EPP Domain type.
	 *
	 * @param root root node for an <code>EppDomain</code> object in
	 *             XML format
	 *
	 * @return an <code>EppDomain</code> object, or null if the node
	 *         is invalid
	 */
	static EppDomain * fromXML( const DOMNode& root );

	DOMString toString()
	{
		return EppEntity::toString(DOMString("domain"));
	};

	/**
	 * Creates an <code>EppCommandDeleteDomain</code> object for
	 * deleting an EPP Domain object from the registry.
	 *
	 * @param name the name of the domain object to be deleted
	 * @param xid  the client transaction id associated with the operation
	 */
	static EppCommandDeleteDomain remove( DOMString name, DOMString xid )
	{
		return EppCommandDeleteDomain(name, xid);
	};

	/**
	 * Creates an <code>EppCommandInfoDomain</code> object for
	 * querying the details of an EPP Domain object
	 *
	 * @param name the name of the domain object to be queried
	 * @param xid  the client transaction id associated with the operation
	 */
	static EppCommandInfoDomain info( DOMString name, DOMString xid )
	{
		return EppCommandInfoDomain(name, xid);
	};

	/**
	 * Creates an <code>EppCommandCheckDomain</code> object for
	 * checking the existance of EPP Domain objects in the registry.
	 * Names of EPP Domain objects can be added via the
	 * <code>add</code> or <code>addName</code> methods.
	 *
	 * @param xid  the client transaction id associated with the operation
	 */
	static EppCommandCheckDomain check( DOMString xid )
	{
		return EppCommandCheckDomain(xid);
	};

	/**
	 * Creates an <code>EppCommandTransferDomain</code> object for
	 * transfering an EPP Domain object in the registry. The operation
	 * type, registration period and authorization information associated
	 * with the operation should be specified via <code>setPeriod</code>,
	 * <code>setOperation</code> and <code>setAuthInfo</code> method.
	 *
	 * @param name   the name of the domain object to be transferred
	 * @param period the extended registration period of the domain object,
	 *               or null if there is no change in the expiration
	 *               timestamp of the domain object after the transfer
	 *               operation
	 * @param xid    the client transaction id associated with the operation
	 */
	static EppCommandTransferDomain transfer( DOMString name, EppPeriod& period, DOMString xid )
	{
		return EppCommandTransferDomain(name, period, xid);
	};

	/**
	 * Creates an <code>EppCommandUpdateDomain</code> object for
	 * updating an EPP Domain object in the registry. The actual update
	 * information should be specified via the various methods defined
	 * for the <code>EppCommandUpdateDomain</code> object.
	 *
	 * @param name the name of the domain object to be updated
	 * @param xid  the client transaction id associated with the operation
	 */
	static EppCommandUpdateDomain update( DOMString name, DOMString xid )
	{
		return EppCommandUpdateDomain(name, xid);
	};

	/**
	 * Creates an <code>EppCommandRenewDomain</code> object for
	 * renewing the registration of an EPP Domain object in the registry.
	 *
	 * @param name       the name of the domain object to be renewed
	 * @param curExpDate the current expiration date of the domain object
	 * @param period     the new registration period of the domain object,
	 *                   or null if using the value specified by the
	 *                   registry
	 * @param xid        the client transaction id associated with the
	 *                   operation
	 */
	static EppCommandRenewDomain renew( DOMString name, time_t curExpDate, EppPeriod& period, DOMString xid )
	{
		return EppCommandRenewDomain(name, curExpDate, period, xid);
	};
};

#endif     /* EPPDOMAIN_HPP */  /* } */
