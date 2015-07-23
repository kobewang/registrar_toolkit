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
# $Id: Makefile,v 1.3 2010/06/11 21:20:50 achowdhu Exp $
#
# This is the Makefile for building EPP C++ Implementation
#
# Makefile for directory src/epp/core

TOP=../../..
DIRS=command response
include $(TOP)/src/Makefile.plat

OBJS=\
	EppEntity.$(O)	\
	EppAddress.$(O)	\
	EppAuthInfo.$(O)	\
	EppE164.$(O)	\
	EppPeriod.$(O)	\
	EppIpAddress.$(O)	\
	EppContactData.$(O)	\
	EppContactType.$(O)	\
	EppHello.$(O)	\
	EppStatus.$(O)	\
	EppPollable.$(O)	\
	EppServiceMenu.$(O)	\
	EppDataCollectionStatement.$(O)	\
	EppDataCollectionPolicy.$(O)	\
	EppGreeting.$(O)	\
	EppObject.$(O)	\
	EppContactDisclose.$(O)	\
	EppContact.$(O)	\
	EppHostAttribute.$(O)	\
	EppDomain.$(O)	\
	EppHost.$(O)	\
	EppSvcsub.$(O)	\
	EppSvcsubParam.$(O)	\
	EppTransactionId.$(O)	\
	EppError.$(O)	\
	EppGenericNVPairs.$(O)	\
	EppUnspec.$(O)	\
	EppParser.$(O)	\
	EppUtil.$(O)

HDRS=\
	EppEntity.hpp	\
	EppAddress.hpp	\
	EppAuthInfo.hpp	\
	EppE164.hpp	\
	EppPeriod.hpp	\
	EppIpAddress.hpp	\
	EppContactData.hpp	\
	EppContactType.hpp	\
	EppStatus.hpp	\
	EppHello.hpp	\
	EppPollable.hpp	\
	EppServiceMenu.hpp	\
	EppDataCollectionStatement.hpp	\
	EppDataCollectionPolicy.hpp	\
	EppGreeting.hpp	\
	EppObject.hpp	\
	EppContactDisclose.hpp	\
	EppContact.hpp	\
	EppHostAttribute.hpp	\
	EppDomain.hpp	\
	EppHost.hpp	\
	EppSvcsub.hpp	\
	EppSvcsubParam.hpp	\
	EppTransactionId.hpp	\
	EppError.hpp	\
	EppGenericNVPairs.hpp	\
	EppUnspec.hpp	\
	EppParser.hpp	\
	EppUtil.hpp

SRCS=\
	EppEntity.cpp	\
	EppAddress.cpp	\
	EppAuthInfo.cpp	\
	EppE164.cpp	\
	EppPeriod.cpp	\
	EppIpAddress.cpp	\
	EppContactData.cpp	\
	EppContactType.cpp	\
	EppHello.cpp	\
	EppStatus.cpp	\
	EppPollable.cpp	\
	EppServiceMenu.cpp	\
	EppDataCollectionStatement.cpp	\
	EppDataCollectionPolicy.cpp	\
	EppGreeting.cpp	\
	EppObject.cpp	\
	EppContactDisclose.cpp	\
	EppContact.cpp	\
	EppHostAttribute.cpp	\
	EppDomain.cpp	\
	EppHost.cpp	\
	EppSvcsub.cpp	\
	EppSvcsubParam.cpp	\
	EppTransactionId.cpp	\
	EppError.cpp	\
	EppGenericNVPairs.cpp	\
	EppUnspec.cpp	\
	EppParser.cpp	\
	EppUtil.cpp	\
	EppUtilTime.cpp

TARGETS=$(OBJS)
include $(TOP)/src/Makefile.incl
