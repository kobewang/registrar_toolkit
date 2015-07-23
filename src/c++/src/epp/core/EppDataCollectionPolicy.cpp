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
#include "EppDataCollectionPolicy.hpp"
#include "EppUtil.hpp"

const char * EppDataCollectionPolicy::ACCESS_ALL                = "all";
const char * EppDataCollectionPolicy::ACCESS_NONE               = "none";
const char * EppDataCollectionPolicy::ACCESS_NULL               = "null";
const char * EppDataCollectionPolicy::ACCESS_PERSONAL           = "personal";
const char * EppDataCollectionPolicy::ACCESS_PERSONAL_AND_OTHER = "personalAndOther";
const char * EppDataCollectionPolicy::ACCESS_OTHER              = "other";

DOMElement* EppDataCollectionPolicy::toXML( DOMDocument& doc, const DOMString& tag )
{
	DOMElement* body = doc.createElement(tag);
	DOMElement* elm;
	DOMElement* exp;

	if( this->access.isNotNull() )
	{
		elm = doc.createElement(XS("access"));
		elm->appendChild(doc.createElement(this->access));
		body->appendChild(elm);
	}
	if( this->statement != null )
	{
		for( unsigned int i = 0; i < this->statement->size(); i++ )
		{
			EppDataCollectionStatement stmt = this->statement->elementAt(i);
			elm = stmt.toXML(doc, "statement");
			body->appendChild(elm);
		}
	}
	if( this->expiry_absolute != null )
	{
		elm = doc.createElement(XS("expiry"));
		exp = doc.createElement(XS("absolute"));
		exp->appendChild(EppUtil::createTextNode(doc, this->expiry_absolute));
		elm->appendChild(exp);
		body->appendChild(elm);
	}
	else if( this->expiry_relative.isNotNull() )
	{
		elm = doc.createElement(XS("expiry"));
		exp = doc.createElement(XS("relative"));
		exp->appendChild(doc.createTextNode(this->expiry_relative));
		elm->appendChild(exp);
		body->appendChild(elm);
	}

	return body;
}

EppDataCollectionPolicy * EppDataCollectionPolicy::fromXML( const DOMNode& root )
{
	EppDataCollectionPolicy * policy = new EppDataCollectionPolicy();
	if( policy == null )
	{
		return policy;
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
		if( name.equals("access") )
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
				if(    cname.equals("all")
					|| cname.equals("none")
					|| cname.equals("null")
					|| cname.equals("personal")
					|| cname.equals("personalAndOther")
					|| cname.equals("other") )
				{
					policy->setAccess(cname);
				}
			}
		}
		else if( name.equals("statement") )
		{
			EppDataCollectionStatement * stmt;

			stmt = EppDataCollectionStatement::fromXML(*node);

			if( stmt != null )
			{
				policy->addStatement(*stmt);
				delete stmt;
			}
		}
		else if( name.equals("expiry") )
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
				if( cname.equals("absolute") )
				{
					time_t ts = EppUtil::getDate(*cnode);
					if( ts != null )
					{
						policy->setExpiry(ts);
					}
				}
				else if( cname.equals("relative") )
				{
					DOMString dp = EppUtil::getText(*cnode);
					if( dp.isNotNull() )
					{
						policy->setExpiry(dp);
					}
				}
			}
		}
	}
	return policy;
}
