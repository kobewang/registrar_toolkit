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

#if ! defined(EPPRESPONSEDATACHECKSVCSUB_HPP)    /* { */
#define       EPPRESPONSEDATACHECKSVCSUB_HPP        1

#include "EppResponseDataCheck.hpp"

/**
 * This <code>EppResponseDataCheckSvcsub</code> class implements EPP
 * Response Data entity for EPP Command Check of EPP Svcsub objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $
 */
class EPP_EXPORT EppResponseDataCheckSvcsub : public EppResponseDataCheck
{
private:
	DOMString service;

public:

	/**
	 * Creates an <code>EppResponseDataCheckSvcsub</code> object
	 */
	EppResponseDataCheckSvcsub()
	{
		this->service = null;
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
	 * Destructor
	 */
	~EppResponseDataCheckSvcsub() {};

	/**
	 * Returns the run-time type of an EppEntity object
	 */
	virtual int getEntityType()
	{
		return EppEntity::TYPE_EppResponseDataCheckSvcsub;
	};

	/**
	 * Converts an XML element into an
	 * <code>EppResponseDataCheckSvcsub</code> object. The caller of this
	 * method must make sure that the root node is the resData element of
	 * EPP responseType for creating an EPP Svcsub object.
	 *
	 * @param root root node for an
	 *             <code>EppResponseDataCheckSvcsub</code> object
	 *             in XML format
	 *
	 * @return an <code>EppResponseDataCheckSvcsub</code> object, or null
	 *         if the node is invalid
	 */
	static EppResponseDataCheckSvcsub * fromXML( const DOMNode& root );

	/**
	 * Converts an <code>EppResponseDataCheckSvcsub</code> object into
	 * an XML element.
	 *
	 * @param doc the XML <code>DOMElement</code> object
	 * @param tag the tag/element name for the
	 *            <code>EppResponseDataCheckSvcsub</code> object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );
};

#endif     /* EPPRESPONSEDATACHECKSVCSUB_HPP */  /* } */
