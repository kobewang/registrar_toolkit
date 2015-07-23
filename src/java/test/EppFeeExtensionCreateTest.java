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
import java.util.Calendar;

import org.w3c.dom.Node;

import com.neulevel.epp.core.EppAuthInfo;
import com.neulevel.epp.core.EppContactType;
import com.neulevel.epp.core.EppDomain;
import com.neulevel.epp.core.EppError;
import com.neulevel.epp.core.EppParser;
import com.neulevel.epp.core.EppPeriod;
import com.neulevel.epp.core.EppTransactionId;
import com.neulevel.epp.core.command.EppCommand;
import com.neulevel.epp.core.command.EppCommandCreate;
import com.neulevel.epp.core.response.EppResponse;
import com.neulevel.epp.core.response.EppResult;
import com.neulevel.epp.ext.fee.EppFeeFee;
import com.neulevel.epp.ext.fee.command.EppCommandCreateFee;
import com.neulevel.epp.ext.fee.response.EppResponseCreateFee;

public class EppFeeExtensionCreateTest
{
	public static void main(String args[])
	{
		testRequest();
		testResponse();
	}

	private static void testRequest()
	{
		Node epp;
		EppCommand cmd;
		EppCommand newcmd;

		EppDomain domain = new EppDomain("CREATE-FEE.NEUSTAR");
		domain.setPeriod(new EppPeriod(2, 'y'));
		domain.setAuthInfo(new EppAuthInfo(EppAuthInfo.TYPE_PW, "2fooBar"));

		domain.addNameServer("ns1.example.com");
		domain.addNameServer("ns2.example.com");
		domain.setRegistrant("JD1234-EXAMPLE");
		domain.addContact(new EppContactType("JD1234-NEULEVEL", "admin"));
		domain.addContact(new EppContactType("JD1234-NEULEVEL", "billing"));
		domain.addContact(new EppContactType("JD1234-NEULEVEL", "tech"));

		cmd = new EppCommandCreate(domain);
		cmd.setClientTransactionId("ABC-DEF-12345");

		EppCommandCreateFee ext = new EppCommandCreateFee();
		ext.setCurrency("USD");
		EppFeeFee feeFee = new EppFeeFee();

		feeFee.setFee(100.1101);
		feeFee.setDescription("Tesing create command description 1");
		ext.addFee(feeFee);

		EppFeeFee feeFee2 = new EppFeeFee();

		feeFee2.setFee(1002.99);
		feeFee2.setDescription("Tesing create command description 2");
		ext.addFee(feeFee2);

		cmd.addExtension(ext);

		String str = cmd.toString();

		System.out.println(str);

		EppParser parser = new EppParser(str,
				"/home/santosh/GIT/rtk_tld/src/java/xsd");
		if (parser.hasError())
		{
			System.out.println(parser.getResult());
			System.exit(1);
		}

		epp = parser.getRootNode();
		newcmd = (EppCommand) EppCommand.fromXML(epp);
		if (newcmd == null)
		{
			System.out.println("Error in fromXML");
			System.exit(1);
		}
		String str2 = newcmd.toString();

		if (str2.equals(str))
		{
			System.out.println("MATCHES...");
		} else
		{
			System.out.println("Do NOT MATCH...");

		}
	}

	private static void testResponse()
	{
		Node epp;
		EppResponse res;
		EppResponse newres;
		EppResult result;
		EppTransactionId xid;

		res = new EppResponse();
		result = new EppResult(EppError.CODE_NO_ERROR, EppError.TEXT_NO_ERROR);
		result.addValue("Value 1");
		result.addValue("Value 2");

		res.addResult(result);
		xid = new EppTransactionId("ABC-DEF-12345", "SERVER-ID-9999");
		res.setTransactionId(xid);
		res.setMessageId("ABC123XYZ");
		res.setMessageQueued(99);
		res.setMessageQueueUpdated(Calendar.getInstance());

		EppResponseCreateFee ext = new EppResponseCreateFee();
		ext.setCurrency("USD");

		EppFeeFee feeFee = new EppFeeFee();
		feeFee.setFee(5.00);
		feeFee.setDescription("create create response description 1");

		EppFeeFee feeFee1 = new EppFeeFee();
		feeFee1.setFee(10.00);
		feeFee1.setDescription("create create response description 2");

		ext.addFee(feeFee);
		ext.addFee(feeFee1);

		res.addExtension(ext);

		String str = res.toString();
		System.out.println(str);

		EppParser parser = new EppParser(res.toString(),
				"/home/santosh/GIT/rtk_tld/src/java/xsd");
		if (parser.hasError())
		{
			System.out.println(parser.getResult());
			System.exit(1);
		}

		epp = parser.getRootNode();
		newres = (EppResponse) EppResponse.fromXML(epp);
		if (newres == null)
		{
			System.out.println("Error in fromXML");
			System.exit(1);
		}
		String str2 = newres.toString();

		if (str2.equals(str))
		{
			System.out.println("MATCHES...");
		} else
		{
			System.out.println(str2);

			System.out.println("Do NOT MATCH...");

		}
	}
}
