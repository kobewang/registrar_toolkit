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

#if ! defined(EPPSECDNSKEYDATA_HPP)    /* { */
#define       EPPSECDNSKEYDATA_HPP        1

#include <time.h>
#include "EppEntity.hpp"

/**
 * This <code>EppSecDnsKeyData</code> class implements DS data specified in the
 * EPP DNS Security extension, defined by IETF Draft:
 * <A HREF="http://www.ietf.org/internet-drafts/draft-hollenbeck-epp-secdns-04.txt">
 * draft-hollenbeck-epp-secdns-04.txt</A>, with the following modifications:
 *
 * <UL>
 * <LI>The XML schema file has been modified to handle DS data only
 * <LI>The XML schema file has been modified to allow additions of new DS data
 * </UL>
 *
 */
class EPP_EXPORT EppSecDnsKeyData : public EppEntity
{
public:

	EppSecDnsKeyData(): flags(-1), protocol(-1), alg(-1), public_key(NULLXS)
	{
	}
	
	//Copy ctor
	EppSecDnsKeyData( const EppSecDnsKeyData &kd ):
	flags(kd.flags), protocol(kd.protocol),
	alg(kd.alg), public_key(kd.public_key)
	{
	}

	EppSecDnsKeyData& operator=(const EppSecDnsKeyData &kd)
	{
		if( this == &kd )
			return *this;
		this->flags = kd.flags;
		this->protocol = kd.protocol;
		this->alg = kd.alg;
		this->public_key = kd.public_key;
		return *this;
	}

	~EppSecDnsKeyData() 
	{
	};

	virtual int getEntityType()
	{
		return EppEntity::TYPE_EppSecDnsKeyData;
	}

	/**
	 * Gets the algorithm value
	 */
	int8_t getAlgorithm()
	{
		return this->alg;
	}

	/**
	 * Sets the algorithm value
	 */
	void setAlgorithm( int8_t alg )
	{
		this->alg = alg ;
	}

	short getFlags()
	{
		return this->flags;
	}

	void setFlags( short _flags)
	{
		this->flags = _flags;
	}

	int8_t getProtocol()
	{
		return this->protocol;
	}

	void setProtocol( int8_t _protocol )
	{
		this->protocol = _protocol;
	}

	DOMString getPublicKey()
	{
		return this->public_key;
	}

	void setPublicKey( DOMString _pubkey )
	{
		this->public_key = _pubkey;
	}

	/**
	 * Converts an XML element into an <code>EppSecDnsKeyData</code> object.
	 * The caller of this method must make sure that the root node is of
	 * EPP SECDNS dsDataType
	 *
	 * @param root root node for an <code>EppSecDnsKeyData</code> object in XML format
	 *
	 * @return an <code>EppSecDnsKeyData</code> object, or null if the node is invalid
	 */
	static EppSecDnsKeyData * fromXML( const DOMNode& root );

	/**
	 * Converts the <code>EppSecDnsKeyData</code> object into an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the <code>EppSecDnsKeyData</code> object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );

	DOMString toString()
	{
		return EppEntity::toString(DOMString("secDNS"));
	}
private:
	short flags;
	int8_t protocol;
	int8_t alg;
	DOMString public_key;
};

#endif     /* EPPSECDNSKEYDATA_HPP */  /* } */
