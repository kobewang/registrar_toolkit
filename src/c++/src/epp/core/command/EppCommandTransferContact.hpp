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

#if ! defined(EPPCOMMANDTRANSFERCONTACT_HPP)    /* { */
#define       EPPCOMMANDTRANSFERCONTACT_HPP        1

#include "EppCommandTransfer.hpp"

/**
 * This <code>EppCommandTransferContact</code> class implements EPP Command
 * Transfer entity for EPP Contact objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:37 $
 */
class EPP_EXPORT EppCommandTransferContact : public EppCommandTransfer
{
private:
	DOMString id;

public:

	/**
	 * Creates an <code>EppCommandTransferContact</code> object for
	 */
	EppCommandTransferContact()
	{
		this->id = NULLXS;
	};

	/**
	 * Creates an <code>EppCommandTransferContact</code> object for
	 * transfering a contact object based on its id
	 */
	EppCommandTransferContact( DOMString id )
	{
		this->id = id;
	};

	/**
	 * Creates an <code>EppCommandTransferContact</code> object for
	 * transfering a contact object based on its id, given a client
	 * transaction id associated with operation
	 */
	EppCommandTransferContact( DOMString id, DOMString xid )
	{
		this->id = id;
		this->clTRID = xid;
	};

	/**
	 * Destructor
	 */
	~EppCommandTransferContact() {};

	/**
	 * Returns the run-time type of an EppEntity object
	 */
	virtual int getEntityType()
	{
		return EppEntity::TYPE_EppCommandTransferContact;
	};

	/**
	 * Gets the id of the contact object to be transferred
	 */
	DOMString getId()
	{
		return this->id;
	};

	/**
	 * Sets the id of the contact object to be transferred
	 */
	void setId( DOMString id )
	{
		this->id = id;
	};

	/**
	 * Converts the <code>EppCommandTransferContact</code> object into
	 * an XML element for an <code>EppPollable</code> object
	 *
	 * @param doc the XML <code>DOMElement</code> object
	 * @param tag the tag/element name for the
	 *            <code>EppCommandTransferContact</code> object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXMLPoll( DOMDocument& doc, const DOMString& tag );

	/**
	 * Converts the <code>EppCommandTransferContact</code> object into
	 * an XML element
	 *
	 * @param doc the XML <code>DOMElement</code> object
	 * @param tag the tag/element name for the
	 *            <code>EppCommandTransferContact</code> object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );

	/**
	 * Converts an XML element into an
	 * <code>EppCommandTransferContact</code> object. The caller of this
	 * method must make sure that the root node is of an EPP Command
	 * Transfer entity for EPP Contact object.
	 *
	 * @param root root node for an <code>EppCommandTransferContact</code>
	 *             object in XML format
	 *
	 * @return an <code>EppCommandTransferContact</code> object, or null
	 *         if the node is invalid
	 */
	static EppCommandTransferContact * fromXML( const DOMNode& root );
};

#endif     /* EPPCOMMANDTRANSFERCONTACT_HPP */  /* } */
