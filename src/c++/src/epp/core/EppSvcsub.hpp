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

#if ! defined(EPPSVCSUB_HPP)    /* { */
#define       EPPSVCSUB_HPP        1

#include "EppObject.hpp"
#include "EppPeriod.hpp"
#include "EppContactType.hpp"
#include "EppSvcsubParam.hpp"
#include "EppCommandDeleteSvcsub.hpp"
#include "EppCommandInfoSvcsub.hpp"
#include "EppCommandCheckSvcsub.hpp"
#include "EppCommandTransferSvcsub.hpp"
#include "EppCommandUpdateSvcsub.hpp"
#include "EppCommandRenewSvcsub.hpp"

#define	MAX_NUM_OF_SVCCONTACTS	10
#define MAX_NUM_OF_SVCPARAMS	10
#define MAX_NUM_OF_SVCVALUES	10

/**
 * This <code>EppSvcsub</code> class implements EPP Svcsub objects. The EPP
 * command mapping for EPP Svcsub objects can be found at <A
 * HREF="http://epp-ver-04.sourceforge.net/IETF/EPP-04/draft-zhang-epp-svcsub-00.txt">
 * http://epp-ver-04.sourceforge.net/IETF/EPP-04/draft-zhang-epp-svcsub-00.txt</A>.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $
 */
class EPP_EXPORT EppSvcsub : public EppObject
{
private:
	DOMString                       id;
	DOMString			service;
	OldValueVectorOf<EppSvcsubParam> * param;
	OldValueVectorOf<EppContactType> * contact;
	EppPeriod                     * period;
	DOMString                       registrant;

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
	 * Contact type for primary contact ("primary")
	 */
	static const char * CONTACT_TYPE_PRIMARY;
	/**
	 * Contact type for secondary contact ("secondary")
	 */
	static const char * CONTACT_TYPE_SECONDARY;
	/**
	 * Contact type for BIZlock contact ("bizlock")
	 */
	static const char * CONTACT_TYPE_BIZLOCK;

	/**
	 * Svcsub status - clientDeleteProhibited
	 */
	static const char * STATUS_CLIENT_DELETE_PROHIBITED;
	/**
	 * Svcsub status - clientSuspended
	 */
	static const char * STATUS_CLIENT_SUSPENDED;
	/**
	 * Svcsub status - clientRenewProhibited
	 */
	static const char * STATUS_CLIENT_RENEW_PROHIBITED;
	/**
	 * Svcsub status - clientTransferProhibited
	 */
	static const char * STATUS_CLIENT_TRANSFER_PROHIBITED;
	/**
	 * Svcsub status - clientUpdateProhibited
	 */
	static const char * STATUS_CLIENT_UPDATE_PROHIBITED;
	/**
	 * Svcsub status - inactive
	 */
	static const char * STATUS_INACTIVE;
	/**
	 * Svcsub status - ok
	 */
	static const char * STATUS_OK;
	/**
	 * Svcsub status - pendingDelete
	 */
	static const char * STATUS_PENDING_DELETE;
	/**
	 * Svcsub status - pendingTransfer
	 */
	static const char * STATUS_PENDING_TRANSFER;
	/**
	 * Svcsub status - pendingVerification
	 */
	static const char * STATUS_PENDING_VERIFICATION;
	/**
	 * Svcsub status - serverDeleteProhibited
	 */
	static const char * STATUS_SERVER_DELETE_PROHIBITED;
	/**
	 * Svcsub status - serverSuspended
	 */
	static const char * STATUS_SERVER_SUSPENDED;
	/**
	 * Svcsub status - serverRenewProhibited
	 */
	static const char * STATUS_SERVER_RENEW_PROHIBITED;
	/**
	 * Svcsub status - serverTransferProhibited
	 */
	static const char * STATUS_SERVER_TRANSFER_PROHIBITED;
	/**
	 * Svcsub status - serverUpdateProhibited
	 */
	static const char * STATUS_SERVER_UPDATE_PROHIBITED;

	/**
	 * Creates an <code>EppSvcsub</code> object
	 */
	EppSvcsub()
	{
		this->param   = new OldValueVectorOf<EppSvcsubParam>(MAX_NUM_OF_SVCPARAMS);
		this->contact = new OldValueVectorOf<EppContactType>(MAX_NUM_OF_SVCCONTACTS);
		this->id      = NULLXS;
		this->service = NULLXS;
		this->period  = null;
		this->registrant = NULLXS;
	};

	/**
	 * Creates an <code>EppSvcsub</code> object with a service subscription id
	 */
	EppSvcsub( DOMString id )
	{
		this->param   = new OldValueVectorOf<EppSvcsubParam>(MAX_NUM_OF_SVCPARAMS);
		this->contact = new OldValueVectorOf<EppContactType>(MAX_NUM_OF_SVCCONTACTS);
		this->id      = id;
		this->service = NULLXS;
		this->period  = null;
		this->registrant = NULLXS;
	};

	/**
	 * Creates an <code>EppSvcsub</code> object with a service subscription
	 * id and service name
	 */
	EppSvcsub( DOMString id, DOMString service )
	{
		this->param   = new OldValueVectorOf<EppSvcsubParam>(MAX_NUM_OF_SVCPARAMS);
		this->contact = new OldValueVectorOf<EppContactType>(MAX_NUM_OF_SVCCONTACTS);
		this->id      = id;
		this->service = service;
		this->period  = null;
		this->registrant = NULLXS;
	};

	/**
	 * Destructor
	 */
	~EppSvcsub()
	{
		EppObject::freeCommon();
		if( this->param != null )
		{
			delete this->param;
			this->param = null;
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
		return EppEntity::TYPE_EppSvcsub;
	};

	/**
	 * Gets the service subscription id
	 */
	DOMString getId()
	{
		return this->id;
	};

	/**
	 * Sets the service subscription id
	 */
	void setId( DOMString id )
	{
		this->id = id;
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
	 * Gets a list of parameters associated with the service subscription 
	 * object
	 */
	OldValueVectorOf<EppSvcsubParam> * getParam()
	{
		return this->param;
	};

	/**
	 * Extracts a list of the parameter values associated with the service
	 * subscription object based on the parameter name.
	 *
	 * @note the caller must free the returned list if not null
	 */
	OldValueVectorOf<DOMString> * getParamValue( DOMString name )
	{
		return EppSvcsubParam::getParamValue(this->param, name);
	};

	/**
	 * Extracts a list of the contact ids associated with the service
	 * subscription object based on the contact type
	 *
	 * @note the caller must free the returned list if not null
	 */
	OldValueVectorOf<DOMString> * getContactId( DOMString type )
	{
		return EppContactType::getContactId(this->contact, type);
	};

	/**
	 * Add a service parameter to be associated with the subscription
	 */
	void addParam( EppSvcsubParam param )
	{
		this->param->addElement(param);
	};

	/**
	 * Add a service parameter to be associated with the subscription,
	 * given a parameter name and its value
	 */
	void addParam( DOMString name, DOMString value )
	{
		EppSvcsubParam param(name, value);
		this->param->addElement(param);
	};

	/**
	 * Gets a list of contacts associated with the service subscription object
	 */
	OldValueVectorOf<EppContactType> * getContact()
	{
		return this->contact;
	};

	/**
	 * Adds a contact for the service subscription object
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
	 * Gets registration period for the subscription
	 */
	EppPeriod * getPeriod()
	{
		return this->period;
	};

	/**
	 * Sets registration period for the subscription
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
	 * Gets the contact id of the registrant which owns the subscription
	 */
	DOMString getRegistrant()
	{
		return this->registrant;
	};

	/**
	 * Sets the contact id of the registrant which owns the subscription
	 */
	void setRegistrant( DOMString registrant )
	{
		this->registrant = registrant;
	};

	/**
	 * Converts the <code>EppSvcsub</code> object into an XML element
	 *
	 * @param doc the XML <code>DOMElement</code> object
	 * @param tag the tag/element name for the <code>EppSvcsub</code> object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );

	/**
	 * Converts an XML element into an <code>EppSvcsub</code> object.
	 * The caller of this method must make sure that the root node is of
	 * the EPP Svcsub type.
	 *
	 * @param root root node for an <code>EppSvcsub</code> object in
	 *             XML format
	 *
	 * @return an <code>EppSvcsub</code> object, or null if the node
	 *         is invalid
	 */
	static EppSvcsub * fromXML( const DOMNode& root );

	DOMString toString()
	{
		return EppEntity::toString(DOMString("svcsub"));
	};

	/**
	 * Creates an <code>EppCommandDeleteSvcsub</code> object for
	 * deleting an EPP Svcsub object from the registry.
	 *
	 * @param id  the id of the subscription object to be deleted
	 * @param xid the client transaction id associated with the operation
	 */
	static EppCommandDeleteSvcsub remove( DOMString id, DOMString xid )
	{
		return EppCommandDeleteSvcsub(id, xid);
	};

	/**
	 * Creates an <code>EppCommandInfoSvcsub</code> object for
	 * querying the details of an EPP Svcsub object
	 *
	 * @param id  the id of the subscription object to be queried
	 * @param xid the client transaction id associated with the operation
	 */
	static EppCommandInfoSvcsub info( DOMString id, DOMString xid )
	{
		return EppCommandInfoSvcsub(id, xid);
	};

	/**
	 * Creates an <code>EppCommandCheckSvcsub</code> object for
	 * checking the existance of EPP Svcsub objects in the registry.
	 * Names of EPP Svcsub objects can be added via the
	 * <code>add</code> or <code>addName</code> methods.
	 *
	 * @param xid  the client transaction id associated with the operation
	 */
	static EppCommandCheckSvcsub check( DOMString xid )
	{
		return EppCommandCheckSvcsub(xid);
	};

	/**
	 * Creates an <code>EppCommandTransferSvcsub</code> object for
	 * transfering an EPP Svcsub object in the registry. The operation
	 * type, registration period and authorization information associated
	 * with the operation should be specified via <code>setPeriod</code>,
	 * <code>setOperation</code> and <code>setAuthInfo</code> method.
	 *
	 * @param id     the id of the subscription object to be transferred
	 * @param period the extended registration period of the subscription
	 *               object, or null if there is no change in the expiration
	 *               timestamp of the subscription object after the transfer
	 *               operation
	 * @param xid    the client transaction id associated with the operation
	 */
	static EppCommandTransferSvcsub transfer( DOMString id, EppPeriod& period, DOMString xid )
	{
		return EppCommandTransferSvcsub(id, period, xid);
	};

	/**
	 * Creates an <code>EppCommandUpdateSvcsub</code> object for
	 * updating an EPP Svcsub object in the registry. The actual update
	 * information should be specified via the various methods defined
	 * for the <code>EppCommandUpdateSvcsub</code> object.
	 *
	 * @param id   the id of the subscription object to be updated
	 * @param xid  the client transaction id associated with the operation
	 */
	static EppCommandUpdateSvcsub update( DOMString id, DOMString xid )
	{
		return EppCommandUpdateSvcsub(id, xid);
	};

	/**
	 * Creates an <code>EppCommandRenewSvcsub</code> object for
	 * renewing the registration of an EPP Svcsub object in the registry.
	 *
	 * @param id         the id of the subscription object to be renewed
	 * @param curExpDate the current expiration date of the subscription object
	 * @param period     the new registration period of the subscription object,
	 *                   or null if using the value specified by the
	 *                   registry
	 * @param xid        the client transaction id associated with the
	 *                   operation
	 */
	static EppCommandRenewSvcsub renew( DOMString id, time_t curExpDate, EppPeriod& period, DOMString xid )
	{
		return EppCommandRenewSvcsub(id, curExpDate, period, xid);
	};
};

#endif     /* EPPSVCSUB_HPP */  /* } */
