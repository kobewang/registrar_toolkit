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
#include "EppResult.hpp"
#include "EppUtil.hpp"

DOMElement* EppResult::toXML( DOMDocument& doc, const DOMString& tag )
{
	DOMElement* body = doc.createElement(tag);
	DOMElement* elm;
	char buf[16];

	(void) sprintf(buf, "%d", code);

	body->setAttribute(XS("code"), XS(buf));

	if( msg != null )
	{
		body->appendChild(msg->toXML(doc, "msg"));
	}

	if( (value != null) && (value->size() > 0) )
	{
		for( unsigned int i = 0; i < value->size(); i++ )
		{
			EppValueReason obj = value->elementAt(i);
			if(    ((obj.getReason().isNull())   || (obj.getReason().length()   == 0))
				&& ((obj.getLanguage().isNull()) || (obj.getLanguage().length() == 0)) )
			{
				elm = obj.toXML(doc, "value");
			}
			else
			{
				elm = obj.toXML(doc, "extValue");
			}
			body->appendChild(elm);
		}
	}

	return body;
}

EppResult * EppResult::fromXML( const DOMNode& root )
{
	EppResult * result = new EppResult();
	DOMElement * elm = (DOMElement *) &root;

	DOMString code = elm->getAttribute(XS("code"));
	if( code.isNotNull() )
	{
		char * p = code.transcode();
		if( p != null )
		{
			result->code = atoi(p);
			XercesString::Delete(p);
		}
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
		if( name.equals("msg") )
		{
			result->msg = EppResultMessage::fromXML(*node);
		}
		else if( name.equals("value") )
		{
			EppValueReason * v = EppValueReason::fromXML(*node);
			if( v != null )
			{
				result->addValue(*v);
				delete v;
			}
		}
		else if( name.equals("extValue") )
		{
			EppValueReason * v = EppValueReason::fromXML(*node);
			if( v != null )
			{
				result->addValue(*v);
				delete v;
			}
		}
	}
	return result;
}

EppResult * EppResult::clone()
{
	EppResult * p = new EppResult();
	if( p == null )
	{
		return null;
	}
	p->code = this->code;
	if( this->msg != null )
	{
		p->setMessage(this->msg);
	}
	if( this->value != null )
	{
		for( unsigned int i = 0; i < this->value->size(); i++ )
		{
			EppValueReason s = this->value->elementAt(i);
			p->addValue(s);
		}
	}

	return p;
}

void EppResult::setMessage( EppResultMessage * message )
{
	if( message == null )
	{
		return;
	}
	if( this->msg == null )
	{
		this->msg = new EppResultMessage();
	}
	if( this->msg != null )
	{
		this->msg->setId(message->getId());
		this->msg->setLanguage(message->getLanguage());
		this->msg->setMessage(message->getMessage());
	}
}
