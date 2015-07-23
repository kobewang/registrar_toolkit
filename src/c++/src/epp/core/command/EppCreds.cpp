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
#include "EppCreds.hpp"
#include "EppUtil.hpp"

DOMElement* EppCreds::toXML( DOMDocument& doc, const DOMString& tag )
{
	DOMElement* body = doc.createElement(tag);
	DOMElement* elm;

	if( clID.isNotNull() )
	{
		elm = doc.createElement(XS("clID"));
		elm->appendChild(doc.createTextNode(clID));
		body->appendChild(elm);
	}
	if( pw.isNotNull() )
	{
		elm = doc.createElement(XS("pw"));
		elm->appendChild(doc.createTextNode(pw));
		body->appendChild(elm);
	}
	if( newPW.isNotNull() )
	{
		elm = doc.createElement(XS("newPW"));
		elm->appendChild(doc.createTextNode(newPW));
		body->appendChild(elm);
	}
	if( options != null )
	{
		body->appendChild(options->toXML(doc, "options"));
	}

	return body;
}

EppCreds * EppCreds::fromXML( const DOMNode& root )
{
	DOMString clID;
	DOMString pw;
	DOMString newPW;
	EppCredsOptions * opt = null;
	EppCreds * creds = null;

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
		if( name.equals("clID") )
		{
			clID = EppUtil::getText(*node);
		}
		else if( name.equals("pw") )
		{
			pw = EppUtil::getText(*node);
		}
		else if( name.equals("newPW") )
		{
			newPW = EppUtil::getText(*node);
		}
		else if( name.equals("options") )
		{
			if( opt != null )
			{
				delete opt;
			}
			opt = EppCredsOptions::fromXML(*node);
		}
	}

	if( (clID.isNotNull()) && (pw.isNotNull()) )
	{
		creds = new EppCreds(clID, pw, newPW);
		if( opt != null )
		{
			creds->setOptions(*opt);
		}
	}

	if( opt != null )
	{
		delete opt;
	}

	return creds;
}
