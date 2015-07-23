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
package com.neulevel.epp.ext.fee;

import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.neulevel.epp.core.EppUtil;
import com.neulevel.epp.ext.EppExtension;

/**
 * 
 * @author Santosh Kalsangrah
 * 
 */
public abstract class EppRequestFeeType extends EppExtension
{

	protected String currency;
	protected Vector<EppFeeFee> eppFeeFeeVector;

	public String getCurrency()
	{
		return currency;
	}

	public void setCurrency(String currency)
	{
		this.currency = currency;
	}

	/**
	 * @return the eppFeeFeeVector
	 */
	public Vector<EppFeeFee> getFee()
	{
		return eppFeeFeeVector;
	}

	public void addFee(EppFeeFee feeFee)
	{
		if (eppFeeFeeVector == null)
		{
			eppFeeFeeVector = new Vector<EppFeeFee>();
		}

		eppFeeFeeVector.add(feeFee);
	}

	/**
	 * @param eppFeeFeeVector
	 *            the eppFeeFeeVector to set
	 */
	public void setEppFeeFeeVector(Vector<EppFeeFee> eppFeeFeeVector)
	{
		this.eppFeeFeeVector = eppFeeFeeVector;
	}

	public Element toXML(Document doc, String tag)
	{
		Element body = doc.createElement(tag);
		toXMLCommon(doc, tag, body);
		return body;
	}

	public void toXMLCommon(Document doc, String tag, Element body)
	{
		if (currency != null)
		{
			Element elm = doc.createElement("currency");
			elm.appendChild(doc.createTextNode(currency));
			body.appendChild(elm);

		}
		if (eppFeeFeeVector != null)
		{
			for (EppFeeFee feeFee : eppFeeFeeVector)
			{
				if (feeFee != null)
				{
					body.appendChild(feeFee.toXML(doc, "fee"));
				}
			}
		}
	}

	static public void fromXMLCommon(Node root, EppRequestFeeType data)
	{

		NodeList nodeList = root.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++)
		{
			Node node = nodeList.item(i);
			String name = node.getLocalName();
			if (name == null)
			{
				name = node.getNodeName();
			}

			if (name != null)
			{
				if (name.equals("fee"))
				{
					EppFeeFee feeFee = EppFeeFee.fromXML(node);
					data.addFee(feeFee);
					continue;
				}

				if (name.equals("currency"))
				{
					data.currency = EppUtil.getText(node);
					continue;
				}
			} else
			{
				continue;
			}
		}

	}
}
