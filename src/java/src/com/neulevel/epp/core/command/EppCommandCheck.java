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
 * This <code>EppCommandCheck</code> class implements EPP Command Check
 * entity.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.4 $ $Date: 2009/11/23 14:35:52 $
 */
abstract public class EppCommandCheck extends EppCommand
{
	/**
	 * Gets a list of EPP Object ids to be checked
	 */
	abstract public Vector get();

	/**
	 * Adds an object id to the list of ids of objects to be checked
	 */
	abstract public void add( String objectId );

	/**
	 * Converts an XML element into an <code>EppCommandCheck</code> object.
	 * The caller of this method must make sure that the root node is of an
	 * EPP Command Check entity.
	 *
	 * @param root root node for an <code>EppCommandCheck</code> object
	 *             in XML format
	 *
	 * @return an <code>EppCommandCheck</code> object, or null if the node
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
				if( prefix.equals("contact") )
				{
					return EppCommandCheckContact.fromXML(node);
				}
				else if( prefix.equals("domain") )
				{
					return EppCommandCheckDomain.fromXML(node);
				}
				else if( prefix.equals("host") )
				{
					return EppCommandCheckHost.fromXML(node);
				}
				else if( prefix.equals("svcsub") )
				{
					return EppCommandCheckSvcsub.fromXML(node);
				}
				else if( prefix.equals("xriAU") )
				{
					return EppCommandCheckXriAuthority.fromXML(node);
				}
				else if( prefix.equals("xriINU") )
				{
					return EppCommandCheckXriNumber.fromXML(node);
				}
				else if( prefix.equals("xriINA") )
				{
					return EppCommandCheckXriName.fromXML(node);
				}
				else if( prefix.equals("xriISV") )
				{
					return EppCommandCheckXriService.fromXML(node);
				}
				else if( prefix.equals("xriCS") )
				{
					return EppCommandCheckXriCodeString.fromXML(node);
				}
			}
		}

		return null;
	}

	public String toString()
	{
		return toString("check");
	}
}
