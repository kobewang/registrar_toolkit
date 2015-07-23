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
import org.w3c.dom.Node;

import com.neulevel.biz.BizLock;
import com.neulevel.epp.core.EppAddress;
import com.neulevel.epp.core.EppAuthInfo;
import com.neulevel.epp.core.EppContact;
import com.neulevel.epp.core.EppContactData;
import com.neulevel.epp.core.EppContactType;
import com.neulevel.epp.core.EppDomain;
import com.neulevel.epp.core.EppHost;
import com.neulevel.epp.core.EppIpAddress;
import com.neulevel.epp.core.EppParser;
import com.neulevel.epp.core.EppPeriod;
import com.neulevel.epp.core.EppSvcsub;
import com.neulevel.epp.core.command.EppCommand;
import com.neulevel.epp.core.command.EppCommandCreate;
import com.neulevel.epp.ext.EppIDNData;

/**
 * The <code>EppCreateTest</code> class tests functions of
 * <code>EppCommandCreate</code> commands.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.3 $ $Date: 2013/06/14 16:40:26 $
 */
public class EppCreateTest
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
		contact.setAuthInfo(new EppAuthInfo("pw", "NEULEVEL-123"));

		EppCommandCreate cmd = new EppCommandCreate(contact);
		cmd.setClientTransactionId("ABC-DEF-12345");
		
		
		System.out.println(cmd);

		EppParser parser = new EppParser(cmd.toString());
		if( parser.hasError() )
		{
			System.out.println(parser.getResult());
			System.exit(1);
		}

		Node epp = parser.getRootNode();
		EppCommand newcmd = (EppCommand) EppCommand.fromXML(epp);
		if( newcmd == null )
		{
			System.out.println("Error in fromXML");
			System.exit(1);
		}
		System.out.println(newcmd);
	}

	public static void testDomain()
	{
		EppDomain domain = new EppDomain("example.com");
		domain.setPeriod(new EppPeriod(2, 'y'));

		domain.addNameServer("ns1.example.com");
		domain.addNameServer("ns2.example.com");
		domain.setRegistrant("JD1234-EXAMPLE");
		domain.addContact(new EppContactType("JD1234-NEULEVEL", "admin"));
		domain.setAuthInfo(new EppAuthInfo("pw", "2fooBAR", "JD1234-EXAMPLE"));

		EppCommandCreate cmd = new EppCommandCreate(domain);
		cmd.setClientTransactionId("ABC-DEF-12345");
		EppIDNData idn = new EppIDNData();
		idn.table("es");
		idn.uname("espa&#xF1;ol.example.com");
		cmd.addExtension(idn);
		System.out.println(cmd);

		EppParser parser = new EppParser(cmd.toString());
		if( parser.hasError() )
		{
			System.out.println(parser.getResult());
			System.exit(1);
		}

		Node epp = parser.getRootNode();
		if(null == epp){
			System.out.println("Error in parsing");
			System.exit(1);
		}
		EppCommand newcmd  = (EppCommand) EppCommand.fromXML(epp);

		System.out.println(newcmd);
	}

	public static void testHost()
	{
		EppHost host = new EppHost("example.com");
		host.addAddress(new EppIpAddress("192.168.1.1"));
		host.addAddress(new EppIpAddress("192.168.1.2", "v4"));
		host.addAddress(new EppIpAddress("::FFFF:192.168.1.3", "v6"));

		EppCommandCreate cmd = new EppCommandCreate(host);
		cmd.setClientTransactionId("ABC-DEF-12345");
		System.out.println(cmd);

		EppParser parser = new EppParser(cmd.toString());
		if( parser.hasError() )
		{
			System.out.println(parser.getResult());
			System.exit(1);
		}

		Node epp = parser.getRootNode();
		EppCommand newcmd = (EppCommand) EppCommand.fromXML(epp);
		if( newcmd == null )
		{
			System.out.println("Error in fromXML");
			System.exit(1);
		}
		System.out.println(newcmd);
	}

	public static void testSvcsub()
	{
		EppSvcsub svcsub = new EppSvcsub("BIZLOCK-123");
		svcsub.setService(BizLock.ID);
		svcsub.setPeriod(new EppPeriod(2, 'y'));

		svcsub.addParam(BizLock.PARAM_DOMAIN  , "example.biz");
		svcsub.addParam(BizLock.PARAM_DOMAIN_AUTHINFO, "2fooBAR");
		svcsub.addParam(BizLock.PARAM_BIZACCOUNT , "myBizAccount");
		svcsub.addParam(BizLock.PARAM_USERID, "myUserID");
		svcsub.addParam(BizLock.PARAM_PASSWORD, "myPassword");
		svcsub.addParam(BizLock.PARAM_LOCKTYPE , "hardLock");
		svcsub.addParam(BizLock.PARAM_LOCKSTATE , "on");
		svcsub.setRegistrant("JD1234-EXAMPLE");
		svcsub.addContact(new EppContactType("JD1234-NEULEVEL",
				svcsub.CONTACT_TYPE_PRIMARY));
		svcsub.addContact(new EppContactType("JD1235-NEULEVEL",
				svcsub.CONTACT_TYPE_SECONDARY));
		svcsub.addContact(new EppContactType("JD1235-NEULEVEL",
				svcsub.CONTACT_TYPE_BILLING));
		svcsub.setAuthInfo(new EppAuthInfo("pw", "2fooBAR", "JD1234-EXAMPLE"));

		EppCommandCreate cmd = new EppCommandCreate(svcsub);
		cmd.setClientTransactionId("ABC-DEF-12345");
		System.out.println(cmd);

		EppParser parser = new EppParser(cmd.toString());
		if( parser.hasError() )
		{
			System.out.println(parser.getResult());
			System.exit(1);
		}

		Node epp = parser.getRootNode();
		EppCommand newcmd = (EppCommand) EppCommand.fromXML(epp);
		if( newcmd == null )
		{
			System.out.println("Error in fromXML");
			System.exit(1);
		}
		System.out.println(newcmd);
	}
}
