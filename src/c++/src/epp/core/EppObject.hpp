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

#if ! defined(EPPOBJECT_HPP)    /* { */
#define       EPPOBJECT_HPP        1

#include <time.h>
#include "EppEntity.hpp"
#include "EppStatus.hpp"
#include "EppAuthInfo.hpp"


#define	MAX_NUM_OF_STATUS	10

/* stupid math.h */
#ifdef DOMAIN
#undef DOMAIN
#endif

class EppCommandCreate;

/**
 * This <code>EppObject</code> class is the base class for all objects
 * registered in the registry via the EPP Protocol.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.4 $ $Date: 2009/11/23 14:35:38 $
 */
class EPP_EXPORT EppObject : public EppEntity
{
protected:
	/**
	 * The ROID of the EPP Object in the registry
	 */
	DOMString                  roid;
	/**
	 * The status list associated with the EPP Object in the registry
	 */
	OldValueVectorOf<EppStatus> * status;
	/**
	 * The id of the client that is sponsering the EPP Object
	 */
	DOMString                  clID;
	/**
	 * The id of the client that created the EPP Object initially
	 */
	DOMString                  crID;
	/**
	 * The timestamp when the EPP Object was created initially
	 */
	time_t                     crDate;
	/**
	 * The id of the client that updated the EPP Object most recently
	 */
	DOMString                  upID;        // optional
	/**
	 * The timestamp when the EPP Object was updated most recently
	 */
	time_t                     upDate;      // optional
	/**
	 * The timestamp when the EPP Object is set to be expired
	 *
	 * @note this field only applies to EPP objects with registration period.
	 */
	time_t                     exDate;      // optional, only valid for EppDomain/EppSvcsub
	/**
	 * The timestamp when the EPP Object was transferred most recently
	 *
	 * @note this field does not apply to EPP Host objects
	 */
	time_t                     trDate;      // optional
	/**
	 * The authorization information associated with the EPP object
	 *
	 * @note this field does not apply to EPP Host objects
	 */
	EppAuthInfo              * authInfo;    // not valid for EppHost

	/**
	 * Converts shared <code>EppObject</code> components from XML
	 *
	 * @param node the node for a shared <code>EppObject</code> component
	 * @param name the name of the node for the shared
	 *             <code>EppObject</code> component
	 */
	void fromXMLCommon( const DOMNode& node, const DOMString& name );

	/**
	 * Converts shared <code>EppObject</code> components into XML
	 *
	 * @param doc the XML <code>DOMElement</code> object
	 * @param body the XML <code>DOMElement</code> to be attached
	 */
	void toXMLCommon( DOMDocument& doc, DOMElement& body );

	/**
	 * Frees shared <code>EppObject</code> components
	 */
	void freeCommon();

public:
	/**
	 * Type for EPP Contact objcet
	 */
	static const int CONTACT;
	static const int DOMAIN;
	/**
	 * Type for EPP Domain object
	 */
	/**
	 * Type for EPP Host object
	 */
	static const int HOST;
	/**
	 * Type for EPP Svcsub object
	 */
	static const int SVCSUB;
	/**
	 * Type for EPP XRI Authority object
	 */
	static const int XRI_AUTHORITY;
	/**
	 * Type for EPP XRI I-Number object
	 */
	static const int XRI_INUMBER;
	/**
	 * Type for EPP XRI I-Name object
	 */
	static const int XRI_INAME;
	/**
	 * Type for EPP XRI I-Service object
	 */
	static const int XRI_ISERVICE;

	/**
	 * Type for EPP XRI CodeString object
	 */
	static const int XRI_CODESTRING;
	/**
	 * Constructor
	 */
	EppObject();

	/**
	 * Destructor
	 */
	virtual ~EppObject() {};

	/**
	 * Returns the run-time type of an EppEntity object
	 */
	virtual int getEntityType()
	{
		return EppEntity::TYPE_EppObject;
	};

	/**
	 * Gets ROID of the <code>EppObject</code>
	 */
	DOMString getRoid()
	{
		return this->roid;
	};

	/**
	 * Sets ROID of the <code>EppObject</code>
	 */
	void setRoid( const DOMString& roid )
	{
		this->roid = roid;
	};

	/**
	 * Gets the id of the registrar client sponsering the
	 * <code>EppObject</code>.
	 */
	DOMString getClientId()
	{
		return this->clID;
	};

	/**
	 * Sets the id of the registrar client sponsering the
	 * <code>EppObject</code>
	 */
	void setClientId( const DOMString& clientId )
	{
		this->clID = clientId;
	};

	/**
	 * Gets the id of the registrar client creating the
	 * <code>EppObject</code>
	 * initially
	 */
	DOMString getClientIdCreated()
	{
		return this->crID;
	};

	/**
	 * Sets the id of the registrar client creating the
	 * <code>EppObject</code> initially
	 */
	void setClientIdCreated( const DOMString& clientId )
	{
		this->crID = clientId;
	};

	/**
	 * Gets the date of the <code>EppObject</code> created
	 */
	time_t getDateCreated()
	{
		return this->crDate;
	};

	/**
	 * Sets the date of the <code>EppObject</code> created
	 */
	void setDateCreated( time_t cal )
	{
		this->crDate = cal;
	};

	/**
	 * Gets the id of the registrar client updating the
	 * <code>EppObject</code> most recently
	 */
	DOMString getClientIdUpdated()
	{
		return this->upID;
	};

	/**
	 * Sets the id of the registrar client updating the
	 * <code>EppObject</code> most recently
	 */
	void setClientIdUpdated( const DOMString& clientId )
	{
		this->upID = clientId;
	};

	/**
	 * Gets the date of the <code>EppObject</code> updated
	 */
	time_t getDateUpdated()
	{
		return this->upDate;
	};

	/**
	 * Sets the date of the <code>EppObject</code> updated
	 */
	void setDateUpdated( time_t cal )
	{
		this->upDate = cal;
	};

	/**
	 * Gets the date of the <code>EppObject</code> expiration date
	 *
	 * @note Currently, only an EPP domain object has an expiration date
	 */
	time_t getDateExpired()
	{
		return this->exDate;
	};

	/**
	 * Sets the expiration date of the <code>EppObject</code>
	 *
	 * @note Currently, only an EPP domain object has an expiration date
	 */
	void setDateExpired( time_t cal )
	{
		this->exDate = cal;
	};

	/**
	 * Gets the date of the <code>EppObject</code> transferred
	 */
	time_t getDateTransferred()
	{
		return this->trDate;
	};

	/**
	 * Sets the date of the <code>EppObject</code> transferred
	 */
	void setDateTransferred( time_t cal )
	{
		this->trDate = cal;
	};

	/**
	 * Gets the authorization info of the <code>EppObject</code>
	 */
	EppAuthInfo * getAuthInfo()
	{
		return this->authInfo;
	};

	/**
	 * Sets the authorization info of the <code>EppObject</code>
	 */
	void setAuthInfo( EppAuthInfo authInfo )
	{
		if( this->authInfo == null )
		{
			this->authInfo = new EppAuthInfo();
		}
		*this->authInfo = authInfo;
	};

	/**
	 * Gets the status of the <code>EppObject</code>
	 */
	OldValueVectorOf<EppStatus> * getStatus()
	{
		return this->status;
	};

	/**
	 * Adds a status to the <code>EppObject</code>
	 */
	void addStatus( EppStatus status )
	{
		this->status->addElement(status);
	};

	/**
	 * Converts an XML element into an <code>EppObject</code> object
	 *
	 * @param root root node for an <code>EppObject</code> object
	 * in XML format
	 */
	static EppObject * fromXML( const DOMNode& root );

	/**
	 * Creates an <code>EppCommandCreate</code> object for creating
	 * an EPP Object in the registry.
	 *
	 * @param object the EPP Object to be created in the registry
	 * @param xid the client transaction id associated with the operation
	 */
	static EppCommandCreate create( EppObject * object, DOMString xid );
};

#endif     /* EPPOBJECT_HPP */  /* } */
