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
 * This <code>EppResponseDataTransferSvcsub</code> class implements EPP
 * Response Data entity for EPP Command Transfer of EPP Svcsub objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppResponseDataTransferSvcsub extends EppResponseDataTransfer
{
	private String id;
	private String service;

	/**
	 * Creates an <code>EppResponseDataTransferSvcsub</code> object
	 */
	public EppResponseDataTransferSvcsub( String id )
	{
		this.id = id;
		this.service = null;
	}

	/**
	 * Gets the id of the EPP Svcsub object
	 */
	public String getId()
	{
		return this.id;
	}

	/**
	 * Sets the id of the EPP Svcsub Object
	 */
	public void setId( String id )
	{
		this.id = id;
	}

	/**
	 * Gets the new expiration date of the service subscription object
	 * after the transfer, or null if the transfer request does not
	 * change the expiration date of the service subscription object.
	 */
	public Calendar getDateExpired()
	{
		return this.exDate;
	}

	/**
	 * Sets the new expiration date of the service subscription object
	 * after the transfer. The value of the new expiration date is optional.
	 */
	public void setDateExpired( Calendar date )
	{
		this.exDate = date;
	}

	/**
	 * Gets the service name
	 */
	public String getService()
	{
		return this.service;
	}

	/**
	 * Sets the service name
	 */
	public void setService( String service )
	{
		this.service = service;
	}

	/**
	 * Converts the <code>EppResponseDataTransferSvcsub</code> object into
	 * an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the
	 *            <code>EppResponseDataTransferSvcsub</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Element elm;
		Element body = doc.createElement(tag);
		Element data = EppUtil.createElementNS(doc, "svcsub", "trnData");
		body.appendChild(data);

		if( id != null )
		{
			elm = doc.createElement("id");
			elm.appendChild(doc.createTextNode(id));
			data.appendChild(elm);
		}
		if( service != null )
		{
			elm = doc.createElement("service");
			elm.appendChild(doc.createTextNode(service));
			data.appendChild(elm);
		}

		toXMLCommon(doc, data);

		return body;
	}

	/**
	 * Converts an XML element into an
	 * <code>EppResponseDataTransferSvcsub</code> object. The caller of
	 * this method must make sure that the root node is of an EPP Response
	 * Transfer entity for EPP Svcsub object.
	 *
	 * @param root root node for an
	 *             <code>EppResponseDataTransferSvcsub</code>
	 *             object in XML format
	 *
	 * @return an <code>EppResponseDataTransferSvcsub</code> object, or null
	 *         if the node is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		EppResponseDataTransferSvcsub res = null;
		NodeList list = root.getChildNodes();
		for( int i = 0; i < list.getLength(); i++ )
		{
			Node node = list.item(i);
			String name = node.getLocalName();
			if( name == null )
			{
				continue;
			}
			if( name.equals("id") )
			{
				String id = EppUtil.getText(node);
				res = new EppResponseDataTransferSvcsub(id);
			}
			else if( name.equals("service") )
			{
				String service = EppUtil.getText(node);
				if( res != null )
				{
					res.setService(service);
				}
			}
			else if( res != null )
			{
				res.fromXMLCommon(node, name);
			}
		}

		return res;
	}
}
