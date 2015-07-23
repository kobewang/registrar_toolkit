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
package com.neulevel.epp.core.command;

import java.util.*;
import org.w3c.dom.*;
import com.neulevel.epp.core.*;
import com.neulevel.epp.xri.command.*;

/**
 * This <code>EppCommandRenew</code> class implements EPP Command Renew
 * entity.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.3 $ $Date: 2006/03/01 01:35:38 $
 */
abstract public class EppCommandRenew extends EppCommand
{
	/**
	 * The current expiration date of the registration for the EPP object
	 * to be renewed
	 */
	protected Calendar  curExpDate;
	/**
	 * The new registration period for the the EPP object to be renewed
	 */
	protected EppPeriod period;

	/**
	 * Gets the current expiration date of the object to be renewed
	 */
	public Calendar getCurrentExpireDate()
	{
		return this.curExpDate;
	}

	/**
	 * Sets the current expiration date of the object to be renewed
	 */
	public void setCurrentExpireDate( Calendar curExpDate )
	{
		this.curExpDate = curExpDate;
	}

	/**
	 * Gets the <code>EppPeriod</code> object containing the period
	 * to be renewed for the object
	 */
	public EppPeriod getPeriod()
	{
		return this.period;
	}

	/**
	 * Sets the <code>EppPeriod</code> object containing the period
	 * to be renewed for the object
	 */
	public void setPeriod( EppPeriod period )
	{
		this.period = period;
	}

	/**
	 * Converts an XML element into an <code>EppCommandRenew</code> object.
	 * The caller of this method must make sure that the root node is of an
	 * EPP Command Renew entity.
	 *
	 * @param root root node for an <code>EppCommandRenew</code> object
	 *             in XML format
	 *
	 * @return an <code>EppCommandRenew</code> object, or null if the node
	 *         is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		String command = root.getLocalName();
		NodeList list = root.getChildNodes();
		for( int i = 0; i < list.getLength(); i++ )
		{
			Node node = list.item(i);
			String prefix = node.getPrefix();
			String name = node.getLocalName();
			if( (prefix == null) || (name == null) )
			{
				continue;
			}
			if( name.equals(command) )
			{
				if( prefix.equals("domain") )
				{
					return EppCommandRenewDomain.fromXML(node);
				}
				else if( prefix.equals("svcsub") )
				{
					return EppCommandRenewSvcsub.fromXML(node);
				}
				else if( prefix.equals("xriINU") )
				{
					return EppCommandRenewXriNumber.fromXML(node);
				}
				else if( prefix.equals("xriINA") )
				{
					return EppCommandRenewXriName.fromXML(node);
				}
				else if( prefix.equals("xriISV") )
				{
					return EppCommandRenewXriService.fromXML(node);
				}
			}
		}

		return null;
	}

	public String toString()
	{
		return toString("renew");
	}
}
