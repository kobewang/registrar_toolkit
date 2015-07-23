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
package com.neulevel.epp.core;

import org.w3c.dom.*;
import java.util.*;

/**
 * This <code>EppContactType</code> class implements EPP Contact Type entity.
 * Currently, only EPP Domain objects have contact types defined:
 *
 * <UL>
 * <LI><B>EPP Domain Object</B><UL>
 *     <LI>billing</LI>
 *     <LI>admin</LI>
 *     <LI>tech</LI>
 *     </UL></LI>
 * </UL>
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppContactType extends EppEntity
{
	private String id;
	private String type;

	/**
	 * Creates an <code>EppContactType</code> object with the id of
	 * a contact object
	 */
	public EppContactType( String id )
	{
		this.id = id;
		this.type = null;
	}

	/**
	 * Creates an <code>EppContactType</code> object with the id of
	 * a contact object and a type string
	 */
	public EppContactType( String id, String type )
	{
		this.id = id;
		this.type = type;
	}

	/**
	 * Gets the id of the contact
	 */
	public String getId()
	{
		return this.id;
	}

	/**
	 * Sets the id of the contact
	 */
	public void setId( String id )
	{
		this.id = id;
	}

	/**
	 * Gets the type of the contact
	 */
	public String getType()
	{
		return this.type;
	}

	/**
	 * Sets the type of the contact
	 */
	public void setType( String type )
	{
		this.type = type;
	}

	/**
	 * Gets a sub-list of contact ids, given a list of contact types
	 * and a type string
	 *
	 * @param contact a list of EppContactType objects
	 * @param type the type of contacts to be extracted from the list
	 *
	 * @return a list of contact ids, or null if not found
	 */
	public static Vector getContactId( Vector contact, String type )
	{
		Vector list = null;
		if( type == null )
		{
			return null;
		}
		if( contact == null )
		{
			return null;
		}
		for( int i = 0; i < contact.size(); i++ )
		{
			Object obj = contact.elementAt(i);
			if( obj == null )
			{
				continue;
			}
			if( ! (obj instanceof EppContactType) )
			{
				continue;
			}
			EppContactType p = (EppContactType) obj;
			String t = p.getType();
			String s = p.getId();
			if( (s == null) || (t == null) || (! t.equals(type)) )
			{
				continue;
			}
			if( list == null )
			{
				list = new Vector();
				if( list == null )
				{
					return null;
				}
			}
			list.add(s);
		}
		return list;
	};

	/**
 	 * Converts the <code>EppContactType</code> object into an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the <code>EppContactType</code>
	 *            object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Element body = doc.createElement(tag);
		if( this.type != null )
		{
			body.setAttribute("type", this.type);
		}
		if (this.id != null )
		{
			body.appendChild(doc.createTextNode(this.id));
		}
		return body;
	}

	/**
	 * Converts an XML element into an <code>EppContactType</code> object.
	 * The caller of this method must make sure that the root node is of
	 * the EPP Contact Type type.
	 *
	 * @param root root node for an <code>EppContactType</code> object
	 *             in XML format
	 *
	 * @return an <code>EppContactType</code> object, or null if the node
	 *         is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		String id = EppUtil.getText(root);
		String type = ((Element) root).getAttribute("type");

		return new EppContactType(id, type);
	}

	public String toString()
	{
		return toString("contact");
	}
}
