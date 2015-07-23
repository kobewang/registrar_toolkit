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
package com.neulevel.epp.core.response;

import java.util.*;
import org.w3c.dom.*;
import com.neulevel.epp.core.*;

/**
 * This <code>EppResponseDataTransferDomain</code> class implements EPP
 * Response Data entity for EPP Command Transfer of EPP Domain objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppResponseDataTransferDomain extends EppResponseDataTransfer
{
	private String name;

	/**
	 * Creates an <code>EppResponseDataTransferDomain</code> object
	 */
	public EppResponseDataTransferDomain( String name )
	{
		this.name = name;
	}

	/**
	 * Gets the name of the EPP Domain object
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * Sets the name of the EPP Domain Object
	 */
	public void setName( String name )
	{
		this.name = name;
	}

	/**
	 * Gets the new expiration date of the domain after the transfer, or
	 * null if the transfer request does not change the expiration date
	 * of domain.
	 */
	public Calendar getDateExpired()
	{
		return this.exDate;
	}

	/**
	 * Sets the new expiration date of the domain after the transfer.
	 * The value of the new expiration date is optional.
	 */
	public void setDateExpired( Calendar date )
	{
		this.exDate = date;
	}

	/**
	 * Converts the <code>EppResponseDataTransferDomain</code> object into
	 * an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the
	 *            <code>EppResponseDataTransferDomain</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Element elm;
		Element body = doc.createElement(tag);
		Element data = EppUtil.createElementNS(doc, "domain", "trnData");
		body.appendChild(data);

		if( name != null )
		{
			elm = doc.createElement("name");
			elm.appendChild(doc.createTextNode(name));
			data.appendChild(elm);
		}

		toXMLCommon(doc, data);

		return body;
	}

	/**
	 * Converts an XML element into an
	 * <code>EppResponseDataTransferDomain</code> object. The caller of
	 * this method must make sure that the root node is of an EPP Response
	 * Transfer entity for EPP Domain object.
	 *
	 * @param root root node for an
	 *             <code>EppResponseDataTransferDomain</code>
	 *             object in XML format
	 *
	 * @return an <code>EppResponseDataTransferDomain</code> object, or null
	 *         if the node is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		EppResponseDataTransferDomain res = null;
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
				String domain = EppUtil.getText(node);
				res = new EppResponseDataTransferDomain(domain);
			}
			else if( res != null )
			{
				res.fromXMLCommon(node, name);
			}
		}

		return res;
	}
}
