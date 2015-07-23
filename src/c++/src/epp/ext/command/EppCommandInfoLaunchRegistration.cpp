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
#include "EppCommandInfoLaunchRegistration.hpp"
#include "EppUtil.hpp"

EppCommandInfoLaunchRegistration::EppCommandInfoLaunchRegistration():_phase(), _appId(), _includeMark(false)
{
}

EppCommandInfoLaunchRegistration::EppCommandInfoLaunchRegistration(
	const EppCommandInfoLaunchRegistration& _src)
{
	this->_phase       = _src._phase;
	this->_appId       = _src._appId;
	this->_includeMark = _src._includeMark;
}

EppCommandInfoLaunchRegistration& EppCommandInfoLaunchRegistration::operator=(
	const EppCommandInfoLaunchRegistration& _src)
{
	if ( &_src == this )
	{
		return *this;
	}
	this->_phase       = _src._phase;
	this->_appId       = _src._appId;
	this->_includeMark = _src._includeMark;
	return *this;
}

EppCommandInfoLaunchRegistration::~EppCommandInfoLaunchRegistration(){ }

void
EppCommandInfoLaunchRegistration::phase(const EppLaunchPhase &_p)
{
	this->_phase = _p;
}

EppLaunchPhase EppCommandInfoLaunchRegistration::phase(void)
{
	return this->_phase;
}

bool EppCommandInfoLaunchRegistration::includeMark(void)
{
	return this->_includeMark;
}

void EppCommandInfoLaunchRegistration::includeMark(bool _m)
{
	this->_includeMark = _m;
}

void EppCommandInfoLaunchRegistration::applicationID(const DOMString &_id)
{
	this->_appId = _id;
}

DOMString EppCommandInfoLaunchRegistration::applicationID(void)
{
	return this->_appId;
}

DOMElement* EppCommandInfoLaunchRegistration::toXML( DOMDocument& doc, const DOMString& tag )
{
	DOMElement* elm;
	DOMElement* body = EppUtil::createElementNS(doc, "launch", (tag.isNull()) ? "info" : tag, false, "-1.0");

	DOMAttr* attr = doc.createAttribute(XS("includeMark"));
	attr->setValue( ( this->_includeMark ) ? XS("true") : XS("false") );
	body->setAttributeNode(attr);

	if( this->_phase.phase().length() > 0 )
	{
		DOMElement* elm = this->_phase.toXML(doc, "phase");
		body->appendChild(elm);
	}
	if( this->_appId.isNotNull() )
	{
		if( this->_appId.length() > 0 )
		{
			elm = doc.createElement(XS("applicationID"));
			elm->appendChild(doc.createTextNode(this->_appId));
			body->appendChild(elm);
		}
	}
	return body;
}

EppCommandInfoLaunchRegistration* EppCommandInfoLaunchRegistration::fromXML( const DOMNode& root )
{
	EppCommandInfoLaunchRegistration* cmd  = new EppCommandInfoLaunchRegistration();
	if( cmd == null )
	{
		return null;
	}
	DOMNodeList* list      = root.getChildNodes();
	DOMNamedNodeMap* attrs = root.getAttributes();

	for( unsigned int i = 0; i < list->getLength(); i++ )
	{
		DOMNode* node  = list->item(i);
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
				cmd->_phase = *_pptr;
				delete _pptr;
			}
			_pptr = null;
		}
		else if ( name.equals("applicationID") )
		{
			cmd->_appId = EppUtil::getText(*node);
		}
	}
	for( unsigned int i = 0;i<attrs->getLength();i++ )
	{
		DOMNode* attr = attrs->item(i);
		if( XS(attr->getNodeName()).equals("includeMark") )
		{
			DOMString _v = attr->getNodeValue();
			if( _v.length() > 0 )
			{
				if( _v.equals("true") )
				{
					cmd->includeMark(true);
				}
			}
			break;
		}
	}
	return cmd;
}

DOMString EppCommandInfoLaunchRegistration::toString()
{
	return EppEntity::toString(DOMString("launch:info"));
}

int EppCommandInfoLaunchRegistration::getEntityType()
{
	return EppEntity::TYPE_EppCommandInfoLaunchRegistration;
}
