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

import org.w3c.dom.*;
import com.neulevel.epp.core.*;
import com.neulevel.epp.core.command.*;

/**
 * This <code>EppCommandInfo</code> class implements EPP Command Info
 * entity for EPP XRI I-Number objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppCommandInfoXriNumber extends EppCommandInfo
{
	private String inumber;

	/**
	 * Creates an <code>EppCommandInfoXriNumber</code> object for
	 * querying an XRI i-number based on its i-number
	 */
	public EppCommandInfoXriNumber( String inumber )
	{
		this.inumber = inumber;
	}

	/**
	 * Creates an <code>EppCommandInfoXriNumber</code> object for
	 * querying an XRI i-number based on its i-number, given a client
	 * transaction id associated with the operation
	 */
	public EppCommandInfoXriNumber( String inumber, String xid )
	{
		this.inumber = inumber;
		this.clTRID = xid;
	}

	/**
	 * Gets the i-number of the XRI i-number object to be queried
	 */
	public String getINumber()
	{
		return this.inumber;
	}

	/**
	 * Sets the i-number of the XRI i-number object to be queried
	 */
	public void setINumber( String inumber )
	{
		this.inumber = inumber;
	}

	/**
	 * Converts the <code>EppCommandInfoXriNumber</code> object into an XML
	 * element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the
	 *            <code>EppCommandInfoXriNumber</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Element elm;
		Element body = EppUtil.createElementNS(doc, "xriINU", tag);

		if( inumber != null )
		{
			elm = doc.createElement("inumber");
			elm.appendChild(doc.createTextNode(inumber));
			body.appendChild(elm);
		}

		return toXMLCommon(doc, tag, body);
	}

	/**
	 * Converts an XML element into an <code>EppCommandInfoXriNumber</code>
	 * object. The caller of this method must make sure that the root node
	 * is of an EPP Command Info entity for EPP XriNumber object.
	 *
	 * @param root root node for an <code>EppCommandInfoXriNumber</code>
	 *             object in XML format
	 *
	 * @return an <code>EppCommandInfoXriNumber</code> object, or null
	 *         if the node is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		EppCommandInfoXriNumber cmd = null;
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
				if( cmd == null )
				{
					cmd = new EppCommandInfoXriNumber(inumber);
				}
				else
				{
					cmd.setINumber(inumber);
				}
			}
		}

		return cmd;
	}
}
