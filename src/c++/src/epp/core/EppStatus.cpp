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
#include "EppStatus.hpp"
#include "EppUtil.hpp"

DOMElement* EppStatus::toXML( DOMDocument& doc, const DOMString& tag )
{
	DOMElement* body = doc.createElement(tag);

	if( lang.isNotNull() )
	{
		body->setAttribute(XS("lang"), lang);
	}
	if( s.isNull() )
	{
		body->setAttribute(XS("s"), XS("ok"));
	}
	else
	{
		body->setAttribute(XS("s"), s);
	}
	if( message.isNotNull() )
	{
		body->appendChild(doc.createTextNode(message));
	}
	return body;
}

EppStatus * EppStatus::fromXML( const DOMNode& root )
{
	DOMElement * elm = (DOMElement *) &root;
	DOMString str;
	EppStatus * status = new EppStatus();

	str = elm->getAttribute(XS("s"));

	if( str.isNotNull() && str.length() > 0 )
	{
		status->s = str;
	}

	str = elm->getAttribute(XS("lang"));
	if( str.isNotNull() && str.length() > 0 )
	{
		status->lang = str;
	}

	str = EppUtil::getText(root);
	if( str.isNotNull() && str.length() > 0 )
	{
		status->message = str;
	}
	return status;
}
