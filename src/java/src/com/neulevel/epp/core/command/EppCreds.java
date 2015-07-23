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

import org.w3c.dom.*;
import com.neulevel.epp.core.*;

/**
 * This <code>EppCreds</code> class implements EPP credsType entity.
 *
 * <P><B>Warning</B>: This class is for backward compatibility. It will be
 * removed in the future version.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppCreds extends EppEntity
{
	private String          clID;
	private String          pw;
	private String          newPW;
	private EppCredsOptions options;

	/**
	 * Creates an <code>EppCreds</code> object, given the registrar client
	 * id and the password
	 *
	 * @param clientId client/registrar id
	 * @param password the password for the client/registrar
	 */
	public EppCreds( String clientId, String password )
	{
		this(clientId, password, null);
	}

	/**
	 * Creates an <code>EppCreds</code> object, given the registrar client
	 * id, the current password, and the new password
	 *
	 * @param clientId registrar client id
	 * @param password the current password for the registrar client
	 * @param newPassword the new password replacing the current password
	 */
	public EppCreds( String clientId, String password, String newPassword )
	{
		this.clID = clientId;
		this.pw = password;
		this.newPW = newPassword;

		this.options = new EppCredsOptions("1.0", "en-US");
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
	 * Converts the <code>EppCreds</code> object into an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the <code>EppCreds</code> object
	 *
	 * @return an <code>Element</code> object
	 */
	public Element toXML( Document doc, String tag )
	{
		Element body = doc.createElement(tag);
		Element elm;

		if( clID != null )
		{
			elm = doc.createElement("clID");
			elm.appendChild(doc.createTextNode(clID));
			body.appendChild(elm);
		}
		if( pw != null )
		{
			elm = doc.createElement("pw");
			elm.appendChild(doc.createTextNode(pw));
			body.appendChild(elm);
		}
		if( newPW != null )
		{
			elm = doc.createElement("newPW");
			elm.appendChild(doc.createTextNode(newPW));
			body.appendChild(elm);
		}
		if( options != null )
		{
			body.appendChild(options.toXML(doc, "options"));
		}

		return body;
	}

	/**
	 * Converts an XML element into an <code>EppCreds</code> object. The
	 * caller of this method must make sure that the root node is of the
	 * EPP credsType.
	 *
	 * @param root root node for an <code>EppCreds</code> object in XML format
	 *
	 * @return an <code>EppCreds</code> object, or null if the node is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		String clID = null;
		String pw = null;
		String newPW = null;
		EppCredsOptions opt = null;

		NodeList list = root.getChildNodes();
		for( int i = 0; i < list.getLength(); i++ )
		{
			Node node = list.item(i);
			String name = node.getLocalName();
			if( name == null )
			{
				continue;
			}
			if( name.equals("clID") )
			{
				clID = EppUtil.getText(node);
			}
			else if( name.equals("pw") )
			{
				pw = EppUtil.getText(node);
			}
			else if( name.equals("newPW") )
			{
				newPW = EppUtil.getText(node);
			}
			else if( name.equals("options") )
			{
				opt = (EppCredsOptions) EppCredsOptions.fromXML(node);
			}
		}

		if( (clID != null) && (pw != null) )
		{
			EppCreds creds = new EppCreds(clID, pw, newPW);
			creds.setOptions(opt);
			return creds;
		}

		return null;
	}

	/**
	 * Converts the <code>EppCreds</code> object into plain XML text string
	 * by using the default root tag name
	 *
	 * @return a text string representing the <code>EppCreds</code> object
	 *         in XML format
	 */
	public String toString()
	{
		return toString("creds");
	}
}
