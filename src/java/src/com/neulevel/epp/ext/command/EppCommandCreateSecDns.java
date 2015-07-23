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

/**
 * This <code>EppCommandCreateSecDns</code> class implements the EPP
 * create command for EPP DNS Security Extension
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.4 $ $Date: 2013/09/03 14:21:25 $
 */
public class EppCommandCreateSecDns extends EppExtension
{
	private boolean keyDataPresent;
	private int maxSigLife;
	private Vector	dsDataList;
	private Vector  keyDataList;

	/**
	 * Creates an <code>EppCommandCreateSecDns</code> object
	 */
	public EppCommandCreateSecDns()
	{
		this.dsDataList = new Vector();
		this.keyDataList = new Vector();
		this.keyDataPresent = false;
		this.maxSigLife = -1;
	}
	public EppCommandCreateSecDns(String secDnsVer)
	{
		this.dsDataList = new Vector();
		this.keyDataList = new Vector();
		this.keyDataPresent = false;
		this.maxSigLife = -1;
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
	/**
	 * Adds Key Data to the list to be attached to a domain name
	 */
	public void add( EppSecDnsKeyData keyData )
	{
		this.keyDataPresent = true;
		this.keyDataList.addElement(keyData);
	}


	/**
	 * Gets the list of DS data to be attached to a domain name
	 */
	public Vector getDsData()
	{
		return this.dsDataList;
	}
	/**
	 * Gets the list of Key data to be attached to a domain name
	 */
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
	 * Converts an XML element into an <code>EppCommandCreateSecDns</code> object.
	 * The caller of this method must make sure that the root node is of
	 * EPP SECDNS createType
	 *
	 * @param root root node for an <code>EppCommandCreateSecDns</code> object in XML format
	 *
	 * @return an <code>EppCommandCreateSecDns</code> object, or null if the node is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		EppCommandCreateSecDns cmd  = new EppCommandCreateSecDns();
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
			} else if( name.equals("maxSigLife") ) { 
				String value = EppUtil.getText(node);
				if( value != null &&
						value.trim().length() > 0 ) {
					 cmd.maxSigLife = Integer.parseInt( value );
				}
			} else if( name.equals("keyData") ) {
				// FIXME(zhang) not supported
				cmd.keyDataPresent = true;
				EppSecDnsKeyData kd = (EppSecDnsKeyData)EppSecDnsKeyData.fromXML(node);
				if( kd != null ) {
					cmd.add(kd);
				}
			}
		}

		return cmd;
	}

	/**
	 * Converts the <code>EppCommandCreateSecDns</code> object into an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the <code>EppCommandCreateSecDns</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		if( tag == null )
		{
			tag = "create";
		}
		Element body = EppUtil.createElementNS(doc, "secDNS", tag,true,"-"+this.secDnsMajor+"."+this.secDnsMinor);

		if( this.maxSigLife != -1 ) {
			Element elm = doc.createElement("maxSigLife");
			elm.appendChild(doc.createTextNode(Integer.toString(this.maxSigLife) ));
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
		if( this.keyDataPresent == true ) {
			for( int i = 0; i < this.keyDataList.size(); i++ ) {
				Object obj = keyDataList.elementAt(i);
				if( (obj != null) && (obj instanceof EppSecDnsKeyData) ) {
					EppSecDnsKeyData kd = (EppSecDnsKeyData) obj;
					Element elm = kd.toXML(doc, "keyData");
					body.appendChild(elm);
				}
			}
		}
		return body;
	}

	public String toString()
	{
		return toString("create");
	}
}
