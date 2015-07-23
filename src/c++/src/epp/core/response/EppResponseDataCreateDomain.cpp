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
#include "EppResponseDataCreateDomain.hpp"
#include "EppUtil.hpp"

EppResponseDataCreateDomain * EppResponseDataCreateDomain::fromXML( const DOMNode& root )
{
	DOMString roid;
	DOMString domain;
	time_t exDate = 0;
	time_t crDate = 0
		;
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
		//if( name.equals("roid") )
		if( name.equals("roid") || name.equals("domain:roid") )
		{
			roid = EppUtil::getText(*node);
		}
		//else if( name.equals("name") )
		else if( name.equals("name") || name.equals("domain:name") )
		{
			domain = EppUtil::getText(*node);
		}
		//else if( name.equals("exDate") )
		else if( name.equals("exDate") || name.equals("domain:exDate") )
		{
			exDate = EppUtil::getDate(*node);
		}
		//else if( name.equals("crDate") )
		else if( name.equals("crDate") || name.equals("domain:crDate") )
		{
			crDate = EppUtil::getDate(*node);
		}
	}

	return new EppResponseDataCreateDomain(domain, exDate, crDate);
}

DOMElement* EppResponseDataCreateDomain::toXML( DOMDocument& doc, const DOMString& tag )
{
	DOMElement* elm;
	DOMElement* body = doc.createElement(tag);
	DOMElement* data = EppUtil::createElementNS(doc, "domain", "creData");
	body->appendChild(data);

	if( name.isNotNull() )
	{
		elm = doc.createElement(XS("name"));
		elm->appendChild(doc.createTextNode(name));
		data->appendChild(elm);
	}
	//	if( roid.isNotNull() )
	//	{
	//		elm = doc.createElement(XS("roid");
	//		elm->appendChild(doc.createTextNode(roid));
	//		data->appendChild(elm);
	//	}
	if( crDate != 0 )
	{
		elm = doc.createElement(XS("crDate"));
		elm->appendChild(EppUtil::createTextNode(doc, crDate));
		data->appendChild(elm);
	}
	if( exDate != 0 )
	{
		elm = doc.createElement(XS("exDate"));
		elm->appendChild(EppUtil::createTextNode(doc, exDate));
		data->appendChild(elm);
	}

	return body;
}
