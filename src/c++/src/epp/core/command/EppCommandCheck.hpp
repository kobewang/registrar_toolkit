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

#if ! defined(EPPCOMMANDCHECK_HPP)    /* { */
#define       EPPCOMMANDCHECK_HPP        1

#include "EppCommand.hpp"

/**
 * This <code>EppCommandCheck</code> class implements EPP Command Check
 * entity.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:37 $
 */
class EPP_EXPORT EppCommandCheck : public EppCommand
{
public:
	/**
	 * Gets a list of EPP Object ids to be checked
	 */
	virtual OldValueVectorOf<DOMString> * get() = 0;

	/**
	 * Adds an object id to the list of ids of objects to be checked
	 */
	virtual void add( DOMString objectId ) = 0;

	/**
	 * Returns the run-time type of an EppEntity object
	 */
	virtual int getEntityType()
	{
		return EppEntity::TYPE_EppCommandCheck;
	};

	/**
	 * Converts an XML element into an <code>EppCommandCheck</code> object.
	 * The caller of this method must make sure that the root node is of an
	 * EPP Command Check entity.
	 *
	 * @param root root node for an <code>EppCommandCheck</code> object
	 *             in XML format
	 *
	 * @return an <code>EppCommandCheck</code> object, or null if the node
	 *         is invalid
	 */
	static EppCommandCheck * fromXML( const DOMNode& root );

	DOMString toString()
	{
		return EppEntity::toString(DOMString("check"));
	};
};

#endif     /* EPPCOMMANDCHECK_HPP */  /* } */
