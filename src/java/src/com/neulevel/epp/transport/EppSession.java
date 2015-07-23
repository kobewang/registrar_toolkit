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

import java.io.*;
import com.neulevel.epp.core.*;
import com.neulevel.epp.core.command.*;
import com.neulevel.epp.core.response.*;

/**
 * This <code>EppSession</code> class is designed to handle a session
 * connected to the EPP Server.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.3 $ $Date: 2006/03/11 23:39:22 $
 */
abstract public class EppSession
{
	private Exception e;
	private String    message;

	/**
	 * The property for enabling client side authentication, default true
	 */
	public static final String CLIENT_AUTH  = "ssl.client.authentication";
	/**
	 * The property for key manager algorithm, default "SUNX509"
	 */
	public static final String KM_ALGORITHM = "ssl.keymanager.algorithm";
	/**
	 * The property for key manager provider, default null
	 */
	public static final String KM_PROVIDER  = "ssl.keymanager.provider";
	/**
	 * The property for key store type, default "JKS"
	 */
	public static final String KS_TYPE      = "ssl.keystore.type";
	/**
	 * The property for key store provider, default null
	 */
	public static final String KS_PROVIDER  = "ssl.keystore.provider";
	/**
	 * The property for key store format, default "file".
	 *
	 * <P><B>Note:</B> Currently, only the "file" format is supported.
	 */
	public static final String KS_FORMAT    = "ssl.keystore.format";
	/**
	 * The property for key store name.
	 *
	 * If the key store format is "file",
	 * the value of this property is the file name of the key store file.
	 */
	public static final String KS_NAME      = "ssl.keystore.name";
	/**
	 * The property for key store password, default null
	 */
	public static final String KS_STOREPASS = "ssl.keystore.storepass";
	/**
	 * The property for key password for recovering keys, default as
	 * the value of the key store password
	 */
	public static final String KS_KEYPASS   = "ssl.keystore.keypass";
	/**
	 * The property for trust manager algorithm, default "SUNX509"
	 */
	public static final String TM_ALGORITHM = "ssl.trustmanager.algorithm";
	/**
	 * The property for trust manager provider, default null
	 */
	public static final String TM_PROVIDER  = "ssl.trustmanager.provider";
	/**
	 * The property for trust store type, default "JKS"
	 */
	public static final String TS_TYPE      = "ssl.truststore.type";
	/**
	 * The property for trust store provider, default null
	 */
	public static final String TS_PROVIDER  = "ssl.truststore.provider";
	/**
	 * The property for trust store format, default "file".
	 *
	 * <P><B>Note:</B> Currently, only the "file" format is supported.
	 */
	public static final String TS_FORMAT    = "ssl.truststore.format";
	/**
	 * The property for trust store name.
	 *
	 * If the key store format is "file",
	 * the value of this property is the file name of the trust store file.
	 */
	public static final String TS_NAME      = "ssl.truststore.name";
	/**
	 * The property for trust store password, default null
	 */
	public static final String TS_STOREPASS = "ssl.truststore.storepass";
	/**
	 * The property for cipher suite, default null.
	 *
	 * @note (BEEP/PureTLS only)
	 */
	public static final String PK_CIPHER_SUITE = "ssl.puretls.cipher.suite";
	/**
	 * The property for file name storing public key (PEM format)
	 *
	 * @note (BEEP/PureTLS only)
	 */
	public static final String PK_PUBLIC_KEY = "ssl.ptls.public.keyfile";
	/**
	 * The property for file name storing private key (PEM format)
	 *
	 * @note (BEEP/PureTLS only)
	 */
	public static final String PK_PRIVATE_KEY = "ssl.ptls.private.keyfile";
	/**
	 * The property for the passphrease of the file storing private key,
	 * default ""
	 *
	 * @note (BEEP/PureTLS only)
	 */
	public static final String PK_PRIVATE_PASS = "ssl.ptls.private.keypass";
	/**
	 * The property for the private key algorithm, default "RSA"
	 *
	 * @note (BEEP/PureTLS only)
	 */
	public static final String PK_PRIVATE_TYPE = "ssl.ptls.private.keytype";
	/**
	 * The property for file name storing trusted certificates (PEM format)
	 *
	 * @note (BEEP/PureTLS only)
	 */
	public static final String PK_TRUSTED_CERTS = "ssl.ptls.trusted.certfile";

	/**
	 * Initializes the run-time parameters related to an EPP Session.
	 *
	 * This method must be called before the EPP Session is started.
	 * The configuration object can be a <code>Properties</code>
	 * object if the implementation choose to use Java Property
	 * file, or an <code>Element</code> object, if the configuration
	 * file is in XML format, or an <code>String</code> that represents
	 * the name of the configuration file in any format.
	 *
	 * @param object the configuration object for setting up run-time
	 *               parameters related to the EPP Session
	 */
	abstract public void init( Object object ) throws IOException, FileNotFoundException;

	/**
	 * Gets the last exception received for the EPP Session
	 */
	public Exception getException()
	{
		return this.e;
	}

	/**
	 * Sets the current exception received for the EPP Session
	 */
	public void setException( Exception e )
	{
		this.e = e;
	}

	/**
	 * Gets the last message received for the EPP Session
	 */
	public String getMessage()
	{
		return this.message;
	}

	/**
	 * Sets the current message received for the EPP Session
	 */
	public void setMessage( String message )
	{
		this.message = message;
	}

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
	 * <P><B>Note:</B> If there is any error occurred during the process of
	 *       establishing the connection, this method will return null,
	 *       and <code>getException</code> method should be called
	 *       for obtaining the details of the error. If the exception
	 *       returned is null, then the <code>getMessage</code> for
	 *       obtaining the last message received from the EPP Server.
	 */
	abstract public EppGreeting connect( String host, int port );
	
	/**
	 * Closes the EPP Session.
	 *
	 * <P><B>Note:</B> If there is any error occurred during the process of
	 *       closing the EPP Session, <code>getException</code> method
	 *       should be called for obtaining the details of the error
	 */
	abstract public void close();

	/**
	 * Allocates an EPP Channel
	 *
	 * @return an <code>EppChannel</code> object, or null if the EPP
	 *         Channel cannot be allocated
	 *
	 * <P><B>Note:</B> Different transport mechanisms may allow different numbers
	 *       of EPP Channels to be established with one EPP Session.
	 */
	abstract public EppChannel getChannel();

	/**
	 * Sends an EPP Hello message to the EPP Server and returns
	 * back with an <code>EppGreeting</code> object
	 *
	 * @return an <code>EppGreeting</code> returned by the EPP Server,
	 *         or null if there is any error
	 */
	abstract public EppGreeting hello();
}
