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
#include "EppHost.hpp"
#include "EppUtil.hpp"

const char * EppHost::STATUS_CLIENT_DELETE_PROHIBITED = "clientDeleteProhibited";
const char * EppHost::STATUS_CLIENT_UPDATE_PROHIBITED = "clientUpdateProhibited";
const char * EppHost::STATUS_LINKED = "linked";
const char * EppHost::STATUS_OK = "ok";
const char * EppHost::STATUS_PENDING_CREATE = "pendingCreate";
const char * EppHost::STATUS_PENDING_DELETE = "pendingDelete";
const char * EppHost::STATUS_PENDING_TRANSFER = "pendingTransfer";
const char * EppHost::STATUS_PENDING_UPDATE = "pendingUpdate";
const char * EppHost::STATUS_SERVER_DELETE_PROHIBITED = "serverDeleteProhibited";
const char * EppHost::STATUS_SERVER_UPDATE_PROHIBITED = "serverUpdateProhibited";

DOMElement* EppHost::toXML( DOMDocument& doc, const DOMString& tag )
{
	DOMElement* elm;
	DOMElement* body = EppUtil::createElementNS(doc, "host", tag);

	if( name.isNotNull() )
	{
		elm = doc.createElement(XS("name"));
		elm->appendChild(doc.createTextNode(name));
		body->appendChild(elm);
	}
	if( roid.isNotNull() )
	{
		elm = doc.createElement(XS("roid"));
		elm->appendChild(doc.createTextNode(roid));
		body->appendChild(elm);
	}
	if( status != null )
	{
		for( unsigned int i = 0; i < status->size(); i++ )
		{
			EppStatus s = status->elementAt(i);
			body->appendChild(s.toXML(doc, "status"));
		}
	}
	if( addr != null )
	{
		for( unsigned int i = 0; i < addr->size(); i++ )
		{
			EppIpAddress ip = addr->elementAt(i);
			body->appendChild(ip.toXML(doc, "addr"));
		}
	}

	toXMLCommon(doc, *body);

	return body;
}

EppHost * EppHost::fromXML( const DOMNode& root )
{
	EppHost * host = new EppHost();
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
		if( name.substringData(0, 5).equals("host:") )
		{
			name = name.substringData(5, name.length() - 5);
		}
		if( name.equals("name") )
		{
			host->setName(EppUtil::getText(*node));
		}
		else if( name.equals("addr") )
		{
			EppIpAddress * ip = EppIpAddress::fromXML(*node);
			if ( ip != null )
			{
				host->addAddress(*ip);
				delete ip;
			}
		}
		else
		{
			host->fromXMLCommon(*node, name);
		}
	}

	return host;
}
