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

#if ! defined(EPPRESPONSEDATACHECK_HPP)    /* { */
#define       EPPRESPONSEDATACHECK_HPP        1

#include "EppResponseData.hpp"
#include <xercesc/util/KeyValuePair.hpp>

#define	MAX_NUM_OF_CHECKS	16

/**
 * This <code>EppResponseDataCheck</code> class implements EPP Response
 * Data entity for EPP Command Check.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
class EPP_EXPORT EppResponseDataCheck : public EppResponseData
{
protected:
	/**
	 * A vector for storing key and value pairs
	 */
	ValueVectorOf<DOMStringPtrPair*>* hashMap;
	/**
	 * A vector for storing key and reason pairs
	 */
	ValueVectorOf<DOMStringPtrPair*>* reasonHashMap;

	/**
	 * Converts shared <code>EppResponseDataCheck</code> component into
	 * XML elements
	 *
	 * @param doc the XML <code>DOMElement</code> object
	 * @param body the XML <code>DOMElement</code> to be attached
	 * @param tag the XML tag for the object identifiers
	 */
	void toXMLCommon( DOMDocument& doc, DOMElement& body, const DOMString& tag );

	/**
	 * Converts shared <code>EppResponseDataCheck</code> components from
	 * XML format.
	 * 
	 * @param root root node for the list of shared components
	 * @param tag the XML tag for the object identifiers
	 * @param ns the XML namespace for the object identifiers
	 */
	void fromXMLCommon( const DOMNode& root, const DOMString& tag, const DOMString& ns );

public:
	/**
	 * Status for checking the existance of an object in the registry
	 */
	static const char * UNKNOWN;
	/**
	 * Status for checking the existance of an object in the registry
	 */
	static const char * FOUND;
	/**
	 * Status for checking the existance of an object in the registry
	 */
	static const char * NOT_FOUND;

	/**
	 * Constructor
	 */
	EppResponseDataCheck()
	{
		this->hashMap       = new ValueVectorOf<DOMStringPtrPair*>(MAX_NUM_OF_CHECKS);
		this->reasonHashMap = new ValueVectorOf<DOMStringPtrPair*>(MAX_NUM_OF_CHECKS);
	};

	/**
	 * Destructor
	 */
	virtual ~EppResponseDataCheck()
	{
		if( this->hashMap != null )
		{
			for( unsigned int i = 0; i < this->hashMap->size(); i++ )
			{
				DOMStringPtrPair* v = this->hashMap->elementAt(i);

				DOMString* first  = v->getKey();
				DOMString* second = v->getValue();

				if ( first != NULL )
				{
					delete first;
				}

				if ( second != NULL )
				{
					delete second;
				}
				delete v;
			}

			delete this->hashMap;
			this->hashMap = null;
		}

		if( this->reasonHashMap != null )
		{
			for( unsigned int i = 0; i < this->reasonHashMap->size(); i++ )
			{
				DOMStringPtrPair* v = this->reasonHashMap->elementAt(i);

				DOMString* first  = v->getKey();
				DOMString* second = v->getValue();

				if ( first != NULL )
				{
					delete first;
				}

				if ( second != NULL )
				{
					delete second;
				}
				delete v;
			}
			delete this->reasonHashMap;
			this->reasonHashMap = null;
		}
	};

	/**
	 * Returns the run-time type of an EppEntity object
	 */
	virtual int getEntityType()
	{
		return EppEntity::TYPE_EppResponseDataCheck;
	};

	/**
	 * Gets the check result list associated with the result data of
	 * the check command - for backward compatibility, alias for getResultHashMap()
	 *
	 * @note this method is different from the Java API in which as
	 *       a hash table is returned instead of a vector.
	 */
	ValueVectorOf<DOMStringPtrPair*>* getHashMap()
	{
		return this->hashMap;
	};

	/**
	 * Gets the check result list associated with the result data of
	 * the check command
	 *
	 * @note this method is different from the Java API in which as
	 *       a hash table is returned instead of a vector.
	 */
	ValueVectorOf<DOMStringPtrPair*>* getResultHashMap()
	{
		return this->hashMap;
	};

	/**
	 * Gets the <code>HashMap</code> associated with the reason data of
	 * the check command
	 */
	ValueVectorOf<DOMStringPtrPair*>*  getReasonHashMap()
	{
		return this->reasonHashMap;
	};

	/**
	 * Adds an entry into the <code>HashMap</code> for indicating if the
	 * object exists in the registry or not - for backward compatibility
	 *
	 * @param id   The Id associated with an EPP object, ROID for a contact
	 *             object, domain name for a domain object, host name for
	 *             a host object
	 * @param flag one of the <code>FOUND</code>, <code>NOT_FOUND</code>,
	 *             <code>UNKNWON</code>, indicating if the object exists in
	 *             the registry or not, or the existance is unknown
	 */
	void add( const DOMString& id, const DOMString& flag )
	{
		DOMString* ptr1 = new DOMString(id);
		DOMString* ptr2 = new DOMString(flag);
		DOMStringPtrPair* v = new DOMStringPtrPair(ptr1, ptr2);
		this->hashMap->addElement(v);

		ptr1 = new DOMString(id);
		ptr2 = new DOMString();
		DOMStringPtrPair* r = new DOMStringPtrPair(ptr1, ptr2);
		this->reasonHashMap->addElement(r);
	};

	/**
	 * Adds an entry into the <code>HashMap</code> for indicating if the
	 * object can be privisioned in the registry or not
	 *
	 * @param id   The Id associated with an EPP object, ROID for a contact
	 *             object, domain name for a domain object, host name for
	 *             a host object
	 * @param flag a boolean flag that indicates the availability of an
	 *             object, i.e.can it be provisioned or not.
	 */
	void add( const DOMString& id, bool flag )
	{
		this->add(id, flag, DOMString());
	};

	/**
	 * Adds an entry into the <code>HashMap</code> for indicating if the
	 * object can be privisioned in the registry or not
	 *
	 * @param id   The Id associated with an EPP object, ROID for a contact
	 *             object, domain name for a domain object, host name for
	 *             a host object
	 * @param flag a boolean flag that indicates the availability of an
	 *             object, i.e.can it be provisioned or not.
	 * @param reason a reason text that provides more details of the status
	 */
	void add( const DOMString& id, bool flag, const DOMString& reason )
	{
		DOMString* ptr1;
		DOMString* ptr2;

		if( flag == true )
		{
			ptr1 = new DOMString(id);
			ptr2 = new DOMString(NOT_FOUND);
			DOMStringPtrPair* v = new DOMStringPtrPair(ptr1, ptr2);
			this->hashMap->addElement(v);
		}
		else
		{
			ptr1 = new DOMString(id);
			ptr2 = new DOMString(FOUND);
			DOMStringPtrPair* v = new DOMStringPtrPair(ptr1, ptr2);
			this->hashMap->addElement(v);
		}
		ptr1 = new DOMString(id);
		ptr2 = new DOMString(reason);
		DOMStringPtrPair* r = new DOMStringPtrPair(ptr1, ptr2);
		this->reasonHashMap->addElement(r);
	};

	/**
	 * Checks if an object id is in the <code>HashMap</code>
	 *
	 * @return one of the <code>FOUND</code>, <code>NOT_FOUND</code>,
	 *         <code>UNKNWON</code>
	 */
	DOMString check( const DOMString& id );

	/**
	 * Checks if an object id is available for provisioning
	 */
	bool isAvailable( const DOMString& id );

	/**
	 * Gets the reason for an object id
	 */
	DOMString getReason( const DOMString& id );
};

#endif     /* EPPRESPONSEDATACHECK_HPP */  /* } */
