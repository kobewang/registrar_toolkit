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
import com.neulevel.epp.core.*;
import org.w3c.dom.*;
import org.apache.xerces.dom.*;

/**
 * This <code>EppResponseDataRenewSvcsub</code> class implements EPP
 * Response Data entity for EPP Command Renew of EPP Svcsub objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppResponseDataRenewSvcsub extends EppResponseDataRenew
{
	private String	id;
	private String	service;

	/**
	 * Creates an <code>EppResponseDataRenewSvcsub</code> object
	 *
	 * @param id the id of the <code>EppSvcsub</code> object renewed
	 */
	public EppResponseDataRenewSvcsub( String id )
	{
		this.id = id;
		this.exDate = null;
		this.service = null;
	}

	/**
	 * Creates an <code>EppResponseDataRenewSvcsub</code> object
	 *
	 * @param id the id of the <code>EppSvcsub</code> object renewed
	 * @param exDate the expiration date of the <code>EppSvcsub</code>
	 *               object renewed
	 */
	public EppResponseDataRenewSvcsub( String id, Calendar exDate )
	{
		this.id = id;
		this.exDate = exDate;
		this.service = null;
	}

	/**
	 * Gets the id of the svcsub renewed
	*/
	public String getId()
	{
		return this.id;
	}

	/**
	 * Sets the id of the svcsub renewed
	 */
	public void setId( String id )
	{
		this.id = id;
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
	 * Converts an XML element into an
	 * <code>EppResponseDataRenewSvcsub</code> object. The caller of this
	 * method must make sure that the root node is the resData element of
	 * EPP responseType for renewing an EPP Svcsub object
	 *
	 * @param root root node for an <code>EppResponseDataRenewSvcsub</code>
	 *             object in XML format
	 *
	 * @return an <code>EppResponseDataRenewSvcsub</code> object,
	 *         or null if the node is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		String id = null;
		String service = null;
		Calendar exDate = null;
		NodeList list = root.getChildNodes();
		for( int i = 0; i < list.getLength(); i++ )
		{
			Node node = list.item(i);
			String name = node.getLocalName();
			if( name == null )
			{
				continue;
			}
			else if( name.equals("id") )
			{
				id = EppUtil.getText(node);
			}
			else if( name.equals("service") )
			{
				service = EppUtil.getText(node);
			}
			else if( name.equals("exDate") )
			{
				exDate = EppUtil.getDate(node);
			}
		}

		EppResponseDataRenewSvcsub cmd;
		cmd = new EppResponseDataRenewSvcsub(id, exDate);
		if( cmd != null )
		{
			cmd.setService(service);
		}
		return cmd;
	}

	/**
	 * Converts an <code>EppResponseDataRenewSvcsub</code> object
	 * into an XML element.
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the
	 *        <code>EppResponseDataRenewSvcsub</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Element elm;
		Element body = doc.createElement(tag);
		ElementNSImpl data = EppUtil.createElementNS(doc, "svcsub", "renData");
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
		if( exDate != null )
		{
			elm = doc.createElement("exDate");
			elm.appendChild(EppUtil.createTextNode(doc, exDate));
			data.appendChild(elm);
		}

		return body;
	}
}
