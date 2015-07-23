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

#if ! defined(EPPRESPONSEDATATRANSFERDOMAIN_HPP)    /* { */
#define       EPPRESPONSEDATATRANSFERDOMAIN_HPP        1

#include "EppResponseDataTransfer.hpp"

/**
 * This <code>EppResponseDataTransferDomain</code> class implements EPP
 * Response Data entity for EPP Command Transfer of EPP Domain objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
class EPP_EXPORT EppResponseDataTransferDomain : public EppResponseDataTransfer
{
private:
	DOMString name;

public:

	/**
	 * Creates an <code>EppResponseDataTransferDomain</code> object
	 */
	EppResponseDataTransferDomain()
	{
		this->name = null;
	};

	/**
	 * Creates an <code>EppResponseDataTransferDomain</code> object
	 */
	EppResponseDataTransferDomain( DOMString name )
	{
		this->name = name;
	};

	/**
	 * Destructor
	 */
	~EppResponseDataTransferDomain() {};

	/**
	 * Returns the run-time type of an EppEntity object
	 */
	virtual int getEntityType()
	{
		return EppEntity::TYPE_EppResponseDataTransferDomain;
	};

	/**
	 * Gets the name of the EPP Domain object
	 */
	DOMString getName()
	{
		return this->name;
	};

	/**
	 * Sets the name of the EPP Domain Object
	 */
	void setName( DOMString name )
	{
		this->name = name;
	};

	/**
	 * Gets the new expiration date of the domain after the transfer, or
	 * null if the transfer request does not change the expiration date
	 * of domain.
	 */
	time_t getDateExpired()
	{
		return this->exDate;
	};

	/**
	 * Sets the new expiration date of the domain after the transfer.
	 * The value of the new expiration date is optional.
	 */
	void setDateExpired( time_t date )
	{
		this->exDate = date;
	};

	/**
	 * Converts the <code>EppResponseDataTransferDomain</code> object into
	 * an XML element
	 *
	 * @param doc the XML <code>DOMElement</code> object
	 * @param tag the tag/element name for the
	 *            <code>EppResponseDataTransferDomain</code> object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );

	/**
	 * Converts an XML element into an
	 * <code>EppResponseDataTransferDomain</code> object. The caller of
	 * this method must make sure that the root node is of an EPP Response
	 * Transfer entity for EPP Domain object.
	 *
	 * @param root root node for an
	 *             <code>EppResponseDataTransferDomain</code>
	 *             object in XML format
	 *
	 * @return an <code>EppResponseDataTransferDomain</code> object, or null
	 *         if the node is invalid
	 */
	static EppResponseDataTransferDomain * fromXML( const DOMNode& root );
};

#endif     /* EPPRESPONSEDATATRANSFERDOMAIN_HPP */  /* } */
