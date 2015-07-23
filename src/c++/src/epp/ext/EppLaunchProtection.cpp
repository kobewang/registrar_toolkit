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
#include "EppLaunchProtection.hpp"
#include "EppUtil.hpp"

EppLaunchProtection::EppLaunchProtection(const char* ns)
:EppMarkNameSpace(ns), _cc(), _ruling(), _region()
{
}

EppLaunchProtection::EppLaunchProtection(const EppLaunchProtection& _src)
{
	this->_cc = _src._cc;
	this->_ruling = _src._ruling;
	this->_region = _src._region;
}

EppLaunchProtection::~EppLaunchProtection()
{
}

EppLaunchProtection& EppLaunchProtection::operator=(const EppLaunchProtection& _src)
{
	this->_cc = _src._cc;
	this->_ruling = _src._ruling;
	this->_region = _src._region;
	return *this;
}

DOMString EppLaunchProtection::region()
{
	return this->_region;
}

void EppLaunchProtection::region(const DOMString &_r)
{
	this->_region = _r;
}

DOMString EppLaunchProtection::ruling(void)
{
	return this->_ruling;
}

void EppLaunchProtection::ruling(const DOMString& _c)
{
	this->_ruling = _c.substringData(0, 2);
}

DOMString EppLaunchProtection::countryCode(void)
{
	return this->_cc;
}

void EppLaunchProtection::countryCode(const DOMString &_c)
{
	this->_cc = _c.substringData(0, 2);
}

DOMElement* EppLaunchProtection::toXML(DOMDocument &doc, const DOMString &tag)
{
	DOMElement* elm;
	DOMElement* body = doc.createElement(tag);

	if( this->_cc.length() >0 )
	{
		elm = doc.createElement(XS("cc"));
		elm->appendChild(doc.createTextNode(this->_cc));
		body->appendChild(elm);
	}
	if( this->_region.length() > 0 )
	{
		elm = doc.createElement(XS("region"));
		elm->appendChild(doc.createTextNode(this->_region));
		body->appendChild(elm);
	}
	if( this->_ruling.length() > 0 )
	{
		elm = doc.createElement(XS("ruling"));
		elm->appendChild(doc.createTextNode(this->_ruling));
		body->appendChild(elm);
	}

	return body;
}

EppLaunchProtection* EppLaunchProtection::fromXML( const DOMNode& root, const char* ns )
{
	EppLaunchProtection *_ret = new EppLaunchProtection(ns);
	if( null == _ret )
		return null;
	DOMNodeList* list = root.getChildNodes();
	int nsLen = strlen(ns);

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
		if( name.substringData(0, nsLen + 1).equals((_ret->getNameSpace() + ":").c_str() ) )
		{
			name = name.substringData(nsLen + 1, name.length() - (nsLen + 1));
		}
		if( name.equals("cc") )
		{
			_ret->_cc = (EppUtil::getText(*node)).substringData(0, 2);
		}
		else if( name.equals("region") )
		{
			_ret->_region = EppUtil::getText(*node);
		}
		else if( name.equals("ruling") )
		{
			_ret->_ruling = EppUtil::getText(*node);
		}
	}
	return _ret;
}

DOMString EppLaunchProtection::toString()
{
	return EppEntity::toString(DOMString((getNameSpace() + ":protection").c_str()));
}

