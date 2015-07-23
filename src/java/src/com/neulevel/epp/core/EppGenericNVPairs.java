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
package com.neulevel.epp.core;

import java.util.*;
import org.w3c.dom.*;
import com.neulevel.epp.core.EppEntity;
import com.neulevel.epp.core.EppObject;
import com.neulevel.epp.core.EppUtil;

public class EppGenericNVPairs extends EppEntity {
/**
  * No argument constructor for creating an object of <code>EppGenericNVPairs</code>
  * @param
  * @return
  **/
  public EppGenericNVPairs() {
    nvPairs = new Vector();
  }
  public EppGenericNVPairs(EppGenericNVPairs ext) {
    nvPairs = new Vector( ext.getGenericNVPairs() );
  }
  /**
  * Constructor with 2 arguments of <code>EppGenericNVPairs</code> object.
  * @param _name Name of value added services
  *        _value Value of service
  * @return
  **/
  public EppGenericNVPairs(String _name,String _value) {
    nvPairs = new Vector();
    this.addGenericNVPair(_name,_value);
  }
  /**
  * Interface to add a combination of value added serveice.
  * @param _name String representing name of nvPairs and _value representing value of _name
  * @return returns void
  **/
  public void addGenericNVPair(String _name,String _value) {
    EppUnspecNVPairTuple v = new EppUnspecNVPairTuple();
    v.setName(_name);
    v.setValue(_value);
    nvPairs.addElement(v);
  }
  /**
  * Function to retrive all value added services .
  * @param
  * @return Vector of <code>nvPairs_t</code> class that has to public members <code>name</code> and <code>val
  **/
  public Vector getGenericNVPairs() { return this.nvPairs; }
  
  public Element toXML( Document doc, String tag ) {
    Element body;
    Element elm;
    if( doc == null )
      return null;
    body = doc.createElement(tag);
    
    for(int i = 0 ; i< this.nvPairs.size(); i++ ) {
      EppUnspecNVPairTuple t = (EppUnspecNVPairTuple)this.nvPairs.elementAt(i);
      elm = doc.createElement("NVTuple");
      elm.appendChild(doc.createTextNode(t.getValue()));
      elm.setAttribute("name", t.getName() );
      body.appendChild(elm);
    }
    return body;
  }
  public static EppGenericNVPairs fromXML( Node root ) {
    EppGenericNVPairs ret_val = new EppGenericNVPairs();
    NodeList list = root.getChildNodes();
    for( int i = 0; i < list.getLength(); i++ ) {
      Node node = list.item(i);
      if( node == null )
        return null;
      String name = node.getLocalName();
      if( name == null ) {
        name = node.getNodeName();
      }
      if( name == null ) {
        return null;
      }
      if( name.equals("NVTuple") || name.equals("neulevel:NVTuple") ) {
        String attri_name = ((Element)node).getAttribute("name");
        ret_val.addGenericNVPair(attri_name,EppUtil.getText(node));
      }
    }
    return ret_val;
  }
  public String toString() {
    return  toString("genericNVPairs");
  }
  private Vector nvPairs;
}


