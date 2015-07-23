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

#if ! defined(EPPCOMMANDINFOHOST_HPP)    /* { */
#define       EPPCOMMANDINFOHOST_HPP        1

#include "EppCommandInfo.hpp"

/**
 * This <code>EppCommandInfoHost</code> class implements EPP Command Info
 * entity for EPP Host objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:37 $
 */
class EPP_EXPORT EppCommandInfoHost : public EppCommandInfo
{
private:
	DOMString name;

public:

	/**
	 * Creates an <code>EppCommandInfoHost</code> object
	 */
	EppCommandInfoHost()
	{
		this->name = NULLXS;
	};

	/**
	 * Creates an <code>EppCommandInfoHost</code> object for
	 * querying a host object based on its name
	 */
	EppCommandInfoHost( DOMString name )
	{
		this->name = name;
	};

	/**
	 * Creates an <code>EppCommandInfoHost</code> object for
	 * querying a host object based on its name, given a client
	 * transaction id associated with the operation
	 */
	EppCommandInfoHost( DOMString name, DOMString xid )
	{
		this->name = name;
		this->clTRID = xid;
	};

	/**
	 * Destructor
	 */
	~EppCommandInfoHost() {};

	/**
	 * Returns the run-time type of an EppEntity object
	 */
	virtual int getEntityType()
	{
		return EppEntity::TYPE_EppCommandInfoHost;
	};

	/**
	 * Gets the name of the host object to be queried
	 */
	DOMString getName()
	{
		return this->name;
	};

	/**
	 * Sets the name of the host object to be queried
	 */
	void setName( DOMString name )
	{
		this->name = name;
	};

	/**
	 * Converts the <code>EppCommandInfoHost</code> object into an XML
	 * element
	 *
	 * @param doc the XML <code>DOMElement</code> object
	 * @param tag the tag/element name for the
	 *            <code>EppCommandInfoHost</code> object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );

	/**
	 * Converts an XML element into an <code>EppCommandInfoHost</code>
	 * object. The caller of this method must make sure that the root node
	 * is of an EPP Command Info entity for EPP host object.
	 *
	 * @param root root node for an <code>EppCommandInfoHost</code>
	 *             object in XML format
	 *
	 * @return an <code>EppCommandInfoHost</code> object, or null
	 *         if the node is invalid
	 */
	static EppCommandInfoHost * fromXML( const DOMNode& root );
};

#endif     /* EPPCOMMANDINFOHOST_HPP */  /* } */
