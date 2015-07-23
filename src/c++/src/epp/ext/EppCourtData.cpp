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
#include "EppCourtData.hpp"
#include "EppUtil.hpp"

EppCourtData::EppCourtData(const char* ns)
:EppMarkData(ns), _refNum(), _proDate(0), _cc(), _region(10), _courtName()
{
}

EppCourtData::EppCourtData(const EppCourtData& _src)
:EppMarkData(std::string(_src.getNameSpace()).c_str()), _refNum(), _proDate(0), _cc(), _region(10), _courtName()
{
	this->_refNum    = _src._refNum;
	this->_proDate   = _src._proDate;
	this->_cc        = _src._cc;
	this->_region    = _src._region;
	this->_courtName = _src._courtName;
}

EppCourtData::~EppCourtData()
{
}

EppCourtData& EppCourtData::operator=(const EppCourtData& _src)
{
	if( this == &_src )
		return *this;
	EppMarkData::operator=(_src);
	this->_refNum    = _src._refNum;
	this->_proDate   = _src._proDate;
	this->_cc        = _src._cc.substringData(0, 2);
	this->_region    = _src._region;
	this->_courtName = _src._courtName;
	return *this;
}

void EppCourtData::refNum(const DOMString &_r)
{
	this->_refNum = _r;
}

DOMString EppCourtData::refNum(void)
{
	return this->_refNum;
}

void EppCourtData::proDate(const DOMString &_rd)
{
	char *_crd = _rd.transcode();
	if( NULL != _crd )
	{
		this->_proDate = EppUtil::getDate(_crd, false);
		XercesString::Delete(_crd);
	}
}

time_t EppCourtData::proDate(void)
{
	return this->_proDate;
}

void EppCourtData::countryCode(const DOMString& _cc)
{
	this->_cc = _cc;
}

DOMString EppCourtData::countryCode(void)
{
	return this->_cc;
}

void EppCourtData::addRegion(const DOMString& _r)
{
	this->_region.addElement(_r);
}

ValueVectorOf<DOMString> EppCourtData::region(void)
{
	return this->_region;
}

void EppCourtData::courtName(const DOMString& _cn)
{
	this->_courtName = _cn;
}

DOMString EppCourtData::courtName(void)
{
	return this->_courtName;
}

DOMElement* EppCourtData::toXML(DOMDocument &doc, const DOMString &tag)
{
	DOMElement* elm;
	DOMElement* body = EppMarkData::toXML(doc, "court");

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
	for( XMLSize_t i = 0;i<_holder.size();i++ )
	{
		EppMarkHolder* _h = _holder.elementAt(i);
		body->appendChild(_h->toXML( doc, "holder"));
	}
	for( XMLSize_t i = 0;i<_contact.size();i++ )
	{
		EppMarkHolder* _c = _contact.elementAt(i);
		body->appendChild(_c->toXML( doc, "contact"));
	}

	for( unsigned int i = 0;i<_label.size();i++ )
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
	if( this->_cc.length() > 0 )
	{
		elm = doc.createElement(XS("cc"));
		elm->appendChild(doc.createTextNode(this->_cc));
		body->appendChild(elm);
	}
	for( unsigned int i = 0;i<this->_region.size();i++ )
	{
		DOMString _s = this->_region.elementAt(i);
		if( _s.length() > 0 )
		{
			elm = doc.createElement(XS("region"));
			elm->appendChild(doc.createTextNode(_s));
			body->appendChild(elm);
		}
	}
	if( this->_courtName.length() > 0 )
	{
		elm = doc.createElement(XS("courtName"));
		elm->appendChild(doc.createTextNode(this->_courtName));
		body->appendChild(elm);
	}
	return body;
}

EppCourtData* EppCourtData::fromXML( const DOMNode& root, const char* ns )
{
	DOMNode* _temp_root = (DOMNode*) &root;
	EppCourtData *_ret = new EppCourtData(ns);

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
		if( name.substringData(0, nsLen + 1).equals((_ret->getNameSpace() + ":").c_str() ) )
		{
			name = name.substringData(nsLen + 1, name.length() - (nsLen + 1));
		}
		if( name.equals("refNum") )
		{
			_ret->_refNum = EppUtil::getText(*node);
		}
		else if( name.equals("proDate") )
		{
			_ret->_proDate = EppUtil::getDate(*node);
		}
		else if( name.equals("cc") )
		{
			DOMString _scc = EppUtil::getText(*node);
			_ret->_cc = _scc.substringData(0, 2);
		}
		else if( name.equals("region") )
		{
			DOMString _sr = EppUtil::getText(*node);
			_ret->_region.addElement(_sr);
		}
		else if( name.equals("courtName") )
		{
			DOMString _scn = EppUtil::getText(*node);
			_ret->_courtName = _scn;
		}
	}
	return _ret;
}

DOMString EppCourtData::toString()
{
	return DOMString((getNameSpace() + ":court").c_str());
}

