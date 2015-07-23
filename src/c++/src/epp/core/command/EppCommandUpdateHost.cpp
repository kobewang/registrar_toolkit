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
#include "EppCommandUpdateHost.hpp"
#include "EppUtil.hpp"

DOMElement* EppCommandUpdateHost::toXML( DOMDocument& doc, const DOMString& tag )
{
	DOMElement* elm;
	DOMElement* body = EppUtil::createElementNS(doc, "host", tag);

	if( name.isNotNull() )
	{
		elm = doc.createElement(XS("name"));
		elm->appendChild(doc.createTextNode(name));
		body->appendChild(elm);
	}
	if( (addrAdded->size() > 0) || (statusAdded->size() > 0) )
	{
		elm = doc.createElement(XS("add"));
		if( addrAdded->size() > 0 )
		{
			addressToXML(doc, *elm, addrAdded);
		}
		if( statusAdded->size() > 0 )
		{
			statusToXML(doc, *elm, statusAdded);
		}
		body->appendChild(elm);
	}
	if( (addrRemoved->size() > 0) || (statusRemoved->size() > 0) )
	{
		elm = doc.createElement(XS("rem"));
		if( addrRemoved->size() > 0 )
		{
			addressToXML(doc, *elm, addrRemoved);
		}
		if( statusRemoved->size() > 0 )
		{
			statusToXML(doc, *elm, statusRemoved);
		}
		body->appendChild(elm);
	}
	if( newName.isNotNull() )
	{
		elm = doc.createElement(XS("chg"));
		DOMElement* name = doc.createElement(XS("name"));
		name->appendChild(doc.createTextNode(newName));
		elm->appendChild(name);
		body->appendChild(elm);
	}

	return toXMLCommon(doc, tag, *body);
}

void EppCommandUpdateHost::addressToXML( DOMDocument& doc, DOMElement& body, OldValueVectorOf<EppIpAddress> *list )
{
	DOMElement* elm;
	if( list != null )
	{
		for( unsigned int i = 0; i < list->size(); i++ )
		{
			EppIpAddress addr = list->elementAt(i);
			elm = addr.toXML(doc, "addr");
			body.appendChild(elm);
		}
	}
}

EppCommandUpdateHost * EppCommandUpdateHost::fromXML( const DOMNode& root )
{
	EppCommandUpdateHost * cmd = null;
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
		//if( name.equals("name") )
		if( name.equals("name") || name.equals("host:name") )
		{
			if( cmd == null )
			{
				DOMString host = EppUtil::getText(*node);
				cmd = new EppCommandUpdateHost(host);
			}
		}
		//else if( name.equals("add") )
		else if( name.equals("add") || name.equals("host:add") )
		{
			if( cmd != null )
			{
				cmd->addressFromXML(*node, cmd->addrAdded);
				cmd->statusFromXML(*node, cmd->statusAdded);
			}
		}
		//else if( name.equals("rem") )
		else if( name.equals("rem") || name.equals("host:rem") )
		{
			if( cmd != null )
			{
				cmd->addressFromXML(*node, cmd->addrRemoved);
				cmd->statusFromXML(*node, cmd->statusRemoved);
			}
		}
		//else if( name.equals("chg") )
		else if( name.equals("chg") || name.equals("host:chg") )
		{
			DOMNodeList* tlist = node->getChildNodes();
			for( unsigned int j = 0; j < tlist->getLength(); j++ )
			{
				DOMNode* tnode = tlist->item(j);
				DOMString tname = tnode->getLocalName();
				if( tname.isNull() )
				{
					tname = tnode->getNodeName();
				}
				if( tname.isNull() )
				{
					continue;
				}
				//if( tname.equals("name") )
				if( tname.equals("name") || tname.equals("host:name") )
				{
					DOMString host = EppUtil::getText(*tnode);
					if( cmd != null )
					{
						cmd->setNewName(host);
					}
				}
			}
		}
	}

	return cmd;
}

void EppCommandUpdateHost::addressFromXML( const DOMNode& root, OldValueVectorOf<EppIpAddress> * addressList )
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
		//if( name.equals("addr") )
		if( name.equals("addr") || name.equals("host:addr") )
		{
			EppIpAddress * addr = EppIpAddress::fromXML(*node);
			if( addr != null )
			{
				addressList->addElement(*addr);
				delete addr;
			}
		}
	}
}
