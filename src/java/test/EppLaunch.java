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
import com.neulevel.epp.ext.*;
import com.neulevel.epp.ext.command.*;
import com.neulevel.epp.ext.response.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.Provider;
import java.util.*;

import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;

import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class EppLaunch {
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
	
//	public static String _smd = "<smd:signedMark xmlns:smd=\"urn:ietf:params:xml:ns:signedMark-1.0\" xmlns:signedMark=\"urn:ietf:params:xml:ns:signedMark-1.0\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:signedMark-1.0 signedMark-1.0.xsd\">"+
	public static String _smd = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
	//"<smd:signedMark xmlns:smd=\"urn:ietf:params:xml:ns:signedMark-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:signedMark-1.0 signedMark-1.0.xsd\" id=\"signedMark\">"+
	//"<smd:signedMark id=\"signedMark\" xmlns:smd=\"urn:ietf:params:xml:ns:smd-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:smd-1.0 smd-1.0.xsd\"/>" +
	"<smd:signedMark xmlns:smd=\"urn:ietf:params:xml:ns:signedMark-1.0\" id=\"signedMark\">" +
		"<smd:id>1-2</smd:id>"+
   "<smd:issuerInfo issuerID=\"2\">" +
    "<smd:org>Example Inc.</smd:org>"+
    "<smd:email>support@example.tld</smd:email>"+
    "<smd:url>http://www.example.tld</smd:url>"+
    "<smd:voice x=\"1234\">+1.7035555555</smd:voice>"+
   "</smd:issuerInfo>"+
   "<smd:notBefore>2009-08-16T09:00:00.0Z</smd:notBefore>"+
   "<smd:notAfter>2010-08-16T09:00:00.0Z</smd:notAfter>"+
   "<mark:mark xmlns:mark=\"urn:ietf:params:xml:ns:mark-1.0\">"+
    "<mark:trademark>"+
     "<mark:id>1234-2</mark:id>"+
     "<mark:markName>Example One</mark:markName>"+
     "<mark:holder entitlement=\"owner\">"+
      "<mark:org>Example Inc.</mark:org>"+
      "<mark:addr>"+
       "<mark:street>123 Example Dr.</mark:street>"+
       "<mark:street>Suite 100</mark:street>"+
       "<mark:city>Reston</mark:city>"+
       "<mark:sp>VA</mark:sp>"+
       "<mark:pc>20190</mark:pc>"+
       "<mark:cc>US</mark:cc>"+
      "</mark:addr>"+
     "</mark:holder>"+
     "<mark:jurisdiction>US</mark:jurisdiction>"+
     "<mark:class>35</mark:class>"+
     "<mark:class>36</mark:class>"+
     "<mark:label>example-one</mark:label>"+
     "<mark:label>exampleone</mark:label>"+
     "<mark:goodsAndServices>Dirigendas et eiusmodi"+
      "featuring infringo in airfare et cartam servicia."+
     "</mark:goodsAndServices>"+
     "<mark:regNum>234235</mark:regNum>"+
     "<mark:regDate>2009-08-16T09:00:00.0Z</mark:regDate>"+
     "<mark:exDate>2015-08-16T09:00:00.0Z</mark:exDate>"+
    "</mark:trademark>"+
   "</mark:mark>"+
   "<Signature xmlns=\"http://www.w3.org/2000/09/xmldsig#\">"+
    "<SignedInfo>"+
     "<CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/>"+
     "<SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#rsa-sha256\"/>"+
     "<Reference URI=\"#signedMark\">"+
      "<Transforms>"+
       "<Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\"/>"+
      "</Transforms>"+
      "<DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#sha256\"/>"+
      "<DigestValue>"+
"       miF4M2aTd1Y3tKOzJtiyl2VpzAnVPnV1Hq7Zax+yzrA="+
      "</DigestValue>"+
     "</Reference>"+
    "</SignedInfo>"+
    "<SignatureValue>"+
"MELpHTWEVfG1JcsG1/a//o54OnlJ5A864+X5JwfqgGBBeZSzGHNzwzTKFzIyyyfn"+
"lGxVwNMoBV5aSvkF7oEKMNVzfcl/P0czNQZ/LJ83p3Ol27/iUNsqgCaGf9Zupw+M"+
"XT4Q2lOrIw+qSx5g7q9T83siMLvkD5uEYlU5dPqgsObLTW8/doTQrA14RcxgY4kG"+
"a4+t5B1cT+5VaghTOPb8uUSEDKjnOsGdy8p24wgyK9n8h0CTSS2ZQ6Zq/RmQeT7D"+
"sbceUHheQ+mkQWIljpMQqsiBjw5XXh4jkEgfAzrb6gkYEF+X8ReuPZuOYC4QjIET"+
"yx8ifN4KE3GIbMXeF4LDsA=="+
    "</SignatureValue>"+
    "<KeyInfo>"+
     "<KeyValue>"+
      "<RSAKeyValue>"+
       "<Modulus>"+
"o/cwvXhbVYl0RDWWvoyeZpETVZVVcMCovUVNg/swWinuMgEWgVQFrz0xA04pEhXC"+
"FVv4evbUpekJ5buqU1gmQyOsCKQlhOHTdPjvkC5upDqa51Flk0TMaMkIQjs7aUKC"+
"mA4RG4tTTGK/EjR1ix8/D0gHYVRldy1YPrMP+ou75bOVnIos+HifrAtrIv4qEqwL"+
"L4FTZAUpaCa2BmgXfy2CSRQbxD5Or1gcSa3vurh5sPMCNxqaXmIXmQipS+DuEBqM"+
"M8tldaN7RYojUEKrGVsNk5i9y2/7sjn1zyyUPf7vL4GgDYqhJYWV61DnXgx/Jd6C"+
"WxvsnDF6scscQzUTEl+hyw=="+
       "</Modulus>"+
       "<Exponent>"+
"AQAB"+
       "</Exponent>"+
      "</RSAKeyValue>"+
     "</KeyValue>"+
     "<X509Data>"+
      "<X509Certificate>"+
"MIIESTCCAzGgAwIBAgIBAjANBgkqhkiG9w0BAQsFADBiMQswCQYDVQQGEwJVUzEL"+
"MAkGA1UECBMCQ0ExFDASBgNVBAcTC0xvcyBBbmdlbGVzMRMwEQYDVQQKEwpJQ0FO"+
"TiBUTUNIMRswGQYDVQQDExJJQ0FOTiBUTUNIIFRFU1QgQ0EwHhcNMTMwMjA4MDAw"+
"MDAwWhcNMTgwMjA3MjM1OTU5WjBsMQswCQYDVQQGEwJVUzELMAkGA1UECBMCQ0Ex"+
"FDASBgNVBAcTC0xvcyBBbmdlbGVzMRcwFQYDVQQKEw5WYWxpZGF0b3IgVE1DSDEh"+
"MB8GA1UEAxMYVmFsaWRhdG9yIFRNQ0ggVEVTVCBDRVJUMIIBIjANBgkqhkiG9w0B"+
"AQEFAAOCAQ8AMIIBCgKCAQEAo/cwvXhbVYl0RDWWvoyeZpETVZVVcMCovUVNg/sw"+
"WinuMgEWgVQFrz0xA04pEhXCFVv4evbUpekJ5buqU1gmQyOsCKQlhOHTdPjvkC5u"+
"pDqa51Flk0TMaMkIQjs7aUKCmA4RG4tTTGK/EjR1ix8/D0gHYVRldy1YPrMP+ou7"+
"5bOVnIos+HifrAtrIv4qEqwLL4FTZAUpaCa2BmgXfy2CSRQbxD5Or1gcSa3vurh5"+
"sPMCNxqaXmIXmQipS+DuEBqMM8tldaN7RYojUEKrGVsNk5i9y2/7sjn1zyyUPf7v"+
"L4GgDYqhJYWV61DnXgx/Jd6CWxvsnDF6scscQzUTEl+hywIDAQABo4H/MIH8MAwG"+
"A1UdEwEB/wQCMAAwHQYDVR0OBBYEFPZEcIQcD/Bj2IFz/LERuo2ADJviMIGMBgNV"+
"HSMEgYQwgYGAFO0/7kEh3FuEKS+Q/kYHaD/W6wihoWakZDBiMQswCQYDVQQGEwJV"+
"UzELMAkGA1UECBMCQ0ExFDASBgNVBAcTC0xvcyBBbmdlbGVzMRMwEQYDVQQKEwpJ"+
"Q0FOTiBUTUNIMRswGQYDVQQDExJJQ0FOTiBUTUNIIFRFU1QgQ0GCAQEwDgYDVR0P"+
"AQH/BAQDAgeAMC4GA1UdHwQnMCUwI6AhoB+GHWh0dHA6Ly9jcmwuaWNhbm4ub3Jn"+
"L3RtY2guY3JsMA0GCSqGSIb3DQEBCwUAA4IBAQB2qSy7ui+43cebKUKwWPrzz9y/"+
"IkrMeJGKjo40n+9uekaw3DJ5EqiOf/qZ4pjBD++oR6BJCb6NQuQKwnoAz5lE4Ssu"+
"y5+i93oT3HfyVc4gNMIoHm1PS19l7DBKrbwbzAea/0jKWVzrvmV7TBfjxD3AQo1R"+
"bU5dBr6IjbdLFlnO5x0G0mrG7x5OUPuurihyiURpFDpwH8KAH1wMcCpXGXFRtGKk"+
"wydgyVYAty7otkl/z3bZkCVT34gPvF70sR6+QxUy8u0LzF5A/beYaZpxSYG31amL"+
"AdXitTWFipaIGea9lEGFM0L9+Bg7XzNn4nVLXokyEB3bgS4scG6QznX23FGk"+
      "</X509Certificate>"+
     "</X509Data>"+
    "</KeyInfo>"+
   "</Signature></smd:signedMark>";
	
	
	public static void main( String argv[] ) {
 
		testCommand();
		testResponse();

	}

	private static void testCommand() {
		EppDomain domain = new EppDomain("example.com");
		domain.setPeriod(new EppPeriod(2, 'y'));
		domain.addNameServer("ns1.example.com");
		domain.addNameServer("ns2.example.com");
		domain.setRegistrant("JD1234-EXAMPLE");
		domain.addContact(new EppContactType("JD1234-NEULEVEL", "admin"));
		domain.setAuthInfo(new EppAuthInfo("pw", "2fooBAR", "JD1234-EXAMPLE"));

		EppCommandCreate cmd = new EppCommandCreate(domain);

		EppCommandCreateLaunchRegistration l_ext = new EppCommandCreateLaunchRegistration();
		EppSignedMarkData _mark = new EppSignedMarkData();
		try {
			_smd = EppTestClientSMD.readFromFile("SMDFile");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		_mark.rawXml(_smd);
		EppLaunchPhase _phase = new EppLaunchPhase();
		_phase.phase("sunrise");
		l_ext.phase(_phase);
		l_ext.signedMark(_mark);
		l_ext.noticeID("49FD46E6C4B45C55D4AC");
		l_ext.acceptedDate("2012-06-19T10:00:10.0Z");
		l_ext.notAfter("2012-06-19T09:01:30.0Z");
		cmd.addExtension(l_ext);
		
		EppIDNData idn = new EppIDNData();
		idn.table("es");
		idn.uname("espa&#xF1;ol.example.com");
		cmd.addExtension(idn);
		cmd.setClientTransactionId("ABC-DEF-12345");
		System.out.println(cmd);
		//Read the input XML file and put it in the Document
		Document smdDoc = null;
		try {
			smdDoc = documentBuilder.parse(new InputSource(new ByteArrayInputStream(cmd.toString().getBytes("utf-8"))) );

		} catch (SAXException ex1) {
			// TODO Auto-generated catch block
			ex1.printStackTrace();
		} catch (IOException ex2){
			ex2.printStackTrace();
		}
//		EppParser cre_parser = new EppParser(cmd.toString());
//		if( cre_parser.hasError() )
//		{
//			System.out.println(cre_parser.getResult());
//			System.exit(1);
//		}

		Node createepp = smdDoc.getDocumentElement();
		EppCommandCreate newcmd = (EppCommandCreate) EppCommand.fromXML(createepp);
		if( newcmd == null )
		{
			System.out.println("Error in fromXML");
			System.exit(1);
		}
		System.out.println(newcmd);


		EppCommandUpdateDomain updateadd = new EppCommandUpdateDomain("example.com");
		EppCommandUpdateLaunchRegistration upLaunch = new EppCommandUpdateLaunchRegistration();
		_phase = new EppLaunchPhase();
		_phase.phase("sunrise");
		upLaunch.phase(_phase);
		upLaunch.applicationId("444");
		updateadd.addExtension(upLaunch);

		updateadd.setClientTransactionId("ABC-DEF-12345");
		System.out.println(updateadd);

		EppParser parser = new EppParser(updateadd.toString());
		if( parser.hasError() ) {
			System.out.println(parser.getResult());
			System.exit(1);
		}

		Node epp = parser.getRootNode();
		EppCommand newupdateadd = (EppCommand) EppCommand.fromXML(epp);
		if( newupdateadd == null ) {
			System.out.println("Error in fromXML");
			System.exit(1);
		}
		System.out.println(newupdateadd);
/*
*/
		EppCommandDeleteDomain deladd = new EppCommandDeleteDomain("example.com");
		EppCommandDeleteLaunchRegistration delLaunch = new EppCommandDeleteLaunchRegistration();
		_phase = new EppLaunchPhase();
		_phase.phase("sunrise");
		delLaunch.phase(_phase);
		delLaunch.applicationId("444");
		deladd.addExtension(delLaunch);

		deladd.setClientTransactionId("ABC-DEF-12345");
		System.out.println(deladd);
		EppParser parser_del = new EppParser(deladd.toString());
		if( parser_del.hasError() ) {
			System.out.println(parser_del.getResult());
			System.exit(1);
		}
		Node epp_del = parser_del.getRootNode();
		EppCommand newdeladd = (EppCommand) EppCommand.fromXML(epp_del);
		if( newdeladd == null ) {
			System.out.println("Error in fromXML");
			System.exit(1);
		}
		System.out.println(newdeladd);

		EppCommandInfoDomain infCmd = new EppCommandInfoDomain("example.com");
		EppCommandInfoLaunchRegistration infCmdReg = new EppCommandInfoLaunchRegistration();
		_phase = new EppLaunchPhase();
		_phase.phase("sunrise");
		infCmdReg.phase(_phase);
		infCmdReg.applicationId("444");
		infCmd.addExtension(infCmdReg);
		

		infCmd.setClientTransactionId("ABC-DEF-12345");
		System.out.println(infCmd);
		EppParser parser_inf = new EppParser(infCmd.toString());
		if( parser_inf.hasError() ) {
			System.out.println(parser_inf.getResult());
			System.exit(1);
		}
		Node epp_inf = parser_inf.getRootNode();
		EppCommand newinfCmd = (EppCommand) EppCommand.fromXML(epp_inf);
		if( newinfCmd == null ) {
			System.out.println("Error in fromXML");
			System.exit(1);
		}
		System.out.println(newinfCmd);
	}

	private static void testResponse()
	{
		Node epp;
		EppResponse res;
		EppResponse newres;
		EppResult result;
		EppTransactionId xid;

		res = new EppResponse();
		result = new EppResult(EppError.CODE_NO_ERROR,
		                       EppError.TEXT_NO_ERROR);

//		res.addResult(result);
//
//		result = new EppResult(EppError.CODE_COMMAND_SYNTAX_ERROR,
//		                       EppError.TEXT_COMMAND_SYNTAX_ERROR);
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

		EppResponseDataInfoLaunchRegistration _lInfo = new EppResponseDataInfoLaunchRegistration();
		EppMarkMark _mark = new EppMarkMark();
		EppTrademarkData _tm = new EppTrademarkData();
		EppMarkHolder _holder = new EppMarkHolder();
		EppMarkHolder _contact = new EppMarkHolder(EppMarkHolder.EPPMARK_CONTACT);
		EppMarkAddress _addr = new EppMarkAddress();
	
		_addr.city("Noida");
		_addr.streetLine1("401 C58/26");
		_addr.streetLine2("Oriental Inc. Apt.");
		_addr.streetLine3("Sector 62");
		_addr.stateProvince("U.P.");
		_addr.postalCode("201301");
		_addr.countryCode("IN");

		_holder.name("Navin Seshadri");
		_holder.org("Home!!");
		_holder.address(_addr);
		_holder.voice("+91.9810205057");
		_holder.fax("+91.9810205057");
		_holder.holderParam("owner");

		_contact.name("Navin Seshadri");
		_contact.org("Home!!");
		_contact.address(_addr);
		_contact.voice("+91.9810205057");
		_contact.fax("+91.9810205057");
		_contact.email("navin.seshadri@impetus.co.in");
		_contact.holderParam("agent");


		_tm.id("1-1");
		_tm.markName("testme");
		_tm.addLabel("navin");
		_tm.addLabel("seshadri");
		_tm.addLabel("codemonkey");
		_tm.addLabel("code-monkey");
		_tm.goodsServices("Am a coder and love to do so.");
		_tm.jurisdiction("US");
		_tm.tmClass(35);
		_tm.apId("1-2");
		_tm.apDate(Calendar.getInstance());
		_tm.regNum("5955");
		_tm.regDate(Calendar.getInstance());
		_tm.exDate(Calendar.getInstance());
		_tm.addHolder(_holder);
		_tm.addContact(_contact);
		_mark.addMark(_tm);
		_lInfo.phase("sunrise");
		_lInfo.mark(_mark);
		res.addExtension(_lInfo);
		EppIDNData idnInfoRsp = new EppIDNData();
		idnInfoRsp.table("es");
		idnInfoRsp.uname("espa&#xF1;ol.example.com");
		
		res.addExtension(idnInfoRsp);
		
		EppResponseDataCheckClaims claimsChk = new EppResponseDataCheckClaims();
		claimsChk.phase("claims1");
		claimsChk.add("example1.tld", true, "2013041500/2/6/9/rJ1NrDO92vDsAzf7EQzgjX4R0000000001");
		claimsChk.add("example2.tld", true, "2012041500/2/6/9/rJ1NrDO92vDsAzf7EQzgjX4R0000000002");
		res.addExtension(claimsChk);
		
		System.out.println(res);
		EppParser parser = new EppParser(res.toString());
		if( parser.hasError() ) {
			System.out.println(parser.getResult());
			System.exit(1);
		}

		epp = parser.getRootNode();
		newres = (EppResponse) EppResponse.fromXML(epp);
		if( newres == null ) {
			System.out.println("Error in fromXML");
		}
		System.out.println(newres);
	}
/*
*/
}
