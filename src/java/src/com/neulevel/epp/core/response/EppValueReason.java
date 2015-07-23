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

import java.util.*;
import org.w3c.dom.*;
import com.neulevel.epp.core.*;

/**
 * This <code>EppValueReason</code> class implements EPP value/reason
 * for error diagnostic information.
 *
 * <P><B>Warning</B>: Currently, this class supports free text values
 * only. In the future, a <I>DOMNode</I> may be accepted for the value tag.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppValueReason extends EppEntity
{
	private String           value;
	private String           reason;
	private String		 lang;

	/**
	 * Creates an <code>EppValueReason</code> object
	 */
	public EppValueReason()
	{
		this.value      = null;
		this.reason     = null;
		this.lang	= null;
	}

	/**
	 * Creates an <code>EppValueReason</code> object with a string value and a reason text
	 */
	public EppValueReason( String value, String reason )
	{
		this.value      = value;
		this.reason     = reason;
		this.lang	= null;
	}

	/**
	 * Creates an <code>EppValueReason</code> object with a string value, a reason text and its language code
	 */
	public EppValueReason( String value, String reason, String language )
	{
		this.value      = value;
		this.reason     = reason;
		this.lang	= language;
	}

	/**
	 * Gets the value
	 */
	public String getValue()
	{
		return this.value;
	}

	/**
	 * Sets the value
	 */
	public void setValue( String value )
	{
		this.value = value;
	}

	/**
	 * Gets the reason
	 */
	public String getReason()
	{
		return this.reason;
	}

	/**
	 * Sets the reason
	 */
	public void setReason( String reason )
	{
		this.reason = reason;
	}

	/**
	 * Gets language type of the reason message
	 */
	public String getLanguage()
	{
		return this.lang;
	}

	/**
	 * Sets language type of the reason message
	 */
	public void setLanguage( String language )
	{
		this.lang = language;
	}

	/**
	 * Converts the <code>EppValueReason</code> object into an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the <code>EppValueReason</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Element body = doc.createElement(tag);
		Element elm;

		if( value != null )
		{
			elm = doc.createElement("value");
//			elm.appendChild(doc.createTextNode(value));
			Element txt = doc.createElement("text");
			txt.appendChild(doc.createTextNode((String) value));
			elm.appendChild(txt);
			body.appendChild(elm);
		}
		if( reason != null )
		{
			elm = doc.createElement("reason");
			elm.appendChild(doc.createTextNode(reason));
			if( lang != null )
			{
				elm.setAttribute("lang", lang);
			}
			body.appendChild(elm);
		}

		return body;
	}

	/**
	 * Converts an XML element into an <code>EppValueReason</code> object. The
	 * caller of this method must make sure that the root node is of the EPP
	 * ExtErrValue Type.
	 *
	 * @param root root node for an <code>EppValueReason</code> object in XML format
	 *
	 * @return an <code>EppValueReason</code> object, or null if the node is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		EppValueReason result = new EppValueReason();

		NodeList list = root.getChildNodes();
		for( int i = 0; i < list.getLength(); i++ )
		{
			Node node = list.item(i);
			String name = node.getLocalName();
			if( name == null )
			{
				continue;
			}
			if( name.equals("value") )
			{
//				result.value = EppUtil.getText(node);
				NodeList clist = node.getChildNodes();
				for( int j = 0; j < clist.getLength(); j++ )
				{
					Node cnode = clist.item(j);
					String cname = cnode.getLocalName();
					if( cname == null )
					{
						continue;
					}
					if( cname.equals("text") == false )
					{
						continue;
					}
					result.value = EppUtil.getText(cnode);
					break;
				}
			}
			else if( name.equals("reason") )
			{
				result.reason = EppUtil.getText(node);
				String language = ((Element) node).getAttribute("lang");
				if( (language != null) && (language.length() > 0) )
				{
					result.lang = language;
				}
			}
		}
		return result;
	}

	public String toString()
	{
		return toString("extValue");
	}
}
