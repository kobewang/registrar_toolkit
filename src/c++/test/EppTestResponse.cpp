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

#include "EppEntity.hpp"
#include "EppAuthInfo.hpp"
#include "EppUnspec.hpp"
#include "EppAddress.hpp"
#include "EppE164.hpp"
#include "EppPeriod.hpp"
#include "EppIpAddress.hpp"
#include "EppContactData.hpp"
#include "EppContactType.hpp"
#include "EppHello.hpp"
#include "EppStatus.hpp"
#include "EppServiceMenu.hpp"
#include "EppTransactionId.hpp"
#include "EppGreeting.hpp"
#include "EppContact.hpp"
#include "EppDomain.hpp"
#include "EppHost.hpp"
#include "EppError.hpp"
#include "EppParser.hpp"
#include "EppUtil.hpp"

#include "EppResultMessage.hpp"
#include "EppResult.hpp"
#include "EppResponse.hpp"
#include "EppResponseData.hpp"
#include "EppResponseDataCreate.hpp"
#include "EppResponseDataCreateContact.hpp"
#include "EppResponseDataCreateDomain.hpp"
#include "EppResponseDataCreateHost.hpp"
#include "EppResponseDataCreateSvcsub.hpp"
#include "EppResponseDataInfo.hpp"
#include "EppResponseDataCheck.hpp"
#include "EppResponseDataCheckContact.hpp"
#include "EppResponseDataCheckDomain.hpp"
#include "EppResponseDataCheckHost.hpp"
#include "EppResponseDataCheckSvcsub.hpp"
#include "EppResponseDataPending.hpp"
#include "EppResponseDataPendingContact.hpp"
#include "EppResponseDataPendingDomain.hpp"
#include "EppResponseDataPendingHost.hpp"
#include "EppResponseDataPendingSvcsub.hpp"
#include "EppResponseDataPoll.hpp"
#include "EppCommandTransfer.hpp"
#include "EppCommandTransferContact.hpp"
#include "EppCommandTransferDomain.hpp"
#include "EppCommandTransferSvcsub.hpp"
#include "EppResponseDataTransfer.hpp"
#include "EppResponseDataTransferContact.hpp"
#include "EppResponseDataTransferDomain.hpp"
#include "EppResponseDataTransferSvcsub.hpp"
#include "EppResponseDataCheckClaims.hpp"

int runEppTestResponse(EppParser & parser);
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
	runEppTestResponse(*parser);

	delete 	parser;
	XMLPlatformUtils::Terminate();
}
#endif

int runEppTestResponse(EppParser & parser)
{
	DOMNode* n = NULL;

	EppResult result(EppError::CODE_NO_ERROR);
	EppValueReason reason0("Value0", "Reason0", "en_US");
	EppValueReason reason4("Value4");
	result.addValue(reason0);
	result.addValue("Value1");
	result.addValue("Value2");
	result.addValue("Value3");
	result.addValue(reason4);
	cout << result.toString() << endl;
	parser.parse(result.toString());
	n = parser.getDocument().getElementsByTagName(XS("result"))->item(0);
	EppResult * newResult = EppResult::fromXML(*n);
	cout << newResult->toString() << endl;
	delete newResult;

	EppTransactionId trid("CLIENT-XID", "SERVER-ID");
	EppResponse rsp;
	rsp.addResult(result);
	rsp.setTransactionId(trid);
	cout << rsp.toString() << endl;
	parser.parse(rsp.toString());
	n = parser.getRootNode();
	EppResponse * newRsp = EppResponse::fromXML(*n);
	cout << newRsp->toString() << endl;
	delete newRsp;

	EppResponseDataCreateContact creContact("CONTACT-ROID");
	rsp.setResponseData(&creContact);
	cout << rsp.toString() << endl;
	parser.parse(rsp.toString());
	n = parser.getRootNode();
	EppResponse * newRspCreContact = EppResponse::fromXML(*n);
	cout << newRspCreContact->toString() << endl;
	delete newRspCreContact;

	EppResponseDataCreateDomain creDomain("DOMAIN.BIZ", time(0));
	rsp.setResponseData(&creDomain);
	cout << rsp.toString() << endl;
	parser.parse(rsp.toString());
	n = parser.getRootNode();
	EppResponse * newRspCreDomain = EppResponse::fromXML(*n);
	cout << newRspCreDomain->toString() << endl;
	delete newRspCreDomain;

	EppResponseDataCreateHost creHost("NS.1DOMAIN.BIZ");
	rsp.setResponseData(&creHost);
	cout << rsp.toString() << endl;
	parser.parse(rsp.toString());
	n = parser.getRootNode();
	EppResponse * newRspCreHost = EppResponse::fromXML(*n);
	cout << newRspCreHost->toString() << endl;
	delete newRspCreHost;

	EppResponseDataCreateSvcsub creSvcsub("BIZLOCK-001", time(0));
	rsp.setResponseData(&creSvcsub);
	cout << rsp.toString() << endl;
	parser.parse(rsp.toString());
	n = parser.getRootNode();
	EppResponse * newRspCreSvcsub = EppResponse::fromXML(*n);
	cout << newRspCreSvcsub->toString() << endl;
	delete newRspCreSvcsub;

        EppHost host("ns.abc.com");
        host.addAddress(EppIpAddress("192.9.1.1"));
        host.addAddress(EppIpAddress("192.9.1.2"));
        host.addAddress(EppIpAddress("192.9.1.3"));
	EppResponseDataInfo infHost(&host);
	rsp.setResponseData(&infHost);
	cout << rsp.toString() << endl;
	parser.parse(rsp.toString());
	n = parser.getRootNode();
	EppResponse * newRspInfHost = EppResponse::fromXML(*n);
	cout << newRspInfHost->toString() << endl;
	delete newRspInfHost;

	EppResponseDataCheckContact chkContact;
	chkContact.add("ABC-CONTACT-1", DOMString(EppResponseDataCheck::FOUND));
	chkContact.add("ABC-CONTACT-2", DOMString(EppResponseDataCheck::NOT_FOUND));
	chkContact.add("ABC-CONTACT-3", DOMString(EppResponseDataCheck::FOUND));
	chkContact.add("ABC-CONTACT-4", DOMString(EppResponseDataCheck::NOT_FOUND));
	rsp.setResponseData(&chkContact);
	cout << rsp.toString() << endl;
	parser.parse(rsp.toString());
	n = parser.getRootNode();
	EppResponse * newRspChkContact = EppResponse::fromXML(*n);
	cout << newRspChkContact->toString() << endl;
	delete newRspChkContact;

	EppResponse  rsp2 ;
	EppResponseDataCheckDomain chkDomain;
	chkDomain.add("DOMAIN1.BIZ", DOMString(EppResponseDataCheck::FOUND));
	chkDomain.add("DOMAIN2.BIZ", DOMString(EppResponseDataCheck::NOT_FOUND));
	chkDomain.add("DOMAIN3.BIZ", DOMString(EppResponseDataCheck::FOUND));
	chkDomain.add("DOMAIN4.BIZ", DOMString(EppResponseDataCheck::NOT_FOUND));
	rsp2.setResponseData(&chkDomain);

	EppResponseDataCheckClaims * chkClaimsExt = new EppResponseDataCheckClaims();
	//chkClaimsExt->phase("claims1"); //TODO: Santosh, need to fix this.
	chkClaimsExt->add(DOMString("DOMAIN5.BIZ"), true,DOMString("claimKey1"));
	chkClaimsExt->add(DOMString("DOMAIN6.BIZ"), false,DOMString("claimKey2"));
	rsp2.addExtension(chkClaimsExt);

	//EppResponseDataCheckClaims  chkClaimsExt ;
	//chkClaimsExt.phase("claims1");
	//chkClaimsExt.add(DOMString("DOMAIN5.BIZ"), true,DOMString("claimKey1"));
	//chkClaimsExt.add(DOMString("DOMAIN6.BIZ"), false,DOMString("claimKey2"));
	//rsp2.addExtension(&chkClaimsExt);

	cout << rsp2.toString() << endl;
	parser.parse(rsp2.toString());
	n = parser.getRootNode();
	EppResponse * newRspChkDomain = EppResponse::fromXML(*n);
	cout << newRspChkDomain->toString() << endl;
	delete newRspChkDomain;

	EppResponseDataCheckHost chkHost;
	chkHost.add("NS.DOMAIN1.BIZ", DOMString(EppResponseDataCheck::FOUND));
	chkHost.add("NS.DOMAIN2.BIZ", DOMString(EppResponseDataCheck::NOT_FOUND));
	chkHost.add("NS.DOMAIN3.BIZ", DOMString(EppResponseDataCheck::FOUND));
	chkHost.add("NS.DOMAIN4.BIZ", DOMString(EppResponseDataCheck::NOT_FOUND));
	rsp.setResponseData(&chkHost);
	cout << rsp.toString() << endl;
	parser.parse(rsp.toString());
	n = parser.getRootNode();
	EppResponse * newRspChkHost = EppResponse::fromXML(*n);
	cout << newRspChkHost->toString() << endl;
	delete newRspChkHost;

	EppResponseDataCheckSvcsub chkSvcsub;
	chkSvcsub.add("BIZACCOUNT0", DOMString(EppResponseDataCheck::FOUND));
	chkSvcsub.add("BIZACCOUNT1", DOMString(EppResponseDataCheck::NOT_FOUND));
	chkSvcsub.add("BIZLOCK-001", DOMString(EppResponseDataCheck::FOUND));
	chkSvcsub.add("BIZLOCK-002", DOMString(EppResponseDataCheck::NOT_FOUND));
	rsp.setResponseData(&chkSvcsub);
	cout << rsp.toString() << endl;
	parser.parse(rsp.toString());
	n = parser.getRootNode();
	EppResponse * newRspChkSvcsub = EppResponse::fromXML(*n);
	cout << newRspChkSvcsub->toString() << endl;
	delete newRspChkSvcsub;

	EppCommandTransferContact xferContact("CONTACT-123");
	xferContact.setOperation(EppCommandTransfer::OPTYPE_REQUEST);
	EppAuthInfo authInfoContact(EppAuthInfo::TYPE_PW, "fooBar");
	xferContact.setAuthInfo(authInfoContact);
	xferContact.setClientTransactionId("CLINET-XID");

	EppResponseDataPoll pollContact(&xferContact);
	rsp.setResponseData(&pollContact);
	cout << rsp.toString() << endl;
	parser.parse(rsp.toString());
	n = parser.getRootNode();
	EppResponse * newRspPollContact = EppResponse::fromXML(*n);
	cout << newRspPollContact->toString() << endl;
	delete newRspPollContact;

	EppCommandTransferDomain xferDomain("DOMAIN.BIZ");
	xferDomain.setOperation(EppCommandTransfer::OPTYPE_REQUEST);
	EppAuthInfo authInfoDomain(EppAuthInfo::TYPE_PW, "fooBar");
	xferDomain.setAuthInfo(authInfoDomain);
	EppPeriod renewPeriod(99, EppPeriod::UNIT_YEAR);
	xferDomain.setPeriod(renewPeriod);
	xferDomain.setClientTransactionId("CLINET-XID");

	EppResponseDataPoll pollDomain(&xferDomain);
	rsp.setResponseData(&pollDomain);
	cout << rsp.toString() << endl;
	parser.parse(rsp.toString());
	n = parser.getRootNode();
	EppResponse * newRspPollDomain = EppResponse::fromXML(*n);
	cout << newRspPollDomain->toString() << endl;
	delete newRspPollDomain;

	EppCommandTransferSvcsub xferSvcsub("DOMAIN.BIZ");
	xferSvcsub.setOperation(EppCommandTransfer::OPTYPE_REQUEST);
	EppAuthInfo authInfoSvcsub(EppAuthInfo::TYPE_PW, "fooBar");
	xferSvcsub.setAuthInfo(authInfoSvcsub);
	EppPeriod renewPeriodSvcsub(99, EppPeriod::UNIT_YEAR);
	xferSvcsub.setPeriod(renewPeriodSvcsub);
	xferSvcsub.setClientTransactionId("CLINET-XID");

	EppResponseDataPoll pollSvcsub(&xferSvcsub);
	rsp.setResponseData(&pollSvcsub);
	cout << rsp.toString() << endl;
	parser.parse(rsp.toString());
	n = parser.getRootNode();
	EppResponse * newRspPollSvcsub = EppResponse::fromXML(*n);
	cout << newRspPollSvcsub->toString() << endl;
	delete newRspPollSvcsub;

	EppResponseDataTransferContact transContact("CONTACT-123");
	transContact.setTransferStatus(EppResponseDataTransfer::STATUS_PENDING);
	transContact.setRequestClientId("CLIENT-ID-REQ");
	transContact.setRequestDate(time(0));
	transContact.setActionClientId("CLIENT-ID-ACT");
	transContact.setActionDate(time(0) + 30 * 3600 * 24);
	rsp.setResponseData(&transContact);
	cout << rsp.toString() << endl;
	parser.parse(rsp.toString());
	n = parser.getRootNode();
	EppResponse * newRspTransContact = EppResponse::fromXML(*n);
	cout << newRspTransContact->toString() << endl;
	delete newRspTransContact;

	EppResponseDataTransferDomain transDomain("DOMAIN.BIZ");
	transDomain.setTransferStatus(EppResponseDataTransfer::STATUS_PENDING);
	transDomain.setRequestClientId("CLIENT-ID-REQ");
	transDomain.setRequestDate(time(0));
	transDomain.setActionClientId("CLIENT-ID-ACT");
	transDomain.setActionDate(time(0) + 30 * 3600 * 24);
	transDomain.setDateExpired(time(0) + 60 * 3600 * 24);
	rsp.setResponseData(&transDomain);
	cout << rsp.toString() << endl;
	parser.parse(rsp.toString());
	n = parser.getRootNode();
	EppResponse * newRspTransDomain = EppResponse::fromXML(*n);
	cout << newRspTransDomain->toString() << endl;
	delete newRspTransDomain;

	EppResponseDataTransferSvcsub transSvcsub("BIZLOCK-001");
	transSvcsub.setTransferStatus(EppResponseDataTransfer::STATUS_PENDING);
	transSvcsub.setRequestClientId("CLIENT-ID-REQ");
	transSvcsub.setRequestDate(time(0));
	transSvcsub.setActionClientId("CLIENT-ID-ACT");
	transSvcsub.setActionDate(time(0) + 30 * 3600 * 24);
	transSvcsub.setDateExpired(time(0) + 60 * 3600 * 24);
	rsp.setResponseData(&transSvcsub);
	cout << rsp.toString() << endl;
	parser.parse(rsp.toString());
	n = parser.getRootNode();
	EppResponse * newRspTransSvcsub = EppResponse::fromXML(*n);
	cout << newRspTransSvcsub->toString() << endl;
	delete newRspTransSvcsub;

	EppResponseDataPendingContact pendingContact("CONTACT-123", true);
	pendingContact.setTransactionId(EppTransactionId("CLIENT-ID", "SERVER-ID"));
	pendingContact.setDate(time(0));
	rsp.setResponseData(&pendingContact);
	cout << rsp.toString() << endl;
	parser.parse(rsp.toString());
	n = parser.getRootNode();
	EppResponse * newRspPendingContact = EppResponse::fromXML(*n);
	cout << newRspPendingContact->toString() << endl;
	delete newRspPendingContact;

	rsp.setResponseData(null);

	return 0;
}
