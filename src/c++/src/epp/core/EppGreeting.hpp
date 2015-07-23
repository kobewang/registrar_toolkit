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

#if ! defined(EPPGREETING_HPP)    /* { */
#define       EPPGREETING_HPP        1

#include <time.h>
#include "EppEntity.hpp"
#include "EppServiceMenu.hpp"
#include "EppDataCollectionPolicy.hpp"

/**
 * This <code>EppGreeting</code> class implements EPP Greeting entity used
 * in the EPP Protocol initialization.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:37 $
 */
class EPP_EXPORT EppGreeting : public EppEntity
{
private:
	DOMString        svID;
	time_t           svDate;
	EppServiceMenu * svcMenu;
	EppDataCollectionPolicy * dcp;

	/**
	 * Clones the service menu
	 */
	void cloneServiceMenu( EppServiceMenu& menu );
	/**
	 * Sets defaults data collection policy
	 */
	void setDefaultDataCollectionPolicy();

public:

	/**
	 * Creates an <code>EppGreeting</code> object with default setting
	 */
	EppGreeting()
	{
		this->svID = "Neustar EPP Server";
		this->svDate = time(0);
		this->svcMenu = new EppServiceMenu();
		if( this->svcMenu != null )
		{
			this->svcMenu->addVersion("1.0");
			this->svcMenu->addLanguage("en-US");
			this->svcMenu->addService("urn:ietf:params:xml:ns:contact");
			this->svcMenu->addService("urn:ietf:params:xml:ns:host");
			this->svcMenu->addService("urn:ietf:params:xml:ns:domain");
			this->svcMenu->addService("urn:ietf:params:xml:ns:svcsub");
		}
		this->dcp = null;
		setDefaultDataCollectionPolicy();
	};

	/**
	 * Creates an <code>EppGreeting</code> object with name of TLD included in svID
	 */
	EppGreeting(const string& sTLD)
	{
		this->svID = "Neustar EPP Server";

		if ( sTLD.length() > 0 )
		{
			svID.append(":");
			svID.append(sTLD.c_str());
		}

		this->svDate  = time(0);
		this->svcMenu = new EppServiceMenu();
		if( this->svcMenu != null )
		{
			this->svcMenu->addVersion("1.0");
			this->svcMenu->addLanguage("en-US");
			this->svcMenu->addService("urn:ietf:params:xml:ns:contact");
			this->svcMenu->addService("urn:ietf:params:xml:ns:host");
			this->svcMenu->addService("urn:ietf:params:xml:ns:domain");
			this->svcMenu->addService("urn:ietf:params:xml:ns:svcsub");
		}
		this->dcp = null;
		setDefaultDataCollectionPolicy();
	};

	/**
	 * Creates an <code>EppGreeting</code> object, given a service
	 * identifier and service menu
	 *
	 * @param serviceId the identifier of the service provided
	 * @param serviceMenu the menu of the service provided
	 */
	EppGreeting( DOMString serviceId, EppServiceMenu& serviceMenu )
	{
		this->svID = serviceId;
		this->svDate = time(0);
		this->svcMenu = new EppServiceMenu();
		cloneServiceMenu(serviceMenu);
		setDefaultDataCollectionPolicy();
	};

	/**
	 * Creates an <code>EppGreeting</code> object, given a service
	 * identifier and a service menu, and a data collection policy
	 *
	 * @param serviceId the identifier of the service provided
	 * @param serviceMenu the menu of the service provided
	 * @param dcp the data collection policy provided
	 */
	EppGreeting( DOMString serviceId, EppServiceMenu& serviceMenu, EppDataCollectionPolicy * dcp )
	{
		this->svID = serviceId;
		this->svDate = time(0);
		this->svcMenu = new EppServiceMenu();
		cloneServiceMenu(serviceMenu);
		this->dcp = dcp;
	};

	/**
	 * Destructor
	 */
	~EppGreeting()
	{
		if( this->svcMenu != null )
		{
			delete this->svcMenu;
			this->svcMenu = null;
		}
		if( this->dcp != null )
		{
			delete this->dcp;
			this->dcp = null;
		}
	};

	/**
	 * Gets the identifier of the service
	 */
	DOMString getServiceId()
	{
		return this->svID;
	};

	/**
	 * Sets the identifier of the service
	 */
	void setServiceId( DOMString serviceId )
	{
		this->svID = serviceId;
	};

	/**
	 * Gets the date associated with the service. The default value is the
	 * <code>time_t</code> value when the <code>EppGreeting</code> is
	 * created.
	 */
	time_t getServiceDate()
	{
		return this->svDate;
	};

	/**
	 * Sets the date associated with the service
	 */
	void setServiceDate( time_t serviceDate )
	{
		this->svDate = serviceDate;
	};

	/**
	 * Gets the menu of the service provided
	 */
	EppServiceMenu * getServiceMenu()
	{
		return this->svcMenu;
	};

	/**
	 * Sets the menu of the service provided
	 */
	void setServiceMenu( EppServiceMenu& serviceMenu )
	{
		cloneServiceMenu(serviceMenu);
	};

	/**
	 * Gets the data collection policy
	 */
	EppDataCollectionPolicy * getDataCollectionPolicy()
	{
		return this->dcp;
	};

	/**
	 * Sets the data collection policy
	 */
	void setDataCollectionPolicy( EppDataCollectionPolicy * dcp )
	{
		if( this->dcp != null )
		{
			delete this->dcp;
		}
		this->dcp = dcp;
	};

	/**
	 * Converts the <code>EppGreeting</code> object into an XML element
	 *
	 * @param doc the XML <code>DOMElement</code> object
	 * @param tag the tag/element name for the <code>EppGreeting</code>
	 *            object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );

	/**
	 * Converts an XML element into an <code>EppGreeting</code> object.
	 * The caller of this method must make sure that the root node is of
	 * the EPP addrType.
	 *
	 * @param root root node for an <code>EppGreeting</code> object
	 *             in XML format
	 *
	 * @return an <code>EppGreeting</code> object, or null if the node
	 *         is invalid
	 */
	static EppGreeting * fromXML( const DOMNode& root );

	DOMString toString()
	{
		return EppEntity::toString(DOMString("greeting"));
	};
};

#endif     /* EPPGREETING_HPP */  /* } */
