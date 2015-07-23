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
package com.neulevel.epp.xri.command;

import java.util.*;
import org.w3c.dom.*;
import com.neulevel.epp.core.*;
import com.neulevel.epp.core.command.*;

/**
 * This <code>EppCommandRenewXriName</code> class implements EPP Command Renew
 * entity for EPP XRI I-Name objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppCommandRenewXriName extends EppCommandRenew
{
	private String iname;

	/**
	 * Creates an <code>EppCommandRenewXriName</code> object with a
	 * default expiration period, specified by the registry
	 */
	public EppCommandRenewXriName( String iname, Calendar curExpDate )
	{
		this(iname, curExpDate, null, null);
	}

	/**
	 * Creates an <code>EppCommandRenewXriName</code> object with a
	 * client transaction id associated with the operation. The current
	 * date of expiration would be the current date.
	 */
	public EppCommandRenewXriName( String iname, String xid )
	{
		this(iname, Calendar.getInstance(), null, xid);
	}

	/**
	 * Creates an <code>EppCommandRenewXriName</code> object with a
	 * default expiration period, specified by the registry, and a
	 * client transaction id associated with the operation
	 */
	public EppCommandRenewXriName( String iname, Calendar curExpDate, String xid )
	{
		this(iname, curExpDate, null, xid);
	}

	/**
	 * Creates an <code>EppCommandRenewXriName</code> object with a
	 * specified expiration period, and a client transaction id associated
	 * with the operation
	 */
	public EppCommandRenewXriName( String iname, Calendar curExpDate, EppPeriod period, String xid )
	{
		this.iname = iname;
		this.curExpDate = curExpDate;
		this.period = period;
		this.clTRID = xid;
	}

	/**
	 * Gets the i-name of the XRI i-name object to be renewed
	 */
	public String getIName()
	{
		return this.iname;
	}

	/**
	 * Sets the i-name of the XRI i-name object to be renewed
	 */
	public void setIName( String iname )
	{
		this.iname = iname;
	}

	/**
	 * Converts the <code>EppCommandRenewXriName</code> object into
	 *		an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the
	 *		<code>EppCommandRenewXriName</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Element elm;
		Element body = EppUtil.createElementNS(doc, "xriINA", tag);

		if( iname != null )
		{
			elm = doc.createElement("iname");
			elm.appendChild(doc.createTextNode(iname));
			body.appendChild(elm);
		}
		if( curExpDate != null )
		{
			elm = doc.createElement("curExpDate");
			elm.appendChild(EppUtil.createTextNode(doc, curExpDate, true));
			body.appendChild(elm);
		}
		if( period != null )
		{
			body.appendChild(period.toXML(doc, "period"));
		}

		return toXMLCommon(doc, tag, body);
	}

	/**
	 * Converts an XML element into an <code>EppCommandRenewXriName</code>
	 * object. The caller of this method must make sure that the root node
	 * is of an EPP Command Renew entity for EPP XRI I-Name object
	 *
	 * @param root root node for an <code>EppCommandRenewXriName</code>
	 *             object in XML format
	 *
	 * @return an <code>EppCommandRenewXriName</code> object, or null
	 *         if the node is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		EppCommandRenewXriName cmd = null;
		String iname = null;
		Calendar curExpDate = null;
		EppPeriod period = null;

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
				iname = EppUtil.getText(node);
			}
			else if( name.equals("curExpDate") )
			{
				curExpDate = EppUtil.getDate(node, true);
			}
			else if( name.equals("period") )
			{
				period = (EppPeriod) EppPeriod.fromXML(node);
			}
		}
		if( iname != null )
		{
			cmd = new EppCommandRenewXriName(iname, curExpDate, period, null);
		}

		return cmd;
	}
}
