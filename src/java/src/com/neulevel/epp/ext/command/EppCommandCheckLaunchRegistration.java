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
import com.neulevel.epp.ext.EppLaunchPhase;


public class EppCommandCheckLaunchRegistration extends EppExtension {

	public EppCommandCheckLaunchRegistration()
	{
		this._phase = new EppLaunchPhase();
		this._type = new String("claims");
	}

	public void
	phase(final EppLaunchPhase _p)
	{
		this._phase = _p;
	}

	public EppLaunchPhase
	phase()
	{
		return this._phase;
	}

	public void
	type(final String _i)
	{
		this._type = _i;
	}

	public String
	type()
	{
		return this._type;
	}

	public static EppCommandCheckLaunchRegistration
	fromXML( final Node root )
	{
		EppCommandCheckLaunchRegistration _ret = new EppCommandCheckLaunchRegistration();
		if( null == _ret )
			return null;
		NodeList list  = root.getChildNodes();
		NamedNodeMap attrs = root.getAttributes();
		for( int i = 0; i < list.getLength(); i++ ) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if( name == null ) {
				name = node.getNodeName();
			}
			if( name == null )
				continue;

			if( name.startsWith("launch:")) {
				name = name.substring("launch:".length());
			}
			
			if( name.equals("phase") ) {
				EppLaunchPhase _pptr = EppLaunchPhase.fromXML(node);
				if( null != _pptr ) {
					_ret._phase = _pptr;
				}
				_pptr = null;
				break;
			}
		}
		
		for(int i = 0;i<attrs.getLength();i++) {
			Node attr = attrs.item(i);
			String _v = attr.getNodeValue();
			if( attr.getNodeName().equals("type") ) {
				_ret._type = attr.getNodeValue();
				break;
			}
		}
		return _ret;
	}

	public Element
	toXML( Document doc, final String tag )
	{
	  Element body = EppUtil.createElementNS(doc, "launch", (tag == null) ? "check" : tag,false,"-1.0");

		if( this._type.length() > 0 ) {
	    Attr attr = doc.createAttribute("type");
	    attr.setValue( this._type);
	    body.setAttributeNode(attr);
	  }

		if (this._phase != null && this._phase.phase().length() > 0) {
			Element elm = this._phase.toXML(doc, "launch:phase");
			body.appendChild(elm);
	  	}
	  return body;
	}

	public String
	toString()
	{
		return new String("launch:check");
	}

	private EppLaunchPhase _phase;
	private String _type;
};
