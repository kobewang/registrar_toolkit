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
#include "EppSvcsub.hpp"
#include "EppUtil.hpp"

const char * EppSvcsub::CONTACT_TYPE_ADMIN = "admin";
const char * EppSvcsub::CONTACT_TYPE_BILLING = "billing";
const char * EppSvcsub::CONTACT_TYPE_TECH = "tech";
const char * EppSvcsub::CONTACT_TYPE_PRIMARY = "primary";
const char * EppSvcsub::CONTACT_TYPE_SECONDARY = "secondary";
const char * EppSvcsub::CONTACT_TYPE_BIZLOCK = "bizlock";

const char * EppSvcsub::STATUS_CLIENT_DELETE_PROHIBITED = "clientDeleteProhibited";
const char * EppSvcsub::STATUS_CLIENT_SUSPENDED = "clientSuspended";
const char * EppSvcsub::STATUS_CLIENT_RENEW_PROHIBITED = "clientRenewProhibited";
const char * EppSvcsub::STATUS_CLIENT_TRANSFER_PROHIBITED = "clientTransferProhibited";
const char * EppSvcsub::STATUS_CLIENT_UPDATE_PROHIBITED = "clientUpdateProhibited";
const char * EppSvcsub::STATUS_INACTIVE = "inactive";
const char * EppSvcsub::STATUS_OK = "ok";
const char * EppSvcsub::STATUS_PENDING_DELETE = "pendingDelete";
const char * EppSvcsub::STATUS_PENDING_TRANSFER = "pendingTransfer";
const char * EppSvcsub::STATUS_PENDING_VERIFICATION = "pendingVerification";
const char * EppSvcsub::STATUS_SERVER_DELETE_PROHIBITED = "serverDeleteProhibited";
const char * EppSvcsub::STATUS_SERVER_SUSPENDED = "serverSuspended";
const char * EppSvcsub::STATUS_SERVER_RENEW_PROHIBITED = "serverRenewProhibited";
const char * EppSvcsub::STATUS_SERVER_TRANSFER_PROHIBITED = "serverTransferProhibited";
const char * EppSvcsub::STATUS_SERVER_UPDATE_PROHIBITED = "serverUpdateProhibited";

DOMElement* EppSvcsub::toXML( DOMDocument& doc, const DOMString& tag )
{
	DOMElement* elm;
	DOMElement* body = EppUtil::createElementNS(doc, "svcsub", tag);
	bool isCreate = tag.equals("create");

	// the order of the tags for create is:
	//
	// id/service/period/registrant/contact/authInfo
	//
	// the order of the tags for info is
	//
	// id/roid/status/registrant/contact/service/param/host/clID/crID/crDate
	// upDate/exDate/trDate/authInfo
	//
	if( isCreate )
	{
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
		if( param != null )
		{
			for( unsigned int i = 0; i < param->size(); i++ )
			{
				EppSvcsubParam p = param->elementAt(i);
				body->appendChild(p.toXML(doc, "param"));
			}
		}
		if( period != null )
		{
			body->appendChild(period->toXML(doc, "period"));
		}
		if( registrant.isNotNull() )
		{
			elm = doc.createElement(XS("registrant"));
			elm->appendChild(doc.createTextNode(registrant));
			body->appendChild(elm);
		}
		if( contact != null )
		{
			for( unsigned int i = 0; i < contact->size(); i++ )
			{
				EppContactType c = contact->elementAt(i);
				body->appendChild(c.toXML(doc, "contact"));
			}
		}
	}
	else
	{
		//id/roid/status/registrant/contact/service/param/etc
		if( id.isNotNull() )
		{
			elm = doc.createElement(XS("id"));
			elm->appendChild(doc.createTextNode(id));
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
		if( registrant.isNotNull() )
		{
			elm = doc.createElement(XS("registrant"));
			elm->appendChild(doc.createTextNode(registrant));
			body->appendChild(elm);
		}
		if( contact != null )
		{
			for( unsigned int i = 0; i < contact->size(); i++ )
			{
				EppContactType c = contact->elementAt(i);
				body->appendChild(c.toXML(doc, "contact"));
			}
		}
		if( service.isNotNull() )
		{
			elm = doc.createElement(XS("service"));
			elm->appendChild(doc.createTextNode(service));
			body->appendChild(elm);
		}
		if( param != null )
		{
			for( unsigned int i = 0; i < param->size(); i++ )
			{
				EppSvcsubParam p = param->elementAt(i);
				body->appendChild(p.toXML(doc, "param"));
			}
		}
	}

	toXMLCommon(doc, *body);

	return body;
}

EppSvcsub * EppSvcsub::fromXML( const DOMNode& root )
{
	EppSvcsub * svcsub = new EppSvcsub();
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
		if( name.substringData(0, 7).equals("svcsub:") )
		{
			name = name.substringData(7, name.length() - 7);
		}
		if( name.equals("id") )
		{
			svcsub->setId(EppUtil::getText(*node));
		}
		if( name.equals("service") )
		{
			svcsub->setService(EppUtil::getText(*node));
		}
		else if( name.equals("param") )
		{
			EppSvcsubParam * p = EppSvcsubParam::fromXML(*node);
			if( p != null )
			{
				svcsub->addParam(*p);
				delete p;
			}
		}
		else if( name.equals("contact") )
		{
			EppContactType * c = EppContactType::fromXML(*node);
			if( c != null )
			{
				svcsub->addContact(*c);
				delete c;
			}
		}
		else if( name.equals("period") )
		{
			EppPeriod * p = EppPeriod::fromXML(*node);
			if( p != null )
			{
				svcsub->setPeriod(*p);
				delete p;
			}
		}
		else if( name.equals("registrant") )
		{
			svcsub->setRegistrant(EppUtil::getText(*node));
		}
		else if( name.equals("authInfo") )
		{
			EppAuthInfo * authInfo = EppAuthInfo::fromXML(*node);
			if( authInfo != null )
			{
				svcsub->setAuthInfo(*authInfo);
				delete authInfo;
			}
		}
		else
		{
			svcsub->fromXMLCommon(*node, name);
		}
	}

	return svcsub;
}
