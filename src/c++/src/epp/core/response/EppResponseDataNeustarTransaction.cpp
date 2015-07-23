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
#include "EppResponseDataNeustarTransaction.hpp"
#include "EppUtil.hpp"

DOMElement* EppResponseDataNeustarTransaction::toXML( DOMDocument& doc, const DOMString& tag )
{
	DOMElement* elm;
	DOMElement* body = doc.createElement(tag);
	DOMElement* data = EppUtil::createElementNS(doc, "neustar", "trnData");
	body->appendChild(data);

	if( this->objID.isNotNull() )
	{
		elm = doc.createElement(XS("objId"));
		elm->appendChild(doc.createTextNode(this->objID));
		data->appendChild(elm);
	}
	if( this->objType.isNotNull() )
	{
		elm = doc.createElement(XS("objType"));
		elm->appendChild(doc.createTextNode(this->objType));
		data->appendChild(elm);
	}
	if( this->operation.isNotNull() )
	{
		elm = doc.createElement(XS("operation"));
		elm->appendChild(doc.createTextNode(this->operation));
		data->appendChild(elm);
	}
	if( this->trnDate != 0 )
	{
		elm = doc.createElement(XS("trnDate"));
		elm->appendChild(EppUtil::createTextNode(doc, this->trnDate));
		data->appendChild(elm);
	}

	return body;
}

EppResponseDataNeustarTransaction * EppResponseDataNeustarTransaction::fromXML( const DOMNode& root )
{
	DOMString objID;
	DOMString objType;
	DOMString operation;
	time_t trnDate   = 0;

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
		if( name.equals("objId") || name.equals("neustar:objId") )
		{
			objID = EppUtil::getText(*node);
		}
		else if( name.equals("objType") || name.equals("neustar:objType") )
		{
			objType = EppUtil::getText(*node);
		}
		else if( name.equals("operation") || name.equals("neustar:operation") )
		{
			operation = EppUtil::getText(*node);
		}
		else if( name.equals("trnDate") || name.equals("neustar:trnDate") )
		{
			trnDate = EppUtil::getDate(*node);
		}
	}

	return new EppResponseDataNeustarTransaction(objID, objType, operation, trnDate);
}
