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
#include "EppUnspec.hpp"
#include "EppUtil.hpp"

DOMElement* EppUnspec::toXML( DOMDocument &doc, const DOMString &tag )
{
	DOMElement* body = EppUtil::createElementNS(doc, "neulevel", tag, true);
	DOMElement* elm = doc.createElement(XS("unspec"));

	if( value.isNotNull() && value.length() > 0 )
	{
		elm->appendChild(doc.createTextNode(value));
	}
	body->appendChild(elm);
	if ( this->nvPairs != null && this->nvPairs->howMany() > 0 )
	{
		elm = nvPairs->toXML(doc, "genericNVPairs");
		body->appendChild(elm);
	}
	return body;
}

EppUnspec * EppUnspec::fromXML( const DOMNode &root )
{

	EppUnspec * unspec = null;
	DOMString value = NULLXS;

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
		name = EppUtil::substring(':', name);

		//if( name.equals("unspec") )
		if( name.equals("unspec") || name.equals("neulevel:unspec") )
		{
			value = EppUtil::getText(*node);
			if( !unspec )
			{
				unspec = new EppUnspec(value);
			}
			else
			{
				unspec->setValue(value);
			}
		}
		else if( name.equals("genericNVPairs") || name.equals("neulevel:genericNVPairs") )
		{
			if( !unspec )
			{
				unspec = new EppUnspec(NULLXS);
			}

			EppGenericNVPairs * pairs = EppGenericNVPairs::fromXML(*node);
			if ( pairs )
			{
				unspec->setGenericNVPairs(*pairs);
			}
			delete pairs;
		}
	}
	return unspec ;
}
