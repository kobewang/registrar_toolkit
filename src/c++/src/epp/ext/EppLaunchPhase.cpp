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
#include "EppLaunchPhase.hpp"
#include "EppUtil.hpp"

EppLaunchPhase::EppLaunchPhase():_phase(""), _sub_phase("")
{
}

EppLaunchPhase::EppLaunchPhase(const EppLaunchPhase& _s)
{
	this->_phase = _s._phase;
	this->_sub_phase = _s._sub_phase;
}

EppLaunchPhase::~EppLaunchPhase()
{
}

EppLaunchPhase& EppLaunchPhase::operator=(const EppLaunchPhase& _s)
{
	this->_phase = _s._phase;
	this->_sub_phase = _s._sub_phase;
	return *this;
}

DOMString EppLaunchPhase::phase(void)
{
	return this->_phase;
}

void EppLaunchPhase::phase(const DOMString &_i)
{
	this->_phase = _i;
}

DOMString EppLaunchPhase::subPhase(void)
{
	return this->_sub_phase;
}

void EppLaunchPhase::subPhase(const DOMString &_i)
{
	this->_sub_phase = _i;
}

DOMElement* EppLaunchPhase::toXML(DOMDocument &doc, const DOMString &tag)
{
	//DOMElement* elm;
	DOMElement* body = doc.createElement(tag);
	if( this->_sub_phase.length() > 0 )
	{
		DOMAttr* attr = doc.createAttribute(XS("name"));
		attr->setValue( this->_sub_phase );
		body->setAttributeNode(attr);
	}
	if( this->_phase.length() > 0 )
	{
		body->appendChild(doc.createTextNode(this->_phase));
	}
	return body;
}

EppLaunchPhase* EppLaunchPhase::fromXML( const DOMNode& root )
{
	DOMNamedNodeMap* attrs = root.getAttributes();
	EppLaunchPhase *_ret = new EppLaunchPhase();
	if( null == _ret )
		return null;
	{
		DOMNode* node = (DOMNode*)&root;
		DOMString name = node->getLocalName();
		if( name.isNull() )
		{
			name = node->getNodeName();
		}
		if( name.isNotNull() )
		{
			if( name.substringData(0, 7).equals("launch:") )
			{
				name = name.substringData(7, name.length() - 7);
			}
			if( name.equals("phase") )
			{
				_ret->_phase = EppUtil::getText(*node);
			}
		}
	}
	for( unsigned int i = 0;i<attrs->getLength();i++ )
	{
		DOMNode* attr = attrs->item(i);
		DOMString _v = attr->getNodeValue();
		if( XS(attr->getNodeName()).equals("name") )
		{
			_ret->_sub_phase = attr->getNodeValue();
			break;
		}
	}
	return _ret;
}

DOMString EppLaunchPhase::toString()
{
	return EppEntity::toString(DOMString("launch:phase"));
}
/*
	private:
		DOMString _phase;
		DOMString _sub_phase;
 */
