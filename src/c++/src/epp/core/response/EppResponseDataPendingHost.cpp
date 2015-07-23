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
#include "EppResponseDataPendingHost.hpp"
#include "EppUtil.hpp"

EppResponseDataPendingHost * EppResponseDataPendingHost::fromXML( const DOMNode& root )
{
	EppResponseDataPendingHost * res = null;
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
		//else if( name.equals("name") )
		else if( name.equals("name") || name.equals("host:name") )
		{
			DOMString name = EppUtil::getText(*node);
			if( res == null )
			{
				res = new EppResponseDataPendingHost(name);
				if( res == null )
				{
					return res;
				}
				DOMString cd = ((DOMElement *) node)->getAttribute(XS("paResult"));
				if(    (cd.isNotNull())
					&& (    cd.equals("0")
					|| cd.equals("f")
					|| cd.equals("false") ) )
				{
					res->setResult(false);
				}
				else
				{
					res->setResult(true);
				}
			}
		}
		else if( res != null )
		{
			res->fromXMLCommon(*node, name, "host");
		}
	}

	return res;
}

DOMElement* EppResponseDataPendingHost::toXML( DOMDocument& doc, const DOMString& tag )
{
	DOMElement* elm;
	DOMElement* body = doc.createElement(tag);
	DOMElement* data = EppUtil::createElementNS(doc, "host", "panData");
	body->appendChild(data);

	if( name.isNotNull() )
	{
		elm = doc.createElement(XS("name"));
		elm->appendChild(doc.createTextNode(name));

		if( paResult == true )
		{
			elm->setAttribute(XS("paResult"), XS("1"));
		}
		else
		{
			elm->setAttribute(XS("paResult"), XS("0"));
		}
		data->appendChild(elm);
	}

	toXMLCommon(doc, *data);

	return body;
}
