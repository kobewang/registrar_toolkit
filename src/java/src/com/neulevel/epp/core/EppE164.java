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
 * This <code>EppE164</code> class implements EPP e164Type, which is
 * the ITU E.164 format for telephone numbers, plus extension.
 *
 * <P>An E.164 telephone number consists of 4 parts, with a maximum length
 * of 17 characters:
 * <UL>
 * <LI>A leading plus ("+") sign,</LI>
 * <LI>A up to 3-dight country code,</LI>
 * <LI>A period (".") sign</LI>
 * <LI>A up to 12-dight phoner number (for EPP-04, it is a 10-digit, and is extended by NeuLevel to 12)</LI>
 * </UL>
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppE164 extends EppEntity
{
	private String number;
	private String extension;

	/**
	 * Creates an <code>EppE164</code> object without an extension
	 */
	public EppE164( String number )
	{
		this(number, null);
	}

	/**
	 * Creates an <code>EppE164</code> object with an extension
	 */
	public EppE164( String number, String extension )
	{
		this.number    = number;
		this.extension = extension;
	}

	/**
	 * Gets the phone number
	 */
	public String getNumber()
	{
		return this.number;
	}

	/**
	 * Sets the phone number
	 */
	public void setNumber( String number )
	{
		this.number = number;
	}

	/**
	 * Gets the extension of the phone number, if any
	 */
	public String getExtension()
	{
		return this.extension;
	}

	/**
	 * Sets the extension of the phone number
	 */
	public void setExtension( String extension )
	{
		this.extension = extension;
	}

	/**
	 * Converts the <code>EppE164</code> object into an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the <code>EppE164</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag)
	{
		Element body = doc.createElement(tag);
		if( (extension != null) && (extension.length() > 0) )
		{
			body.setAttribute("x", extension);
		}
		if( number != null )
		{
			body.appendChild(doc.createTextNode(number));
		}

		return body;
	}

	/**
	 * Converts an XML element into an <code>EppE164</code> object.
	 * The caller of this method must make sure that the root node is of
	 * the EPP E164 type.
	 *
	 * @param root root node for an <code>EppE164</code> object
	 *             in XML format
	 *
	 * @return an <code>EppE164</code> object, or null if the node
	 *         is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		String x = ((Element) root).getAttribute("x");
		String s = EppUtil.getText(root);
		if( (x != null) && (x.length() == 0) )
		{
			x = null;
		}

		return new EppE164(s, x);
	}

	public String toString()
	{
	   return toString("e164");
	}
}
