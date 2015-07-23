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
 * This <code>EppAddress</code> class implements EPP Address entity used in
 * the EPP Contact objects.
 *
 * <P>An EPP Address entity has 5 components:
 * <UL>
 * <LI>0 to 3 <I>street</I> address lines, with a maximum length of 255 characters.</LI>
 * <LI>a required <I>city</I> line, with a maximum length of 255 characters.</LI>
 * <LI>an optional "<I>state</I> or <I>province</I> line, with a maximum length of
 * 255 characters.</LI>
 * <LI>an optional <I>postal code</I> line, with a maximum length of 16 characters.</LI>
 * <LI>a required <I>country code</I> line.
 * For a full list of two-letter country codes, defined in ISO 3166,
 * the International Country Code Standard, please refer either the
 * <A HREF="http://www.iana.org/cctld/cctld.htm">IANA ccTLD Database</A> or
 * current <A HREF="http://www.iso.ch/iso/en/prods-services/iso3166ma/index.html">ISO
 * 3166 Maintenance Agency</A> web site.</LI>
 * </UL>
 *
 * <P>In EPP 04, the maximum length of the <I>street</I>, <I>city</I> and
 * <I>state</I>/<I>provice</I> fields was specified as 32, which was extended by
 * NeuLevel to 64. Also, the maximum length of a <I>postal code</I> field is 10.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.3 $ $Date: 2006/04/03 20:18:13 $
 */
public class EppAddress extends EppEntity
{
	/**
	 * Maximum number (lines) of streets in an address
	 */
	public static final int MAX_NUM_OF_STREETS = 3;

	private String street[];
	private String city;
	private String sp;		// state or province, optional
	private String pc;		// postal code
	private String cc;		// country code

	/**
	 * Creates an <code>EppAddress</code> object without any contents
	 */
	public EppAddress()
	{
		this((String[]) null, null, null, null, null);
	}

	/**
	 * Creates an <code>EppAddress</code> object without street addresses
	 */
	public EppAddress( String city, String sp, String pc, String cc )
	{
		this((String[]) null, city, sp, pc, cc);
	}

	/**
	 * Creates an <code>EppAddress</code> object with one street address
	 */
	public EppAddress( String street, String city, String sp, String pc, String cc )
	{
		this((String[]) null, city, sp, pc, cc);
		setStreet(0, street);
	}

	/**
	 * Creates an <code>EppAddress</code> object with full address information
	 */
	public EppAddress( String[] street, String city, String sp, String pc, String cc )
	{
		this.street = new String[EppAddress.MAX_NUM_OF_STREETS];
		this.setStreet(street);
		this.city = city;
		this.sp = sp;
		this.pc = pc;
		this.cc = cc;
	}

	/**
	 * Gets the street lines stored in a string array
	 */
	public String[] getStreet()
	{
		return (String[])this.street.clone();
	}

	/**
	 * Sets the street lines stored in a string array
	 */
	public void setStreet( String[] street )
	{
		if( street != null )
		{
			for( int i = 0; i < EppAddress.MAX_NUM_OF_STREETS; i++ )
			{
				if( i < street.length )
				{
					this.street[i] = street[i];
				}
				else
				{
					this.street[i] = null;
				}
			}
		}
	}

	/**
	 * Sets the street line, given an line index
	 */
	public void setStreet( int index, String street )
	{
		if( (index >= 0) && (index < EppAddress.MAX_NUM_OF_STREETS) )
		{
			this.street[index] = street;
		}
	}

	/**
	 * Gets the city string
	 */
	public String getCity()
	{
		return this.city;
	}

	/**
	 * Sets the city string
	 */
	public void setCity( String city )
	{
		this.city = city;
	}

	/**
	 * Gets the province or state string
	 */
	public String getState()
	{
		return this.sp;
	}

	/**
	 * Sets the province or state string
	 */
	public void setState( String sp )
	{
		this.sp = sp;
	}

	/**
	 * Gets the postal code
	 */
	public String getPostalCode()
	{
		return this.pc;
	}

	/**
	 * Sets the postal code
	 */
	public void setPostalCode( String pc )
	{
		this.pc = pc;
	}

	/**
	 * Gets the country code
	 */
	public String getCountryCode()
	{
		return this.cc;
	}

	/**
	 * Sets the country code in two characters
	 */
	public void setCountryCode( String cc )
	{
		this.cc = cc;
	}

	/**
	 * Converts the <code>EppAddress</code> object into an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the <code>EppAddress</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Element body = doc.createElement(tag);
		Element elm;

		for( int i = 0; i < street.length; i++ )
		{
			if( street[i] != null )
			{
				elm = doc.createElement("street");
				elm.appendChild(doc.createTextNode(street[i]));
				body.appendChild(elm);
			}
		}
		if( city != null )
		{
			elm = doc.createElement("city");
			elm.appendChild(doc.createTextNode(city));
			body.appendChild(elm);
		}
		if( sp != null )
		{
			elm = doc.createElement("sp");
			elm.appendChild(doc.createTextNode(sp));
			body.appendChild(elm);
		}
		if( pc != null )
		{
			elm = doc.createElement("pc");
			elm.appendChild(doc.createTextNode(pc));
			body.appendChild(elm);
		}
		if( cc != null )
		{
			elm = doc.createElement("cc");
			elm.appendChild(doc.createTextNode(cc));
			body.appendChild(elm);
		}
		return body;
	}

	/**
	 * Converts an XML element into an <code>EppAddress</code> object.
	 * The caller of this method must make sure that the root node is of
	 * the EPP addrType.
	 *
	 * @param root root node for an <code>EppAddress</code> object in XML format
	 *
	 * @return an <code>EppAddress</code> object, or null if the node is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		String street[] = null;
		String city = null;
		String sp = null;
		String pc = null;
		String cc = null;

		NodeList list = root.getChildNodes();
		for( int i = 0; i < list.getLength(); i++ )
		{
			Node node = list.item(i);
			String name = node.getLocalName();
			if( name == null )
			{
				continue;
			}
			if( name.equals("street") )
			{
				if( street == null )
				{
					street = new String[EppAddress.MAX_NUM_OF_STREETS];
				}
				for( int j = 0; j < street.length; j++ )
				{
					if( street[j] == null )
					{
						street[j] = EppUtil.getText(node);
						break;
					}
				}
			}
			else if( name.equals("city") )
			{
				city = EppUtil.getText(node);
			}
			else if( name.equals("sp") )
			{
				sp = EppUtil.getText(node);
			}
			else if( name.equals("pc") )
			{
				pc = EppUtil.getText(node);
			}
			else if( name.equals("cc") )
			{
				cc = EppUtil.getText(node);
			}
		}

		return new EppAddress(street, city, sp, pc, cc);
	}

	public String toString()
	{
		return toString("address");
	}
}
