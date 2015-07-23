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

#if ! defined(EPPRESPONSE_HPP)    /* { */
#define       EPPRESPONSE_HPP        1

#include <time.h>

#include "EppResult.hpp"
#include "EppResponseData.hpp"
#include "EppUnspec.hpp"
#include "EppTransactionId.hpp"

#define	MAX_NUM_OF_RESULTS	16
#define	MAX_NUM_OF_EXTENSIONS	4

/**
 * This <code>EppResponse</code> class implements EPP Response entity.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
class EPP_EXPORT EppResponse : public EppEntity
{
private:
	/**
	 * The list of result associated with the EPP Response object, for
	 * a successful operation, the length of the list is one, with an
	 * EPP Result object indicating the completion of an EPP Command.
	 */
	OldValueVectorOf<EppResult *> * result;
	/**
	 * The id of the message retrieved from the queue for the client
	 * on the server of the registry
	 */
	DOMString		     msgId; // optional
	/**
	 * The number of messages remaining in the queue for the client
	 * on the server of the registry
	 */
	int                          msgQCount; // optional
	/**
	 * The timestamp of the message queue updated for the client on
	 * the server of the registry
	 */
	time_t                       msgQDate; // optional
	/**
	 * The text of the message queued for the client on the server
	 * of the registry
	 */
	DOMString		     msgQText; // optional
	/**
	 * The response data related to a sucessful operation
	 */
	EppResponseData            * resData; // optional
	/**
	 * Any extension data associated with the response
	 */
	OldValueVectorOf<EppEntity *> * extension;  // optional
	/**
	 * The Transaction Ids (from both client and server) related to the
	 * response
	 */
	EppTransactionId           * trID;
	/**
	 * Flag indicating if the resData object should be freed. It is
	 * set to true only in fromXML(). If the resData field is set via
	 * setResponseData(), the flag will be set to false;
	 */
	bool                         freeable;

public:

	/**
	 * Creates an <code>EppResponse</code> object
	 */
	EppResponse()
	{
		this->result = new OldValueVectorOf<EppResult *>(MAX_NUM_OF_RESULTS);
		this->msgId = null;
		this->msgQCount = 0;
		this->msgQDate = 0;
		this->msgQText = null;
		this->resData = null;
		this->trID = null;
		this->freeable = false;
		this->extension = null;
	};

	/**
	 * Destructor
	 */
	~EppResponse();

	/**
	 * Returns the run-time type of an EppEntity object
	 */
	virtual int getEntityType()
	{
		return EppEntity::TYPE_EppResponse;
	};

	/**
	 * Gets result
	 */
	OldValueVectorOf<EppResult *> * getResult()
	{
		return this->result;
	};

	/**
	 * Adds a result object
	 */
	void addResult( EppResult * result )
	{
		if( result != null )
		{
			this->result->addElement(result);
		}
	};

	/**
	 * Adds a result object
	 */
	void addResult( EppResult& result )
	{
		this->result->addElement(result.clone());
	};

	/**
	 * Gets the id of the message polled from the message queue.
	 *
	 * <P>This is the replacement of <I>EppResultMessage::getId()</I> in EPP-04
	 *
	 * @since EPP-1.0
	 */
	DOMString getMessageId()
	{
		return this->msgId;
	};

	/**
	 * Sets the id of the message polled from the message queue.
	 *
	 * <P>This is the replacement of <I>EppResultMessage::setId()</I> in EPP-04
	 *
	 * @since EPP-1.0
	 */
	void setMessageId( DOMString id )
	{
		this->msgId = id;
	};

	/**
	 * Gets the text of the message polled from the message queue.
	 *
	 * @since EPP-1.0
	 */
	DOMString getMessageText()
	{
		return this->msgQText;
	};

	/**
	 * Sets the text of the message polled from the message queue.
	 *
	 * @since EPP-1.0
	 */
	void setMessageText( DOMString msg )
	{
		this->msgQText = msg;
	};

	/**
	 * Gets number of messages queued
	 */
	int getMessageQueued()
	{
		return this->msgQCount;
	};

	/**
	 * Sets number of messages queued
	 */
	void setMessageQueued( int number )
	{
		this->msgQCount = number;
	};

	/**
	 * Gets the timestamp of the message queue updated by the server
	 */
	time_t getMessageQueueUpdated()
	{
		return this->msgQDate;
	};

	/**
	 * Sets the timestamp of the message queue updated by the server
	 */
	void setMessageQueueUpdated( time_t cal )
	{
		this->msgQDate = cal;
	};

	/**
	 * Gets response data
	 */
	EppResponseData * getResponseData()
	{
		return this->resData;
	};

	/**
	 * Sets response data
	 *
	 * @note the memory associated with the response data will not be
	 *       freed by this class. The caller of this method should be
	 *       responsible to free the memory, if needed.
	 */
	void setResponseData( EppResponseData * data )
	{
		if( this->freeable )
		{
			if( this->resData != null )
			{
				delete this->resData;
			}
			this->freeable = false;
		}
		this->resData = data;
	};

	/**
	 * Gets optional unspecified object - for backward compatibility
	 */
	EppUnspec * getUnspec()
	{
		if( this->extension == null )
		{
			return null;
		}
		for( unsigned int i = 0; i < this->extension->size(); i++ )
		{
			EppEntity * obj = this->extension->elementAt(i);
			if( (obj != null) && (obj->getEntityType() == EppEntity::TYPE_EppExtensionUnspec) )
			{
				return (EppUnspec *) obj;
			}
		}
		return null;
	};

	/**
	 * Sets optional unspecified object -  - for backward compatibility
	 */
	void setUnspec( EppUnspec& unspec )
	{
		if( this->extension == null )
		{
			this->extension = new OldValueVectorOf<EppEntity *>(MAX_NUM_OF_EXTENSIONS);
		}
		if( this->extension == null )
		{
			return;
		}
		EppUnspec * p = new EppUnspec();
		if( p == null )
		{
			return;
		}
		*p = unspec;
		this->extension->addElement(p);
	};

	/**
	 * Gets the list of extension objects, either EppUnspec, or EppEntity
	 */
	OldValueVectorOf<EppEntity *> * getExtension()
	{
		return this->extension;
	};

	/**
	 * Adds an optional extension object
	 */
	void addExtension( EppEntity * ext )
	{
		if( ext == null )
		{
			return;
		}
		if( this->extension == null )
		{
			this->extension = new OldValueVectorOf<EppEntity *>(MAX_NUM_OF_EXTENSIONS);
		}
		if( this->extension == null )
		{
			return;
		}
		this->extension->addElement(ext);
	};

	/**
	 * Gets transaction id from the registrar client
	 */
	EppTransactionId * getTransactionId()
	{
		return this->trID;
	};

	/**
	 * Sets transaction id
	 */
	void setTransactionId( EppTransactionId& xid )
	{
		if( this->trID == null )
		{
			this->trID = new EppTransactionId();
		}
		*(this->trID) = xid;
	};

	/**
	 * Checks if the EPP Response is for a completed operation without
	 * any error
	 */
	bool success();

	/**
	 * Converts the <code>EppResponse</code> object into an XML element
	 *
	 * @param doc the XML <code>DOMElement</code> object
	 * @param tag the tag/element name for the <code>EppResponse</code>
	 *            object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );

	/**
	 * Converts an XML element into an <code>EppResponse</code> object. The
	 * caller of this method must make sure that the root node is of the EPP
	 * responseType.
	 *
	 * @param root root node for an <code>EppResponse</code> object in XML
	 *             format
	 *
	 * @return an <code>EppResponse</code> object, or null if the node is
	 *         invalid
	 */
	static EppResponse * fromXML( const DOMNode& root );

	DOMString toString()
	{
		return EppEntity::toString(DOMString("response"));
	};
};

#endif     /* EPPRESPONSE_HPP */  /* } */
