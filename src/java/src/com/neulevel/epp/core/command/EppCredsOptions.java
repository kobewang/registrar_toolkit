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
 * This <code>EppCredsOptions</code> class implements EPP credsOptionsType
 * entity.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppCredsOptions extends EppEntity
{
	private String version;
	private String lang;

	/**
	 * Creates an <code>EppCredsOptions</code> object, given the version
	 * string and language id
	 *
	 * @param version verion string
	 * @param language language id
	 */
	public EppCredsOptions( String version, String language )
	{
		this.version = version;
		this.lang = language;
	}

	/**
	 * Gets version string
	 */
	public String getVersion()
	{
		return this.version;
	}

	/**
	 * Sets version string
	 */
	public void setVersion( String version )
	{
		this.version = version;
	}

	/**
	 * Gets language id
	 */
	public String getLanguage()
	{
		return this.lang;
	}

	/**
	 * Sets language id
	 */
	public void setLanguage( String language )
	{
		this.lang = language;
	}

	/**
	 * Converts the <code>EppCredsOptions</code> object into an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the <code>EppCredsOptions</code>
	 *            object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Element body = doc.createElement(tag);
		Element elm;

		if( version != null )
		{
			elm = doc.createElement("version");
			elm.appendChild(doc.createTextNode(version));
			body.appendChild(elm);
		}
		if( lang != null )
		{
			elm = doc.createElement("lang");
			elm.appendChild(doc.createTextNode(lang));
			body.appendChild(elm);
		}

		return body;
	}

	/**
	 * Converts an XML element into an <code>EppCredsOptions</code> object.
	 * The caller of this method must make sure that the root node is of the
	 * EPP credsOptionsType.
	 *
	 * @param root root node for an <code>EppCredsOptions</code> object in
	 *             XML format
	 *
	 * @return an <code>EppCredsOptions</code> object, or null if the node
	 *         is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		String version = null;
		String lang = null;

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
				version = EppUtil.getText(node);
			}
			else if( name.equals("lang") )
			{
				lang = EppUtil.getText(node);
			}
		}

		if( (version != null) && (lang != null) )
		{
			return new EppCredsOptions(version, lang);
		}

		return null;
	}

	/**
	 * Converts the <code>EppCredsOptions</code> object into plain XML text
	 * string by using the default root tag name
	 *
	 * @return a text string representing the <code>EppCredsOptions</code>
	 *         object in XML format
	 */
	public String toString()
	{
		return toString("options");
	}
}
