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
 * This <code>EppCommandUpdateHost</code> class implements EPP Command Update
 * entity for EPP Host objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppCommandUpdateHost extends EppCommandUpdate
{
	private String name;
	private Vector addrAdded;
	private Vector addrRemoved;
	private String newName;

	/**
	 * Creates an <code>EppCommandUpdateHost</code> given the name of the
	 * host
	 */
	public EppCommandUpdateHost( String name )
	{
		this(name, null);
	}

	/**
	 * Creates an <code>EppCommandUpdateHost</code> given the name of the
	 * host with a client transaction id associated with operation
	 */
	public EppCommandUpdateHost( String name, String xid )
	{
		this.name        = name;
		this.addrAdded   = new Vector();
		this.addrRemoved = new Vector();
		this.clTRID      = xid;
	}

	/**
	 * Gets the name of the host to be updated
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * Sets the name of the host to be updated
	 */
	public void setName( String name )
	{
		this.name= name;
	}

	/**
	 * Gets the list of new IP addresses to be added to the host
	 */
	public Vector getAddressAdded()
	{
		return this.addrAdded;
	}

	/**
	 * Adds a new IP address to be associated with the host
	 */
	public void addAddress( EppIpAddress address )
	{
		this.addrAdded.addElement(address);
	}

	/**
	 * Gets the list of old IP addresses to be removed from the host
	 */
	public Vector getAddressRemoved()
	{
		return this.addrRemoved;
	}

	/*
	 * Removes an old IP address associated with the host
	 */
	public void removeAddress( EppIpAddress address )
	{
		this.addrRemoved.addElement(address);
	}

	/**
	 * Gets the new name for the host, or null if the name of the
	 * the host is not to be changed
	 */
	public String getNewName()
	{
		return this.newName;
	}

	/**
	 * Sets the new name for the host if the name of the
	 * the host needs to be changed
	 */
	public void setNewName( String name )
	{
		this.newName = name;
	}

	/**
	 * Converts the <code>EppCommandUpdateHost</code> object into 
	 * an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the 
	 *            <code>EppCommandUpdateHost</code> object
	 *
	 * @return an <code>Element</code> object 
	 */
	public Element toXML( Document doc, String tag )
	{
		Element elm;
		Element body = EppUtil.createElementNS(doc, "host", tag);

		if( name != null )
		{
			elm = doc.createElement("name");
			elm.appendChild(doc.createTextNode(name));
			body.appendChild(elm);
		}
		if( (addrAdded.size() > 0) || (statusAdded.size() > 0) )
		{
			elm = doc.createElement("add");
			if( addrAdded.size() > 0 )
			{
				addressToXML(doc, elm, addrAdded);
			}
			if( statusAdded.size() > 0 )
			{
				statusToXML(doc, elm, statusAdded);
			}
			body.appendChild(elm);
		}
		if( (addrRemoved.size() > 0) || (statusRemoved.size() > 0) )
		{
			elm = doc.createElement("rem");
			if( addrRemoved.size() > 0 )
			{
				addressToXML(doc, elm, addrRemoved);
			}
			if( statusRemoved.size() > 0 )
			{
				statusToXML(doc, elm, statusRemoved);
			}
			body.appendChild(elm);
		}
		if( newName != null )
		{
			elm = doc.createElement("chg");
			Element name = doc.createElement("name");
			name.appendChild(doc.createTextNode(newName));
			elm.appendChild(name);
			body.appendChild(elm);
		}

		return toXMLCommon(doc, tag, body);
	}

	/**
	 * Converts a list of IP addresses into XML
	 *
	 * @param doc  the XML <code>Document</code> object
	 * @param body the XML <code>Element</code> object to which the list
	 *             of IP addresses is appended
	 * @param list the list of IP addresses to be converted
	 */
	private void addressToXML( Document doc, Element body, Vector list )
	{
		Element elm;
		if( list != null )
		{
			for( int i = 0; i < list.size(); i++ )
			{
				Object obj = list.elementAt(i);
				if( obj instanceof EppIpAddress )
				{
					EppIpAddress addr = (EppIpAddress) obj;
					elm = addr.toXML(doc, "addr");
					body.appendChild(elm);
				}
			}
		}
	}

	/**
	 * Converts an XML element into an <code>EppCommandUpdateHost</code>
	 * object. The caller of this method must make sure that the root node
	 * is of an EPP Command Update entity for an EPP Host object.
	 *
	 * @param root root node for an <code>EppCommandUpdate</code> object
	 *	     in XML format
	 *
	 * @return an <code>EppCommandUpdate</code> object, or null if the node
	 *	 is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		EppCommandUpdateHost cmd = null;
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
				String host = EppUtil.getText(node);
				cmd = new EppCommandUpdateHost(host);
			}
			else if( name.equals("add") )
			{
				if( cmd != null )
				{
					cmd.addressFromXML(node, cmd.addrAdded);
					cmd.statusFromXML(node, cmd.statusAdded);
				}
			}
			else if( name.equals("rem") )
			{
				if( cmd != null )
				{
					cmd.addressFromXML(node, cmd.addrRemoved);
					cmd.statusFromXML(node, cmd.statusRemoved);
				}
			}
			else if( name.equals("chg") )
			{
				NodeList tlist = node.getChildNodes();
				for( int j = 0; j < tlist.getLength(); j++ )
				{
					Node tnode = tlist.item(j);
					String tname = tnode.getLocalName();
					if( tname == null )
					{
						continue;
					}
					if( tname.equals("name") )
					{
						String host = EppUtil.getText(tnode);
						if( cmd != null )
						{
							cmd.setNewName(host);
						}
					}
				}
			}
		}

		return cmd;
	}

	/**
	 * Converts a list of IP addresses from XML format
	 *
	 * @param root the XML <code>Node</code> object containing the list
	 *             of IP addresses 
	 * @param addressList the list of IP addresses to be stored
	 */
	private void addressFromXML( Node root, Vector addressList )
	{
		NodeList list = root.getChildNodes();
		for( int i = 0; i < list.getLength(); i++ )
		{
			Node node = list.item(i);
			String name = node.getLocalName();
			if( name == null )
			{
				continue;
			}
			if( name.equals("addr") )
			{
				EppIpAddress addr = (EppIpAddress) EppIpAddress.fromXML(node);
				addressList.addElement(addr);
			}
		}
	}
}
