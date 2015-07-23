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
package com.neulevel.biz;

/**
 * This <code>BizAccount</code> class defines various constants and methods
 * related to the BIZaccount service.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class BizAccount
{
	/**
	 * Service ID for BIZ Account
	 */
	public static final String ID = "BIZaccount";
	/**
	 * Service parameter for distribution list, which is a list of
	 * email addresses.
	 *
	 * <P><B>Note</B>: This &lt;param&gt; can have zero or multiple instances. It can
	 *       be supplied via the &lt;create&gt; command and can be added
	 *       or removed via the &lt;update&gt; command
	 */
	public static final String PARAM_DISTLIST = "distList";
	/**
	 * Service parameter for the preferred language used in service
	 * notification.
	 *
	 * The value associated with this parameter is defined
	 * by the registry. The following is a list of languages supported:
	 *
	 * <P>
	 * <UL>
	 *     <LI>English</LI>
	 *     <LI>Chinese</LI>
	 *     <LI>French</LI>
	 *     <LI>German</LI>
	 *     <LI>Japanese</LI>
	 *     <LI>Korean</LI>
	 *     <LI>Spanish</LI>
	 *     <!--LI>Arabic</LI>
	 *     <LI>Danish</LI>
	 *     <LI>Italian</LI>
	 *     <LI>Norwegian</LI>
	 *     <LI>Portugese</LI-->
	 * </UL>
	 *
	 * <P><B>Note</B>: This &lt;param&gt; can have only one instance. It is required
	 *       in the &lt;create&gt; command and can be changed by removing
	 *       and adding this &lt;param&gt; in a single &lt;update&gt; command 
	 */
	public static final String PARAM_LANGPREF = "langPref";
	/**
	 * Service parameter for other services associated with the account.
	 *
	 * This parameter value is to be set by the registry server only
	 *
	 * <P><B>Note</B>: This &lt;param&gt; can have zero or multiple instances and can be
	 *       returned in the response of an &lt;info&gt; command
	 */
	public static final String PARAM_SVCLIST = "svcList";
}
