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
package com.neulevel.epp.xri.response;

import java.util.*;
import org.w3c.dom.*;
import com.neulevel.epp.core.*;
import com.neulevel.epp.core.response.*;

/**
 * This <code>EppResponseDataCreateXriName</code> class implements EPP
 * Response Data entity for EPP Command Create of EPP XRI I-Name objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppResponseDataCreateXriName extends EppResponseDataCreate
{
	private String   iname;
	private Calendar exDate;

	/**
	 * Creates an <code>EppResponseDataCreateXriName</code> object,
	 * with the current date as the creation date.
	 *
         * @param iname the i-name of the EPP XRI i-name object created
	 */
	public EppResponseDataCreateXriName( String iname )
	{
		this(iname, null, Calendar.getInstance());
	}

	/**
	 * Creates an <code>EppResponseDataCreateXriName</code> object,
	 * given the i-name of the XRI i-name object, and an expiration date,
	 * with the current date as the creation date
	 *
         * @param iname the i-name of the EPP XRI i-name object created
         * @param exDate  the expiration date of the XRI i-name object created
	 */
	public EppResponseDataCreateXriName( String iname, Calendar exDate )
	{
		this(iname, exDate, Calendar.getInstance());
	}

	/**
	 * Creates an <code>EppResponseDataCreateXriName</code> object,
	 * given the i-name of the XRI i-name object, a reference id,
	 * and an expiration date,
	 * with the current date as the creation date
	 *
         * @param iname the i-name of the EPP XRI i-name object created
         * @param exDate  the expiration date of the XRI i-name object created
         * @param crDate  the creation date of the XRI i-name object created
	 */
	public EppResponseDataCreateXriName( String iname, Calendar exDate, Calendar crDate )
	{
		this.iname  = iname;
		this.exDate = exDate;
		this.crDate = crDate;
	}

	/**
	 * Sets the i-name
	 */
	public void setIName( String iname )
	{
		this.iname = iname;
	}

	/**
	 * Gets the i-name
	 */
	public String getIName()
	{
		return this.iname;
	}

	/**
	 * Gets expiration date of the XRI i-name object created
	 */
	public Calendar getDateExpired()
	{
		return this.exDate;
	}

	/**
	 * Sets expiration date of the XRI i-name object created
	 */
	public void setDateExpired( Calendar exDate )
	{
		this.exDate = exDate;
	}

	/**
	 * Converts an XML element into an
	 * <code>EppResponseDataCreateXriName</code> object. The caller of this
	 * method must make sure that the root node is the resData element of
	 * EPP responseType for creating an EPP XriName object.
	 *
	 * @param root root node for an
	 *             <code>EppResponseDataCreateXriName</code> object
	 *             in XML format
	 *
	 * @return an <code>EppResponseDataCreateXriName</code> object, or null
	 *         if the node is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		String   i_name    = null;
		Calendar create_date = null;
		Calendar expire_date = null;
		NodeList list = root.getChildNodes();
		for( int i = 0; i < list.getLength(); i++ )
		{
			Node node = list.item(i);
			String name = node.getLocalName();
			if( name == null )
			{
				continue;
			}
			if( name.equals("iname") )
			{
				String id = EppUtil.getText(node);
				if( (id != null) && (id.length() > 0) )
				{
					i_name = id;
				}
			}
			else if( name.equals("crDate") )
			{
				Calendar d = EppUtil.getDate(node);
				if( d != null )
				{
					create_date = d;
				}
			}
			else if( name.equals("exDate") )
			{
				Calendar d = EppUtil.getDate(node);
				if( d != null )
				{
					expire_date = d;
				}
			}
		}

		return new EppResponseDataCreateXriName(i_name, expire_date, create_date);
	}

	/**
	 * Converts an <code>EppResponseDataCreateXriName</code> object into
	 * an XML element.
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the
	 *            <code>EppResponseDataCreateXriName</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Element elm;
		Element body = doc.createElement(tag);
		Element data = EppUtil.createElementNS(doc, "xriINA", "creData");
		body.appendChild(data);
		if( iname != null )
		{
			elm = doc.createElement("iname");
			elm.appendChild(doc.createTextNode(iname));
			data.appendChild(elm);
		}
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
