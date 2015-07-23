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
#include "EppAllocationTokenData.hpp"
#include "EppUtil.hpp"

EppAllocationTokenData::EppAllocationTokenData()
{
	s_token = "";
}

EppAllocationTokenData::~EppAllocationTokenData()
{
	s_token = "";
}

EppAllocationTokenData::EppAllocationTokenData(const EppAllocationTokenData& src)
{
	s_token = src.s_token;
}

EppAllocationTokenData & EppAllocationTokenData::operator=(const EppAllocationTokenData& in)
{
	if ( this == &in )
	{
		return *this;
	}
	s_token = in.s_token;

	return *this;
}

void EppAllocationTokenData::setToken(const DOMString & t)
{
	s_token = t;
}

DOMString EppAllocationTokenData::getToken()
{
	return s_token;
}

DOMElement* EppAllocationTokenData::toXML(DOMDocument &doc, const DOMString &tag)
{

	DOMElement* body = EppUtil::createElementNS(doc, "allocationToken", "allocationToken");

	if ( s_token.length() > 0 )
	{
		body->appendChild(doc.createTextNode(s_token));
	}
	return body;
}

DOMString EppAllocationTokenData::toString()
{
	return EppEntity::toString(DOMString("allocationToken"));
}

EppAllocationTokenData * EppAllocationTokenData::fromXML( const DOMNode& root )
{

	EppAllocationTokenData * tokenData = new EppAllocationTokenData();

	tokenData->s_token = EppUtil::getText(root);

	return tokenData;
}

int EppAllocationTokenData::getEntityType()
{
	return EppEntity::TYPE_EppAllocationToken;
}
