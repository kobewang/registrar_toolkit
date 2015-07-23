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
#include "EppDataCollectionStatement.hpp"
#include "EppUtil.hpp"

const char * EppDataCollectionStatement::PURPOSE_ADMIN        = "admin";
const char * EppDataCollectionStatement::PURPOSE_CONTACT      = "contact";
const char * EppDataCollectionStatement::PURPOSE_PROV         = "prov";
const char * EppDataCollectionStatement::PURPOSE_OTHER        = "other";

const char * EppDataCollectionStatement::RECIPIENT_OTHER      = "other";
const char * EppDataCollectionStatement::RECIPIENT_OURS       = "ours";
const char * EppDataCollectionStatement::RECIPIENT_PUBLIC     = "public";
const char * EppDataCollectionStatement::RECIPIENT_SAME       = "same";
const char * EppDataCollectionStatement::RECIPIENT_UNRELATED  = "unrelated";

const char * EppDataCollectionStatement::RETENTION_BUSINESS   = "business";
const char * EppDataCollectionStatement::RETENTION_INDEFINITE = "indefinite";
const char * EppDataCollectionStatement::RETENTION_LEGAL      = "legal";
const char * EppDataCollectionStatement::RETENTION_NONE       = "none";
const char * EppDataCollectionStatement::RETENTION_STATED     = "stated";

DOMElement* EppDataCollectionStatement::toXML( DOMDocument& doc, const DOMString& tag )
{
	DOMElement* body = doc.createElement(tag);
	DOMElement* elm;

	elm = doc.createElement(XS("purpose"));
	body->appendChild(elm);

	if( this->purpose_admin == true )
	{
		elm->appendChild(doc.createElement(XS("admin")));
	}
	if( this->purpose_contact == true )
	{
		elm->appendChild(doc.createElement(XS("contact")));
	}
	if( this->purpose_other == true )
	{
		elm->appendChild(doc.createElement(XS("other")));
	}
	if( this->purpose_prov == true )
	{
		elm->appendChild(doc.createElement(XS("prov")));
	}
	elm = doc.createElement(XS("recipient"));
	body->appendChild(elm);

	if( this->recipient_other == true )
	{
		elm->appendChild(doc.createElement(XS("other")));
	}
	if( this->recipient_ours == true )
	{
		DOMElement* ours = doc.createElement(XS("ours"));
		if( this->recipient_ours_rec_desc.isNotNull() )
		{
			DOMElement* desc = doc.createElement(XS("recDesc"));
			desc->appendChild(doc.createTextNode(this->recipient_ours_rec_desc));
			ours->appendChild(desc);
		}
		elm->appendChild(ours);
	}
	if( this->recipient_public == true )
	{
		elm->appendChild(doc.createElement(XS("public")));
	}
	if( this->recipient_same == true )
	{
		elm->appendChild(doc.createElement(XS("same")));
	}
	if( this->recipient_unrelated == true )
	{
		elm->appendChild(doc.createElement(XS("unrelated")));
	}
	if( this->retention.isNotNull() )
	{
		elm = doc.createElement(XS("retention"));
		elm->appendChild(doc.createElement(this->retention));
		body->appendChild(elm);
	}

	return body;
}

EppDataCollectionStatement * EppDataCollectionStatement::fromXML( const DOMNode& root )
{
	EppDataCollectionStatement * stmt = new EppDataCollectionStatement();
	if( stmt == null )
	{
		return stmt;
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
		if( name.equals("purpose") )
		{
			DOMNodeList* clist = node->getChildNodes();
			for( unsigned int j = 0; j < clist->getLength(); j++ )
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
				if(    cname.equals(PURPOSE_ADMIN)
					|| cname.equals(PURPOSE_CONTACT)
					|| cname.equals(PURPOSE_PROV)
					|| cname.equals(PURPOSE_OTHER) )
				{
					stmt->setPurpose(cname, true);
				}
			}
		}
		else if( name.equals("recipient") )
		{
			DOMNodeList* clist = node->getChildNodes();
			for( unsigned int j = 0; j < clist->getLength(); j++ )
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
				if(    cname.equals(RECIPIENT_OTHER)
					|| cname.equals(RECIPIENT_OURS)
					|| cname.equals(RECIPIENT_PUBLIC)
					|| cname.equals(RECIPIENT_SAME)
					|| cname.equals(RECIPIENT_UNRELATED) )
				{
					stmt->setRecipient(cname, true);
				}
				else
				{
					continue;
				}
				if( cname.equals(RECIPIENT_OURS) == false )
				{
					continue;
				}
				DOMNodeList* dlist = cnode->getChildNodes();
				for( unsigned int k = 0; k < dlist->getLength(); k++ )
				{
					DOMNode* dnode = dlist->item(k);
					DOMString dname = dnode->getLocalName();

					if( dname.isNull() )
					{
						dname = dnode->getNodeName();
					}
					if( dname.isNull() )
					{
						continue;
					}
					if( dname.equals("recDesc") == false )
					{
						continue;
					}
					DOMString desc = EppUtil::getText(*node);
					stmt->setOursRecipientDesc(desc);
					break;
				}
			}
		}
		else if( name.equals("retention") )
		{
			DOMNodeList* clist = node->getChildNodes();
			for( unsigned int j = 0; j < clist->getLength(); j++ )
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
				if(    cname.equals(RETENTION_BUSINESS)
					|| cname.equals(RETENTION_INDEFINITE)
					|| cname.equals(RETENTION_LEGAL)
					|| cname.equals(RETENTION_NONE)
					|| cname.equals(RETENTION_STATED) )
				{
					stmt->setRetention(cname);
				}
			}
		}
	}
	return stmt;
}
