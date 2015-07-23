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

#include "EppChangePollExt.hpp"
#include "EppUtil.hpp"

EppChangePollExt::EppChangePollExt()
: state("after")
{
	reason    = null;
	operation = null;
	date      = null;
}

EppChangePollExt::~EppChangePollExt()
{
	if ( this->reason != null )
	{
		delete this->reason;
		this->reason = null;
	}
	if ( this->operation != null )
	{
		delete this->operation;
		this->operation = null;
	}
	date = null;
}

DOMElement* EppChangePollExt::toXML(DOMDocument &doc, const DOMString &tag)
{
	DOMElement* body = EppUtil::createElementNS(doc, "changePoll", "changeData");

	DOMElement* elm = null;

	if ( this->state.length() > 0 )
	{
		body->setAttribute(XS("state"), this->state);
	}

	if ( this->operation != null )
	{
		elm = this->operation->toXML(doc, "operation");
		if ( elm != null )
		{
			body->appendChild(elm);
		}
	}

	if ( this->date != null )
	{
		elm = doc.createElement(XS("date"));
		elm->appendChild(EppUtil::createTextNode(doc, date));
		body->appendChild(elm);
	}

	if ( this->svTRID.length() > 0 )
	{
		elm = doc.createElement(XS("svTRID"));
		elm->appendChild(doc.createTextNode(this->svTRID));
		body->appendChild(elm);
	}

	if ( this->who.length() > 0 )
	{
		elm = doc.createElement(XS("who"));
		elm->appendChild(doc.createTextNode(this->who));
		body->appendChild(elm);
	}

	if( this->reason != null )
	{
		if ( reason != NULL )
		{
			elm = reason->toXML(doc, "reason");
			if ( elm != null )
			{
				body->appendChild(elm);
			}
		}
	}

	return body;
}

EppChangePollExt * EppChangePollExt::fromXML( const DOMNode& root )
{
	DOMNodeList* list = root.getChildNodes();

	if ( list == NULL )
	{
		return null;
	}

	EppChangePollExt * ext = new EppChangePollExt();

	for( unsigned int i = 0; i < list->getLength(); i++ )
	{
		DOMNode* node = list->item(i);
		if ( node == NULL )
		{
			continue;
		}

		DOMString name = node->getLocalName();

		if( name.isNull() )
		{
			name = node->getNodeName();
		}

		if( name.isNull() )
		{
			continue;
		}

		if( name.equals("operation") || name.equals("changePoll:operation") )
		{
			EppChangePollOperation* oper = EppChangePollOperation::fromXML(*node);	
			ext->setOperation(oper);
		}
		else if( name.equals("date") || name.equals("changePoll:date") )
		{
			ext->setDate(EppUtil::getDate(*node));
		}
		else if( name.equals("svTRID") || name.equals("changePoll:svTRID") )
		{
			ext->setSvTRID(EppUtil::getText(*node));
		}
		else if( name.equals("who") || name.equals("changePoll:who") )
		{
			ext->setWho(EppUtil::getText(*node));
		}
		else if( name.equals("reason") || name.equals("changePoll:reason") )
		{
			EppReason* reason = EppReason::fromXML(*node);

			if ( reason != null )
			{
				ext->setReason(reason);
			}
		}
	}

	DOMNamedNodeMap* attrs = root.getAttributes();

	if ( attrs == NULL )
	{
		return ext;
	}

	for ( unsigned int i = 0; i < attrs->getLength(); i++ )
	{
		DOMNode* attr = attrs->item(i);

		if ( attr == NULL )
		{
			continue;
		}

		DOMString name = attr->getNodeName();

		if( name.equals("state") )
		{
			ext->setState(attr->getNodeValue());
			break;
		}
	}

	return ext;
}

void EppChangePollExt::setState(const DOMString& st)
{
	this->state = st;
}

DOMString EppChangePollExt::getState() const
{
	return this->state; 
}

void EppChangePollExt::setOperation(EppChangePollOperation* op)
{
	if ( this->operation != null )
	{
		delete this->operation;
	}
	this->operation = op;
}

EppChangePollOperation* EppChangePollExt::getOperation() const
{
	return this->operation;
}

void EppChangePollExt::setDate(time_t dt)
{
	this->date = dt;
}

time_t EppChangePollExt::getDate() const
{
	return this->date;
}

void EppChangePollExt::setSvTRID(const DOMString& trid)
{
	this->svTRID = trid;
}

DOMString EppChangePollExt::getSvTRID() const
{
	return this->svTRID;
}

void EppChangePollExt::setWho(const DOMString& wh)
{
	this->who = wh;
}

DOMString EppChangePollExt::getWho() const
{
	return this->who; 
}

void EppChangePollExt::setReason(EppReason* reas)
{
	if ( this->reason != null )
	{ 
		delete this->reason;
	}
	this->reason = reas;
}

EppReason* EppChangePollExt::getReason() const
{
	return this->reason;
}
