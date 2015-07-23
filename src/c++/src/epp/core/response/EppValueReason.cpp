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
#include "EppValueReason.hpp"
#include "EppUtil.hpp"

DOMElement* EppValueReason::toXML( DOMDocument& doc, const DOMString& tag )
{
	DOMElement* body = doc.createElement(tag);
	DOMElement* elm;

	if( value.isNotNull() )
	{
		DOMElement* txt = doc.createElement(XS("text"));
		txt->appendChild(doc.createTextNode(value));

		if( (reason.isNull()) && (lang.isNull()) )
		{
			body->appendChild(txt);
		}
		else
		{
			elm = doc.createElement(XS("value"));
			elm->appendChild(txt);
			body->appendChild(elm);
		}
	}
	if( reason.isNotNull() )
	{
		elm = doc.createElement(XS("reason"));
		elm->appendChild(doc.createTextNode(reason));

		if( lang.isNotNull() )
		{
			elm->setAttribute(XS("lang"), lang);
		}
		body->appendChild(elm);
	}

	return body;
}

EppValueReason * EppValueReason::fromXML( const DOMNode& root )
{
	EppValueReason * result = new EppValueReason();
	if( result == null )
	{
		return result;
	}
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
		if( name.equals("value") )
		{
			//result.value = EppUtil.getText(node);
			DOMNodeList* clist = node->getChildNodes();
			for( unsigned int j = 0; j < clist->getLength(); j++ )
			{
				DOMNode* cnode = clist->item(j);
				DOMString cname = cnode->getLocalName();

				if( cname.isNull() )
				{
					cname = cnode->getNodeName();
				}
				if( cname.isNull() )
				{
					continue;
				}
				if( cname.equals("text") == false )
				{
					continue;
				}
				result->value = EppUtil::getText(*cnode);
				break;
			}
		}
		else if( name.equals("reason") )
		{
			result->reason = EppUtil::getText(*node);
			DOMString language = ((DOMElement *) node)->getAttribute(XS("lang"));
			if( language.isNotNull() && language.length() > 0 )
			{
				result->lang = language;
			}
		}
		else if( name.equals("text") )
		{
			result->value = EppUtil::getText(*node);
			result->reason = null;
			result->lang = null;
		}
	}
	return result;
}
