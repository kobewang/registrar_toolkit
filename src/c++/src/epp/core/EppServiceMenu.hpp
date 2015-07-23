/*******************************************************************************
 * The MIT License (MIT)
 *  
 * Copyright (c)2015 Neustar Inc.
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

#if ! defined(EPPSERVICEMENU_HPP)    /* { */
#define       EPPSERVICEMENU_HPP        1

#include "EppEntity.hpp"

#define	MAX_NUM_OF_VERSIONS	5
#define	MAX_NUM_OF_SERVICES	10
#define	MAX_NUM_OF_LANGUAGES	20
#define	MAX_NUM_OF_UNSPECS	10

/**
 * This <code>EppServiceMenu</code> class implements EPP svcMenuType entity.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:37 $
 */
class EPP_EXPORT EppServiceMenu : public EppEntity
{
protected:
	/**
	 * The version list of the EPP Service Menu
	 */
	OldValueVectorOf<DOMString> * version;
	/**
	 * The language list of the EPP Service Menu
	 */
	OldValueVectorOf<DOMString> * lang;
	/**
	 * The standard object URI list of the EPP Service Menu (EPP-1.0).
	 * For example: "urn:ietf:params:xml:ns:obj1"
	 */
	OldValueVectorOf<DOMString> * objURI;
	/**
	 * The <I>svcExtension</I> object list of the EPP Service Menu (EPP-1.0).
	 * For example: "http://custom/obj1ext-1.0 obj1ext-1.0.xsd"
	 */
	OldValueVectorOf<DOMString> * extURI;

public:

	/**
	 * Creates an <code>EppServiceMenu</code> object
	 */
	EppServiceMenu()
	{
		this->version = new OldValueVectorOf<DOMString>(MAX_NUM_OF_VERSIONS);
		this->lang    = new OldValueVectorOf<DOMString>(MAX_NUM_OF_LANGUAGES);
		this->objURI  = new OldValueVectorOf<DOMString>(MAX_NUM_OF_SERVICES);
		this->extURI  = new OldValueVectorOf<DOMString>(MAX_NUM_OF_UNSPECS);
	};

	/**
	 * Destructor
	 */
	~EppServiceMenu()
	{
		if( this->version != null )
		{
			delete this->version;
		}
		if( this->lang != null )
		{
			delete this->lang;
		}
		if( this->objURI != null )
		{
			delete this->objURI;
		}
		if( this->extURI != null )
		{
			delete this->extURI;
		}
		this->version = null;
		this->lang    = null;
		this->objURI  = null;
		this->extURI  = null;
	};

	/**
	 * Returns a <code>Vector</code> containing all version numbers
	 * supported by the EPP server
	 */
	OldValueVectorOf<DOMString> * getVersion()
	{
		return this->version;
	};

	/**
	 * Adds a version number to the list of versions supported by the
	 * EPP server
	 */
	void addVersion( const DOMString& version )
	{
		this->version->addElement(version);
	};

	/**
	 * Returns a <code>Vector</code> containing all languages supported
	 * by the Epp server
	 */
	OldValueVectorOf<DOMString> * getLanguage()
	{
		return this->lang;
	};

	/**
	 * Adds a language to the list of languages supported by the
	 * EPP server
	 */
	void addLanguage( const DOMString& language )
	{
		this->lang->addElement(language);
	};

	/**
	 * Returns a <code>Vector</code> containing the the URIs
	 * for all standard objects supported by the EPP server
	 */
	OldValueVectorOf<DOMString> * getService()
	{
		return this->objURI;
	};

	/**
	 * Adds an object URI to the list of all standard objects supported by
	 * the EPP server
	 */
	void addService( const DOMString& service )
	{
		this->objURI->addElement(service);
	};

	/**
	 * Returns a <code>Vector</code> containing the URIs of
	 * all <I>extension</I> objects supported by the EPP server - alias to getServiceExtention()
	 */
	OldValueVectorOf<DOMString> * getUnspec()
	{
		return this->extURI;
	};

	/**
	 * Returns a <code>Vector</code> containing the URIs of
	 * all <I>extension</I> objects supported by the EPP server
	 */
	OldValueVectorOf<DOMString> * getServiceExtension()
	{
		return this->extURI;
	};

	/**
	 * Adds an <I>extension</I> object to the list of all <I>extURI</I>
	 * objects supported by the EPP server
	 */
	void addServiceExtension( const DOMString& uri )
	{
		this->extURI->addElement(uri);
	};

	/**
	 * Adds an <I>extension</I> object to the list of all <I>extension</I>
	 * objects supported by the EPP server
	 *
	 * @param name the name of the object (not used)
	 * @param uri the uri of the object
	 * @param schema the XML schema defining the object (not used)
	 *
	 * <P><B>Note:</B> This method is retained for backward compatiblity
	 */
	void addUnspec( const DOMString& name, const DOMString& uri, const DOMString& schema )
	{
		this->extURI->addElement(uri);
	};

	/**
	 * Converts the <code>EppServiceMenu</code> object into an XML element
	 *
	 * @param doc the XML <code>DOMElement</code> object
	 * @param tag the tag/element name for the <code>EppServiceMenu</code>
	 *            object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );

	/**
	 * Converts an XML element into an <code>EppServiceMenu</code> object.
	 * The caller of this method must make sure that the root node is of
	 * the EPP addrType.
	 *
	 * @param root root node for an <code>EppServiceMenu</code> object
	 *             in XML format
	 *
	 * @return an <code>EppServiceMenu</code> object, or null if the node
	 *         is invalid
	 */
	static EppServiceMenu * fromXML( const DOMNode& root );

	DOMString toString()
	{
		return EppEntity::toString(DOMString("svcMenu"));
	};
};

#endif     /* EPPSERVICEMENU_HPP */  /* } */
