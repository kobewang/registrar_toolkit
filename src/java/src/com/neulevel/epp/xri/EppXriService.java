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
 * This <code>EppXriService</code> class implements EPP XRI I-Service objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.3 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppXriService extends EppObject
{
	/**
	 * I-Service status - clientDeleteProhibited
	 */
	public static final String STATUS_CLIENT_DELETE_PROHIBITED = "clientDeleteProhibited";
	/**
	 * I-Service status - clientHold
	 */
	public static final String STATUS_CLIENT_HOLD = "clientHold";
	/**
	 * I-Service status - clientRenewProhibited
	 */
	public static final String STATUS_CLIENT_RENEW_PROHIBITED = "clientRenewProhibited";
	/**
	 * I-Service status - clientUpdateProhibited
	 */
	public static final String STATUS_CLIENT_UPDATE_PROHIBITED = "clientUpdateProhibited";
	/**
	 * I-Service status - ok
	 */
	public static final String STATUS_OK = "ok";
	/**
	 * I-Service status - pendingCreate
	 */
	public static final String STATUS_PENDING_CREATE = "pendingCreate";
	/**
	 * I-Service status - pendingDelete
	 */
	public static final String STATUS_PENDING_DELETE = "pendingDelete";
	/**
	 * I-Service status - pendingTransfer
	 */
	public static final String STATUS_PENDING_TRANSFER = "pendingTransfer";
	/**
	 * I-Service status - pendingUpdate
	 */
	public static final String STATUS_PENDING_UPDATE = "pendingUpdate";
	/**
	 * I-Service status - serverDeleteProhibited
	 */
	public static final String STATUS_SERVER_DELETE_PROHIBITED = "serverDeleteProhibited";
	/**
	 * I-Service status - serverHold
	 */
	public static final String STATUS_SERVER_HOLD = "serverHold";
	/**
	 * I-Service status - serverRenewProhibited
	 */
	public static final String STATUS_SERVER_RENEW_PROHIBITED = "serverRenewProhibited";
	/**
	 * I-Service status - serverUpdateProhibited
	 */
	public static final String STATUS_SERVER_UPDATE_PROHIBITED = "serverUpdateProhibited";

	private String           id;
	private String           type;
	private String           authId;
	private EppPeriod        period; 
	private Vector           uri;

	/**
	 * Creates an <code>EppXriService</code> object
	 */
	public EppXriService()
	{
		this.id         = null;
		this.type       = null;
		this.authId     = null;
		this.period     = null;
		this.uri        = new Vector();
	}

	/**
	 * Creates an <code>EppXriService</code> object with an XRI i-service identifier
	 */
	public EppXriService( String id )
	{
		this();
		this.id = id;
	}

	/**
	 * Gets the i-service identifier
	 */
	public String getId()
	{
		return this.id;
	}

	/**
	 * Sets the i-service identifier
	 */
	public void setId( String id )
	{
		this.id = id;
	}

	/**
	 * Gets the i-service type
	 */
	public String getType()
	{
		return this.type;
	}

	/**
	 * Sets the i-service type
	 */
	public void setType( String type )
	{
		this.type = type;
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
	 * Gets the list of URIs associated with the XRI i-service object
	 */
	public Vector getURI()
	{
		return this.uri;
	}

	/**
	 * Adds a URL to the list of URIs associated with the XRI i-service object
	 */
	public void addURI( String uri )
	{
		this.uri.addElement(uri);
	}

	/**
	 * Gets registration period for the i-service object
	 */
	public EppPeriod getPeriod()
	{
		return this.period;
	}

	/**
	 * Sets registration period for the i-service object
	 */
	public void setPeriod( EppPeriod period )
	{
		this.period = period;
	}

	/**
	 * Converts the <code>EppXriService</code> object into an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the <code>EppXriService</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Element elm;
		Element body = EppUtil.createElementNS(doc, "xriISV", tag);
		boolean isCreate = tag.equals("create");

		// the order of the tags for create is:
		//
		// id/type/authId/authInfo/period
		//
		// the order of the tags for info is
		//
		// id/roid/status/type/uri/authId/clID/crID/crDate
		// exDate/upDate/trDate
		//

		if( isCreate )
		{
			if( id != null )
			{
				elm = doc.createElement("id");
				elm.appendChild(doc.createTextNode(id));
				body.appendChild(elm);
			}
			if( type != null )
			{
				elm = doc.createElement("type");
				elm.appendChild(doc.createTextNode(type));
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

		if( id != null )
		{
			elm = doc.createElement("id");
			elm.appendChild(doc.createTextNode(id));
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
		if( type != null )
		{
			elm = doc.createElement("type");
			elm.appendChild(doc.createTextNode(type));
			body.appendChild(elm);
		}
		if( uri != null )
		{
			for( int i = 0; i < uri.size(); i++ )
			{
				String s = (String) uri.elementAt(i);
				elm = doc.createElement("uri");
				elm.appendChild(doc.createTextNode(s));
				body.appendChild(elm);
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
	 * Converts an XML element into an <code>EppXriService</code> object.
	 * The caller of this method must make sure that the root node is of
	 * the EPP I-Service type.
	 *
	 * @param root root node for an <code>EppXriService</code> object in
	 *             XML format
	 *
	 * @return an <code>EppXriService</code> object, or null if the node
	 *         is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		EppXriService xin = new EppXriService();
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
				xin.setId(EppUtil.getText(node));
			}
			else if( name.equals("type") )
			{
				xin.setType(EppUtil.getText(node));
			}
			else if( name.equals("uri") )
			{
				xin.addURI(EppUtil.getText(node));
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
		return toString("xriISV");
	}

	/**
	 * Creates an <code>EppCommandDeleteXriService</code> object for
	 * deleting an EPP XRI I-Service object from the registry.
	 *
	 * @param id the identifier of the XRI i-service object to be deleted
	 * @param xid     the client transaction id associated with the operation
	 */
	public static EppCommandDeleteXriService delete( String id, String xid )
	{
		return new EppCommandDeleteXriService(id, xid);
	}

	/**
	 * Creates an <code>EppCommandInfoXriService</code> object for
	 * querying the details of an EPP XRI I-Service object
	 *
	 * @param id      the identifier of the XRI i-service object to be created
	 * @param xid     the client transaction id associated with the operation
	 */
	public static EppCommandInfoXriService info( String id, String xid )
	{
		return new EppCommandInfoXriService(id, xid);
	}

	/**
	 * Creates an <code>EppCommandCheckXriService</code> object for
	 * checking the existance of EPP XRI I-Service objects in the registry.
	 * Identifiers of EPP XRI I-Service objects can be added via the
	 * <code>add</code> or <code>addIService</code> methods.
	 *
	 * @param xid  the client transaction id associated with the operation
	 */
	public static EppCommandCheckXriService check( String xid )
	{
		return new EppCommandCheckXriService(xid);
	}

	/**
	 * Creates an <code>EppCommandRenewXriService</code> object for
	 * renewing the registration of an EPP XRI I-Service object in the registry.
	 *
	 * @param id         the identifier of the XRI i-service object to be renewed
	 * @param curExpDate the current expiration date of the svcsub object
	 * @param period     the new registration period of the svcsub object,
	 *                   or null if using the value specified by the
	 *                   registry
	 * @param xid        the client transaction id associated with the
	 *                   operation
	 */
	public static EppCommandRenewXriService renew( String id, Calendar curExpDate, EppPeriod period, String xid )
	{
		return new EppCommandRenewXriService(id, curExpDate, period, xid);
	}

	/**
	 * Creates an <code>EppCommandUpdateXriService</code> object for
	 * updating an EPP I-Service object in the registry. The actual update
	 * information should be specified via the various methods defined
	 * for the <code>EppCommandUpdateXriService</code> object.
	 *
	 * @param id     the identifier of the XRI i-service object to be updated
	 * @param xid    the client transaction id associated with the operation
	 */
	public static EppCommandUpdateXriService update( String id, String xid )
	{
		return new EppCommandUpdateXriService(id, xid);
	}
}
