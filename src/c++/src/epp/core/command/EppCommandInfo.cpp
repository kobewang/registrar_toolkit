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
#include "EppCommandInfo.hpp"
#include "EppCommandInfoContact.hpp"
#include "EppCommandInfoDomain.hpp"
#include "EppCommandInfoHost.hpp"
#include "EppCommandInfoSvcsub.hpp"
#if defined(RTK_SUPPORT_XRI)
#include "EppCommandInfoXriAuthority.hpp"
#include "EppCommandInfoXriNumber.hpp"
#include "EppCommandInfoXriName.hpp"
#include "EppCommandInfoXriService.hpp"
#include "EppCommandInfoXriCodeString.hpp"
#endif

EppCommandInfo * EppCommandInfo::fromXML( const DOMNode& root )
{
	DOMString command = root.getLocalName();
	DOMNodeList* list = root.getChildNodes();

	for( unsigned int i = 0; i < list->getLength(); i++ )
	{
		DOMNode* node = list->item(i);
		DOMString prefix = node->getPrefix();
		DOMString name = node->getLocalName();

		if( name.isNull() )
		{
			name = node->getNodeName();
		}
		/*
				if( (prefix == null) || (name == null) )
				{
					continue;
				}
				if( name.equals(command) )
				{
					if( prefix.equals("contact") )
					{
						return EppCommandInfoContact.fromXML(node);
					}
					else if( prefix.equals("domain") )
					{
						return EppCommandInfoDomain.fromXML(node);
					}
					else if( prefix.equals("host") )
					{
						return EppCommandInfoHost.fromXML(node);
					}
					else if( prefix.equals("svcsub") )
					{
						return EppCommandInfoSvcsub.fromXML(node);
					}
				}
		 */
		if( name.equals("contact") || name.equals("contact:info") )
		{
			return EppCommandInfoContact::fromXML(*node);
		}
		else if( name.equals("domain") || name.equals("domain:info") )
		{
			return EppCommandInfoDomain::fromXML(*node);
		}
		else if( name.equals("host") || name.equals("host:info") )
		{
			return EppCommandInfoHost::fromXML(*node);
		}
		else if( name.equals("svcsub") || name.equals("svcsub:info") )
		{
			return EppCommandInfoSvcsub::fromXML(*node);
		}
#if defined(RTK_SUPPORT_XRI)		
		else if( name.equals("xriAU") || name.equals("xriAU:info") )
		{
			return EppCommandInfoXriAuthority::fromXML(*node);
		}
		else if( name.equals("xriINU") || name.equals("xriINU:info") )
		{
			return EppCommandInfoXriNumber::fromXML(*node);
		}
		else if( name.equals("xriINA") || name.equals("xriINA:info") )
		{
			return EppCommandInfoXriName::fromXML(*node);
		}
		else if( name.equals("xriISV") || name.equals("xriISV:info") )
		{
			return EppCommandInfoXriService::fromXML(*node);
		}
		else if( name.equals("xriCS") || name.equals("xriCS:info") )
		{
			return EppCommandInfoXriCodeString::fromXML(*node);
		}
#endif
	}

	return null;
}
