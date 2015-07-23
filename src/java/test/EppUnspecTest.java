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

import com.neulevel.biz.*;

import java.util.*;
import org.w3c.dom.*;

/**
 * The <code>EppUnspecTest</code> class tests functions of
 * <code>EppUnspec</code> commands.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.3 $ $Date: 2010/08/20 20:19:02 $
 */
public class EppUnspecTest
{
	public static void main( String argv[] )
	{
		testCommand();
		//testResponse();
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

		EppUnspec unspec = new EppUnspec("AppPurpose=P1 NexusCategory=C11");
		EppGenericNVPairs nvPairs = new EppGenericNVPairs();
		nvPairs.addGenericNVPair("LockService","");
		nvPairs.addGenericNVPair("LockNotes","Some notes ... Some notes again.");
		unspec.setGenericNVPairs(nvPairs);
		cmd.setUnspec(unspec);

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
		res.addResult(result);

		result = new EppResult(EppError.CODE_COMMAND_SYNTAX_ERROR,
		                       EppError.TEXT_COMMAND_SYNTAX_ERROR);
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

		EppUnspec unspec = new EppUnspec("");
		res.setUnspec(unspec);

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
