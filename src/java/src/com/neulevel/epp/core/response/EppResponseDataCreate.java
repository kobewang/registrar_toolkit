/*******************************************************************************
 * The MIT License (MIT)
 *  
 * Copyright (c) 2015 Neustar Inc.
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
package com.neulevel.epp.core.response;

import java.util.*;
import com.neulevel.epp.core.*;

/**
 * This <code>EppResponseDataCreate</code> class implements EPP Response
 * Data entity for EPP Command Create.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
abstract public class EppResponseDataCreate extends EppResponseData
{
	/**
	 * The ROID associated with the response data after creating
	 * an object sucessfully
	 *
	 * @deprecated for EPP-04 (06/29/2001)
	 */
	protected String roid;

	/**
         * The creation date of the object created.
         *
	 * <P><B>Note:</B> The default value is the date when the <I>EppResponseDataCreate</I> object is created
	 *
         * @since EPP-1.0
	 */
	protected Calendar crDate;

	/**
	 * Gets ROID associated with the creation of an <code>EppObject</code>
	 *
	 * @deprecated for EPP-04 (06/29/2001)
	 */
	public String getRoid()
	{
		return this.roid;
	}

	/**
	 * Sets ROID associated with the creation of an <code>EppObject</code>
	 *
	 * @deprecated for EPP-04 (06/29/2001)
	 */
	public void setRoid( String roid )
	{
		this.roid = roid;
	}

	/**
	 * Gets creation date of the object created
	 *
         * @since EPP-1.0
	 */
	public Calendar getDateCreated()
	{
		return this.crDate;
	}

	/**
	 * Sets creation date of the object created
	 */
	public void setDateCreated( Calendar crDate )
	{
		this.crDate = crDate;
	}
}
