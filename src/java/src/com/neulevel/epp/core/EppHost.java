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
package com.neulevel.epp.core;

import java.util.*;
import org.w3c.dom.*;
import com.neulevel.epp.core.command.*;

/**
 * This <code>EppHost</code> class implements EPP Host objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppHost extends EppObject
{
	/**
	 * Host status - clientDeleteProhibited
	 */
	public static final String STATUS_CLIENT_DELETE_PROHIBITED = "clientDeleteProhibited";
	/**
	 * Host status - clientUpdateProhibited
	 */
	public static final String STATUS_CLIENT_UPDATE_PROHIBITED = "clientUpdateProhibited";
	/**
	 * Host status - linked
	 */
	public static final String STATUS_LINKED = "linked";
	/**
	 * Host status - ok
	 */
	public static final String STATUS_OK = "ok";
	/**
	 * Host status - pendingCreate (EPP-1.0)
	 */
	public static final String STATUS_PENDING_CREATE = "pendingCreate";
	/**
	 * Host status - pendingDelete
	 */
	public static final String STATUS_PENDING_DELETE = "pendingDelete";
	/**
	 * Host status - pendingTransfer
	 */
	public static final String STATUS_PENDING_TRANSFER = "pendingTransfer";
	/**
	 * Host status - pendingUpdate (EPP-1.0)
	 */
	public static final String STATUS_PENDING_UPDATE = "pendingUpdate";
	/**
	 * Host status - serverDeleteProhibited
	 */
	public static final String STATUS_SERVER_DELETE_PROHIBITED = "serverDeleteProhibited";
	/**
	 * Host status - serverUpdateProhibited
	 */
	public static final String STATUS_SERVER_UPDATE_PROHIBITED = "serverUpdateProhibited";

	private String name;
	private Vector addr;

	/**
	 * Creates an <code>EppHost</code> object
	 */
	public EppHost()
	{
		this.name = null;
		this.addr = new Vector();
	}

	/**
	 * Creates an <code>EppHost</code> object with a domain name
	 */
	public EppHost( String name )
	{
		this();
		this.name = name;
	}

	/**
	 * Gets the name of the host
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * Sets the name of the host
	 */
	public void setName( String name )
	{
		this.name = name;
	}

	/**
	 * Gets the list of IP addresses associated with the host
	 */
	public Vector getAddress()
	{
		return this.addr;
	}

	/**
	 * Adds an IP address into the list of the IP addresses associated with
	 * the host
	 */
	public void addAddress( EppIpAddress address )
	{
		this.addr.addElement(address);
	}

	/**
	 * Converts the <code>EppHost</code> object into an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the <code>EppHost</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML(Document doc, String tag)
	{
		Element elm;
		Element body = EppUtil.createElementNS(doc, "host", tag);

		if( name != null )
		{
			elm = doc.createElement("name");
			elm.appendChild(doc.createTextNode(name));
			body.appendChild(elm);
		}
		if( roid != null )
		{
			elm = doc.createElement("roid");
			elm.appendChild(doc.createTextNode(roid));
			body.appendChild(elm);
		}
		if( status != null )
		{
			for( int i = 0; i < status.size(); i++ )
			{
				EppStatus s = (EppStatus) status.elementAt(i);
				body.appendChild(s.toXML(doc, "status"));
			}
		}
		if( addr != null )
		{
			for( int i = 0; i < addr.size(); i++ )
			{
				EppIpAddress ip = (EppIpAddress) addr.elementAt(i);
				body.appendChild(ip.toXML(doc, "addr"));
			}
		}

		toXMLCommon(doc, body);

		return body;
	}

	/**
	 * Converts an XML element into an <code>EppHost</code> object.
	 * The caller of this method must make sure that the root node is of
	 * the EPP Host type.
	 *
	 * @param root root node for an <code>EppHost</code> object in
	 *             XML format
	 *
	 * @return an <code>EppHost</code> object, or null if the node
	 *         is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		EppHost host = new EppHost();
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
				host.setName(EppUtil.getText(node));
			}
			else if( name.equals("addr") )
			{
				EppIpAddress ip = (EppIpAddress) EppIpAddress.fromXML(node);
				if ( ip != null )
				{
					host.addAddress(ip);
				}
			}
			else
			{
				host.fromXMLCommon(node, name);
			}
		}

	   	return host;
	}

	public String toString()
	{
		return toString("host");
	}

	/**
	 * Creates an <code>EppCommandDeleteHost</code> object for
	 * deleting an EPP Host object from the registry.
	 *
	 * @param name the name of the host object to be deleted
	 * @param xid  the client transaction id associated with the operation
	 */
	public static EppCommandDeleteHost delete( String name, String xid )
	{
		return new EppCommandDeleteHost(name, xid);
	}

	/**
	 * Creates an <code>EppCommandInfoHost</code> object for
	 * querying the details of an EPP Host object
	 *
	 * @param name the name of the host object to be queried
	 * @param xid  the client transaction id associated with the operation
	 */
	public static EppCommandInfoHost info( String name, String xid )
	{
		return new EppCommandInfoHost(name, xid);
	}

	/**
	 * Creates an <code>EppCommandCheckHost</code> object for
	 * checking the existance of EPP Host objects in the registry.
	 * Names of EPP Host objects can be added via the
	 * <code>add</code> or <code>addName</code> methods.
	 *
	 * @param xid  the client transaction id associated with the operation
	 */
	public static EppCommandCheckHost check( String xid )
	{
		return new EppCommandCheckHost(xid);
	}

	/**
	 * Creates an <code>EppCommandUpdateHost</code> object for
	 * updating an EPP Host object in the registry. The actual update
	 * information should be specified via the various methods defined
	 * for the <code>EppCommandUpdateHost</code> object.
	 *
	 * @param name the name of the host object to be updated
	 * @param xid  the client transaction id associated with the operation
	 */
	public static EppCommandUpdateHost update( String name, String xid )
	{
		return new EppCommandUpdateHost(name, xid);
	}
}
