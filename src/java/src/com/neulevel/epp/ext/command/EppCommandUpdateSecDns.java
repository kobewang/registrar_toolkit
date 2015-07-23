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
 * This <code>EppCommandUpdateSecDns</code> class implements the EPP
 * update command for EPP DNS Security Extension
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.5 $ $Date: 2013/09/03 14:21:42 $
 */
public class EppCommandUpdateSecDns extends EppExtension
{
	private Vector	addDsDataList;
	private Vector	addKeyDataList;

	private Vector	chgDsDataList;

	private Vector	remKeyDataList;
	private Vector	remDsDataList;

	private boolean	urgent;

	private int maxSigLife;
	private boolean removeAllDS;
	private boolean keyDataPresent;

	private static  int OP_ADD = 1;
	private static  int OP_CHG = 2;
	private static  int OP_REM = 3;

	/**
	 * Updates an <code>EppCommandUpdateSecDns</code> object
	 */
	public EppCommandUpdateSecDns()
	{
		this.addDsDataList = new Vector();
		this.addKeyDataList = new Vector();

		this.chgDsDataList = new Vector();

		this.remKeyDataList = new Vector();
		this.remDsDataList = new Vector();

		this.urgent = false;

		this.maxSigLife = -1;
		this.removeAllDS = false;
		this.keyDataPresent = false;
	}
	public EppCommandUpdateSecDns(String secDnsVer)
	{
		this.addDsDataList = new Vector();
		this.addKeyDataList = new Vector();

		this.chgDsDataList = new Vector();

		this.remKeyDataList = new Vector();
		this.remDsDataList = new Vector();

		this.urgent = false;

		this.maxSigLife = -1;
		this.removeAllDS = false;
		this.keyDataPresent = false;
		if( null != secDnsVer ) {
			String maj_min [] = EppUtil.getDnsSecMajorMinor( secDnsVer );
			if( null != maj_min ) {
				this.secDnsMajor = maj_min[0];
				this.secDnsMinor = maj_min[1];
			}
		}
	}


	/**
	 * Gets the urgent update boolean flag
	 */
	public boolean getUrgentFlag()
	{
		return this.urgent;
	}

	/**
	 * Sets the urgent update boolean flag
	 */
	public void setUrgentFlag( boolean urgent )
	{
		this.urgent = urgent;
	}

	public boolean isRemovelAllDS() {
		return this.removeAllDS;
	}

	public void isRemoveAllDS( boolean flag ) {
		this.removeAllDS = flag;
	}

	public int getMaxSigLife() {
		return this.maxSigLife;
	}

	public void setMaxSigLife( int secs ) {
		this.maxSigLife = secs;
	}

	public boolean isKeyDataPresent() {
		return this.keyDataPresent;
	}

	/**
	 * Adds DS Data to the list to be attached to a domain name
	 */
	public void add( EppSecDnsDsData dsData )
	{
		this.addDsDataList.addElement(dsData);
	}
  public void add( EppSecDnsKeyData keyData )
  {
		this.keyDataPresent = true;
		this.addKeyDataList.addElement(keyData);
  }

	/**
	 * Gets the list of DS data to be attached to a domain name
	 */
	public Vector getDsDataAdded()
	{
		return this.addDsDataList;
	}
	public Vector getKeyDataAdded()
  {
		return this.addKeyDataList;
  };

	/**
	 * Adds key tag to the list of DS data to be detached from a domain name
	 */
	public void remove(  EppSecDnsDsData dsData)
	{
		this.remDsDataList.addElement(dsData);
	}

	public void remove( EppSecDnsKeyData keyData)
	{
		this.keyDataPresent = true;
		this.remKeyDataList.addElement(keyData);
	}

	/**
	 * Gets the list of key tags of DS data to be detached from a domain name
	 */
	public Vector getKeyDataRemoved()
	{
		return this.remKeyDataList;
	}
	public Vector getDsDataRemoved()
	{
		return this.remDsDataList;
	}

	/**
	 * Converts an XML element into an <code>EppCommandUpdateSecDns</code> object.
	 * The caller of this method must make sure that the root node is of
	 * EPP SECDNS updateType
	 *
	 * @param root root node for an <code>EppCommandUpdateSecDns</code> object in XML format
	 *
	 * @return an <code>EppCommandUpdateSecDns</code> object, or null if the node is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		EppCommandUpdateSecDns cmd  = new EppCommandUpdateSecDns();
		NodeList list  = root.getChildNodes();
		for( int i = 0; i < list.getLength(); i++ )
		{
			Node node = list.item(i);
			String name = node.getLocalName();
			if( name == null )
			{
				continue;
			}
			if( name.equals("add") )
			{
				cmd.fromXML(node, EppCommandUpdateSecDns.OP_ADD);
			}
			else if( name.equals("chg") )
			{
				cmd.fromXML(node, EppCommandUpdateSecDns.OP_CHG);
			}
			else if( name.equals("rem") )
			{
				cmd.fromXML(node, EppCommandUpdateSecDns.OP_REM);
			}
		}

		String flag = ((Element) root).getAttribute("urgent");
		if( (flag != null) && (flag.equals("true") || flag.equals("1")) )
		{
			cmd.setUrgentFlag(true);
		}
		return cmd;
	}

	/**
	 * Converts an XML element into an <code>EppSecDnsDsData</code> object list,
	 * <code>key tag</code> list, The caller of this method must make sure that
	 * the root node contains a list of EPP SECDNS dsDataType objects
	 *
	 * @param root root node for a <code>add</code>, <code>chg</code> or <code>rem</code> tag
	 *	  op flag indicating one of the operations: OP_ADD, OP_CHG, OP_REM.
	 *
	 * @return none
	 */
	private void fromXML( Node root, int op )
	{
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
					if( op == EppCommandUpdateSecDns.OP_ADD )
					{
						this.add(ds);
					}
					else if( op == EppCommandUpdateSecDns.OP_REM )
					{
						this.remove(ds);
					}
				}
			}
/*
			else if( name.equals("keyTag") )
			{
				String value = EppUtil.getText(node);
				if( (value != null) && (value.trim().length() > 0) )
				{
					if( op == EppCommandUpdateSecDns.OP_REM )
					{
						this.remove(Integer.parseInt(value));
					}
				}
			}
*/
			else if( name.equals("all") && EppCommandUpdateSecDns.OP_REM == op )
			{
				this.removeAllDS = EppUtil.getTextAsBool(node);
			}
			else if( name.equals("maxSigLife") && EppCommandUpdateSecDns.OP_CHG == op )
			{
				String value = EppUtil.getText(node);
				if( value != null && value.trim().length() > 0 ) {
					this.maxSigLife = Integer.parseInt(value);
				}
			}
			else if( name.equals("keyData") )
			{
				EppSecDnsKeyData kd = ( EppSecDnsKeyData)EppSecDnsKeyData.fromXML(node);
				if( kd != null ) {
					if( op == EppCommandUpdateSecDns.OP_ADD ) {
						this.add(kd);
					} else if( op == EppCommandUpdateSecDns.OP_REM) {
						this.remove(kd);
					}
				}
      	this.keyDataPresent = true;
				// FIXME(zhang) not supported
			}
		}
	}

	/**
	 * Converts the <code>EppCommandUpdateSecDns</code> object into an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the <code>EppCommandUpdateSecDns</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		if( tag == null )
		{
			tag = "update";
		}
		Element body = EppUtil.createElementNS(doc, "secDNS", tag,true,"-"+this.secDnsMajor+"."+this.secDnsMinor);

		Element elm  = null;
		if( this.urgent == true )
		{
			body.setAttribute("urgent", "true");
		}

		elm = this.toXML(doc, "rem", this.remKeyDataList);
		if( elm != null ) {
      body.appendChild(elm);
		}

		elm = this.toXML(doc, "rem", this.remDsDataList);
		if( elm != null ) {
      body.appendChild(elm);
		}

		if( ( this.remKeyDataList != null) && (this.remKeyDataList.size() <= 0) &&
				( this.remDsDataList != null) && (this.remDsDataList.size() <= 0) ) {
			if( this.removeAllDS == true ) {
				Element rem = null;
				elm = doc.createElement("rem");
				rem = doc.createElement("all");
				rem.appendChild(doc.createTextNode("true"));
				elm.appendChild(rem);
				body.appendChild(elm);
    	}
  	}


		elm = this.toXML(doc, "add", this.addDsDataList);
		if( elm != null ) {
			body.appendChild(elm);
		}
		elm = this.toXML(doc, "add", this.addKeyDataList);
		if( elm != null ) {
			body.appendChild(elm);
		}

		if( this.maxSigLife != -1 ) {
     	Element chg = null;
      elm = doc.createElement("chg");
      chg = doc.createElement("maxSigLife");
      chg.appendChild(doc.createTextNode(Integer.toString(this.maxSigLife) ));
			elm.appendChild(chg);
      body.appendChild(elm);
    }

		if( elm != null )
		{
			body.appendChild(elm);
		}
		return body;
	}

	/**
	 * Converts a list of object into an XML format
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the object, "add", "chg", or "rem"
	 * @param list the list of objects, either <code>EppSecDnsDsData</code> or <code>Integer</code>
	 *
	 * @return an <code>Element</code> object
	 */
	private Element toXML( Document doc, String tag, Vector list )
	{
		if( (list == null) || (list.size() == 0) )
		{
			return null;
		}
		Element elm;
		Element body = doc.createElement(tag);
		for( int i = 0; i < list.size(); i++ )
		{
			Object obj = list.elementAt(i);
			if( obj == null )
			{
				continue;
			}
			if( obj instanceof EppSecDnsDsData )
			{
				EppSecDnsDsData ds = (EppSecDnsDsData) obj;
				elm = ds.toXML(doc, "dsData");
				body.appendChild(elm);
			} else if( obj instanceof EppSecDnsKeyData ) {
				EppSecDnsKeyData kd = (EppSecDnsKeyData) obj;
				elm = kd.toXML(doc, "keyData");
				body.appendChild(elm);
			}

		}
		return body;
	}

	public String toString()
	{
		return toString("update");
	}
}
