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

#if ! defined(EPPDATACOLLECTIONPOLICY_HPP)	/* { */
#define	      EPPDATACOLLECTIONPOLICY_HPP	   1

#include "time.h"
#include "EppDataCollectionStatement.hpp"


#define	MAX_NUM_OF_STATEMENTS	10

/**
 * This <code>EppDataCollectionPolicy</code> class implements EPP Data
 * Collection Policy (DCP) entity.
 *
 * <P>An EPP Data Collection Policy object describes the server's privacy
 * policy for data collection and management.  Policy implications usually extend
 * beyond the client-server relationship.  Both clients and servers
 * can have relationships with other entities that need to know the
 * server operator's data collection policy to make informed
 * provisioning decisions.  Policy information must be disclosed to
 * provisioning entities, though the method of disclosing policy data
 * outside of direct protocol interaction is beyond the scope of the EPP
 * specification.
 *
 * <P>An EPP Data Collection Policy object contains 3 components:
 * <UL>
 * <LI>Description of the access provided by the server to the
 *     client on behalf of the originating data source.</LI>
 * <LI>Description of data collection purposes, data recipients,
 *     and data retention practices</LI>
 * <LI>Description of the lifetime of the data collection policy
 *     (optional).</LI>
 * </UL>
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:37 $
 */
class EPP_EXPORT EppDataCollectionPolicy : public EppEntity
{
private:
	DOMString				   access;
	OldValueVectorOf<EppDataCollectionStatement>* statement;
	time_t				           expiry_absolute;
	//
	// FIXME(ning.zhang@neustar,com)
	//
	// this needs to be defined as javax.xml.datatype.Duration in JDK 1.5
	//
	DOMString				   expiry_relative;

public:
	/**
	 * DCP Access Type - all
	 *
	 * <P>Access is given to all identified data.
	 */
	static const char * ACCESS_ALL;
	/**
	 * DCP Access Type - none:
	 *
	 * <P>No access is provided to identified data.
	 */
	static const char * ACCESS_NONE;
	/**
	 * DCP Access Type - null
	 *
	 * <P>Data is not persistent, so no access is possible.
	 */
	static const char * ACCESS_NULL;
	/**
	 * DCP Access Type - personal
	 *
	 * <P>Access is given to identified data relating to
	 * individuals and organizational entities.
	 */
	static const char * ACCESS_PERSONAL;
	/**
	 * DCP Access Type - personalAndOther
	 *
	 * <P>Access is given to identified data
	 * relating to individuals, organizational entities, and other
	 * data of a non-personal nature.
	 */
	static const char * ACCESS_PERSONAL_AND_OTHER;
	/**
	 * DCP Access Type - other
	 *
	 * <P>Access is given to other identified data of a non-personal nature.
	 */
	static const char * ACCESS_OTHER;

	/**
	 * Creates an <code>EppDataCollectionPolicy</code> object
	 */
	EppDataCollectionPolicy()
	{
		this->access = NULLXS;
		this->statement = new OldValueVectorOf<EppDataCollectionStatement>(MAX_NUM_OF_STATEMENTS);
		this->expiry_absolute = null;
		this->expiry_relative = NULLXS;
	};

	/**
	 * Destructor
	 */
	~EppDataCollectionPolicy()
	{
		if( this->statement != null )
		{
			delete this->statement;
		}
		this->access = NULLXS;
		this->statement = null;
		this->expiry_absolute = null;
		this->expiry_relative = NULLXS;
	};

	/**
	 * Gets data access policy
	 */
	DOMString getAccess()
	{
		return this->access;
	};

	/**
	 * Sets data access policy, with one of the following values
	 *
	 * <UL>
	 * <LI>ACCESS_ALL</LI>
	 * <LI>ACCESS_NONE</LI>
	 * <LI>ACCESS_NULL</LI>
	 * <LI>ACCESS_PERSONAL</LI>
	 * <LI>ACCESS_PERSONAL_AND_OTHER</LI>
	 * <LI>ACCESS_OTHER</LI>
	 * </UL>
	 */
	void setAccess( DOMString type )
	{
		this->access = type;
	};

	/**
	 * Gets the list of data collection statements
	 */
	OldValueVectorOf<EppDataCollectionStatement> * getStatement()
	{
		return this->statement;
	};

	/**
	 * Sets the list of data collection statements
	 */
	void setStatement( OldValueVectorOf<EppDataCollectionStatement> * list )
	{
		if( this->statement != null )
		{
			delete this->statement;
		}
		this->statement = list;
	};

	/**
	 * Adds a data collection statement
	 */
	void addStatement( EppDataCollectionStatement stmt )
	{
		if( this->statement == null )
		{
			this->statement = new OldValueVectorOf<EppDataCollectionStatement>(MAX_NUM_OF_STATEMENTS);
		}
		if( this->statement != null )
		{
			this->statement->addElement(stmt);
		}
	};

	/**
	 * Gets the absolute policy expiry timestamp
	 */
	time_t getExpiryAbsolute()
	{
		return this->expiry_absolute;
	};

	/**
	 * Gets the relative policy expiry timestamp
	 */
	DOMString getExpiryRelative()
	{
		return this->expiry_relative;
	};

	/**
	 * Sets the absolute policy expiry timestamp
	 */
	void setExpiry( time_t timestamp )
	{
		this->expiry_absolute = timestamp;
		this->expiry_relative = NULLXS;
	};

	/**
	 * Sets the relative policy expiry duration.
	 *
	 * <P>Please refer <A HREF="http://www.w3.org/TR/2001/REC-xmlschema-2-20010502/">
	 * XML Schema Part 2: Datatypes</A> for the exact definition and
	 * format of the XML <I>duration</I> data type.
	 *
	 * <P>FIXME(ning.zhang@neustar,com) - <I>duration</I> needs to be defined as
	 * javax.xml.datatype.Duration in JDK 1.5.
	 */
	void setExpiry( DOMString duration )
	{
		this->expiry_absolute = null;
		this->expiry_relative = duration;
	};

	/**
	 * Converts the <code>EppDataCollectionPolicy</code> object into an XML element
	 *
	 * @param doc the XML <code>DOMDocument</code> object
	 * @param tag the tag/element name for the <code>EppDataCollectionPolicy</code> object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );

	/**
	 * Converts an XML element into an <code>EppDataCollectionPolicy</code> object.
	 * The caller of this method must make sure that the root node is of
	 * the EPP Data Collection Statment.type.
	 *
	 * @param root root node for an <code>EppDataCollectionPolicy</code> object in
	 *             XML format
	 *
	 * @return an <code>EppDataCollectionPolicy</code> object, or null if the node is
	 *         invalid
	 */
	static EppDataCollectionPolicy * fromXML( const DOMNode& root );

	DOMString toString()
	{
		return EppEntity::toString(DOMString("policy"));
	};
};

#endif	/*    EPPDATACOLLECTIONPOLICY_HPP */	/* } */
