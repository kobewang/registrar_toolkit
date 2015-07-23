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
import com.neulevel.epp.ext.*;
import com.neulevel.epp.ext.command.*;
import com.neulevel.epp.ext.response.*;

import java.util.*;
import org.w3c.dom.*;

/**
 * The <code>EppSecDnsTest</code> class tests functions of
 * <code>DNSSEC</code> related commands.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.3 $ $Date: 2012/06/26 12:08:42 $
 */
public class EppSecDnsTest
{
	public static void main( String argv[] )
	{
/*
		testCommand();
*/
		testResponse();
	}

	private static void testCommand()
	{
		EppDomain domain = new EppDomain("example.com");
		domain.setPeriod(new EppPeriod(2, 'y'));

		domain.addNameServer("ns1.example.com");
		domain.addNameServer("ns2.example.com");
		domain.setRegistrant("JD1234-EXAMPLE");
		domain.addContact(new EppContactType("JD1234-NEULEVEL", "admin"));
		domain.setAuthInfo(new EppAuthInfo("pw", "2fooBAR", "JD1234-EXAMPLE"));

		EppCommandCreate cmd = new EppCommandCreate(domain);

		EppSecDnsDsData ns0 = new EppSecDnsDsData();
		EppSecDnsDsData ns1 = new EppSecDnsDsData();


		ns0.setKeyTag(12345);
		ns0.setAlgorithm(3);	// RSA/SHA
		ns0.setDigestType(1);	// SHA-1
		ns0.setDigestString("ABCDEF0123456789");

		ns1.setKeyTag(54321);
		ns1.setAlgorithm(3);	// RSA/SHA
		ns1.setDigestType(1);	// SHA-1
		ns1.setDigestString("0123456789ABCDEF");


		EppSecDnsKeyData kd_inner = new EppSecDnsKeyData();
		EppSecDnsKeyData kd_outer = new EppSecDnsKeyData();


		kd_inner.setFlags( 2 );
		kd_outer.setFlags( 5 );

		kd_inner.setProtocol( 202 );
		kd_outer.setProtocol( 205 );

		kd_inner.setAlgorithm( 1 );
		kd_outer.setAlgorithm( 3 );

		kd_inner.setPublicKey("DEADBEEF");
		kd_outer.setPublicKey("FEED2012");

		ns0.setKeyData( kd_inner );

		EppCommandCreateSecDns secdnscmd = new EppCommandCreateSecDns();

		secdnscmd.add(ns0);
		secdnscmd.add(ns1);
		secdnscmd.setMaxSigLife(1000);

		cmd.addExtension(secdnscmd);

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

		/*create command with external keyData*/
		cmd = new EppCommandCreate(domain);
		secdnscmd = new EppCommandCreateSecDns();
		secdnscmd.add(kd_outer);
		secdnscmd.add(kd_outer);
		secdnscmd.setMaxSigLife(2000);
		cmd.addExtension(secdnscmd);
		cmd.setClientTransactionId("ABC-DEF-12345");
		System.out.println(cmd);
		parser = new EppParser(cmd.toString());
		if( parser.hasError() ) {
			System.out.println(parser.getResult());
			System.exit(1);
		}
		epp = parser.getRootNode();
		newcmd = (EppCommand) EppCommand.fromXML(epp);
		if( newcmd == null ) {
			System.out.println("Error in fromXML");
			System.exit(1);
		}
		System.out.println(newcmd);
		/*create command with external keyData*/


		EppCommandUpdateDomain updateadd = new EppCommandUpdateDomain("example.com");
		EppCommandUpdateSecDns secdnsadd = new EppCommandUpdateSecDns();

		secdnsadd.add(ns0);
		secdnsadd.add(ns1);
		secdnsadd.isRemoveAllDS(true);
		secdnsadd.setMaxSigLife(10000);

		updateadd.addExtension(secdnsadd);

		cmd.setClientTransactionId("ABC-DEF-12345");
		System.out.println(updateadd);

		parser = new EppParser(updateadd.toString());
		if( parser.hasError() )
		{
			System.out.println(parser.getResult());
			System.exit(1);
		}

		epp = parser.getRootNode();
		EppCommand newupdateadd = (EppCommand) EppCommand.fromXML(epp);
		if( newupdateadd == null )
		{
			System.out.println("Error in fromXML");
			System.exit(1);
		}
		System.out.println(newupdateadd);

		EppCommandUpdateDomain updaterem = new EppCommandUpdateDomain("example.com");
		EppCommandUpdateSecDns secdnsrem = new EppCommandUpdateSecDns();

		secdnsrem.setUrgentFlag(true);
/*
		secdnsrem.remove(ns0);
		secdnsrem.remove(ns1);
*/
		secdnsrem.setMaxSigLife(2000);
		secdnsrem.add(ns0);
		secdnsrem.add(ns1);
		secdnsrem.isRemoveAllDS(true);

		updaterem.addExtension(secdnsrem);

		cmd.setClientTransactionId("ABC-DEF-12345");
		System.out.println(updaterem);

		parser = new EppParser(updaterem.toString());
		if( parser.hasError() )
		{
			System.out.println(parser.getResult());
			System.exit(1);
		}

		epp = parser.getRootNode();
		EppCommand newupdaterem = (EppCommand) EppCommand.fromXML(epp);
		if( newupdaterem == null )
		{
			System.out.println("Error in fromXML");
			System.exit(1);
		}
		System.out.println(newupdaterem);
	}

	private static void testResponse()
	{
		Node epp;
		EppResponse res;
		EppResponse newres;
		EppResult result;
		EppTransactionId xid;

		res = new EppResponse();
		result = new EppResult(EppError.CODE_NO_ERROR,
		                       EppError.TEXT_NO_ERROR);
/*
		res.addResult(result);

		result = new EppResult(EppError.CODE_COMMAND_SYNTAX_ERROR,
		                       EppError.TEXT_COMMAND_SYNTAX_ERROR);
*/
		result.addValue("Value 1");
		result.addValue("Value 2");
		result.addValue("Value 3");
		result.addValue("Value 4");
		result.addValue(new EppValueReason("Value 5", "Reason 5"));
		result.addValue("Value 6");
		result.addValue(new EppValueReason("Value 7", "Reason 7"));
		res.addResult(result);
		xid = new EppTransactionId("ABC-DEF-12345", "SERVER-ID-9999");
		res.setTransactionId(xid);
		res.setMessageId("ABC123XYZ");
		res.setMessageQueued(99);
		res.setMessageQueueUpdated(Calendar.getInstance());

		EppSecDnsDsData ns0 = new EppSecDnsDsData();
		EppSecDnsDsData ns1 = new EppSecDnsDsData();

		ns0.setKeyTag(12345);
		ns0.setAlgorithm(3);	// RSA/SHA
		ns0.setDigestType(1);	// SHA-1
		ns0.setDigestString("ABCDEF0123456789");

		ns1.setKeyTag(54321);
		ns1.setAlgorithm(3);	// RSA/SHA
		ns1.setDigestType(1);	// SHA-1
		ns1.setDigestString("0123456789ABCDEF");

		EppSecDnsKeyData kd_inner = new EppSecDnsKeyData();
		EppSecDnsKeyData kd_outer = new EppSecDnsKeyData();


		kd_inner.setFlags( 2 );
		kd_outer.setFlags( 5 );

		kd_inner.setProtocol( 202 );
		kd_outer.setProtocol( 205 );

		kd_inner.setAlgorithm( 1 );
		kd_outer.setAlgorithm( 3 );

		kd_inner.setPublicKey("DEADBEEF");
		kd_outer.setPublicKey("FEED2012");

		ns0.setKeyData( kd_inner );


		EppResponseDataInfoSecDns secdnsinfo = new EppResponseDataInfoSecDns();
		secdnsinfo.add(ns0);
		secdnsinfo.add(ns1);
/*
		secdnsinfo.add(kd_outer);
*/
		secdnsinfo.setMaxSigLife(1000);

		res.addExtension(secdnsinfo);

		System.out.println(res);

		EppParser parser = new EppParser(res.toString());
		if( parser.hasError() )
		{
			System.out.println(parser.getResult());
			System.exit(1);
		}

		epp = parser.getRootNode();
		newres = (EppResponse) EppResponse.fromXML(epp);
		if( newres == null )
		{
			System.out.println("Error in fromXML");
		}
		System.out.println(newres);
	}
}
