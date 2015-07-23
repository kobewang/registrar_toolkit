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
 * This <code>EppDomain</code> class implements EPP Domain objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppDomain extends EppObject
{
	/**
	 * Contact type for administration contact ("admin");
	 */
	public static final String CONTACT_TYPE_ADMIN = "admin";
	/**
	 * Contact type for billing contact ("billing");
	 */
	public static final String CONTACT_TYPE_BILLING = "billing";
	/**
	 * Contact type for technical contact ("tech")
	 */
	public static final String CONTACT_TYPE_TECH = "tech";

	/**
	 * Domain status - clientDeleteProhibited
	 */
	public static final String STATUS_CLIENT_DELETE_PROHIBITED = "clientDeleteProhibited";
	/**
	 * Domain status - clientHold
	 */
	public static final String STATUS_CLIENT_HOLD = "clientHold";
	/**
	 * Domain status - clientRenewProhibited
	 */
	public static final String STATUS_CLIENT_RENEW_PROHIBITED = "clientRenewProhibited";
	/**
	 * Domain status - clientTransferProhibited
	 */
	public static final String STATUS_CLIENT_TRANSFER_PROHIBITED = "clientTransferProhibited";
	/**
	 * Domain status - clientUpdateProhibited
	 */
	public static final String STATUS_CLIENT_UPDATE_PROHIBITED = "clientUpdateProhibited";
	/**
	 * Domain status - inactive
	 */
	public static final String STATUS_INACTIVE = "inactive";
	/**
	 * Domain status - ok
	 */
	public static final String STATUS_OK = "ok";
	/**
	 * Domain status - pendingCreate (EPP-1.0)
	 */
	public static final String STATUS_PENDING_CREATE = "pendingCreate";
	/**
	 * Domain status - pendingDelete
	 */
	public static final String STATUS_PENDING_DELETE = "pendingDelete";
	/**
	 * Domain status - pendingTransfer
	 */
	public static final String STATUS_PENDING_TRANSFER = "pendingTransfer";
	/**
	 * Domain status - pendingUpdaate (EPP-1.0)
	 */
	public static final String STATUS_PENDING_UPDATE = "pendingUpdate";
	/**
	 * Domain status - pendingVerification (EPP-04)
	 */
	public static final String STATUS_PENDING_VERIFICATION = "pendingVerification";
	/**
	 * Domain status - serverDeleteProhibited
	 */
	public static final String STATUS_SERVER_DELETE_PROHIBITED = "serverDeleteProhibited";
	/**
	 * Domain status - serverHold
	 */
	public static final String STATUS_SERVER_HOLD = "serverHold";
	/**
	 * Domain status - serverRenewProhibited
	 */
	public static final String STATUS_SERVER_RENEW_PROHIBITED = "serverRenewProhibited";
	/**
	 * Domain status - serverTransferProhibited
	 */
	public static final String STATUS_SERVER_TRANSFER_PROHIBITED = "serverTransferProhibited";
	/**
	 * Domain status - serverUpdateProhibited
	 */
	public static final String STATUS_SERVER_UPDATE_PROHIBITED = "serverUpdateProhibited";

	private String    name;
	private Vector    host;
	private Vector    ns;
	private Vector    nsHostAttr;
	private Vector    contact;
	private EppPeriod period;
	private String    registrant;

	/**
	 * Creates an <code>EppDomain</code> object
	 */
	public EppDomain()
	{
		this.host    = new Vector();
		this.ns      = new Vector();
		this.contact = new Vector();
		this.nsHostAttr = null;
	}

	/**
	 * Creates an <code>EppDomain</code> object with a domain name
	 */
	public EppDomain( String name )
	{
		this();
		this.name = name;
	}

	/**
	 * Gets the domain name
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * Sets the domain name
	 */
	public void setName( String name )
	{
		this.name = name;
	}

	/**
	 * Gets a list of host names associated with the domain
	 */
	public Vector getHost()
	{
		return this.host;
	}

	/**
	 * Adds a host name associated with the domain.
	 *
	 * <P><B>Note:</B> This method should be only called by the EPP server, and
	 *       an EPP client should not perform this operation.
	 */
	public void addHost( String host )
	{
		this.host.addElement(host);
	}

	/**
	 * Gets a list of name servers associated with the domain
	 */
	public Vector getNameServer()
	{
		return this.ns;
	}

	/**
	 * Add a name server associated with the domain
	 */
	public void addNameServer( String nameServer )
	{
		this.nsHostAttr = null;
		if( this.ns == null )
		{
			this.ns = new Vector();
		}
		this.ns.addElement(nameServer);
	}

	/**
	 * Gets a list of name servers associated with the domain,
	 * specified via host attributes
	 */
	public Vector getNameServerHostAttribute()
	{
		return this.nsHostAttr;
	}

	/**
	 * Add a name server associated with the domain,
	 * specified via a host attribute
	 */
	public void addNameServer( EppHostAttribute nsHostAddr )
	{
		this.ns = null;
		if( this.nsHostAttr == null )
		{
			this.nsHostAttr = new Vector();
		}
		this.nsHostAttr.addElement(nsHostAddr);
	}

	/**
	 * Gets a list of contacts associated with the domain
	 */
	public Vector getContact()
	{
		return this.contact;
	}

	/**
	 * Adds a contact for the domain
	 */
	public void addContact( EppContactType contact )
	{
		this.contact.addElement(contact);
	}

	/**
	 * Adds a contact for the domain, given a contact id and its type
	 */
	public void addContact( String id, String type )
	{
		this.contact.addElement(new EppContactType(id, type));
	}

	/**
	 * Gets registration period for the domain name
	 */
	public EppPeriod getPeriod()
	{
		return this.period;
	}

	/**
	 * Sets registration period for the domain name
	 */
	public void setPeriod( EppPeriod period )
	{
		this.period = period;
	}

	/**
	 * Gets the contact id of the registrant which owns the domain
	 */
	public String getRegistrant()
	{
		return this.registrant;
	}

	/**
	 * Sets the contact id of the registrant which owns the domain
	 */
	public void setRegistrant( String registrant )
	{
		this.registrant = registrant;
	}

	/**
	 * Converts the <code>EppDomain</code> object into an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the <code>EppDomain</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Element elm;
		Element body = EppUtil.createElementNS(doc, "domain", tag);
		boolean isCreate = tag.equals("create");

		// the order of the tags for create is:
		//
		// name/period/ns/registrant/contact/authInfo
		//
		// the order of the tags for info is
		//
		// name/roid/status/registrant/contact/ns/host/clID/crID/crDate
		// upDate/exDate/trDate/authInfo
		//
		// so the tricky part is to put ns ahead of registrant for
		// a create tag, and ns after contact for an info tag

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

		// ns for a create tag

		if( isCreate && (ns != null) && (ns.size() > 0) )
		{
			elm = doc.createElement("ns");
			body.appendChild(elm);
			for( int i = 0; i < ns.size(); i++ )
			{
				String s = (String) ns.elementAt(i);
				Element e = doc.createElement("hostObj");
				e.appendChild(doc.createTextNode(s));
				elm.appendChild(e);
			}
		}
		else if( isCreate && (nsHostAttr != null) && (nsHostAttr.size() > 0) )
		{
			elm = doc.createElement("ns");
			body.appendChild(elm);
			for( int i = 0; i < nsHostAttr.size(); i++ )
			{
				EppHostAttribute h = (EppHostAttribute) nsHostAttr.elementAt(i);
				Element e = h.toXML(doc, "hostAttr");
				elm.appendChild(e);
			}
		}
		if( registrant != null )
		{
			elm = doc.createElement("registrant");
			elm.appendChild(doc.createTextNode(registrant));
			body.appendChild(elm);
		}
		if( contact != null )
		{
			for( int i = 0; i < contact.size(); i++ )
			{
				EppContactType c = (EppContactType) contact.elementAt(i);
				body.appendChild(c.toXML(doc, "contact"));
			}
		}

		// ns for an info tag

		if( ! isCreate && (ns != null) && (ns.size() > 0) )
		{
			elm = doc.createElement("ns");
			body.appendChild(elm);
			for( int i = 0; i < ns.size(); i++ )
			{
				String s = (String) ns.elementAt(i);
				Element e = doc.createElement("hostObj");
				e.appendChild(doc.createTextNode(s));
				elm.appendChild(e);
			}
		}
		else if( ! isCreate && (nsHostAttr != null) && (nsHostAttr.size() > 0) )
		{
			elm = doc.createElement("ns");
			body.appendChild(elm);
			for( int i = 0; i < nsHostAttr.size(); i++ )
			{
				EppHostAttribute h = (EppHostAttribute) nsHostAttr.elementAt(i);
				Element e = h.toXML(doc, "hostAttr");
				elm.appendChild(e);
			}
		}
		if( host != null )
		{
			for( int i = 0; i < host.size(); i++ )
			{
				String s = (String) host.elementAt(i);
				elm = doc.createElement("host");
				elm.appendChild(doc.createTextNode(s));
				body.appendChild(elm);
			}
		}

		toXMLCommon(doc, body);

		return body;
	}

	/**
	 * Converts an XML element into an <code>EppDomain</code> object.
	 * The caller of this method must make sure that the root node is of
	 * the EPP Domain type.
	 *
	 * @param root root node for an <code>EppDomain</code> object in
	 *             XML format
	 *
	 * @return an <code>EppDomain</code> object, or null if the node
	 *         is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		EppDomain domain = new EppDomain();
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
				domain.setName(EppUtil.getText(node));
			}
			else if( name.equals("host") )
			{
				domain.addHost(EppUtil.getText(node));
			}
			else if( name.equals("ns") )
			{
				NodeList clist = node.getChildNodes();
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
						domain.addNameServer(EppUtil.getText(cnode));
					}
					else if( cname.equals("hostAttr") )
					{
						EppHostAttribute h;
						h = (EppHostAttribute) EppHostAttribute.fromXML(cnode);
						if( h != null )
						{
							domain.addNameServer(h);
						}
					}
				}
			}
			else if( name.equals("contact") )
			{
				EppContactType c = (EppContactType) EppContactType.fromXML(node);
				if( c != null )
				{
					domain.addContact(c);
				}
			}
			else if( name.equals("period") )
			{
				EppPeriod p = (EppPeriod) EppPeriod.fromXML(node);
				if( p != null )
				{
					domain.setPeriod(p);
				}
			}
			else if( name.equals("registrant") )
			{
				domain.setRegistrant(EppUtil.getText(node));
			}
			else if( name.equals("authInfo") )
			{
				EppAuthInfo authInfo = (EppAuthInfo) EppAuthInfo.fromXML(node);
				if( authInfo != null )
				{
					domain.setAuthInfo(authInfo);
				}
			}
			else
			{
				domain.fromXMLCommon(node, name);
			}
		}

		return domain;
	}

	public String toString()
	{
		return toString("domain");
	}

	/**
	 * Creates an <code>EppCommandDeleteDomain</code> object for
	 * deleting an EPP Domain object from the registry.
	 *
	 * @param name the name of the domain object to be deleted
	 * @param xid  the client transaction id associated with the operation
	 */
	public static EppCommandDeleteDomain delete( String name, String xid )
	{
		return new EppCommandDeleteDomain(name, xid);
	}

	/**
	 * Creates an <code>EppCommandInfoDomain</code> object for
	 * querying the details of an EPP Domain object
	 *
	 * @param name the name of the domain object to be queried
	 * @param xid  the client transaction id associated with the operation
	 */
	public static EppCommandInfoDomain info( String name, String xid )
	{
		return new EppCommandInfoDomain(name, xid);
	}

	/**
	 * Creates an <code>EppCommandCheckDomain</code> object for
	 * checking the existance of EPP Domain objects in the registry.
	 * Names of EPP Domain objects can be added via the
	 * <code>add</code> or <code>addName</code> methods.
	 *
	 * @param xid  the client transaction id associated with the operation
	 */
	public static EppCommandCheckDomain check( String xid )
	{
		return new EppCommandCheckDomain(xid);
	}

	/**
	 * Creates an <code>EppCommandTransferDomain</code> object for
	 * transfering an EPP Domain object in the registry. The operation
	 * type, registration period and authorization information associated
	 * with the operation should be specified via <code>setPeriod</code>,
	 * <code>setOperation</code> and <code>setAuthInfo</code> method.
	 *
	 * @param name   the name of the domain object to be transferred
	 * @param period the extended registration period of the domain object,
	 *               or null if there is no change in the expiration
	 *               timestamp of the domain object after the transfer
	 *               operation
	 * @param xid    the client transaction id associated with the operation
	 */
	public static EppCommandTransferDomain transfer( String name, EppPeriod period, String xid )
	{
		return new EppCommandTransferDomain(name, period, xid);
	}

	/**
	 * Creates an <code>EppCommandUpdateDomain</code> object for
	 * updating an EPP Domain object in the registry. The actual update
	 * information should be specified via the various methods defined
	 * for the <code>EppCommandUpdateDomain</code> object.
	 *
	 * @param name the name of the domain object to be updated
	 * @param xid  the client transaction id associated with the operation
	 */
	public static EppCommandUpdateDomain update( String name, String xid )
	{
		return new EppCommandUpdateDomain(name, xid);
	}

	/**
	 * Creates an <code>EppCommandRenewDomain</code> object for
	 * renewing the registration of an EPP Domain object in the registry.
	 *
	 * @param name       the name of the domain object to be renewed
	 * @param curExpDate the current expiration date of the domain object
	 * @param period     the new registration period of the domain object,
	 *                   or null if using the value specified by the
	 *                   registry
	 * @param xid        the client transaction id associated with the
	 *                   operation
	 */
	public static EppCommandRenewDomain renew( String name, Calendar curExpDate, EppPeriod period, String xid )
	{
		return new EppCommandRenewDomain(name, curExpDate, period, xid);
	}
}
