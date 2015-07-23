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
#include "EppAuthInfo.hpp"
#include "EppUtil.hpp"

const char * EppAuthInfo::TYPE_PW  = "pw";
const char * EppAuthInfo::TYPE_EXT = "ext";

DOMElement* EppAuthInfo::toXML( DOMDocument& doc, const DOMString& tag )
{
	DOMElement* body = doc.createElement(tag);
	DOMElement* elm;
	if( (type.isNotNull()) && type.equals(TYPE_PW) )
	{
		elm = doc.createElement(XS("pw"));
		body->appendChild(elm);
		if( roid.isNotNull() && roid.length() > 0 )
		{
			elm->setAttribute(XS("roid"), roid);
		}
		if( value.isNotNull() )
		{
			elm->appendChild(doc.createTextNode(value));
		}
	}
	else if( (type.isNotNull()) && type.equals(TYPE_EXT) )
	{
		elm = doc.createElement(XS("ext"));
		body->appendChild(elm);
	}

	return body;
}

EppAuthInfo * EppAuthInfo::fromXML( const DOMNode& root )
{
	DOMNodeList* list = root.getChildNodes();
	for( unsigned int i = 0; i < list->getLength(); i++ )
	{
		DOMNode* node = list->item(i);
		DOMString name = node->getLocalName();
		if( name.isNull() )
		{
			name = node->getNodeName();
		}
		if( name.isNull() )
		{
			continue;
		}
//		if( name.equals("pw") )
		if( name.equals("pw") || name.equals("contact:pw") || name.equals("domain:pw") || name.equals("svcsub:pw")
			|| name.equals("xriAU:pw") || name.equals("xriINU:pw") || name.equals("xriINA:pw") || name.equals("xriISV:pw") /* Uncomment for CodeString || name.equals("xriCS:pw")*/ )
		{
			DOMElement * elm = (DOMElement *) node;
			DOMString roid = elm->getAttribute(XS("roid"));
			DOMString value = EppUtil::getText(*node);
			return new EppAuthInfo(XS(TYPE_PW), value, roid);
		}
//		else if( name.equals("ext") )
		else if( name.equals("ext") || name.equals("contact:ext") || name.equals("domain:ext") || name.equals("svcsub:ext")
			|| name.equals("xriAU:ext") || name.equals("xriINU:ext") || name.equals("xriINA:ext") || name.equals("xriISV:ext") )
		{
			return new EppAuthInfo(XS(TYPE_EXT), NULLXS);
		}
	}
	return null;
}
