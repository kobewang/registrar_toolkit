/*******************************************************************************
 * The MIT License (MIT)
 *  
 * Copyright (c)2015 Neustar Inc.
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

#if ! defined(EPPDATACOLLECTIONSTATEMENT_HPP)	/* { */
#define	      EPPDATACOLLECTIONSTATEMENT_HPP	   1

#include "EppEntity.hpp"

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
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:37 $
 */
class EPP_EXPORT EppDataCollectionStatement : public EppEntity
{
private:
	bool       purpose_admin;
	bool       purpose_contact;
	bool       purpose_other;
	bool       purpose_prov;

	bool       recipient_other;
	bool       recipient_ours;
	DOMString  recipient_ours_rec_desc;
	bool       recipient_public;
	bool       recipient_same;
	bool       recipient_unrelated;

	DOMString  retention;

public:
	/**
	 * DCP Statement Purpose Type - admin
	 *
	 * <P>Administrative purposes.  Information can be used for
	 * administrative and technical support of the provisioning system.
	 */
	static const char * PURPOSE_ADMIN;
	/**
	 * DCP Statement Purpose Type - contact
	 *
	 * <P>Contact for marketing purposes.  Information can be
	 * used to contact individuals, through a communications channel
	 * other than the protocol, for the promotion of a product or
	 * service.
	 */
	static const char * PURPOSE_CONTACT;
	/**
	 * DCP Statement Purpose Type - prov
	 *
	 * <P> Object provisioning purposes.  Information can be used to
	 * identify objects and inter-object relationships.
	 */
	static const char * PURPOSE_PROV;
	/**
	 * DCP Statement Purpose Type - other
	 *
	 * <P>Other purposes.  Information may be used in other ways
	 * not captured by the above definitions.
	 */
	static const char * PURPOSE_OTHER;
	/**
	 * DCP Statement Recipient Type - other
	 *
	 * <P>Other entities following unknown practices.
	 */
	static const char * RECIPIENT_OTHER;
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
	static const char * RECIPIENT_OURS;
	/**
	 * DCP Statement Recipient Type - public
	 *
	 * <P>Public forums.
	 */
	static const char * RECIPIENT_PUBLIC;
	/**
	 * DCP Statement Recipient Type - same
	 *
	 * <P>Other entities following server practices.
	 */
	static const char * RECIPIENT_SAME;
	/**
	 * DCP Statement Recipient Type - unrelated
	 *
	 * <P>Unrelated third parties.
	 */
	static const char * RECIPIENT_UNRELATED;
	/**
	 * DCP Statement Retention Type - business
	 *
	 * <P>Data persists per business practices.
	 */
	static const char * RETENTION_BUSINESS;
	/**
	 * DCP Statement Retention Type - indefinite
	 *
	 * <P>Data persists indefinitely.
	 */
	static const char * RETENTION_INDEFINITE;
	/**
	 * DCP Statement Retention Type - legal
	 *
	 * <P>Data persists per legal requirements.
	 */
	static const char * RETENTION_LEGAL;
	/**
	 * DCP Statement Retention Type - none
	 *
	 * <P>Data is not persistent, and is not retained for more
	 * than a brief period of time necessary to make use of it during
	 * the course of a single online interaction.
	 */
	static const char * RETENTION_NONE;
	/**
	 * DCP Statement Retention Type - stated
	 *
	 * <P>Data persists to meet the stated purpose.
	 */
	static const char * RETENTION_STATED;

	/**
	 * Creates an <code>EppDataCollectionStatement</code> object
	 */
	EppDataCollectionStatement()
	{
		purpose_admin   = false;
		purpose_contact = false;
		purpose_other   = false;
		purpose_prov    = false;

		recipient_other         = false;
		recipient_ours          = false;
		recipient_ours_rec_desc = NULLXS;
		recipient_public        = false;
		recipient_same          = false;
		recipient_unrelated     = false;

		retention               = NULLXS;
	};

	/**
	 * Destructor
	 */
	~EppDataCollectionStatement() {};

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
	bool getPurpose( DOMString purpose )
	{
		bool rtn = false;
		if( purpose.isNotNull() )
		{
			if( purpose.equals(PURPOSE_ADMIN) )
			{
				rtn = this->purpose_admin;
			}
			else if( purpose.equals(PURPOSE_CONTACT) )
			{
				rtn = this->purpose_contact;
			}
			else if( purpose.equals(PURPOSE_OTHER) )
			{
				rtn = this->purpose_other;
			}
			else if( purpose.equals(PURPOSE_PROV) )
			{
				rtn = this->purpose_prov;
			}
		}
		return rtn;
	};

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
	void setPurpose( DOMString purpose, bool flag )
	{
		if( purpose.isNotNull() )
		{
			if( purpose.equals(PURPOSE_ADMIN) )
			{
				this->purpose_admin = flag;
			}
			else if( purpose.equals(PURPOSE_CONTACT) )
			{
				this->purpose_contact = flag;
			}
			else if( purpose.equals(PURPOSE_OTHER) )
			{
				this->purpose_other = flag;
			}
			else if( purpose.equals(PURPOSE_PROV) )
			{
				this->purpose_prov = flag;
			}
		}
	};

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
	bool getRecipient( DOMString recipient )
	{
		bool rtn = false;
		if( recipient.isNotNull() )
		{
			if( recipient.equals(RECIPIENT_OTHER) )
			{
				rtn = this->recipient_other;
			}
			else if( recipient.equals(RECIPIENT_OURS) )
			{
				rtn = this->recipient_ours;
			}
			else if( recipient.equals(RECIPIENT_PUBLIC) )
			{
				rtn = this->recipient_public;
			}
			else if( recipient.equals(RECIPIENT_SAME) )
			{
				rtn = this->recipient_same;
			}
			else if( recipient.equals(RECIPIENT_UNRELATED) )
			{
				rtn = this->recipient_unrelated;
			}
		}
		return rtn;
	};

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
	void setRecipient( DOMString recipient, bool flag )
	{
		if( recipient.isNotNull() )
		{
			if( recipient.equals(RECIPIENT_OTHER) )
			{
				this->recipient_other = flag;
			}
			else if( recipient.equals(RECIPIENT_OURS) )
			{
				this->recipient_ours = flag;
			}
			else if( recipient.equals(RECIPIENT_PUBLIC) )
			{
				this->recipient_public = flag;
			}
			else if( recipient.equals(RECIPIENT_SAME) )
			{
				this->recipient_same = flag;
			}
			else if( recipient.equals(RECIPIENT_UNRELATED) )
			{
				this->recipient_unrelated = flag;
			}
		}
	};

	/**
	 * Gets the description of "ours" recipient, when
	 * the <I>RECIPIENT_OURS</I> flag is set.
	 */
	DOMString getOursReceiptientDesc()
	{
		return this->recipient_ours_rec_desc;
	};

	/**
	 * Sets the description of "ours" recipient, when
	 * the <I>RECIPIENT_OURS</I> flag is set.
	 */
	void setOursRecipientDesc( DOMString desc )
	{
		this->recipient_ours_rec_desc = desc;
	};

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
	DOMString getRetention()
	{
		return this->retention;
	};

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
	void setRetention( DOMString type )
	{
		this->retention = type;
	};

	/**
	 * Converts the <code>EppDataCollectionStatement</code> object into an XML element
	 *
	 * @param doc the XML <code>DOMDocument</code> object
	 * @param tag the tag/element name for the <code>EppDataCollectionStatement</code> object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );

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
	static EppDataCollectionStatement * fromXML( const DOMNode& root );

	DOMString toString()
	{
		return EppEntity::toString(DOMString("statement"));
	};
};

#endif	/*    EPPDATACOLLECTIONSTATEMENT_HPP */	/* } */
