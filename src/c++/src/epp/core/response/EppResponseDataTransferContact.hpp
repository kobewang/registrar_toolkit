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

#if ! defined(EPPRESPONSEDATATRANSFERCONTACT_HPP)    /* { */
#define       EPPRESPONSEDATATRANSFERCONTACT_HPP        1

#include "EppResponseDataTransfer.hpp"

/**
 * This <code>EppResponseDataTransferContact</code> class implements EPP
 * Response Data entity for EPP Command Transfer of EPP Contact objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
class EPP_EXPORT EppResponseDataTransferContact : public EppResponseDataTransfer
{
private:
	DOMString id;

public:

	/**
	 * Creates an <code>EppResponseDataTransferContact</code> object
	 */
	EppResponseDataTransferContact()
	{
		this->id = null;
	};

	/**
	 * Creates an <code>EppResponseDataTransferContact</code> object
	 */
	EppResponseDataTransferContact( DOMString id )
	{
		this->id = id;
	};

	/**
	 * Destructor
	 */
	~EppResponseDataTransferContact() {};

	/**
	 * Returns the run-time type of an EppEntity object
	 */
	virtual int getEntityType()
	{
		return EppEntity::TYPE_EppResponseDataTransferContact;
	};

	/**
	 * Gets the id of the EPP Contact object
	 */
	DOMString getId()
	{
		return this->id;
	};

	/**
	 * Sets the id of the EPP Contact Object
	 */
	void setId( DOMString id )
	{
		this->id = id;
	};

	/**
	 * Converts the <code>EppResponseDataTransferContact</code> object into
	 * an XML element
	 *
	 * @param doc the XML <code>DOMElement</code> object
	 * @param tag the tag/element name for the
	 *            <code>EppResponseDataTransferContact</code> object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );

	/**
	 * Converts an XML element into an
	 * <code>EppResponseDataTransferContact</code> object. The caller
	 * of this method must make sure that the root node is of an EPP
	 * Response Transfer entity for EPP Contact object.
	 *
	 * @param root root node for an
	 *             <code>EppResponseDataTransferContact</code>
	 *             object in XML format
	 *
	 * @return an <code>EppResponseDataTransferContact</code> object,
	 *         or null if the node is invalid
	 */
	static EppResponseDataTransferContact * fromXML( const DOMNode& root );
};

#endif     /* EPPRESPONSEDATATRANSFERCONTACT_HPP */  /* } */
