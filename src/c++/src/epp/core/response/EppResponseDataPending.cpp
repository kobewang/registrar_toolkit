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
#include "EppResponseDataPending.hpp"
#include "EppUtil.hpp"

void EppResponseDataPending::toXMLCommon( DOMDocument& doc, DOMElement& body )
{
	DOMElement* elm;

	if(    (paTRID.getClientTransactionId().isNotNull())
		&& (paTRID.getServiceTransactionId().isNotNull()) )
	{
		elm = paTRID.toXML(doc, "paTRID", true);
		body.appendChild(elm);
	}
	if( paDate != null )
	{
		elm = doc.createElement(XS("paDate"));
		elm->appendChild(EppUtil::createTextNode(doc, paDate));
		body.appendChild(elm);
	}
}

void EppResponseDataPending::fromXMLCommon( const DOMNode& node, const DOMString& name, const DOMString& ns )
{
	if( name.equals("paTRID") || name.equals((XS)ns + ":paTRID") )
	{
		EppTransactionId * xid = EppTransactionId::fromXML(node);
		if( xid != null )
		{
			paTRID = *xid;
			delete xid;
		}
	}
	else if( name.equals("paDate") || name.equals((XS)ns + ":paDate") )
	{
		paDate = EppUtil::getDate(node);
	}
}
