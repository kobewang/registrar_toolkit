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
import org.apache.xerces.dom.*;

/**
 * The <code>EppEntity</code> class is the base class for all entities/objects
 * defined for EPP. All subclasses of <code>EppEntity</code> need to implement
 * the following three methods:
 * <UL>
 * <LI><code>fromXML</code></LI>
 * <LI><code>toXML</code></LI>
 * <LI><code>toString</code></LI>
 * </UL>
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.3 $ $Date: 2013/06/14 16:43:09 $
 */
abstract public class EppEntity
{
	/**
	 * Converts an XML element into an <code>EppEntity</code> object.
	 * The caller of this method must make sure that the root node is
	 * of an EPP entity.
	 *
	 * @param root root node for an <code>EppEntity</code> object in
	 *             XML format
	 *
	 * @return an <code>EppEntity</code> object, or null if the node
	 *         is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		return null;
	}

	/**
	 * Converts the <code>EppEntity</code> object into an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the <code>EppEntity</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	abstract public Element toXML( Document doc, String tag );

	/**
	 * Converts the <code>EppEntity</code> object into plain XML text string
	 *
	 * @param tag XML tag name for the root node
	 *
	 * @return a text string representing the <code>EppEntity</code> object
	 *         in XML format
	 */
	protected String toString( String tag )
	{
		Document doc = new DocumentImpl();
		Element elm = this.toXML(doc, tag);
		doc.appendChild(elm);
		return EppUtil.toString(doc);
	}
	
	/**
	 * Converts the <code>EppEntity</code> object into plain XML text string
	 *
	 * @param tag XML tag name for the root node
	 *
	 * @return a text string representing the <code>EppEntity</code> object
	 *         in XML format
	 */
	protected String toString( String tag , boolean noFormatting )
	{
		Document doc = new DocumentImpl();
		Element elm = this.toXML(doc, tag);
		doc.appendChild(elm);
		if(noFormatting){
			return EppUtil.toStringNoFormatting(doc,null);
		}
		else {
			return EppUtil.toString(doc);		
		}
	}

	/*
	 * Converts the <code>EppEntity</code> object into plain XML text string
	 * by using the default root tag name
	 *
	 * @return a text string representing the <code>EppEntity</code> object
	 *         in XML format
	 */
	abstract public String toString();
}
