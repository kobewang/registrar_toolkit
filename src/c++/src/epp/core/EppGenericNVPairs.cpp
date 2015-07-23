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
#include "EppGenericNVPairs.hpp"
#include "EppUtil.hpp"

DOMElement* EppGenericNVPairs::toXML(DOMDocument& doc, const DOMString& tag )
{
	DOMElement* body;
	DOMElement* elm;

	//	if( doc == null )
	//		return body;
	body = doc.createElement(tag);
	for( unsigned int i = 0 ; i< this->vas->size(); i++ )
	{
		pair_t t = this->vas->elementAt(i);
		elm = doc.createElement(XS("NVTuple"));
		elm->appendChild(doc.createTextNode(t.value));
		elm->setAttribute(XS("name"), t.name );
		body->appendChild(elm);
	}
	return body;
}

EppGenericNVPairs* EppGenericNVPairs::fromXML(const DOMNode& root)
{
	EppGenericNVPairs *ret_val = null;
	ret_val = new EppGenericNVPairs();

	DOMNodeList* list = root.getChildNodes();

	for( unsigned int i = 0; i < list->getLength(); i++ )
	{
		DOMNode* node = list->item(i);
		if( node == null )
			return null;
		DOMString name = node->getLocalName();
		if( name.isNull() )
		{
			name = node->getNodeName();
		}
		if( name.isNull() )
		{
			delete ret_val;
			ret_val = null;
			return null;
		}
		if( name.equals("NVTuple") || name.equals("neulevel:NVTuple") )
		{
			DOMString name = ((DOMElement *)node)->getAttribute(XS("name"));
			ret_val->addGenericNVPair(name, EppUtil::getText(*node));

		}
	}
	return ret_val;
}
