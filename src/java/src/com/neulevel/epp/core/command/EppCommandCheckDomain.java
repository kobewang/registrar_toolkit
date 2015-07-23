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
 * This <code>EppCommandCheckDomain</code> class implements EPP Command Check
 * entity for EPP Domain objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppCommandCheckDomain extends EppCommandCheck
{
	private Vector names;

	/**
	 * Creates an <code>EppCommandCheckDomain</code> object
	 */
	public EppCommandCheckDomain()
	{
		this(null);
	}

	/**
	 * Creates an <code>EppCommandCheckDomain</code> object, given a
	 * client transaction id associated with the operation
	 */
	public EppCommandCheckDomain( String xid )
	{
		this.names = new Vector();
		this.clTRID = xid;
	}

	/**
	 * Gets the list of the names of the domain objects to be checked
	 */
	public Vector getName()
	{
		return this.names;
	}

	/**
	 * Gets the list of the names of the domain objects to be checked.
	 *
	 * <P><B>Note:</B> This is an alias for <code>getName</code>
	 */
	public Vector get()
	{
		return this.getName();
	}

	/**
	 * Add a domain name to the list of the names of the domain
	 * objects to be checked
	 */
	public void addName( String name )
	{
		this.names.addElement(name);
	}

	/**
	 * Add a domain name to the list of the names of the domain
	 * objects to be checked.
	 *
	 * <P><B>Note:</B> This is an alias for <code>addName</code>
	 */
	public void add( String name )
	{
		this.addName(name);
	}

	/**
	 * Converts the <code>EppCommandCheckDomain</code> object into 
	 * an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the
	 *            <code>EppCommandCheckDomain</code> object
	 *
	 * @return an <code>Element</code> object 
	 */
	public Element toXML( Document doc, String tag )
	{
		Element elm;
		Element body = EppUtil.createElementNS(doc, "domain", tag);

		if( names != null )
		{
			for( int i = 0; i < names.size(); i++ )
			{
				String name = (String) names.get(i);
				elm = doc.createElement("name");
				elm.appendChild(doc.createTextNode(name));
				body.appendChild(elm);
			}
		}

		return toXMLCommon(doc, tag, body);
	}

	/**
	 * Converts an XML element into an <code>EppCommandCheckDomain</code>
	 * object. The caller of this method must make sure that the root node
	 * is of an EPP Command Check entity for EPP Domain objects.
	 *
	 * @param root root node for an <code>EppCommandCheckDomain</code>
	 *             object in XML format
	 *
	 * @return an <code>EppCommandCheckDomain</code> object, or null
	 *         if the node is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		EppCommandCheckDomain cmd = new EppCommandCheckDomain();
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
				cmd.addName(domain);
			}
		}

		return cmd;
	}
}
