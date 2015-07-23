/*******************************************************************************
 * The MIT License (MIT)
 *  
 * Copyright (c)2015 Neustar Inc.
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

#if ! defined(EPPCHANNEL_HPP)    /* { */
#define       EPPCHANNEL_HPP        1

#include <string.h>
#include "EppGreeting.hpp"
#include "EppCommand.hpp"
#include "EppCommandLogin.hpp"
#include "EppResponse.hpp"
#include "EppSession.hpp"

/**
 * This <code>EppChannel</code> class is designed to handle a channel connected
 * to the EPP server.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
class EPP_EXPORT EppChannel
{
private:
	char       * e;
	int          eLength;
	char       * message;
	int          messageLength;
	EppSession * session;

protected:
	/**
	 * The status value when the <code>EppChannel</code> object is
	 * created, but not started
	 */
	static const int STATUS_INIT;
	/**
	 * The status value when the <code>EppChannel</code> object is
	 * started after sucessfully calling the <code>start</code> method
	 */
	static const int STATUS_START;
	/**
	 * The status value when the <code>EppChannel</code> object is
	 * closed atfer successfully calling the <code>close</code> method
	 */
	static const int STATUS_CLOSED;
	/**
	 * The status of the <code>EppChannel</code> object. It is used
	 * for recording the status transition from <code>STATUS_INIT</code>
	 * to <code>STATUS_START</code>, and finally to
	 * <code>STATUS_CLOSED</code>
	 */
	int      status;

public:

	/**
	 * Creates an <code>EppChannel</code> object
	 */
	EppChannel()
	{
		this->e = null;
		this->eLength = 0;
		this->message = null;
		this->messageLength = 0;
		this->session = null;
		this->status = STATUS_INIT;
	};

	/**
	 * Destructor
	 */
	virtual ~EppChannel()
	{
		if( this->e != null )
		{
			delete [] this->e;
			this->e = null;
			this->eLength = 0;
		}
		if( this->message != null )
		{
			delete [] this->message;
			this->message = null;
			this->messageLength = 0;
		}
		this->session = null;
		this->status = STATUS_CLOSED;
	};

	/**
	 * Gets the <code>EppSession</code> object associated with the 
	 * EPP Channel
	 */
	EppSession * getSession()
	{
		return this->session;
	};

	/**
	 * Sets the <code>EppSession</code> object associated with the
	 * EPP Channel
	 *
	 * @param session the <code>EppSession</code> used for creating the
	 *                channel
	 */
	void setSession( EppSession * session )
	{
		this->session = session;
	};

	/**
	 * Gets the last message received for the EPP Channel
	 */
	const char * getMessage()
	{
		if( this->message != null )
		{
			if( *this->message == 0 )
			{
				return null;
			}
		}
		return this->message;
	};

	/**
	 * Sets the current message received for the EPP Channel
	 */
	void setMessage( const char * message )
	{
		if( message == null )
		{
			if( this->message != null )
			{
				*this->message = 0;
			}
			return;
		}
		int len = strlen(message) + 1;
		if( len > messageLength )
		{
			if( this->message != null )
			{
				delete [] this->message;
			}
			this->message = new char[len];
			this->messageLength = len;
		}
		if( this->message != null )
		{
			(void) strcpy(this->message, message);
		}
	};

	/**
	 * Gets the last exception received for the EPP Channel
	 */
	const char * getException()
	{
		if( this->e != null )
		{
			if( *this->e == 0 )
			{
				return null;
			}
		}
		return this->e;
	};

	/**
	 * Sets the current exception received for the EPP Channel
	 */
	void setException( const char * e )
	{
		if( e == null )
		{
			if( this->e != null )
			{
				*this->e = 0;
			}
			return;
		}
		int len = strlen(e) + 1;
		if( len > eLength )
		{
			if( this->e != null )
			{
				delete [] this->e;
			}
			this->e = new char[len];
			this->eLength = len;
		}
		if( this->e != null )
		{
			(void) strcpy(this->e, e);
		}
	};

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
	virtual EppResponse * start( EppCommandLogin& login ) = 0;

	/**
	 * Sends an EPP Hello message to the EPP Server and returns
	 * back with an <code>EppGreeting</code> object
	 *
	 * @return an <code>EppGreeting</code> returned by the EPP Server,
	 *         or null if there is any error
	 */
	virtual EppGreeting * hello() = 0;

	/**
	 * Terminates an EPP Channel
	 *
	 * @return an <code>EppResponse</code> object indicating if the
	 *         channel is terminated successfully or not
	 */
	virtual EppResponse * terminate() = 0;

	/**
	 * Sends an <code>EppCommand</code> object to the EPP Server and gets
	 * an <code>EppResponse</code> object back from the EPP Server
	 *
	 * @param command the command to be send to the EPP server
	 *
	 * @return an <code>EppResponse</code> object, or null if there is
	 *         error with the connection to the server
	 *
	 * @note If there is any syntax error or missing required parameters
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
	virtual EppResponse * send( EppCommand& command ) = 0;
};

#endif     /* EPPCHANNEL_HPP */  /* } */
