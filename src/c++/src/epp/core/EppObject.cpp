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
#include "EppObject.hpp"
#include "EppContact.hpp"
#include "EppDomain.hpp"
#include "EppHost.hpp"
#include "EppSvcsub.hpp"
#if defined(RTK_SUPPORT_XRI)
#include "EppXriAuthority.hpp"
#include "EppXriNumber.hpp"
#include "EppXriName.hpp"
#include "EppXriService.hpp"
#include "EppXriCodeString.hpp"
#endif
#include "EppUtil.hpp"
#include "EppCommandCreate.hpp"

const int EppObject::CONTACT = 1;
const int EppObject::DOMAIN  = 2;
const int EppObject::HOST    = 3;
const int EppObject::SVCSUB  = 4;
const int EppObject::XRI_AUTHORITY = 5;
const int EppObject::XRI_INUMBER   = 6;
const int EppObject::XRI_INAME     = 7;
const int EppObject::XRI_ISERVICE  = 8;
const int EppObject::XRI_CODESTRING = 9;

EppObject::EppObject()
{
	this->roid     = NULLXS;
	this->status   = new OldValueVectorOf<EppStatus>(MAX_NUM_OF_STATUS);
	this->clID     = NULLXS;
	this->crID     = NULLXS;
	this->crDate   = 0;
	this->upID     = NULLXS;
	this->upDate   = 0;
	this->exDate   = 0;
	this->trDate   = 0;
	this->authInfo = null;
}

void EppObject::freeCommon()
{
	if( this->status != null )
	{
		delete this->status;
		this->status = null;
	}
	if( this->authInfo != null )
	{
		delete this->authInfo;
		this->authInfo = null;
	}
}

void EppObject::fromXMLCommon( const DOMNode& node, const DOMString& name )
{
	if( name.equals("roid") )
	{
		roid = EppUtil::getText(node);
	}
	else if( name.equals("clID") )
	{
		clID = EppUtil::getText(node);
	}
	else if( name.equals("crID") )
	{
		crID = EppUtil::getText(node);
	}
	else if( name.equals("crDate") )
	{
		crDate = EppUtil::getDate(node);
	}
	else if( name.equals("upID") )
	{
		upID = EppUtil::getText(node);
	}
	else if( name.equals("upDate") )
	{
		upDate = EppUtil::getDate(node);
	}
	else if( name.equals("exDate") )
	{
		exDate = EppUtil::getDate(node);
	}
	else if( name.equals("trDate") )
	{
		trDate = EppUtil::getDate(node);
	}
	else if( name.equals("authInfo") )
	{
		if( authInfo != null )
		{
			delete authInfo;
		}
		authInfo = EppAuthInfo::fromXML(node);
	}
	else if( name.equals("status") )
	{
		EppStatus * s = EppStatus::fromXML(node);
		if( s != null )
		{
			addStatus(*s);
			delete s;
		}
	}
}

void EppObject::toXMLCommon( DOMDocument& doc, DOMElement& body )
{
	DOMElement* elm;
	if( clID.isNotNull() )
	{
		elm = doc.createElement(XS("clID"));
		elm->appendChild(doc.createTextNode(clID));
		body.appendChild(elm);
	}
	if( crID.isNotNull() )
	{
		elm = doc.createElement(XS("crID"));
		elm->appendChild(doc.createTextNode(crID));
		body.appendChild(elm);
	}
	if( crDate != 0 )
	{
		elm = doc.createElement(XS("crDate"));
		elm->appendChild(EppUtil::createTextNode(doc, crDate));
		body.appendChild(elm);
	}
	if( upID.isNotNull() )
	{
		elm = doc.createElement(XS("upID"));
		elm->appendChild(doc.createTextNode(upID));
		body.appendChild(elm);
	}
	if( upDate != 0 )
	{
		elm = doc.createElement(XS("upDate"));
		elm->appendChild(EppUtil::createTextNode(doc, upDate));
		body.appendChild(elm);
	}
	if( exDate != 0 )
	{
		elm = doc.createElement(XS("exDate"));
		elm->appendChild(EppUtil::createTextNode(doc, exDate));
		body.appendChild(elm);
	}
	if( trDate != 0 )
	{
		elm = doc.createElement(XS("trDate"));
		elm->appendChild(EppUtil::createTextNode(doc, trDate));
		body.appendChild(elm);
	}
	if( authInfo != null )
	{
		body.appendChild(authInfo->toXML(doc, "authInfo"));
	}
}

EppObject * EppObject::fromXML( const DOMNode& root )
{
	DOMString prefix = root.getPrefix();
	DOMString name = root.getNodeName();

	if( (prefix.isNotNull()) || (name.isNotNull()) )
	{
		if( ((prefix.isNotNull()) && prefix.equals("contact")) ||
			((prefix.isNull()) && name.substringData(0, 8).equals("contact:")) )
		{
			return EppContact::fromXML(root);
		}
		else if( ((prefix.isNotNull()) && prefix.equals("domain")) ||
			     ((prefix.isNull()) && name.substringData(0, 7).equals("domain:")) )
		{
			return EppDomain::fromXML(root);
		}
		else if( ((prefix.isNotNull()) && prefix.equals("host")) ||
			     ((prefix.isNull()) && name.substringData(0, 5).equals("host:")) )
		{
			return EppHost::fromXML(root);
		}
		else if( ((prefix.isNotNull()) && prefix.equals("svcsub")) ||
			     ((prefix.isNull()) && name.substringData(0, 7).equals("svcsub:")) )
		{
			return EppSvcsub::fromXML(root);
		}
#if defined(RTK_SUPPORT_XRI)		
		else if( ((prefix.isNotNull()) && prefix.equals("xriAU")) ||
			((prefix.isNull()) && name.substringData(0, 6).equals("xriAU:")) )
		{
			return EppXriAuthority::fromXML(root);
		}
		else if( ((prefix.isNotNull()) && prefix.equals("xriINU")) ||
			     ((prefix.isNull()) && name.substringData(0, 7).equals("xriINU:")) )
		{
			return EppXriNumber::fromXML(root);
		}
		else if( ((prefix.isNotNull()) && prefix.equals("xriINA")) ||
			      ((prefix.isNull()) && name.substringData(0, 7).equals("xriINA:")) )
		{
			return EppXriName::fromXML(root);
		}
		else if( ((prefix.isNotNull()) && prefix.equals("xriISV")) ||
			     ((prefix.isNull()) && name.substringData(0, 7).equals("xriISV:")) )
		{
			return EppXriService::fromXML(root);
		}
		else if( ((prefix.isNotNull()) && prefix.equals("xriCS")) ||
			     ((prefix.isNull()) && name.substringData(0, 6).equals("xriCS:")) )
		{
			return EppXriCodeString::fromXML(root);
		}
#endif
		/*
		if( prefix.equals("contact") )
		{
			return EppContact::fromXML(root);
		}
		else if( prefix.equals("domain") )
		{
			return EppDomain::fromXML(root);
		}
		else if( prefix.equals("host") )
		{
			return EppHost::fromXML(root);
		}
		else if( prefix.equals("svcsub") )
		{
			return EppSvcsub::fromXML(root);
		}
		 */
	}

	return null;
}

EppCommandCreate EppObject::create( EppObject * object, DOMString xid )
{
	return EppCommandCreate(object, xid);
}
