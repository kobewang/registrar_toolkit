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
#include "EppCommandRenewSvcsub.hpp"
#include "EppUtil.hpp"

DOMElement* EppCommandRenewSvcsub::toXML( DOMDocument& doc, const DOMString& tag )
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
	if( curExpDate != 0 )
	{
		elm = doc.createElement(XS("curExpDate"));
		elm->appendChild(EppUtil::createTextNode(doc, curExpDate, true));
		body->appendChild(elm);
	}
	if( period != null )
	{
		body->appendChild(period->toXML(doc, "period"));
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

EppCommandRenewSvcsub * EppCommandRenewSvcsub::fromXML( const DOMNode& root )
{
	DOMString id;
	time_t curExpDate = 0;
	EppPeriod * period = null;
	EppAuthInfo * authInfo = null;
	DOMString userid;
	DOMString service;

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
			id = EppUtil::getText(*node);
		}
		//else if( name.equals("service") )
		else if( name.equals("service") || name.equals("svcsub:service") )
		{
			service = EppUtil::getText(*node);
		}
			//		else if( name.equals("curExpDate") )
		else if( name.equals("curExpDate") || name.equals("svcsub:curExpDate") )
		{
			curExpDate = EppUtil::getDate(*node, true);
		}
		//else if( name.equals("period") )
		else if( name.equals("period") || name.equals("svcsub:period") )
		{
			if( period != null )
			{
				delete period;
			}
			period = EppPeriod::fromXML(*node);
		}
		//else if( name.equals("userid") )
		else if( name.equals("userid") || name.equals("svcsub:userid") )
		{
			userid = EppUtil::getText(*node);
		}
		//else if( name.equals("authInfo") )
		else if( name.equals("authInfo") || name.equals("svcsub:authInfo") )
		{
			if( authInfo != null )
			{
				delete authInfo;
			}
			authInfo = EppAuthInfo::fromXML(*node);
		}
	}

	EppCommandRenewSvcsub * cmd = new EppCommandRenewSvcsub(id, curExpDate);
	if( cmd != null )
	{
		cmd->period = period;
		cmd->authInfo = authInfo;
		cmd->userid = userid;
		cmd->service = service;
	}
	else
	{
		if( period != null )
		{
			delete period;
		}
		if( authInfo != null )
		{
			delete authInfo;
		}
	}

	return cmd;
}
