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
import java.util.*;
import org.w3c.dom.*;
import com.neulevel.epp.core.*;

public class EppTrademarkData extends EppMarkData {

	public EppTrademarkData()
	{
		this._jurisdiction = new String();
		this._class = new Vector();
		this._apId = new String();
		this._apDate = Calendar.getInstance();
		this._regNum = new String();
		this._regDate = Calendar.getInstance();
		this._exDate = Calendar.getInstance();
	}
	public void jurisdiction(final String _j)
	{
		this._jurisdiction = _j.substring(0,2);
	}
	public String jurisdiction()
	{
		return this._jurisdiction;
	}
	public void tmClass(Integer _c)
	{
		this._class.addElement(_c);
	}
	public Vector tmClass()
	{
		return this._class;
	}
	public void apId(final String _a)
	{
		this._apId = _a;
	}
	public String apId()
	{
		return this._apId;
	}
	public void apDate(final Calendar _d)
	{
		this._apDate = _d;
	}
	public Calendar apDate()
	{
		return this._apDate;
	}
	public void regNum(final String _r)
	{
		this._regNum = _r;
	}
	public String regNum()
	{
		return this._regNum;
	}
	public void regDate(final Calendar _rd)
	{
		this._regDate = _rd;
	}
	public Calendar regDate()
	{
		return this._regDate;
	}
	public void exDate(final Calendar _ed)
	{
		this._exDate = _ed;
	}
	public Calendar exDate()
	{
		return this._exDate;
	}
	public Element toXML(Document doc, final String tag)
	{
		Element elm;
		Element body = super.toXML(doc,"mark:trademark");
	
		if( this.Id.length() > 0 ) {
			elm = doc.createElement("mark:id");
			elm.appendChild(doc.createTextNode(this.Id));
			body.appendChild(elm);
		}
		if( this._markName.length() > 0 ) {
			elm = doc.createElement("mark:markName");
			elm.appendChild(doc.createTextNode(this._markName));
			body.appendChild(elm);
		}
		for( int i=0;i<_holder.size();i++) {
			EppMarkHolder _h = (EppMarkHolder)_holder.elementAt(i);
			body.appendChild(_h.toXML( doc,"mark:holder"));
		}
		for( int i=0;i<_contact.size();i++) {
			EppMarkHolder _c = (EppMarkHolder)_contact.elementAt(i);
			body.appendChild(_c.toXML( doc,"mark:contact"));
		}

		if( this._jurisdiction.length() > 0 ) {
			elm = doc.createElement("mark:jurisdiction");
			elm.appendChild(doc.createTextNode(this._jurisdiction));
			body.appendChild(elm);
		}
		for(int i=0;i<this._class.size();i++) {
			Integer _c = (Integer)this._class.elementAt(i);
			elm = doc.createElement("mark:class");
			elm.appendChild(doc.createTextNode(_c.toString()));
			body.appendChild(elm);
		}
	
		for( int i=0;i<_label.size();i++) {
			String _s = (String)_label.elementAt(i);
			if( _s.length() > 0 ) {
				elm = doc.createElement("mark:label");
				elm.appendChild(doc.createTextNode(_s));
				body.appendChild(elm);
			}
		}
		if( this.goodsAndServices.length() > 0 ) {
			elm = doc.createElement("mark:goodsAndServices");
			elm.appendChild(doc.createTextNode(this.goodsAndServices));
			body.appendChild(elm);
		}

		if( _apId.length() > 0 ) {
			elm = doc.createElement("mark:apId");
			elm.appendChild(doc.createTextNode(this._apId));
			body.appendChild(elm);
		}
		if( this._apDate != null ) {
			elm = doc.createElement("mark:apDate");
			elm.appendChild(EppUtil.createTextNode(doc,this._apDate));
			body.appendChild(elm);
		}
		if( this._regNum.length() > 0 ) {
			elm = doc.createElement("mark:regNum");
			elm.appendChild(doc.createTextNode(this._regNum));
			body.appendChild(elm);
		}
		if( this._regDate != null ) {
			elm = doc.createElement("mark:regDate");
			elm.appendChild(EppUtil.createTextNode(doc,this._regDate));
			body.appendChild(elm);
		}
		if( this._exDate != null ) {
			elm = doc.createElement("mark:exDate");
			elm.appendChild(EppUtil.createTextNode(doc,this._exDate));
			body.appendChild(elm);
		}
		return body;
	}

	public static EppEntity fromXML( final Node root )
	{
		Node _temp_root = root;
		EppTrademarkData _ret = new EppTrademarkData();
		_ret.fromXml(_temp_root);
		NodeList list = root.getChildNodes();
		for( int i = 0; i < list.getLength(); i++ ) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if( name == null ) {
				continue;
			}
			if( name.equals("jurisdiction") ) {
				_ret._jurisdiction = (EppUtil.getText(node)).substring(0,2);
			} else if( name.equals("class") ) {
				String _sn = EppUtil.getText(node);
				_ret._class.addElement( Integer.valueOf(_sn) );
			} else if( name.equals("apId") ) {
				_ret._apId = EppUtil.getText(node);
			} else if( name.equals("apDate") ) {
				_ret._apDate = EppUtil.getDate(node);
			} else if( name.equals("regNum") ) {
				_ret._regNum = EppUtil.getText(node);
			} else if( name.equals("regDate") ) {
				_ret._regDate = EppUtil.getDate(node);
			} else if( name.equals("exDate") ) {
				_ret._exDate = EppUtil.getDate(node);
			}
		}
		return _ret;
	}
	public String toString()
	{
		return new String("mark:trademark");
	}
	private String _jurisdiction; 
	private Vector _class; /* * */ 
	private String _apId; /*optional*/ 
	private Calendar _apDate; /*optional*/ 
	private String _regNum; /*registration number of TM*/ 
	private Calendar _regDate; /*registration date*/ 
	private Calendar _exDate;/*optional: expiration date*/
};

