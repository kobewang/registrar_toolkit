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
package com.junit;

import junit.framework.Assert;

import org.junit.Test;
import org.w3c.dom.Node;

import com.neulevel.epp.core.EppAuthInfo;
import com.neulevel.epp.core.EppParser;
import com.neulevel.epp.core.EppPeriod;
import com.neulevel.epp.core.command.EppCommand;
import com.neulevel.epp.core.command.EppCommandInfoDomain;
import com.neulevel.epp.ext.fee.EppFeeCommand;
import com.neulevel.epp.ext.fee.command.EppCommandInfoFee;

public class EppCommandInfoFeeTest {

	@Test
	public void test() {
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
		
		String str = cmd.toString();

		System.out.println(str);

		String rootDir = System.getProperty("user.dir");
		rootDir= rootDir.replace("\\", "//");
		EppParser parser = new EppParser(cmd.toString(),rootDir + "//xsd");
		if (parser.hasError())
		{
			System.out.println(parser.getResult());
			System.exit(1);
		}

		epp = parser.getRootNode();
		newcmd = (EppCommandInfoDomain) EppCommand.fromXML(epp);
		if( newcmd == null )
		{
			System.out.println("Error in fromXML");
			System.exit(1);
		}
		System.out.println(newcmd);
		
		Assert.assertEquals(cmd.toString(), newcmd.toString());
	}
}