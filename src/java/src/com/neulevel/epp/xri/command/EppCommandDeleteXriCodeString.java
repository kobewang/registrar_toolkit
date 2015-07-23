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
 * This <code>EppCommandDelete</code> class implements EPP Command Delete
 * entity for EPP XRI CodeString objects.
 *
 * @author Animesh Chowdhury animesh.chowdhury@neustar.biz
 * @version $Revision: 1.1 $ $Date: 2010/01/05 20:27:15 $
 */
public class EppCommandDeleteXriCodeString extends EppCommandDelete
{
	private String codeString;

	/**
	 * Creates an <code>EppCommandDeleteXriCodeString</code> object for
	 * deleting an XRI CodeString based on its CodeString
	 */
	public EppCommandDeleteXriCodeString( String cs )
	{
		this.codeString = cs;
	}

	/**
	 * Creates an <code>EppCommandDeleteXriCodeString</code> object for
	 * deleting an XRI i-name based on its i-name, given a client
	 * transaction id associated with the operation
	 */
	public EppCommandDeleteXriCodeString( String cs, String xid )
	{
		this.codeString = cs;
		this.clTRID = xid;
	}

	/**
	 * Gets the codeString of the XRI codeString object to be deleted
	 */
	public String getCodeString()
	{
		return this.codeString;
	}

	/**
	 * Sets the codeString  of the XRI codeString object to be deleted
	 */
	public void setCodeString( String cs )
	{
		this.codeString = cs;
	}

	/**
	 * Converts the <code>EppCommandDeleteXriCodeString</code> object into an XML
	 * element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the
	 *            <code>EppCommandDeleteXriCodeString</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Element elm;
		Element body = EppUtil.createElementNS(doc, "xriCS", tag);

		if( codeString != null )
		{
			elm = doc.createElement("codeString");
			elm.appendChild(doc.createTextNode(codeString));
			body.appendChild(elm);
		}

		return toXMLCommon(doc, tag, body);
	}

	/**
	 * Converts an XML element into an <code>EppCommandDeleteXriCodeString</code>
	 * object. The caller of this method must make sure that the root node
	 * is of an EPP Command Delete entity for EPP XriName object.
	 *
	 * @param root root node for an <code>EppCommandDeleteXriCodeString</code>
	 *             object in XML format
	 *
	 * @return an <code>EppCommandDeleteXriCodeString</code> object, or null
	 *         if the node is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		EppCommandDeleteXriCodeString cmd = null;
		NodeList list = root.getChildNodes();
		for( int i = 0; i < list.getLength(); i++ )
		{
			Node node = list.item(i);
			String name = node.getLocalName();
			if( name == null )
			{
				continue;
			}
			if( name.equals("codeString") || name.equals("xriCS:codeString") )
			{
				String cs = EppUtil.getText(node);
				if( cmd == null )
				{
					cmd = new EppCommandDeleteXriCodeString(cs);
				}
				else
				{
					cmd.setCodeString(cs);
				}
			}
		}

		return cmd;
	}
}
