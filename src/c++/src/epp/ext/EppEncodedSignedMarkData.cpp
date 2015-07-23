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
#include "EppEncodedSignedMarkData.hpp"

EppEncodedSignedMarkData::EppEncodedSignedMarkData():_rawXml(), _hasElement(false)
{
}

EppEncodedSignedMarkData::EppEncodedSignedMarkData(const EppEncodedSignedMarkData& _s)
{
	this->_rawXml = _s._rawXml;
	this->_hasElement = _s._hasElement;
}

EppEncodedSignedMarkData& EppEncodedSignedMarkData::operator=(const EppEncodedSignedMarkData& _s)
{
	this->_rawXml = _s._rawXml;
	this->_hasElement = _s._hasElement;
	return *this;
}

EppEncodedSignedMarkData::~EppEncodedSignedMarkData()
{
	/*do nothing*/
}

void EppEncodedSignedMarkData::rawXml(const DOMString& _input)
{
	this->_rawXml = _input;
	this->_hasElement = true;/*assuming user of this interface knows what he/she is doing*/
}

DOMString EppEncodedSignedMarkData::rawXml(void)
{
	return this->_rawXml;
}

bool EppEncodedSignedMarkData::hasSMD()
{
	return this->_hasElement;
}

void EppEncodedSignedMarkData::hasSMD(bool _f)
{
	this->_hasElement = _f;
}

/*one will have to explicitly use "rawXml()" function to get XML in raw format*/
DOMElement* EppEncodedSignedMarkData::toXML(DOMDocument &doc, const DOMString &tag)
{
	return doc.createElement(tag);
}

EppEncodedSignedMarkData* EppEncodedSignedMarkData::fromXML( const DOMNode& root )
{
	EppEncodedSignedMarkData *_ret = new EppEncodedSignedMarkData();
	if( null == _ret )
		return null;
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
		if( name.substringData(0, 4).equals("smd:") )
		{
			name = name.substringData(4, name.length() - 4);
			_ret->_hasElement = true;
		}
	}
	return _ret;
}

DOMString EppEncodedSignedMarkData::toString()
{
	return EppEntity::toString(DOMString("smd:encodedSignedMark"));
}

int EppEncodedSignedMarkData::getEntityType()
{
	return EppEntity::TYPE_EppEncodedSignedMarkData;
}

