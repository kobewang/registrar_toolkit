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

#if ! defined(EPPSESSIONTCP_HPP)    /* { */
#define       EPPSESSIONTCP_HPP        1

#include <openssl/ssl.h>
#include <openssl/err.h>
#include <openssl/crypto.h>
#include <openssl/x509.h>

#include "EppSession.hpp"

#define	MAX_NUM_OF_DELAY_SECONDS	120

class EppChannelTcp;

/**
 * This <code>EppSessionTcp</code> is designed to handle a channel connection
 * to the EPP server via TCP/TLS.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
class EPP_EXPORT EppSessionTcp : public EppSession
{
private:
	char          * host;
	int             port;
	EppChannelTcp * channel;
	bool            useTls;
	int             sock;
	SSL_CTX       * sslctx;
	SSL           * sslssl;
	int		sotimeout;

	int             privateKeyFileType;
	char          * privateKeyFileName;
	int             publicKeyFileType;
	char          * publicKeyFileName;
	char          * remoteKeyFileName;

	/**
	 * Initializes connection using plain socket
	 */
	void initSocket();

	/**
	 * Initializes connection using OpenSSL
	 */
	void initTLS();

	/**
	 * Cleans up everything
	 */
	void cleanup();

	/**
	 * Utility method to set socket options
	 */
	void setSocketOption();

	/**
	 * Utility method to shutdown (optionally ) and close the socket.
	 * @param bShutdown, if bShutdown is true, this method would call socket shutdown as well.
	 */
	void resetSocket( bool bShutdown );

	/**
	 * Try to connect to host pointed by addr. 
	 * @param addr, Address to which connect would be attempted. 
	 * @return true if connection was successfully established, false otherwise,
	 */
	bool connectToAddress( struct addrinfo* addr );

public:
	/**
	 * Creates an <code>EppSessionTcp</code> object, which will use
	 * TLS as the transport layer security protocol.
	 */
	EppSessionTcp();

	/**
	 * Creates an <code>EppSessionTcp</code> object, given a flag
	 * indicating if TLS should be used as the transport
	 * layer security protocol.
	 */
	EppSessionTcp( bool useTLS );

	/**
	 * Destructor
	 */
	~EppSessionTcp();

	/**
	 * Initializes the run-time parameters related to an EPP Session.
	 * This method must be called before the EPP Session is started.
	 * The configuration object can be a <code>Properties</code>
	 * object if the implementation choose to use Java Property
	 * file, or an <code>DOMElement</code> object, if the configuration
	 * file is in XML format, or an <code>const char *</code> that represents
	 * the name of the configuration file in any format.
	 *
	 * @param object - the configuration object for setting up run-time
	 *                 parameters related to the EPP Session
	 *
	 * @note currently the object is type of <I>char * argv[3]</I>, containing
	 *       the names of three files in SSL_FILETYPE_PEM format:<UL>
	 *       <LI>argv[0] - client private key file</LI>
	 *       <LI>argv[1] - client public key file</LI>
	 *       <LI>argv[2] - server public key file</LI></UL>
	 */
	void init( void * object );

	/**
	 * Sets the client private key file and type. The file type must be
	 * either SSL_FILETYPE_DER or SSL_FILETYPE_PEM format
	 */
	void setPrivateKeyFile( char * name, int type );

	/**
	 * Sets the client public key file and type. The file type must be
	 * either SSL_FILETYPE_DER or SSL_FILETYPE_PEM format
	 */
	void setPublicKeyFile( char * name, int type );

	/**
	 * Sets the server public key file. The file may contains multiple
	 * certificates and must be in SSL_FILETYPE_PEM format
	 */
	void setRemoteKeyFile( char * name );

	/**
	 * Sets the socket timeout value used for calling setsockopt(2) with
	 * parameter SOL_SOCKET, SO_RVCTIMEO and SO_SNDTIMEO, before the socket
	 * is bounded. The default value is 0, which indicates that setsockopt(2)
	 * will not be called.
	 */
	void setSocketTimeout( int timeout )
	{
		this->sotimeout = timeout;
		if( this->sotimeout < 0 )
		{
			this->sotimeout = 0;
		}
	};

	/**
	 * Gets the socket timeout value used for calling setsockopt(2) with
	 * parameter SOL_SOCKET, SO_RVCTIMEO and SO_SNDTIMEO, before the socket
	 * is bounded. The default value is 0, which indicates that setsockopt(2)
	 * will not be called.
	 */
	int getSocketTimeout()
	{
		return this->sotimeout;
	};

	/**
	 * Connects to the EPP Server and establishs an EPP session over
	 * TCP/TLS
	 *
	 * @param host the host name of the EPP Server to be connected
	 * @param port the port number of the EPP Server to be connected
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
	 *       obtaining the last message received from the EPP Server
	 */
	EppGreeting * connect( const char * host, const int port );

	/**
	 * Closes the EPP Session over TCP/TLS
	 */
	void close();

	/**
	 * Allocates an EPP Channel over TCP/TLS
	 *
	 * @return an <code>EppChannel</code> object, or null if the EPP
	 *         Channel cannot be allocated
	 *
	 * @note only one EPP Channel can be allocated for one EPP Sesseion
	 *       over TCP/TLS
	 */
	EppChannel * getChannel();

	/**
	 * Sends an EPP Hello message to the EPP Server and returns
	 * back with an <code>EppGreeting</code> object
	 *
	 * @return an <code>EppGreeting</code> returned by the EPP Server,
	 *         or null if there is any error
	 */
	EppGreeting * hello();

	/**
	 * Gets the socket associated with the EPP Session without OpenSSL
	 */
	int getSocket()
	{
		return this->sock;
	};

	/**
	 * Gets the SSL object associated with the EPP Session using OpenSSL
	 */
	SSL * getSSL()
	{
		return this->sslssl;
	};

	/**
	 * Gets the flag indicating if TLS is used
	 */
	bool useTLS()
	{
		return this->useTls;
	};
};

#endif     /* EPPSESSIONTCP_HPP */  /* } */
