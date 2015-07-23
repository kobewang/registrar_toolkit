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

#if ! defined(EPPCONTACT_HPP)    /* { */
#define       EPPCONTACT_HPP        1

#include "EppObject.hpp"
#include "EppContactData.hpp"
#include "EppContactDisclose.hpp"
#include "EppE164.hpp"
#include "EppCommandDeleteContact.hpp"
#include "EppCommandInfoContact.hpp"
#include "EppCommandCheckContact.hpp"
#include "EppCommandTransferContact.hpp"
#include "EppCommandUpdateContact.hpp"

/**
 * This <code>EppContact</code> class implements EPP Contact objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:37 $
 */
class EPP_EXPORT EppContact : public EppObject
{
private:
	DOMString        id;
	EppContactData * ascii;
	EppContactData * i15d;          // optional
	EppE164        * voice;         // optional
	EppE164        * fax;           // optional
	DOMString        email;
	EppContactDisclose * disclose;	// optional

public:
	/**
	 * Contact status - clientDeleteProhibited
	 */
	static const char * STATUS_CLIENT_DELETE_PROHIBITED;
	/**
	 * Contact status - clientTransferProhibited
	 */
	static const char * STATUS_CLIENT_TRANSFER_PROHIBITED;
	/**
	 * Contact status - clientUpdateProhibited
	 */
	static const char * STATUS_CLIENT_UPDATE_PROHIBITED;
	/**
	 * Contact status - linked
	 */
	static const char * STATUS_LINKED;
	/**
	 * Contact status - ok
	 */
	static const char * STATUS_OK;
	/**
	 * Contact status - pendingCreate (EPP-1.0)
	 */
	static const char * STATUS_PENDING_CREATE;
	/**
	 * Contact status - pendingDelete
	 */
	static const char * STATUS_PENDING_DELETE;
	/**
	 * Contact status - pendingTransfer
	 */
	static const char * STATUS_PENDING_TRANSFER;
	/**
	 * Contact status - pendingUpdate (EPP-1.0)
	 */
	static const char * STATUS_PENDING_UPDATE;
	/**
	 * Contact status - serverDeleteProhibited
	 */
	static const char * STATUS_SERVER_DELETE_PROHIBITED;
	/**
	 * Contact status - serverTransferProhibited
	 */
	static const char * STATUS_SERVER_TRANSFER_PROHIBITED;
	/**
	 * Contact status - serverUpdateProhibited
	 */
	static const char * STATUS_SERVER_UPDATE_PROHIBITED;

	/**
	 * Creates an <code>EppContact</code> object
	 */
	EppContact()
	{
		this->id    = NULLXS;
		this->ascii = null;
		this->i15d  = null;
		this->voice = null;
		this->fax   = null;
		this->email = NULLXS;
		this->disclose = null;
	};

	/**
	 * Creates an <code>EppContact</code> object, with a contact id
	 */
	EppContact( DOMString id )
	{
		this->id    = id;
		this->ascii = null;
		this->i15d  = null;
		this->voice = null;
		this->fax   = null;
		this->email = NULLXS;
		this->disclose = null;
	};

	/**
	 * Destructor
	 */
	~EppContact()
	{
		EppObject::freeCommon();
		if( this->ascii != null )
		{
			delete this->ascii;
			this->ascii = null;
		}
		if( this->i15d != null )
		{
			delete this->i15d;
			this->i15d = null;
		}
		if( this->voice != null )
		{
			delete this->voice;
			this->voice = null;
		}
		if( this->fax != null )
		{
			delete this->fax;
			this->fax = null;
		}
		if( this->disclose != null )
		{
			delete this->disclose;
			this->disclose = null;
		}
	};

	/**
	 * Returns the run-time type of an EppEntity object
	 */
	virtual int getEntityType()
	{
		return EppEntity::TYPE_EppContact;
	};

	/**
	 * Gets the contact id
	 */
	DOMString getId()
	{
		return this->id;
	};

	/**
	 * Sets the contact id
	 */
	void setId( DOMString id )
	{
		this->id = id;
	};

	/**
	 * Gets the contact data in internationalized encoding (ASCII)
	 */
	EppContactData * getContactDataInt()
	{
		return this->ascii;
	};

	/**
	 * Sets the contact data in internationalized encoding (ASCII)
	 */
	void setContactDataInt( EppContactData& ascii )
	{
		if( this->ascii == null )
		{
			this->ascii = new EppContactData();
		}
		*(this->ascii) = ascii;
	};

	/**
	 * Gets the contact data in native encoding (UTF-8)
	 */
	EppContactData * getContactDataLoc()
	{
		return this->i15d;
	};

	/**
	 * Sets the contact data in native encoding (UTF-8)
	 */
	void setContactDataLoc( EppContactData& i15d )
	{
		if( this->i15d == null )
		{
			this->i15d = new EppContactData();
		}
		*(this->i15d) = i15d;
	};

	/**
	 * Gets the contact data in ascii encoding - alias to getContactDataInt()
	 */
	EppContactData * getContactDataAscii()
	{
		return this->ascii;
	};

	/**
	 * Sets the contact data in ascii encoding - alias to setContactDataInt()
	 */
	void setContactDataAscii( EppContactData& ascii )
	{
		if( this->ascii == null )
		{
			this->ascii = new EppContactData();
		}
		*(this->ascii) = ascii;
	};

	/**
	 * Gets the contact data in native encoding - alias to getContactDataLoc()
	 */
	EppContactData * getContactDataI15d()
	{
		return this->i15d;
	};

	/**
	 * Sets the contact data in native encoding - alias to setContactDataLoc()
	 */
	void setContactDataI15d( EppContactData& i15d )
	{
		if( this->i15d == null )
		{
			this->i15d = new EppContactData();
		}
		*(this->i15d) = i15d;
	};

	/**
	 * Gets the voice phone number of the contact
	 */
	EppE164 * getVoice()
	{
		return this->voice;
	};

	/**
	 * Sets the voice phone number of the contact
	 */
	void setVoice( DOMString voice )
	{
		setVoice(EppE164(voice));
	};

	/**
	 * Sets the voice phone number and extenstion
	 */
	void setVoice( DOMString voice, DOMString ext )
	{
		setVoice(EppE164(voice, ext));
	};

	/**
	 * Sets the voice phone number of the contact
	 */
	void setVoice( EppE164 voice )
	{
		if( this->voice == null )
		{
			this->voice = new EppE164();
		}
		*(this->voice) = voice;
	};

	/**
	 * Gets the fax number of the contact
	 */
	EppE164 * getFax()
	{
		return this->fax;
	};

	/**
	 * Sets the fax number of the contact
	 */
	void setFax( DOMString fax )
	{
		setFax(EppE164(fax));
	};

	/**
	 * Sets the fax number and extenstion
	 */
	void setFax( DOMString fax, DOMString ext )
	{
		setFax(EppE164(fax, ext));
	};

	/**
	 * Sets the fax number extension
	 */
	void setFax( EppE164 fax )
	{
		if( this->fax == null )
		{
			this->fax = new EppE164();
		}
		*(this->fax) = fax;
	};

	/**
	 * Gets the email address of the contact
	 */
	DOMString getEmail()
	{
		return this->email;
	};

	/**
	 * Sets the email address of the contact
	 */
	void setEmail( DOMString email )
	{
		this->email = email;
	};

	/**
	 * Gets the EPP Contact Disclose object
	 */
	EppContactDisclose * getContactDisclose()
	{
		return this->disclose;
	};

	/**
	 * Sets the EPP Contact Disclose object
	 */
	void setContactDisclose( EppContactDisclose& disclose )
	{
		if( this->disclose == null )
		{
			this->disclose = new EppContactDisclose();
		}
		*(this->disclose) = disclose;
	};

	/**
	 * Converts the <code>EppContact</code> object into an XML element
	 *
	 * @param doc the XML <code>DOMElement</code> object
	 * @param tag the tag/element name for the <code>EppContact</code> object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );

	/**
	 * Converts an XML element into an <code>EppContact</code> object.
	 * The caller of this method must make sure that the root node is of
	 * the EPP Contact type.
	 *
	 * @param root root node for an <code>EppContact</code> object
	 *             in XML format
	 *
	 * @return an <code>EppContact</code> object, or null if the node
	 *         is invalid
	 */
	static EppContact * fromXML( const DOMNode& root );

	DOMString toString()
	{
		return EppEntity::toString(DOMString("contact"));
	};

	/**
	 * Creates an <code>EppCommandDeleteContact</code> object for
	 * deleting an EPP Contact object from the registry.
	 *
	 * @param id the id of the contact object to be deleted
	 * @param xid  the client transaction id associated with the operation
	 */
	static EppCommandDeleteContact remove( DOMString id, DOMString xid )
	{
		return EppCommandDeleteContact(id, xid);
	};

	/**
	 * Creates an <code>EppCommandInfoContact</code> object for
	 * querying the details of an EPP Contact object
	 *
	 * @param id the id of the contact object to be queried
	 * @param xid  the client transaction id associated with the operation
	 */
	static EppCommandInfoContact info( DOMString id, DOMString xid )
	{
		return EppCommandInfoContact(id, xid);
	};

	/**
	 * Creates an <code>EppCommandCheckContact</code> object for
	 * checking the existance of EPP Contact objects in the registry.
	 * ids of EPP Contact objects can be added via the
	 * <code>add</code> or <code>addRoid</code> methods.
	 *
	 * @param xid  the client transaction id associated with the operation
	 */
	static EppCommandCheckContact check( DOMString xid )
	{
		return EppCommandCheckContact(xid);
	};

	/**
	 * Creates an <code>EppCommandTransferContact</code> object for
	 * transfering an EPP Contact object in the registry. The operation
	 * type and authorization information associated with the operation
	 * should be specified via <code>setOperation</code> and
	 * <code>setAuthInfo</code> method.
	 *
	 * @param id the id of the contact object to be transferred
	 * @param xid  the client transaction id associated with the operation
	 */
	static EppCommandTransferContact transfer( DOMString id, DOMString xid )
	{
		return EppCommandTransferContact(id, xid);
	};

	/**
	 * Creates an <code>EppCommandUpdateContact</code> object for
	 * updating an EPP Contact object in the registry. The actual update
	 * information should be specified via the various methods defined
	 * for the <code>EppCommandUpdateContact</code> object.
	 *
	 * @param id the id of the contact object to be updated
	 * @param xid  the client transaction id associated with the operation
	 */
	static EppCommandUpdateContact update( DOMString id, DOMString xid )
	{
		return EppCommandUpdateContact(id, xid);
	};
};

#endif     /* EPPCONTACT_HPP */  /* } */
