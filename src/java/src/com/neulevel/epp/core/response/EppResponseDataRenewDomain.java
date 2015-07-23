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
 * This <code>EppResponseDataRenewDomain</code> class implements EPP
 * Response Data entity for EPP Command Renew of EPP Domain objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppResponseDataRenewDomain extends EppResponseDataRenew
{
	private String   name;

	/**
	 * Creates an <code>EppResponseDataRenewDomain</code> object
	 *
	 * @param name the name of the <code>EppDomain</code> object renewed
	 */
	public EppResponseDataRenewDomain( String name )
	{
		this.name = name;
		this.exDate = null;
	}

	/**
	 * Creates an <code>EppResponseDataRenewDomain</code> object
	 *
	 * @param name the name of the <code>EppDomain</code> object renewed
	 * @param exDate the expiration date of the <code>EppDomain</code>
	 *               object renewed
	 */
	public EppResponseDataRenewDomain( String name, Calendar exDate )
	{
		this.name = name;
		this.exDate = exDate;
	}

	/**
	 * Gets the name of the domain renewed
	*/
	public String getName()
	{
		return this.name;
	}

	/**
	 * Sets the name of the domain renewed
	 */
	public void setName( String name )
	{
		this.name = name;
	}

	/**
	 * Converts an XML element into an
	 * <code>EppResponseDataRenewDomain</code> object. The caller of this
	 * method must make sure that the root node is the resData element of
	 * EPP responseType for renewing an EPP Domain object
	 *
	 * @param root root node for an <code>EppResponseDataRenewDomain</code>
	 *             object in XML format
	 *
	 * @return an <code>EppResponseDataRenewDomain</code> object,
	 *         or null if the node is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		String domain = null;
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
			else if( name.equals("name") )
			{
				domain = EppUtil.getText(node);
			}
			else if( name.equals("exDate") )
			{
				exDate = EppUtil.getDate(node);
			}
		}

		return new EppResponseDataRenewDomain(domain, exDate);
	}

	/**
	 * Converts an <code>EppResponseDataRenewDomain</code> object
	 * into an XML element.
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the
	 *        <code>EppResponseDataRenewDomain</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Element elm;
		Element body = doc.createElement(tag);
		ElementNSImpl data = EppUtil.createElementNS(doc, "domain", "renData");
		body.appendChild(data);

		if( name != null )
		{
			elm = doc.createElement("name");
			elm.appendChild(doc.createTextNode(name));
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
