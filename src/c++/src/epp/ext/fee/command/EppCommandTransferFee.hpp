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

#if ! defined(EPPCOMMANDTRANSFERFEE_HPP)    /* { */
#define       EPPCOMMANDTRANSFERFEE_HPP 1

#include "EppExtension.hpp"
#include "EppRequestFeeType.hpp"

/**
 * This <code>EppCommandTransferFee</code> class implements domain transfer command specified in the
 * registry fee extension. To prepare domain transfer for registry fee extension, this class should 
 * to be instantiated by user.
 */

class EPP_EXPORT EppCommandTransferFee : public EppExtension, public EppRequestFeeType
{
public:

	EppCommandTransferFee()
	{
	}

	virtual ~EppCommandTransferFee()
	{
	}

	int getEntityType()
	{
		return EppEntity::TYPE_EppCommandTransferFee;
	}

	DOMString toString()
	{
		return EppEntity::toString( DOMString( "transfer" ) );
	}

	static EppCommandTransferFee* fromXML( const DOMNode& root );
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );
};

#endif

