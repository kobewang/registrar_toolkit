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
 * This <code>EppCommandRenewXriNumber</code> class implements EPP Command Renew
 * entity for EPP XRI I-Number objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppCommandRenewXriNumber extends EppCommandRenew
{
	private String inumber;

	/**
	 * Creates an <code>EppCommandRenewXriNumber</code> object with a
	 * default expiration period, specified by the registry
	 */
	public EppCommandRenewXriNumber( String inumber, Calendar curExpDate )
	{
		this(inumber, curExpDate, null, null);
	}

	/**
	 * Creates an <code>EppCommandRenewXriNumber</code> object with a
	 * client transaction id associated with the operation. The current
	 * date of expiration would be the current date.
	 */
	public EppCommandRenewXriNumber( String inumber, String xid )
	{
		this(inumber, Calendar.getInstance(), null, xid);
	}

	/**
	 * Creates an <code>EppCommandRenewXriNumber</code> object with a
	 * default expiration period, specified by the registry, and a
	 * client transaction id associated with the operation
	 */
	public EppCommandRenewXriNumber( String inumber, Calendar curExpDate, String xid )
	{
		this(inumber, curExpDate, null, xid);
	}

	/**
	 * Creates an <code>EppCommandRenewXriNumber</code> object with a
	 * specified expiration period, and a client transaction id associated
	 * with the operation
	 */
	public EppCommandRenewXriNumber( String inumber, Calendar curExpDate, EppPeriod period, String xid )
	{
		this.inumber = inumber;
		this.curExpDate = curExpDate;
		this.period = period;
		this.clTRID = xid;
	}

	/**
	 * Gets the i-number of the XRI i-number object to be renewed
	 */
	public String getINumber()
	{
		return this.inumber;
	}

	/**
	 * Sets the i-number of the XRI i-number object to be renewed
	 */
	public void setINumber( String inumber )
	{
		this.inumber = inumber;
	}

	/**
	 * Converts the <code>EppCommandRenewXriNumber</code> object into
	 *		an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the
	 *		<code>EppCommandRenewXriNumber</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Element elm;
		Element body = EppUtil.createElementNS(doc, "xriINU", tag);

		if( inumber != null )
		{
			elm = doc.createElement("inumber");
			elm.appendChild(doc.createTextNode(inumber));
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
	 * Converts an XML element into an <code>EppCommandRenewXriNumber</code>
	 * object. The caller of this method must make sure that the root node
	 * is of an EPP Command Renew entity for EPP XRI I-Number object
	 *
	 * @param root root node for an <code>EppCommandRenewXriNumber</code>
	 *             object in XML format
	 *
	 * @return an <code>EppCommandRenewXriNumber</code> object, or null
	 *         if the node is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		EppCommandRenewXriNumber cmd = null;
		String inumber = null;
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
			if( name.equals("inumber") )
			{
				inumber = EppUtil.getText(node);
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
		if( inumber != null )
		{
			cmd = new EppCommandRenewXriNumber(inumber, curExpDate, period, null);
		}

		return cmd;
	}
}
