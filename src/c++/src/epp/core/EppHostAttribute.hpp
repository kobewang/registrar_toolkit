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

#if ! defined(EPPHOSTATTRIBUTE_HPP)	/* { */
#define	      EPPHOSTATTRIBUTE_HPP	   1

#include "EppEntity.hpp"
#include "EppIpAddress.hpp"


#define	MAX_NUM_OF_IP_ADDRESSES		13

/**
 * This <code>EppHostAttribute</code> class implements EPP hostAttrType entity
 * that is used by <code>EppDomain</code> objects.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:37 $
 */
class EPP_EXPORT EppHostAttribute : public EppEntity
{
private:
	DOMString		      hostname;
	OldValueVectorOf<EppIpAddress> * hostaddr;

public:

	/**
	 * Creates an <code>EppHostAttribute</code> object with a host name
	 */
	EppHostAttribute( DOMString hostname )
	{
		this->hostname = hostname;
		this->hostaddr = null;
	};

	/**
	 * Creates an <code>EppHostAttribute</code> object with a host name
	 * and a list of <code>EppIpAddress</code> objects
	 */
	EppHostAttribute( DOMString hostname, OldValueVectorOf<EppIpAddress> * address )
	{
		this->hostname = hostname;
		this->hostaddr = address;
	};

	/**
	 * Destructor
	 */
	~EppHostAttribute()
	{
		if( this->hostaddr != null )
		{
			delete this->hostaddr;
		}
		this->hostaddr = null;
	};

	/**
	 * Gets the host name
	 */
	DOMString getHostName()
	{
		return this->hostname;
	};

	/**
	 * Sets the host name
	 */
	void setHostName( DOMString hostname )
	{
		this->hostname = hostname;
	};

	/**
	 * Gets the list of ip addresses associated with the host name
	 */
	OldValueVectorOf<EppIpAddress> * getIpAddress()
	{
		return this->hostaddr;
	}

	/**
	 * Sets the list of ip addresses associated with the host name
	 */
	void setIpAddress( OldValueVectorOf<EppIpAddress> * address )
	{
		if( this->hostaddr != null )
		{
			delete this->hostaddr;
		}
		this->hostaddr = address;
	}

	/**
	 * Adds an ip address to the list of ip addresses associated
	 * with the host name
	 */
	void addIpAddress( EppIpAddress address )
	{
		if( this->hostaddr == null )
		{
			this->hostaddr = new OldValueVectorOf<EppIpAddress>(MAX_NUM_OF_IP_ADDRESSES);
		}
		this->hostaddr->addElement(address);
	};

	/**
	 * Converts the <code>EppHostAttribute</code> object into an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the <code>EppHostAttribute</code>
	 *            object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );

	/**
	 * Converts an XML element into an <code>EppHostAttribute</code> object.
	 * The caller of this method must make sure that the root node is of
	 * the EPP Contact Type type.
	 *
	 * @param root root node for an <code>EppHostAttribute</code> object
	 *             in XML format
	 *
	 * @return an <code>EppHostAttribute</code> object, or null if the node
	 *         is invalid
	 */
	static EppHostAttribute * fromXML( const DOMNode& root );

	DOMString toString()
	{
		return EppEntity::toString(DOMString("hostAttr"));
	};
};

#endif	/*    EPPHOSTATTRIBUTE_HPP */	/* } */
