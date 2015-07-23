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
package com.neulevel.epp.xri.response;

import java.util.*;
import com.neulevel.epp.core.*;
import com.neulevel.epp.core.response.*;
import org.w3c.dom.*;
import org.apache.xerces.dom.*;

/**
 * This <code>EppResponseDataPendingXriName</code> class implements EPP
 * Response Data entity for EPP Pending Actions of EPP XRI I-Name objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppResponseDataPendingXriName extends EppResponseDataPending
{
	private String   iname;

	/**
	 * Creates an <code>EppResponseDataPendingXriName</code> object
	 *
	 * @param iname the i-name of the <code>EppXriName</code> object associated with the pending action
	 */
	public EppResponseDataPendingXriName( String iname )
	{
		this.iname = iname;
		this.paResult = false;
		this.paTRID = null;
		this.paDate = null;
	}

	/**
	 * Creates an <code>EppResponseDataPendingXriName</code> object
	 *
	 * @param iname the i-name of the <code>EppXriName</code> object associated with the pending action
	 * @param result the boolean flag indicating if the pending action is a success or a failure
	 */
	public EppResponseDataPendingXriName( String iname, boolean result )
	{
		this.iname = iname;
		this.paResult = result;
		this.paTRID = null;
		this.paDate = null;
	}

	/**
	 * Gets the i-name of the XRI i-name object associated with the pending action
	*/
	public String getIName()
	{
		return this.iname;
	}

	/**
	 * Sets the i-name of the XRI i-name object associated with the pending action
	 */
	public void setIName( String iname )
	{
		this.iname = iname;
	}

	/**
	 * Converts an XML element into an
	 * <code>EppResponseDataPendingXriName</code> object. The caller of this
	 * method must make sure that the root node is the resData element of
	 * EPP responseType for an pending action of an EPP XRI IName object
	 *
	 * @param root root node for an <code>EppResponseDataPendingXriName</code>
	 *             object in XML format
	 *
	 * @return an <code>EppResponseDataPendingXriName</code> object,
	 *         or null if the node is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		EppResponseDataPendingXriName res = null;
		NodeList list = root.getChildNodes();
		for( int i = 0; i < list.getLength(); i++ )
		{
			Node node = list.item(i);
			String name = node.getLocalName();
			if( name == null )
			{
				continue;
			}
			else if( name.equals("iname") )
			{
				String iname = EppUtil.getText(node);
				if( res == null )
				{
					res = new EppResponseDataPendingXriName(iname);
					String cd = ((Element) node).getAttribute("paResult");
					if(    (cd != null)
					    && (    cd.equals("0")
						 || cd.equalsIgnoreCase("f")
						 || cd.equalsIgnoreCase("false") ) )
					{
						res.setResult(false);
					}
					else
					{
						res.setResult(true);
					}
				}
			}
			else if( res != null )
			{
				res.fromXMLCommon(node, name);
			}
		}

		return res;
	}

	/**
	 * Converts an <code>EppResponseDataPendingXriName</code> object
	 * into an XML element.
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the
	 *        <code>EppResponseDataPendingXriName</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Element elm;
		Element body = doc.createElement(tag);
		ElementNSImpl data = EppUtil.createElementNS(doc, "xriINA", "panData");
		body.appendChild(data);

		if( iname != null )
		{
			elm = doc.createElement("iname");
			elm.appendChild(doc.createTextNode(iname));
			if( paResult == true )
			{
				elm.setAttribute("paResult", "1");
			}
			else
			{
				elm.setAttribute("paResult", "0");
			}
			data.appendChild(elm);
		}

		toXMLCommon(doc, data);

		return body;
	}
}
