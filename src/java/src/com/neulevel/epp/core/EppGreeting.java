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

/**
 * This <code>EppGreeting</code> class implements EPP Greeting entity used
 * in the EPP Protocol initialization.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppGreeting extends EppEntity
{
	private String         svID;
	private Calendar       svDate;
	private EppServiceMenu svcMenu;
	private EppDataCollectionPolicy	dcp;

	/**
	 * Creates an <code>EppGreeting</code> object with default setting
	 */
	public EppGreeting()
	{
		this.svID = "NeuLevel EPP Server";
		this.svDate = Calendar.getInstance();
		this.svcMenu = new EppServiceMenu();
		this.svcMenu.addVersion("1.0");
		this.svcMenu.addLanguage("en-US");
		this.svcMenu.addService("urn:ietf:params:xml:ns:contact");
		this.svcMenu.addService("urn:ietf:params:xml:ns:host");
		this.svcMenu.addService("urn:ietf:params:xml:ns:domain");
		this.svcMenu.addService("urn:ietf:params:xml:ns:svcsub");

		this.dcp = new EppDataCollectionPolicy();
		dcp.setAccess(EppDataCollectionPolicy.ACCESS_ALL);
		EppDataCollectionStatement stmt = new EppDataCollectionStatement();
		stmt.setPurpose(EppDataCollectionStatement.PURPOSE_ADMIN, true);
		stmt.setPurpose(EppDataCollectionStatement.PURPOSE_PROV , true);
		stmt.setRecipient(EppDataCollectionStatement.RECIPIENT_OURS  , true);
		stmt.setRecipient(EppDataCollectionStatement.RECIPIENT_PUBLIC, true);
		stmt.setRetention(EppDataCollectionStatement.RETENTION_STATED);
		dcp.addStatement(stmt);
	}

	/**
	 * Creates an <code>EppGreeting</code> object, given a service
	 * identifier and a service menu, and a default data collection policy
	 *
	 * @param serviceId the identifier of the service provided
	 * @param serviceMenu the menu of the service provided
	 */
	public EppGreeting( String serviceId, EppServiceMenu serviceMenu )
	{
		this.svID = serviceId;
		this.svDate = Calendar.getInstance();
		this.svcMenu = serviceMenu;

		this.dcp = new EppDataCollectionPolicy();
		dcp.setAccess(EppDataCollectionPolicy.ACCESS_ALL);
		EppDataCollectionStatement stmt = new EppDataCollectionStatement();
		stmt.setPurpose(EppDataCollectionStatement.PURPOSE_ADMIN, true);
		stmt.setPurpose(EppDataCollectionStatement.PURPOSE_PROV , true);
		stmt.setRecipient(EppDataCollectionStatement.RECIPIENT_OURS  , true);
		stmt.setRecipient(EppDataCollectionStatement.RECIPIENT_PUBLIC, true);
		stmt.setRetention(EppDataCollectionStatement.RETENTION_STATED);
		dcp.addStatement(stmt);
	}

	/**
	 * Creates an <code>EppGreeting</code> object, given a service
	 * identifier and a service menu, and a data collection policy
	 *
	 * @param serviceId the identifier of the service provided
	 * @param serviceMenu the menu of the service provided
	 * @param dcp the data collection policy provided
	 */
	public EppGreeting( String serviceId, EppServiceMenu serviceMenu, EppDataCollectionPolicy dcp )
	{
		this.svID = serviceId;
		this.svDate = Calendar.getInstance();
		this.svcMenu = serviceMenu;
		this.dcp = dcp;
	}

	/**
	 * Gets the identifier of the service
	 */
	public String getServiceId()
	{
		return this.svID;
	}

	/**
	 * Sets the identifier of the service
	 */
	public void setServiceId( String serviceId )
	{
		this.svID = serviceId;
	}

	/**
	 * Gets the date associated with the service. The default value is the
	 * <code>Calendar</code> value when the <code>EppGreeting</code> is
	 * created.
	 */
	public Calendar getServiceDate()
	{
		return this.svDate;
	}

	/**
	 * Sets the date associated with the service
	 */
	public void setServiceDate( Calendar serviceDate )
	{
		this.svDate = serviceDate;
	}

	/**
	 * Gets the menu of the service provided
	 */
	public EppServiceMenu getServiceMenu()
	{
		return this.svcMenu;
	}

	/**
	 * Sets the menu of the service provided
	 */
	public void setServiceMenu( EppServiceMenu serviceMenu )
	{
		this.svcMenu = serviceMenu;
	}

	/**
	 * Gets the data collection policy
	 */
	public EppDataCollectionPolicy getDataCollectionPolicy()
	{
		return this.dcp;
	}

	/**
	 * Sets the data collection policy
	 */
	public void setDataCollectionPolicy( EppDataCollectionPolicy dcp )
	{
		this.dcp = dcp;
	}

	/**
	 * Converts the <code>EppGreeting</code> object into an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the <code>EppGreeting</code>
	 *            object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Element elm;
		Element epp = EppUtil.createElementNS(doc, "epp", null);
		Element body = doc.createElement(tag);
		epp.appendChild(body);

		if( svID != null )
		{
			elm = doc.createElement("svID");
			elm.appendChild(doc.createTextNode(svID));
			body.appendChild(elm);
		}
		if( svDate != null )
		{
			elm = doc.createElement("svDate");
			elm.appendChild(EppUtil.createTextNode(doc, svDate));
			body.appendChild(elm);
		}
		if( svcMenu != null )
		{
			body.appendChild(svcMenu.toXML(doc, "svcMenu"));
		}
		if( dcp != null )
		{
			body.appendChild(dcp.toXML(doc, "dcp"));
		}
		return epp;
	}

	/**
	 * Converts an XML element into an <code>EppGreeting</code> object.
	 * The caller of this method must make sure that the root node is of
	 * the EPP addrType.
	 *
	 * @param root root node for an <code>EppGreeting</code> object
	 *             in XML format
	 *
	 * @return an <code>EppGreeting</code> object, or null if the node
	 *         is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		EppGreeting greeting = null;
		NodeList list = root.getChildNodes();
		for( int i = 0; i < list.getLength(); i++ )
		{
			Node node = list.item(i);
			String name = node.getLocalName();
			if( name == null )
			{
				continue;
			}
			if( name.equals("greeting") )
			{
				greeting = new EppGreeting();
				root = node;
				break;
			}
		}

		if( greeting == null )
		{
			return null;
		}

		list = root.getChildNodes();
		for( int i = 0; i < list.getLength(); i++ )
		{
			Node node = list.item(i);
			String name = node.getLocalName();
			if( name == null )
			{
				continue;
			}
			if( name.equals("svID") )
			{
				greeting.svID = EppUtil.getText(node);
			}
			else if( name.equals("svDate") )
			{
				greeting.svDate = EppUtil.getDate(node);
			}
			else if( name.equals("svcMenu") )
			{
				greeting.svcMenu = (EppServiceMenu) EppServiceMenu.fromXML(node);
			}
			else if( name.equals("dcp") )
			{
				greeting.dcp = (EppDataCollectionPolicy) EppDataCollectionPolicy.fromXML(node);
			}
		}

		return greeting;
	}

	public String toString()
	{
		return toString("greeting");
	}
}
