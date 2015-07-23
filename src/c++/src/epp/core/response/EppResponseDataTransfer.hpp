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

#if ! defined(EPPRESPONSEDATATRANSFER_HPP)    /* { */
#define       EPPRESPONSEDATATRANSFER_HPP        1

#include <time.h>
#include "EppResponseData.hpp"

/**
 * This <code>EppResponseDataTransfer</code> class implements EPP Response
 * Data entity for EPP Command Transfer.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
class EPP_EXPORT EppResponseDataTransfer : public EppResponseData
{
protected:
	/**
	 * The status of the transfer operation
	 */
	DOMString   trStatus;
	/**
	 * The id of the client who made the transfer request
	 */
	DOMString   reID;
	/**
	 * The timestamp when the transfer request was made
	 */
	time_t      reDate;
	/**
	 * The id of the client who must act upon the transfer request
	 */
	DOMString   acID;
	/**
	 * The timestamp when the client who must or did act upon the
	 * transfer request
	 */
	time_t      acDate;
	/**
	 * The new expiration date of the EPP Object after transfer, if any
	 */
	time_t      exDate;    // optional, valid only for domain

	/**
	 * Converts shared <code>EppResponseDataTransfer</code> component into
	 * XML elements
	 *
	 * @param doc the XML <code>DOMElement</code> object
	 * @param body the XML <code>DOMElement</code> to be attached
	 */
	void toXMLCommon( DOMDocument& doc, DOMElement& body );

	/**
	 * Converts shared <code>EppResponseDataTransfer</code> components from
	 * XML format.
	 * 
	 * @param node the node of a shared component
	 * @param name the name of the node of a shared component
	 */
	void fromXMLCommon( const DOMNode& node, const DOMString& name );

public:
	/**
	 * Status for transfer - pending
	 */
	static const char * STATUS_PENDING;
	/**
	 * Status for transfer - clientApproved
	 */
	static const char * STATUS_CLIENT_APPROVED;
	/**
	 * Status for transfer - clientCancelled
	 */
	static const char * STATUS_CLIENT_CANCELLED;
	/**
	 * Status for transfer - clientRejected
	 */
	static const char * STATUS_CLIENT_REJECTED;
	/**
	 * Status for transfer - serverApproved
	 */
	static const char * STATUS_SERVER_APPROVED;
	/**
	 * Status for transfer - serverCancelled
	 */
	static const char * STATUS_SERVER_CANCELLED;

	/**
	 * Creates an <code>EppResponseDataTransfer</code> object
	 */
	EppResponseDataTransfer()
	{
		this->trStatus = null;
		this->reID = null;
		this->reDate = 0;
		this->acID = null;
		this->acDate = 0;
		this->exDate = 0;
	};

	/**
	 * Destructor
	 */
	virtual ~EppResponseDataTransfer() {};

	/**
	 * Returns the run-time type of an EppEntity object
	 */
	virtual int getEntityType()
	{
		return EppEntity::TYPE_EppResponseDataTransfer;
	};

	/**
	 * Gets the transfer status
	 */
	DOMString getTransferStatus()
	{
		return this->trStatus;
	};

	/**
	 * Sets the transfer status
	 */
	void setTransferStatus( DOMString status )
	{
		this->trStatus = status;
	};

	/**
	 * Gets the id of the client made the transfer request
	 */
	DOMString getRequestClientId()
	{
		return this->reID;
	};

	/**
	 * Sets the id of the client made the transfer request
	 */
	void setRequestClientId( DOMString clientId )
	{
		this->reID = clientId;
	};

	/**
	 * Gets the date that the transfer request is made
	 */
	time_t getRequestDate()
	{
		return this->reDate;
	};

	/**
	 * Sets the date that the transfer request is made
	 */
	void setRequestDate( time_t date )
	{
		this->reDate = date;
	};

	/**
	 * Gets the id of the client that <i>should</i> act upon the
	 * transfer request
	 */
	DOMString getActionClientId()
	{
		return this->acID;
	};

	/**
	 * Sets the id of the client that <i>should</i> act upon the
	 * transfer request
	 */
	void setActionClientId( DOMString clientId )
	{
		this->acID = clientId;
	};

	/**
	 * Gets the date that the transfer request is required to be acted upon
	 * before an automated response action taken place by the server, if
	 * the request status is still <i>pending</i>; or the date that the
	 * request was completed. 
	 */
	time_t getActionDate()
	{
		return this->acDate;
	};

	/**
	 * Sets the date that the transfer request is required to be acted upon
	 * before an automated response action taken place by the server, if
	 * the request status is still <i>pending</i>; or the date that the
	 * request was completed. 
	 */
	void setActionDate( time_t date )
	{
		this->acDate = date;
	};
};

#endif     /* EPPRESPONSEDATATRANSFER_HPP */  /* } */
