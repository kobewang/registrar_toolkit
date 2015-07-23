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

#if ! defined(EPPRESPONSEDATAPENDING_HPP)	/* { */
#define	      EPPRESPONSEDATAPENDING_HPP	   1

#include <time.h>
#include "EppResponseData.hpp"
#include "EppTransactionId.hpp"

/**
 * This <code>EppResponseDataPending</code> class implements EPP
 * Response Data entity for EPP Pending Actions on EPP objects.
 *
 * @since EPP-1.0
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
class EPP_EXPORT EppResponseDataPending : public EppResponseData
{
protected:
	/**
	 * The result of the pending action, true for success, false for failure
	 */
	bool             paResult;

	/**
	 * The transaction id associated with the pending action
	 */
	EppTransactionId paTRID;

	/**
	 * The date when the pending action is processed
	 */
	time_t           paDate;

	/**
	 * Converts shared <code>EppResponseDataPending</code> component into
	 * XML elements
	 *
	 * @param doc the XML <code>DOMDocument</code> object
	 * @param body the XML <code>DOMElement</code> to be attached
	 */
	void toXMLCommon( DOMDocument& doc, DOMElement& body );

	/**
	 * Converts shared <code>EppResponseDataPending</code> components from
	 * XML format.
	 * 
	 * @param node the node of a shared component
	 * @param name the name of the node of a shared component
	 * @param ns   the object namespace of a shared component
	 */
	void fromXMLCommon( const DOMNode& node, const DOMString& name, const DOMString& ns );

public:

	/**
	 * Constructor
	 */
	EppResponseDataPending()
	{
		this->paResult	= false;
		this->paDate	= 0;
		this->paTRID.setClientTransactionId(NULLXS);
		this->paTRID.setServiceTransactionId(NULLXS);
	};

	/**
	 * Destructor
	 */
	virtual ~EppResponseDataPending() {};

	/**
	 * Gets the pending action result, true for success, false for failure
	 */
	bool getResult()
	{
		return this->paResult;
	};

	/**
	 * Gets the pending action result, true for success, false for failure
	 */
	void setResult( bool paResult )
	{
		this->paResult = paResult;
	};

	/**
	 * Gets the transaction id asssociated with the pending action
	 */
	EppTransactionId getTransactionId()
	{
		return this->paTRID;
	};

	/**
	 * Sets the transaction id asssociated with the pending action
	 */
	void setTransactionId( EppTransactionId paTRID )
	{
		this->paTRID = paTRID;
	};

	/**
	 * Gets the date when the pending action is processed
	 */
	time_t getDate()
	{
		return this->paDate;
	};

	/**
	 * Sets the date when the pending action is processed
	 */
	void setDate( time_t paDate )
	{
		this->paDate = paDate;
	};
};

#endif	/*    EPPRESPONSEDATAPENDING_HPP */	/* } */
