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
package com.neulevel.epp.xri.response;

import java.util.*;
import org.w3c.dom.*;
import com.neulevel.epp.core.*;
import com.neulevel.epp.core.response.*;

/**
 * This <code>EppResponseDataCreateXriCodeString</code> class implements EPP
 * Response Data entity for EPP Command Create of EPP XRI CodeString objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.1 $ $Date: 2009/11/23 14:35:33 $
 */
public class EppResponseDataCreateXriCodeString extends EppResponseDataCreate {

	public EppResponseDataCreateXriCodeString() {
		this.codeString = null;
		this.crDate = Calendar.getInstance();
	}

	public EppResponseDataCreateXriCodeString( String codeString ) {
		this.codeString = codeString;
		this.crDate = Calendar.getInstance();
	}

	public EppResponseDataCreateXriCodeString( String codeString, Calendar crDate ) {
		this.codeString = codeString;
		this.crDate = crDate;
	}

	public void setCodeString( String codeString ) {
		this.codeString = codeString;
	}

	public String getCodeString() {
		return this.codeString;
	}

	public static EppResponseDataCreateXriCodeString fromXML( Node root ) {
		String auth_id = null;
		Calendar create_date = null;
		NodeList list = root.getChildNodes();
		for(int i=0;i<list.getLength();i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if(name == null) {
				continue;
			}
			if(name.equals("codeString") || name.equals("xriCS:codeString")) {
				String id = EppUtil.getText(node);
				if( (id != null) && (id.length()>0)) {
					auth_id = id;
				}
			} else if(name.equals("crDate") || name.equals("xriCS:crDate")) {
				Calendar t = EppUtil.getDate(node);
				if(t != null) {
					create_date = t;
				}
			}
		}
		return new EppResponseDataCreateXriCodeString(auth_id,create_date);
	}

	public Element toXML( Document doc, String tag ) {
		Element elm;
		Element body = doc.createElement(tag);
		Element data = EppUtil.createElementNS(doc,"xriCS","creData");
		body.appendChild(data);
		if(codeString != null) {
			elm = doc.createElement("codeString");
			elm.appendChild(doc.createTextNode(codeString));
			data.appendChild(elm);
		}
		if(crDate != null ) {
			elm = doc.createElement("crDate");
			elm.appendChild(EppUtil.createTextNode(doc,crDate));
			data.appendChild(elm);
		}
		return body;

	}
	private String codeString;
}
