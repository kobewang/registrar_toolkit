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

/**
 * This <code>EppUnspec</code> class implements EPP unspecType objects defined in EPP-04.
 * In EPP-1.0, it becomes a special class for handling NeuLevel's EPP extension with full
 * name space specification required in the XML representation. 
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.3 $ $Date: 2010/06/11 21:06:25 $
 */
public class EppUnspec extends EppEntity
{
	private String value;
	private EppGenericNVPairs nvPairs;

	/**
	 * Creates an <code>EppUnspec</code> object
	 */
	public EppUnspec()
	{
		this(null);
		this.nvPairs = new EppGenericNVPairs();
	}

	/**
	 * Creates an <code>EppUnspec</code> object with a value
	 */
	public EppUnspec( String value )
	{
		this.value = value;
		this.nvPairs = new EppGenericNVPairs();
	}
	
	/**
	 * Creates an <code>EppUnspec</code> object with a value and NVPairs
	 */
	public EppUnspec( String value , EppGenericNVPairs p_nvPairs)
	{
		this.value = value;
		if(p_nvPairs != null)
		{
			this.nvPairs = new EppGenericNVPairs(p_nvPairs);
		}
		else
		{
			this.nvPairs = new EppGenericNVPairs();
		}
	}


	/**
	 * Gets the value associated with <code>EppUnspec</code> object
	 */
	public String getValue()
	{
		return this.value;
	}

	/**
	 * Sets the value associated with <code>EppUnspec</code> object
	 */
	public void setValue( String value )
	{
		this.value = value;
	}

	/**
	 * Converts an XML element into an <code>EppUnspec</code> object.
	 * The caller of this method must make sure that the root node is of
	 * EPP unspecType
	 *
	 * @param root root node for an <code>EppUnspec</code> object in XML format
	 *
	 * @return an <code>EppUnspec</code> object, or null if the node is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		String value = null;
		EppGenericNVPairs retNVPairs = null;
		NodeList list = root.getChildNodes();
		for( int i = 0; i < list.getLength(); i++ )
		{
			Node node = list.item(i);
			String name = node.getLocalName();
			if( name == null )
			{
				continue;
			}
			if( name.equals("unspec") )
			{
				value = EppUtil.getText(node);
			}
			else if (name.equals("genericNVPairs") || name.equals("neulevel:genericNVPairs"))
			{
				retNVPairs = EppGenericNVPairs.fromXML(node);
			}
		}

		if( (value == null) || (value.trim().length() == 0) )
		{
			value = null;
		}
		return new EppUnspec(value,retNVPairs);
	}

	/**
	 * Converts the <code>EppUnspec</code> object into an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the <code>EppUnspec</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Element body = EppUtil.createElementNS(doc, "neulevel", tag, true);
		Element elm = doc.createElement("unspec");
		if( value != null )
		{
			elm.appendChild(doc.createTextNode(value));
		}
		
		body.appendChild(elm);
		if (( nvPairs != null ) && (nvPairs.getGenericNVPairs() != null ) && (nvPairs.getGenericNVPairs().size() > 0))
		{
			body.appendChild(nvPairs.toXML(doc,"genericNVPairs"));
		}		
		
		
		return body;
	}

	public String toString()
	{
		return toString("unspec");
	}
	public EppGenericNVPairs getNVPairs()
	{
			return this.nvPairs;
	}
	public void setGenericNVPairs(EppGenericNVPairs p_nvPairs)
	{
		this.nvPairs = new EppGenericNVPairs(p_nvPairs);
	}
	
}
