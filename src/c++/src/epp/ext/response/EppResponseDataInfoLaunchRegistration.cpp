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
#include "EppResponseDataInfoLaunchRegistration.hpp"
#include "EppUtil.hpp"

EppResponseDataInfoLaunchRegistration::EppResponseDataInfoLaunchRegistration()
:_phase(), _appId(), _status(), _mark(NULL), isStatusSet(false)
{
}

EppResponseDataInfoLaunchRegistration::EppResponseDataInfoLaunchRegistration(const EppResponseDataInfoLaunchRegistration& _src)
:_phase(), _appId(), _status(), _mark(NULL), isStatusSet(false)
{
	this->_phase = _src._phase;
	this->_appId = _src._appId;
	this->_status = _src._status;
	if( NULL != this->_mark )
	{
		delete this->_mark;
	}
	this->_mark = _src._mark;
	this->isStatusSet = _src.isStatusSet;
}

EppResponseDataInfoLaunchRegistration& EppResponseDataInfoLaunchRegistration::operator=(
	const EppResponseDataInfoLaunchRegistration& _src)
{
	this->_phase = _src._phase;
	this->_appId = _src._appId;
	this->_status = _src._status;
	if( NULL != this->_mark )
	{
		delete this->_mark;
	}
	this->_mark = _src._mark;
	this->isStatusSet = _src.isStatusSet;
	return *this;
}

EppResponseDataInfoLaunchRegistration::~EppResponseDataInfoLaunchRegistration()
{
	if ( _mark != NULL )
	{
		delete _mark;
	}
}

void EppResponseDataInfoLaunchRegistration::phase(const EppLaunchPhase& _p)
{
	this->_phase = _p;
}

EppLaunchPhase EppResponseDataInfoLaunchRegistration::phase()
{
	return this->_phase;
}

void EppResponseDataInfoLaunchRegistration::applicationId(const DOMString &_apid)
{
	this->_appId = _apid;
}

DOMString EppResponseDataInfoLaunchRegistration::applicationId(void)
{
	return this->_appId;
}

void EppResponseDataInfoLaunchRegistration::status(const DOMString& _s)
{
	if( _s.length() > 0 )
	{
		this->isStatusSet = true;
	}
	this->_status.setStatus(_s);
}

DOMString EppResponseDataInfoLaunchRegistration::status(void)
{
	return this->_status.getStatus();
}

void EppResponseDataInfoLaunchRegistration::mark(EppMarkMark* _m)
{
	if( this->_mark != NULL )
	{
		delete this->_mark;
	}
	this->_mark = _m;
}

EppMarkMark* EppResponseDataInfoLaunchRegistration::mark(void)
{
	return this->_mark;
}

EppResponseDataInfoLaunchRegistration* EppResponseDataInfoLaunchRegistration::fromXML( const DOMNode& root )
{
	EppResponseDataInfoLaunchRegistration *ret = new EppResponseDataInfoLaunchRegistration();
	if( ret == null )
	{
		return null;
	}
	DOMNodeList* list  = root.getChildNodes();
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
				ret->_phase = *_pptr;
				delete _pptr;
			}
			_pptr = null;
		}
		else if( name.equals("applicationID") )
		{
			ret->_appId = EppUtil::getText(*node);
		}
		else if( name.equals("status") )
		{
			ret->isStatusSet = true;
			ret->_status = *((EppStatus*)EppStatus::fromXML(*node));
		}
		else if( name.equals("mark") )
		{
			ret->_mark = EppMarkMark::fromXML(*node);
		}
	}
	return ret;
}

DOMElement* EppResponseDataInfoLaunchRegistration::toXML( DOMDocument& doc, const DOMString& tag )
{
	DOMElement* elm;
	DOMElement* body;

	body = EppUtil::createElementNS(doc, "launch", (tag.isNull()) ? XS("infData") : tag, false, "-1.0");
	if( this->_phase.phase().length() > 0 )
	{
		DOMElement* elm = this->_phase.toXML(doc, "phase");
		body->appendChild(elm);
	}
	if( this->_appId.length() > 0 )
	{
		elm = doc.createElement(XS("applicationID"));
		elm->appendChild(doc.createTextNode(this->_appId));
		body->appendChild(elm);
	}
	if( this->isStatusSet )
	{
		body->appendChild( this->_status.toXML(doc, "status") );
	}
	if( this->_mark != NULL )
	{
		body->appendChild( this->_mark->toXML( doc, "mark:mark"));
	}

	return body;
}

DOMString EppResponseDataInfoLaunchRegistration::toString()
{
	return DOMString("EppResponseDataInfoLaunchRegistration");
}

int EppResponseDataInfoLaunchRegistration::getEntityType()
{
	return EppEntity::TYPE_EppResponseDataInfoLaunchRegistration;
}
