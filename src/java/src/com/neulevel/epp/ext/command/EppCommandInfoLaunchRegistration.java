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

public class EppCommandInfoLaunchRegistration extends EppExtension {
	public EppCommandInfoLaunchRegistration()
	{
		this._phase = new EppLaunchPhase();
		this._appId = new String();
		this._includeMark = false;
	}

	public void phase(final EppLaunchPhase _p)
	{
		this._phase = _p;
	}
	public EppLaunchPhase phase()
	{
		return this._phase;
	}
	public boolean includeMark()
	{
		return this._includeMark;
	}
	public void includeMark(boolean _m)
	{
		this._includeMark = _m;
	}

	public void applicationId(final String _id)
	{
		this._appId = _id;
	}

	public String applicationId()
	{
		return this._appId;
	}

	public Element toXML( Document doc, final String tag )
	{
	  Element body = EppUtil.createElementNS(doc, "launch", (tag == null) ? "info" : tag,false,"-1.0");
	  Attr attr = doc.createAttribute("includeMark");
		attr.setValue( ( this._includeMark )?"true":"false" );
		body.setAttributeNode(attr);

	  if( this._phase != null ) {
			if( this._phase.phase().length() > 0 ) {
			Element elm = this._phase.toXML(doc, "launch:phase");
		    	body.appendChild(elm);
			}
		}
		if( this._appId != null ) {
			if( this._appId.length() > 0 ) {
		    Element elm = doc.createElement("launch:applicationID");
		    elm.appendChild(doc.createTextNode(this._appId));
		    body.appendChild(elm);
			}
	  }
	  return body;
	}

	public static EppCommandInfoLaunchRegistration fromXML( final Node root )
	{
		EppCommandInfoLaunchRegistration cmd  = new EppCommandInfoLaunchRegistration();
		if( cmd == null ) {
			return null;
		}
		NodeList list  = root.getChildNodes();
		NamedNodeMap attrs = root.getAttributes();
		for(int i = 0; i < list.getLength(); i++ ) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if( name == null )
				continue;
			if( name.equals("phase") ) {
                                        EppLaunchPhase _pptr = EppLaunchPhase.fromXML(node);
                                        if( null != _pptr ) {
                                                cmd._phase = _pptr;
                                        }
                                        _pptr = null;

			} else if ( name.equals("applicationID") ) {
				cmd._appId = EppUtil.getText(node);
			}
		}
		for(int i = 0;i<attrs.getLength();i++) {
			Node attr = attrs.item(i);
			if( attr.getNodeName().equals("includeMark") ) {
				String _v = attr.getNodeValue();
				if( _v.length() > 0 ) {
					if( _v.equals("true") ) {
						cmd.includeMark(true);
					}
				}
				break;
			}
		}
		return cmd;
	}

	public String toString()
	{
		return toString("info");
	}

	private EppLaunchPhase _phase;
	private String _appId;
	private boolean _includeMark;
};

