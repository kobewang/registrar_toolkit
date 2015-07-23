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
package com.neulevel.epp.ext;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.Provider;
import java.util.Random;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.neulevel.epp.core.EppEntity;
import com.neulevel.epp.core.EppUtil;

public class EppEncodedSignedMarkData extends EppEntity {
	public static final String PROVIDER_JSR105 = "org.jcp.xml.dsig.internal.dom.XMLDSigRI";

	private static DocumentBuilderFactory documentBuilderFactory = null;
	private static DocumentBuilder documentBuilder = null;
	private static TransformerFactory transformerFactory = null;
	private static Transformer transformer = null;
	private static XMLSignatureFactory signatureFactory = null;
	private static KeyInfoFactory keyInfoFactory = null;
	static {

		try {

			documentBuilderFactory = DocumentBuilderFactory.newInstance();
			documentBuilderFactory.setNamespaceAware(true);
			documentBuilderFactory.setValidating(false);
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			transformerFactory = TransformerFactory.newInstance();
			transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			transformer.setOutputProperty(OutputKeys.INDENT, "no");
			signatureFactory = XMLSignatureFactory.getInstance(
					"DOM",
					(Provider) Class.forName(System.getProperty("jsr105Provider", PROVIDER_JSR105)).newInstance());
			keyInfoFactory = signatureFactory.getKeyInfoFactory();

			//random = new Random();
		} catch (Exception ex) {

			throw new RuntimeException("Cannot initialize XML.", ex);
		}
	}
	public EppEncodedSignedMarkData()
	{
		this._rawXml = "";
		this._hasElement = false;
	}

	public void rawXml(final String _input)
	{
		this._rawXml = _input;
		this._hasElement = true;/*assuming user of this interface knows what he/she is doing*/
	}

	public String rawXml()
	{
		return this._rawXml;
	}

	public boolean hasSMD()
	{
		return this._hasElement;
	}
	public void hasSMD(boolean _f)
	{
		this._hasElement = _f;
	}
	public Element toXML(Document doc, final String tag)
	{
		//Element body = EppUtil.createElementNS(doc, "signedMark","smd", (tag == null) ? "signedMark" : tag,false); //createElementNS(doc, "smd", tag,false,"-1.0");
		Element body = EppUtil.createElementNS2(doc, "smd","encodedSignedMark",true,"signedMark");
		
		
		if( true == this._hasElement ) {
			
//			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//			dbFactory.setNamespaceAware( true);
//			DocumentBuilder builder = null;
//			try {
//				builder = dbFactory.newDocumentBuilder();
//			} catch (ParserConfigurationException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			//Read the input XML file and put it in the Document
			Document smdDoc = null;
			try {
				smdDoc = documentBuilder.parse(new InputSource(new ByteArrayInputStream(this.rawXml().getBytes("utf-8"))) );
				Element _rw = smdDoc.getDocumentElement();
				Node n = doc.importNode(_rw, true);
				return (Element)n;
			} catch (SAXException ex1) {
				// TODO Auto-generated catch block
				ex1.printStackTrace();
			} catch (IOException ex2){
				ex2.printStackTrace();
			}
			
//			EppParser parser = new EppParser(this.rawXml(),"./");
//			if( !parser.hasError() ) {
//				Element _rw = parser.getParser().getDocument().getDocumentElement();
//				if( null == _rw ) {
//					return body;
//				}
//				Node n = doc.importNode( _rw, true );
//				return (Element)n;
//			}
//			else {
//				System.out.println(parser.getErrorMessage());
//			}
		}
		return body;
	}

	public static EppEntity fromXML( final Node root )
	{
		EppEncodedSignedMarkData _ret = new EppEncodedSignedMarkData();
		String name = root.getNodeName();
		if(name == null){
			name = root.getLocalName();
		}
		if((name != null ) && name.startsWith("smd:")){
			name = name.substring(4);
		}
		if(name.equals("encodedSignedMark")){
			_ret.rawXml(EppUtil.toStringNoFormatting(root.getOwnerDocument(), "encodedSignedMark"));
		}
		
//		NodeList list = root.getChildNodes();
//		for( int i = 0; i < list.getLength(); i++ ) {
//			Node node = list.item(i);
//			String name = node.getLocalName();
//			if( name == null ) {
//				continue;
//			}
//			if( name.equals("id") ) {
//				_ret._hasElement = true;
//				break;
//			} 
//		}
		return _ret;
	}
	public String toString()
	{
		return toString("smd:encodedSignedMark",true);
		//return _rawXml;
	}


	private String _rawXml;
	private boolean _hasElement;
	private javax.xml.crypto.dsig.XMLSignature sig;
};
