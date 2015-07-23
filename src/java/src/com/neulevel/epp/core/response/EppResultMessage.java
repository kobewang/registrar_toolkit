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
package com.neulevel.epp.core.response;

import org.w3c.dom.*;
import com.neulevel.epp.core.*;

/**
 * This <code>EppResultMessage</code> class implements EPP Result Message
 * entity.
 *
 * <P>The <I>id</I> tag has been removed from EPP-1.0. For backward compatibility,
 * the API is unchanged, but the <I>id</I> value would always be null.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppResultMessage extends EppEntity
{
	private String id;
	private String lang;
	private String msg;

	/**
	 * Creates an <code>EppResultMessage</code> object
	 *
	 * @param message message to be returned
	 */
	public EppResultMessage( String message )
	{
		this(null, "en-US", message);
	}

	/**
	 * Creates an <code>EppResultMessage</code> object
	 *
	 * @param id message id
	 * @param message message to be returned
	 */
	public EppResultMessage( String id, String message )
	{
		this(id, "en-US", message);
	}

	/**
	 * Creates an <code>EppResultMessage</code> object
	 *
	 * @param id message id
	 * @param language langaue type of the message
	 * @param message message to be returned
	 */
	public EppResultMessage( String id, String language, String message )
	{
		this.id   = id;
		this.lang = language;
		this.msg  = message;
		this.id   = null;
	}

	/**
	 * Gets message id
	 *
	 * This method is not functional in EPP-1.0, will be removed in the future.
	 *
	 * @see EppResponse#getMessageId()
	 */
	public String getId()
	{
		return this.id;
	}

	/**
	 * Sets message id
	 *
	 * This method is not functional in EPP-1.0, will be removed in the future.
	 *
	 * @see EppResponse#setMessageId(java.lang.String)
	 */
	public void setId( String id )
	{
		this.id = id;
	}

	/**
	 * Gets message to be returned
	 */
	public String getMessage()
	{
		return this.msg;
	}

	/**
	 * Sets message to be returned
	 */
	public void setMessage( String message )
	{
		this.msg = message;
	}

	/**
	 * Gets language type of the message
	 */
	public String getLanguage()
	{
		return this.lang;
	}

	/**
	 * Sets language type of the message
	 */
	public void setLanguage( String language )
	{
		this.lang = language;
	}

	/**
	 * Converts the <code>EppResultMessage</code> object into an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the <code>EppResultMessage</code>
	 *            object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Element body = doc.createElement(tag);
		Element elm;

//		if( (id != null) && (id.length() > 0) )
//		{
//			body.setAttribute("id", id);
//		}
		if( (lang != null) && (lang.length() > 0) )
		{
			body.setAttribute("lang", lang);
		}
		if( (msg != null) && (msg.length() > 0) )
		{
			body.appendChild(doc.createTextNode(msg));
		}

		return body;
	}

	/**
	 * Converts an XML element into an <code>EppResultMessage</code> object.
	 * The caller of this method must make sure that the root node is of
	 * the EPP msgType.
	 *
	 * @param root root node for an <code>EppResultMessage</code> object in
	 *             XML format
	 *
	 * @return an <code>EppResultMessage</code> object, or null if the node
	 *         is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		String id   = null;
		String lang = null;
		String msg  = null;

		id   = ((Element) root).getAttribute("id");
		lang = ((Element) root).getAttribute("lang");
		msg  = EppUtil.getText(root);

		return new EppResultMessage(id, lang, msg);
	}

	public String toString()
	{
		return toString("msg");
	}
}
