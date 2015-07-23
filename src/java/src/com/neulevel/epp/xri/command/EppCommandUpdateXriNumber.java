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
import com.neulevel.epp.xri.*;

/**
 * This <code>EppCommandUpdateXriNumber</code> class implements EPP Command Update
 * entity for EPP XRI I-Number objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppCommandUpdateXriNumber extends EppCommandUpdate
{
	private String inumber;
	private int    newPriority;

	/**
	 * Creates an <code>EppCommandUpdateXriNumber</code> given the
	 * i-number of the XRI i-number object
	 */
	public EppCommandUpdateXriNumber( String inumber )
	{
		this(inumber, null);
	}

	/**
	 * Creates an <code>EppCommandUpdateXriNumber</code> given the
	 * i-number of the XRI i-number object and a client transaction id
	 */
	public EppCommandUpdateXriNumber( String inumber, String xid )
	{
		this.inumber = inumber;
		this.clTRID = xid;
		this.newPriority = -1;
	}

	/**
	 * Gets the i-number of the XRI i-number object to be updated
	 */
	public String getINumber()
	{
		return this.inumber;
	}

	/**
	 * Sets the i-number of the XRI i-number object to be updated
	 */
	public void setINumber( String inumber )
	{
		this.inumber = inumber;
	}

	/**
	 * Gets the new priority value for this i-number
	 */
	public int getNewPriority()
	{
		return this.newPriority;
	}

	/**
	 * Sets the priority value for this i-number
	 */
	public void setNewPriority( int newPriority )
	{
		this.newPriority = newPriority;
	}

	/**
	 * Converts the <code>EppCommandUpdateXriNumber</code> object into 
	 * an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the 
	 *            <code>EppCommandUpdateXriNumber</code> object
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
		if( statusAdded.size() > 0 )
		{
			elm = doc.createElement("add");
			statusToXML (doc, elm, statusAdded);
			body.appendChild(elm);
		}
		if( statusRemoved.size() > 0 )
		{
			elm = doc.createElement("rem");
			statusToXML (doc, elm, statusRemoved);
			body.appendChild(elm);
		}
		if( newPriority >= 0 )
		{
			elm = doc.createElement("chg");
			Element p = doc.createElement("priority");
			p.appendChild(doc.createTextNode("" + newPriority));
			elm.appendChild(p);
			body.appendChild(elm);
		}

		return toXMLCommon(doc, tag, body);
	}

	/**
	 * Converts an XML element into an <code>EppCommandUpdateXriNumber</code>
	 * object. The caller of this method must make sure that the root node
	 * is of an EPP Command Update entity for an EPP XRI I-Number object.
	 *
	 * @param root root node for an <code>EppCommandUpdateXriNumber</code>
	 *             object in XML format
	 *
	 * @return an <code>EppCommandUpdateXriNumber</code> object, or null
	 *         if the node is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		EppCommandUpdateXriNumber cmd = null;
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
					cmd = new EppCommandUpdateXriNumber(inumber);
				}
				else
				{
					cmd.setINumber(inumber);
				}
			}
			else if( name.equals("add") )
			{
				if( cmd != null )
				{
					cmd.statusFromXML (node, cmd.statusAdded);
				}
			}
			else if( name.equals("rem") )
			{
				if( cmd != null )
				{
					cmd.statusFromXML (node, cmd.statusRemoved);
				}
			}
			else if( name.equals("chg") )
			{
				if( cmd != null )
				{
					cmd.addNewStuff(node);
				}
			}
		}

		return cmd;
	}

	/**
	 * Adds elements included in the &lt;chg&gt tag of the update command
	 * for an XRI i-number object
	 *
	 * @param root the root <code>Node</code> of the &lt;chg&gt; tag
	 */
	private void addNewStuff( Node root )
	{
		NodeList list = root.getChildNodes();
		for( int i = 0; i < list.getLength(); i++ )
		{
			Node node = list.item(i);
			String name = node.getLocalName();
			if( name == null )
			{
				continue;
			}
			if( name.equals("priority") )
			{
				String flag = EppUtil.getText(node);
				if( flag != null )
				{
					try
					{
						int priority = Integer.parseInt(flag);
						setNewPriority(priority);
					}
					catch( NumberFormatException e )
					{
					}
				}
			}
		}
	}
}
