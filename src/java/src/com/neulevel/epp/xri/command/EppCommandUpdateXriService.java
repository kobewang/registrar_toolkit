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
 * This <code>EppCommandUpdateXriService</code> class implements EPP Command Update
 * entity for EPP XRI I-Service objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppCommandUpdateXriService extends EppCommandUpdate
{
	private String id;

	/**
	 * Creates an <code>EppCommandUpdateXriService</code> given the
	 * identifier of the XRI i-service object
	 */
	public EppCommandUpdateXriService( String id )
	{
		this(id, null);
	}

	/**
	 * Creates an <code>EppCommandUpdateXriService</code> given the
	 * identifiere of the XRI i-service object and a client transaction id
	 */
	public EppCommandUpdateXriService( String id, String xid )
	{
		this.id = id;
		this.clTRID = xid;
	}

	/**
	 * Gets the identifer  of the XRI i-service object to be updated
	 */
	public String getId()
	{
		return this.id;
	}

	/**
	 * Sets the identifer  of the XRI i-service object to be updated
	 */
	public void setId( String id )
	{
		this.id = id;
	}

	/**
	 * Converts the <code>EppCommandUpdateXriService</code> object into 
	 * an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the 
	 *            <code>EppCommandUpdateXriService</code> object
	 *
	 * @return an <code>Element</code> object 
	 */
	public Element toXML( Document doc, String tag )
	{
		Element elm;
		Element body = EppUtil.createElementNS(doc, "xriISV", tag);

		if( id != null )
		{
			elm = doc.createElement("id");
			elm.appendChild(doc.createTextNode(id));
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

		return toXMLCommon(doc, tag, body);
	}

	/**
	 * Converts an XML element into an <code>EppCommandUpdateXriService</code>
	 * object. The caller of this method must make sure that the root node
	 * is of an EPP Command Update entity for an EPP XRI I-Service object.
	 *
	 * @param root root node for an <code>EppCommandUpdateXriService</code>
	 *             object in XML format
	 *
	 * @return an <code>EppCommandUpdateXriService</code> object, or null
	 *         if the node is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		EppCommandUpdateXriService cmd = null;
		NodeList list = root.getChildNodes();
		for( int i = 0; i < list.getLength(); i++ )
		{
			Node node = list.item(i);
			String name = node.getLocalName();
			if( name == null )
			{
				continue;
			}
			if( name.equals("id") )
			{
				String id = EppUtil.getText(node);
				if( cmd == null )
				{
					cmd = new EppCommandUpdateXriService(id);
				}
				else
				{
					cmd.setId(id);
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
		}

		return cmd;
	}
}
