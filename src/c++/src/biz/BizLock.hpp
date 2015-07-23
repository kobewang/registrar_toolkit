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
#if        ! defined(BIZPROTECT_HPP)    /* { */
#define              BIZPROTECT_HPP        1

#if ! defined(EPP_EXPORT)    /* { */
#define       EPP_EXPORT
#endif     /* EPP_EXPORT) */ /* } */

/**
 * This <code>BizLock</code> class defines various constants and methods related
 * to the BIZlock service.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $
 */
class EPP_EXPORT BizLock
{
public:
	/**
	 * Service ID for BIZ lock
	 * @note this &lt;service&gt; element is REQUIRED when the object is
	 *       created and cannot be changed via the &lt;update&gt; command.
	 *       It can have only one instance
	 */
	static const char * ID;
	/**
	 * Service parameter for the domain name to be locked
	 *
	 * @note this &lt;param&gt; element is REQUIRED when the object is created
	 *       and cannot be changed via the &lt;update&gt; command. It can have
	 *       only one instance. The domain must be already registered in
	 *       the registry
	 */
	static const char * PARAM_DOMAIN;
	/**
	 * Service parameter for the authinfo (password) associated
	 * with the domain name to be locked.
	 *
	 * @note this &lt;param&gt; element is REQUIRED when the object is created
	 *       and can have only one instance. It SHOULD not be returned
	 *       in the response of an &lt;info&gt; command. The domain must be
	 *       already registered in the registry
	 */
	static const char * PARAM_DOMAIN_AUTHINFO;
	/**
	 * Service parameter for the BIZaccount to be associated with
	 *
	 * @note this &lt;param&gt; element is REQUIRED when the object is created
	 *       and cannot be changed via the &lt;update&gt; command. It can have
	 *       only one instance
	 */
	static const char * PARAM_BIZACCOUNT;
	/**
	 * Service parameter for the UserID associated with registrant
	 *
	 * @note this &lt;param&gt; element is REQUIRED when the object is created
	 *       and can have only one instance. It SHOULD not be returned
	 *       in the response of an &lt;info&gt; command.
	 */
	static const char * PARAM_USERID;
	/**
	 * Service parameter for the password associated with registrant
	 *
	 * @note this &lt;param&gt; element is REQUIRED when the object is created
	 *       and can have only one instance. It SHOULD not be returned
	 *       in the response of an &lt;info&gt; command.
	 */
	static const char * PARAM_PASSWORD;
	/**
	 * Service parameter for the BIZlock type. It can be two values:
	 * <UL>
	 *     <LI>soft</LI>
	 *     <LI>hard</LI>
	 * </UL>
	 *
	 * @note this &lt;param&gt; element is REQUIRED when the object is created
	 *       and cannot be changed via the &lt;update&gt; command. It can have
	 *       only one instance
	 */
	static const char * PARAM_LOCKTYPE;
	/**
	 * Service parameter value for the BIZlock type: <I>soft</I>,
	 * i.e. the BIZlock Select&153; service.
	 */
	static const char * VALUE_LOCKTYPE_SOFT;
	/**
	 * Service parameter value for the BIZlock type: <I>hard</I>,
	 * i.e. the BIZlock Basic&153; service.
	 */
	static const char * VALUE_LOCKTYPE_HARD;
	/**
	 * Service parameter for the state of the lock. It can be two values:
	 * <UL>
	 *     <LI>on</LI>
	 *     <LI>off</LI>
	 * </UL>
	 *
	 * @note this &lt;param&gt; element is REQUIRED when the object is created
	 *       and CAN BE changed via the &lt;update&gt; command. It can have
	 *       only one instance
	 */
	static const char * PARAM_LOCKSTATE;
	/**
	 * Service parameter value for the state of the lock: on
	 */
	static const char * VALUE_LOCKSTATE_ON;
	/**
	 * Service parameter value for the state of the lock: off
	 */
	static const char * VALUE_LOCKSTATE_OFF;
};

#endif  /* ! defined(BIZPROTECT_HPP) */ /* } */
