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
import java.io.*;

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
 * This <code>EppTestExtension</code> class is used for parsing and validating a
 * chunk of extension
 *
 * @author Wil Tan william.tan@neustar.biz
 * @version $Revision: 1.2 $ $Date: 2008/04/09 21:30:08 $
 */

public class EppTestExtension
{
	static class ExtensionValidator implements ErrorHandler
	{
		public static String NS_XRD = "xri://$xrd*($v*2.0)";
		public static String NS_XRDS = "xri://$xrds";

		private int countWarnings = 0;
		private int countErrors = 0;
		private int countFatals = 0;
		private String errorMessages = "";
		private String warningMessages = "";

		/**
		 * Validates extension chunk to ensure that:
		 * 0. it is smaller than 4096 characters
		 * 1. its top level child nodes are all elements (no PI, etc.)
		 * 2. all child elements are resolvable to a namesapce URI
		 * 3. child element namespace URI must not be that of XRD's or XRDS's
		 */
		public boolean validate(String s)
		{
			// we don't validate null string
			if (s == null)
				return true;

			if (s.length() > 4096) {
				errorMessages = "extension exceeded maximum allowable size";
				return false;
			}

			if (s.trim().length() == 0)
				return true;

			try {
				// wrap input string in element
				s = "<xrd-extension>" + s + "</xrd-extension>";

				InputSource is = new InputSource(new StringReader(s));
				DOMParser parser = new DOMParser();
				// parser.setFeature("http://xml.org/sax/features/validation", true);
				parser.setFeature("http://xml.org/sax/features/namespaces", true);
				parser.setFeature("http://apache.org/xml/features/validation/schema", true);
				parser.setFeature("http://apache.org/xml/features/dom/defer-node-expansion", true);
				parser.setFeature("http://apache.org/xml/features/dom/include-ignorable-whitespace", false);
				parser.setErrorHandler(this);

				parser.parse(is);
				Document doc = parser.getDocument();
				return validateDocument(doc);
			}
			catch (Exception e) {
				System.out.println("shit...");
				e.printStackTrace();
				errorMessages = e.getMessage();
				return false;
			}
		}

		public boolean validateDocument(Document doc)
		{
			Element docElement = doc.getDocumentElement();
			NodeList nl = docElement.getChildNodes();
			int n = nl.getLength();
			for (int i = 0; i < n; i++) {
				Node node = nl.item(i);
				short nodeType = node.getNodeType();

				if (nodeType == Node.TEXT_NODE
						&& node.getNodeValue().trim().length() == 0)
				{
					// whitespace-only text nodes are ok
					continue;
				}

				// only element nodes allowed
				if (nodeType != Node.ELEMENT_NODE) {
					if (errorMessages.length() > 0)
						errorMessages += "\n";
					errorMessages += "only elements and whitespace allowed in extension";
					return false;
				}

				System.out.println("[" + i + "] <" + node.getNodeName() + ">");
				if (!validateElement((Element)node))
					return false;
			}
			return true;
		}


		private boolean validateElement(Element el)
		{
			System.out.println("validateElement entering... " + el.getNodeName());
			// make sure the namespace is given
			String ns = el.getNamespaceURI();
			if (ns == null || ns.length() == 0) {
				if (errorMessages.length() > 0)
					errorMessages += "\n";
				errorMessages += "extension elements must be resolvable to a namespace URI";
				return false;
			}

			if (ns.equalsIgnoreCase(NS_XRDS) || ns.equalsIgnoreCase(NS_XRD)) {
				if (errorMessages.length() > 0)
					errorMessages += "\n";
				errorMessages += "extension elements must not be in XRDS or XRD namespace";
				return false;
			}

			// recursively check all child elements
			NodeList nl = el.getElementsByTagName("*");
			int n = nl.getLength();
			for (int i = 0; i < n; i++) {
				Element e = (Element)nl.item(i);
				System.out.println("[" + i + "] <" + e.getNodeName() + ">");
				if (!validateElement(e))
					return false;
			}
			System.out.println("validateElement exiting... " + el.getNodeName());
			return true;
		}

		public String getWarning()
		{
			return warningMessages;
		}

		public String getError()
		{
			return errorMessages;
		}

		//
		// ErrorHandler methods
		//

		//
		// Warning
		//
		public void warning( SAXParseException ex )
		{
			if (warningMessages.length() > 0)
				warningMessages = warningMessages + "\n";
			else
				warningMessages = "";
			warningMessages += "[Warning] " + getLoc(ex) + ": " + ex.getMessage();
			this.countWarnings++;
		}

		//
		// Error
		//
		public void error( SAXParseException ex ) throws SAXException
		{
			if (errorMessages.length() > 0)
				errorMessages = errorMessages + "\n";
			errorMessages += "[Error] " + getLoc(ex) + ": "+ ex.getMessage();
			this.countErrors++;
			throw ex;
		}

		//
		// Fatal error
		//
		public void fatalError( SAXParseException ex ) throws SAXException
		{
			if (errorMessages.length() > 0)
				errorMessages = errorMessages + "\n";
			errorMessages += "[Fatal Error] " + getLoc(ex) + ": "+ ex.getMessage();
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
			return "ExtensionValidator: warnings=" + this.countWarnings
				+ " errors=" + this.countErrors + " fatals=" + this.countFatals;
		}

	}


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
			System.out.println("Usage: java EppTestExtension xmlfile");
			System.exit(0);
		}

		try {
			String s;
			StringBuffer sb = new StringBuffer();
			// open file
			FileInputStream fis = new FileInputStream(argv[0]);
			BufferedReader in = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
			// read content into StringBuffer
			while ((s = in.readLine()) != null) {
				sb.append(s);
			}

			ExtensionValidator validator = new ExtensionValidator();
			if (validator.validate(sb.toString())) {
				System.out.println("XML Validated");
			}
			else {
				System.out.println("Validation Failed: " + validator.getError());
			}
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}

}
