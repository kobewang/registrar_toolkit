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
import org.w3c.dom.*;
import org.apache.xerces.dom.*;

/**
 * This <code>EppResponseDataRenew</code> class implements EPP
 * Response Data entity for EPP Command Renew on EPP objects.
 *
 * @since EPP-1.0
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
abstract public class EppResponseDataRenew extends EppResponseData
{
	/**
         * The new expiration date of the object after it is renewed
	 */
	protected Calendar exDate;

	/**
	 * Gets new expiration date of the object renewed
	 */
	public Calendar getDateExpired()
	{
		return this.exDate;
	}

	/**
	 * Sets expiration date of the domain renewed
	 */
	public void setDateExpired( Calendar exDate )
	{
		this.exDate = exDate;
	}
}
