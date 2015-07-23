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
package com.neulevel.epp.core;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.TimeZone;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.xerces.dom.ElementNSImpl;
import org.apache.xerces.dom.TextImpl;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 * This <code>EppUtil</code> class implements some utility methods used
 * by various components in the EPP implementation.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.13 $ $Date: 2013/06/17 15:52:29 $
 */
public class EppUtil
{
	private static SimpleDateFormat xmlDateFormat;
	private static SimpleDateFormat xmlShortDateFormat;
	private static boolean fullNamespace = false;
	private static String version = "-1.0";

	public static String NAMESPACEEPP04 = "urn:iana:xml:ns:";
	public static String NAMESPACEEPP09 = "urn:ietf:params:xml:ns:";
	private static String namespaceEpp   = null;

	/**
	 * Property for generating tags with namespace prefixes.
	 *
	 * <P><B>Note:</B> This property is only required to be set with a value for
	 * communicating with EPP servers that expects that all object related
	 * tags, such as 
	 * <pre>
	 *        <contact:id>mycontact-id</contact:id>
	 * </pre>
	 * the contact prefix cannot be omitted as,
	 * <pre>
	 *        <id>mycontact-id</id>
	 * </pre>
 	 * even the default namespace has been specified in the XML message.
	 */
	public static final String xmlFullNamespace  = "com.neulevel.epp.core.EppUtil.fullNamespace";

	/**
	 * Property for generating tags with EPP-04 namespace prefixes.
	 */
	public static final String xmlEppNamespace = "com.neulevel.epp.core.EppUtil.eppNamespace";

	static
	{
		xmlDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'");
		xmlDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		xmlShortDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		xmlShortDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

		fullNamespace  = System.getProperty(xmlFullNamespace) != null;

		String flag = System.getProperty(xmlFullNamespace);
		namespaceEpp = NAMESPACEEPP09;
		if( flag != null )
		{
			if(    flag.equalsIgnoreCase("epp-04")
			    || flag.equalsIgnoreCase("epp04")
			    || flag.equalsIgnoreCase("04") )
			{
				namespaceEpp = NAMESPACEEPP04;
			}
			else if(    flag.equalsIgnoreCase("epp-09")
				 || flag.equalsIgnoreCase("epp09")
				 || flag.equalsIgnoreCase("09")
				 || flag.equalsIgnoreCase("epp-1.0")
				 || flag.equalsIgnoreCase("epp1.0")
				 || flag.equalsIgnoreCase("epp1")
				 || flag.equalsIgnoreCase("1.0")
				 || flag.equalsIgnoreCase("1") )
			{
				namespaceEpp = NAMESPACEEPP09;
			}
		}
	}

	/**
	 * Sets the EPP XSD file version. The default value is "1.0",
	 * for EPP Schema dated on June 29, 2001.
	 */
	public static void setEppVersion( String eppVersion )
	{
		if( eppVersion == null )
		{
			version = "";
		}
		else if( (eppVersion.length() == 0) || (eppVersion.charAt(0) == '-') )
		{
			version = eppVersion;
		}
		else
		{
			version = "-" + eppVersion;
		}
	}

	/**
	 * Gets the EPP XSD file version.
	 */
	public static String getEppVersion()
	{
		if( version.length() == 0 )
		{
			return version;
		}
		return version.substring(1);
	}

	/**
	 * Creates an <code>ElementNSImpl</code> object inside a
	 * <code>Document</code> object. The namespace associated with the
	 * object is specific to EPP.
	 *
	 * @param doc <code>Document</code> object in which the new element
	 *            to be created.
	 * @param ns  The namespace of the new element to be created.
	 * @param tag The tag name of the new element to be created.
	 *
	 * @return an <code>ElementNSImpl</code> object
	 */
	public static ElementNSImpl createElementNS( Document doc, String ns, String tag )
	{
		return createElementNS(doc, ns, tag, true);
	}

	/**
	 * Creates an <code>ElementNSImpl</code> object inside a
	 * <code>Document</code> object. The namespace associated with the
	 * object is specific to EPP.
	 *
	 * @param doc <code>Document</code> object in which the new element to be
	 *            created.
	 * @param ns  The namespace of the new element to be created.
	 * @param tag The tag name of the new element to be created.
	 * @param flag The flag indicating if the default namespace is needed.
	 *
	 * @return an <code>ElementNSImpl</code> object
	 */
	public static ElementNSImpl createElementNS( Document doc, String ns, String tag, boolean flag )
	{
		ElementNSImpl elm;
		if( tag == null )
		{
			elm = (ElementNSImpl) doc.createElementNS(namespaceEpp + ns + version, ns);
			elm.setAttribute("xmlns", namespaceEpp + ns + version);
			if( version.length() == 0 )
			{
				// old EPP-02 schema
				elm.setAttribute("xmlns:xsi", "http://www.w3.org/2000/10/XMLSchema-instance");
			}
			else
			{
				elm.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
			}
		}
		else
		{
			elm = (ElementNSImpl) doc.createElementNS(namespaceEpp + ns + version, tag);
			elm.setPrefix(ns);
			if( flag )
			{
				elm.setAttribute("xmlns", namespaceEpp + ns + version);
			}
			elm.setAttribute("xmlns:" + ns, namespaceEpp + ns + version);
		}
		elm.setAttribute("xsi:schemaLocation", namespaceEpp + ns + version + " " + ns + version + ".xsd");
		return elm;
	}
	/**
	 * Creates an <code>ElementNSImpl</code> object inside a
	 * <code>Document</code> object. The namespace associated with the
	 * object is specific to EPP.
	 *
	 * @param doc <code>Document</code> object in which the new element to be
	 *            created.
	 * @param ns  The namespace of the new element to be created.
	 * @param tag The tag name of the new element to be created.
	 * @param flag The flag indicating if the default namespace is needed.
	 *
	 * @return an <code>ElementNSImpl</code> object
	 * Ex:
	 * <smd:encodedSignedMark xmlns="urn:ietf:params:xml:ns:signedMark-1.0" xmlns:smd="urn:ietf:params:xml:ns:signedMark-1.0" xsi:schemaLocation="urn:ietf:params:xml:ns:signedMark-1.0 signedMark-1.0.xsd">
	 * ns=smd,tag=encodedSignedMark,flag=true,xsdNS=signedMark
	 */
	public static ElementNSImpl createElementNS2( Document doc, String ns, String tag, boolean flag , String xsdNS)
	{
		ElementNSImpl elm;
		if( tag == null )
		{
			elm = (ElementNSImpl) doc.createElementNS(namespaceEpp + xsdNS + version, ns);
			elm.setAttribute("xmlns", namespaceEpp + xsdNS + version);
			if( version.length() == 0 )
			{
				// old EPP-02 schema
				elm.setAttribute("xmlns:xsi", "http://www.w3.org/2000/10/XMLSchema-instance");
			}
			else
			{
				elm.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
			}
		}
		else
		{
			elm = (ElementNSImpl) doc.createElementNS(namespaceEpp + xsdNS + version, tag);
			elm.setPrefix(ns);
			if( flag )
			{
				elm.setAttribute("xmlns", namespaceEpp + xsdNS + version);
			}
			elm.setAttribute("xmlns:" + ns, namespaceEpp + xsdNS + version);
		}
		elm.setAttribute("xsi:schemaLocation", namespaceEpp + xsdNS + version + " " + xsdNS + version + ".xsd");
		return elm;
	}
	/**
	 * Creates an <code>ElementNSImpl</code> object inside a
	 * <code>Document</code> object. The namespace associated with the
	 * object is specific to EPP.
	 *
	 * @param doc <code>Document</code> object in which the new element to be
	 *            created.
	 * @param ns  The namespace of the new element to be created.
	 * @param prefix The prefix attached if 'null' then uses 'ns'.
	 * @param tag The tag name of the new element to be created.
	 * @param flag The flag indicating if the default namespace is needed.
	 *
	 * @return an <code>ElementNSImpl</code> object
	 */

	public static ElementNSImpl createElementNS( Document doc, String ns, String prefix, String tag, boolean flag )
	{
		ElementNSImpl elm;
		if( tag == null )
		{
			elm = (ElementNSImpl) doc.createElementNS(namespaceEpp + ns + version, ns);
			elm.setAttribute("xmlns", namespaceEpp + ns + version);
			if( version.length() == 0 )
			{
				// old EPP-02 schema
				elm.setAttribute("xmlns:xsi", "http://www.w3.org/2000/10/XMLSchema-instance");
			}
			else
			{
				elm.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
			}
		}
		else
		{
			elm = (ElementNSImpl) doc.createElementNS(namespaceEpp + ns + version, tag);
			if( null != prefix ) {
				elm.setPrefix(prefix);
			} else {
				elm.setPrefix(ns);
			}
			if( flag )
			{
				elm.setAttribute("xmlns", namespaceEpp + ns + version);
			}
			elm.setAttribute("xmlns:" + ns, namespaceEpp + ns + version);
		}
		elm.setAttribute("xsi:schemaLocation", namespaceEpp + ns + version + " " + ns + version + ".xsd");
		return elm;
	}

	/**
	 * Creates an <code>ElementNSImpl</code> object inside a
	 * <code>Document</code> object. The namespace associated with the
	 * object is specific to EPP.
	 *
	 * @param doc <code>Document</code> object in which the new element to be
	 *            created.
	 * @param ns  The namespace of the new element to be created.
	 * @param tag The tag name of the new element to be created.
	 * @param flag The flag indicating if the default namespace is needed.
	 * @param version The Version indicate the version of namespace used.
	 *
	 * @return an <code>ElementNSImpl</code> object
	 */
	public static ElementNSImpl createElementNS( Document doc, String ns, String tag, boolean flag,String _version )
	{
		ElementNSImpl elm;
		String use_version = (null == _version)?version:_version;

		if( tag == null )
		{
			elm = (ElementNSImpl) doc.createElementNS(namespaceEpp + ns + use_version, ns);
			elm.setAttribute("xmlns", namespaceEpp + ns + use_version);
			if( version.length() == 0 )
			{
				// old EPP-02 schema
				elm.setAttribute("xmlns:xsi", "http://www.w3.org/2000/10/XMLSchema-instance");
			}
			else
			{
				elm.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
			}
		}
		else
		{
			elm = (ElementNSImpl) doc.createElementNS(namespaceEpp + ns + use_version, tag);
			elm.setPrefix(ns);
			if( flag )
			{
				elm.setAttribute("xmlns", namespaceEpp + ns + use_version);
			}
			elm.setAttribute("xmlns:" + ns, namespaceEpp + ns + use_version);
		}
		elm.setAttribute("xsi:schemaLocation", namespaceEpp + ns + use_version + " " + ns + use_version + ".xsd");
		return elm;
	}


	/**
	 * Creates an <code>ElementNSImpl</code> object inside a
	 * <code>Document</code> object. The namespace associated with the
	 * object is specific to an <I>unspec</I> object in an
	 * <code>EppServiceMenu</code> object.
	 *
	 * @param doc <code>Document</code> object in which the new element to be
	 *            created.
	 * @param unspec The string containing the information about the
	 *        <I>unspec</I> object with three tokens: name, uri, schema. For
	 *        example: "obj1ext http://custom/obj1ext-1.0 obj1ext-1.0.xsd"
	 * @param tag The tag name of the new element to be created.
	 * @param flag The flag indicating if the default namespace is needed.
	 *
	 * @return an <code>ElementNSImpl</code> object, or null if the
	 *         <code>unspec</code> parameter is not valid.
	 */
	public static ElementNSImpl createElementNSUnspec( Document doc, String unspec, String tag, boolean flag )
	{
		StringTokenizer tok = new StringTokenizer(unspec);
		if( tok.countTokens() != 3 )
		{
			return null;
		}
		String ns  = tok.nextToken();
		String uri = tok.nextToken();
		String loc = uri + " " + tok.nextToken();

		ElementNSImpl elm;
		if( tag == null )
		{
			elm = (ElementNSImpl) doc.createElementNS(uri, ns);
			elm.setAttribute("xmlns", uri);
			if( version.length() == 0 )
			{
				// old EPP-02 schema
				elm.setAttribute("xmlns:xsi", "http://www.w3.org/2000/10/XMLSchema-instance");
			}
			else
			{
				elm.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
			}
		}
		else
		{
			elm = (ElementNSImpl) doc.createElementNS(uri, tag);
			elm.setPrefix(ns);
			if( flag )
			{
				elm.setAttribute("xmlns", uri);
			}
			elm.setAttribute("xmlns:" + ns, uri);
		}
		elm.setAttribute("xsi:schemaLocation", loc);
		return elm;
	}

	/**
	 * Creates a <code>Text</code> node for converting a
	 * <code>Calendar</code> object into a string of XML timeInstant type.
	 *
	 * @param doc <code>Document</code> object in which the new element
	 *            to be
	 *            created.
	 * @param cal The <code>Calendar</code> to be converted into a XML
	 *            <code>Text</code> node.
	 */
	public static Text createTextNode( Document doc, Calendar cal )
	{
		return createTextNode(doc, cal, false);
	}

	/**
	 * Creates a <code>Text</code> node for converting a
	 * <code>Calendar</code> object into a string of XML timeInstant
	 * type or XML date type.
	 *
	 * @param doc <code>Document</code> object in which the new element
	 *            to be created.
	 * @param cal The <code>Calendar</code> to be converted into a XML
	 *            <code>Text</code> node.
	 * @param dateOnly true if the format is XML date type, or false
	 *                 if the format is XML timeInstant type
	 */
	synchronized public static Text createTextNode( Document doc, Calendar cal, boolean dateOnly )
	{
		if( dateOnly )
		{
			return doc.createTextNode(xmlShortDateFormat.format(cal.getTime()));
		}
		else
		{
			return doc.createTextNode(xmlDateFormat.format(cal.getTime()));
		}
	}

	/**
	 * Gets the text string associated with a node
	 *
	 * @param root <code>Node</code> object from which text to be extracted.
	 *
	 * @return a string associated with the node
	 */
	public static String getText( Node root )
	{
		NodeList list = root.getChildNodes();
		if( (list == null) || (list.getLength() == 0) )
		{
			String str = root.getNodeValue();
			if( str == null )
			{
				str = "";
			}
			return str;
		}
		StringBuffer buf = new StringBuffer();
		for( int i = 0; i < list.getLength(); i++ )
		{
			Node node = list.item(i);
			if( node instanceof TextImpl )
			{
				String val = ((TextImpl) node).getNodeValue();
				if( (val != null) && (val.length() > 0) )
				{
					buf.append(val);
				}
			}
		}
		return buf.toString();
	}

	/**
	 * Interprets the node's text string as boolean type.
	 * Understands "true" (any case), "1" as TRUE.
	 * Any other value is considered FALSE.
	 * 
	 *
	 * @param root <code>Node</code> object from which text to be extracted.
	 *
	 * @return the boolean value of the node's text.
	 */
	public static boolean getTextAsBool( Node root )
	{
		String s = getText(root);
		if (s != null)
		{
			s = s.trim();
			if (s.equalsIgnoreCase("true") || s.equals("1"))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Converts a text string associated with a node into a
	 * <code>Calendar</code> object
	 *
	 * @param root <code>Node</code> object from which text to be extracted.
	 *
	 * @return a <code>Calendar</code> object, or null if the text string is
	 *         not a valid XML timeInstant string
	 */
	public static Calendar getDate( Node root )
	{
		return getDate(root, false);
	}

	/**
	 * Converts a text string associated with a node into a
	 * <code>Calendar</code> object
	 *
	 * @param root <code>Node</code> object from which text to be extracted.
	 * @param dateOnly true for parsing the string in XML date format, or
	 *                 false for parsing the string in XML timeInstant
	 *                 format
	 *
	 * @return a <code>Calendar</code> object, or null if the text string is
	 *         not a valid XML timeInstant/date string
	 */
	synchronized public static Calendar getDate( Node root, boolean dateOnly )
	{
		Calendar cal = null;

		String s = EppUtil.getText(root);
		if( (s != null) && (s.length() > 0) )
		{
			ParsePosition ps = new ParsePosition(0);
			Date date = null;
			if( dateOnly )
			{
				date = xmlShortDateFormat.parse(s, ps);
			}
			else
			{
				date = xmlDateFormat.parse(s, ps);
			}
			if( date != null )
			{
				cal = Calendar.getInstance();
				cal.setTime(date);
			}
		}

		return cal;
	}
	public static String [] getDnsSecMajorMinor(Node root)
	{
		String value = null;
		if( null == root )
			return null;
		value =  ((Element)root).getAttribute("xmlns:secDNS");
		if( null == value )
			return null;
		return EppUtil.getDnsSecMajorMinor(value);
	}
/*
	public static void getDnsSecMajorMinor(String root, String _major, String _minor)
	{
		if( null == root )
			return;
		try {
			String ns_n_version [] = root.split("-",2);
			if( ns_n_version.length <= 1  )
				return;
			String version = ns_n_version[1];
			String maj_n_min [] = version.split("\\.");
			if( maj_n_min.length <= 1 )
				return;
			_major = maj_n_min[0];
			_minor = maj_n_min[1];
		} catch(Exception e) {
			return;
		}
	}
*/
	public static String[] getDnsSecMajorMinor(String root)
	{
		if( null == root )
			return null;
		try {
			String ns_n_version [] = root.split("-",2);
			if( ns_n_version.length <= 1  )
				return null;
			String version = ns_n_version[1];
			String maj_n_min [] = version.split("\\.");
			if( maj_n_min.length <= 1 )
				return null;
			return maj_n_min;
		} catch(Exception e) {
			return null;
		}
	}


	/**
	 * Converts a <code>Document</code> into a formated XML string.
	 *
	 * @param doc <code>Document</code> to be converted into a string.
	 *
	 * @return a string representing the formated XML document
	 */
	public static String toString( Document doc )
	{
		StringWriter str = new StringWriter();

		try
		{
			if( fullNamespace )
			{
				EppWriter out = new EppWriter(new PrintWriter(str));
				out.print(doc);
			}
			else
			{
				OutputFormat out = new OutputFormat(doc);
				out.setIndenting(true);
				out.setIndent(1);
				out.setStandalone(false);
				out.setEncoding("UTF-8");
				out.setLineWidth(0);

				XMLSerializer ser = new XMLSerializer(str, out);
				ser.serialize(doc.getDocumentElement());
				
			}
		}
		catch( Exception e )
		{
			return null;
		}

		return str.toString();
	}
	/**
	 * Converts a <code>Document</code> into a formated XML string.
	 *
	 * @param doc <code>Document</code> to be converted into a string.
	 *
	 * @return a string representing the formated XML document
	 */
	public static String toStringNoFormatting( Document doc , String tagName)
	{
//		StringWriter str = new StringWriter();
//
//		try
//		{
//				OutputFormat out = new OutputFormat(doc);
//				out.setIndenting(true);
//				out.setIndent(1);
//				out.setStandalone(false);
//				out.setEncoding("UTF-8");
//				//out.setLineWidth(0);
////				String[] cDataElements = {"smd:signedMark"};
////				out.setCDataElements(cDataElements);
//				out.setLineSeparator("\n");
//
//				XMLSerializer ser = new XMLSerializer(str, out);
//				if(null != tagName) {
//					ser.serialize(doc.getElementById(tagName));
//				}
//				else {
//					ser.serialize(doc);
//				}
//		}
//		catch( Exception e )
//		{
//			return null;
//		}
//		return str.toString();
		
		StringWriter writer = new StringWriter();
		OutputFormat format = new OutputFormat("XML", "UTF-8", true);
		format.setIndenting(false);
		format.setPreserveSpace(true);	
		XMLSerializer serializer = new XMLSerializer(writer, format);
		try{
		serializer.asDOMSerializer();
		serializer.serialize(doc);
		writer.close();
		} catch(IOException ioEx){
			ioEx.printStackTrace();
		} finally {
		
		}
		

		String str = writer.getBuffer().toString();

		
		return(str);
		
//		//this would enforce the DOM implementation to be Xerces
//		System.setProperty("javax.xml.parsers.DocumentBuilderFactory","org.apache.xerces.jaxp.DocumentBuilderFactoryImpl");
//		//this would enforce the SAX implementation to be Xerces
//		System.setProperty("javax.xml.parsers.SAXParserFactory","org.apache.xerces.jaxp.SAXParserFactoryImpl");
//		//this would enforce the XSLT implementation to be Xalan
//		System.setProperty("javax.xml.transform.TransformerFactory","org.apache.xalan.processor.TransformerFactoryImpl");
//		
//
//		OutputStream os = new ByteArrayOutputStream(1000);
//		Transformer trans = null;;
//		try {
//			trans = TransformerFactory.newInstance().newTransformer();
//			trans.setOutputProperty("{http://xml.apache.org/xalan}line-separator","\n");			
//		} catch (TransformerConfigurationException
//				| TransformerFactoryConfigurationError e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			trans.transform(new DOMSource(doc), new StreamResult(os));
//		} catch (TransformerException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return os.toString();
	}

}

/**
 * This <code>EppWriter</code> class is specifically designed to add
 * name space prefix string inside object specific elements for fixing
 * the problem in Tucows' EPP Toolkit that it looks for hard-coded tag
 * names like "contact:roid", which is bad programming practice.
 */
class EppWriter
{
	private boolean     canonical;
	private PrintWriter out;
	private String      encoding = "UTF-8";

	/*
	 * Creates a <code>EppWriter</code> object
	 */
	public EppWriter( PrintWriter out )
	{
		this(out, false);
	}

	/*
	 * Creates a <code>EppWriter</code> object, with a cannonical flag
	 */
	public EppWriter( PrintWriter out, boolean canonical )
	{
		this.out = out;
		this.canonical = canonical;
	}

	/*
	 * Prints the specified node, recursively
	 */
	public void print( Node node )
	{
		print(node, 0, null);
	}

	/*
	 * Prints the specified node, recursively, given an indent level
	 * and a default prefix string.
	 */
	public void print( Node node, int level, String prefix )
	{
		if( node == null )
		{
			return;
		}

		NodeList list = null;
		int type = node.getNodeType();
		switch( type )
		{
		case Node.DOCUMENT_NODE:
			if( ! canonical )
			{
				out.println("<?xml version=\"1.0\" encoding=\"" 
					+ encoding + "\"?>");
			}
			list = node.getChildNodes();
			if( list != null )
			{
				for( int i = 0; i < list.getLength(); i++ )
				{
					print(list.item(i), level, prefix);
				}
			}
			break;

		case Node.ELEMENT_NODE:
			for( int i = 0; i < level; i++ )
			{
				out.print(' ');
			}
			out.print('<');
			String newPrefix = node.getPrefix();
			if( newPrefix != null )
			{
				prefix = newPrefix;
			}
			if( (newPrefix == null) && (prefix != null) )
			{
				out.print(prefix);
				out.print(':');
			}
			out.print(node.getNodeName());
			Attr attrs[] = sortAttributes(node.getAttributes());
			if( attrs != null )
			{
				for ( int i = 0; i < attrs.length; i++ )
				{
					Attr attr = attrs[i];
					out.print(' ');
					out.print(attr.getNodeName());
					out.print("=\"");
					out.print(normalize(attr.getNodeValue()));
					out.print('"');
				}
			}
			list = node.getChildNodes(); 
			if( list == null )
			{
				out.println("/>");
			}
			else
			{
				boolean cr = false;
				out.print('>');
				for( int i = 0; i < list.getLength(); i++ )
				{
					if( i == 0 )
					{
						int c = list.item(0).getNodeType();
						if( c != Node.TEXT_NODE )
						{
							out.println();
							cr = true;
						}
					}
					print(list.item(i), level + 1, prefix);
				}
				if( cr )
				{
					for( int i = 0; i < level; i++ )
					{
						out.print(' ');
					}
				}
				out.print('<');
				out.print('/');
				if( (newPrefix == null) && (prefix != null) )
				{
					out.print(prefix);
					out.print(':');
				}
				out.print(node.getNodeName());
				out.println('>');
			}
			break;

		case Node.ENTITY_REFERENCE_NODE:
			if( canonical )
			{
				list = node.getChildNodes(); 
				if( list != null )
				{
					for( int i = 0; i < list.getLength(); i++ )
					{
						print(list.item(i), level, prefix);
					}
				}
			}
			else
			{
				out.print('&');
				out.print(node.getNodeName());
				out.print(';');
			}
			break;

		case Node.CDATA_SECTION_NODE:
			if( canonical )
			{
				out.print(normalize(node.getNodeValue()));
			}
			else
			{
				out.print("<![CDATA[");
				out.print(node.getNodeValue());
				out.print("]]>");
			}
			break;

		case Node.TEXT_NODE:
			out.print(normalize(node.getNodeValue()));
			break;

		case Node.PROCESSING_INSTRUCTION_NODE:
			out.print("<?");
			out.print(node.getNodeName());
			String data = node.getNodeValue();
			if ( data != null && data.length() > 0 )
			{
				out.print(' ');
				out.print(data);
			}
			out.println("?>");
			break;

		default:
			break;
		}

		out.flush();
	}

	/**
	 * Sorts a list of attributes in alphabetic order
	 * based on the attribute names.
	 */
	protected Attr[] sortAttributes( NamedNodeMap attrs )
	{
		if( attrs == null )
		{
			return null;
		}
		int len = attrs.getLength();
		if( len == 0 )
		{
			return null;
		}
		Attr array[] = new Attr[len];
		for ( int i = 0; i < len; i++ )
		{
			array[i] = (Attr) attrs.item(i);
		}
		for ( int i = 0; i < len - 1; i++ )
		{
			String name = array[i].getNodeName();
			int index = i;
			for ( int j = i + 1; j < len; j++ )
			{
				String curName = array[j].getNodeName();
				if ( curName.compareTo(name) < 0 )
				{
					name = curName;
					index = j;
				}
			}
			if ( index != i )
			{
				Attr temp    = array[i];
				array[i]     = array[index];
				array[index] = temp;
			}
		}

		return array;
	}

	/**
	 * Normalizes a string by escaping special characters such as
	 * ';&lt;', '&gt;', '&amp;', '&quot;', '\r', '\n'
	 */
	protected String normalize( String s )
	{
		StringBuffer str = new StringBuffer();

		int len = (s != null) ? s.length() : 0;
		for ( int i = 0; i < len; i++ )
		{
			char ch = s.charAt(i);
			switch( ch )
			{
			case '<':
				str.append("&lt;");
				break;
			case '>':
				str.append("&gt;");
				break;
			case '&':
				str.append("&amp;");
				break;
			case '"':
				str.append("&quot;");
				break;
			case '\r':
			case '\n':
				if( canonical )
				{
					str.append("&#");
					str.append(Integer.toString(ch));
					str.append(';');
					break;
				}
				// else, default append char
			default:
				str.append(ch);
				break;
			}
		}

		return str.toString();
	}
}
