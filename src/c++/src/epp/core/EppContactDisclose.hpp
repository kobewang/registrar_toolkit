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

#if ! defined(EPPCONTACTDISCLOSE_HPP)	/* { */
#define	      EPPCONTACTDISCLOSE_HPP	   1

#include "EppEntity.hpp"

/**
 * This <code>EppContactDisclose</code> class implements EPP Contact
 * DiscloseType for recording contact data privacy information.
 *
 * <P>The privacy information contains the following 9 fields:
 * <UL>
 * <LI><I>name</I>, both "loc" and "int" form</LI>
 * <LI><I>org</I>,  both "loc" and "int" form</LI>
 * <LI><I>addr</I>, both "loc" and "int" form</LI>
 * <LI><I>voice</I>
 * <LI><I>fax</I>
 * <LI><I>email</I>
 * </UL>
 *
 * <P>A <code>EppContactDisclose</code> object contains a set of
 * boolean flags for indicating if one of the above 9 fields needs
 * included in an EPP Contact DiscloseType tag.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:37 $
 */
class EPP_EXPORT EppContactDisclose : public EppEntity
{
private:
	bool	disclose;

	bool    name_int;
	bool    name_loc;
	bool    org_int;
	bool    org_loc;
	bool    addr_int;
	bool    addr_loc;
	bool    voice;
	bool    fax;
	bool    email;

public:
	/**
	 * Constant for changing the setting of the boolean flag
	 * for the "name" tag in the "int" form
	 */
	static const int TAG_NAME_INT;
	/**
	 * Constant for changing the setting of the boolean flag
	 * for the "name" tag in the "loc" form.
	 */
	static const int TAG_NAME_LOC;
	/**
	 * Constant for changing the setting of the boolean flag
	 * for the "org" tag in the "int" form
	 */
	static const int TAG_ORG_INT;
	/**
	 * Constant for changing the setting of the boolean flag
	 * for the "org" tag in the "loc" form
	 */
	static const int TAG_ORG_LOC;
	/**
	 * Constant for changing the setting of the boolean flag
	 * for the "addr" tag in the "int" form
	 */
	static const int TAG_ADDR_INT;
	/**
	 * Constant for changing the setting of the boolean flag
	 * for the "addr" tag in the "loc" form
	 */
	static const int TAG_ADDR_LOC;
	/**
	 * Constant for changing the setting of the boolean flag
	 * for the "voice" tag
	 */
	static const int TAG_VOICE;
	/**
	 * Constant for changing the setting of the boolean flag
	 * for the "fax" tag
	 */
	static const int TAG_FAX;
	/**
	 * Constant for changing the setting of the boolean flag
	 * for the "email" tag
	 */
	static const int TAG_EMAIL;

	/**
	 * Creates an <code>EppContactDisclose</code> object, with all tag
	 * flags set to false and the disclose flag set to true
	 */
	EppContactDisclose()
	{
		disclose = true;
		name_int = false;
		name_loc = false;
		org_int  = false;
		org_loc  = false;
		addr_int = false;
		addr_loc = false;
		voice    = false;
		fax      = false;
		email    = false;
	};

	/**
	 * Creates an <code>EppContactDisclose</code> object, with all tag
	 * flags set to the value specified, and the disclose flag set to true
	 */
	EppContactDisclose( bool tag_flag )
	{
		disclose = true;
		name_int = tag_flag;
		name_loc = tag_flag;
		org_int  = tag_flag;
		org_loc  = tag_flag;
		addr_int = tag_flag;
		addr_loc = tag_flag;
		voice    = tag_flag;
		fax      = tag_flag;
		email    = tag_flag;
	};

	/**
	 * Creates an <code>EppContactDisclose</code> object, with all tag
	 * flags set and the disclose flag set to values specified
	 */
	EppContactDisclose( bool disclose_flag, bool tag_flag )
	{
		disclose = disclose_flag;
		name_int = tag_flag;
		name_loc = tag_flag;
		org_int  = tag_flag;
		org_loc  = tag_flag;
		addr_int = tag_flag;
		addr_loc = tag_flag;
		voice    = tag_flag;
		fax      = tag_flag;
		email    = tag_flag;
	};

	/**
	 * Destructor
	 */
	~EppContactDisclose() {};

	/**
	 * Sets the disclose flag
	 */
	void setDiscloseFlag( bool disclose_flag )
	{
		disclose = disclose_flag;
	};

	/**
	 * Gets the disclose flag
	 */
	bool getDiscloseFlag()
	{
		return disclose;
	};

	/**
	 * Sets the flag for including a specific privacy tag in the
	 * EPP Contcat DiscloseType tag
	 *
	 * @param tag one of the 9 tags defined in this class:
	 * <UL>
	 * <LI>TAG_NAME_INT</LI>
	 * <LI>TAG_NAME_LOC</LI>
	 * <LI>TAG_ORG_INT</LI>
	 * <LI>TAG_ORG_LOC</LI>
	 * <LI>TAG_ADDR_INT</LI>
	 * <LI>TAG_ADDR_LOC</LI>
	 * <LI>TAG_VOICE</LI>
	 * <LI>TAG_FAX</LI>
	 * </UL>
	 * @param flag true if the tag is needed to be included
	 */
	void set( int tag, bool flag )
	{
		if( tag == TAG_NAME_INT )
		{
			name_int = flag;
		}
		else if( tag == TAG_NAME_LOC )
		{
			name_loc = flag;
		}
		else if( tag == TAG_ORG_INT )
		{
			org_int = flag;
		}
		else if( tag == TAG_ORG_LOC )
		{
			org_loc = flag;
		}
		else if( tag == TAG_ADDR_INT )
		{
			addr_int = flag;
		}
		else if( tag == TAG_ADDR_LOC )
		{
			addr_loc = flag;
		}
		else if( tag == TAG_VOICE )
		{
			voice = flag;
		}
		else if( tag == TAG_FAX )
		{
			fax = flag;
		}
		else if( tag == TAG_EMAIL )
		{
			email = flag;
		}
		else
		{
			// do nothing
		}

		return;
	}

	/**
	 * Gets the flag for including a specific privacy tag in the
	 * EPP Contcat DiscloseType tag
	 *
	 * @param tag one of the 9 tags defined in this class:
	 * <UL>
	 * <LI>TAG_NAME_INT</LI>
	 * <LI>TAG_NAME_LOC</LI>
	 * <LI>TAG_ORG_INT</LI>
	 * <LI>TAG_ORG_LOC</LI>
	 * <LI>TAG_ADDR_INT</LI>
	 * <LI>TAG_ADDR_LOC</LI>
	 * <LI>TAG_VOICE</LI>
	 * <LI>TAG_FAX</LI>
	 * <LI>TAG_EMAIL</LI>
	 * </UL>
	 *
	 * @return true if the tag is needed to be included
	 */
	bool get( int tag )
	{
		bool rtn = false;

		if( tag == TAG_NAME_INT )
		{
			rtn = name_int;
		}
		else if( tag == TAG_NAME_LOC )
		{
			rtn = name_loc;
		}
		else if( tag == TAG_ORG_INT )
		{
			rtn = org_int;
		}
		else if( tag == TAG_ORG_LOC )
		{
			rtn = org_loc;
		}
		else if( tag == TAG_ADDR_INT )
		{
			rtn = addr_int;
		}
		else if( tag == TAG_ADDR_LOC )
		{
			rtn = addr_loc;
		}
		else if( tag == TAG_VOICE )
		{
			rtn = voice;
		}
		else if( tag == TAG_FAX )
		{
			rtn = fax;
		}
		else if( tag == TAG_EMAIL )
		{
			rtn = email;
		}
		else
		{
			// do nothing
		}

		return rtn;
	}

	/**
	 * Converts the <code>EppContactDisclose</code> object into an XML element
	 *
	 * @param doc the XML <code>DOMDocument</code> object
	 * @param tag the tag/element name for the <code>EppContactDisclose</code> object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );

	/**
	 * Converts an XML element into an <code>EppContactDisclose</code> object.
	 * The caller of this method must make sure that the root node is of
	 * the EPP Contact DiscloseType.
	 *
	 * @param root root node for an <code>EppContactDisclose</code> object in
	 *             XML format
	 *
	 * @return an <code>EppContactDisclose</code> object, or null if the node is
	 *         invalid
	 */
	static EppContactDisclose * fromXML( const DOMNode& root );

	DOMString toString()
	{
		return EppEntity::toString(DOMString("disclose"));
	}
};

#endif	/*    EPPCONTACTDISCLOSE_HPP */	/* } */
