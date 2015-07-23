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
#include "EppCommandCreateLaunchRegistration.hpp"
#include "EppUtil.hpp"

EppCommandCreateLaunchRegistration::EppCommandCreateLaunchRegistration():_phase(), _signedMark(), _encSignedMark()
{
	_type         = "";
	_noticeID     = "";
	_notAfter     = "";
	_acceptedDate = "";
}

EppCommandCreateLaunchRegistration::EppCommandCreateLaunchRegistration(
	const EppCommandCreateLaunchRegistration& _src)
{
	this->_phase         = _src._phase;
	this->_signedMark    = _src._signedMark;
	this->_encSignedMark = _src._encSignedMark;
	this->_type          = _src._type;
	this->_noticeID      = _src._noticeID;
	this->_notAfter      = _src._notAfter;
	this->_acceptedDate  = _src._acceptedDate;
}

EppCommandCreateLaunchRegistration&
	EppCommandCreateLaunchRegistration::operator=(
	const EppCommandCreateLaunchRegistration& _src)
{
	if ( &_src == this )
	{
		return *this;
	}
	this->_phase         = _src._phase;
	this->_signedMark    = _src._signedMark;
	this->_encSignedMark = _src._encSignedMark;
	this->_type          = _src._type;
	this->_noticeID      = _src._noticeID;
	this->_notAfter      = _src._notAfter;
	this->_acceptedDate  = _src._acceptedDate;
	return *this;
}

EppCommandCreateLaunchRegistration::~EppCommandCreateLaunchRegistration(){ }

void
EppCommandCreateLaunchRegistration::phase(const EppLaunchPhase &_p)
{
	this->_phase = _p;
}

EppLaunchPhase
EppCommandCreateLaunchRegistration::phase(void)
{
	return this->_phase;
}

void
EppCommandCreateLaunchRegistration::signedMark(const EppSignedMarkData& _input)
{
	this->_signedMark = _input;
}

EppSignedMarkData
EppCommandCreateLaunchRegistration::signedMark(void)
{
	return this->_signedMark;
}

void
EppCommandCreateLaunchRegistration::encodedSignedMark(const EppEncodedSignedMarkData& _input)
{
	this->_encSignedMark = _input;
}

EppEncodedSignedMarkData
EppCommandCreateLaunchRegistration::encodedSignedMark(void)
{
	return this->_encSignedMark;
}

void
EppCommandCreateLaunchRegistration::type(const DOMString &_i)
{
	this->_type = _i;
}

DOMString
EppCommandCreateLaunchRegistration::type(void)
{
	return this->_type;
}

DOMString
EppCommandCreateLaunchRegistration::noticeID(void)
{
	return this->_noticeID;
}

DOMString
EppCommandCreateLaunchRegistration::notAfter(void)
{
	return this->_notAfter;
}

DOMString
EppCommandCreateLaunchRegistration::acceptedDate(void)
{
	return this->_acceptedDate;
}

void
EppCommandCreateLaunchRegistration::noticeID(const DOMString &_i)
{
	this->_noticeID = _i;
}

void
EppCommandCreateLaunchRegistration::notAfter(const DOMString &_i)
{
	this->_notAfter = _i;
}

void
EppCommandCreateLaunchRegistration::acceptedDate(const DOMString &_i)
{
	this->_acceptedDate = _i;
}

DOMElement* EppCommandCreateLaunchRegistration::toXML( DOMDocument& doc, const DOMString& tag )
{
	DOMElement* elm;
	DOMElement* body = EppUtil::createElementNS(doc, "launch", (tag.isNull()) ? "create" : tag, false, "-1.0");

	if( this->_type.length() > 0 )
	{
		DOMAttr* attr = doc.createAttribute(XS("type"));
		attr->setValue( this->_type);
		body->setAttributeNode(attr);
	}

	if ( this->_phase.phase().length() > 0 )
	{
		DOMElement* elm = this->_phase.toXML(doc, "phase");
		body->appendChild(elm);
	}

	if( (_signedMark.hasSMD()) )
	{/*RAII*/
		DOMElement* elm = this->_signedMark.toXML(doc, "signedMark");
		body->appendChild(elm);
	}
	if( (_encSignedMark.hasSMD()) )
	{/*RAII*/
		DOMElement* elm = this->_encSignedMark.toXML(doc, "encodedSignedMark");
		body->appendChild(elm);
	}
	if( this->_noticeID.length() > 0  )
	{
		elm = doc.createElement(XS("noticeID"));
		elm->appendChild(doc.createTextNode(this->_noticeID));
		body->appendChild(elm);
	}
	if( this->_notAfter.length() > 0  )
	{
		elm = doc.createElement(XS("notAfter"));
		elm->appendChild(doc.createTextNode(this->_notAfter));
		body->appendChild(elm);
	}
	if( this->_acceptedDate.length() > 0  )
	{
		elm = doc.createElement(XS("acceptedDate"));
		elm->appendChild(doc.createTextNode(this->_acceptedDate));
		body->appendChild(elm);
	}

	return body;
}

EppCommandCreateLaunchRegistration*
EppCommandCreateLaunchRegistration::fromXML( const DOMNode& root )
{
	EppCommandCreateLaunchRegistration* cmd  = new EppCommandCreateLaunchRegistration();
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
		else if( (name.length() > 4) && name.substringData(0, 4).equals("smd:") )
		{
			name = name.substringData(4, name.length() - 4);
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
		else if ( name.equals("notice") )
		{
			DOMNodeList* noticeChildren  = node->getChildNodes();
			for( unsigned int j = 0; j < noticeChildren->getLength(); j++ )
			{
				DOMNode* child = noticeChildren->item(j);
				DOMString chName = child->getLocalName();
				if( chName.isNull() )
				{
					chName = child->getNodeName();
				}
				if( chName.isNull() )
				{
					continue;
				}
				if( (chName.length() > 7) && chName.substringData(0, 7).equals("launch:") )
				{
					chName = chName.substringData(7, chName.length() - 7);
				}
				if ( chName.equals("noticeID") )
				{
					DOMString s = EppUtil::getText(*child);
					cmd->noticeID(s);
				}
				else if ( chName.equals("notAfter") )
				{
					DOMString s = EppUtil::getText(*child);
					cmd->notAfter(s);
				}
				else if ( chName.equals("acceptedDate") )
				{
					DOMString s = EppUtil::getText(*child);
					cmd->acceptedDate(s);
				}
			}
		}
		else if( name.equals("signedMark") )
		{
			EppSignedMarkData *_nd = EppSignedMarkData::fromXML(*node);
			if( null != _nd )
			{
				_nd->hasSMD(true);
				cmd->_signedMark = *_nd;
				delete _nd;
			}
			_nd = null;
		}
		else if( name.equals("encodedSignedMark") )
		{
			EppEncodedSignedMarkData *_nd = EppEncodedSignedMarkData::fromXML(*node);
			if( null != _nd )
			{
				_nd->hasSMD(true);
				cmd->_encSignedMark = *_nd;
				delete _nd;
			}
			_nd = null;
		}
	}
	for( unsigned int i = 0;i<attrs->getLength();i++ )
	{
		DOMNode* attr = attrs->item(i);
		DOMString _v = attr->getNodeValue();

		if( XS(attr->getNodeName()).equals("type") )
		{
			cmd->_type = attr->getNodeValue();
			break;
		}
	}
	return cmd;
}

DOMString
EppCommandCreateLaunchRegistration::toString()
{
	return EppEntity::toString(DOMString("launch:create"));
}

int
EppCommandCreateLaunchRegistration::getEntityType()
{
	return EppEntity::TYPE_EppCommandCreateLaunchRegistration;
}
