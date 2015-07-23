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
package com.neulevel.epp.xri;

import java.util.*;
import org.w3c.dom.*;
import com.neulevel.epp.core.EppEntity;
import com.neulevel.epp.core.EppObject;
import com.neulevel.epp.core.EppUtil;

public class EppXriResolutionPattern extends EppEntity {
	/**
	* Constructor with no argument to construct an object of <code>EppXriResolutionPattern</code>. All other
	* fields shall be init with "*".
	* @param
	* @return
	**/
	public EppXriResolutionPattern() {
		patternId = new String("");
		min  = new String ("*");
		hrs = new String("*");
		dom = new String("*");
		mon = new String("*");
		dow = new String("*");
	}
	/**
	* Contructor with argument to construct an object of <code>EppXriResolutionPattern</code>.
	* @param pid String representing resolution pattern id.
	* @return
	**/
	public EppXriResolutionPattern(String pid) {
		patternId = pid;
		min  = new String ("*");
		hrs = new String("*");
		dom = new String("*");
		mon = new String("*");
		dow = new String("*");
	}
		
	/**
	* Method to set pattern id .
	* @param pid String representing resolution pattern id.
	* @return returns void.
	**/
	public void setPatternId(String pid) {
		this.patternId = pid;
	}
	/**
	* method to set minutes for resolution pattern
	* @param _min String
	* @return returns nothing.
	**/
	public void setMin(String _min) {
		this.min = _min;
	}
	/**
	* Method to set hours.
	* @param _hrs String
	* @return
	**/
	public void setHours(String _hrs) {
		this.hrs = _hrs;
	}
	/**
	* Method to set day of month for execution or validation.
	* @param _dom String
	* @return
	**/
	public void setDayOfMonth(String _dom) {
		this.dom = _dom;
	}
	/**
	* Method to set month of execution or validation.
	* @param _mon String
	* @return
	**/
	public void setMonth(String _mon) {
		this.mon = _mon;
	}
	/**
	* Method to set Day Of Week
	* @param _dow String
	* @return
	**/
	public void setDayOfWeek(String _dow) {
		this.dow = _dow;
	}
	/**
	* Method to retrive pattern id.
	* @param
	* @return String
	**/

	public String getPatternId() {
		return this.patternId;
	}
	/**
	* Method to retrive minutes from resolution pattern object
	* @param
	* @return String
	**/
	public String getMin() {
		return this.min;
	}
	/**
	* Method to retrive hours set ealier from resolution pattern object.
	* @param
	* @return String
	**/
	public String getHours() {
		return this.hrs;
	}
	/**
	* Method to get day of the month from resolution pattern which was populated ealier.
	* @param
	* @return String
	**/
	public String getDayOfMonth() {
		return this.dom;
	}
	/**
	* Method to retrive month from resolution pattern object.
	* @param
	* @return String
	**/
	public String getMonth() {
		return this.mon;
	}
	/**
	* Method to retrive day of week from previously populated <code>EppXriResolutionPattern</code> object.
	* @param
	* @return String.
	**/
	public String getDayOfWeek() {
		return this.dow;
	}
	
	public Element toXML( Document doc,  String tag ) {
		Element body = doc.createElement(tag);
		Element elm;
		if( this.patternId.equals("") || this.patternId == null ) {
			return null;
		}
		elm = doc.createElement("recPatternId");
		elm.appendChild(doc.createTextNode(this.patternId));
		body.appendChild(elm);
	
		elm = doc.createElement("min");
		elm.appendChild(doc.createTextNode(this.min));
		body.appendChild(elm);
	
		elm = doc.createElement("hrs");
		elm.appendChild(doc.createTextNode(this.hrs));
		body.appendChild(elm);
	
		elm = doc.createElement("dom");
		elm.appendChild(doc.createTextNode(this.dom));
		body.appendChild(elm);
	
		elm = doc.createElement("mon");
		elm.appendChild(doc.createTextNode(this.mon));
		body.appendChild(elm);
	
		elm = doc.createElement("dow");
		elm.appendChild(doc.createTextNode(this.dow));
		body.appendChild(elm);
	
		return body;

	}
	public static EppXriResolutionPattern fromXML(  Node root ) {
		EppXriResolutionPattern ret_val = null;
		if( root == null )
			return null;
		NodeList list = root.getChildNodes();
		if( list == null )
			return null;
		ret_val = new EppXriResolutionPattern();
		for(int i=0;i<list.getLength(); i++ ) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if( name == null ) {
				name = node.getNodeName();
			}
			if( name == null ) {
				continue;
			}
			if( name.equals("recPatternId") ) {
				ret_val.setPatternId(EppUtil.getText(node));
			} else if( name.equals("min") ) {
				ret_val.setMin(EppUtil.getText(node));
			} else if( name.equals("hrs") ) {
				ret_val.setHours(EppUtil.getText(node));
			} else if( name.equals("dom") ) {
				ret_val.setDayOfMonth(EppUtil.getText(node));
			} else if( name.equals("mon") ) {
				ret_val.setMonth(EppUtil.getText(node));
			} else if( name.equals("dow") ) {
				ret_val.setDayOfWeek(EppUtil.getText(node));
			}
		}
		return ret_val;
	}
	public String toString() {
		return  toString("resolutionIntervalRecurrencePattern");
	}
	private String patternId,min,hrs,dom,mon,dow;
}
