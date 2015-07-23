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

#if ! defined(EPPRESPONSEDATANEUSTARTRANSACTION_HPP)    /* { */
#define       EPPRESPONSEDATANEUSTARTRANSACTION_HPP        1

#include <time.h>
#include "EppResponseData.hpp"

/**
 * This <code>EppResponseDataNeustarTransaction</code> class implements a Neustar-specific message.
 *
 * @author William Tan william.tan@neustar.biz
 * @version $Revision: 1.1 $ $Date: 2007/10/15 19:03:12 $
 */
class EPP_EXPORT EppResponseDataNeustarTransaction : public EppResponseData
{
private:
	DOMString objID;
	DOMString objType;
	DOMString operation;
	time_t    trnDate;

public:

	/**
	 * Creates an <code>EppResponseDataNeustarTransaction</code> object
	 */
	EppResponseDataNeustarTransaction(DOMString objID, DOMString objType, DOMString operation, time_t trnDate)
	{
		this->objID     = objID;
		this->objType   = objType;
		this->operation = operation;
		this->trnDate   = trnDate;
	};

	/**
	 * Destructor
	 */
	~EppResponseDataNeustarTransaction() {};

	/**
	 * Gets the objID  of the neustar transaction data object
	 */
	DOMString getObjID()
	{
		return this->objID;
	};

	/**
	 * Sets the objID of the neustar transaction data object
	 */
	void setObjID( DOMString objID )
	{
		this->objID = objID;
	};

	/**
	 * Gets the objType of the neustar transaction data object
	 */
	DOMString getObjType()
	{
		return this->objType;
	};

	/**
	 * Sets the objType of the neustar transaction data object
	 */
	void setObjType( DOMString objType )
	{
		this->objType = objType;
	};

	/**
	 * Gets the operation of the neustar transaction data object
	 */
	DOMString getOperation()
	{
		return this->operation;
	};

	/**
	 * Sets the operation of the neustar transaction data object
	 */
	void setOperation( DOMString operation )
	{
		this->operation = operation;
	};

	/**
	 * Gets the date of the neustar transaction data object
	 */
	time_t getTrnDate()
	{
		return this->trnDate;
	};

	/**
	 * Sets the date of the neustar transaction data object
	 */
	void setTrnDate( time_t trnDate )
	{
		this->trnDate = trnDate;
	};


	/**
	 * Converts the <code>EppResponseDataNeustarTransaction</code> object into an XML element
	 *
	 * @param doc the XML <code>DOMElement</code> object
	 * @param tag the tag/element name for the <code>EppResponseDataNeustarTransaction</code> object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );

	/**
	 * Converts an XML element into an <code>EppResponseDataNeustarTransaction</code> object.
	 * The caller of this method must make sure that the root node is of
	 * the EPP Host type.
	 *
	 * @param root root node for an <code>EppResponseDataNeustarTransaction</code> object in
	 *             XML format
	 *
	 * @return an <code>EppResponseDataNeustarTransaction</code> object, or null if the node
	 *         is invalid
	 */
	static EppResponseDataNeustarTransaction * fromXML( const DOMNode& root );

	DOMString toString()
	{
		return EppEntity::toString(DOMString("neustar"));
	};

};

#endif     /* EPPRESPONSEDATANEUSTARTRANSACTION_HPP */  /* } */
