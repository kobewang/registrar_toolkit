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

public abstract class EppMarkData extends EppEntity {

	public EppMarkData()
	{
		this.Id = new String();
		this._markName = new String();
		this._holder = new Vector();
		this._contact = new Vector();
		this._label = new Vector();
		this.goodsAndServices = new String();
	}
	public void id(final String _id)
	{
		this.Id = _id;
	}
	public String id()
	{
		return this.Id;
	}
	public void markName(final String _name)
	{
		this._markName = _name;
	}
	public String markName()
	{
		return this._markName;
	}
	public void addHolder(final EppMarkHolder _h)
	{
		this._holder.addElement(_h);
	}
	public Vector holder()
	{
		return this._holder;
	}
	public void addContact(final EppMarkHolder _c)
	{
		this._contact.addElement(_c);
	}
	public Vector contact()
	{
		return this._contact;
	}
	public void addLabel(final String _l)
	{
		this._label.addElement(_l);
	}
	public Vector labels()
	{
		return this._label;
	}
	public void goodsServices(final String _g)
	{
		this.goodsAndServices = _g;
	}
	public String goodsServices()
	{
		return this.goodsAndServices;
	}
	public Element toXML(Document doc, final String tag)
	{
		Element elm;
		Element body = doc.createElement(tag);
		return body;
	}

	public void fromXml( final Node root )
	{
		NodeList list = root.getChildNodes();
		for( int i = 0; i < list.getLength(); i++ ) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if( name == null ) {
				continue;
			}
			if( name.equals("id") ) {
				this.Id = EppUtil.getText(node);
			} else if( name.equals("markName") ) {
				this._markName = EppUtil.getText(node);
			} else if( name.equals("holder") ) {
				EppMarkHolder _h = (EppMarkHolder)EppMarkHolder.fromXML(node);
				if( null != _h ) {
					this._holder.addElement(_h);
				}
			} else if( name.equals("contact") ) {
				EppMarkHolder _c = (EppMarkHolder)EppMarkHolder.fromXML(node);
				if( null != _c ) {
					this._contact.addElement(_c);
				}
			} else if( name.equals("goodsAndServices") ) {
				this.goodsAndServices = EppUtil.getText(node);
			} else if( name.equals("label") ) {
				this._label.addElement(EppUtil.getText(node));
			}
		}
	}
	protected String Id; 
	protected String _markName; 
	protected Vector _holder; /* ? */
	protected Vector _contact; /* * */
	protected Vector _label; /* * */
	protected String goodsAndServices; 
};

