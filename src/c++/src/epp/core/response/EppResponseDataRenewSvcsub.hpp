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

#if ! defined(EPPRESPONSEDATARENEWSVCSUB_HPP)    /* { */
#define       EPPRESPONSEDATARENEWSVCSUB_HPP        1

#include <time.h>
#include "EppResponseDataRenew.hpp"

/**
 * This <code>EppResponseDataRenewSvcsub</code> class implements EPP
 * Response Data entity for EPP Command Renew of EPP Svcsub objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $
 */
class EPP_EXPORT EppResponseDataRenewSvcsub : public EppResponseDataRenew
{
private:
	DOMString id;
	time_t    exDate;
	DOMString service;

public:

	/**
	 * Renews an <code>EppResponseDataRenewSvcsub</code> object
	 */
	EppResponseDataRenewSvcsub()
	{
		this->id   = null;
		this->roid = null;
		this->exDate = 0;
		this->service = null;
	};

	/**
	 * Renews an <code>EppResponseDataRenewSvcsub</code> object
	 *
	 * @param id the id of the <code>EppSvcsub</code> object renewed
	 */
	EppResponseDataRenewSvcsub( DOMString id )
	{
		this->id   = id;
		this->roid = null;
		this->exDate = 0;
		this->service = null;
	};

	/**
	 * Renews an <code>EppResponseDataRenewSvcsub</code> object
	 *
	 * @param id     the id of the <code>EppSvcsub</code> object renewed
	 * @param exDate the expiration date of the <code>EppSvcsub</code>
	 *               object renewed
	 */
	EppResponseDataRenewSvcsub( DOMString id, time_t exDate )
	{
		this->id   = id;
		this->roid = null;
		this->exDate = exDate;
		this->service = null;
	};

	/**
	 * Renews an <code>EppResponseDataRenewSvcsub</code> object
	 *
	 * @param id   the id of the <code>EppSvcsub</code> object renewed
	 * @param roid the ROID of the <code>EppSvcsub</code> object renewed
	 *
	 * @deprecated for EPP-04 (06/29/2001)
	 */
	EppResponseDataRenewSvcsub( DOMString id, DOMString roid )
	{
		this->id   = id;
		this->roid = roid;
		this->exDate = 0;
		this->service = null;
	};

	/**
	 * Renews an <code>EppResponseDataRenewSvcsub</code> object
	 *
	 * @param id     the id of the <code>EppSvcsub</code> object renewed
	 * @param roid   the ROID of the <code>EppSvcsub</code> object renewed
	 * @param exDate the expiration date of the <code>EppSvcsub</code>
	 *               object renewed
	 *
	 * @deprecated for EPP-04 (06/29/2001)
	 */
	EppResponseDataRenewSvcsub( DOMString id, DOMString roid, time_t exDate )
	{
		this->id   = id;
		this->roid = roid;
		this->exDate = exDate;
		this->service = null;
	};

	/**
	 * Destructor
	 */
	~EppResponseDataRenewSvcsub() {};

	/**
	 * Returns the run-time type of an EppEntity object
	 */
	virtual int getEntityType()
	{
		return EppEntity::TYPE_EppResponseDataRenewSvcsub;
	};

	/**
	 * Gets the id of the subscription object renewed
	 */
	DOMString getId()
	{
		return this->id;
	};

	/**
	 * Sets the id of the subscription object renewed
	 */
	void setId( DOMString id )
	{
		this->id = id;
	};

	/**
	 * Gets expiration date of the subscription object renewed
	 */
	time_t getDateExpired()
	{
		return this->exDate;
	};

	/**
	 * Sets expiration date of the subscription object renewed
	 */
	void setDateExpired( time_t exDate )
	{
		this->exDate = exDate;
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
	 * Converts an XML element into an
	 * <code>EppResponseDataRenewSvcsub</code> object. The caller of this
	 * method must make sure that the root node is the resData element of
	 * EPP responseType for creating an EPP Svcsub object
	 *
	 * @param root root node for an <code>EppResponseDataRenewSvcsub</code>
	 *             object in XML format
	 *
	 * @return an <code>EppResponseDataRenewSvcsub</code> object,
	 *         or null if the node is invalid
	 */
	static EppResponseDataRenewSvcsub * fromXML( const DOMNode& root );

	/**
	 * Converts an <code>EppResponseDataRenewSvcsub</code> object
	 * into an XML element.
	 *
	 * @param doc the XML <code>DOMElement</code> object
	 * @param tag the tag/element name for the
	 *        <code>EppResponseDataRenewSvcsub</code> object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );
};

#endif     /* EPPRESPONSEDATARENEWSVCSUB_HPP */  /* } */
