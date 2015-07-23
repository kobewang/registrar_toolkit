/*
 * Copyright (c) 2001-2006 NeuStar, Inc. All Rights Reserved.
 *
 * $Id: EppResponseDataCreateXriCodeString.cpp,v 1.1 2009/11/23 14:35:32 nseshadr Exp $
 */
#include "EppResponseDataCreateXriCodeString.hpp"
#include "EppUtil.hpp"

EppResponseDataCreateXriCodeString* EppResponseDataCreateXriCodeString::fromXML(const DOM_Node& root) {
	DOMString auth_id = null;
	time_t create_date = 0;
	DOM_NodeList list = root.getChildNodes();
	for(unsigned int i=0;i<list.getLength();i++) {
		DOM_Node node = list.item(i);
		DOMString name = node.getLocalName();
		if(name == null) {
			name = node.getNodeName();
		}
		if(name == null) {
			continue;
		}
		if(name.equals("codeString") || name.equals("xriCS:codeString")) {
			DOMString id = EppUtil::getText(node);
			if( (id != null) && (id.length()>0)) {
				auth_id = id;
			}
		} else if(name.equals("crDate") || name.equals("xriCS:crDate")) {
			time_t t = EppUtil::getDate(node);
			if(t != null) {
				create_date = t;
			}
		}
	}
	return new EppResponseDataCreateXriCodeString(auth_id,create_date);
}

DOM_Element EppResponseDataCreateXriCodeString::toXML(DOM_Document& doc,const DOMString& tag) {
	DOM_Element elm;
	DOM_Element body = doc.createElement(tag);
	DOM_Element data = EppUtil::createElementNS(doc,"xriCS","creData");
	body.appendChild(data);
	if(codeString != null) {
		elm = doc.createElement("codeString");
		elm.appendChild(doc.createTextNode(codeString));
		data.appendChild(elm);
	}
	if(crDate != 0) {
		elm = doc.createElement("crDate");
		elm.appendChild(EppUtil::createTextNode(doc,crDate));
		data.appendChild(elm);
	}
	return body;
}
