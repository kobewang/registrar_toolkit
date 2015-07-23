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

#if ! defined(EPPRESPONSEDATACREATE_HPP)    /* { */
#define       EPPRESPONSEDATACREATE_HPP        1

#include <time.h>
#include "EppResponseData.hpp"

/**
 * This <code>EppResponseDataCreate</code> class implements EPP Response
 * Data entity for EPP Command Create.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
class EPP_EXPORT EppResponseDataCreate : public EppResponseData
{
protected:
	/**
	 * The ROID associated with the response data after creating
	 * an object successfully
	 *
	 * @deprecated for EPP-04 (06/29/2001)
	 */
	DOMString roid;
	/**
	 * The creation date of the object created.
	 *
	 * <P><B>Note:</B> The default value is the date when the <I>EppResponseDataCreate</I> object is created
	 *
	 * @since EPP-1.0
	 */
	time_t crDate;

public:

	/**
	 * Creates <code>EppResponseDataCreate</code> object
	 */
	EppResponseDataCreate()
	{
		this->roid = null;
		this->crDate = 0;
	};

	/**
	 * Destructor
	 */
	virtual ~EppResponseDataCreate() {};

	/**
	 * Returns the run-time type of an EppEntity object
	 */
	virtual int getEntityType()
	{
		return EppEntity::TYPE_EppResponseDataCreate;
	};

	/**
	 * Gets ROID associated with the creation of an <code>EppObject</code>
	 *
	 * @deprecated for EPP-04 (06/29/2001)
	 */
	DOMString getRoid()
	{
		return this->roid;
	};

	/**
	 * Sets ROID associated with the creation of an <code>EppObject</code>
	 *
	 * @deprecated for EPP-04 (06/29/2001)
	 */
	void setRoid( DOMString roid )
	{
		this->roid = roid;
	};

	/**
	 * Gets creation date of the object created
	 *
	 * @since EPP-1.0
	 */
	time_t getDateCreated()
	{
		return this->crDate;
	};

	/**
	 * Sets creation date of the object created
	 */
	void setDateCreated( time_t crDate )
	{
		this->crDate = crDate;
	};
};

#endif     /* EPPRESPONSEDATACREATE_HPP */  /* } */
