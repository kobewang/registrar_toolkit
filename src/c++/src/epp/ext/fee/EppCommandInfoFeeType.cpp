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
#include "EppCommandInfoFeeType.hpp"

DOMElement* EppCommandInfoFeeType::toXML( DOMDocument &doc, const DOMString &tag )
{
	DOMElement* body = doc.createElement(tag);
	this->toXMLCommon(doc, tag, *body);
	return body;
}

void EppCommandInfoFeeType::toXMLCommon( DOMDocument &doc, const DOMString &tag, DOMElement& inElement )
{
	DOMElement* elm = NULL;

	if( this->currency.length() > 0 )
	{
		elm = doc.createElement(XS("currency"));
		elm->appendChild(doc.createTextNode(this->currency));
		inElement.appendChild(elm);
	}

	if( NULL != this->command )
	{
		elm = command->toXML(doc, XS("command"));
		inElement.appendChild(elm);
	}

	if( NULL != this->period )
	{
		elm = period->toXML(doc, XS("period"));
		inElement.appendChild(elm);
	}

}

EppCommandInfoFeeType* EppCommandInfoFeeType::fromXML( const DOMNode& root )
{
	EppCommandInfoFeeType *data = new EppCommandInfoFeeType();
	data->fromXMLCommon(root, data);
	return data;
}

void EppCommandInfoFeeType::fromXMLCommon( const DOMNode& root, EppCommandInfoFeeType* data )
{
	if( NULL == data )
	{
		return;
	}

	DOMNodeList* list = root.getChildNodes();

	if( NULL == list )
	{
		return;
	}

	for( unsigned int i = 0; i < list->getLength(); i++ )
	{
		DOMNode* node = list->item(i);

		if( NULL == node )
		{
			continue;
		}

		DOMString name = node->getLocalName();

		if( name.isNull() )
		{
			name = node->getNodeName();
		}
		if( name.isNull() )
		{
			continue;
		}

		if( name.equals("currency") || name.equals("fee:currency") )
		{
			data->setCurrency(EppUtil::getText(*node));
			continue;
		}
		if( name.equals("command") || name.equals("fee:command") )
		{
			EppFeeCommand* cmd = EppFeeCommand::fromXML(*node);

			if( NULL != cmd )
			{
				data->setCommand(cmd);
			}
			continue;
		}
		if( name.equals("period") || name.equals("fee:period") )
		{
			EppPeriod* period = EppPeriod::fromXML(*node);

			if( NULL != period )
			{
				data->setPeriod(period);
			}
			continue;
		}
	}
}

