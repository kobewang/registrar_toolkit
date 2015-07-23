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
 * This <code>EppCommandUpdateSvcsub</code> class implements EPP Command Update
 * entity for EPP Svcsub objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppCommandUpdateSvcsub extends EppCommandUpdate
{
	private String      id;
	private Vector      paramAdded;
	private Vector      paramRemoved;
	private Vector      contactAdded;
	private Vector      contactRemoved;
	private String      newRegistrant;
	private EppAuthInfo newAuthInfo;
	private String      userid;
	private EppAuthInfo authInfo;
	private String      service;

	/**
	 * Creates an <code>EppCommandUpdateSvcsub</code> given the id of the
	 * service subscription object
	 */
	public EppCommandUpdateSvcsub( String id )
	{
		this(id, null);
	}

	/**
	 * Creates an <code>EppCommandUpdateSvcsub</code> given the id of
	 * service subscription object and the client transaction id
	 */
	public EppCommandUpdateSvcsub( String id, String xid )
	{
		this.id             = id;
		this.paramAdded     = new Vector();
		this.paramRemoved   = new Vector();
		this.contactAdded   = new Vector();
		this.contactRemoved = new Vector();
		this.clTRID         = xid;
		this.userid         = null;
	}

	/**
	 * Gets the id of the service subscription object to be updated
	 */
	public String getId()
	{
		return this.id;
	}

	/**
	 * Sets the id of the service subscription object to be updated
	 */
	public void setId( String id )
	{
		this.id = id;
	}

	/**
	 * Gets the list of new service parameters to be added to the
	 * service subscription object
	 */
	public Vector getParamAdded()
	{
		return this.paramAdded;
	}

//	/**
//	 * Adds a new service parameter to be associated with the
//	 * service subscription object
//	 */
//	public void addParam( String name, String value )
//	{
//		this.paramAdded.addElement(new EppSvcsubParam(name, value));
//	}

	/**
	 * Adds a new service parameter to be associated with the
	 * service subscription object
	 */
	public void addParam( EppSvcsubParam param )
	{
		this.paramAdded.addElement(param);
	}

	/**
	 * Gets the list of old service parameters to be removed to the
	 * service subscription object
	 */
	public Vector getParamRemoved()
	{
		return this.paramRemoved;
	}

//	/**
//	 * Removes an old service parameter associated with the
//	 * service subscription object
//	 */
//	public void removeParam( String name, String value )
//	{
//		this.paramRemoved.addElement(new EppSvcsubParam(name, value));
//	}

	/**
	 * Removes an old service parameter associated with the
	 * service subscription object
	 */
	public void removeParam( EppSvcsubParam param )
	{
		this.paramRemoved.addElement(param);
	}

	/**
	 * Gets the list of new contacts to be added to the 
	 * service subscription object
	 */
	public Vector getContactAdded()
	{
		return this.contactAdded;
	}

	/**
	 * Adds a new contact to be associated with the
	 * service subscription object
	 */
	public void addContact( EppContactType contact )
	{
		this.contactAdded.addElement(contact);
	}

	/**
	 * Gets the list of old contacts to be removed from the
	 * service subscription object
	 */
	public Vector getContactRemoved()
	{
		return this.contactRemoved;
	}

	/*
	 * Removes an old contact associated with the
	 * service subscription object
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
	 * Sets the new registrant's contact id for the service subscription
	 * object if a new registrant claims the ownership of the service
	 * subscription object
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
	 * Gets the user id that can be used for authorization purpose
	 */
	public String getUserId()
	{
		return this.userid;
	}
	
	/**
	 * Sets the user id that can be used for authorization purpose
	 */
	public void setUserId( String userid )
	{
		this.userid = userid;
	}

	/**
	 * Gets the authorization info for the update operation
	 */
	public EppAuthInfo getAuthInfo()
	{
		return this.authInfo;
	}

	/**
	 * Sets the authorization info for the update operation
	 */
	public void setAuthInfo( EppAuthInfo authInfo )
	{
		this.authInfo = authInfo;
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
	 * Converts the <code>EppCommandUpdateSvcsub</code> object into 
	 * an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the 
	 *            <code>EppCommandUpdateSvcsub</code> object
	 *
	 * @return an <code>Element</code> object 
	 */
	public Element toXML( Document doc, String tag )
	{
		Element elm;
		Element body = EppUtil.createElementNS(doc, "svcsub", tag);

		if( id != null )
		{
			elm = doc.createElement("id");
			elm.appendChild(doc.createTextNode(id));
			body.appendChild(elm);
		}
		if( service != null )
		{
			elm = doc.createElement("service");
			elm.appendChild(doc.createTextNode(service));
			body.appendChild(elm);
		}
		if(    (paramAdded.size() > 0)
		    || (contactAdded.size() > 0)
		    || (statusAdded.size() > 0) )
		{
			elm = doc.createElement("add");
			if( paramAdded.size() > 0 )
			{
				paramToXML(doc, elm, paramAdded);
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
		if(    (paramRemoved.size() > 0)
		    || (contactRemoved.size() > 0)
		    || (statusRemoved.size() > 0) )
		{
			elm = doc.createElement("rem");
			if( paramRemoved.size() > 0 )
			{
				paramToXML(doc, elm, paramRemoved);
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
		if( userid != null )
		{
			elm = doc.createElement("userid");
			elm.appendChild(doc.createTextNode(userid));
			body.appendChild(elm);
		}
		if( authInfo != null )
		{
			body.appendChild(authInfo.toXML(doc, "authInfo"));
		}

		return toXMLCommon(doc, tag, body);
	}

	/**
	 * Converts a list of service parameters into XML
	 *
	 * @param doc  the XML <code>Document</code> object
	 * @param body the XML <code>Element</code> object to which the list
	 *             of IP addresses is appended
	 * @param list the list of service parameters to be converted
	 */
	private void paramToXML( Document doc, Element body, Vector list )
	{
		Element elm;
		if( list != null )
		{
			for( int i = 0; i < list.size(); i++ )
			{
				Object obj = list.elementAt(i);
				if( obj instanceof EppSvcsubParam )
				{
					EppSvcsubParam p = (EppSvcsubParam) obj;
					elm = p.toXML(doc, "param");
					body.appendChild(elm);
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
	 * Converts an XML element into an <code>EppCommandUpdateSvcsub</code>
	 * object. The caller of this method must make sure that the root node
	 * is of an EPP Command Update entity for an EPP Svcsub object.
	 *
	 * @param root root node for an <code>EppCommandUpdate</code> object
	 *	     in XML format
	 *
	 * @return an <code>EppCommandUpdateSvcsub</code> object, or null
	 *         if the node is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		EppCommandUpdateSvcsub cmd = null;
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
				String id = EppUtil.getText(node);
				cmd = new EppCommandUpdateSvcsub(id);
			}
			else if( name.equals("add") )
			{
				if( cmd != null )
				{
					cmd.paramFromXML(node, cmd.paramAdded);
					cmd.contactFromXML(node, cmd.contactAdded);
					cmd.statusFromXML(node, cmd.statusAdded);
				}
			}
			else if( name.equals("rem") )
			{
				if( cmd != null )
				{
					cmd.paramFromXML(node, cmd.paramRemoved);
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
			else if( name.equals("service") )
			{
				String service = EppUtil.getText(node);
				if( cmd != null )
				{
					cmd.setService(service);
				}
			}
			else if( name.equals("userid") )
			{
				String userid = EppUtil.getText(node);
				if( cmd != null )
				{
					cmd.setUserId(userid);
				}
			}
			else if( name.equals("authInfo") )
			{
				EppAuthInfo authInfo = (EppAuthInfo) EppAuthInfo.fromXML(node);
				if( cmd != null )
				{
					cmd.setAuthInfo(authInfo);
				}
			}
		}

		return cmd;
	}

	/**
	 * Converts a list of service parameters from XML format
	 *
	 * @param root the XML <code>Node</code> object containing the list
	 *             of IP addresses 
	 * @param addressList the list of service parameters to be stored
	 */
	private void paramFromXML( Node root, Vector paramList )
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
			if( name.equals("param") )
			{
				EppSvcsubParam p;
				p = (EppSvcsubParam) EppSvcsubParam.fromXML(node);
				paramList.addElement(p);
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
