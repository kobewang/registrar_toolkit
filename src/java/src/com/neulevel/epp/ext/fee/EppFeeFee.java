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

import java.text.DecimalFormat;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.neulevel.epp.core.EppEntity;
import com.neulevel.epp.core.EppUtil;

/**
 * 
 * 
 * @author ankit.kothana
 *
 */
public class EppFeeFee {

	protected Double fee;
	protected String description;
	private static final DecimalFormat df = new DecimalFormat("#.00");
	
	/**
	 * @return the fee
	 */
	public Double getFee() {
		return fee;
	}

	/**
	 * @param fee the fee to set
	 */
	public void setFee(Double fee) {
		this.fee = fee;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the refundable
	 */

	public Element toXML(Document doc, String tag) {
		Element body = doc.createElement(tag);
		if (fee != null) {
            body.appendChild(doc.createTextNode(df.format(fee)));
        }
		if (description != null && description.length() > 0) {
			Attr attr = doc.createAttribute("description");
			attr.setValue( this.description );
			body.setAttributeNode(attr); 
		}
		
        return body;
	}
	
	public static EppFeeFee fromXML(final Node root) {
		NamedNodeMap attrs = root.getAttributes();
		EppFeeFee returnEppFeeFee = new EppFeeFee();
		
		String name = root.getLocalName();
		if (name == null) {
			name = root.getNodeName();
		}
		
		if (name != null) {
			if (name.equals("fee")) {
				returnEppFeeFee.fee = Double.parseDouble(EppUtil.getText(root));
			}
		}
		
		for(int i = 0;i<attrs.getLength();i++) {
			Node attr = attrs.item(i);
			String attrValue = attr.getNodeValue();
			if( attr.getNodeName().equals("description") ) {
				returnEppFeeFee.description = attrValue;
				continue;
			} 
			
		}
		
		return returnEppFeeFee;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
