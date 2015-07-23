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
 * This <code>BizLock</code> class defines various constants and methods related
 * to the BIZlock service.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class BizLock
{
	/**
	 * Service ID for BIZ lock.
	 *
	 * <P><B>Note</B>: this &lt;service&gt; element is REQUIRED when the object is
	 *       created and cannot be changed via the &lt;update&gt; command.
	 *       It can have only one instance
	 */
	public static final String ID = "BIZlock";
	/**
	 * Service parameter for the domain name to be locked.
	 *
	 * <P><B>Note</B>: this &lt;param&gt; element is REQUIRED when the object is created
	 *       and cannot be changed via the &lt;update&gt; command. It can have
	 *       only one instance. The domain must be already registered in
	 *       the registry
	 */
	public static final String PARAM_DOMAIN = "domain";
	/**
	 * Service parameter for the authinfo (password) associated
	 * with the domain name to be locked.
	 *
	 * <P><B>Note</B>: this &lt;param&gt; element is REQUIRED when the object is created
	 *       and can have only one instance. It SHOULD not be returned
	 *       in the response of an &lt;info&gt; command. The domain must be
	 *       already registered in the registry
	 */
	public static final String PARAM_DOMAIN_AUTHINFO = "domainAuthInfo";
	/**
	 * Service parameter for the BIZaccount to be associated with.
	 *
	 * <P><B>Note</B>: this &lt;param&gt; element is REQUIRED when the object is created
	 *       and cannot be changed via the &lt;update&gt; command. It can have
	 *       only one instance
	 */
	public static final String PARAM_BIZACCOUNT = "bizAccount";
	/**
	 * Service parameter for the UserID associated with registrant.
	 *
	 * <P><B>Note</B>: this &lt;param&gt; element is REQUIRED when the object is created
	 *       and can have only one instance. It SHOULD not be returned
	 *       in the response of an &lt;info&gt; command.
	 */
	public static final String PARAM_USERID = "userid";
	/**
	 * Service parameter for the password associated with registrant.
	 *
	 * <P><B>Note</B>: this &lt;param&gt; element is REQUIRED when the object is created
	 *       and can have only one instance. It SHOULD not be returned
	 *       in the response of an &lt;info&gt; command.
	 */
	public static final String PARAM_PASSWORD = "password";
	/**
	 * Service parameter for the BIZlock type.

	 * <P>It can be one of the following two values:
	 * <UL>
	 *     <LI>soft</LI>
	 *     <LI>hard</LI>
	 * </UL>
	 *
	 * <P><B>Note</B>: this &lt;param&gt; element is REQUIRED when the object is created
	 *       and cannot be changed via the &lt;update&gt; command. It can have
	 *       only one instance
	 */
	public static final String PARAM_LOCKTYPE = "lockType";
	/**
	 * Service parameter value for the BIZlock type: <I>soft</I>,
	 * the BIZlock Select&153; service.
	 */
	public static final String VALUE_LOCKTYPE_SOFTLOCK = "soft";
	/**
	 * Service parameter value for the BIZlock type: <I>hard</I>,
	 * the BIZlock Basic&153; service.
	 */
	public static final String VALUE_LOCKTYPE_HARDLOCK = "hard";
	/**
	 * Service parameter for the state of the lock. It can be two values:
	 * <UL>
	 *     <LI>on</LI>
	 *     <LI>off</LI>
	 * </UL>
	 *
	 * <P><B>Note</B>: this &lt;param&gt; element is REQUIRED when the object is created
	 *       and CAN BE changed via the &lt;update&gt; command. It can have
	 *       only one instance
	 */
	public static final String PARAM_LOCKSTATE = "lockState";
	/**
	 * Service parameter value for the state of the lock: on
	 */
	public static final String VALUE_LOCKSTATE_ON = "on";
	/**
	 * Service parameter value for the state of the lock: off
	 */
	public static final String VALUE_LOCKSTATE_OFF = "off";
}
