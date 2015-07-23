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
 * This <code>BizContact</code> class defines various constants and methods
 * related to EPP Contact objects associated with the BIZprotect product suite.
 * <P>
 * Please note the following rules regarding associating a contact object with
 * a BIZprotect service:
 * <P>
 * If a contact object is to be associated with a <code>BIZaccount</code>
 * or <code>BIZlock</code> service, it cannot be used as registrant or contacts
 * for a domain object.  
 * <P>
 * In addition, the <code>BIZ_USERID</code> field must be specified in the
 * &lt;unspec&gt; element when the contact object is created. A notification
 * via email or other channels will be sent out with the initial
 * <code>BIZ_PASSWORD</code> value. The fields <code>BIZ_USERID</code> and
 * <code>BIZ_PASSWORD</code> must be specified in the <unspec> element when
 * a contact object used for <code>BIZaccount</code> or <code>BIZlock</code>
 * is updated.
 * <P>
 * The following optional fields can also be supplied in the &lt;unspec&gt;
 * element in a create or update command:
 * <UL>
 *     <LI>BIZ_CONTACT_TITLE</LI>
 *     <LI>BIZ_CONTACT_PAGER</LI>
 *     <LI>BIZ_CONTACT_PHONE</LI>
 *     <LI>BIZ_CONTACT_PHONEEXT</LI>
 *     <LI>BIZ_CONTACT_EMAIL</LI>
 * </UL>
 * And the following optional fields can also be supplied in the &lt;unspec&gt;
 * element in an update command:
 * <UL>
 *     <LI>BIZ_NEWPASSWORD</LI>
 * </UL>
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class BizContact
{
	/**
	 * Owner user id to be supplied in various create or update commands
	 * related to contact objects, supplied in the &lt;unspec&gt; field
	 */
	public static final String BIZ_USERID = "UserId";
	/**
	 * Owner user password to be supplied in various update commands
	 * related to contact objects, supplied in the &lt;unspec&gt; field
	 */
	public static final String BIZ_PASSWORD = "Password";
	/**
	 * Owner user new password to be supplied in various update commands
	 * related to contact objects, supplied in the &lt;unspec&gt; field
	 */
	public static final String BIZ_NEWPASSWORD = "NewPassword";
	/**
	 * Title to be associated with a contact object
	 */
	public static final String BIZ_CONTACT_TITLE = "Title";
	/**
	 * Pager to be associated with a contact object
	 */
	public static final String BIZ_CONTACT_PAGER = "Pager";
	/**
	 * Secondary phone to be associated with a contact object. Its value
	 * follows the E164 format defined in the EPP specification.
	 */
	public static final String BIZ_CONTACT_PHONE = "SecondaryPhone";
	/**
	 * Secondary phone extenstion to be associated with a contact object.
	 */
	public static final String BIZ_CONTACT_PHONEEXT = "SecondaryPhoneExtension";
	/**
	 * Secondary email to be associated with a contact object
	 */
	public static final String BIZ_CONTACT_EMAIL = "SecondaryEmail";
}
