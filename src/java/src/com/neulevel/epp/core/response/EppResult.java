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
 * This <code>EppResult</code> class implements EPP Result entity.
 *
 * <P><B>Warning</B>: Currently, this class supports free text values
 * only. In the future, a <I>DOMNode</I> may be accepted for the value tag.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppResult extends EppEntity
{
	private int              code;
	private EppResultMessage msg;
	private Vector           value;

	/**
	 * Creates an <code>EppResult</code> object
	 */
	public EppResult()
	{
		this(0, null, null);
	}

	/**
	 * Creates an <code>EppResult</code> object
	 *
	 * @param code    result code
	 */
	public EppResult( int code )
	{
		this(code, EppError.getText(code), null);
	}

	/**
	 * Creates an <code>EppResult</code> object
	 *
	 * @param code    result code
	 * @param message message to be returned
	 */
	public EppResult( int code, String message )
	{
		this(code, message, null);
	}

	/**
	 * Creates an <code>EppResult</code> object
	 *
	 * @param code result code
	 * @param message message to be returned
	 * @param value value to be returned
	 */
	public EppResult( int code, String message, String value )
	{
		this.code = code;
		this.msg = new EppResultMessage(message);
		this.value = new Vector();
		if( value != null )
		{
			this.value.addElement(value);
		}
	}

	/**
	 * Gets result code
	 */
	public int getCode()
	{
		return this.code;
	}

	/**
	 * Sets result code
	 */
	public void setCode( int code )
	{
		this.code = code;
	}

	/**
	 * Gets message to be returned
	 */
	public EppResultMessage getMessage()
	{
		return this.msg;
	}

	/**
	 * Sets message to be returned
	 */
	public void setMessage( String message )
	{
		this.msg = new EppResultMessage(message);
	}

	/**
	 * Sets message to be returned
	 */
	public void setMessage( EppResultMessage message )
	{
		this.msg = message;
	}

	/**
	 * Gets values to be returned
	 */
	public Vector getValue()
	{
		return this.value;
	}

	/**
	 * Adds a string value to be returned
	 */
	public void addValue( String value )
	{
		if( this.value == null )
		{
			this.value = new Vector();
		}
		this.value.addElement(value);
	}

	/**
	 * Adds a value/reason pair to be returned
	 */
	public void addValue( EppValueReason value )
	{
		if( this.value == null )
		{
			this.value = new Vector();
		}
		this.value.addElement(value);
	}

	/**
	 * Converts the <code>EppResult</code> object into an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the <code>EppResult</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Element body = doc.createElement(tag);
		Element elm;

		body.setAttribute("code", "" + code);

		if( msg != null )
		{
			body.appendChild(msg.toXML(doc, "msg"));
		}

		if( (value != null) && (value.size() > 0) )
		{
			for( int i = 0; i < value.size(); i++ )
			{
				Object obj = value.get(i);
				if( (obj != null) && (obj instanceof String) )
				{
					elm = doc.createElement("value");
//					elm.appendChild(doc.createTextNode((String) obj));
					Element txt = doc.createElement("text");
					txt.appendChild(doc.createTextNode((String) obj));
					elm.appendChild(txt);
					body.appendChild(elm);
				}
				else if( (obj != null) && (obj instanceof EppValueReason) )
				{
					EppValueReason t = (EppValueReason) obj;
					elm = t.toXML(doc, "extValue");
					body.appendChild(elm);
				}
			}
		}

		return body;
	}

	/**
	 * Converts an XML element into an <code>EppResult</code> object. The
	 * caller of this method must make sure that the root node is of the EPP
	 * responseType.
	 *
	 * @param root root node for an <code>EppResult</code> object in XML format
	 *
	 * @return an <code>EppResult</code> object, or null if the node is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		EppResult result = new EppResult();

		String code = ((Element) root).getAttribute("code");
		if( code != null )
		{
			result.code = Integer.parseInt(code);
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
			if( name.equals("msg") )
			{
				result.msg = (EppResultMessage) EppResultMessage.fromXML(node);
			}
			else if( name.equals("value") )
			{
//				result.addValue(EppUtil.getText(node));
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
					result.addValue(EppUtil.getText(cnode));
					break;
				}
			}
			else if( name.equals("extValue") )
			{
				EppValueReason t = (EppValueReason) EppValueReason.fromXML(node);
				if( t != null )
				{
					result.addValue(t);
				}
			}
		}
		return result;
	}

	public String toString()
	{
		return toString("result");
	}
}
