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

#if ! defined(EPPRESPONSEDATACREATEDOMAIN_HPP)    /* { */
#define       EPPRESPONSEDATACREATEDOMAIN_HPP        1

#include <time.h>
#include "EppResponseDataCreate.hpp"

/**
 * This <code>EppResponseDataCreateDomain</code> class implements EPP
 * Response Data entity for EPP Command Create of EPP Domain objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
class EPP_EXPORT EppResponseDataCreateDomain : public EppResponseDataCreate
{
private:
	DOMString name;
	time_t    exDate;

public:

	/**
	 * Creates an <code>EppResponseDataCreateDomain</code> object
	 */
	EppResponseDataCreateDomain()
	{
		this->name = null;
		this->roid = null;
		this->exDate = 0;
	};

	/**
	 * Creates an <code>EppResponseDataCreateDomain</code> object
	 *
	 * @param name the name of the <code>EppDomain</code> object created
	 */
	EppResponseDataCreateDomain( DOMString name )
	{
		this->name = name;
		this->roid = null;
		this->exDate = 0;
		this->crDate = time(0);
	};

	/**
	 * Creates an <code>EppResponseDataCreateDomain</code> object
	 *
	 * @param name   the name of the <code>EppDomain</code> object created
	 * @param exDate the expiration date of the <code>EppDomain</code>
	 *               object created
	 */
	EppResponseDataCreateDomain( DOMString name, time_t exDate )
	{
		this->name = name;
		this->roid = null;
		this->exDate = exDate;
		this->crDate = time(0);
	};

	/**
	 * Creates an <code>EppResponseDataCreateDomain</code> object
	 *
	 * @param name the name of the <code>EppDomain</code> object created
	 * @param exDate the expiration date of the <code>EppDomain</code>
	 *               object created
	 * @param crDate the date of the <code>EppDomain</code> object was created
	 */
	EppResponseDataCreateDomain( DOMString name, time_t exDate, time_t crDate )
	{
		this->name = name;
		this->roid = null;
		this->exDate = exDate;
		this->crDate = crDate;
	};

	/**
	 * Creates an <code>EppResponseDataCreateDomain</code> object
	 *
	 * @param name the name of the <code>EppDomain</code> object created
	 * @param roid the ROID of the <code>EppDomain</code> object created
	 *
	 * @deprecated for EPP-04 (06/29/2001)
	 */
	EppResponseDataCreateDomain( DOMString name, DOMString roid )
	{
		this->name = name;
		this->roid = roid;
		this->exDate = 0;
		this->crDate = 0;
	};

	/**
	 * Creates an <code>EppResponseDataCreateDomain</code> object
	 *
	 * @param name   the name of the <code>EppDomain</code> object created
	 * @param roid   the ROID of the <code>EppDomain</code> object created
	 * @param exDate the expiration date of the <code>EppDomain</code>
	 *               object created
	 *
	 * @deprecated for EPP-04 (06/29/2001)
	 */
	EppResponseDataCreateDomain( DOMString name, DOMString roid, time_t exDate )
	{
		this->name = name;
		this->roid = roid;
		this->exDate = exDate;
		this->crDate = 0;
	};

	/**
	 * Destructor
	 */
	~EppResponseDataCreateDomain() {};

	/**
	 * Returns the run-time type of an EppEntity object
	 */
	virtual int getEntityType()
	{
		return EppEntity::TYPE_EppResponseDataCreateDomain;
	};

	/**
	 * Gets the name of the domain created
	 */
	DOMString getName()
	{
		return this->name;
	};

	/**
	 * Sets the name of the domain created
	 */
	void setName( DOMString name )
	{
		this->name = name;
	};

	/**
	 * Gets expiration date of the domain created
	 */
	time_t getDateExpired()
	{
		return this->exDate;
	};

	/**
	 * Sets expiration date of the domain created
	 */
	void setDateExpired( time_t exDate )
	{
		this->exDate = exDate;
	};

	/**
	 * Converts an XML element into an
	 * <code>EppResponseDataCreateDomain</code> object. The caller of this
	 * method must make sure that the root node is the resData element of
	 * EPP responseType for creating an EPP Domain object
	 *
	 * @param root root node for an <code>EppResponseDataCreateDomain</code>
	 *             object in XML format
	 *
	 * @return an <code>EppResponseDataCreateDomain</code> object,
	 *         or null if the node is invalid
	 */
	static EppResponseDataCreateDomain * fromXML( const DOMNode& root );

	/**
	 * Converts an <code>EppResponseDataCreateDomain</code> object
	 * into an XML element.
	 *
	 * @param doc the XML <code>DOMElement</code> object
	 * @param tag the tag/element name for the
	 *        <code>EppResponseDataCreateDomain</code> object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );
};

#endif     /* EPPRESPONSEDATACREATEDOMAIN_HPP */  /* } */
