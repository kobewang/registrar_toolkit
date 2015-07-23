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
#include "EppCommandUpdateSvcsub.hpp"
#include "EppUtil.hpp"

DOMElement* EppCommandUpdateSvcsub::toXML( DOMDocument& doc, const DOMString& tag )
{
	DOMElement* elm;
	DOMElement* body = EppUtil::createElementNS(doc, "svcsub", tag);

	if( id.isNotNull() )
	{
		elm = doc.createElement(XS("id"));
		elm->appendChild(doc.createTextNode(id));
		body->appendChild(elm);
	}
	if( service.isNotNull() )
	{
		elm = doc.createElement(XS("service"));
		elm->appendChild(doc.createTextNode(service));
		body->appendChild(elm);
	}
	if(    (paramAdded->size() > 0)
		|| (contactAdded->size() > 0)
		|| (statusAdded->size() > 0) )
	{
		elm = doc.createElement(XS("add"));
		if( paramAdded->size() > 0 )
		{
			paramToXML(doc, *elm, paramAdded);
		}
		if( contactAdded->size() > 0 )
		{
			contactToXML(doc, *elm, contactAdded);
		}
		if( statusAdded->size() > 0 )
		{
			statusToXML(doc, *elm, statusAdded);
		}
		body->appendChild(elm);
	}
	if(    (paramRemoved->size() > 0)
		|| (contactRemoved->size() > 0)
		|| (statusRemoved->size() > 0) )
	{
		elm = doc.createElement(XS("rem"));
		if( paramRemoved->size() > 0 )
		{
			paramToXML(doc, *elm, paramRemoved);
		}
		if( contactRemoved->size() > 0 )
		{
			contactToXML(doc, *elm, contactRemoved);
		}
		if( statusRemoved->size() > 0 )
		{
			statusToXML(doc, *elm, statusRemoved);
		}
		body->appendChild(elm);
	}
	if( (newRegistrant.isNotNull() ) || (newAuthInfo != null) )
	{
		elm = doc.createElement(XS("chg"));
		if( newRegistrant.isNotNull() )
		{
			DOMElement* name = doc.createElement(XS("registrant"));
			name->appendChild(doc.createTextNode(newRegistrant));
			elm->appendChild(name);
		}
		if( newAuthInfo != null )
		{
			elm->appendChild(newAuthInfo->toXML(doc, "authInfo"));
		}
		body->appendChild(elm);
	}
	if( userid.isNotNull() )
	{
		elm = doc.createElement(XS("userid"));
		elm->appendChild(doc.createTextNode(userid));
		body->appendChild(elm);
	}
	if( authInfo != null )
	{
		body->appendChild(authInfo->toXML(doc, "authInfo"));
	}

	return toXMLCommon(doc, tag, *body);
}

void EppCommandUpdateSvcsub::paramToXML( DOMDocument& doc, DOMElement& body, OldValueVectorOf<EppSvcsubParam> * list )
{
	DOMElement* elm;
	if( list != null )
	{
		for( unsigned int i = 0; i < list->size(); i++ )
		{
			EppSvcsubParam p = list->elementAt(i);
			elm = p.toXML(doc, "param");
			body.appendChild(elm);
		}
	}
}

void EppCommandUpdateSvcsub::contactToXML( DOMDocument& doc, DOMElement& body, OldValueVectorOf<EppContactType> *list )
{
	DOMElement* elm;
	if( list != null )
	{
		for( unsigned int i = 0; i < list->size(); i++ )
		{
			EppContactType ct  = list->elementAt(i);
			elm = ct.toXML(doc, "contact");
			body.appendChild(elm);
		}
	}
}

EppCommandUpdateSvcsub * EppCommandUpdateSvcsub::fromXML( const DOMNode& root )
{
	EppCommandUpdateSvcsub * cmd = null;
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
		//if( name.equals("id") )
		if( name.equals("id") || name.equals("svcsub:id") )
		{
			if( cmd == null )
			{
				DOMString id = EppUtil::getText(*node);
				cmd = new EppCommandUpdateSvcsub(id);
			}
		}
		//if( name.equals("service") )
		else if( name.equals("service") || name.equals("svcsub:service") )
		{
			DOMString svc = EppUtil::getText(*node);
			if( cmd != null )
			{
				cmd->service = svc;
			}
		}
		//else if( name.equals("add") )
		else if( name.equals("add") || name.equals("svcsub:add") )
		{
			if( cmd != null )
			{
				cmd->paramFromXML(*node, cmd->paramAdded);
				cmd->contactFromXML(*node, cmd->contactAdded);
				cmd->statusFromXML(*node, cmd->statusAdded);
			}
		}
		//else if( name.equals("rem") )
		else if( name.equals("rem") || name.equals("svcsub:rem") )
		{
			if( cmd != null )
			{
				cmd->paramFromXML(*node, cmd->paramRemoved);
				cmd->contactFromXML(*node, cmd->contactRemoved);
				cmd->statusFromXML(*node, cmd->statusRemoved);
			}
		}
		//else if( name.equals("chg") )
		else if( name.equals("chg") || name.equals("svcsub:chg") )
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
				//if( tname.equals("registrant") )
				if( tname.equals("registrant") || tname.equals("svcsub:registrant") )
				{
					DOMString id = EppUtil::getText(*tnode);
					if( cmd != null )
					{
						cmd->setNewRegistrant(id);
					}
				}
				//else if( tname.equals("authInfo") )
				else if( tname.equals("authInfo") || tname.equals("svcsub:authInfo") )
				{
					EppAuthInfo * authInfo = EppAuthInfo::fromXML(*tnode);
					if( (cmd != null) && (authInfo != null) )
					{
						cmd->setNewAuthInfo(*authInfo);
						delete authInfo;
					}
				}
			}
		}
		//if( name.equals("userid") )
		if( name.equals("userid") || name.equals("svcsub:userid") )
		{
			DOMString userid = EppUtil::getText(*node);
			if( cmd != null )
			{
				cmd->userid = userid;
			}
		}
		//else if( name.equals("authinfo") )
		else if( name.equals("authInfo") || name.equals("svcsub:authInfo") )
		{
			EppAuthInfo * authInfo = EppAuthInfo::fromXML(*node);
			if( (cmd != null) && (authInfo != null) )
			{
				cmd->setAuthInfo(*authInfo);
				delete authInfo;
			}
		}
	}
	return cmd;
}

void EppCommandUpdateSvcsub::paramFromXML( const DOMNode& root, OldValueVectorOf<EppSvcsubParam> * paramList )
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
		//if( name.equals("param") )
		if( name.equals("param") || name.equals("svcsub:param") )
		{
			EppSvcsubParam * p = EppSvcsubParam::fromXML(*node);
			if( p != null )
			{
				paramList->addElement(*p);
				delete p;
			}
		}
	}
}

void EppCommandUpdateSvcsub::contactFromXML( const DOMNode& root, OldValueVectorOf<EppContactType> * contactList )
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
		//if( name.equals("contact") )
		if( name.equals("contact") || name.equals("svcsub:contact") )
		{
			EppContactType * ct = EppContactType::fromXML(*node);
			if( ct != null )
			{
				contactList->addElement(*ct);
				delete ct;
			}
		}
	}
}
