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
#include "EppCommandInfoDomain.hpp"
#include "EppUtil.hpp"

const char * EppCommandInfoDomain::HOSTS_ALL = "all";
const char * EppCommandInfoDomain::HOSTS_DEL = "del";
const char * EppCommandInfoDomain::HOSTS_SUB = "sub";
const char * EppCommandInfoDomain::HOSTS_NONE = "none";

DOMElement* EppCommandInfoDomain::toXML( DOMDocument& doc, const DOMString& tag )
{
	DOMElement* elm;
	DOMElement* body = EppUtil::createElementNS(doc, "domain", tag);

	if( name.isNotNull() )
	{
		elm = doc.createElement(XS("name"));
		elm->appendChild(doc.createTextNode(name));

		if( hostsCtrl.isNotNull() )
		{
			elm->setAttribute(XS("hosts"), hostsCtrl);
		}
		body->appendChild(elm);
	}
	if( authInfo != null )
	{
		body->appendChild(authInfo->toXML(doc, "authInfo"));
	}

	return toXMLCommon(doc, tag, *body);
}

EppCommandInfoDomain * EppCommandInfoDomain::fromXML( const DOMNode& root )
{
	EppCommandInfoDomain * cmd = null;
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
		if( name.equals("name") || name.equals("domain:name") )
		{
			DOMString s = EppUtil::getText(*node);

			if( s.isNotNull() && s.length() > 0 )
			{
				if( cmd == null )
				{
					cmd = new EppCommandInfoDomain(s);
					if( cmd == null )
					{
						return cmd;
					}
				}
				else
				{
					cmd->setName(s);
				}
				s = ((DOMElement *) node)->getAttribute(XS("hosts"));
				if( s.isNotNull() && s.length() > 0 )
				{
					cmd->setHostsControl(s);
				}
			}
		}
		//else if( name.equals("authInfo") )
		else if( name.equals("authInfo") || name.equals("domain:authInfo") )
		{
			if( cmd != null )
			{
				EppAuthInfo * authInfo = EppAuthInfo::fromXML(*node);
				if( authInfo != null )
				{
					cmd->setAuthInfo(*authInfo);
					delete authInfo;
				}
			}
		}
	}

	return cmd;
}
