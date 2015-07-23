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

#if ! defined(EPPCONTACTTYPE_HPP)    /* { */
#define       EPPCONTACTTYPE_HPP        1

#include "EppEntity.hpp"

/**
 * This <code>EppContactType</code> class implements EPP Contact Type entity.
 * Currently, only EPP Domain objects have contact types defined:
 *
 * <UL>
 * <LI><B>EPP Domain Object</B><UL>
 *     <LI>billing</LI>
 *     <LI>admin</LI>
 *     <LI>tech</LI>
 *     </UL></LI>
 * </UL>
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:37 $
 */
class EPP_EXPORT EppContactType : public EppEntity
{
private:
	DOMString id;
	DOMString type;

public:

	/**
	 * Creates an <code>EppContactType</code> object
	 */
	EppContactType()
	{
		this->id = NULLXS;
		this->type = NULLXS;
	};

	/**
	 * Creates an <code>EppContactType</code> object with the id of
	 * a contact object and a type string
	 */
	EppContactType( DOMString id, DOMString type )
	{
		this->id = id;
		this->type = type;
	};

	/**
	 * Destructor
	 */
	~EppContactType() {};

	/**
	 * Gets the id of the contact
	 */
	DOMString getId()
	{
		return this->id;
	};

	/**
	 * Sets the id of the contact
	 */
	void setId( DOMString id )
	{
		this->id = id;
	};

	/**
	 * Gets the type of the contact
	 */
	DOMString getType()
	{
		return this->type;
	};

	/**
	 * Sets the type of the contact
	 */
	void setType( DOMString type )
	{
		this->type = type;
	};

	/**
	 * Gets a sub-list of contact ids, given a list of contact types
	 * and a type string
	 *
	 * @param contact a list of EppContactType objects
	 * @param type the type of contacts to be extracted from the list
	 *
	 * @return a list of contact ids, or null if not found
	 * @note the caller must free the returned list if not null
	 */
	static OldValueVectorOf<DOMString> * getContactId( OldValueVectorOf<EppContactType> * contact, DOMString type );

	/**
	 * Converts the <code>EppContactType</code> object into an XML element
	 *
	 * @param doc the XML <code>DOMElement</code> object
	 * @param tag the tag/element name for the <code>EppContactType</code>
	 *            object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );

	/**
	 * Converts an XML element into an <code>EppContactType</code> object.
	 * The caller of this method must make sure that the root node is of
	 * the EPP Contact Type type.
	 *
	 * @param root root node for an <code>EppContactType</code> object
	 *             in XML format
	 *
	 * @return an <code>EppContactType</code> object, or null if the node
	 *         is invalid
	 */
	static EppContactType * fromXML( const DOMNode& root );

	DOMString toString()
	{
		return EppEntity::toString(DOMString("contact"));
	};
};

#endif     /* EPPCONTACTTYPE_HPP */  /* } */
