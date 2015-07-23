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

#if ! defined(EPPCOMMANDCREATE_HPP)    /* { */
#define       EPPCOMMANDCREATE_HPP        1

#include "EppCommand.hpp"
#include "EppObject.hpp"

/**
 * This <code>EppCommandCreate</code> class implements EPP Command Create
 * entity.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:37 $
 */
class EPP_EXPORT EppCommandCreate : public EppCommand
{
private:
	EppObject * object;
	bool        freeable;

public:

	/**
	 * Creates an <code>EppCommandCreate</code> object
	 */
	EppCommandCreate()
	{
		this->object = null;
		this->freeable = false;
	};

	/**
	 * Creates an <code>EppCommandCreate</code> object
	 *
	 * @param object the <code>EppObject</code> to be processed
	 *
	 * @note the memory associated with the object data will not be
	 *       freed by this class. The caller of this method should be
	 *       responsible to free the memory, if needed.
	 */
	EppCommandCreate( EppObject * object )
	{
		this->object = object;
		this->freeable = false;
	};

	/**
	 * Creates an <code>EppCommandCreate</code> object
	 *
	 * @param object the <code>EppObject</code> to be processed
	 * @param xid    the client transaction id associated with the
	 *               operation
	 */
	EppCommandCreate( EppObject * object, DOMString xid )
	{
		this->object = object;
		this->freeable = false;
		this->clTRID = xid;
	};

	/**
	 * Destructor
	 */
	~EppCommandCreate()
	{
		if( this->freeable )
		{
			if( this->object != null )
			{
				delete this->object;
			}
		}
		this->object = null;
	};

	/**
	 * Returns the run-time type of an EppEntity object
	 */
	virtual int getEntityType()
	{
		return EppEntity::TYPE_EppCommandCreate;
	};

	/**
	 * Gets the object to be processed
	 */
	EppObject * getObject()
	{
		return this->object;
	};

	/**
	 * Sets the object to be processed
	 *
	 * @note the memory associated with the object data will not be
	 *       freed by this class. The caller of this method should be
	 *       responsible to free the memory, if needed.
	 */
	void setObject( EppObject * object )
	{
		if( this->freeable )
		{
			if( this->object != null )
			{
				delete this->object;
			}
			this->freeable = false;
		}
		this->object = object;
	};

	/**
	 * Converts the <code>EppCommandCreate</code> object into an XML element
	 *
	 * @param doc the XML <code>DOMElement</code> object
	 * @param tag the tag/element name for the <code>EppCommandCreate</code>
	 *            object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );

	/**
	 * Converts an XML element into an <code>EppCommandCreate</code> object.
	 * The caller of this method must make sure that the root node is of an
	 * EPP Command Create entity.
	 *
	 * @param root root node for an <code>EppCommandCreate</code> object
	 *             in XML format
	 *
	 * @return an <code>EppCommandCreate</code> object, or null if the node
	 *         is invalid
	 */
	static EppCommandCreate * fromXML( const DOMNode& root );

	DOMString toString()
	{
		return EppEntity::toString(DOMString("create"));
	};
};

#endif     /* EPPCOMMANDCREATE_HPP */  /* } */
