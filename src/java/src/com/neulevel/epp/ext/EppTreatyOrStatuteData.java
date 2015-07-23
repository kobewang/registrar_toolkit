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

public class EppTreatyOrStatuteData extends EppMarkData {

	public EppTreatyOrStatuteData()
	{
		this._region = new Vector();
		this._refNum = new String();
		this._proDate = Calendar.getInstance();
		this._title = new String();
		this._exDate = Calendar.getInstance();
	}
	public void refNum(final String _r)
	{
		this._refNum = _r;
	}
	public String refNum()
	{
		return this._refNum;
	}
	public void proDate(final Calendar t)
	{
		this._proDate = t;
	}
	public Calendar proDate()
	{
		return this._proDate;
	}
	public void exDate(final Calendar t)
	{
		this._exDate = t;
	}
	public Calendar exDate()
	{
		return this._exDate;
	}
	public void title(final String _tt)
	{
		this._title = _tt;
	}
	public String title()
	{
		return this._title;
	}

	public void addRegion(final EppLaunchProtection _r)
	{
		this._region.addElement(_r);
	}
	public Vector region()
	{
		return this._region;
	}

	public Element toXML(Document doc, final String tag)
	{
		Element elm;
		Element body = super.toXML(doc,"mark:treatyOrStatute");

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
			EppMarkHolder _h = (EppMarkHolder)_holder.elementAt(i);
			body.appendChild(_h.toXML( doc,"mark:holder"));
		}
		for( int i=0;i<_contact.size();i++) {
			EppMarkHolder _c = (EppMarkHolder)_contact.elementAt(i);
			body.appendChild(_c.toXML( doc,"mark:contact"));
		}
		for( int i=0;i<_region.size();i++) {
			EppLaunchProtection _c = (EppLaunchProtection)_region.elementAt(i);
			body.appendChild(_c.toXML( doc,"mark:protection"));
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
		if( this._title.length() > 0 ) {
			elm = doc.createElement("mark:title");
			elm.appendChild(doc.createTextNode(this._title));
			body.appendChild(elm);
		}
		if( this._exDate != null ) {
			elm = doc.createElement("mark:execDate");
			elm.appendChild(EppUtil.createTextNode(doc,this._exDate));
			body.appendChild(elm);
		}
		return body;
	}

	public static EppTreatyOrStatuteData fromXML( final Node root )
	{
		Node _temp_root = root;
		EppTreatyOrStatuteData _ret = new EppTreatyOrStatuteData();
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
			} else if( name.equals("title") ) {
				_ret._title = EppUtil.getText(node);
			} else if( name.equals("protection") ) {
				EppLaunchProtection _pro = EppLaunchProtection.fromXML(node);
				if( null != _pro ) {
					_ret._region.addElement( _pro );
				}
			} else if( name.equals("execDate") ) {
				_ret._exDate = EppUtil.getDate(node);
			}
		}
		return _ret;
	}
	public String toString()
	{
		return new String("mark:treatyOrStatute");
	}
	private Vector _region;
	private String _refNum; /*registration number of TM*/ 
	private Calendar _proDate; /*optional*/ 
	private String _title;
	private Calendar _exDate; /*optional*/ 
}

