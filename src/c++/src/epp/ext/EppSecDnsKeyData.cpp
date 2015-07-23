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
#include "stdio.h"
#include "stdlib.h"
#include <stdint.h>
#include "EppSecDnsKeyData.hpp"
#include "EppUtil.hpp"

EppSecDnsKeyData * EppSecDnsKeyData::fromXML( const DOMNode &root )
{
	EppSecDnsKeyData	* data = new EppSecDnsKeyData();
	if( data == null )
	{
		return data;
	}

	char *p = null;
	DOMString	value;
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
		if( name.substringData(0, 7).equals("secDNS:") )
		{
			name = name.substringData(7, name.length() - 7);
		}
		if( name.equals("flags") )
		{
			value = EppUtil::getText(*node);
			p = value.transcode();
			if( p != null )
			{
				uint16_t f = (uint16_t) ::atoi(p);
				XercesString::Delete(p);
				data->setFlags( f );
			}
		}
		else if( name.equals("protocol") )
		{
			value = EppUtil::getText(*node);
			p = value.transcode();
			if( p != null )
			{
				uint8_t proto = (uint8_t) ::atoi(p);
				XercesString::Delete(p);
				data->setProtocol( proto );
			}
		}
		else if( name.equals("alg") )
		{
			value = EppUtil::getText(*node);
			p = value.transcode();
			if( p != null )
			{
				uint8_t a = (uint8_t) ::atoi(p);
				XercesString::Delete(p);
				data->setAlgorithm(a);
			}
		}
		else if( name.equals("pubKey") )
		{
			value = EppUtil::getText(*node);
			if( value.length() > 0 )
			{
				data->setPublicKey(value);
			}
		}
	}

	return data;
}

DOMElement* EppSecDnsKeyData::toXML( DOMDocument& doc, const DOMString& tag )
{
	char	    buf[32];
	DOMElement* elm;
	DOMElement* body = doc.createElement(tag);

	elm = doc.createElement(XS("flags"));
	(void) ::sprintf(buf, "%d", this->flags);
	elm->appendChild(doc.createTextNode(XS(buf)));
	body->appendChild(elm);

	elm = doc.createElement(XS("protocol"));
	(void) ::sprintf(buf, "%d", this->protocol);
	elm->appendChild(doc.createTextNode(XS(buf)));
	body->appendChild(elm);

	elm = doc.createElement(XS("alg"));
	(void) ::sprintf(buf, "%d", this->alg);
	elm->appendChild(doc.createTextNode(XS(buf)));
	body->appendChild(elm);

	if( this->public_key.isNotNull() )
	{
		elm = doc.createElement(XS("pubKey"));
		elm->appendChild(doc.createTextNode(this->public_key));
		body->appendChild(elm);
	}

	return body;
}
