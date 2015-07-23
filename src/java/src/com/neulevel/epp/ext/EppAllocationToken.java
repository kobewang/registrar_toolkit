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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.neulevel.epp.core.EppEntity;
import com.neulevel.epp.core.EppUtil;

public class EppAllocationToken extends EppExtension {

    private String allocationToken;
    
    public EppAllocationToken() {
        this.allocationToken = new String();
    }
    
    public EppAllocationToken(String allocToken) {
        this.allocationToken = allocToken;
    }
    
    public void allocationToken(final String tkn) {
        this.allocationToken = tkn;
    }
    
    public String allocationToken(){
        return this.allocationToken;
    }
    
    @Override
    public String toString() {
        return new String("allocationToken:allocationToken");
    }

    @Override
    public Element toXML(Document doc, String tag) {
        Element body = EppUtil.createElementNS(doc, "allocationToken", (tag == null) ? "allocationToken" : tag,true,"-1.0");
        if (this.allocationToken.length() > 0) {
            body.appendChild(doc.createTextNode(this.allocationToken));
        }
        return body;
    }
    
    public static EppEntity fromXML(final Node root) {

        EppAllocationToken allocationTokenObj = new EppAllocationToken();
        Node node = root;
		String name = node.getLocalName();
		if( name == null ) {
			name = node.getNodeName();
		}
		if( name != null ) {
			if( name.startsWith("allocationToken:") ) {
				name = name.substring(16, name.length() - 16);
			}
			if( name.equals("allocationToken") ) {
				allocationTokenObj.allocationToken = EppUtil.getText(node);
			}
		}
        return allocationTokenObj;
    }

}
