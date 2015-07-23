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
 * This <code>EppCommandRenewXriService</code> class implements EPP Command Renew
 * entity for EPP XRI I-Service objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppCommandRenewXriService extends EppCommandRenew
{
	private String id;

	/**
	 * Creates an <code>EppCommandRenewXriService</code> object with a
	 * default expiration period, specified by the registry
	 */
	public EppCommandRenewXriService( String id, Calendar curExpDate )
	{
		this(id, curExpDate, null, null);
	}

	/**
	 * Creates an <code>EppCommandRenewXriService</code> object with a
	 * client transaction id associated with the operation. The current
	 * date of expiration would be the current date.
	 */
	public EppCommandRenewXriService( String id, String xid )
	{
		this(id, Calendar.getInstance(), null, xid);
	}

	/**
	 * Creates an <code>EppCommandRenewXriService</code> object with a
	 * default expiration period, specified by the registry, and a
	 * client transaction id associated with the operation
	 */
	public EppCommandRenewXriService( String id, Calendar curExpDate, String xid )
	{
		this(id, curExpDate, null, xid);
	}

	/**
	 * Creates an <code>EppCommandRenewXriService</code> object with a
	 * specified expiration period, and a client transaction id associated
	 * with the operation
	 */
	public EppCommandRenewXriService( String id, Calendar curExpDate, EppPeriod period, String xid )
	{
		this.id = id;
		this.curExpDate = curExpDate;
		this.period = period;
		this.clTRID = xid;
	}

	/**
	 * Gets the identifier of the XRI i-service object to be renewed
	 */
	public String getId()
	{
		return this.id;
	}

	/**
	 * Sets the identifier of the XRI i-service object to be renewed
	 */
	public void setId( String id )
	{
		this.id = id;
	}

	/**
	 * Converts the <code>EppCommandRenewXriService</code> object into
	 *		an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the
	 *		<code>EppCommandRenewXriService</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Element elm;
		Element body = EppUtil.createElementNS(doc, "xriISV", tag);

		if( id != null )
		{
			elm = doc.createElement("id");
			elm.appendChild(doc.createTextNode(id));
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
	 * Converts an XML element into an <code>EppCommandRenewXriService</code>
	 * object. The caller of this method must make sure that the root node
	 * is of an EPP Command Renew entity for EPP XRI I-Service object
	 *
	 * @param root root node for an <code>EppCommandRenewXriService</code>
	 *             object in XML format
	 *
	 * @return an <code>EppCommandRenewXriService</code> object, or null
	 *         if the node is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		EppCommandRenewXriService cmd = null;
		String id = null;
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
			if( name.equals("id") )
			{
				id = EppUtil.getText(node);
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
		if( id != null )
		{
			cmd = new EppCommandRenewXriService(id, curExpDate, period, null);
		}

		return cmd;
	}
}
