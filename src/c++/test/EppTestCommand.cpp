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
#include "BizAccount.hpp"
#include "BizLock.hpp"
#include "BizProtect.hpp"
#include "BizContact.hpp"
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
#include "EppSvcsub.hpp"
#include "EppError.hpp"
#include "EppParser.hpp"
#include "EppUtil.hpp"

#include "EppCredsOptions.hpp"
#include "EppCreds.hpp"
#include "EppCommand.hpp"
#include "EppCommandLogin.hpp"
#include "EppCommandLogout.hpp"
#include "EppCommandPoll.hpp"
#include "EppCommandCreate.hpp"
#include "EppCommandDelete.hpp"
#include "EppCommandDeleteContact.hpp"
#include "EppCommandDeleteDomain.hpp"
#include "EppCommandDeleteHost.hpp"
#include "EppCommandDeleteSvcsub.hpp"
#include "EppCommandInfo.hpp"
#include "EppCommandInfoContact.hpp"
#include "EppCommandInfoDomain.hpp"
#include "EppCommandInfoHost.hpp"
#include "EppCommandInfoSvcsub.hpp"
#include "EppCommandCheck.hpp"
#include "EppCommandCheckContact.hpp"
#include "EppCommandCheckDomain.hpp"
#include "EppCommandCheckHost.hpp"
#include "EppCommandCheckSvcsub.hpp"

#include "EppCommandRenew.hpp"
#include "EppCommandRenewDomain.hpp"
#include "EppCommandRenewSvcsub.hpp"

#include "EppCommandTransfer.hpp"
#include "EppCommandTransferContact.hpp"
#include "EppCommandTransferDomain.hpp"
#include "EppCommandTransferSvcsub.hpp"

#include "EppCommandUpdate.hpp"
#include "EppCommandUpdateContact.hpp"
#include "EppCommandUpdateDomain.hpp"
#include "EppCommandUpdateHost.hpp"
#include "EppCommandUpdateSvcsub.hpp"

int runEppTestCommand(EppParser & parser);

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
	runEppTestCommand(*parser);

	delete 	parser;
	XMLPlatformUtils::Terminate();
}
#endif

int runEppTestCommand(EppParser & parser)
{
	DOMNode* n = NULL;

	{
		EppCredsOptions credsOpt("1.0", "en-US");
		cout << credsOpt.toString() << endl;
		parser.parse(credsOpt.toString());
		n = parser.getDocument().getElementsByTagName(XS("options"))->item(0);
		EppCredsOptions * newCredsOpt = EppCredsOptions::fromXML(*n);
		cout << newCredsOpt->toString() << endl;
		delete newCredsOpt;
	}

	{
		EppCreds creds("clientId", "password");
		cout << creds.toString() << endl;
		parser.parse(creds.toString());
		n = parser.getDocument().getElementsByTagName(XS("creds"))->item(0);
		EppCreds * newCreds = EppCreds::fromXML(*n);
		cout << newCreds->toString() << endl;
		newCreds->setNewPassword("newPassword");
		newCreds->setOptions(EppCredsOptions("1.1", "en-us"));
		cout << newCreds->toString() << endl;

		EppServiceMenu menu;
		menu.addService("contact");
		menu.addService("domain");
		EppCommandLogin login(menu);
		cout << login.toString() << endl;
		parser.parse(login.toString());
		n = parser.getRootNode();
		EppCommandLogin * newLogin = (EppCommandLogin *) EppCommand::fromXML(*n);
		cout << newLogin->toString() << endl;
		newLogin->getServiceMenu()->addService("host");
		newLogin->setClientTransactionId("XXXXX-ID");
		newLogin->setCreds(*newCreds);
		cout << newLogin->toString() << endl;
		parser.parse(newLogin->toString());
		delete newLogin;
		n = parser.getRootNode();
		newLogin = (EppCommandLogin *) EppCommand::fromXML(*n);
		cout << newLogin->toString() << endl;
		delete newLogin;
	}
	{
		EppCommandLogout logout("XXXXX-ID");
		cout << logout.toString() << endl;
		parser.parse(logout.toString());
		n = parser.getRootNode();
		EppCommandLogout * newLogout = (EppCommandLogout *) EppCommand::fromXML(*n);
		cout << newLogout->toString() << endl;
		newLogout->setClientTransactionId("YYYYY-ID");
		cout << newLogout->toString() << endl;
		delete newLogout;
	}
	{
		EppCommandPoll poll("XXXXX-ID");
		cout << poll.toString() << endl;
		parser.parse(poll.toString());
		n = parser.getRootNode();
		EppCommandPoll * newPoll = (EppCommandPoll *) EppCommand::fromXML(*n);
		cout << newPoll->toString() << endl;
		newPoll->setOperation(EppCommandPoll::OPTYPE_ACK);
		newPoll->setMessageId("MessageId 123");
		cout << newPoll->toString() << endl;
		delete newPoll;
	}

	{
		EppHost host("ns.abc.com");
		host.addAddress(EppIpAddress("192.9.1.1"));
		host.addAddress(EppIpAddress("192.9.1.2"));
		host.addAddress(EppIpAddress("192.9.1.3"));

		EppCommandCreate create(&host, "ABC-DEF-XXXX-ID");
		cout << create.toString() << endl;
		parser.parse(create.toString());
		n = parser.getRootNode();
		EppCommandCreate * newCreate = (EppCommandCreate *) EppCommand::fromXML(*n);
		cout << newCreate->toString() << endl;
		delete newCreate;
	}

	{
		EppCommandDeleteContact delContact("CONTACT-ROID");
		delContact.setClientTransactionId("CLIENT-XID");
		cout << delContact.toString() << endl;
		parser.parse(delContact.toString());
		n = parser.getRootNode();
		EppCommandDelete * newDelContact = (EppCommandDelete *) EppCommand::fromXML(*n);
		cout << newDelContact->toString() << endl;
		delete newDelContact;
	}
	{
		EppCommandDeleteDomain delDomain("DOMAIN.BIZ");
		delDomain.setClientTransactionId("CLIENT-XID");
		cout << delDomain.toString() << endl;
		parser.parse(delDomain.toString());
		n = parser.getRootNode();
		EppCommandDelete * newDelDomain = (EppCommandDelete *) EppCommand::fromXML(*n);
		cout << newDelDomain->toString() << endl;
		delete newDelDomain;
	}
	{
		EppCommandDeleteHost delHost("NS.DOMAIN.BIZ");
		delHost.setClientTransactionId("CLIENT-XID");
		cout << delHost.toString() << endl;
		parser.parse(delHost.toString());
		n = parser.getRootNode();
		EppCommandDelete * newDelHost = (EppCommandDelete *) EppCommand::fromXML(*n);
		cout << newDelHost->toString() << endl;
		delete newDelHost;
	}
	{
		EppAuthInfo authInfoSvcsub(EppAuthInfo::TYPE_PW, "fooBar");
		EppCommandDeleteSvcsub delSvcsub("BIZLOCK-123");
		delSvcsub.setUserId("myUserId");
		delSvcsub.setAuthInfo(authInfoSvcsub);
		delSvcsub.setClientTransactionId("CLIENT-XID");
		cout << delSvcsub.toString() << endl;
		parser.parse(delSvcsub.toString());
		n = parser.getRootNode();
		EppCommandDelete * newDelSvcsub = (EppCommandDelete *) EppCommand::fromXML(*n);
		cout << newDelSvcsub->toString() << endl;
		delete newDelSvcsub;
	}
	{
		EppCommandInfoContact infoContact("CONTACT-ROID");
		infoContact.setClientTransactionId("CLINET-XID");
		cout << infoContact.toString() << endl;
		parser.parse(infoContact.toString());
		n = parser.getRootNode();
		EppCommandInfo * newInfoContact = (EppCommandInfo *) EppCommand::fromXML(*n);
		cout << newInfoContact->toString() << endl;
		delete newInfoContact;
	}
	{
		EppCommandInfoDomain infoDomain("DOMAIN.BIZ");
		infoDomain.setHostsControl("none");
		infoDomain.setClientTransactionId("CLINET-XID");
		cout << infoDomain.toString() << endl;
		parser.parse(infoDomain.toString());
		n = parser.getRootNode();
		EppCommandInfo * newInfoDomain = (EppCommandInfo *) EppCommand::fromXML(*n);
		cout << newInfoDomain->toString() << endl;
		delete newInfoDomain;
	}
	{
		EppCommandInfoHost infoHost("DOMAIN.BIZ");
		infoHost.setClientTransactionId("CLINET-XID");
		cout << infoHost.toString() << endl;
		parser.parse(infoHost.toString());
		n = parser.getRootNode();
		EppCommandInfo * newInfoHost = (EppCommandInfo *) EppCommand::fromXML(*n);
		cout << newInfoHost->toString() << endl;
		delete newInfoHost;
	}
	{
		EppAuthInfo authInfoSvcsub(EppAuthInfo::TYPE_PW, "fooBar");
		EppCommandInfoSvcsub infoSvcsub("BIZLOCK-123");
		infoSvcsub.setUserId("myUserId");
		infoSvcsub.setAuthInfo(authInfoSvcsub);
		infoSvcsub.setClientTransactionId("CLINET-XID");
		cout << infoSvcsub.toString() << endl;
		parser.parse(infoSvcsub.toString());
		n = parser.getRootNode();
		EppCommandInfo * newInfoSvcsub = (EppCommandInfo *) EppCommand::fromXML(*n);
		cout << newInfoSvcsub->toString() << endl;
		delete newInfoSvcsub;
	}
	{
		EppCommandCheckContact checkContact;
		checkContact.setClientTransactionId("CLINET-XID");
		checkContact.add("CONTACT-1");
		checkContact.add("CONTACT-2");
		checkContact.add("CONTACT-3");
		cout << checkContact.toString() << endl;
		parser.parse(checkContact.toString());
		n = parser.getRootNode();
		EppCommandCheck * newCheckContact = (EppCommandCheck *) EppCommand::fromXML(*n);
		cout << newCheckContact->toString() << endl;
		delete newCheckContact;
	}
	{
		EppCommandCheckDomain checkDomain;
		checkDomain.setClientTransactionId("CLINET-XID");
		checkDomain.add("DOMAIN1.BIZ");
		checkDomain.add("DOMAIN2.BIZ");
		checkDomain.add("DOMAIN2.BIZ");
		cout << checkDomain.toString() << endl;
		parser.parse(checkDomain.toString());
		n = parser.getRootNode();
		EppCommandCheck * newCheckDomain = (EppCommandCheck *) EppCommand::fromXML(*n);
		cout << newCheckDomain->toString() << endl;
		delete newCheckDomain;
	}
	{
		EppCommandCheckHost checkHost;
		checkHost.setClientTransactionId("CLINET-XID");
		checkHost.add("NS1.DOMAIN1.BIZ");
		checkHost.add("NS2.DOMAIN2.BIZ");
		checkHost.add("NS3.DOMAIN2.BIZ");
		cout << checkHost.toString() << endl;
		parser.parse(checkHost.toString());
		n = parser.getRootNode();
		EppCommandCheck * newCheckHost = (EppCommandCheck *) EppCommand::fromXML(*n);
		cout << newCheckHost->toString() << endl;
		delete newCheckHost;
	}
	{
		EppCommandCheckSvcsub checkSvcsub;
		checkSvcsub.setClientTransactionId("CLINET-XID");
		checkSvcsub.add("BIZACCOUNT0");
		checkSvcsub.add("BIZLOCK-001");
		checkSvcsub.add("BIZLOCK-002");
		cout << checkSvcsub.toString() << endl;
		parser.parse(checkSvcsub.toString());
		n = parser.getRootNode();
		EppCommandCheck * newCheckSvcsub = (EppCommandCheck *) EppCommand::fromXML(*n);
		cout << newCheckSvcsub->toString() << endl;
		delete newCheckSvcsub;
	}
	{
		EppCommandRenewDomain renewDomain("DOMAIN.BIZ", 0);
		renewDomain.setClientTransactionId("CLINET-XID");
		EppPeriod renewPeriodSvcsub(10, EppPeriod::UNIT_YEAR);
		renewDomain.setPeriod(renewPeriodSvcsub);
		cout << renewDomain.toString() << endl;
		parser.parse(renewDomain.toString());
		n = parser.getRootNode();
		EppCommandRenew * newRenewDomain = (EppCommandRenew *) EppCommand::fromXML(*n);
		cout << newRenewDomain->toString() << endl;
		delete newRenewDomain;
	}
	{
		EppAuthInfo authInfoSvcsub(EppAuthInfo::TYPE_PW, "fooBar");
		EppCommandRenewSvcsub renewSvcsub("BIZLOCK-001", 0);
		renewSvcsub.setUserId("myUserId");
		renewSvcsub.setAuthInfo(authInfoSvcsub);
		renewSvcsub.setClientTransactionId("CLINET-XID");
		EppPeriod renewPeriod(10, EppPeriod::UNIT_YEAR);
		renewSvcsub.setPeriod(renewPeriod);
		cout << renewSvcsub.toString() << endl;
		parser.parse(renewSvcsub.toString());
		n = parser.getRootNode();
		EppCommandRenew * newRenewSvcsub = (EppCommandRenew *) EppCommand::fromXML(*n);
		cout << newRenewSvcsub->toString() << endl;
		delete newRenewSvcsub;
	}
	{
		EppCommandTransferContact xferContact("CONTACT-123");
		xferContact.setOperation(EppCommandTransfer::OPTYPE_REQUEST);
		EppAuthInfo authInfoContact(EppAuthInfo::TYPE_PW, "fooBar");
		xferContact.setAuthInfo(authInfoContact);
		xferContact.setClientTransactionId("CLINET-XID");
		cout << xferContact.toString() << endl;
		parser.parse(xferContact.toString());
		n = parser.getRootNode();
		EppCommandTransfer * newXferContact = (EppCommandTransfer *) EppCommand::fromXML(*n);
		cout << newXferContact->toString() << endl;
		delete newXferContact;
	}
	{
		EppCommandTransferDomain xferDomain("DOMAIN.BIZ");
		xferDomain.setOperation(EppCommandTransfer::OPTYPE_REQUEST);
		EppAuthInfo authInfoDomain(EppAuthInfo::TYPE_PW, "fooBar");
		xferDomain.setAuthInfo(authInfoDomain);
		EppPeriod renewPeriod(10, EppPeriod::UNIT_YEAR);
		xferDomain.setPeriod(renewPeriod);
		xferDomain.setClientTransactionId("CLINET-XID");
		cout << xferDomain.toString() << endl;
		parser.parse(xferDomain.toString());
		n = parser.getRootNode();
		EppCommandTransfer * newXferDomain = (EppCommandTransfer *) EppCommand::fromXML(*n);
		cout << newXferDomain->toString() << endl;
		delete newXferDomain;
	}
	{
		EppAuthInfo authInfoSvcsub(EppAuthInfo::TYPE_PW, "fooBar");
		EppCommandTransferSvcsub xferSvcsub("BIZLOCK-001");
		xferSvcsub.setUserId("myUserId");
		xferSvcsub.setOperation(EppCommandTransfer::OPTYPE_REQUEST);
		xferSvcsub.setAuthInfo(authInfoSvcsub);
		EppPeriod renewPeriodSvcsub(10, EppPeriod::UNIT_YEAR);
		xferSvcsub.setPeriod(renewPeriodSvcsub);
		xferSvcsub.setClientTransactionId("CLINET-XID");
		cout << xferSvcsub.toString() << endl;
		parser.parse(xferSvcsub.toString());
		n = parser.getRootNode();
		EppCommandTransfer * newXferSvcsub = (EppCommandTransfer *) EppCommand::fromXML(*n);
		cout << newXferSvcsub->toString() << endl;
		delete newXferSvcsub;
	}
	{
		EppCommandUpdateContact updateContact("CONTACT-123");
		updateContact.setNewEmail("ABC@BIZ.COM");
		updateContact.setNewVoice("12345", "xyz");
		updateContact.setNewFax("faxnum", "xyz");
		EppStatus updateContactStatus(EppContact::STATUS_OK);
		updateContact.addStatus(updateContactStatus);
		updateContact.removeStatus(updateContactStatus);
		EppAuthInfo authInfoContact(EppAuthInfo::TYPE_PW, "fooBar");
		updateContact.setNewAuthInfo(authInfoContact);
		updateContact.setClientTransactionId("CLINET-XID");
		cout << updateContact.toString() << endl;
		parser.parse(updateContact.toString());
		n = parser.getRootNode();
		EppCommandUpdate * newUpdateContact = (EppCommandUpdate *) EppCommand::fromXML(*n);
		cout << newUpdateContact->toString() << endl;
		delete newUpdateContact;
	}
	{
		EppCommandUpdateDomain updateDomain("DOMAIN.BIZ");
		updateDomain.setNewRegistrant("NEW-REGISTRANT-123");
		EppStatus updateDomainStatus(EppDomain::STATUS_OK);
		updateDomain.addStatus(updateDomainStatus);
		updateDomain.removeStatus(updateDomainStatus);
		updateDomain.addNameServer("ns1.DOMAIN.BIZ");
		updateDomain.removeNameServer("ns2.DOMAIN.BIZ");
		updateDomain.removeNameServer("ns3.DOMAIN.BIZ");
		EppContactType updateDomainContact("CONTACT-1", EppDomain::CONTACT_TYPE_BILLING);
		updateDomain.addContact(updateDomainContact);
		updateDomain.removeContact(updateDomainContact);
		EppAuthInfo authInfoDomain(EppAuthInfo::TYPE_PW, "fooBar");
		updateDomain.setNewAuthInfo(authInfoDomain);
		updateDomain.setClientTransactionId("CLINET-XID");
		cout << updateDomain.toString() << endl;
		parser.parse(updateDomain.toString());
		n = parser.getRootNode();
		EppCommandUpdate * newUpdateDomain = (EppCommandUpdate *) EppCommand::fromXML(*n);
		cout << newUpdateDomain->toString() << endl;
		delete newUpdateDomain;
	}
	{
		EppCommandUpdateHost updateHost("NS.DOMAIN.BIZ");
		EppStatus updateHostStatus(EppHost::STATUS_OK);
		updateHost.addStatus(updateHostStatus);
		updateHost.removeStatus(updateHostStatus);
		EppIpAddress updateHostIp("192.9.1.1");
		EppIpAddress updateHostIp1("192.9.1.1", EppIpAddress::TYPE_V6);
		updateHost.addAddress(updateHostIp);
		updateHost.removeAddress(updateHostIp1);
		updateHost.setNewName("NEWNS.DOMAIN.BIZ");
		updateHost.setClientTransactionId("CLINET-XID");
		cout << updateHost.toString() << endl;
		parser.parse(updateHost.toString());
		n = parser.getRootNode();
		EppCommandUpdate * newUpdateHost = (EppCommandUpdate *) EppCommand::fromXML(*n);
		cout << newUpdateHost->toString() << endl;
		delete newUpdateHost;
	}
	{
		EppAuthInfo authInfoSvcsub(EppAuthInfo::TYPE_PW, "fooBar");
		EppCommandUpdateSvcsub updateSvcsub("BIZLOCK-001");
		updateSvcsub.setUserId("myUserId");
		updateSvcsub.setNewRegistrant("NEW-REGISTRANT-123");
		EppStatus updateSvcsubStatus(EppSvcsub::STATUS_OK);
		updateSvcsub.addStatus(updateSvcsubStatus);
		updateSvcsub.removeStatus(updateSvcsubStatus);
		EppSvcsubParam updateSvcsubParam(BizLock::PARAM_LOCKSTATE, BizLock::VALUE_LOCKSTATE_OFF);
		updateSvcsub.addParam(updateSvcsubParam);
		updateSvcsub.removeParam(updateSvcsubParam);
		EppContactType updateSvcsubContact("CONTACT-1", EppSvcsub::CONTACT_TYPE_BILLING);
		updateSvcsub.addContact(updateSvcsubContact);
		updateSvcsub.removeContact(updateSvcsubContact);
		updateSvcsub.setNewAuthInfo(authInfoSvcsub);
		updateSvcsub.setAuthInfo(authInfoSvcsub);
		updateSvcsub.setClientTransactionId("CLINET-XID");
		cout << updateSvcsub.toString() << endl;
		parser.parse(updateSvcsub.toString());
		n = parser.getRootNode();
		EppCommandUpdate * newUpdateSvcsub = (EppCommandUpdate *) EppCommand::fromXML(*n);
		cout << newUpdateSvcsub->toString() << endl;
		delete newUpdateSvcsub;
	}

	return 0;
}
