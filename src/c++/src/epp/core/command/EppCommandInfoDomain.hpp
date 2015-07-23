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

#if ! defined(EPPCOMMANDINFODOMAIN_HPP)    /* { */
#define       EPPCOMMANDINFODOMAIN_HPP        1

#include "EppCommandInfo.hpp"

/**
 * This <code>EppCommandInfoDomain</code> class implements EPP Command Info
 * entity for EPP Domain objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:37 $
 */
class EPP_EXPORT EppCommandInfoDomain : public EppCommandInfo
{
private:
	DOMString     name;
	EppAuthInfo * authInfo;
	DOMString     hostsCtrl;

public:
	/**
	 * Hosts Control - all
	 *
	 * <P>A value of "all" (the default, which MAY be absent) returns
	 * information describing both subordinate and delegated hosts.
	 */
	static const char * HOSTS_ALL;
	/**
	 * Hosts Control - del
	 *
	 * <P>A value of "del" returns information describing only delegated hosts.
	 */
	static const char * HOSTS_DEL;
	/**
	 * Hosts Control - sub
	 *
	 * <P>A value of "sub" returns information describing only subordinate hosts.
	 */
	static const char * HOSTS_SUB;
	/**
	 * Hosts Control - none
	 *
	 * <P>A value of "none" returns no information describing delegated or subordinate hosts.
	 */
	static const char * HOSTS_NONE;

	/**
	 * Creates an <code>EppCommandInfoDomain</code> object for
	 */
	EppCommandInfoDomain()
	{
		this->name = NULLXS;
		this->authInfo = null;
		this->hostsCtrl = HOSTS_ALL;
	};

	/**
	 * Creates an <code>EppCommandInfoDomain</code> object for
	 * querying a domain object based on its name
	 */
	EppCommandInfoDomain( DOMString name )
	{
		this->name = name;
		this->authInfo = null;
		this->hostsCtrl = HOSTS_ALL;
	};

	/**
	 * Creates an <code>EppCommandInfoDomain</code> object for
	 * querying a domain object based on its name, given a client
	 * transaction id associated with the operation
	 */
	EppCommandInfoDomain( DOMString name, DOMString xid )
	{
		this->name = name;
		this->authInfo = null;
		this->hostsCtrl = HOSTS_ALL;
		this->clTRID = xid;
	};

	/**
	 * Destructor
	 */
	~EppCommandInfoDomain()
	{
		if( this->authInfo != null )
		{
			delete this->authInfo;
			this->authInfo = null;
		}
	};

	/**
	 * Returns the run-time type of an EppEntity object
	 */
	virtual int getEntityType()
	{
		return EppEntity::TYPE_EppCommandInfoDomain;
	};

	/**
	 * Gets the name of the domain object to be queried
	 */
	DOMString getName()
	{
		return this->name;
	};

	/**
	 * Sets the name of the domain object to be queried
	 */
	void setName( DOMString name )
	{
		this->name = name;
	};

	/**
	 * Gets the authorization info for querying the domain object
	 */
	EppAuthInfo * getAuthInfo()
	{
		return this->authInfo;
	};

	/**
	 * Sets the authorization info for querying the domain object
	 */
	void setAuthInfo( EppAuthInfo& authInfo )
	{
		if( this->authInfo == null )
		{
			this->authInfo = new EppAuthInfo();
		}
		*(this->authInfo) = authInfo;
	};

	/**
	 * Gets the hosts control attribute for querying the domain object
	 */
	DOMString getHostsControl()
	{
		return this->hostsCtrl;
	};

	/**
	 * Sets the hosts control attribute for querying the domain object,
	 * with one of the following values:
	 *
	 * <UL>
	 * <LI>HOSTS_ALL</LI>
	 * <LI>HOSTS_DEL</LI>
	 * <LI>HOSTS_SUB</LI>
	 * <LI>HOSTS_NONE</LI>
	 * </UL>
	 */
	void setHostsControl( DOMString control )
	{
		this->hostsCtrl = control;
	};

	/**
	 * Converts the <code>EppCommandInfoDomain</code> object into an XML
	 * element
	 *
	 * @param doc the XML <code>DOMElement</code> object
	 * @param tag the tag/element name for the
	 *            <code>EppCommandInfoDomain</code> object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );

	/**
	 * Converts an XML element into an <code>EppCommandInfoDomain</code>
	 * object. The caller of this method must make sure that the root node
	 * is of an EPP Command Info entity for EPP domain object.
	 *
	 * @param root root node for an <code>EppCommandInfoDomain</code>
	 *             object in XML format
	 *
	 * @return an <code>EppCommandInfoDomain</code> object, or null
	 *         if the node is invalid
	 */
	static EppCommandInfoDomain * fromXML( const DOMNode& root );
};

#endif     /* EPPCOMMANDINFODOMAIN_HPP */  /* } */
