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

#if ! defined(EPPRESPONSEDATACHECKCLAIMS_HPP)    /* { */
#define       EPPRESPONSEDATACHECKCLAIMS_HPP        1

#include "EppResponseData.hpp"
#include "EppExtension.hpp"
#include <xercesc/util/ValueVectorOf.hpp>
#include <xercesc/util/KeyValuePair.hpp>
#include "EppLaunchPhase.hpp"

#define	MAX_NUM_OF_CLAIM_CHECKS	16

/**
 * This <code>EppResponseDataCheckClaims</code> class implements EPP Response
 * Data entity for EPP Command Check Claims.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2013/07/09 18:26:37 $
 */
class EPP_EXPORT EppResponseDataCheckClaims : public EppExtension
{
protected:
	/**
	 * A vector for storing key and value pairs
	 */
	ValueVectorOf<DOMStringPtrPair*>* hashMap;
	/**
	 * A vector for storing key and claimKey pairs
	 */
	ValueVectorOf<DOMStringPtrPair*>* claimKeyHashMap;



public:
	//TODO: santosh, moved to public area temporarily. This needs to be in private section and some API would require to get _phase and its members.
	/**
	 * Value for launch:phase
	 */
	EppLaunchPhase _phase;
	/**
	 * Converts <code>EppResponseDataCheckClaims</code> components into
	 * XML elements
	 *
	 * @param doc the XML <code>DOMElement</code> object
	 * @param tag the XML tag for the object identifiers
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );
	/**
	 * Converts shared <code>EppResponseDataCheck</code> components from
	 * XML format.
	 * 
	 * @param root root node for the list of shared components
	 */
	static EppResponseDataCheckClaims * fromXML( const DOMNode& root );
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
	 * Note: Instantiating the object on the stack will not work properly. Partivularly, the object destruction will fail in a fiery crash. This is due to some yet unknown behavior of the ValueVector object. So, always instantiate the object on the heap and everything will be fine.
	 */
	EppResponseDataCheckClaims();

	EppResponseDataCheckClaims(const EppResponseDataCheckClaims&);
	EppResponseDataCheckClaims& operator=(const EppResponseDataCheckClaims&);

	/**
	 * Destructor
	 */
	virtual ~EppResponseDataCheckClaims();

	/**
	 * Returns the run-time type of an EppEntity object
	 */
	virtual int getEntityType()
	{
		return EppEntity::TYPE_EppResponseDataCheckClaims;
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
	 * Gets the <code>HashMap</code> associated with the claimKey data of
	 * the check command
	 */
	ValueVectorOf<DOMStringPtrPair*>*  getClaimKeyHashMap()
	{
		return this->claimKeyHashMap;
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
	 *             the registry or not, or the existence is unknown
	 */
	void add( DOMString id, DOMString flag )
	{
		DOMString* ptr1 = new DOMString(id);
		DOMString* ptr2 = new DOMString(flag);
		DOMStringPtrPair* v = new DOMStringPtrPair(ptr1, ptr2);
		this->hashMap->addElement(v);

		ptr1 = new DOMString(id);
		ptr2 = new DOMString();
		DOMStringPtrPair* r = new DOMStringPtrPair(ptr1, ptr2);
		this->claimKeyHashMap->addElement(r);
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
	void add( DOMString id, bool flag )
	{
		this->add(id, flag, NULLXS);
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
	 * @param claimKey a claimKey text that provides more details of the status
	 */
	void add( DOMString id, bool flag, DOMString claimKey )
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
		ptr2 = new DOMString(claimKey);
		DOMStringPtrPair* r = new DOMStringPtrPair(ptr1, ptr2);
		this->claimKeyHashMap->addElement(r);
	};

	/**
	 * Checks if an object id is in the <code>HashMap</code>
	 *
	 * @return one of the <code>FOUND</code>, <code>NOT_FOUND</code>,
	 *         <code>UNKNWON</code>
	 */
	DOMString check( DOMString id );

	/**
	 * Checks if an object id is available for provisioning
	 */
	bool isAvailable( DOMString id );

	/**
	 * Gets the claimKey for an object id
	 */
	DOMString getClaimKey( DOMString id );

	/**
	 * Gets the phase value
	 */
	EppLaunchPhase phase();

	/**
	 * Sets the phase value
	 */
	void phase(const EppLaunchPhase & s);

	/**
	 *
	 */
	DOMString toString();
};

#endif     /* EPPRESPONSEDATACHECKCLAIMS_HPP */  /* } */
