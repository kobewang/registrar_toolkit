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

#if ! defined(EPPRESPONSEDATATRANSFERSVCSUB_HPP)    /* { */
#define       EPPRESPONSEDATATRANSFERSVCSUB_HPP        1

#include "EppResponseDataTransfer.hpp"

/**
 * This <code>EppResponseDataTransferSvcsub</code> class implements EPP
 * Response Data entity for EPP Command Transfer of EPP Svcsub objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $
 */
class EPP_EXPORT EppResponseDataTransferSvcsub : public EppResponseDataTransfer
{
private:
	DOMString id;
	DOMString service;

public:

	/**
	 * Creates an <code>EppResponseDataTransferSvcsub</code> object
	 */
	EppResponseDataTransferSvcsub()
	{
		this->id = null;
		this->service = null;
	};

	/**
	 * Creates an <code>EppResponseDataTransferSvcsub</code> object
	 */
	EppResponseDataTransferSvcsub( DOMString id )
	{
		this->id = id;
		this->service = null;
	};

	/**
	 * Destructor
	 */
	~EppResponseDataTransferSvcsub() {};

	/**
	 * Returns the run-time type of an EppEntity object
	 */
	virtual int getEntityType()
	{
		return EppEntity::TYPE_EppResponseDataTransferSvcsub;
	};

	/**
	 * Gets the id of the EPP Svcsub object
	 */
	DOMString getId()
	{
		return this->id;
	};

	/**
	 * Sets the id of the EPP Svcsub object
	 */
	void setId( DOMString id )
	{
		this->id = id;
	};

	/**
	 * Gets the new expiration date of the subscription object after the
	 * transfer, or null if the transfer request does not change the
	 * expiration date of the subscription object.
	 */
	time_t getDateExpired()
	{
		return this->exDate;
	};

	/**
	 * Sets the new expiration date of the subscription object after the
	 * transfer. The value of the new expiration date is optional.
	 */
	void setDateExpired( time_t date )
	{
		this->exDate = date;
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
	 * Converts the <code>EppResponseDataTransferSvcsub</code> object into
	 * an XML element
	 *
	 * @param doc the XML <code>DOMElement</code> object
	 * @param tag the tag/element name for the
	 *            <code>EppResponseDataTransferSvcsub</code> object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );

	/**
	 * Converts an XML element into an
	 * <code>EppResponseDataTransferSvcsub</code> object. The caller of
	 * this method must make sure that the root node is of an EPP Response
	 * Transfer entity for EPP Svcsub object.
	 *
	 * @param root root node for an
	 *             <code>EppResponseDataTransferSvcsub</code>
	 *             object in XML format
	 *
	 * @return an <code>EppResponseDataTransferSvcsub</code> object, or null
	 *         if the node is invalid
	 */
	static EppResponseDataTransferSvcsub * fromXML( const DOMNode& root );
};

#endif     /* EPPRESPONSEDATATRANSFERSVCSUB_HPP */  /* } */
