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

#if ! defined(EPPVALUEREASON_HPP)	/* { */
#define	      EPPVALUEREASON_HPP	   1

#include "EppEntity.hpp"

/**
 * This <code>EppValueReason</code> class implements EPP ErrValue and
 * ExtErrValue types for error diagnostic information.
 *
 * <P><B>Warning</B>: Currently, this class supports free text values
 * only. In the future, a <I>DOMNode</I> may be accepted for the value tag.
 *
 * <P>If the <I>reason</I> and <I>lang</I> components of an <code>EppValueReason</code>
 * object are null, it would be assumed as of EPP ErrValue Type.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
class EppValueReason : public EppEntity
{
private:
	DOMString       value;
	DOMString	reason;
	DOMString	lang;

public:

	/**
	 * Creates an <code>EppValueReason</code> object
	 */
	EppValueReason()
	{
		this->value	= NULLXS;
		this->reason	= NULLXS;
		this->lang	= NULLXS;
	};

	/**
	 * Creates an <code>EppValueReason</code> object with a string value
	 */
	EppValueReason( const DOMString& value )
	{
		this->value	= value;
		this->reason	= NULLXS;
		this->lang	= NULLXS;
	};

	/**
	 * Creates an <code>EppValueReason</code> object with a string value and a reason text
	 */
	EppValueReason( const DOMString& value, const DOMString& reason )
	{
		this->value	= value;
		this->reason	= reason;
		this->lang	= NULLXS;
	};

	/**
	 * Creates an <code>EppValueReason</code> object with a string value, a reason text and its language code
	 */
	EppValueReason( const DOMString& value, const DOMString& reason, const DOMString& language )
	{
		this->value	= value;
		this->reason	= reason;
		this->lang	= language;
	};

	/**
	 * Destructor
	 */
	~EppValueReason() {};

	/**
	 * Gets the value
	 */
	DOMString getValue()
	{
		return this->value;
	};

	/**
	 * Sets the value
	 */
	void setValue( const DOMString& value )
	{
		this->value = value;
	};

	/**
	 * Gets the reason
	 */
	DOMString getReason()
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
	DOMString getLanguage()
	{
		return this->lang;
	};

	/**
	 * Sets language type of the reason message
	 */
	void setLanguage( const DOMString& language )
	{
		this->lang = language;
	};

	/**
	 * Converts the <code>EppValueReason</code> object into an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the <code>EppValueReason</code> object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );

	/**
	 * Converts an XML element into an <code>EppValueReason</code> object. The
	 * caller of this method must make sure that the root node is of the EPP
	 * ErrValue or ExtErrValue Type.
	 *
	 * @param root root node for an <code>EppValueReason</code> object in XML format
	 *
	 * @return an <code>EppValueReason</code> object, or null if the node is invalid
	 */
	static EppValueReason * fromXML( const DOMNode& root );

	DOMString toString()
	{
		return EppEntity::toString(DOMString("extValue"));
	};
};

#endif	/*    EPPVALUEREASON_HPP */	/* } */
