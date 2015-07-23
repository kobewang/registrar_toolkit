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

import org.w3c.dom.*;

/**
 * The <code>EppGreetingTest</code> class tests functions
 * of <code>EppGreeting</code> commands.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppGreetingTest
{
	public static void main( String argv[] )
	{
		System.out.println(new EppGreeting());

		EppServiceMenu menu = new EppServiceMenu();

		menu.addVersion("1.0");
		menu.addLanguage("en-US");
		menu.addService("contact");
		menu.addService("domain");
		menu.addService("host");
		menu.addService("svcsub");
		menu.addUnspec("obj1ext", "http://custom/obj1ext-1.0", "xml/obj1ext-1.0.xsd");

		EppGreeting greeting = new EppGreeting("NeuStar EPP TestServer", menu);
		System.out.println(greeting);

		EppParser parser = new EppParser(greeting.toString());
		if( parser.hasError() )
		{
			System.out.println(parser.getResult());
			System.exit(1);
		}

		Node epp = parser.getRootNode();
		EppGreeting cmd = (EppGreeting) EppGreeting.fromXML(epp);
		if( cmd == null )
		{
			System.out.println("Error in fromXML");
			System.exit(1);
		}
		System.out.println(cmd);
	}
}
