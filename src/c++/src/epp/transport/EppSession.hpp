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

#if ! defined(EPPSESSION_HPP)    /* { */
#define       EPPSESSION_HPP        1

#include <string.h>
#include "EppGreeting.hpp"

class EppChannel;

/**
 * This <code>EppSession</code> class is designed to handle a session
 * connected to the EPP Server.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
class EPP_EXPORT EppSession
{
private:
	char * e;
	int    eLength;
	char * message;
	int    messageLength;

public:

	/**
	 * Creates an <code>EppSession</code> object
	 */
	EppSession()
	{
		this->e = null;
		this->eLength = 0;
		this->message = null;
		this->messageLength = 0;
	};

	/**
	 * Destructor
	 */
	virtual ~EppSession()
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
	};

	/**
	 * Initializes the run-time parameters related to an EPP Session.
	 * This method must be called before the EPP Session is started.
	 * The configuration object can be a <code>Properties</code>
	 * object if the implementation choose to use Java Property
	 * file, or an <code>DOMElement</code> object, if the configuration
	 * file is in XML format, or an <code>const char *</code> that represents
	 * the name of the configuration file in any format.
	 *
	 * @param object the configuration object for setting up run-time
	 *               parameters related to the EPP Session
	 */
	virtual void init( void * object ) = 0;

	/**
	 * Gets the last exception received for the EPP Session
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
	 * Sets the current exception received for the EPP Session
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
	 * Gets the last message received for the EPP Session
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
	 * Sets the current message received for the EPP Session
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
	 * Connects to the EPP Server and establishs an EPP Session
	 *
	 * @param host the host name of the EPP server to be connected
	 * @param port the port number of the EPP server to be connected
	 *
	 * @return an <code>EppGreeting</code> object returned by the EPP
	 *         server if the connection is successfully established,
	 *         or null if there is any error
	 *
	 * @note if there is any error occurred during the process of
	 *       establishing the connection, this method will return null,
	 *       and <code>getException</code> method should be called
	 *       for obtaining the details of the error. If the exception
	 *       returned is null, then the <code>getMessage</code> for
	 *       obtaining the last message received from the EPP Server.
	 */
	virtual EppGreeting * connect( const char * host, const int port ) = 0;

	/**
	 * Closes the EPP Session
	 *
	 * @note if there is any error occurred during the process of
	 *       closing the EPP Session, <code>getException</code> method
	 *       should be called for obtaining the details of the error
	 */
	virtual void close() = 0;

	/**
	 * Allocates an EPP Channel
	 *
	 * @return an <code>EppChannel</code> object, or null if the EPP
	 *         Channel cannot be allocated
	 *
	 * @note different transport mechanisms may allow different numbers
	 *       of EPP Channels to be established with one EPP Session.
	 */
	virtual EppChannel * getChannel() = 0;

	/**
	 * Sends an EPP Hello message to the EPP Server and returns
	 * back with an <code>EppGreeting</code> object
	 *
	 * @return an <code>EppGreeting</code> returned by the EPP Server,
	 *         or null if there is any error
	 */
	virtual EppGreeting * hello() = 0;
};

#endif     /* EPPSESSION_HPP */  /* } */
