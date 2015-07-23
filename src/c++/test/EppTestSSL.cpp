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

#if       defined(win32)        /* { */

#include <windows.h>
#include <winsock.h>
#include <io.h>
#include <time.h>
#include <errno.h>
#include <fcntl.h>

#ifndef	   EINPROGRESS		/* { */
#define	   EINPROGRESS		WSAEINPROGRESS
#endif	/* EINPROGRESS	*/ 	/* } */
#ifndef	   EALREADY		/* { */
#define	   EALREADY		WSAEALREADY
#endif	/* EALREADY	*/ 	/* } */
#ifndef	   EISCONN		/* { */
#define	   EISCONN		WSAEISCONN
#endif	/* EISCONN	*/ 	/* } */

#else  /* defined(win32) */     /* } { */

#include <unistd.h>
#include <errno.h>
#include <fcntl.h>
#include <netdb.h>
#include <sys/types.h>
#include <sys/ioctl.h>
#include <sys/socket.h>
#include <netinet/in.h>

#endif /* defined(win32) */     /* } */

#include <xercesc/parsers/XercesDOMParser.hpp>

#include "openssl/rand.h"

#include "EppSessionTcp.hpp"
#include "EppChannelTcp.hpp"
#include "EppAddress.hpp"
#include "EppContactData.hpp"
#include "EppContactType.hpp"
#include "EppCommandCreate.hpp"
#include "EppCommandPoll.hpp"
#include "EppResponse.hpp"
#include "EppContact.hpp"
#include "EppDomain.hpp"
#include "EppHost.hpp"
#include "EppSvcsub.hpp"

#ifdef XRI_TEST
#include "EppXriAuthority.hpp"
#include "EppXriName.hpp"
#include "EppXriNumber.hpp"
#include "EppXriService.hpp"
#include "EppResponseDataCreateXriAuthority.hpp"
#include "EppResponseDataCreateXriName.hpp"
#include "EppResponseDataCreateXriNumber.hpp"
#include "EppResponseDataCreateXriService.hpp"
#include "EppResponseDataCheckXriAuthority.hpp"
#include "EppResponseDataCheckXriName.hpp"
#include "EppResponseDataCheckXriNumber.hpp"
#include "EppResponseDataCheckXriService.hpp"
#include "EppResponseDataTransferXriAuthority.hpp"
#include "EppResponseDataTransferXriName.hpp"
#include "EppCommandRenewXriName.hpp"
#include "EppCommandRenewXriNumber.hpp"
#include "EppCommandRenewXriService.hpp"
#include "EppCommandTransferXriAuthority.hpp"
#include "EppCommandTransferXriName.hpp"
#include "EppCommandCheckXriAuthority.hpp"
#include "EppCommandInfoXriAuthority.hpp"
#include "EppCommandDeleteXriAuthority.hpp"
#include "EppCommandUpdateXriAuthority.hpp"
#include "EppCommandCheckXriName.hpp"
#include "EppCommandInfoXriName.hpp"
#include "EppCommandUpdateXriName.hpp"
#include "EppCommandDeleteXriName.hpp"
#include "EppCommandCheckXriNumber.hpp"
#include "EppCommandInfoXriNumber.hpp"
#include "EppCommandUpdateXriNumber.hpp"
#include "EppCommandDeleteXriNumber.hpp"
#include "EppCommandCheckXriService.hpp"
#include "EppCommandInfoXriService.hpp"
#include "EppCommandUpdateXriService.hpp"
#include "EppCommandDeleteXriService.hpp"
#endif

#define TEST_XRI_AUTH1		"=!(!!1002!2000.2)"
#define TEST_XRI_AUTH2		"FOO-BAR-ID"
#define TEST_XRI_AGENT1		"@!(!!0001)"
#define TEST_XRI_NAME1		"@foo"
#define TEST_XRI_NAME2		"=bar"
#define TEST_XRI_NUM1		"!!1234!5678"
#define TEST_XRI_NUM2		"!!1111!2222"
#define TEST_XRI_SRV1		"+service1"
#define TEST_XRI_SRV2		"+service2"


void domInit()
{
	try
	{
		XMLPlatformUtils::Initialize();
	}
	catch( const XMLException& e )
	{
                cout    << "Error during Xerces-c Initialization\n"
                        << "  Exception message:"
                        << DOMString(e.getMessage()) << endl;
		exit(0);
	}
}

void domFinish()
{
	XMLPlatformUtils::Terminate();
}

// Generate Unique Client ID

char * getClientId() 
{
	static int count = 1000;
	static char buf[100];
	(void) sprintf(buf, "ABC-%d", count++);
	return &buf[0];
}

// Dump error

void dumpError( const char * msg )
{
	cout << msg << endl;
	domFinish();
	exit(1);
}

// Dump session

void dumpSession( EppSessionTcp * session )
{
	if( ! session->useTLS() )
	{
		cout << "Session does not use TLS" << endl;
		return;
	}
	SSL * ssl = session->getSSL();
	if( ssl == null )
	{
		cout << "TLS Session is established" << endl;
		return;
	}
	cout << "SSL/TLS: Version " << SSL_get_version(ssl) << endl;

	SSL_CIPHER * ciph = (SSL_CIPHER *)SSL_get_current_cipher(ssl);
	if( ciph == null )
	{
		cout << "Cipher cannot be found" << endl;
	}
	else
	{
		cout << "Cipher : Version " << SSL_CIPHER_get_version(ciph);
		cout << " " << SSL_CIPHER_get_name(ciph) << endl;
	}
	X509 * cert = SSL_get_peer_certificate(ssl);
	if( cert == null )
	{
		cout << "Cannot get peer certificate" << endl;
		return;
	}
	char * str;
	str = X509_NAME_oneline (X509_get_subject_name(cert), 0, 0);
	if( str == null )
	{
		cout << "Subject: " << "none" << endl;
	}
	else
	{
		cout << "Subject: " << str << endl;
		free(str);
	}
	str = X509_NAME_oneline (X509_get_issuer_name(cert), 0, 0);
	if( str == null )
	{
		cout << "Issuer : " << "none" << endl;
	}
	else
	{
		cout << "Issuer : " << str << endl;
		free(str);
	}
	EVP_PKEY * pkey = X509_get_pubkey(cert);
	if( pkey == null )
	{
		cout << "Pubkey : " << "none" << endl;
	}
	else if( (pkey->type == EVP_PKEY_RSA) && (pkey->pkey.rsa != null) )
	{
		cout << "Pubkey : " << BN_num_bits(pkey->pkey.rsa->n);
		cout << " bit RSA" << endl;
	}
	else if( (pkey->type == EVP_PKEY_DSA) && (pkey->pkey.dsa != null) )
	{
		cout << "Pubkey : " << BN_num_bits(pkey->pkey.dsa->p);
		cout << " bit DSA" << endl;
	}
	EVP_PKEY_free(pkey);
	X509_free(cert);
}

// Check response

void checkResponse( EppResponse * rsp )
{
	if( rsp != null )
	{
		cout << rsp << endl;
		delete rsp;
	}
	else
	{
		cout << "Response is null" << endl;
	}
}

void testContact( EppChannel * channel )
{
	EppAddress addr;
	addr.setStreet(0, "101 Park Street");
	addr.setStreet(1, "Suite 101");
	addr.setCity("Sterling");
	addr.setState("VA");
	addr.setPostalCode("20166");
	addr.setCountryCode("US");
	EppContactData ascii("FooBar, Inc", NULLXS, addr);
	EppContactData i15d("Foo Bar", "FooBar, Inc", addr);

	EppContact contact("CONTACT-12345");
	contact.setContactDataAscii(ascii);
	contact.setContactDataAscii(i15d);
	contact.setVoice("+1.7035551234", "9999");
	contact.setEmail("mail@foobar.com");
	EppAuthInfo auth(EppAuthInfo::TYPE_PW, "2fooBar");
	contact.setAuthInfo(auth);

	EppResponse * rsp;

	EppCommandCreate cmdCreate(&contact, getClientId());
	cout << cmdCreate << endl;
	rsp = channel->send(cmdCreate);
	checkResponse(rsp);

	EppCommandCheckContact cmdCheck(getClientId());
	cmdCheck.add("ROID-1");
	cmdCheck.add("ROID-2");
	cout << cmdCheck << endl;
	rsp = channel->send(cmdCheck);
	checkResponse(rsp);

	EppCommandInfoContact cmdInfo("ROID-1", getClientId());
	cout << cmdInfo << endl;
	rsp = channel->send(cmdInfo);
	checkResponse(rsp);

	EppCommandDeleteContact cmdDelete("ROID-1", getClientId());
	cout << cmdDelete << endl;
	rsp = channel->send(cmdDelete);
	checkResponse(rsp);

	EppCommandTransferContact cmdTransferQ("ROID-1", getClientId());
	cmdTransferQ.setAuthInfo(auth);
	cmdTransferQ.setOperation(EppCommandTransfer::OPTYPE_QUERY);
	cout << cmdTransferQ << endl;
	rsp = channel->send(cmdTransferQ);
	checkResponse(rsp);

	EppCommandTransferContact cmdTransfer("ROID-1", getClientId());
	cmdTransfer.setAuthInfo(auth);
	cmdTransfer.setOperation(EppCommandTransfer::OPTYPE_REQUEST);
	cout << cmdTransfer << endl;
	rsp = channel->send(cmdTransfer);
	checkResponse(rsp);

	EppCommandUpdateContact cmdUpdate("ROID-1", getClientId());
	EppAuthInfo newAuthInfo(EppAuthInfo::TYPE_PW, "3fooBar");
	cmdUpdate.setNewAuthInfo(newAuthInfo);
	cout << cmdUpdate << endl;
	rsp = channel->send(cmdUpdate);
	checkResponse(rsp);
}

void testDomain( EppChannel * channel )
{
	EppAuthInfo auth(EppAuthInfo::TYPE_PW, "2fooBar");

	EppDomain domain("example1.biz");
	domain.addContact("ROID-1", EppDomain::CONTACT_TYPE_ADMIN);
	domain.addContact("ROID-2", EppDomain::CONTACT_TYPE_TECH);
	domain.addNameServer("ns1.example1.biz");
	domain.setPeriod(EppPeriod(2, EppPeriod::UNIT_YEAR));
	domain.setRegistrant("ROID-3");
	domain.setAuthInfo(EppAuthInfo(EppAuthInfo::TYPE_PW, "2fooBar"));
	EppResponse * rsp;

	EppCommandCreate cmdCreate(&domain, getClientId());
	cout << cmdCreate << endl;
	rsp = channel->send(cmdCreate);
	checkResponse(rsp);

	EppCommandCheckDomain cmdCheck(getClientId());
	cmdCheck.add("example1.biz");
	cmdCheck.add("example2.biz");
	cout << cmdCheck << endl;
	rsp = channel->send(cmdCheck);
	checkResponse(rsp);

	EppCommandInfoDomain cmdInfo("example1.biz", getClientId());
	cout << cmdInfo << endl;
	rsp = channel->send(cmdInfo);
	checkResponse(rsp);

	EppCommandDeleteDomain cmdDelete("example1.biz", getClientId());
	cout << cmdDelete << endl;
	rsp = channel->send(cmdDelete);
	checkResponse(rsp);

	EppCommandTransferDomain cmdTransferQ("example1.biz", getClientId());
	cmdTransferQ.setAuthInfo(auth);
	cmdTransferQ.setOperation(EppCommandTransfer::OPTYPE_QUERY);
	cout << cmdTransferQ << endl;
	rsp = channel->send(cmdTransferQ);
	checkResponse(rsp);

	EppCommandTransferDomain cmdTransfer("example1.biz", getClientId());
	cmdTransfer.setAuthInfo(auth);
	cmdTransfer.setOperation(EppCommandTransfer::OPTYPE_REQUEST);
	cout << cmdTransfer << endl;
	rsp = channel->send(cmdTransfer);
	checkResponse(rsp);

	EppCommandUpdateDomain cmdUpdate("example1.biz", getClientId());
	EppAuthInfo newAuthInfo(EppAuthInfo::TYPE_PW, "3fooBar");
	cmdUpdate.setNewAuthInfo(newAuthInfo);
	cmdUpdate.removeNameServer("ns1.example1.biz");
	EppContactType ctype1("ROID-1", EppDomain::CONTACT_TYPE_ADMIN);
	EppContactType ctype2("ROID-1", EppDomain::CONTACT_TYPE_BILLING);
	cmdUpdate.removeContact(ctype1);
	cmdUpdate.addContact(ctype2);
	cout << cmdUpdate << endl;
	rsp = channel->send(cmdUpdate);
	checkResponse(rsp);

	EppCommandRenewDomain cmdRenew("example1.biz", getClientId());
	EppPeriod newPeriod(4, EppPeriod::UNIT_YEAR);
	cmdRenew.setPeriod(newPeriod);
	cmdRenew.setCurrentExpireDate(time(0) + 30 * 24 * 3600);
	cout << cmdRenew << endl;
	rsp = channel->send(cmdRenew);
	checkResponse(rsp);
}

void testHost( EppChannel * channel )
{
	EppAuthInfo auth(EppAuthInfo::TYPE_PW, "2fooBar");

	EppHost host("ns1.example1.biz");
	host.addAddress(EppIpAddress("194.34.23.45"));
	host.addAddress(EppIpAddress("194.34.67.78", EppIpAddress::TYPE_V4));
	EppResponse * rsp;

	EppCommandCreate cmdCreate(&host, getClientId());
	cout << cmdCreate << endl;
	rsp = channel->send(cmdCreate);
	checkResponse(rsp);

	EppCommandCheckHost cmdCheck(getClientId());
	cmdCheck.add("ns1.example1.biz");
	cmdCheck.add("ns2.example2.biz");
	cout << cmdCheck << endl;
	rsp = channel->send(cmdCheck);
	checkResponse(rsp);

	EppCommandInfoHost cmdInfo("ns1.example1.biz", getClientId());
	cout << cmdInfo << endl;
	rsp = channel->send(cmdInfo);
	checkResponse(rsp);

	EppCommandDeleteHost cmdDelete("ns1.example1.biz", getClientId());
	cout << cmdDelete << endl;
	rsp = channel->send(cmdDelete);
	checkResponse(rsp);

	EppCommandUpdateHost cmdUpdate("ns1.example1.biz", getClientId());
	EppIpAddress addr1("198.24.34.56");
	EppIpAddress addr2("::FFFF:204.34.56.98", EppIpAddress::TYPE_V6);
	cmdUpdate.addAddress(addr1);
	cmdUpdate.addAddress(addr2);
	cmdUpdate.setNewName("ns2.example1.biz");
	cout << cmdUpdate << endl;
	rsp = channel->send(cmdUpdate);
	checkResponse(rsp);
}

void testSvcsub( EppChannel * channel )
{
	EppAuthInfo auth(EppAuthInfo::TYPE_PW, "2fooBar");

	EppSvcsub svcsub("BIZLOCK-001");
	svcsub.addContact("ROID-1", EppSvcsub::CONTACT_TYPE_ADMIN);
	svcsub.addContact("ROID-2", EppSvcsub::CONTACT_TYPE_TECH);
	svcsub.addParam(BizLock::PARAM_DOMAIN, "example.biz");
	svcsub.addParam(BizLock::PARAM_DOMAIN_AUTHINFO, "password");
	svcsub.addParam(BizLock::PARAM_BIZACCOUNT, "myBizAccount");
	svcsub.addParam(BizLock::PARAM_LOCKTYPE, BizLock::VALUE_LOCKTYPE_HARD);
	svcsub.setPeriod(EppPeriod(2, EppPeriod::UNIT_YEAR));
	svcsub.setRegistrant("ROID-3");
	svcsub.setAuthInfo(EppAuthInfo(EppAuthInfo::TYPE_PW, "2fooBar"));
	EppResponse * rsp;

	EppCommandCreate cmdCreate(&svcsub, getClientId());
	cout << cmdCreate << endl;
	rsp = channel->send(cmdCreate);
	checkResponse(rsp);

	EppCommandCheckSvcsub cmdCheck(getClientId());
	cmdCheck.add("BIZACCOUNT0");
	cmdCheck.add("BIZLOCK-001");
	cout << cmdCheck << endl;
	rsp = channel->send(cmdCheck);
	checkResponse(rsp);

	EppCommandInfoSvcsub cmdInfo("BIZLOCK-001", getClientId());
	cmdInfo.setUserId("myUserId");
	cmdInfo.setAuthInfo(auth);
	cout << cmdInfo << endl;
	rsp = channel->send(cmdInfo);
	checkResponse(rsp);

	EppCommandDeleteSvcsub cmdDelete("BIZLOCK-001", getClientId());
	cmdDelete.setUserId("myUserId");
	cmdDelete.setAuthInfo(auth);
	cout << cmdDelete << endl;
	rsp = channel->send(cmdDelete);
	checkResponse(rsp);

	EppCommandTransferSvcsub cmdTransferQ("BIZLOCK-001", getClientId());
	cmdTransferQ.setUserId("myUserId");
	cmdTransferQ.setAuthInfo(auth);
	cmdTransferQ.setOperation(EppCommandTransfer::OPTYPE_QUERY);
	cout << cmdTransferQ << endl;
	rsp = channel->send(cmdTransferQ);
	checkResponse(rsp);

	EppCommandTransferSvcsub cmdTransfer("BIZLOCK-001", getClientId());
	cmdTransfer.setUserId("myUserId");
	cmdTransfer.setAuthInfo(auth);
	cmdTransfer.setOperation(EppCommandTransfer::OPTYPE_REQUEST);
	cout << cmdTransfer << endl;
	rsp = channel->send(cmdTransfer);
	checkResponse(rsp);

	EppCommandUpdateSvcsub cmdUpdate("BIZLOCK-001", getClientId());
	EppAuthInfo newAuthInfo(EppAuthInfo::TYPE_PW, "3fooBar");
	cmdUpdate.setUserId("myUserId");
	cmdUpdate.setAuthInfo(auth);
	cmdUpdate.setNewAuthInfo(newAuthInfo);
	EppContactType ctype1("ROID-1", EppSvcsub::CONTACT_TYPE_ADMIN);
	EppContactType ctype2("ROID-1", EppSvcsub::CONTACT_TYPE_BILLING);
	cmdUpdate.removeContact(ctype1);
	cmdUpdate.addContact(ctype2);
	EppSvcsubParam p1(BizLock::PARAM_LOCKSTATE, BizLock::VALUE_LOCKSTATE_ON);
	EppSvcsubParam p2(BizLock::PARAM_LOCKSTATE, BizLock::VALUE_LOCKSTATE_OFF);
	cmdUpdate.removeParam(p1);
	cmdUpdate.addParam(p2);
	cout << cmdUpdate << endl;
	rsp = channel->send(cmdUpdate);
	checkResponse(rsp);

	EppCommandRenewSvcsub cmdRenew("BIZLOCK-001", getClientId());
	cmdRenew.setUserId("myUserId");
	cmdRenew.setAuthInfo(auth);
	EppPeriod newPeriod(4, EppPeriod::UNIT_YEAR);
	cmdRenew.setPeriod(newPeriod);
	cmdRenew.setCurrentExpireDate(time(0) + 30 * 24 * 3600);
	cout << cmdRenew << endl;
	rsp = channel->send(cmdRenew);
	checkResponse(rsp);
}

#ifdef XRI_TEST
void testXriAuthority( EppChannel * channel )
{
	EppAuthInfo auth(EppAuthInfo::TYPE_PW, "2fooBar");

	EppXriAuthority xriAU("=!(!!1002!2000.2)");
	EppXriTrustee t;
	t.setAuthorityId("=!(!!1111!2222)");
	xriAU.setEscrowAgent(t);
	xriAU.setAuthInfo(EppAuthInfo(EppAuthInfo::TYPE_PW, "2fooBar"));

	EppResponse * rsp;

	EppCommandCreate cmdCreate(&xriAU, getClientId());
	cout << cmdCreate << endl;
	rsp = channel->send(cmdCreate);
	checkResponse(rsp);

	EppCommandCheckXriAuthority cmdCheck(getClientId());
	cmdCheck.add(TEST_XRI_AUTH1);
	cmdCheck.add(TEST_XRI_AUTH2);
	cout << cmdCheck << endl;
	rsp = channel->send(cmdCheck);
	checkResponse(rsp);

	EppCommandInfoXriAuthority cmdInfo(TEST_XRI_AUTH1, getClientId());
	cout << cmdInfo << endl;
	rsp = channel->send(cmdInfo);
	checkResponse(rsp);

	EppCommandTransferXriAuthority cmdTransferQ(TEST_XRI_AUTH1, getClientId());
	cmdTransferQ.setAuthInfo(auth);
	cmdTransferQ.setOperation(EppCommandTransfer::OPTYPE_QUERY);
	cout << cmdTransferQ << endl;
	rsp = channel->send(cmdTransferQ);
	checkResponse(rsp);

	EppCommandTransferXriAuthority cmdTransfer(TEST_XRI_AUTH1, getClientId());
	cmdTransfer.setAuthInfo(auth);
	cmdTransfer.setTargetAuthorityId(TEST_XRI_AUTH2);
	cmdTransfer.setOperation(EppCommandTransfer::OPTYPE_REQUEST);
	cout << cmdTransfer << endl;
	rsp = channel->send(cmdTransfer);
	checkResponse(rsp);

	EppCommandUpdateXriAuthority cmdUpdate(TEST_XRI_AUTH1, getClientId());
	EppAuthInfo newAuthInfo(EppAuthInfo::TYPE_PW, "3fooBar");
	cmdUpdate.setNewAuthInfo(newAuthInfo);
	t.setAuthorityId(TEST_XRI_AGENT1);
	cmdUpdate.setNewEscrowAgent(&t);
	cout << cmdUpdate << endl;
	rsp = channel->send(cmdUpdate);
	checkResponse(rsp);

	EppCommandDeleteXriAuthority cmdDelete(TEST_XRI_AUTH1, getClientId());
	cout << cmdDelete << endl;
	rsp = channel->send(cmdDelete);
	checkResponse(rsp);

}


void testXriName( EppChannel * channel )
{
	EppAuthInfo auth(EppAuthInfo::TYPE_PW, "2fooBar");

	EppXriName iname(TEST_XRI_NAME1);
	iname.setAuthorityId(TEST_XRI_AUTH1);
	EppPeriod newPeriod(1, EppPeriod::UNIT_YEAR);
	iname.setPeriod(newPeriod);

	EppResponse * rsp;

	EppCommandCreate cmdCreate(&iname, getClientId());
	cout << cmdCreate << endl;
	rsp = channel->send(cmdCreate);
	checkResponse(rsp);

	EppCommandCheckXriName cmdCheck(getClientId());
	cmdCheck.add(TEST_XRI_NAME1);
	cmdCheck.add(TEST_XRI_NAME2);
	cout << cmdCheck << endl;
	rsp = channel->send(cmdCheck);
	checkResponse(rsp);

	EppCommandInfoXriName cmdInfo(TEST_XRI_NAME1, getClientId());
	cout << cmdInfo << endl;
	rsp = channel->send(cmdInfo);
	checkResponse(rsp);

	EppCommandTransferXriName cmdTransferQ(TEST_XRI_NAME1, newPeriod, getClientId());
	cmdTransferQ.setAuthInfo(auth);
	cmdTransferQ.setOperation(EppCommandTransfer::OPTYPE_QUERY);
	cout << cmdTransferQ << endl;
	rsp = channel->send(cmdTransferQ);
	checkResponse(rsp);

	EppCommandTransferXriName cmdTransfer(TEST_XRI_NAME1, getClientId());
	cmdTransfer.setTarget(TEST_XRI_AUTH1, auth);
	cmdTransfer.setOperation(EppCommandTransfer::OPTYPE_REQUEST);
	cout << cmdTransfer << endl;
	rsp = channel->send(cmdTransfer);
	checkResponse(rsp);

	EppCommandUpdateXriName cmdUpdate(TEST_XRI_NAME1, getClientId());
	cmdUpdate.addStatus("clientHold");
	cout << cmdUpdate << endl;
	rsp = channel->send(cmdUpdate);
	checkResponse(rsp);

	EppCommandDeleteXriName cmdDelete(TEST_XRI_NAME1, getClientId());
	cout << cmdDelete << endl;
	rsp = channel->send(cmdDelete);
	checkResponse(rsp);

}


void testXriNumber( EppChannel * channel )
{
	EppAuthInfo auth(EppAuthInfo::TYPE_PW, "2fooBar");

	EppXriNumber inumber(TEST_XRI_NUM1);
	inumber.setAuthorityId(TEST_XRI_AUTH1);
	EppPeriod newPeriod(1, EppPeriod::UNIT_YEAR);
	inumber.setPeriod(newPeriod);

	EppResponse * rsp;

	EppCommandCreate cmdCreate(&inumber, getClientId());
	cout << cmdCreate << endl;
	rsp = channel->send(cmdCreate);
	checkResponse(rsp);

	EppCommandCheckXriNumber cmdCheck(getClientId());
	cmdCheck.add(TEST_XRI_NUM1);
	cmdCheck.add(TEST_XRI_NUM2);
	cout << cmdCheck << endl;
	rsp = channel->send(cmdCheck);
	checkResponse(rsp);

	EppCommandInfoXriNumber cmdInfo(TEST_XRI_NUM1, getClientId());
	cout << cmdInfo << endl;
	rsp = channel->send(cmdInfo);
	checkResponse(rsp);

	EppCommandUpdateXriNumber cmdUpdate(TEST_XRI_NUM1, getClientId());
	cmdUpdate.addStatus("clientHold");
	cout << cmdUpdate << endl;
	rsp = channel->send(cmdUpdate);
	checkResponse(rsp);

	EppCommandDeleteXriNumber cmdDelete(TEST_XRI_NUM1, getClientId());
	cout << cmdDelete << endl;
	rsp = channel->send(cmdDelete);
	checkResponse(rsp);

}


void testXriService( EppChannel * channel )
{
	EppAuthInfo auth(EppAuthInfo::TYPE_PW, "2fooBar");

	EppXriService srv(TEST_XRI_SRV1);
	srv.setAuthorityId(TEST_XRI_AUTH1);
	EppPeriod newPeriod(1, EppPeriod::UNIT_YEAR);
	srv.setPeriod(newPeriod);

	EppResponse * rsp;

	EppCommandCreate cmdCreate(&srv, getClientId());
	cout << cmdCreate << endl;
	rsp = channel->send(cmdCreate);
	checkResponse(rsp);

	EppCommandCheckXriService cmdCheck(getClientId());
	cmdCheck.add(TEST_XRI_SRV1);
	cmdCheck.add(TEST_XRI_SRV2);
	cout << cmdCheck << endl;
	rsp = channel->send(cmdCheck);
	checkResponse(rsp);

	EppCommandInfoXriService cmdInfo(TEST_XRI_SRV1, getClientId());
	cout << cmdInfo << endl;
	rsp = channel->send(cmdInfo);
	checkResponse(rsp);

	EppCommandUpdateXriService cmdUpdate(TEST_XRI_SRV1, getClientId());
	cmdUpdate.addStatus("clientHold");
	cout << cmdUpdate << endl;
	rsp = channel->send(cmdUpdate);
	checkResponse(rsp);

	EppCommandDeleteXriService cmdDelete(TEST_XRI_SRV1, getClientId());
	cout << cmdDelete << endl;
	rsp = channel->send(cmdDelete);
	checkResponse(rsp);

}

#endif

void testMain( EppChannel * channel )
{
	testContact(channel);
	testDomain(channel);
	testHost(channel);
	testSvcsub(channel);
#ifdef XRI_TEST
	testXriAuthority(channel);
	testXriName(channel);
	testXriNumber(channel);
	testXriService(channel);
#endif
	EppCommandPoll cmdPoll(getClientId());
	cout << cmdPoll << endl;
	EppResponse * rsp = channel->send(cmdPoll);
	checkResponse(rsp);
}

void testLogin( EppChannel * channel, EppGreeting * greeting )
{
	EppServiceMenu * menu = greeting->getServiceMenu();
	EppCommandLogin login(*menu);
	EppCreds creds("testuser", "testpass");
	login.setCreds(creds);
	login.setClientTransactionId(getClientId());
	EppResponse * rsp = channel->start(login);
	if( rsp == null )
	{
		dumpError("Cannot login\n");
	}
	if( ! rsp->success() )
	{
		cout << rsp << endl;
		dumpError("Cannot login\n");
	}
	cout << rsp << endl;
	delete rsp;
}

void test( char * host, int port, int loop, bool usetls )
{
	EppSessionTcp session(usetls);

	char * files[3];
	files[0] = "testkeys.client.private.pem";
	files[1] = "testkeys.client.public.pem";
	files[2] = "testkeys.server.public.pem";

	session.init((void *) files);

	EppGreeting * greeting = session.connect(host, port);
	if( greeting == null )
	{
		ERR_print_errors_fp(stderr);
		dumpError(session.getException()); 
	}
	dumpSession(&session);
	EppChannel * channel = session.getChannel();
	if( channel == null )
	{
		dumpError("Cannot get channel\n");
	}

	testLogin(channel, greeting);
	delete greeting;

	for( int i = 0; i < loop; i++ )
	{
		if( loop > 1 )
		{
			cerr << "LOOP " << i << endl;
		}
		testMain(channel);
	}
	if( loop > 1 )
	{
		cerr << "LOOP DONE" <<endl;
	}

	EppResponse * rsp = channel->terminate();
	checkResponse(rsp);
	session.close();
}

int main( int argc, char * argv[] )
{

	char * host = "localhost";
	int    port = 10288;
	bool   usetls = true;
	if( argc > 1 )
	{
		host = argv[1];
	}
	if( argc > 2 )
	{
		port = atoi(argv[2]);
	}	
	if( argc > 3 )
	{
		if( strcmp(argv[3], "notls") == 0 )
		{
			usetls = false;
		}
	}
	int loop = 1;
	if( argc > 4 )
	{
		loop = atoi(argv[4]);
	}
#if       defined(HPUX) || defined(solaris)           /* { */
	{
		char buf[256];
		(void) memset(buf, 0xFF, sizeof(buf));
		RAND_seed(buf, 256);
	}
#endif /* defined(HPUX) || defined(solaris) */        /* } */

#if	   defined(win32)       /* { */
	static WSADATA wsa_data;
	static int     wsa_status;

	wsa_status = WSAStartup(MAKEWORD(0x1, 0x1), &wsa_data);
	if( wsa_status != 0 )
	{
		goto leave;
	}
#endif	/* defined(win32) */	/* } */
	domInit();
	test(host, port, loop, usetls);
	domFinish();

#if	   defined(win32)       /* { */
leave:
	if( wsa_status == 0 )
	{
		WSACleanup();
	}
#endif	/* defined(win32) */	/* } */

	return 0;
}
