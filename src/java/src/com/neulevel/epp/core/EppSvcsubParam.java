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

import org.w3c.dom.*;
import java.util.*;

/**
 * This <code>EppSvcsubParam</code> class implements EPP Svcsub Param entity.
 * Currently, only EPP Svcsub objects have Svcsub params defined:
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppSvcsubParam extends EppEntity
{
	private String name;
	private String value;

	/**
	 * Creates an <code>EppSvcsubParam</code> object with the name
	 * of a parameter
	 */
	public EppSvcsubParam( String name )
	{
		this.name = name;
		this.value = null;
	}

	/**
	 * Creates an <code>EppSvcsubParam</code> object with the name
	 * of a parameter and a value string
	 */
	public EppSvcsubParam( String name, String value )
	{
		this.name = name;
		this.value = value;
	}

	/**
	 * Gets the name of the parameter
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * Sets the name of the parameter
	 */
	public void setName( String name )
	{
		this.name = name;
	}

	/**
	 * Gets the value of the parameter
	 */
	public String getValue()
	{
		return this.value;
	}

	/**
	 * Sets the value of the parameter
	 */
	public void setValue( String value )
	{
		this.value = value;
	}

	/**
	 * Gets a sub-list of parameter values, given a list of parameter values
	 * and a name string
	 *
	 * @param param a list of EppSvcsubParam objects
	 * @param name the name of parameters to be extracted from the list
	 *
	 * @return a list of parameter values, or null if not found
	 */
	public static Vector getParamValue( Vector param, String name )
	{
		Vector list = null;
		if( name == null )
		{
			return null;
		}
		if( param == null )
		{
			return null;
		}
		for( int i = 0; i < param.size(); i++ )
		{
			Object obj = param.elementAt(i);
			if( obj == null )
			{
				continue;
			}
			if( ! (obj instanceof EppSvcsubParam) )
			{
				continue;
			}
			EppSvcsubParam p = (EppSvcsubParam) obj;
			String n = p.getName();
			String v = p.getValue();
			if( (v == null) || (n == null) || (! n.equals(name)) )
			{
				continue;
			}
			if( list == null )
			{
				list = new Vector();
				if( list == null )
				{
					return null;
				}
			}
			list.add(v);
		}
		return list;
	};

	/**
 	 * Converts the <code>EppSvcsubParam</code> object into an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the <code>EppSvcsubParam</code>
	 *            object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Element body = doc.createElement(tag);
		if( this.name != null )
		{
			body.setAttribute("name", this.name);
		}
		if (this.value != null )
		{
			body.appendChild(doc.createTextNode(this.value));
		}
		return body;
	}

	/**
	 * Converts an XML element into an <code>EppSvcsubParam</code> object.
	 * The caller of this method must make sure that the root node is of
	 * the EPP Svcsub Param type.
	 *
	 * @param root root node for an <code>EppSvcsubParam</code> object
	 *             in XML format
	 *
	 * @return an <code>EppSvcsubParam</code> object, or null if the node
	 *         is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		String name = ((Element) root).getAttribute("name");
		String value = EppUtil.getText(root);

		return new EppSvcsubParam(name, value);
	}

	public String toString()
	{
		return toString("param");
	}
}
