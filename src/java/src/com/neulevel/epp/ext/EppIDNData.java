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
package com.neulevel.epp.ext;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.neulevel.epp.core.EppEntity;
import com.neulevel.epp.core.EppUtil;

public class EppIDNData extends EppExtension {
	
	public EppIDNData(){
		_table = new String();
		_uname = new String();
	}

	public EppIDNData(String table, String uname){
		_table = new String(table);
		_uname = new String(uname);
	}
	@Override
	public Element toXML(Document doc, String tag) {
		Element body = EppUtil.createElementNS(doc, "idn", (tag == null) ? "data" : tag,true,"-1.0");

		
		if (_table.length() > 0) {
			Element elm = doc.createElement("table");
			elm.appendChild(doc.createTextNode(_table));
			body.appendChild(elm);
	  	}
		if (_uname.length() > 0) {
			Element elm = doc.createElement("uname");
			elm.appendChild(doc.createTextNode(_uname));
			body.appendChild(elm);
	  	}
	  return body;
	}
	public static EppEntity fromXML(final Node root) {

		EppIDNData idnData = new EppIDNData();
		NodeList list = root.getChildNodes();
		for( int i = 0; i < list.getLength(); i++ ) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if( name == null ) {
				name = node.getNodeName();
			}		
			if( name == null ) {
				continue;
			}
			if( name.startsWith("idn:") ) {
				name = name.substring(4, name.length() - 4);
			}
			if( name.equals("table") ) {
				idnData._table = EppUtil.getText(node);
			} else if( name.equals("uname") ) {
				idnData._uname = EppUtil.getText(node);
			}
		}
		return idnData;
	}
	@Override
	public String toString() {
		return new String("idn:data");
	}
	public String table(){
		return _table;
	}
	public void table(String val){
		_table = val;
	}
	
	public String uname() {
		return _uname;
	}

	public void uname(String val){
		_uname = val;
	}
	private String _table;
	private String _uname;
}
