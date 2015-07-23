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

public class EppResponseDataCreateLaunchRegistration extends EppExtension {

EppResponseDataCreateLaunchRegistration() {
                this._phase = null;
                this._appId = null;

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
        public static EppResponseDataCreateLaunchRegistration fromXML( final Node root )
        {
                EppResponseDataCreateLaunchRegistration ret = new EppResponseDataCreateLaunchRegistration();
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
			}
                }
                return ret;
        }
        public Element toXML( Document doc, final String tag )
        {
                Element elm;
                Element body;
                body = EppUtil.createElementNS(doc, "launch", (tag == null) ? "creData" : tag,false,"-1.0");
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
                return body;
        }

        public String toString()
        {
                return super.toString("EppResponseDataCreateLaunchRegistration");
        }
        private String _phase;
        private String _appId;
}
