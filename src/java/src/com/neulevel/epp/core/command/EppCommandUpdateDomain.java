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
 * This <code>EppCommandUpdateDomain</code> class implements EPP Command Update
 * entity for EPP Domain objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppCommandUpdateDomain extends EppCommandUpdate
{
	private String      name;
	private Vector      nsAdded;
	private Vector      nsRemoved;
	private Vector      nsAttrAdded;
	private Vector      nsAttrRemoved;
	private Vector      contactAdded;
	private Vector      contactRemoved;
	private String      newRegistrant;
	private EppAuthInfo newAuthInfo;

	/**
	 * Creates an <code>EppCommandUpdateDomain</code> given the name of the
	 * domain
	 */
	public EppCommandUpdateDomain( String name )
	{
		this(name, null);
	}

	/**
	 * Creates an <code>EppCommandUpdateDomain</code> given the name of the
	 * domain and a client transaction id
	 */
	public EppCommandUpdateDomain( String name, String xid )
	{
		this.name           = name;
		this.nsAdded        = null;
		this.nsRemoved      = null;
		this.nsAttrAdded    = null;
		this.nsAttrRemoved  = null;
		this.contactAdded   = new Vector();
		this.contactRemoved = new Vector();
		this.clTRID         = xid;
	}

	/**
	 * Gets the name of the domain to be updated
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * Sets the name of the domain to be updated
	 */
	public void setName( String name )
	{
		this.name = name;
	}

	/**
	 * Gets the list of new name servers to be added to the domain
	 */
	public Vector getNameServerAdded()
	{
		return this.nsAdded;
	}

	/**
	 * Adds a new name server to be associated with the domain
	 */
	public void addNameServer( String nameServer )
	{
		this.nsAttrAdded = null;
		if( this.nsAdded == null )
		{
			this.nsAdded = new Vector();
		}
		this.nsAdded.addElement(nameServer);
	}

	/**
	 * Gets the list of old name servers to be removed from the domain
	 */
	public Vector getNameServerRemoved()
	{
		return this.nsRemoved;
	}

	/**
	 * Removes an old name server associated with the domain
	 */
	public void removeNameServer( String nameServer )
	{
		this.nsAttrRemoved = null;
		if( this.nsRemoved == null )
		{
			this.nsRemoved = new Vector();
		}
		this.nsRemoved.addElement(nameServer);
	}

	/**
	 * Gets the list of new name servers, specified as host attributes, to be added to the domain
	 */
	public Vector getNameServerHostAttributeAdded()
	{
		return this.nsAttrAdded;
	}

	/**
	 * Adds a new name server, specified as host attribute, to be associated with the domain
	 */
	public void addNameServer( EppHostAttribute nameServer )
	{
		this.nsAdded = null;
		if( this.nsAttrAdded == null )
		{
			this.nsAttrAdded = new Vector();
		}
		this.nsAttrAdded.addElement(nameServer);
	}

	/**
	 * Gets the list of old name servers, specified as host attributes, to be removed from the domain
	 */
	public Vector getNameServerHostAttributeRemoved()
	{
		return this.nsAttrRemoved;
	}

	/**
	 * Removes an old name server, specified as a host attributes, associated with the domain
	 */
	public void removeNameServer( EppHostAttribute nameServer )
	{
		this.nsRemoved = null;
		if( this.nsAttrRemoved == null )
		{
			this.nsAttrRemoved = new Vector();
		}
		this.nsAttrRemoved.addElement(nameServer);
	}

	/**
	 * Gets the list of new contacts to be added to the domain
	 */
	public Vector getContactAdded()
	{
		return this.contactAdded;
	}

	/**
	 * Adds a new contact to be associated with the domain
	 */
	public void addContact( EppContactType contact )
	{
		this.contactAdded.addElement(contact);
	}

	/**
	 * Gets the list of old contacts to be removed from the domain
	 */
	public Vector getContactRemoved()
	{
		return this.contactRemoved;
	}

	/*
	 * Removes an old contact associated with the domain
	 */
	public void removeContact( EppContactType contact )
	{
		this.contactRemoved.addElement(contact);
	}

	/**
	 * Gets the new registrant's contact id for the domain, or null if the
	 * registrant of the domain is not to be changed
	 */
	public String getNewRegistrant()
	{
		return this.newRegistrant;
	}

	/**
	 * Sets the new registrant's contact id for the domain if a new registrant
	 * claims the ownership of the domain
	 */
	public void setNewRegistrant( String registrant )
	{
		this.newRegistrant = registrant;
	}

	/**
	 * Gets the new authorization information for the domain, or null if
	 * the authorization information of the domain is not to be changed
	 */
	public EppAuthInfo getNewAuthInfo()
	{
		return this.newAuthInfo;
	}

	/**
	 * Sets the new authorization information for the domain if it needs
	 * to be changed
	 */
	public void setNewAuthInfo( EppAuthInfo authInfo )
	{
		this.newAuthInfo = authInfo;
	}

	/**
	 * Converts the <code>EppCommandUpdateDomain</code> object into 
	 * an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the 
	 *            <code>EppCommandUpdateDomain</code> object
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
			body.appendChild(elm);
		}
		if(    ((nsAdded     != null) && (nsAdded.size()     > 0))
		    || ((nsAttrAdded != null) && (nsAttrAdded.size() > 0))
		    || (contactAdded.size() > 0)
		    || (statusAdded.size() > 0) )
		{
			elm = doc.createElement("add");
			if( (nsAdded != null) && (nsAdded.size() > 0) )
			{
				nameServerToXML(doc, elm, nsAdded);
			}
			else if( (nsAttrAdded != null) && (nsAttrAdded.size() > 0) )
			{
				nameServerHostAttributeToXML(doc, elm, nsAttrAdded);
			}
			if( contactAdded.size() > 0 )
			{
				contactToXML(doc, elm, contactAdded);
			}
			if( statusAdded.size() > 0 )
			{
				statusToXML(doc, elm, statusAdded);
			}
			body.appendChild(elm);
		}
		if(    ((nsRemoved     != null) && (nsRemoved.size()     > 0))
		    || ((nsAttrRemoved != null) && (nsAttrRemoved.size() > 0))
		    || (contactRemoved.size() > 0)
		    || (statusRemoved.size() > 0) )
		{
			elm = doc.createElement("rem");
			if( (nsRemoved != null) && (nsRemoved.size() > 0) )
			{
				nameServerToXML(doc, elm, nsRemoved);
			}
			else if( (nsAttrRemoved != null) && (nsAttrRemoved.size() > 0) )
			{
				nameServerHostAttributeToXML(doc, elm, nsAttrRemoved);
			}

			if( contactRemoved.size() > 0 )
			{
				contactToXML(doc, elm, contactRemoved);
			}
			if( statusRemoved.size() > 0 )
			{
				statusToXML(doc, elm, statusRemoved);
			}
			body.appendChild(elm);
		}
		if( (newRegistrant != null) || (newAuthInfo != null) )
		{
			elm = doc.createElement("chg");
			if( newRegistrant != null )
			{
				Element name = doc.createElement("registrant");
				name.appendChild(doc.createTextNode(newRegistrant));
				elm.appendChild(name);
			}
			if( newAuthInfo != null )
			{
				elm.appendChild(newAuthInfo.toXML(doc, "authInfo"));
			}
			body.appendChild(elm);
		}

		return toXMLCommon(doc, tag, body);
	}

	/**
	 * Converts a list of name servers into XML
	 *
	 * @param doc  the XML <code>Document</code> object
	 * @param body the XML <code>Element</code> object to which the list
	 *             of IP addresses is appended
	 * @param list the list of name servers to be converted
	 */
	private void nameServerToXML( Document doc, Element body, Vector list )
	{
		Element elm = null;
		if( list != null )
		{
			for( int i = 0; i < list.size(); i++ )
			{
				Object obj = list.elementAt(i);
				if( obj instanceof String )
				{
					String ns = (String) obj;
					if( (ns == null) || (ns.length() == 0) )
					{
						continue;
					}
					if( elm == null )
					{
						elm = doc.createElement("ns");
						body.appendChild(elm);
					}
					Element t = doc.createElement("hostObj");
					t.appendChild(doc.createTextNode(ns));
					elm.appendChild(t);
				}
			}
		}
	}

	/**
	 * Converts a list of name servers, specified as host attributes, into XML
	 *
	 * @param doc  the XML <code>Document</code> object
	 * @param body the XML <code>Element</code> object to which the list
	 *             of IP addresses is appended
	 * @param list the list of name servers to be converted
	 */
	private void nameServerHostAttributeToXML( Document doc, Element body, Vector list )
	{
		Element elm = null;
		if( list != null )
		{
			for( int i = 0; i < list.size(); i++ )
			{
				Object obj = list.elementAt(i);
				if( obj instanceof EppHostAttribute )
				{
					EppHostAttribute ns = (EppHostAttribute) obj;
					if( ns == null )
					{
						continue;
					}
					if( elm == null )
					{
						elm = doc.createElement("ns");
						body.appendChild(elm);
					}
					Element t = ns.toXML(doc, "hostAttr");
					elm.appendChild(t);
				}
			}
		}
	}

	/**
	 * Converts a list of contacts into XML
	 *
	 * @param doc  the XML <code>Document</code> object
	 * @param body the XML <code>Element</code> object to which the list
	 *             of IP addresses is appended
	 * @param list the list of contacts to be converted
	 */
	private void contactToXML( Document doc, Element body, Vector list )
	{
		Element elm;
		if( list != null )
		{
			for( int i = 0; i < list.size(); i++ )
			{
				Object obj = list.elementAt(i);
				if( obj instanceof EppContactType )
				{
					EppContactType ct = (EppContactType) obj;
					elm = ct.toXML(doc, "contact");
					body.appendChild(elm);
				}
			}
		}
	}

	/**
	 * Converts an XML element into an <code>EppCommandUpdateDomain</code>
	 * object. The caller of this method must make sure that the root node
	 * is of an EPP Command Update entity for an EPP Domain object.
	 *
	 * @param root root node for an <code>EppCommandUpdate</code> object
	 *	     in XML format
	 *
	 * @return an <code>EppCommandUpdateDomain</code> object, or null
	 *         if the node is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		EppCommandUpdateDomain cmd = null;
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
				cmd = new EppCommandUpdateDomain(host);
			}
			else if( name.equals("add") )
			{
				if( cmd != null )
				{
					cmd.nameServerFromXML(node, true);
					cmd.contactFromXML(node, cmd.contactAdded);
					cmd.statusFromXML(node, cmd.statusAdded);
				}
			}
			else if( name.equals("rem") )
			{
				if( cmd != null )
				{
					cmd.nameServerFromXML(node, false);
					cmd.contactFromXML(node, cmd.contactRemoved);
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
					if( tname.equals("registrant") )
					{
						String id = EppUtil.getText(tnode);
						if( cmd != null )
						{
							cmd.setNewRegistrant(id);
						}
					}
					else if( tname.equals("authInfo") )
					{
						EppAuthInfo authInfo = (EppAuthInfo) EppAuthInfo.fromXML(tnode);
						if( cmd != null )
						{
							cmd.setNewAuthInfo(authInfo);
						}
					}
				}
			}
		}

		return cmd;
	}

	/**
	 * Converts a list of name servers from XML format
	 *
	 * @param root the XML <code>Node</code> object containing the list
	 *             of IP addresses 
	 * @param addOrRem boolean flag for adding or removing name servers
	 */
	private void nameServerFromXML( Node root, boolean addOrRem )
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
			if( name.equals("ns") == false )
			{
				continue;
			}
			NodeList clist = node.getChildNodes();
			if( clist == null )
			{
				continue;
			}
			for( int j = 0; j < clist.getLength(); j++ )
			{
				Node cnode = clist.item(j);
				String cname = cnode.getLocalName();
				if( cname == null )
				{
					continue;
				}
				if( cname.equals("hostObj") )
				{
					String ns = EppUtil.getText(cnode);
					if( (ns != null) && (ns.length() > 0) )
					{
						if( addOrRem == true )
						{
							this.addNameServer(ns);
						}
						else
						{
							this.removeNameServer(ns);
						}
					}
				}
				else if( cname.equals("hostAttr") )
				{
					EppHostAttribute ns;
					ns = (EppHostAttribute) EppHostAttribute.fromXML(cnode);
					if( ns != null )
					{
						if( addOrRem == true )
						{
							this.addNameServer(ns);
						}
						else
						{
							this.removeNameServer(ns);
						}
					}
				}
			}
		}
	}

	/**
	 * Converts a list of contacts from XML format
	 *
	 * @param root the XML <code>Node</code> object containing the list
	 *             of IP addresses 
	 * @param contactList the list of contacts to be stored
	 */
	private void contactFromXML( Node root, Vector contactList )
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
			if( name.equals("contact") )
			{
				EppContactType ct = (EppContactType) EppContactType.fromXML(node);
				contactList.addElement(ct);
			}
		}
	}
}
