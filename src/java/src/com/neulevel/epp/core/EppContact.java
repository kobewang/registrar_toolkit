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
package com.neulevel.epp.core;

import org.w3c.dom.*;
import com.neulevel.epp.core.command.*;

/**
 * This <code>EppContact</code> class implements EPP Contact objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppContact extends EppObject
{
	/**
	 * Contact status - clientDeleteProhibited
	 */
	public static final String STATUS_CLIENT_DELETE_PROHIBITED = "clientDeleteProhibited";
	/**
	 * Contact status - clientTransferProhibited
	 */
	public static final String STATUS_CLIENT_TRANSFER_PROHIBITED = "clientTransferProhibited";
	/**
	 * Contact status - clientUpdateProhibited
	 */
	public static final String STATUS_CLIENT_UPDATE_PROHIBITED = "clientUpdateProhibited";
	/**
	 * Contact status - linked
	 */
	public static final String STATUS_LINKED = "linked";
	/**
	 * Contact status - ok
	 */
	public static final String STATUS_OK = "ok";
	/**
	 * Contact status - pendingCreate (EPP-1.0)
	 */
	public static final String STATUS_PENDING_CREATE = "pendingCreate";
	/**
	 * Contact status - pendingDelete
	 */
	public static final String STATUS_PENDING_DELETE = "pendingDelete";
	/**
	 * Contact status - pendingTransfer
	 */
	public static final String STATUS_PENDING_TRANSFER = "pendingTransfer";
	/**
	 * Contact status - pendingUpdate (EPP-1.0)
	 */
	public static final String STATUS_PENDING_UPDATE = "pendingUpdate";
	/**
	 * Contact status - serverDeleteProhibited
	 */
	public static final String STATUS_SERVER_DELETE_PROHIBITED = "serverDeleteProhibited";
	/**
	 * Contact status - serverTransferProhibited
	 */
	public static final String STATUS_SERVER_TRANSFER_PROHIBITED = "serverTransferProhibited";
	/**
	 * Contact status - serverUpdateProhibited
	 */
	public static final String STATUS_SERVER_UPDATE_PROHIBITED = "serverUpdateProhibited";

	private String         id;
	private EppContactData ascii;
	private EppContactData i15d;          // optional
	private EppE164        voice;         // optional
	private EppE164        fax;           // optional
	private String         email;

	private EppContactDisclose	disclose;	// optional
	/**
	 * Creates an <code>EppContact</code> object
	 */
	public EppContact()
	{
	}

	/**
	 * Creates an <code>EppContact</code> object with a client-supplied
	 * contact id
	 */
	public EppContact( String id )
	{
		this.id = id;
	}

	/**
	 * Gets the contact id
	 */
	public String getId()
	{
		return this.id;
	}

	/**
	 * Sets the contact id
	 */
	public void setId( String id )
	{
		this.id = id;
	}

	/**
	 * Gets the contact data in internationalized encoding (ASCII)
	 */
	public EppContactData getContactDataInt()
	{
		return this.ascii;
	}

	/**
	 * Sets the contact data in internationalized encoding (ASCII)
	 */
	public void setContactDataInt( EppContactData int_data )
	{
		this.ascii = int_data;
	}

	/**
	 * Gets the contact data in native encoding (UTF-8)
	 */
	public EppContactData getContactDataLoc()
	{
		return this.i15d;
	}

	/**
	 * Sets the contact data in native encoding (UTF-8)
	 */
	public void setContactDataLoc( EppContactData loc_data )
	{
		this.i15d = loc_data;
	}

	/**
	 * Gets the contact data in ascii encoding - alias to getContactDataInt()
	 */
	public EppContactData getContactDataAscii()
	{
		return this.ascii;
	}

	/**
	 * Sets the contact data in ascii encoding - alias to setContactDataInt()
	 */
	public void setContactDataAscii( EppContactData ascii )
	{
		this.ascii = ascii;
	}

	/**
	 * Gets the contact data in native encoding - alias to getContactDataLoc()
	 */
	public EppContactData getContactDataI15d()
	{
		return this.i15d;
	}

	/**
	 * Sets the contact data in native encoding - alias to setContactDataLoc()
	 */
	public void setContactDataI15d( EppContactData i15d )
	{
		this.i15d = i15d;
	}

	/**
	 * Gets the voice phone number of the contact
	 */
	public EppE164 getVoice()
	{
		return this.voice;
	}

	/**
	 * Sets the voice phone number of the contact
	 */
	public void setVoice( String voice )
	{
		this.voice = new EppE164(voice);
	}

	/**
	 * Sets the voice phone number and extenstion
	 */
	public void setVoice( String voice, String ext )
	{
		this.voice = new EppE164(voice, ext);
	}

	/**
	 * Sets the voice phone number of the contact
	 */
	public void setVoice( EppE164 voice )
	{
		this.voice = voice;
	}

	/**
	 * Gets the fax number of the contact
	 */
	public EppE164 getFax()
	{
		return this.fax;
	}

	/**
	 * Sets the fax number of the contact
	 */
	public void setFax( String fax )
	{
		this.fax = new EppE164(fax);
	}

	/**
	 * Sets the fax number and extenstion
	 */
	public void setFax( String fax, String ext )
	{
		this.fax = new EppE164(fax, ext);
	}

	/**
	 * Sets the fax number extension
	 */
	public void setFax( EppE164 fax )
	{
		this.fax = fax;
	}

	/**
	 * Gets the email address of the contact
	 */
	public String getEmail()
	{
		return this.email;
	}

	/**
	 * Sets the email address of the contact
	 */
	public void setEmail( String email )
	{
		this.email = email;
	}

	/**
	 * Gets the EPP Contact Disclose object
	 */
	public EppContactDisclose getContactDisclose()
	{
		return this.disclose;
	}

	/**
	 * Sets the EPP Contact Disclose object
	 */
	public void setContactDisclose( EppContactDisclose disclose )
	{
		this.disclose = disclose;
	}

	/**
	 * Converts the <code>EppContact</code> object into an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the <code>EppContact</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Element elm;
		Element body = EppUtil.createElementNS(doc, "contact", tag);

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
		if( ascii != null )
		{
			elm = ascii.toXML(doc, "postalInfo");
			elm.setAttribute("type", "int");
			body.appendChild(elm);
		}
		if( i15d != null )
		{
			elm = i15d.toXML(doc, "postalInfo");
			elm.setAttribute("type", "loc");
			body.appendChild(elm);
		}
		if( voice != null )
		{
			body.appendChild(voice.toXML(doc, "voice"));
		}
		if( fax != null )
		{
			body.appendChild(fax.toXML(doc, "fax"));
		}
		if( email != null )
		{
			elm = doc.createElement("email");
			elm.appendChild(doc.createTextNode(email));
			body.appendChild(elm);
		}

		toXMLCommon(doc, body);

		if( disclose != null )
		{
			elm = disclose.toXML(doc, "disclose");
			body.appendChild(elm);
		}

		return body;
	}

	/**
	 * Converts an XML element into an <code>EppContact</code> object.
	 * The caller of this method must make sure that the root node is of
	 * the EPP Contact type.
	 *
	 * @param root root node for an <code>EppContact</code> object
	 *             in XML format
	 *
	 * @return an <code>EppContact</code> object, or null if the node
	 *         is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		EppContact contact = new EppContact();
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
				contact.id = EppUtil.getText(node);
			}
			else if( name.equals("postalInfo") )
			{
				String type = ((Element) node).getAttribute("type");
				if( (type != null) && type.equals("int") )
				{
					contact.ascii = (EppContactData) EppContactData.fromXML(node);
				}
				else if( (type != null) && type.equals("loc") )
				{
					contact.i15d = (EppContactData) EppContactData.fromXML(node);
				}
			}
			else if( name.equals("voice") )
			{
				contact.voice = (EppE164) EppE164.fromXML(node);
			}
			else if( name.equals("fax") )
			{
				contact.fax = (EppE164) EppE164.fromXML(node);
			}
			else if( name.equals("email") )
			{
				contact.email = EppUtil.getText(node);
			}
			else if( name.equals("disclose") )
			{
				contact.disclose = (EppContactDisclose) EppContactDisclose.fromXML(node);
			}
			else
			{
				contact.fromXMLCommon(node, name);
			}
		}

		return contact;
	}

	public String toString()
	{
		return toString("contact");
	}

	/**
	 * Creates an <code>EppCommandDeleteContact</code> object for
	 * deleting an EPP Contact object from the registry.
	 *
	 * @param id the id of the contact object to be deleted
	 * @param xid  the client transaction id associated with the operation
	 */
	public static EppCommandDeleteContact delete( String id, String xid )
	{
		return new EppCommandDeleteContact(id, xid);
	}

	/**
	 * Creates an <code>EppCommandInfoContact</code> object for
	 * querying the details of an EPP Contact object
	 *
	 * @param id the id of the contact object to be queried
	 * @param xid  the client transaction id associated with the operation
	 */
	public static EppCommandInfoContact info( String id, String xid )
	{
		return new EppCommandInfoContact(id, xid);
	}

	/**
	 * Creates an <code>EppCommandCheckContact</code> object for
	 * checking the existance of EPP Contact objects in the registry.
	 * ids of EPP Contact objects can be added via the
	 * <code>add</code> or <code>addRoid</code> methods.
	 *
	 * @param xid  the client transaction id associated with the operation
	 */
	public static EppCommandCheckContact check( String xid )
	{
		return new EppCommandCheckContact(xid);
	}

	/**
	 * Creates an <code>EppCommandTransferContact</code> object for
	 * transfering an EPP Contact object in the registry. The operation
	 * type and authorization information associated with the operation
	 * should be specified via <code>setOperation</code> and
	 * <code>setAuthInfo</code> method.
	 *
	 * @param id the id of the contact object to be transferred
	 * @param xid  the client transaction id associated with the operation
	 */
	public static EppCommandTransferContact transfer( String id, String xid )
	{
		return new EppCommandTransferContact(id, xid);
	}

	/**
	 * Creates an <code>EppCommandUpdateContact</code> object for
	 * updating an EPP Contact object in the registry. The actual update
	 * information should be specified via the various methods defined
	 * for the <code>EppCommandUpdateContact</code> object.
	 *
	 * @param id the id of the contact object to be updated
	 * @param xid  the client transaction id associated with the operation
	 */
	public static EppCommandUpdateContact update( String id, String xid )
	{
		return new EppCommandUpdateContact(id, xid);
	}
}
