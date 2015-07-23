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

public class EppMarkAddress extends EppEntity {

	public EppMarkAddress()
	{
		this._city = new String();
		this._sp= new String();
		this._pc= new String();
		this._cc= new String();
		this._street = new String[3];

		for(int i=0;i<3;i++)
			this._street[i] = new String();
	}

	public String streetLine1()
	{
		return this._street[0];
	}
	public void streetLine1(String line1)
	{
		this._street[0] = line1;
	}
	public String streetLine2()
	{
		return this._street[1];
	}
	public void streetLine2(String line1)
	{
		this._street[1] = line1;
	}
	public String streetLine3()
	{
		return this._street[2];
	}
	public void streetLine3(final String line1)
	{
		this._street[2] = line1;
	}
	public String city()
	{
		return this._city;
	}
	public void city(final String _c)
	{
		this._city = _c.substring(0,2);
	}
	public String stateProvince()
	{
		return this._sp;
	}
	public void stateProvince(final String _s)
	{
		this._sp = _s;
	}
	public String postalCode()
	{
		return this._pc;
	}
	public void postalCode(final String _p)
	{
		this._pc = _p;
	}
	public String countryCode()
	{
		return this._cc;
	}
	public void countryCode(final String _c)
	{
		this._cc = _c;
	}

	public Element toXML(Document doc, final String tag)
	{
		Element elm;
		Element body = doc.createElement(tag);
		for(int i=0;i<3;i++) {
			if( this._street[i].length() > 0 ) {
				elm = doc.createElement("mark:street");
				elm.appendChild(doc.createTextNode(this._street[i]));
				body.appendChild(elm);
			}
		}
		if( this._city.length() > 0 ) {
			elm = doc.createElement("mark:city");
			elm.appendChild(doc.createTextNode(this._city));
			body.appendChild(elm);
		}
		if(this._sp.length()>0) {
			elm = doc.createElement("mark:sp");
			elm.appendChild(doc.createTextNode(this._sp));
			body.appendChild(elm);
		}
		if( this._pc.length()>0) {
			elm = doc.createElement("mark:pc");
			elm.appendChild(doc.createTextNode(this._pc));
			body.appendChild(elm);
		}
		if( this._cc.length()>0) {
			elm = doc.createElement("mark:cc");
			elm.appendChild(doc.createTextNode(this._cc));
			body.appendChild(elm);
		}
		return body;
	}

	public static  EppEntity fromXML( final Node root )
	{
		int _street_c = 0;
		EppMarkAddress _ret = new EppMarkAddress();
		if( null == _ret )
			return null;
		NodeList list = root.getChildNodes();
		for( int i = 0; i < list.getLength(); i++ ) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if( name == null ) {
				continue;
			}
			if( name.equals("street") ) {
				if( 3 > _street_c )
					_ret._street[_street_c++] = EppUtil.getText(node);
			} else if( name.equals("city") ) {
				_ret._city = EppUtil.getText(node);
			} else if( name.equals("sp") ) {
				_ret._sp = EppUtil.getText(node);
			} else if( name.equals("pc") ) {
				_ret._pc = EppUtil.getText(node);
			} else if( name.equals("cc") ) {
				_ret._cc = (EppUtil.getText(node)).substring(0,2);
			}
		}
		return _ret;
	}
	public String toString()
	{
		return super.toString("mark:addr");
	}
	private String[] _street; /*one, two or three ; optional street*/ 
	private String _city; 
	private String _sp;/* optional state/province*/ 
	private String _pc;/*postal code*/ 
	private String _cc;/*country code*/
};

