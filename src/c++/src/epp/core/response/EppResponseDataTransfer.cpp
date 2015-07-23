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
#include "EppResponseDataTransfer.hpp"
#include "EppUtil.hpp"

const char * EppResponseDataTransfer::STATUS_PENDING = "pending";
const char * EppResponseDataTransfer::STATUS_CLIENT_APPROVED = "clientApproved";
const char * EppResponseDataTransfer::STATUS_CLIENT_CANCELLED = "clientCancelled";
const char * EppResponseDataTransfer::STATUS_CLIENT_REJECTED = "clientRejected";
const char * EppResponseDataTransfer::STATUS_SERVER_APPROVED = "serverApproved";
const char * EppResponseDataTransfer::STATUS_SERVER_CANCELLED = "serverCanCelled";

void EppResponseDataTransfer::toXMLCommon( DOMDocument& doc, DOMElement& body )
{
	DOMElement* elm;

	if( trStatus.isNotNull() )
	{
		elm = doc.createElement(XS("trStatus"));
		elm->appendChild(doc.createTextNode(trStatus));
		body.appendChild(elm);
	}
	if( reID.isNotNull() )
	{
		elm = doc.createElement(XS("reID"));
		elm->appendChild(doc.createTextNode(reID));
		body.appendChild(elm);
	}
	if( reDate != 0 )
	{
		elm = doc.createElement(XS("reDate"));
		elm->appendChild(EppUtil::createTextNode(doc, reDate));
		body.appendChild(elm);
	}
	if( acID.isNotNull() )
	{
		elm = doc.createElement(XS("acID"));
		elm->appendChild(doc.createTextNode(acID));
		body.appendChild(elm);
	}
	if( acDate != 0 )
	{
		elm = doc.createElement(XS("acDate"));
		elm->appendChild(EppUtil::createTextNode(doc, acDate));
		body.appendChild(elm);
	}
	if( exDate != 0 )
	{
		elm = doc.createElement(XS("exDate"));
		elm->appendChild(EppUtil::createTextNode(doc, exDate));
		body.appendChild(elm);
	}
}

void EppResponseDataTransfer::fromXMLCommon( const DOMNode& node, const DOMString& name )
{
	// if( name.equals("trStatus") )
	if(    name.equals("trStatus")
		|| name.equals("contact:trStatus")
		|| name.equals("domain:trStatus")
		|| name.equals("svcsub:trStatus")
		|| name.equals("xriAU:trStatus")
		|| name.equals("xriINA:trStatus") )
	{
		trStatus = EppUtil::getText(node);
	}
	// else if( name.equals("reID") )
	else if(    name.equals("reID")
		|| name.equals("contact:reID")
		|| name.equals("domain:reID")
		|| name.equals("svcsub:reID")
		|| name.equals("xriAU:reID")
		|| name.equals("xriINA:reID") )
	{
		reID = EppUtil::getText(node);
	}
	// else if( name.equals("reDate") )
	else if(    name.equals("reDate")
		|| name.equals("contact:reDate")
		|| name.equals("domain:reDate")
		|| name.equals("svcsub:reDate")
		|| name.equals("xriAU:reDate")
		|| name.equals("xriINA:reDate") )
	{
		reDate = EppUtil::getDate(node);
	}
	//	else if( name.equals("acID") )
	else if(    name.equals("acID")
		|| name.equals("contact:acID")
		|| name.equals("domain:acID")
		|| name.equals("svcsub:acID")
		|| name.equals("xriAU:acID")
		|| name.equals("xriINA:acID") )
	{
		acID = EppUtil::getText(node);
	}
	//	else if( name.equals("acDate") )
	else if(    name.equals("acDate")
		|| name.equals("contact:acDate")
		|| name.equals("domain:acDate")
		|| name.equals("svcsub:acDate")
		|| name.equals("xriAU:acDate")
		|| name.equals("xriINA:acDate") )
	{
		acDate = EppUtil::getDate(node);
	}
	//	else if( name.equals("exDate") )
	else if(    name.equals("exDate")
		|| name.equals("contact:exDate")
		|| name.equals("domain:exDate")
		|| name.equals("svcsub:exDate")
		|| name.equals("xriAU:exDate")
		|| name.equals("xriINA:exDate") )
	{
		exDate = EppUtil::getDate(node);
	}
}
