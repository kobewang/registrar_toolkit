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
 * This <code>EppXriNumber</code> class implements EPP XRI I-Number objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.5 $ $Date: 2008/04/24 21:31:59 $
 */
public class EppXriNumber extends EppObject
{
	/**
	 * I-Number status - clientDeleteProhibited
	 */
	public static final String STATUS_CLIENT_DELETE_PROHIBITED = "clientDeleteProhibited";
	/**
	 * I-Number status - clientHold
	 */
	public static final String STATUS_CLIENT_HOLD = "clientHold";
	/**
	 * I-Number status - clientRenewProhibited
	 */
	public static final String STATUS_CLIENT_RENEW_PROHIBITED = "clientRenewProhibited";
	/**
	 * I-Number status - clientUpdateProhibited
	 */
	public static final String STATUS_CLIENT_UPDATE_PROHIBITED = "clientUpdateProhibited";
	/**
	 * I-Number status - ok
	 */
	public static final String STATUS_OK = "ok";
	/**
	 * I-Number status - pendingCreate
	 */
	public static final String STATUS_PENDING_CREATE = "pendingCreate";
	/**
	 * I-Number status - pendingDelete
	 */
	public static final String STATUS_PENDING_DELETE = "pendingDelete";
	/**
	 * I-Number status - pendingTransfer
	 */
	public static final String STATUS_PENDING_TRANSFER = "pendingTransfer";
	/**
	 * I-Number status - pendingUpdate
	 */
	public static final String STATUS_PENDING_UPDATE = "pendingUpdate";
	/**
	 * I-Number status - serverDeleteProhibited
	 */
	public static final String STATUS_SERVER_DELETE_PROHIBITED = "serverDeleteProhibited";
	/**
	 * I-Number status - serverHold
	 */
	public static final String STATUS_SERVER_HOLD = "serverHold";
	/**
	 * I-Number status - serverRenewProhibited
	 */
	public static final String STATUS_SERVER_RENEW_PROHIBITED = "serverRenewProhibited";
	/**
	 * I-Number status - serverUpdateProhibited
	 */
	public static final String STATUS_SERVER_UPDATE_PROHIBITED = "serverUpdateProhibited";
	/**
	 * I-Number status - terminated
	 */
	public static final String STATUS_TERMINATED = "terminated";

	private String           inumber;
	private String           refId;
	private String           authId;
	private EppPeriod        period; 

	/**
	 * Creates an <code>EppXriNumber</code> object
	 */
	public EppXriNumber()
	{
		this.inumber    = null;
		this.refId      = null;
		this.authId     = null;
		this.period     = null;
	}

	/**
	 * Creates an <code>EppXriNumber</code> object with an XRI i-number
	 */
	public EppXriNumber( String inumber )
	{
		this();
		this.inumber = inumber;
	}

	/**
	 * Gets the i-number
	 */
	public String getINumber()
	{
		return this.inumber;
	}

	/**
	 * Sets the i-number
	 */
	public void setINumber( String inumber )
	{
		this.inumber = inumber;
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
	 * Gets the reference identifier used in generating the i-number, if any
	 */
	public String getReferenceId()
	{
		return this.refId;
	}

	/**
	 * Gets the reference identifier used in generating the i-number, if any
	 */
	public void setReferenceId( String refId )
	{
		this.refId = refId;
	}

	/**
	 * Gets registration period for the i-number
	 */
	public EppPeriod getPeriod()
	{
		return this.period;
	}

	/**
	 * Sets registration period for the i-number
	 */
	public void setPeriod( EppPeriod period )
	{
		this.period = period;
	}

	/**
	 * Converts the <code>EppXriNumber</code> object into an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the <code>EppXriNumber</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Element elm;
		Element body = EppUtil.createElementNS(doc, "xriINU", tag);
		boolean isCreate = tag.equals("create");

		// the order of the tags for create is:
		//
		// inumber/refid/authId/authInfo/period
		//
		// the order of the tags for info is
		//
		// inumber/roid/status/refId/authId/clID/crID/crDate
		// exDate/upDate/trDate
		//

		if( isCreate )
		{
			if( inumber != null )
			{
				elm = doc.createElement("inumber");
				elm.appendChild(doc.createTextNode(inumber));
				body.appendChild(elm);
			}
			if( refId != null )
			{
				elm = doc.createElement("refId");
				elm.appendChild(doc.createTextNode(refId));
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

		if( inumber != null )
		{
			elm = doc.createElement("inumber");
			elm.appendChild(doc.createTextNode(inumber));
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
		if( refId != null )
		{
			elm = doc.createElement("refId");
			elm.appendChild(doc.createTextNode(refId));
			body.appendChild(elm);
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
	 * Converts an XML element into an <code>EppXriNumber</code> object.
	 * The caller of this method must make sure that the root node is of
	 * the EPP I-Number type.
	 *
	 * @param root root node for an <code>EppXriNumber</code> object in
	 *             XML format
	 *
	 * @return an <code>EppXriNumber</code> object, or null if the node
	 *         is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		EppXriNumber xin = new EppXriNumber();
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
				xin.setINumber(EppUtil.getText(node));
			}
			else if( name.equals("refId") )
			{
				xin.setReferenceId(EppUtil.getText(node));
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
		return toString("xriINU");
	}

	/**
	 * Creates an <code>EppCommandDeleteXriNumber</code> object for
	 * deleting an EPP XRI I-Number object from the registry.
	 *
	 * @param inumber the i-number of the XRI i-number object to be deleted
	 * @param xid     the client transaction id associated with the operation
	 */
	public static EppCommandDeleteXriNumber delete( String inumber, String xid )
	{
		return new EppCommandDeleteXriNumber(inumber, xid);
	}

	/**
	 * Creates an <code>EppCommandInfoXriNumber</code> object for
	 * querying the details of an EPP XRI I-Number object
	 *
	 * @param inumber the i-number of the XRI i-number object to be queried
	 * @param xid     the client transaction id associated with the operation
	 */
	public static EppCommandInfoXriNumber info( String inumber, String xid )
	{
		return new EppCommandInfoXriNumber(inumber, xid);
	}

	/**
	 * Creates an <code>EppCommandCheckXriNumber</code> object for
	 * checking the existance of EPP XRI I-Number objects in the registry.
	 * Identifiers of EPP XRI I-Number objects can be added via the
	 * <code>add</code> or <code>addINumber</code> methods.
	 *
	 * @param xid  the client transaction id associated with the operation
	 */
	public static EppCommandCheckXriNumber check( String xid )
	{
		return new EppCommandCheckXriNumber(xid);
	}

	/**
	 * Creates an <code>EppCommandRenewXriNumber</code> object for
	 * renewing the registration of an EPP XRI I-Number object in the registry.
	 *
	 * @param inumber    the inumber of the XRI i-number object to be renewed
	 * @param curExpDate the current expiration date of the svcsub object
	 * @param period     the new registration period of the svcsub object,
	 *                   or null if using the value specified by the
	 *                   registry
	 * @param xid        the client transaction id associated with the
	 *                   operation
	 */
	public static EppCommandRenewXriNumber renew( String inumber, Calendar curExpDate, EppPeriod period, String xid )
	{
		return new EppCommandRenewXriNumber(inumber, curExpDate, period, xid);
	}

	/**
	 * Creates an <code>EppCommandUpdateXriNumber</code> object for
	 * updating an EPP I-Number object in the registry. The actual update
	 * information should be specified via the various methods defined
	 * for the <code>EppCommandUpdateXriNumber</code> object.
	 *
	 * @param inumber the i-number of the XRI i-number object to be updated
	 * @param xid    the client transaction id associated with the operation
	 */
	public static EppCommandUpdateXriNumber update( String inumber, String xid )
	{
		return new EppCommandUpdateXriNumber(inumber, xid);
	}
}
