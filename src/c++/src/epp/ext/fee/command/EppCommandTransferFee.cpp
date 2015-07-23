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
#include "EppCommandTransferFee.hpp"

DOMElement* EppCommandTransferFee::toXML( DOMDocument& doc, const DOMString& tag )
{
	DOMElement* fExt = EppUtil::createElementNS(doc, "fee", ( tag.isNull() ) ? "transfer" : tag, true, ( char* ) FEE_VERSION.c_str());
	EppRequestFeeType::toXML(doc, *fExt);
	return fExt;
}

EppCommandTransferFee* EppCommandTransferFee::fromXML( const DOMNode& root )
{
	EppCommandTransferFee* ext = new EppCommandTransferFee();
	EppRequestFeeType::fromXMLCommon(root, ext);
	return ext;
}

