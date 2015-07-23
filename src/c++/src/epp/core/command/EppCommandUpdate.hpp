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

#if ! defined(EPPCOMMANDUPDATE_HPP)    /* { */
#define       EPPCOMMANDUPDATE_HPP        1

#include "EppCommand.hpp"
#include "EppStatus.hpp"

#define	MAX_NUM_OF_UPDATE_STATUS	10

/**
 * This <code>EppCommandUpdate</code> class implements EPP Command Update
 * entity.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
class EPP_EXPORT EppCommandUpdate : public EppCommand
{
protected:
	OldValueVectorOf<EppStatus> * statusAdded;
	OldValueVectorOf<EppStatus> * statusRemoved;

	/**
	 * Converts a list of EPP Status objects into XML
	 *
	 * @param doc  the XML <code>DOMElement</code> object
	 * @param body the XML <code>DOMElement</code> object to which the list
	 *             of EPP Status objects is appended
	 * @param list the list of EPP Status objects to be converted
	 */
	void statusToXML( DOMDocument& doc, DOMElement& body, OldValueVectorOf<EppStatus> * list );

	/**
	 * Converts a list of EPP Status objects from XML
	 *
	 * @param root the XML <code>Node</code> object containing the list
	 *             of EPP Status objects
	 * @param statusList the list of EPP Status objects to be stored
	 */
	void statusFromXML( const DOMNode& root, OldValueVectorOf<EppStatus> * statusList );

public:

	/**
	 * Creates an <code>EppCommandUpdate</code> object
	 */
	EppCommandUpdate()
	{
		this->statusAdded   = new OldValueVectorOf<EppStatus>(MAX_NUM_OF_UPDATE_STATUS);
		this->statusRemoved = new OldValueVectorOf<EppStatus>(MAX_NUM_OF_UPDATE_STATUS);
	};

	/**
	 * Destructor
	 */
	virtual ~EppCommandUpdate()
	{
		if( this->statusAdded != null )
		{
			delete this->statusAdded;
			this->statusAdded = null;
		}
		if( this->statusRemoved != null )
		{
			delete this->statusRemoved;
			this->statusRemoved = null;
		}
	};

	/**
	 * Returns the run-time type of an EppEntity object
	 */
	virtual int getEntityType()
	{
		return EppEntity::TYPE_EppCommandUpdate;
	};

	/**
	 * Gets the list of new status to be added for the EPP object
	 */
	OldValueVectorOf<EppStatus> * getAddedStatus()
	{
		return this->statusAdded;
	};

	/*
	 * Adds a new status for the EPP object
	 */
	void addStatus( DOMString status )
	{
		EppStatus s(status);
		this->addStatus(s);
	};

	/*
	 * Adds a new status for the EPP object
	 */
	void addStatus( EppStatus& status )
	{
		this->statusAdded->addElement(status);
	};

	/**
	 * Gets the list of old status to be removed for the EPP object
	 */
	OldValueVectorOf<EppStatus> * getRemovedStatus()
	{
		return this->statusRemoved;
	};

	/*
	 * Removes an old status for the EPP object
	 */
	void removeStatus( DOMString status )
	{
		EppStatus s(status);
		this->removeStatus(s);
	};

	/*
	 * Removes an old status for the EPP object
	 */
	void removeStatus( EppStatus& status )
	{
		this->statusRemoved->addElement(status);
	};

	/**
	 * Converts an XML element into an <code>EppCommandUpdate</code>
	 * object. The caller of this method must make sure that the root
	 * node is of an EPP Command Update entity.
	 *
	 * @param root root node for an <code>EppCommandUpdate</code> object
	 *             in XML format
	 *
	 * @return an <code>EppCommandUpdate</code> object, or null if the
	 *         node is invalid
	 */
	static EppCommandUpdate * fromXML( const DOMNode& root );

	DOMString toString()
	{
		return EppEntity::toString(DOMString("update"));
	};
};

#endif     /* EPPCOMMANDUPDATE_HPP */  /* } */
