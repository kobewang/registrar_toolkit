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
#include "EppCommandUpdateContact.hpp"
#include "EppUtil.hpp"

DOMElement* EppCommandUpdateContact::toXML( DOMDocument& doc, const DOMString& tag )
{
	DOMElement* elm;
	DOMElement* body = EppUtil::createElementNS(doc, "contact", tag);

	if( id.isNotNull() )
	{
		elm = doc.createElement(XS("id"));
		elm->appendChild(doc.createTextNode(id));
		body->appendChild(elm);
	}
	if( statusAdded->size() > 0 )
	{
		elm = doc.createElement(XS("add"));
		statusToXML(doc, *elm, statusAdded);
		body->appendChild(elm);
	}
	if( statusRemoved->size() > 0 )
	{
		elm = doc.createElement(XS("rem"));
		statusToXML(doc, *elm, statusRemoved);
		body->appendChild(elm);
	}
	if(    (newAscii != null) || (newI15d != null)
		|| (newVoice != null) || (newFax != null)
		|| (newEmail.isNotNull() ) || (newAuthInfo != null) || (newDisclose != null) )
	{
		elm = doc.createElement(XS("chg"));
		if( newAscii != null )
		{
			DOMElement* t = newAscii->toXML(doc, "postalInfo");
			if( t != null )
			{
				t->setAttribute(XS("type"), XS("int"));
				elm->appendChild(t);
			}
		}
		if( newI15d != null )
		{
			DOMElement* t = newI15d->toXML(doc, "postalInfo");
			if( t != null )
			{
				t->setAttribute(XS("type"), XS("loc"));
				elm->appendChild(t);
			}
		}
		if( newVoice != null )
		{
			elm->appendChild(newVoice->toXML(doc, "voice"));
		}
		if( newFax != null )
		{
			elm->appendChild(newFax->toXML(doc, "fax"));
		}
		if( newEmail.isNotNull() )
		{
			DOMElement* email = doc.createElement(XS("email"));
			email->appendChild(doc.createTextNode(newEmail));
			elm->appendChild(email);
		}
		if( newAuthInfo != null )
		{
			elm->appendChild(newAuthInfo->toXML(doc, "authInfo"));
		}
		if( newDisclose != null )
		{
			elm->appendChild(newDisclose->toXML(doc, "disclose"));
		}
		body->appendChild(elm);
	}

	return toXMLCommon(doc, tag, *body);
}

EppCommandUpdateContact * EppCommandUpdateContact::fromXML( const DOMNode& root )
{
	EppCommandUpdateContact * cmd = null;
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
		if( name.equals("id") || name.equals("contact:id") )
		{
			if( cmd == null )
			{
				DOMString id = EppUtil::getText(*node);
				cmd = new EppCommandUpdateContact(id);
			}
		}
		//else if( name.equals("add") )
		else if( name.equals("add") || name.equals("contact:add") )
		{
			if( cmd != null )
			{
				cmd->statusFromXML(*node, cmd->statusAdded);
			}
		}
		//else if( name.equals("rem") )
		else if( name.equals("rem") || name.equals("contact:rem") )
		{
			if( cmd != null )
			{
				cmd->statusFromXML(*node, cmd->statusRemoved);
			}
		}
		//else if( name.equals("chg") )
		else if( name.equals("chg") || name.equals("contact:chg") )
		{
			if( cmd != null )
			{
				cmd->addNewStuff(*node);
			}
		}
	}

	return cmd;
}

void EppCommandUpdateContact::addNewStuff( const DOMNode& root )
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
		//if( name.equals("postalInfo") )
		if( name.equals("postalInfo") || name.equals("contact:postalInfo") )
		{
			name = ((DOMElement *) node)->getAttribute(XS("type"));
			if( (name.isNotNull() ) && (name.equals("int") || name.equals("loc")) )
			{
				EppContactData * data = EppContactData::fromXML(*node);
				if( data != null )
				{
					if( name.equals("int") )
					{
						setNewContactDataInt(*data);
					}
					else
					{
						setNewContactDataLoc(*data);
					}
					delete data;
				}
			}
		}
		//else if( name.equals("voice") )
		else if( name.equals("voice") || name.equals("contact:voice") )
		{
			EppE164 * voice = EppE164::fromXML(*node);
			if( voice != null )
			{
				setNewVoice(*voice);
				delete voice;
			}
		}
		//else if( name.equals("fax") )
		else if( name.equals("fax") || name.equals("contact:fax") )
		{
			EppE164 * fax = EppE164::fromXML(*node);
			if( fax != null )
			{
				setNewFax(*fax);
				delete fax;
			}
		}
		//else if( name.equals("email") )
		else if( name.equals("email") || name.equals("contact:email") )
		{
			DOMString email = EppUtil::getText(*node);
			setNewEmail(email);
		}
		//else if( name.equals("authInfo") )
		else if( name.equals("authInfo") || name.equals("contact:authInfo") )
		{
			EppAuthInfo * authInfo = EppAuthInfo::fromXML(*node);
			if( authInfo != null )
			{
				setNewAuthInfo(*authInfo);
				delete authInfo;
			}
		}
		//else if( name.equals("disclose") )
		else if( name.equals("disclose") || name.equals("contact:disclose") )
		{
			EppContactDisclose * disclose = EppContactDisclose::fromXML(*node);
			if( disclose != null )
			{
				setNewContactDisclose(*disclose);
				delete disclose;
			}
		}
	}
}
