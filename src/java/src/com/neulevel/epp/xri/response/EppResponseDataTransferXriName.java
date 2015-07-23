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
import org.w3c.dom.*;
import com.neulevel.epp.core.*;
import com.neulevel.epp.core.response.*;

/**
 * This <code>EppResponseDataTransferXriName</code> class implements EPP
 * Response Data entity for EPP Command Transfer of EPP XRI I-Name objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppResponseDataTransferXriName extends EppResponseDataTransfer
{
	private String iname;
	private String sourceAuthId;
	private String targetAuthId;
	private String trToken;

	/**
	 * Creates an <code>EppResponseDataTransferXriName</code> object
	 * given the i-name of an XRI i-name object to be transferred
	 */
	public EppResponseDataTransferXriName( String iname )
	{
		this.iname = iname;
	}

	/**
	 * Gets the i-name of the XRI I-Name object
	 */
	public String getIName()
	{
		return this.iname;
	}

	/**
	 * Sets the iname of the XRI I-Name object
	 */
	public void setIName( String iname )
	{
		this.iname = iname;
	}

	/**
	 * Gets the identifier of the XRI Authority object involved
	 * in the XRI I-Name transfer that acts as the source
	 */
	public String getSourceAuthorityId()
	{
		return this.sourceAuthId;
	}

	/**
	 * Sets the identifier of the XRI Authority object involved
	 * in the XRI I-Name transfer that acts as the source
	 */
	public void setSourceAuthorityId( String sourceAuthId )
	{
		this.sourceAuthId = sourceAuthId;
	}

	/**
	 * Gets the identifier of the XRI Authority object involved
	 * in the XRI I-Name transfer that acts as the target
	 */
	public String getTargetAuthorityId()
	{
		return this.targetAuthId;
	}

	/**
	 * Sets the identifier of the XRI Authority object involved
	 * in the XRI I-Name transfer that acts as the target
	 */
	public void setTargetAuthorityId( String targetAuthId )
	{
		this.targetAuthId = targetAuthId;
	}

	/**
	 * Gets the transfer token for the transfer operation
	 */
	public String getTransferToken()
	{
		return this.trToken;
	}

	/**
	 * Sets the transfer token for the transfer operation
	 */
	public void setTransferToken( String trToken )
	{
		this.trToken = trToken;
	}

	/**
	 * Gets the new expiration date of the i-name after the transfer, or
	 * null if the transfer request does not change the expiration date
	 * of the i-name
	 */
	public Calendar getDateExpired()
	{
		return this.exDate;
	}

	/**
	 * Sets the new expiration date of the domain after the transfer.
	 * The value of the new expiration date is optional.
	 */
	public void setDateExpired( Calendar date )
	{
		this.exDate = date;
	}

	/**
	 * Converts the <code>EppResponseDataTransferXriName</code> object into
	 * an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the
	 *            <code>EppResponseDataTransferXriName</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Element elm;
		Element body = doc.createElement(tag);
		Element data = EppUtil.createElementNS(doc, "xriINA", "trnData");
		body.appendChild(data);

		if( iname != null )
		{
			elm = doc.createElement("iname");
			elm.appendChild(doc.createTextNode(iname));
			data.appendChild(elm);
		}
		if( sourceAuthId != null )
		{
			elm = doc.createElement("source");
			elm.appendChild(doc.createTextNode(sourceAuthId));
			data.appendChild(elm);
		}
		if( targetAuthId != null )
		{
			elm = doc.createElement("target");
			elm.appendChild(doc.createTextNode(targetAuthId));
			data.appendChild(elm);
		}
		if( trToken != null )
		{
			elm = doc.createElement("trToken");
			elm.appendChild(doc.createTextNode(trToken));
			data.appendChild(elm);
		}

		toXMLCommon(doc, data);

		return body;
	}

	/**
	 * Converts an XML element into an
	 * <code>EppResponseDataTransferXriName</code> object. The caller
	 * of this method must make sure that the root node is of an EPP
	 * Response Transfer entity for EPP XRI I-Name object.
	 *
	 * @param root root node for an
	 *             <code>EppResponseDataTransferXriName</code>
	 *             object in XML format
	 *
	 * @return an <code>EppResponseDataTransferXriName</code> object,
	 *         or null if the node is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		EppResponseDataTransferXriName res = null;
		NodeList list = root.getChildNodes();
		for( int i = 0; i < list.getLength(); i++ )
		{
			Node node = list.item(i);
			String name = node.getLocalName();
			if( name == null )
			{
				continue;
			}
			if( name.equals("iname") )
			{
				String iname = EppUtil.getText(node);
				if( res == null )
				{
					res = new EppResponseDataTransferXriName(iname);
				}
				else
				{
					res.setIName(iname);
				}
			}
			else if( res != null )
			{
				if( name.equals("source") )
				{
					String source = EppUtil.getText(node);
					res.setSourceAuthorityId(source);
				}
				else if( name.equals("target") )
				{
					String target = EppUtil.getText(node);
					res.setTargetAuthorityId(target);
				}
				else if( name.equals("trToken") )
				{
					String trToken = EppUtil.getText(node);
					res.setTransferToken(trToken);
				}
				else
				{
					res.fromXMLCommon(node, name);
				}
			}
		}

		return res;
	}
}
