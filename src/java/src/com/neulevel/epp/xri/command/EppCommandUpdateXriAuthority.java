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
 * This <code>EppCommandUpdateXriAuthority</code> class implements EPP Command Update
 * entity for EPP XRI Authority objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.10 $ $Date: 2010/01/08 00:20:19 $
 */
public class EppCommandUpdateXriAuthority extends EppCommandUpdate
{
	private String           authId;

	private Vector           trusteeAdded;
	private Vector           trusteeRemoved;

	private Vector           refAdded;
	private Vector           refRemoved;

	private Vector           redirectAdded;
	private Vector           redirectRemoved;

	private Vector           equivIDAdded;
	private Vector           equivIDRemoved;

	private Vector           sepAdded;
	private Vector           sepRemoved;

	private Vector           contactAdded;
	private Vector           contactRemoved;


	private String           newAuthId;
	private Boolean          newIsEscrow;
	private Boolean          newIsContact;
	private EppXriTrustee    newEscrowAgent;
	private EppXriTrustee    newContactAgent;
	private EppXriSocialData newSocialData;
	private String           newExtension;
	private EppAuthInfo      newAuthInfo;

	private EppAuthInfo      authInfo;

	private boolean		 nullifySocialData;
	private EppXriSocialData addSocialData;

	private String           addCanonicalEquivID;
	private String           remCanonicalEquivID;

	/**
	 * Creates an <code>EppCommandUpdateXriAuthority</code> given the
	 * identifier of the XRI authority object
	 */
	public EppCommandUpdateXriAuthority( String authId )
	{
		this(authId, null);
	}

	/**
	 * Creates an <code>EppCommandUpdateXriAuthority</code> given the
	 * identifier of the XRI authority object and a client transaction id
	 */
	public EppCommandUpdateXriAuthority( String authId, String xid )
	{
		this.authId = authId;
		this.clTRID = xid;

		this.trusteeAdded   = new Vector();
		this.trusteeRemoved = new Vector();

		this.refAdded        = new Vector();
		this.refRemoved      = new Vector();

		this.redirectAdded        = new Vector();
		this.redirectRemoved      = new Vector();

		this.equivIDAdded       = new Vector();
		this.equivIDRemoved     = new Vector();

		this.sepAdded       = new Vector();
		this.sepRemoved     = new Vector();

		this.contactAdded       = new Vector();
		this.contactRemoved     = new Vector();

		this.newAuthId      = null;
		this.newIsEscrow    = null;
		this.newIsContact   = null;
		this.newEscrowAgent  = null;
		this.newContactAgent = null;
		this.newAuthInfo     = null;
		this.newExtension   = null;

		this.authInfo       = null;

		this.nullifySocialData = false;
		this.addCanonicalEquivID = null;
		this.remCanonicalEquivID = null;
	}

	/**
	 * Gets the identifier of the XRI authority object to be updated
	 */
	public String getAuthorityId()
	{
		return this.authId;
	}

	/**
	 * Sets the identifier of the XRI authority object to be updated
	 */
	public void setAuthorityId( String authId )
	{
		this.authId = authId;
	}

	/**
	 * Gets the list of trustees to be associated with the XRI
	 * authority object
	 */
	public Vector getTrusteeAdded()
	{
		return this.trusteeAdded;
	}

	/**
	 * Adds a trustee to the list of trustees to be associated with
	 * the XRI authority object
	 */
	public void addTrustee( EppXriTrustee trustee )
	{
		this.trusteeAdded.addElement(trustee);
	}

	/**
	 * Gets the list of trustees to be removed from the XRI
	 * authority object
	 */
	public Vector getTrusteeRemoved()
	{
		return this.trusteeRemoved;
	}

	/**
	 * Adds a trustee to the list of trustees to be removed from
	 * the XRI authority object
	 */
	public void removeTrustee( EppXriTrustee trustee )
	{
		this.trusteeRemoved.addElement(trustee);
	}

	/**
	 * Gets the list of refs to be associated with
	 * the XRI authority object
	 */
	public Vector getRefAdded()
	{
		return this.refAdded;
	}

	/**
	 * Adds a ref to the list of refs to
	 * be associated with the XRI authority object
	 */
	public void addRef( String ref )
	{
		EppXriRef exr = new EppXriRef(ref);
		this.refAdded.addElement(exr);
	}

	/**
	 * Adds a ref to the list of refs to
	 * be associated with the XRI authority object
	 */
	public void addRef( EppXriRef ref )
	{
		this.refAdded.addElement(ref);
	}

	/**
	 * Gets the list of refs to be removed from the XRI
	 * authority object
	 */
	public Vector getRefRemoved()
	{
		return this.refRemoved;
	}

	/**
	 * Adds a ref to the list of refs to
	 * be removed from the XRI authority object
	 */
	public void removeRef( String ref )
	{
		this.refRemoved.addElement(ref);
	}

	/**
	 * Gets the list of redirects to be associated with
	 * the XRI authority object
	 */
	public Vector getRedirectAdded()
	{
		return this.redirectAdded;
	}

	/**
	 * Adds a redirect to the list of redirects to
	 * be associated with the XRI authority object
	 */
	public void addRedirect( String redirect )
	{
		EppXriURI redir = new EppXriURI(redirect);
		this.redirectAdded.addElement(redir);
	}

	/**
	 * Adds a redirect to the list of redirects to
	 * be associated with the XRI authority object
	 */
	public void addRedirect( EppXriURI redirect )
	{
		this.redirectAdded.addElement(redirect);
	}

	/**
	 * Gets the list of redirects to be removed from the XRI
	 * authority object
	 */
	public Vector getRedirectRemoved()
	{
		return this.redirectRemoved;
	}

	/**
	 * Adds a redirect to the list of redirects to
	 * be removed from the XRI authority object
	 */
	public void removeRedirect( String redirect )
	{
		this.redirectRemoved.addElement(redirect);
	}

	/**
	 * Gets the list of EquivID's to be associated with
	 * the XRI authority object
	 */
	public Vector getEquivIDAdded()
	{
		return this.equivIDAdded;
	}

	/**
	 * Adds an EquivID to the list of EquivID's to
	 * be associated with the XRI authority object
	 */
	public void addEquivID( String id )
	{
		EppXriSynonym equivID = new EppXriSynonym(id);
		this.equivIDAdded.addElement(id);
	}

	/**
	 * Adds an EquivID to the list of EquivID's to
	 * be associated with the XRI authority object
	 */
	public void addEquivID( EppXriSynonym equivID )
	{
		this.equivIDAdded.addElement(equivID);
	}

	/**
	 * Gets the list of EquivID's to be removed from the XRI
	 * authority object
	 */
	public Vector getEquivIDRemoved()
	{
		return this.equivIDRemoved;
	}

	/**
	 * Adds an EquivID to the list of EquivID's to
	 * be removed from the XRI authority object
	 */
	public void removeEquivID( String id )
	{
		this.equivIDRemoved.addElement(id);
	}

	/**
	 * Gets the list of service endpoints to be associated with the XRI authority
	 * object
	 */
	public Vector getServiceEndpointAdded()
	{
		return this.sepAdded;
	}
	public Vector getContactsAdded() {
		return this.contactAdded;
	}
	/**
	 * Adds an service endpoint to the list of service endpoints to be associated with the XRI
	 * authority object
	 */
	public void addServiceEndpoint( EppXriServiceEndpoint sep )
	{
		this.sepAdded.addElement(sep);
	}
	public void addContact(EppXriContactData contact) {
		this.contactAdded.addElement(contact);
	}

	/**
	 * Gets the list of identifiers of service endpoints to be removed from the
	 * XRI authority object
	 */
	public Vector getServiceEndpointRemoved()
	{
		return this.sepRemoved;
	}
	public Vector getContactsRemoved() {
		return this.contactRemoved;
	}

	/**
	 * Adds the identifier of an service endpoint to the list of identifiers of
	 * service endpoints to be removed from the XRI authority object
	 */
	public void removeServiceEndpoint( String id )
	{
		this.sepRemoved.addElement(id);
	}
	public void removeContact(EppXriContactData contact) {
		this.contactRemoved.addElement( contact );
	}

	/**
	 * Gets the new identifier for the XRI authority object, or null if
	 * the identifier of the XRI authority object is not to be changed
	 */
	public String getNewAuthorityId()
	{
		return this.newAuthId;
	}

	/**
	 * Sets the new identifier for the XRI authority object if it needs
	 * to be changed
	 */
	public void setNewAuthorityId( String newAuthId )
	{
		this.newAuthId = newAuthId;
	}

	/**
	 * Gets the new extension chunk for the XRI authority object, or null if
	 * the extension chunk of the XRI authority object is not to be changed
	 */
	public String getNewExtension()
	{
		return this.newExtension;
	}

	/**
	 * Sets the new extension chunk for the XRI authority object if it needs
	 * to be changed
	 */
	public void setNewExtension( String newExtension )
	{
		this.newExtension = newExtension;
	}

	/**
	 * Gets the new isEscrow flag for the XRI authority object, or null if
	 * the identifier of the XRI authority object is not to be changed
	 */
	public Boolean getNewIsEscrow()
	{
		return this.newIsEscrow;
	}

	/**
	 * Sets the new isEscrow flag for the XRI authority object if it needs
	 * to be changed
	 */
	public void setNewIsEscrow( Boolean newIsEscrow )
	{
		this.newIsEscrow = newIsEscrow;
	}

	/**
	 * Gets the new isContact flag for the XRI authority object, or null if
	 * the identifier of the XRI authority object is not to be changed
	 */
	public Boolean getNewIsContact()
	{
		return this.newIsContact;
	}

	/**
	 * Sets the new isContact flag for the XRI authority object if it needs
	 * to be changed
	 */
	public void setNewIsContact( Boolean newIsContact )
	{
		this.newIsContact = newIsContact;
	}

	/**
	 * Gets the new escrowAgent for the XRI authority object, or null if
	 * the identifier of the XRI authority object is not to be changed
	 */
	public EppXriTrustee getNewEscrowAgent()
	{
		return this.newEscrowAgent;
	}

	/**
	 * Sets the new escrowAgent flag for the XRI authority object if it needs
	 * to be changed
	 */
	public void setNewEscrowAgent( EppXriTrustee newEscrowAgent )
	{
		this.newEscrowAgent = newEscrowAgent;
	}

	/**
	 * Gets the new contactAgent for the XRI authority object, or null if
	 * the identifier of the XRI authority object is not to be changed
	 */
	public EppXriTrustee getNewContactAgent()
	{
		return this.newContactAgent;
	}

	/**
	 * Sets the new contactAgent for the XRI authority object if it needs
	 * to be changed
	 */
	public void setNewContactAgent( EppXriTrustee newContactAgent )
	{
		this.newContactAgent = newContactAgent;
	}

	/**
	 * Gets the new/updated social data associated with the XRI authority object
	 */
	public EppXriSocialData getNewSocialData()
	{
		return this.newSocialData;
	}

	/**
	 * Sets the new/updated social data associated with the XRI authority object
	 */
	public void setNewSocialData( EppXriSocialData newSocialData )
	{
		this.newSocialData = newSocialData;
		this.addSocialData = null;
		this.nullifySocialData = false;
	}

	/**
	 * Gets the social data to be associated with the XRI authority object
	 */
	public EppXriSocialData getSocialData()
	{
		return this.addSocialData;
	}

	/**
	 * Sets the social data to be associated with the XRI authority object
	 */
	public void setSocialData( EppXriSocialData addSocialData )
	{
		this.addSocialData = addSocialData;
		this.newSocialData = null;
		this.nullifySocialData = false;
	}

	/*
	 * Gets the flag for nullifying existing social data associated 
	 * with the XRI authority object
	 */
	public boolean isSocialDataNullified()
	{
		return this.nullifySocialData;
	}
	
	/**
	 * Sets the flag for nullifying existing social data associated 
	 * with the XRI authority object
	 */
	public void nullifySocialData()
	{
		this.nullifySocialData = true;
		this.addSocialData = null;
		this.newSocialData = null;
	}

	/**
	 * Gets the CanonicalEquivID to be associated with the XRI authority object
	 */
	public String getCanonicalEquivID()
	{
		return this.addCanonicalEquivID;
	}

	/**
	 * Sets the CanonicalEquivID to be associated with the XRI authority object
	 */
	public void setCanonicalEquivID( String addCanonicalEquivID )
	{
		this.addCanonicalEquivID = addCanonicalEquivID;
	}

	/*
	 * Gets the CanonicalEquivID to be removed from the XRI authority object
	 */
	public String getCanonicalEquivIDRemoved()
	{
		return this.remCanonicalEquivID;
	}
	
	/**
	 * Sets the CanonicalEquivID to be removed from the XRI authority object
	 */
	public void removeCanonicalEquivID(String remCanonicalEquivID)
	{
		this.remCanonicalEquivID = remCanonicalEquivID;
	}

	/*
	 * Gets the new authorization information for the XRI authority object, or null if
	 * the authorization information of the XRI authority object is not to be changed
	 */
	public EppAuthInfo getNewAuthInfo()
	{
		return this.newAuthInfo;
	}

	/**
	 * Sets the new authorization information for the XRI authority object if it needs
	 * to be changed
	 */
	public void setNewAuthInfo( EppAuthInfo authInfo )
	{
		this.newAuthInfo = authInfo;
	}

	/**
	 * Gets the authorization info for the update operation
	 */
	public EppAuthInfo getAuthInfo()
	{
		return this.authInfo;
	}

	/**
	 * Sets the authorization info for the update operation
	 */
	public void setAuthInfo( EppAuthInfo authInfo )
	{
		this.authInfo = authInfo;
	}

	/**
	 * Converts the <code>EppCommandUpdateXriAuthority</code> object into 
	 * an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the 
	 *            <code>EppCommandUpdateXriAuthority</code> object
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
		if(    (statusAdded.size()  > 0)
		    || (trusteeAdded.size() > 0)
		    || (refAdded.size()      > 0)
		    || (redirectAdded.size()      > 0)
		    || (equivIDAdded.size()      > 0)
		    || (sepAdded.size()     > 0)
		    || (addSocialData != null)
		    || (addCanonicalEquivID != null)
				|| (contactAdded.size()      > 0) )
		{
			elm = doc.createElement("add");
			if( addSocialData != null )
			{
				elm.appendChild(addSocialData.toXML(doc, "socialData"));
			}
			trusteeToXML (doc, elm, trusteeAdded);
			refToXML     (doc, elm, refAdded);
			redirectToXML(doc, elm, redirectAdded);
			equivIDToXML (doc, elm, equivIDAdded);
			if( addCanonicalEquivID != null )
			{
				Element ceidAdd = doc.createElement("canonicalEquivID");
				ceidAdd.appendChild(doc.createTextNode(addCanonicalEquivID));
				elm.appendChild(ceidAdd);
			}
			sepToXML     (doc, elm, sepAdded);
			authContactToXML(doc, elm, contactAdded);
			statusToXML  (doc, elm, statusAdded);
			body.appendChild(elm);
		}
		if(    (statusRemoved.size()   > 0)
		    || (trusteeRemoved.size()  > 0)
		    || (refRemoved.size()      > 0)
		    || (redirectRemoved.size() > 0)
		    || (equivIDRemoved.size()  > 0)
		    || (sepRemoved.size()      > 0)
		    || (nullifySocialData == true)
		    || (remCanonicalEquivID != null)
				|| (contactRemoved.size()  > 0 ) )
		{
			elm = doc.createElement("rem");
			if( nullifySocialData == true )
			{
				elm.appendChild(doc.createElement("socialData"));
			}
			trusteeToXML(doc, elm, trusteeRemoved);
			stringToXML (doc, elm, refRemoved , "ref" , null);
			stringToXML (doc, elm, redirectRemoved , "redirect" , null);
			stringToXML (doc, elm, equivIDRemoved , "equivID" , null);
			if( remCanonicalEquivID != null )
			{
				Element ceidRem = doc.createElement("canonicalEquivID");
				ceidRem.appendChild(doc.createTextNode(remCanonicalEquivID));
				elm.appendChild(ceidRem);
			}
			stringToXML (doc, elm, sepRemoved, "sep", "id");
			authContactToXML(doc, elm, this.contactRemoved);
			statusToXML (doc, elm, statusRemoved);
			body.appendChild(elm);
		}
		if(    (newAuthId       != null)
		    || (newIsEscrow     != null)
		    || (newIsContact    != null)
		    || (newEscrowAgent  != null)
		    || (newContactAgent != null)
		    || (newSocialData   != null)
		    || (newAuthInfo     != null)
		    || (newExtension    != null) )
		{
			elm = doc.createElement("chg");
			if( newAuthId != null )
			{
				Element authId = doc.createElement("authId");
				authId.appendChild(doc.createTextNode(newAuthId));
				elm.appendChild(authId);
			}
			if( newIsEscrow != null )
			{
				Element flg = doc.createElement("isEscrow");
				flg.appendChild(doc.createTextNode(newIsEscrow.toString()));
				elm.appendChild(flg);
			}
			if( newIsContact != null )
			{
				Element flg = doc.createElement("isContact");
				flg.appendChild(doc.createTextNode(newIsContact.toString()));
				elm.appendChild(flg);
			}
			if( newEscrowAgent != null )
			{
				elm.appendChild(newEscrowAgent.toXML(doc, "escrowAgent"));
			}
			if( newContactAgent != null )
			{
				elm.appendChild(newContactAgent.toXML(doc, "contactAgent"));
			}
			if( newSocialData != null )
			{
				elm.appendChild(newSocialData.toXML(doc, "socialData"));
			}
			if( newAuthInfo != null )
			{
				elm.appendChild(newAuthInfo.toXML(doc, "authInfo"));
			}
			if( newExtension != null )
			{
				Element ext = doc.createElement("extension");
				ext.appendChild(doc.createTextNode(newExtension));
				elm.appendChild(ext);
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
	 * Converts an XML element into an <code>EppCommandUpdateAuthority</code>
	 * object. The caller of this method must make sure that the root node
	 * is of an EPP Command Update entity for an EPP XRI Authority object.
	 *
	 * @param root root node for an <code>EppCommandUpdateAuthority</code>
	 *             object in XML format
	 *
	 * @return an <code>EppCommandUpdateAuthority</code> object, or null
	 *         if the node is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		EppCommandUpdateXriAuthority cmd = null;
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
					cmd = new EppCommandUpdateXriAuthority(authId);
				}
				else
				{
					cmd.setAuthorityId(authId);
				}
			}
			else if( name.equals("add") )
			{
				if( cmd != null )
				{
					cmd.socialDataFromXML(node, true);
					cmd.canonicalEquivIDFromXML(node, true);
					cmd.trusteeFromXML(node, cmd.trusteeAdded);
					cmd.refFromXML     (node, cmd.refAdded);
					cmd.redirectFromXML     (node, cmd.redirectAdded);
					cmd.equivIDFromXML     (node, cmd.equivIDAdded);
					cmd.sepFromXML    (node, cmd.sepAdded);
					cmd.statusFromXML (node, cmd.statusAdded);
					cmd.authContactFromXML(node, cmd.contactAdded);
				}
			}
			else if( name.equals("rem") )
			{
				if( cmd != null )
				{
					cmd.socialDataFromXML(node, false);
					cmd.canonicalEquivIDFromXML(node, false);
					cmd.trusteeFromXML(node, cmd.trusteeRemoved);
					cmd.stringFromXML (node, cmd.refRemoved    , "ref" ,  null);
					cmd.stringFromXML (node, cmd.redirectRemoved    , "redirect" ,  null);
					cmd.stringFromXML (node, cmd.equivIDRemoved    , "equivID" ,  null);
					cmd.stringFromXML (node, cmd.sepRemoved   , "sep", "id");
					cmd.statusFromXML (node, cmd.statusRemoved);
					cmd.authContactFromXML(node, cmd.contactRemoved);
				}
			}
			else if( name.equals("chg") )
			{
				if( cmd != null )
				{
					cmd.addNewStuff(node);
				}
			}
			else if( name.equals("authInfo") )
			{
				if( cmd != null )
				{
					EppAuthInfo authInfo = (EppAuthInfo) EppAuthInfo.fromXML(node);
					if( authInfo != null )
					{
						cmd.setAuthInfo(authInfo);
					}
				}
			}
		}

		return cmd;
	}

	/**
	 * Adds elements included in the &lt;chg&gt tag of the update command
	 * for an XRI authority object
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
			if( name.equals("authId") )
			{
				String authId = EppUtil.getText(node);
				setNewAuthorityId(authId);
			}
			else if( name.equals("isEscrow") )
			{
				setNewIsEscrow((EppUtil.getTextAsBool(node))? Boolean.TRUE : Boolean.FALSE);
			}
			else if( name.equals("isContact") )
			{
				setNewIsContact((EppUtil.getTextAsBool(node))? Boolean.TRUE : Boolean.FALSE);
			}
			else if( name.equals("escrowAgent") )
			{
				EppXriTrustee agent = (EppXriTrustee) EppXriTrustee.fromXML(node);
				if (agent != null)
					setNewEscrowAgent(agent);
			}
			else if( name.equals("contactAgent") )
			{
				EppXriTrustee agent = (EppXriTrustee) EppXriTrustee.fromXML(node);
				if (agent != null)
					setNewContactAgent(agent);
			}
			else if( name.equals("socialData") )
			{
				EppXriSocialData socialData = (EppXriSocialData) EppXriSocialData.fromXML(node);
				setNewSocialData(socialData);
			}
			else if( name.equals("authInfo") )
			{
				EppAuthInfo authInfo = (EppAuthInfo) EppAuthInfo.fromXML(node);
				setNewAuthInfo(authInfo);
			}
			if( name.equals("extension") )
			{
				String ext = EppUtil.getText(node);
				setNewExtension(ext);
			}
		}
	}

	/**
	 * Converts a list of EPP XRI trustee objects into XML
	 *
	 * @param doc  the XML <code>Document</code> object
	 * @param body the XML <code>Element</code> object to which the list
	 *             of EPP XRI trustee objects is appended
	 * @param list the list of EPP XRI trustee objects to be converted
	 */
	private void trusteeToXML( Document doc, Element body, Vector list )
	{
		Element elm;
		if( list != null )
		{
			for( int i = 0; i < list.size(); i++ )
			{
				Object obj = list.elementAt(i);
				if( obj instanceof EppXriTrustee )
				{
					EppXriTrustee trustee = (EppXriTrustee) obj;
					elm = trustee.toXML(doc, "trustee");
					body.appendChild(elm);
				}
			}
		}
	}

	/**
	 * Converts a list of EPP XRI Trustee objects from XML
	 *
	 * @param root the XML <code>Node</code> object containing the list
	 *             of EPP XRI Trustee objects
	 * @param trusteeList the list of EPP XRI Trustee objects to be stored
	 */
	private void trusteeFromXML( Node root, Vector trusteeList )
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
			if( name.equals("trustee") )
			{
				EppXriTrustee trustee = (EppXriTrustee) EppXriTrustee.fromXML(node);
				trusteeList.addElement(trustee);
			}
		}
	}

	/**
	 * Converts a list of EPP XRI Ref objects into XML
	 *
	 * @param doc  the XML <code>Document</code> object
	 * @param body the XML <code>Element</code> object to which the list
	 *             of EPP XRI Ref objects is appended
	 * @param list the list of EPP XRI Ref objects to be converted
	 */
	private void refToXML( Document doc, Element body, Vector list )
	{
		Element elm;
		if( list != null )
		{
			for( int i = 0; i < list.size(); i++ )
			{
				Object obj = list.elementAt(i);
				if( obj instanceof EppXriRef )
				{
					EppXriRef ref = (EppXriRef) obj;
					elm = ref.toXML(doc, "ref");
					body.appendChild(elm);
				}
			}
		}
	}

	/**
	 * Converts a list of EPP XRI Ref objects from XML
	 *
	 * @param root the XML <code>Node</code> object containing the list
	 *             of EPP XRI Ref objects
	 * @param refList the list of EPP XRI Ref objects to be stored
	 */
	private void refFromXML( Node root, Vector refList )
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
			if( name.equals("ref") )
			{
				EppXriRef ref = (EppXriRef) EppXriRef.fromXML(node);
				refList.addElement(ref);
			}
		}
	}

	/**
	 * Converts a list of EPP XRI Redirect objects into XML
	 *
	 * @param doc  the XML <code>Document</code> object
	 * @param body the XML <code>Element</code> object to which the list
	 *             of EPP XRI Redirect objects is appended
	 * @param list the list of EPP XRI Redirect objects to be converted
	 */
	private void redirectToXML( Document doc, Element body, Vector list )
	{
		Element elm;
		if( list != null )
		{
			for( int i = 0; i < list.size(); i++ )
			{
				Object obj = list.elementAt(i);
				if( obj instanceof EppXriURI )
				{
					EppXriURI redir = (EppXriURI) obj;
					elm = redir.toXML(doc, "redirect");
					body.appendChild(elm);
				}
			}
		}
	}

	/**
	 * Converts a list of EPP XRI Redirect objects from XML
	 *
	 * @param root the XML <code>Node</code> object containing the list
	 *             of EPP XRI Redirect objects
	 * @param redirectList the list of EPP XRI Redirect objects to be stored
	 */
	private void redirectFromXML( Node root, Vector redirectList )
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
			if( name.equals("redirect") )
			{
				EppXriURI redir = (EppXriURI) EppXriURI.fromXML(node);
				redirectList.addElement(redir);
			}
		}
	}

	/**
	 * Converts a list of EPP XRI EquivID objects into XML
	 *
	 * @param doc  the XML <code>Document</code> object
	 * @param body the XML <code>Element</code> object to which the list
	 *             of EPP XRI EquivID objects is appended
	 * @param list the list of EPP XRI EquivID objects to be converted
	 */
	private void equivIDToXML( Document doc, Element body, Vector list )
	{
		Element elm;
		if( list != null )
		{
			for( int i = 0; i < list.size(); i++ )
			{
				Object obj = list.elementAt(i);
				if( obj instanceof EppXriSynonym )
				{
					EppXriSynonym equivID = (EppXriSynonym) obj;
					elm = equivID.toXML(doc, "equivID");
					body.appendChild(elm);
				}
			}
		}
	}

	/**
	 * Converts a list of EPP XRI EquivID objects from XML
	 *
	 * @param root the XML <code>Node</code> object containing the list
	 *             of EPP XRI EquivID objects
	 * @param equivIDList the list of EPP XRI EquivID objects to be stored
	 */
	private void equivIDFromXML( Node root, Vector equivIDList )
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
			if( name.equals("equivID") )
			{
				EppXriSynonym equivID = (EppXriSynonym) EppXriSynonym.fromXML(node);
				equivIDList.addElement(equivID);
			}
		}
	}

	/**
	 * Converts a list of EPP XRI service endpoint objects into XML
	 *
	 * @param doc  the XML <code>Document</code> object
	 * @param body the XML <code>Element</code> object to which the list
	 *             of EPP XRI service endpoint objects is appended
	 * @param list the list of EPP XRI service endpoint objects to be converted
	 */
	private void sepToXML( Document doc, Element body, Vector list )
	{
		Element elm;
		if( list != null )
		{
			for( int i = 0; i < list.size(); i++ )
			{
				Object obj = list.elementAt(i);
				if( obj instanceof EppXriServiceEndpoint )
				{
					EppXriServiceEndpoint sep = (EppXriServiceEndpoint) obj;
					elm = sep.toXML(doc, "sep");
					body.appendChild(elm);
				}
			}
		}
	}

	/**
	 * Converts a list of EPP XRI service endpoint objects from XML
	 *
	 * @param root the XML <code>Node</code> object containing the list
	 *             of EPP XRI service endpoint objects
	 * @param sepList the list of EPP XRI service endpoint objects to be stored
	 */
	private void sepFromXML( Node root, Vector sepList )
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
			if( name.equals("sep") )
			{
				EppXriServiceEndpoint sep = (EppXriServiceEndpoint) EppXriServiceEndpoint.fromXML(node);
				sepList.addElement(sep);
			}
		}
	}

	/**
	 * Converts a list of string objects into XML
	 *
	 * @param doc  the XML <code>Document</code> object
	 * @param body the XML <code>Element</code> object to which the list
	 *             of string objects is appended
	 * @param tag  the XML tag name for each string object
	 * @param child  the XML tag name for each string object, if it is a child element; or null
	 * @param list the list of string objects to be converted
	 */
	private void stringToXML( Document doc, Element body, Vector list, String tag, String child )
	{
		Element elm;
		if( list != null )
		{
			for( int i = 0; i < list.size(); i++ )
			{
				Object obj = list.elementAt(i);
				if( obj instanceof String )
				{
					elm = doc.createElement(tag);
					if( child == null )
					{
						elm.appendChild(doc.createTextNode((String) obj));
					}
					else
					{
						Element celm = doc.createElement(child);
						celm.appendChild(doc.createTextNode((String) obj));
						elm.appendChild(celm);
					}
					body.appendChild(elm);
				}
			}
		}
	}

	/**
	 * Converts a list of string objects from XML
	 *
	 * @param root the XML <code>Node</code> object containing the list
	 *             of string objects
	 * @param stringList the list of string objects to be stored
	 * @param tag  the XML tag name for each string object
	 * @param child  the XML tag name for each string object, if it is a child element; or null
	 */
	private void stringFromXML( Node root, Vector stringList, String tag, String child )
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
			if( name.equals(tag) )
			{
				if( child == null )
				{
					String s = EppUtil.getText(node);
					stringList.addElement(s);
				}
				else
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
						if( cname.equals(child) )
						{
							String s = EppUtil.getText(cnode);
							stringList.addElement(s);
							break;
						}
					}
				}
			}
		}
	}

	/**
	 * Converts social data added to/removed from EPP XRI Authoruty objects from XML
	 *
	 * @param root the XML <code>Node</code> object containing the list
	 *             of EPP XRI Trustee objects
	 * @param addOrRemove the flag indicating if the social data is to be added or removed
	 */
	private void socialDataFromXML( Node root, boolean addOrRemove )
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
			if( name.equals("socialData") )
			{
				if( addOrRemove == true )
				{
					EppXriSocialData data = (EppXriSocialData) EppXriSocialData.fromXML(node);
					this.setSocialData(data);
				}
				else
				{
					this.nullifySocialData();
				}
				break;
			}
		}
	}

	/**
	 * Converts CanonicalEquivID added to/removed from EPP XRI Authoruty objects from XML
	 *
	 * @param root the XML <code>Node</code> object containing the list
	 *             of EPP XRI Trustee objects
	 * @param addOrRemove the flag indicating if the CanonicalEquivID is to be added or removed
	 */
	private void canonicalEquivIDFromXML( Node root, boolean addOrRemove )
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
			if( name.equals("canonicalEquivID") )
			{
				String ceid = EppUtil.getText(node);
				if( addOrRemove == true )
					this.setCanonicalEquivID(ceid);
				else
					this.removeCanonicalEquivID(ceid);
				break;
			}
		}
	}
}
