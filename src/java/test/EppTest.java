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
import org.xml.sax.*;
import org.apache.xerces.parsers.*;

/* Features recognized, copied from org.apache.xerces.framework.XMLParser.java

	// SAX2 core
	"http://xml.org/sax/features/validation",
	"http://xml.org/sax/features/external-general-entities",
	"http://xml.org/sax/features/external-parameter-entities",
	"http://xml.org/sax/features/namespaces",
	// Xerces
	"http://apache.org/xml/features/validation/schema",
	"http://apache.org/xml/features/validation/dynamic",
	"http://apache.org/xml/features/validation/default-attribute-values",
	"http://apache.org/xml/features/validation/validate-content-models",
	"http://apache.org/xml/features/validation/validate-datatypes",
	"http://apache.org/xml/features/validation/warn-on-duplicate-attdef",
	"http://apache.org/xml/features/validation/warn-on-undeclared-elemdef",
	"http://apache.org/xml/features/allow-java-encodings",
	"http://apache.org/xml/features/continue-after-fatal-error",
	"http://apache.org/xml/features/nonvalidating/load-dtd-grammar",
	"http://apache.org/xml/features/nonvalidating/load-external-dtd"
	"http://apache.org/xml/features/dom/include-ignorable-whitespace"
*/

/**
 * This <code>EppTest</code> class is used for parsing and validating a
 * single XML file against XML Schema. 
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppTest implements ErrorHandler
{
	private int countWarnings = 0;
	private int countErrors = 0;
	private int countFatals = 0;

	/**
	 * The main method of the <code>EppTest</code> class
	 *
	 * @param argv argv[0] is the name of the XML file to be parsed
	 *             and validated
	 */
	public static void main( String argv[] )
	{
		if( argv.length != 1 )
		{
			System.out.println("Usage: java EppTest xmlfile");
			System.exit(0);
		}

		DOMParser parser = new DOMParser();
		EppTest test = new EppTest();

		try
		{
			parser.setFeature("http://xml.org/sax/features/validation", true);
			parser.setFeature("http://xml.org/sax/features/namespaces", true);
			parser.setFeature("http://apache.org/xml/features/validation/schema", true);
			parser.setFeature("http://apache.org/xml/features/dom/defer-node-expansion", true);
			parser.setFeature("http://apache.org/xml/features/dom/include-ignorable-whitespace", false);

			parser.setErrorHandler(test);
			parser.parse(argv[0]);
			Document doc = parser.getDocument();
		}
		catch( Exception e )
		{
//			e.printStackTrace();
		}
		System.out.println(test);
	}

	//
	// ErrorHandler methods
	//

	//
	// Warning
	//
	public void warning( SAXParseException ex )
	{
		System.out.println("[Warning] " + getLoc(ex) + ": " + ex.getMessage());
		this.countWarnings++;
	}

	//
	// Error
	//
	public void error( SAXParseException ex )
	{
		System.out.println("[Error] " + getLoc(ex) + ": "+ ex.getMessage());
		this.countErrors++;
	}

	//
	// Fatal error
	//
	public void fatalError( SAXParseException ex ) throws SAXException
	{
		System.out.println("[Fatal Error] " + getLoc(ex) + ": "+ ex.getMessage());
		this.countFatals++;
		throw ex;
	}

	private String getLoc( SAXParseException ex )
	{
		StringBuffer buf = new StringBuffer();

		String id = ex.getSystemId();
		if( id != null )
		{
			int index = id.lastIndexOf('/');
			if( index != -1 ) 
			{
				id = id.substring(index + 1);
			}
			buf.append(id);
		}
		buf.append(':');
		buf.append(ex.getLineNumber());
		buf.append(':');
		buf.append(ex.getColumnNumber());

		return buf.toString();
	}

	public String toString()
	{
		return "EppTest: warnings=" + this.countWarnings
			+ " errors=" + this.countErrors + " fatals=" + this.countFatals;
	}
}
