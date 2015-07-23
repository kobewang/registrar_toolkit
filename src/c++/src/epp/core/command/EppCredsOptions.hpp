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

#if ! defined(EPPCREDSOPTIONS_HPP)    /* { */
#define       EPPCREDSOPTIONS_HPP        1

#include "EppEntity.hpp"

/**
 * This <code>EppCredsOptions</code> class implements EPP credsOptionsType
 * entity.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
class EPP_EXPORT EppCredsOptions : public EppEntity
{
private:
	DOMString version;
	DOMString lang;

public:

	/**
	 * Creates an <code>EppCredsOptions</code> object
	 */
	EppCredsOptions()
	{
		this->version = NULLXS;
		this->lang = NULLXS;
	};

	/**
	 * Creates an <code>EppCredsOptions</code> object, given the version
	 * string and language id
	 *
	 * @param version verion string
	 * @param language language id
	 */
	EppCredsOptions( DOMString version, DOMString language )
	{
		this->version = version;
		this->lang = language;
	};

	/**
	 * Destructor
	 */
	~EppCredsOptions() {};

	/**
	 * Gets version string
	 */
	DOMString getVersion()
	{
		return this->version;
	};

	/**
	 * Sets version string
	 */
	void setVersion( DOMString version )
	{
		this->version = version;
	};

	/**
	 * Gets language id
	 */
	DOMString getLanguage()
	{
		return this->lang;
	};

	/**
	 * Sets language id
	 */
	void setLanguage( DOMString language )
	{
		this->lang = language;
	};

	/**
	 * Converts the <code>EppCredsOptions</code> object into an XML element
	 *
	 * @param doc the XML <code>DOMElement</code> object
	 * @param tag the tag/element name for the <code>EppCredsOptions</code>
	 *            object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );

	/**
	 * Converts an XML element into an <code>EppCredsOptions</code> object.
	 * The caller of this method must make sure that the root node is of the
	 * EPP credsOptionsType.
	 *
	 * @param root root node for an <code>EppCredsOptions</code> object in
	 *             XML format
	 *
	 * @return an <code>EppCredsOptions</code> object, or null if the node
	 *         is invalid
	 */
	static EppCredsOptions * fromXML( const DOMNode& root );

	/**
	 * Converts the <code>EppCredsOptions</code> object into plain XML text
	 * string by using the default root tag name
	 *
	 * @return a text string representing the <code>EppCredsOptions</code>
	 *         object in XML format
	 */
	DOMString toString()
	{
		return EppEntity::toString(DOMString("options"));
	};
};

#endif     /* EPPCREDSOPTIONS_HPP */  /* } */
