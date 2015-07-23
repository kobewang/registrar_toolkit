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
# $Id: Makefile,v 1.6 2013/05/09 13:01:44 nseshadr Exp $
#
# This is the Makefile for building EPP C++ Implementation
#
# Makefile for directory src/epp/core

TOP=../../../../..
DIRS=
include $(TOP)/src/Makefile.plat

OBJS=\
	EppCommandCheckFee.$(O) \
	EppCommandInfoFee.$(O) \
	EppCommandCreateFee.$(O) \
	EppCommandRenewFee.$(O) \
	EppCommandUpdateFee.$(O) \
	EppCommandTransferFee.$(O) \

HDRS=\
	EppCommandCheckFee.hpp \
	EppCommandInfoFee.hpp \
	EppCommandCreateFee.hpp \
	EppCommandRenewFee.hpp \
	EppCommandUpdateFee.hpp \
	EppCommandTransferFee.hpp \

SRCS=\
	EppCommandCheckFee.cpp \
	EppCommandInfoFee.cpp \
	EppCommandCreateFee.cpp \
	EppCommandRenewFee.cpp \
	EppCommandUpdateFee.cpp \
	EppCommandTransferFee.cpp \

TARGETS=$(OBJS)
include $(TOP)/src/Makefile.incl
