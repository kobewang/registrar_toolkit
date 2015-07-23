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
 * This <code>EppCommandInfoXriAuthority</code> class implements EPP Command Info
 * entity for EPP XRI Authority objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppCommandInfoXriAuthority extends EppCommandInfo
{
	/**
	 * I-Name/I-Number/I-Service Control - all
	 *
	 * <P>A value of "all" returns information of associated
	 * subordinate i-names, i-numbers and i-service identifiers
	 */
	public static final String CONTROL_ALL = "all";
	/**
	 * I-Name/I-Number/I-Service Control - iname
	 *
	 * <P>A value of "iname" returns information of associated subordinate i-names only
	 */
	public static final String CONTROL_INAME = "iname";
	/**
	 * I-Name/I-Number/I-Service Control - inumber
	 *
	 * <P>A value of "inumber" returns information of associated subordinate i-numbers only
	 */
	public static final String CONTROL_INUMBER = "inumber";
	/**
	 * I-Name/I-Number/I-Service Control - iservice
	 *
	 * <P>A value of "iservice" returns information of associated subordinate i-service identifiers only
	 */
	public static final String CONTROL_ISERVICE = "iservice";
	/**
	 * I-Name/I-Numbre/I-Service Control - none
	 *
	 * <P>A value of "none" (the default, which MAY be absent) returns no information
	 * of associated subordinate i-names, i-numbers, or i-service identifiers
	 */
	public static final String CONTROL_NONE = "none";

	private String      authId;
	private EppAuthInfo authInfo;
	private String      control;

	/**
	 * Creates an <code>EppCommandInfoXriAuthority</code> object for
	 * querying an XRI authority object based on its identifier
	 */
	public EppCommandInfoXriAuthority( String authId )
	{
		this(authId, null);
	}

	/**
	 * Creates an <code>EppCommandInfoXriAuthority</code> object for
	 * querying an XRI authority object based on its identifier, given a client
	 * transaction id associated with the operation
	 */
	public EppCommandInfoXriAuthority( String authId, String xid )
	{
		this.authId   = authId;
		this.clTRID   = xid;
		this.authInfo = null;
		this.control  = CONTROL_NONE;
	}

	/**
	 * Gets the identifier of the XRI authority object to be queried
	 */
	public String getAuthorityId()
	{
		return this.authId;
	}

	/**
	 * Sets the identifier of the XRI authority object to be queried
	 */
	public void setAuthorityId( String authId )
	{
		this.authId = authId;
	}

	/**
	 * Gets the optional authorization info for querying the XRI authority object
	 */
	public EppAuthInfo getAuthInfo()
	{
		return this.authInfo;
	}

	/**
	 * Sets the optional authorization info for querying the XRI authority object
	 */
	public void setAuthInfo( EppAuthInfo authInfo )
	{
		this.authInfo = authInfo;
	}

	/**
	 * Gets the control attribute for querying the XRI authority object
	 */
	public String getControl()
	{
		return this.control;
	}

	/**
	 * Sets the control attribute for querying the XRI authority object,
	 * with one of the following values:
	 *
	 * <UL>
	 * <LI>CONTROL_ALL</LI>
	 * <LI>CONTROL_INAME</LI>
	 * <LI>CONTROL_INUMBER</LI>
	 * <LI>CONTROL_ISERVICE</LI>
	 * <LI>CONTROL_NONE</LI>
	 * </UL>
	 */
	public void setControl( String control )
	{
		this.control = control;
	}

	/**
	 * Converts the <code>EppCommandInfoXriAuthority</code> object into an XML
	 * element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the
	 *            <code>EppCommandInfoXriAuthority</code> object
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
			if( control != null )
			{
				elm.setAttribute("control", control);
			}
			body.appendChild(elm);
		}
		if( authInfo != null )
		{
			body.appendChild(authInfo.toXML(doc, "authInfo"));
		}

		return toXMLCommon(doc, tag, body);
	}

	/**
	 * Converts an XML element into an <code>EppCommandInfoXriAuthority</code>
	 * object. The caller of this method must make sure that the root node
	 * is of an EPP Command Info entity for EPP XRI authority object.
	 *
	 * @param root root node for an <code>EppCommandInfoXriAuthority</code>
	 *             object in XML format
	 *
	 * @return an <code>EppCommandInfoXriAuthority</code> object, or null
	 *         if the node is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		EppCommandInfoXriAuthority cmd = null;
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
				String s = EppUtil.getText(node);
				if( (s != null) && (s.length() > 0) )
				{
					if( cmd == null )
					{
						cmd = new EppCommandInfoXriAuthority(s);
					}
					else
					{
						cmd.setAuthorityId(s);
					}
					s = ((Element) node).getAttribute("control");
					if( (s != null) && (s.length() > 0) )
					{
						cmd.setControl(s);
					}
				}
			}
			else if( name.equals("authInfo") )
			{
				if( cmd != null )
				{
					EppAuthInfo p;
					p = (EppAuthInfo) EppAuthInfo.fromXML(node);
					if( p != null )
					{
						cmd.setAuthInfo(p);
					}
				}
			}
		}

		return cmd;
	}
}
