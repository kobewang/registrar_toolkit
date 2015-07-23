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
package com.neulevel.epp.xri;

import java.util.*;
import org.w3c.dom.*;
import com.neulevel.epp.core.EppEntity;
import com.neulevel.epp.core.EppUtil;

/**
 * This <code>EppXriTrustee</code> class defines trustee
 * information associated with XRI authority objects.  It
 * implements XRI trusteeType and trusteeInfType defined
 * in the XRI authority schema file.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppXriTrustee extends EppEntity
{
	private String  authId;
	private String  inumber;
	private boolean external;

	/**
	 * Creates an <code>EppXriTrustee</code> object
	 */
	public EppXriTrustee()
	{
		this.authId   = null;
		this.inumber  = null;
		this.external = false;
	}

	/**
	 * Returns true if this trustee is an external one
	 */
	public boolean isExternal()
	{
		return this.external;
	}

	/**
	 * Sets the boolean flag for indicating if this trustee is an external one or not
	 */
	public void setExternal( boolean flag )
	{
		this.external = flag;
	}

	/**
	 * Gets the identifier of the XRI authority as a trustee
	 *
	 * @return a string representing the XRI authority identifier,
	 * or null if the trustee is not an XRI authority object.
	 */
	public String getAuthorityId()
	{
		return this.authId;
	}

	/**
	 * Gets the i-number of the XRI i-number as a trustee
	 *
	 * @return a string representing the XRI i-number,
	 * or null if the trustee is not an XRI i-number object.
	 */
	public String getINumber()
	{
		return this.inumber;
	}

	/**
	 * Sets the identifier of an XRI authority as an internal trutee
	 *
	 * @param authId identifier of the XRI authority object
	 */
	public void setAuthorityId( String authId )
	{
		this.setAuthorityId(authId, false);
	}

	/**
	 * Sets the identifier of an XRI authority as a trustee, either external or internal
	 *
	 * @param authId identifier of the XRI authority object
	 * @param external flag indicating if the trustee is external or internal
	 */
	public void setAuthorityId( String authId, boolean external )
	{
		this.authId   = authId;
		this.inumber  = null;
		this.external = external;
	}

	/**
	 * Sets the i-number of an XRI i-number as an internal trutee
	 *
	 * @param inumber i-number of the XRI i-number object
	 */
	public void setINumber( String inumber )
	{
		this.setINumber(inumber, false);
	}

	/**
	 * Sets the i-number of an XRI i-number as a trustee, either external or internal
	 *
	 * @param inumber i-number of the XRI i-number object
	 * @param external flag indicating if the trustee is external or internal
	 */
	public void setINumber( String inumber, boolean external )
	{
		this.authId   = null;
		this.inumber  = inumber;
		this.external = external;
	}

	/**
         * Converts the <code>EppXriTrustee</code> object into an XML element
         *
         * @param doc the XML <code>Document</code> object
         * @param tag the tag/element name for the <code>EppXriTrustee</code> object
         *
         * @return an <code>Element</code> object
         */
	public Element toXML( Document doc, String tag )
	{
		Element body = doc.createElement(tag);
		Element elm;
		if( this.inumber == null )
		{
			elm = doc.createElement("authId");
			elm.appendChild(doc.createTextNode(this.authId));
			body.appendChild(elm);
		}
		else
		{
			elm = doc.createElement("inumber");
			elm.appendChild(doc.createTextNode(this.inumber));
			body.appendChild(elm);
		}
		body.setAttribute("external", this.external ? "1" : "0");

		return body;
	}

	/**
	 * Converts an XML element into an <code>EppXriTrustee</code> object.
	 * The caller of this method must make sure that the root node is of
	 * the EPP XRI trusteeType or trusteeInfType.
	 *
	 * @param root root node for an <code>EppXriTrustee</code> object in
	 *             XML format
	 *
	 * @return an <code>EppXriTrustee</code> object, or null if the node is
	 *         invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		EppXriTrustee trustee = null;
		boolean external      = false;

		String flag = ((Element) root).getAttribute("external");
		if(    (flag != null)
		    && (    flag.equals("1")
			 || flag.equalsIgnoreCase("t")
			 || flag.equalsIgnoreCase("true") ) )
		{
			external = true;
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
			if( name.equals("authId") )
			{
				trustee = new EppXriTrustee();
				trustee.setAuthorityId(EppUtil.getText(node), external);
				break;
			}
			if( name.equals("inumber") )
			{
				trustee = new EppXriTrustee();
				trustee.setINumber(EppUtil.getText(node), external);
				break;
			}
		}

		return trustee;
	}

	public String toString()
	{
		return toString("trustee");
	}
}
