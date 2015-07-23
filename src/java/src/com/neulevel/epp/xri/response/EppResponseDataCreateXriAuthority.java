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
 * This <code>EppResponseDataCreateXriAuthority</code> class implements EPP
 * Response Data entity for EPP Command Create of EPP XRI Authority objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppResponseDataCreateXriAuthority extends EppResponseDataCreate
{
	private String authId;

	/**
	 * Creates an <code>EppResponseDataCreateXriAuthority</code> object,
	 * given the XRI authority identifier, with the current date as the creation date
	 */
	public EppResponseDataCreateXriAuthority( String authId )
	{
		this.authId = authId;
		this.crDate = Calendar.getInstance();
	}

	/**
	 * Creates an <code>EppResponseDataCreateXriAuthority</code> object,
	 * given the XRI authority identifier and the creation date
	 */
	public EppResponseDataCreateXriAuthority( String authId, Calendar crDate )
	{
		this.authId = authId;
		this.crDate = crDate;
	}

	/**
	 * Sets the XRI authority identifier
	 */
	public void setAuthorityId( String authId )
	{
		this.authId = authId;
	}

	/**
	 * Gets the XRI authority identifier
	 */
	public String getAuthorityId()
	{
		return this.authId;
	}

	/**
	 * Converts an XML element into an
	 * <code>EppResponseDataCreateXriAuthority</code> object. The caller of this
	 * method must make sure that the root node is the resData element of
	 * EPP responseType for creating an EPP XriAuthority object.
	 *
	 * @param root root node for an
	 *             <code>EppResponseDataCreateXriAuthority</code> object
	 *             in XML format
	 *
	 * @return an <code>EppResponseDataCreateXriAuthority</code> object, or null
	 *         if the node is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		String   auth_id = null;
		Calendar create_date = null;
		NodeList list = root.getChildNodes();
		for( int i = 0; i < list.getLength(); i++ )
		{
			Node node = list.item(i);
			String name = node.getLocalName();
			if( name == null )
			{
				continue;
			}
			if( name.equals("authId") )
			{
				String id = EppUtil.getText(node);
				if( (id != null) && (id.length() > 0) )
				{
					auth_id = id;
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
		}

		return new EppResponseDataCreateXriAuthority(auth_id, create_date);
	}

	/**
	 * Converts an <code>EppResponseDataCreateXriAuthority</code> object into
	 * an XML element.
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the
	 *            <code>EppResponseDataCreateXriAuthority</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Element elm;
		Element body = doc.createElement(tag);
		Element data = EppUtil.createElementNS(doc, "xriAU", "creData");
		body.appendChild(data);
		if( authId != null )
		{
			elm = doc.createElement("authId");
			elm.appendChild(doc.createTextNode(authId));
			data.appendChild(elm);
		}
		if( crDate != null )
		{
			elm = doc.createElement("crDate");
			elm.appendChild(EppUtil.createTextNode(doc, crDate));
			data.appendChild(elm);
		}

		return body;
	}
}
