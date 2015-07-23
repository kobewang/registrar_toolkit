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
 * This <code>EppResponseDataCreateDomain</code> class implements EPP
 * Response Data entity for EPP Command Create of EPP Domain objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppResponseDataCreateDomain extends EppResponseDataCreate
{
	private String   name;
	private Calendar exDate;

	/**
	 * Creates an <code>EppResponseDataCreateDomain</code> object
	 *
	 * @param name the name of the <code>EppDomain</code> object created
	 */
	public EppResponseDataCreateDomain( String name )
	{
		this.name = name;
		this.exDate = null;
		this.crDate = Calendar.getInstance();
	}

	/**
	 * Creates an <code>EppResponseDataCreateDomain</code> object
	 *
	 * @param name the name of the <code>EppDomain</code> object created
	 * @param exDate the expiration date of the <code>EppDomain</code>
	 *               object created
	 */
	public EppResponseDataCreateDomain( String name, Calendar exDate )
	{
		this.name = name;
		this.exDate = exDate;
		this.crDate = Calendar.getInstance();
	}

	/**
	 * Creates an <code>EppResponseDataCreateDomain</code> object
	 *
	 * @param name the name of the <code>EppDomain</code> object created
	 * @param exDate the expiration date of the <code>EppDomain</code>
	 *               object created
	 * @param crDate the date of the <code>EppDomain</code> object was created
	 */
	public EppResponseDataCreateDomain( String name, Calendar exDate, Calendar crDate )
	{
		this.name = name;
		this.exDate = exDate;
		this.crDate = crDate;
	}

	/**
	 * Creates an <code>EppResponseDataCreateDomain</code> object
	 *
	 * @param name the name of the <code>EppDomain</code> object created
	 * @param roid the ROID of the <code>EppDomain</code> object created
	 *
	 * @deprecated for EPP-04 (06/29/2001)
	 */
	public EppResponseDataCreateDomain( String name, String roid )
	{
		this.name = name;
		this.roid = roid;
		this.exDate = null;
	}

	/**
	 * Creates an <code>EppResponseDataCreateDomain</code> object
	 *
	 * @param name   the name of the <code>EppDomain</code> object created
	 * @param roid   the ROID of the <code>EppDomain</code> object created
	 * @param exDate the expiration date of the <code>EppDomain</code>
	 *               object created
	 *
	 * @deprecated for EPP-04 (06/29/2001)
	 */
	public EppResponseDataCreateDomain( String name, String roid, Calendar exDate )
	{
		this.name = name;
		this.roid = roid;
		this.exDate = exDate;
	}

	/**
	 * Gets the name of the domain created
	*/
	public String getName()
	{
		return this.name;
	}

	/**
	 * Sets the name of the domain created
	 */
	public void setName( String name )
	{
		this.name = name;
	}

	/**
	 * Gets expiration date of the domain created
	 */
	public Calendar getDateExpired()
	{
		return this.exDate;
	}

	/**
	 * Sets expiration date of the domain created
	 */
	public void setDateExpired( Calendar exDate )
	{
		this.exDate = exDate;
	}

	/**
	 * Converts an XML element into an
	 * <code>EppResponseDataCreateDomain</code> object. The caller of this
	 * method must make sure that the root node is the resData element of
	 * EPP responseType for creating an EPP Domain object
	 *
	 * @param root root node for an <code>EppResponseDataCreateDomain</code>
	 *             object in XML format
	 *
	 * @return an <code>EppResponseDataCreateDomain</code> object,
	 *         or null if the node is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		String roid = null;
		String domain = null;
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
			else if( name.equals("name") )
			{
				domain = EppUtil.getText(node);
			}
			else if( name.equals("exDate") )
			{
				exDate = EppUtil.getDate(node);
			}
			else if( name.equals("crDate") )
			{
				crDate = EppUtil.getDate(node);
			}
		}

		return new EppResponseDataCreateDomain(domain, exDate, crDate);
	}

	/**
	 * Converts an <code>EppResponseDataCreateDomain</code> object
	 * into an XML element.
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the
	 *        <code>EppResponseDataCreateDomain</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Element elm;
		Element body = doc.createElement(tag);
		ElementNSImpl data = EppUtil.createElementNS(doc, "domain", "creData");
		body.appendChild(data);

		if( name != null )
		{
			elm = doc.createElement("name");
			elm.appendChild(doc.createTextNode(name));
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
