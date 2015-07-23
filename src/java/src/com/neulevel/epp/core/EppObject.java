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
package com.neulevel.epp.core;

import java.util.*;
import org.w3c.dom.*;
import com.neulevel.epp.core.command.*;
import com.neulevel.epp.xri.*;

/**
 * This <code>EppObject</code> class is the base class for all objects
 * registered in the registry via the EPP Protocol.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.5 $ $Date: 2009/11/23 14:35:49 $
 */
abstract public class EppObject extends EppEntity
{
	/**
	 * Type for EPP Contact objcet
	 */
	public static final int CONTACT = 1;
	/**
	 * Type for EPP Domain object
	 */
	public static final int DOMAIN  = 2;
	/**
	 * Type for EPP Host object
	 */
	public static final int HOST    = 3;
	/**
	 * Type for EPP Host object
	 */
	public static final int SVCSUB  = 4;
	/**
	 * Type for EPP XRI Authority object
	 */
	public static final int XRI_AUTHORITY  = 5;
	/**
	 * Type for EPP XRI I-Name object
	 */
	public static final int XRI_INAME      = 6;
	/**
	 * Type for EPP XRI I-Number object
	 */
	public static final int XRI_INUMBER    = 7;
	/**
	 * Type for EPP XRI I-Service object
	 */
	public static final int XRI_ISERVICE    = 8;
	public static final int XRI_CODESTRING  = 9;

	/**
	 * The ROID of the EPP Object in the registry
	 */
	protected String      roid;
	/**
	 * The status list associated with the EPP Object in the registry
	 */
	protected Vector      status;
	/**
	 * The id of the client that is sponsering the EPP Object
	 */
	protected String      clID;
	/**
	 * The id of the client that created the EPP Object initially
	 */
	protected String      crID;
	/**
	 * The timestamp when the EPP Object was created initially
	 */
	protected Calendar    crDate;
	/**
	 * The id of the client that updated the EPP Object most recently
	 */
	protected String      upID;        // optional
	/**
	 * The timestamp when the EPP Object was updated most recently
	 */
	protected Calendar    upDate;      // optional
	/**
	 * The timestamp when the EPP Object is set to be expired.
	 *
	 * <P><B>Note:</B> This field only applies to EPP objects with registration period.
	 */
	protected Calendar    exDate;      // optional, only valid for EppDomain/EppSvcsub/EppXriName/EppXriNumber
	/**
	 * The timestamp when the EPP Object was transferred most recently.
	 *
	 * <P><B>Note:</B> This field does not apply to EPP Host objects and EPP XRI I-Name objects
	 */
	protected Calendar    trDate;      // optional
	/**
	 * The authorization information associated with the EPP object.
	 *
	 * <P><B>Note:</B> This field does not apply to EPP Host objects
	 */
	protected EppAuthInfo authInfo;    // not valid for EppHost

	/**
	 * Gets ROID of the <code>EppObject</code>
	 */
	public String getRoid()
	{
		return this.roid;
	}

	/**
	 * Sets ROID of the <code>EppObject</code>
	 */
	public void setRoid( String roid )
	{
		this.roid = roid;
	}

	/**
	 * Gets the id of the registrar client sponsering the
	 * <code>EppObject</code>.
	 */
	public String getClientId()
	{
		return this.clID;
	}

	/**
	 * Sets the id of the registrar client sponsering the
	 * <code>EppObject</code>
	 */
	public void setClientId( String clientId )
	{
		this.clID = clientId;
	}

	/**
	 * Gets the id of the registrar client creating the
	 * <code>EppObject</code>
	 * initially
	 */
	public String getClientIdCreated()
	{
		return this.crID;
	}

	/**
	 * Sets the id of the registrar client creating the
	 * <code>EppObject</code> initially
	 */
	public void setClientIdCreated( String clientId )
	{
		this.crID = clientId;
	}

	/**
	 * Gets the date of the <code>EppObject</code> created
	 */
	public Calendar getDateCreated()
	{
		return this.crDate;
	}

	/**
	 * Sets the date of the <code>EppObject</code> created
	 */
	public void setDateCreated( Calendar cal )
	{
		this.crDate = cal;
	}

	/**
	 * Gets the id of the registrar client updating the
	 * <code>EppObject</code> most recently
	 */
	public String getClientIdUpdated()
	{
		return this.upID;
	}

	/**
	 * Sets the id of the registrar client updating the
	 * <code>EppObject</code> most recently
	 */
	public void setClientIdUpdated( String clientId )
	{
		this.upID = clientId;
	}

	/**
	 * Gets the date of the <code>EppObject</code> updated
	 */
	public Calendar getDateUpdated()
	{
		return this.upDate;
	}

	/**
	 * Sets the date of the <code>EppObject</code> updated
	 */
	public void setDateUpdated( Calendar cal )
	{
		this.upDate = cal;
	}

	/**
	 * Gets the date of the <code>EppObject</code> expiration date.
	 *
	 * <P><B>Note:</B> Currently, only an EPP domain or svcsub object has
	 * an expiration date
	 */
	public Calendar getDateExpired()
	{
		return this.exDate;
	}

	/**
	 * Sets the expiration date of the <code>EppObject</code>.
	 *
	 * <P><B>Note:</B> Currently, only an EPP domain or svcsub object has
	 * an expiration date
	 */
	public void setDateExpired( Calendar cal )
	{
		this.exDate = cal;
	}

	/**
	 * Gets the date of the <code>EppObject</code> transferred
	 */
	public Calendar getDateTransferred()
	{
		return this.trDate;
	}

	/**
	 * Sets the date of the <code>EppObject</code> transferred
	 */
	public void setDateTransferred( Calendar cal )
	{
		this.trDate = cal;
	}

	/**
	 * Gets the authorization info of the <code>EppObject</code>
	 */
	public EppAuthInfo getAuthInfo()
	{
		return this.authInfo;
	}

	/**
	 * Sets the authorization info of the <code>EppObject</code>
	 */
	public void setAuthInfo( EppAuthInfo authInfo )
	{
		this.authInfo = authInfo;
	}

	/**
	 * Gets the status of the <code>EppObject</code>
	 */
	public Vector getStatus()
	{
		return this.status;
	}

	/**
	 * Adds a status to the <code>EppObject</code>
	 */
	public void addStatus( EppStatus status )
	{
		if( this.status == null )
		{
			this.status = new Vector();
		}
		this.status.addElement(status);
	}

	/**
	 * Converts shared <code>EppObject</code> components from XML
	 *
	 * @param node the node for a shared <code>EppObject</code> component
	 * @param name the name of the node for the shared
	 *             <code>EppObject</code> component
	 */
	protected void fromXMLCommon( Node node, String name )
	{
		if( name.equals("roid") )
		{
			roid = EppUtil.getText(node);
		}
		else if( name.equals("clID") )
		{
			clID = EppUtil.getText(node);
		}
		else if( name.equals("crID") )
		{
			crID = EppUtil.getText(node);
		}
		else if( name.equals("crDate") )
		{
			crDate = EppUtil.getDate(node);
		}
		else if( name.equals("upID") )
		{
			upID = EppUtil.getText(node);
		}
		else if( name.equals("upDate") )
		{
			upDate = EppUtil.getDate(node);
		}
		else if( name.equals("exDate") )
		{
			exDate = EppUtil.getDate(node);
		}
		else if( name.equals("trDate") )
		{
			trDate = EppUtil.getDate(node);
		}
		else if( name.equals("authInfo") )
		{
			authInfo = (EppAuthInfo) EppAuthInfo.fromXML(node);
		}
		else if( name.equals("status") )
		{
			EppStatus s = (EppStatus) EppStatus.fromXML(node);
			if( s != null )
			{
				this.addStatus(s);
			}
		}
	}

	/**
	 * Converts shared <code>EppObject</code> components into XML
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param body the XML <code>Element</code> to be attached
	 */
	protected void toXMLCommon( Document doc, Element body )
	{
		Element elm;
		if( clID != null )
		{
			elm = doc.createElement("clID");
			elm.appendChild(doc.createTextNode(clID));
			body.appendChild(elm);
		}
		if( crID != null )
		{
			elm = doc.createElement("crID");
			elm.appendChild(doc.createTextNode(crID));
			body.appendChild(elm);
		}
		if( crDate != null )
		{
			elm = doc.createElement("crDate");
			elm.appendChild(EppUtil.createTextNode(doc, crDate));
			body.appendChild(elm);
		}
		if( upID != null )
		{
			elm = doc.createElement("upID");
			elm.appendChild(doc.createTextNode(upID));
			body.appendChild(elm);
		}
		if( upDate != null )
		{
			elm = doc.createElement("upDate");
			elm.appendChild(EppUtil.createTextNode(doc, upDate));
			body.appendChild(elm);
		}
		if( exDate != null )
		{
			elm = doc.createElement("exDate");
			elm.appendChild(EppUtil.createTextNode(doc, exDate));
			body.appendChild(elm);
		}
		if( trDate != null )
		{
			elm = doc.createElement("trDate");
			elm.appendChild(EppUtil.createTextNode(doc, trDate));
			body.appendChild(elm);
		}
		if( authInfo != null )
		{
			body.appendChild(authInfo.toXML(doc, "authInfo"));
		}
	}

	/**
	 * Converts an XML element into an <code>EppObject</code> object
	 *
	 * @param root root node for an <code>EppObject</code> object
	 * in XML format
	 */
	public static EppEntity fromXML( Node root )
	{
		String prefix = root.getPrefix();
		if( prefix != null )
		{
			if( prefix.equals("contact") )
			{
				return EppContact.fromXML(root);
			}
			else if( prefix.equals("domain") )
			{
				return EppDomain.fromXML(root);
			}
			else if( prefix.equals("host") )
			{
				return EppHost.fromXML(root);
			}
			else if( prefix.equals("svcsub") )
			{
				return EppSvcsub.fromXML(root);
			}
			else if( prefix.equals("xriAU") )
			{
				return EppXriAuthority.fromXML(root);
			}
			else if( prefix.equals("xriINU") )
			{
				return EppXriNumber.fromXML(root);
			}
			else if( prefix.equals("xriINA") )
			{
				return EppXriName.fromXML(root);
			}
			else if( prefix.equals("xriISV") )
			{
				return EppXriService.fromXML(root);
			}
			else if( prefix.equals("xriCS") )
			{
				return EppXriCodeString.fromXML(root);
			}
		}

		return null;
	}

	/**
	 * Creates an <code>EppCommandCreate</code> object for creating
	 * an EPP Object in the registry.
	 *
	 * @param object the EPP Object to be created in the registry
	 * @param xid the client transaction id associated with the operation
	 */
	public static EppCommandCreate create( EppObject object, String xid )
	{
		return new EppCommandCreate(object, xid);
	}
}
