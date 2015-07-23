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

public class EppLaunchPhase extends EppEntity {

	public EppLaunchPhase()
	{
		this._phase = new String();
		this._sub_phase = new String();
	}

	public String
	phase()
	{
		return this._phase;
	}
	public void
	phase(final String _i)
	{
		this._phase = _i;
	}
	public String
	subPhase()
	{
		return this._sub_phase;
	}
	public void
	subPhase(final String _i)
	{
		this._sub_phase = _i;
	}

	public Element
	toXML(Document doc, final String tag)
	{
		Element elm;
		Element body = doc.createElement(tag);
		if( this._sub_phase.length() > 0 ) {
			Attr attr = doc.createAttribute("name");
			attr.setValue( this._sub_phase );
			body.setAttributeNode(attr);
		}
		if( this._phase.length()>0) {
			body.appendChild(doc.createTextNode(this._phase));
		}
		return body;
	}

	public static EppLaunchPhase
	fromXML( final Node root )
	{
		NamedNodeMap attrs = root.getAttributes();
		EppLaunchPhase _ret = new EppLaunchPhase();
		if( null == _ret )
			return null;
		{
			Node node = root;
			String name = node.getLocalName();
			if( name == null ) {
				name = node.getNodeName();
			}
			if( name != null ) {
				if( name.startsWith("launch:") ) {
					name = name.substring(7, name.length() - 7);
				}
				if( name.equals("phase") ) {
					_ret._phase = EppUtil.getText(node);
				}
			}
		}
		for(int i = 0;i<attrs.getLength();i++) {
			Node attr = attrs.item(i);
			String _v = attr.getNodeValue();
			if( attr.getNodeName().equals("name") ) {
				_ret._sub_phase = attr.getNodeValue();
				break;
			}
		}
		return _ret;
	}
	public String
	toString()
	{
		return new String("launch:phase");
	}

	private String _phase;
	private String _sub_phase;
};

