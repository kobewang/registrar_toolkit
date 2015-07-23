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

#if ! defined(EPPCOMMANDCHECKDOMAIN_HPP)    /* { */
#define       EPPCOMMANDCHECKDOMAIN_HPP        1

#include "EppCommandCheck.hpp"

#define	MAX_NUM_OF_DOMAIN_NAMES	16

/**
 * This <code>EppCommandCheckDomain</code> class implements EPP Command Check
 * entity for EPP Domain objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:37 $
 */
class EPP_EXPORT EppCommandCheckDomain : public EppCommandCheck
{
private:
	OldValueVectorOf<DOMString> * names;

public:

	/**
	 * Creates an <code>EppCommandCheckDomain</code> object
	 */
	EppCommandCheckDomain()
	{
		this->names = new OldValueVectorOf<DOMString>(MAX_NUM_OF_DOMAIN_NAMES);
	};

	/**
	 * Creates an <code>EppCommandCheckDomain</code> object, given a
	 * client transaction id associated with the operation
	 */
	EppCommandCheckDomain( DOMString xid )
	{
		this->names = new OldValueVectorOf<DOMString>(MAX_NUM_OF_DOMAIN_NAMES);
		this->clTRID = xid;
	};

	/**
	 * Destructor
	 */
	~EppCommandCheckDomain()
	{
		if( this->names != null )
		{
			delete this->names;
			this->names = null;
		}
	};

	/**
	 * Returns the run-time type of an EppEntity object
	 */
	virtual int getEntityType()
	{
		return EppEntity::TYPE_EppCommandCheckDomain;
	};

	/**
	 * Gets the list of the names of the domain objects to be checked
	 */
	OldValueVectorOf<DOMString> * getName()
	{
		return this->names;
	};

	/**
	 * Gets the list of the names of the domain objects to be checked
	 *
	 * @note this is an alias for <code>getName</code>
	 */
	OldValueVectorOf<DOMString> * get()
	{
		return this->getName();
	}

	/**
	 * Add a domain name to the list of the names of the domain
	 * objects to be checked
	 */
	void addName( DOMString name )
	{
		this->names->addElement(name);
	};

	/**
	 * Add a domain name to the list of the names of the domain
	 * objects to be checked
	 *
	 * @note this is an alias for <code>addName</code>
	 */
	void add( DOMString name )
	{
		this->addName(name);
	};

	/**
	 * Converts the <code>EppCommandCheckDomain</code> object into 
	 * an XML element
	 *
	 * @param doc the XML <code>DOMElement</code> object
	 * @param tag the tag/element name for the
	 *            <code>EppCommandCheckDomain</code> object
	 *
	 * @return an <code>DOMElement</code> object 
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );

	/**
	 * Converts an XML element into an <code>EppCommandCheckDomain</code>
	 * object. The caller of this method must make sure that the root node
	 * is of an EPP Command Check entity for EPP Domain objects.
	 *
	 * @param root root node for an <code>EppCommandCheckDomain</code>
	 *             object in XML format
	 *
	 * @return an <code>EppCommandCheckDomain</code> object, or null
	 *         if the node is invalid
	 */
	static EppCommandCheckDomain * fromXML( const DOMNode& root );
};

#endif     /* EPPCOMMANDCHECKDOMAIN_HPP */  /* } */
