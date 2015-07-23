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

#if ! defined(EPPCOMMANDINFO_HPP)    /* { */
#define       EPPCOMMANDINFO_HPP        1

#include "EppCommand.hpp"

/**
 * This <code>EppCommandInfo</code> class implements EPP Command Info
 * entity.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:37 $
 */
class EPP_EXPORT EppCommandInfo : public EppCommand
{
public:

	/**
	 * Returns the run-time type of an EppEntity object
	 */
	virtual int getEntityType()
	{
		return EppEntity::TYPE_EppCommandInfo;
	};

	/**
	 * Converts an XML element into an <code>EppCommandInfo</code> object.
	 * The caller of this method must make sure that the root node is of an
	 * EPP Command Info entity.
	 *
	 * @param root root node for an <code>EppCommandInfo</code> object
	 *             in XML format
	 *
	 * @return an <code>EppCommandInfo</code> object, or null if the node
	 *         is invalid
	 */
	static EppCommandInfo * fromXML( const DOMNode& root );

	DOMString toString()
	{
		return EppEntity::toString(DOMString("info"));
	};
};

#endif     /* EPPCOMMANDINFO_HPP */  /* } */
