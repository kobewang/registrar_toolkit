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

#if ! defined(EPPRESPONSEDATACREATEHOST_HPP)    /* { */
#define       EPPRESPONSEDATACREATEHOST_HPP        1

#include "EppResponseDataCreate.hpp"

/**
 * This <code>EppResponseDataCreateHost</code> class implements EPP
 * Response Data entity for EPP Command Create of EPP Contact objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
class EPP_EXPORT EppResponseDataCreateHost : public EppResponseDataCreate
{
private:
	DOMString name;

public:

	/**
	 * Creates an <code>EppResponseDataCreateHost</code> object
	 */
	EppResponseDataCreateHost()
	{
		this->name = null;
		this->roid = null;
	};

	/**
	 * Creates an <code>EppResponseDataCreateHost</code> object
	 *
	 * @param name the name of the <code>EppHost</code> object created
	 */
	EppResponseDataCreateHost( DOMString name )
	{
		this->name = name;
		this->roid = null;
		this->crDate = time(0);
	};

	/**
	 * Creates an <code>EppResponseDataCreateHost</code> object
	 *
	 * @param name the name of the <code>EppHost</code> object created
	 * @param crDate the date of the <code>EppHost</code> object was created
	 */
	EppResponseDataCreateHost( DOMString name, time_t crDate )
	{
		this->name = name;
		this->roid = null;
		this->crDate = crDate;
	};

	/**
	 * Creates an <code>EppResponseDataCreateHost</code> object
	 *
	 * @param name the name of the <code>EppHost</code> object created
	 * @param roid the ROID of the <code>EppHost</code> object created
	 *
	 * @deprecated for EPP-04 (06/29/2001)
	 */
	EppResponseDataCreateHost( DOMString name, DOMString roid )
	{
		this->name = name;
		this->roid = roid;
	};

	/**
	 * Destructor
	 */
	~EppResponseDataCreateHost() {};

	/**
	 * Returns the run-time type of an EppEntity object
	 */
	virtual int getEntityType()
	{
		return EppEntity::TYPE_EppResponseDataCreateHost;
	};

	/**
	 * Gets the name of the host created
	 */
	DOMString getName()
	{
		return this->name;
	};

	/**
	 * Sets the name of the host created
	 */
	void setName( DOMString name )
	{
		this->name = name;
	};

	/**
	 * Converts an XML element into an
	 * <code>EppResponseDataCreateHost</code> object. The caller of this
	 * method must make sure that the root node is the resData element of
	 * EPP responseType for creating an EPP Host object
	 *
	 * @param root root node for an <code>EppResponseDataCreateHost</code>
	 *             object in XML format
	 *
	 * @return an <code>EppResponseDataCreateHost</code> object,
	 *         or null if the node is invalid
	 */
	static EppResponseDataCreateHost * fromXML( const DOMNode& root );

	/**
	 * Converts an <code>EppResponseDataCreateHost</code> object
	 * into an XML element.
	 *
	 * @param doc the XML <code>DOMElement</code> object
	 * @param tag the tag/element name for the
	 *        <code>EppResponseDataCreateHost</code> object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );
};

#endif     /* EPPRESPONSEDATACREATEHOST_HPP */  /* } */
