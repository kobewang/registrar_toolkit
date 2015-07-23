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
# $Id: Makefile,v 1.10 2013/10/16 16:24:01 s_skalsa Exp $
#
# This is the Makefile for building EPP C++ Implementation
#
# Makefile for directory src/epp/core

TOP=../../../..
DIRS=command response
include $(TOP)/src/Makefile.plat

OBJS=\
	EppFeeCommand.$(O)\
	EppFeeFee.$(O)\
	EppFeeCredit.$(O)\
	EppCommandInfoFeeType.$(O)\
	EppCommandCheckFeeType.$(O)\
	EppResponseInfoFeeType.$(O)\
	EppResponseCheckFeeType.$(O)\
	EppRequestFeeType.$(O)\
	EppResponseFeeType.$(O)\
	

HDRS=\
	EppFeeDefs.hpp\
	EppFeeCommand.hpp\
	EppFeeFee.hpp\
	EppFeeCredit.hpp\
	EppCommandInfoFeeType.hpp\
	EppCommandCheckFeeType.hpp\
	EppResponseInfoFeeType.hpp\
	EppResponseCheckFeeType.hpp\
	EppRequestFeeType.hpp\
	EppResponseFeeType.hpp\
	

SRCS=\
	EppFeeCommand.cpp\
	EppFeeFee.cpp\
	EppFeeCredit.cpp\
	EppCommandInfoFeeType.cpp\
	EppCommandCheckFeeType.cpp\
	EppResponseInfoFeeType.cpp\
	EppResponseCheckFeeType.cpp\
	EppRequestFeeType.cpp\
	EppResponseFeeType.cpp\


TARGETS=$(OBJS)
include $(TOP)/src/Makefile.incl
