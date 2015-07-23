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
package com.neulevel.epp.xri;

import java.util.*;
import org.w3c.dom.*;
import com.neulevel.epp.core.EppEntity;
import com.neulevel.epp.core.EppUtil;

/**
 * This <code>EppXriURI</code> class defines URI
 * information associated with XRI authority objects.  It
 * implements XRI uriType defined
 * in the XRI authority schema file.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.5 $ $Date: 2008/04/17 04:28:05 $
 */
public class EppXriURI extends EppEntity
{
	public static final String APPEND_LOCAL     = "local";
	public static final String APPEND_AUTHORITY = "authority";
	public static final String APPEND_PATH      = "path";
	public static final String APPEND_QUERY     = "query";
	public static final String APPEND_QXRI      = "qxri";
	public static final String APPEND_NONE      = "none";

	/**
	 * If the "append" attribute is not present, its default value
	 * is <code>APPEND_LOCAL</code>.
	 */
	public static final String DEFAULT_APPEND   = APPEND_LOCAL;

	private String  uri;
	private int     priority;
	private String  append;

	/**
	 * Creates an <code>EppXriURI</code> object
	 */
	public EppXriURI()
	{
		this(null, -1);
	}

	/**
	 * Creates an <code>EppXriURI</code> object with a URI
	 */
	public EppXriURI( String uri )
	{
		this(uri, -1);
	}

	/**
	 * Creates an <code>EppXriURI</code> object with a URI and a priority value
	 */
	public EppXriURI( String uri, int priority )
	{
		this(uri, priority, null);
	}

	/**
	 * Creates an <code>EppXriURI</code> object with a URI, a priority value and an append value.
	 */
	public EppXriURI( String uri, int priority, String append )
	{
		this.uri      = uri;
		this.append   = append;
		setPriority(priority);
	}

	/**
	 * Gets the URI
	 */
	public String getURI()
	{
		return this.uri;
	}

	/**
	 * Sets the URI
	 */
	public void setURI( String uri )
	{
		this.uri = uri;
	}

	/**
	 * Gets the priority value for this URI
	 */
	public int getPriority()
	{
		return this.priority;
	}

	/**
	 * Sets the priority value for this URI
	 */
	public void setPriority( int priority )
	{
		if (priority < -1)
			priority = -1;
		this.priority = priority;
	}

	/**
	 * Gets the append attribute value
	 */
	public String getAppend()
	{
		return this.append;
	}

	/**
	 * Sets the append attribute value
	 */
	public void setAppend( String append )
	{
		this.append = append;
	}

	/**
         * Converts the <code>EppXriURI</code> object into an XML element
         *
         * @param doc the XML <code>Document</code> object
         * @param tag the tag/element name for the <code>EppXriURI</code> object
         *
         * @return an <code>Element</code> object
         */
	public Element toXML( Document doc, String tag )
	{
		Element body = doc.createElement(tag);

		if (this.priority >= 0)
			body.setAttribute("priority", "" + this.priority);

		if ( this.append != null )
		{
			body.setAttribute("append", this.append);
		}

		body.appendChild(doc.createTextNode(this.uri));

		return body;
	}

	/**
	 * Converts an XML element into an <code>EppXriURI</code> object.
	 * The caller of this method must make sure that the root node is of
	 * the EPP XRI uriAddType or uriInfType.
	 *
	 * @param root root node for an <code>EppXriURI</code> object in
	 *             XML format
	 *
	 * @return an <code>EppXriURI</code> object, or null if the node is
	 *         invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		EppXriURI xin = new EppXriURI();

		xin.setURI(EppUtil.getText(root));

		String attr = ((Element) root).getAttribute("priority");
		if( attr != null )
		{
			try
			{
				int priority = Integer.parseInt(attr);
				xin.setPriority(priority);
			}
			catch( NumberFormatException e )
			{
			}
		}

		attr = ((Element) root).getAttribute("append");
		if( attr != null )
		{
			xin.setAppend(attr.trim());
		}

		return xin;
	}

	public String toString()
	{
		return toString("uri");
	}
}
