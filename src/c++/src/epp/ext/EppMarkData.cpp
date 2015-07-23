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
#include "EppMarkData.hpp"
#include "EppUtil.hpp"

EppMarkData::EppMarkData(const char* ns)
:EppMarkNameSpace(ns), Id(), _markName(), _holder(10), _contact(10), _label(10), goodsAndServices()
{
}

EppMarkData::EppMarkData(const EppMarkData& _src):Id(), _markName(), _holder(10), _contact(10), _label(10), goodsAndServices()
{

	this->Id        = _src.Id;
	this->_markName = _src._markName;

	for ( XMLSize_t i = 0; i < _src._holder.size(); i++ )
	{
		_holder.addElement(_src._holder.elementAt(i));
	}
	//this->_holder = _src._holder;

	for ( XMLSize_t i = 0; i < _src._contact.size(); i++ )
	{
		_contact.addElement(_src._contact.elementAt(i));
	}
	//this->_contact = _src._contact;

	this->_label = _src._label;
	this->goodsAndServices = _src.goodsAndServices;
}

EppMarkData::~EppMarkData()
{
	for ( XMLSize_t i = 0; i < this->_holder.size(); i++ )
	{
		EppMarkHolder* ptr = this->_holder.elementAt(i);
		delete ptr;
	}
	_holder.removeAllElements();

	for ( XMLSize_t i = 0; i < this->_contact.size(); i++ )
	{
		EppMarkHolder* ptr = this->_contact.elementAt(i);
		delete ptr;
	}
	_contact.removeAllElements();
}

EppMarkData& EppMarkData::operator=(const EppMarkData& _src)
{
	this->Id        = _src.Id;
	this->_markName = _src._markName;

	for ( XMLSize_t i = 0; i < _src._holder.size(); i++ )
	{
		_holder.addElement(_src._holder.elementAt(i));
	}
	//this->_holder = _src._holder;

	for ( XMLSize_t i = 0; i < _src._contact.size(); i++ )
	{
		_contact.addElement(_src._contact.elementAt(i));
	}
	//this->_contact = _src._contact;

	this->_label           = _src._label;
	this->goodsAndServices = _src.goodsAndServices;
	return *this;
}
/*13801497614*/

/*18602331234*/
void EppMarkData::id(const DOMString &_id)
{
	this->Id = _id;
}

DOMString EppMarkData::id(void)
{
	return this->Id;
}

void EppMarkData::markName(const DOMString& _name)
{
	this->_markName = _name;
}

DOMString EppMarkData::markName(void)
{
	return this->_markName;
}
// Not required
//void EppMarkData::addHolder(const EppMarkHolder &_h)
//{
//	this->_holder.addElement(_h);
//}

OldValueVectorOf<EppMarkHolder*> EppMarkData::holder(void)
{
	return this->_holder;
}
// Not required
//void EppMarkData::addContact(const EppMarkHolder &_c)
//{
//	this->_contact.addElement(_c);
//}

OldValueVectorOf<EppMarkHolder*> EppMarkData::contact(void)
{
	return this->_contact;
}

void EppMarkData::addLabel(const DOMString &_l)
{
	this->_label.addElement(_l);
}

OldValueVectorOf<DOMString> EppMarkData::labels(void)
{
	return this->_label;
}

void EppMarkData::goodsServices(const DOMString& _g)
{
	this->goodsAndServices = _g;
}

DOMString EppMarkData::goodsServices(void)
{
	return this->goodsAndServices;
}

DOMElement* EppMarkData::toXML(DOMDocument &doc, const DOMString &tag)
{
	//DOMElement* elm;
	DOMElement* body = doc.createElement(tag);
#if 0
	if( this->Id.length() > 0 )
	{
		elm = doc.createElement(XS("id");
			elm->appendChild(doc.createTextNode(this->Id));
			body->appendChild(elm);
	}
	if( this->_markName.length() > 0 )
	{
		elm = doc.createElement(XS("markName");
			elm->appendChild(doc.createTextNode(this->_markName));
			body->appendChild(elm);
	}
	for( int i = 0; i < _holder.size();i++ )
	{
		EppMarkHolder _h = _holder.elementAt(i);
			body->appendChild(_h.toXML( doc, "holder"));
	}
	for( int i = 0; i < _contact.size();i++ )
	{
		EppMarkHolder _c = _contact.elementAt(i);
			body->appendChild(_c.toXML( doc, "contact"));
	}
	for( int i = 0; i < _label.size();i++ )
	{
		DOMString _s = _label.elementAt(i);
		if( _s.length() > 0 )
		{
			elm = doc.createElement(XS("label");
				elm->appendChild(doc.createTextNode(_s));
				body->appendChild(elm);
		}
	}
	if( this->goodsAndServices.length() > 0 )
	{

		elm = doc.createElement(XS("goodsAndServices");
			elm->appendChild(doc.createTextNode(this->goodsAndServices));
			body->appendChild(elm);
	}
#endif
	return body;
}

void EppMarkData::fromXml( const DOMNode& root )
{

	int nsLen = getNameSpace().size();

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
		if( name.substringData(0, nsLen + 1).equals((getNameSpace() + ":").c_str()) )
		{
			name = name.substringData(nsLen + 1, name.length() - (nsLen + 1));
		}
		if( name.equals("id") )
		{
			this->Id = EppUtil::getText(*node);
		}
		else if( name.equals("markName") )
		{
			this->_markName = EppUtil::getText(*node);
		}
		else if( name.equals("holder") )
		{
			EppMarkHolder *_h = EppMarkHolder::fromXML(*node, getNameSpace().c_str());
			if( NULL != _h )
			{
				this->_holder.addElement(_h);
					//delete _h;
			}
		}
		else if( name.equals("contact") )
		{
			EppMarkHolder *_c = EppMarkHolder::fromXML(*node, getNameSpace().c_str());
			if( NULL != _c )
			{
				this->_contact.addElement(_c);
					//delete _c;
			}
		}
		else if( name.equals("goodsAndServices") )
		{
			this->goodsAndServices = EppUtil::getText(*node);
		}
		else if( name.equals("label") )
		{
			this->_label.addElement(EppUtil::getText(*node));
		}
	}
}
