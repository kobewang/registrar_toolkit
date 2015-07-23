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
package com.neulevel.epp.xri.command;

import java.util.*;
import org.w3c.dom.*;
import com.neulevel.epp.core.*;
import com.neulevel.epp.core.command.*;

public class EppCommandCheckXriCodeString extends EppCommandCheck {
	/**
	* Constructor to create an object of <code>EppCommandCheckXriCodeString</code>
	* @param
	* @return
	**/
	public EppCommandCheckXriCodeString() {
		this.codeString = new Vector();
		this.clTRID = null;
	}
	/**
	* Constructor to create object of <code>EppCommandCheckXriCodeString</code> with one argument.
	* @param xid String client transaction id.
	* @return
	**/
	public EppCommandCheckXriCodeString( String xid ) {
		this.codeString = new Vector();
		this.clTRID = xid;
	}
	/**
	* Method to retrive collection of code string user wants to check.
	* @param
	* @return Vector of String
	**/
	public Vector getCodeStringId() {
		return this.codeString;
	}
	/**
	* Method to retrive collection of code string user wants to check.
	* @param
	* @return Vector of String
	**/
	public Vector get() {
		return this.codeString;
	}
	/**
	* Method to add codeString to be checcked.
	* @param csId String codeString.
	* @return
	**/
	public void addCodeStringId( String csId ) {
		this.codeString.addElement(csId);
	}
	/**
	* Method to add codeString to be checcked.
	* @param csId String codeString.
	* @return
	**/
	public void add( String csId) {
		this.addCodeStringId(csId);
	}
	public Element toXML( Document doc, String tag ) {
		Element elm;
		Element body = EppUtil.createElementNS(doc, "xriCS", tag);
	
		if( codeString != null ) {
			for( int i = 0; i < codeString.size(); i++ ) {
				String authId = (String)codeString.elementAt(i);
				if( authId != null ) {
					elm = doc.createElement("codeString");
					elm.appendChild(doc.createTextNode(authId));
					body.appendChild(elm);
				}
			}
		}
		return toXMLCommon(doc, tag, body);
	}
	public static EppCommandCheckXriCodeString fromXML( Node root ) {
		EppCommandCheckXriCodeString cmd = new EppCommandCheckXriCodeString();
		NodeList list = root.getChildNodes();
		for(int i = 0;i<list.getLength();i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if( name == null ) {
				continue;
			}
			if( name.equals("codeString") || name.equals("xriCS:codeString") ) {
				String authId = EppUtil.getText(node);
				if( authId != null ) {
					cmd.addCodeStringId(authId);
				}
			}
		}
		return cmd;

	}
	private Vector codeString;
}
