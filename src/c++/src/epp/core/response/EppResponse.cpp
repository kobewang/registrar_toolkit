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
#include <stdio.h>
#include <stdlib.h>
#include "EppResponse.hpp"
#include "EppUtil.hpp"
#include "EppExtension.hpp"

EppResponse::~EppResponse()
{
	if( this->result != null )
	{
		for( unsigned int i = 0; i < this->result->size(); i++ )
		{
			EppResult * p = this->result->elementAt(i);
			if( p != null )
			{
				delete p;
			}
		}
		delete this->result;
		this->result = null;
	}
	if( this->extension != null )
	{
		for( unsigned int i = 0; i < this->extension->size(); i++ )
		{
			EppEntity * p = this->extension->elementAt(i);
			if( p != null )
			{
				delete p;
			}
		}
		delete this->extension;
		this->extension = null;
	}

	if( this->resData != null )
	{
		if( this->freeable )
		{
			delete this->resData;
		}
		this->resData = null;
	}
	if( this->trID != null )
	{
		delete this->trID;
		this->trID = null;
	}
}

bool EppResponse::success()
{
	if( result == null )
	{
		return false;
	}

	if( result->size() != 1 )
	{
		return false;
	}

	EppResult * r = result->elementAt(0);

	if( (r != null) && EppError::isError(r->getCode()) )
	{
		return false;
	}

	return true;
}

DOMElement* EppResponse::toXML( DOMDocument& doc, const DOMString& tag )
{
	DOMElement* elm;
	DOMElement* epp = EppUtil::createElementNS(doc, "epp", NULLXS);
	DOMElement* body = doc.createElement(tag);
	epp->appendChild(body);

	if( result != null )
	{
		for( unsigned int i = 0; i < result->size(); i++ )
		{
			EppResult * obj = result->elementAt(i);
			if( obj != null )
			{
				body->appendChild(obj->toXML(doc, "result"));
			}
		}
	}

	if( (msgQCount > 0) || (msgId.isNotNull()) || (msgQDate != 0) || (msgQText.isNotNull()) )
	{
		elm = doc.createElement(XS("msgQ"));
		if( msgId.isNotNull() )
		{
			elm->setAttribute(XS("id"), msgId);
		}
		if( msgQCount >= 0 )
		{
			char buf[16];

			(void) sprintf(buf, "%d", msgQCount);

			elm->setAttribute(XS("count"), XS(buf));
		}
		if( msgQDate != 0 )
		{
			DOMElement* d = doc.createElement(XS("qDate"));
			d->appendChild(EppUtil::createTextNode(doc, msgQDate));
			elm->appendChild(d);
		}
		if( msgQText.isNotNull() )
		{
			DOMElement* txt = doc.createElement(XS("msg"));
			txt->appendChild(doc.createTextNode(msgQText));
			elm->appendChild(txt);
		}
		body->appendChild(elm);
	}

	if( resData != null )
	{
		body->appendChild(resData->toXML(doc, "resData"));
	}

	if( extension != null )
	{
		elm = null;
		for( unsigned int i = 0; i < extension->size(); i++ )
		{
			EppEntity * obj = extension->elementAt(i);
			DOMElement* ext;
			if( obj == null )
			{
				continue;
			}
			if( obj->getEntityType() == EppEntity::TYPE_EppExtensionUnspec )
			{
				EppUnspec * unspec = (EppUnspec *) obj;
				ext = unspec->toXML(doc, "extension");
			}
			else if( obj->instanceOf(EppEntity::TYPE_EppExtension) == true )
			{
				EppExtension * extobj = (EppExtension *) obj;
				ext = extobj->toXML(doc, NULLXS);
			}
			else
			{
				ext = obj->toXML(doc, "extension");
			}
			if( ext != null )
			{
				if( elm == null )
				{
					elm = doc.createElement(XS("extension"));
					body->appendChild(elm);
				}
				elm->appendChild(ext);
			}
		}
	}

	if( trID != null )
	{
		body->appendChild(trID->toXML(doc, "trID"));
	}

	return epp;
}

EppResponse * EppResponse::fromXML( const DOMNode& root )
{
	unsigned int i;
	EppResponse * rsp = null;
	DOMNode* response;
	bool found = false;

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
		if( name.equals("response") )
		{
			response = node;
			found = true;
			break;
		}
	}

	if( found == false )
	{
		return null;
	}

	rsp = new EppResponse();
	list = response->getChildNodes();
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
		if( name.equals("result") )
		{
			EppResult * r = EppResult::fromXML(*node);
			if( r != null )
			{
				rsp->result->addElement(r);
			}
		}
		else if( name.equals("msgQ") )
		{
			DOMElement * elm = (DOMElement *) node;
			DOMString id = elm->getAttribute(XS("id"));
			if( id.isNotNull() && id.length() > 0 )
			{
				rsp->msgId = id;
			}
			DOMString str = elm->getAttribute(XS("count"));
			if( str.isNotNull() && str.length() > 0 )
			{
				char * p = str.transcode();
				if( p != null )
				{
					rsp->msgQCount = atoi(p);
					XercesString::Delete(p);
				}
			}

			DOMNodeList* qlist = node->getChildNodes();
			for( unsigned int j = 0; j < qlist->getLength(); j++ )
			{
				node = qlist->item(j);
				name = node->getLocalName();

				if( name.isNull() )
				{
					name = node->getNodeName();
				}
				if( name.isNull() )
				{
					continue;
				}
				if( name.equals("qDate") )
				{
					rsp->msgQDate = EppUtil::getDate(*node);
				}
				else if( name.equals("msg") )
				{
					rsp->msgQText = EppUtil::getText(*node);
				}
			}
		}
		else if( name.equals("resData") )
		{
			if( rsp->resData == null )
			{
				rsp->resData = EppResponseData::fromXML(*node);
				rsp->freeable = true;
			}
		}
		else if( name.equals("extension") )
		{
			DOMNodeList* clist = node->getChildNodes();
			for( unsigned int j = 0; j < clist->getLength(); j++ )
			{
				DOMNode* cnode   = clist->item(j);
				DOMString prefix = cnode->getPrefix();
				DOMString cname  = cnode->getLocalName();

				if( cname.isNull() )
				{
					cname = cnode->getNodeName();
				}
				if( cname.isNull() )
				{
					continue;
				}
				if(     cname.equals("neulevel:extension")
					|| (    (prefix.isNotNull()) && prefix.equals("neulevel")
					&& (cname.isNotNull()) && cname.equals("extension") ) )
				{
					EppUnspec * unspec = EppUnspec::fromXML(*cnode);
					if( unspec != null )
					{
						rsp->addExtension(unspec);
					}
				}
				else if( cname.isNotNull() )
				{
					EppExtension * ext = EppExtension::fromXML(*cnode);
					if( ext != null )
					{
						rsp->addExtension(ext);
					}
					// else other extension
				}
				else
				{
					// other extensions
				}
			}
		}
		else if( name.equals("trID") )
		{
			if( rsp->trID == null )
			{
				rsp->trID = EppTransactionId::fromXML(*node);
			}
		}
	}

	return rsp;
}
