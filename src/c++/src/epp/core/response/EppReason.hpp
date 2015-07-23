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

#if ! defined(EPPREASON_HPP)
#define	      EPPREASON_HPP	   1

#include "EppEntity.hpp"

/**
 * This <code>EppReason</code> class implements EPP Reason with
 * language attribute.
 */

class EppReason : public EppEntity
{
public:

	/**
	 * Creates an <code>EppReason</code> object
	 */
	EppReason()
	{
		this->reason	= NULLXS;
		this->language	= NULLXS;
	};

	/**
	 * Creates an <code>EppReason</code> object with a string value and a language text
	 */
	EppReason( const DOMString& reason, const DOMString& lang)
	{
		this->reason	= reason;
		this->language	= lang;
	};

	/**
	 * Creates an <code>EppReason</code> object with a string value 
	 */
	EppReason( const DOMString& reason )
	{
		this->reason	= reason;
		this->language	= NULLXS;
	};

	/**
	 * Destructor
	 */
	virtual ~EppReason() {};

	/**
	 * Gets the reason
	 */
	DOMString getReason() const
	{
		return this->reason;
	};

	/**
	 * Sets the reason
	 */
	void setReason( const DOMString& reason )
	{
		this->reason = reason;
	};

	/**
	 * Gets language type of the reason message
	 */
	DOMString getLanguage() const
	{
		return this->language;
	};

	/**
	 * Sets language type of the reason message
	 */
	void setLanguage( const DOMString& language )
	{
		this->language = language;
	};

	/**
	 * Converts the <code>EppReason</code> object into an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the <code>EppReason</code> object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );

	/**
	 * Converts an XML element into an <code>EppReason</code> object. 
	 *
	 * @param root root node for an <code>EppReason</code> object in XML format
	 *
	 * @return an <code>EppReason</code> object, or null if the node is invalid
	 */
	static EppReason * fromXML( const DOMNode& root );

	DOMString toString()
	{
		return EppEntity::toString(DOMString("reason"));
	};

private:
	DOMString	reason;
	DOMString	language;
};

#endif
