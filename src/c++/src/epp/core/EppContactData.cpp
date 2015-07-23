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
#include "EppContactData.hpp"
#include "EppUtil.hpp"

DOMElement* EppContactData::toXML( DOMDocument& doc, const DOMString& tag )
{
	DOMElement* body = doc.createElement(tag);
	DOMElement* elm;

	if( name.isNotNull() )
	{
		elm = doc.createElement(XS("name"));
		elm->appendChild(doc.createTextNode(name));
		body->appendChild(elm);
	}
	if( (org.isNotNull()) || hasOrg )
	{
		elm = doc.createElement(XS("org"));
		elm->appendChild(doc.createTextNode(org));
		body->appendChild(elm);
	}
	if( !addr.isNull() )
	{
		body->appendChild(addr.toXML(doc, "addr"));
	}

	return body;
}

EppContactData * EppContactData::fromXML( const DOMNode& root )
{
	DOMString contact_name = NULLXS;
	DOMString contact_org  = NULLXS;
	EppAddress * contact_addr = null;
	EppContactData * contact_data = null;
	bool has_org = false;

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
		if( name.substringData(0, 8).equals("contact:") )
		{
			name = name.substringData(8, name.length() - 8);
		}
		if( name.substringData(0, 6).equals("xriAU:") )
		{
			name = name.substringData(6, name.length() - 6);
		}
		if( name.equals("name") )
		{
			contact_name = EppUtil::getText(*node);
		}
		else if( name.equals("org") )
		{
			contact_org = EppUtil::getText(*node);
			has_org = true;
		}
		else if( name.equals("addr") )
		{
			if( contact_addr != null )
			{
				delete contact_addr;
			}
			contact_addr = EppAddress::fromXML(*node);
		}
	}
	if( has_org ||
		(contact_name.isNotNull()) ||
		(contact_org.isNotNull())  ||
		(contact_addr != null) )
	{
		contact_data = new EppContactData();
		if( contact_data != null )
		{
			contact_data->setName(contact_name);
			contact_data->setOrganization(contact_org);
			if( contact_addr != null )
			{
				contact_data->setAddress(*contact_addr);
				delete contact_addr;
				contact_addr = null;
			}
			contact_data->hasOrg = has_org;
		}
	}

	return contact_data;
}
