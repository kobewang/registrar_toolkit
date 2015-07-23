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

#if ! defined(EPPCHANGEPOLLOPERATION_HPP)
#define	      EPPCHANGEPOLLOPERATION_HPP	   1

#include "EppEntity.hpp"

/**
 * This <code>EppChangePollOperation</code> class implements EPP Poll Operation
 * as defined in EPP changePoll extension. 
 */
class EppChangePollOperation : public EppEntity
{
public:

	/**
	 * Creates an <code>EppChangePollOperation</code> object
	 */
	EppChangePollOperation()
	{
		this->operation = NULLXS;
		this->subOperation = NULLXS;
	};

	/**
	 * Creates an <code>EppChangePollOperation</code> object with a string operation and a subOperation text
	 */
	EppChangePollOperation( const DOMString& operation, const DOMString& subOp)
	{
		this->operation = operation;
		this->subOperation = subOp;
	};

	/**
	 * Destructor
	 */
	virtual ~EppChangePollOperation() {};

	/**
	 * Gets the operation
	 */
	DOMString getOperation() const
	{
		return this->operation;
	};

	/**
	 * Sets the operation
	 */
	void setOperation( const DOMString& operation )
	{
		this->operation = operation;
	};

	/**
	 * Gets subOperation type of the operation
	 */
	DOMString getSubOperation() const
	{
		return this->subOperation;
	};

	/**
	 * Sets subOperation of the operation
	 */
	void setSubOperation( const DOMString& subOp )
	{
		this->subOperation = subOp;
	};

	/**
	 * Converts the <code>EppChangePollOperation</code> object into an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the <code>EppChangePollOperation</code> object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );

	/**
	 * Converts an XML element into an <code>EppChangePollOperation</code> object. 
	 *
	 * @param root root node for an <code>EppChangePollOperation</code> object in XML format
	 *
	 * @return an <code>EppChangePollOperation</code> object, or null if the node is invalid
	 */
	static EppChangePollOperation * fromXML( const DOMNode& root );

	DOMString toString()
	{
		return EppEntity::toString(DOMString("operation"));
	};

private:
	DOMString	operation;
	DOMString	subOperation;
};

#endif
