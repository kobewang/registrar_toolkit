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
import com.neulevel.epp.core.EppE164;
import com.neulevel.epp.core.EppStatus;
import com.neulevel.epp.core.EppAuthInfo;
import com.neulevel.epp.core.EppContactData;
import com.neulevel.epp.core.EppUtil;
import com.neulevel.epp.xri.EppXriContactData;
import com.neulevel.epp.xri.command.*;

/**
 * This <code>EppXriAuthority</code> class implements EPP XRI Authority objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.13 $ $Date: 2009/12/18 12:03:08 $
 */
public class EppXriAuthority extends EppObject
{
	/**
	 * Authority status - clientAssociateProhibited
	 */
	public static final String STATUS_CLIENT_ASSOCIATE_PROHIBITED = "clientAssociateProhibited";
	/**
	 * Authority status - clientDeleteProhibited
	 */
	public static final String STATUS_CLIENT_DELETE_PROHIBITED = "clientDeleteProhibited";
	/**
	 * Authority status - clientHold
	 */
	public static final String STATUS_CLIENT_HOLD = "clientHold";
	/**
	 * Authority status - clientTransferProhibited
	 */
	public static final String STATUS_CLIENT_TRANSFER_PROHIBITED = "clientTransferProhibited";
	/**
	 * Authority status - clientUpdateProhibited
	 */
	public static final String STATUS_CLIENT_UPDATE_PROHIBITED = "clientUpdateProhibited";
	/**
	 * Authority status - linked
	 */
	public static final String STATUS_LINKED = "linked";
	/**
	 * Authority status - ok
	 */
	public static final String STATUS_OK = "ok";
	/**
	 * Authority status - pendingCreate
	 */
	public static final String STATUS_PENDING_CREATE = "pendingCreate";
	/**
	 * Authority status - pendingDelete
	 */
	public static final String STATUS_PENDING_DELETE = "pendingDelete";
	/**
	 * Authority status - pendingINameTransfer
	 */
	public static final String STATUS_PENDING_INAME_TRANSFER = "pendingINameTransfer";
	/**
	 * Authority status - pendingTransfer
	 */
	public static final String STATUS_PENDING_TRANSFER = "pendingTransfer";
	/**
	 * Authority status - pendingUpdate
	 */
	public static final String STATUS_PENDING_UPDATE = "pendingUpdate";
	/**
	 * Authority status - serverAssociateProhibited
	 */
	public static final String STATUS_SERVER_ASSOCIATE_PROHIBITED = "serverAssociateProhibited";
	/**
	 * Authority status - serverDeleteProhibited
	 */
	public static final String STATUS_SERVER_DELETE_PROHIBITED = "serverDeleteProhibited";
	/**
	 * Authority status - serverHold
	 */
	public static final String STATUS_SERVER_HOLD = "serverHold";
	/**
	 * Authority status - serverTransferProhibited
	 */
	public static final String STATUS_SERVER_TRANSFER_PROHIBITED = "serverTransferProhibited";
	/**
	 * Authority status - serverUpdateProhibited
	 */
	public static final String STATUS_SERVER_UPDATE_PROHIBITED = "serverUpdateProhibited";

	private String           authId;
	private Boolean          isEscrow;
	private Boolean          isContact;
	private EppXriTrustee    escrowAgent;
	private EppXriTrustee    contactAgent;
	private EppXriSocialData socialData;
	private Vector           trustee;
	private Vector           ref;
	private Vector           redirect;
	private Vector           equivIDs;
	private String           canonicalEquivID;
	private Vector           sep;
	private Vector           iname;
	private Vector           inumber;
	private Vector           iservice;
	private Vector           contactHands;
	private String           extension;

	/**
	 * Creates an <code>EppXriAuthority</code> object
	 */
	public EppXriAuthority()
	{
		this.authId       = null;
		this.socialData   = null;
		this.isEscrow     = null;
		this.isContact    = null;
		this.escrowAgent  = null;
		this.contactAgent = null;
		this.trustee      = new Vector();
		this.ref          = new Vector();
		this.redirect     = new Vector();
		this.equivIDs     = new Vector();
		this.canonicalEquivID = null;
		this.sep          = new Vector();
		this.iname        = new Vector();
		this.inumber      = new Vector();
		this.iservice     = new Vector();
		this.contactHands = new Vector();
		this.extension    = null;
	}

	/**
	 * Creates an <code>EppXriAuthority</code> object with an XRI authority identifier
	 */
	public EppXriAuthority( String authId )
	{
		this();
		this.authId = authId;
	}

	/**
	 * Gets the XRI authority identifier
	 */
	public String getAuthorityId()
	{
		return this.authId;
	}
	public Vector getContactHandles() {
		return this.contactHands;
	}

	/**
	 * Sets the XRI authority identifier
	 */
	public void setAuthorityId( String authId )
	{
		this.authId = authId;
	}

	/**
	 * Gets the XRI authority isEscrow flag. If the flag was never set, this method would
	 * return the default (false) value.
	 */
	public boolean getIsEscrow()
	{
		if (isEscrow == null)
			return false;
		return isEscrow.booleanValue();
	}

	/**
	 * Sets the XRI authority isEscrow flag
	 */
	public void setIsEscrow( boolean isEscrow )
	{
		this.isEscrow = Boolean.valueOf(isEscrow);
	}

	/**
	 * Gets the XRI authority isContact flag. If the flag was never set, this method
	 * returns the default value (false).
	 */
	public boolean getIsContact()
	{
		if (isContact == null)
			return false;
		return isContact.booleanValue();
	}

	/**
	 * Sets the XRI authority isContact flag
	 */
	public void setIsContact( boolean isContact )
	{
		this.isContact = Boolean.valueOf(isContact);
	}

	/**
	 * Gets the XRI authority's escrowAgent
	 */
	public EppXriTrustee getEscrowAgent()
	{
		return this.escrowAgent;
	}

	/**
	 * Sets the XRI authority's escrowAgent
	 */
	public void setEscrowAgent( EppXriTrustee escrowAgent )
	{
		this.escrowAgent = escrowAgent;
	}

	/**
	 * Gets the XRI authority's contactAgent
	 */
	public EppXriTrustee getContactAgent()
	{
		return this.contactAgent;
	}

	/**
	 * Sets the XRI authority's contactAgent
	 */
	public void setContactAgent( EppXriTrustee contactAgent )
	{
		this.contactAgent = contactAgent;
	}

	/**
	 * Gets the social data associated with the XRI authority object
	 */
	public EppXriSocialData getSocialData()
	{
		return this.socialData;
	}

	/**
	 * Gets the social data associated with the XRI authority object
	 */
	public void setSocialData( EppXriSocialData socialData )
	{
		this.socialData = socialData;
	}

	/**
	 * Gets a list of trustees associated with the XRI authority object
	 */
	public Vector getTrustee()
	{
		return this.trustee;
	}

	/**
	 * Adds a trustee associated with the XRI authority object
	 *
	 * <P><B>Note:</B> This method should be only called by the EPP server, and
	 *       an EPP client should not perform this operation.
	 */
	public void addTrustee( EppXriTrustee trustee )
	{
		this.trustee.addElement(trustee);
	}

	/**
	 * Gets a list of Refs associated with the XRI authority object
	 */
	public Vector getRef()
	{
		return this.ref;
	}

	/**
	 * Adds a Ref associated with the XRI authority object
	 *
	 * <P><B>Note:</B> This method should be only called by the EPP server, and
	 *       an EPP client should not perform this operation.
	 */
	public void addRef( EppXriRef ref )
	{
		this.ref.addElement(ref);
	}

	/**
	 * Gets a list of Redirects associated with the XRI authority object
	 */
	public Vector getRedirect()
	{
		return this.redirect;
	}

	/**
	 * Adds a Redirect associated with the XRI authority object
	 *
	 * <P><B>Note:</B> This method should be only called by the EPP server, and
	 *       an EPP client should not perform this operation.
	 */
	public void addRedirect( EppXriURI redirect )
	{
		this.redirect.addElement(redirect);
	}

	/**
	 * Gets a list of EquivIDs associated with the XRI authority object
	 */
	public Vector getEquivID()
	{
		return this.equivIDs;
	}

	/**
	 * Adds a EquivID associated with the XRI authority object
	 *
	 * <P><B>Note:</B> This method should be only called by the EPP server, and
	 *       an EPP client should not perform this operation.
	 */
	public void addEquivID( EppXriSynonym equivID )
	{
		this.equivIDs.addElement(equivID);
	}

	/**
	 * Gets the XRI authority's CanonicalEquivID
	 */
	public String getCanonicalEquivID()
	{
		return this.canonicalEquivID;
	}

	/**
	 * Sets the XRI authority's CanonicalEquivID
	 */
	public void setCanonicalEquivID( String canonicalEquivID )
	{
		this.canonicalEquivID = canonicalEquivID;
	}

	/**
	 * Gets a list of XRI service endpoint (SEP) associated with the XRI authority object
	 */
	public Vector getServiceEndpoint()
	{
		return this.sep;
	}

	/**
	 * Adds a XRI service service endpoint (SEP) associated with the XRI authority object
	 *
	 * <P><B>Note:</B> This method should be only called by the EPP server, and
	 *       an EPP client should not perform this operation.
	 */
	public void addServiceEndpoint( EppXriServiceEndpoint sep )
	{
		this.sep.addElement(sep);
	}

	/**
	 * Gets a list of i-names associated with the XRI authority object
	 *
	 * <P><B>Note:</B> This method should be only called by the EPP server, and
	 *       an EPP client should not perform this operation.
	 */
	public Vector getIName()
	{
		return this.iname;
	}

	/**
	 * Adds an i-name associated with the XRI authority object
	 *
	 * <P><B>Note:</B> This method should be only called by the EPP server, and
	 *       an EPP client should not perform this operation.
	 */
	public void addIName( String iname )
	{
		this.iname.addElement(iname);
	}

	/**
	 * Gets a list of i-numbers associated with the XRI authority object
	 */
	public Vector getINumber()
	{
		return this.inumber;
	}

	/**
	 * Adds an i-number associated with the XRI authority object
	 *
	 * <P><B>Note:</B> This method should be only called by the EPP server, and
	 *       an EPP client should not perform this operation.
	 */
	public void addINumber( String inumber )
	{
		EppXriNumberAttribute xin = new EppXriNumberAttribute();
		xin.setINumber(inumber);
		this.inumber.addElement(xin);
	}

	/**
	 * Adds an i-number associated with the XRI authority object
	 *
	 * <P><B>Note:</B> This method should be only called by the EPP server, and
	 *       an EPP client should not perform this operation.
	 */
	public void addINumber( EppXriNumberAttribute xin )
	{
		this.inumber.addElement(xin);
	}

	/**
	 * Gets a list of identifiers of XRI I-Service objects associated with the XRI authority object
	 */
	public Vector getIService()
	{
		return this.iservice;
	}

	/**
	 * Adds the identifier of an XRI I-Service object associated with the XRI authority object
	 *
	 * <P><B>Note:</B> This method should be only called by the EPP server, and
	 *       an EPP client should not perform this operation.
	 */
	public void addIService( String iServiceId )
	{
		this.iservice.addElement(iServiceId);
	}

	public void addContactHandle(EppXriContactData contact) {
		this.contactHands.addElement(new EppXriContactData(contact));
	}


	/**
	 * Gets the XRI authority extension chunk.
	 * If the extension was never set, this method returns null.
	 */
	public String getExtension()
	{
		return this.extension;
	}

	/**
	 * Sets the XRI authority extension chunk.
	 */
	public void setExtension( String extension )
	{
		this.extension = extension;
	}

	/**
	 * Converts the <code>EppXriAuthority</code> object into an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the <code>EppXriAuthority</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Element elm;
		Element body = EppUtil.createElementNS(doc, "xriAU", tag);
		boolean isCreate = tag.equals("create");

		// the order of the tags for create is:
		//
		// authId/isEscrow/isContact/escrowAgent/contactAgent/socialData/authInfo
		//
		// the order of the tags for info is
		//
		// authId/isEscrow/isContact/escrowAgent/contactAgent/
		// roid/status/socialData/trustee/ref/redirect/equivID/canonicalEquivID/
		// sep/iname/inumber/iservice/extension/
		// clID/crID/crDate/upDate/trDate/authInfo
		//

		if( authId != null )
		{
			elm = doc.createElement("authId");
			elm.appendChild(doc.createTextNode(authId));
			body.appendChild(elm);
		}

		if( isEscrow != null )
		{
			elm = doc.createElement("isEscrow");
			elm.appendChild(doc.createTextNode(isEscrow.toString()));
			body.appendChild(elm);
		}

		if( isContact != null )
		{
			elm = doc.createElement("isContact");
			elm.appendChild(doc.createTextNode(isContact.toString()));
			body.appendChild(elm);
		}

		if ( escrowAgent != null)
		{
			body.appendChild(escrowAgent.toXML(doc, "escrowAgent"));
		}

		if ( contactAgent != null)
		{
			body.appendChild(contactAgent.toXML(doc, "contactAgent"));
		}
		if( contactHands != null ) {
			for( int i = 0; i < contactHands.size(); i++ ) {
				EppXriContactData t = (EppXriContactData)contactHands.elementAt(i);
				body.appendChild(t.toXML(doc, "contactId"));
			}
		}
		if( isCreate )
		{
			if( socialData != null )
			{
				body.appendChild(socialData.toXML(doc, "socialData"));
			}
			if( authInfo != null )
			{
				body.appendChild(authInfo.toXML(doc, "authInfo"));
			}

			return body;
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
		if( socialData != null )
		{
			body.appendChild(socialData.toXML(doc, "socialData"));
		}
		if( trustee != null )
		{
			for( int i = 0; i < trustee.size(); i++ )
			{
				EppXriTrustee t = (EppXriTrustee) trustee.elementAt(i);
				body.appendChild(t.toXML(doc, "trustee"));
			}
		}
		if( ref != null )
		{
			for( int i = 0; i < ref.size(); i++ )
			{
				EppXriRef t = (EppXriRef) ref.elementAt(i);
				body.appendChild(t.toXML(doc, "ref"));
			}
		}
		if( redirect != null )
		{
			for( int i = 0; i < redirect.size(); i++ )
			{
				EppXriURI t = (EppXriURI) redirect.elementAt(i);
				body.appendChild(t.toXML(doc, "redirect"));
			}
		}
		if( equivIDs != null )
		{
			for( int i = 0; i < equivIDs.size(); i++ )
			{
				EppXriSynonym id = (EppXriSynonym) equivIDs.elementAt(i);
				body.appendChild(id.toXML(doc, "equivID"));
			}
		}
		if( canonicalEquivID != null )
		{
			elm = doc.createElement("canonicalEquivID");
			elm.appendChild(doc.createTextNode(canonicalEquivID));
			body.appendChild(elm);
		}
		if( sep != null )
		{
			for( int i = 0; i < sep.size(); i++ )
			{
				EppXriServiceEndpoint t = (EppXriServiceEndpoint) sep.elementAt(i);
				body.appendChild(t.toXML(doc, "sep"));
			}
		}
		if( iname != null )
		{
			for( int i = 0; i < iname.size(); i++ )
			{
				String s = (String) iname.elementAt(i);
				elm = doc.createElement("iname");
				elm.appendChild(doc.createTextNode(s));
				body.appendChild(elm);
			}
		}
		if( inumber != null )
		{
			for( int i = 0; i < inumber.size(); i++ )
			{
				EppXriNumberAttribute xin = (EppXriNumberAttribute) inumber.elementAt(i);
				body.appendChild(xin.toXML(doc, "inumber"));
			}
		}
		if( iservice != null )
		{
			for( int i = 0; i < iservice.size(); i++ )
			{
				String s = (String) iservice.elementAt(i);
				elm = doc.createElement("iservice");
				elm.appendChild(doc.createTextNode(s));
				body.appendChild(elm);
			}
		}
		if( extension != null )
		{
			elm = doc.createElement("extension");
			elm.appendChild(doc.createTextNode(extension));
			body.appendChild(elm);
		}

		toXMLCommon(doc, body);

		return body;
	}

	/**
	 * Converts an XML element into an <code>EppXriAuthority</code> object.
	 * The caller of this method must make sure that the root node is of
	 * the EPP Authority type.
	 *
	 * @param root root node for an <code>EppXriAuthority</code> object in
	 *             XML format
	 *
	 * @return an <code>EppXriAuthority</code> object, or null if the node
	 *         is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		EppXriAuthority authority = new EppXriAuthority();
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
				authority.setAuthorityId(EppUtil.getText(node));
			}
			if( name.equals("isEscrow") )
			{
				authority.setIsEscrow(EppUtil.getTextAsBool(node));
			}
			if( name.equals("isContact") )
			{
				authority.setIsContact(EppUtil.getTextAsBool(node));
			}
			if( name.equals("escrowAgent") )
			{
				EppXriTrustee agent = (EppXriTrustee) EppXriTrustee.fromXML(node);
				if (agent != null)
					authority.setEscrowAgent(agent);
			}
			if( name.equals("contactAgent") )
			{
				EppXriTrustee agent = (EppXriTrustee) EppXriTrustee.fromXML(node);
				if (agent != null)
					authority.setContactAgent(agent);
			}
			else if( name.equals("socialData") )
			{
				EppXriSocialData socialData = (EppXriSocialData) EppXriSocialData.fromXML(node);
				if( socialData != null )
				{
					authority.setSocialData(socialData);
				}
			}
			else if( name.equals("trustee") )
			{
				EppXriTrustee trustee = (EppXriTrustee) EppXriTrustee.fromXML(node);
				if( trustee != null )
				{
					authority.addTrustee(trustee);
				}
			}
			else if( name.equals("contactId") )
			{
				EppXriContactData con = (EppXriContactData) EppXriContactData.fromXML(node);
				if( con != null )
				{
					authority.addContactHandle(con);
				}
			}
			else if( name.equals("ref") )
			{
				EppXriRef ref = (EppXriRef) EppXriRef.fromXML(node);
				if( ref != null )
				{
					authority.addRef(ref);
				}
			}
			else if( name.equals("redirect") )
			{
				EppXriURI redirect = (EppXriURI) EppXriURI.fromXML(node);
				if( redirect != null )
				{
					authority.addRedirect(redirect);
				}
			}
			else if( name.equals("equivID") )
			{
				EppXriSynonym equivID = (EppXriSynonym) EppXriSynonym.fromXML(node);
				if( equivID != null )
				{
					authority.addEquivID(equivID);
				}
			}
			if( name.equals("canonicalEquivID") )
			{
				authority.setCanonicalEquivID(EppUtil.getText(node));
			}
			else if( name.equals("sep") )
			{
				EppXriServiceEndpoint sep = (EppXriServiceEndpoint) EppXriServiceEndpoint.fromXML(node);
				if( sep != null )
				{
					authority.addServiceEndpoint(sep);
				}
			}
			else if( name.equals("iname") )
			{
				authority.addIName(EppUtil.getText(node));
			}
			else if( name.equals("inumber") )
			{
				EppXriNumberAttribute xin = (EppXriNumberAttribute) EppXriNumberAttribute.fromXML(node);
				if( xin != null )
				{
					authority.addINumber(xin);
				}
			}
			else if( name.equals("iservice") )
			{
				authority.addIService(EppUtil.getText(node));
			}
			else if( name.equals("authInfo") )
			{
				EppAuthInfo authInfo = (EppAuthInfo) EppAuthInfo.fromXML(node);
				if( authInfo != null )
				{
					authority.setAuthInfo(authInfo);
				}
			}
			else if( name.equals("extension") )
			{
				authority.setExtension(EppUtil.getText(node));
			}
			else
			{
				authority.fromXMLCommon(node, name);
			}
		}

		return authority;
	}

	public String toString()
	{
		return toString("xriAU");
	}

	/**
	 * Creates an <code>EppCommandDeleteXriAuthority</code> object for
	 * deleting an EPP XRI Authority object from the registry.
	 *
	 * @param authId the identifier of the XRI authority object to be deleted
	 * @param xid    the client transaction id associated with the operation
	 */
	public static EppCommandDeleteXriAuthority delete( String authId, String xid )
	{
		return new EppCommandDeleteXriAuthority(authId, xid);
	}

	/**
	 * Creates an <code>EppCommandInfoXriAuthority</code> object for
	 * querying the details of an EPP XRI Authority object
	 *
	 * @param authId the identifier of the XRI authority object to be queried
	 * @param xid    the client transaction id associated with the operation
	 */
	public static EppCommandInfoXriAuthority info( String authId, String xid )
	{
		return new EppCommandInfoXriAuthority(authId, xid);
	}

	/**
	 * Creates an <code>EppCommandCheckXriAuthority</code> object for
	 * checking the existance of EPP XRI Authority objects in the registry.
	 * Identifiers of EPP XRI Authority objects can be added via the
	 * <code>add</code> or <code>addAuthorityId</code> methods.
	 *
	 * @param xid  the client transaction id associated with the operation
	 */
	public static EppCommandCheckXriAuthority check( String xid )
	{
		return new EppCommandCheckXriAuthority(xid);
	}

	/**
	 * Creates an <code>EppCommandTransferXriAuthority</code> object for
	 * transfering an EPP XRI Authority object in the registry. The operation
	 * type, transfer token, target authority and authorization information associated
	 * with the operation should be specified via <code>setOperation</code>,
	 * <code>setTransferToken</code>, <code>setTarget</code>, and <code>setAuthInfo</code> method.
	 *
	 * @param authId the identifier of the XRI authority object to be transferred
	 * @param xid    the client transaction id associated with the operation
	 */
	public static EppCommandTransferXriAuthority transfer( String authId, String xid )
	{
		return new EppCommandTransferXriAuthority(authId, xid);
	}

	/**
	 * Creates an <code>EppCommandUpdateXriAuthority</code> object for
	 * updating an EPP Authority object in the registry. The actual update
	 * information should be specified via the various methods defined
	 * for the <code>EppCommandUpdateXriAuthority</code> object.
	 *
	 * @param authId the identifier of the XRI authority object to be updated
	 * @param xid    the client transaction id associated with the operation
	 */
	public static EppCommandUpdateXriAuthority update( String authId, String xid )
	{
		return new EppCommandUpdateXriAuthority(authId, xid);
	}
}
