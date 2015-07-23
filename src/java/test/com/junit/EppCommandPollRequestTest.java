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

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Node;

import com.neulevel.epp.core.EppParser;
import com.neulevel.epp.core.command.EppCommand;
import com.neulevel.epp.core.command.EppCommandPoll;

/**
 * @author vmehra
 *
 */
public class EppCommandPollRequestTest 
{

	/**
	 * Test method for generating Poll request with default constructor
	 */
	@Test
	public void testPollRequest() {
		Node epp;
		EppCommandPoll cmd;
		EppCommandPoll newcmd;

		cmd = new EppCommandPoll();
		cmd.setClientTransactionId("ABC-DEF-12345");
		System.out.println(cmd);

		String rootDir = System.getProperty("user.dir");
		rootDir= rootDir.replace("\\", "//");
		EppParser parser = new EppParser(cmd.toString(),rootDir + "//xsd");

		if( parser.hasError() )
		{
			System.out.println(parser.getResult());
			System.exit(1);
		}

		epp = parser.getRootNode();
		newcmd = (EppCommandPoll) EppCommand.fromXML(epp);
		if( newcmd == null )
		{
			System.out.println("Error in fromXML");
			System.exit(1);
		}
		System.out.println(newcmd);
		
		Assert.assertEquals(cmd.toString(), newcmd.toString());
	}


	/**
	 * Test method for generating Poll request with "req" parameter
	 */
	@Test
	public void testPollRequestWithReqParam() {
		Node epp;
		EppCommandPoll cmd;
		EppCommandPoll newcmd;

		cmd = new EppCommandPoll();
		cmd.setOperation("req");
		cmd.setMessageId("12345");
		cmd.setClientTransactionId("ABC-DEF-12345");
		System.out.println(cmd);

		String rootDir = System.getProperty("user.dir");
		rootDir= rootDir.replace("\\", "//");
		EppParser parser = new EppParser(cmd.toString(),rootDir + "//xsd");
		if( parser.hasError() )
		{
			System.out.println(parser.getResult());
			System.exit(1);
		}

		epp = parser.getRootNode();
		newcmd = (EppCommandPoll) EppCommand.fromXML(epp);
		if( newcmd == null )
		{
			System.out.println("Error in fromXML");
			System.exit(1);
		}
		System.out.println(newcmd);
		
		Assert.assertEquals(cmd.toString(), newcmd.toString());
	}

	/**
	 * Test method for generating Poll request with ALL parameter  (opration=req, messageid=12345, xid=ABC-DEF-12345) 
	 */
	@Test
	public void testPollRequestWithAllParam() {
		Node epp;
		EppCommandPoll cmd;
		EppCommandPoll newcmd;

		cmd = new EppCommandPoll("req","12345","ABC-DEF-12345");
		System.out.println(cmd);

		String rootDir = System.getProperty("user.dir");
		rootDir= rootDir.replace("\\", "//");
		EppParser parser = new EppParser(cmd.toString(),rootDir + "//xsd");
		if( parser.hasError() )
		{
			System.out.println(parser.getResult());
			System.exit(1);
		}

		epp = parser.getRootNode();
		newcmd = (EppCommandPoll) EppCommand.fromXML(epp);
		if( newcmd == null )
		{
			System.out.println("Error in fromXML");
			System.exit(1);
		}
		System.out.println(newcmd);
		
		Assert.assertEquals(cmd.toString(), newcmd.toString());
	}

	/**
	 * Test method for generating Poll ACK request
	 */
	@Test
	public void testPollAckRequest() 
	{
		Node epp;
		EppCommandPoll cmd;
		EppCommandPoll newcmd;

		cmd = new EppCommandPoll();
		cmd.setOperation("ack");
		cmd.setMessageId("12345");
		cmd.setClientTransactionId("ABC-DEF-12345");
		System.out.println(cmd);

		String rootDir = System.getProperty("user.dir");
		rootDir= rootDir.replace("\\", "//");
		EppParser parser = new EppParser(cmd.toString(),rootDir + "//xsd");
		if( parser.hasError() )
		{
			System.out.println(parser.getResult());
			System.exit(1);
		}

		epp = parser.getRootNode();
		newcmd = (EppCommandPoll) EppCommand.fromXML(epp);
		if( newcmd == null )
		{
			System.out.println("Error in fromXML");
			System.exit(1);
		}
		System.out.println(newcmd);
		
		Assert.assertEquals(cmd.toString(), newcmd.toString());
	}
	
	/**
	 * Test method for generating Poll Ack request with ALL parameter  (opration=req, messageid=12345, xid=ABC-DEF-12345) 
	 */
	@Test
	public void testPollAckRequestWithAllParams() {
		Node epp;
		EppCommandPoll cmd;
		EppCommandPoll newcmd;

		cmd = new EppCommandPoll("ack","12345","ABC-DEF-12345");
		System.out.println(cmd);

		String rootDir = System.getProperty("user.dir");
		rootDir= rootDir.replace("\\", "//");
		EppParser parser = new EppParser(cmd.toString(),rootDir + "//xsd");
		if( parser.hasError() )
		{
			System.out.println(parser.getResult());
			System.exit(1);
		}

		epp = parser.getRootNode();
		newcmd = (EppCommandPoll) EppCommand.fromXML(epp);
		if( newcmd == null )
		{
			System.out.println("Error in fromXML");
			System.exit(1);
		}
		System.out.println(newcmd);
		
		Assert.assertEquals(cmd.toString(), newcmd.toString());
	}
	
	/**
	 * Test method for generating Poll Ack request with ALL parameters  (opration=req, messageid=12345, xid=ABC-DEF-12345) 
	 */
	@Test
	public void testPollAckRequestWithSetAllParams() {
		Node epp;
		EppCommandPoll cmd;
		EppCommandPoll newcmd;

		cmd = new EppCommandPoll();
		cmd.setOperation("ack");
		cmd.setMessageId("12345");
		cmd.setClientTransactionId("ABC-DEF-12345");
		
		System.out.println(cmd);

		String rootDir = System.getProperty("user.dir");
		rootDir= rootDir.replace("\\", "//");
		EppParser parser = new EppParser(cmd.toString(),rootDir + "//xsd");
		if( parser.hasError() )
		{
			System.out.println(parser.getResult());
			System.exit(1);
		}

		epp = parser.getRootNode();
		newcmd = (EppCommandPoll) EppCommand.fromXML(epp);
		if( newcmd == null )
		{
			System.out.println("Error in fromXML");
			System.exit(1);
		}
		System.out.println(newcmd);
		
		Assert.assertEquals(cmd.toString(), newcmd.toString());
	}
	
	/**
	 * Negative Test Case With Unexpected Request Params 
	 */
	@Test
	public void testPollRequestWithUnexpectedParams() {
		EppCommandPoll cmd;

		cmd = new EppCommandPoll();
		cmd.setOperation("blah");
		cmd.setMessageId("12345");
		cmd.setClientTransactionId("ABC-DEF-12345");
		
		System.out.println(cmd);

		String rootDir = System.getProperty("user.dir");
		rootDir= rootDir.replace("\\", "//");
		EppParser parser = new EppParser(cmd.toString(),rootDir + "//xsd");
		if( parser.hasError() )
		{
			System.out.println(parser.getResult());
			Assert.assertTrue(true);
		}
	}
	
}
