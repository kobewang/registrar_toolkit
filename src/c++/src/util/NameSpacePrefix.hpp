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

#if ! defined(NAMESPACEPREFIX_HPP)    
#define       NAMESPACEPREFIX_HPP        1
#include "XercesString.hpp"
#include <xercesc/dom/DOM.hpp>

class NameSpacePrefix
{
public:

	static inline DOMString getEppPrefix(const DOMNode* node)
	{
		if ( NULL == node )
		{
			return NULLXS;
		}

		return node->lookupPrefix(XS("urn:ietf:params:xml:ns:epp-1.0"));
	}

	static inline DOMString getSMDPrefix(const DOMNode* node)
	{
		if ( NULL == node )
		{
			return NULLXS;
		}

		return  node->lookupPrefix(XS("urn:ietf:params:xml:ns:signedMark-1.0"));
	}

	static inline DOMString getLaunchPrefix(const DOMNode* node)
	{
		if ( NULL == node )
		{
			return NULLXS;
		}

		return  node->lookupPrefix(XS("urn:ietf:params:xml:ns:launch-1.0"));
	}

	static inline DOMString getMarkPrefix(const DOMNode* node)
	{
		if ( NULL == node )
		{
			return NULLXS;
		}

		return node->lookupPrefix(XS("urn:ietf:params:xml:ns:mark-1.0"));
	}

	static inline DOMString getDomainPrefix(const DOMNode* node)
	{
		if ( NULL == node )
		{
			return NULLXS;
		}

		return node->lookupPrefix(XS("urn:ietf:params:xml:ns:domain-1.0"));
	}

	static inline DOMString getContactPrefix(const DOMNode* node)
	{
		if ( NULL == node )
		{
			return NULLXS;
		}

		return  node->lookupPrefix(XS("urn:ietf:params:xml:ns:contact-1.0"));
	}

	static inline DOMString getSecDNSPrefix(const DOMNode* node)
	{
		if ( NULL == node )
		{
			return NULLXS;
		}

		return  node->lookupPrefix(XS("urn:ietf:params:xml:ns:secDNS-1.1"));
	}

	static inline DOMString getIdnPrefix(const DOMNode* node)
	{
		if ( NULL == node )
		{
			return NULLXS;
		}

		return  node->lookupPrefix(XS("urn:ietf:params:xml:ns:idn-1.0"));
	}

	static inline DOMString getNeulevelPrefix(const DOMNode* node)
	{
		if ( NULL == node )
		{
			return NULLXS;
		}

		return  node->lookupPrefix(XS("urn:ietf:params:xml:ns:neulevel"));
	}
};

#endif
