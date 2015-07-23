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
package com.neulevel.epp.xri.command;

import java.util.*;
import org.w3c.dom.*;
import com.neulevel.epp.core.*;
import com.neulevel.epp.core.command.*;

/**
 * This <code>EppCommandCheckXriNumber</code> class implements EPP Command Check
 * entity for EPP XRI I-Number objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppCommandCheckXriNumber extends EppCommandCheck
{
	private Vector inumbers;

	/**
	 * Creates an <code>EppCommandCheckXriNumber</code> object
	 */
	public EppCommandCheckXriNumber()
	{
		this(null);
	}

	/**
	 * Creates an <code>EppCommandCheckXriNumber</code> object, given a
	 * client transaction id associated with the operation
	 */
	public EppCommandCheckXriNumber( String xid )
	{
		this.inumbers = new Vector();
		this.clTRID = xid;
	}

	/**
	 * Gets the list of the i-numbers of the XRI i-number objects to be checked
	 */
	public Vector getINumber()
	{
		return this.inumbers;
	}

	/**
	 * Gets the list of the i-numbers of the XRI i-number objects to be checked.
	 *
	 * <P><B>Note:</B> This is an alias for <code>getINumber</code>
	 */
	public Vector get()
	{
		return this.getINumber();
	}

	/**
	 * Adds the i-number of an XRI i-number object to the list of i-numbers of XRI i-number
	 * objects be checked
	 */
	public void addINumber( String inumber )
	{
		this.inumbers.addElement(inumber);
	}

	/**
	 * Adds the i-number of an XRI i-number object to the list of i-numbers of XRI i-number
	 * objects be checked.
	 *
	 * <P><B>Note:</B> This is an alias for <code>addINumber</code>
	 */
	public void add( String inumber )
	{
		this.addINumber(inumber);
	}

	/**
	 * Converts the <code>EppCommandCheckXriNumber</code> object into 
	 * an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the
	 *            <code>EppCommandCheckXriNumber</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Element elm;
		Element body = EppUtil.createElementNS(doc, "xriINU", tag);

		if( inumbers != null )
		{
			for( int i = 0; i < inumbers.size(); i++ )
			{
				String id = (String) inumbers.get(i);
				elm = doc.createElement("inumber");
				elm.appendChild(doc.createTextNode(id));
				body.appendChild(elm);
			}
		}

		return toXMLCommon(doc, tag, body);
	}

	/**
	 * Converts an XML element into an <code>EppCommandCheckXriNumber</code>
	 * object. The caller of this method must make sure that the root node
	 * is of an EPP Command Check entity for EPP XriNumber objects.
	 *
	 * @param root root node for an <code>EppCommandCheckXriNumber</code>
	 *             object in XML format
	 *
	 * @return an <code>EppCommandCheckXriNumber</code> object, or null
	 *         if the node is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		EppCommandCheckXriNumber cmd = new EppCommandCheckXriNumber();
		NodeList list = root.getChildNodes();
		for( int i = 0; i < list.getLength(); i++ )
		{
			Node node = list.item(i);
			String name = node.getLocalName();
			if( name == null )
			{
				continue;
			}
			if( name.equals("inumber") )
			{
				String inumber = EppUtil.getText(node);
				cmd.addINumber(inumber);
			}
		}

		return cmd;
	}
}
