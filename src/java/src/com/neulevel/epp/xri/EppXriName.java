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
package com.neulevel.epp.xri;

import java.util.*;
import org.w3c.dom.*;
import com.neulevel.epp.core.EppEntity;
import com.neulevel.epp.core.EppObject;
import com.neulevel.epp.core.EppStatus;
import com.neulevel.epp.core.EppPeriod;
import com.neulevel.epp.core.EppAuthInfo;
import com.neulevel.epp.core.EppUtil;
import com.neulevel.epp.xri.command.*;

/**
 * This <code>EppXriName</code> class implements EPP XRI I-Name objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.5 $ $Date: 2008/04/24 21:31:59 $
 */
public class EppXriName extends EppObject
{
	/**
	 * I-Name status - clientDeleteProhibited
	 */
	public static final String STATUS_CLIENT_DELETE_PROHIBITED = "clientDeleteProhibited";
	/**
	 * I-Name status - clientHold
	 */
	public static final String STATUS_CLIENT_HOLD = "clientHold";
	/**
	 * I-Name status - clientRenewProhibited
	 */
	public static final String STATUS_CLIENT_RENEW_PROHIBITED = "clientRenewProhibited";
	/**
	 * I-Name status - clientTransferProhibited
	 */
	public static final String STATUS_CLIENT_TRANSFER_PROHIBITED = "clientTransferProhibited";
	/**
	 * I-Name status - clientUpdateProhibited
	 */
	public static final String STATUS_CLIENT_UPDATE_PROHIBITED = "clientUpdateProhibited";
	/**
	 * I-Name status - ok
	 */
	public static final String STATUS_OK = "ok";
	/**
	 * I-Name status - pendingCreate
	 */
	public static final String STATUS_PENDING_CREATE = "pendingCreate";
	/**
	 * I-Name status - pendingDelete
	 */
	public static final String STATUS_PENDING_DELETE = "pendingDelete";
	/**
	 * I-Name status - pendingTransfer
	 */
	public static final String STATUS_PENDING_TRANSFER = "pendingTransfer";
	/**
	 * I-Name status - pendingUpdate
	 */
	public static final String STATUS_PENDING_UPDATE = "pendingUpdate";
	/**
	 * I-Name status - serverDeleteProhibited
	 */
	public static final String STATUS_SERVER_DELETE_PROHIBITED = "serverDeleteProhibited";
	/**
	 * I-Name status - serverHold
	 */
	public static final String STATUS_SERVER_HOLD = "serverHold";
	/**
	 * I-Name status - serverRenewProhibited
	 */
	public static final String STATUS_SERVER_RENEW_PROHIBITED = "serverRenewProhibited";
	/**
	 * I-Name status - serverTransferProhibited
	 */
	public static final String STATUS_SERVER_TRANSFER_PROHIBITED = "serverTransferProhibited";
	/**
	 * I-Name status - serverUpdateProhibited
	 */
	public static final String STATUS_SERVER_UPDATE_PROHIBITED = "serverUpdateProhibited";

	private String           iname;
	private String           authId;
	private EppPeriod        period; 

	/**
	 * Creates an <code>EppXriName</code> object
	 */
	public EppXriName()
	{
		this.iname      = null;
		this.authId     = null;
		this.period     = null;
	}

	/**
	 * Creates an <code>EppXriName</code> object with an XRI i-name
	 */
	public EppXriName( String iname )
	{
		this();
		this.iname = iname;
	}

	/**
	 * Gets the i-name
	 */
	public String getIName()
	{
		return this.iname;
	}

	/**
	 * Sets the i-name
	 */
	public void setIName( String iname )
	{
		this.iname = iname;
	}

	/**
	 * Sets the identifier of the superorindate XRI authority object,
	 * and authInfo associated with the authority, if any
	 */
	public void setAuthority( String authId, EppAuthInfo authInfo )
	{
		this.authId   = authId;
		this.authInfo = authInfo;
	}

	/**
	 * Gets the identifier of the superordinate XRI authority object
	 */
	public String getAuthorityId()
	{
		return this.authId;
	}

	/**
	 * Sets the identifier of the superordinate XRI authority object
	 */
	public void setAuthorityId( String authId )
	{
		this.authId = authId;
	}

	/**
	 * Gets registration period for the i-name
	 */
	public EppPeriod getPeriod()
	{
		return this.period;
	}

	/**
	 * Sets registration period for the i-name
	 */
	public void setPeriod( EppPeriod period )
	{
		this.period = period;
	}

	/**
	 * Converts the <code>EppXriName</code> object into an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the <code>EppXriName</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Element elm;
		Element body = EppUtil.createElementNS(doc, "xriINA", tag);
		boolean isCreate = tag.equals("create");

		// the order of the tags for create is:
		//
		// iname/authId/authInfo/period
		//
		// the order of the tags for info is
		//
		// iname/roid/status/authId/clID/crID/crDate
		// exDate/upDate/trDate
		//

		if( isCreate )
		{
			if( iname != null )
			{
				elm = doc.createElement("iname");
				elm.appendChild(doc.createTextNode(iname));
				body.appendChild(elm);
			}
			if( authId != null )
			{
				elm = doc.createElement("authority");
				Element celm = doc.createElement("authId");
				celm.appendChild(doc.createTextNode(authId));
				elm.appendChild(celm);
				if( authInfo != null )
				{
					elm.appendChild(authInfo.toXML(doc, "authInfo"));
				}
				body.appendChild(elm);
			}
			if( period != null )
			{
				body.appendChild(period.toXML(doc, "period"));
			}
			return body;
		}

		if( iname != null )
		{
			elm = doc.createElement("iname");
			elm.appendChild(doc.createTextNode(iname));
			body.appendChild(elm);
		}
		if( roid != null )
		{
			elm = doc.createElement("roid");
			elm.appendChild(doc.createTextNode(roid));
			body.appendChild(elm);
		}
		if( status != null )
		{
			for( int i = 0; i < status.size(); i++ )
			{
				EppStatus s = (EppStatus) status.elementAt(i);
				body.appendChild(s.toXML(doc, "status"));
			}
		}
		if( authId != null )
		{
			elm = doc.createElement("authId");
			elm.appendChild(doc.createTextNode(authId));
			body.appendChild(elm);
		}

		toXMLCommon(doc, body);

		return body;
	}

	/**
	 * Converts an XML element into an <code>EppXriName</code> object.
	 * The caller of this method must make sure that the root node is of
	 * the EPP I-Name type.
	 *
	 * @param root root node for an <code>EppXriName</code> object in
	 *             XML format
	 *
	 * @return an <code>EppXriName</code> object, or null if the node
	 *         is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		EppXriName xin = new EppXriName();
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
				xin.setIName(EppUtil.getText(node));
			}
			else if( name.equals("authId") )
			{
				xin.setAuthorityId(EppUtil.getText(node));
			}
			else if( name.equals("authority") )
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
						xin.setAuthorityId(EppUtil.getText(cnode));
					}
					else if( cname.equals("authInfo") )
					{
						EppAuthInfo authInfo = (EppAuthInfo) EppAuthInfo.fromXML(cnode);
						if( authInfo != null )
						{
							xin.setAuthInfo(authInfo);
						}
					}
				}
			}
			else if( name.equals("period") )
			{
				EppPeriod p = (EppPeriod) EppPeriod.fromXML(node);
				if( p != null )
				{
					xin.setPeriod(p);
				}
			}
			else
			{
				xin.fromXMLCommon(node, name);
			}
		}

		return xin;
	}

	public String toString()
	{
		return toString("xriINA");
	}

	/**
	 * Creates an <code>EppCommandDeleteXriName</code> object for
	 * deleting an EPP XRI I-Name object from the registry.
	 *
	 * @param iname the i-name of the XRI i-name object to be deleted
	 * @param xid     the client transaction id associated with the operation
	 */
	public static EppCommandDeleteXriName delete( String iname, String xid )
	{
		return new EppCommandDeleteXriName(iname, xid);
	}

	/**
	 * Creates an <code>EppCommandInfoXriName</code> object for
	 * querying the details of an EPP XRI I-Name object
	 *
	 * @param iname the i-name of the XRI i-name object to be queried
	 * @param xid     the client transaction id associated with the operation
	 */
	public static EppCommandInfoXriName info( String iname, String xid )
	{
		return new EppCommandInfoXriName(iname, xid);
	}

	/**
	 * Creates an <code>EppCommandCheckXriName</code> object for
	 * checking the existance of EPP XRI I-Name objects in the registry.
	 * Identifiers of EPP XRI I-Name objects can be added via the
	 * <code>add</code> or <code>addIName</code> methods.
	 *
	 * @param xid  the client transaction id associated with the operation
	 */
	public static EppCommandCheckXriName check( String xid )
	{
		return new EppCommandCheckXriName(xid);
	}

	/**
	 * Creates an <code>EppCommandRenewXriName</code> object for
	 * renewing the registration of an EPP XRI I-Name object in the registry.
	 *
	 * @param iname    the iname of the XRI i-name object to be renewed
	 * @param curExpDate the current expiration date of the svcsub object
	 * @param period     the new registration period of the svcsub object,
	 *                   or null if using the value specified by the
	 *                   registry
	 * @param xid        the client transaction id associated with the
	 *                   operation
	 */
	public static EppCommandRenewXriName renew( String iname, Calendar curExpDate, EppPeriod period, String xid )
	{
		return new EppCommandRenewXriName(iname, curExpDate, period, xid);
	}

	/**
	 * Creates an <code>EppCommandTransferXriName</code> object for
	 * transfering an EPP XRI I-Name object in the registry. The operation
	 * type, transfer token, target authority and authorization information associated
	 * with the operation should be specified via <code>setOperation</code>,
	 * <code>setTransferToken</code>, <code>setTarget</code>, and <code>setAuthInfo</code> method.
	 *
	 * @param iname the identifier of the XRI authority object to be transferred
	 * @param xid   the client transaction id associated with the operation
	 */
	public static EppCommandTransferXriName transfer( String iname, String xid )
	{
		return new EppCommandTransferXriName(iname, xid);
	}

	/**
	 * Creates an <code>EppCommandUpdateXriName</code> object for
	 * updating an EPP I-Name object in the registry. The actual update
	 * information should be specified via the various methods defined
	 * for the <code>EppCommandUpdateXriName</code> object.
	 *
	 * @param iname the i-name of the XRI i-name object to be updated
	 * @param xid   the client transaction id associated with the operation
	 */
	public static EppCommandUpdateXriName update( String iname, String xid )
	{
		return new EppCommandUpdateXriName(iname, xid);
	}
}
