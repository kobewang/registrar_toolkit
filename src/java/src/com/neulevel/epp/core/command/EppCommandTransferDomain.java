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
package com.neulevel.epp.core.command;

import java.util.*;
import org.w3c.dom.*;
import com.neulevel.epp.core.*;

/**
 * This <code>EppCommandTransferDomain</code> class implements EPP Command
 * Transfer entity for EPP Domain objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppCommandTransferDomain extends EppCommandTransfer
{
	private String    name;
	private EppPeriod period;

	/**
	 * Creates an <code>EppCommandTransferDomain</code> object for
	 * transfering a domain object based on its name
	 */
	public EppCommandTransferDomain( String name )
	{
		this(name, null, null);
	}

	/**
	 * Creates an <code>EppCommandTransferDomain</code> object for
	 * transfering a domain object based on its name, given a client
	 * transaction id associated with operation
	 */
	public EppCommandTransferDomain( String name, String xid )
	{
		this(name, null, xid);
	}

	/**
	 * Creates an <code>EppCommandTransferDomain</code> object for
	 * transfering a domain object based on its name and with an
	 * extended expiration period
	 */
	public EppCommandTransferDomain( String name, EppPeriod period )
	{
		this(name, period, null);
	}

	/**
	 * Creates an <code>EppCommandTransferDomain</code> object for
	 * transfering a domain object based on its name and with an
	 * extended expiration period and a client transaction id
	 * associated with operation
	 */
	public EppCommandTransferDomain( String name, EppPeriod period, String xid )
	{
		this.name   = name;
		this.period = period;
		this.clTRID = xid;
	}

	/**
	 * Gets the name of the domain object to be transferred
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * Sets the name of the domain object to be transferred
	 */
	public void setName( String name )
	{
		this.name = name;
	}

	/**
	 * Gets the registration period of the domain object to be transferred
	 */
	public EppPeriod getPeriod()
	{
		return this.period;
	}

	/**
	 * Sets the registration period of the domain object to be transferred
	 */
	public void setPeriod( EppPeriod period )
	{
		this.period = period;
	}

	/**
	 * Converts the <code>EppCommandTransferDomain</code> object into
	 * an XML element for an <code>EppPollable</code> object
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the
	 *            <code>EppCommandTransferDomain</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXMLPoll( Document doc, String tag )
	{
		Element elm;
		Element body = EppUtil.createElementNS(doc, "domain", tag);

		if( name != null )
		{
			elm = doc.createElement("name");
			elm.appendChild(doc.createTextNode(name));
			body.appendChild(elm);
		}
		if( period != null )
		{
			body.appendChild(period.toXML(doc, "period"));
		}
		if( authInfo != null )
		{
			body.appendChild(authInfo.toXML(doc, "authInfo"));
		}

		return body;
	}

	/**
	 * Converts the <code>EppCommandTransferDomain</code> object into
	 * an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the
	 *            <code>EppCommandTransferDomain</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Element body = toXMLPoll(doc, tag);

		Attr attr = doc.createAttribute("op");
		attr.setValue(this.getOperation());
		Vector attrList = new Vector();
		attrList.addElement(attr);

		return toXMLCommon(doc, tag, body, attrList);
	}

	/**
	 * Converts an XML element into an
	 * <code>EppCommandTransferDomain</code> object. The caller of this
	 * method must make sure that the root node is of an EPP Command
	 * Transfer entity for EPP Domain object.
	 *
	 * @param root root node for an <code>EppCommandTransferDomain</code>
	 *             object in XML format
	 *
	 * @return an <code>EppCommandTransferDomain</code> object, or null
	 *         if the node is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		EppCommandTransferDomain cmd = null;
		EppPeriod period = null;
		EppAuthInfo authInfo = null;
		NodeList list = root.getChildNodes();
		for( int i = 0; i < list.getLength(); i++ )
		{
			Node node = list.item(i);
			String name = node.getLocalName();
			if( name == null )
			{
				continue;
			}
			if( name.equals("name") )
			{
				String domain = EppUtil.getText(node);
				cmd = new EppCommandTransferDomain(domain);
			}
			else if( name.equals("period") )
			{
				period = (EppPeriod) EppPeriod.fromXML(node);
			}
			else if( name.equals("authInfo") )
			{
				authInfo = (EppAuthInfo) EppAuthInfo.fromXML(node);
			}
		}
		if( cmd != null )
		{
			cmd.setPeriod(period);
			cmd.setAuthInfo(authInfo);
		}

		return cmd;
	}
}
