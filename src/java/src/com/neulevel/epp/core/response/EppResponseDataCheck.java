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
package com.neulevel.epp.core.response;

import java.util.*;
import org.w3c.dom.*;
import com.neulevel.epp.core.*;

/**
 * This <code>EppResponseDataCheck</code> class implements EPP Response
 * Data entity for EPP Command Check.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
abstract public class EppResponseDataCheck extends EppResponseData
{
	/**
	 * Status for checking the existance of an object in the registry
	 */
	public static final String UNKNOWN   = " ";
	/**
	 * Status for checking the existance of an object in the registry
	 */
	public static final String FOUND     = "+";
	/**
	 * Status for checking the existance of an object in the registry
	 */
	public static final String NOT_FOUND = "-";

	/**
	 * A <code>HashMap</code> storing key and value pairs
	 */
	protected HashMap hashMap = new HashMap();
	/**
	 * A <code>HashMap</code> storing key and reason pairs
	 */
	protected HashMap reasonHashMap = new HashMap();

	/**
	 * Gets the <code>HashMap</code> associated with the result data of
	 * the check command - for backward compatibility, alias for getResultHashMap()
	 */
	public HashMap getHashMap()
	{
		return this.hashMap;
	}

	/**
	 * Gets the <code>HashMap</code> associated with the result data of
	 * the check command
	 */
	public HashMap getResultHashMap()
	{
		return this.hashMap;
	}

	/**
	 * Gets the <code>HashMap</code> associated with the reason data of
	 * the check command
	 */
	public HashMap getReasonHashMap()
	{
		return this.reasonHashMap;
	}

	/**
	 * Adds an entry into the <code>HashMap</code> for indicating if the
	 * object exists in the registry or not - for backward compatibility
	 *
	 * @param id   The Id associated with an EPP object, ROID for a contact
	 *             object, domain name for a domain object, host name for
	 *             a host object
	 * @param flag one of the <code>FOUND</code>, <code>NOT_FOUND</code>,
	 *             <code>UNKNWON</code>, indicating if the object exists in
	 *             the registry or not, or the existance is unknown
	 */
	public void add( String id, String flag )
	{
		this.hashMap.put(id, flag);
	}

	/**
	 * Adds an entry into the <code>HashMap</code> for indicating if the
	 * object can be privisioned in the registry or not
	 *
	 * @param id   The Id associated with an EPP object, ROID for a contact
	 *             object, domain name for a domain object, host name for
	 *             a host object
	 * @param flag a boolean flag that indicates the availability of an
	 *             object, i.e.can it be provisioned or not.
	 */
	public void add( String id, boolean flag )
	{
		this.add(id, flag, null);
	}

	/**
	 * Adds an entry into the <code>HashMap</code> for indicating if the
	 * object can be privisioned in the registry or not
	 *
	 * @param id   The Id associated with an EPP object, ROID for a contact
	 *             object, domain name for a domain object, host name for
	 *             a host object
	 * @param flag a boolean flag that indicates the availability of an
	 *             object, i.e.can it be provisioned or not.
	 * @param reason a reason text that provides more details of the status
	 */
	public void add( String id, boolean flag, String reason )
	{
		if( flag == true )
		{
			this.hashMap.put(id, NOT_FOUND);
		}
		else
		{
			this.hashMap.put(id, FOUND);
		}
		if( reason != null )
		{
			this.reasonHashMap.put(id, reason);
		}
	}

	/**
	 * Checks if an object id is in the <code>HashMap</code> - for backward compatibility
	 *
	 * @return one of the <code>FOUND</code>, <code>NOT_FOUND</code>,
	 *         <code>UNKNWON</code>
	 */
	public String check( String id )
	{
		Object obj = this.hashMap.get(id);
		if( (obj != null) && (obj instanceof String) )
		{
			return (String) obj;
		}
		return UNKNOWN;
	}

	/**
	 * Checks if an object id is available for provisioning
	 */
	public boolean isAvailable( String id )
	{
		Object obj = this.hashMap.get(id);
		if( (obj != null) && (obj instanceof String) )
		{
			if( obj.equals(FOUND) )
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * Gets the reason for an object id
	 */
	public String getReason( String id )
	{
		Object obj = this.reasonHashMap.get(id);
		if( (obj != null) && (obj instanceof String) )
		{
			return (String) obj;
		}
		return null;
	}

	/**
	 * Converts shared <code>EppResponseDataCheck</code> component into
	 * XML elements
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param body the XML <code>Element</code> to be attached
	 * @param tag the XML tag for the object identifiers
	 */
	protected void toXMLCommon( Document doc, Element body, String tag )
	{
		Set keyset = this.hashMap.keySet();
		if( keyset == null )
		{
			return;
		}
		Iterator list = keyset.iterator();
		if( list == null )
		{
			return;
		}
		Element cd;
		Element elm;
		while( list.hasNext() )
		{
			Object o = list.next();
			Object x = this.hashMap.get(o);
			if(    (o != null) && (o instanceof String)
			    && (x != null) && (x instanceof String) )
			{
				cd = doc.createElement("cd");
				elm = doc.createElement(tag);
				if( x.equals(FOUND) )
				{
					elm.setAttribute("avail", "0");
				}
				else
				{
					elm.setAttribute("avail", "1");
				}
				elm.appendChild(doc.createTextNode((String) o));
				cd.appendChild(elm);
				x = this.reasonHashMap.get(o);
			    	if( (x != null) && (x instanceof String) )
				{
					String s = (String) x;
					if( (s != null) && (s.length() > 0) )
					{
						elm = doc.createElement("reason");
						elm.appendChild(doc.createTextNode(s));
						cd.appendChild(elm);
					}
				}
				body.appendChild(cd);
			}
		}

		return;
	}

	/**
	 * Converts shared <code>EppResponseDataCheck</code> components from
	 * XML format.
	 * 
	 * @param root root node for the list of shared components
	 * @param tag the XML tag for the object identifiers
	 */
	protected void fromXMLCommon( Node root, String tag )
	{
		NodeList list = root.getChildNodes();
		for( int i = 0; i < list.getLength(); i++ )
		{
			Node node = list.item(i);
			String name = node.getLocalName();
			if( name == null )
			{
				continue;
			}
			if( name.equals("cd") )
			{
				NodeList clist = node.getChildNodes();
				if( clist == null )
				{
					continue;
				}
				String  id = null;
				boolean flag = true;
				String  reason = null;
				for( int j = 0; j < clist.getLength(); j++ )
				{
					Node cnode = clist.item(j);
					String cname = cnode.getLocalName();
					if( cname == null )
					{
						continue;
					}
					if( cname.equals(tag) )
					{
						id = EppUtil.getText(cnode);
						String cd = ((Element) cnode).getAttribute("avail");
						if( (cd != null)
						    && (    cd.equals("0")
							 || cd.equalsIgnoreCase("f")
							 || cd.equalsIgnoreCase("false") ) )
						{
							flag = false;
						}
						else
						{
							flag = true;
						}
					}
					else if( cname.equals("reason") )
					{
						reason = EppUtil.getText(cnode);
					}
				}
				if( (id != null) && (id.length() > 0) )
				{
					this.add(id, flag, reason);
				}
			}
		}
	}
}
