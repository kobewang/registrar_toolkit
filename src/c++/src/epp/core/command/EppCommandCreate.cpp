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
#include "EppCommandCreate.hpp"
#include "EppContact.hpp"
#include "EppDomain.hpp"
#include "EppHost.hpp"
#include "EppSvcsub.hpp"
#if defined(RTK_SUPPORT_XRI)
#include "EppXriAuthority.hpp"
#include "EppXriNumber.hpp"
#include "EppXriName.hpp"
#include "EppXriService.hpp"
#include "EppXriCodeString.hpp"
#endif
#include "EppUtil.hpp"

DOMElement* EppCommandCreate::toXML( DOMDocument& doc, const DOMString& tag )
{
	return toXMLCommon(doc, tag, object);
}

EppCommandCreate* EppCommandCreate::fromXML( const DOMNode& root )
{
	// FIXME(zhang)
	DOMString command = root.getLocalName();
	if( command.isNull() )
	{
		command = root.getNodeName();
	}
	DOMNodeList* list = root.getChildNodes();
	EppObject * object = null;

	for( unsigned int i = 0; i < list->getLength(); i++ )
	{
		DOMNode* node = list->item(i);
		DOMString prefix = node->getPrefix();
		DOMString name = node->getLocalName();

		if( name.isNull() )
		{
			name = node->getNodeName();
		}
		if( name.isNull() )
		{
			continue;
		}
		/*
				if( prefix == null )
				{
					if( name.substringData(name.length() - command.length() - 1,
					command.length() + 1).equals(DOMString(":") + command) )
					{
						prefix = name.substringData(0, name.length() - command.length() - 1);
					}
				}
				if( (prefix == null) || (name == null) )
				{
					continue;
				}
				if( name.equals(command) )
				{
					if( prefix.equals("contact") )
					{
						object = EppContact::fromXML(node);
					}
					else if( prefix.equals("domain") )
					{
						object = EppDomain::fromXML(node);
					}
					else if( prefix.equals("host") )
					{
						object = EppHost::fromXML(node);
					}
					else if( prefix.equals("svcsub") )
					{
						object = EppSvcsub::fromXML(node);
					}
				}
		 */
		if( name.equals("contact") || name.equals("contact:create") )
		{
			object = EppContact::fromXML(*node);
		}
		else if( name.equals("domain") || name.equals("domain:create") )
		{
			object = EppDomain::fromXML(*node);
		}
		else if( name.equals("host") || name.equals("host:create") )
		{
			object = EppHost::fromXML(*node);
		}
		else if( name.equals("svcsub") || name.equals("svcsub:create") )
		{
			object = EppSvcsub::fromXML(*node);
		}
#if defined(RTK_SUPPORT_XRI)		
		else if( name.equals("xriAU") || name.equals("xriAU:create") )
		{
			object = EppXriAuthority::fromXML(*node);
		}
		else if( name.equals("xriINU") || name.equals("xriINU:create") )
		{
			object = EppXriNumber::fromXML(*node);
		}
		else if( name.equals("xriINA") || name.equals("xriINA:create") )
		{
			object = EppXriName::fromXML(*node);
		}
		else if( name.equals("xriISV") || name.equals("xriISV:create") )
		{
			object = EppXriService::fromXML(*node);
		}
		else if( name.equals("xriCS") || name.equals("xriCS:create") )
		{
			object = EppXriCodeString::fromXML(*node);
		}
#endif
		if( object != null )
		{
			EppCommandCreate * cmd = new EppCommandCreate(object);
			cmd->freeable = true;
			return cmd;
		}
	}

	return null;
}
