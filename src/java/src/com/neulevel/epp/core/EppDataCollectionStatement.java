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

/**
 * This <code>EppDataCollectionStatement</code> class implements EPP Data
 * Collection Statement for the Data Collection Policy (DCP) entity.
 *
 * <P>An EPP Data Collection Statement object contains 3 components:
 * <UL>
 * <LI>Data collection purposes</LI>
 * <LI>Data recipients</LI>
 * <LI>Data retention pratices</LI>
 * </UL>
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppDataCollectionStatement extends EppEntity
{
	/**
	 * DCP Statement Purpose Type - admin
	 *
	 * <P>Administrative purposes.  Information can be used for
	 * administrative and technical support of the provisioning system.
	 */
	public static final String PURPOSE_ADMIN = "admin";
	/**
	 * DCP Statement Purpose Type - contact
	 *
	 * <P>Contact for marketing purposes.  Information can be
	 * used to contact individuals, through a communications channel
	 * other than the protocol, for the promotion of a product or
	 * service.
	 */
	public static final String PURPOSE_CONTACT = "contact";
	/**
	 * DCP Statement Purpose Type - prov
	 *
	 * <P> Object provisioning purposes.  Information can be used to
	 * identify objects and inter-object relationships.
	 */
	public static final String PURPOSE_PROV = "prov";
	/**
	 * DCP Statement Purpose Type - other
	 *
	 * <P>Other purposes.  Information may be used in other ways
	 * not captured by the above definitions.
	 */
	public static final String PURPOSE_OTHER = "other";
	/**
	 * DCP Statement Recipient Type - other
	 *
	 * <P>Other entities following unknown practices.
	 */
	public static final String RECIPIENT_OTHER = "other";
	/**
	 * DCP Statement Recipient Type - ours
	 *
	 * <P>Server operator and/or entities acting as agents or
	 * entities for whom the server operator is acting as an agent.
	 * An agent in this instance is defined as a third party that
	 * processes data only on behalf of the service provider for the
	 * completion of the stated purposes. An optional description
	 * string can be used to describe the recipient.
	 */
	public static final String RECIPIENT_OURS = "ours";
	/**
	 * DCP Statement Recipient Type - public
	 *
	 * <P>Public forums.
	 */
	public static final String RECIPIENT_PUBLIC = "public";
	/**
	 * DCP Statement Recipient Type - same
	 *
	 * <P>Other entities following server practices.
	 */
	public static final String RECIPIENT_SAME = "same";
	/**
	 * DCP Statement Recipient Type - unrelated
	 *
	 * <P>Unrelated third parties.
	 */
	public static final String RECIPIENT_UNRELATED = "unrelated";
	/**
	 * DCP Statement Retention Type - business
	 *
	 * <P>Data persists per business practices.
	 */
	public static final String RETENTION_BUSINESS = "business";
	/**
	 * DCP Statement Retention Type - indefinite
	 *
	 * <P>Data persists indefinitely.
	 */
	public static final String RETENTION_INDEFINITE = "indefinite";
	/**
	 * DCP Statement Retention Type - legal
	 *
	 * <P>Data persists per legal requirements.
	 */
	public static final String RETENTION_LEGAL = "legal";
	/**
	 * DCP Statement Retention Type - none
	 *
	 * <P>Data is not persistent, and is not retained for more
	 * than a brief period of time necessary to make use of it during
	 * the course of a single online interaction.
	 */
	public static final String RETENTION_NONE = "none";
	/**
	 * DCP Statement Retention Type - stated
	 *
	 * <P>Data persists to meet the stated purpose.
	 */
	public static final String RETENTION_STATED = "stated";

	private boolean    purpose_admin;
	private boolean    purpose_contact;
	private boolean    purpose_other;
	private boolean    purpose_prov;

	private boolean    recipient_other;
	private boolean    recipient_ours;
	private String     recipient_ours_rec_desc;
	private boolean    recipient_public;
	private boolean    recipient_same;
	private boolean    recipient_unrelated;

	private String	   retention;

	/**
	 * Creates an <code>EppDataCollectionStatement</code> object
	 */
	public EppDataCollectionStatement()
	{
		purpose_admin   = false;
		purpose_contact = false;
		purpose_other   = false;
		purpose_prov    = false;

		recipient_other         = false;
		recipient_ours          = false;
		recipient_ours_rec_desc = null;
		recipient_public        = false;
		recipient_same          = false;
		recipient_unrelated     = false;

		retention               = null;
	}

	/**
	 * Gets the flag of a statement purpose, for one of the following
	 * purpose types:
	 *
	 * <UL>
	 * <LI>PURPOSE_ADMIN</LI>
	 * <LI>PURPOSE_CONTACT</LI>
	 * <LI>PURPOSE_OTHER</LI>
	 * <LI>PURPOSE_PROV</LI>
	 * </UL>
	 */
	public boolean getPurpose( String purpose )
	{
		boolean rtn = false;
		if( purpose != null )
		{
			if( purpose.equals(PURPOSE_ADMIN) )
			{
				rtn = this.purpose_admin;
			}
			else if( purpose.equals(PURPOSE_CONTACT) )
			{
				rtn = this.purpose_contact;
			}
			else if( purpose.equals(PURPOSE_OTHER) )
			{
				rtn = this.purpose_other;
			}
			else if( purpose.equals(PURPOSE_PROV) )
			{
				rtn = this.purpose_prov;
			}
		}
		return rtn;
	}

	/**
	 * Sets the flag of a statement purpose, for one of the following
	 * purpose types:
	 *
	 * <UL>
	 * <LI>PURPOSE_ADMIN</LI>
	 * <LI>PURPOSE_CONTACT</LI>
	 * <LI>PURPOSE_OTHER</LI>
	 * <LI>PURPOSE_PROV</LI>
	 * </UL>
	 */
	public void setPurpose( String purpose, boolean flag )
	{
		if( purpose != null )
		{
			if( purpose.equals(PURPOSE_ADMIN) )
			{
				this.purpose_admin = flag;
			}
			else if( purpose.equals(PURPOSE_CONTACT) )
			{
				this.purpose_contact = flag;
			}
			else if( purpose.equals(PURPOSE_OTHER) )
			{
				this.purpose_other = flag;
			}
			else if( purpose.equals(PURPOSE_PROV) )
			{
				this.purpose_prov = flag;
			}
		}
	}

	/**
	 * Gets the flag of a statement recipient, for one of the following
	 * recipient types:
	 *
	 * <UL>
	 * <LI>RECIPIENT_OTHER</LI>
	 * <LI>RECIPIENT_OURS</LI>
	 * <LI>RECIPIENT_PUBLIC</LI>
	 * <LI>RECIPIENT_SAME</LI>
	 * <LI>RECIPIENT_UNRELATED</LI>
	 * </UL>
	 */
	public boolean getRecipient( String recipient )
	{
		boolean rtn = false;
		if( recipient != null )
		{
			if( recipient.equals(RECIPIENT_OTHER) )
			{
				rtn = this.recipient_other;
			}
			else if( recipient.equals(RECIPIENT_OURS) )
			{
				rtn = this.recipient_ours;
			}
			else if( recipient.equals(RECIPIENT_PUBLIC) )
			{
				rtn = this.recipient_public;
			}
			else if( recipient.equals(RECIPIENT_SAME) )
			{
				rtn = this.recipient_same;
			}
			else if( recipient.equals(RECIPIENT_UNRELATED) )
			{
				rtn = this.recipient_unrelated;
			}
		}
		return rtn;
	}

	/**
	 * Sets the flag of a statement recipient, for one of the following
	 * recipient types:
	 *
	 * <UL>
	 * <LI>RECIPIENT_OTHER</LI>
	 * <LI>RECIPIENT_OURS</LI>
	 * <LI>RECIPIENT_PUBLIC</LI>
	 * <LI>RECIPIENT_SAME</LI>
	 * <LI>RECIPIENT_UNRELATED</LI>
	 * </UL>
	 */
	public void setRecipient( String recipient, boolean flag )
	{
		if( recipient != null )
		{
			if( recipient.equals(RECIPIENT_OTHER) )
			{
				this.recipient_other = flag;
			}
			else if( recipient.equals(RECIPIENT_OURS) )
			{
				this.recipient_ours = flag;
			}
			else if( recipient.equals(RECIPIENT_PUBLIC) )
			{
				this.recipient_public = flag;
			}
			else if( recipient.equals(RECIPIENT_SAME) )
			{
				this.recipient_same = flag;
			}
			else if( recipient.equals(RECIPIENT_UNRELATED) )
			{
				this.recipient_unrelated = flag;
			}
		}
	}

	/**
	 * Gets the description of "ours" recipient, when
	 * the <I>RECIPIENT_OURS</I> flag is set.
	 */
	public String getOursReceiptientDesc()
	{
		return this.recipient_ours_rec_desc;
	}

	/**
	 * Sets the description of "ours" recipient, when
	 * the <I>RECIPIENT_OURS</I> flag is set.
	 */
	public void setOursRecipientDesc( String desc )
	{
		this.recipient_ours_rec_desc = desc;
	}

	/**
	 * Gets the retention type, with one of the following values;
	 *
	 * <UL>
	 * <LI>RETENTION_BUSINESS</LI>
	 * <LI>RETENTION_INDEFINITE</LI>
	 * <LI>RETENTION_LEGAL</LI>
	 * <LI>RETENTION_NONE</LI>
	 * <LI>RETENTION_STATED</LI>
	 * </UL>
	 */
	public String getRetention()
	{
		return this.retention;
	}

	/**
	 * Sets the retention type, with one of the following values:
	 *
	 * <UL>
	 * <LI>RETENTION_BUSINESS</LI>
	 * <LI>RETENTION_INDEFINITE</LI>
	 * <LI>RETENTION_LEGAL</LI>
	 * <LI>RETENTION_NONE</LI>
	 * <LI>RETENTION_STATED</LI>
	 * </UL>
	 */
	public void setRetention( String type )
	{
		this.retention = type;
	}

	/**
	 * Converts the <code>EppDataCollectionStatement</code> object into an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the <code>EppDataCollectionStatement</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Element body = doc.createElement(tag);
		Element elm;
		elm = doc.createElement("purpose");
		body.appendChild(elm);
		if( this.purpose_admin )
		{
			elm.appendChild(doc.createElement("admin"));
		}
		if( this.purpose_contact )
		{
			elm.appendChild(doc.createElement("contact"));
		}
		if( this.purpose_other )
		{
			elm.appendChild(doc.createElement("other"));
		}
		if( this.purpose_prov )
		{
			elm.appendChild(doc.createElement("prov"));
		}
		elm = doc.createElement("recipient");
		body.appendChild(elm);
		if( this.recipient_other )
		{
			elm.appendChild(doc.createElement("other"));
		}
		if( this.recipient_ours )
		{
			Element ours = doc.createElement("ours");
			if(    (this.recipient_ours_rec_desc != null)
			    && (this.recipient_ours_rec_desc.length() > 0) )
			{
				Element desc = doc.createElement("recDesc");
				desc.appendChild(doc.createTextNode(this.recipient_ours_rec_desc));
				ours.appendChild(desc);
			}
			elm.appendChild(ours);
		}
		if( this.recipient_public )
		{
			elm.appendChild(doc.createElement("public"));
		}
		if( this.recipient_same )
		{
			elm.appendChild(doc.createElement("same"));
		}
		if( this.recipient_unrelated )
		{
			elm.appendChild(doc.createElement("unrelated"));
		}
		if( this.retention != null )
		{
			elm = doc.createElement("retention");
			elm.appendChild(doc.createElement(this.retention));
			body.appendChild(elm);
		}

		return body;
	}

	/**
	 * Converts an XML element into an <code>EppDataCollectionStatement</code> object.
	 * The caller of this method must make sure that the root node is of
	 * the EPP Data Collection Statment.type.
	 *
	 * @param root root node for an <code>EppDataCollectionStatement</code> object in
	 *             XML format
	 *
	 * @return an <code>EppDataCollectionStatement</code> object, or null if the node is
	 *         invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		EppDataCollectionStatement stmt = new EppDataCollectionStatement();

		NodeList list = root.getChildNodes();
		for( int i = 0; i < list.getLength(); i++ )
		{
			Node node = list.item(i);
			String name = node.getLocalName();
			if( name == null )
			{
				continue;
			}
			if( name.equals("purpose") )
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
					if(    cname.equals(PURPOSE_ADMIN)
					    || cname.equals(PURPOSE_CONTACT)
					    || cname.equals(PURPOSE_PROV)
					    || cname.equals(PURPOSE_OTHER) )
					{
						stmt.setPurpose(cname, true);
					}
				}
			}
			else if( name.equals("recipient") )
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
					if(    cname.equals(RECIPIENT_OTHER)
					    || cname.equals(RECIPIENT_OURS)
					    || cname.equals(RECIPIENT_PUBLIC)
					    || cname.equals(RECIPIENT_SAME)
					    || cname.equals(RECIPIENT_UNRELATED) )
					{
						stmt.setRecipient(cname, true);
					}
					else
					{
						continue;
					}
					if( cname.equals(RECIPIENT_OURS) == false )
					{
						continue;
					}
					NodeList dlist = cnode.getChildNodes();
					for( int k = 0; k < dlist.getLength(); k++ )
					{
						Node dnode = dlist.item(k);
						String dname = dnode.getLocalName();
						if( dname == null )
						{
							continue;
						}
						if( dname.equals("recDesc") == false )
						{
							continue;
						}
						String desc = EppUtil.getText(node);
						stmt.setOursRecipientDesc(desc);
						break;
					}
				}
			}
			else if( name.equals("retention") )
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
					if(    cname.equals(RETENTION_BUSINESS)
					    || cname.equals(RETENTION_INDEFINITE)
					    || cname.equals(RETENTION_LEGAL)
					    || cname.equals(RETENTION_NONE)
					    || cname.equals(RETENTION_STATED) )
					{
						stmt.setRetention(cname);
					}
				}
			}
		}
		return stmt;
	}

	public String toString()
	{
		return toString("statement");
	}
}
