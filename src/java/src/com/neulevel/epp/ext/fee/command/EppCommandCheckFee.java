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
package com.neulevel.epp.ext.fee.command;

import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.neulevel.epp.core.EppUtil;
import com.neulevel.epp.ext.EppExtension;
import com.neulevel.epp.ext.fee.EppCommandCheckFeeType;
import com.neulevel.epp.ext.fee.EppCommonFeeType;

/**
 * 
 * @author ankit.kothana
 *
 */
public class EppCommandCheckFee extends EppExtension {
	
	protected Vector<EppCommandCheckFeeType> eppCommandCheckFeeTypeVector;
	
	public EppCommandCheckFee() {
		eppCommandCheckFeeTypeVector = new Vector<EppCommandCheckFeeType>();
	}
	
	public void add (EppCommandCheckFeeType eppCommandCheckFeeType) {
		eppCommandCheckFeeTypeVector.add(eppCommandCheckFeeType);
	}

	@Override
	public Element toXML(Document doc, String tag) {
		Element body = EppUtil.createElementNS(doc, "fee", (tag == null) ? "check" : tag,true,"-0.6");
		
		for (EppCommandCheckFeeType eppCommandCheckFeeType : eppCommandCheckFeeTypeVector) {
			Element element = eppCommandCheckFeeType.toXML(doc, "domain");
			body.appendChild(element);
		}
		return body;
	}
	
	public static EppCommandCheckFee fromXML(Node root) {
		EppCommandCheckFee eppCommandCheckFee = new EppCommandCheckFee();
		NodeList nodeList = root.getChildNodes();
		
		for (int i=0; i< nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			String name = node.getLocalName();
			if (name == null) {
				name = node.getNodeName();
			}
			
			if (name != null) {
				if (name.equals("domain")) {
					EppCommandCheckFeeType eppCommandCheckFeeType = EppCommandCheckFeeType.fromXML(node);
					if (eppCommandCheckFeeType != null) {
						eppCommandCheckFee.add(eppCommandCheckFeeType);
					}
				}
				
			} else {
				continue;
			}
			
		}
		
		return eppCommandCheckFee;
	}
	
	@Override
	public String toString()
	{
		return new String("fee:domain");
	}

}
