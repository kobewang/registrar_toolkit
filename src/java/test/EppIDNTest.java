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


import java.util.*;

import org.w3c.dom.*;
public class EppIDNTest {
	public static void main( String argv[] ) {
		testCommand();
		testResponse();
	}
	
	private static void testCommand() {
		EppDomain domain = new EppDomain("example.com");
		domain.setPeriod(new EppPeriod(2, 'y'));
		domain.addNameServer("ns1.example.com");
		domain.addNameServer("ns2.example.com");
		domain.setRegistrant("JD1234-EXAMPLE");
		domain.addContact(new EppContactType("JD1234-NEULEVEL", "admin"));
		domain.setAuthInfo(new EppAuthInfo("pw", "2fooBAR", "JD1234-EXAMPLE"));

		EppCommandCreate cmd = new EppCommandCreate(domain);
		EppIDNData idn = new EppIDNData();
		idn.table("es");
		idn.uname("espa&#xF1;ol.example.com");
		
		cmd.addExtension(idn);
		cmd.setClientTransactionId("ABC-DEF-12345");
		System.out.println(cmd);

		EppParser cre_parser = new EppParser(cmd.toString());
		if( cre_parser.hasError() )
		{
			System.out.println(cre_parser.getResult());
			System.exit(1);
		}
/*
*/
		Node createepp = cre_parser.getRootNode();
		EppCommand newcmd = (EppCommand) EppCommand.fromXML(createepp);
		if( newcmd == null )
		{
			System.out.println("Error in fromXML");
			System.exit(1);
		}
		System.out.println(newcmd);
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
		EppIDNData idnInfoRsp = new EppIDNData();
		idnInfoRsp.table("es");
		idnInfoRsp.uname("espa&#xF1;ol.example.com");
		
		res.addExtension(idnInfoRsp);
		
		System.out.println(res);
		EppParser parser = new EppParser(res.toString());
		if( parser.hasError() ) {
			System.out.println(parser.getResult());
			System.exit(1);
		}

		epp = parser.getRootNode();
		newres = (EppResponse) EppResponse.fromXML(epp);
		if( newres == null ) {
			System.out.println("Error in fromXML");
		}
		System.out.println(newres);
	}
}
