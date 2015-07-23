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

/**
 * This <code>EppSecDnsKeyData</code> class implements DS data specified in the
 * EPP DNS Security extension, defined by IETF Draft:
*  <A HREF="http://www.ietf.org/internet-drafts/draft-hollenbeck-epp-secdns-04.txt">
 * draft-hollenbeck-epp-secdns-04.txt</A>, with the following modifications:
 *
 * <UL>
 * <LI>The XML schema file has been modified to handle DS data only
 * <LI>The XML schema file has been modified to allow additions of new DS data
 * </UL>
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.1 $ $Date: 2012/06/26 12:09:39 $
 */
public class EppSecDnsKeyData extends EppEntity
{
	private int flags;
	private int protocol;
	private int alg;
	private String public_key;

	public EppSecDnsKeyData() {
		this.flags = -1;
		this.protocol = -1;
		this.alg = -1;
		this.public_key = "";
	}

	public int getFlags() {
		return this.flags;
	}

	public void setFlags( int _flags ) {
		this.flags = _flags;
	}

	public int getProtocol() {
		return this.protocol;
	}

	public void setProtocol( int _proto ) {
		this.protocol = _proto;
	}


	/**
	 * Gets the algorithm value
	 */
	public int getAlgorithm() {
		return this.alg;
	}

	/**
	 * Sets the algorithm value
	 */
	public void setAlgorithm( int alg ) {
		this.alg = alg ;
	}

	/**
	 * Gets the Public Key value
	 */
	public String getPublicKey() {
		return this.public_key;
	}

	/**
	 * Sets the Public Key value;
	 */
	public boolean setPublicKey( String key) {
		this.public_key = key;
		return true;
	}

	/**
	 * Converts an XML element into an <code>EppSecDnsKeyData</code> object.
	 * The caller of this method must make sure that the root node is of
	 * EPP SECDNS dsKeyType
	 *
	 * @param root root node for an <code>EppSecDnsKeyData</code> object in XML format
	 *
	 * @return an <code>EppSecDnsKeyData</code> object, or null if the node is invalid
	 */
	public static EppEntity fromXML( Node root ) {
		String          value = null;
		EppSecDnsKeyData data  = new EppSecDnsKeyData();
		NodeList        list  = root.getChildNodes();

		for( int i = 0; i < list.getLength(); i++ ) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if( name == null ) {
				continue;
			}
			if( name.equals("flags") ) {
				value = EppUtil.getText(node);
				if( (value != null) && (value.trim().length() > 0) ) {
					data.setFlags( Integer.parseInt(value) );
				}
			} else if( name.equals("protocol") ) {
				value = EppUtil.getText(node);
				if( (value != null) && (value.trim().length() > 0) ) {
					data.setProtocol( Integer.parseInt(value) );
				}
			} else if( name.equals("alg") ) {
				value = EppUtil.getText(node);
				if( (value != null) && (value.trim().length() > 0) ) {
					data.setAlgorithm(Integer.parseInt(value));
				}
			} else if( name.equals("pubKey") ) {
				value = EppUtil.getText(node);
				if( (value != null) && (value.trim().length() > 0) ) {
					data.setPublicKey(value);
				}
			}
		}
		return data;
	}

	/**
	 * Converts the <code>EppSecDnsKeyData</code> object into an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the <code>EppSecDnsKeyData</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag ) {
		Element elm;
		Element body = doc.createElement(tag);

		elm = doc.createElement("flags");
		elm.appendChild(doc.createTextNode(Integer.toString(this.flags)));
		body.appendChild(elm);
  
		elm = doc.createElement("protocol");
		elm.appendChild(doc.createTextNode(Integer.toString(this.protocol)));
		body.appendChild(elm);
  
		elm = doc.createElement("alg");
		elm.appendChild(doc.createTextNode(Integer.toString(this.alg)));
		body.appendChild(elm);
  
		if( this.public_key!= null ) {
			elm = doc.createElement("pubKey");
			elm.appendChild(doc.createTextNode(this.public_key));
			body.appendChild(elm);
		}
		return body;
	}

	public String toString() {
		return toString("keyData");
	}
}
