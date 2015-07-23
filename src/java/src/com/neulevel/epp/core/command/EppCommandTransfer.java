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
package com.neulevel.epp.core.command;

import org.w3c.dom.*;
import com.neulevel.epp.core.*;
import com.neulevel.epp.xri.command.*;

/**
 * This <code>EppCommandTransfer</code> class implements EPP Command Transfer
 * entity.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.3 $ $Date: 2006/03/01 01:35:38 $
 */
abstract public class EppCommandTransfer extends EppCommand implements EppPollable
{
	/**
	 * Transfer operation type for approving a transfer request
	 */
	public static final String OPTYPE_APPROVE = "approve";
	/**
	 * Transfer operation type for canceling a transfer request
	 */
	public static final String OPTYPE_CANCEL = "cancel";
	/**
	 * Transfer operation type for querying the status of a transfer request
	 */
	public static final String OPTYPE_QUERY = "query";
	/**
	 * Transfer operation type for rejecting a transfer request
	 */
	public static final String OPTYPE_REJECT = "reject";
	/**
	 * Transfer operation type for requesting an object transfer
	 */
	public static final String OPTYPE_REQUEST = "request";

	/**
	 * The type of the transfer operation
	 */
	protected String      op;
	/**
	 * The authorization information associated with the transfer operation
	 */
	protected EppAuthInfo authInfo;

	/**
	 * Gets the authorization info for the transfer operation
	 */
	public EppAuthInfo getAuthInfo()
	{
		return this.authInfo;
	}

	/**
	 * Sets the authorization info for the transfer operation
	 */
	public void setAuthInfo( EppAuthInfo authInfo )
	{
		this.authInfo = authInfo;
	}

	/**
	 * Gets the operation related to the transfer.
	 */
	public String getOperation()
	{
		return this.op;
	}

	/**
	 * Sets the operation related to the transfer. Valid operations are:
	 * <UL>
	 * <LI>approve</LI>
	 * <LI>cancel</LI>
	 * <LI>query</LI>
	 * <LI>reject</LI>
	 * <LI>request</LI>
	 * </UL>
	 */
	public void setOperation( String operation )
	{
		this.op = operation;
	}

	/**
	 * Converts an XML element into an <code>EppCommandTransfer</code>
	 * object. The caller of this method must make sure that the root
	 * node is of an EPP Object transferType entity. This method is
	 * for the interface defined in <code>EppPollable</code>
	 *
	 * @param root root node for an <code>EppCommandTransfer</code> object
	 *             in XML format
	 *
	 * @return an <code>EppCommandTransfer</code> object, or null if the
	 *         node is invalid
	 */
	public static EppEntity fromXMLPoll( Node root )
	{
		EppCommandTransfer cmd = null;
		String prefix = root.getPrefix();
		if( prefix != null )
		{
			if( prefix.equals("contact") )
			{
				cmd = (EppCommandTransfer) EppCommandTransferContact.fromXML(root);
			}
			else if( prefix.equals("domain") )
			{
				cmd = (EppCommandTransfer) EppCommandTransferDomain.fromXML(root);
			}
			else if( prefix.equals("svcsub") )
			{
				cmd = (EppCommandTransfer) EppCommandTransferSvcsub.fromXML(root);
			}
			else if( prefix.equals("xriAU") )
			{
				cmd = (EppCommandTransfer) EppCommandTransferXriAuthority.fromXML(root);
			}
			else if( prefix.equals("xriINA") )
			{
				cmd = (EppCommandTransfer) EppCommandTransferXriName.fromXML(root);
			}
		}
		return cmd;
	}

	/**
	 * Converts an XML element into an <code>EppCommandTransfer</code>
	 * object. The caller of this method must make sure that the root
	 * node is of an EPP Command Transfer entity.
	 *
	 * @param root root node for an <code>EppCommandTransfer</code> object
	 *             in XML format
	 *
	 * @return an <code>EppCommandTransfer</code> object, or null if the
	 *         node is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		String command = root.getLocalName();
		EppCommandTransfer cmd = null;

		NodeList list = root.getChildNodes();
		for( int i = 0; i < list.getLength(); i++ )
		{
			Node node = list.item(i);
			String prefix = node.getPrefix();
			String name = node.getLocalName();
			if( (prefix == null) || (name == null) )
			{
				continue;
			}
			if( name.equals(command) )
			{
				cmd = (EppCommandTransfer) fromXMLPoll(node);
				break;
			}
		}
			
		if( cmd != null )
		{
			cmd.setOperation(((Element) root).getAttribute("op"));
		}

		return cmd;
	}

	public String toString()
	{
		return toString("transfer");
	}
}
