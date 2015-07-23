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
import com.neulevel.epp.core.EppError;
import com.neulevel.epp.core.EppParser;
import com.neulevel.epp.core.EppPeriod;
import com.neulevel.epp.core.EppTransactionId;
import com.neulevel.epp.core.command.EppCommand;
import com.neulevel.epp.core.command.EppCommandInfoDomain;
import com.neulevel.epp.core.response.EppResponse;
import com.neulevel.epp.core.response.EppResult;
import com.neulevel.epp.core.response.EppValueReason;
import com.neulevel.epp.ext.fee.EppFeeCommand;
import com.neulevel.epp.ext.fee.EppFeeFee;
import com.neulevel.epp.ext.fee.command.EppCommandInfoFee;
import com.neulevel.epp.ext.fee.response.EppResponseInfoFee;


public class EppFeeExtensionInfoTest {
	public static void main (String args[]) {
		testDomainInfoRequest();
		testDomainInfoResponse();
		
	}
	
	private static void testDomainInfoRequest() {
		Node epp;
		EppCommand cmd;
		EppCommand newcmd;

		cmd = new EppCommandInfoDomain("example.biz");
		cmd.setClientTransactionId("ABC-DEF-12345");
		((EppCommandInfoDomain) cmd).setAuthInfo(new EppAuthInfo(EppAuthInfo.TYPE_PW, "2fooBar"));
		((EppCommandInfoDomain) cmd).setHostsControl(EppCommandInfoDomain.HOSTS_NONE);
		
		
		EppCommandInfoFee feeInfo = new EppCommandInfoFee();
		feeInfo.setCurrency("USD");
		feeInfo.setPeriod(new EppPeriod(1, 'y'));
		EppFeeCommand feeCommand = new EppFeeCommand();
		feeCommand.setCommand("create");
		feeCommand.setPhase("open");
		feeCommand.setSubphase("sunrise");
		feeInfo.setFeeCommand(feeCommand);
		
		cmd.addExtension(feeInfo);
		
		System.out.println(cmd);

		EppParser parser = new EppParser(cmd.toString(),"D://gitrepo//rtk_tld//src//java//xsd");
		if( parser.hasError() )
		{
			System.out.println(parser.getResult());
			System.exit(1);
		}

		epp = parser.getRootNode();
		newcmd = (EppCommand) EppCommand.fromXML(epp);
		if( newcmd == null )
		{
			System.out.println("Error in fromXML");
			System.exit(1);
		}
		System.out.println(newcmd);
		
		System.out.println(newcmd.toString().equals(cmd.toString()));
	}
	
	private static void testDomainInfoResponse() {
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
        
        EppResponseInfoFee feeInfo = new EppResponseInfoFee();
		feeInfo.setCurrency("USD");
		feeInfo.setPeriod(new EppPeriod(1, 'm'));
		EppFeeCommand feeCommand = new EppFeeCommand();
		feeCommand.setCommand("create");
		feeCommand.setPhase("open");
		feeCommand.setSubphase("sunrise");
		feeInfo.setFeeCommand(feeCommand);
		
		EppFeeFee feeFee = new EppFeeFee();
		feeFee.setFee(5.00);
		feeFee.setDescription("create");
		
		EppFeeFee feeFee1 = new EppFeeFee();
		feeFee1.setFee(10.00);
		
		feeInfo.addFee(feeFee);
		
		feeInfo.addFee(feeFee1);
		
		res.addExtension(feeInfo);
		
		System.out.println(res);
		
		EppParser parser = new EppParser(res.toString(),"D://gitrepo//rtk_tld//src//java//xsd");
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
			System.exit(1);
		}
		System.out.println(newres);
		
		System.out.println(newres.toString().equals(res.toString()));
	}
}
