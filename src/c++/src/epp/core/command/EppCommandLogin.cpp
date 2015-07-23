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
#include "EppCommandLogin.hpp"
#include "EppUtil.hpp"

void EppCommandLogin::setServiceMenu( EppServiceMenu& serviceMenu )
{
	if( this->svcs != null )
	{
		delete this->svcs;
	}
	this->svcs = new EppServiceMenu();
	OldValueVectorOf<DOMString> * mySvc = this->svcs->getService();
	OldValueVectorOf<DOMString> * svc = serviceMenu.getService();
	for( unsigned int i = 0; (svc != null) && (i < svc->size()); i++ )
	{
		mySvc->addElement(svc->elementAt(i));
	}
	OldValueVectorOf<DOMString> * myExt = this->svcs->getServiceExtension();
	OldValueVectorOf<DOMString> * ext = serviceMenu.getServiceExtension();
	for( unsigned int j = 0; (ext != null) && (j < ext->size()); j++ )
	{
		myExt->addElement(ext->elementAt(j));
	}

}

DOMElement* EppCommandLogin::toXML( DOMDocument& doc, const DOMString& tag )
{
	DOMElement* body = doc.createElement(tag);
	DOMElement* elm;
	if( clID.isNotNull() )
	{
		elm = doc.createElement(XS("clID"));
		elm->appendChild(doc.createTextNode(clID));
		body->appendChild(elm);
	}
	if( pw.isNotNull() )
	{
		elm = doc.createElement(XS("pw"));
		elm->appendChild(doc.createTextNode(pw));
		body->appendChild(elm);
	}
	if( newPW.isNotNull() )
	{
		elm = doc.createElement(XS("newPW"));
		elm->appendChild(doc.createTextNode(newPW));
		body->appendChild(elm);
	}
	if( options != null )
	{
		elm = options->toXML(doc, "options");
		body->appendChild(elm);
	}
	if( svcs != null )
	{
		elm = svcs->toXML(doc, "svcs");
		body->appendChild(elm);
	}

	return toXMLCommon(doc, tag, *body);
}

EppCommandLogin * EppCommandLogin::fromXML( const DOMNode& root )
{
	EppCommandLogin * cmd = null;
	unsigned int i;
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
		if( name.equals("svcs") )
		{
			EppServiceMenu * menu = EppServiceMenu::fromXML(*node);
			if( menu != null )
			{
				cmd = new EppCommandLogin(*menu);
				delete menu;
				break;
			}
		}
	}
	if( cmd == null )
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
		if( name.equals("clID") )
		{
			cmd->clID = EppUtil::getText(*node);
		}
		else if( name.equals("pw") )
		{
			cmd->pw = EppUtil::getText(*node);
		}
		else if( name.equals("newPW") )
		{
			cmd->newPW = EppUtil::getText(*node);
		}
		else if( name.equals("options") )
		{
			EppCredsOptions * opt = EppCredsOptions::fromXML(*node);
			if( opt != null )
			{
				cmd->setOptions(*opt);
				delete opt;
			}
		}
	}

	return cmd;
}
