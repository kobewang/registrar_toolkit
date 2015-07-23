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
#include "EppResponseCheckFeeType.hpp"

DOMElement* EppResponseCheckFeeType::toXML(DOMDocument &doc, const DOMString &tag)
{
	DOMElement* body = doc.createElement(tag);
	this->toXMLCommon(doc, tag, *body);
	return body;
}

void EppResponseCheckFeeType::toXMLCommon(DOMDocument &doc, const DOMString &tag, DOMElement& inElement)
{
	EppCommandCheckFeeType::toXMLCommon(doc, tag, inElement);
	DOMElement* elm = NULL;

	if ( NULL != this->fee )
	{
		for ( unsigned int i = 0; i < fee->size(); i++ )
		{
			EppFeeFee* member = fee->elementAt(i);

			if ( NULL != member )
			{
				elm = member->toXML(doc, "fee");

				if ( NULL != elm )
				{
					inElement.appendChild(elm);
				}
			}
		}
	}

	if ( this->fClass.length() > 0 )
	{
		elm = doc.createElement(XS("class"));
		elm->appendChild(doc.createTextNode(this->fClass));
		inElement.appendChild(elm);
	}
}

EppResponseCheckFeeType* EppResponseCheckFeeType::fromXML( const DOMNode& root )
{
	EppResponseCheckFeeType *data = new EppResponseCheckFeeType();
	data->fromXMLCommon(root, data);
	return data;
}

void EppResponseCheckFeeType::fromXMLCommon( const DOMNode& root, EppResponseCheckFeeType* data )
{
	if ( NULL == data )
	{
		return;
	}

	EppCommandCheckFeeType::fromXMLCommon(root, data);

	DOMNodeList* list = root.getChildNodes();

	if ( NULL == list )
	{
		return;
	}

	for ( unsigned int i = 0; i < list->getLength(); i++ )
	{
		DOMNode* node = list->item(i);

		if ( NULL == node )
		{
			continue;
		}

		DOMString name = node->getLocalName();

		if ( name.isNull() )
		{
			name = node->getNodeName();
		}
		if ( name.isNull() )
		{
			continue;
		}

		if ( name.equals("fee") || name.equals("fee:fee") )
		{
			EppFeeFee* f = EppFeeFee::fromXML(*node);
			if ( NULL != f )
			{
				data->addFee(f);
			}
			continue;
		}
		if ( name.equals("class") || name.equals("fee:class") )
		{
			data->setFeeClass(EppUtil::getText(*node));
			continue;
		}

	}
}

