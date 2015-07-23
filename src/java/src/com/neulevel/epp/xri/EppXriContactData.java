/*******************************************************************************
 * The MIT License (MIT)
 *  
 * Copyright (c) 2015 Neustar Inc.
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
package com.neulevel.epp.xri;

import java.util.*;
import org.w3c.dom.*;
import com.neulevel.epp.core.EppEntity;
import com.neulevel.epp.core.EppObject;
import com.neulevel.epp.core.EppE164;
import com.neulevel.epp.core.EppStatus;
import com.neulevel.epp.core.EppAuthInfo;
import com.neulevel.epp.core.EppContactData;
import com.neulevel.epp.core.EppUtil;
import com.neulevel.epp.xri.command.*;
/*
enum xriContactType {
	negContact					= -1,
	billingContact,
	adminContact,
	techContact,
	invalidContact			= 3
};
*/


public class EppXriContactData extends EppEntity {
	public final static short negContact			= -1;
	public final static short billingContact	= 0;
	public final static short adminContact		= 1;
	public final static short techContact			= 2;
	public final static short invalidContact	= 3;

	public	EppXriContactData() {
		contactHandle = null;
		contactType = negContact;
	}
	public	EppXriContactData(EppXriContactData c) {
		contactHandle = c.contactHandle;
		contactType = c.contactType;
	}
	/**
	* Method to set contact handle.
	* @param input of type String representing contact handle.
	* @return
	**/
	public void setContactHandle(String input) {
		contactHandle = input;
	}
	/**
	* Method to set contact type.
	* @param input of type short with possibles values of  billingContact,adminContact &amp; techContact
	* @return
	**/
	public void setContactType(short input) {
		contactType = input;
	}
	/**
	* Method to set contact type.
	* @param type of type String with possible values of "billing", "admin" or "tech"
	* @return
	**/
	public void setContactType(String type) {
		short conType = negContact;
		if( type.equals("billing") ) {
			conType = billingContact;
		} else if( type.equals("admin") ) {
			conType = adminContact;
		} else if( type.equals("tech") ) {
			conType = techContact;
		}
		contactType = conType;
	}
	/**
	* Method to retrive the previously set contact handle.
	* @param
	* @return String value representing contact handle.
	**/
	public String getContactHandle() {
		return contactHandle;
	}
	/**
	* Method to get current contact type
	* @param
	* @return short with possible values of negContact, billingContact,adminContact,techContact &amp; invalidContact
	**/
	public short getContactType() {
		return contactType;
	}
	/**
	* Method to validate if the input is valid or not.
	* @param t of type short with possibles values of  billingContact,adminContact &amp; techContact
	* @return Boolean true if it's a valid type or else false.
	**/
	public Boolean isValidType(short t) {
		if( t < billingContact || t > techContact )
			return false;
		return true;
	}
	public Element toXML( Document doc, String tag ) {
		Element body = doc.createElement(tag);
		if( contactHandle != null && isValidType(contactType) ) {
			body.appendChild(doc.createTextNode(contactHandle));
			body.setAttribute("type", contactMap[ (short)contactType ] );
		}
		return body;
	}
	public static EppEntity fromXML( Node node) {
		EppXriContactData data = null;
		String name = node.getLocalName();
		if( name == null ) {
			name = node.getNodeName();
		}
		if( name == null ) {
			return null;
		}
		if( name.equals("contactId") ){
			if( data == null ) {
				data = new EppXriContactData();
			}
			String conId = EppUtil.getText(node);
			String type = ((Element)node).getAttribute("type");
			data.setContactHandle(conId);
			data.setContactType( (type != null)?type:"");
	}
	return data;

	}

	public String toString() {
		return  toString("contactId");
	}
	private String contactHandle;
	private short contactType;
	private final static String contactMap[] = { "billing", "admin", "tech" };
}
