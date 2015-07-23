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
import com.neulevel.epp.xri.*;

/**
 * This <code>EppXriServiceEndpoint</code> class defines a service endpoint
 * associated with XRI authority objects.
 * It implements XRI sepAddType and sepInfType defined
 * in the XRI authority schema file.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.10 $ $Date: 2008/04/17 04:28:05 $
 */
public class EppXriServiceEndpoint extends EppEntity
{
	private String  id;
	private int     priority;
	private String  authority;
	private Vector  type;
	private Vector  path;
	private Vector  uri;
	private Vector  mediaType;
	private Vector  ref;
	private Vector  redirect;
	private Vector  localID;
	private String  extension;

	/**
	 * Creates an <code>EppXriServiceEndpoint</code> object
	 */
	public EppXriServiceEndpoint()
	{
		this(null);
	}

	/**
	 * Creates an <code>EppXriServiceEndpoint</code> object with an identifier
	 */
	public EppXriServiceEndpoint( String id )
	{
		this.id        = id;
		this.priority  = -1;
		this.authority = null;
		this.type      = new Vector();
		this.path      = new Vector();
		this.uri       = new Vector();
		this.mediaType = new Vector();
		this.ref       = new Vector();
		this.redirect  = new Vector();
		this.localID   = new Vector();
		this.extension = null;
	}

	/**
	 * Gets the identifier of this XRI Service Endpoint object
	 */
	public String getId()
	{
		return this.id;
	}

	/**
	 * Sets the identifier of this XRI Service Endpoint object
	 */
	public void setId( String id )
	{
		this.id = id;
	}

	/**
	 * Gets the priority value for this XRI Service Endpoint object
	 */
	public int getPriority()
	{
		return this.priority;
	}

	/**
	 * Sets the priority value for this XRI Service Endpoint object
	 */
	public void setPriority( int priority )
	{
		if (priority < -1)
			priority = -1;
		this.priority = priority;
	}

	/**
	 * Gets the identifier of the authority that provides the services of this XRI Service Endpoint object
	 */
	public String getAuthority()
	{
		return this.authority;
	}

	/**
	 * Sets the identifier of the authority that provides the services of this XRI Service Endpoint object
	 */
	public void setAuthority( String authority )
	{
		this.authority = authority;
	}

	/**
	 * Gets the list of service types of this XRI Service Endpoint object
	 */
	public Vector getType()
	{
		return this.type;
	}

	/**
	 * Add a service type to the service type list of this XRI Service Endpoint object
	 */
	public void addType( String type )
	{
		this.addType(type, null, null);
	}

	/**
	 * Add a service type to the service type list of this XRI Service Endpoint object
	 */
	public void addType( String type, String match, Boolean select )
	{
		EppXriServiceEndpointType sepType = new EppXriServiceEndpointType(type, match, select);
		this.addType(sepType);
	}

	/**
	 * Add a service type object to the service type list
	 */
	public void addType( EppXriServiceEndpointType type )
	{
		this.type.addElement(type);
	}

	/**
	 * Gets the list of paths of this XRI Service Endpoint object
	 */
	public Vector getPath()
	{
		return this.path;
	}

	/**
	 * Add a path to the path list of this XRI Service Endpoint object
	 */
	public void addPath( String path )
	{
		this.addPath(path, null, null);
	}

	/**
	 * Add a path to the path list of this XRI Service Endpoint object
	 */
	public void addPath( String path, String match, Boolean select )
	{
		EppXriServiceEndpointPath sepPath = new EppXriServiceEndpointPath(path, match, select);
		addPath(sepPath);
	}

	/**
	 * Add a path object to the path list 
	 */
	public void addPath( EppXriServiceEndpointPath path )
	{
		this.path.addElement(path);
	}

	/**
	 * Gets the URIs associated with this XRI Service Endpoint object
	 */
	public Vector getURI()
	{
		return this.uri;
	}

	/**
	 * Adds a URI to be associated with this XRI Service Endpoint object with no priority value
	 */
	public void addURI( String uri )
	{
		EppXriURI t = new EppXriURI(uri);
		this.uri.addElement(t);
	}

	/**
	 * Adds a URI to be associated with this XRI Service Endpoint object with a priority value
	 */
	public void addURI( String uri, int priority, String append )
	{
		EppXriURI t = new EppXriURI(uri, priority, append);
		this.uri.addElement(t);
	}

	/**
	 * Adds a URI to be associated with this XRI Service Endpoint object
	 */
	public void addURI( EppXriURI uri )
	{
		this.uri.addElement(uri);
	}

	/**
	 * Gets the media types associated with this XRI Service Endpoint object
	 */
	public Vector getMediaType()
	{
		return this.mediaType;
	}

	/**
	 * Adds a media type to be associated with this XRI Service Endpoint object
	 */
	public void addMediaType( String mediaType )
	{
		this.addMediaType(mediaType, null, null);
	}

	/**
	 * Adds a media type to be associated with this XRI Service Endpoint object
	 */
	public void addMediaType( String mediaType, String match, Boolean select )
	{
		EppXriServiceEndpointMediaType sepMType =
			new EppXriServiceEndpointMediaType(mediaType, match, select);
		addMediaType(sepMType);
	}

	/**
	 * Adds a media type object to be associated with this XRI Service Endpoint object
	 */
	public void addMediaType( EppXriServiceEndpointMediaType mediaType )
	{
		this.mediaType.addElement(mediaType);
	}

	/**
	 * Gets a list of Refs associated with the XRI authority object
	 */
	public Vector getRef()
	{
		return this.ref;
	}

	/**
	 * Adds a Ref associated with the XRI authority object
	 *
	 * <P><B>Note:</B> This method should be only called by the EPP server, and
	 *       an EPP client should not perform this operation.
	 */
	public void addRef( EppXriRef ref )
	{
		this.ref.addElement(ref);
	}

	/**
	 * Gets a list of Redirects associated with the XRI authority object
	 */
	public Vector getRedirect()
	{
		return this.redirect;
	}

	/**
	 * Adds a Redirect associated with the XRI authority object
	 *
	 * <P><B>Note:</B> This method should be only called by the EPP server, and
	 *       an EPP client should not perform this operation.
	 */
	public void addRedirect( EppXriURI redirect )
	{
		this.redirect.addElement(redirect);
	}

	/**
	 * Gets a list of LocalIDs associated with the XRI authority object
	 */
	public Vector getLocalID()
	{
		return this.localID;
	}

	/**
	 * Adds a LocalID associated with the XRI authority object
	 *
	 * <P><B>Note:</B> This method should be only called by the EPP server, and
	 *       an EPP client should not perform this operation.
	 */
	public void addLocalID( EppXriSynonym id )
	{
		this.localID.addElement(id);
	}

	/**
	 * Gets the extension chunk of this XRI Service Endpoint object
	 */
	public String getExtension()
	{
		return this.extension;
	}

	/**
	 * Sets the extension chunk of this XRI Service Endpoint object
	 */
	public void setExtension( String extension )
	{
		this.extension = extension;
	}

	/**
         * Converts the <code>EppXriServiceEndpoint</code> object into an XML element
         *
         * @param doc the XML <code>Document</code> object
         * @param tag the tag/element name for the <code>EppXriServiceEndpoint</code> object
         *
         * @return an <code>Element</code> object
         */
	public Element toXML( Document doc, String tag )
	{
		Element body = doc.createElement(tag);
		Element elm;

		if( this.id != null )
		{
			elm = doc.createElement("id");
			elm.appendChild(doc.createTextNode(this.id));
			body.appendChild(elm);
		}

		if (this.priority >= 0)
		{
			elm = doc.createElement("priority");
			elm.appendChild(doc.createTextNode("" + this.priority));
			body.appendChild(elm);
		}

		if( this.authority != null )
		{
			elm = doc.createElement("authority");
			elm.appendChild(doc.createTextNode(this.authority));
			body.appendChild(elm);
		}
		if( this.type != null )
		{
			for( int i = 0; i < this.type.size(); i++ )
			{
				EppXriServiceEndpointType sepType =
					(EppXriServiceEndpointType) this.type.elementAt(i);
				body.appendChild(sepType.toXML(doc, "type"));
			}
		}
		if( this.path != null )
		{
			for( int i = 0; i < this.path.size(); i++ )
			{
				EppXriServiceEndpointPath sepPath = 
					(EppXriServiceEndpointPath) this.path.elementAt(i);
				body.appendChild(sepPath.toXML(doc, "path"));
			}
		}
		if( this.uri != null )
		{
			for( int i = 0; i < this.uri.size(); i++ )
			{
				EppXriURI s = (EppXriURI) this.uri.elementAt(i);
				body.appendChild(s.toXML(doc, "uri"));
			}
		}
		if( this.mediaType != null )
		{
			for( int i = 0; i < this.mediaType.size(); i++ )
			{
				EppXriServiceEndpointMediaType sepMType = 
					(EppXriServiceEndpointMediaType) this.mediaType.elementAt(i);
				body.appendChild(sepMType.toXML(doc, "mediaType"));
			}
		}
		if( ref != null )
		{
			for( int i = 0; i < ref.size(); i++ )
			{
				EppXriRef t = (EppXriRef) ref.elementAt(i);
				body.appendChild(t.toXML(doc, "ref"));
			}
		}
		if( redirect != null )
		{
			for( int i = 0; i < redirect.size(); i++ )
			{
				EppXriURI t = (EppXriURI) redirect.elementAt(i);
				body.appendChild(t.toXML(doc, "redirect"));
			}
		}
		if( localID != null )
		{
			for( int i = 0; i < localID.size(); i++ )
			{
				EppXriSynonym t = (EppXriSynonym) localID.elementAt(i);
				body.appendChild(t.toXML(doc, "localID"));
			}
		}
		if( extension != null )
		{
			elm = doc.createElement("extension");
			elm.appendChild(doc.createTextNode(extension));
			body.appendChild(elm);
		}

		return body;
	}

	/**
	 * Converts an XML element into an <code>EppXriServiceEndpoint</code> object.
	 * The caller of this method must make sure that the root node is of
	 * the EPP XRI sepAddType or sepInfType.
	 *
	 * @param root root node for an <code>EppXriServiceEndpoint</code> object in
	 *             XML format
	 *
	 * @return an <code>EppXriServiceEndpoint</code> object, or null if the node is
	 *         invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		EppXriServiceEndpoint sep = new EppXriServiceEndpoint();

		NodeList list = root.getChildNodes();
		for( int i = 0; i < list.getLength(); i++ )
		{
			Node node = list.item(i);
			String name = node.getLocalName();
			if( name == null )
			{
				continue;
			}
			if( name.equals("id") )
			{
				sep.setId(EppUtil.getText(node));
			}
			else if( name.equals("priority") )
			{
				String flag = EppUtil.getText(node);
				if( flag != null )
				{
					try
					{
						int priority = Integer.parseInt(flag);
						sep.setPriority(priority);
					}
					catch( NumberFormatException e )
					{
					}
				}
			}
			else if( name.equals("authority") )
			{
				sep.setAuthority(EppUtil.getText(node));
			}
			else if( name.equals("type") )
			{
				EppXriServiceEndpointType type = (EppXriServiceEndpointType) EppXriServiceEndpointType.fromXML(node);
				sep.addType(type);
			}
			else if( name.equals("path") )
			{
				EppXriServiceEndpointPath path = (EppXriServiceEndpointPath) EppXriServiceEndpointPath.fromXML(node);
				sep.addPath(path);
			}
			else if( name.equals("uri") )
			{
				EppXriURI s = (EppXriURI) EppXriURI.fromXML(node);
				sep.addURI(s);
			}
			else if( name.equals("mediaType") )
			{
				EppXriServiceEndpointMediaType mType = (EppXriServiceEndpointMediaType) EppXriServiceEndpointMediaType.fromXML(node);
				sep.addMediaType(mType);
			}
			else if( name.equals("ref") )
			{
				EppXriRef ref = (EppXriRef) EppXriRef.fromXML(node);
				if( ref != null )
				{
					sep.addRef(ref);
				}
			}
			else if( name.equals("redirect") )
			{
				EppXriURI redirect = (EppXriURI) EppXriURI.fromXML(node);
				if( redirect != null )
				{
					sep.addRedirect(redirect);
				}
			}
			else if( name.equals("localID") )
			{
				EppXriSynonym localID = (EppXriSynonym) EppXriSynonym.fromXML(node);
				if( localID != null )
				{
					sep.addLocalID(localID);
				}
			}
			else if( name.equals("extension") )
			{
				sep.setExtension(EppUtil.getText(node));
			}
		}

		return sep;
	}

	public String toString()
	{
		return toString("sep");
	}
}



