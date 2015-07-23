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
#include "EppHostAttribute.hpp"
#include "EppUtil.hpp"

DOMElement* EppHostAttribute::toXML( DOMDocument& doc, const DOMString& tag )
{
	DOMElement* body = doc.createElement(tag);
	DOMElement* elm;

	if( hostname.isNotNull() )
	{
		elm = doc.createElement(XS("hostName"));
		elm->appendChild(doc.createTextNode(this->hostname));
		body->appendChild(elm);
	}
	if( this->hostaddr != null )
	{
		for( unsigned int i = 0; i < this->hostaddr->size(); i++ )
		{
			EppIpAddress ip = this->hostaddr->elementAt(i);
			body->appendChild(ip.toXML(doc, "hostAddr"));
		}
	}
	return body;
}

EppHostAttribute * EppHostAttribute::fromXML( const DOMNode& root )
{
	DOMString hostname = NULLXS;
	OldValueVectorOf<EppIpAddress> * address = null;

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
		if( name.equals("hostName") )
		{
			hostname = EppUtil::getText(*node);
		}
		else if( name.equals("hostAddr") )
		{
			EppIpAddress * ip = EppIpAddress::fromXML(*node);
			if( ip != null )
			{
				if( address == null )
				{
					address = new OldValueVectorOf<EppIpAddress>(MAX_NUM_OF_IP_ADDRESSES);
				}
				address->addElement(*ip);
				delete ip;
			}
		}
	}

	return new EppHostAttribute(hostname, address);
}
