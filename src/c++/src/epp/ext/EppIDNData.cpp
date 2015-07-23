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
#include "EppIDNData.hpp"
#include "EppUtil.hpp"

EppIDNData::EppIDNData()
{
	_table = "";
	_uname = "";
}

EppIDNData::~EppIDNData()
{
	_table = "";
	_uname = "";
}

EppIDNData::EppIDNData(const EppIDNData& src)
{
	_table = src._table;
	_uname = src._uname;
}

EppIDNData & EppIDNData::operator=(const EppIDNData& o)
{
	if ( this == &o )
	{
		return *this;
	}
	_table = o._table;
	_uname = o._uname;
	return *this;
}

void EppIDNData::table(const DOMString & t)
{
	_table = t;
}

DOMString EppIDNData::table()
{
	return _table;
}

void EppIDNData::uname(const DOMString & u)
{
	_uname = u;
}

DOMString EppIDNData::uname()
{
	return _uname;
}

DOMElement* EppIDNData::toXML(DOMDocument &doc, const DOMString &tag)
{
	DOMElement* body = EppUtil::createElementNS(doc, "idn", (tag.isNull()) ? "data" : tag, true, "-1.0");

	if ( _table.length() > 0 )
	{
		DOMElement* elm = doc.createElement(XS("table"));
		elm->appendChild(doc.createTextNode(_table));
		body->appendChild(elm);
	}
	if ( _uname.length() > 0 )
	{
		DOMElement* elm = doc.createElement(XS("uname"));
		elm->appendChild(doc.createTextNode(_uname));
		body->appendChild(elm);
	}
	return body;
}

DOMString EppIDNData::toString()
{
	return EppEntity::toString(DOMString("idn:data"));
}

EppIDNData * EppIDNData::fromXML( const DOMNode& root )
{

	EppIDNData * idnData = new EppIDNData();
	DOMNodeList* list = root.getChildNodes();
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
		if( name.substringData(0, 4).equals("idn:") )
		{
			name = name.substringData(4, name.length() - 4);
		}
		if( name.equals("table") )
		{
			idnData->_table = EppUtil::getText(*node);
		}
		else if( name.equals("uname") )
		{
			idnData->_uname = EppUtil::getText(*node);
		}
	}
	return idnData;
}

int EppIDNData::getEntityType()
{
	return EppEntity::TYPE_EppIDN;
}
