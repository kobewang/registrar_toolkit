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
import com.neulevel.epp.core.*;
import com.neulevel.epp.core.command.*;
import com.neulevel.epp.core.response.*;

import org.w3c.dom.*;

/**
 * The <code>EppUpdateTest</code> class tests functions of
 * <code>EppCommandUpdate</code> commands.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppUpdateTest
{
	public static void main( String argv[] )
	{
		testContact();
		testDomain();
		testHost();
		testSvcsub();
	}

	private static void testContact()
	{
		Node epp;
		EppCommandUpdateContact cmd;
		EppCommandUpdateContact newcmd;

		cmd = new EppCommandUpdateContact("CONTACT-12345");
		cmd.addStatus(EppContact.STATUS_CLIENT_DELETE_PROHIBITED);
		cmd.addStatus(EppContact.STATUS_CLIENT_TRANSFER_PROHIBITED);
		cmd.addStatus(EppContact.STATUS_CLIENT_UPDATE_PROHIBITED);
		cmd.addStatus(EppContact.STATUS_LINKED);
		cmd.addStatus(new EppStatus(EppContact.STATUS_OK));
		cmd.removeStatus(new EppStatus(EppContact.STATUS_PENDING_DELETE));
		cmd.removeStatus(EppContact.STATUS_PENDING_TRANSFER);
		cmd.removeStatus(EppContact.STATUS_SERVER_DELETE_PROHIBITED);
		cmd.removeStatus(EppContact.STATUS_SERVER_TRANSFER_PROHIBITED);
		cmd.removeStatus(EppContact.STATUS_SERVER_UPDATE_PROHIBITED);

		EppContactData ascii = new EppContactData();
		ascii.setName("NEW CONTACT NAME");
		cmd.setNewAscii(ascii);

		EppContactData i15d = new EppContactData();
		i15d.setAddress(new EppAddress("100 Park Street", "Sterling", "VA", "20166", "US"));
		cmd.setNewI15d(i15d);

		cmd.setNewVoice("+1.7035551234", "1234");
		cmd.setNewFax("+1.7035551234", "4321");
		cmd.setNewEmail("foo@bar.biz");

		cmd.setNewAuthInfo(new EppAuthInfo("pw", "2fooBar", "NEULEVEL-12345"));

		cmd.setClientTransactionId("ABC-DEF-12345");
		System.out.println(cmd);

		EppParser parser = new EppParser(cmd.toString());
		if( parser.hasError() )
		{
			System.out.println(parser.getResult());
			System.exit(1);
		}

		epp = parser.getRootNode();
		newcmd = (EppCommandUpdateContact) EppCommand.fromXML(epp);
		if( newcmd == null )
		{
			System.out.println("Error in fromXML");
			System.exit(1);
		}
		System.out.println(newcmd);
	}

	private static void testDomain()
	{
		Node epp;
		EppCommandUpdateDomain cmd;
		EppCommandUpdateDomain newcmd;

		cmd = new EppCommandUpdateDomain("example.biz");
		cmd.addStatus(EppDomain.STATUS_CLIENT_DELETE_PROHIBITED);
		cmd.addStatus(EppDomain.STATUS_CLIENT_HOLD);
		cmd.addStatus(EppDomain.STATUS_CLIENT_RENEW_PROHIBITED);
		cmd.addStatus(EppDomain.STATUS_CLIENT_TRANSFER_PROHIBITED);
		cmd.addStatus(EppContact.STATUS_CLIENT_UPDATE_PROHIBITED);
		cmd.addStatus(new EppStatus(EppDomain.STATUS_INACTIVE));
		cmd.addStatus(new EppStatus(EppDomain.STATUS_OK));
		cmd.removeStatus(new EppStatus(EppDomain.STATUS_PENDING_DELETE));
		cmd.removeStatus(EppDomain.STATUS_PENDING_TRANSFER);
//		cmd.removeStatus(EppDomain.STATUS_PENDING_VERIFICATION);
		cmd.removeStatus(EppDomain.STATUS_PENDING_CREATE);
		cmd.removeStatus(EppDomain.STATUS_PENDING_UPDATE);
		cmd.removeStatus(EppDomain.STATUS_SERVER_DELETE_PROHIBITED);
		cmd.removeStatus(EppDomain.STATUS_SERVER_HOLD);
		cmd.removeStatus(EppDomain.STATUS_SERVER_RENEW_PROHIBITED);
		cmd.removeStatus(EppDomain.STATUS_SERVER_TRANSFER_PROHIBITED);
		cmd.removeStatus(EppDomain.STATUS_SERVER_UPDATE_PROHIBITED);
		cmd.addNameServer("ns1.example.biz");
		cmd.addNameServer("ns2.example.biz");
		cmd.removeNameServer("ns3.example.biz");
		cmd.removeNameServer("ns4.example.biz");
		cmd.addContact(new EppContactType("CONTACT1-12345", "billing"));
		cmd.addContact(new EppContactType("CONTACT2-12345", "admin"));
		cmd.addContact(new EppContactType("CONTACT3-12345", "tech"));
		cmd.removeContact(new EppContactType("CONTACT4-12345", "billing"));
		cmd.removeContact(new EppContactType("CONTACT5-12345", "admin"));
		cmd.removeContact(new EppContactType("CONTACT6-12345", "tech"));
		cmd.setNewRegistrant("NEWREGISTRANT");
		cmd.setNewAuthInfo(new EppAuthInfo("pw", "2fooBar", "NEULEVEL-12345"));

		cmd.setClientTransactionId("ABC-DEF-12345");
		System.out.println(cmd);

		EppParser parser = new EppParser(cmd.toString());
		if( parser.hasError() )
		{
			System.out.println(parser.getResult());
			System.exit(1);
		}

		epp = parser.getRootNode();
		newcmd = (EppCommandUpdateDomain) EppCommand.fromXML(epp);
		if( newcmd == null )
		{
			System.out.println("Error in fromXML");
			System.exit(1);
		}
		System.out.println(newcmd);
	}

	private static void testHost()
	{
		Node epp;
		EppCommandUpdateHost cmd;
		EppCommandUpdateHost newcmd;

		cmd = new EppCommandUpdateHost("ns1.example.biz");
		cmd.addStatus(EppHost.STATUS_CLIENT_DELETE_PROHIBITED);
		cmd.addStatus(EppHost.STATUS_CLIENT_UPDATE_PROHIBITED);
		cmd.addStatus(EppHost.STATUS_LINKED);
		cmd.addStatus(new EppStatus(EppHost.STATUS_OK));
		cmd.removeStatus(new EppStatus(EppHost.STATUS_PENDING_DELETE));
		cmd.removeStatus(EppHost.STATUS_PENDING_TRANSFER);
		cmd.removeStatus(EppHost.STATUS_SERVER_DELETE_PROHIBITED);
		cmd.removeStatus(EppHost.STATUS_SERVER_UPDATE_PROHIBITED);
		cmd.addAddress(new EppIpAddress("192.168.1.1"));
		cmd.addAddress(new EppIpAddress("192.168.1.2"));
		cmd.removeAddress(new EppIpAddress("192.168.1.3"));
		cmd.removeAddress(new EppIpAddress("192.168.1.4"));
		cmd.setNewName("ns2.example.biz");

		cmd.setClientTransactionId("ABC-DEF-12345");
		System.out.println(cmd);

		EppParser parser = new EppParser(cmd.toString());
		if( parser.hasError() )
		{
			System.out.println(parser.getResult());
			System.exit(1);
		}

		epp = parser.getRootNode();
		newcmd = (EppCommandUpdateHost) EppCommand.fromXML(epp);
		if( newcmd == null )
		{
			System.out.println("Error in fromXML");
			System.exit(1);
		}
		System.out.println(newcmd);
	}

	private static void testSvcsub()
	{
		Node epp;
		EppCommandUpdateSvcsub cmd;
		EppCommandUpdateSvcsub newcmd;

		cmd = new EppCommandUpdateSvcsub("BIZLOCK-001");
		cmd.addStatus(EppSvcsub.STATUS_CLIENT_DELETE_PROHIBITED);
		cmd.addStatus(EppSvcsub.STATUS_CLIENT_SUSPENDED);
		cmd.addStatus(EppSvcsub.STATUS_CLIENT_RENEW_PROHIBITED);
		cmd.addStatus(EppSvcsub.STATUS_CLIENT_TRANSFER_PROHIBITED);
		cmd.addStatus(EppContact.STATUS_CLIENT_UPDATE_PROHIBITED);
//		cmd.addStatus(new EppStatus(EppSvcsub.STATUS_INACTIVE));
		cmd.addStatus(new EppStatus(EppSvcsub.STATUS_OK));
		cmd.removeStatus(new EppStatus(EppSvcsub.STATUS_PENDING_DELETE));
		cmd.removeStatus(EppSvcsub.STATUS_PENDING_TRANSFER);
//		cmd.removeStatus(EppSvcsub.STATUS_PENDING_VERIFICATION);
		cmd.removeStatus(EppSvcsub.STATUS_SERVER_DELETE_PROHIBITED);
		cmd.removeStatus(EppSvcsub.STATUS_SERVER_SUSPENDED);
		cmd.removeStatus(EppSvcsub.STATUS_SERVER_RENEW_PROHIBITED);
		cmd.removeStatus(EppSvcsub.STATUS_SERVER_TRANSFER_PROHIBITED);
		cmd.removeStatus(EppSvcsub.STATUS_SERVER_UPDATE_PROHIBITED);
		cmd.addParam(new EppSvcsubParam("XXXX", "YYYY"));
		cmd.addParam(new EppSvcsubParam("YYYY", "ZZZZ"));
		cmd.removeParam(new EppSvcsubParam("XXXX1", "YYYY"));
		cmd.removeParam(new EppSvcsubParam("YYYY1", "ZZZZ"));
		cmd.addContact(new EppContactType("CONTACT1-12345", "billing"));
		cmd.addContact(new EppContactType("CONTACT2-12345", "admin"));
		cmd.addContact(new EppContactType("CONTACT3-12345", "tech"));
		cmd.addContact(new EppContactType("CONTACT3-12345", "primary"));
		cmd.addContact(new EppContactType("CONTACT3-12345", "secondary"));
		cmd.removeContact(new EppContactType("CONTACT4-12345", "billing"));
		cmd.removeContact(new EppContactType("CONTACT5-12345", "admin"));
		cmd.removeContact(new EppContactType("CONTACT6-12345", "tech"));
		cmd.setNewRegistrant("NEWREGISTRANT");
		cmd.setNewAuthInfo(new EppAuthInfo("pw", "2fooBar", "NEULEVEL-12345"));
		cmd.setUserId("myUserId");
		cmd.setAuthInfo(new EppAuthInfo("pw", "2fooBar", "NEULEVEL-12345"));

		cmd.setClientTransactionId("ABC-DEF-12345");
		System.out.println(cmd);

		EppParser parser = new EppParser(cmd.toString());
		if( parser.hasError() )
		{
			System.out.println(parser.getResult());
			System.exit(1);
		}

		epp = parser.getRootNode();
		newcmd = (EppCommandUpdateSvcsub) EppCommand.fromXML(epp);
		if( newcmd == null )
		{
			System.out.println("Error in fromXML");
			System.exit(1);
		}
		System.out.println(newcmd);
	}
}
