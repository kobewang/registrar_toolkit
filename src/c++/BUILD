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

EPP 1.0 (EPP-09) C++ Implementation - Build Instructions

Table of Contents

 1. Required Third Party Packages
 2. Build EPP C++ Implementation
 3. Build EPP C++ Documentation
 4. Special Instructions
    4.1 Building on Windows Platform
    4.2 Building on UNIX Platform

---------------------------------------------------------------------

1. Required Third Party Packages

   The EPP C++ Implementation requires the following packages:

   1. XERCES-C++ Version 3.1.1 or higher

	  http://xml.apache.org/xerces-c/index.html

      Please note that the binary distribution from the Apache website
      may not compatiable with the compiler and operating systems
      required for building the EPP C++ API. Therefore, the XERCES C++
      source code may have to be recompiled for generating compatiable
      XERCES C++ libraries. For example, the binary distribution for
      LINUX platform supports RedHat 6.1 and GCC/G++ 2.91.66 only.

   2. OPENSSL Version 0.9.6a

          http://www.openssl.org/source/

   3. GNU Make Version 3.79.1

          ftp://ftp.gnu.org/gnu/make/
 
   4. DOC++ Version 3.4.8

          http://docpp.sourceforge.net

      or

          http://sourceforge.net/projects/docpp/

2. Building EPP C++ Implementation

   Before you perform the build, you need to select one of the Makefiles
   in the src directory that includes the platform dependent information.
   Currently, there are 4 known platforms supported by EPP C++ 0.4.1

          Makefile.linux   - for G++: 2.96 
          Makefile.solaris - for Solaris 2.7/2.8 with G++ 2.95.2 or SC 4.2
          Makefile.hpux    - for aCC: HP ANSI C++ B3910B A.03.13 or later
          Makefile.win32   - for MS VC++: 6.0 (CL 12.00.8168 or later)

   1. Copy one of the platform dependent Makefile to Makefile.plat. For
      example:

          cp Makefile.hp Makefile.plat

   2. Edit Makefile.plat to specify the paths and libraries for XERCES-C++
      and OPENSSL:

          XERCES_INC_DIR - include directory for XERCES-C++
          XERCES_LIB_DIR - the absoulte path of the XERCES-C++ library
          OPENSSL_INC_DIR - include directory for OPENSSL
          OPENSSL_LIB_DIR - the absoulte path names of the OPENSSL libraries

      For Solaris, you also need to select options for either G++ 2.95.2 or
      SC 4.2 by commenting out either SC 4.2 or G++ options.

   3. Make any other changes in the Makefile.plat file to suit your needs.

   The top level Makefile will build everything, including the
   documentation. If you only want to compile the source code and
   make the library files, run the following command from the top of
   the EPP C++ tree:
   
     make compile_src lib
     
   If you want to build everything, including the documentation,
   read the following instructions before running make.

   If you want to compile C++ files with optimization, add CXXFLAGS=-O
   in the make command line. For example:

     make CXXFLAGS=-O
   
   When building, all special instructions must be followed.

3. Building EPP C++ Documentation

   From the top of the EPP C++ tree, type the following command to
   build the documentation:
   
     make docs

4. Special Instructions

4.1 Building on Windows Platform

    First, you need a copy of Cygwin. See

      http://www.cygwin.com/

    Then, if you're not using the Cygwin bash shell, you need to set
    the variable MAKE_MODE in your environment like this:

      set MAKE_MODE=UNIX

    Also, you need to copy .DLL files associated with OPENSSL and
    XERCES-C to the test directory if you want to run the test client or
    the server:

      OPENSSL :   libeay32.dll
                  ssleay32.dll
      XERCES-C:   xerces-c_1_5_1.dll, or
                  xerces-c_1_5_1D.dll if xerces-c_1D.lib is used

    The executables linked with EPP C++ library must be built with /MD or
    /MDd option.

    Finally, from the top of the EPP C++ tree, run make.

4.2 Building on Unix Platform

    If all third party packages are installed properly on your system
    and correct Makefile is selected for the build, all you need to do
    do is to follow the instruction described in Section 1. Then from
    the top of the directory tree, run make.

-EOF- $Id: BUILD,v 1.1 2005/12/06 20:11:37 wtan Exp $
