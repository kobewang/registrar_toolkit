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
import com.neulevel.epp.core.EppUtil;


public class EppXriCodeStringExt extends EppEntity {

	/**
	* No argument constructor for creating an object of <code>EppXriCodeStringExt</code>
	* @param
	* @return
	**/
	public EppXriCodeStringExt() {
		vas = new Vector();
	}
	public EppXriCodeStringExt(EppXriCodeStringExt ext) {
		vas = new Vector( ext.vas );
	}
	/**
	* Constructor with 2 arguments of <code>EppXriCodeStringExt</code> object.
	* @param _name Name of value added services
	*				 _value Value of service
	* @return
	**/
	public EppXriCodeStringExt(String _name,String _value) {
		vas = new Vector();
		this.addVas(_name,_value);
	}
	/**
	* Interface to add a combination of value added serveice.
	* @param _name String representing name of vas and _value representing value of _name
	* @return returns void
	**/
	public void addVas(String _name,String _value) {
		EppXriCodeStringVASNVPair v = new EppXriCodeStringVASNVPair();
		v.setName(_name);
		v.setValue(_value);
		vas.addElement(v);
	}
	/**
	* Function to retrive all value added services .
	* @param
	* @return Vector of <code>vas_t</code> class that has to public members <code>name</code> and <code>value</code>
	**/
	public Vector getVas() { return this.vas; }
	public Element toXML( Document doc, String tag ) {
		Element body;
		Element elm;
		if( doc == null )
			return null;
		body = doc.createElement(tag);
		for(int i = 0 ; i< this.vas.size(); i++ ) {
			EppXriCodeStringVASNVPair t = (EppXriCodeStringVASNVPair)this.vas.elementAt(i);
			elm = doc.createElement("vas");
			elm.appendChild(doc.createTextNode(t.getValue()));
			elm.setAttribute("name", t.getName() );
			body.appendChild(elm);
		}
		return body;
	}
	public static EppXriCodeStringExt fromXML( Node root ) {
		EppXriCodeStringExt ret_val = new EppXriCodeStringExt();
		NodeList list = root.getChildNodes();
		for( int i = 0; i < list.getLength(); i++ ) {
			Node node = list.item(i);
			if( node == null )
				return null;
			String name = node.getLocalName();
			if( name == null ) {
				name = node.getNodeName();
			}
			if( name == null ) {
				return null;
			}
			if( name.equals("vas") ) {
				String attri_name = ((Element)node).getAttribute("name");
				ret_val.addVas(attri_name,EppUtil.getText(node));
			}
		}
		return ret_val;
	}
	public String toString() {
		return  toString("ext");
	}
	private Vector vas;
}
