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

#if ! defined(EPPADDRESS_HPP)    /* { */
#define       EPPADDRESS_HPP        1

#include "EppEntity.hpp"

#define	MAX_NUM_OF_STREETS	3

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
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:37 $
 */
class EPP_EXPORT EppAddress : public EppEntity
{
private:
	DOMString street[MAX_NUM_OF_STREETS];
	DOMString city;
	DOMString sp;
	DOMString pc;
	DOMString cc;

public:
	/**
	 * Creates an <code>EppAddress</code> object without any contents
	 */
	EppAddress();

	/**
	 * Creates an <code>EppAddress</code> object without street addresses
	 */
	EppAddress( DOMString city, DOMString sp, DOMString pc, DOMString cc );

	/**
	 * Creates an <code>EppAddress</code> object with one street address
	 */
	EppAddress( DOMString street, DOMString city, DOMString sp, DOMString pc, DOMString cc );

	/**
	 * Creates an <code>EppAddress</code> object with full address information
	 */
	EppAddress( DOMString street[], DOMString city, DOMString sp, DOMString pc, DOMString cc );

	/**
	 * Destructor
	 */
	~EppAddress() {};

	/**
	 * Gets the streets lines stored in a DOMString array
	 */
	DOMString * getStreet()
	{
		return this->street;
	};

	/**
	 * Sets the street lines stored in a string array
	 */
	void setStreet( DOMString street[] );

	/**
	 * Sets the street line, given an line index
	 */
	void setStreet( int index, DOMString street );

	/**
	 * Gets the city string
	 */
	DOMString getCity()
	{
		return this->city;
	};

	/**
	 * Sets the city string
	 */
	void setCity( DOMString city )
	{
		this->city = city;
	};

	/**
	 * Gets the province or state string
	 */
	DOMString getState()
	{
		return this->sp;
	};

	/**
	 * Sets the province or state string
	 */
	void setState( DOMString sp )
	{
		this->sp = sp;
	};

	/**
	 * Gets the postal code
	 */
	DOMString getPostalCode()
	{
		return this->pc;
	};

	/**
	 * Sets the postal code
	 */
	void setPostalCode( DOMString pc )
	{
		this->pc = pc;
	};

	/**
	 * Gets the country code
	 */
	DOMString getCountryCode()
	{
		return this->cc;
	};

	/**
	 * Sets the country code
	 */
	void setCountryCode( DOMString cc )
	{
		this->cc = cc;
	};

	/**
	 * Checks if an address is empty
	 */
	bool isNull()
	{
		for( int i = 0; i < MAX_NUM_OF_STREETS; i++ )
		{
			if( this->street[i].isNotNull() )
			{
				return false;
			}
		}
		if( this->city.isNotNull() )
		{
			return false;
		}
		if( this->sp.isNotNull() )
		{
			return false;
		}
		if( this->pc.isNotNull() )
		{
			return false;
		}
		if( this->cc.isNotNull() )
		{
			return false;
		}
		return true;
	};

	/**
	 * Converts an XML element into an <code>EppAddress</code> object.
	 * The caller of this method must make sure that the root node is of
	 * the EPP addrType.
	 *
	 * @param root root node for an <code>EppAddress</code> object in XML format
	 *
	 * @return an <code>EppAddress</code> object, or null if the node is invalid
	 */
	static EppAddress * fromXML( const DOMNode &root );

	/**
	 * Converts the <code>EppAddress</code> object into an XML element
	 *
	 * @param doc the XML <code>DOMElement</code> object
	 * @param tag the tag/element name for the <code>EppAddress</code> object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );

	/**
	 * Convert an EppAddress into XML an string, with its default tag name
	 */
	DOMString toString()
	{
		return EppEntity::toString(DOMString("address"));
	};
};

#endif     /* EPPADDRESS_HPP */  /* } */
