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

import org.w3c.dom.*;
import com.neulevel.epp.core.*;

/**
 * This <code>EppCommandInfoDomain</code> class implements EPP Command Info
 * entity for EPP Domain objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppCommandInfoDomain extends EppCommandInfo
{
	/**
	 * Hosts Control - all
	 *
	 * <P>A value of "all" (the default, which MAY be absent) returns
	 * information describing both subordinate and delegated hosts.
	 */
	public static final String HOSTS_ALL = "all";
	/**
	 * Hosts Control - del
	 *
	 * <P>A value of "del" returns information describing only delegated hosts.
	 */
	public static final String HOSTS_DEL = "del";
	/**
	 * Hosts Control - sub
	 *
	 * <P>A value of "sub" returns information describing only subordinate hosts.
	 */
	public static final String HOSTS_SUB = "sub";
	/**
	 * Hosts Control - none
	 *
	 * <P>A value of "none" returns no information describing delegated or subordinate hosts.
	 */
	public static final String HOSTS_NONE = "none";

	private String      name;
	private EppAuthInfo authInfo;
	private String      hostsCtrl;

	/**
	 * Creates an <code>EppCommandInfoDomain</code> object for
	 * querying a domain object based on its name
	 */
	public EppCommandInfoDomain( String name )
	{
		this(name, null);
	}

	/**
	 * Creates an <code>EppCommandInfoDomain</code> object for
	 * querying a domain object based on its name, given a client
	 * transaction id associated with the operation
	 */
	public EppCommandInfoDomain( String name, String xid )
	{
		this.name = name;
		this.clTRID = xid;
		this.authInfo  = null;
		this.hostsCtrl = HOSTS_ALL;
	}

	/**
	 * Gets the name of the domain object to be queried
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * Sets the name of the domain object to be queried
	 */
	public void setName( String name )
	{
		this.name = name;
	}

	/**
	 * Gets the optional authorization info for querying the domain object
	 */
	public EppAuthInfo getAuthInfo()
	{
		return this.authInfo;
	}

	/**
	 * Sets the optional authorization info for querying the domain object
	 */
	public void setAuthInfo( EppAuthInfo authInfo )
	{
		this.authInfo = authInfo;
	}

	/**
	 * Gets the hosts control attribute for querying the domain object
	 */
	public String getHostsControl()
	{
		return this.hostsCtrl;
	}

	/**
	 * Sets the hosts control attribute for querying the domain object,
	 * with one of the following values:
	 *
	 * <UL>
	 * <LI>HOSTS_ALL</LI>
	 * <LI>HOSTS_DEL</LI>
	 * <LI>HOSTS_SUB</LI>
	 * <LI>HOSTS_NONE</LI>
	 * </UL>
	 */
	public void setHostsControl( String control )
	{
		this.hostsCtrl = control;
	}

	/**
	 * Converts the <code>EppCommandInfoDomain</code> object into an XML
	 * element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the
	 *            <code>EppCommandInfoDomain</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Element elm;
		Element body = EppUtil.createElementNS(doc, "domain", tag);

		if( name != null )
		{
			elm = doc.createElement("name");
			elm.appendChild(doc.createTextNode(name));
			if( hostsCtrl != null )
			{
				elm.setAttribute("hosts", hostsCtrl);
			}
			body.appendChild(elm);
		}
		if( authInfo != null )
		{
			body.appendChild(authInfo.toXML(doc, "authInfo"));
		}

		return toXMLCommon(doc, tag, body);
	}

	/**
	 * Converts an XML element into an <code>EppCommandInfoDomain</code>
	 * object. The caller of this method must make sure that the root node
	 * is of an EPP Command Info entity for EPP domain object.
	 *
	 * @param root root node for an <code>EppCommandInfoDomain</code>
	 *             object in XML format
	 *
	 * @return an <code>EppCommandInfoDomain</code> object, or null
	 *         if the node is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		EppCommandInfoDomain cmd = null;
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
				String s = EppUtil.getText(node);
				if( (s != null) && (s.length() > 0) )
				{
					if( cmd == null )
					{
						cmd = new EppCommandInfoDomain(s);
					}
					else
					{
						cmd.setName(s);
					}
					s = ((Element) node).getAttribute("hosts");
					if( (s != null) && (s.length() > 0) )
					{
						cmd.setHostsControl(s);
					}
				}
			}
			else if( name.equals("authInfo") )
			{
				if( cmd != null )
				{
					EppAuthInfo p;
					p = (EppAuthInfo) EppAuthInfo.fromXML(node);
					if( p != null )
					{
						cmd.setAuthInfo(p);
					}
				}
			}
		}

		return cmd;
	}
}
