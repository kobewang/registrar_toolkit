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
public class EppChangePollOperation extends EppEntity {
	
	private String operation;
	private String subOperation;

	public EppChangePollOperation(String operation, String subOperation) {
		this.operation = operation;
		this.subOperation = subOperation;
	}

	public EppChangePollOperation() {
	}

	/**
	 * @return the operation
	 */
	public String getOperation() {
		return operation;
	}

	/**
	 * @param operation the operation to set
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}

	/**
	 * @return the subOperation
	 */
	public String getSubOperation() {
		return subOperation;
	}

	/**
	 * @param subOperation the subOperation to set
	 */
	public void setSubOperation(String subOperation) {
		this.subOperation = subOperation;
	}

	@Override
	public Element toXML(Document doc, String tag) {
		Element body = doc.createElement(tag);
		if (operation != null && operation.length() > 0) {
			body.appendChild(doc.createTextNode(operation));
		}
		
		if (subOperation != null && subOperation.length() > 0) {
			Attr attr = doc.createAttribute("op");
			attr.setValue( this.subOperation );
			body.setAttributeNode(attr); 
		}
		return body;
	}
	
	public static EppChangePollOperation fromXML(final Node root) {
		NamedNodeMap attrs = root.getAttributes();
		EppChangePollOperation returnEppChangePollOperation = new EppChangePollOperation();
		String name = root.getLocalName();
		
		if (name == null) {
			name = root.getNodeName();
		}
		
		if (name != null) {
			if (name.equals("operation")) {
				returnEppChangePollOperation.operation = EppUtil.getText(root);
			}
		}
		
		for(int i = 0;i<attrs.getLength();i++) {
			Node attr = attrs.item(i);
			String attrValue = attr.getNodeValue();
			if( attr.getNodeName().equals("op") ) {
				returnEppChangePollOperation.subOperation = attrValue;
				continue;
			} 
			
		}
		
		return returnEppChangePollOperation;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new String("operation");
	}

	

}
