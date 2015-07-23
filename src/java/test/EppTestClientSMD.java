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
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.security.cert.X509Certificate;

import com.neulevel.epp.core.EppAddress;
import com.neulevel.epp.core.EppAuthInfo;
import com.neulevel.epp.core.EppContact;
import com.neulevel.epp.core.EppContactData;
import com.neulevel.epp.core.EppContactType;
import com.neulevel.epp.core.EppDomain;
import com.neulevel.epp.core.EppGreeting;
import com.neulevel.epp.core.EppHost;
import com.neulevel.epp.core.EppIpAddress;
import com.neulevel.epp.core.EppObject;
import com.neulevel.epp.core.EppPeriod;
import com.neulevel.epp.core.EppSvcsub;
import com.neulevel.epp.core.EppSvcsubParam;
import com.neulevel.epp.core.command.EppCommand;
import com.neulevel.epp.core.command.EppCommandCheck;
import com.neulevel.epp.core.command.EppCommandCreate;
import com.neulevel.epp.core.command.EppCommandDelete;
import com.neulevel.epp.core.command.EppCommandInfo;
import com.neulevel.epp.core.command.EppCommandLogin;
import com.neulevel.epp.core.command.EppCommandPoll;
import com.neulevel.epp.core.command.EppCommandRenew;
import com.neulevel.epp.core.command.EppCommandRenewDomain;
import com.neulevel.epp.core.command.EppCommandTransfer;
import com.neulevel.epp.core.command.EppCommandTransferDomain;
import com.neulevel.epp.core.command.EppCommandUpdate;
import com.neulevel.epp.core.command.EppCommandUpdateContact;
import com.neulevel.epp.core.command.EppCommandUpdateDomain;
import com.neulevel.epp.core.command.EppCommandUpdateHost;
import com.neulevel.epp.core.command.EppCommandUpdateSvcsub;
import com.neulevel.epp.core.command.EppCreds;
import com.neulevel.epp.core.response.EppResponse;
import com.neulevel.epp.core.response.EppResponseDataCheck;
import com.neulevel.epp.core.response.EppResponseDataCreate;
import com.neulevel.epp.core.response.EppResponseDataCreateDomain;
import com.neulevel.epp.core.response.EppResponseDataCreateSvcsub;
import com.neulevel.epp.core.response.EppResponseDataInfo;
import com.neulevel.epp.core.response.EppResponseDataTransferContact;
import com.neulevel.epp.core.response.EppResponseDataTransferDomain;
import com.neulevel.epp.core.response.EppResponseDataTransferSvcsub;
import com.neulevel.epp.core.response.EppResult;
import com.neulevel.epp.ext.EppLaunchPhase;
import com.neulevel.epp.ext.EppSignedMarkData;
import com.neulevel.epp.ext.command.EppCommandCreateLaunchRegistration;
import com.neulevel.epp.transport.EppChannel;
import com.neulevel.epp.transport.EppSession;
import com.neulevel.epp.transport.tcp.EppChannelTcp;
import com.neulevel.epp.transport.tcp.EppSessionTcp;
import com.neulevel.epp.xri.EppXriAuthority;
import com.neulevel.epp.xri.EppXriName;
import com.neulevel.epp.xri.EppXriNumber;
import com.neulevel.epp.xri.EppXriService;
import com.neulevel.epp.xri.EppXriTrustee;
import com.neulevel.epp.xri.command.EppCommandTransferXriAuthority;
import com.neulevel.epp.xri.command.EppCommandTransferXriName;
import com.neulevel.epp.xri.command.EppCommandUpdateXriAuthority;
import com.neulevel.epp.xri.command.EppCommandUpdateXriName;
import com.neulevel.epp.xri.command.EppCommandUpdateXriNumber;
import com.neulevel.epp.xri.command.EppCommandUpdateXriService;
import com.neulevel.epp.xri.response.EppResponseDataCreateXriAuthority;
import com.neulevel.epp.xri.response.EppResponseDataCreateXriName;
import com.neulevel.epp.xri.response.EppResponseDataCreateXriNumber;
import com.neulevel.epp.xri.response.EppResponseDataCreateXriService;
import com.neulevel.epp.xri.response.EppResponseDataTransferXriAuthority;
import com.neulevel.epp.xri.response.EppResponseDataTransferXriName;

/**
 * This <code>EppTestClient</code> class is designed to perform some basic
 * EPP Client tests.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2013/06/17 17:48:37 $
 */
public class EppTestClientSMD
{
	// Static variables used thoughout the test code

	static int    counter      = 1000;
	static String checkString1 = "abc";
	static String checkString2 = "abc";
	static EppSignedMarkData smd = null;
	static EppCommandCreateLaunchRegistration  elr = null;

	// Usage

	public static void usage()
	{
		System.out.println("Usage: java EppTestClient transport host port");
		System.out.println("transport: tcp      - TCP  with    TLS");
		System.out.println("           tcp-tls  - TCP  without TLS");
		System.out.println("host:      host name of the EPP Server to be connected");
		System.out.println("port:      port to be connected on the EPP Server");
		System.exit(1);
	}

	// The main method

	public static void main( String[] arg )
	{
		if( arg.length != 3 )
		{
			usage();
		}

		EppSession session = null;

		try
		{
			if( arg[0].equals("tcp") )
			{
				session = new EppSessionTcp();
				session.init("testkeys.client.prop");
			}
			else if( arg[0].equals("tcp-tls") )
			{
				session = new EppSessionTcp(false);
			}
			else
			{
				usage();
			}
		}
		catch (Exception e) {
			System.err.println("Error initializing EppSession: " + e.getMessage());
			System.exit(1);
		}

		EppGreeting greeting = session.connect(arg[1], Integer.parseInt(arg[2]));
		if( greeting == null )
		{
			System.out.println("Cannot connect to " + arg[1] + ":"
				+ arg[2]);
			Exception e = session.getException();
			if( e != null )
			{
				e.printStackTrace();
			}
			String s = session.getMessage();
			if( s != null )
			{
				System.out.println("Message received:\n" + s);
			}
			System.exit(1);
		}
		System.out.println(greeting.toString());
		// Validate Greeting - Service date and service menu
		
		EppChannel channel = session.getChannel();
		EppCommandLogin login = new EppCommandLogin(greeting.getServiceMenu());
		login.setClientTransactionId(getClientId());
		login.setCreds(new EppCreds("AFFREGISTRAR02", "AFFREGISTRAR02PW"));
		EppResponse res = channel.start(login);
		if( res == null )
		{
			System.out.println("LOGIN error");
			System.exit(1);
		}
		if( ! res.success() )
		{
			printErrors(res.getResult());
			System.exit(1);
		}

		dumpChannel(channel);

		// Contact Management functions		

//		res = channel.send(createContact());
//		createObjectResponse(res);
//		res = channel.send(checkContact());
//		checkObjectResponse(res);
//		res = channel.send(infoContact());
//		infoContactResponse(res);
//		res = channel.send(deleteContact());
//		deleteObjectResponse(res);
//		res = channel.send(transferQueryContact());
//		transferContactResponse(res);
//		res = channel.send(transferContact());
//		transferContactResponse(res);
//		res = channel.send(updateContact());
//		updateObjectResponse(res);
		// res = channel.send(renewContact());
		// createObjectResponse(res);

		// Domain Management functions

		res = channel.send(createDomain());
		createDomainResponse(res);
//		res = channel.send(checkDomain());
//		checkObjectResponse(res);
//		res = channel.send(infoDomain());
//		infoDomainResponse(res);
//		res = channel.send(deleteDomain());
//		deleteObjectResponse(res);
//		res = channel.send(transferQueryDomain());
//		transferDomainResponse(res);
//		res = channel.send(transferDomain());
//		transferDomainResponse(res);
//		res = channel.send(updateDomain());
//		updateObjectResponse(res);
//		res = channel.send(renewDomain());
//		createDomainResponse(res);
//
//		// Host Management functions
//
//		res = channel.send(createHost());
//		createObjectResponse(res);
//		res = channel.send(checkHost());
//		checkObjectResponse(res);
//		res = channel.send(infoHost());
//		infoHostResponse(res);
//		res = channel.send(deleteHost());
//		deleteObjectResponse(res);
//		// res = channel.send(transferQueryHost());
//		// transferHostResponse(res);
//		// res = channel.send(transferHost());
//		// transferHostResponse(res);
//		res = channel.send(updateHost());
//		updateObjectResponse(res);
//		// res = channel.send(renewHost());
//		// createObjectResponse(res);
//
//		// Svcsub Management functions
//
//		res = channel.send(createSvcsub());
//		createSvcsubResponse(res);
//		res = channel.send(checkSvcsub());
//		checkObjectResponse(res);
//		res = channel.send(infoSvcsub());
//		infoSvcsubResponse(res);
//		res = channel.send(deleteSvcsub());
//		deleteObjectResponse(res);
//		res = channel.send(transferQuerySvcsub());
//		transferSvcsubResponse(res);
//		res = channel.send(transferSvcsub());
//		transferSvcsubResponse(res);
//		res = channel.send(updateSvcsub());
//		updateObjectResponse(res);
//		res = channel.send(renewSvcsub());
//		createSvcsubResponse(res);
//
//		// XRI Authority functions
//		res = channel.send(createXriAuthority());
//		createXriAuthorityResponse(res);
//		res = channel.send(checkXriAuthority());
//		checkObjectResponse(res);
//		res = channel.send(infoXriAuthority());
//		infoXriAuthorityResponse(res);
//		res = channel.send(transferQueryXriAuthority());
//		transferXriAuthorityResponse(res);
//		res = channel.send(transferXriAuthority());
//		transferXriAuthorityResponse(res);
//		res = channel.send(updateXriAuthority());
//		updateObjectResponse(res);
//		res = channel.send(deleteXriAuthority());
//		deleteObjectResponse(res);
//
//		// XRI IName functions
//		res = channel.send(createXriName());
//		createXriNameResponse(res);
//		res = channel.send(checkXriName());
//		checkObjectResponse(res);
//		res = channel.send(infoXriName());
//		infoXriNameResponse(res);
//		res = channel.send(transferQueryXriName());
//		transferXriNameResponse(res);
//		res = channel.send(transferXriName());
//		transferXriNameResponse(res);
//		res = channel.send(updateXriName());
//		updateObjectResponse(res);
//		res = channel.send(deleteXriName());
//		deleteObjectResponse(res);
//
//		// XRI INumber functions
//		res = channel.send(createXriNumber());
//		createXriNumberResponse(res);
//		res = channel.send(checkXriNumber());
//		checkObjectResponse(res);
//		res = channel.send(infoXriNumber());
//		infoXriNumberResponse(res);
//		res = channel.send(updateXriNumber());
//		updateObjectResponse(res);
//		res = channel.send(deleteXriNumber());
//		deleteObjectResponse(res);
//
//		// XRI IService functions
//		res = channel.send(createXriService());
//		createXriServiceResponse(res);
//		res = channel.send(checkXriService());
//		checkObjectResponse(res);
//		res = channel.send(infoXriService());
//		infoXriServiceResponse(res);
//		res = channel.send(updateXriService());
//		updateObjectResponse(res);
//		res = channel.send(deleteXriService());
//		deleteObjectResponse(res);
//
//		// Poll function
//
//		res = channel.send(pollMessage());
//		System.out.println(res);
	 
		// End of the test

		channel.terminate();
		session.close();

		System.exit(0);
	}

	// Check Functions for Contact, Domain, Host and Svcsub

	public static EppCommandCheck checkContact()
	{
		EppCommandCheck cmd = EppCommand.check(EppObject.CONTACT, getClientId());
		checkString1 = "ROID-1";
		checkString2 = "ROID-2";
		cmd.add(checkString1);
		cmd.add(checkString2);
		System.out.println(cmd);
		return cmd;
	}
	public static EppCommandCheck checkDomain()
	{
		EppCommandCheck cmd = EppCommand.check(EppObject.DOMAIN,getClientId());
		checkString1="example1.biz";
		checkString2="example2.biz";
		cmd.add(checkString1);
		cmd.add(checkString2);
		System.out.println(cmd);
		return cmd;
	}
	public static EppCommandCheck checkHost()
	{
		EppCommandCheck cmd = EppCommand.check(EppObject.HOST,getClientId());
		checkString1="ns1.example1.biz";
		checkString2="ns2.example1.biz";
		cmd.add(checkString1);
		cmd.add(checkString2);
		System.out.println(cmd);
		return cmd;
	}
	public static EppCommandCheck checkSvcsub()
	{
		EppCommandCheck cmd = EppCommand.check(EppObject.SVCSUB,getClientId());
		checkString1="BIZLOCK-001";
		checkString2="BIZLOCK-002";
		cmd.add(checkString1);
		cmd.add(checkString2);
		System.out.println(cmd);
		return cmd;
	}
	public static EppCommandCheck checkXriAuthority()
	{
		EppCommandCheck cmd = EppCommand.check(EppObject.XRI_AUTHORITY, getClientId());
		checkString1="FOO-BAR-ID";
		checkString2="=!(!!1002!2000.2)";
		cmd.add(checkString1);
		cmd.add(checkString2);
		System.out.println(cmd);
		return cmd;
	}

	public static EppCommandCheck checkXriName()
	{
		EppCommandCheck cmd = EppCommand.check(EppObject.XRI_INAME, getClientId());
		checkString1="@foo";
		checkString2="=bar";
		cmd.add(checkString1);
		cmd.add(checkString2);
		System.out.println(cmd);
		return cmd;
	}

	public static EppCommandCheck checkXriNumber()
	{
		EppCommandCheck cmd = EppCommand.check(EppObject.XRI_INUMBER, getClientId());
		checkString1="!!1234!5678";
		checkString2="!!1111!2222";
		cmd.add(checkString1);
		cmd.add(checkString2);
		System.out.println(cmd);
		return cmd;
	}

	public static EppCommandCheck checkXriService()
	{
		EppCommandCheck cmd = EppCommand.check(EppObject.XRI_ISERVICE, getClientId());
		checkString1="+service1";
		checkString2="+service2";
		cmd.add(checkString1);
		cmd.add(checkString2);
		System.out.println(cmd);
		return cmd;
	}

	// Info Functions for Contact, Domain, Host and Svcsub

	public static EppCommandInfo infoContact()
	{
		EppCommandInfo cmd = EppCommand.info(EppObject.CONTACT, "ROID-1", getClientId());
		System.out.println(cmd);
		return cmd;
	}
	public static EppCommandInfo infoDomain()
	{
		EppCommandInfo cmd = EppCommand.info(EppObject.DOMAIN, "example1.biz", getClientId());
		System.out.println(cmd);
		return cmd;
	}
	public static EppCommandInfo infoHost()
	{
		EppCommandInfo cmd = EppCommand.info(EppObject.HOST, "ns1.example1.biz", getClientId());
		System.out.println(cmd);
		return cmd;
	}
	public static EppCommandInfo infoSvcsub()
	{
		EppCommandInfo cmd = EppCommand.info(EppObject.SVCSUB, "BIZLOCK-001", getClientId());
		System.out.println(cmd);
		return cmd;
	}
	public static EppCommandInfo infoXriAuthority()
	{
		EppCommandInfo cmd = EppCommand.info(EppObject.XRI_AUTHORITY, "=!(!!1002!2000.2)", getClientId());
		System.out.println(cmd);
		return cmd;
	}
	public static EppCommandInfo infoXriName()
	{
		EppCommandInfo cmd = EppCommand.info(EppObject.XRI_INAME, "@foo", getClientId());
		System.out.println(cmd);
		return cmd;
	}

	public static EppCommandInfo infoXriNumber()
	{
		EppCommandInfo cmd = EppCommand.info(EppObject.XRI_INUMBER, "!!1234!5678", getClientId());
		System.out.println(cmd);
		return cmd;
	}

	public static EppCommandInfo infoXriService()
	{
		EppCommandInfo cmd = EppCommand.info(EppObject.XRI_ISERVICE, "+service1", getClientId());
		System.out.println(cmd);
		return cmd;
	}


	// Delete Functions for Contact, Domain, Host and Svcsub

	public static EppCommandDelete deleteContact()
	{
		EppCommandDelete cmd = EppCommand.delete(EppObject.CONTACT, "ROID-1", getClientId());
		System.out.println(cmd);
		return cmd;
	}
	public static EppCommandDelete deleteDomain()
	{
		EppCommandDelete cmd = EppCommand.delete(EppObject.DOMAIN, "example1.biz", getClientId());
		System.out.println(cmd);
		return cmd;
	}
	public static EppCommandDelete deleteHost()
	{
		EppCommandDelete cmd = EppCommand.delete(EppObject.HOST, "ns1.example1.biz", getClientId());
		System.out.println(cmd);
		return cmd;
	}
	public static EppCommandDelete deleteSvcsub()
	{
		EppCommandDelete cmd = EppCommand.delete(EppObject.SVCSUB, "BIZLOCK-001", getClientId());
		System.out.println(cmd);
		return cmd;
	}
	public static EppCommandDelete deleteXriAuthority()
	{
		EppCommandDelete cmd = EppCommand.delete(EppObject.XRI_AUTHORITY, "=!(!!1002!2000.2)", getClientId());
		System.out.println(cmd);
		return cmd;
	}
	public static EppCommandDelete deleteXriName()
	{
		EppCommandDelete cmd = EppCommand.delete(EppObject.XRI_INAME, "@foo", getClientId());
		System.out.println(cmd);
		return cmd;
	}
	public static EppCommandDelete deleteXriNumber()
	{
		EppCommandDelete cmd = EppCommand.delete(EppObject.XRI_INUMBER, "!!1234!5678", getClientId());
		System.out.println(cmd);
		return cmd;
	}
	public static EppCommandDelete deleteXriService()
	{
		EppCommandDelete cmd = EppCommand.delete(EppObject.XRI_ISERVICE, "+service1", getClientId());
		System.out.println(cmd);
		return cmd;
	}

	// Transfer Query Functions for Contact, Domain, Host and Svcsub

	public static EppCommandTransfer transferQueryContact()
	{
		EppCommandTransfer cmd = EppCommand.transfer(EppObject.CONTACT, "ROID-1", getClientId());
		EppAuthInfo obj_authinfo = new EppAuthInfo(EppAuthInfo.TYPE_PW, "2fooBar"); 
		cmd.setAuthInfo(obj_authinfo);
		cmd.setOperation(EppCommandTransfer.OPTYPE_QUERY);
		System.out.println(cmd);
		return cmd;
	}
	public static EppCommandTransfer transferQueryDomain()
	{
		EppCommandTransferDomain cmd = (EppCommandTransferDomain) EppCommand.transfer(EppObject.DOMAIN, "example1.biz", getClientId());
		EppAuthInfo obj_authinfo = new EppAuthInfo(EppAuthInfo.TYPE_PW, "2fooBar"); 
		cmd.setAuthInfo(obj_authinfo);
		cmd.setOperation(EppCommandTransfer.OPTYPE_QUERY);
		cmd.setPeriod(new EppPeriod(1, EppPeriod.UNIT_YEAR));
		System.out.println(cmd);
		return cmd;
	}
	public static EppCommandTransfer transferQueryHost()
	{
		EppCommandTransfer cmd = EppCommand.transfer(EppObject.HOST, "ns1.example1.biz", getClientId());
		EppAuthInfo obj_authinfo = new EppAuthInfo(EppAuthInfo.TYPE_PW, "2fooBar"); 
		cmd.setAuthInfo(obj_authinfo);
		cmd.setOperation(EppCommandTransfer.OPTYPE_QUERY);
		System.out.println(cmd);
		return cmd;
	}
	public static EppCommandTransfer transferQuerySvcsub()
	{
		EppCommandTransfer cmd = EppCommand.transfer(EppObject.SVCSUB, "BIZLOCK-001", getClientId());
		EppAuthInfo obj_authinfo = new EppAuthInfo(EppAuthInfo.TYPE_PW, "2fooBar"); 
		cmd.setAuthInfo(obj_authinfo);
		cmd.setOperation(EppCommandTransfer.OPTYPE_QUERY);
		System.out.println(cmd);
		return cmd;
	}
	public static EppCommandTransfer transferQueryXriAuthority()
	{
		EppCommandTransferXriAuthority cmd = (EppCommandTransferXriAuthority) EppCommand.transfer(EppObject.XRI_AUTHORITY, "=!(!!1002!2000.2)", getClientId());
		EppAuthInfo obj_authinfo = new EppAuthInfo(EppAuthInfo.TYPE_PW, "2fooBar"); 
		cmd.setAuthInfo(obj_authinfo);
		cmd.setOperation(EppCommandTransfer.OPTYPE_QUERY);
		System.out.println(cmd);
		return cmd;
	}

	public static EppCommandTransfer transferQueryXriName()
	{
		EppCommandTransferXriName cmd = (EppCommandTransferXriName) EppCommand.transfer(EppObject.XRI_INAME, "@foo", getClientId());
		EppAuthInfo obj_authinfo = new EppAuthInfo(EppAuthInfo.TYPE_PW, "2fooBar");
		cmd.setAuthInfo(obj_authinfo);
		cmd.setOperation(EppCommandTransfer.OPTYPE_QUERY);
		System.out.println(cmd);
		return cmd;
	}

	// Transfer Request Functions for Contact, Domain, Host and Svcsub

	public static EppCommandTransfer transferContact()
	{
		EppCommandTransfer cmd = EppCommand.transfer(EppObject.CONTACT, "ROID-1", getClientId());
		EppAuthInfo obj_authinfo = new EppAuthInfo(EppAuthInfo.TYPE_PW, "2fooBar"); 
		cmd.setAuthInfo(obj_authinfo);
		cmd.setOperation(EppCommandTransfer.OPTYPE_REQUEST);
		System.out.println(cmd);
		return cmd;
	}
	public static EppCommandTransfer transferDomain()
	{
		EppCommandTransferDomain cmd = (EppCommandTransferDomain) EppCommand.transfer(EppObject.DOMAIN, "example1.biz", getClientId());
		EppAuthInfo obj_authinfo = new EppAuthInfo(EppAuthInfo.TYPE_PW, "2fooBar"); 
		cmd.setAuthInfo(obj_authinfo);
		cmd.setOperation(EppCommandTransfer.OPTYPE_REQUEST);
		cmd.setPeriod(new EppPeriod(1, EppPeriod.UNIT_YEAR));
		System.out.println(cmd);
		return cmd;
	}
	public static EppCommandTransfer transferHost()
	{
		EppCommandTransfer cmd = EppCommand.transfer(EppObject.HOST, "ns1.example1.biz", getClientId());
		EppAuthInfo obj_authinfo = new EppAuthInfo(EppAuthInfo.TYPE_PW, "2fooBar"); 
		cmd.setAuthInfo(obj_authinfo);
		cmd.setOperation(EppCommandTransfer.OPTYPE_REQUEST);
		System.out.println(cmd);
		return cmd;
	}
	public static EppCommandTransfer transferSvcsub()
	{
		EppCommandTransfer cmd = EppCommand.transfer(EppObject.SVCSUB, "BIZLOCK-001", getClientId());
		EppAuthInfo obj_authinfo = new EppAuthInfo(EppAuthInfo.TYPE_PW, "2fooBar"); 
		cmd.setAuthInfo(obj_authinfo);
		cmd.setOperation(EppCommandTransfer.OPTYPE_REQUEST);
		System.out.println(cmd);
		return cmd;
	}
	public static EppCommandTransfer transferXriAuthority()
	{
		EppCommandTransferXriAuthority cmd = (EppCommandTransferXriAuthority) EppCommand.transfer(EppObject.XRI_AUTHORITY, "=!(!!1002!2000.2)", getClientId());
		EppAuthInfo obj_authinfo = new EppAuthInfo(EppAuthInfo.TYPE_PW, "2fooBar"); 
		cmd.setAuthInfo(obj_authinfo);
		cmd.setOperation(EppCommandTransfer.OPTYPE_REQUEST);
		System.out.println(cmd);
		return cmd;
	}
	public static EppCommandTransfer transferXriName()
	{
		EppCommandTransferXriName cmd = (EppCommandTransferXriName) EppCommand.transfer(EppObject.XRI_INAME, "@foo", getClientId());
		EppAuthInfo obj_authinfo = new EppAuthInfo(EppAuthInfo.TYPE_PW, "2fooBar"); 
		cmd.setAuthInfo(obj_authinfo);
		cmd.setOperation(EppCommandTransfer.OPTYPE_REQUEST);
		System.out.println(cmd);
		return cmd;
	}

	// Create Functions for Contact, Domain, Host and Svcsub

	public static EppCommandCreate createContact()
	{
		EppCommandCreate cmd = EppCommand.create(buildContact(), getClientId());
		System.out.println(cmd);
		return cmd;
	}
	public static EppCommandCreate createDomain()
	{
		EppCommandCreate cmd = EppCommand.create(buildDomain(), getClientId());
		EppCommandCreateLaunchRegistration elr = readSMD("SMDFile");
		cmd.addExtension(elr);
		System.out.println(cmd.toString());
		return cmd;
	}
	public static EppCommandCreate createHost()
	{
		EppCommandCreate cmd = EppCommand.create(buildHost(), getClientId());
		System.out.println(cmd);
		return cmd;
	}
	public static EppCommandCreate createSvcsub()
	{
		EppCommandCreate cmd = EppCommand.create(buildSvcsub(), getClientId());
		System.out.println(cmd);
		return cmd;
	}
	public static EppCommandCreate createXriAuthority()
	{
		EppCommandCreate cmd = EppCommand.create(buildXriAuthority(), getClientId());
		System.out.println(cmd);
		return cmd;
	}
	public static EppCommandCreate createXriName()
	{
		EppCommandCreate cmd = EppCommand.create(buildXriName(), getClientId());
		System.out.println(cmd);
		return cmd;
	}
	public static EppCommandCreate createXriNumber()
	{
		EppCommandCreate cmd = EppCommand.create(buildXriNumber(), getClientId());
		System.out.println(cmd);
		return cmd;
	}
	public static EppCommandCreate createXriService()
	{
		EppCommandCreate cmd = EppCommand.create(buildXriService(), getClientId());
		System.out.println(cmd);
		return cmd;
	}
		
	// Update Functions for Contact, Domain, Host and Svcsub

	public static EppCommandUpdate updateContact()
	{
		EppCommandUpdateContact cmd = (EppCommandUpdateContact)EppCommand.update(EppObject.CONTACT, "ROID-1", getClientId());
		EppAuthInfo obj_authinfo = new EppAuthInfo(EppAuthInfo.TYPE_PW, "3fooBar"); 
		cmd.setNewAuthInfo(obj_authinfo);
		System.out.println(cmd);
		return cmd;
	}
	public static EppCommandUpdate updateDomain()
	{
		EppCommandUpdateDomain cmd = (EppCommandUpdateDomain)EppCommand.update(EppObject.DOMAIN, "example1.biz", getClientId());
		EppAuthInfo obj_authinfo = new EppAuthInfo(EppAuthInfo.TYPE_PW, "3fooBar"); 
		cmd.setNewAuthInfo(obj_authinfo);
		cmd.removeNameServer("ns1.example1.biz");
		cmd.removeContact(new EppContactType("ROID-1", "admin"));
		cmd.addNameServer("ns2.example1.biz");
		System.out.println(cmd);
		return cmd;
	}
	public static EppCommandUpdate updateHost()
	{
		EppCommandUpdateHost cmd = (EppCommandUpdateHost)EppCommand.update(EppObject.HOST, "ns1.example1.biz", getClientId());
		cmd.addAddress(new EppIpAddress("198.24.34.56"));
		cmd.addAddress(new EppIpAddress("::FFFF:204.34.56.98", EppIpAddress.TYPE_V6));
		cmd.setNewName("ns2.example1.biz");
		System.out.println(cmd);
		return cmd;
	}
	public static EppCommandUpdate updateSvcsub()
	{
		EppCommandUpdateSvcsub cmd = (EppCommandUpdateSvcsub)EppCommand.update(EppObject.SVCSUB, "BIZLOCK-001", getClientId());
		EppAuthInfo obj_authinfo = new EppAuthInfo(EppAuthInfo.TYPE_PW, "3fooBar"); 
		cmd.setNewAuthInfo(obj_authinfo);
		EppAuthInfo obj_authinfo1 = new EppAuthInfo(EppAuthInfo.TYPE_PW, "3fooBar"); 
		cmd.setAuthInfo(obj_authinfo1);
		cmd.removeParam(new EppSvcsubParam("xxxx", "yyyy"));
		cmd.removeContact(new EppContactType("ROID-1", "admin"));
		cmd.addParam(new EppSvcsubParam("yyyy", "zzzz"));
		System.out.println(cmd);
		return cmd;
	}
	public static EppCommandUpdate updateXriAuthority()
	{
		EppCommandUpdateXriAuthority cmd = (EppCommandUpdateXriAuthority)EppCommand.update(EppObject.XRI_AUTHORITY, "=!(!!1002!2000.2)", getClientId());
		EppAuthInfo obj_authinfo = new EppAuthInfo(EppAuthInfo.TYPE_PW, "3fooBar"); 
		cmd.setNewAuthInfo(obj_authinfo);

		EppXriTrustee t = new EppXriTrustee();
		t.setAuthorityId("@!(!!0001)");
		cmd.setNewEscrowAgent(t);
		cmd.setNewContactAgent(t);

		System.out.println(cmd);
		return cmd;
	}

	public static EppCommandUpdate updateXriName()
	{
		EppCommandUpdateXriName cmd = (EppCommandUpdateXriName)EppCommand.update(EppObject.XRI_INAME, "@foo", getClientId());
		cmd.addStatus("clientHold");

		System.out.println(cmd);
		return cmd;
	}

	public static EppCommandUpdate updateXriNumber()
	{
		EppCommandUpdateXriNumber cmd = (EppCommandUpdateXriNumber)EppCommand.update(EppObject.XRI_INUMBER, "!!1234!5678", getClientId());
		cmd.addStatus("clientHold");

		System.out.println(cmd);
		return cmd;
	}

	public static EppCommandUpdate updateXriService()
	{
		EppCommandUpdateXriService cmd = (EppCommandUpdateXriService)EppCommand.update(EppObject.XRI_ISERVICE, "+service1", getClientId());
		cmd.addStatus("clientHold");

		System.out.println(cmd);
		return cmd;
	}

	// Renew Functions for Contact, Domain, Host and Svcsub

	public static EppCommand renewContact()
	{
		EppCommandRenew cmd = EppCommand.renew(EppObject.CONTACT, "ROID-1", getClientId());
		if (cmd == null)
		{
			System.out.println("Not a valid command");
		}
		return cmd;
	}
	public static EppCommandRenewDomain renewDomain()
	{
		EppCommandRenewDomain cmd = (EppCommandRenewDomain)EppCommand.renew(EppObject.DOMAIN, "example1.biz", getClientId());
		cmd.setPeriod(new EppPeriod(4, EppPeriod.UNIT_YEAR));
		cmd.setCurrentExpireDate(new GregorianCalendar(2001,10,1));
		return cmd;
	}
	public static EppCommand renewHost()
	{
		EppCommandRenew cmd = EppCommand.renew(EppObject.HOST, "ns2.example1.biz", getClientId());
		if (cmd == null)
		{
			System.out.println("Not a valid command");
		}
		return cmd;
	}

	public static EppCommand renewSvcsub()
	{
		EppCommandRenew cmd = EppCommand.renew(EppObject.SVCSUB, "BIZLOCK-001", getClientId());
		cmd.setPeriod(new EppPeriod(4, EppPeriod.UNIT_YEAR));
		cmd.setCurrentExpireDate(new GregorianCalendar(2001,10,1));
		return cmd;
	}

	// Fetch Create Response for Contact, Domain, Host and Svcsub

	public static void createObjectResponse( EppResponse res )
	{
		if( res != null )
		{
			System.out.println(res);
			if( res.success() )
			{
				EppResponseDataCreate res_data = (EppResponseDataCreate) res.getResponseData();
				if( res_data != null )
				{
//					System.out.println("ROID is: " +  res_data.getRoid());
				}
			}
			else
			{
				printErrors(res.getResult());
			}
		}
		else
		{
			System.out.println("NULL Response");
		}
	}
	public static void createDomainResponse( EppResponse res )
	{
		if( res != null )
		{
			System.out.println(res);
			if( res.success() )
			{
				EppResponseDataCreateDomain res_data = (EppResponseDataCreateDomain) res.getResponseData();
				if( res_data != null )
				{
//					System.out.println("ROID is: " +  res_data.getRoid());
					System.out.println("Name is: " +  res_data.getName());
					System.out.println("exDate is: " +  res_data.getDateExpired().getTime());
				}
			}
			else
			{
				printErrors(res.getResult());
			}
		}
		else
		{
			System.out.println("NULL Response");
		}
	}
	public static void createSvcsubResponse( EppResponse res )
	{
		if( res != null )
		{
			System.out.println(res);
			if( res.success() )
			{
				EppResponseDataCreateSvcsub res_data = (EppResponseDataCreateSvcsub) res.getResponseData();
				if( res_data != null )
				{
//					System.out.println("ROID is: " +  res_data.getRoid());
					System.out.println("ID is: " +  res_data.getId());
					System.out.println("exDate is: " +  res_data.getDateExpired().getTime());
				}
			}
			else
			{
				printErrors(res.getResult());
			}
		}
		else
		{
			System.out.println("NULL Response");
		}
	}

	public static void createXriAuthorityResponse( EppResponse res )
	{
		if (res == null) {
			System.out.println("NULL Response");
			System.exit(1);
		}

		System.out.println(res);
		if (!res.success()) {
			printErrors(res.getResult());
			return;
		}

		EppResponseDataCreateXriAuthority res_data = (EppResponseDataCreateXriAuthority) res.getResponseData();
		if (res_data == null) {
			System.out.println("NULL Response Data");
			System.exit(1);
		}

		System.out.println("Authority ID is: " +  res_data.getAuthorityId());
	}

	public static void createXriNameResponse( EppResponse res )
	{
		if (res == null) {
			System.out.println("NULL Response");
			System.exit(1);
		}

		System.out.println(res);
		if (!res.success()) {
			printErrors(res.getResult());
			return;
		}

		EppResponseDataCreateXriName res_data = (EppResponseDataCreateXriName) res.getResponseData();
		if (res_data == null) {
			System.out.println("NULL Response Data");
			System.exit(1);
		}

		System.out.println("IName is: " +  res_data.getIName());
	}

	public static void createXriNumberResponse( EppResponse res )
	{
		if (res == null) {
			System.out.println("NULL Response");
			System.exit(1);
		}

		System.out.println(res);
		if (!res.success()) {
			printErrors(res.getResult());
			return;
		}

		EppResponseDataCreateXriNumber res_data = (EppResponseDataCreateXriNumber) res.getResponseData();
		if (res_data == null) {
			System.out.println("NULL Response Data");
			System.exit(1);
		}

		System.out.println("INumber is: " +  res_data.getINumber());
	}

	public static void createXriServiceResponse( EppResponse res )
	{
		if (res == null) {
			System.out.println("NULL Response");
			System.exit(1);
		}

		System.out.println(res);
		if (!res.success()) {
			printErrors(res.getResult());
			return;
		}

		EppResponseDataCreateXriService res_data = (EppResponseDataCreateXriService) res.getResponseData();
		if (res_data == null) {
			System.out.println("NULL Response Data");
			System.exit(1);
		}

		System.out.println("IService ID is: " +  res_data.getId());
	}

	// Fetch Delete Response for Contact, Domain, Host and Svcsub

	public static void deleteObjectResponse( EppResponse res )
	{
		if( res != null )
		{
			System.out.println(res);
			if( res.success() )
			{
				System.out.println("Delete Success");
			}
			else
			{
				printErrors(res.getResult());
			}
		}
		else
		{
			System.out.println("NULL Response");
		}
	}

	// Fetch Check Response for Contact, Domain, Host and Svcsub

	public static void checkObjectResponse( EppResponse res )
	{
		if( res != null )
		{
			System.out.println(res);
			if( res.success() )
			{
				EppResponseDataCheck res_data = (EppResponseDataCheck) res.getResponseData();
				if( res_data != null )
				{
					System.out.println("Value for " + checkString1 + " is ::" + res_data.check(checkString1));
					System.out.println("Value for " + checkString2 + " is ::" + res_data.check(checkString2));
				}
			}
			else
			{
				printErrors(res.getResult());
			}
		}
		else
		{
			System.out.println("NULL Response");
		}
	}

	// Fetch Info Response for Contact, Domain, Host and Svcsub

	public static void infoContactResponse( EppResponse res )
	{
		if( res != null )
		{
			System.out.println(res);
			if( res.success() )
			{
				EppResponseDataInfo res_data = (EppResponseDataInfo) res.getResponseData();
				if( res_data != null )
				{
					EppContact  obj = (EppContact) res_data.getObject();
					printContact(obj);

				}
			}
			else
			{
				printErrors(res.getResult());
			}
		}
		else
		{
			System.out.println("NULL Response");
		}
	}
	public static void infoDomainResponse( EppResponse res )
	{
		if( res != null )
		{
			System.out.println(res);
			if( res.success() )
			{
				EppResponseDataInfo res_data = (EppResponseDataInfo) res.getResponseData();
				if( res_data != null )
				{
					EppDomain  obj = (EppDomain) res_data.getObject();
					printDomain(obj);

				}
			}
			else
			{
				printErrors(res.getResult());
			}
		}
		else
		{
			System.out.println("NULL Response");
		}
	}
	public static void infoHostResponse( EppResponse res )
	{
		if( res != null )
		{
			System.out.println(res);
			if( res.success() )
			{
				EppResponseDataInfo res_data = (EppResponseDataInfo) res.getResponseData();
				if( res_data != null )
				{
					EppHost  obj = (EppHost) res_data.getObject();
					printHost(obj);

				}
			}
			else
			{
				printErrors(res.getResult());
			}
		}
		else
		{
			System.out.println("NULL Response");
		}
	}
	public static void infoSvcsubResponse( EppResponse res )
	{
		if( res != null )
		{
			System.out.println(res);
			if( res.success() )
			{
				EppResponseDataInfo res_data = (EppResponseDataInfo) res.getResponseData();
				if( res_data != null )
				{
					EppSvcsub  obj = (EppSvcsub) res_data.getObject();
					printSvcsub(obj);

				}
			}
			else
			{
				printErrors(res.getResult());
			}
		}
		else
		{
			System.out.println("NULL Response");
		}
	}

	public static void infoXriAuthorityResponse( EppResponse res )
	{
		if (res == null) {
			System.out.println("NULL Response");
			System.exit(1);
		}

		System.out.println(res);
		if (!res.success()) {
			printErrors(res.getResult());
			return;
		}

		EppResponseDataInfo res_data = (EppResponseDataInfo) res.getResponseData();
		if (res_data == null) {
			System.out.println("NULL Response Data");
			System.exit(1);
		}

		EppXriAuthority obj = (EppXriAuthority) res_data.getObject();
		printXriAuthority(obj);
	}

	public static void infoXriNameResponse( EppResponse res )
	{
		if (res == null) {
			System.out.println("NULL Response");
			System.exit(1);
		}

		System.out.println(res);
		if (!res.success()) {
			printErrors(res.getResult());
			return;
		}

		EppResponseDataInfo res_data = (EppResponseDataInfo) res.getResponseData();
		if (res_data == null) {
			System.out.println("NULL Response Data");
			System.exit(1);
		}

		EppXriName obj = (EppXriName) res_data.getObject();
		printXriName(obj);
	}

	public static void infoXriNumberResponse( EppResponse res )
	{
		if (res == null) {
			System.out.println("NULL Response");
			System.exit(1);
		}

		System.out.println(res);
		if (!res.success()) {
			printErrors(res.getResult());
			return;
		}

		EppResponseDataInfo res_data = (EppResponseDataInfo) res.getResponseData();
		if (res_data == null) {
			System.out.println("NULL Response Data");
			System.exit(1);
		}

		EppXriNumber obj = (EppXriNumber) res_data.getObject();
		printXriNumber(obj);
	}

	public static void infoXriServiceResponse( EppResponse res )
	{
		if (res == null) {
			System.out.println("NULL Response");
			System.exit(1);
		}

		System.out.println(res);
		if (!res.success()) {
			printErrors(res.getResult());
			return;
		}

		EppResponseDataInfo res_data = (EppResponseDataInfo) res.getResponseData();
		if (res_data == null) {
			System.out.println("NULL Response Data");
			System.exit(1);
		}

		EppXriService obj = (EppXriService) res_data.getObject();
		printXriService(obj);
	}

	// Fetch Transfer Response for Contact, Domain, Host and Svcsub

	public static void transferContactResponse( EppResponse res )
	{
		if( res != null )
		{
			System.out.println(res);
			if( res.success() )
			{
				EppResponseDataTransferContact res_data = (EppResponseDataTransferContact) res.getResponseData();
				if( res_data != null )
				{

					System.out.println("Transfer Response is: ");
					System.out.println("ID is: " +  res_data.getId());
					System.out.println("trStatus is: " +  res_data.getTransferStatus());
					System.out.println("reID is: " +  res_data.getRequestClientId());
					System.out.println("reDate is: " +  res_data.getRequestDate().getTime());
					System.out.println("acID is: " +  res_data.getActionClientId());
					System.out.println("acDate is: " +  res_data.getActionDate().getTime());
				}
			}
			else
			{
				printErrors(res.getResult());
			}
		}
		else
		{
			System.out.println("NULL Response");
		}
	}
	public static void transferDomainResponse( EppResponse res )
	{
		if( res != null )
		{
			System.out.println(res);
			if( res.success() )
			{
				EppResponseDataTransferDomain res_data = (EppResponseDataTransferDomain) res.getResponseData();
				if( res_data != null )
				{

					System.out.println("Transfer Response is: ");
					System.out.println("Name is: " +  res_data.getName());
					System.out.println("trStatus is: " +  res_data.getTransferStatus());
					System.out.println("ex-date : " +  res_data.getDateExpired().getTime());
					System.out.println("reID is: " +  res_data.getRequestClientId());
					System.out.println("reDate is: " +  res_data.getRequestDate().getTime());
					System.out.println("acID is: " +  res_data.getActionClientId());
					System.out.println("acDate is: " +  res_data.getActionDate().getTime());
				}
			}
			else
			{
				printErrors(res.getResult());
			}
		}
		else
		{
			System.out.println("NULL Response");
		}
	}
	public static void transferHostResponse( EppResponse res )
	{
		if( res != null )
		{
			System.out.println(res);
			System.out.println("ERROR - It should return null");
		}
		else
		{
			System.out.println("NULL Response");
		}
	}
	public static void transferSvcsubResponse( EppResponse res )
	{
		if( res != null )
		{
			System.out.println(res);
			if( res.success() )
			{
				EppResponseDataTransferSvcsub res_data = (EppResponseDataTransferSvcsub) res.getResponseData();
				if( res_data != null )
				{

					System.out.println("Transfer Response is: ");
					System.out.println("ID is: " +  res_data.getId());
					System.out.println("trStatus is: " +  res_data.getTransferStatus());
					System.out.println("ex-date : " +  res_data.getDateExpired().getTime());
					System.out.println("reID is: " +  res_data.getRequestClientId());
					System.out.println("reDate is: " +  res_data.getRequestDate().getTime());
					System.out.println("acID is: " +  res_data.getActionClientId());
					System.out.println("acDate is: " +  res_data.getActionDate().getTime());
				}
			}
			else
			{
				printErrors(res.getResult());
			}
		}
		else
		{
			System.out.println("NULL Response");
		}
	}

	public static void transferXriAuthorityResponse( EppResponse res )
	{
		if (res == null) {
			System.out.println("NULL Response");
			System.exit(1);
		}

		System.out.println(res);
		if (!res.success()) {
			printErrors(res.getResult());
			return;
		}

		EppResponseDataTransferXriAuthority res_data = (EppResponseDataTransferXriAuthority) res.getResponseData();
		if (res_data == null) {
			System.out.println("NULL Response Data");
			System.exit(1);
		}

		System.out.println("Transfer Response is: ");
		System.out.println("AuthorityID is: " +  res_data.getAuthorityId());
		System.out.println("Source AuthorityID is: " +  res_data.getSourceAuthorityId());
		System.out.println("Target AuthorityID is: " +  res_data.getTargetAuthorityId());
		System.out.println("trStatus is: " +  res_data.getTransferStatus());
		System.out.println("reID is: " +  res_data.getRequestClientId());
		System.out.println("reDate is: " +  res_data.getRequestDate().getTime());
		System.out.println("acID is: " +  res_data.getActionClientId());
		System.out.println("acDate is: " +  res_data.getActionDate().getTime());
	}

	public static void transferXriNameResponse( EppResponse res )
	{
		if (res == null) {
			System.out.println("NULL Response");
			System.exit(1);
		}

		System.out.println(res);
		if (!res.success()) {
			printErrors(res.getResult());
			return;
		}

		EppResponseDataTransferXriName res_data = (EppResponseDataTransferXriName) res.getResponseData();
		if (res_data == null) {
			System.out.println("NULL Response Data");
			System.exit(1);
		}

		System.out.println("Transfer Response is: ");
		System.out.println("Source AuthorityID is: " +  res_data.getSourceAuthorityId());
		System.out.println("Target AuthorityID is: " +  res_data.getTargetAuthorityId());
		System.out.println("trStatus is: " +  res_data.getTransferStatus());
		System.out.println("reID is: " +  res_data.getRequestClientId());
		System.out.println("reDate is: " +  res_data.getRequestDate().getTime());
		System.out.println("acID is: " +  res_data.getActionClientId());
		System.out.println("acDate is: " +  res_data.getActionDate().getTime());
	}

	// Fetch Update Response for Contact, Domain, Host and Svcsub

	public static void updateObjectResponse( EppResponse res )
	{
		if( res != null )
		{
			System.out.println(res);
			if(res.success())
			{
				System.out.println("Update Success");
			}
			else
			{
				printErrors(res.getResult());
			}
		}
		else
		{
			System.out.println("NULL Response");
		}
	}

	// Poll Command 

	public static EppCommand pollMessage()
	{
	   EppCommand cmd = new EppCommandPoll(getClientId());
	   System.out.println(cmd);
	   return cmd;
	}

	// Print Object functions for Contact, Domain and Host

	public static void printContact( EppContact obj )
	{
		System.out.println("CONTACT INFO IS::");
		System.out.println("ID :"+ obj.getId());
		System.out.println("ROID :"+ obj.getRoid());
		System.out.println("NAME :"+ obj.getContactDataAscii().getName());
		System.out.println("ORG :"+ obj.getContactDataAscii().getOrganization());
		System.out.println("STREET1 :"+ obj.getContactDataAscii().getAddress().getStreet()[0]);
		System.out.println("STREET2 :"+ obj.getContactDataAscii().getAddress().getStreet()[1]);
		System.out.println("STREET3 :"+ obj.getContactDataAscii().getAddress().getStreet()[2]);
		System.out.println("CITY :"+ obj.getContactDataAscii().getAddress().getCity());
		System.out.println("SP :"+ obj.getContactDataAscii().getAddress().getState());
		System.out.println("PC :"+ obj.getContactDataAscii().getAddress().getPostalCode());
		System.out.println("CC :"+ obj.getContactDataAscii().getAddress().getCountryCode());
		System.out.println("VOICE :"+ obj.getVoice().getNumber() + " ext: " + obj.getVoice().getExtension());
		System.out.println("FAX :"+ obj.getFax().getNumber() + " ext: " + obj.getVoice().getExtension());
		System.out.println("EMAIL :"+ obj.getEmail());
		System.out.println("clID :"+ obj.getClientId());
		System.out.println("crID :"+ obj.getClientIdCreated());
		System.out.println("crDate :"+ obj.getDateCreated().getTime().toString());
		System.out.println("upID :"+ obj.getClientIdUpdated());
		System.out.println("upDate :"+ obj.getDateUpdated().getTime().toString());
		System.out.println("trDate :"+ obj.getDateTransferred().getTime().toString());
		System.out.println("authInfo : type: "+ obj.getAuthInfo().getType() + " value: " + obj.getAuthInfo().getValue());
	}
	public static void printDomain( EppDomain obj )
	{
		System.out.println("DOMAIN is");
		System.out.println("ROID :"+ obj.getRoid());
		System.out.println("Name :"+ obj.getName());
		System.out.println("Registrant :"+ obj.getRegistrant());
		System.out.println("clID :"+ obj.getClientId());
		System.out.println("crID :"+ obj.getClientIdCreated());
		System.out.println("crDate :"+ obj.getDateCreated().getTime().toString());
		System.out.println("upID :"+ obj.getClientIdUpdated());
		System.out.println("upDate :"+ obj.getDateUpdated().getTime().toString());
		System.out.println("trDate :"+ obj.getDateTransferred().getTime().toString());
	}
	public static void printHost( EppHost obj )
	{
		System.out.println("HOST is");
		System.out.println("ROID :"+ obj.getRoid());
		System.out.println("Name :"+ obj.getName());
		System.out.println("clID :"+ obj.getClientId());
		System.out.println("crID :"+ obj.getClientIdCreated());
		System.out.println("crDate :"+ obj.getDateCreated().getTime().toString());
		System.out.println("upID :"+ obj.getClientIdUpdated());
		System.out.println("upDate :"+ obj.getDateUpdated().getTime().toString());
		System.out.println("trDate :"+ obj.getDateTransferred().getTime().toString());
		for( int i = 0; i < obj.getAddress().size(); i++ )
		{
			EppIpAddress addr = (EppIpAddress) obj.getAddress().get(i);
			System.out.println("IPAddress:" + addr.getAddress() + "Type:" + addr.getType());
		}
	}
	public static void printSvcsub( EppSvcsub obj )
	{
		System.out.println("SVCSUB is");
		System.out.println("ROID :"+ obj.getRoid());
		System.out.println("ID   :"+ obj.getId());
		System.out.println("SVCID:"+ obj.getService());
		System.out.println("clID :"+ obj.getClientId());
		System.out.println("crID :"+ obj.getClientIdCreated());
		System.out.println("crDate :"+ obj.getDateCreated().getTime().toString());
		System.out.println("upID :"+ obj.getClientIdUpdated());
		System.out.println("upDate :"+ obj.getDateUpdated().getTime().toString());
		System.out.println("trDate :"+ obj.getDateTransferred().getTime().toString());
		System.out.println("exDate :"+ obj.getDateExpired().getTime().toString());
		for( int i = 0; i < obj.getParam().size(); i++ )
		{
			EppSvcsubParam p = (EppSvcsubParam) obj.getParam().get(i);
			System.out.println("Param: " + p.getName() + "=" + p.getValue());
		}
	}
	public static void printXriAuthority( EppXriAuthority obj )
	{
		System.out.println("XRI Authority is");
		System.out.println("AuthID :"+ obj.getAuthorityId());
		System.out.println("Escrow agent :"+ obj.getEscrowAgent().getAuthorityId());
		System.out.println("Contact agent :"+ obj.getContactAgent().getAuthorityId());
		System.out.println("clID :"+ obj.getClientId());
		System.out.println("crID :"+ obj.getClientIdCreated());
		System.out.println("crDate :"+ obj.getDateCreated().getTime().toString());
		System.out.println("upID :"+ obj.getClientIdUpdated());
		System.out.println("upDate :"+ obj.getDateUpdated().getTime().toString());
		System.out.println("trDate :"+ obj.getDateTransferred().getTime().toString());
	}
	public static void printXriName( EppXriName obj )
	{
		System.out.println("IName is");
		System.out.println("IName :"+ obj.getIName());
		System.out.println("AuthID :"+ obj.getAuthorityId());
		System.out.println("clID :"+ obj.getClientId());
		System.out.println("crID :"+ obj.getClientIdCreated());
		System.out.println("crDate :"+ obj.getDateCreated().getTime().toString());
		System.out.println("upID :"+ obj.getClientIdUpdated());
		System.out.println("upDate :"+ obj.getDateUpdated().getTime().toString());
		System.out.println("trDate :"+ obj.getDateTransferred().getTime().toString());
	}
	public static void printXriNumber( EppXriNumber obj )
	{
		System.out.println("INumber is");
		System.out.println("INumber :"+ obj.getINumber());
		System.out.println("AuthID :"+ obj.getAuthorityId());
		System.out.println("clID :"+ obj.getClientId());
		System.out.println("crID :"+ obj.getClientIdCreated());
		System.out.println("crDate :"+ obj.getDateCreated().getTime().toString());
		System.out.println("upID :"+ obj.getClientIdUpdated());
		System.out.println("upDate :"+ obj.getDateUpdated().getTime().toString());
		System.out.println("trDate :"+ obj.getDateTransferred().getTime().toString());
	}
	public static void printXriService( EppXriService obj )
	{
		System.out.println("XRI Service is");
		System.out.println("IService ID :"+ obj.getId());
		System.out.println("AuthID :"+ obj.getAuthorityId());
		System.out.println("clID :"+ obj.getClientId());
		System.out.println("crID :"+ obj.getClientIdCreated());
		System.out.println("crDate :"+ obj.getDateCreated().getTime().toString());
		System.out.println("upID :"+ obj.getClientIdUpdated());
		System.out.println("upDate :"+ obj.getDateUpdated().getTime().toString());
		System.out.println("trDate :"+ obj.getDateTransferred().getTime().toString());
	}

	// Build Object Functions for Contact, Domain and Host

	public static EppContact buildContact()
	{
		EppAddress addr = new EppAddress();
		addr.setStreet(0, "101 Park Street");
		addr.setStreet(1, "Suite 101");
		addr.setCity("Sterling");
		addr.setState("VA");
		addr.setPostalCode("20166");
		addr.setCountryCode("US");

		EppContactData ascii = new EppContactData("FooBar, Inc,", null, addr);
		EppContactData i15d  = new EppContactData("Foo Bar", "FooBar, Inc.", addr);

		EppContact contact = new EppContact("CONTACT-ID");
		contact.setContactDataAscii(ascii);
		contact.setContactDataI15d(i15d);
		contact.setVoice("+1.7035551234", "9999");
		contact.setEmail("mail@foobar.com");
		contact.setAuthInfo(new EppAuthInfo(EppAuthInfo.TYPE_PW, "2fooBar"));

		return contact;
	}
	public static EppDomain buildDomain()
	{
		EppDomain domain = new EppDomain("aff0701d.NEUSTAR");
		domain.addContact("AFFCONTACT07_01", EppDomain.CONTACT_TYPE_ADMIN);
		domain.addContact("AFFCONTACT07_02", EppDomain.CONTACT_TYPE_TECH);
		domain.addContact("AFFCONTACT07_03", EppDomain.CONTACT_TYPE_BILLING);
		
		domain.setPeriod(new EppPeriod(2, EppPeriod.UNIT_YEAR));
		domain.setRegistrant("AFFCONTACT07_10");
		domain.setAuthInfo(new EppAuthInfo(EppAuthInfo.TYPE_PW, "2fooBar"));
		return domain;
	}
    public static String readFromFile(String fileName) throws IOException {
    	String fileContent = "";
    	File file = new File(fileName);
    	ByteArrayOutputStream bas = new ByteArrayOutputStream();
        FileInputStream fis = new FileInputStream(file);
        try {
            byte[] buffer = new byte[1024];
            // remaining is the number of bytes to read to fill the buffer
            int remaining = 1024; 
            // block number is incremented each time a block of 1024 bytes is read 
            //and written
            int blockNumber = 1;
            int offset = 0;
            while (true) {
            	
                int read = fis.read(buffer, buffer.length - remaining, remaining);
                if (read >= 0) { // some bytes were read
                    remaining -= read;
                    if (remaining == 0) { // the buffer is full
                    	bas.write(buffer, 0, read);
                        blockNumber++;
                        remaining = buffer.length;
                    }
                }
                else { 
                    // the end of the file was reached. If some bytes are in the buffer
                    // they are written to the last output file
                    if (remaining < buffer.length) {
                    	bas.write(buffer,0,buffer.length - remaining);
                    }
                    break;
                }
                offset += read;
            }
        }
        finally {
            fis.close();
        }
        fileContent = bas.toString();
        return fileContent;
    }
    
	public static EppCommandCreateLaunchRegistration readSMD(String smdFileName){
        
        
		String smdSB ="";
		
//		BufferedReader smdReader = null;
//       
//		try {
//			smdReader = new BufferedReader( new FileReader (smdFileName));
//		} catch (FileNotFoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//        if(smdReader!=null){
//	        Path p = Paths.get(smdFileName);
//	        try {
//				byte[] byteArray = Files.readAllBytes(p);
//				smdSB = new String(byteArray);
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}

//	        try {
//				while( ( smdReader.read(cbuf) ) != -1 ) {
//	        	//while((smdLine = smdReader.readLine()) != null){
//					smdSB+=new String (cbuf);
//	        	}
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//	        try {
//				smdReader.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//        }
		try {
			smdSB = readFromFile(smdFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	        if(smdSB.length() > 0){
				elr = new EppCommandCreateLaunchRegistration(); 
				smd = new EppSignedMarkData();
				String _smd = smdSB.toString();
				smd.rawXml(_smd);
				//System.out.println(smd.rawXml());
				EppLaunchPhase elp = new EppLaunchPhase();
				elp.phase("sunrise");
				elr.phase(elp);
				elr.signedMark(smd);
				System.out.println(smd.toString());
				
	        }
        
        System.out.println(elr.toString());
        return elr;

	}
	public static EppHost buildHost()
	{
		EppHost host = new EppHost("ns1.example1.biz");
		host.addAddress(new EppIpAddress("194.34.23.45"));
		host.addAddress(new EppIpAddress("198.34.67.78","v4"));
		return host;
	}
	public static EppSvcsub buildSvcsub()
	{
		EppSvcsub svcsub = new EppSvcsub("BIZLOCK-001");
		svcsub.setService("BIZLOCK");
		svcsub.addContact("ROID-1", EppDomain.CONTACT_TYPE_ADMIN);
		svcsub.addContact("ROID-2", EppDomain.CONTACT_TYPE_TECH);
		svcsub.addParam("domain", "example.biz");
		svcsub.addParam("password", "foobar");
		svcsub.setPeriod(new EppPeriod(2, EppPeriod.UNIT_YEAR));
		svcsub.setRegistrant("ROID-3");
		svcsub.setAuthInfo(new EppAuthInfo(EppAuthInfo.TYPE_PW, "2fooBar"));
		return svcsub;
	}

	public static EppXriAuthority buildXriAuthority()
	{
		EppXriAuthority a = new EppXriAuthority("=!(!!1002!2000.2)");
		EppXriTrustee t = new EppXriTrustee();
		t.setAuthorityId("=!(!!1111!2222)");
		a.setEscrowAgent(t);
		a.setAuthInfo(new EppAuthInfo(EppAuthInfo.TYPE_PW, "2fooBar"));
		return a;
	}

	public static EppXriName buildXriName()
	{
		EppXriName n = new EppXriName("@foo");
		n.setPeriod(new EppPeriod(2, EppPeriod.UNIT_YEAR));
		n.setAuthority("=!(!!1111!2222)",
			new EppAuthInfo(EppAuthInfo.TYPE_PW, "2fooBar"));
		return n;
	}

	public static EppXriNumber buildXriNumber()
	{
		EppXriNumber n = new EppXriNumber("!!1234!5678");
		n.setPeriod(new EppPeriod(2, EppPeriod.UNIT_YEAR));
		n.setAuthority("=!(!!1111!2222)",
			new EppAuthInfo(EppAuthInfo.TYPE_PW, "2fooBar"));
		return n;
	}

	public static EppXriService buildXriService()
	{
		EppXriService s = new EppXriService("+service1");
		s.setPeriod(new EppPeriod(2, EppPeriod.UNIT_YEAR));
		s.setAuthority("=!(!!1111!2222)",
			new EppAuthInfo(EppAuthInfo.TYPE_PW, "2fooBar"));
		return s;
	}

	// Generate Unique Client ID

	public static String getClientId() 
	{
		counter = counter + 1;	
		return "ABC-" + counter;
	}

	// Print Response Errors

	public static void printErrors(Vector results)
	{
		System.out.println("ERRORS ARE::" );
		for( int i = 0; i < results.size(); i++ )
		{
			EppResult result = (EppResult) results.get(i);
			System.out.println(result.getCode() + ":" + result.getMessage().getMessage());
		}
	}

	// Dump the socket associated with the channel

	public static void dumpChannel( EppChannel channel )
	{
		Socket socket = null;

		if( channel instanceof EppChannelTcp )
		{
			socket = ((EppChannelTcp) channel).getSocket();
		}

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
}
