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
#include "EppServiceMenu.hpp"
#include "EppUtil.hpp"

DOMElement* EppServiceMenu::toXML( DOMDocument& doc, const DOMString& tag )
{
	DOMElement* elm;
	DOMElement* body = doc.createElement(tag);

	if( version != null )
	{
		for( unsigned int i = 0; i < version->size(); i++ )
		{
			DOMString obj = version->elementAt(i);
			if( obj.isNotNull() && obj.length() > 0 )
			{
				elm = doc.createElement(XS("version"));
				elm->appendChild(doc.createTextNode(obj));
				body->appendChild(elm);
			}
		}
	}
	if( lang != null )
	{
		for( unsigned int i = 0; i < lang->size(); i++ )
		{
			DOMString obj = lang->elementAt(i);
			if( obj.isNotNull() && obj.length() > 0 )
			{
				elm = doc.createElement(XS("lang"));
				elm->appendChild(doc.createTextNode(obj));
				body->appendChild(elm);
			}
		}
	}
	if( objURI != null )
	{
		for( unsigned int i = 0; i < objURI->size(); i++ )
		{
			DOMString obj = objURI->elementAt(i);

			if( obj.isNotNull() && obj.length() > 0 )
			{
				elm = doc.createElement(XS("objURI"));
				elm->appendChild(doc.createTextNode(obj));
				body->appendChild(elm);
			}
		}
	}

	if( (extURI != null) && (extURI->size() > 0) )
	{
		elm = null;
		for( unsigned int i = 0; i < extURI->size(); i++ )
		{
			DOMString obj = extURI->elementAt(i);
			if( obj.isNotNull() && obj.length() > 0 )
			{
				DOMElement* ext = doc.createElement(XS("extURI"));
				ext->appendChild(doc.createTextNode(obj));
				if( elm == null )
				{
					elm = doc.createElement(XS("svcExtension"));
					body->appendChild(elm);
				}
				elm->appendChild(ext);
			}
		}
	}
	return body;
}

EppServiceMenu * EppServiceMenu::fromXML( const DOMNode& root )
{
	EppServiceMenu * svcmenu = new EppServiceMenu();
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
		if( name.equals("version") )
		{
			svcmenu->addVersion(EppUtil::getText(*node));
		}
		else if( name.equals("lang") )
		{
			svcmenu->addLanguage(EppUtil::getText(*node));
		}
		else if( name.equals("objURI") )
		{
			DOMString uri = EppUtil::getText(*node);
			if( uri.isNotNull() && uri.length() > 0 )
			{
				svcmenu->addService(uri);
			}
		}
		else if( name.equals("svcExtension") )
		{
			DOMNodeList* ulist = node->getChildNodes();
			for( unsigned int j = 0; j < ulist->getLength(); j++ )
			{
				DOMNode* unode = ulist->item(j);
				name = unode->getLocalName();
				if( name.isNull() )
				{
					name = unode->getNodeName();
				}
				if( name.isNull() )
				{
					continue;
				}
				else if( name.equals("extURI") )
				{
					DOMString ext = EppUtil::getText(*unode);
					if( ext.isNotNull() && ext.length() > 0 )
					{
						svcmenu->addServiceExtension(ext);
					}
				}
			}
		}
	}

	return svcmenu;
}
