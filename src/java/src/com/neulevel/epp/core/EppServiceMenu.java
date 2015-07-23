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
 * This <code>EppServiceMenu</code> class implements EPP svcMenuType entity.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppServiceMenu extends EppEntity
{
	/**
	 * The version list of the EPP Service Menu
	 */
	protected Vector version;
	/**
	 * The language list of the EPP Service Menu
	 */
	protected Vector lang;
	/**
	 * The standard object URI list of the EPP Service Menu (EPP-1.0).
	 * For example: "urn:ietf:params:xml:ns:obj1"
	 */
	protected Vector objURI;
	/**
	 * The <I>svcExtension</I> object URI list of the EPP Service Menu (EPP-1.0).
	 * For example: "http://custom/obj1ext-1.0 obj1ext-1.0.xsd"
	 */
	protected Vector extURI;

	/**
	 * Creates an <code>EppServiceMenu</code> object
	 */
	public EppServiceMenu()
	{
		this.version = new Vector();
		this.lang    = new Vector();
		this.objURI  = new Vector();
		this.extURI  = new Vector();
	}

	/**
	 * Returns a <code>Vector</code> containing all version numbers
	 * supported by the EPP server
	 */
	public Vector getVersion()
	{
		return this.version;
	}

	/**
	 * Adds a version number to the list of versions supported by the
	 * EPP server
	 */
	public void addVersion( String version )
	{
		this.version.addElement(version);
	}

	/**
	 * Returns a <code>Vector</code> containing all languages supported
	 * by the Epp server
	 */
	public Vector getLanguage()
	{
		return this.lang;
	}

	/**
	 * Adds a language to the list of languages supported by the
	 * EPP server
	 */
	public void addLanguage( String language )
	{
		this.lang.addElement(language);
	}

	/**
	 * Returns a <code>Vector</code> containing the URIs
	 * for all standard objects supported by the EPP server
	 */
	public Vector getService()
	{
		return this.objURI;
	}

	/**
	 * Adds an object URI to the list of all standard objects supported by
	 * the EPP server
	 */
	public void addService( String service )
	{
		this.objURI.addElement(service);
	}

	/**
	 * Returns a <code>Vector</code> containing the URIs of
	 * all <I>extension</I> objects supported by the EPP server - alias to getServiceExtention()
	 */
	public Vector getUnspec()
	{
		return this.extURI;
	}

	/**
	 * Returns a <code>Vector</code> containing the URIs of
	 * all <I>extension</I> objects supported by the EPP server
	 */
	public Vector getServiceExtension()
	{
		return this.extURI;
	}

	/**
	 * Adds an <I>extension</I> object to the list of all <I>extURI</I>
	 * objects supported by the EPP server
	 */
	public void addServiceExtension( String uri )
	{
		this.extURI.addElement(uri);
	}

	/**
	 * Adds an <I>extension</I> object to the list of all <I>extURI</I>
	 * objects supported by the EPP server
	 *
	 * @param name the name of the object (not used)
	 * @param uri the uri of the object
	 * @param schema the XML schema defining the object (not used)
	 *
	 * <P><B>Note:</B> This method is retained for backward compatiblity
	 */
	public void addUnspec( String name, String uri, String schema )
	{
		this.extURI.addElement(uri);
	}

	/**
	 * Converts the <code>EppServiceMenu</code> object into an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the <code>EppServiceMenu</code>
	 *            object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Element elm;
		Element body = doc.createElement(tag);

		if( version != null )
		{
			for( int i = 0; i < version.size(); i++ )
			{
				Object obj = version.get(i);
				if( (obj != null) && (obj instanceof String) )
				{
					elm = doc.createElement("version");
					elm.appendChild(doc.createTextNode((String) obj));
					body.appendChild(elm);
				}
			}
		}
		if( lang != null )
		{
			for( int i = 0; i < lang.size(); i++ )
			{
				Object obj = lang.get(i);
				if( (obj != null) && (obj instanceof String) )
				{
					elm = doc.createElement("lang");
					elm.appendChild(doc.createTextNode((String) obj));
					body.appendChild(elm);
				}
			}
		}
		if( objURI != null )
		{
			for( int i = 0; i < objURI.size(); i++ )
			{
				Object obj = objURI.get(i);
				if( (obj != null) && (obj instanceof String) )
				{
					String uri = (String) obj;
					elm = doc.createElement("objURI");
					elm.appendChild(doc.createTextNode(uri));
					body.appendChild(elm);
				}
			}
		}
		if( (extURI != null) && (extURI.size() > 0) )
		{
			elm = null;
			for( int i = 0; i < extURI.size(); i++ )
			{
				Object obj = extURI.get(i);
				if( (obj != null) && (obj instanceof String) )
				{
					String uri = (String) obj;
					Element ext = doc.createElement("extURI");
					ext.appendChild(doc.createTextNode(uri));
					if( elm == null )
					{
						elm = doc.createElement("svcExtension");
						body.appendChild(elm);
					}
					elm.appendChild(ext);
				}
			}
		}

		return body;
	}

	/**
	 * Converts an XML element into an <code>EppServiceMenu</code> object.
	 * The caller of this method must make sure that the root node is of
	 * the EPP addrType.
	 *
	 * @param root root node for an <code>EppServiceMenu</code> object
	 *             in XML format
	 *
	 * @return an <code>EppServiceMenu</code> object, or null if the node
	 *         is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		EppServiceMenu svcmenu = new EppServiceMenu();
		NodeList list = root.getChildNodes();
		for( int i = 0; i < list.getLength(); i++ )
		{
			Node node = list.item(i);
			String name = node.getLocalName();
			if( name == null )
			{
				continue;
			}
			if( name.equals("version") )
			{
				svcmenu.addVersion(EppUtil.getText(node));
			}
			else if( name.equals("lang") )
			{
				svcmenu.addLanguage(EppUtil.getText(node));
			}
			else if( name.equals("objURI") )
			{
				String uri = EppUtil.getText(node);
				if( (uri != null) && (uri.length() > 0) )
				{
					svcmenu.addService(uri);
				}
			}
			else if( name.equals("svcExtension") )
			{
				NodeList ulist = node.getChildNodes();
				for( int j = 0; j < ulist.getLength(); j++ )
				{
					Node unode = ulist.item(j);
					name = unode.getLocalName();
					if( name == null )
					{
						continue;
					}
					if( name.equals("extURI") )
					{
						String uri = EppUtil.getText(unode);
						if( (uri != null) && (uri.length() > 0) )
						{
							svcmenu.addServiceExtension(uri);
						}
					}
				}
			}
		}

		return svcmenu;
	}

	public String toString()
	{
		return toString("svcMenu");
	}
}
