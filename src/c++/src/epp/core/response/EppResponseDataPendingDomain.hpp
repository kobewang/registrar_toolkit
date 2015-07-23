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

#if ! defined(EPPRESPONSEDATAPENDINGDOMAIN_HPP)		/* { */
#define	      EPPRESPONSEDATAPENDINGDOMAIN_HPP		   1

#include "EppResponseDataPending.hpp"

/**
 * This <code>EppResponseDataPendingDomain</code> class implements EPP
 * Response Data entity for EPP Pending Actions of EPP Domain objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
class EPP_EXPORT EppResponseDataPendingDomain : public EppResponseDataPending
{
private:
	DOMString   name;

public:

	/**
	 * Creates an <code>EppResponseDataPendingDomain</code> object
	 *
	 * @param name the name of the <code>EppDomain</code> object associated with the pending action
	 */
	EppResponseDataPendingDomain( DOMString name )
	{
		this->name = name;
		this->paResult = false;
		this->paTRID.setClientTransactionId(NULLXS);
		this->paTRID.setServiceTransactionId(NULLXS);
		this->paDate = 0;
	};

	/**
	 * Creates an <code>EppResponseDataPendingDomain</code> object
	 *
	 * @param name the name of the <code>EppDomain</code> object associated with the pending action
	 * @param result the boolean flag indicating if the pending action is a success or a failure
	 */
	EppResponseDataPendingDomain( DOMString name, bool result )
	{
		this->name = name;
		this->paResult = result;
		this->paTRID.setClientTransactionId(NULLXS);
		this->paTRID.setServiceTransactionId(NULLXS);
		this->paDate = 0;
	};

	/**
	 * Destructor
	 */
	~EppResponseDataPendingDomain() {};

	/**
	 * Gets the name of the domain object associated with the pending action
	 */
	DOMString getName()
	{
		return this->name;
	};

	/**
	 * Sets the name of the domain object associated with the pending action
	 */
	void setName( DOMString name )
	{
		this->name = name;
	};

	/**
	 * Returns the run-time type of an EppEntity object
	 */
	virtual int getEntityType()
	{
		return EppEntity::TYPE_EppResponseDataPendingDomain;
	};

	/**
	 * Converts an XML element into an
	 * <code>EppResponseDataPendingDomain</code> object. The caller of this
	 * method must make sure that the root node is the resData element of
	 * EPP responseType for creating an EPP Domain object
	 *
	 * @param root root node for an <code>EppResponseDataPendingDomain</code>
	 *             object in XML format
	 *
	 * @return an <code>EppResponseDataPendingDomain</code> object,
	 *         or null if the node is invalid
	 */
	static EppResponseDataPendingDomain * fromXML( const DOMNode& root );

	/**
	 * Converts an <code>EppResponseDataPendingDomain</code> object
	 * into an XML element.
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the
	 *        <code>EppResponseDataPendingDomain</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );
};

#endif	/*    EPPRESPONSEDATAPENDINGDOMAIN_HPP */	/* } */
