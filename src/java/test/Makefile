#******************************************************************************
# The MIT License (MIT)
#  
# Copyright (c) 2015 Neustar Inc.
#  
# Permission is hereby granted, free of charge, to any person obtaining a copy
# of this software and associated documentation files (the "Software"), to deal
# in the Software without restriction, including without limitation the rights
# to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
# copies of the Software, and to permit persons to whom the Software is
# furnished to do so, subject to the following conditions:
#  
# The above copyright notice and this permission notice shall be included in
# all copies or substantial portions of the Software.
#  
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
# IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
# FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
# AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
# LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
# OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
# THE SOFTWARE.
#******************************************************************************
#
# $Id: Makefile,v 1.7 2013/09/03 14:28:01 achowdhu Exp $
#
# This is the Makefile for building EPP Java Implementation
#
# Makefile for directory . (test)

TARGETS = EppHelloTest.class \
          EppGreetingTest.class \
          EppLoginTest.class \
          EppLogoutTest.class \
          EppPollTest.class \
          EppCheckTest.class \
          EppCreateTest.class \
          EppDeleteTest.class \
          EppInfoTest.class \
          EppRenewTest.class \
          EppTransferTest.class \
          EppUpdateTest.class \
          EppResponseTest.class \
          EppSecDnsTest.class \
          EppTestXML.class \
          EppTestClient.class \
          EppTestServerTcp.class \
	  EppUnspecTest.class\
          EppTestExtension.class \
          EppTest.class \
          EppTestClientSMD.class \
          EppLaunch.class \
          #EppXriTest.class \

TESTS   = EppTestXML \
	  EppHelloTest \
          EppGreetingTest \
          EppLoginTest \
          EppLogoutTest \
          EppPollTest \
          EppCreateTest \
          EppCheckTest \
          EppDeleteTest \
          EppInfoTest \
          EppRenewTest \
          EppTransferTest \
          EppUpdateTest \
          EppResponseTest \
	  EppUnspecTest\
          EppTestExtension \
          EppSecDnsTest \
          EppTestClientSMD \
          EppLaunch \
          #EppXriTest \

DIRS =
TOP = ..
#CLASSPATH=.
include $(TOP)/src/Makefile.incl

XSDS = epp-1.0.xsd eppcom-1.0.xsd contact-1.0.xsd domain-1.0.xsd host-1.0.xsd \
       svcsub-1.0.xsd neulevel-1.0.xsd obj1ext-1.0.xsd secDNS-1.0.xsd \
       xriCommon-1.0.xsd xriAU-1.0.xsd xriINU-1.0.xsd xriINA-1.0.xsd xriISV-1.0.xsd \
       epp.xml launch-1.0.xsd signedMark-1.0.xsd mark-1.0.xsd xmldsig-core-schema.xsd smd-1.0.xsd idn-1.0.xsd

TUCOWS = -Dcom.neulevel.epp.core.EppUtil.forTucows
#
#RUNOPT = -Dcom.neulevel.epp.transport.EppMessageUtil.eppMessageSize=-write,-fetch
#
RUNOPT =

clean::
	${RM} -rf *.class *.xsd xml

EppHelloTest: EppHelloTest.class $(XSDS)
	java $(RUNOPT) -classpath "$(CLPATH):." $@

EppGreetingTest: EppGreetingTest.class $(XSDS)
	java $(RUNOPT) -classpath "$(CLPATH):." $@

EppLoginTest: EppLoginTest.class $(XSDS)
	java $(RUNOPT) -classpath "$(CLPATH):." $@

EppLogoutTest: EppLogoutTest.class $(XSDS)
	java $(RUNOPT) -classpath "$(CLPATH):." $@

EppPollTest: EppPollTest.class $(XSDS)
	java $(RUNOPT) -classpath "$(CLPATH):." $@

EppCheckTest: EppCheckTest.class $(XSDS)
	java $(RUNOPT) -classpath "$(CLPATH):." $@

EppCreateTest: EppCreateTest.class $(XSDS)
	java $(RUNOPT) -classpath "$(CLPATH):." $@

EppDeleteTest: EppDeleteTest.class $(XSDS)
	java $(RUNOPT) -classpath "$(CLPATH):." $@

EppInfoTest: EppInfoTest.class $(XSDS)
	java $(RUNOPT) -classpath "$(CLPATH):." $@

EppRenewTest: EppRenewTest.class $(XSDS)
	java $(RUNOPT) -classpath "$(CLPATH):." $@

EppTransferTest: EppTransferTest.class $(XSDS)
	java $(RUNOPT) -classpath "$(CLPATH):." $@

EppUpdateTest: EppUpdateTest.class $(XSDS)
	java $(RUNOPT) -classpath "$(CLPATH):." $@

EppResponseTest: EppResponseTest.class $(XSDS)
	java $(RUNOPT) -classpath "$(CLPATH):." $@

EppSecDnsTest: EppSecDnsTest.class $(XSDS)
	java $(RUNOPT) -classpath "$(CLPATH):." $@

EppUnspecTest: EppUnspecTest.class $(XSDS)
	java $(RUNOPT) -classpath "$(CLPATH):." $@

EppTestExtension: EppTestExtension.class $(XSDS)
	java $(RUNOPT) -classpath "$(CLPATH):." $@


#
# Test Server and Client
#
EppTestClientTcp: EppTestClient.class $(XSDS)
	java $(RUNOPT) -classpath "$(CLPATH):." EppTestClient tcp localhost 10288

EppTestClientTcp-Tls: EppTestClient.class $(XSDS)
	java $(RUNOPT) -classpath "$(CLPATH):." EppTestClient tcp-tls localhost 10288

EppTestServerTcp: EppTestServerTcp.class $(XSDS)
	java $(RUNOPT) -classpath "$(CLPATH):." EppTestServerTcp 10288 testkeys.server.prop

EppTestServerTcp-Tls: EppTestServerTcp.class $(XSDS)
	java $(RUNOPT) -classpath "$(CLPATH):." EppTestServerTcp 10288

EppTestXML: EppTestXML.class $(XSDS)
	java $(RUNOPT) -classpath "$(CLPATH):." $@ xml/*.xml

#
# Copy EPP Schema files from ../xsd
#
epp-1.0.xsd:: ../xsd/epp-1.0.xsd
	${CP} -p $< $@

eppcom-1.0.xsd:: ../xsd/eppcom-1.0.xsd
	${CP} -p $< $@

contact-1.0.xsd:: ../xsd/contact-1.0.xsd
	${CP} -p $< $@

domain-1.0.xsd:: ../xsd/domain-1.0.xsd
	${CP} -p $< $@

host-1.0.xsd:: ../xsd/host-1.0.xsd
	${CP} -p $< $@

svcsub-1.0.xsd:: ../xsd/svcsub-1.0.xsd
	${CP} -p $< $@

obj1ext-1.0.xsd:: ../xsd/obj1ext-1.0.xsd
	${CP} -p $< $@

neulevel-1.0.xsd:: ../xsd/neulevel-1.0.xsd
	${CP} -p $< $@

secDNS-1.0.xsd:: ../xsd/secDNS-1.0.xsd
	${CP} -p $< $@

xriCommon-1.0.xsd:: ../xsd/xriCommon-1.0.xsd
	${CP} -p $< $@

xriAU-1.0.xsd:: ../xsd/xriAU-1.0.xsd
	${CP} -p $< $@

xriINU-1.0.xsd:: ../xsd/xriINU-1.0.xsd
	${CP} -p $< $@

xriINA-1.0.xsd:: ../xsd/xriINA-1.0.xsd
	${CP} -p $< $@

xriISV-1.0.xsd:: ../xsd/xriISV-1.0.xsd
	${CP} -p $< $@

launch-1.0.xsd:: ../xsd/launch-1.0.xsd
	${CP} -p $< $@

signedMark-1.0.xsd:: ../xsd/signedMark-1.0.xsd
	${CP} -p $< $@

smd-1.0.xsd:: ../xsd/signedMark-1.0.xsd
	${CP} -p $< $@

mark-1.0.xsd:: ../xsd/mark-1.0.xsd
	${CP} -p $< $@

xmldsig-core-schema.xsd:: ../xsd/xmldsig-core-schema.xsd
	${CP} -p $< $@

idn-1.0.xsd:: ../xsd/idn-1.0.xsd
	${CP} -p $< $@

epp.xml::
	${RM} -rf xml
	${CP} -pr ../xsd/xml .
#
# Test all
#
testall:	$(TARGETS) $(XSDS)
	echo "Test all..."
	echo $(TESTS) | \
	while read epptest; \
	do \
		echo "Performing $$epptest ..." ; \
		make $$epptest ; \
	done
