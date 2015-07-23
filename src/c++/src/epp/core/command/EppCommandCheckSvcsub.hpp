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

#if ! defined(EPPCOMMANDCHECKSVCSUB_HPP)    /* { */
#define       EPPCOMMANDCHECKSVCSUB_HPP        1

#include "EppCommandCheck.hpp"

#define	MAX_NUM_OF_SVCSUB_IDS	16

/**
 * This <code>EppCommandCheckSvcsub</code> class implements EPP Command Check
 * entity for EPP Svcsub objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $
 */
class EPP_EXPORT EppCommandCheckSvcsub : public EppCommandCheck
{
private:
	OldValueVectorOf<DOMString> * ids;
	DOMString service;

public:

	/**
	 * Creates an <code>EppCommandCheckSvcsub</code> object
	 */
	EppCommandCheckSvcsub()
	{
		this->ids = new OldValueVectorOf<DOMString>(MAX_NUM_OF_SVCSUB_IDS);
		this->service = NULLXS;
	};

	/**
	 * Creates an <code>EppCommandCheckSvcsub</code> object, given a
	 * client transaction id associated with the operation
	 */
	EppCommandCheckSvcsub( DOMString xid )
	{
		this->ids = new OldValueVectorOf<DOMString>(MAX_NUM_OF_SVCSUB_IDS);
		this->service = NULLXS;
		this->clTRID = xid;
	};

	/**
	 * Destructor
	 */
	~EppCommandCheckSvcsub()
	{
		if( this->ids != null )
		{
			delete this->ids;
			this->ids = null;
		}
	};

	/**
	 * Returns the run-time type of an EppEntity object
	 */
	virtual int getEntityType()
	{
		return EppEntity::TYPE_EppCommandCheckSvcsub;
	};

	/**
	 * Gets the list of the ids of the subscription objects to be checked
	 */
	OldValueVectorOf<DOMString> * getId()
	{
		return this->ids;
	};

	/**
	 * Gets the list of the ids of the subscription objects to be checked
	 *
	 * @note this is an alias for <code>getId</code>
	 */
	OldValueVectorOf<DOMString> * get()
	{
		return this->getId();
	};

	/**
	 * Adds the id of a subscription object to the list of ids of subscription
	 * objects be checked
	 */
	void addId( DOMString id )
	{
		this->ids->addElement(id);
	};

	/**
	 * Adds the id of a subscription object to the list of ids of subscription
	 * objects be checked
	 *
	 * @note this is an alias for <code>addId</code>
	 */
	void add( DOMString id )
	{
		this->addId(id);
	};

	/**
	 * Gets the service name
	 */
	DOMString getService()
	{
		return this->service;
	};

	/**
	 * Sets the service name
	 */
	void setService( DOMString service )
	{
		this->service = service;
	};

	/**
	 * Converts the <code>EppCommandCheckSvcsub</code> object into 
	 * an XML element
	 *
	 * @param doc the XML <code>DOMElement</code> object
	 * @param tag the tag/element name for the
	 *            <code>EppCommandCheckSvcsub</code> object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );

	/**
	 * Converts an XML element into an <code>EppCommandCheckSvcsub</code>
	 * object. The caller of this method must make sure that the root node
	 * is of an EPP Command Check entity for EPP Svcsub objects.
	 *
	 * @param root root node for an <code>EppCommandCheckSvcsub</code>
	 *             object in XML format
	 *
	 * @return an <code>EppCommandCheckSvcsub</code> object, or null
	 *         if the node is invalid
	 */
	static EppCommandCheckSvcsub * fromXML( const DOMNode& root );
};

#endif     /* EPPCOMMANDCHECKSVCSUB_HPP */  /* } */
