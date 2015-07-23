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
package com.neulevel.epp.core.response;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.neulevel.epp.core.EppEntity;
import com.neulevel.epp.core.EppUtil;

/**
 * 
 * @author ankit.kothana
 *
 */
public class EppReason extends EppEntity {
	
	private String reason;
	private String language;
	
	public EppReason(String reason, String language) {
		this.reason = reason;
		this.language = language;
	}

	public EppReason() {
	}

	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}


	@Override
	public Element toXML(Document doc, String tag) {
		Element body = doc.createElement(tag);
		if (reason != null && reason.length() > 0) {
			body.appendChild(doc.createTextNode(reason));
		}
		
		if (language != null && language.length() > 0) {
			Attr attr = doc.createAttribute("lang");
			attr.setValue( this.language );
			body.setAttributeNode(attr); 
		}
		return body;
	}
	
	public static EppReason fromXML(final Node root) {
		NamedNodeMap attrs = root.getAttributes();
		EppReason returnEppReason = new EppReason();
		String name = root.getLocalName();
		
		if (name == null) {
			name = root.getNodeName();
		}
		
		if (name != null) {
			if (name.equals("reason")) {
				returnEppReason.reason = EppUtil.getText(root);
			}
		}
		
		for(int i = 0;i<attrs.getLength();i++) {
			Node attr = attrs.item(i);
			String attrValue = attr.getNodeValue();
			if( attr.getNodeName().equals("lang") ) {
				returnEppReason.language = attrValue;
				continue;
			} 
			
		}
		
		return returnEppReason;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new String("reason");
	}


	
}
