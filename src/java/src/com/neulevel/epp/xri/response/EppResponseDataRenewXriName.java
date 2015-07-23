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
import com.neulevel.epp.core.*;
import com.neulevel.epp.core.response.*;
import org.w3c.dom.*;
import org.apache.xerces.dom.*;

/**
 * This <code>EppResponseDataRenewXriName</code> class implements EPP
 * Response Data entity for EPP Command Renew of EPP XRI I-Name objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppResponseDataRenewXriName extends EppResponseDataRenew
{
	private String   iname;

	/**
	 * Creates an <code>EppResponseDataRenewXriName</code> object
	 *
	 * @param iname the i-name of the XRI i-name object renewed
	 */
	public EppResponseDataRenewXriName( String iname )
	{
		this.iname  = iname;
		this.exDate = null;
	}

	/**
	 * Creates an <code>EppResponseDataRenewXriName</code> object
	 *
	 * @param iname  the i-name of the XRI i-name object renewed
	 * @param exDate the expiration date of the XRI i-name
	 *               object renewed
	 */
	public EppResponseDataRenewXriName( String iname, Calendar exDate )
	{
		this.iname  = iname;
		this.exDate = exDate;
	}

	/**
	 * Gets the i-name
	*/
	public String getIName()
	{
		return this.iname;
	}

	/**
	 * Sets the i-name
	 */
	public void setIName( String iname )
	{
		this.iname = iname;
	}

	/**
	 * Converts an XML element into an
	 * <code>EppResponseDataRenewXriName</code> object. The caller of this
	 * method must make sure that the root node is the resData element of
	 * EPP responseType for creating an EPP XRI I-Name object
	 *
	 * @param root root node for an <code>EppResponseDataRenewXriName</code>
	 *             object in XML format
	 *
	 * @return an <code>EppResponseDataRenewXriName</code> object,
	 *         or null if the node is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		String i_number = null;
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
			else if( name.equals("iname") )
			{
				i_number = EppUtil.getText(node);
			}
			else if( name.equals("exDate") )
			{
				exDate = EppUtil.getDate(node);
			}
		}

		return new EppResponseDataRenewXriName(i_number, exDate);
	}

	/**
	 * Converts an <code>EppResponseDataRenewXriName</code> object
	 * into an XML element.
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the
	 *        <code>EppResponseDataRenewXriName</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Element elm;
		Element body = doc.createElement(tag);
		ElementNSImpl data = EppUtil.createElementNS(doc, "xriINA", "renData");
		body.appendChild(data);

		if( iname != null )
		{
			elm = doc.createElement("iname");
			elm.appendChild(doc.createTextNode(iname));
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
