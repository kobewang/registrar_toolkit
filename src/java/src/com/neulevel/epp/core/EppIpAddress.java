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
 * This <code>EppIpAddress</code> class implements EPP IP Address entity.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppIpAddress extends EppEntity
{
	/**
	 * IP address type for "v4"
	 */
	public static final String TYPE_V4 = "v4";
	/**
	 * IP address type for "v6"
	 */
	public static final String TYPE_V6 = "v6";

	private String ip;
	private String address;

	/**
	 * Creates an <code>EppAddress</code> object with the default
	 * address type, "v4"
	 */
	public EppIpAddress( String address )
	{
		this(address, null);
	}

	/**
	 * Creates an <code>EppAddress</code> object with an address type,
	 * either "v4" (default) or "v6"
	 */
	public EppIpAddress( String address, String type )
	{
		this.address = address;
		this.ip = type;
	}

	/**
	 * Gets the IP address type, either "v4" or "v6"
	 */
	public String getType()
	{
		return this.ip;
	}

	/**
	 * Sets the IP address type, either "v4" or "v6"
	 */
	public void setType( String type )
	{
		this.ip = type;
	}

	/**
	 * Gets the IP address
	 */
	public String getAddress()
	{
		return this.address;
	}

	/**
	 * Sets the IP address
	 */
	public void setAddress( String address )
	{
		this.address = address;
	}

	/**
	 * Converts the <code>EppIpAddress</code> object into an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the <code>EppIpAddress</code>
	 *              object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag)
	{
		Element body = doc.createElement(tag);
		if( ip != null )
		{
			body.setAttribute("ip", ip);
		}
		if( address != null )
		{
			body.appendChild(doc.createTextNode(address));
		}
		return body;
	}

	/**
	 * Converts an XML element into an <code>EppIpAddress</code> object.
	 * The caller of this method must make sure that the root node is of
	 * the EPP IP Address type.
	 *
	 * @param root root node for an <code>EppIpAddress</code> object
	 *             in XML format
	 *
	 * @return an <code>EppIpAddress</code> object, or null if the node
	 *         is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		String address = EppUtil.getText(root);
		String ip = ((Element) root).getAttribute("ip");

		return new EppIpAddress(address, ip);
	}

	public String toString()
	{
		return toString("address");
	}
}
