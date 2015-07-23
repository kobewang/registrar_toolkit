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

import static org.junit.Assert.*;

import java.util.Calendar;

import junit.framework.Assert;

import org.junit.Test;
import org.w3c.dom.Node;

import com.neulevel.epp.core.EppParser;
import com.neulevel.epp.core.EppPeriod;
import com.neulevel.epp.core.command.EppCommand;
import com.neulevel.epp.core.command.EppCommandRenewDomain;
import com.neulevel.epp.ext.fee.EppFeeFee;
import com.neulevel.epp.ext.fee.command.EppCommandRenewFee;
import com.neulevel.epp.ext.fee.command.EppCommandTransferFee;

public class EppCommandRenewFeeTest {

	@Test
	public void test() {
		Node epp;
		EppCommandRenewDomain cmd;
		EppCommandRenewDomain newcmd;
		Calendar cal = Calendar.getInstance();

		cmd = new EppCommandRenewDomain("example.biz", cal);
		cmd.setPeriod(new EppPeriod(1, 'y'));
		cmd.setClientTransactionId("ABC-DEF-12345");
		EppCommandRenewFee cmdRenewFee = new EppCommandRenewFee();
		cmdRenewFee.setCurrency("USD");
		EppFeeFee feeFee = new EppFeeFee();
		feeFee.setDescription("Renew Fee");
		feeFee.setFee(15.00);
		cmdRenewFee.addFee(feeFee);
		cmd.addExtension(cmdRenewFee);
		
		String rootDir = System.getProperty("user.dir");
		rootDir= rootDir.replace("\\", "//");
		EppParser parser = new EppParser(cmd.toString(),rootDir + "//xsd");
		if( parser.hasError() )
		{
			System.out.println(parser.getResult());
			System.exit(1);
		}

		epp = parser.getRootNode();
		newcmd = (EppCommandRenewDomain) EppCommand.fromXML(epp);
		if( newcmd == null )
		{
			System.out.println("Error in fromXML");
			System.exit(1);
		}
		System.out.println(newcmd);
		
		Assert.assertEquals(cmd.toString(), newcmd.toString());
	}

}
