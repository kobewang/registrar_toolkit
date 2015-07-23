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
 * This <code>EppPeriod</code> class implements EPP Domain periodType entity.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppPeriod extends EppEntity
{
	/**
	 * Period unit for year ('y')
	 */
	public static final char UNIT_YEAR = 'y';
	/**
	 * Period unit for month ('m')
	 */
	public static final char UNIT_MONTH = 'm';

	private int  value;
	private char unit;

	public EppPeriod( int value, char unit )
	{
		this.value = value;
		this.unit  = unit;
	}

	/**
	 * Gets the value of the period
	 */
	public int getValue()
	{
		return this.value;
	}

	/**
	 * Sets the value of the period
	 */
	public void setValue( int value )
	{
		this.value = value;
	}

	/**
	 * Gets the unit of the period
	 */
	public char getUnit()
	{
		return this.unit;
	}

	/**
	 * Sets the unit of the period, either 'y' for year or 'm' for month
	 */
	public void setUnit( char unit )
	{
		this.unit = unit;
	}

	/**
	 * Converts the <code>EppPeriod</code> object into an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the <code>EppPeriod</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag)
	{
		Element body = doc.createElement(tag);
		body.setAttribute("unit", "" + unit);
		body.appendChild(doc.createTextNode(Integer.toString(value)));

		return body;
	}

	/**
	 * Converts an XML element into an <code>EppPeriod</code> object.
	 * The caller of this method must make sure that the root node is of
	 * the EPP Period type.
	 *
	 * @param root root node for an <code>EppPeriod</code> object
	 *             in XML format
	 *
	 * @return an <code>EppPeriod</code> object, or null if the node
	 *         is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		String value = EppUtil.getText(root);
		String unit = ((Element) root).getAttribute("unit");

		if(    (value != null) && (value.length() > 0)
		    && (unit  != null) && (unit.length()  > 0) )
		{
			return new EppPeriod(Integer.parseInt(value), unit.charAt(0));
		}

		return null;
	}

	public String toString()
	{
		return toString("period");
	}
}
