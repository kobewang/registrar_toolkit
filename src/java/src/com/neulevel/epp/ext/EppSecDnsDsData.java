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
 * This <code>EppSecDnsDsData</code> class implements DS data specified in the
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
 * @version $Revision: 1.6 $ $Date: 2012/06/26 12:09:39 $
 */
public class EppSecDnsDsData extends EppEntity
{
	private int		keyTag;
	private int		alg;
	private int		digestType;
	private String		digest;
	private boolean keyDataPresent;
	private EppSecDnsKeyData secKeyData;

/*
	private Calendar	sDate;
	private Calendar	eDate;
	private String		vInterval;
*/

	private static String	hex[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

	/**
	 * Creates an <code>EppSecDnsDsData</code> object
	 */
	public EppSecDnsDsData()
	{
		this.keyTag       = 0;
		this.alg          = 0;
		this.digestType   = 0;
		this.digest       = null;
    this.secKeyData		= null;
    this.keyDataPresent = false;

/*
		this.sDate        = null;
		this.eDate        = null;
		this.vInterval    = null;
*/
	}

	/**
	 * Gets the key tag value
	 */
	public int getKeyTag()
	{
		return this.keyTag;
	}

	/**
	 * Sets the key tag value
	 */
	public void setKeyTag( int keyTag )
	{
		this.keyTag = keyTag ;
	}

	/**
	 * Gets the algorithm value
	 */
	public int getAlgorithm()
	{
		return this.alg;
	}

	/**
	 * Sets the algorithm value
	 */
	public void setAlgorithm( int alg )
	{
		this.alg = alg ;
	}

	/**
	 * Gets the digest type
	 */
	public int getDigestType()
	{
		return this.digestType;
	}

	/**
	 * Sets the digest type
	 */
	public void setDigestType( int digestType )
	{
		this.digestType = digestType ;
	}

	/**
	 * Gets the digest value
	 */
	public String getDigest()
	{
		return this.digest;
	}

	/**
	 * Sets the digest value, given a byte array
	 */
	private void setDigest( byte [] digestBytes )
	{
		StringBuffer str = new StringBuffer(digestBytes.length * 2);
		for( int i = 0; i < digestBytes.length; i++ )
		{
			str.append(hex[(digestBytes[i] & 0xF0) >> 4]);
                        str.append(hex[ digestBytes[i] & 0x0F]);
		}
		this.digest = str.toString();
	}

	/**
	 * Sets the digest value, if the digest string is a valid hex binary string
	 */
	public boolean setDigestString( String digestString )
	{
		this.digest = digestString;

		return true;
	}

	public EppSecDnsKeyData getKeyData() {
    return this.secKeyData;
  }

	public void setKeyData(EppSecDnsKeyData kd) {
    if( null == kd )
      return;
    this.keyDataPresent = true;
    this.secKeyData = kd;
  }

  public boolean isKeyDataPresent() {
    return this.keyDataPresent;
  }


	/**
	 * Gets the optional start date for using the DS data
	 */
/*
	public Calendar getStartDate()
	{
		return this.sDate;
	}
*/

	/**
	 * Sets the optional start date for using the DS data
	 */
/*
	public void setStartDate( Calendar sDate )
	{
		this.sDate = sDate;
	}
*/

	/**
	 * Gets the optional end date for using the DS data
	 */
/*
	public Calendar getEndDate()
	{
		return this.eDate;
	}
*/

	/**
	 * Sets the optional end date for using the DS data
	 */
/*
	public void setEndDate( Calendar eDate )
	{
		this.eDate = eDate;
	}
*/

	/**
	 * Gets the optional validation interval, must be in XML duration format
	 */
/*
	public String getValidationInterval()
	{
		return this.vInterval;
	}
*/

	/**
	 * Sets the optional validation interval, in the XML duration format
	 */
/*
	public void setValidationInterval( String vInterval )
	{
		this.vInterval = vInterval;
	}
*/

	/**
	 * Converts an XML element into an <code>EppSecDnsDsData</code> object.
	 * The caller of this method must make sure that the root node is of
	 * EPP SECDNS dsDataType
	 *
	 * @param root root node for an <code>EppSecDnsDsData</code> object in XML format
	 *
	 * @return an <code>EppSecDnsDsData</code> object, or null if the node is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		String          value = null;
		Calendar        date  = null;
		EppSecDnsDsData data  = new EppSecDnsDsData();
		NodeList        list  = root.getChildNodes();
		for( int i = 0; i < list.getLength(); i++ )
		{
			Node node = list.item(i);
			String name = node.getLocalName();
			if( name == null )
			{
				continue;
			}
			if( name.equals("keyTag") )
			{
				value = EppUtil.getText(node);
				if( (value != null) && (value.trim().length() > 0) )
				{
					data.setKeyTag(Integer.parseInt(value));
				}
			}
			else if( name.equals("alg") )
			{
				value = EppUtil.getText(node);
				if( (value != null) && (value.trim().length() > 0) )
				{
					data.setAlgorithm(Integer.parseInt(value));
				}
			}
			else if( name.equals("digestType") )
			{
				value = EppUtil.getText(node);
				if( (value != null) && (value.trim().length() > 0) )
				{
					data.setDigestType(Integer.parseInt(value));
				}
			}
			else if( name.equals("digest") )
			{
				value = EppUtil.getText(node);
				if( (value != null) && (value.trim().length() > 0) )
				{
					data.setDigestString(value);
				}
			} else if( name.equals("keyData") ) {
				EppSecDnsKeyData kd = (EppSecDnsKeyData)EppSecDnsKeyData.fromXML(node);
				if( null != kd ) {
					data.keyDataPresent = true;/*NOTICE: this is redundant*/
					data.setKeyData( kd );
				}
			}
/*
			else if( name.equals("sDate") )
			{
				date = EppUtil.getDate(node);
				if( date != null )
				{
					data.setStartDate(date);
				}
			}
			else if( name.equals("eDate") )
			{
				date = EppUtil.getDate(node);
				if( date != null )
				{
					data.setEndDate(date);
				}
			}
			else if( name.equals("vInterval") )
			{
				value = EppUtil.getText(node);
				if( (value != null) && (value.trim().length() > 0) )
				{
					data.setValidationInterval(value);
				}
			}
*/
		}

		return data;
	}

	/**
	 * Converts the <code>EppSecDnsDsData</code> object into an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the <code>EppSecDnsDsData</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Element elm;
		Element body = doc.createElement(tag);

		elm = doc.createElement("keyTag");
		elm.appendChild(doc.createTextNode(Integer.toString(this.keyTag)));
		body.appendChild(elm);

		elm = doc.createElement("alg");
		elm.appendChild(doc.createTextNode(Integer.toString(this.alg)));
		body.appendChild(elm);

		elm = doc.createElement("digestType");
		elm.appendChild(doc.createTextNode(Integer.toString(this.digestType)));
		body.appendChild(elm);

		if( this.digest != null )
		{
			elm = doc.createElement("digest");
			elm.appendChild(doc.createTextNode(this.digest));
			body.appendChild(elm);
		}
		if( true == this.keyDataPresent && null != this.secKeyData ) {
			Element element = this.secKeyData.toXML(doc, "keyData");
			body.appendChild(element);
		}
/*
		if( this.sDate != null )
		{
			elm = doc.createElement("sDate");
			elm.appendChild(EppUtil.createTextNode(doc, this.sDate));
			body.appendChild(elm);
		}

		if( this.eDate != null )
		{
			elm = doc.createElement("eDate");
			elm.appendChild(EppUtil.createTextNode(doc, this.eDate));
			body.appendChild(elm);
		}

		if( this.vInterval != null )
		{
			elm = doc.createElement("vInterval");
			elm.appendChild(doc.createTextNode(this.vInterval));
			body.appendChild(elm);
		}
*/
		return body;
	}

	public String toString()
	{
		return toString("dsData");
	}
}
