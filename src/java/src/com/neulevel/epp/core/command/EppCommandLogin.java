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

/**
 * This <code>EppCommandLogin</code> class implements EPP Command Login
 * entity.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.3 $ $Date: 2006/04/03 20:18:33 $
 */
public class EppCommandLogin extends EppCommand
{
	static class EppLoginMenu extends EppServiceMenu
	{
		public EppLoginMenu( EppServiceMenu serviceMenu )
		{
			this.version = null;
			this.lang = null;
			this.objURI = serviceMenu.getService();
			this.extURI = serviceMenu.getServiceExtension();
		}
	}

	private EppLoginMenu svcs;
	private String          clID;
	private String          pw;
	private String          newPW;
	private EppCredsOptions options;
	/**
	 * Creates an <code>EppCommandLogin</code> object
	 *
	 * @param serviceMenu the menu of services to be supported
	 */
	public EppCommandLogin( EppServiceMenu serviceMenu )
	{
		this.svcs = new EppLoginMenu(serviceMenu);

		this.clID  = null;
		this.pw    = null;
		this.newPW = null;
		this.options = new EppCredsOptions("1.0", "en-US");
	}

	/**
	 * Gets the menu of services to be supported
	 */
	public EppServiceMenu getServiceMenu()
	{
		return this.svcs;
	}

	/**
	 * Sets the menu of services to be supported
	 */
	public void setServiceMenu( EppServiceMenu serviceMenu )
	{
		this.svcs = new EppLoginMenu(serviceMenu);
	}

	/**
	 * Gets registrar client id
	 */
	public String getClientId()
	{
		return this.clID;
	}

	/**
	 * Sets registrar client id
	 */
	public void setClientId( String clientId )
	{
		this.clID = clientId;
	}

	/**
	 * Gets registrar client's password
	 */
	public String getPassword()
	{
		return this.pw;
	}

	/**
	 * Sets registrar client's password
	 */
	public void setPassword( String password )
	{
		this.pw = password;
	}

	/**
	 * Gets registrar client's new password
	 */
	public String getNewPassword()
	{
		return this.newPW;
	}

	/**
	 * Sets registrar client's new password
	 */
	public void setNewPassword( String newPassword )
	{
		this.newPW = newPassword;
	}

	/**
	 * Gets credentials options
	 */
	public EppCredsOptions getOptions()
	{
		return this.options;
	}

	/**
	 * Sets credentials options
	 */
	public void setOptions( EppCredsOptions options )
	{
		this.options = options;
	}

	/**
	 * Sets the login credentials - for backward compatibility
	 */
	public void setCreds( EppCreds creds )
	{
		if( creds != null )
		{
			this.clID    = creds.getClientId();
			this.pw      = creds.getPassword();
			this.newPW   = creds.getNewPassword();
			this.options = creds.getOptions();
		}
	}

	/**
	 * Gets the login credentials - for backward compatibility
	 */
	public EppCreds getCreds()
	{
		EppCreds creds = new EppCreds(this.clID, this.pw, this.newPW);
		if( creds != null )
		{
			creds.setOptions(this.options);
		}

		return creds;
	}
	
	/**
	 * Converts the <code>EppCommandLogin</code> object into an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the <code>EppCommandLogin</code>
	 *            object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Vector list = new Vector();
		if( list == null )
		{
			return null;
		}
		Element elm;
		if( clID != null )
		{
			elm = doc.createElement("clID");
			elm.appendChild(doc.createTextNode(clID));
			list.addElement(elm);
		}
		if( pw != null )
		{
			elm = doc.createElement("pw");
			elm.appendChild(doc.createTextNode(pw));
			list.addElement(elm);
		}
		if( newPW != null )
		{
			elm = doc.createElement("newPW");
			elm.appendChild(doc.createTextNode(newPW));
			list.addElement(elm);
		}
		if( options != null )
		{
			elm = options.toXML(doc, "options");
			list.addElement(elm);
		}
		if( svcs != null )
		{
			elm = svcs.toXML(doc, "svcs");
			list.addElement(elm);
		}

		return toXMLCommon(doc, tag, list, null, null);
	}

	/**
	 * Converts an XML element into an <code>EppCommandLogin</code> object.
	 * The caller of this method must make sure that the root node is of an
	 * EPP Command Create entity.
	 *
	 * @param root root node for an <code>EppCommandLogin</code> object
	 *             in XML format
	 *
	 * @return an <code>EppCommandLogin</code> object, or null if the node
	 *         is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		EppCommandLogin cmd = null;
		NodeList list = root.getChildNodes();
		for( int i = 0; i < list.getLength(); i++ )
		{
			Node node = list.item(i);
			String name = node.getLocalName();
			if( name == null )
			{
				continue;
			}
			else if( name.equals("svcs") )
			{
				EppServiceMenu menu = (EppServiceMenu)
						EppServiceMenu.fromXML(node);
				if( menu != null )
				{
					cmd = new EppCommandLogin(menu);
					break;
				}
			}
		}
		if( cmd == null )
		{
			return null;
		}
		for( int i = 0; i < list.getLength(); i++ )
		{
			Node node = list.item(i);
			String name = node.getLocalName();
			if( name == null )
			{
				continue;
			}
			else if( name.equals("clID") )
			{
				cmd.clID = EppUtil.getText(node);
			}
			else if( name.equals("pw") )
			{
				cmd.pw = EppUtil.getText(node);
			}
			else if( name.equals("newPW") )
			{
				cmd.newPW = EppUtil.getText(node);
			}
			else if( name.equals("options") )
			{
				cmd.options = (EppCredsOptions) EppCredsOptions.fromXML(node);
			}
		}

		return cmd;
	}

	public String toString()
	{
		return toString("login");
	}
}
