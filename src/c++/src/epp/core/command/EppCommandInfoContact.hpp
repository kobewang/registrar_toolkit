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

#if ! defined(EPPCOMMANDINFOCONTACT_HPP)    /* { */
#define       EPPCOMMANDINFOCONTACT_HPP        1

#include "EppCommandInfo.hpp"
#include "EppAuthInfo.hpp"

/**
 * This <code>EppCommandInfo</code> class implements EPP Command Info
 * entity for EPP Contact objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:37 $
 */
class EPP_EXPORT EppCommandInfoContact : public EppCommandInfo
{
private:
	DOMString id;
	EppAuthInfo * authInfo;

public:

	/**
	 * Creates an <code>EppCommandInfoContact</code> object
	 */
	EppCommandInfoContact()
	{
		this->id = NULLXS;
		this->authInfo = null;
	};

	/**
	 * Creates an <code>EppCommandInfoContact</code> object for
	 * querying a contact object based on its id
	 */
	EppCommandInfoContact( DOMString id )
	{
		this->id = id;
		this->authInfo = null;
	};

	/**
	 * Creates an <code>EppCommandInfoContact</code> object for
	 * querying a contact object based on its id, given a client
	 * transaction id assoicated with the operation
	 */
	EppCommandInfoContact( DOMString id, DOMString xid )
	{
		this->id = id;
		this->clTRID = xid;
		this->authInfo = null;
	};

	/**
	 * Destructor
	 */
	~EppCommandInfoContact()
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
		return EppEntity::TYPE_EppCommandInfoContact;
	};

	/**
	 * Gets the id of the contact object to be queried
	 */
	DOMString getId()
	{
		return this->id;
	};

	/**
	 * Sets the id of the contact object to be queried
	 */
	void setId( DOMString id )
	{
		this->id = id;
	};

	/**
	 * Gets the authorization info for querying the contact object
	 */
	EppAuthInfo * getAuthInfo()
	{
		return this->authInfo;
	};

	/**
	 * Sets the authorization info for querying the contact object
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
	 * Converts the <code>EppCommandInfoContact</code> object into an XML
	 * element
	 *
	 * @param doc the XML <code>DOMElement</code> object
	 * @param tag the tag/element name for the
	 *            <code>EppCommandInfoContact</code> object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );

	/**
	 * Converts an XML element into an <code>EppCommandInfoContact</code>
	 * object. The caller of this method must make sure that the root node
	 * is of an EPP Command Info entity for EPP contact object.
	 *
	 * @param root root node for an <code>EppCommandInfoContact</code>
	 *             object in XML format
	 *
	 * @return an <code>EppCommandInfoContact</code> object, or null
	 *         if the node is invalid
	 */
	static EppCommandInfoContact * fromXML( const DOMNode& root );
};

#endif     /* EPPCOMMANDINFOCONTACT_HPP */  /* } */
