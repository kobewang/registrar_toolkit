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
package com.neulevel.epp.xri;

import java.util.*;
import org.w3c.dom.*;
import com.neulevel.epp.core.EppEntity;
import com.neulevel.epp.core.EppObject;
import com.neulevel.epp.core.EppUtil;

public class EppXriCodeStringVASNVPair 
{
  /**
  * No argument constructor for creating an object of <code>EppXriCodeStringVASNVPair</code>
  * @param
  * @return
  **/

		public EppXriCodeStringVASNVPair()
		{
			name = new String();
			value = new String();
		}

  /**
  * Two argument constructor for creating an object of <code>EppXriCodeStringVASNVPair</code>
  * @param nm : Name
	* @param v  : Value
  * @return
  **/
		public EppXriCodeStringVASNVPair(String nm , String v)
		{
			name = new String(nm);
			value = new String(v);
		}
	/**
 	* Method to get name part of the NV pair
 	* @param none
	*	@return name
	*/

		public String getName()
		{
			return name;
		}

	/**
 	* Method to get value part of the NV pair
 	* @param none
	*	@return value
	*/
		public String getValue()
		{
			return value;
		}
	/**
 	* Method to set name part of the NV pair
 	* @param String nm
	*	@return void
	*/
		public void setName(String nm)
		{
			name = new String(nm);
		}
	/**
 	* Method to set value part of the NV pair
 	* @param String v
	*	@return void
	*/
		public void setValue(String v)
		{
			value = new String(v);
		}
    private String name;
    private String value;
}
