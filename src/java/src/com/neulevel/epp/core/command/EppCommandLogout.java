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
package com.neulevel.epp.core.command;

import org.w3c.dom.*;
import com.neulevel.epp.core.*;

/**
 * This <code>EppCommandLogout</code> class implements EPP Command Logout
 * entity.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppCommandLogout extends EppCommand
{
	/**
	 * Creates an <code>EppCommandLogout</code> object
	 */
	public EppCommandLogout()
	{
	}

	/**
	 * Creates an <code>EppCommandLogout</code> object
	 *
	 * @param clientTransactionId client transaction id associated with
	 *                            the command
	 */
	public EppCommandLogout( String clientTransactionId )
	{
		this.setClientTransactionId(clientTransactionId);
	}

	/**
	 * Converts the <code>EppCommandLogout</code> object into an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the <code>EppCommandLogout</code>
	 *            object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		return toXMLCommon(doc, tag, (EppEntity) null);
	}

	/**
	 * Converts an XML element into an <code>EppCommandLogout</code> object.
	 * The caller of this method must make sure that the root node is of an
	 * EPP Command Create entity.
	 *
	 * @param root root node for an <code>EppCommandLogout</code> object
	 *             in XML format
	 *
	 * @return an <code>EppCommandLogout</code> object, or null if the node
	 *         is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		return new EppCommandLogout();
	}

	public String toString()
	{
		return toString("logout");
	}
}
