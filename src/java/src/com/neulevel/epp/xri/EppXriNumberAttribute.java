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
 * This <code>EppXriNumberAttribute</code> class defines i-numbers
 * information associated with XRI authority objects.  It
 * implements XRI inumberType defined
 * in the XRI authority schema file.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.3 $ $Date: 2008/04/24 21:31:59 $
 */
public class EppXriNumberAttribute extends EppEntity
{
	private String  inumber;

	/**
	 * Creates an <code>EppXriNumberAttribute</code> object
	 */
	public EppXriNumberAttribute()
	{
		this(null);
	}

	/**
	 * Creates an <code>EppXriNumberAttribute</code> object with an identifier
	 */
	public EppXriNumberAttribute( String inumber )
	{
		this.inumber  = inumber;
	}

	/**
	 * Gets the i-number
	 */
	public String getINumber()
	{
		return this.inumber;
	}

	/**
	 * Sets the i-number
	 */
	public void setINumber( String inumber )
	{
		this.inumber = inumber;
	}

	/**
         * Converts the <code>EppXriNumberAttribute</code> object into an XML element
         *
         * @param doc the XML <code>Document</code> object
         * @param tag the tag/element name for the <code>EppXriNumberAttribute</code> object
         *
         * @return an <code>Element</code> object
         */
	public Element toXML( Document doc, String tag )
	{
		Element body = doc.createElement(tag);
		body.appendChild(doc.createTextNode(this.inumber));
		return body;
	}

	/**
	 * Converts an XML element into an <code>EppXriNumberAttribute</code> object.
	 * The caller of this method must make sure that the root node is of
	 * the EPP XRI xriAU:inumberType.
	 *
	 * @param root root node for an <code>EppXriNumberAttribute</code> object in
	 *             XML format
	 *
	 * @return an <code>EppXriNumberAttribute</code> object, or null if the node is
	 *         invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		EppXriNumberAttribute xin = new EppXriNumberAttribute();
		xin.setINumber(EppUtil.getText(root));
		return xin;
	}

	public String toString()
	{
		return toString("inumber");
	}
}
