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

#if ! defined(EPPTRANSACTIONID_HPP)    /* { */
#define       EPPTRANSACTIONID_HPP        1

#include "EppEntity.hpp"

/**
 * This <code>EppTransactionId</code> class implements EPP trIDType entity.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:37 $
 */
class EPP_EXPORT EppTransactionId : public EppEntity
{
private:
	DOMString clTRID;
	DOMString svTRID;

public:

	/**
	 * Creates an <code>EppTransactionId</code> object
	 */
	EppTransactionId()
	{
		this->clTRID = NULLXS;
		this->svTRID = NULLXS;
	};

	/**
	 * Creates an <code>EppTransactionId</code> object, given only
	 * service transaction id
	 *
	 * @param serviceXID service transaction id
	 */
	EppTransactionId( DOMString serviceXID )
	{
		this->clTRID = NULLXS;
		this->svTRID = serviceXID;
	};

	/**
	 * Creates an <code>EppTransactionId</code> object, given the client
	 * transaction id and service transaction id
	 *
	 * @param clientXID client transaction id
	 * @param serviceXID service transaction id
	 */
	EppTransactionId( DOMString clientXID, DOMString serviceXID )
	{
		this->clTRID = clientXID;
		this->svTRID = serviceXID;
	};

	/**
	 * Destructor
	 */
	~EppTransactionId() {};

	/**
	 * Gets client transaction id
	 */
	DOMString getClientTransactionId()
	{
		return this->clTRID;
	};

	/**
	 * Sets client transaction id
	 */
	void setClientTransactionId( DOMString clientXID )
	{
		this->clTRID = clientXID;
	};

	/**
	 * Gets service transaction id
	 */
	DOMString getServiceTransactionId()
	{
		return this->svTRID;
	};

	/**
	 * Sets service transaction id
	 */
	void setServiceTransactionId( DOMString serviceXID )
	{
		this->svTRID = serviceXID;
	};

	/**
	 * Converts the <code>EppTransactionId</code> object into an XML element
	 *
	 * @param doc the XML <code>DOMElement</code> object
	 * @param tag the tag/element name for the <code>EppTransactionId</code>
	 *            object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag )
	{
		return toXML(doc, tag, false);
	};

	/**
	 * Converts the <code>EppTransactionId</code> object into an XML element
	 *
	 * @param doc the XML <code>DOMDocument</code> object
	 * @param tag the tag/element name for the <code>EppTransactionId</code>
	 *            object
	 * @param nsflag the flag indicating if "epp" name space prefix is reqired
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag, bool nsflag );

	/**
	 * Converts an XML element into an <code>EppTransactionId</code> object.
	 * The caller of this method must make sure that the root node is of the
	 * EPP trIDType
	 *
	 * @param root root node for an <code>EppTransactionId</code> object
	 *             in XML format
	 *
	 * @return an <code>EppTransactionId</code> object, or null if the node
	 *         is invalid
	 */
	static EppTransactionId * fromXML( const DOMNode& root );

	DOMString toString()
	{
		return EppEntity::toString(DOMString("trID"));
	};
};

#endif     /* EPPTRANSACTIONID_HPP */  /* } */
