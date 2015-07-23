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

import java.util.Calendar;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.neulevel.epp.core.EppUtil;
import com.neulevel.epp.core.response.EppReason;

/**
 * 
 * @author ankit.kothana
 *
 */
public class EppChangePollExt extends EppExtension {
	
	private String state;
	private EppChangePollOperation eppChangePollOperation;
	private Calendar date;
	private String who;
	private String serverTransId;
	private EppReason eppReason;
	

	public EppChangePollExt() {
	}

	public EppChangePollExt(String state,
			EppChangePollOperation eppChangePollOperation, Calendar date,
			String who, String serverTransId, EppReason eppReason) {
		this.state = state;
		this.eppChangePollOperation = eppChangePollOperation;
		this.date = date;
		this.who = who;
		this.serverTransId = serverTransId;
		this.eppReason = eppReason;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the eppChangePollOperation
	 */
	public EppChangePollOperation getEppChangePollOperation() {
		return eppChangePollOperation;
	}

	/**
	 * @param eppChangePollOperation the eppChangePollOperation to set
	 */
	public void setEppChangePollOperation(
			EppChangePollOperation eppChangePollOperation) {
		this.eppChangePollOperation = eppChangePollOperation;
	}

	/**
	 * @return the date
	 */
	public Calendar getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Calendar date) {
		this.date = date;
	}

	/**
	 * @return the who
	 */
	public String getWho() {
		return who;
	}

	/**
	 * @param who the who to set
	 */
	public void setWho(String who) {
		this.who = who;
	}

	/**
	 * @return the serverTransId
	 */
	public String getServerTransId() {
		return serverTransId;
	}

	/**
	 * @param serverTransId the serverTransId to set
	 */
	public void setServerTransId(String serverTransId) {
		this.serverTransId = serverTransId;
	}

	/**
	 * @return the eppReason
	 */
	public EppReason getEppReason() {
		return eppReason;
	}

	/**
	 * @param eppReason the eppReason to set
	 */
	public void setEppReason(EppReason eppReason) {
		this.eppReason = eppReason;
	}

	@Override
	public Element toXML(Document doc, String tag) {
		Element body = EppUtil.createElementNS(doc, "changePoll", "changeData");
		Element elem = null;
		if (state != null && state.length() > 0) {
			Attr attr = doc.createAttribute("state");
			attr.setValue( this.state );
			body.setAttributeNode(attr); 
		}
		
		if (eppChangePollOperation != null) {
			elem = eppChangePollOperation.toXML(doc, "operation");
			if (elem != null) {
				body.appendChild(elem);
			}
		}
		
		if (date != null) {
			elem = doc.createElement("date");
			elem.appendChild(EppUtil.createTextNode(doc, date));
			body.appendChild(elem);
		}
		
		if (serverTransId != null) {
			elem = doc.createElement("svTRID");
			elem.appendChild(doc.createTextNode(serverTransId));
			body.appendChild(elem);
		}
		
		if (who != null) {
			elem = doc.createElement("who");
			elem.appendChild(doc.createTextNode(who));
			body.appendChild(elem);
		}
		
		if (eppReason != null) {
			elem = eppReason.toXML(doc, "reason");
			if (elem != null) {
				body.appendChild(elem);
			}
		}
		
		return body;
	}
	
	public static EppChangePollExt fromXML(final Node root) {
		EppChangePollExt returnEppChangePollExt = new EppChangePollExt();
		NodeList nodeList = root.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			String name = node.getLocalName();
			if (name == null)
			{
				name = node.getNodeName();
			}

			if (name != null)
			{
				if (name.equals("operation"))
				{
					EppChangePollOperation changePollOp = EppChangePollOperation.fromXML(node);
					returnEppChangePollExt.setEppChangePollOperation(changePollOp);
					continue;
				}

				if (name.equals("date"))
				{
					returnEppChangePollExt.setDate(EppUtil.getDate(node));
					continue;
				}
				
				if (name.equals("svTRID"))
				{
					returnEppChangePollExt.setServerTransId(EppUtil.getText(node));
					continue;
				}
				
				if (name.equals("who"))
				{
					returnEppChangePollExt.setWho(EppUtil.getText(node));
					continue;
				}
				
				if (name.equals("reason"))
				{
					EppReason reason = EppReason.fromXML(node);
					returnEppChangePollExt.setEppReason(reason);
					continue;
				}
			} else {
				continue;
			}
		}
		
		NamedNodeMap attrs = root.getAttributes();
		for(int i = 0;i<attrs.getLength();i++) {
			Node attr = attrs.item(i);
			String attrValue = attr.getNodeValue();
			if( attr.getNodeName().equals("state") ) {
				returnEppChangePollExt.setState(attrValue);
				continue;
			} 
			
		}
		
		return returnEppChangePollExt;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new String("changePoll");
	}


}
