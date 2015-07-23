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
#include "EppTreatyOrStatuteData.hpp"
#include "EppUtil.hpp"

EppTreatyOrStatuteData::EppTreatyOrStatuteData(const char* ns)
:EppMarkData(ns), _region(10), _refNum(), _proDate(0), _title(), _exDate(0)
{
}

EppTreatyOrStatuteData::EppTreatyOrStatuteData(const EppTreatyOrStatuteData& _src)
:EppMarkData(_src.getNameSpace().c_str()), _region(10), _refNum(), _proDate(0), _title(), _exDate(0)
{
	this->_region = _src._region;
	this->_refNum = _src._refNum;
	this->_proDate = _src._proDate;
	this->_title = _src._title;
	this->_exDate = _src._exDate;
}

EppTreatyOrStatuteData::~EppTreatyOrStatuteData()
{
}

EppTreatyOrStatuteData& EppTreatyOrStatuteData::operator=(const EppTreatyOrStatuteData& _src)
{
	if( this == &_src )
		return *this;
	EppMarkData::operator=(_src);
	this->_region = _src._region;
	this->_refNum = _src._refNum;
	this->_proDate = _src._proDate;
	this->_title = _src._title;
	this->_exDate = _src._exDate;
	return *this;
}

void EppTreatyOrStatuteData::refNum(const DOMString &_r)
{
	this->_refNum = _r;
}

DOMString EppTreatyOrStatuteData::refNum(void)
{
	return this->_refNum;
}

void EppTreatyOrStatuteData::proDate(time_t t)
{
	this->_proDate = t;
}

void EppTreatyOrStatuteData::proDate(const DOMString &_rd)
{
	char *_crd = _rd.transcode();
	if( NULL != _crd )
	{
		this->_proDate = EppUtil::getDate(_crd, false);
		XercesString::Delete(_crd);
	}
}

time_t EppTreatyOrStatuteData::proDate(void)
{
	return this->_proDate;
}

void EppTreatyOrStatuteData::exDate(time_t t)
{
	this->_exDate = t;
}

void EppTreatyOrStatuteData::exDate(const DOMString &_rd)
{
	char *_crd = _rd.transcode();
	if( NULL != _crd )
	{
		this->_exDate = EppUtil::getDate(_crd, false);
		XercesString::Delete(_crd);
	}
}

time_t EppTreatyOrStatuteData::exDate(void)
{
	return this->_exDate;
}

void EppTreatyOrStatuteData::title(const DOMString& _tt)
{
	this->_title = _tt;
}

DOMString EppTreatyOrStatuteData::title(void)
{
	return this->_title;
}

void EppTreatyOrStatuteData::addRegion(const EppLaunchProtection& _r)
{
	this->_region.addElement(_r);
}

ValueVectorOf<EppLaunchProtection> EppTreatyOrStatuteData::region(void)
{
	return this->_region;
}

DOMElement* EppTreatyOrStatuteData::toXML(DOMDocument &doc, const DOMString &tag)
{
	DOMElement* elm;
	DOMElement* body = EppMarkData::toXML(doc, "treatyOrStatute");

	if( this->Id.length() > 0 )
	{
		elm = doc.createElement(XS("id"));
		elm->appendChild(doc.createTextNode(this->Id));
		body->appendChild(elm);
	}
	if( this->_markName.length() > 0 )
	{
		elm = doc.createElement(XS("markName"));
		elm->appendChild(doc.createTextNode(this->_markName));
		body->appendChild(elm);
	}
	for( XMLSize_t i = 0; i < _holder.size();i++ )
	{
		EppMarkHolder* _h = _holder.elementAt(i);
		body->appendChild(_h->toXML( doc, "holder"));
	}
	for( unsigned int i = 0; i < _contact.size();i++ )
	{
		EppMarkHolder* _c = _contact.elementAt(i);
		body->appendChild(_c->toXML( doc, "contact"));
	}

	for( unsigned int i = 0; i <_region.size();i++ )
	{
		EppLaunchProtection _c = _region.elementAt(i);
		body->appendChild(_c.toXML( doc, "protection"));
	}
	for( unsigned int i = 0; i < _label.size();i++ )
	{
		DOMString _s = _label.elementAt(i);
		if( _s.length() > 0 )
		{
			elm = doc.createElement(XS("label"));
			elm->appendChild(doc.createTextNode(_s));
			body->appendChild(elm);
		}
	}
	if( this->goodsAndServices.length() > 0 )
	{
		elm = doc.createElement(XS("goodsAndServices"));
		elm->appendChild(doc.createTextNode(this->goodsAndServices));
		body->appendChild(elm);
	}

	if( this->_refNum.length() > 0 )
	{
		elm = doc.createElement(XS("refNum"));
		elm->appendChild(doc.createTextNode(this->_refNum));
		body->appendChild(elm);
	}
	if( this->_proDate > 0 )
	{
		elm = doc.createElement(XS("proDate"));
		elm->appendChild(EppUtil::createTextNode(doc, this->_proDate));
		body->appendChild(elm);
	}
	if( this->_title.length() > 0 )
	{
		elm = doc.createElement(XS("title"));
		elm->appendChild(doc.createTextNode(this->_title));
		body->appendChild(elm);
	}
	if( this->_exDate > 0 )
	{
		elm = doc.createElement(XS("execDate"));
		elm->appendChild(EppUtil::createTextNode(doc, this->_exDate));
		body->appendChild(elm);
	}

	return body;
}

EppTreatyOrStatuteData* EppTreatyOrStatuteData::fromXML( const DOMNode& root, const char* ns )
{
	DOMNode* _temp_root = (DOMNode*)&root;
	EppTreatyOrStatuteData *_ret = new EppTreatyOrStatuteData(ns);
	_ret->fromXml(*_temp_root);
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
		if( name.substringData(0, nsLen + 1).equals((_ret->getNameSpace() + ":").c_str()) )
		{
			name = name.substringData(nsLen + 1, name.length() - ( nsLen + 1 ));
		}
		if( name.equals("refNum") )
		{
			_ret->_refNum = EppUtil::getText(*node);
		}
		else if( name.equals("proDate") )
		{
			_ret->_proDate = EppUtil::getDate(*node);
		}
		else if( name.equals("title") )
		{
			_ret->_title = EppUtil::getText(*node);
		}
		else if( name.equals("protection") )
		{
			EppLaunchProtection *_pro = EppLaunchProtection::fromXML(*node, ns);
			if( NULL != _pro )
			{
				_ret->_region.addElement( *_pro );
				delete _pro;
			}
		}
		else if( name.equals("execDate") )
		{
			_ret->_exDate = EppUtil::getDate(*node);
		}
	}
	return _ret;
}

DOMString EppTreatyOrStatuteData::toString()
{
	return DOMString((getNameSpace() + ":treatyOrStatute").c_str());
}

