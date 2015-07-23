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

import com.neulevel.epp.core.EppPeriod;
import com.neulevel.epp.core.EppUtil;
import com.neulevel.epp.ext.EppExtension;

/**
 * 
 * @author ankit.kothana
 *
 */
public class EppCommonFeeType extends EppExtension  {
	
	protected String currency;
	protected EppFeeCommand feeCommand;
	protected EppPeriod period;
	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}
	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	/**
	 * @return the feeCommand
	 */
	public EppFeeCommand getFeeCommand() {
		return feeCommand;
	}
	/**
	 * @param feeCommand the feeCommand to set
	 */
	public void setFeeCommand(EppFeeCommand feeCommand) {
		this.feeCommand = feeCommand;
	}
	/**
	 * @return the period
	 */
	public EppPeriod getPeriod() {
		return period;
	}
	/**
	 * @param period the period to set
	 */
	public void setPeriod(EppPeriod period) {
		this.period = period;
	}
	
	@Override
	public Element toXML(Document doc, String tag) {
		Element body = doc.createElement(tag);
		toXMLCommon(doc, tag, body);
        return body;
	}
	
	public void toXMLCommon (Document doc, String tag, Element body) {
		if (body != null) {
			if (currency != null && currency.length() > 0) {
				Element currencyElement = doc.createElement("currency");
				currencyElement.appendChild(doc.createTextNode(currency));
				body.appendChild(currencyElement);
	        }
			
			if (feeCommand != null) {
	            body.appendChild(feeCommand.toXML(doc, "command"));
	        }
			
			if (period != null) {
	            body.appendChild(period.toXML(doc, "period"));
	        }
		}
	}
	
	public static EppCommonFeeType fromXML (Node root) {
		EppCommonFeeType returnCommonFeeType = new EppCommonFeeType();
		fromXMLCommon(root, returnCommonFeeType);
		return returnCommonFeeType;
	}
	
	public static void fromXMLCommon (Node root, EppCommonFeeType eppCommonFeeType ) {
		if (eppCommonFeeType != null) {
			NodeList nodeList = root.getChildNodes();
			for (int i= 0; i < nodeList.getLength() ; i++) {
				Node node = nodeList.item(i);
				String name = node.getLocalName();
				if (name == null ) {
					name = node.getNodeName();
				}
				
				if (name != null) {
					if (name.equals("currency")) {
						eppCommonFeeType.currency = EppUtil.getText(node);
						continue;
					}
					
					if (name.equals("command")) {
						eppCommonFeeType.feeCommand = EppFeeCommand.fromXML(node);
						continue;
					}
					
					if (name.equals("period")) {
						eppCommonFeeType.period = (EppPeriod) EppPeriod.fromXML(node);
						continue;
					}
				} else {
					continue;
				}
			}
		}
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
}
