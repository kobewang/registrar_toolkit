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

#if ! defined(EPPCOMMANDPOLL_HPP)    /* { */
#define       EPPCOMMANDPOLL_HPP        1

#include "EppCommand.hpp"

/**
 * This <code>EppCommandPoll</code> class implements EPP Command Poll
 * entity.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:37 $
 */
class EPP_EXPORT EppCommandPoll : public EppCommand
{
private:
	DOMString op;
	DOMString msgID;

public:
	/**
	 * Poll operation type for polling existing messages (default)
	 */
	static const char * OPTYPE_REQ;
	/**
	 * Poll operation type for acknowledging the receiption of a message
	 */
	static const char * OPTYPE_ACK;

	/**
	 * Creates an <code>EppCommandPoll</code> object, with default operation
	 * type as "req"
	 */
	EppCommandPoll();

	/**
	 * Creates an <code>EppCommandPoll</code> object, with default operation
	 * type as "req", given a client transaction id associated with
	 * operation
	 */
	EppCommandPoll( DOMString xid );

	/**
	 * Creates an <code>EppCommandPoll</code> object, given the operation
	 * type and the message id.
	 *
	 * @note the operation type is either "req" for request, or "ack" for
	 *       acknowledgement
	 */
	EppCommandPoll( DOMString operation, DOMString messageId );

	/**
	 * Creates an <code>EppCommandPoll</code> object, given the operation
	 * type and the message id and a client transaction id associated with
	 * operation
	 *
	 * @note the operation type is either "req" for request, or "ack" for
	 *       acknowledgement
	 */
	EppCommandPoll( DOMString operation, DOMString messageId, DOMString xid );

	/**
	 * Destructor
	 */
	~EppCommandPoll() {};

	/**
	 * Returns the run-time type of an EppEntity object
	 */
	virtual int getEntityType()
	{
		return EppEntity::TYPE_EppCommandPoll;
	};

	/**
	 * Gets the poll operation type
	 */
	DOMString getOperation()
	{
		return this->op;
	};

	/**
	 * Sets the poll operation type, either "req" or "ack"
	 */
	void setOperation( DOMString operation )
	{
		this->op = operation;
	};

	/**
	 * Gets the message id related to the poll command
	 */
	DOMString getMessageId()
	{
		return this->msgID;
	};

	/**
	 * Sets the message id related to the poll command
	 */
	void setMessageId( DOMString messageId )
	{
		this->msgID = messageId;
	};

	/**
	 * Converts the <code>EppCommandPoll</code> object into an XML element
	 *
	 * @param doc the XML <code>DOMElement</code> object
	 * @param tag the tag/element name for the <code>EppCommandPoll</code>
	 *            object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );

	/**
	 * Converts an XML element into an <code>EppCommandPoll</code> object.
	 * The caller of this method must make sure that the root node is of an
	 * EPP Command Create entity.
	 *
	 * @param root root node for an <code>EppCommandPoll</code> object
	 *             in XML format
	 *
	 * @return an <code>EppCommandPoll</code> object, or null if the node
	 *         is invalid
	 */
	static EppCommandPoll * fromXML( const DOMNode& root );

	DOMString toString()
	{
		return EppEntity::toString(DOMString("poll"));
	};
};

#endif     /* EPPCOMMANDPOLL_HPP */  /* } */
