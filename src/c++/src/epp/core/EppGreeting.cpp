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
#include "EppGreeting.hpp"
#include "EppUtil.hpp"

void EppGreeting::cloneServiceMenu( EppServiceMenu& menu )
{
	unsigned int i;
	OldValueVectorOf<DOMString> * version = menu.getVersion();
	OldValueVectorOf<DOMString> * lang    = menu.getLanguage();
	OldValueVectorOf<DOMString> * svc     = menu.getService();

	if( this->svcMenu == null )
	{
		this->svcMenu = new EppServiceMenu();
	}

	OldValueVectorOf<DOMString> * myVersion = this->svcMenu->getVersion();
	OldValueVectorOf<DOMString> * myLang    = this->svcMenu->getLanguage();
	OldValueVectorOf<DOMString> * mySvc     = this->svcMenu->getService();

	myVersion->removeAllElements();
	myLang->removeAllElements();
	mySvc->removeAllElements();

	for( i = 0; i < version->size(); i++ )
	{
		myVersion->addElement(version->elementAt(i));
	}
	for( i = 0; i < lang->size(); i++ )
	{
		myLang->addElement(lang->elementAt(i));
	}
	for( i = 0; i < svc->size(); i++ )
	{
		mySvc->addElement(svc->elementAt(i));
	}
}

void EppGreeting::setDefaultDataCollectionPolicy()
{
	if( this->dcp != null )
	{
		delete this->dcp;
	}
	this->dcp = new EppDataCollectionPolicy();
	if( this->dcp != null )
	{
		this->dcp->setAccess(EppDataCollectionPolicy::ACCESS_ALL);
		EppDataCollectionStatement stmt;
		stmt.setPurpose(EppDataCollectionStatement::PURPOSE_ADMIN, true);
		stmt.setPurpose(EppDataCollectionStatement::PURPOSE_PROV , true);
		stmt.setRecipient(EppDataCollectionStatement::RECIPIENT_OURS  , true);
		stmt.setRecipient(EppDataCollectionStatement::RECIPIENT_PUBLIC, true);
		stmt.setRetention(EppDataCollectionStatement::RETENTION_STATED);
		this->dcp->addStatement(stmt);
	}
}

DOMElement* EppGreeting::toXML( DOMDocument& doc, const DOMString& tag )
{
	DOMElement* elm;
	DOMElement* epp = EppUtil::createElementNS(doc, "epp", NULLXS);
	DOMElement* body = doc.createElement(tag);
	epp->appendChild(body);

	if( svID .isNotNull() )
	{
		elm = doc.createElement(XS("svID"));
		elm->appendChild(doc.createTextNode(svID));
		body->appendChild(elm);
	}
	elm = doc.createElement(XS("svDate"));
	elm->appendChild(EppUtil::createTextNode(doc, svDate));
	body->appendChild(elm);

	if( svcMenu != null )
	{
		body->appendChild(svcMenu->toXML(doc, "svcMenu"));
	}
	if( dcp != null )
	{
		body->appendChild(dcp->toXML(doc, "dcp"));
	}
	return epp;
}

EppGreeting * EppGreeting::fromXML( const DOMNode &root )
{
	unsigned int i;
	EppGreeting * greeting = null;

	DOMNodeList* list = root.getChildNodes();
	for( i = 0; i < list->getLength(); i++ )
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
		if( name.equals("greeting") )
		{
			greeting = new EppGreeting();
			list = node->getChildNodes();
			break;
		}
	}

	if( greeting == null )
	{
		return null;
	}

	for( i = 0; i < list->getLength(); i++ )
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
		if( name.equals("svID") )
		{
			greeting->svID = EppUtil::getText(*node);
		}
		else if( name.equals("svDate") )
		{
			greeting->svDate = EppUtil::getDate(*node);
		}
		else if( name.equals("svcMenu") )
		{
			if( greeting->svcMenu != null )
			{
				delete greeting->svcMenu;
			}
			greeting->svcMenu = EppServiceMenu::fromXML(*node);
		}
		else if( name.equals("dcp") )
		{
			if( greeting->dcp != null )
			{
				delete greeting->dcp;
			}
			greeting->dcp = EppDataCollectionPolicy::fromXML(*node);
		}
	}

	return greeting;
}
