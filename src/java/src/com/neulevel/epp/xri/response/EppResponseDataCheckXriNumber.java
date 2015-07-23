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
package com.neulevel.epp.xri.response;

import org.w3c.dom.*;
import com.neulevel.epp.core.*;
import com.neulevel.epp.core.response.*;

/**
 * This <code>EppResponseDataCheckXriNumber</code> class implements EPP
 * Response Data entity for EPP Command Check of EPP XRI I-Number objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppResponseDataCheckXriNumber extends EppResponseDataCheck
{
	/**
	 * Checks an <code>EppResponseDataCheckXriNumber</code> object
	 */
	public EppResponseDataCheckXriNumber()
	{
	}

	/**
	 * Converts an XML element into an
	 * <code>EppResponseDataCheckXriNumber</code> object. The caller of this
	 * method must make sure that the root node is the resData element of
	 * EPP responseType for checking EPP XRI I-Number objects.
	 *
	 * @param root root node for an
	 *             <code>EppResponseDataCheckXriNumber</code> object
	 *             in XML format
	 *
	 * @return an <code>EppResponseDataCheckXriNumber</code> object, or null
	 *         if the node is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		EppResponseDataCheckXriNumber rsp = new EppResponseDataCheckXriNumber();
		rsp.fromXMLCommon(root, "inumber");
		return rsp;
	}

	/**
	 * Converts an <code>EppResponseDataCheckXriNumber</code> object into
	 * an XML element.
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the
	 *            <code>EppResponseDataCheckXriNumber</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Element elm;
		Element body = doc.createElement(tag);
		Element data = EppUtil.createElementNS(doc, "xriINU", "chkData");
		body.appendChild(data);
		toXMLCommon(doc, data, "inumber");

		return body;
	}
}
