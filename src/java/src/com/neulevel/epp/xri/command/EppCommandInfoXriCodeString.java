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


public class EppCommandInfoXriCodeString extends EppCommandInfo {
	/**
	* No argument constructor for constructing object of <code>EppCommandInfo</code>
	* @param
	* @return
	**/
	public EppCommandInfoXriCodeString() {
		this.codeString = null;
		clTRID   = null;
	}

	/**
	* Constructor with 2 arguments to construct object of <code>EppCommandInfoXriCodeString</code>
	* @param codeString String value representing codeString
	* @return
	**/
	public EppCommandInfoXriCodeString( String codeString) {
		this.codeString   = codeString;
		clTRID   = null;
	}

	/**
	* Constructor with 2 arguments to construct object of <code>EppCommandInfoXriCodeString</code>
	* @param codeString String value representing codeString
	* @param xid String value representing client transaction id.
	* @return
	**/
	public EppCommandInfoXriCodeString( String codeString, String xid ) {
		this.codeString   = codeString;
		clTRID   = xid;
	}

	/**
	* Method to retrive previously populated codeString.
	* @param
	* @return String representing codeString.
	**/
	public String getCodeString() {
		return this.codeString;
	}

	/**
	* Method to set codeString to be queried.
	* @param codeString String
	* @return
	**/
	public void setCodeString( String codeString ) {
		this.codeString = codeString;
	}
	public Element toXML( Document doc, String tag ) {
		Element elm;
		Element body = EppUtil.createElementNS(doc, "xriCS", tag);
		if( codeString  != null ) {
			elm = doc.createElement("codeString");
			elm.appendChild(doc.createTextNode(codeString));
			body.appendChild(elm);
		}
		return toXMLCommon(doc, tag, body);

	}
	public static EppCommandInfoXriCodeString fromXML( Node root ) {
		EppCommandInfoXriCodeString cmd = null;
		NodeList list = root.getChildNodes();
		for( int i = 0; i < list.getLength(); i++ ) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if( name == null ) {
				continue;
			}
			if( name.equals("codeString") || name.equals("xriCS:codeString") ) {
				String s = EppUtil.getText(node);
				if( (s != null) && (s.length() > 0) ) {
					if( cmd == null ) {
						cmd = new EppCommandInfoXriCodeString(s);
						if( cmd == null ) {
							return cmd;
						}
					} else {
						cmd.setCodeString(s);
					}
				}
			}
		}
		return cmd;
	}
	private String		codeString;
}
