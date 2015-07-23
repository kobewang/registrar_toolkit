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
package com.neulevel.epp.ext.fee;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.neulevel.epp.core.EppUtil;

public class EppCommandCheckFeeType extends EppCommonFeeType {
	protected String name;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	public Element toXML (Document doc, String tag) {
		Element body = doc.createElement(tag);
		toXMLCommon(doc, tag, body);
		return body;
	}
	
	public void toXMLCommon (Document doc, String tag, Element body) {
		if (body != null) {
			if (name != null && name.length() > 0) {
				Element nameElem = doc.createElement("name");
				nameElem.appendChild(doc.createTextNode(name));
				body.appendChild(nameElem);
			}
			super.toXMLCommon(doc, tag, body);
		}
	}
	
	public static EppCommandCheckFeeType fromXML (Node root) {
		EppCommandCheckFeeType commandCheckFeeType = new EppCommandCheckFeeType();
		fromXMLCommon(root, commandCheckFeeType);
		return commandCheckFeeType;
	}
	
	public static EppCommandCheckFeeType fromXMLCommon (Node root,EppCommandCheckFeeType commandCheckFeeType) {
		if (commandCheckFeeType != null) {
			EppCommonFeeType.fromXMLCommon(root, commandCheckFeeType);
			
			NodeList nodeList = root.getChildNodes();
			for (int i =0 ; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				String name = node.getLocalName();
				if (name == null ) {
					name = node.getNodeName();
				}
				
				if (name != null) {
					if (name.equals("name")) {
						commandCheckFeeType.setName(EppUtil.getText(node));
						continue;
					}
					
					
				} else {
					continue;
				}
			}
		}
		
		return commandCheckFeeType;
	}
	
	public String toString()
	{
		return new String("fee:domain");
	}
	
}
