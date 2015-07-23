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
import com.neulevel.epp.core.*;
import org.w3c.dom.*;
import org.apache.xerces.dom.*;

/**
 * This <code>EppResponseDataPending</code> class implements EPP
 * Response Data entity for EPP Pending Actions on EPP objects.
 *
 * @since EPP-1.0
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
abstract public class EppResponseDataPending extends EppResponseData
{
	/**
	 * The result of the pending action, true for success, false for failure
	 */
	protected boolean paResult;

	/**
	 * The transaction id associated with the pending action
	 */
	protected EppTransactionId paTRID;

	/**
	 * The date when the pending action is processed
	 */
	protected Calendar paDate;

	/**
	 * Gets the pending action result, true for success, false for failure
	 */
	public boolean getResult()
	{
		return this.paResult;
	}

	/**
	 * Gets the pending action result, true for success, false for failure
	 */
	public void setResult( boolean paResult )
	{
		this.paResult = paResult;
	}
	
	/**
	 * Gets the transaction id asssociated with the pending action
	 */
	public EppTransactionId getTransactionId()
	{
		return this.paTRID;
	}

	/**
	 * Sets the transaction id asssociated with the pending action
	 */
	public void setTransactionId( EppTransactionId paTRID )
	{
		this.paTRID = paTRID;
	}

	/**
	 * Gets the date when the pending action is processed
	 */
	public Calendar getDate()
	{
		return this.paDate;
	}

	/**
	 * Sets the date when the pending action is processed
	 */
	public void setDate( Calendar paDate )
	{
		this.paDate = paDate;
	}

	/**
	 * Converts shared <code>EppResponseDataPending</code> component into
	 * XML elements
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param body the XML <code>Element</code> to be attached
	 */
	protected void toXMLCommon( Document doc, Element body )
	{
		Element elm;

		if( paTRID != null )
		{
			elm = paTRID.toXML(doc, "paTRID", true);
			body.appendChild(elm);
		}
		if( paDate != null )
		{
			elm = doc.createElement("paDate");
			elm.appendChild(EppUtil.createTextNode(doc, paDate));
			body.appendChild(elm);
		}
	}

	/**
	 * Converts shared <code>EppResponseDataPending</code> components from
	 * XML format.
	 * 
	 * @param node the node of a shared component
	 * @param name the name of the node of a shared component
	 */
	protected void fromXMLCommon( Node node, String name )
	{
		if( name.equals("paTRID") )
		{
			paTRID = (EppTransactionId) EppTransactionId.fromXML(node);
		}
		else if( name.equals("paDate") )
		{
			paDate = EppUtil.getDate(node);
		}
	}
}
