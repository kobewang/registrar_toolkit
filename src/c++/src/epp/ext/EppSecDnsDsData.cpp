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
#include "EppSecDnsDsData.hpp"
#include "EppUtil.hpp"

const char EppSecDnsDsData::hex[16] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

EppSecDnsDsData * EppSecDnsDsData::fromXML( const DOMNode &root )
{
	EppSecDnsDsData	* data = new EppSecDnsDsData();
	if( data == null )
	{
		return data;
	}

	int		val   = 0;
	char	      * p     = null;
	DOMString	value;
#if defined(EPPDNSSEC_COMPT)
	time_t		date  = null;
#endif
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
		if( name.equals("keyTag") )
		{
			value = EppUtil::getText(*node);
			p = value.transcode();
			if( p != null && EppUtil::isNumeric(p) )
			{
				val = ::atoi(p);
				XercesString::Delete(p);
				data->setKeyTag(val);
			}
		}
		else if( name.equals("alg") )
		{
			value = EppUtil::getText(*node);
			p = value.transcode();
			if( p != null && EppUtil::isNumeric(p) )
			{
				val = ::atoi(p);
				XercesString::Delete(p);
				data->setAlgorithm(val);
			}
		}
		else if( name.equals("digestType") )
		{
			value = EppUtil::getText(*node);
			p = value.transcode();
			if( p != null )
			{
				val = ::atoi(p);
				XercesString::Delete(p);
				data->setDigestType(val);
			}
		}
		else if( name.equals("digest") )
		{
			value = EppUtil::getText(*node);
			if( value.isNotNull() && value.length() > 0 )
			{
				data->setDigestString(value);
			}
		}
#if defined(EPPDNSSEC_COMPT)
		else if( name.equals("sDate") )
		{
			date = EppUtil::getDate(*node);
			if( date != null )
			{
				data->setStartDate(date);
			}
		}
		else if( name.equals("eDate") )
		{
			date = EppUtil::getDate(*node);
			if( date != null )
			{
				data->setEndDate(date);
			}
		}
		else if( name.equals("vInterval") )
		{
			value = EppUtil::getText(*node);
			if( value.isNotNull() && value.length() > 0 )
			{
				data->setValidationInterval(value);
			}
		}
#endif
		else if( name.equals("keyData") )
		{
			EppSecDnsKeyData *kd = EppSecDnsKeyData::fromXML(*node);
			if( null != kd )
			{
				data->keyDataPresent = true;/*NOTICE: this is redundant*/
				data->setKeyData( *kd );
				delete kd;
			}
		}
	}
	return data;
}

DOMElement* EppSecDnsDsData::toXML( DOMDocument& doc, const DOMString& tag )
{
	char	    buf[32];
	DOMElement* elm;
	DOMElement* body = doc.createElement(tag);

	elm = doc.createElement(XS("keyTag"));
	(void) ::sprintf(buf, "%d", this->keyTag);
	elm->appendChild(doc.createTextNode(XS(buf)));
	body->appendChild(elm);

	elm = doc.createElement(XS("alg"));
	(void) ::sprintf(buf, "%d", this->alg);
	elm->appendChild(doc.createTextNode(XS(buf)));
	body->appendChild(elm);

	elm = doc.createElement(XS("digestType"));
	(void) ::sprintf(buf, "%d", this->digestType);
	elm->appendChild(doc.createTextNode(XS(buf)));
	body->appendChild(elm);

	if( this->digest.isNotNull() )
	{
		elm = doc.createElement(XS("digest"));
		elm->appendChild(doc.createTextNode(this->digest));
		body->appendChild(elm);
	}
#if defined(EPPDNSSEC_COMPT)
	if( this->sDate != null )
	{
		elm = doc.createElement(XS("sDate"));
		elm->appendChild(EppUtil::createTextNode(doc, this->sDate));
		body->appendChild(elm);
	}

	if( this->eDate != null )
	{
		elm = doc.createElement(XS("eDate"));
		elm->appendChild(EppUtil::createTextNode(doc, this->eDate));
		body->appendChild(elm);
	}

	if( this->vInterval.isNotNull() )
	{
		elm = doc.createElement(XS("vInterval"));
		elm->appendChild(doc.createTextNode(this->vInterval));
		body->appendChild(elm);
	}
#endif
	if( true == this->keyDataPresent )
	{
		DOMElement* element = this->secKeyData.toXML(doc, "keyData");
		body->appendChild(element);
	}

	return body;
}
