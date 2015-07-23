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
#include "EppCommandCheckLaunchRegistration.hpp"
#include "EppUtil.hpp"

EppCommandCheckLaunchRegistration::EppCommandCheckLaunchRegistration():_phase(), _type("claims")
{
}

EppCommandCheckLaunchRegistration::~EppCommandCheckLaunchRegistration()
{
}

EppCommandCheckLaunchRegistration::EppCommandCheckLaunchRegistration(const EppCommandCheckLaunchRegistration& _s)
{
	this->_phase = _s._phase;
	this->_type = _s._type;
}

EppCommandCheckLaunchRegistration&
	EppCommandCheckLaunchRegistration::operator=(const EppCommandCheckLaunchRegistration& _s)
{
	if ( this == &_s )
	{
		return *this;
	}
	this->_phase = _s._phase;
	this->_type  = _s._type;
	return *this;
}

void
EppCommandCheckLaunchRegistration::phase(const EppLaunchPhase &_p)
{
	this->_phase = _p;
}

EppLaunchPhase
EppCommandCheckLaunchRegistration::phase(void)
{
	return this->_phase;
}

void
EppCommandCheckLaunchRegistration::type(const DOMString &_i)
{
	this->_type = _i;
}

DOMString
EppCommandCheckLaunchRegistration::type(void)
{
	return this->_type;
}

EppCommandCheckLaunchRegistration*
EppCommandCheckLaunchRegistration::fromXML( const DOMNode& root )
{
	EppCommandCheckLaunchRegistration *_ret = new EppCommandCheckLaunchRegistration();
	if( null == _ret )
	{
		return null;
	}

	DOMNodeList* list      = root.getChildNodes();
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
		{
			continue;
		}

		if( (name.length() > 7) && name.substringData(0, 7).equals("launch:") )
		{
			name = name.substringData(7, name.length() - 7);
		}

		if( name.equals("phase") )
		{
			EppLaunchPhase *_pptr = EppLaunchPhase::fromXML(*node);
			if( null != _pptr )
			{
				_ret->_phase = *_pptr;
				delete _pptr;
			}
			_pptr = null;
			break;
		}
	}
	for( unsigned int i = 0;i<attrs->getLength();i++ )
	{
		DOMNode* attr = attrs->item(i);
		DOMString _v  = attr->getNodeValue();

		if( XS(attr->getNodeName()).equals("type") )
		{
			_ret->_type = attr->getNodeValue();
			break;
		}
	}
	return _ret;
}

DOMElement*
EppCommandCheckLaunchRegistration::toXML( DOMDocument& doc, const DOMString& tag )
{
	//DOMElement* elm;
	DOMElement* body = EppUtil::createElementNS(doc, "launch", (tag.isNull()) ? XS("check") : tag, false, "-1.0");

	if( this->_type.length() > 0 )
	{
		DOMAttr* attr = doc.createAttribute(XS("type"));
		attr->setValue(XS( this->_type));
		body->setAttributeNode(attr);
	}

	if( this->_phase.phase().length() > 0 )
	{
		DOMElement* elm = this->_phase.toXML(doc, "phase");
		body->appendChild(elm);
	}
	return body;

}

DOMString
EppCommandCheckLaunchRegistration::toString()
{
	return EppEntity::toString(DOMString("launch:check"));
}

int
EppCommandCheckLaunchRegistration::getEntityType()
{
	return EppEntity::TYPE_EppCommandCheckLaunchRegistration;
}

/*
	private:
		EppLaunchPhase _phase;
		DOMString _type;
 */

