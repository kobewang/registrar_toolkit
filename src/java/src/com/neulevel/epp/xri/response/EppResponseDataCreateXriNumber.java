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
 * This <code>EppResponseDataCreateXriNumber</code> class implements EPP
 * Response Data entity for EPP Command Create of EPP XRI I-Number objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppResponseDataCreateXriNumber extends EppResponseDataCreate
{
	private String   inumber;
	private String   refId;
	private Calendar exDate;

	/**
	 * Creates an <code>EppResponseDataCreateXriNumber</code> object,
	 * with the current date as the creation date.
	 *
         * @param inumber the i-number of the EPP XRI i-number object created
	 */
	public EppResponseDataCreateXriNumber( String inumber )
	{
		this(inumber, null, null, Calendar.getInstance());
	}

	/**
	 * Creates an <code>EppResponseDataCreateXriNumber</code> object,
	 * given the i-number of the XRI i-number object, and an expiration date,
	 * with the current date as the creation date
	 *
         * @param inumber the i-number of the EPP XRI i-number object created
         * @param exDate  the expiration date of the XRI i-number object created
	 */
	public EppResponseDataCreateXriNumber( String inumber, Calendar exDate )
	{
		this(inumber, null, exDate, Calendar.getInstance());
	}

	/**
	 * Creates an <code>EppResponseDataCreateXriNumber</code> object,
	 * given the i-number of the XRI i-number object, a reference id,
	 * and an expiration date,
	 * with the current date as the creation date
	 *
         * @param inumber the i-number of the EPP XRI i-number object created
         * @param refId   the reference id of the XRI i-number object created
         * @param exDate  the expiration date of the XRI i-number object created
	 */
	public EppResponseDataCreateXriNumber( String inumber, String refId, Calendar exDate )
	{
		this(inumber, refId, exDate, Calendar.getInstance());
	}

	/**
	 * Creates an <code>EppResponseDataCreateXriNumber</code> object,
	 * given the i-number of the XRI i-number object, a reference id,
	 * and an expiration date,
	 * with the current date as the creation date
	 *
         * @param inumber the i-number of the EPP XRI i-number object created
         * @param refId   the reference id of the XRI i-number object created
         * @param exDate  the expiration date of the XRI i-number object created
         * @param crDate  the creation date of the XRI i-number object created
	 */
	public EppResponseDataCreateXriNumber( String inumber, String refId, Calendar exDate, Calendar crDate )
	{
		this.inumber = inumber;
		this.refId   = refId;
		this.exDate  = exDate;
		this.crDate  = crDate;
	}

	/**
	 * Sets the i-number
	 */
	public void setINumber( String inumber )
	{
		this.inumber = inumber;
	}

	/**
	 * Gets the i-number
	 */
	public String getINumber()
	{
		return this.inumber;
	}

	/**
	 * Gets expiration date of the XRI i-number object created
	 */
	public Calendar getDateExpired()
	{
		return this.exDate;
	}

	/**
	 * Sets expiration date of the XRI i-number object created
	 */
	public void setDateExpired( Calendar exDate )
	{
		this.exDate = exDate;
	}

	/**
	 * Gets the reference identifier used in generating the i-number, if any
	 */
	public String getReferenceId()
	{
		return this.refId;
	}

	/**
	 * Gets the reference identifier used in generating the i-number, if any
	 */
	public void setReferenceId( String refId )
	{
		this.refId = refId;
	}

	/**
	 * Converts an XML element into an
	 * <code>EppResponseDataCreateXriNumber</code> object. The caller of this
	 * method must make sure that the root node is the resData element of
	 * EPP responseType for creating an EPP XriNumber object.
	 *
	 * @param root root node for an
	 *             <code>EppResponseDataCreateXriNumber</code> object
	 *             in XML format
	 *
	 * @return an <code>EppResponseDataCreateXriNumber</code> object, or null
	 *         if the node is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		String   i_number    = null;
		String   ref_id      = null;
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
			if( name.equals("inumber") )
			{
				String id = EppUtil.getText(node);
				if( (id != null) && (id.length() > 0) )
				{
					i_number = id;
				}
			}
			else if( name.equals("refId") )
			{
				String id = EppUtil.getText(node);
				if( (id != null) && (id.length() > 0) )
				{
					ref_id = id;
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

		return new EppResponseDataCreateXriNumber(i_number, ref_id, expire_date, create_date);
	}

	/**
	 * Converts an <code>EppResponseDataCreateXriNumber</code> object into
	 * an XML element.
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the
	 *            <code>EppResponseDataCreateXriNumber</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Element elm;
		Element body = doc.createElement(tag);
		Element data = EppUtil.createElementNS(doc, "xriINU", "creData");
		body.appendChild(data);
		if( inumber != null )
		{
			elm = doc.createElement("inumber");
			elm.appendChild(doc.createTextNode(inumber));
			data.appendChild(elm);
		}
		if( refId != null )
		{
			elm = doc.createElement("refId");
			elm.appendChild(doc.createTextNode(refId));
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
