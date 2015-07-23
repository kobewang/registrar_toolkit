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
package com.neulevel.epp.transport;

import com.neulevel.epp.core.*;
import com.neulevel.epp.core.command.*;
import com.neulevel.epp.core.response.*;

/**
 * This <code>EppChannel</code> class is designed to handle a channel connected
 * to the EPP server.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
abstract public class EppChannel
{
	/**
	 * The status value when the <code>EppChannel</code> object is
	 * created, but not started
	 */
	protected static final int STATUS_INIT   = 0;
	/**
	 * The status value when the <code>EppChannel</code> object is
	 * started after sucessfully calling the <code>start</code> method
	 */
	protected static final int STATUS_START  = 1;
	/**
	 * The status value when the <code>EppChannel</code> object is
	 * closed atfer successfully calling the <code>close</code> method
	 */
	protected static final int STATUS_CLOSED = 2;
	/**
	 * The status of the <code>EppChannel</code> object. It is used
	 * for recording the status transition from <code>STATUS_INIT</code>
	 * to <code>STATUS_START</code>, and finally to
	 * <code>STATUS_CLOSED</code>
	 */
	protected int      status  = STATUS_INIT;

	private Exception  e = null;
	private EppSession session = null;
	private String     message = null;

	/**
	 * Gets the <code>EppSession</code> object associated with the 
	 * EPP Channel
	 */
	public EppSession getSession()
	{
		return this.session;
	}

	/**
	 * Sets the <code>EppSession</code> object associated with the
	 * EPP Channel
	 *
	 * @param session the <code>EppSession</code> used for creating the
	 *                channel
	 */
	public void setSession( EppSession session )
	{
		this.session = session;
	}

	/**
	 * Sets the current message received for the EPP Channel
	 */
	public void setMessage( String message )
	{
		this.message = message;
	}

	/**
	 * Gets the last message received for the EPP Channel
	 */
	public String getMessage()
	{
		return this.message;
	}

	/**
	 * Gets the last exception received for the EPP Channel
	 */
	public Exception getException()
	{
		return this.e;
	}

	/**
	 * Sets the current exception received for the EPP Channel
	 */
	public void setException( Exception e )
	{
		this.e = e;
	}

	/**
	 * Starts up the <code>EppChannel</code> by sending an
	 * <code>EppCommandLogin</code> object to the EPP Server
	 *
	 * @param login the <code>EppCommandLogin</code> object for
	 *              establisging the EPP Channel
	 *
	 * @return an <code>EppResponse</code> object indicating if the channel
	 *         is started successfully or not
	 */
	abstract public EppResponse start( EppCommandLogin login );
	
	/**
	 * Sends an EPP Hello message to the EPP Server and returns
	 * back with an <code>EppGreeting</code> object
	 *
	 * @return an <code>EppGreeting</code> returned by the EPP Server,
	 *         or null if there is any error
	 */
	abstract public EppGreeting hello();

	/**
	 * Terminates an EPP Channel
	 *
	 * @return an <code>EppResponse</code> object indicating if the
	 *         channel is terminated successfully or not
	 */
	abstract public EppResponse terminate();

	/**
	 * Sends an <code>EppCommand</code> object to the EPP Server and gets
	 * an <code>EppResponse</code> object back from the EPP Server
	 *
	 * @param command the command to be send to the EPP server
	 *
	 * @return an <code>EppResponse</code> object, or null if there is
	 *         error with the connection to the server
	 *
	 * <P><B>Note:</B> If there is any syntax error or missing required parameters
	 *       in the command, the command will not be sent out, and a
	 *       EPP Response object will be returned indicating the errors.
	 *       As there is no EPP server action involved, the transaction
	 *       id for both the client and the server are the same value
	 *       copied from the command itself.<BR>If there is any syntax
	 *       error or missing required parameters from the response data
	 *       from the EPP Server, the EPP Response would include an
	 *       <code>EppUnspec</code> object with the XML data returned
	 *       from the server as its value.<BR>If there is any error
	 *       with the connection to to server, the <code>getException</code>
	 *       should be called for obtaining the details of the error.
	 */
	abstract public EppResponse send( EppCommand command );
}
