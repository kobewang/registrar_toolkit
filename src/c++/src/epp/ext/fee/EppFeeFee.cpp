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
#include "EppFeeFee.hpp"
#include <string>
#include <sstream>      // std::ostringstream

using namespace std;

DOMElement* EppFeeFee::toXML( DOMDocument& doc, const DOMString& tag )
{
	DOMElement* body = doc.createElement(tag);

	if( this->description.length() > 0 )
	{
		body->setAttribute(XS("description"), this->description);
	}
	if( this->refundable.length() > 0 )
	{
		body->setAttribute(XS("refundable"), this->refundable);
	}
	if( this->gracePeriod.length() > 0 )
	{
		body->setAttribute(XS("grace-period"), this->gracePeriod);
	}
	if( this->applied.length() > 0 )
	{
		body->setAttribute(XS("applied"), this->applied);
	}

	std::ostringstream os;
	os.precision(2);
	os << std::fixed << this->fee;
	std::string str = os.str();

	body->appendChild(doc.createTextNode(XS(str.c_str())));

	return body;

}

EppFeeFee * EppFeeFee::fromXML( const DOMNode& root )
{
	EppFeeFee *data = new EppFeeFee();

	char* value = EppUtil::getText(root).transcode();

	if( NULL != value )
	{
		data->setFee(std::atof(value));
		XercesString::Delete(value);
	}

	DOMNamedNodeMap* attrs = root.getAttributes();

	if( NULL == attrs )
	{
		return data;
	}

	for( unsigned int i = 0; i < attrs->getLength(); i++ )
	{
		DOMNode* attr = attrs->item(i);

		if( NULL == attr )
		{
			continue;
		}

		DOMString name = attr->getNodeName();

		if( name.equals("description") )
		{
			data->setDescription(attr->getNodeValue());
			continue;
		}
		if( name.equals("refundable") )
		{
			data->setRefundable(attr->getNodeValue());
			continue;
		}
		if( name.equals("grace-period") )
		{
			data->setGracePeriod(attr->getNodeValue());
			continue;
		}
		if( name.equals("applied") )
		{
			data->setApplied(attr->getNodeValue());
			continue;
		}
	}
	return data;
}
