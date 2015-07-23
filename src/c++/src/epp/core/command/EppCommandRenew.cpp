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
#include "EppCommandRenew.hpp"
#include "EppCommandRenewDomain.hpp"
#include "EppCommandRenewSvcsub.hpp"
#if defined(RTK_SUPPORT_XRI)
#include "EppCommandRenewXriNumber.hpp"
#include "EppCommandRenewXriName.hpp"
#include "EppCommandRenewXriService.hpp"
#endif

EppCommandRenew * EppCommandRenew::fromXML( const DOMNode& root )
{
	DOMString command = root.getLocalName();
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
					if( prefix.equals("domain") )
					{
						return EppCommandRenewDomain.fromXML(node);
					}
					else if( prefix.equals("svcsub") )
					{
						return EppCommandRenewSvcsub.fromXML(node);
					}
				}
		 */
		if( name.isNull() )
		{
			name = node->getNodeName();
		}
		if( name.equals("domain:renew") )
		{
			return EppCommandRenewDomain::fromXML(*node);
		}
		else if( name.equals("svcsub:renew") )
		{
			return EppCommandRenewSvcsub::fromXML(*node);
		}
#if defined(RTK_SUPPORT_XRI)		
		else if( name.equals("xriINU:renew") )
		{
			return EppCommandRenewXriNumber::fromXML(*node);
		}
		else if( name.equals("xriINA:renew") )
		{
			return EppCommandRenewXriName::fromXML(*node);
		}
		else if( name.equals("xriISV:renew") )
		{
			return EppCommandRenewXriService::fromXML(*node);
		}
#endif
	}

	return null;
}
