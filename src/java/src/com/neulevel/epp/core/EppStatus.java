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
 * This <code>EppStatus</code> class implements EPP Status entity. Different EPP
 * objects have different status values:
 *
 * <UL>
 * <LI><B>EPP Contact Object</B><UL>
 *     <LI>clientDeleteProhibited</LI>
 *     <LI>clientTransferProhibited</LI>
 *     <LI>clientUpdateProhibited</LI>
 *     <LI>linked</LI>
 *     <LI>ok</LI>
 *     <LI>pendingCreate &dagger;</LI>
 *     <LI>pendingDelete</LI>
 *     <LI>pendingTransfer</LI>
 *     <LI>pendingUpdate &dagger;</LI>
 *     <LI>serverDeleteProhibited</LI>
 *     <LI>serverTransferProhibited</LI>
 *     <LI>serverUpdateProhibited</LI>
 *     </UL></LI>
 * <LI><B>EPP Domain Object</B><UL>
 *     <LI>clientDeleteProhibited</LI>
 *     <LI>clientHold</LI>
 *     <LI>clientRenewProhibited</LI>
 *     <LI>clientTransferProhibited</LI>
 *     <LI>clientUpdateProhibited</LI>
 *     <LI>inactive</LI>
 *     <LI>ok</LI>
 *     <LI>pendingCreate &dagger;</LI>
 *     <LI>pendingDelete</LI>
 *     <LI>pendingRenew &dagger;</LI>
 *     <LI>pendingTransfer</LI>
 *     <LI>pendingUpdate &dagger;</LI>
 *     <LI>pendingVerification &Dagger;</LI>
 *     <LI>serverDeleteProhibited</LI>
 *     <LI>serverHold</LI>
 *     <LI>serverRenewProhibited</LI>
 *     <LI>serverTransferProhibited</LI>
 *     <LI>serverUpdateProhibited</LI>
 *     </UL></LI>
 * <LI><B>EPP Host Object</B><UL>
 *     <LI>clientDeleteProhibited</LI>
 *     <LI>clientUpdateProhibited</LI>
 *     <LI>linked</LI>
 *     <LI>ok</LI>
 *     <LI>pendingCreate &dagger;</LI>
 *     <LI>pendingDelete</LI>
 *     <LI>pendingTransfer</LI>
 *     <LI>pendingUpdate &dagger;</LI>
 *     <LI>serverDeleteProhibited</LI>
 *     <LI>serverUpdateProhibited</LI>
 *     </UL></LI>
 * </UL>
 *
 * <P><B>Note &dagger;:<B> EPP-1.0 (EPP-09) only.
 * <P><B>Note &Dagger;:<B> EPP-04 only.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppStatus extends EppEntity
{
	private String message;
	private String s;
	private String lang;

	/**
	 * Creates an <code>EppStatus</code> object, with default status
	 * as "ok" and language as "en"
	 */
	public EppStatus()
	{
		this("ok", null, null);
	}

	/**
	 * Creates an <code>EppStatus</code> object with a status code
	 */
	public EppStatus( String status )
	{
		this(status, null, null);
	}

	/**
	 * Creates an <code>EppStatus</code> object with a status code,
	 * and a descriptive message
	 */
	public EppStatus( String status, String lang, String message )
	{
		this.s = status;
		this.lang = lang;
		this.message = message;
	}

	/**
	 * Gets the description message associated with the
	 * <code>EppStatus</code> object
	 */
	public String getMessage()
	{
		return this.message;
	}

	/**
	 * Sets the description message associated with the
	 * <code>EppStatus</code> object
	 */
	public void setMessage( String message )
	{
		this.message = message;
	}

	/**
	 * Gets the status code string
	 */
	public String getStatus()
	{
		return this.s;
	}

	/**
	 * Sets the status code string
	 */
	public void setStatus( String status )
	{
		this.s = status;
	}

	/**
	 * Gets the language for encoding the description message
	 */
	public String getLanguage()
	{
		return this.lang;
	}

	/**
	 * Sets the language for encoding the description message
	 */
	public void setLanguage( String language )
	{
		this.lang = language;
	}

	/**
	 * Converts the <code>EppStatus</code> object into an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the <code>EppStatus</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag)
	{
		Element body = doc.createElement(tag);
		if( lang != null )
		{
			body.setAttribute("lang", lang);
		}
		if( s == null )
		{
			body.setAttribute("s", "ok");
		}
		else
		{
			body.setAttribute("s", s);
		}
		if( message != null )
		{
			body.appendChild(doc.createTextNode(message));
		}

		return body;
	}

	/**
	 * Converts an XML element into an <code>EppStatus</code> object.
	 * The caller of this method must make sure that the root node is of
	 * the EPP Status type.
	 *
	 * @param root root node for an <code>EppStatus</code> object
	 *             in XML format
	 *
	 * @return an <code>EppStatus</code> object, or null if the node
	 *         is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		String str;
		EppStatus status = new EppStatus();

		str = ((Element) root).getAttribute("s");
		if( (str != null) && (str.length() > 0) )
		{
			status.s = str;
		}
		str = ((Element) root).getAttribute("lang");
		if( (str != null) && (str.length() > 0) )
		{
			status.lang = str;
		}
		str = EppUtil.getText(root);
		if( (str != null) && (str.length() > 0) )
		{
			status.message = str;
		}
		return status;
	}

	public String toString()
	{
	   return toString("status");
	}
}
