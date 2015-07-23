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

#if ! defined(EPPAUTHINFO_HPP)    /* { */
#define       EPPAUTHINFO_HPP        1

#include "EppEntity.hpp"

/**
 * This <code>EppAuthInfo</code> class implements EPP AuthInfo entity.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:37 $
 */
class EPP_EXPORT EppAuthInfo : public EppEntity
{
private:
	DOMString type;
	DOMString roid;
	DOMString value;

public:
	/**
	 * Authorization type "pw" for password
	 */
	static const char * TYPE_PW;
	/**
	 * Authorization type "ext" for extension (Not implemented yet)
	 */
	static const char * TYPE_EXT;

	/**
	 * Creates an <code>EppAuthInfo</code> object
	 */
	EppAuthInfo()
	{
		this->type = null;
		this->value = null;
		this->roid = null;
	};

	/**
	 * Creates an <code>EppAuthInfo</code> object
	 *
	 * @note the only type value is "pw", for password
	 */
	EppAuthInfo( DOMString type, DOMString value )
	{
		this->type = type;
		this->value = value;
		this->roid = null;
	};

	/**
	 * Creates an <code>EppAuthInfo</code> object with a ROID.
	 *
	 * @note the only type value is "pw", for password
	 */
	EppAuthInfo( DOMString type, DOMString value, DOMString roid )
	{
		this->type = type;
		this->value = value;
		this->roid = roid;
	};

	/**
	 * Destructor
	 */
	~EppAuthInfo() {};

	/**
	 * Gets the value associated with the authorization information
	 */
	DOMString getValue()
	{
		return this->value;
	};

	/**
	 * Gets the value associated with the authorization information
	 */
	void setValue( DOMString value )
	{
		this->value = value;
	};

	/**
	 * Gets the ROID of the authorization information
	 */
	DOMString getRoid()
	{
		return this->roid;
	};

	/**
	 * Sets the ROID of the authorization information
	 */
	void setRoid( DOMString roid )
	{
		this->roid = roid;
	};

	/**
	 * Gets the type of the authorization information
	 */
	DOMString getType()
	{
		return this->type;
	};

	/**
	 * Sets the type of the authorization information
	 */
	void setType( DOMString type )
	{
		this->type = type;
	};

	/**
	 * Converts the <code>EppAuthInfo</code> object into an XML element
	 *
	 * @param doc the XML <code>DOMElement</code> object
	 * @param tag the tag/element name for the <code>EppAuthInfo</code>
	 *            object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );

	/**
	 * Converts an XML element into an <code>EppAuthInfo</code> object.
	 * The caller of this method must make sure that the root node is of
	 * the EPP AuthInfo type.
	 *
	 * @param root root node for an <code>EppAuthInfo</code> object
	 *             in XML format
	 *
	 * @return an <code>EppAuthInfo</code> object, or null if the node
	 *         is invalid
	 */
	static EppAuthInfo * fromXML( const DOMNode& root );

	DOMString toString()
	{
		return EppEntity::toString(DOMString("authInfo"));
	};
};

#endif     /* EPPAUTHINFO_HPP */  /* } */
