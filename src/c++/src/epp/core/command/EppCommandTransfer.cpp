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
#include "EppCommandTransfer.hpp"
#include "EppCommandTransferContact.hpp"
#include "EppCommandTransferDomain.hpp"
#include "EppCommandTransferSvcsub.hpp"
#if defined(RTK_SUPPORT_XRI)
#include "EppCommandTransferXriAuthority.hpp"
#include "EppCommandTransferXriName.hpp"
#endif

const char * EppCommandTransfer::OPTYPE_APPROVE = "approve";
const char * EppCommandTransfer::OPTYPE_CANCEL = "cancel";
const char * EppCommandTransfer::OPTYPE_QUERY = "query";
const char * EppCommandTransfer::OPTYPE_REJECT = "reject";
const char * EppCommandTransfer::OPTYPE_REQUEST = "request";

EppCommandTransfer * EppCommandTransfer::fromXMLPoll( const DOMNode& root )
{
	EppCommandTransfer * cmd = null;
	DOMString prefix = root.getPrefix();

	if( prefix.isNull() )
	{
		prefix = root.getNodeName();
	}
	if( prefix.isNotNull() )
	{
		//if( prefix.equals("contact") )
		if( prefix.equals("contact") || prefix.equals("contact:transfer") )
		{
			cmd = EppCommandTransferContact::fromXML(root);
		}
		//else if( prefix.equals("domain") )
		else if( prefix.equals("domain") || prefix.equals("domain:transfer") )
		{
			cmd = EppCommandTransferDomain::fromXML(root);
		}
		//else if( prefix.equals("svcsub") )
		else if( prefix.equals("svcsub") || prefix.equals("svcsub:transfer") )
		{
			cmd = EppCommandTransferSvcsub::fromXML(root);
		}
#if defined(RTK_SUPPORT_XRI)		
		//else if( prefix.equals("xriAU") )
		else if( prefix.equals("xriAU") || prefix.equals("xriAU:transfer") )
		{
			cmd = EppCommandTransferXriAuthority::fromXML(root);
		}
		//else if( prefix.equals("xriAU") )
		else if( prefix.equals("xriINA") || prefix.equals("xriINA:transfer") )
		{
			cmd = EppCommandTransferXriName::fromXML(root);
		}
#endif
	}
	return cmd;
}

EppCommandTransfer * EppCommandTransfer::fromXML( const DOMNode& root )
{
	DOMString command = root.getLocalName();
	EppCommandTransfer * cmd = null;

	DOMNodeList* list = root.getChildNodes();

	for( unsigned int i = 0; i < list->getLength(); i++ )
	{
		DOMNode* node = list->item(i);
		DOMString prefix = node->getPrefix();
		DOMString name = node->getLocalName();
		/*
				if( (prefix == null) || (name == null) )
				{
					continue;
				}
				if( name.equals(command) )
				{
					cmd = fromXMLPoll(node);
					break;
				}
		 */
		if( name.isNull() )
		{
			name = node->getNodeName();
		}
		if(    name.equals("contact:transfer")
			|| name.equals("domain:transfer")
			|| name.equals("svcsub:transfer")
			|| name.equals("xriAU:transfer")
			|| name.equals("xriINA:transfer") )
		{
			cmd = fromXMLPoll(*node);
			break;
		}
	}

	if( cmd != null )
	{
		DOMElement * elm = (DOMElement *) &root;
		cmd->setOperation(elm->getAttribute(XS("op")));
	}

	return cmd;
}
