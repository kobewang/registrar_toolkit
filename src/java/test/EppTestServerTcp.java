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
import java.io.*;
import java.net.*;
import java.lang.*;
import java.util.*;
import java.security.*;
import javax.net.*;
import javax.net.ssl.*;
import javax.security.cert.*;

import org.w3c.dom.*;

import com.neulevel.epp.core.*;
import com.neulevel.epp.core.command.*;
import com.neulevel.epp.core.response.*;
import com.neulevel.epp.xri.*;
import com.neulevel.epp.xri.command.*;
import com.neulevel.epp.xri.response.*;
import com.neulevel.epp.transport.*;
import com.neulevel.epp.transport.tcp.*;

/**
 * This <code>EppTestServerTcpThread</code> class is designed to handle
 * one EPP Channel over TCP/TLS.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.4 $ $Date: 2006/09/06 09:31:56 $
 */
class EppTestServerTcpThread extends Thread
{
	private Socket socket;
	private boolean hasLogin;
	private boolean quit;

	/**
	 * Creates an <code>EppTestServerTcpThread</code> object
	 *
	 * @param socket the socket to which the client is connected
	 */
	public EppTestServerTcpThread( Socket socket )
	{
		super();
		this.socket = socket;
		this.hasLogin = false;
		this.quit = false;
	}

	/**
	 * Sets a faked transaction id in an <code>EppResponse</code> object
	 */
	private void setTransactionId( EppResponse rsp )
	{
		rsp.setTransactionId(new EppTransactionId(
			"NO-CLIENT-ID", "SERVER-ID-9999"));
	}

	/**
	 * Sets a faked server transaction id in an <code>EppResponse</code>
	 * object, given the <code>EppCommand</code> object from the client
	 */
	private void setTransactionId( EppResponse rsp, EppCommand cmd )
	{
		rsp.setTransactionId(new EppTransactionId(
			cmd.getClientTransactionId(), "SERVER-ID-9999"));
	}

	/**
	 * Sets response data for a EppCommandCreate object
	 */
	private void setResponseDataCreate( EppCommand cmd, EppResponse rsp )
	{
		EppObject obj = ((EppCommandCreate) cmd).getObject();
		EppResponseDataCreate data = null;
		if( obj instanceof EppContact )
		{
			String id = ((EppContact) obj).getId();
			data = new EppResponseDataCreateContact(id);
		}
		else if( obj instanceof EppDomain )
		{
			String name = ((EppDomain) obj).getName();
			data = new EppResponseDataCreateDomain(name, Calendar.getInstance());
			
		}
		else if( obj instanceof EppHost )
		{
			String name = ((EppHost) obj).getName();
			data = new EppResponseDataCreateHost(name);
		}
		else if( obj instanceof EppSvcsub )
		{
			String id = ((EppSvcsub) obj).getId();
			data = new EppResponseDataCreateSvcsub(id, Calendar.getInstance());
		}
		else if( obj instanceof EppXriAuthority )
		{
			String id = ((EppXriAuthority) obj).getAuthorityId();
			data = new EppResponseDataCreateXriAuthority(id, Calendar.getInstance());
		}
		else if( obj instanceof EppXriName )
		{
			String name = ((EppXriName) obj).getIName();
			data = new EppResponseDataCreateXriName(name, Calendar.getInstance());
		}
		else if( obj instanceof EppXriNumber )
		{
			String num = ((EppXriNumber) obj).getINumber();
			data = new EppResponseDataCreateXriNumber(num, Calendar.getInstance());
		}
		else if( obj instanceof EppXriService )
		{
			EppXriService srv = (EppXriService)obj;
			String id = srv.getId();
			data = new EppResponseDataCreateXriService(
				srv.getId(),
				srv.getType(),
				Calendar.getInstance(),
				Calendar.getInstance()
			);
		}
		if( data != null )
		{
			rsp.setResponseData(data);
		}
	}

	/**
	 * Sets response data for a EppCommandCheck object
	 */
	private void setResponseDataCheck( EppCommand cmd, EppResponse rsp )
	{
		EppResponseDataCheck data = null;
		if( cmd instanceof EppCommandCheckContact )
		{
			data = new EppResponseDataCheckContact();
		}
		else if( cmd instanceof EppCommandCheckDomain )
		{
			data = new EppResponseDataCheckDomain();
		}
		else if( cmd instanceof EppCommandCheckHost )
		{
			data = new EppResponseDataCheckHost();
		}
		else if( cmd instanceof EppCommandCheckSvcsub )
		{
			data = new EppResponseDataCheckSvcsub();
		}
		else if( cmd instanceof EppCommandCheckXriAuthority )
		{
			data = new EppResponseDataCheckXriAuthority();
		}
		else if( cmd instanceof EppCommandCheckXriName )
		{
			data = new EppResponseDataCheckXriName();
		}
		else if( cmd instanceof EppCommandCheckXriNumber )
		{
			data = new EppResponseDataCheckXriNumber();
		}
		else if( cmd instanceof EppCommandCheckXriService )
		{
			data = new EppResponseDataCheckXriService();
		}

		Vector list = ((EppCommandCheck) cmd).get();
		for( int i = 0; i < list.size(); i++ )
		{
			String id = (String) list.get(i);
			data.add(id,  ((i % 2) == 0) ?
				EppResponseDataCheck.FOUND :
				EppResponseDataCheck.NOT_FOUND);
		}

		rsp.setResponseData(data);
	}

	/**
	 * Sets response data for a EppCommandRenew object
	 */
	private void setResponseDataRenew( EppCommand cmd, EppResponse rsp )
	{
		EppResponseData data = null;
		if( cmd instanceof EppCommandRenewDomain )
		{
			String name = ((EppCommandRenewDomain) cmd).getName();
			data = new EppResponseDataCreateDomain(name, Calendar.getInstance());
		}
		else if( cmd instanceof EppCommandRenewSvcsub )
		{
			String id = ((EppCommandRenewSvcsub) cmd).getId();
			data = new EppResponseDataCreateSvcsub(id, Calendar.getInstance());
		}
		if( cmd instanceof EppCommandRenewXriName )
		{
			String name = ((EppCommandRenewXriName) cmd).getIName();
			data = new EppResponseDataCreateXriName(name, Calendar.getInstance());
		}
		if( cmd instanceof EppCommandRenewXriNumber )
		{
			String num = ((EppCommandRenewXriNumber) cmd).getINumber();
			data = new EppResponseDataCreateXriNumber(num, Calendar.getInstance());
		}
		if( cmd instanceof EppCommandRenewXriService )
		{
			String id = ((EppCommandRenewXriService) cmd).getId();
			data = new EppResponseDataCreateXriService(id, Calendar.getInstance());
		}
		rsp.setResponseData(data);
	}

	/**
	 * Sets response data for a EppCommandTransfer object
	 */
	private void setResponseDataTransfer( EppCommand cmd, EppResponse rsp )
	{
		EppResponseDataTransfer data = null;
		if( cmd instanceof EppCommandTransferContact )
		{
			String id = ((EppCommandTransferContact) cmd).getId();
			data = new EppResponseDataTransferContact(id);
		}
		else if( cmd instanceof EppCommandTransferDomain )
		{
			String name = ((EppCommandTransferDomain) cmd).getName();
			data = new EppResponseDataTransferDomain(name);
			((EppResponseDataTransferDomain) data).setDateExpired(Calendar.getInstance());
		}
		else if( cmd instanceof EppCommandTransferSvcsub )
		{
			String id = ((EppCommandTransferSvcsub) cmd).getId();
			data = new EppResponseDataTransferSvcsub(id);
			((EppResponseDataTransferSvcsub) data).setDateExpired(Calendar.getInstance());
		}
		else if( cmd instanceof EppCommandTransferXriAuthority )
		{
			String id = ((EppCommandTransferXriAuthority) cmd).getAuthorityId();
			data = new EppResponseDataTransferXriAuthority(id);
		}
		else if( cmd instanceof EppCommandTransferXriName )
		{
			String name = ((EppCommandTransferXriName) cmd).getIName();
			data = new EppResponseDataTransferXriName(name);
			((EppResponseDataTransferXriName) data).setDateExpired(Calendar.getInstance());
		}
		if( data != null )
		{
			data.setTransferStatus(EppResponseDataTransfer.STATUS_PENDING);
			data.setRequestClientId("CLIENT-ID-REQ");
			data.setActionClientId("CLIENT-ID-ACT");
			data.setRequestDate(Calendar.getInstance());
			data.setActionDate(Calendar.getInstance());
		}

		rsp.setResponseData(data);
	}

	/**
	 * Processes an EPP message prior a sucessful login
	 */
	private EppEntity processLogin( Node root )
	{
		// check if it is a hello

		EppHello hello = (EppHello) EppHello.fromXML(root);
		if( hello != null )
		{
			return new EppGreeting();
		}

		// check if it is login command

		EppCommand cmd = (EppCommand) EppCommand.fromXML(root);
		if( cmd == null )
		{
			EppResponse res = new EppResponse();
			res.addResult(new EppResult(EppError.CODE_UNKNOWN_COMMAND));
			this.setTransactionId(res);
			return res;
		}

		if( cmd instanceof EppCommandLogin )
		{
			EppResponse res = new EppResponse();
			res.addResult(new EppResult(EppError.CODE_NO_ERROR));
			this.setTransactionId(res, cmd);
			this.hasLogin = true;
			return res;
		}

		// it is an invalid command sequence

		EppResponse res = new EppResponse();
		res.addResult(new EppResult(EppError.CODE_COMMAND_USE_ERROR));
		this.setTransactionId(res, cmd);
		return res;
	}

	/**
	 * Processes an EPP message
	 */
	private EppEntity process( String msg )
	{
		// parse the message first

		EppParser parser = new EppParser(msg);
		if( parser.hasError() )
		{
			EppResponse rsp = new EppResponse();
			rsp.addResult(parser.getResult());
			this.setTransactionId(rsp);
			return rsp;
		}

		Node root = parser.getRootNode();
		if( root == null )
		{
			EppResponse res = new EppResponse();
			res.addResult(new EppResult(EppError.CODE_UNKNOWN_COMMAND));
			this.setTransactionId(res);
			return res;
		}

		if( this.hasLogin == false )
		{
			return this.processLogin(root);
		}

		EppCommand cmd = (EppCommand) EppCommand.fromXML(root);
		if( cmd == null )
		{
			EppHello hello = (EppHello) EppHello.fromXML(root);
			if( hello != null )
			{
				return new EppGreeting();
			}

			EppResponse rsp = new EppResponse();
			rsp.addResult(new EppResult(EppError.CODE_UNKNOWN_COMMAND));
			this.setTransactionId(rsp);
			return rsp;
		}

		if( cmd instanceof EppCommandLogin )
		{
			EppResponse rsp = new EppResponse();
			rsp.addResult(new EppResult(EppError.CODE_COMMAND_USE_ERROR));
			this.setTransactionId(rsp, cmd);
			return rsp;
		}

		if( cmd instanceof EppCommandLogout )
		{
			EppResponse rsp = new EppResponse();
			rsp.addResult(new EppResult(EppError.CODE_NO_ERROR_ENDING_SESSION));
			this.setTransactionId(rsp, cmd);
			this.hasLogin = false;
			this.quit = true;
			return rsp;
		}

		if(    (cmd instanceof EppCommandDelete)
		    || (cmd instanceof EppCommandUpdate) )
		{
			EppResponse rsp = new EppResponse();
			rsp.addResult(new EppResult(EppError.CODE_NO_ERROR));
			this.setTransactionId(rsp, cmd);
			return rsp;
		}

		if( cmd instanceof EppCommandCreate )
		{
			EppResponse rsp = new EppResponse();
			rsp.addResult(new EppResult(EppError.CODE_NO_ERROR));
			this.setTransactionId(rsp, cmd);
			setResponseDataCreate(cmd, rsp);
			return rsp;
		}

		if( cmd instanceof EppCommandCheck )
		{
			EppResponse rsp = new EppResponse();
			rsp.addResult(new EppResult(EppError.CODE_NO_ERROR));
			this.setTransactionId(rsp, cmd);
			setResponseDataCheck(cmd, rsp);
			return rsp;
		}

		if( cmd instanceof EppCommandRenew )
		{
			EppResponse rsp = new EppResponse();
			rsp.addResult(new EppResult(EppError.CODE_NO_ERROR));
			this.setTransactionId(rsp, cmd);
			setResponseDataRenew(cmd, rsp);
			return rsp;
		}

		if( cmd instanceof EppCommandTransfer )
		{
			EppResponse rsp = new EppResponse();
			rsp.addResult(new EppResult(EppError.CODE_NO_ERROR));
			this.setTransactionId(rsp, cmd);
			setResponseDataTransfer(cmd, rsp);
			return rsp;
		}

		if( cmd instanceof EppCommandInfo )
		{
			EppResponse rsp = new EppResponse();
			rsp.addResult(new EppResult(EppError.CODE_OBJECT_DOES_NOT_EXIST));
			this.setTransactionId(rsp, cmd);
			return rsp;
		}

		if( cmd instanceof EppCommandPoll )
		{
			EppResponse rsp = new EppResponse();
			rsp.addResult(new EppResult(EppError.CODE_NO_ERROR_NO_MESSAGE));
			this.setTransactionId(rsp, cmd);
			return rsp;
		}

		EppResponse rsp = new EppResponse();
		rsp.addResult(new EppResult(EppError.CODE_UNKNOWN_COMMAND));
		this.setTransactionId(rsp, cmd);
		return rsp;
	}

	public void run()
	{
		InputStream in = null;
		OutputStream out = null;
		EppGreeting greeting = new EppGreeting();

		// first try to send back a greeting object
		try
		{
			in = this.socket.getInputStream();
			out = this.socket.getOutputStream();
//			out.write(greeting.toString().getBytes());
//			out.flush();
			EppMessageUtil.putEppPayload(out, greeting.toString());
			out.flush();
		}
		catch( IOException e )
		{
			e.printStackTrace();
			return;
		}

		while( this.quit == false )
		{
			// wait for a message

			String msg = null;
			try
			{
				msg = EppMessageUtil.getEppPayload(in);
			}
			catch( Exception e )
			{
				e.printStackTrace();
				return;
			}
			if( msg == null )
			{
				System.out.println("Cannot get EPP message");
				System.out.flush();
				break;
			}
			System.out.println("EPP Message:\n" + msg);
			System.out.flush();
			EppEntity res = this.process(msg);
			if( res == null )
			{
				continue;
			}
			System.out.println("EPP Response:\n" + res);
			System.out.flush();
			try
			{
//				out.write(res.toString().getBytes());
//				out.flush();
				EppMessageUtil.putEppPayload(out, res.toString());
			}
			catch( IOException e )
			{
				e.printStackTrace();
				break;
			}
		}

		System.out.print("Closing connection...");
		System.out.flush();
		try
		{
			this.sleep(1000);
			this.socket.close();
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		System.out.println("Done");
		System.out.flush();
	}
}

/**
 * This <code>EppTestServerTcp</code> class is designed as a test EPP Server
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.4 $ $Date: 2006/09/06 09:31:56 $
 */
public class EppTestServerTcp
{
	private int     port;
	private boolean useTLS;
	private boolean clientAuth;
	private Properties tlsArgs;
	private SSLServerSocketFactory factory = null;
	private ServerSocket socket = null;

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
	 * The property for key store format, default "file"
	 * @note only "file" is supported currently.
	 */
	public static final String KS_FORMAT    = "ssl.keystore.format";
	/**
	 * The property for key store name. If the key store format is "file",
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
	 * The property for trust store format, default "file"
	 * @note only "file" is supported currently.
	 */
	public static final String TS_FORMAT    = "ssl.truststore.format";
	/**
	 * The property for trust store name. If the key store format is "file",
	 * the value of this property is the file name of the trust store file.
	 */
	public static final String TS_NAME      = "ssl.truststore.name";
	/**
	 * The property for trust store password, default null
	 */
	public static final String TS_STOREPASS = "ssl.truststore.storepass";

	public EppTestServerTcp( int port )
	{
		this.port = port;
		this.useTLS = false;
		this.clientAuth = false;
	}

	public void setTLS( boolean flag )
	{
		this.useTLS = flag;
	}

	public void init( Object object )
	{
		if( object instanceof String )
		{
			this.tlsArgs = new Properties();
			try
			{
				FileInputStream in = new FileInputStream((String) object);
				this.tlsArgs.load(in);
				in.close();
			}
			catch( Exception e )
			{
				this.tlsArgs = null;
			}
		}
		else if( object instanceof Properties )
		{
			this.tlsArgs = (Properties) object;
		}
	}

	private SSLServerSocketFactory getServerSocketFactoryTLS() throws Exception
	{
		if( this.tlsArgs == null )
		{
			return (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
		}

		SSLContext ctx          = SSLContext.getInstance("TLS");
		KeyManagerFactory   kmf = null;
		KeyStore            ks  = null;
		TrustManagerFactory tmf = null;
		KeyStore            ts  = null;
		String              s   = null;
		s = this.tlsArgs.getProperty(CLIENT_AUTH, "false");
		this.clientAuth = s.equalsIgnoreCase("true");

		String kmAlgorithm;
		String kmProvider;
		String ksType;
		String ksProvider;
		String ksName;
		String ksFormat;
		String ksStorePass;
		String ksKeyPass;

		kmAlgorithm = this.tlsArgs.getProperty(KM_ALGORITHM, "SunX509");
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

		tmAlgorithm = this.tlsArgs.getProperty(TM_ALGORITHM, "SunX509");
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
			ks.load(new FileInputStream(ksName), ksStorePass.toCharArray());
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
			ts.load(new FileInputStream(tsName), tsStorePass.toCharArray());
		}
		else
		{
			throw new KeyStoreException("Trust Store Format [" +
					tsFormat + "] not supported");
		}
		tmf.init(ts);
		ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

		return (SSLServerSocketFactory) ctx.getServerSocketFactory();
	}

	private SSLServerSocketFactory getServerSocketFactory()
	{
		try
		{
			if( this.useTLS )
			{
				return getServerSocketFactoryTLS();
			}
			else
			{
				return (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
			}
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		return null;
	}

	private void dumpSession( Socket socket )
	{

		System.out.println("Connected from: " + socket.getInetAddress()
				+ " remote port: " + socket.getPort());
		if( ! (socket instanceof SSLSocket) )
		{
			System.out.println("Session started without TLS");
			System.out.flush();
			return;
		}

		SSLSocket ssls = (SSLSocket) socket;
		SSLSession session = ssls.getSession();
		System.out.println("Session    = " + session);
		System.out.println("Session Id = " + session.getId());
		System.out.println("Peer Host  = " + session.getPeerHost());
		try
		{
			X509Certificate certs[] = session.getPeerCertificateChain();
			for( int n = 0; n < certs.length; n++ )
			{
				System.out.println("PeerCertificates[" + n + "] = " + certs[n]);
			}
		}
		catch( SSLPeerUnverifiedException e )
		{
			e.printStackTrace();
		}
		System.out.flush();
	}

	public void start()
	{
		this.factory = this.getServerSocketFactory();
		if( this.factory == null )
		{
			System.out.println("Cannot get SSL Server Socket Factory");
			System.exit(1);
		}
		try
		{
			if( useTLS )
			{
				this.socket = (SSLServerSocket) factory.createServerSocket(this.port);

				((SSLServerSocket) socket).setNeedClientAuth(this.clientAuth);
				System.out.println("Server started with" +
					(this.clientAuth ? "" : "out") +
					" client authentication");
			}
			else
			{
				this.socket = new ServerSocket(this.port);
				System.out.println("Server started without TLS");
			}
			System.out.flush();
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}

		if( socket == null )
		{
			System.out.println("Cannot get SSL Server Socket");
			System.exit(1);
		}
		while( true )
		{
			try
			{
				Socket client = socket.accept();
				this.dumpSession(client);
				EppTestServerTcpThread t = new EppTestServerTcpThread(client);
				System.out.flush();
				t.start();
				System.out.flush();
			}
			catch( Exception e )
			{
				e.printStackTrace();
			}
		}
	}

	public static final void main( String[] arg )
	{
		if( (arg.length != 1) && (arg.length != 2) )
		{
			System.out.println("Usage: java EppTestServerTcp port [ssl.property.file]");
			System.exit(0);
		}

		int port = Integer.parseInt(arg[0]);
		EppTestServerTcp server = new EppTestServerTcp(port);
		if( arg.length == 2 )
		{
			server.setTLS(true);
			server.init(arg[1]);
		}
		server.start();
	}
}
