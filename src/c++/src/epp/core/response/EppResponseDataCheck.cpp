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
#include "EppResponseDataCheck.hpp"
#include "EppUtil.hpp"
#include "XercesString.hpp"

const char * EppResponseDataCheck::UNKNOWN   = " ";
const char * EppResponseDataCheck::FOUND     = "+";
const char * EppResponseDataCheck::NOT_FOUND = "-";

DOMString EppResponseDataCheck::check( const DOMString& id )
{
	if( this->hashMap != null )
	{
		for( unsigned int i = 0; i < this->hashMap->size(); i++ )
		{
			DOMStringPtrPair* v = this->hashMap->elementAt(i);
			if( id.equals(*v->getKey()) )
			{
				return *v->getValue();
			}
		}
	}
	return DOMString(EppResponseDataCheck::UNKNOWN);
}

bool EppResponseDataCheck::isAvailable( const DOMString& id )
{
	if( this->hashMap != null )
	{
		for( unsigned int i = 0; i < this->hashMap->size(); i++ )
		{
			DOMStringPtrPair* v = this->hashMap->elementAt(i);
			if( id.equals(*v->getKey()) )
			{
				DOMString* t = v->getValue();
				if( (t->isNotNull()) && t->equals(EppResponseDataCheck::FOUND) )
				{
					return false;
				}
				return true;
			}
		}
	}
	return true;
}

DOMString EppResponseDataCheck::getReason( const DOMString& id )
{
	if( this->reasonHashMap != null )
	{
		for( unsigned int i = 0; i < this->reasonHashMap->size(); i++ )
		{
			DOMStringPtrPair* v = this->reasonHashMap->elementAt(i);
			if( id.equals(*v->getKey()) )
			{
				return *v->getValue();
			}
		}
	}
	return NULLXS;
}

void EppResponseDataCheck::toXMLCommon( DOMDocument& doc, DOMElement& body, const DOMString& tag )
{
	DOMElement* elm;
	DOMElement* cd;

	if( this->hashMap != null )
	{
		for( unsigned int i = 0; i < this->hashMap->size(); i++ )
		{
			DOMStringPtrPair* v = this->hashMap->elementAt(i);
			DOMString* key = v->getKey();

			if( key->isNotNull() && key->length() > 0 )
			{
				cd = doc.createElement(XS("cd"));
				elm = doc.createElement(tag);
				elm->appendChild(doc.createTextNode(*key));
				DOMString* t = v->getValue();

				if( (t->isNotNull()) && t->equals(EppResponseDataCheck::FOUND) )
				{
					elm->setAttribute(XS("avail"), XS("0"));
				}
				else
				{
					elm->setAttribute(XS("avail"), XS("1"));
				}

				cd->appendChild(elm);
				DOMString reason = this->getReason(*key);

				if( reason.isNotNull() && reason.length() > 0 )
				{
					elm = doc.createElement(XS("reason"));
					elm->appendChild(doc.createTextNode(reason));
					cd->appendChild(elm);
				}
				body.appendChild(cd);
			}
		}
	}
}

void EppResponseDataCheck::fromXMLCommon( const DOMNode& root, const DOMString& tag, const DOMString& ns )
{
	DOMString ns_tag = (DOMString)ns + ":"  + tag;
	DOMString ns_rsn = (DOMString)ns + ":reason";

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
		//		if( name.equals("cd") )
		if(    name.equals("cd") || name.equals("contact:cd")
			|| name.equals("domain:cd") || name.equals("host:cd")
			|| name.equals("svcsub:cd")
			|| name.equals("xriAU:cd")
			|| name.equals("xriINU:cd")
			|| name.equals("xriINA:cd")
			|| name.equals("xriISV:cd")
			|| name.equals("xriCS:cd") )
		{
			DOMNodeList* clist = node->getChildNodes();

			if( clist == null )
			{
				continue;
			}
			DOMString cd;
			DOMString id;
			bool flag = true;
			DOMString reason;

			for( unsigned j = 0; j < clist->getLength(); j++ )
			{
				DOMNode* cnode = clist->item(j);
				DOMString cname = cnode->getLocalName();

				if( cname.isNull() )
				{
					cname = cnode->getNodeName();
				}
				if( cname.isNull() )
				{
					continue;
				}
				if( cname.equals(tag) || cname.equals(ns_tag) )
				{
					id = EppUtil::getText(*cnode);
					cd = ((DOMElement *) cnode)->getAttribute(XS("avail"));
					if(    (cd.isNotNull())
						&& (    cd.equals("0")
						|| cd.equals("f")
						|| cd.equals("false") ) )
					{
						flag = false;
					}
					else
					{
						flag = true;
					}
				}
				else if( cname.equals("reason") || cname.equals(ns_rsn) )
				{
					reason = EppUtil::getText(*cnode);
				}
			}
			if( id.isNotNull() && id.length() > 0 )
			{
				this->add(id, flag, reason);
			}
		}
	}
}
