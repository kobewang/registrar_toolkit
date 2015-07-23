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
package com.neulevel.epp.ext.command;

import java.util.*;
import org.w3c.dom.*;

import com.neulevel.epp.core.*;
import com.neulevel.epp.ext.*;

public class EppCommandCreateLaunchRegistration extends EppExtension {
	public EppCommandCreateLaunchRegistration() {
		this._phase = new EppLaunchPhase();
		this._type = new String();
		this._signedMark = new EppSignedMarkData();

		this._encodedSMD = new EppEncodedSignedMarkData();
		this._noticeID = new String();
		this._acceptedDate = new String();
		this._notAfter = new String();
	}

	public void phase(final EppLaunchPhase _p) {
		this._phase = _p;
	}

	public EppLaunchPhase phase() {
		return this._phase;
	}

	public void type(final String _i) {
		this._type = _i;
	}

	public String type() {
		return this._type;
	}

	public void signedMark(final EppSignedMarkData _input) {
		this._signedMark = _input;
	}

	public EppSignedMarkData signedMark() {
		return this._signedMark;
	}

	public EppEncodedSignedMarkData encodedSignedMark() {
		return this._encodedSMD;
	}

	public void encodedSignedMark(EppEncodedSignedMarkData data) {
		this._encodedSMD = data;
	}
	public String noticeID(){
		return _noticeID;
	}
	public String notAfter(){
		return _notAfter;
	}
	public String acceptedDate(){
		return _acceptedDate;
	}
	
	public void noticeID(String s){
		_noticeID = s;
	}
	public void notAfter(String s){
		_notAfter = s;
	}
	public void acceptedDate(String s){
		_acceptedDate = s;
	}

	public Element toXML(Document doc, String tag) {
		if (tag == null) {
			tag = "create";
		}
		Element body = EppUtil.createElementNS(doc, "launch", tag, false,
				"-1.0");
		if (this._type.length() > 0) {
			Attr attr = doc.createAttribute("type");
			attr.setValue(this._type);
			body.setAttributeNode(attr);
		}

		if (this._phase != null) {
			if (this._phase.phase().length() > 0) {
				Element elm = this._phase.toXML(doc, "launch:phase");
				body.appendChild(elm);
			}
		}

		if ((null != _signedMark) && (_signedMark.hasSMD())) {/* RAII */
			Element elmSmd = this._signedMark.toXML(doc, "smd:signedMark");
			body.appendChild(elmSmd);
		}
		if ((null != _encodedSMD) && (_encodedSMD.hasSMD())) {
			Element elmEncSMD = this._encodedSMD.toXML(doc,
					"smd:encodedSignedMark");
			body.appendChild(elmEncSMD);
		}
		if(_noticeID.length() > 0){
			Element notice = doc.createElement("launch:notice");
			Element noticeID = doc.createElement("launch:noticeID");
			noticeID.setTextContent(_noticeID);
			notice.appendChild(noticeID);
			Element notAfter = doc.createElement("launch:notAfter");
			notAfter.setTextContent(_notAfter);
			notice.appendChild(notAfter);
			Element acceptedDate = doc.createElement("launch:acceptedDate");
			acceptedDate.setTextContent(_acceptedDate);
			notice.appendChild(acceptedDate);
			body.appendChild(notice);
		}
		return body;
	}

	public static EppEntity fromXML(final Node root) {
		EppCommandCreateLaunchRegistration cmd = new EppCommandCreateLaunchRegistration();

		NodeList list = root.getChildNodes();
		NamedNodeMap attrs = root.getAttributes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (name == null)
				continue;
			if (name.equals("phase")) {
				EppLaunchPhase _pptr = EppLaunchPhase.fromXML(node);
				if (null != _pptr) {
					cmd._phase = _pptr;
				}
				_pptr = null;

			} else if (name.equals("signedMark")) {
				EppSignedMarkData _nd = (EppSignedMarkData) EppSignedMarkData
						.fromXML(node);
				if (null != _nd) {
					_nd.hasSMD(true);
					cmd._signedMark = _nd;
				}
			} else if (name.equals("encodedSignedMark")) {
				EppEncodedSignedMarkData _nd = (EppEncodedSignedMarkData) EppEncodedSignedMarkData
						.fromXML(node);
				if (null != _nd) {
					_nd.hasSMD(true);
					cmd._encodedSMD = _nd;
				}

			} else if (name.equals("notice")) {
				NodeList children = node.getChildNodes();
				for(int j= 0 ; j < children.getLength() ; j++){
					Node child = children.item(j);
					String chName = child.getLocalName();
					if (chName == null) {
						continue;
					}
					if(chName.equals("noticeID")) {
						String s = EppUtil.getText(child);
						cmd.noticeID(s);
					} else if (chName.equals("notAfter")){
						String s = EppUtil.getText(child);
						cmd.notAfter(s);
						
					} else if (chName.equals("acceptedDate")) {
						String s = EppUtil.getText(child);
						cmd.acceptedDate(s);
					}
				}
			}
		}
		for (int i = 0; i < attrs.getLength(); i++) {
			Node attr = attrs.item(i);
			String _v = attr.getNodeValue();
			if (attr.getNodeName().equals("type")) {
				cmd._type = attr.getNodeValue();
				break;
			}
		}
		return cmd;
	}

	public String toString() {
		return toString("launch:create", true);
	}

	private EppLaunchPhase _phase;
	private String _type;
	private EppSignedMarkData _signedMark;
	private EppEncodedSignedMarkData _encodedSMD;
	private String _noticeID;
	private String _notAfter;
	private String _acceptedDate;
};
