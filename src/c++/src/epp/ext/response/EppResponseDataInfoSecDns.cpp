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
#include "EppResponseDataInfoSecDns.hpp"
#include "EppUtil.hpp"

EppResponseDataInfoSecDns * EppResponseDataInfoSecDns::fromXML( const DOMNode &root )
{
	EppResponseDataInfoSecDns * cmd  = new EppResponseDataInfoSecDns();
	if( cmd == null )
	{
		return null;
	}
	DOMNodeList* list  = root.getChildNodes();
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
		if( name.equals("dsData") )
		{
			EppSecDnsDsData * ds = EppSecDnsDsData::fromXML(*node);
			if( ds != null )
			{
				cmd->add(*ds);
				delete ds;
			}
		}
		else if( name.equals("keyData") )
		{
			// FIXME(zhang) not supported
			EppSecDnsKeyData *kd = EppSecDnsKeyData::fromXML(*node);
			if( kd != null )
			{
				cmd->keyDataPresent = true;
				cmd->add(*kd);
				delete kd;
			}
		}
		else if( name.equals("maxSigLife") )
		{
			DOMString value = EppUtil::getText(*node);
			char *p = value.transcode();
			if( p != null )
			{
				cmd->maxSigLife = ::atoi(p);
				XercesString::Delete(p);
			}
		}
	}
	return cmd;
}

DOMElement* EppResponseDataInfoSecDns::toXML( DOMDocument& doc, const DOMString& tag )
{
	DOMElement* body;
	if( secDnsVersion == EPPSECDNS_10 )
	{
		body = EppUtil::createElementNS(doc, "secDNS", (tag.isNull()) ? XS("infData") : tag, false, "-1.0");
	}
	else if ( secDnsVersion == EPPSECDNS_11  )
	{
		body = EppUtil::createElementNS(doc, "secDNS", (tag.isNull()) ? XS("infData") : tag, false, "-1.1");
	}
	else
	{
		body = EppUtil::createElementNS(doc, "secDNS", (tag.isNull()) ? XS("infData") : tag);
	}
	if( this->maxSigLife != -1 )
	{
		DOMElement* elm = doc.createElement(XS("maxSigLife"));
		char cBuffer[32];
		sprintf(cBuffer, "%d", this->maxSigLife);
		elm->appendChild(doc.createTextNode(XS(cBuffer)));

		body->appendChild(elm);
	}
	for( unsigned int i = 0; i < this->dsDataList->size(); i++ )
	{
		EppSecDnsDsData ds = dsDataList->elementAt(i);
		DOMElement* elm = ds.toXML(doc, "dsData");
		body->appendChild(elm);
	}
	if( this->keyDataList != null && keyDataPresent == true )
	{
		for( unsigned int i = 0; i < this->keyDataList->size(); i++ )
		{
			EppSecDnsKeyData kd = keyDataList->elementAt(i);
			DOMElement* elm = kd.toXML(doc, "keyData");
			body->appendChild(elm);
		}
	}
	return body;
}
