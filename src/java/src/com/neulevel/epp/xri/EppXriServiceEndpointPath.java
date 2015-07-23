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
import com.neulevel.epp.core.EppUtil;


/**
 * This <code>EppXriServiceEndpointPath</code> class encapsulates
 * the EPP XRI Authority ServiceEndpoint Type as defined in the
 * XRI Authority XML Schema type <code>sepPathType</code>.
 *
 * @author William Tan william.tan@neustar.biz
 * @version $Revision: 1.1 $ $Date: 2006/03/12 02:01:30 $
 */
public class EppXriServiceEndpointPath extends EppXriServiceEndpointRule
{

	/**
	 * Creates a default <code>EppXriServiceEndpointPath</code> object
	 */
	public EppXriServiceEndpointPath()
	{
		super();
	}

	/**
	 * Creates an <code>EppXriServiceEndpointPath</code> object with the specified fields
	 */
	public EppXriServiceEndpointPath( String path, String match, Boolean select )
	{
		super(path, match, select);
	}

	/**
	 * Converts an XML element into an <code>EppXriServiceEndpointPath</code> object.
	 * The caller of this method must make sure that the root node is of
	 * the EPP XRI sepTypeType.
	 *
	 * @param root root node for an <code>EppXriServiceEndpoint</code> object in
	 *             XML format
	 *
	 * @return an <code>EppXriServiceEndpointPath</code> object, or null if the node is
	 *         invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		EppXriServiceEndpointPath sepPath = new EppXriServiceEndpointPath();
		sepPath.setFromXML(root);
		return sepPath;
	}

	/**
	 * Gets the value of this Path rule.
	 * This is an alias for the superclass' <code>getValue</code> method.
	 */
	public String getPath()
	{
		return getValue();
	}

	/**
	 * Sets the value of this Path rule.
	 * This is an alias for the superclass' <code>setValue</code> method.
	 */
	public void setPath( String path )
	{
		setValue(path);
	}


	public String toString()
	{
		return toString("path");
	}
}
