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
package com.neulevel.epp.ext.response;

import java.util.*;
import org.w3c.dom.*;
import com.neulevel.epp.core.*;
import com.neulevel.epp.ext.*;

public class EppResponseDataInfoLaunchRegistration extends EppExtension {

	public EppResponseDataInfoLaunchRegistration()
	{
		this._phase = null;
		this._appId = null;
		this._status = null;
		this._mark = null;
		this.isStatusSet = false;
	}
	public void phase(final String _p)
	{
		this._phase = _p;
	}
	public String phase()
	{
		return this._phase;
	}
	public void applicationId(final String _apid)
	{
		this._appId = _apid;
	}
	public String applicationId()
	{
		return this._appId;
	}
	public void status(final String _s)
	{
		if( _s.length() > 0 )
			this.isStatusSet = true;
		this._status.setStatus(_s);
	}
	public String status()
	{
		return this._status.getStatus();
	}
	public void mark(EppMarkMark _m)
	{
		this._mark = _m;
	}
	public EppMarkMark mark()
	{
		return this._mark;
	}
	public static EppResponseDataInfoLaunchRegistration fromXML( final Node root )
	{
		EppResponseDataInfoLaunchRegistration ret = new EppResponseDataInfoLaunchRegistration();
	  if( ret == null ) {
			return null;
		}
		NodeList list  = root.getChildNodes();
		for( int i = 0; i < list.getLength(); i++ ) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if( name == null ) {
				continue;
			}
			if( name.equals("phase") ) {
				ret._phase = EppUtil.getText(node);
			} else if( name.equals("applicationID") ) {
				ret._appId= EppUtil.getText(node);
			} else if( name.equals("status") ) {
				ret.isStatusSet = true;
				ret._status = ((EppStatus)EppStatus.fromXML(node));
			} else if( name.equals("mark") ) {
				ret._mark = (EppMarkMark)EppMarkMark.fromXML(node);
			}
		}
		return ret;
	}

	public Element toXML( Document doc, final String tag )
	{
		Element elm;
		Element body;
		body = EppUtil.createElementNS(doc, "launch", (tag == null) ? "infData" : tag,false,"-1.0");
		if( null != this._phase && this._phase.length() > 0 ) {
			elm = doc.createElement("launch:phase");
			elm.appendChild(doc.createTextNode(this._phase));
			body.appendChild(elm);
		}
		if( null != this._appId && this._appId.length() > 0 ) {
			elm = doc.createElement("launch:applicationID");
			elm.appendChild(doc.createTextNode(this._appId));
			body.appendChild(elm);
		}
		if( this.isStatusSet ) {
			body.appendChild( this._status.toXML(doc,"launch:status") );
		}
		if( this._mark != null ) {
			body.appendChild( this._mark.toXML( doc,"mark:mark"));
		}
		return body;
	}

	public String toString()
	{
		return super.toString("EppResponseDataInfoLaunchRegistration");
	}
	private String _phase;
	private String _appId;
	private EppStatus _status;
	private EppMarkMark _mark;
	private boolean isStatusSet;
};

