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
 * This <code>EppHostAttribute</code> class implements EPP hostAttrType entity
 * that is used by <code>EppDomain</code> objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppHostAttribute extends EppEntity
{
	private String	hostname;
	private Vector	hostaddr;

	/**
	 * Creates an <code>EppHostAttribute</code> object with a host name
	 */
	public EppHostAttribute( String hostname )
	{
		this(hostname, null);
	}

	/**
	 * Creates an <code>EppHostAttribute</code> object with a host name
	 * and a list of <code>EppIpAddress</code> objects
	 */
	public EppHostAttribute( String hostname, Vector address )
	{
		this.hostname = hostname;
		this.hostaddr = address;
	}

	/**
	 * Gets the host name
	 */
	public String getHostName()
	{
		return this.hostname;
	}

	/**
	 * Sets the host name
	 */
	public void setHostName( String hostname )
	{
		this.hostname = hostname;
	}

	/**
	 * Gets the list of ip addresses associated with the host name
	 */
	public Vector getIpAddress()
	{
		return this.hostaddr;
	}

	/**
	 * Sets the list of ip addresses associated with the host name
	 */
	public void setIpAddress( Vector address )
	{
		this.hostaddr = address;
	}

	/**
	 * Adds an ip address to the list of ip addresses associated
	 * with the host name
	 */
	public void addIpAddress( EppIpAddress address )
	{
		if( this.hostaddr == null )
		{
			this.hostaddr = new Vector();
		}
		this.hostaddr.addElement(address);
	}

	/**
 	 * Converts the <code>EppHostAttribute</code> object into an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the <code>EppHostAttribute</code>
	 *            object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Element body = doc.createElement(tag);
		Element elm;
	
		if( this.hostname != null )
		{
			elm = doc.createElement("hostName");
			elm.appendChild(doc.createTextNode(this.hostname));
			body.appendChild(elm);
		}
		if( this.hostaddr != null )
		{
			for( int i = 0; i < this.hostaddr.size(); i++ )
			{
				Object obj = this.hostaddr.elementAt(i);
				if( obj == null )
				{
					continue;
				}
				if( ! (obj instanceof EppIpAddress) )
				{
					continue;
				}
				EppIpAddress ip = (EppIpAddress) obj;
				body.appendChild(ip.toXML(doc, "hostAddr"));
			}
		}
		return body;
	}

	/**
	 * Converts an XML element into an <code>EppHostAttribute</code> object.
	 * The caller of this method must make sure that the root node is of
	 * the EPP Contact Type type.
	 *
	 * @param root root node for an <code>EppHostAttribute</code> object
	 *             in XML format
	 *
	 * @return an <code>EppHostAttribute</code> object, or null if the node
	 *         is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		String hostname = null;
		Vector address  = null;
		NodeList list = root.getChildNodes();
		for( int i = 0; i < list.getLength(); i++ )
		{
			Node node = list.item(i);
			String name = node.getLocalName();
			if( name == null )
			{
				continue;
			}
			if( name.equals("hostName") )
			{
				hostname = EppUtil.getText(node);
			}
			else if( name.equals("hostAddr") )
			{
				EppIpAddress ip = (EppIpAddress) EppIpAddress.fromXML(node);
				if( ip != null )
				{
					if( address == null )
					{
						address = new Vector();
					}
					address.addElement(ip);
				}
			}
		}

		return new EppHostAttribute(hostname, address);
	}

	public String toString()
	{
		return toString("hostAttr");
	}
}
