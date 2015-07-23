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

#if ! defined(EPPE164_HPP)    /* { */
#define       EPPE164_HPP        1

#include "EppEntity.hpp"

/**
 * This <code>EppE164</code> class implements EPP e164Type, which is
 * the ITU E.164 format for telephone numbers, plus extension.
 *
 * <P>An E.164 telephone number consists of 4 parts, with a maximum length
 * of 17 characters:
 * <UL>
 * <LI>A leading plus ("+") sign,</LI>
 * <LI>A up to 3-dight country code,</LI>
 * <LI>A period (".") sign</LI>
 * <LI>A up to 12-dight phoner number (for EPP-04, it is a 10-digit, and is extended by NeuLevel to 12)</LI>
 * </UL>
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:37 $
 */
class EPP_EXPORT EppE164 : public EppEntity
{
private:
	DOMString number;
	DOMString extension;

public:

	/**
	 * Creates an <code>EppE164</code> object
	 */
	EppE164()
	{
		this->number    = null;
		this->extension = null;
	};

	/**
	 * Creates an <code>EppE164</code> object without an extension
	 */
	EppE164( DOMString number )
	{
		this->number    = number;
		this->extension = null;
	};

	/**
	 * Creates an <code>EppE164</code> object with an extension
	 */
	EppE164( DOMString number, DOMString extension )
	{
		this->number    = number;
		this->extension = extension;
	};

	/**
	 * Destructor
	 */
	~EppE164() {};

	/**
	 * Gets the phone number
	 */
	DOMString getNumber()
	{
		return this->number;
	};

	/**
	 * Sets the phone number
	 */
	void setNumber( DOMString number )
	{
		this->number = number;
	};

	/**
	 * Gets the extension of the phone number, if any
	 */
	DOMString getExtension()
	{
		return this->extension;
	};

	/**
	 * Sets the extension of the phone number
	 */
	void setExtension( DOMString extension )
	{
		this->extension = extension;
	};

	/**
	 * Converts the <code>EppE164</code> object into an XML element
	 *
	 * @param doc the XML <code>DOMElement</code> object
	 * @param tag the tag/element name for the <code>EppE164</code> object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );

	/**
	 * Converts an XML element into an <code>EppE164</code> object.
	 * The caller of this method must make sure that the root node is of
	 * the EPP E164 type.
	 *
	 * @param root root node for an <code>EppE164</code> object
	 *             in XML format
	 *
	 * @return an <code>EppE164</code> object, or null if the node
	 *         is invalid
	 */
	static EppE164 * fromXML( const DOMNode& root );

	DOMString toString()
	{
		return EppEntity::toString(DOMString("e164"));
	};
};

#endif     /* EPPE164_HPP */  /* } */
