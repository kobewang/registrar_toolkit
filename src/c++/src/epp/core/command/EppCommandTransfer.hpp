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

#if ! defined(EPPCOMMANDTRANSFER_HPP)    /* { */
#define       EPPCOMMANDTRANSFER_HPP        1

#include "EppCommand.hpp"
#include "EppPollable.hpp"
#include "EppAuthInfo.hpp"

/**
 * This <code>EppCommandTransfer</code> class implements EPP Command Transfer
 * entity.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:37 $
 */
class EPP_EXPORT EppCommandTransfer : public EppCommand, public EppPollable
{
protected:
	/**
	 * The type of the transfer operation
	 */
	DOMString     op;
	/**
	 * The authorization information associated with the transfer operation
	 */
	EppAuthInfo * authInfo;

public:
	/**
	 * Transfer operation type for approving a transfer request
	 */
	static const char * OPTYPE_APPROVE;
	/**
	 * Transfer operation type for canceling a transfer request
	 */
	static const char * OPTYPE_CANCEL;
	/**
	 * Transfer operation type for querying the status of a transfer request
	 */
	static const char * OPTYPE_QUERY;
	/**
	 * Transfer operation type for rejecting a transfer request
	 */
	static const char * OPTYPE_REJECT;
	/**
	 * Transfer operation type for requesting an object transfer
	 */
	static const char * OPTYPE_REQUEST;

	/**
	 * Creates an <code>EppCommandTransfer</code> object
	 */
	EppCommandTransfer()
	{
		this->op = NULLXS;
		this->authInfo = null;
	};

	/**
	 * Destructor
	 */
	virtual ~EppCommandTransfer()
	{
		if( this->authInfo != null )
		{
			delete this->authInfo;
			this->authInfo = null;
		}
	};

	/**
	 * Returns the run-time type of an EppEntity object
	 */
	virtual int getEntityType()
	{
		return EppEntity::TYPE_EppCommandTransfer;
	};

	/**
	 * Gets the authorization info for the transfer operation
	 */
	EppAuthInfo * getAuthInfo()
	{
		return this->authInfo;
	};

	/**
	 * Sets the authorization info for the transfer operation
	 */
	void setAuthInfo( EppAuthInfo& authInfo )
	{
		if( this->authInfo == null )
		{
			this->authInfo = new EppAuthInfo();
		}
		*(this->authInfo) = authInfo;
	};

	/**
	 * Gets the operation related to the transfer.
	 */
	DOMString getOperation()
	{
		return this->op;
	}

	/**
	 * Sets the operation related to the transfer. Valid operations are:
	 * <UL>
	 * <LI>approve</LI>
	 * <LI>cancel</LI>
	 * <LI>query</LI>
	 * <LI>reject</LI>
	 * <LI>request</LI>
	 * </UL>
	 */
	void setOperation( DOMString operation )
	{
		this->op = operation;
	};

	/**
	 * Converts an XML element into an <code>EppCommandTransfer</code>
	 * object. The caller of this method must make sure that the root
	 * node is of an EPP Object transferType entity. This method is
	 * for the interface defined in <code>EppPollable</code>
	 *
	 * @param root root node for an <code>EppCommandTransfer</code> object
	 *             in XML format
	 *
	 * @return an <code>EppCommandTransfer</code> object, or null if the
	 *         node is invalid
	 */
	static EppCommandTransfer * fromXMLPoll( const DOMNode& root );

	/**
	 * Converts an XML element into an <code>EppCommandTransfer</code>
	 * object. The caller of this method must make sure that the root
	 * node is of an EPP Command Transfer entity.
	 *
	 * @param root root node for an <code>EppCommandTransfer</code> object
	 *             in XML format
	 *
	 * @return an <code>EppCommandTransfer</code> object, or null if the
	 *         node is invalid
	 */
	static EppCommandTransfer * fromXML( const DOMNode& root );

	DOMString toString()
	{
		return EppEntity::toString(DOMString("transfer"));
	};
};

#endif     /* EPPCOMMANDTRANSFER_HPP */  /* } */
