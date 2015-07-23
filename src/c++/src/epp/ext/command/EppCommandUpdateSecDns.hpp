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

#if ! defined(EPPCOMMANDUPDATESECDNS_HPP)    /* { */
#define       EPPCOMMANDUPDATESECDNS_HPP        1

#include "EppExtension.hpp"
#include "EppSecDnsKeyData.hpp"
#include "EppSecDnsDsData.hpp"
#include <xercesc/util/ValueVectorOf.hpp>

#define MAX_NUM_OF_DS_DATA	4

/**
 * This <code>EppCommandSecDns</code> class implements EPP CommandSecDns entity.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.7 $ $Date: 2013/03/06 20:45:08 $
 */
class EPP_EXPORT EppCommandUpdateSecDns : public EppExtension
{
private:
	OldValueVectorOf<EppSecDnsDsData> * addDsDataList;
	OldValueVectorOf<EppSecDnsDsData> * chgDsDataList;
	OldValueVectorOf<EppSecDnsDsData> * remKeyTagList;
	OldValueVectorOf<EppSecDnsKeyData> * addKeyDataList;
	OldValueVectorOf<EppSecDnsKeyData> * remKeyDataList;

	bool				 urgent;
	int maxSigLife;
	bool removeAllDS;
	bool keyDataPresent;

	static const int		 OP_ADD;
	static const int		 OP_CHG;
	static const int		 OP_REM;

	/**
	 * Converts an XML element into an <code>EppSecDnsDsData</code> object list,
	 * or <code>key tag</code> list, The caller of this method must make sure that
	 * the root node contains a list of EPP SECDNS dsDataType objects
	 *
	 * @param root root node for a <code>add</code>, <code>chg</code> or <code>rem</code> tag
	 *        op flag indicating one of the operations: OP_ADD, OP_CHG, OP_REM.
	 *
	 * @return none
	 */
	void fromXML( const DOMNode& root, const int op );

	/**
	 * Converts a list of object into an XML format
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param root the element for attaching the child element, such as, "add", "chg", or "rem"
	 * @param tag the tag/element name for the object, "add", "chg", or "rem"
	 * @param dsDataList the list of <code>EppSecDnsDsData</code> objects
	 * @param keyTagList the list of <code>Kay Tag</code> integers
	 *
	 * @return true if a child element is added
	 */
	bool toXML( DOMDocument& doc, DOMElement& root, const DOMString& tag, OldValueVectorOf<EppSecDnsDsData> * dsDataList, ValueVectorOf<int> * keyTagList );

public:

	/**
	 * Creates an <code>EppCommandSecDns</code> object
	 */
	EppCommandUpdateSecDns():addDsDataList(null), chgDsDataList(null), remKeyTagList(null),
	addKeyDataList(null), remKeyDataList(null), urgent(false), maxSigLife(-1),
	removeAllDS(false), keyDataPresent(false)
	{
		this->addDsDataList = new OldValueVectorOf<EppSecDnsDsData>(MAX_NUM_OF_DS_DATA);
#if defined(EPPDNSSEC_COMPT)
		this->chgDsDataList = new OldValueVectorOf<EppSecDnsDsData>(MAX_NUM_OF_DS_DATA);
#endif
		this->remKeyTagList = new OldValueVectorOf<EppSecDnsDsData>(MAX_NUM_OF_DS_DATA);
		this->addKeyDataList = new OldValueVectorOf<EppSecDnsKeyData>(MAX_NUM_OF_DS_DATA);
		this->remKeyDataList = new OldValueVectorOf<EppSecDnsKeyData>(MAX_NUM_OF_DS_DATA);

		this->urgent        = false;
		this->maxSigLife		= -1;
		this->removeAllDS		= false;
		this->keyDataPresent	= false;
	};

	/**
	 * Destructor
	 */
	~EppCommandUpdateSecDns()
	{
		if( this->addDsDataList != null )
		{
			delete this->addDsDataList;
			this->addDsDataList = null;
		}
#if defined(EPPDNSSEC_COMPT)
		if( this->chgDsDataList != null )
		{
			delete this->chgDsDataList;
			this->chgDsDataList = null;
		}
#endif
		if( this->remKeyTagList != null )
		{
			delete this->remKeyTagList;
			this->remKeyTagList = null;
		}

		if( this->addKeyDataList != null )
		{
			delete this->addKeyDataList;
			this->addKeyDataList = null;
		}

		if( this->remKeyDataList != null )
		{
			delete this->remKeyDataList;
			this->remKeyDataList = null;
		}

	};

	/**
	 * Returns the run-time type of an EppEntity object
	 */
	virtual int getEntityType()
	{
		return EppEntity::TYPE_EppCommandUpdateSecDns;
	};

	/**
	 * Gets the urgent update boolean flag
	 */
	bool getUrgentFlag()
	{
		return this->urgent;
	};

	/**
	 * Sets the urgent update boolean flag
	 */
	void setUrgentFlag( bool urgent )
	{
		this->urgent = urgent;
	};

	bool isRemovelAllDS()
	{
		return this->removeAllDS;
	}

	void isRemoveAllDS( bool flag )
	{
		this->removeAllDS = flag;
	}

	int getMaxSigLife()
	{
		return this->maxSigLife;
	}

	void setMaxSigLife( int secs )
	{
		this->maxSigLife = secs;
	}

	bool isKeyDataPresent()
	{
		return this->keyDataPresent;
	}

	/**
	 * Adds DS Data to the list to be attached to a domain name
	 */
	void add( EppSecDnsDsData& dsData )
	{
		this->addDsDataList->addElement(dsData);
	};

	void add( EppSecDnsKeyData& keyData )
	{
		this->addKeyDataList->addElement(keyData);
	}

	/**
	 * Gets the list of DS data to be attached to a domain name
	 */
	OldValueVectorOf<EppSecDnsDsData> * getDsDataAdded()
	{
		return this->addDsDataList;
	};

	OldValueVectorOf<EppSecDnsKeyData> * getKeyDataAdded()
	{
		return this->addKeyDataList;
	};
#if defined(EPPDNSSEC_COMPT)

	/**
	 * Changes DS Data to the list to be attached to a domain name
	 */
	void change( EppSecDnsDsData& dsData )
	{
		this->chgDsDataList->addElement(dsData);

	};

	/**
	 * Gets the list of DS data to be changed to a domain name
	 */
	OldValueVectorOf<EppSecDnsDsData> * getDsDataChanged()
	{
		return this->chgDsDataList;
	};
#endif

	void remove( EppSecDnsDsData& dsData )
	{
		this->remKeyTagList->addElement(dsData);
	};

	void remove( EppSecDnsKeyData& keyData )
	{
		this->remKeyDataList->addElement(keyData);
	}

	/**
	 * Gets the list of key tags of DS data to be detached from a domain name
	 */
	OldValueVectorOf<EppSecDnsDsData>* getKeyTagRemoved()
	{
		return this->remKeyTagList;
	};

	OldValueVectorOf<EppSecDnsKeyData>* getKeyDataRemoved()
	{
		return this->remKeyDataList;
	}


	/**
	 * Converts an XML element into an <code>EppCommandUpdateSecDns</code> object.
	 * The caller of this method must make sure that the root node is of
	 * EPP SECDNS updateType
	 *
	 * @param root root node for an <code>EppCommandUpdateSecDns</code> object in XML format
	 *
	 * @return an <code>EppCommandUpdateSecDns</code> object, or null if the node is invalid
	 */
	static EppCommandUpdateSecDns * fromXML( const DOMNode& root );

	/**
	 * Converts the <code>EppCommandUpdateSecDns</code> object into an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the <code>EppCommandUpdateSecDns</code> object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );

	DOMString toString()
	{
		return EppEntity::toString(DOMString("update"));
	};
};

#endif     /* EPPCOMMANDUPDATESECDNS_HPP */  /* } */
