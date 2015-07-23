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
 * This <code>EppTransactionId</code> class implements EPP trIDType entity.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppTransactionId extends EppEntity
{
	private String clTRID;
	private String svTRID;

	/**
	 * Creates an <code>EppTransactionId</code> object, given only
	 * service transaction id
	 *
	 * @param serviceXID service transaction id
	 */
	public EppTransactionId( String serviceXID )
	{
		this.clTRID = null;
		this.svTRID = serviceXID;
	}

	/**
	 * Creates an <code>EppTransactionId</code> object, given the client
	 * transaction id and service transaction id
	 *
	 * @param clientXID client transaction id
	 * @param serviceXID service transaction id
	 */
	public EppTransactionId( String clientXID, String serviceXID )
	{
		this.clTRID = clientXID;
		this.svTRID = serviceXID;
	}

	/**
	 * Gets client transaction id
	 */
	public String getClientTransactionId()
	{
		return this.clTRID;
	}

	/**
	 * Sets client transaction id
	 */
	public void setClientTransactionId( String clientXID )
	{
		this.clTRID = clientXID;
	}

	/**
	 * Gets service transaction id
	 */
	public String getServiceTransactionId()
	{
		return this.svTRID;
	}

	/**
	 * Sets service transaction id
	 */
	public void setServiceTransactionId( String serviceXID )
	{
		this.svTRID = serviceXID;
	}

	/**
	 * Converts the <code>EppTransactionId</code> object into an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the <code>EppTransactionId</code>
	 *            object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		return this.toXML(doc, tag, false);
	}

	/**
	 * Converts the <code>EppTransactionId</code> object into an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the <code>EppTransactionId</code>
	 *            object
	 * @param namespace the flag indicating if "epp" name space prefix is reqired
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag, boolean namespace )
	{
		Element body = doc.createElement(tag);
		Element elm;

		if( clTRID != null )
		{
			if( namespace == true )
			{
				elm = EppUtil.createElementNS(doc, "epp", "clTRID", false);
			}
			else
			{
				elm = doc.createElement("clTRID");
			}
			elm.appendChild(doc.createTextNode(clTRID));
			body.appendChild(elm);
		}
		if( svTRID != null )
		{
			if( namespace == true )
			{
				elm = EppUtil.createElementNS(doc, "epp", "svTRID", false);
			}
			else
			{
				elm = doc.createElement("svTRID");
			}
			elm.appendChild(doc.createTextNode(svTRID));
			body.appendChild(elm);
		}

		return body;
	}

	/**
	 * Converts an XML element into an <code>EppTransactionId</code> object.
	 * The caller of this method must make sure that the root node is of the
	 * EPP trIDType
	 *
	 * @param root root node for an <code>EppTransactionId</code> object
	 *             in XML format
	 *
	 * @return an <code>EppTransactionId</code> object, or null if the node
	 *         is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		String clTRID = null;
		String svTRID = null;

		NodeList list = root.getChildNodes();
		for( int i = 0; i < list.getLength(); i++ )
		{
			Node node = list.item(i);
			String name = node.getLocalName();
			if( name == null )
			{
				continue;
			}
			if( name.equals("clTRID") )
			{
				clTRID = EppUtil.getText(node);
			}
			else if( name.equals("svTRID") )
			{
				svTRID = EppUtil.getText(node);
			}
		}

		if( svTRID != null )
		{
			return new EppTransactionId(clTRID, svTRID);
		}

		return null;
	}

	/*
	 * Converts the <code>EppTransactionId</code> object into plain XML text
	 * string by using the default root tag name
	 *
	 * @return a text string representing the <code>EppTransactionId</code> object
	 *         in XML format
	 */
	public String toString()
	{
		return toString("trID");
	}
}
