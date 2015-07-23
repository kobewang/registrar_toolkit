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
 * This <code>EppResponseDataCreateHost</code> class implements EPP
 * Response Data entity for EPP Command Create of EPP Contact objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppResponseDataCreateHost extends EppResponseDataCreate
{
	private String   name;

	/**
	 * Creates an <code>EppResponseDataCreateHost</code> object
	 *
	 * @param name the name of the <code>EppHost</code> object created
	 */
	public EppResponseDataCreateHost( String name )
	{
		this.name = name;
		this.crDate = Calendar.getInstance();
	}

	/**
	 * Creates an <code>EppResponseDataCreateHost</code> object
	 *
	 * @param name the name of the <code>EppHost</code> object created
	 * @param crDate the date of the <code>EppHost</code> object was created
	 */
	public EppResponseDataCreateHost( String name, Calendar crDate )
	{
		this.name = name;
		this.crDate = crDate;
	}

	/**
	 * Creates an <code>EppResponseDataCreateHost</code> object
	 *
	 * @param name the name of the <code>EppHost</code> object created
	 * @param roid the ROID of the <code>EppHost</code> object created
	 *
	 * @deprecated for EPP-04 (06/29/2001)
	 */
	public EppResponseDataCreateHost( String name, String roid )
	{
		this.name = name;
		this.roid = roid;
	}

	/**
	 * Sets the name of the host created
	*/
	public String getName()
	{
		return this.name;
	}

	/**
	 * Sets the name of the host created
	 */
	public void setName( String name )
	{
		this.name = name;
	}

	/**
	 * Converts an XML element into an
	 * <code>EppResponseDataCreateHost</code> object. The caller of this
	 * method must make sure that the root node is the resData element of
	 * EPP responseType for creating an EPP Host object
	 *
	 * @param root root node for an <code>EppResponseDataCreateHost</code>
	 *             object in XML format
	 *
	 * @return an <code>EppResponseDataCreateHost</code> object,
	 *         or null if the node is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		String roid = null;
		String host = null;
		Calendar cal = null;
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
				host = EppUtil.getText(node);
			}
			else if( name.equals("crDate") )
			{
				cal = EppUtil.getDate(node);
			}
		}

		return new EppResponseDataCreateHost(host, cal);
	}

	/**
	 * Converts an <code>EppResponseDataCreateHost</code> object
	 * into an XML element.
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the
	 *        <code>EppResponseDataCreateHost</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Element elm;
		Element body = doc.createElement(tag);
		ElementNSImpl data = EppUtil.createElementNS(doc, "host", "creData");
		body.appendChild(data);

		if( name != null )
		{
			elm = doc.createElement("name");
			elm.appendChild(doc.createTextNode(name));
			data.appendChild(elm);
		}
		if( crDate != null )
		{
			elm = doc.createElement("crDate");
			elm.appendChild(EppUtil.createTextNode(doc, crDate));
			data.appendChild(elm);
		}
//		if( roid != null )
//		{
//			elm = doc.createElement("roid");
//			elm.appendChild(doc.createTextNode(roid));
//			data.appendChild(elm);
//		}

		return body;
	}
}
