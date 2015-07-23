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

public class EppMarkMark extends EppEntity {
	public EppMarkMark()
	{
		this._marks = new Vector();
	}
	public void addMark(EppMarkData _d)
	{
		this._marks.addElement(_d);
	}
	public Vector getMark()
	{
		return _marks;
	}

	public Element toXML(Document doc, final String tag)
	{
		//Element body = doc.createElement((tag==null)?"mark":tag);
		Element body = EppUtil.createElementNS(doc, "mark", tag,false,"-1.0");
		for(int i =0; i<this._marks.size();i++) {
			EppMarkData _ptr = (EppMarkData)this._marks.elementAt(i);
			if( null != _ptr ) {
				Element elm = _ptr.toXML(doc,null);
				body.appendChild(elm);
			}
		}
		return body;
	}

	public static EppEntity fromXML( final Node root )
	{
		EppMarkMark _ret = new EppMarkMark();
	  NodeList list = root.getChildNodes();
		if( list == null ) {
			return null;
		}
	  for( int i = 0; i < list.getLength(); i++ ) {
	    Node node = list.item(i);
	    String name = node.getLocalName();
	    if( name == null ) {
	      continue;
	    }
			if( name.equals("trademark") ) {
				EppTrademarkData _tmd = (EppTrademarkData)EppTrademarkData.fromXML(node);
				if( null != _tmd ) {
					_ret._marks.addElement(_tmd);
				}
			}
		}
		return _ret;
	}

	public String toString()
	{
		return new String("mark:mark");
	}
	private Vector _marks;
};
