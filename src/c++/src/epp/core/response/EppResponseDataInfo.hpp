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

#if ! defined(EPPRESPONSEDATAINFO_HPP)    /* { */
#define       EPPRESPONSEDATAINFO_HPP        1

#include "EppResponseData.hpp"
#include "EppObject.hpp"

/**
 * This <code>EppResponseDataInfo</code> class implements EPP Response
 * Data entity for EPP Command Info.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
class EPP_EXPORT EppResponseDataInfo : public EppResponseData
{
private:
	EppObject * object;
	bool        freeable;

public:

	/**
	 * Creates an <code>EppResponseDataInfo</code> object
	 */
	EppResponseDataInfo()
	{
		this->object = null;
		this->freeable = false;
	};

	/**
	 * Creates an <code>EppResponseDataInfo</code> object
	 *
	 * @note the memory associated with the object data will not be
	 *       freed by this class. The caller of this method should be
	 *       responsible to free the memory, if needed.
	 */
	EppResponseDataInfo( EppObject * object )
	{
		this->object = object;
		this->freeable = false;
	};

	/**
	 * Destructor
	 */
	~EppResponseDataInfo()
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
		return EppEntity::TYPE_EppResponseDataInfo;
	};

	/**
	 * Gets the <code>EppObject</code> returned by the EPP info command
	 */
	EppObject * getObject()
	{
		return this->object;
	};

	/**
	 * Sets the <code>EppObject</code> returned by the EPP info command
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
	 * Converts the <code>EppResponseDataInfo</code> object into an XML
	 * element
	 *
	 * @param doc the XML <code>DOMElement</code> object
	 * @param tag the tag/element name for the <code>EppResponse</code>
	 *            object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );

	/**
	 * Converts an XML element into an <code>EppResponseDataInfo</code>
	 * object. The caller of this method must make sure that the root
	 * node is of the EPP infDataType.
	 *
	 * @param root root node for an <code>EppResponseDataInfo</code>
	 *             object in XML format
	 *
	 * @return an <code>EppResponseDataInfo</code> object, or null
	 *         if the node is invalid
	 */
	static EppResponseDataInfo * fromXML( const DOMNode& root );
};

#endif     /* EPPRESPONSEDATAINFO_HPP */  /* } */
