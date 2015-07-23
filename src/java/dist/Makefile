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
# Makefile for developers
#
# $Id: Makefile,v 1.11 2008/04/09 19:13:47 wtan Exp $


NAME=NeuStar-XRI
VER=0.4.9
DIR=$(NAME).$(VER)
TEMPLATE_FILES= \
	   ../README \
	   ../LICENSE \
	   ../bin/README \
	   ../bin/c++/README \
	   ../bin/java/README \
	   ../doc/README \
	   ../src/README \

OTHER_FILES= \
	   ../bin/java/Epp-bin.$(VER).zip \
	   ../bin/java/Epp-doc.$(VER).zip \
	   ../bin/java/Epp-src.$(VER).zip \
	   ../doc/Epp-C++-doc.$(VER).tar.gz \
	   ../doc/Epp-C++-doc.$(VER).zip \
	   ../doc/Epp-doc.$(VER).zip \
	   ../bin/c++/Epp-C++-doc.$(VER).tar.gz \
	   ../bin/c++/Epp-C++-doc.$(VER).zip \
	   ../bin/c++/epp-c-linux-g++-$(VER).tar.gz \
	   ../bin/c++/epp-c-linux-g++-$(VER).zip \
#	   ../bin/c++/epp-c-hpux-aCC-$(VER).tar.gz \
#	   ../bin/c++/epp-c-hpux-aCC-$(VER).zip \
#

DIST_FILES= \
	   $(TEMPLATE_FILES) \
	   $(OTHER_FILES) \

OTHER_DIRS= \
	    ../src/c++ \
	    ../src/java \

XSD_DIR=../src/java/xsd

ZIP=/usr/bin/zip
TAR=/bin/tar -cf
GZ=/bin/gzip

dist: $(NAME).$(VER).tar.gz $(NAME).$(VER).zip



$(NAME).$(VER).tar.gz $(NAME).$(VER).zip: $(DIST_FILES)
	@mkdir $(DIR)
	
	# copying source codes
	@for d in `echo $(OTHER_DIRS)`; do \
		dst="$(DIR)/`echo $$d | sed 's/\.\.\///'`"; \
		mkdir -p `dirname $$dst`; \
		cp -pR $$d `dirname $$dst`; \
		echo ========== Cleaning source directory $$dst ==========; \
		(cd $$dst; make clean || exit 1) >> make.out ; \
		find $$dst -depth -name CVS -exec rm -rf {} \; ; \
		echo ========== Finished leaning source directory $$dst ==========; \
	done

	# copying template files
	@for f in `echo $(TEMPLATE_FILES)`; do \
		dst="$(DIR)/`echo $$f | sed 's/\.\.\///'`"; \
		mkdir -p `dirname $$dst`; \
		cp $$f $$dst; \
		perl -pi -e 's/!!VER!!/$(VER)/g' $$dst; \
	done

	# copying bins and docs
	@for f in `echo $(OTHER_FILES)`; do \
		dst="$(DIR)/`echo $$f | sed 's/\.\.\///'`"; \
		mkdir -p `dirname $$dst`; \
		cp $$f $$dst; \
	done

	$(TAR) - $(DIR) | $(GZ) -c > $(NAME).$(VER).tar.gz
	$(ZIP) -r $(NAME).$(VER).zip $(DIR)


xml:
	mkdir -p tkweb/epp-xri-au
	mkdir -p tkweb/epp-xri-ina
	mkdir -p tkweb/epp-xri-inu
	mkdir -p tkweb/epp-xri-isv

	# docs
	cp ../doc/tkweb-index.html tkweb/index.html
	(cd tkweb && unzip ../../doc/Epp-doc.$(VER).zip)

	# xri-au
	cp -pR $(XSD_DIR)/xml/xri-au_*.xml tkweb/epp-xri-au
	cp $(XSD_DIR)/{contact,epp,eppcom,xriCommon}-1.0.xsd tkweb/epp-xri-au
	cp $(XSD_DIR)/xriAU-1.0.xsd tkweb/epp-xri-au
	cp ../doc/epp-xri-au-*.pdf tkweb/epp-xri-au
	(cd tkweb && $(ZIP) -r epp-xri-au.zip epp-xri-au)

	# xri-ina
	cp -pR $(XSD_DIR)/xml/xri-ina_*.xml tkweb/epp-xri-ina
	cp $(XSD_DIR)/{contact,epp,eppcom,xriCommon}-1.0.xsd tkweb/epp-xri-ina
	cp $(XSD_DIR)/xriINA-1.0.xsd tkweb/epp-xri-ina
	cp ../doc/epp-xri-ina-*.pdf tkweb/epp-xri-ina
	(cd tkweb && $(ZIP) -r epp-xri-ina.zip epp-xri-ina)

	# xri-inu
	cp -pR $(XSD_DIR)/xml/xri-inu_*.xml tkweb/epp-xri-inu
	cp $(XSD_DIR)/{contact,epp,eppcom,xriCommon}-1.0.xsd tkweb/epp-xri-inu
	cp $(XSD_DIR)/xriINU-1.0.xsd tkweb/epp-xri-inu
	cp ../doc/epp-xri-inu-*.pdf tkweb/epp-xri-inu
	(cd tkweb && $(ZIP) -r epp-xri-inu.zip epp-xri-inu)

	# xri-isv
	cp -pR $(XSD_DIR)/xml/xri-isv_*.xml tkweb/epp-xri-isv
	cp $(XSD_DIR)/{contact,epp,eppcom,xriCommon}-1.0.xsd tkweb/epp-xri-isv
	cp $(XSD_DIR)/xriISV-1.0.xsd tkweb/epp-xri-isv
	cp ../doc/epp-xri-isv-*.pdf tkweb/epp-xri-isv
	(cd tkweb && $(ZIP) -r epp-xri-isv.zip epp-xri-isv)

	# bundle up everything
	tar -jcf tkweb.tar.bz2 tkweb

clean:
	rm -rf tkweb tkweb.tar.gz
	rm -rf $(NAME).$(VER)
	rm -f $(NAME).$(VER).tar.gz $(NAME).$(VER).zip make.out
