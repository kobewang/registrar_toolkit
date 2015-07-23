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

/**
 * This <code>EppContactData</code> class implements EPP Contact Postal Type
 * for recording contact data.
 *
 * <P>An EPP Postal Type entity has 3 components:
 * <UL>
 * <LI>a required <I>name</I> line, with a maximum length of 255 characters.</LI>
 * <LI>an optional "<I>org</I>line, with a maximum length of 255 characters.</LI>
 * <LI>the address information, defined as an <code>EppAddress</code> object</LI>
 * </UL>
 *
 * <P>In EPP 04, the maximum length of the <I>name</I> and <I>org</I> fields
 * was specified as 32, which was extended by NeuLevel to 64.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppContactData extends EppEntity
{
	private String     name;
	private String     org;         // optional
	private EppAddress addr;

	/**
	 * Creates an <code>EppContactData</code> object
	 */
	public EppContactData()
	{
		this(null, null, null);
	}

	/**
	 * Creates an <code>EppContactData</code> object without organization
	 * value
	 *
	 * @param name name of the contact, either a person or a company
	 * @param address address of the contact
	 */
	public EppContactData( String name, EppAddress address )
	{
		this(name, null, address);
	}

	/**
	 * Creates an <code>EppContactData</code> object with organization name
	 *
	 * @param name name of the contact, either a person or a company
	 * @param org name of the organization for the contact
	 * @param address address of the contact
	 */
	public EppContactData( String name, String org, EppAddress address )
	{
		this.name = name;
		this.org  = org;
		this.addr = address;
	}

	/**
	 * Gets the name of the contact
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * Sets the name of the contact
	 */
	public void setName( String name )
	{
		this.name = name;
	}

	/**
	 * Gets the organization name of the contact
	 */
	public String getOrganization()
	{
		return this.org;
	}

	/**
	 * Sets the organization name of the contact
	 */
	public void setOrganization( String org )
	{
		this.org = org;
	}

	/**
	 * Gets the address of the contact
	 */
	public EppAddress getAddress()
	{
		return this.addr;
	}

	/**
	 * Sets the address of the contact
	 */
	public void setAddress( EppAddress address )
	{
		this.addr = address;
	}

	/**
	 * Converts the <code>EppContactData</code> object into an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the <code>EppContactData</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Element body = doc.createElement(tag);
		Element elm;
		if( name != null )
		{
			elm = doc.createElement("name");
			elm.appendChild(doc.createTextNode(name));
			body.appendChild(elm);
		}
		if( org != null )
		{
			elm = doc.createElement("org");
			elm.appendChild(doc.createTextNode(org));
			body.appendChild(elm);
		}
		if( addr != null )
		{
			body.appendChild(addr.toXML(doc, "addr"));
		}

		return body;
	}

	/**
	 * Converts an XML element into an <code>EppContactData</code> object.
	 * The caller of this method must make sure that the root node is of
	 * the EPP Contact multiDataType.
	 *
	 * @param root root node for an <code>EppContactData</code> object in
	 *             XML format
	 *
	 * @return an <code>EppContactData</code> object, or null if the node is
	 *         invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		String contact_name = null;
		String contact_org  = null;
		EppAddress contact_addr = null;

		NodeList list = root.getChildNodes();
		for( int i = 0; i < list.getLength(); i++ )
		{
			Node node = list.item(i);
			String name = node.getLocalName();
			if( name == null )
			{
				continue;
			}
			if( name.equals("name") )
			{
				contact_name = EppUtil.getText(node);
			}
			else if( name.equals("org") )
			{
				contact_org = EppUtil.getText(node);
			}
			else if( name.equals("addr") )
			{
				contact_addr = (EppAddress) EppAddress.fromXML(node);
			}
		}
		return new EppContactData(contact_name, contact_org, contact_addr);
	}

	public String toString()
	{
		return toString("contact-data");
	}
}
