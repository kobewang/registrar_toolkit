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
#include "EppContactDisclose.hpp"
#include "EppUtil.hpp"

const int EppContactDisclose::TAG_NAME_INT = 1;
const int EppContactDisclose::TAG_NAME_LOC = 2;
const int EppContactDisclose::TAG_ORG_INT  = 3;
const int EppContactDisclose::TAG_ORG_LOC  = 4;
const int EppContactDisclose::TAG_ADDR_INT = 5;
const int EppContactDisclose::TAG_ADDR_LOC = 6;
const int EppContactDisclose::TAG_VOICE    = 7;
const int EppContactDisclose::TAG_FAX      = 8;
const int EppContactDisclose::TAG_EMAIL    = 9;

DOMElement* EppContactDisclose::toXML( DOMDocument& doc, const DOMString& tag )
{
	DOMElement* body = doc.createElement(tag);
	DOMElement* elm;

	body->setAttribute(XS("flag"), (disclose == true) ? XS("1") : XS("0"));

	if( name_int == true )
	{
		elm = doc.createElement(XS("name"));
		elm->setAttribute(XS("type"), XS("int"));
		body->appendChild(elm);
	}
	if( name_loc == true )
	{
		elm = doc.createElement(XS("name"));
		elm->setAttribute(XS("type"), XS("loc"));
		body->appendChild(elm);
	}
	if( org_int == true )
	{
		elm = doc.createElement(XS("org"));
		elm->setAttribute(XS("type"), XS("int"));
		body->appendChild(elm);
	}
	if( org_loc == true )
	{
		elm = doc.createElement(XS("org"));
		elm->setAttribute(XS("type"), XS("loc"));
		body->appendChild(elm);
	}
	if( addr_int == true )
	{
		elm = doc.createElement(XS("addr"));
		elm->setAttribute(XS("type"), XS("int"));
		body->appendChild(elm);
	}
	if( addr_loc == true )
	{
		elm = doc.createElement(XS("addr"));
		elm->setAttribute(XS("type"), XS("loc"));
		body->appendChild(elm);
	}
	if( voice == true )
	{
		elm = doc.createElement(XS("voice"));
		body->appendChild(elm);
	}
	if( fax == true )
	{
		elm = doc.createElement(XS("fax"));
		body->appendChild(elm);
	}
	if( email == true )
	{
		elm = doc.createElement(XS("email"));
		body->appendChild(elm);
	}

	return body;
}

EppContactDisclose * EppContactDisclose::fromXML( const DOMNode& root )
{
	EppContactDisclose * disc = new EppContactDisclose();
	if( disc == null )
	{
		return disc;
	}
	DOMString type = ((DOMElement *) &root)->getAttribute(XS("flag"));

	if( (type.isNotNull()) &&
		(type.equals("0") || type.equals("f") || type.equals("false") ) )
	{
		disc->setDiscloseFlag(false);
	}
	else
	{
		disc->setDiscloseFlag(true);
	}

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
//		if( name.equals("name") )
		if( name.equals("name") || name.equals("contact:name") )
		{
			type = ((DOMElement *) node)->getAttribute(XS("type"));
			if( (type.isNotNull()) && type.equals("int") )
			{
				disc->set(TAG_NAME_INT, true);
			}
			else if( (type.isNotNull()) && type.equals("loc") )
			{
				disc->set(TAG_NAME_LOC, true);
			}
		}
//		else if( name.equals("org") )
		else if( name.equals("org") || name.equals("contact:org") )
		{
			type = ((DOMElement *) node)->getAttribute(XS("type"));
			if( (type.isNotNull()) && type.equals("int") )
			{
				disc->set(TAG_ORG_INT, true);
			}
			else if( (type.isNotNull()) && type.equals("loc") )
			{
				disc->set(TAG_ORG_LOC, true);
			}
		}
//		else if( name.equals("addr") )
		else if( name.equals("addr") || name.equals("contact:addr") )
		{
			type = ((DOMElement *) node)->getAttribute(XS("type"));
			if( (type.isNotNull()) && type.equals("int") )
			{
				disc->set(TAG_ADDR_INT, true);
			}
			else if( (type.isNotNull()) && type.equals("loc") )
			{
				disc->set(TAG_ADDR_LOC, true);
			}
		}
//		else if( name.equals("voice") )
		else if( name.equals("voice") || name.equals("contact:voice") )
		{
			disc->set(TAG_VOICE, true);
		}
//		else if( name.equals("fax") )
		else if( name.equals("fax") || name.equals("contact:fax") )
		{
			disc->set(TAG_FAX, true);
		}
//		else if( name.equals("email") )
		else if( name.equals("email") || name.equals("contact:email") )
		{
			disc->set(TAG_EMAIL, true);
		}
	}
	return disc;
}
