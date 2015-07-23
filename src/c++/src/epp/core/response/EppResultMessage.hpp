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

#if ! defined(EPPRESULTMESSAGE_HPP)    /* { */
#define       EPPRESULTMESSAGE_HPP        1

#include "EppEntity.hpp"

/**
 * This <code>EppResultMessage</code> class implements EPP Result Message
 * entity.
 *
 * <P>The <I>id</I> tag has been removed from EPP-1.0. For backward compatibility,
 * the API is unchanged, but the <I>id</I> value would always be null.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
class EPP_EXPORT EppResultMessage : public EppEntity
{
private:
	DOMString id;
	DOMString lang;
	DOMString msg;

public:

	/**
	 * Creates an <code>EppResultMessage</code> object
	 */
	EppResultMessage()
	: id()
	, lang()
	, msg() 
	{
	};

	/**
	 * Creates an <code>EppResultMessage</code> object
	 *
	 * @param message message to be returned
	 */
	EppResultMessage( const DOMString& message )
	: id()
	, lang(DOMString("en-US"))
	, msg(message)
	{
	};

	/**
	 * Creates an <code>EppResultMessage</code> object
	 *
	 * @param id message id
	 * @param message message to be returned
	 */
	EppResultMessage( const DOMString& idIn, const DOMString& message )
	: id(idIn)
	, lang(DOMString("en-US"))
	, msg(message)
	{
	};

	/**
	 * Creates an <code>EppResultMessage</code> object
	 *
	 * @param id message id
	 * @param language langaue type of the message
	 * @param message message to be returned
	 */
	EppResultMessage( const DOMString& idIn, const DOMString& language, const DOMString& message )
	: id(idIn)
	, lang(language)
	, msg(message)
	{
	};

	/**
	 * Destructor
	 */
	~EppResultMessage() {};

	/**
	 * Gets message id
	 *
	 * This method is not functional in EPP-1.0, will be removed in the future.
	 *
	 * Please use EppResponse::getMessageId() instead
	 */
	DOMString getId()
	{
		return this->id;
	};

	/**
	 * Sets message id
	 *
	 * This method is not functional in EPP-1.0, will be removed in the future.
	 *
	 * Please use EppResponse::setMessageId() instead
	 */
	void setId( const DOMString& id )
	{
		this->id = id;
		this->id = NULLXS;
	};

	/**
	 * Gets message to be returned
	 */
	DOMString getMessage()
	{
		return this->msg;
	};

	/**
	 * Sets message to be returned
	 */
	void setMessage( const DOMString& message )
	{
		this->msg = message;
	};

	/**
	 * Gets language type of the message
	 */
	DOMString getLanguage()
	{
		return this->lang;
	};

	/**
	 * Sets language type of the message
	 */
	void setLanguage( const DOMString& language )
	{
		this->lang = language;
	};

	/**
	 * Converts the <code>EppResultMessage</code> object into an XML element
	 *
	 * @param doc the XML <code>DOMElement</code> object
	 * @param tag the tag/element name for the <code>EppResultMessage</code>
	 *            object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );

	/**
	 * Converts an XML element into an <code>EppResultMessage</code> object.
	 * The caller of this method must make sure that the root node is of
	 * the EPP msgType.
	 *
	 * @param root root node for an <code>EppResultMessage</code> object in
	 *             XML format
	 *
	 * @return an <code>EppResultMessage</code> object, or null if the node
	 *         is invalid
	 */
	static EppResultMessage * fromXML( const DOMNode& root );

	DOMString toString()
	{
		return EppEntity::toString(DOMString("msg"));
	};
};

#endif     /* EPPRESULTMESSAGE_HPP */  /* } */
