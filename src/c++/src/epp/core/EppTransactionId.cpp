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
#include "EppTransactionId.hpp"
#include "EppUtil.hpp"

DOMElement* EppTransactionId::toXML( DOMDocument& doc, const DOMString& tag, bool nsflag )
{
	DOMElement* body = doc.createElement(tag);
	DOMElement* elm;

	if( clTRID.isNotNull() )
	{
		if( nsflag == true )
		{
			elm = EppUtil::createElementNS(doc, "epp", "clTRID", false);
		}
		else
		{
			elm = doc.createElement(XS("clTRID"));
		}
		elm->appendChild(doc.createTextNode(clTRID));
		body->appendChild(elm);
	}
	if( svTRID.isNotNull() )
	{
		if( nsflag == true )
		{
			elm = EppUtil::createElementNS(doc, "epp", "svTRID", false);
		}
		else
		{
			elm = doc.createElement(XS("svTRID"));
		}
		elm->appendChild(doc.createTextNode(svTRID));
		body->appendChild(elm);
	}
	return body;
}

EppTransactionId * EppTransactionId::fromXML( const DOMNode& root )
{
	DOMString clTRID = NULLXS;
	DOMString svTRID = NULLXS;

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
		if( name.equals("clTRID") || name.equals("epp:clTRID") )
		{
			clTRID = EppUtil::getText(*node);
		}
		else if( name.equals("svTRID") || name.equals("epp:svTRID") )
		{
			svTRID = EppUtil::getText(*node);
		}
	}

	if( svTRID.isNotNull() )
	{
		return new EppTransactionId(clTRID, svTRID);
	}

	return null;
}
