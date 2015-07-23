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

#if ! defined(EPPCOMMANDDELETEHOST_HPP)    /* { */
#define       EPPCOMMANDDELETEHOST_HPP        1

#include "EppCommandDelete.hpp"

/**
 * This <code>EppCommandDeleteHost</code> class implements EPP Command Delete
 * entity for EPP Host objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:37 $
 */
class EPP_EXPORT EppCommandDeleteHost : public EppCommandDelete
{
private:
	DOMString name;

public:

	/**
	 * Creates an <code>EppCommandDeleteHost</code> object
	 */
	EppCommandDeleteHost()
	{
		this->name = NULLXS;
	};

	/**
	 * Creates an <code>EppCommandDeleteHost</code> object for
	 * deleting a host object based on its name
	 */
	EppCommandDeleteHost( DOMString name )
	{
		this->name = name;
	};

	/**
	 * Creates an <code>EppCommandDeleteHost</code> object for
	 * deleting a host object based on its name, given a client
	 * transaction id associated with the operation
	 */
	EppCommandDeleteHost( DOMString name, DOMString xid )
	{
		this->name = name;
		this->clTRID = xid;
	};

	/**
	 * Destructor
	 */
	~EppCommandDeleteHost() {};

	/**
	 * Returns the run-time type of an EppEntity object
	 */
	virtual int getEntityType()
	{
		return EppEntity::TYPE_EppCommandDeleteHost;
	};

	/**
	 * Gets the name of the host object to be deleted
	 */
	DOMString getName()
	{
		return this->name;
	};

	/**
	 * Sets the name of the host object to be deleted
	 */
	void setName( DOMString name )
	{
		this->name = name;
	};

	/**
	 * Converts the <code>EppCommandDeleteHost</code> object into an XML
	 * element
	 *
	 * @param doc the XML <code>DOMElement</code> object
	 * @param tag the tag/element name for the
	 *            <code>EppCommandDeleteHost</code> object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );

	/**
	 * Converts an XML element into an <code>EppCommandDeleteHost</code>
	 * object. The caller of this method must make sure that the root node
	 * is of an EPP Command Delete entity for EPP host object.
	 *
	 * @param root root node for an <code>EppCommandDeleteHost</code>
	 *             object in XML format
	 *
	 * @return an <code>EppCommandDeleteHost</code> object, or null
	 *         if the node is invalid
	 */
	static EppCommandDeleteHost * fromXML( const DOMNode& root );
};

#endif     /* EPPCOMMANDDELETEHOST_HPP */  /* } */
