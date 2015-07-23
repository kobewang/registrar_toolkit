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
 * This <code>EppCommandTransferXriName</code> class implements EPP Command
 * Transfer entity for EPP XRI I-Name objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppCommandTransferXriName extends EppCommandTransfer
{
	private String      iname;
	private String      trToken;
	private String      targetAuthId;
	private EppAuthInfo targetAuthInfo;
	private EppPeriod   period;

	/**
	 * Creates an <code>EppCommandTransferXriName</code> object for
	 * transfering an XRI i-name object based on its i-name
	 */
	public EppCommandTransferXriName( String iname )
	{
		this(iname, null, null, null);
	}

	/**
	 * Creates an <code>EppCommandTransferXriName</code> object for
	 * transfering an XRI i-name object based on its i-name
	 * given a client transaction id associated with operation
	 */
	public EppCommandTransferXriName( String iname, String xid )
	{
		this(iname, null, null, xid);
	}

	/**
	 * Creates an <code>EppCommandTransferXriName</code> object for
	 * transfering an XRI i-name object based on its i-name,
	 * a client transaction id associated with operation
	 * and with a transfer token
	 */
	public EppCommandTransferXriName( String iname, String trToken, String xid )
	{
		this(iname, null, trToken, xid);
	}

	/**
	 * Creates an <code>EppCommandTransferXriName</code> object for
	 * transfering an XRI i-name object based on its i-name,
	 * and an extended expiration period
	 */
	public EppCommandTransferXriName( String iname, EppPeriod period )
	{
		this(iname, period, null, null);
	}

	/**
	 * Creates an <code>EppCommandTransferXriName</code> object for
	 * transfering an XRI i-name object based on its i-name,
	 * with an extended expiration period
	 * and a client transaction id associated with operation
	 */
	public EppCommandTransferXriName( String iname, EppPeriod period, String xid )
	{
		this(iname, period, null, xid);
	};

	/**
	 * Creates an <code>EppCommandTransferXriName</code> object for
	 * transfering an XRI i-name object based on its i-name,
	 * with an extended expiration period,
	 * a transfer token
	 * and a client transaction id associated with operation
	 */
	public EppCommandTransferXriName( String iname, EppPeriod period, String trToken, String xid )
	{
		this.iname = iname;
		this.trToken = trToken;
		this.period = period;
		this.clTRID = xid;
	};

	/**
	 * Gets the i-name of the XRI i-name object to be transferred
	 */
	public String getIName()
	{
		return this.iname;
	}

	/**
	 * Sets the i-name of the XRI i-name object to be transferred
	 */
	public void setIName( String iname )
	{
		this.iname = iname;
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
	 * Gets the target XRI authority authInfo
	 */
	public EppAuthInfo getTargetAuthInfo()
	{
		return this.targetAuthInfo;
	}

	/**
	 * Sets the identifier and authInfo of the target XRI i-name involved in 
	 * XRI i-name transfer operation
	 */
	public void setTarget( String authId, EppAuthInfo authInfo )
	{
		this.targetAuthId   = authId;
		this.targetAuthInfo = authInfo;
	}

	/**
	 * Gets the extended registration period of the XRI I-Name object to be transferred
	 */
	public EppPeriod getPeriod()
	{
		return this.period;
	}

	/**
	 * Sets the extended registration period of the XRI I-Name object to be transferred
	 */
	public void setPeriod( EppPeriod period )
	{
		this.period = period;
	}

	/**
	 * Converts the <code>EppCommandTransferXriName</code> object into
	 * an XML element for an <code>EppPollable</code> object
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the
	 *            <code>EppCommandTransferXriName</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXMLPoll( Document doc, String tag )
	{
		Element elm;
		Element child;
		Element body = EppUtil.createElementNS(doc, "xriINA", tag);

		if( iname != null )
		{
			elm = doc.createElement("iname");
			elm.appendChild(doc.createTextNode(iname));
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
		if( period != null )
		{
			body.appendChild(period.toXML(doc, "period"));
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
	 * Converts the <code>EppCommandTransferXriName</code> object into
	 * an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the
	 *            <code>EppCommandTransferXriName</code> object
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
	 * <code>EppCommandTransferXriName</code> object. The caller of this
	 * method must make sure that the root node is of an EPP Command
	 * Transfer entity for EPP XriName object.
	 *
	 * @param root root node for an <code>EppCommandTransferXriName</code>
	 *             object in XML format
	 *
	 * @return an <code>EppCommandTransferXriName</code> object, or null
	 *         if the node is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		EppCommandTransferXriName cmd = null;
		EppPeriod   period         = null;
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
			if( name.equals("iname") )
			{
				String iname = EppUtil.getText(node);
				if( cmd == null )
				{
					cmd = new EppCommandTransferXriName(iname);
				}
				else
				{
					cmd.setIName(iname);
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
			else if( name.equals("period") )
			{
				period = (EppPeriod) EppPeriod.fromXML(node);
			}
			else if( name.equals("period") )
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
			cmd.setPeriod(period);
		}

		return cmd;
	}
}
