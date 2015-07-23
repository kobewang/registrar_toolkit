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
#include <util/PlatformUtils.hpp>
#include <parsers/DOMParser.hpp>
#include <dom/DOM.hpp>

#include <string.h>
#include <stdlib.h>       
#include <iostream.h>     

#include "EppEntity.hpp"
#include "EppE164.hpp"
#include "EppParser.hpp"
#include "EppUtil.hpp"

#include "EppCommand.hpp"
#include "EppCommandUpdateXriAuthority.hpp"
#include "EppCommandCheckXriCodeString.hpp"
#include "EppCommandInfoXriCodeString.hpp"
#include "EppCommandUpdateXriCodeString.hpp"
#include "EppResponseDataCheckXriCodeString.hpp"
#include "EppXriAuthority.hpp"
#include "EppXriCodeString.hpp"
#include "EppStatus.hpp"
#include "EppXriResolutionPattern.hpp"

int main( int argc, char ** argv ) {
	try {
		XMLPlatformUtils::Initialize();
	} catch( const XMLException& e ) {
		cerr	<< "Error during Xerces-c Initialization\n" << "  Exception message:" << DOMString(e.getMessage()) << endl;
		return 1;
	}

	EppParser parser;
	DOM_Node n;

//	EppXriTrustee trustee;
//	EppXriSocialData esd;
	EppXriContactData cd;
	cd.setContactHandle("nav-admin-con");
	cd.setContactType(adminContact);

	EppXriAuthority auth("auth-id-dsdsa");
	auth.setIsEscrow(true);
	auth.setIsContact(true);
	auth.addContactHandle(cd);
	cd.setContactHandle("nav-billing-con");
	cd.setContactType(billingContact);
	auth.addContactHandle(cd);
	cd.setContactHandle("nav-tech-con");
	cd.setContactType(techContact);
	auth.addContactHandle(cd);
	cd.setContactHandle("nav-tech2-con");
	cd.setContactType(techContact);
	auth.addContactHandle(cd);
	cd.setContactHandle("nav-tech3-con");
	cd.setContactType(techContact);
	auth.addContactHandle(cd);
	cd.setContactHandle("nav-billing2-con");
	cd.setContactType(billingContact);
	auth.addContactHandle(cd);
	cout << auth.toString() << endl;
	parser.parse(auth.toString());
	n = parser.getDocument().getElementsByTagName("xriAU:xriAU").item(0);
	if( n != null ) {
		EppXriAuthority *_del = EppXriAuthority::fromXML(n);
		cout<<_del->toString()<<endl;
		delete _del;
	} else {
		cout<<"We got root node as NULL"<<endl;
	}
	EppCommandUpdateXriAuthority upAuth("auth-nav-123","123");
	upAuth.addContact(cd);
	cd.setContactHandle("nav-admin-con");
	cd.setContactType(adminContact);
	upAuth.removeContact(cd);
	cout<<upAuth.toString()<<endl;
	parser.parse(upAuth.toString());
	n = parser.getRootNode();
	if( n != null ) {
		EppCommandUpdateXriAuthority* upA = (EppCommandUpdateXriAuthority*) EppCommand::fromXML(n);
		cout<<upA->toString()<<endl;
		delete upA;
	} else {
		cout<<"Root Node is null for EppCommandUpdateXriAuthority"<<endl;
	}

	EppXriCodeString cs;
	EppStatus _lstat_("ok");
	cs.setParentIName("@mc*neustar");
	cs.setCodeString("@mc*neustar*campaign");
	cs.setRoid("test-IS-ON-9090");
	cs.setClid("cl-cur-date-time-123-sec");
	cs.setCrid("cr-cur-date-time-abc-text");
	cs.setCrdate("2005-05-03T22:00:00.0Z");
	cs.setUpdatedId("50-10000");
	cs.setUpdatedDate("2009-10-05T09:00:00.0Z");
	cs.setExpiryDate("2010-01-01T00:00:00.0Z");
	cs.setTransactionDate("2005-05-04T09:00:00.0Z");
	cs.setAuthId("AUTHORITY");
	cs.setUsedRUnits(45);
	cs.setTotalRUnits(100);
	cs.setUsedMCap(100);
	cs.setTotalMCap(100);
	cs.setResStartDate("2005-05-03T22:00:00.0Z");
	cs.setResEndDate("2010-01-01T00:00:00.0Z");
	cs.setStatus(_lstat_);

	EppXriCodeStringExt ext;
	ext.addVas("UseServiceMark", "Y");
	ext.addVas("IPManagement", "Y");
	ext.addVas("Location", "Y");
	ext.addVas("UMD","Y");
	ext.addVas("InternationReach","Y");
	ext.addVas("FrooToEndUser","Y");

	cs.addValueAddedService(ext);
	EppAuthInfo ai;
	ai.setType("pw");
	ai.setValue("guesswhat");
	cs.setAuthInfo(ai);

	EppXriResolutionPattern res;
	res.setPatternId("every-day");
	cs.addResPattern(res);
	res.setPatternId("every-monday");
	res.setDayOfWeek("1");
	cs.addResPattern(res);

	cout<<cs.toString()<<endl;

	cout<<"*************************** Commands ***************************"<<endl;
	EppCommandCheckXriCodeString ckCS;
	DOMString cs_str;
	cs_str = DOMString("@mc*neustar*pepsi");
	ckCS.addCodeStringId(cs_str);
	cs_str = DOMString("@mc*neustar*coke");
	ckCS.addCodeStringId(cs_str);
	cs_str = DOMString("@mc*neustar*pepsi*mirinda");
	ckCS.addCodeStringId(cs_str);
	cs_str = DOMString("@mc*neustar*pepsi*sprint");
	ckCS.addCodeStringId(cs_str);
	cout<<ckCS.toString()<<endl;

	EppCommandInfoXriCodeString inCS(cs_str,"yyuuyyuu");
//	inCS.setClientTransactionId("hhhhgghgh");
	cout<<inCS.toString()<<endl;

	EppCommandUpdateXriCodeString upCS("@mc*neustar*pepsi*sprint","neu-xx-level-one-two");
	EppStatus stat("clientInGoodHolding");
	upCS.addNewStatus(stat);
	stat.setStatus("Ok");
	upCS.addNewStatus(stat);
	stat.setStatus("pendingDelete");
	upCS.addRemStatus(stat);
	stat.setStatus("clientHold");
	upCS.addRemStatus(stat);
	stat.setStatus("serverHold");
	upCS.addRemStatus(stat);
	upCS.addNewRecPattterns(res);
	res.setPatternId("every-day");
	res.setDayOfWeek("*");
	upCS.addRemRecPattern(res);
	upCS.setResolutionStartDate("2009-10-05T09:00:00.0Z", "delete");
	upCS.setResolutionEndDate("2019-10-05T09:00:00.0Z", "add");
	upCS.updateTotalRUnits(5000,"dec",10000);
	upCS.updateTotalRUnits(1000,"add",10000);
	upCS.updateTotalMUnits(1000,"add",10000);
	upCS.updateTotalMUnits(5000,"dec",10000);
	cout<<upCS.toString()<<endl;

/*
	cs.setPeriod();
*/
/*
	n = parser.getDocument().getElementsByTagName("").item(0);
	EppCredsOptions * newCredsOpt = EppCredsOptions::fromXML(n);
	cout << newCredsOpt->toString() << endl;
	delete auth;
*/
/*
	EppCommandDeleteHost delHost("NS.DOMAIN.BIZ");
	delHost.setClientTransactionId("CLIENT-XID");
	cout << delHost.toString() << endl;
	parser.parse(delHost.toString());
	n = parser.getRootNode();
	EppCommandDelete * newDelHost = (EppCommandDelete *) EppCommand::fromXML(n);
	cout << newDelHost->toString() << endl;
	delete newDelHost;

	EppCommandInfoHost infoHost("DOMAIN.BIZ");
	infoHost.setClientTransactionId("CLINET-XID");
	cout << infoHost.toString() << endl;
	parser.parse(infoHost.toString());
	n = parser.getRootNode();
	EppCommandInfo * newInfoHost = (EppCommandInfo *) EppCommand::fromXML(n);
	cout << newInfoHost->toString() << endl;
	delete newInfoHost;
*/
	XMLPlatformUtils::Terminate();
	return 0;
}
