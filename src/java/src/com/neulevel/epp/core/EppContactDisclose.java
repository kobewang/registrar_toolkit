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
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppContactDisclose extends EppEntity
{
	/**
	 * Constant for changing the setting of the boolean flag
	 * for the "name" tag in the "int" form
	 */
	public static final int TAG_NAME_INT = 1;
	/**
	 * Constant for changing the setting of the boolean flag
	 * for the "name" tag in the "loc" form.
	 */
	public static final int TAG_NAME_LOC = 2;
	/**
	 * Constant for changing the setting of the boolean flag
	 * for the "org" tag in the "int" form
	 */
	public static final int TAG_ORG_INT = 3;
	/**
	 * Constant for changing the setting of the boolean flag
	 * for the "org" tag in the "loc" form
	 */
	public static final int TAG_ORG_LOC = 4;
	/**
	 * Constant for changing the setting of the boolean flag
	 * for the "addr" tag in the "int" form
	 */
	public static final int TAG_ADDR_INT = 5;
	/**
	 * Constant for changing the setting of the boolean flag
	 * for the "addr" tag in the "loc" form
	 */
	public static final int TAG_ADDR_LOC = 6;
	/**
	 * Constant for changing the setting of the boolean flag
	 * for the "voice" tag
	 */
	public static final int TAG_VOICE = 7;
	/**
	 * Constant for changing the setting of the boolean flag
	 * for the "fax" tag
	 */
	public static final int TAG_FAX = 8;
	/**
	 * Constant for changing the setting of the boolean flag
	 * for the "email" tag
	 */
	public static final int TAG_EMAIL = 9;

	private boolean	   disclose;

	private boolean    name_int;
	private boolean    name_loc;
	private boolean    org_int;
	private boolean    org_loc;
	private boolean    addr_int;
	private boolean    addr_loc;
	private boolean    voice;
	private boolean    fax;
	private boolean    email;

	/**
	 * Creates an <code>EppContactDisclose</code> object, with all tag
	 * flags set to false and the disclose flag set to true
	 */
	public EppContactDisclose()
	{
		this(true, false);
	}

	/**
	 * Creates an <code>EppContactDisclose</code> object, with all tag
	 * flags set to the value specified, and the disclose flag set to true
	 */
	public EppContactDisclose( boolean tag_flag )
	{
		this(true, tag_flag);
	}

	/**
	 * Creates an <code>EppContactDisclose</code> object, with all tag
	 * flags set and the disclose flag set to values specified
	 */
	public EppContactDisclose( boolean disclose_flag, boolean tag_flag )
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
	}

	/**
	 * Sets the disclose flag
	 */
	public void setDiscloseFlag( boolean disclose_flag )
	{
		disclose = disclose_flag;
	}

	/**
	 * Gets the disclose flag
	 */
	public boolean getDiscloseFlag()
	{
		return disclose;
	}

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
	public void set( int tag, boolean flag )
	{
		switch( tag )
		{
		case TAG_NAME_INT:
			name_int = flag;
			break;

		case TAG_NAME_LOC:
			name_loc = flag;
			break;

		case TAG_ORG_INT:
			org_int = flag;
			break;

		case TAG_ORG_LOC:
			org_loc = flag;
			break;

		case TAG_ADDR_INT:
			addr_int = flag;
			break;

		case TAG_ADDR_LOC:
			addr_loc = flag;
			break;

		case TAG_VOICE:
			voice = flag;
			break;

		case TAG_FAX:
			fax = flag;
			break;

		case TAG_EMAIL:
			email = flag;
			break;

		default:
			// do nothing
			break;
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
	public boolean get( int tag )
	{
		boolean rtn = false;

		switch( tag )
		{
		case TAG_NAME_INT:
			rtn = name_int;
			break;

		case TAG_NAME_LOC:
			rtn = name_loc;
			break;

		case TAG_ORG_INT:
			rtn = org_int;
			break;

		case TAG_ORG_LOC:
			rtn = org_loc;
			break;

		case TAG_ADDR_INT:
			rtn = addr_int;
			break;

		case TAG_ADDR_LOC:
			rtn = addr_loc;
			break;

		case TAG_VOICE:
			rtn = voice;
			break;

		case TAG_FAX:
			rtn = fax;
			break;

		case TAG_EMAIL:
			rtn = email;
			break;

		default:
			// do nothing
			break;
		}

		return rtn;
	}

	/**
	 * Converts the <code>EppContactDisclose</code> object into an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the <code>EppContactDisclose</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Element body = doc.createElement(tag);
		Element elm;

		body.setAttribute("flag", (disclose == true) ? "1" : "0");
		if( name_int == true )
		{
			elm = doc.createElement("name");
			elm.setAttribute("type", "int");
			body.appendChild(elm);
		}
		if( name_loc == true )
		{
			elm = doc.createElement("name");
			elm.setAttribute("type", "loc");
			body.appendChild(elm);
		}
		if( org_int == true )
		{
			elm = doc.createElement("org");
			elm.setAttribute("type", "int");
			body.appendChild(elm);
		}
		if( org_loc == true )
		{
			elm = doc.createElement("org");
			elm.setAttribute("type", "loc");
			body.appendChild(elm);
		}
		if( addr_int == true )
		{
			elm = doc.createElement("addr");
			elm.setAttribute("type", "int");
			body.appendChild(elm);
		}
		if( addr_loc == true )
		{
			elm = doc.createElement("addr");
			elm.setAttribute("type", "loc");
			body.appendChild(elm);
		}
		if( voice == true )
		{
			elm = doc.createElement("voice");
			body.appendChild(elm);
		}
		if( fax == true )
		{
			elm = doc.createElement("fax");
			body.appendChild(elm);
		}
		if( email == true )
		{
			elm = doc.createElement("email");
			body.appendChild(elm);
		}

		return body;
	}

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
	public static EppEntity fromXML( Node root )
	{
		EppContactDisclose disc = new EppContactDisclose();
		String type = ((Element) root).getAttribute("flag");
		if(    (type != null)
		    && (    type.equals("0")
			 || type.equalsIgnoreCase("f")
			 || type.equalsIgnoreCase("false") ) )
		{
			disc.setDiscloseFlag(false);
		}
		else
		{
			disc.setDiscloseFlag(true);
		}

		NodeList list = root.getChildNodes();
		for( int i = 0; i < list.getLength(); i++ )
		{
			Node node = list.item(i);
			String name = node.getLocalName();
			if( name == null )
			{
				continue;
			}
			if( name.equals("name") )
			{
				type = ((Element) node).getAttribute("type");
				if( (type != null) && type.equals("int") )
				{
					disc.set(TAG_NAME_INT, true);
				}
				else if( (type != null) && type.equals("loc") )
				{
					disc.set(TAG_NAME_LOC, true);
				}
			}
			else if( name.equals("org") )
			{
				type = ((Element) node).getAttribute("type");
				if( (type != null) && type.equals("int") )
				{
					disc.set(TAG_ORG_INT, true);
				}
				else if( (type != null) && type.equals("loc") )
				{
					disc.set(TAG_ORG_LOC, true);
				}
			}
			else if( name.equals("addr") )
			{
				type = ((Element) node).getAttribute("type");
				if( (type != null) && type.equals("int") )
				{
					disc.set(TAG_ADDR_INT, true);
				}
				else if( (type != null) && type.equals("loc") )
				{
					disc.set(TAG_ADDR_LOC, true);
				}
			}
			else if( name.equals("voice") )
			{
				disc.set(TAG_VOICE, true);
			}
			else if( name.equals("fax") )
			{
				disc.set(TAG_FAX, true);
			}
			else if( name.equals("email") )
			{
				disc.set(TAG_EMAIL, true);
			}
		}
		return disc;
	}

	public String toString()
	{
		return toString("disclose");
	}
}
