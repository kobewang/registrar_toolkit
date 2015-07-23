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
#include "EppTrademarkData.hpp"
#include "EppUtil.hpp"

EppTrademarkData::EppTrademarkData(const char* ns)
:EppMarkData(ns), _jurisdiction(), _class(10), _apId(), _apDate(0), _regNum(), _regDate(0), _exDate(0)
{
}

EppTrademarkData::EppTrademarkData(const EppTrademarkData& _src)
:EppMarkData(_src), _jurisdiction(), _class(10), _apId(), _apDate(0), _regNum(), _regDate(0), _exDate(0)
{
	this->_jurisdiction = _src._jurisdiction.substringData(0, 2);
	this->_class = _src._class;
	this->_apId = _src._apId;
	this->_apDate = _src._apDate;
	this->_regNum = _src._regNum;
	this->_regDate = _src._regDate;
	this->_exDate = _src._exDate;
}

EppTrademarkData::~EppTrademarkData()
{
}

EppTrademarkData& EppTrademarkData::operator=(const EppTrademarkData& _src)
{
	if( this == &_src )
		return *this;
	EppMarkData::operator=(_src);
	this->_jurisdiction = _src._jurisdiction.substringData(0, 2);
	this->_class = _src._class;
	this->_apId = _src._apId;
	this->_apDate = _src._apDate;
	this->_regNum = _src._regNum;
	this->_regDate = _src._regDate;
	this->_exDate = _src._exDate;
	return *this;
}

void EppTrademarkData::jurisdiction(const DOMString& _j)
{
	this->_jurisdiction = _j.substringData(0, 2);
}

DOMString EppTrademarkData::jurisdiction(void)
{
	return this->_jurisdiction;
}

void EppTrademarkData::tmClass(int _c)
{
	this->_class.addElement(_c);
}

ValueVectorOf<int> EppTrademarkData::tmClass(void)
{
	return this->_class;
}

void EppTrademarkData::apId(const DOMString &_a)
{
	this->_apId = _a;
}

DOMString EppTrademarkData::apId(void)
{
	return this->_apId;
}

void EppTrademarkData::apDate(const DOMString &_d)
{
	char *_cd = _d.transcode();
	if( NULL != _cd )
	{
		this->_apDate = EppUtil::getDate(_cd, false);
		XercesString::Delete(_cd);
	}
}

time_t EppTrademarkData::apDate(void)
{
	return this->_apDate;
}

void EppTrademarkData::regNum(const DOMString &_r)
{
	this->_regNum = _r;
}

DOMString EppTrademarkData::regNum(void)
{
	return this->_regNum;
}

void EppTrademarkData::regDate(const DOMString &_rd)
{
	char *_crd = _rd.transcode();
	if( NULL != _crd )
	{
		this->_regDate = EppUtil::getDate(_crd, false);
		XercesString::Delete(_crd);
	}
}

time_t EppTrademarkData::regDate(void)
{
	return this->_regDate;
}

void EppTrademarkData::exDate(const DOMString &_ed)
{
	char *_ced = _ed.transcode();
	if( NULL != _ced )
	{
		this->_exDate = EppUtil::getDate(_ced, false);
		XercesString::Delete(_ced);
	}
}

time_t EppTrademarkData::exDate(void)
{
	return this->_exDate;
}

DOMElement* EppTrademarkData::toXML(DOMDocument &doc, const DOMString &tag)
{
	DOMElement* elm;
	DOMElement* body = EppMarkData::toXML(doc, "trademark");

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
	for( XMLSize_t i = 0; i < _contact.size();i++ )
	{
		EppMarkHolder* _c = _contact.elementAt(i);
		body->appendChild(_c->toXML( doc, "contact"));
	}

	if( this->_jurisdiction.length() > 0 )
	{
		elm = doc.createElement(XS("jurisdiction"));
		elm->appendChild(doc.createTextNode(this->_jurisdiction));
		body->appendChild(elm);
	}
	for( unsigned int i = 0; i < this->_class.size();i++ )
	{
		char buf[10] = "\0";
		int _c = this->_class.elementAt(i);
		memset(buf, '\0', 10);
		snprintf(buf, 10, "%d", _c);
		elm = doc.createElement(XS("class"));
		elm->appendChild(doc.createTextNode(XS(buf)));
		body->appendChild(elm);
	}

	for( int i = 0; i < _label.size();i++ )
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

	if( _apId.length() > 0 )
	{
		elm = doc.createElement(XS("apId"));
		elm->appendChild(doc.createTextNode(this->_apId));
		body->appendChild(elm);
	}
	if( this->_apDate > 0 )
	{
		elm = doc.createElement(XS("apDate"));
		elm->appendChild(EppUtil::createTextNode(doc, this->_apDate));
		body->appendChild(elm);
	}
	if( this->_regNum.length() > 0 )
	{
		elm = doc.createElement(XS("regNum"));
		elm->appendChild(doc.createTextNode(this->_regNum));
		body->appendChild(elm);
	}
	if( this->_regDate > 0 )
	{
		elm = doc.createElement(XS("regDate"));
		elm->appendChild(EppUtil::createTextNode(doc, this->_regDate));
		body->appendChild(elm);
	}
	if( this->_exDate > 0 )
	{
		elm = doc.createElement(XS("exDate"));
		elm->appendChild(EppUtil::createTextNode(doc, this->_exDate));
		body->appendChild(elm);
	}
	return body;
}

EppTrademarkData* EppTrademarkData::fromXML( const DOMNode& root, const char* ns )
{
	DOMNode* _temp_root = (DOMNode*)&root;
	EppTrademarkData *_ret = new EppTrademarkData(ns);
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
			name = name.substringData(nsLen + 1, name.length() - (nsLen + 1));
		}
		if( name.equals("jurisdiction") )
		{
			_ret->_jurisdiction = (EppUtil::getText(*node)).substringData(0, 2);
		}
		else if( name.equals("class") )
		{
			DOMString _sn = EppUtil::getText(*node);
			char *_csn = _sn.transcode();
			if( NULL != _csn )
			{
				_ret->_class.addElement( atoi( _csn ) );
				XercesString::Delete(_csn);
			}
		}
		else if( name.equals("apId") )
		{
			_ret->_apId = EppUtil::getText(*node);
		}
		else if( name.equals("apDate") )
		{
			_ret->_apDate = EppUtil::getDate(*node);
		}
		else if( name.equals("regNum") )
		{
			_ret->_regNum = EppUtil::getText(*node);
		}
		else if( name.equals("regDate") )
		{
			_ret->_regDate = EppUtil::getDate(*node);
		}
		else if( name.equals("exDate") )
		{
			_ret->_exDate = EppUtil::getDate(*node);
		}
	}
	return _ret;
}

DOMString EppTrademarkData::toString()
{
	return DOMString((getNameSpace() + ":trademark").c_str());
}

