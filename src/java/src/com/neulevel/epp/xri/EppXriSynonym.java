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
 * This <code>EppXriSynonym</code> class defines a synonym element
 * with a priority attribute. Elements such as LocalID and EquivID
 * are represented with this class.
 * It implements XRI synonymAddType and synonymInfType defined
 * in the XRI authority schema file.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.1 $ $Date: 2008/03/21 00:39:00 $
 */
public class EppXriSynonym extends EppEntity
{
	private int     priority;
	private String  synonym;

	/**
	 * Creates an <code>EppXriSynonym</code> object
	 */
	public EppXriSynonym()
	{
		this(null);
	}

	/**
	 * Creates an <code>EppXriSynonym</code> object with an Synonym.
	 */
	public EppXriSynonym( String synonym )
	{
		this(synonym, -1);
	}

	/**
	 * Creates an <code>EppXriSynonym</code> object with a Synonym
	 * and a priority value
	 */
	public EppXriSynonym( String synonym, int priority )
	{
		setSynonym(synonym);
		setPriority(priority);
	}

	/**
	 * Gets the priority value for this Synonym. A value of <code>-1</code> indicates
	 * null priority.
	 */
	public int getPriority()
	{
		return this.priority;
	}

	/**
	 * Sets the priority value for this Synonym. Use <code>-1</code> to set it to null.
	 */
	public void setPriority( int priority )
	{
		if (priority < -1)
			priority = -1;
		this.priority = priority;
	}

	/**
	 * Gets the XRI string of this Synonym
	 */
	public String getSynonym()
	{
		return this.synonym;
	}

	/**
	 * Sets the XRI string of this Synonym
	 */
	public void setSynonym( String synonym )
	{
		this.synonym = synonym;
	}

	/**
         * Converts the <code>EppXriSynonym</code> object into an XML element
         *
         * @param doc the XML <code>Document</code> object
         * @param tag the tag/element name for the <code>EppXriSynonym</code> object
         *
         * @return an <code>Element</code> object
         */
	public Element toXML( Document doc, String tag )
	{
		Element body = doc.createElement(tag);

		body.appendChild(doc.createTextNode(this.synonym));
		if (this.priority >= 0)
			body.setAttribute("priority", "" + this.priority);

		return body;
	}

	/**
	 * Converts an XML element into an <code>EppXriSynonym</code> object.
	 * The caller of this method must make sure that the root node is of
	 * the EPP XRI synonymAddType or synonymInfType.
	 *
	 * @param root root node for an <code>EppXriSynonym</code> object in
	 *             XML format
	 *
	 * @return an <code>EppXriSynonym</code> object, or null if the node is
	 *         invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		EppXriSynonym synonym     = new EppXriSynonym();
		String   flag     = null;

		synonym.setSynonym(EppUtil.getText(root));

		flag = ((Element) root).getAttribute("priority");
		if( flag != null )
		{
			try
			{
				int priority = Integer.parseInt(flag);
				synonym.setPriority(priority);
			}
			catch( NumberFormatException e )
			{
			}
		}
		return synonym;
	}

	public String toString()
	{
		return toString("synonym");
	}
}
