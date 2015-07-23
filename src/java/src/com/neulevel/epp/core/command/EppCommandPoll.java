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

import java.util.*;
import org.w3c.dom.*;
import com.neulevel.epp.core.*;

/**
 * This <code>EppCommandPoll</code> class implements EPP Command Poll
 * entity.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppCommandPoll extends EppCommand
{
	/**
	 * Poll operation type for polling existing messages (default)
	 */
	public static final String OPTYPE_REQ = "req";
	/**
	 * Poll operation type for acknowledging the receiption of a message
	 */
	public static final String OPTYPE_ACK = "ack";

	private String op;
	private String msgID;

	/**
	 * Creates an <code>EppCommandPoll</code> object, with default operation
	 * type as "req"
	 */
	public EppCommandPoll()
	{
		this(OPTYPE_REQ, null, null);
	}

	/**
	 * Creates an <code>EppCommandPoll</code> object, with default operation
	 * type as "req", given a client transaction id associated with
	 * operation
	 */
	public EppCommandPoll( String xid )
	{
		this(OPTYPE_REQ, null, xid);
	}
	
	/**
	 * Creates an <code>EppCommandPoll</code> object, given the operation
	 * type and the message id.
	 *
	 * <P><B>Note:</B> The operation type is either "req" for request, or "ack" for
	 *       acknowledgement
	 */
	public EppCommandPoll( String operation, String messageId )
	{
		this(operation, messageId, null);
	}

	/**
	 * Creates an <code>EppCommandPoll</code> object, given the operation
	 * type and the message id and a client transaction id associated with
	 * operation.
	 *
	 * <P><B>Note:</B> The operation type is either "req" for request, or "ack" for
	 *       acknowledgement
	 */
	public EppCommandPoll( String operation, String messageId, String xid )
	{
		this.op = operation;
		this.msgID = messageId;
		this.clTRID = xid;
	}

	/**
	 * Gets the poll operation type
	 */
	public String getOperation()
	{
		return this.op;
	}

	/**
	 * Sets the poll operation type, either "req" or "ack"
	 */
	public void setOperation( String operation )
	{
		this.op = operation;
	}

	/**
	 * Gets the message id related to the poll command
	 */
	public String getMessageId()
	{
		return this.msgID;
	}

	/**
	 * Sets the message id related to the poll command
	 */
	public void setMessageId( String messageId )
	{
		this.msgID = messageId;
	}

	/**
	 * Converts an XML element into an <code>EppCommandPoll</code> object.
	 * The caller of this method must make sure that the root node is of an
	 * EPP Command Create entity.
	 *
	 * @param root root node for an <code>EppCommandPoll</code> object
	 *             in XML format
	 *
	 * @return an <code>EppCommandPoll</code> object, or null if the node
	 *         is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		String operation = ((Element) root).getAttribute("op");
		String messageId = ((Element) root).getAttribute("msgID");
		if( (messageId != null) && (messageId.length() == 0) )
		{
			messageId = null;
		}

		return new EppCommandPoll(operation, messageId);
	}

	/**
	 * Converts the <code>EppCommandPoll</code> object into an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the <code>EppCommandPoll</code>
	 *            object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Attr attr;
		Vector attrList = new Vector();

		if( op != null )
		{
			attr = doc.createAttribute("op");
			attr.setValue(op);
			attrList.addElement(attr);
		}
		if( msgID != null )
		{
			attr = doc.createAttribute("msgID");
			attr.setValue(msgID);
			attrList.addElement(attr);
		}

		return toXMLCommon(doc, tag, (Element) null, attrList);
	}

	public String toString()
	{
		return toString("poll");
	}
}
