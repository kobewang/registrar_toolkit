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
import org.w3c.dom.*;
import com.neulevel.epp.core.*;
import com.neulevel.epp.core.command.*;
import com.neulevel.epp.core.response.*;

/**
 * This <code>EppTestXML</code> class is used for parsing and validating a
 * XML files containing EPP messages
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppTestXML
{
	/**
	 * The main method of the <code>EppTest</code> class
	 *
	 * @param argv argv is a list of the XML file names to be parsed
	 *             and validated according to EPP Schema
	 */
	public static void main( String argv[] )
	{
		EppHello    hello;
		EppGreeting greet;
		EppCommand  cmd;
		EppResponse rsp;
		boolean     output  = false;
		boolean     verify  = false;
		boolean     verbose = false;

		int loop  = 1;
		int start = 0;

		if( argv.length == 0 )
		{
			System.out.println("Usage: java EppTestXML [-loop] xmlfile ...");
			System.exit(1);
		}

		if( argv[0].equals("-output") )
		{
			output = true;
			start = 1;
		}
		else if( argv[0].equals("-verify") )
		{
			verify = true;
			start = 1;
		}
		else if( argv[0].equals("-verbose") )
		{
			verify = true;
			verbose = true;
			start = 1;
		}
		else if( argv[0].charAt(0) == '-' )
		{
			loop = - Integer.parseInt(argv[0]);
			start = 1;
		}

		EppParser parser = new EppParser();

		for( int j = 0; j < loop; j++ )
		{
			System.out.println("LOOP " + j);
			for( int i = start; i < argv.length; i++ )
			{
//				EppParser parser = new EppParser();
				parser.parseFile(argv[i]);
				if( j != 0 )
				{
					continue;
				}
				if( parser.hasError() )
				{
					System.out.println(argv[i] + " Error: "
						+ parser.getResult());
					continue;
				}
				System.out.print(argv[i] + " OK ");

				Node epp = parser.getRootNode();

				greet = (EppGreeting) EppGreeting.fromXML(epp);
				if( greet != null )
				{
					System.out.println(" Greeting");
					if( output )
					{
						System.out.println(greet.toString());
					}
					if( verify )
					{
						EppParser newParser = new EppParser(greet.toString());
						if( newParser.hasError() )
						{
							System.out.println(argv[i] + " Error: "
								+ newParser.getResult());
							continue;
						}
						Node newEpp = newParser.getRootNode();
						EppGreeting newGreeting = (EppGreeting) EppGreeting.fromXML(newEpp);
						if( verbose )
						{
							System.out.println(newGreeting.toString());
						}
					}
					continue;
				}
				hello = (EppHello) EppHello.fromXML(epp);
				if( hello != null )
				{
					System.out.println(" Hello");
					if( output )
					{
						System.out.println(hello.toString());
					}
					if( verify )
					{
						EppParser newParser = new EppParser(hello.toString());
						if( newParser.hasError() )
						{
							System.out.println(argv[i] + " Error: "
								+ newParser.getResult());
							continue;
						}
						Node newEpp = newParser.getRootNode();
						EppHello newHello = (EppHello) EppHello.fromXML(newEpp);
						if( verbose )
						{
							System.out.println(newHello.toString());
						}
					}
					continue;
				}
				cmd = (EppCommand) EppCommand.fromXML(epp);
				if( cmd != null )
				{
					System.out.println(" Command");
					if( output )
					{
						System.out.println(cmd.toString());
					}
					if( verify )
					{
						EppParser newParser = new EppParser(cmd.toString());
						if( newParser.hasError() )
						{
							System.out.println(argv[i] + " Error: "
								+ newParser.getResult());
							continue;
						}
						Node newEpp = newParser.getRootNode();
						EppCommand newCmd = (EppCommand) EppCommand.fromXML(newEpp);
						if( verbose )
						{
							System.out.println(newCmd.toString());
						}
					}
					continue;
				}
				rsp = (EppResponse) EppResponse.fromXML(epp);
				if( rsp != null )
				{
					System.out.println(" Response");
					if( output )
					{
						System.out.println(rsp.toString());
					}
					if( verify )
					{
						EppParser newParser = new EppParser(rsp.toString());
						if( newParser.hasError() )
						{
							System.out.println(argv[i] + " Error: "
								+ newParser.getResult());
							continue;
						}
						Node newEpp = newParser.getRootNode();
						EppResponse newRsp = (EppResponse) EppResponse.fromXML(newEpp);
						if( verbose )
						{
							System.out.println(newRsp.toString());
						}
					}
					continue;
				}
				System.out.println(" Unknown");
			}
		}

		System.out.println("DONE");
		System.out.flush();
		try
		{
			Thread.sleep(10000);
		}
		catch( Exception e )
		{
		}
	}
}
