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
 * This <code>EppCommandTransferXriAuthority</code> class implements EPP Command
 * Transfer entity for EPP XRI Authority objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppCommandTransferXriAuthority extends EppCommandTransfer
{
	private String      authId;
	private String      trToken;
	private String      targetAuthId;
	private EppAuthInfo targetAuthInfo;

	/**
	 * Creates an <code>EppCommandTransferXriAuthority</code> object for
	 * transfering an XRI authority object based on its identifier
	 */
	public EppCommandTransferXriAuthority( String authId )
	{
		this(authId, null, null);
	}

	/**
	 * Creates an <code>EppCommandTransferXriAuthority</code> object for
	 * transfering an XRI authority object based on its identifier
	 * given a client transaction id associated with operation
	 */
	public EppCommandTransferXriAuthority( String authId, String xid )
	{
		this(authId, null, xid);
	}

	/**
	 * Creates an <code>EppCommandTransferXriAuthority</code> object for
	 * transfering an XRI authority object based on its identifier,
	 * a client transaction id associated with operation
	 * and with a transfer token
	 */
	public EppCommandTransferXriAuthority( String authId, String trToken, String xid )
	{
		this.authId  = authId;
		this.trToken = trToken;
		this.clTRID  = xid;
	}

	/**
	 * Gets the identifier of the XRI authority object to be transferred
	 */
	public String getAuthorityId()
	{
		return this.authId;
	}

	/**
	 * Sets the identifier of the XRI authority object to be transferred
	 */
	public void setAuthorityId( String authId )
	{
		this.authId = authId;
	}

	/**
	 * Gets the transfer token for the transfer operation
	 */
	public String getTransferToken()
	{
		return this.trToken;
	}

	/**
	 * Sets the transfer token for the transfer operation
	 */
	public void setTransferToken( String trToken )
	{
		this.trToken = trToken;
	}

	/**
	 * Gets the target XRI authority identifier
	 */
	public String getTargetAuthorityId()
	{
		return this.targetAuthId;
	}

	/**
	 * Sets the target XRI authority identifier
	 */
	public void setTargetAuthorityId( String authId )
	{
		this.targetAuthId = authId;
	}

	/**
	 * Sets the target XRI authority authInfo
	 */
	public EppAuthInfo getTargetAuthInfo()
	{
		return this.targetAuthInfo;
	}

	/**
	 * Gets the target XRI authority authInfo
	 */
	public void setTargetAuthInfo( EppAuthInfo authInfo )
	{
		this.targetAuthInfo = authInfo;
	}

	/**
	 * Sets the identifier and authInfo of the target XRI authority involved in 
	 * XRI authority transfer operation
	 */
	public void setTarget( String authId, EppAuthInfo authInfo )
	{
		this.targetAuthId   = authId;
		this.targetAuthInfo = authInfo;
	}

	/**
	 * Converts the <code>EppCommandTransferXriAuthority</code> object into
	 * an XML element for an <code>EppPollable</code> object
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the
	 *            <code>EppCommandTransferXriAuthority</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXMLPoll( Document doc, String tag )
	{
		Element elm;
		Element child;
		Element body = EppUtil.createElementNS(doc, "xriAU", tag);

		if( authId != null )
		{
			elm = doc.createElement("authId");
			elm.appendChild(doc.createTextNode(authId));
			body.appendChild(elm);
		}
		if( targetAuthId != null )
		{
			elm = doc.createElement("target");
			child = doc.createElement("authId");
			child.appendChild(doc.createTextNode(targetAuthId));
			elm.appendChild(child);
			if( targetAuthInfo != null )
			{
				elm.appendChild(targetAuthInfo.toXML(doc, "authInfo"));
			}
			body.appendChild(elm);
		}
		if( trToken != null )
		{
			elm = doc.createElement("trToken");
			elm.appendChild(doc.createTextNode(trToken));
			body.appendChild(elm);
		}
		if( authInfo != null )
		{
			body.appendChild(authInfo.toXML(doc, "authInfo"));
		}

		return body;
	}

	/**
	 * Converts the <code>EppCommandTransferXriAuthority</code> object into
	 * an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the
	 *            <code>EppCommandTransferXriAuthority</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Element body = toXMLPoll(doc, tag);

		Attr attr = doc.createAttribute("op");
		attr.setValue(this.getOperation());
		Vector attrList = new Vector();
		attrList.addElement(attr);

		return toXMLCommon(doc, tag, body, attrList);
	}

	/**
	 * Converts an XML element into an
	 * <code>EppCommandTransferXriAuthority</code> object. The caller of this
	 * method must make sure that the root node is of an EPP Command
	 * Transfer entity for EPP XriAuthority object.
	 *
	 * @param root root node for an <code>EppCommandTransferXriAuthority</code>
	 *             object in XML format
	 *
	 * @return an <code>EppCommandTransferXriAuthority</code> object, or null
	 *         if the node is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		EppCommandTransferXriAuthority cmd = null;
		String      trToken        = null;
		String      targetAuthId   = null;
		EppAuthInfo targetAuthInfo = null;
		EppAuthInfo authInfo       = null;

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
					cmd = new EppCommandTransferXriAuthority(authId);
				}
				else
				{
					cmd.setAuthorityId(authId);
				}
			}
			else if( name.equals("target") )
			{
				NodeList clist = node.getChildNodes();
				for( int j = 0; j < clist.getLength(); j++ )
				{
					Node cnode = clist.item(j);
					String cname = cnode.getLocalName();
					if( cname == null )
					{
						continue;
					}
					if( cname.equals("authId") )
					{
						targetAuthId = EppUtil.getText(cnode);
					}
					else if( cname.equals("authInfo") )
					{
						targetAuthInfo = (EppAuthInfo) EppAuthInfo.fromXML(cnode);
					}
				}
			}
			else if( name.equals("trToken") )
			{
				trToken = EppUtil.getText(node);
			}
			else if( name.equals("authInfo") )
			{
				authInfo = (EppAuthInfo) EppAuthInfo.fromXML(node);
			}
		}
		if( cmd != null )
		{
			cmd.setTarget(targetAuthId, targetAuthInfo);
			cmd.setTransferToken(trToken);
			cmd.setAuthInfo(authInfo);
		}

		return cmd;
	}
}
