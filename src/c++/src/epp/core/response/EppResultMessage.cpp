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
#include "EppResultMessage.hpp"
#include "EppUtil.hpp"

DOMElement* EppResultMessage::toXML( DOMDocument& doc, const DOMString& tag )
{
	DOMElement* body = doc.createElement(tag);
	//	DOMElement* elm;

	//	if( (id.isNotNull()) && (id.length() > 0) )
	//	{
	//		body.setAttribute(XS("id", id);
	//	}
	if( lang.isNotNull() && lang.length() > 0 )
	{
		body->setAttribute(XS("lang"), lang);
	}
	if( msg.isNotNull() && msg.length() > 0 )
	{
		body->appendChild(doc.createTextNode(msg));
	}

	return body;
}

EppResultMessage * EppResultMessage::fromXML( const DOMNode& root )
{
	DOMString id;
	DOMString lang;
	DOMString msg;

	DOMElement * elm = (DOMElement *) & root;
	id   = elm->getAttribute(XS("id"));
	lang = elm->getAttribute(XS("lang"));
	msg  = EppUtil::getText(root);

	return new EppResultMessage(id, lang, msg);
}
