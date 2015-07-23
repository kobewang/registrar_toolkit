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

#if ! defined(EPPSECDNSDSDATA_HPP)    /* { */
#define       EPPSECDNSDSDATA_HPP        1

#include <time.h>
#include "EppEntity.hpp"
#include "EppSecDnsKeyData.hpp"

/**
 * This <code>EppSecDnsDsData</code> class implements DS data specified in the
 * EPP DNS Security extension, defined by IETF Draft:
 * <A HREF="http://www.ietf.org/internet-drafts/draft-hollenbeck-epp-secdns-04.txt">
 * draft-hollenbeck-epp-secdns-04.txt</A>, with the following modifications:
 *
 * <UL>
 * <LI>The XML schema file has been modified to handle DS data only
 * <LI>The XML schema file has been modified to allow additions of new DS data
 * </UL>
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.6 $ $Date: 2012/06/21 05:41:10 $
 */
class EPP_EXPORT EppSecDnsDsData : public EppEntity
{
private:
	int		keyTag;
	int		alg;
	int		digestType;
	DOMString	digest;
#if defined(EPPDNSSEC_COMPT)
	time_t		sDate;
	time_t		eDate;
	DOMString	vInterval;
#endif
	bool keyDataPresent;
	EppSecDnsKeyData secKeyData;

	static const char	hex[16];

	/**
	 * Sets the digest value, given a byte array
	 */
	void setDigest( unsigned char * digestBytes, int size )
	{
		DOMString out;
		for( int i = 0; i < size; i++ )
		{
			out += hex[(digestBytes[i] & 0xF0) >> 4];
			out += hex[ digestBytes[i] & 0x0F];
		}
		this->digest = out;
	};

public:

	/**
	 * Creates an <code>EppSecDnsDsData</code> object
	 */
	EppSecDnsDsData()
	{
		this->keyTag       = -1;
		this->alg          = -1;
		this->digestType   = -1;
		this->digest       = NULLXS;
#if defined(EPPDNSSEC_COMPT)
		this->sDate        = 0;
		this->eDate        = 0;
		this->vInterval    = NULLXS;
#endif
		this->secKeyData = EppSecDnsKeyData();
		this->keyDataPresent = false;
	};

	/**
	 * Destructor
	 */
	~EppSecDnsDsData() {};

	/**
	 * Returns the run-time type of an EppEntity object
	 */
	virtual int getEntityType()
	{
		return EppEntity::TYPE_EppSecDnsDsData;
	};

	/**
	 * Gets the key tag value
	 */
	int getKeyTag()
	{
		return this->keyTag;
	};

	/**
	 * Sets the key tag value
	 */
	void setKeyTag( int keyTag )
	{
		this->keyTag = keyTag ;
	};

	/**
	 * Gets the algorithm value
	 */
	int getAlgorithm()
	{
		return this->alg;
	};

	/**
	 * Sets the algorithm value
	 */
	void setAlgorithm( int alg )
	{
		this->alg = alg ;
	};

	/**
	 * Gets the digest type
	 */
	int getDigestType()
	{
		return this->digestType;
	};

	/**
	 * Sets the digest type
	 */
	void setDigestType( int digestType )
	{
		this->digestType = digestType ;
	};

	/**
	 * Gets the digest value
	 */
	DOMString getDigest()
	{
		return this->digest;
	};

	/**
	 * Sets the digest value, if the digest string is a valid hex binary string
	 */
	bool setDigestString( DOMString& digestString )
	{
		if( digestString.length() % 2 != 0 )
		{
			return false;
		}
		for( unsigned int i = 0; i < digestString.length(); i++ )
		{
			char c = digestString.charAt(i);
			if( (c >= '0') && (c <= '9') )
			{
				continue;
			}
			if( (c >= 'a') && (c <= 'f') )
			{
				continue;
			}
			if( (c >= 'A') && (c <= 'F') )
			{
				continue;
			}
			return false;
		}
		this->digest = digestString;

		return true;
	};

#if defined(EPPDNSSEC_COMPT)

	/**
	 * Gets the optional start date for using the DS data
	 */
	time_t getStartDate()
	{
		return this->sDate;
	};

	/**
	 * Sets the optional start date for using the DS data
	 */
	void setStartDate( time_t sDate )
	{
		this->sDate = sDate;
	};

	/**
	 * Gets the optional end date for using the DS data
	 */
	time_t getEndDate()
	{
		return this->eDate;
	};

	/**
	 * Sets the optional end date for using the DS data
	 */
	void setEndDate( time_t eDate )
	{
		this->eDate = eDate;
	};

	/**
	 * Gets the optional validation interval, must be in XML duration format
	 */
	DOMString getValidationInterval()
	{
		return this->vInterval;
	};

	/**
	 * Sets the optional validation interval, in the XML duration format
	 */
	void setValidationInterval( DOMString vInterval )
	{
		this->vInterval = vInterval;
	};
#endif

	/**
	 * Notice that we are returning a copy not the source
	 */
	EppSecDnsKeyData getKeyData()
	{
		return this->secKeyData;
	}

	void setKeyData(const EppSecDnsKeyData *kd)
	{
		if( null == kd )
			return;
		this->keyDataPresent = true;
		this->secKeyData = *kd;
	}

	void setKeyData(const EppSecDnsKeyData &kd)
	{
		this->keyDataPresent = true;
		this->secKeyData = kd;
	}

	bool isKeyDataPresent()
	{
		return keyDataPresent;
	}

	/**
	 * Converts an XML element into an <code>EppSecDnsDsData</code> object.
	 * The caller of this method must make sure that the root node is of
	 * EPP SECDNS dsDataType
	 *
	 * @param root root node for an <code>EppSecDnsDsData</code> object in XML format
	 *
	 * @return an <code>EppSecDnsDsData</code> object, or null if the node is invalid
	 */
	static EppSecDnsDsData * fromXML( const DOMNode& root );

	/**
	 * Converts the <code>EppSecDnsDsData</code> object into an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the <code>EppSecDnsDsData</code> object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );

	DOMString toString()
	{
		return EppEntity::toString(DOMString("secDNS"));
	};
};

#endif     /* EPPSECDNSDSDATA_HPP */  /* } */
