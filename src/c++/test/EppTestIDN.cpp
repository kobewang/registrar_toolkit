/*******************************************************************************
 * The MIT License (MIT)
 *  
 * Copyright (c)2015 Neustar Inc.
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
#include <xercesc/parsers/XercesDOMParser.hpp>
#include <string.h>
#include <stdlib.h>       
#include <iostream>     

#include "EppError.hpp"
#include "EppParser.hpp"
#include "EppUtil.hpp"

#include "EppDomain.hpp"
#include "EppCommandCreate.hpp"
#include "EppCommandUpdateDomain.hpp"
#include "EppSecDnsDsData.hpp"
#include "EppCommandCreateSecDns.hpp"
#include "EppCommandUpdateSecDns.hpp"

#include "EppResponse.hpp"
#include "EppResponseDataInfo.hpp"
#include "EppResponseDataInfoSecDns.hpp"
#include "EppIDNData.hpp"

int runEppTestIDN(EppParser & parser);

#ifndef COMBINE_TEST

int main( int argc, char ** argv )
{
	try
	{
		XMLPlatformUtils::Initialize();
	}
	catch( const XMLException& e )
	{
		cerr	<< "Error during Xerces-c Initialization\n"
			<< "  Exception message:"
			<< DOMString(e.getMessage()) << endl;
		return 1;
	}
	EppParser *parser = new EppParser();
	runEppTestIDN(*parser);

	delete 	parser;
	XMLPlatformUtils::Terminate();
}
#endif

int runEppTestIDN(EppParser & parser)
{
	DOMNode* n;

	EppDomain domain("DOMAIN.BIZ");
	EppAuthInfo authInfo(EppAuthInfo::TYPE_PW, "3fooBar");
	domain.setAuthInfo(authInfo);
	EppCommandCreate create(&domain, "ABC-DEF-XXXX-ID");
	EppIDNData * idnExt = new EppIDNData();
	idnExt->table("es");
	idnExt->uname("espa&#xF1;ol.example.com");


	create.addExtension(idnExt);

	cout << create.toString() << endl;
	parser.parse(create.toString());
	n = parser.getRootNode();
	EppCommandCreate * newCreate = (EppCommandCreate *) EppCommand::fromXML(*n);
	cout << newCreate->toString() << endl;
	delete newCreate;


	EppResult result(EppError::CODE_NO_ERROR);
	EppValueReason reason0("Value0", "Reason0", "en_US");
	EppValueReason reason4("Value4");
	result.addValue(reason0);
	result.addValue("Value1");
	result.addValue("Value2");
	result.addValue("Value3");
	result.addValue(reason4);
	EppTransactionId trid("CLIENT-XID", "SERVER-ID");
	EppResponse rsp;
	rsp.addResult(result);
	rsp.setTransactionId(trid);
	EppResponseDataInfo infData(&domain);
	rsp.setResponseData(&infData);
	EppIDNData * idnExtRsp = new EppIDNData();
	idnExtRsp->table("es");
	idnExtRsp->uname("espa&#xF1;ol.example.com");

	rsp.addExtension(idnExtRsp);

	cout << rsp.toString() << endl;
	parser.parse(rsp.toString());
	n = parser.getRootNode();
	EppResponse * newRsp = EppResponse::fromXML(*n);
	cout << newRsp->toString() << endl;
	delete newRsp;

	return 0;
}
