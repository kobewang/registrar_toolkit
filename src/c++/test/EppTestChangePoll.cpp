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
#include "EppChangePollExt.hpp"

int runEppTestChangePoll(EppParser & parser);

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
	runEppTestChangePoll(*parser);

	delete 	parser;
	XMLPlatformUtils::Terminate();
}
#endif

int runEppTestChangePoll(EppParser & parser)
{
	// tests on helper classes define as part of ChangePollExtension implementation
	{
		//EppReason test
		EppReason* r = new EppReason();
		r->setReason("test reason");
		r->setLanguage("en-US");
		cout<<endl <<"EppReason=" << r->toString();
		delete r;
		
		//EppChangePollOperation test
		EppChangePollOperation* op = new EppChangePollOperation();
		op->setOperation("delete");
		op->setSubOperation("purge");
		cout<<endl <<"EppChangePollOperation=" << op->toString();
		delete op;
	}

	DOMNode* n;

	EppDomain domain("DOMAIN.BIZ");
	domain.setRoid("TESTROID-12345");
	EppStatus st(EppDomain::STATUS_OK);
	domain.addStatus(st);
	domain.setClientId("testID");
	EppResult result(EppError::CODE_NO_ERROR);
	EppValueReason reason0("TestValue0", "TestReason0", "en-US");
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
	EppChangePollExt* ext = new EppChangePollExt();
	ext->setState("after");
	EppChangePollOperation *operation = new EppChangePollOperation();
	operation->setOperation("delete");
	operation->setSubOperation("purge");
	ext->setOperation(operation);
	ext->setWho("you");
	ext->setDate(time_t(0) + (30* 3600 * 24));
	ext->setSvTRID("x1y2z3");
	ext->setReason(new EppReason("Example-Reason-EN", "en-US"));

	rsp.addExtension(ext);

	DOMString orig = rsp.toString();

	parser.parse(orig);
	n = parser.getRootNode();
	EppResponse * newRsp = EppResponse::fromXML(*n);

	DOMString again = newRsp->toString();

	if ( orig.equals(again) )
	{
		std::cout<<endl
			 <<"Original=" <<endl<<orig<<endl
			 <<"PASSED: "  <<__FILE__<<endl;
	}
	else
	{
		std::cout<<endl
			 <<"Original=" <<endl<<orig<<endl
			 <<"Again="    <<endl<<again<<endl
		  	 <<"FAILED : " <<__FILE__<<endl;
	}
	delete newRsp;

	return 0;
}
