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
# $Id: Makefile,v 1.4 2008/04/24 22:00:14 wtan Exp $
#
# This is the Makefile for building EPP Java Implementation
#
# Top Makefile

all:: compile jars docs apidocs package test

TOP = .
include $(TOP)/src/Makefile.incl

compile:: compile_src 
package:: package_bin package_src package_doc

compile_src::
	@echo Building Source
	${MAKE} -C src

jars:: compile
	@echo Building Jar files in bin directory
	${MKDIR} class
	${MKDIR} bin
	${MAKE} -C src jars

docs:: ./src/classfiles_updated
	${MAKE} apidocs

apidocs::
	@echo Building apiDocs in docs directory.
	${MKDIR} docs/apiDocs
	${MAKE} -C src apidocs

package_bin:: jars apidocs ${BINZIPFILE}
${BINZIPFILE}:: ./src/classfiles_updated
	@echo Building the binary release package
	${MKDIR} bin
	${CP} LICENSE bin
	${CP} CHANGELOG bin
	${CP} EPP-1.0-MIGRATION-GUIDE bin
	${CP} xsd/*.xsd bin
	${MV} bin epp-${PRODUCTVERSION}
	${MKDIR} epp-${PRODUCTVERSION}/test
	${CP} test/RunTestTcp            epp-${PRODUCTVERSION}/test
	${CP} test/EppTestClient.java    epp-${PRODUCTVERSION}/test
	${CP} test/EppTestServerTcp.java epp-${PRODUCTVERSION}/test
	${CP} test/testkeys.*            epp-${PRODUCTVERSION}/test
	$(JAR) cvfM ./dist/${BINZIPFILE} epp-${PRODUCTVERSION} 
	$(MV) epp-${PRODUCTVERSION} bin

package_src:: ${SRCZIPFILE}
${SRCZIPFILE}: ./src/classfiles_updated
	@echo Building the source release package
	${MAKE} -C src package_src
	$(MV) source epp-${PRODUCTVERSION}
	$(JAR) cvfM ./dist/${SRCZIPFILE} epp-${PRODUCTVERSION} 
	$(MV) epp-${PRODUCTVERSION} source

package_doc:: jars apidocs ${DOCZIPFILE}
${DOCZIPFILE}: ./src/classfiles_updated
	@echo Building the document release package
	$(JAR) cvfM ./dist/${DOCZIPFILE} docs

dist:: ${DOCZIPFILE} ${SRCZIPFILE} ${BINZIPFILE}
	#cp ${DOCZIPFILE} ./dist
	#cp ${DOCZIPFILE} ./dist
	#cp ${BINZIPFILE} ${SRCZIPFILE} ./dist

test:: jars
	${MAKE} -C test

clean::
	${MAKE} -C src clean
	${MAKE} -C test clean
	${RM} -rf bin/* class source docs/apiDocs docs/html 
	${RM} ./dist/${BINZIPFILE} ./dist/${SRCZIPFILE} ./dist/${DOCZIPFILE}
	find . -name '*~' -exec rm \{\} \;
