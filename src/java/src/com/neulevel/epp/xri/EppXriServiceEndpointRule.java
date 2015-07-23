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
 * This <code>EppXriServiceEndpointRule</code> abstract class encapsulates
 * the common EPP XRI Authority ServiceEndpoint rules (Type, MediaType and Path).
 *
 * @author William Tan william.tan@neustar.biz
 * @version $Revision: 1.6 $ $Date: 2006/04/03 20:36:44 $
 */
public abstract class EppXriServiceEndpointRule extends EppEntity
{
	public static final String MATCH_ATTR_DEFAULT  = "default";
	public static final String MATCH_ATTR_CONTENT  = "content";
	public static final String MATCH_ATTR_ANY      = "any";
	public static final String MATCH_ATTR_NON_NULL = "non-null";
	public static final String MATCH_ATTR_NULL     = "null";
	public static final String MATCH_ATTR_NONE     = "none";

	/**
	 * Default value of the match attribute if it was omitted or its
	 * value is null. This is an alias for <code>MATCH_ATTR_CONTENT</code>
	 * as defined in xri-resolution-v2.0-wd-10-ed-08.
	 */
	public static final String DEFAULT_MATCH_ATTR = MATCH_ATTR_CONTENT;

	public static final String  SELECT_ATTR_TRUE  = "true";
	public static final String  SELECT_ATTR_FALSE = "false";

	/**
	 * Default value of the select attribute is FALSE if it was omitted
	 * in the parent element.
	 */
	public static final String  DEFAULT_SELECT_ATTR = SELECT_ATTR_FALSE;
	public static final boolean DEFAULT_SELECT_ATTR_BOOL = false;

	private String  match;
	private Boolean select;
	private String  value; // represents the value of this rule

	/**
	 * Creates a default <code>EppXriServiceEndpointRule</code> object
	 */
	public EppXriServiceEndpointRule()
	{
		this(null, null, null);
	}

	/**
	 * Creates a default <code>EppXriServiceEndpointRule</code> object with the given value
	 */
	public EppXriServiceEndpointRule( String value, String match, Boolean select )
	{
		this.match  = match;
		this.select = select;
		this.value  = value;
	}

	/**
	 * Gets the "match" attribute of this Type/MediaType/Path rule
	 */
	public String getMatch()
	{
		return this.match;
	}

	/**
	 * Sets the "match" attribute of this Type/MediaType/Path rule
	 */
	public void setMatch( String match )
	{
		this.match = match;
	}

	/**
	 * Gets the "select" attribute of this Type/MediaType/Path rule
	 */
	public boolean getSelect()
	{
		if ( this.select != null )
		{
			return this.select.booleanValue();
		}
		else
		{
			return DEFAULT_SELECT_ATTR_BOOL;
		}
	}

	/**
	 * Sets the "select" attribute of this Type/MediaType/Path rule
	 */
	public void setSelect( boolean select )
	{
		this.select = Boolean.valueOf(select);
	}

	/**
	 * Sets the "select" attribute of this Type/MediaType/Path rule.
	 * Interprets "true" (any case) or "1" as TRUE. Any other value
	 * is considered FALSE.
	 */
	public void setSelect( String select )
	{
		if (select.equalsIgnoreCase("true") || select.equals("1"))
		{
			this.select = Boolean.TRUE;
		}
		else
		{
			this.select = Boolean.FALSE;
		}
	}

	/**
	 * Gets the value of this Type/MediaType/Path rule
	 */
	public String getValue()
	{
		return this.value;
	}

	/**
	 * Sets the value of this Type/MediaType/Path rule
	 */
	public void setValue( String value )
	{
		this.value = value;
	}


	/**
         * Converts the <code>EppXriServiceEndpointRule</code> object into an XML element
         *
         * @param doc the XML <code>Document</code> object
         * @param tag the tag/element name for the <code>EppXriServiceEndpoint</code> object
         *
         * @return an <code>Element</code> object
         */
	public Element toXML( Document doc, String tag )
	{
		Element body = doc.createElement(tag);
		Element elm;

		if( this.match != null )
		{
			body.setAttribute("match", this.match);
		}

		if( this.select != null )
		{
			body.setAttribute("select", this.select.toString());
		}

		if( this.value != null)
		{
			body.appendChild(doc.createTextNode(this.value));
		}

		return body;
	}

	/**
	 * Sets the members of this <code>EppXriServiceEndpointRule</code> object from the given XML element.
	 * The caller of this method must make sure that the root node is of
	 * the EPP XRI sepRuleType.
	 *
	 * @param root root node for an <code>EppXriServiceEndpointRule</code> object in
	 *             XML format
	 *
	 */
	public void setFromXML( Node root )
	{
		String attr = ((Element) root).getAttribute("match");
		if ( attr != null )
		{
			this.setMatch(attr.trim());
		}

		attr = ((Element) root).getAttribute("select");
		if ( attr != null )
		{
			this.setSelect(attr.trim());
		}

		this.setValue(EppUtil.getText(root));
	}

}
