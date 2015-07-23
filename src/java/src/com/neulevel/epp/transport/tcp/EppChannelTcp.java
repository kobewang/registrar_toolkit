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
package com.neulevel.epp.transport.tcp;

import java.io.*;
import java.net.*;

import com.neulevel.epp.core.*;
import com.neulevel.epp.core.command.*;
import com.neulevel.epp.core.response.*;
import com.neulevel.epp.transport.*;

/**
 * This <code>EppChannelTcp</code> is designed to handle a session connection
 * to the EPP server via TCP/TLS.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.3 $ $Date: 2010/01/05 13:52:44 $
 */
public class EppChannelTcp extends EppChannel
{
	private boolean isValid            = true;
	private long    lastNetTransaction = 0;
    private String  xsdFilesLocation;
     
	public String getXsdFilesLocation() {
      return xsdFilesLocation;
    }

    public void setXsdFilesLocation(String xsdFilesLocation) {
      this.xsdFilesLocation = xsdFilesLocation;
    }

  /**
	 * Creates an <code>EppChannelTcp</code> object
	 *
	 * @param session the <code>EppSessionTcp</code> object in which
	 *                an EPP Channel is created
	 */
	public EppChannelTcp( EppSessionTcp session )
	{
		this.setSession(session);
	}

	/**
	 * Gets the socket associated with the EPP Channel
	 */
	public Socket getSocket()
	{
		return ((EppSessionTcp) this.getSession()).getSocket();
	}

	/**
	 * Gets the timestamp of last network transaction, obtained via
	 * <code>System.currentTimeMillis()</code>. If the value returned
	 * is 0, no transaction has been performed.
	 */
	public long getLastNetTransaction()
	{
		return this.lastNetTransaction;
	}
    
	/**
	 * Returns the boolean flag indicating if an <code>IOException</code>
	 * has been caught during a network transaction, or the socket associated
	 * with the channel is not valid. If the return value is false, the
	 * channel as well as the session is no longer valid,
	 */
	public boolean isValid()
	{
		return this.isValid;
	}

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
	 *       from the EPP Server, the message can be retrieved by calling
	 *       the <code>getMessage</code> method.<BR>If there is any error
	 *       with the connection to to server, the <code>getException</code>
	 *       should be called for obtaining the details of the error.
	 */
	public EppResponse send( EppCommand command )
	{
		Socket socket = this.getSocket();
		if( socket == null )
		{
			this.isValid = false;
			return null;
		}

		// first make sure that the command is valid, if not, we
		// will not send the command out at all

		this.setException(null);
		this.setMessage(null);

		String cmd = command.toString();
		EppParser parser = new EppParser(cmd,xsdFilesLocation);
		if( parser.hasError() )
		{
			EppResponse rsp = new EppResponse();
			rsp.addResult(parser.getResult());
			rsp.setTransactionId(new EppTransactionId(
				command.getClientTransactionId(),
				command.getClientTransactionId()));
			return rsp;
		}

		// ok, we are sure that the command syntax is OK, send the
		// the command string in XML format

		String msg = null;
		try
		{
			msg = EppMessageUtil.send(socket, cmd);
		}
		catch( IOException e )
		{
			this.isValid = false;
			this.setException(e);
		}
		catch( Exception e )
		{
			this.setException(e);
		}

		this.lastNetTransaction = System.currentTimeMillis();

		if( msg == null )
		{
			return null;
		}

		// we got an reply from the server, make sure the syntax is OK

		this.setMessage(msg);
		parser = new EppParser(msg,xsdFilesLocation);
		if( parser.hasError() )
		{
			EppResponse rsp = new EppResponse();
			EppResult result = parser.getResult();
			rsp.addResult(result);
			return rsp;
		}

		// there is syntax error with the response

		return (EppResponse) EppResponse.fromXML(parser.getRootNode());
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
	public EppResponse start( EppCommandLogin login )
	{
		EppResponse rsp = this.send(login);
		if( rsp.success() )
		{
			if( this.status == STATUS_INIT )
			{
				this.status = STATUS_START;
			}
		}
		return rsp;
	}

	/**
	 * Sends an EPP Hello message to the EPP Server and returns
	 * back with an <code>EppGreeting</code> object
	 *
	 * @return an <code>EppGreeting</code> returned by the EPP Server,
	 *         or null if there is any error
	 */
	public EppGreeting hello()
	{
		EppGreeting greeting = null;

		Socket socket = this.getSocket();
		if( socket == null )
		{
			this.isValid = false;
			return null;
		}

		this.setException(null);
		this.setMessage(null);

		try
		{
			EppHello hello = new EppHello();
			String str = EppMessageUtil.send(socket, hello.toString());
			this.setMessage(str);
			EppParser parser = new EppParser(str,xsdFilesLocation);
			if( ! parser.hasError() )
			{
				greeting = (EppGreeting) EppGreeting.fromXML(parser.getRootNode());
			}
		}
		catch( IOException e )
		{
			this.isValid = false;
			this.setException(e);
		}
		catch( Exception e )
		{
			this.setException(e);
			return null;
		}

		this.lastNetTransaction = System.currentTimeMillis();

		return greeting;
	}

	/**
	 * Terminates an EPP Channel
	 *
	 * @return an <code>EppResponse</code> object indicating if the
	 *         channel is terminated successfully or not
	 */
	public EppResponse terminate()
	{
		if( this.status != STATUS_START )
		{
			return null;
		}
		EppResponse rsp = this.send(new EppCommandLogout());
		if( (rsp != null) && rsp.success() )
		{
			this.status = STATUS_CLOSED;
		}
		return rsp;
	}
}
