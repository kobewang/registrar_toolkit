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
import java.util.*;
import java.security.*;
import javax.net.*;
import javax.net.ssl.*;
//import com.sun.net.ssl.*;
import javax.security.cert.*;

import com.neulevel.epp.core.*;
import com.neulevel.epp.core.command.*;
import com.neulevel.epp.core.response.*;
import com.neulevel.epp.transport.*;

/**
 * This <code>EppSessionTcp</code> is designed to handle a channel connection
 * to the EPP server via TCP/TLS.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.7 $ $Date: 2010/01/05 13:52:44 $
 */
public class EppSessionTcp extends EppSession
{
	private String           host;
	private int              port;
	private SSLSocketFactory factory;
	private Socket           socket;
	private EppChannelTcp    channel;
	private boolean          useTls;
	private Properties       tlsArgs;
	private String           xsdFilesLocation;
	public String getXsdFilesLocation() {
    return xsdFilesLocation;
  }

  public void setXsdFilesLocation(String xsdFilesLocation) {
    this.xsdFilesLocation = xsdFilesLocation;
  }

  /**
	 * Creates an <code>EppSessionTcp</code> object, which will use
	 * TLS as the transport layer security protocol.
	 */
	public EppSessionTcp()
	{
		this(true);
	}

	/**
	 * Creates an <code>EppSessionTcp</code> object, given a flag
	 * indicating if TLS should be used as the transport
	 * layer security protocol.
	 */
	public EppSessionTcp( boolean useTLS )
	{
		this.useTls = useTLS;
		this.channel = null;
	}

	/**
	 * Initializes the run-time parameters related to an EPP Session.
	 * This method must be called before the EPP Session is started.
	 * The configuration object can be a <code>Properties</code>
	 * object if the implementation choose to use Java Property
	 * file, or an <code>Element</code> object, if the configuration
	 * file is in XML format, or an <code>String</code> that represents
	 * the name of the configuration file in any format.
	 *
	 * @param object - the configuration object for setting up run-time
	 *                 parameters related to the EPP Session
	 *
	 * <P><B>Note:</B> Currently only Java Property file format is supported. The
	 *       <code>object</code> parameter is either a
	 *       <code>Properties</code> object or a Java property file name.
	 */
	public void init( Object object ) throws IOException, FileNotFoundException
	{
		if( object instanceof String )
		{
			this.tlsArgs = new Properties();
			FileInputStream in = null;
			try
			{
				in = new FileInputStream((String) object);
				this.tlsArgs.load(in);
			}
			finally {
				if (in != null) in.close();
			}
		}
		else if( object instanceof Properties )
		{
			this.tlsArgs = (Properties) object;
		}
	}

	private SSLSocketFactory getSSLSocketFactory() throws Exception
	{
		if( this.tlsArgs == null )
		{
			return (SSLSocketFactory) SSLSocketFactory.getDefault();
		}

		SSLContext ctx          = SSLContext.getInstance("TLS");
		KeyManagerFactory   kmf = null;
		KeyStore            ks  = null;
		TrustManagerFactory tmf = null;
		KeyStore            ts  = null;

		String kmAlgorithm;
		String kmProvider;
		String ksType;
		String ksProvider;
		String ksName;
		String ksFormat;
		String ksStorePass;
		String ksKeyPass;

		kmAlgorithm = this.tlsArgs.getProperty(KM_ALGORITHM, "SUNX509");
		kmProvider  = this.tlsArgs.getProperty(KM_PROVIDER,  null);
		ksType      = this.tlsArgs.getProperty(KS_TYPE,      "JKS");
		ksProvider  = this.tlsArgs.getProperty(KS_PROVIDER,  null);
		ksName      = this.tlsArgs.getProperty(KS_NAME,      null);
		ksFormat    = this.tlsArgs.getProperty(KS_FORMAT,    "file");
		ksStorePass = this.tlsArgs.getProperty(KS_STOREPASS, null);
		ksKeyPass   = this.tlsArgs.getProperty(KS_KEYPASS,   ksStorePass);

		String tmAlgorithm;
		String tmProvider;
		String tsType;
		String tsProvider;
		String tsName;
		String tsFormat;
		String tsStorePass;

		tmAlgorithm = this.tlsArgs.getProperty(TM_ALGORITHM, "SUNX509");
		tmProvider  = this.tlsArgs.getProperty(TM_PROVIDER,  null);
		tsType      = this.tlsArgs.getProperty(TS_TYPE,      "JKS");
		tsProvider  = this.tlsArgs.getProperty(TS_PROVIDER,  null);
		tsName      = this.tlsArgs.getProperty(TS_NAME,      null);
		tsFormat    = this.tlsArgs.getProperty(TS_FORMAT,    "file");
		tsStorePass = this.tlsArgs.getProperty(TS_STOREPASS, null);

		if( kmProvider == null )
		{
			kmf = KeyManagerFactory.getInstance(kmAlgorithm);
		}
		else
		{
			kmf = KeyManagerFactory.getInstance(kmAlgorithm, kmProvider);
		}
		if( ksProvider == null )
		{
			ks = KeyStore.getInstance(ksType);
		}
		else
		{
			ks = KeyStore.getInstance(ksType, ksProvider);
		}
		if( ksFormat.equalsIgnoreCase("file") )
		{
			FileInputStream in = null;
			try {
				in = new FileInputStream(ksName);
				ks.load(in, ksStorePass.toCharArray());
			}catch(IOException ioex){
				ioex.printStackTrace();
			} finally{
				if(in != null) in.close();
			}
			
		}
		else
		{
			throw new KeyStoreException("Key Store Format [" +
					ksFormat + "] not supported");
		}
		kmf.init(ks, ksKeyPass.toCharArray());

		if( tmProvider == null )
		{
			tmf = TrustManagerFactory.getInstance(tmAlgorithm);
		}
		else
		{
			tmf = TrustManagerFactory.getInstance(tmAlgorithm, tmProvider);
		}
		if( tsProvider == null )
		{
			ts = KeyStore.getInstance(tsType);
		}
		else
		{
			ts = KeyStore.getInstance(tsType, tsProvider);
		}
		if( tsFormat.equalsIgnoreCase("file") )
		{
			FileInputStream in = null;
			try{
				in = new FileInputStream(tsName);
				ts.load(in, tsStorePass.toCharArray());
			}catch(IOException ioex){
				ioex.printStackTrace();
			} finally {
				if (in != null) in.close();
			}
			
		}
		else
		{
			throw new KeyStoreException("Trust Store Format [" +
					tsFormat + "] not supported");
		}
		tmf.init(ts);
		ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

		return (SSLSocketFactory) ctx.getSocketFactory();
	}

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
	 * <P><B>Note:</B> If there is any error occurred during the process of
	 *       establishing the connection, this method will return null,
	 *       and <code>getException</code> method should be called
	 *       for obtaining the details of the error. If the exception
	 *       returned is null, then the <code>getMessage</code> for
	 *       obtaining the last message received from the EPP Server
	 */
	public EppGreeting connect( String host, int port )
	{
		EppGreeting greeting = null;

		this.host = host;
		this.port = port;
		this.setException(null);
		this.setMessage(null);

		try
		{
			if( this.useTls )
			{
				this.factory = getSSLSocketFactory();
				this.socket = factory.createSocket(this.host, this.port);
//				this.socket.setUseClientMode(true);
//				this.socket.startHandshake(true);
			}
			else
			{
				this.socket = new Socket(this.host, this.port);
			}

			this.socket.setSoTimeout(EppMessageUtil.getTimeout()*1000);


			// expecting an EPP Greeting message

			String str = EppMessageUtil.getEppPayload(this.socket.getInputStream());
			this.setMessage(str);

			EppParser parser = new EppParser(str,xsdFilesLocation);
			if(parser.hasError()) {
				this.setException(new RuntimeException("Error parsing greeting: " + parser.getErrorMessage()));
				return null;
			}

			greeting = (EppGreeting) EppGreeting.fromXML(parser.getRootNode());
		}
		catch( Exception e )
		{
			this.setException(e);
			return null;
		}

		return greeting;
	}

	/**
	 * Closes the EPP Session over TCP/TLS
	 */
	public void close()
	{
		if( this.channel != null )
		{
			channel.terminate();
			this.channel = null;
		}
		if( this.socket != null )
		{
			try
			{
				this.socket.close();
			}
			catch( Exception e )
			{
				this.setException(e);
			}
		}
		this.socket = null;
	}

	/**
	 * Allocates an EPP Channel over TCP/TLS
	 *
	 * @return an <code>EppChannel</code> object, or null if the EPP
	 *         Channel cannot be allocated
	 *
	 * <P><B>Note:</B> Only one EPP Channel can be allocated for one EPP Sesseion
	 *       over TCP/TLS
	 */
	public EppChannel getChannel()
	{
		if( this.channel == null )
		{
			if((this.channel = new EppChannelTcp(this))!= null) 
			  this.channel.setXsdFilesLocation(xsdFilesLocation);
		}
		return this.channel;
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

		if( this.socket == null )
		{
			return null;
		}

		this.setException(null);
		this.setMessage(null);

		try
		{
			EppHello hello = new EppHello();
			String str = EppMessageUtil.send(this.socket, hello.toString());
			this.setMessage(str);
			EppParser parser = new EppParser(str,xsdFilesLocation);
			if( ! parser.hasError() )
			{
				greeting = (EppGreeting) EppGreeting.fromXML(parser.getRootNode());
			}
		}
		catch( Exception e )
		{
			this.setException(e);
			return null;
		}

		return greeting;
	}

	/**
	 * Gets the socket associated with the EPP Session
	 */
	public Socket getSocket()
	{
		return this.socket;
	}
}
