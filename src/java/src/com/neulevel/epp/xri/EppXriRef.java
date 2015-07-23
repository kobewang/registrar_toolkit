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
 * This <code>EppXriRef</code> class defines Ref 
 * information associated with XRI authority objects.  It
 * implements XRI refAddType and refInfType defined
 * in the XRI authority schema file.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.4 $ $Date: 2008/04/17 04:28:05 $
 */
public class EppXriRef extends EppEntity
{
	private int     priority;
	private String  ref;

	/**
	 * Creates an <code>EppXriRef</code> object
	 */
	public EppXriRef()
	{
		this(null);
	}

	/**
	 * Creates an <code>EppXriRef</code> object with a Ref
	 */
	public EppXriRef( String ref )
	{
		this(ref, -1);
	}

	/**
	 * Creates an <code>EppXriRef</code> object with a Ref
	 * and a priority value
	 */
	public EppXriRef( String ref, int priority )
	{
		this.ref      = ref;
		setPriority(priority);
	}

	/**
	 * Gets the priority value for this Ref
	 */
	public int getPriority()
	{
		return this.priority;
	}

	/**
	 * Sets the priority value for this Ref
	 */
	public void setPriority( int priority )
	{
		if (priority < -1)
			priority = -1;
		this.priority = priority;
	}

	/**
	 * Gets the XRI string of the Ref
	 */
	public String getRef()
	{
		return this.ref;
	}

	/**
	 * Sets the XRI string of the Ref
	 */
	public void setRef( String ref )
	{
		this.ref = ref;
	}

	/**
         * Converts the <code>EppXriRef</code> object into an XML element
         *
         * @param doc the XML <code>Document</code> object
         * @param tag the tag/element name for the <code>EppXriRef</code> object
         *
         * @return an <code>Element</code> object
         */
	public Element toXML( Document doc, String tag )
	{
		Element body = doc.createElement(tag);

		body.appendChild(doc.createTextNode(this.ref));
		if (this.priority >= 0)
			body.setAttribute("priority", "" + this.priority);

		return body;
	}

	/**
	 * Converts an XML element into an <code>EppXriRef</code> object.
	 * The caller of this method must make sure that the root node is of
	 * the EPP XRI refAddType or refInfType.
	 *
	 * @param root root node for an <code>EppXriRef</code> object in
	 *             XML format
	 *
	 * @return an <code>EppXriRef</code> object, or null if the node is
	 *         invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		EppXriRef ref     = new EppXriRef();
		String   flag     = null;

		ref.setRef(EppUtil.getText(root));

		flag = ((Element) root).getAttribute("priority");
		if( flag != null )
		{
			try
			{
				int priority = Integer.parseInt(flag);
				ref.setPriority(priority);
			}
			catch( NumberFormatException e )
			{
			}
		}
		return ref;
	}

	public String toString()
	{
		return toString("ref");
	}
}
