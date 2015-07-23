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

/**
 * This <code>EppResponseDataInfoSecDns</code> class implements the EPP
 * info response for EPP DNS Security Extension
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.4 $ $Date: 2012/07/12 10:58:11 $
 */
public class EppResponseDataInfoSecDns extends EppExtension
{
	private boolean keyDataPresent;
	private  int maxSigLife;
	private Vector keyDataList;
	private Vector	dsDataList;

	/**
	 * Creates an <code>EppResponseDataInfoSecDns</code> object
	 */
	public EppResponseDataInfoSecDns()
	{
		keyDataList = new Vector();
		dsDataList = new Vector();
		maxSigLife = -1;
		keyDataPresent = false;
	}
	public EppResponseDataInfoSecDns(String secDnsVer)
	{
		keyDataList = new Vector();
		dsDataList = new Vector();
		maxSigLife = -1;
		keyDataPresent = false;
		if( null != secDnsVer ) {
			String maj_min [] = EppUtil.getDnsSecMajorMinor( secDnsVer );
			if( null != maj_min ) {
				this.secDnsMajor = maj_min[0];
				this.secDnsMinor = maj_min[1];
			}
		}
	}


	/**
	 * Adds DS Data to the list to be attached to a domain name
	 */
	public void add( EppSecDnsDsData dsData )
	{
		this.dsDataList.addElement(dsData);
	}
	public void add( EppSecDnsKeyData kData )
	{
		this.keyDataPresent = true;
		this.keyDataList.addElement(kData);
	}

	/**
	 * Gets the list of DS data to be attached to a domain name
	 */
	public Vector getDsData()
	{
		return this.dsDataList;
	}

	public Vector getKeyData() {
		return this.keyDataList;
	}

	public int getMaxSigLife() {
		return this.maxSigLife;
	}
	public void setMaxSigLife( int life ) {
		this.maxSigLife = life;
	}

	public boolean isKeyDataPresent() {
		return this.keyDataPresent;
	}

	/**
	 * Converts an XML element into an <code>EppResponseDataInfoSecDns</code> object.
	 * The caller of this method must make sure that the root node is of
	 * EPP SECDNS infDataType
	 *
	 * @param root root node for an <code>EppResponseDataInfoSecDns</code> object in XML format
	 *
	 * @return an <code>EppResponseDataInfoSecDns</code> object, or null if the node is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		EppResponseDataInfoSecDns cmd  = new EppResponseDataInfoSecDns();
		NodeList list  = root.getChildNodes();
		for( int i = 0; i < list.getLength(); i++ )
		{
			Node node = list.item(i);
			String name = node.getLocalName();
			if( name == null )
			{
				continue;
			}
			if( name.equals("dsData") )
			{
				EppSecDnsDsData ds = (EppSecDnsDsData) EppSecDnsDsData.fromXML(node);
				if( ds != null )
				{
					cmd.add(ds);
				}
			}
			else if( name.equals("maxSigLife") ) {
				String value = EppUtil.getText(node);
				if( null != value && value.trim().length() > 0 ) {
					cmd.maxSigLife = Integer.parseInt( value );
				}
			}
			else if( name.equals("keyData") )
			{
				// FIXME(zhang) not supported
				EppSecDnsKeyData kd = (EppSecDnsKeyData)EppSecDnsKeyData.fromXML(node);
				if( kd != null ) {
					cmd.add( kd );
				}
			}
		}

		return cmd;
	}

	/**
	 * Converts the <code>EppResponseDataInfoSecDns</code> object into an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the <code>EppResponseDataInfoSecDns</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		if( tag == null ) {
			tag = "infData";
		}

		Element body = null;
		if( this.secDnsMajor.equals("1") && this.secDnsMinor.equals("0") ) {
			body = EppUtil.createElementNS(doc, "secDNS", tag,true,"-1.0");
		} else if ( this.secDnsMajor.equals("1") && this.secDnsMinor.equals("1") ) {
			body = EppUtil.createElementNS(doc, "secDNS", tag,true,"-1.1");
		} else {
			body = EppUtil.createElementNS(doc, "secDNS", tag);
		}

		if( this.maxSigLife != -1 ) {
			Element elm = doc.createElement("maxSigLife");
			elm.appendChild(doc.createTextNode(Integer.toString(this.maxSigLife)));
			body.appendChild(elm);
		}
		for( int i = 0; i < dsDataList.size(); i++ )
		{
			Object obj = dsDataList.elementAt(i);
			if( (obj != null) && (obj instanceof EppSecDnsDsData) )
			{
				EppSecDnsDsData ds = (EppSecDnsDsData) obj;
				Element elm = ds.toXML(doc, "dsData");
				body.appendChild(elm);
			}
		}
		if( this.keyDataList != null && keyDataPresent == true ) {
			for( int i = 0; i < this.keyDataList.size(); i++ ) {
				EppSecDnsKeyData kd = (EppSecDnsKeyData)keyDataList.elementAt(i);
				Element elm = kd.toXML(doc, "keyData");
				body.appendChild(elm);
			}
		}
		return body;
	}

	public String toString()
	{
		return toString("infData");
	}
}
