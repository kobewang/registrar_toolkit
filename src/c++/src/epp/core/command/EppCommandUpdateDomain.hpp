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

#if ! defined(EPPCOMMANDUPDATEDOMAIN_HPP)    /* { */
#define       EPPCOMMANDUPDATEDOMAIN_HPP        1

#include "EppCommandUpdate.hpp"
#include "EppContactType.hpp"
#include "EppAuthInfo.hpp"
#include "EppHostAttribute.hpp"

#define	MAX_NUM_OF_UPDATE_NAMESERVERS	16
#define	MAX_NUM_OF_UPDATE_CONTACTS	16

/**
 * This <code>EppCommandUpdateDomain</code> class implements EPP Command Update
 * entity for EPP Domain objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
class EPP_EXPORT EppCommandUpdateDomain : public EppCommandUpdate
{
private:
	DOMString                       name;
	OldValueVectorOf<DOMString>      * nsAdded;
	OldValueVectorOf<DOMString>      * nsRemoved;
	OldValueVectorOf<EppHostAttribute *> * nsAttrAdded;
	OldValueVectorOf<EppHostAttribute *> * nsAttrRemoved;
	OldValueVectorOf<EppContactType> * contactAdded;
	OldValueVectorOf<EppContactType> * contactRemoved;
	DOMString                       newRegistrant;
	EppAuthInfo                   * newAuthInfo;

	/**
	 * Frees a list of name server host attributes
	 */
	void freeNameServerAttribute( OldValueVectorOf<EppHostAttribute *> * list )
	{
		if( list != null )
		{
			for( unsigned int i = 0; i < list->size(); i++ )
			{
				EppHostAttribute * p = list->elementAt(i);
				if( p != null )
				{
					delete p;
				}
			}
			delete list;
		}
	};

	/**
	 * Converts a list of name servers into XML
	 *
	 * @param doc  the XML <code>DOMElement</code> object
	 * @param body the XML <code>DOMElement</code> object to which the list
	 *             of name servers is appended
	 * @param list the list of name servers to be converted
	 */
	void nameServerToXML( DOMDocument& doc, DOMElement& body, OldValueVectorOf<DOMString> * list );

	/**
	 * Converts a list of name servers into XML
	 *
	 * @param doc  the XML <code>DOMElement</code> object
	 * @param body the XML <code>DOMElement</code> object to which the list
	 *             of name servers is appended
	 * @param list the list of name servers to be converted
	 */
	void nameServerToXML( DOMDocument& doc, DOMElement& body, OldValueVectorOf<EppHostAttribute *> * list );

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
	 * Converts a list of name servers from XML format
	 *
	 * @param root the XML <code>Node</code> object containing the list
	 *             of name servers
	 * @param addOrRem flag indicating for adding or removing name servers
	 */
	void nameServerFromXML( const DOMNode& root, bool addOrRem );

	/**
	 * Converts a list of contacts from XML format
	 *
	 * @param root the XML <code>Node</code> object containing the list
	 *             of IP addresses 
	 * @param contactList the list of contacts to be stored
	 */
	void contactFromXML( const DOMNode& root, OldValueVectorOf<EppContactType> * contactList );

public:

	/**
	 * Creates an <code>EppCommandUpdateDomain</code>
	 */
	EppCommandUpdateDomain()
	{
		this->name           = NULLXS;
		this->nsAdded        = null;
		this->nsRemoved      = null;
		this->nsAttrAdded    = null;
		this->nsAttrRemoved  = null;
		this->contactAdded   = new OldValueVectorOf<EppContactType>(MAX_NUM_OF_UPDATE_CONTACTS);
		this->contactRemoved = new OldValueVectorOf<EppContactType>(MAX_NUM_OF_UPDATE_CONTACTS);
		this->newRegistrant  = NULLXS;
		this->newAuthInfo    = null;
	};

	/**
	 * Creates an <code>EppCommandUpdateDomain</code> given the name of the
	 * domain
	 */
	EppCommandUpdateDomain( DOMString name )
	{
		this->name           = name;
		this->nsAdded        = null;
		this->nsRemoved      = null;
		this->nsAttrAdded    = null;
		this->nsAttrRemoved  = null;
		this->contactAdded   = new OldValueVectorOf<EppContactType>(MAX_NUM_OF_UPDATE_CONTACTS);
		this->contactRemoved = new OldValueVectorOf<EppContactType>(MAX_NUM_OF_UPDATE_CONTACTS);
		this->newRegistrant  = NULLXS;
		this->newAuthInfo    = null;
	};

	/**
	 * Creates an <code>EppCommandUpdateDomain</code> given the name of the
	 * domain
	 */
	EppCommandUpdateDomain( DOMString name, DOMString xid )
	{
		this->name           = name;
		this->nsAdded        = null;
		this->nsRemoved      = null;
		this->nsAttrAdded    = null;
		this->nsAttrRemoved  = null;
		this->contactAdded   = new OldValueVectorOf<EppContactType>(MAX_NUM_OF_UPDATE_CONTACTS);
		this->contactRemoved = new OldValueVectorOf<EppContactType>(MAX_NUM_OF_UPDATE_CONTACTS);
		this->newRegistrant  = NULLXS;
		this->newAuthInfo    = null;
		this->clTRID         = xid;
	};

	/**
	 * Destructor
	 */
	~EppCommandUpdateDomain()
	{
		if( this->nsAdded != null )
		{
			delete this->nsAdded;
			this->nsAdded = null;
		}
		if( this->nsRemoved != null )
		{
			delete this->nsRemoved;
			this->nsRemoved = null;
		}
		if( this->nsAttrAdded != null )
		{
			freeNameServerAttribute(this->nsAttrAdded);
			this->nsAttrAdded = null;
		}
		if( this->nsAttrRemoved != null )
		{
			freeNameServerAttribute(this->nsAttrRemoved);
			this->nsAttrRemoved = null;
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
	};

	/**
	 * Returns the run-time type of an EppEntity object
	 */
	virtual int getEntityType()
	{
		return EppEntity::TYPE_EppCommandUpdateDomain;
	};

	/**
	 * Gets the name of the domain to be updated
	 */
	DOMString getName()
	{
		return this->name;
	};

	/**
	 * Sets the name of the domain to be updated
	 */
	void setName( DOMString& name )
	{
		this->name = name;
	};

	/**
	 * Gets the list of new name servers to be added to the domain
	 */
	OldValueVectorOf<DOMString> * getNameServerAdded()
	{
		return this->nsAdded;
	};

	/**
	 * Adds a new name server to be associated with the domain
	 */
	void addNameServer( DOMString nameServer )
	{
		if( this->nsAttrAdded != null )
		{
			freeNameServerAttribute(this->nsAttrAdded);
			this->nsAttrAdded = null;
		}
		if( this->nsAdded == null )
		{
			this->nsAdded = new OldValueVectorOf<DOMString>(MAX_NUM_OF_UPDATE_NAMESERVERS);
		}
		if( this->nsAdded != null )
		{
			this->nsAdded->addElement(nameServer);
		}
	};

	/**
	 * Gets the list of old name servers to be removed from the domain
	 */
	OldValueVectorOf<DOMString> * getNameServerRemoved()
	{
		return this->nsRemoved;
	};

	/*
	 * Removes an old name server associated with the domain
	 */
	void removeNameServer( DOMString nameServer )
	{
		if( this->nsAttrRemoved != null )
		{
			freeNameServerAttribute(this->nsAttrRemoved);
			this->nsAttrRemoved = null;
		}
		if( this->nsRemoved == null )
		{
			this->nsRemoved = new OldValueVectorOf<DOMString>(MAX_NUM_OF_UPDATE_NAMESERVERS);
		}
		if( this->nsRemoved != null )
		{
			this->nsRemoved->addElement(nameServer);
		}
	};

	/**
	 * Gets the list of new name servers, specified as host attributes, to be added to the domain
	 */
	OldValueVectorOf<EppHostAttribute *> * getNameServerAttributeAdded()
	{
		return this->nsAttrAdded;
	};

	/**
	 * Adds a new name server, specified as host attribute, be associated with the domain
	 */
	void addNameServer( EppHostAttribute * nameServer )
	{
		if( nameServer == null )
		{
			return;
		}
		if( this->nsAdded != null )
		{
			delete this->nsAdded;
			this->nsAdded = null;
		}
		if( this->nsAttrAdded == null )
		{
			this->nsAttrAdded = new OldValueVectorOf<EppHostAttribute *>(MAX_NUM_OF_UPDATE_NAMESERVERS);
		}
		if( this->nsAttrAdded != null )
		{
			this->nsAttrAdded->addElement(nameServer);
		}
	};

	/**
	 * Gets the list of old name servers, specified as host attributes, to be removed from the domain
	 */
	OldValueVectorOf<EppHostAttribute *> * getNameServerAttributeRemoved()
	{
		return this->nsAttrRemoved;
	};

	/*
	 * Removes an old name server, specified as host attributes, associated with the domain
	 */
	void removeNameServer( EppHostAttribute * nameServer )
	{
		if( nameServer == null )
		{
			return;
		}
		if( this->nsRemoved != null )
		{
			delete this->nsRemoved;
			this->nsRemoved = null;
		}
		if( this->nsAttrRemoved == null )
		{
			this->nsAttrRemoved = new OldValueVectorOf<EppHostAttribute *>(MAX_NUM_OF_UPDATE_NAMESERVERS);
		}
		if( this->nsAttrRemoved != null )
		{
			this->nsAttrRemoved->addElement(nameServer);
		}
	};

	/**
	 * Gets the list of new contacts to be added to the domain
	 */
	OldValueVectorOf<EppContactType> * getContactAdded()
	{
		return this->contactAdded;
	};

	/**
	 * Adds a new contact to be associated with the domain
	 */
	void addContact( EppContactType& contact )
	{
		this->contactAdded->addElement(contact);
	};

	/**
	 * Gets the list of old contacts to be removed from the domain
	 */
	OldValueVectorOf<EppContactType> * getContactRemoved()
	{
		return this->contactRemoved;
	};

	/*
	 * Removes an old contact associated with the domain
	 */
	void removeContact( EppContactType& contact )
	{
		this->contactRemoved->addElement(contact);
	};

	/**
	 * Gets the new registrant's contact id for the domain, or null if the
	 * registrant of the domain is not to be changed
	 */
	DOMString getNewRegistrant()
	{
		return this->newRegistrant;
	};

	/**
	 * Sets the new registrant's contact id for the domain if a new registrant
	 * claims the ownership of the domain
	 */
	void setNewRegistrant( DOMString registrant )
	{
		this->newRegistrant = registrant;
	};

	/**
	 * Gets the new authorization information for the domain, or null if
	 * the authorization information of the domain is not to be changed
	 */
	EppAuthInfo * getNewAuthInfo()
	{
		return this->newAuthInfo;
	};

	/**
	 * Sets the new authorization information for the domain if it needs
	 * to be changed
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
	 * Converts the <code>EppCommandUpdateDomain</code> object into 
	 * an XML element
	 *
	 * @param doc the XML <code>DOMElement</code> object
	 * @param tag the tag/element name for the 
	 *            <code>EppCommandUpdateDomain</code> object
	 *
	 * @return an <code>DOMElement</code> object 
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );

	/**
	 * Converts an XML element into an <code>EppCommandUpdateDomain</code>
	 * object. The caller of this method must make sure that the root node
	 * is of an EPP Command Update entity for an EPP Domain object.
	 *
	 * @param root root node for an <code>EppCommandUpdate</code> object
	 *	     in XML format
	 *
	 * @return an <code>EppCommandUpdateDomain</code> object, or null
	 *         if the node is invalid
	 */
	static EppCommandUpdateDomain * fromXML( const DOMNode& root );
};

#endif     /* EPPCOMMANDUPDATEDOMAIN_HPP */  /* } */
