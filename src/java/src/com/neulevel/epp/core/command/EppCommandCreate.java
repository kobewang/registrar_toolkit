/*******************************************************************************
 * The MIT License (MIT)
 *  
 * Copyright (c) 2015 Neustar Inc.
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
package com.neulevel.epp.core.command;

import org.w3c.dom.*;
import com.neulevel.epp.core.*;
import com.neulevel.epp.xri.*;

/**
 * This <code>EppCommandCreate</code> class implements EPP Command Create
 * entity.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.6 $ $Date: 2013/06/17 15:52:50 $
 */
public class EppCommandCreate extends EppCommand
{
	private EppObject object;

	/**
	 * Creates an <code>EppCommandCreate</code> object
	 *
	 * @param object the <code>EppObject</code> to be processed
	 */
	public EppCommandCreate( EppObject object )
	{
		this.object = object;
	}

	/**
	 * Creates an <code>EppCommandCreate</code> object
	 *
	 * @param object the <code>EppObject</code> to be processed
	 * @param xid    the client transaction id associated with the
	 *               operation
	 */
	public EppCommandCreate( EppObject object, String xid )
	{
		this.object = object;
		this.clTRID = xid;
	}


	/**
	 * Gets the object to be processed
	 */
	public EppObject getObject()
	{
		return this.object;
	}

	/**
	 * Sets the object to be processed
	 */
	public void setObject( EppObject object )
	{
		this.object = object;
	}

	/**
	 * Converts the <code>EppCommandCreate</code> object into an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the <code>EppCommandCreate</code>
	 *            object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		return toXMLCommon(doc, tag, object);
	}

	/**
	 * Converts an XML element into an <code>EppCommandCreate</code> object.
	 * The caller of this method must make sure that the root node is of an
	 * EPP Command Create entity.
	 *
	 * @param root root node for an <code>EppCommandCreate</code> object
	 *             in XML format
	 *
	 * @return an <code>EppCommandCreate</code> object, or null if the node
	 *         is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		String command = root.getLocalName();
		NodeList list = root.getChildNodes();
		EppObject object = null;
		for( int i = 0; i < list.getLength(); i++ )
		{
			Node node = list.item(i);
			String prefix = node.getPrefix();
			String name = node.getLocalName();
			if( (prefix == null) || (name == null) )
			{
				continue;
			}
			if( name.equals(command) )
			{
				if( prefix.equals("contact") )
				{
					object = (EppObject) EppContact.fromXML(node);
				}
				else if( prefix.equals("domain") )
				{
					object = (EppObject) EppDomain.fromXML(node);
				}
				else if( prefix.equals("host") )
				{
					object = (EppObject) EppHost.fromXML(node);
				}
				else if( prefix.equals("svcsub") )
				{
					object = (EppSvcsub) EppSvcsub.fromXML(node);
				}
				else if( prefix.equals("xriAU") )
				{
					object = (EppXriAuthority) EppXriAuthority.fromXML(node);
				}
				else if( prefix.equals("xriINU") )
				{
					object = (EppXriNumber) EppXriNumber.fromXML(node);
				}
				else if( prefix.equals("xriINA") )
				{
					object = (EppXriName) EppXriName.fromXML(node);
				}
				else if( prefix.equals("xriISV") )
				{
					object = (EppXriService) EppXriService.fromXML(node);
				}
				else if( prefix.equals("xriCS") )
				{
					object = (EppXriCodeString) EppXriCodeString.fromXML(node);
				}
				if( object != null )
				{
					return new EppCommandCreate(object);
				}
			}
		}

		return null;
	}

	public String toString()
	{
		return toString("create",true);
	}
}
