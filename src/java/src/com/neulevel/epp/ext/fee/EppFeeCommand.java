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

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.neulevel.epp.core.EppUtil;

/**
 * 
 * @author ankit.kothana
 *
 */
public class EppFeeCommand {
	protected String command;
	protected String phase;
	protected String subphase;
	
	public String getCommand() {
		return command;
	}
	
	public void setCommand(String command) {
		this.command = command;
	}
	
	public String getPhase() {
		return phase;
	}
	
	public void setPhase(String phase) {
		this.phase = phase;
	}
	
	public String getSubphase() {
		return subphase;
	}
	
	public void setSubphase(String subphase) {
		this.subphase = subphase;
	}

	public Element toXML(Document doc, String tag) {
		Element body = doc.createElement(tag);
		if (command != null && command.length() > 0) {
            body.appendChild(doc.createTextNode(this.command));
        }
		if (phase != null && phase.length() > 0) {
			Attr attr = doc.createAttribute("phase");
			attr.setValue( this.phase );
			body.setAttributeNode(attr); 
		}
		
		if (subphase != null && subphase.length() > 0) {
			Attr attr = doc.createAttribute("subphase");
			attr.setValue( this.subphase );
			body.setAttributeNode(attr); 
		}
		
        return body;
	}
	
	public static EppFeeCommand
	fromXML( final Node root )
	{
		NamedNodeMap attrs = root.getAttributes();
		EppFeeCommand returnFeeCommand = new EppFeeCommand();
		
		String name = root.getLocalName();
		if( name == null ) {
			name = root.getNodeName();
		}
		if( name != null ) {
			if( name.equals("command") ) {
				returnFeeCommand.command = EppUtil.getText(root);
			}
		}
		
		for(int i = 0;i<attrs.getLength();i++) {
			Node attr = attrs.item(i);
			String attrValue = attr.getNodeValue();
			if( attr.getNodeName().equals("phase") ) {
				returnFeeCommand.phase = attrValue;
			} else if (attr.getNodeName().equals("subphase")) {
				returnFeeCommand.subphase = attrValue;
			}
		}
		return returnFeeCommand;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
