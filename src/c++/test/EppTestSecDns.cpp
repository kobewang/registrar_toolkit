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

#include <string.h>
#include <stdlib.h>       
#include <iostream>     
#include <xercesc/parsers/XercesDOMParser.hpp>

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

int runEppTestSecDns(EppParser & parser);

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
	runEppTestSecDns(*parser);

	delete 	parser;
	XMLPlatformUtils::Terminate();
}
#endif

int runEppTestSecDns(EppParser& parser)
{


	DOMNode* n;

	EppDomain domain("DOMAIN.BIZ");
	EppAuthInfo authInfo(EppAuthInfo::TYPE_PW, "3fooBar");
	domain.setAuthInfo(authInfo);
	EppCommandCreate create(&domain, "ABC-DEF-XXXX-ID");

	EppCommandCreateSecDns * createSecDns = new EppCommandCreateSecDns();
	EppSecDnsDsData ds0;
	EppSecDnsDsData ds1;
	time_t cal = ::time(0);

	char * str0 = "ABCDEF012345789";
	char * str1 = "012345789ABCDEF";

	ds0.setKeyTag(12345);
	ds0.setAlgorithm(3);
	ds0.setDigestType(1);
	//ds0.setDigest((unsigned char *) str0, 16);
	//ds0.setStartDate(cal);
	//ds0.setEndDate(cal + 86400 * 365);

	ds1.setKeyTag(54321);
	ds1.setAlgorithm(3);
	ds1.setDigestType(1);
	//ds1.setDigest((unsigned char *) str1, 16);
	//ds1.setStartDate(cal + 86400 * 365);
	//ds1.setEndDate(cal + 86400 * 730);
	//ds1.setValidationInterval("P60D");

	createSecDns->add(ds0);
	createSecDns->add(ds1);

	create.addExtension(createSecDns);

	cout << create.toString() << endl;
	parser.parse(create.toString());
	n = parser.getRootNode();
	EppCommandCreate * newCreate = (EppCommandCreate *) EppCommand::fromXML(*n);
	cout << newCreate->toString() << endl;
	delete newCreate;

	EppCommandUpdateDomain updateDomainAdd("DOMAIN.BIZ");
	updateDomainAdd.setClientTransactionId("CLINET-XID");

	EppCommandUpdateSecDns * addSecDns = new EppCommandUpdateSecDns();
	addSecDns->add(ds0);
	addSecDns->add(ds1);
	updateDomainAdd.addExtension(addSecDns);

	cout << updateDomainAdd.toString() << endl;
	parser.parse(updateDomainAdd.toString());
	n = parser.getRootNode();
	EppCommandUpdate * newUpdateDomainAdd = (EppCommandUpdate *) EppCommand::fromXML(*n);
	cout << newUpdateDomainAdd->toString() << endl;
	delete newUpdateDomainAdd;

	EppCommandUpdateDomain updateDomainChg("DOMAIN.BIZ");
	updateDomainChg.setClientTransactionId("CLINET-XID");

	EppCommandUpdateSecDns * chgSecDns = new EppCommandUpdateSecDns();
	chgSecDns->change(ds0);
	chgSecDns->change(ds1);
	updateDomainChg.addExtension(chgSecDns);

	cout << updateDomainChg.toString() << endl;
	parser.parse(updateDomainChg.toString());
	n = parser.getRootNode();
	EppCommandUpdate * newUpdateDomainChg = (EppCommandUpdate *) EppCommand::fromXML(*n);
	cout << newUpdateDomainChg->toString() << endl;
	delete newUpdateDomainChg;

	EppCommandUpdateDomain updateDomainRem("DOMAIN.BIZ");
	updateDomainRem.setClientTransactionId("CLINET-XID");

	EppCommandUpdateSecDns * remSecDns = new EppCommandUpdateSecDns();
	updateDomainRem.addExtension(remSecDns);

	cout << updateDomainRem.toString() << endl;
	parser.parse(updateDomainRem.toString());
	n = parser.getRootNode();
	EppCommandUpdate * newUpdateDomainRem = (EppCommandUpdate *) EppCommand::fromXML(*n);
	cout << newUpdateDomainRem->toString() << endl;
	delete newUpdateDomainRem;

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

	EppResponseDataInfoSecDns * infSecDns = new EppResponseDataInfoSecDns();
	infSecDns->add(ds0);
	infSecDns->add(ds1);
	rsp.addExtension(infSecDns);

	cout << rsp.toString() << endl;
	parser.parse(rsp.toString());
	n = parser.getRootNode();
	EppResponse * newRsp = EppResponse::fromXML(*n);
	cout << newRsp->toString() << endl;
	delete newRsp;

	return 0;
}
