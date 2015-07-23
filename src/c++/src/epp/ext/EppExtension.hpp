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

#if ! defined(EPPEXTENSION_HPP)    /* { */
#define       EPPEXTENSION_HPP        1

#include "EppEntity.hpp"
#include <stdint.h>

#define EPPSECDNS_NONE		0
#define EPPSECDNS_10			16
#define EPPSECDNS_11			17

#define EPPSECDNSMAJOR_MASK (240 & ( (1<<7)|(1<<6)|(1<<5)|(1<<4) ))
#define EPPSECDNSMINOR_MASK (15 & ( (1<<3)|(1<<2)|(1<<1)|(1<<0) ))
#define getDnsSecMajor(i) ((((uint8_t)i) & (EPPSECDNSMAJOR_MASK))>>4)
#define getDnsSecMinor(i) (((uint8_t)i) & (EPPSECDNSMINOR_MASK))
#define setDnsSecVersion(ma,mi) ( (((uint8_t)ma)<<4) | ((uint8_t)mi) )

/**
 * This <code>EppExtension</code> class implements EPP Extension entity.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.3 $ $Date: 2012/05/18 05:38:49 $
 */
class EPP_EXPORT EppExtension : public EppEntity
{
public:

	/**
	 * Creates an <code>EppExtension</code> object
	 */
	EppExtension():secDnsVersion(setDnsSecVersion(0, 0)){ };

	/**
	 * Destructor
	 */
	virtual ~EppExtension();

	/**
	 * Returns the run-time type of an EppEntity object
	 */
	virtual int getEntityType()
	{
		return EppEntity::TYPE_EppExtension;
	};

	virtual int getDnsSecMajorVersion()
	{
		return (int)(getDnsSecMajor(this->secDnsVersion));
	}

	virtual int getDnsSecMinorVersion()
	{
		return (int)(getDnsSecMinor(this->secDnsVersion));
	}

	/**
	 * Converts an XML element into an <code>EppExtension</code> object.
	 * The caller of this method must make sure that the root node is a
	 * child node of an EPP extension tag
	 *
	 * @param root root node for an <code>EppExtension</code> object in XML format
	 *
	 * @return an <code>EppExtension</code> object, or null if the node is invalid
	 */
	static EppExtension* fromXML( const DOMNode &root );
protected:
	uint8_t secDnsVersion;
private:
};

#endif     /* EPPEXTENSION_HPP */  /* } */
