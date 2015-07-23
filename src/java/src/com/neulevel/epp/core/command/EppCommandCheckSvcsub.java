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
 * This <code>EppCommandCheckSvcsub</code> class implements EPP Command Check
 * entity for EPP Svcsub objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppCommandCheckSvcsub extends EppCommandCheck
{
	private Vector ids;
	private String service;

	/**
	 * Creates an <code>EppCommandCheckSvcsub</code> object
	 */
	public EppCommandCheckSvcsub()
	{
		this(null);
	}

	/**
	 * Creates an <code>EppCommandCheckSvcsub</code> object, given a
	 * client transaction id associated with the operation
	 */
	public EppCommandCheckSvcsub( String xid )
	{
		this.ids = new Vector();
		this.clTRID = xid;
		this.service = null;
	}

	/**
	 * Gets the list of the ids of the service subscription objects
	 * to be checked
	 */
	public Vector getId()
	{
		return this.ids;
	}

	/**
	 * Gets the list of the ids of the service subscription objects
	 * to be checked.
	 *
	 * <P><B>Note:</B> This is an alias for <code>getId</code>
	 */
	public Vector get()
	{
		return this.getId();
	}

	/**
	 * Adds the id of a service subscription object to the list of
	 * ids of service subscription objects be checked
	 */
	public void addId( String id )
	{
		this.ids.addElement(id);
	}

	/**
	 * Adds the id of a service subscription object to the list of
	 * ids of service subscription objects be checked.
	 *
	 * <P><B>Note:</B> This is an alias for <code>addId</code>
	 */
	public void add( String id )
	{
		this.addId(id);
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
	 * Converts the <code>EppCommandCheckSvcsub</code> object into 
	 * an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the
	 *            <code>EppCommandCheckSvcsub</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Element elm;
		Element body = EppUtil.createElementNS(doc, "svcsub", tag);

		if( service != null )
		{
			elm = doc.createElement("service");
			elm.appendChild(doc.createTextNode(service));
			body.appendChild(elm);
		}
		if( ids != null )
		{
			for( int i = 0; i < ids.size(); i++ )
			{
				String id = (String) ids.get(i);
				elm = doc.createElement("id");
				elm.appendChild(doc.createTextNode(id));
				body.appendChild(elm);
			}
		}

		return toXMLCommon(doc, tag, body);
	}

	/**
	 * Converts an XML element into an <code>EppCommandCheckSvcsub</code>
	 * object. The caller of this method must make sure that the root node
	 * is of an EPP Command Check entity for EPP Svcsub objects.
	 *
	 * @param root root node for an <code>EppCommandCheckSvcsub</code>
	 *             object in XML format
	 *
	 * @return an <code>EppCommandCheckSvcsub</code> object, or null
	 *         if the node is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		EppCommandCheckSvcsub cmd = new EppCommandCheckSvcsub();
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
				cmd.addId(id);
			}
			else if( name.equals("service") )
			{
				String svc = EppUtil.getText(node);
				cmd.setService(svc);
			}
		}

		return cmd;
	}
}
