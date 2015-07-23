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

import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.neulevel.epp.core.EppPeriod;
import com.neulevel.epp.core.EppUtil;

/**
 * 
 * @author ankit.kothana
 *
 */
public class EppCommonResponseFeeType extends EppCommonFeeType {
	
	protected String feeClass;
	protected Vector<EppFeeFee> eppFeeFeeVector;
	
	/**
	 * @return the feeClass
	 */
	public String getFeeClass() {
		return feeClass;
	}
	
	/**
	 * @param feeClass the feeClass to set
	 */
	public void setFeeClass(String feeClass) {
		this.feeClass = feeClass;
	}
	
	/**
	 * @return the eppFeeFeeVector
	 */
	public Vector<EppFeeFee> getEppFeeFeeVector() {
		return eppFeeFeeVector;
	}
	
	public void addFee(EppFeeFee feeFee) {
		if (eppFeeFeeVector != null) {
			eppFeeFeeVector.add(feeFee);
		} else {
			eppFeeFeeVector = new Vector<EppFeeFee>();
			eppFeeFeeVector.add(feeFee);
		}
	}
	
	/**
	 * @param eppFeeFeeVector the eppFeeFeeVector to set
	 */
	public void setEppFeeFeeVector(Vector<EppFeeFee> eppFeeFeeVector) {
		this.eppFeeFeeVector = eppFeeFeeVector;
	}
	
	
	public Element toXML(Document doc, String tag) {
		Element body = doc.createElement(tag);
		toXMLCommon(doc, tag, body);
		return body;
	}
	
	public void toXMLCommon(Document doc, String tag, Element body) {
		super.toXMLCommon(doc, tag, body);
		if (eppFeeFeeVector != null) {
			for ( EppFeeFee feeFee: eppFeeFeeVector) {
				if (feeFee != null) {
					body.appendChild(feeFee.toXML(doc, "fee"));
				}
			}
		}
		
		if (feeClass != null) {
			Element classElement = doc.createElement("class");
			classElement.appendChild(doc.createTextNode(feeClass));
			body.appendChild(classElement);
			
		}
	}
	
	public static EppCommonResponseFeeType fromXML(Node root) {
		EppCommonResponseFeeType commonResponseFeeType = new EppCommonResponseFeeType();
		fromXMLCommon(root, commonResponseFeeType);
		return commonResponseFeeType;
	}
	
	public static void fromXMLCommon(Node root,EppCommonResponseFeeType commonResponseFeeType ) {
		EppCommonFeeType.fromXMLCommon(root, commonResponseFeeType);
		
		NodeList nodeList = root.getChildNodes();
		for (int i =0 ; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			String name = node.getLocalName();
			if (name == null ) {
				name = node.getNodeName();
			}
			
			if (name != null) {
				if (name.equals("fee")) {
					EppFeeFee feeFee = EppFeeFee.fromXML(node);
					commonResponseFeeType.addFee(feeFee);
					continue;
				}
				
				if (name.equals("class")) {
					commonResponseFeeType.feeClass = EppUtil.getText(node);
				}
			} else {
				continue;
			}
		}
		
	}
}
