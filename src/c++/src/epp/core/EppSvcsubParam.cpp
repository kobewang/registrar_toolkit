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
#include "EppSvcsubParam.hpp"
#include "EppUtil.hpp"
//#include "util/ValueVectorOf.hpp"

DOMElement* EppSvcsubParam::toXML( DOMDocument& doc, const DOMString& tag )
{
	DOMElement* body = doc.createElement(tag);
	if( name.isNotNull() )
	{
		body->setAttribute(XS("name"), name);
	}
	if( value.isNotNull() )
	{
		body->appendChild(doc.createTextNode(value));
	}
	return body;
}

EppSvcsubParam * EppSvcsubParam::fromXML( const DOMNode& root )
{
	DOMString name  = ((DOMElement *) &root)->getAttribute(XS("name"));
	DOMString value = EppUtil::getText(root);

	return new EppSvcsubParam(name, value);
}

OldValueVectorOf<DOMString> * EppSvcsubParam::getParamValue( OldValueVectorOf<EppSvcsubParam> * param, DOMString name )
{
	OldValueVectorOf<DOMString> * list = null;
	if( name.isNull() )
	{
		return null;
	}
	if( param == null )
	{
		return null;
	}
	for( unsigned int i = 0; i < param->size(); i++ )
	{
		EppSvcsubParam p = param->elementAt(i);
		if( p.getName().equals(name) )
		{
			if( list == null )
			{
				int size = param->size() / 2;
				if( size <= 0 )
				{
					size = 1;
				}
				list = new OldValueVectorOf<DOMString>(size);
				if( list == null )
				{
					return null;
				}
			}
			list->addElement(p.getValue());
		}
	}
	return list;
}
