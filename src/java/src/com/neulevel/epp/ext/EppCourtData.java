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

public class EppCourtData extends EppMarkData {

	public EppCourtData()
	{
		this._refNum = new String();
		this._proDate = Calendar.getInstance();
		this._cc = new String();
		this._region = new Vector();
		this._courtName = new String();
	}
	public void refNum(final String _r)
	{
		this._refNum = _r;
	}
	public String refNum()
	{
		return this._refNum;
	}
	public void proDate(final Calendar _rd)
	{
		this._proDate = _rd;
	}
	public Calendar proDate()
	{
		return this._proDate;
	}

	public void countryCode(final String _cc)
	{
		this._cc = _cc;
	}
	public String countryCode()
	{
		return this._cc;
	}

	public void addRegion(final String _r)
	{
		this._region.addElement(_r);
	}
	public Vector region()
	{
		return this._region;
	}

	public void courtName(final String _cn)
	{
		this._courtName = _cn;
	}
	public String courtName()
	{
		return this._courtName;
	}

	public Element toXML(Document doc, final String tag)
	{
		Element elm;
		Element body = super.toXML(doc,"mark:court");

		if( this.Id.length() > 0 ) {
			elm = doc.createElement("id");
			elm.appendChild(doc.createTextNode(this.Id));
			body.appendChild(elm);
		}
		if( this._markName.length() > 0 ) {
			elm = doc.createElement("mark:markName");
			elm.appendChild(doc.createTextNode(this._markName));
			body.appendChild(elm);
		}
		for( int i=0;i<_holder.size();i++) {
			EppMarkHolder _h =(EppMarkHolder) _holder.elementAt(i);
			body.appendChild(_h.toXML( doc,"mark:holder"));
		}
		for( int i=0;i<_contact.size();i++) {
			EppMarkHolder _c = (EppMarkHolder)_contact.elementAt(i);
			body.appendChild(_c.toXML( doc,"mark:contact"));
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

		if( this._refNum.length() > 0 ) {
			elm = doc.createElement("mark:refNum");
			elm.appendChild(doc.createTextNode(this._refNum));
			body.appendChild(elm);
		}
		if( this._proDate != null ) {
			elm = doc.createElement("mark:proDate");
			elm.appendChild(EppUtil.createTextNode(doc,this._proDate));
			body.appendChild(elm);
		}
		if( this._cc.length() > 0 ) {
			elm = doc.createElement("mark:cc");
			elm.appendChild(doc.createTextNode(this._cc));
			body.appendChild(elm);
		}
		for( int i=0;i<this._region.size();i++) {
			String _s = (String)this._region.elementAt(i);
			if( _s.length() > 0 ) {
				elm = doc.createElement("mark:region");
				elm.appendChild(doc.createTextNode(_s));
				body.appendChild(elm);
			}
		}
		if( this._courtName.length() > 0 ) {
			elm = doc.createElement("mark:courtName");
			elm.appendChild(doc.createTextNode(this._courtName));
			body.appendChild(elm);
		}
		return body;
	}

	public static EppCourtData fromXML( final Node root )
	{
		Node _temp_root = root;
		EppCourtData _ret = new EppCourtData();
		_ret.fromXml(_temp_root);
		NodeList list = root.getChildNodes();
		for( int i = 0; i < list.getLength(); i++ ) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if( name == null ) {
				name = node.getNodeName();
			}
			if( name == null ) {
				continue;
			}
			if( name.substring(0, 5).equals("mark:") ) {
				name = name.substring(5, name.length() - 5);
			}
			if( name.equals("refNum") ) {
				_ret._refNum = EppUtil.getText(node);
			} else if( name.equals("proDate") ) {
				_ret._proDate = EppUtil.getDate(node);
			} else if( name.equals("cc") ) {
				String _scc = EppUtil.getText(node);
				_ret._cc = _scc.substring(0,2);
			} else if( name.equals("region") ) {
				String _sr = EppUtil.getText(node);
				_ret._region.addElement(_sr);
			} else if( name.equals("courtName") ) {
				String _scn = EppUtil.getText(node);
				_ret._courtName = _scn;
			}
		}
		return _ret;
	}
	public String toString()
	{
		return new String("mark:court");
	}

	private String _refNum; /*registration number of TM*/ 
	private Calendar _proDate; /*optional*/ 
	private String _cc;
	private Vector _region;
	private String _courtName;
};

