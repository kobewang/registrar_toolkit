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

#if ! defined(EPPCHANNELTCP_HPP)    /* { */
#define       EPPCHANNELTCP_HPP        1

#include "EppChannel.hpp"
#include "EppSessionTcp.hpp"
#include "EppCommand.hpp"
#include "EppCommandLogin.hpp"
#include "EppResponse.hpp"
#include "EppGreeting.hpp"

/**
 * This <code>EppChannelTcp</code> is designed to handle a session connection
 * to the EPP server via TCP/TLS.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
class EPP_EXPORT EppChannelTcp : public EppChannel
{
public:

	/**
	 * Creates an <code>EppChannelTcp</code> object
	 */
	EppChannelTcp()
	{
		this->setSession(null);
	};

	/**
	 * Creates an <code>EppChannelTcp</code> object
	 *
	 * @param session the <code>EppSessionTcp</code> object in which
	 *                an EPP Channel is created
	 */
	EppChannelTcp( EppSessionTcp * session )
	{
		this->setSession(session);
	};

	/**
	 * Destructor
	 */
	~EppChannelTcp()
	{
		this->setSession(null);
	};

	/**
	 * Gets the socket associated with the EPP Channel
	 */
	int getSocket()
	{
		return ((EppSessionTcp *) this->getSession())->getSocket();
	};

	/**
	 * Gets the <code>SSL</code> object of OpenSSL associated with the
	 * EPP Channel
	 */
	SSL * getSSL()
	{
		return ((EppSessionTcp *) this->getSession())->getSSL();
	};

	/**
	 * Check if OpenSSL is used for establishing the EPP session
	 */
	bool useTLS()
	{
		return ((EppSessionTcp *) this->getSession())->useTLS();
	};

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
	 *       from the EPP Server, the message can be retrieved by calling
	 *       the <code>getMessage</code> method.<BR>If there is any error
	 *       with the connection to to server, the <code>getException</code>
	 *       should be called for obtaining the details of the error.
	 */
	EppResponse * send( EppCommand& command );

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
	EppResponse * start( EppCommandLogin& login );

	/**
	 * Sends an EPP Hello message to the EPP Server and returns
	 * back with an <code>EppGreeting</code> object
	 *
	 * @return an <code>EppGreeting</code> returned by the EPP Server,
	 *         or null if there is any error
	 */
	EppGreeting * hello();

	/**
	 * Terminates an EPP Channel
	 *
	 * @return an <code>EppResponse</code> object indicating if the
	 *         channel is terminated successfully or not
	 */
	EppResponse * terminate();
};

#endif     /* EPPCHANNELTCP_HPP */  /* } */
