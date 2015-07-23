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
#include "EppResponseDataCreateLaunchRegistration.hpp"
#include "EppUtil.hpp"

EppResponseDataCreateLaunchRegistration::EppResponseDataCreateLaunchRegistration():_phase(), _appId(""){ }

EppResponseDataCreateLaunchRegistration::EppResponseDataCreateLaunchRegistration(const EppResponseDataCreateLaunchRegistration& _src)
{
	this->_phase = _src._phase;
	this->_appId = _src._appId;
}

EppResponseDataCreateLaunchRegistration& EppResponseDataCreateLaunchRegistration::operator=(const EppResponseDataCreateLaunchRegistration& _src)
{
	this->_phase = _src._phase;
	this->_appId = _src._appId;
	return *this;
}

EppResponseDataCreateLaunchRegistration::~EppResponseDataCreateLaunchRegistration(){ }

void EppResponseDataCreateLaunchRegistration::phase(const EppLaunchPhase& _p)
{
	this->_phase = _p;
}

EppLaunchPhase EppResponseDataCreateLaunchRegistration::phase()
{
	return this->_phase;
}

void EppResponseDataCreateLaunchRegistration::applicationID(const DOMString& _a)
{
	this->_appId = _a;
}

DOMString EppResponseDataCreateLaunchRegistration::applicationID()
{
	return this->_appId;
}

EppResponseDataCreateLaunchRegistration* EppResponseDataCreateLaunchRegistration::fromXML( const DOMNode& root )
{
	EppResponseDataCreateLaunchRegistration *cmd  = new EppResponseDataCreateLaunchRegistration();
	if( cmd == null )
	{
		return null;
	}
	DOMNodeList* list  = root.getChildNodes();
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
		else if( name.equals("applicationID") )
		{
			cmd->_appId = EppUtil::getText(*node);
		}
	}
	return cmd;
}

DOMElement* EppResponseDataCreateLaunchRegistration::toXML( DOMDocument& doc, const DOMString& tag )
{
	DOMElement* body;
	body = EppUtil::createElementNS(doc, "launch", (tag.isNull()) ? XS("creData") : tag, false, "-1.0");
	if( this->_phase.phase().length() > 0 )
	{
		DOMElement* elm = this->_phase.toXML(doc, "phase");
		body->appendChild(elm);
	}
	if( this->_appId.isNotNull() )
	{
		if( this->_appId.length() > 0 )
		{
			DOMElement* elm = doc.createElement(XS("applicationID"));
			elm->appendChild(doc.createTextNode(this->_appId));
			body->appendChild(elm);
		}
	}

	return body;

}

DOMString EppResponseDataCreateLaunchRegistration::toString()
{
	return EppEntity::toString(DOMString("launch:creData"));
}

int EppResponseDataCreateLaunchRegistration::getEntityType()
{
	return EppEntity::TYPE_EppResponseDataCreateLaunchRegistration;
}
