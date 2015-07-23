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
 * entity for EPP XRI Authority objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppCommandDeleteXriAuthority extends EppCommandDelete
{
	private String authId;
	private EppAuthInfo authInfo;

	/**
	 * Creates an <code>EppCommandDeleteXriAuthority</code> object for
	 * deleting an XRI authority based on its identifier
	 */
	public EppCommandDeleteXriAuthority( String authId )
	{
		this.authId = authId;
		this.authInfo = null;
	}

	/**
	 * Creates an <code>EppCommandDeleteXriAuthority</code> object for
	 * deleting an XRI authority based on its identifier, given a client
	 * transaction id associated with the operation
	 */
	public EppCommandDeleteXriAuthority( String authId, String xid )
	{
		this.authId = authId;
		this.authInfo = null;
		this.clTRID = xid;
	}

	/**
	 * Gets the identifier of the XRI authority object to be deleted
	 */
	public String getAuthorityId()
	{
		return this.authId;
	}

	/**
	 * Sets the identifier of the XRI authority object to be deleted
	 */
	public void setAuthorityId( String authId )
	{
		this.authId = authId;
	}

	/**
	 * Gets the authorization info for the delete operation
	 */
	public EppAuthInfo getAuthInfo()
	{
		return this.authInfo;
	}

	/**
	 * Sets the authorization info for the delete operation
	 */
	public void setAuthInfo( EppAuthInfo authInfo )
	{
		this.authInfo = authInfo;
	}

	/**
	 * Converts the <code>EppCommandDeleteXriAuthority</code> object into an XML
	 * element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the
	 *            <code>EppCommandDeleteXriAuthority</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Element elm;
		Element body = EppUtil.createElementNS(doc, "xriAU", tag);

		if( authId != null )
		{
			elm = doc.createElement("authId");
			elm.appendChild(doc.createTextNode(authId));
			body.appendChild(elm);
		}
		if( authInfo != null )
		{
			body.appendChild(authInfo.toXML(doc, "authInfo"));
		}

		return toXMLCommon(doc, tag, body);
	}

	/**
	 * Converts an XML element into an <code>EppCommandDeleteXriAuthority</code>
	 * object. The caller of this method must make sure that the root node
	 * is of an EPP Command Delete entity for EPP XriAuthority object.
	 *
	 * @param root root node for an <code>EppCommandDeleteXriAuthority</code>
	 *             object in XML format
	 *
	 * @return an <code>EppCommandDeleteXriAuthority</code> object, or null
	 *         if the node is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		EppCommandDeleteXriAuthority cmd = null;
		EppAuthInfo authInfo = null;
		NodeList list = root.getChildNodes();
		for( int i = 0; i < list.getLength(); i++ )
		{
			Node node = list.item(i);
			String name = node.getLocalName();
			if( name == null )
			{
				continue;
			}
			if( name.equals("authId") )
			{
				String authId = EppUtil.getText(node);
				if( cmd == null )
				{
					cmd = new EppCommandDeleteXriAuthority(authId);
				}
				else
				{
					cmd.setAuthorityId(authId);
				}
			}
			else if( name.equals("authInfo") )
			{
				authInfo = (EppAuthInfo) EppAuthInfo.fromXML(node);
			}
		}
		if( cmd != null )
		{
			cmd.setAuthInfo(authInfo);
		}

		return cmd;
	}
}
