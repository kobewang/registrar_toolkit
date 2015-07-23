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

#if ! defined(EPPCOMMANDUPDATESVCSUB_HPP)    /* { */
#define       EPPCOMMANDUPDATESVCSUB_HPP        1

#include "EppCommandUpdate.hpp"
#include "EppContactType.hpp"
#include "EppSvcsubParam.hpp"
#include "EppAuthInfo.hpp"

#define	MAX_NUM_OF_UPDATE_SVCPARAMS	16
#define	MAX_NUM_OF_UPDATE_SVCCONTACTS	16

/**
 * This <code>EppCommandUpdateSvcsub</code> class implements EPP Command Update
 * entity for EPP Svcsub objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $
 */
class EPP_EXPORT EppCommandUpdateSvcsub : public EppCommandUpdate
{
private:
	DOMString                       id;
	OldValueVectorOf<EppSvcsubParam> * paramAdded;
	OldValueVectorOf<EppSvcsubParam> * paramRemoved;
	OldValueVectorOf<EppContactType> * contactAdded;
	OldValueVectorOf<EppContactType> * contactRemoved;
	DOMString                       newRegistrant;
	EppAuthInfo                   * newAuthInfo;
	DOMString                       userid;
	EppAuthInfo                   * authInfo;
	DOMString			service;

	/**
	 * Converts a list of service parameters into XML
	 *
	 * @param doc  the XML <code>DOMElement</code> object
	 * @param body the XML <code>DOMElement</code> object to which the list
	 *             of parameters is appended
	 * @param list the list of parameters to be converted
	 */
	void paramToXML( DOMDocument& doc, DOMElement& body, OldValueVectorOf<EppSvcsubParam> * list );

	/**
	 * Converts a list of contacts into XML
	 *
	 * @param doc  the XML <code>DOMElement</code> object
	 * @param body the XML <code>DOMElement</code> object to which the list
	 *             of contacts is appended
	 * @param list the list of contacts to be converted
	 */
	void contactToXML( DOMDocument& doc, DOMElement& body, OldValueVectorOf<EppContactType> * list );

	/**
	 * Converts a list of service parameters from XML format
	 *
	 * @param root the XML <code>Node</code> object containing the list
	 *             of parameters
	 * @param addressList the list of parameters to be stored
	 */
	void paramFromXML( const DOMNode& root, OldValueVectorOf<EppSvcsubParam> * paramList );

	/**
	 * Converts a list of contacts from XML format
	 *
	 * @param root the XML <code>Node</code> object containing the list
	 *             of contacts
	 * @param contactList the list of contacts to be stored
	 */
	void contactFromXML( const DOMNode& root, OldValueVectorOf<EppContactType> * contactList );

public:

	/**
	 * Creates an <code>EppCommandUpdateSvcsub</code>
	 */
	EppCommandUpdateSvcsub()
	{
		this->id             = NULLXS;
		this->paramAdded     = new OldValueVectorOf<EppSvcsubParam>(MAX_NUM_OF_UPDATE_SVCPARAMS);
		this->paramRemoved   = new OldValueVectorOf<EppSvcsubParam>(MAX_NUM_OF_UPDATE_SVCPARAMS);
		this->contactAdded   = new OldValueVectorOf<EppContactType>(MAX_NUM_OF_UPDATE_SVCCONTACTS);
		this->contactRemoved = new OldValueVectorOf<EppContactType>(MAX_NUM_OF_UPDATE_SVCCONTACTS);
		this->newRegistrant  = NULLXS;
		this->newAuthInfo    = null;
		this->userid         = NULLXS;
		this->authInfo       = null;
		this->service        = NULLXS;
	};

	/**
	 * Creates an <code>EppCommandUpdateSvcsub</code> given the id of the
	 * svcsub object
	 */
	EppCommandUpdateSvcsub( DOMString id )
	{
		this->id             = id;
		this->paramAdded     = new OldValueVectorOf<EppSvcsubParam>(MAX_NUM_OF_UPDATE_SVCPARAMS);
		this->paramRemoved   = new OldValueVectorOf<EppSvcsubParam>(MAX_NUM_OF_UPDATE_SVCPARAMS);
		this->contactAdded   = new OldValueVectorOf<EppContactType>(MAX_NUM_OF_UPDATE_SVCCONTACTS);
		this->contactRemoved = new OldValueVectorOf<EppContactType>(MAX_NUM_OF_UPDATE_SVCCONTACTS);
		this->newRegistrant  = NULLXS;
		this->newAuthInfo    = null;
		this->userid         = NULLXS;
		this->authInfo       = null;
	};

	/**
	 * Creates an <code>EppCommandUpdateSvcsub</code> given the id of the
	 * svcsub object
	 */
	EppCommandUpdateSvcsub( DOMString id, DOMString xid )
	{
		this->id             = id;
		this->paramAdded     = new OldValueVectorOf<EppSvcsubParam>(MAX_NUM_OF_UPDATE_SVCPARAMS);
		this->paramRemoved   = new OldValueVectorOf<EppSvcsubParam>(MAX_NUM_OF_UPDATE_SVCPARAMS);
		this->contactAdded   = new OldValueVectorOf<EppContactType>(MAX_NUM_OF_UPDATE_SVCCONTACTS);
		this->contactRemoved = new OldValueVectorOf<EppContactType>(MAX_NUM_OF_UPDATE_SVCCONTACTS);
		this->newRegistrant  = NULLXS;
		this->newAuthInfo    = null;
		this->userid         = NULLXS;
		this->authInfo       = null;
		this->clTRID         = xid;
		this->service        = NULLXS;
	};

	/**
	 * Destructor
	 */
	~EppCommandUpdateSvcsub()
	{
		if( this->paramAdded != null )
		{
			delete this->paramAdded;
			this->paramAdded = null;
		}
		if( this->paramRemoved != null )
		{
			delete this->paramRemoved;
			this->paramRemoved = null;
		}
		if( this->contactAdded != null )
		{
			delete this->contactAdded;
			this->contactAdded = null;
		}
		if( this->contactRemoved != null )
		{
			delete this->contactRemoved;
			this->contactRemoved = null;
		}
		if( this->newAuthInfo != null )
		{
			delete this->newAuthInfo;
			this->newAuthInfo = null;
		}
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
		return EppEntity::TYPE_EppCommandUpdateSvcsub;
	};

	/**
	 * Gets the id of the svcsub object to be updated
	 */
	DOMString getId()
	{
		return this->id;
	};

	/**
	 * Sets the id of the svcsub object to be updated
	 */
	void setId( DOMString& id )
	{
		this->id = id;
	};

	/**
	 * Gets the list of new parameters to be added to the svcsub object
	 */
	OldValueVectorOf<EppSvcsubParam> * getParamAdded()
	{
		return this->paramAdded;
	};

	/**
	 * Adds a new parameter to be associated with the svcsub object
	 */
	void addParam( EppSvcsubParam& param )
	{
		this->paramAdded->addElement(param);
	};

	/**
	 * Gets the list of old parameters to be removed from the svcsub object
	 */
	OldValueVectorOf<EppSvcsubParam> * getParamRemoved()
	{
		return this->paramRemoved;
	};

	/*
	 * Removes an old parameter associated with the svcsub object
	 */
	void removeParam( EppSvcsubParam& param )
	{
		this->paramRemoved->addElement(param);
	};

	/**
	 * Gets the list of new contacts to be added to the svcsub object
	 */
	OldValueVectorOf<EppContactType> * getContactAdded()
	{
		return this->contactAdded;
	};

	/**
	 * Adds a new contact to be associated with the svcsub object
	 */
	void addContact( EppContactType& contact )
	{
		this->contactAdded->addElement(contact);
	};

	/**
	 * Gets the list of old contacts to be removed from the svcsub object
	 */
	OldValueVectorOf<EppContactType> * getContactRemoved()
	{
		return this->contactRemoved;
	};

	/*
	 * Removes an old contact associated with the svcsub object
	 */
	void removeContact( EppContactType& contact )
	{
		this->contactRemoved->addElement(contact);
	};

	/**
	 * Gets the new registrant's contact id for the svcsub object,
	 * or null if the registrant of the svcsub object is not to be changed
	 */
	DOMString getNewRegistrant()
	{
		return this->newRegistrant;
	};

	/**
	 * Sets the new registrant's contact id for the svcsub object,
	 * if a new registrant claims the ownership of the svcsub object
	 */
	void setNewRegistrant( DOMString registrant )
	{
		this->newRegistrant = registrant;
	};

	/**
	 * Gets the new authorization information for the svcsub object, or null
	 * if the authorization information of the svcsub object is not to be
	 * changed
	 */
	EppAuthInfo * getNewAuthInfo()
	{
		return this->newAuthInfo;
	};

	/**
	 * Sets the new authorization information for the svcsub object if it
	 * needs to be changed
	 */
	void setNewAuthInfo( EppAuthInfo& authInfo )
	{
		if( this->newAuthInfo == null )
		{
			this->newAuthInfo = new EppAuthInfo();
		}
		*(this->newAuthInfo) = authInfo;
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
	 * Gets the authorization information for the update operation
	 */
	EppAuthInfo * getAuthInfo()
	{
		return this->authInfo;
	};

	/**
	 * Sets the authorization information for the update operation
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
	 * Converts the <code>EppCommandUpdateSvcsub</code> object into 
	 * an XML element
	 *
	 * @param doc the XML <code>DOMElement</code> object
	 * @param tag the tag/element name for the 
	 *            <code>EppCommandUpdateSvcsub</code> object
	 *
	 * @return an <code>DOMElement</code> object 
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );

	/**
	 * Converts an XML element into an <code>EppCommandUpdateSvcsub</code>
	 * object. The caller of this method must make sure that the root node
	 * is of an EPP Command Update entity for an EPP Svcsub object.
	 *
	 * @param root root node for an <code>EppCommandUpdate</code> object
	 *	     in XML format
	 *
	 * @return an <code>EppCommandUpdateSvcsub</code> object, or null
	 *         if the node is invalid
	 */
	static EppCommandUpdateSvcsub * fromXML( const DOMNode& root );
};

#endif     /* EPPCOMMANDUPDATESVCSUB_HPP */  /* } */
