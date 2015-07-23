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
 * This <code>EppResponseDataTransfer</code> class implements EPP Response
 * Data entity for EPP Command Transfer.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
abstract public class EppResponseDataTransfer extends EppResponseData
{
	/**
	 * Status for transfer - pending
	 */
	public static final String STATUS_PENDING = "pending";
	/**
	 * Status for transfer - clientApproved
	 */
	public static final String STATUS_CLIENT_APPROVED = "clientApproved";
	/**
	 * Status for transfer - clientCancelled
	 */
	public static final String STATUS_CLIENT_CANCELLED = "clientCancelled";
	/**
	 * Status for transfer - clientRejected
	 */
	public static final String STATUS_CLIENT_REJECTED = "clientRejected";
	/**
	 * Status for transfer - serverApproved
	 */
	public static final String STATUS_SERVER_APPROVED = "serverApproved";
	/**
	 * Status for transfer - serverCancelled
	 */
	public static final String STATUS_SERVER_CANCELLED = "serverCanCelled";

	/**
	 * The status of the transfer operation
	 */
	protected String   trStatus;
	/**
	 * The id of the client who made the transfer request
	 */
	protected String   reID;
	/**
	 * The timestamp when the transfer request was made
	 */
	protected Calendar reDate;
	/**
	 * The id of the client who must act upon the transfer request
	 */
	protected String   acID;
	/**
	 * The timestamp when the client who must or did act upon the
	 * transfer request
	 */
	protected Calendar acDate;
	/**
	 * The new expiration date of the EPP Object after transfer, if any
	 */
	protected Calendar exDate;    // optional, valid only for domain

	/**
	 * Gets the transfer status
	 */
	public String getTransferStatus()
	{
		return this.trStatus;
	}

	/**
	 * Sets the transfer status
	 */
	public void setTransferStatus( String status )
	{
		this.trStatus = status;
	}

	/**
	 * Gets the id of the client made the transfer request
	 */
	public String getRequestClientId()
	{
		return this.reID;
	}

	/**
	 * Sets the id of the client made the transfer request
	 */
	public void setRequestClientId( String clientId )
	{
		this.reID = clientId;
	}

	/**
	 * Gets the date that the transfer request is made
	 */
	public Calendar getRequestDate()
	{
		return this.reDate;
	}

	/**
	 * Sets the date that the transfer request is made
	 */
	public void setRequestDate( Calendar date )
	{
		this.reDate = date;
	}

	/**
	 * Gets the id of the client that <i>should</i> act upon the
	 * transfer request
	 */
	public String getActionClientId()
	{
		return this.acID;
	}

	/**
	 * Sets the id of the client that <i>should</i> act upon the
	 * transfer request
	 */
	public void setActionClientId( String clientId )
	{
		this.acID = clientId;
	}

	/**
	 * Gets the date that the transfer request is required to be acted upon
	 * before an automated response action taken place by the server, if
	 * the request status is still <i>pending</i>; or the date that the
	 * request was completed. 
	 */
	public Calendar getActionDate()
	{
		return this.acDate;
	}

	/**
	 * Sets the date that the transfer request is required to be acted upon
	 * before an automated response action taken place by the server, if
	 * the request status is still <i>pending</i>; or the date that the
	 * request was completed. 
	 */
	public void setActionDate( Calendar date )
	{
		this.acDate = date;
	}

	/**
	 * Converts shared <code>EppResponseDataTransfer</code> component into
	 * XML elements
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param body the XML <code>Element</code> to be attached
	 */
	protected void toXMLCommon( Document doc, Element body )
	{
		Element elm;

		if( trStatus != null )
		{
			elm = doc.createElement("trStatus");
			elm.appendChild(doc.createTextNode(trStatus));
			body.appendChild(elm);
		}
		if( reID != null )
		{
			elm = doc.createElement("reID");
			elm.appendChild(doc.createTextNode(reID));
			body.appendChild(elm);
		}
		if( reDate != null )
		{
			elm = doc.createElement("reDate");
			elm.appendChild(EppUtil.createTextNode(doc, reDate));
			body.appendChild(elm);
		}
		if( acID != null )
		{
			elm = doc.createElement("acID");
			elm.appendChild(doc.createTextNode(acID));
			body.appendChild(elm);
		}
		if( acDate != null )
		{
			elm = doc.createElement("acDate");
			elm.appendChild(EppUtil.createTextNode(doc, acDate));
			body.appendChild(elm);
		}
		if( exDate != null )
		{
			elm = doc.createElement("exDate");
			elm.appendChild(EppUtil.createTextNode(doc, exDate));
			body.appendChild(elm);
		}
	}

	/**
	 * Converts shared <code>EppResponseDataTransfer</code> components from
	 * XML format.
	 * 
	 * @param node the node of a shared component
	 * @param name the name of the node of a shared component
	 */
	protected void fromXMLCommon( Node node, String name )
	{
		if( name.equals("trStatus") )
		{
			trStatus = EppUtil.getText(node);
		}
		else if( name.equals("reID") )
		{
			reID = EppUtil.getText(node);
		}
		else if( name.equals("reDate") )
		{
			reDate = EppUtil.getDate(node);
		}
		else if( name.equals("acID") )
		{
			acID = EppUtil.getText(node);
		}
		else if( name.equals("acDate") )
		{
			acDate = EppUtil.getDate(node);
		}
		else if( name.equals("exDate") )
		{
			exDate = EppUtil.getDate(node);
		}
	}
}
