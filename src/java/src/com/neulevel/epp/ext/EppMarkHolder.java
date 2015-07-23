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


public class EppMarkHolder extends EppEntity {
	public static final int EPPMARK_HOLDER = 0;
	public static final int EPPMARK_CONTACT = 1;

	public EppMarkHolder()
	{
		this(EPPMARK_HOLDER);
	}

 	public EppMarkHolder( int _t)
	{
		this._name = new String();
		this._org = new String();
		this._addr = new EppMarkAddress();
		this._voice = new EppE164("");
		this._fax = new EppE164("");
		this._email = new String();
		this._addParam = new String();
		this._type = _t;
	}
	public void name(final String _n)
	{
		this._name = _n;
	}
	public String name()
	{
		return this._name;
	}
	public void org(final String _o)
	{
		this._org = _o;
	}
	public String org()
	{
		return this._org;
	}
	public void address(final EppMarkAddress _a)
	{
		this._addr = _a;
	}
	public EppMarkAddress address()
	{
		return this._addr;
	}
	public void voice(final String _v)
	{
		this._voice.setNumber(_v);
	}
	public String voice()
	{
		return this._voice.getNumber();
	}
	public void fax(final String _f)
	{
		this._fax.setNumber(_f);
	}
	public String fax()
	{
		return this._fax.getNumber();
	}
	public void email(final String _e)
	{
		this._email = _e;
	}
	public String email()
	{
		return this._email;
	}
	public void type(int _t)
	{
		this._type = _t;
	}
	public int type()
	{
		return this._type;
	}
	public void holderParam(final String _s)
	{
		this._addParam = _s;
	}
	public String holderParam()
	{
		return this._addParam;
	}

	public Element toXML(Document doc, final String tag)
	{
		String attr_name = "type";
		Element elm;
		Element body = doc.createElement(tag);
	
		if( this._type == EppMarkHolder.EPPMARK_HOLDER ) {
			attr_name = "entitlement";
		}
		Attr attr = doc.createAttribute(attr_name);
		attr.setValue( this._addParam );
		body.setAttributeNode(attr);
	
		if( this._name.length() > 0 ) {
			elm = doc.createElement("mark:name");
			elm.appendChild(doc.createTextNode(this._name));
			body.appendChild(elm);
		}
		if( this._org.length() > 0 ) {
			elm = doc.createElement("mark:org");
			elm.appendChild(doc.createTextNode(this._org));
			body.appendChild(elm);
		}
		body.appendChild(this._addr.toXML(doc,"mark:addr"));

		if( this._voice.getNumber().length() > 0 ) {
			elm = this._voice.toXML(doc,"mark:voice");
			body.appendChild(elm);
		}
		if( this._fax.getNumber().length() > 0 ) {
			elm = this._fax.toXML(doc,"mark:fax");
			body.appendChild(elm);
		}
		if( this._email.length() > 0 ) {
			elm = doc.createElement("mark:email");
			elm.appendChild(doc.createTextNode(this._email));
			body.appendChild(elm);
		}

		return body;
	}
 /* NOTE: need to keep _type field in consideration as attribute type/entitlement depends on it.*/
	public static  EppEntity fromXML( final Node root )
	{
		EppMarkHolder _ret = new EppMarkHolder(EppMarkHolder.EPPMARK_HOLDER);
		if( null == _ret )
			return null;
		NodeList list  = root.getChildNodes();
		NamedNodeMap attrs = root.getAttributes();

		for( int i = 0; i < list.getLength(); i++ ) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if( name == null )
				continue;
			if( name.equals("name") ) {
				_ret._name = EppUtil.getText(node);
			} else if ( name.equals("org") ) {
				_ret._org = EppUtil.getText(node);
			} else if ( name.equals("addr") ) {
				_ret._addr = ((EppMarkAddress)EppMarkAddress.fromXML( node ));
			} else if ( name.equals("voice") ) {
				EppE164 _v = (EppE164)EppE164.fromXML((Node)node);
				if( null != _v ) {
					_ret._voice = _v;
				}
			} else if ( name.equals("fax") ) {
				EppE164 _f = (EppE164)EppE164.fromXML((Node)node);
				if( null != _f ) {
					_ret._fax = _f;
				}
			} else if ( name.equals("email") ) {
				_ret._email = EppUtil.getText(node);
			}
		}

		for(int i = 0;i<attrs.getLength();i++) {
			Node attr = attrs.item(i);
			String _v = attr.getNodeValue();
			if( attr.getNodeName().equals("entitlement") ) {
				_ret._type = EppMarkHolder.EPPMARK_HOLDER;
				if( _v.length() > 0 ) {
					_ret._addParam = _v;
				}
				break;
			} else if (attr.getNodeName().equals("type") ) {
				_ret._type = EppMarkHolder.EPPMARK_CONTACT;
				if( _v.length() > 0 ) {
					_ret._addParam = _v;
				}
				break;
			}
		}
		return _ret;
	}
	public String toString()
	{
		return super.toString("mark:contact");
	}
	private String _name;/* optional unless org is specified */ 
	private String _org;/* optional if _name is specified */ 
	private EppMarkAddress _addr;/* address of the entiry */ 
	private EppE164 _voice; /* optional phone number */ 
	private EppE164 _fax; /* optional fax number */ 
	private String _email; /* optional email */ 
	private String _addParam; /*some TM data can specify type/entitlement param to oject*/ 
	private int _type; /*identifies if it's type of holder or contact*/ 
};

