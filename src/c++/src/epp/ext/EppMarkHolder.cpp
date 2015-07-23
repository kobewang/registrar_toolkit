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
#include "EppMarkHolder.hpp"
#include "EppUtil.hpp"

EppMarkHolder::EppMarkHolder( holderType _t, const char* ns)
: EppMarkNameSpace(ns), _name(), _org(), _addr(ns), _voice(), _fax(), _email(), _addParam(), _type(_t)
{
}

EppMarkHolder::EppMarkHolder( const EppMarkHolder &_src)
: _addr(_src.getNameSpace().c_str())
{
	this->_name = _src._name;
	this->_org = _src._org;
	this->_addr = _src._addr;
	this->_voice = _src._voice;
	this->_fax = _src._fax;
	this->_email = _src._email;
	this->_addParam = _src._addParam;
	this->_type = _src._type;

}

EppMarkHolder::~EppMarkHolder()
{
}

EppMarkHolder& EppMarkHolder::operator=(const EppMarkHolder& _src)
{
	this->_name = _src._name;
	this->_org = _src._org;
	this->_addr = _src._addr;
	this->_voice = _src._voice;
	this->_fax = _src._fax;
	this->_email = _src._email;
	this->_addParam = _src._addParam;
	this->_type = _src._type;
	return *this;
}

void EppMarkHolder::name(const DOMString& _n)
{
	this->_name = _n;
}

DOMString EppMarkHolder::name(void)
{
	return this->_name;
}

void EppMarkHolder::org(const DOMString& _o)
{
	this->_org = _o;
}

DOMString EppMarkHolder::org(void)
{
	return this->_org;
}

void EppMarkHolder::address(const EppMarkAddress &_a)
{
	this->_addr = _a;
}

EppMarkAddress EppMarkHolder::address(void)
{
	return this->_addr;
}

void EppMarkHolder::voice(const DOMString& _v)
{
	this->_voice.setNumber(_v);
}

DOMString EppMarkHolder::voice(void)
{
	return this->_voice.getNumber();
}

void EppMarkHolder::fax(const DOMString& _f)
{
	this->_fax.setNumber(_f);
}

DOMString EppMarkHolder::fax(void)
{
	return this->_fax.getNumber();
}

void EppMarkHolder::email(const DOMString& _e)
{
	this->_email = _e;
}

DOMString EppMarkHolder::email(void)
{
	return this->_email;
}

void EppMarkHolder::type(holderType _t)
{
	this->_type = _t;
}

holderType EppMarkHolder::type(void)
{
	return this->_type;
}

void EppMarkHolder::holderParam(const DOMString &_s)
{
	this->_addParam = _s;
}

DOMString EppMarkHolder::holderParam(void)
{
	return this->_addParam;
}

/* NOTE: need to keep _type field in consideration as attribute type/entitlement depends on it.*/
DOMElement* EppMarkHolder::toXML(DOMDocument &doc, const DOMString &tag)
{
	DOMString attr_name = "type";
	DOMElement* elm;
	DOMElement* body = doc.createElement(tag);

	if( this->_type == EPPMARK_HOLDER )
	{
		attr_name = "entitlement";
	}
	DOMAttr* attr = doc.createAttribute(attr_name);
	attr->setValue( this->_addParam );
	body->setAttributeNode(attr);

	if( this->_name.length() > 0 )
	{
		elm = doc.createElement(XS("name"));
		elm->appendChild(doc.createTextNode(this->_name));
		body->appendChild(elm);
	}
	if( this->_org.length() > 0 )
	{
		elm = doc.createElement(XS("org"));
		elm->appendChild(doc.createTextNode(this->_org));
		body->appendChild(elm);
	}
	body->appendChild(this->_addr.toXML(doc, "addr"));

	if( this->_voice.getNumber().length() > 0 )
	{
		elm = this->_voice.toXML(doc, "voice");
		body->appendChild(elm);
	}
	if( this->_fax.getNumber().length() > 0 )
	{
		elm = this->_fax.toXML(doc, "fax");
		body->appendChild(elm);
	}
	if( this->_email.length() > 0 )
	{
		elm = doc.createElement(XS("email"));
		elm->appendChild(doc.createTextNode(this->_email));
		body->appendChild(elm);
	}
	return body;
}

/* NOTE: need to keep _type field in consideration as attribute type/entitlement depends on it.*/
EppMarkHolder* EppMarkHolder::fromXML( const DOMNode& root, const char* ns )
{
	/*
	  this->_name = _src._name;
	  this->_org = _src._org;
	  this->_addr = _src._addr;
	  this->_voice = _src._voice;
	  this->_fax = _src._fax;
	  this->_email = _src._email;
	  this->_addParam = _src._addParam;
	  this->_type = _src._type;

	 */
	EppMarkHolder *_ret = new EppMarkHolder(EPPMARK_HOLDER, ns);
	//_ret->setNameSpace(ns);
	int nsLen = strlen(ns);

	if( null == _ret )
		return null;
	DOMNodeList* list  = root.getChildNodes();
	DOMNamedNodeMap* attrs = root.getAttributes();

	for( unsigned int i = 0; i < list->getLength(); i++ )
	{
		DOMNode* node = list->item(i);
		DOMString name = node->getLocalName();
		if( name.isNull() )
		{
			name = node->getNodeName();
		}
		if( name.isNull() )
			continue;
		if( name.substringData(0, nsLen + 1).equals((_ret->getNameSpace() + ":").c_str()) )
		{
			name = name.substringData(nsLen + 1, name.length() - ( nsLen + 1 ));
			if( name.equals("name") )
			{
				_ret->_name = EppUtil::getText(*node);
			}
			else if ( name.equals("org") )
			{
				_ret->_org = EppUtil::getText(*node);
			}
			else if ( name.equals("addr") )
			{
				EppMarkAddress* mAddr = (EppMarkAddress*)EppMarkAddress::fromXML( *node, ns );
				_ret->_addr = *mAddr;
				delete mAddr;
			}
			else if ( name.equals("voice") )
			{
				EppE164 *_v = EppE164::fromXML(*node);
				if( NULL != _v )
				{
					_ret->_voice = *_v;
					delete _v;
				}
			}
			else if ( name.equals("fax") )
			{
				EppE164 *_f = EppE164::fromXML(*node);
				if( NULL != _f )
				{
					_ret->_fax = *_f;
					delete _f;
				}
			}
			else if ( name.equals("email") )
			{
				_ret->_email = EppUtil::getText(*node);
			}
		}
	}

	for( unsigned int i = 0; i < attrs->getLength();i++ )
	{
		DOMNode* attr = attrs->item(i);
		DOMString _v = attr->getNodeValue();
		if( XS(attr->getNodeName()).equals("entitlement") )
		{
			_ret->_type = EPPMARK_HOLDER;
			if( _v.length() > 0 )
			{
				_ret->_addParam = _v;
			}
			break;
		}
		else if ( XS(attr->getNodeName()).equals("type") )
		{
			_ret->_type = EPPMARK_CONTACT;
			if( _v.length() > 0 )
			{
				_ret->_addParam = _v;
			}
			break;
		}
	}
	return _ret;
}

DOMString EppMarkHolder::toString()
{
	return EppEntity::toString(DOMString((getNameSpace() + ":contact").c_str()));
}

