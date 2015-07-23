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

import java.util.*;
import org.w3c.dom.*;

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
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppDataCollectionPolicy extends EppEntity
{
	/**
	 * DCP Access Type - all
	 *
	 * <P>Access is given to all identified data.
	 */
	public static final String ACCESS_ALL = "all";
	/**
	 * DCP Access Type - none:
	 *
	 * <P>No access is provided to identified data.
	 */
	public static final String ACCESS_NONE = "none";
	/**
	 * DCP Access Type - null
	 *
	 * <P>Data is not persistent, so no access is possible.
	 */
	public static final String ACCESS_NULL = "null";
	/**
	 * DCP Access Type - personal
	 *
	 * <P>Access is given to identified data relating to
	 * individuals and organizational entities.
	 */
	public static final String ACCESS_PERSONAL = "personal";
	/**
	 * DCP Access Type - personalAndOther
	 *
	 * <P>Access is given to identified data
	 * relating to individuals, organizational entities, and other
	 * data of a non-personal nature.
	 */
	public static final String ACCESS_PERSONAL_AND_OTHER = "personalAndOther";
	/**
	 * DCP Access Type - other
	 *
	 * <P>Access is given to other identified data of a non-personal nature.
	 */
	public static final String ACCESS_OTHER = "other";

	private String           access;
	private Vector		 statement;
	private Calendar	 expiry_absolute;
	//
	// FIXME(ning.zhang@neustar,com)
	//
	// this needs to be defined as javax.xml.datatype.Duration in JDK 1.5
	//
	private String		 expiry_relative;

	/**
	 * Creates an <code>EppDataCollectionPolicy</code> object
	 */
	public EppDataCollectionPolicy()
	{
		this.access = null;
		this.statement = new Vector();
		this.expiry_absolute = null;
		this.expiry_relative = null;
	}

	/**
	 * Gets data access policy
	 */
	public String getAccess()
	{
		return this.access;
	}

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
	public void setAccess( String type )
	{
		this.access = type;
	}

	/**
	 * Gets the list of data collection statements
	 */
	public Vector getStatement()
	{
		return this.statement;
	}

	/**
	 * Sets the list of data collection statements
	 */
	public void setStatement( Vector list )
	{
		this.statement = list;
	}

	/**
	 * Adds a data collection statement
	 */
	public void addStatement( EppDataCollectionStatement stmt )
	{
		if( this.statement == null )
		{
			this.statement = new Vector();
		}
		this.statement.addElement(stmt);
	}

	/**
	 * Gets the absolute policy expiry timestamp
	 */
	public Calendar getExpiryAbsolute()
	{
		return this.expiry_absolute;
	}

	/**
	 * Gets the relative policy expiry timestamp
	 */
	public String getExpiryRelative()
	{
		return this.expiry_relative;
	}

	/**
	 * Sets the absolute policy expiry timestamp
	 */
	public void setExpiry( Calendar timestamp )
	{
		this.expiry_absolute = timestamp;
		this.expiry_relative = null;
	}

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
	public void setExpiry( String duration )
	{
		this.expiry_absolute = null;
		this.expiry_relative = duration;
	}

	/**
	 * Converts the <code>EppDataCollectionPolicy</code> object into an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the <code>EppDataCollectionPolicy</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Element body = doc.createElement(tag);
		Element elm;
		Element exp;
		if( this.access != null )
		{
			elm = doc.createElement("access");
			elm.appendChild(doc.createElement(this.access));
			body.appendChild(elm);
		}
		if( this.statement != null )
		{
			for( int i = 0; i < this.statement.size(); i++ )
			{
				Object obj = this.statement.elementAt(i);
				if( obj == null )
				{
					continue;
				}
				if( ! (obj instanceof EppDataCollectionStatement) )
				{
					continue;
				}
				EppDataCollectionStatement stmt;
				stmt = (EppDataCollectionStatement) obj;
				elm = stmt.toXML(doc, "statement");
				body.appendChild(elm);
			}
		}
		if( this.expiry_absolute != null )
		{
			elm = doc.createElement("expiry");
			exp = doc.createElement("absolute");
			exp.appendChild(EppUtil.createTextNode(doc, this.expiry_absolute));
			elm.appendChild(exp);
			body.appendChild(elm);
		}
		else if( this.expiry_relative != null )
		{
			elm = doc.createElement("expiry");
			exp = doc.createElement("relative");
			exp.appendChild(doc.createTextNode(this.expiry_relative));
			elm.appendChild(exp);
			body.appendChild(elm);
		}

		return body;
	}

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
	public static EppEntity fromXML( Node root )
	{
		EppDataCollectionPolicy policy = new EppDataCollectionPolicy();
		NodeList list = root.getChildNodes();
		for( int i = 0; i < list.getLength(); i++ )
		{
			Node node = list.item(i);
			String name = node.getLocalName();
			if( name == null )
			{
				continue;
			}
			if( name.equals("access") )
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
					if(    cname.equals("all")
					    || cname.equals("none")
					    || cname.equals("null")
					    || cname.equals("personal")
					    || cname.equals("personalAndOther")
					    || cname.equals("other") )
					{
						policy.setAccess(cname);
					}
				}
			}
			else if( name.equals("statement") )
			{
				EppDataCollectionStatement stmt;

				stmt = (EppDataCollectionStatement) EppDataCollectionStatement.fromXML(node);

				if( stmt != null )
				{
					policy.addStatement(stmt);
				}
			}
			else if( name.equals("expiry") )
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
					if( cname.equals("absolute") )
					{
						Calendar ts = EppUtil.getDate(cnode);
						if( ts != null )
						{
							policy.setExpiry(ts);
						}
					}
					else if( cname.equals("relative") )
					{
						String dp = EppUtil.getText(cnode);
						if( dp != null )
						{
							policy.setExpiry(dp);
						}
					}
				}
			}
		}
		return policy;
	}

	public String toString()
	{
		return toString("policy");
	}
}
