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

#if ! defined(EPPRESPONSEDATAPENDINGSVCSUB_HPP)	/* { */
#define	      EPPRESPONSEDATAPENDINGSVCSUB_HPP		   1

#include "EppResponseDataPending.hpp"

/**
 * This <code>EppResponseDataPendingSvcsub</code> class implements EPP
 * Response Data entity for EPP Pending Actions of EPP Svcsub objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
class EPP_EXPORT EppResponseDataPendingSvcsub : public EppResponseDataPending
{
private:
	DOMString   id;
	DOMString   service;

public:

	/**
	 * Creates an <code>EppResponseDataPendingSvcsub</code> object
	 *
	 * @param id the id of the <code>EppSvcsub</code> object associated with the pending action
	 */
	EppResponseDataPendingSvcsub( DOMString id )
	{
		this->id = id;
		this->service = NULLXS;
		this->paResult = false;
		this->paTRID.setClientTransactionId(NULLXS);
		this->paTRID.setServiceTransactionId(NULLXS);
		this->paDate = 0;
	};

	/**
	 * Creates an <code>EppResponseDataPendingSvcsub</code> object
	 *
	 * @param id the id of the <code>EppSvcsub</code> object associated with the pending action
	 * @param result the boolean flag indicating if the pending action is a success or a failure
	 */
	EppResponseDataPendingSvcsub( DOMString id, bool result )
	{
		this->id = id;
		this->service = NULLXS;
		this->paResult = result;
		this->paTRID.setClientTransactionId(NULLXS);
		this->paTRID.setServiceTransactionId(NULLXS);
		this->paDate = null;
	};

	/**
	 * Destructor
	 */
	~EppResponseDataPendingSvcsub() {};

	/**
	 * Gets the id of the svcsub object associated with the pending action
	 */
	DOMString getId()
	{
		return this->id;
	};

	/**
	 * Sets the id of the svcsub object associated with the pending action
	 */
	void setId( DOMString id )
	{
		this->id = id;
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
	 * Returns the run-time type of an EppEntity object
	 */
	virtual int getEntityType()
	{
		return EppEntity::TYPE_EppResponseDataPendingSvcsub;
	};

	/**
	 * Converts an XML element into an
	 * <code>EppResponseDataPendingSvcsub</code> object. The caller of this
	 * method must make sure that the root node is the resData element of
	 * EPP responseType for creating an EPP Svcsub object
	 *
	 * @param root root node for an <code>EppResponseDataPendingSvcsub</code>
	 *             object in XML format
	 *
	 * @return an <code>EppResponseDataPendingSvcsub</code> object,
	 *         or NULLXS if the node is invalid
	 */
	static EppResponseDataPendingSvcsub * fromXML( const DOMNode& root );

	/**
	 * Converts an <code>EppResponseDataPendingSvcsub</code> object
	 * into an XML element.
	 *
	 * @param doc the XML <code>DOMDocument</code> object
	 * @param tag the tag/element name for the
	 *        <code>EppResponseDataPendingSvcsub</code> object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );
};

#endif	/*    EPPRESPONSEDATAPENDINGSVCSUB_HPP */	/* } */
