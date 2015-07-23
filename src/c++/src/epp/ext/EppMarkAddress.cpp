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
#include "EppMarkAddress.hpp"
#include "EppUtil.hpp"

EppMarkAddress::EppMarkAddress(const char* ns)
:EppMarkNameSpace(ns), _city(), _sp(), _pc(), _cc()
{
	for( int i = 0;i < 3;i++ )
		this->_street[i] = DOMString();
}

EppMarkAddress::EppMarkAddress(const EppMarkAddress& _src)
{
	this->_city = _src._city;
	this->_sp = _src._sp;
	this->_pc = _src._pc;
	this->_cc = _src._cc;

	for( int i = 0;i < 3;i++ )
		this->_street[i] = _src._street[i];
}

EppMarkAddress::~EppMarkAddress()
{
}

EppMarkAddress& EppMarkAddress::operator=(const EppMarkAddress& _src)
{
	this->_city = _src._city;
	this->_sp = _src._sp;
	this->_pc = _src._pc;
	this->_cc = _src._cc;

	for( int i = 0; i < 3;i++ )
		this->_street[i] = _src._street[i];
	return *this;
}

DOMString EppMarkAddress::streetLine1()
{
	return this->_street[0];
}

void EppMarkAddress::streetLine1(DOMString &line1)
{
	this->_street[0] = line1;
}

DOMString EppMarkAddress::streetLine2()
{
	return this->_street[1];
}

void EppMarkAddress::streetLine2(DOMString &line1)
{
	this->_street[1] = line1;
}

DOMString EppMarkAddress::streetLine3()
{
	return this->_street[2];
}

void EppMarkAddress::streetLine3(const DOMString &line1)
{
	this->_street[2] = line1;
}

DOMString EppMarkAddress::city(void)
{
	return this->_city;
}

void EppMarkAddress::city(const DOMString& _c)
{
	this->_city = _c.substringData(0, 2);
}

DOMString EppMarkAddress::stateProvince(void)
{
	return this->_sp;
}

void EppMarkAddress::stateProvince(const DOMString &_s)
{
	this->_sp = _s;
}

DOMString EppMarkAddress::postalCode(void)
{
	return this->_pc;
}

void EppMarkAddress::postalCode(const DOMString &_p)
{
	this->_pc = _p;
}

DOMString EppMarkAddress::countryCode(void)
{
	return this->_cc;
}

void EppMarkAddress::countryCode(const DOMString &_c)
{
	this->_cc = _c;
}

DOMElement* EppMarkAddress::toXML(DOMDocument &doc, const DOMString &tag)
{
	DOMElement* elm;
	DOMElement* body = doc.createElement(tag);
	for( int i = 0; i < 3;i++ )
	{
		if( this->_street[i].length() > 0 )
		{
			elm = doc.createElement(XS("street"));
			elm->appendChild(doc.createTextNode(this->_street[i]));
			body->appendChild(elm);
		}
	}
	if( this->_city.length() > 0 )
	{
		elm = doc.createElement(XS("city"));
		elm->appendChild(doc.createTextNode(this->_city));
		body->appendChild(elm);
	}
	if( this->_sp.length() > 0 )
	{
		elm = doc.createElement(XS("sp"));
		elm->appendChild(doc.createTextNode(this->_sp));
		body->appendChild(elm);
	}
	if( this->_pc.length() > 0 )
	{
		elm = doc.createElement(XS("pc"));
		elm->appendChild(doc.createTextNode(this->_pc));
		body->appendChild(elm);
	}
	if( this->_cc.length() > 0 )
	{
		elm = doc.createElement(XS("cc"));
		elm->appendChild(doc.createTextNode(this->_cc));
		body->appendChild(elm);
	}
	return body;
}

EppMarkAddress* EppMarkAddress::fromXML( const DOMNode& root, const char* ns )
{
	int _street_c = 0;
	EppMarkAddress *_ret = new EppMarkAddress(ns);
	if( null == _ret )
		return null;
	DOMNodeList* list = root.getChildNodes();
	int nsLen = _ret->getNameSpace().size();

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
		if( name.substringData(0, nsLen + 1).equals((_ret->getNameSpace() + ":").c_str()) )
		{
			name = name.substringData(nsLen + 1, name.length() - (nsLen + 1));
		}
		if( name.equals("street") )
		{
			if( 3 > _street_c )
				_ret->_street[_street_c++] = EppUtil::getText(*node);
		}
		else if( name.equals("city") )
		{
			_ret->_city = EppUtil::getText(*node);
		}
		else if( name.equals("sp") )
		{
			_ret->_sp = EppUtil::getText(*node);
		}
		else if( name.equals("pc") )
		{
			_ret->_pc = EppUtil::getText(*node);
		}
		else if( name.equals("cc") )
		{
			_ret->_cc = (EppUtil::getText(*node)).substringData(0, 2);
		}
	}
	return _ret;

}

DOMString EppMarkAddress::toString()
{
	return EppEntity::toString(DOMString((getNameSpace() + ":addr").c_str()));

}

int EppMarkAddress::getEntityType()
{
	return EppEntity::TYPE_EppMarkAddress;
}

