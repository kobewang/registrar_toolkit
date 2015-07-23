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
 * This <code>EppSvcsub</code> class implements EPP Svcsub objects. The EPP
 * command mapping for EPP Svcsub objects can be found at <A
 * HREF="http://epp-ver-04.sourceforge.net/IETF/EPP-04/draft-zhang-epp-svcsub-00.txt">
 * http://epp-ver-04.sourceforge.net/IETF/EPP-04/draft-zhang-epp-svcsub-00.txt</A>.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppSvcsub extends EppObject
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
	 * Contact type for technical contact ("primary")
	 */
	public static final String CONTACT_TYPE_PRIMARY = "primary";
	/**
	 * Contact type for technical contact ("primary")
	 */
	public static final String CONTACT_TYPE_SECONDARY = "secondary";
	/**
	 * Contact type for BIZlock contact ("bizlock")
	 */
	public static final String CONTACT_TYPE_BIZLOCK = "bizlock";

	/**
	 * Svcsub status - clientDeleteProhibited
	 */
	public static final String STATUS_CLIENT_DELETE_PROHIBITED = "clientDeleteProhibited";
	/**
	 * Svcsub status - clientSuspended
	 */
	public static final String STATUS_CLIENT_SUSPENDED = "clientSuspended";
	/**
	 * Svcsub status - clientRenewProhibited
	 */
	public static final String STATUS_CLIENT_RENEW_PROHIBITED = "clientRenewProhibited";
	/**
	 * Svcsub status - clientTransferProhibited
	 */
	public static final String STATUS_CLIENT_TRANSFER_PROHIBITED = "clientTransferProhibited";
	/**
	 * Svcsub status - clientUpdateProhibited
	 */
	public static final String STATUS_CLIENT_UPDATE_PROHIBITED = "clientUpdateProhibited";
	/**
	 * Svcsub status - inactive
	 */
	public static final String STATUS_INACTIVE = "inactive";
	/**
	 * Svcsub status - ok
	 */
	public static final String STATUS_OK = "ok";
	/**
	 * Svcsub status - pendingDelete
	 */
	public static final String STATUS_PENDING_DELETE = "pendingDelete";
	/**
	 * Svcsub status - pendingTransfer
	 */
	public static final String STATUS_PENDING_TRANSFER = "pendingTransfer";
	/**
	 * Svcsub status - pendingVerification
	 */
	public static final String STATUS_PENDING_VERIFICATION = "pendingVerification";
	/**
	 * Svcsub status - serverDeleteProhibited
	 */
	public static final String STATUS_SERVER_DELETE_PROHIBITED = "serverDeleteProhibited";
	/**
	 * Svcsub status - serverSuspended
	 */
	public static final String STATUS_SERVER_SUSPENDED = "serverSuspended";
	/**
	 * Svcsub status - serverRenewProhibited
	 */
	public static final String STATUS_SERVER_RENEW_PROHIBITED = "serverRenewProhibited";
	/**
	 * Svcsub status - serverTransferProhibited
	 */
	public static final String STATUS_SERVER_TRANSFER_PROHIBITED = "serverTransferProhibited";
	/**
	 * Svcsub status - serverUpdateProhibited
	 */
	public static final String STATUS_SERVER_UPDATE_PROHIBITED = "serverUpdateProhibited";

	private String    id;
	private String    service;
	private Vector    param;
	private Vector    contact;
	private EppPeriod period;
	private String    registrant;

	/**
	 * Creates an <code>EppSvcsub</code> object
	 */
	public EppSvcsub()
	{
		this.param   = new Vector();
		this.contact = new Vector();
	}

	/**
	 * Creates an <code>EppSvcsub</code> object with a service subscription id
	 */
	public EppSvcsub( String id )
	{
		this();
		this.id = id;
	}

	/**
	 * Creates an <code>EppSvcsub</code> object with a service subscription
	 * id and service name
	 */
	public EppSvcsub( String id, String service )
	{
		this();
		this.id = id;
		this.service = service;
	}

	/**
	 * Gets the service subscription id
	 */
	public String getId()
	{
		return this.id;
	}

	/**
	 * Sets the service subscription id
	 */
	public void setId( String id )
	{
		this.id = id;
	}

	/**
	 * Gets the service name
	 */
	public String getService()
	{
		return this.service;
	}

	/**
	 * Sets the service name
	 */
	public void setService( String service )
	{
		this.service = service;
	}

	/**
	 * Gets a list of service parameters associated with the subscription
	 */
	public Vector getParam()
	{
		return this.param;
	}

	/**
	 * Extracts a list of the parameter values associated with the service
	 * subscription object based on the parameter name.
	 */
	public Vector getParam( String name )
	{
		if( name == null )
		{
			return null;
		}
		Vector list = new Vector();
		Vector param = this.getParam();
		if( param == null )
		{
			return list;
		}
		for( int i = 0; i < param.size(); i++ )
		{
			Object obj = param.elementAt(i);
			if( obj instanceof EppSvcsubParam )
			{
				EppSvcsubParam p = (EppSvcsubParam) obj;
				if( p.getName().equals(name) )
				{
					list.addElement(p.getValue());
				}
			}
		}
		return list;
	}

	/**
	 * Adds a service parameter to be associated with the subscription
	 */
	public void addParam( EppSvcsubParam param )
	{
		this.param.addElement(param);
	}

	/**
	 * Adds a service parameter to be associated with the subscription,
	 * given a paramater name and its value
	 */
	public void addParam( String name, String value )
	{
		this.param.addElement(new EppSvcsubParam(name, value));
	}

	/**
	 * Gets a list of contacts associated with the subscription
	 */
	public Vector getContact()
	{
		return this.contact;
	}

	/**
	 * Adds a contact for the subscription
	 */
	public void addContact( EppContactType contact )
	{
		this.contact.addElement(contact);
	}

	/**
	 * Adds a contact for the subscription, given a contact id and its type
	 */
	public void addContact( String id, String type )
	{
		this.contact.addElement(new EppContactType(id, type));
	}

	/**
	 * Gets registration period for the subscription
	 */
	public EppPeriod getPeriod()
	{
		return this.period;
	}

	/**
	 * Sets registration period for the subscription
	 */
	public void setPeriod( EppPeriod period )
	{
		this.period = period;
	}

	/**
	 * Gets the contact id of the registrant which owns the subscription
	 */
	public String getRegistrant()
	{
		return this.registrant;
	}

	/**
	 * Sets the contact id of the registrant which owns the subscription
	 */
	public void setRegistrant( String registrant )
	{
		this.registrant = registrant;
	}

	/**
	 * Extracts a list of the parameter values associated with the service
	 * subscription object based on the parameter name.
	 */
	public Vector getParamValue( String name )
	{
		return EppSvcsubParam.getParamValue(this.param, name);
	}

	/**
	 * Extracts a list of the contact ids associated with the service
	 * subscription object based on the contact type
	 */
	public Vector getContactId( String type )
	{
		return EppContactType.getContactId(this.contact, type);
	}

	/**
	 * Converts the <code>EppSvcsub</code> object into an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the <code>EppSvcsub</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Element elm;
		Element body = EppUtil.createElementNS(doc, "svcsub", tag);
		boolean isCreate = tag.equals("create");

		// the order of the tags for create is:
		//
		// id/service/period/registrant/contact/authInfo
		//
		// the order of the tags for info is
		//
		// id/roid/status/registrant/contact/service/param/clID/crID/crDate
		// upDate/exDate/trDate/authInfo
		//
		if( isCreate )
		{
			if( id != null )
			{
				elm = doc.createElement("id");
				elm.appendChild(doc.createTextNode(id));
				body.appendChild(elm);
			}
			if( service != null  )
			{
				elm = doc.createElement("service");
				elm.appendChild(doc.createTextNode(service));
				body.appendChild(elm);
			}
			if( param != null )
			{
				for( int i = 0; i < param.size(); i++ )
				{
					EppSvcsubParam p;
					p = (EppSvcsubParam) param.elementAt(i);
					body.appendChild(p.toXML(doc, "param"));
				}
			}
			if( period != null )
			{
				body.appendChild(period.toXML(doc, "period"));
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
					EppContactType p;
					p = (EppContactType) contact.elementAt(i);
					body.appendChild(p.toXML(doc, "contact"));
				}
			}
		}
		else
		{
			// id/roid/status/registrant/contact/service/param/etc
			if( id != null )
			{
				elm = doc.createElement("id");
				elm.appendChild(doc.createTextNode(id));
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
			if( service != null  )
			{
				elm = doc.createElement("service");
				elm.appendChild(doc.createTextNode(service));
				body.appendChild(elm);
			}
			if( param != null )
			{
				for( int i = 0; i < param.size(); i++ )
				{
					EppSvcsubParam p;
					p = (EppSvcsubParam) param.elementAt(i);
					body.appendChild(p.toXML(doc, "param"));
				}
			}
		}

		toXMLCommon(doc, body);

		return body;
	}

	/**
	 * Converts an XML element into an <code>EppSvcsub</code> object.
	 * The caller of this method must make sure that the root node is of
	 * the EPP Svcsub type.
	 *
	 * @param root root node for an <code>EppSvcsub</code> object in
	 *             XML format
	 *
	 * @return an <code>EppSvcsub</code> object, or null if the node
	 *         is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		EppSvcsub svcsub = new EppSvcsub();
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
				svcsub.setId(EppUtil.getText(node));
			}
			else if( name.equals("service") )
			{
				svcsub.setService(EppUtil.getText(node));
			}
			else if( name.equals("param") )
			{
				EppSvcsubParam p = (EppSvcsubParam) EppSvcsubParam.fromXML(node);
				if( p != null )
				{
					svcsub.addParam(p);
				}
			}
			else if( name.equals("contact") )
			{
				EppContactType c = (EppContactType) EppContactType.fromXML(node);
				if( c != null )
				{
					svcsub.addContact(c);
				}
			}
			else if( name.equals("period") )
			{
				EppPeriod p = (EppPeriod) EppPeriod.fromXML(node);
				if( p != null )
				{
					svcsub.setPeriod(p);
				}
			}
			else if( name.equals("registrant") )
			{
				svcsub.setRegistrant(EppUtil.getText(node));
			}
			else if( name.equals("authInfo") )
			{
				EppAuthInfo authInfo = (EppAuthInfo) EppAuthInfo.fromXML(node);
				if( authInfo != null )
				{
					svcsub.setAuthInfo(authInfo);
				}
			}
			else
			{
				svcsub.fromXMLCommon(node, name);
			}
		}

		return svcsub;
	}

	public String toString()
	{
		return toString("svcsub");
	}

	/**
	 * Creates an <code>EppCommandDeleteSvcsub</code> object for
	 * deleting an EPP Svcsub object from the registry.
	 *
	 * @param name the name of the svcsub object to be deleted
	 * @param xid  the client transaction id associated with the operation
	 */
	public static EppCommandDeleteSvcsub delete( String name, String xid )
	{
		return new EppCommandDeleteSvcsub(name, xid);
	}

	/**
	 * Creates an <code>EppCommandInfoSvcsub</code> object for
	 * querying the details of an EPP Svcsub object
	 *
	 * @param name the name of the svcsub object to be queried
	 * @param xid  the client transaction id associated with the operation
	 */
	public static EppCommandInfoSvcsub info( String name, String xid )
	{
		return new EppCommandInfoSvcsub(name, xid);
	}

	/**
	 * Creates an <code>EppCommandCheckSvcsub</code> object for
	 * checking the existance of EPP Svcsub objects in the registry.
	 * Names of EPP Svcsub objects can be added via the
	 * <code>add</code> or <code>addName</code> methods.
	 *
	 * @param xid  the client transaction id associated with the operation
	 */
	public static EppCommandCheckSvcsub check( String xid )
	{
		return new EppCommandCheckSvcsub(xid);
	}

	/**
	 * Creates an <code>EppCommandTransferSvcsub</code> object for
	 * transfering an EPP Svcsub object in the registry. The operation
	 * type, registration period and authorization information associated
	 * with the operation should be specified via <code>setPeriod</code>,
	 * <code>setOperation</code> and <code>setAuthInfo</code> method.
	 *
	 * @param name   the name of the svcsub object to be transferred
	 * @param period the extended registration period of the svcsub object,
	 *               or null if there is no change in the expiration
	 *               timestamp of the svcsub object after the transfer
	 *               operation
	 * @param xid    the client transaction id associated with the operation
	 */
	public static EppCommandTransferSvcsub transfer( String name, EppPeriod period, String xid )
	{
		return new EppCommandTransferSvcsub(name, period, xid);
	}

	/**
	 * Creates an <code>EppCommandUpdateSvcsub</code> object for
	 * updating an EPP Svcsub object in the registry. The actual update
	 * information should be specified via the various methods defined
	 * for the <code>EppCommandUpdateSvcsub</code> object.
	 *
	 * @param name the name of the svcsub object to be updated
	 * @param xid  the client transaction id associated with the operation
	 */
	public static EppCommandUpdateSvcsub update( String name, String xid )
	{
		return new EppCommandUpdateSvcsub(name, xid);
	}

	/**
	 * Creates an <code>EppCommandRenewSvcsub</code> object for
	 * renewing the registration of an EPP Svcsub object in the registry.
	 *
	 * @param name       the name of the svcsub object to be renewed
	 * @param curExpDate the current expiration date of the svcsub object
	 * @param period     the new registration period of the svcsub object,
	 *                   or null if using the value specified by the
	 *                   registry
	 * @param xid        the client transaction id associated with the
	 *                   operation
	 */
	public static EppCommandRenewSvcsub renew( String name, Calendar curExpDate, EppPeriod period, String xid )
	{
		return new EppCommandRenewSvcsub(name, curExpDate, period, xid);
	}
}
