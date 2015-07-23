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
#include "EppCredsOptions.hpp"
#include "EppUtil.hpp"

DOMElement* EppCredsOptions::toXML( DOMDocument& doc, const DOMString& tag )
{
	DOMElement* body = doc.createElement(tag);
	DOMElement* elm;

	if( version.isNotNull() )
	{
		elm = doc.createElement(XS("version"));
		elm->appendChild(doc.createTextNode(version));
		body->appendChild(elm);
	}
	if( lang.isNotNull() )
	{
		elm = doc.createElement(XS("lang"));
		elm->appendChild(doc.createTextNode(lang));
		body->appendChild(elm);
	}

	return body;
}

EppCredsOptions * EppCredsOptions::fromXML( const DOMNode& root )
{
	DOMString version;
	DOMString lang;

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
		if( name.equals("version") )
		{
			version = EppUtil::getText(*node);
		}
		else if( name.equals("lang") )
		{
			lang = EppUtil::getText(*node);
		}
	}

	if( (version.isNotNull()) && (lang.isNotNull()) )
	{
		return new EppCredsOptions(version, lang);
	}

	return null;
}
