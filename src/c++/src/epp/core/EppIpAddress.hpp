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

#if ! defined(EPPIPADDRESS_HPP)    /* { */
#define       EPPIPADDRESS_HPP        1

#include "EppEntity.hpp"

/**
 * This <code>EppIpAddress</code> class implements EPP IP Address entity.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:37 $
 */
class EPP_EXPORT EppIpAddress : public EppEntity
{
private:
	DOMString ip;
	DOMString address;

public:
	/**
	 * IP address type for "v4"
	 */
	static const char * TYPE_V4;

	/**
	 * IP address type for "v6"
	 */
	static const char * TYPE_V6;

	/**
	 * Creates an <code>EppAddress</code> object
	 */
	EppIpAddress()
	{
		this->address = NULLXS;
		this->ip = NULLXS;
	};

	/**
	 * Creates an <code>EppAddress</code> object with the default
	 * address type, "v4"
	 */
	EppIpAddress( DOMString address )
	{
		this->address = address;
		this->ip = NULLXS;
	};

	/**
	 * Creates an <code>EppAddress</code> object with an address type,
	 * either "v4" (default) or "v6"
	 */
	EppIpAddress( DOMString address, DOMString type )
	{
		this->address = address;
		this->ip = type;
	};

	/**
	 * Destructor
	 */
	~EppIpAddress() {};

	/**
	 * Gets the IP address type, either "v4" or "v6"
	 */
	DOMString getType()
	{
		return this->ip;
	};

	/**
	 * Sets the IP address type, either "v4" or "v6"
	 */
	void setType( DOMString type )
	{
		this->ip = type;
	};

	/**
	 * Gets the IP address
	 */
	DOMString getAddress()
	{
		return this->address;
	};

	/**
	 * Sets the IP address
	 */
	void setAddress( DOMString address )
	{
		this->address = address;
	};

	/**
	 * Converts the <code>EppIpAddress</code> object into an XML element
	 *
	 * @param doc the XML <code>DOMElement</code> object
	 * @param tag the tag/element name for the <code>EppIpAddress</code>
	 *              object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );

	/**
	 * Converts an XML element into an <code>EppIpAddress</code> object.
	 * The caller of this method must make sure that the root node is of
	 * the EPP IP Address type.
	 *
	 * @param root root node for an <code>EppIpAddress</code> object
	 *             in XML format
	 *
	 * @return an <code>EppIpAddress</code> object, or null if the node
	 *         is invalid
	 */
	static EppIpAddress * fromXML( const DOMNode& root );

	DOMString toString()
	{
		return EppEntity::toString(DOMString("address"));
	};
};

#endif     /* EPPIPADDRESS_HPP */  /* } */
