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
 * This <code>EppResponseDataCreateXriService</code> class implements EPP
 * Response Data entity for EPP Command Create of EPP XRI I-Service objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppResponseDataCreateXriService extends EppResponseDataCreate
{
	private String   id;
	private String   type;
	private Vector   uri;
	private Calendar exDate;

	/**
	 * Creates an <code>EppResponseDataCreateXriService</code> object,
	 * with the current date as the creation date.
	 *
         * @param id the identifier of the EPP XRI i-service object created
	 */
	public EppResponseDataCreateXriService( String id )
	{
		this(id, null, null, Calendar.getInstance());
	}

	/**
	 * Creates an <code>EppResponseDataCreateXriService</code> object,
	 * given the identifier of the XRI i-service object, and an expiration date,
	 * with the current date as the creation date
	 *
         * @param id the identifier of the EPP XRI i-service object created
         * @param exDate  the expiration date of the XRI i-service object created
	 */
	public EppResponseDataCreateXriService( String id, Calendar exDate )
	{
		this(id, null, exDate, Calendar.getInstance());
	}

	/**
	 * Creates an <code>EppResponseDataCreateXriService</code> object,
	 * given the identifier of the XRI i-service object, a reference id,
	 * and an expiration date,
	 * with the current date as the creation date
	 *
         * @param id the identifier of the EPP XRI i-service object created
         * @param exDate  the expiration date of the XRI i-service object created
         * @param crDate  the creation date of the XRI i-service object created
	 */
	public EppResponseDataCreateXriService( String id, Calendar exDate, Calendar crDate )
	{
		this(id, null, exDate, crDate);
	}

	/**
	 * Creates an <code>EppResponseDataCreateXriService</code> object,
	 * given the identifier of the XRI i-service object, a reference id,
	 * and an expiration date,
	 * with the current date as the creation date
	 *
         * @param id the identifier of the EPP XRI i-service object created
         * @param exDate  the expiration date of the XRI i-service object created
         * @param crDate  the creation date of the XRI i-service object created
	 */
	public EppResponseDataCreateXriService( String id, String type, Calendar exDate, Calendar crDate )
	{
		this.id     = id;
		this.type   = type;
		this.exDate = exDate;
		this.crDate = crDate;
		this.uri    = new Vector();
	}

	/**
	 * Sets the identifier of the i-service object
	 */
	public void setId( String id )
	{
		this.id = id;
	}

	/**
	 * Gets the identifier of the i-service object
	 */
	public String getId()
	{
		return this.id;
	}

	/**
	 * Sets the type of the i-service object
	 */
	public void setType( String type )
	{
		this.type = type;
	}

	/**
	 * Gets the type of the i-service object
	 */
	public String getType()
	{
		return this.type;
	}

	/**
	 * Adds a URI to the list of URIs associated with the i-service object
	 */
	public void addURI( String uri )
	{
		this.uri.addElement(uri);
	}

	/**
	 * Gets the list of URIs associated with the i-service object
	 */
	public Vector getURI()
	{
		return this.uri;
	}

	/**
	 * Gets expiration date of the XRI i-service object created
	 */
	public Calendar getDateExpired()
	{
		return this.exDate;
	}

	/**
	 * Sets expiration date of the XRI i-service object created
	 */
	public void setDateExpired( Calendar exDate )
	{
		this.exDate = exDate;
	}

	/**
	 * Converts an XML element into an
	 * <code>EppResponseDataCreateXriService</code> object. The caller of this
	 * method must make sure that the root node is the resData element of
	 * EPP responseType for creating an EPP XriService object.
	 *
	 * @param root root node for an
	 *             <code>EppResponseDataCreateXriService</code> object
	 *             in XML format
	 *
	 * @return an <code>EppResponseDataCreateXriService</code> object, or null
	 *         if the node is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		String   i_service   = null;
		String   i_type      = null;
		Calendar create_date = null;
		Calendar expire_date = null;
		Vector   uri_list    = new Vector();
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
				if( (id != null) && (id.length() > 0) )
				{
					i_service = id;
				}
			}
			else if( name.equals("type") )
			{
				String t = EppUtil.getText(node);
				if( (t != null) && (t.length() > 0) )
				{
					i_type = t;
				}
			}
			else if( name.equals("uri") )
			{
				String t = EppUtil.getText(node);
				if( (t != null) && (t.length() > 0) )
				{
					uri_list.addElement(t);
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

		EppResponseDataCreateXriService data;
		data = new EppResponseDataCreateXriService(i_service, i_type, expire_date, create_date);
		data.uri = uri_list;

		return data;
	}

	/**
	 * Converts an <code>EppResponseDataCreateXriService</code> object into
	 * an XML element.
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the
	 *            <code>EppResponseDataCreateXriService</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Element elm;
		Element body = doc.createElement(tag);
		Element data = EppUtil.createElementNS(doc, "xriISV", "creData");
		body.appendChild(data);
		if( id != null )
		{
			elm = doc.createElement("id");
			elm.appendChild(doc.createTextNode(id));
			data.appendChild(elm);
		}
		if( type != null )
		{
			elm = doc.createElement("type");
			elm.appendChild(doc.createTextNode(type));
			data.appendChild(elm);
		}
		if( uri != null )
		{
			for( int i = 0; i < uri.size(); i++ )
			{
				String s = (String) uri.elementAt(i);
				elm = doc.createElement("uri");
				elm.appendChild(doc.createTextNode(s));
				data.appendChild(elm);
			}
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
