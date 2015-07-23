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

/**
 * User: vneema
 * Date: 04/09/13
 * Time: 12:57 PM
 * To change this template use File | Settings | File Templates.
 */
import org.w3c.dom.*;
import com.neulevel.epp.core.*;
import com.neulevel.epp.ext.*;

public class EppCommandUpdateLaunchRegistration extends EppExtension {
	public EppCommandUpdateLaunchRegistration()
	{
		this._phase = new EppLaunchPhase();
		this._applicationId = "";
	}
	public void phase(final EppLaunchPhase _p)
	{
		this._phase = _p;
	}
	public EppLaunchPhase phase()
	{
		return this._phase;
	}
	public void applicationId(final String _input)
	{
		this._applicationId = _input;
	}
	public String applicationId()
	{
		return this._applicationId;
	}
	public Element toXML( Document doc, String tag )
	{
		if( tag == null ) {
			tag = "update";
		}
		Element body = EppUtil.createElementNS(doc, "launch", tag,false,"-1.0");
		if( this._phase != null && this._phase.phase().length() > 0 ) {
			Element elm = this._phase.toXML(doc, "launch:phase");
			body.appendChild(elm);
		}
		if( this._applicationId != null && this._applicationId.length() > 0 ) {
			Element elm = doc.createElement("launch:applicationID");
			elm.appendChild(doc.createTextNode(this._applicationId));
			body.appendChild(elm);
		}
		return body;
	}

	public static EppEntity fromXML( final Node root )
	{
		EppCommandUpdateLaunchRegistration cmd  = new EppCommandUpdateLaunchRegistration();
		NodeList list  = root.getChildNodes();
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

			} else if( name.equals("applicationID") ) {
				cmd._applicationId = EppUtil.getText(node);
			}
		}
		return cmd;
	}
	public String toString()
	{
		return toString("update");
	}
	private EppLaunchPhase _phase;
	private String _applicationId;
};


