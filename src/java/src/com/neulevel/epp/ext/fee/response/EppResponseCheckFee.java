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
package com.neulevel.epp.ext.fee.response;

import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.neulevel.epp.core.EppUtil;
import com.neulevel.epp.ext.EppExtension;
import com.neulevel.epp.ext.fee.EppResponseCheckFeeType;

public class EppResponseCheckFee extends EppExtension {
	
	private Vector<EppResponseCheckFeeType> responseCheckFeeTypeVector;
	
	

	/**
	 * @return the responseCheckFeeTypeVector
	 */
	public Vector<EppResponseCheckFeeType> getResponseCheckFeeTypeVector() {
		return responseCheckFeeTypeVector;
	}

	/**
	 * @param responseCheckFeeTypeVector the responseCheckFeeTypeVector to set
	 */
	public void setResponseCheckFeeTypeVector(
			Vector<EppResponseCheckFeeType> responseCheckFeeTypeVector) {
		this.responseCheckFeeTypeVector = responseCheckFeeTypeVector;
	}

	public EppResponseCheckFee() {
		responseCheckFeeTypeVector = new Vector<EppResponseCheckFeeType>();
	}
	
	public void add (EppResponseCheckFeeType responseCheckFeeType) {
		responseCheckFeeTypeVector.add(responseCheckFeeType);
	}
	
	@Override
	public Element toXML(Document doc, String tag) {
		Element body = EppUtil.createElementNS(doc, "fee", (tag == null) ? "chkData" : tag,true,"-0.6");
		for (EppResponseCheckFeeType responseCheckFeeType : responseCheckFeeTypeVector) {
			Element element = responseCheckFeeType.toXML(doc, "cd");
			body.appendChild(element);
		}
		return body;
	}
	
	public static EppResponseCheckFee fromXML(Node root) {
		EppResponseCheckFee responseCheckFee = new EppResponseCheckFee();
		NodeList nodeList = root.getChildNodes();
		
		for (int i=0; i< nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			String name = node.getLocalName();
			if (name == null) {
				name = node.getNodeName();
			}
			
			if (name != null) {
				if (name.equals("cd")) {
					EppResponseCheckFeeType responseCheckFeeType = EppResponseCheckFeeType.fromXML(node);
					responseCheckFee.add(responseCheckFeeType);
				}
				
			} else {
				continue;
			}
			
		}
		
		return responseCheckFee;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
