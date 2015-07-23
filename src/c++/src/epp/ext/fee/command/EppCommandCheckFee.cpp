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
#include "EppCommandCheckFee.hpp"
#include "EppUtil.hpp"

EppCommandCheckFee* EppCommandCheckFee::fromXML( const DOMNode& root )
{
	EppCommandCheckFee *data = new EppCommandCheckFee();

	DOMNodeList* list = root.getChildNodes();

	if( NULL == list )
	{
		return data;
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

		if( name.equals("domain") || name.equals("fee:domain") )
		{
			EppCommandCheckFeeType *feeDomain = EppCommandCheckFeeType::fromXML(*node);

			if( NULL != feeDomain )
			{
				data->add(feeDomain);
			}
			continue;
		}
	}

	return data;
}

DOMElement* EppCommandCheckFee::toXML( DOMDocument& doc, const DOMString& tag )
{
	DOMElement* fExt = EppUtil::createElementNS(doc, "fee", ( tag.isNull() ) ? "check" : tag, true, ( char* ) FEE_VERSION.c_str());

	if( NULL == feeDomains )
	{
		return fExt;
	}

	for( unsigned int i = 0; i < feeDomains->size(); i++ )
	{
		EppCommandCheckFeeType* member = feeDomains->elementAt(i);

		if( NULL != member )
		{
			DOMElement* element = member->toXML(doc, "domain");

			if( NULL != element )
			{
				fExt->appendChild(element);
			}
		}
	}
	return fExt;
}
