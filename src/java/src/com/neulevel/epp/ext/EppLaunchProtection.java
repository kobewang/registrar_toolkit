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


public class EppLaunchProtection extends EppEntity {
	public EppLaunchProtection()
	{
		this._cc = new String();
		this._ruling = new String();
		this._region = new String();
	}
	public String region()
	{
		return this._region;
	}
	public void region(final String _r)
	{
		this._region = _r;
	}
	public String ruling()
	{
		return this._ruling;
	}
	public void ruling(final String _c)
	{
		this._ruling = _c.substring(0,2);
	}
	public String countryCode()
	{
		return this._cc;
	}
	public void countryCode(final String _c)
	{
		this._cc = _c.substring(0,2);
	}

	public Element toXML(Document doc, final String tag)
	{
		Element elm;
		Element body = doc.createElement(tag);

		if( this._cc.length()>0) {
			elm = doc.createElement("mark:cc");
			elm.appendChild(doc.createTextNode(this._cc));
			body.appendChild(elm);
		}
		if( this._region.length() > 0 ) {
			elm = doc.createElement("mark:region");
			elm.appendChild(doc.createTextNode(this._region));
			body.appendChild(elm);
		}
		if( this._ruling.length() > 0 ) {
			elm = doc.createElement("mark:ruling");
			elm.appendChild(doc.createTextNode(this._ruling));
			body.appendChild(elm);
		}

		return body;
	}

	public static EppLaunchProtection fromXML( final Node root )
	{
		EppLaunchProtection _ret = new EppLaunchProtection();
		if( null == _ret )
			return null;
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
			if( name.equals("cc") ) {
				_ret._cc = (EppUtil.getText(node)).substring(0,2);
			} else if( name.equals("region") ) {
				_ret._region= EppUtil.getText(node);
			} else if( name.equals("ruling") ) {
				_ret._ruling = EppUtil.getText(node);
			}
		}
		return _ret;
	}

	public String toString()
	{
		return new String("mark:protection");
	}

	private String _cc;/*country code*/
	private String _ruling;/*attribute length 2*/
	private String _region;
};

