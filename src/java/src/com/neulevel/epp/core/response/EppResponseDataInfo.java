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
package com.neulevel.epp.core.response;

import org.w3c.dom.*;
import com.neulevel.epp.core.*;

/**
 * This <code>EppResponseDataInfo</code> class implements EPP Response
 * Data entity for EPP Command Info.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppResponseDataInfo extends EppResponseData
{
	private EppObject object;

	/**
	 * Creates an <code>EppResponseDataInfo</code> object
	 */
	public EppResponseDataInfo( EppObject object )
	{
		this.object = object;
	}

	/**
	 * Gets the <code>EppObject</code> returned by the EPP info command
	 */
	public EppObject getObject()
	{
		return this.object;
	}

	/**
	 * Sets the <code>EppObject</code> returned by the EPP info command
	 */
	public void setObject( EppObject object )
	{
		this.object = object;
	}

	/**
	 * Converts the <code>EppResponseDataInfo</code> object into an XML
	 * element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the <code>EppResponse</code>
	 *            object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Element elm = object.toXML(doc, "infData"); 
		Element body = doc.createElement(tag);
		body.appendChild(elm);

		return body;
	}

	/**
	 * Converts an XML element into an <code>EppResponseDataInfo</code>
	 * object. The caller of this method must make sure that the root
	 * node is of the EPP infDataType.
	 *
	 * @param root root node for an <code>EppResponseDataInfo</code>
	 *             object in XML format
	 *
	 * @return an <code>EppResponseDataInfo</code> object, or null
	 *         if the node is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		EppObject object = (EppObject) EppObject.fromXML(root);
		if( object != null )
		{
			return new EppResponseDataInfo(object);
		}

		return null;
	}
}
