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
 * This <code>EppResponseDataCreateSvcsub</code> class implements EPP
 * Response Data entity for EPP Command Create of EPP Svcsub objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppResponseDataCreateSvcsub extends EppResponseDataCreate
{
	private String   id;
	private Calendar exDate;
	private String   service;

	/**
	 * Creates an <code>EppResponseDataCreateSvcsub</code> object
	 *
	 * @param id the id of the <code>EppSvcsub</code> object created
	 */
	public EppResponseDataCreateSvcsub( String id )
	{
		this.id = id;
		this.exDate = null;
		this.crDate = Calendar.getInstance();
		this.service = null;
	}

	/**
	 * Creates an <code>EppResponseDataCreateSvcsub</code> object
	 *
	 * @param id the id of the <code>EppSvcsub</code> object created
	 * @param exDate the expiration date of the <code>EppSvcsub</code>
	 *               object created
	 */
	public EppResponseDataCreateSvcsub( String id, Calendar exDate )
	{
		this.id = id;
		this.exDate = exDate;
		this.crDate = Calendar.getInstance();
		this.service = null;
	}

	/**
	 * Creates an <code>EppResponseDataCreateSvcsub</code> object
	 *
	 * @param id the id of the <code>EppSvcsub</code> object created
	 * @param exDate the expiration date of the <code>EppSvcsub</code>
	 *               object created
	 * @param crDate the creation date of the <code>EppSvcsub</code>
	 *               object created
	 */
	public EppResponseDataCreateSvcsub( String id, Calendar exDate, Calendar crDate )
	{
		this.id = id;
		this.exDate = exDate;
		this.crDate = crDate;
		this.service = null;
	}

	/**
	 * Creates an <code>EppResponseDataCreateSvcsub</code> object
	 *
	 * @param id the id of the <code>EppSvcsub</code> object created
	 * @param roid the ROID of the <code>EppSvcsub</code> object created
	 *
	 * @deprecated for EPP-04 (06/29/2001)
	 */
	public EppResponseDataCreateSvcsub( String id, String roid )
	{
		this.id = id;
		this.roid = roid;
		this.exDate = null;
		this.service = null;
	}

	/**
	 * Creates an <code>EppResponseDataCreateSvcsub</code> object
	 *
	 * @param id  the id of the <code>EppSvcsub</code> object created
	 * @param roid the ROID of the <code>EppSvcsub</code> object created
	 * @param exDate the expiration date of the <code>EppSvcsub</code>
	 *               object created
	 *
	 * @deprecated for EPP-04 (06/29/2001)
	 */
	public EppResponseDataCreateSvcsub( String id, String roid, Calendar exDate )
	{
		this.id = id;
		this.roid = roid;
		this.exDate = exDate;
		this.service = null;
	}

	/**
	 * Gets the id of the service subscription object created
	*/
	public String getId()
	{
		return this.id;
	}

	/**
	 * Sets the id of the service subscription object created
	 */
	public void setId( String id )
	{
		this.id = id;
	}

	/**
	 * Gets expiration date of the service subscription object created
	 */
	public Calendar getDateExpired()
	{
		return this.exDate;
	}

	/**
	 * Sets expiration date of the service subscription object created
	 */
	public void setDateExpired( Calendar exDate )
	{
		this.exDate = exDate;
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
	 * <code>EppResponseDataCreateSvcsub</code> object. The caller of this
	 * method must make sure that the root node is the resData element of
	 * EPP responseType for creating an EPP Svcsub object
	 *
	 * @param root root node for an <code>EppResponseDataCreateSvcsub</code>
	 *             object in XML format
	 *
	 * @return an <code>EppResponseDataCreateSvcsub</code> object,
	 *         or null if the node is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		String roid = null;
		String id = null;
		String service = null;
		Calendar exDate = null;
		Calendar crDate = null;
		NodeList list = root.getChildNodes();
		for( int i = 0; i < list.getLength(); i++ )
		{
			Node node = list.item(i);
			String name = node.getLocalName();
			if( name == null )
			{
				continue;
			}
			if( name.equals("roid") )
			{
				roid = EppUtil.getText(node);
			}
			else if( name.equals("id") )
			{
				id = EppUtil.getText(node);
			}
			else if( name.equals("service") )
			{
				service = EppUtil.getText(node);
			}
			else if( name.equals("crDate") )
			{
				crDate = EppUtil.getDate(node);
			}
			else if( name.equals("exDate") )
			{
				exDate = EppUtil.getDate(node);
			}
		}

		EppResponseDataCreateSvcsub cmd = null;
		if( crDate != null )
		{
			cmd = new EppResponseDataCreateSvcsub(id, exDate, crDate);
		}
		else
		{
			cmd = new EppResponseDataCreateSvcsub(id, exDate);
		}
		if( cmd != null )
		{
			cmd.setService(service);
		}
		return cmd;
	}

	/**
	 * Converts an <code>EppResponseDataCreateSvcsub</code> object
	 * into an XML element.
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the
	 *        <code>EppResponseDataCreateSvcsub</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Element elm;
		Element body = doc.createElement(tag);
		ElementNSImpl data = EppUtil.createElementNS(doc, "svcsub", "creData");
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
//		if( roid != null )
//		{
//			elm = doc.createElement("roid");
//			elm.appendChild(doc.createTextNode(roid));
//			data.appendChild(elm);
//		}
		if( crDate != null )
		{
			elm = doc.createElement("crDate");
			elm.appendChild(EppUtil.createTextNode(doc, crDate));
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
