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

#if ! defined(EPPCREDS_HPP)    /* { */
#define       EPPCREDS_HPP        1

#include "EppEntity.hpp"
#include "EppCredsOptions.hpp"

/**
 * This <code>EppCreds</code> class implements EPP credsType entity.
 *
 * <P><B>Warning</B>: This class is for backward compatibility. It will be
 * removed in the future version.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
class EPP_EXPORT EppCreds : public EppEntity
{
private:
	DOMString         clID;
	DOMString         pw;
	DOMString         newPW;
	EppCredsOptions * options;

public:

	/**
	 * Creates an <code>EppCreds</code> object
	 */
	EppCreds()
	{
		this->clID = NULLXS;
		this->pw = NULLXS;
		this->newPW = NULLXS;
		this->options = null;
	};

	/**
	 * Creates an <code>EppCreds</code> object, given the registrar client
	 * id and the password
	 *
	 * @param clientId client/registrar id
	 * @param password the password for the client/registrar
	 */
	EppCreds( DOMString clientId, DOMString password )
	{
		this->clID = clientId;
		this->pw = password;
		this->newPW = NULLXS;
		this->options = new EppCredsOptions("1.0", "en-US");
	};

	/**
	 * Creates an <code>EppCreds</code> object, given the registrar client
	 * id, the current password, and the new password
	 *
	 * @param clientId registrar client id
	 * @param password the current password for the registrar client
	 * @param newPassword the new password replacing the current password
	 */
	EppCreds( DOMString clientId, DOMString password, DOMString newPassword )
	{
		this->clID = clientId;
		this->pw = password;
		this->newPW = newPassword;
		this->options = new EppCredsOptions("1.0", "en-US");
	};

	/**
	 * Destructor
	 */
	~EppCreds()
	{
		if( this->options != null )
		{
			delete this->options;
			this->options = null;
		}
	};

	/**
	 * Gets registrar client id
	 */
	DOMString getClientId()
	{
		return this->clID;
	};

	/**
	 * Sets registrar client id
	 */
	void setClientId( DOMString clientId )
	{
		this->clID = clientId;
	};

	/**
	 * Gets registrar client's password
	 */
	DOMString getPassword()
	{
		return this->pw;
	};

	/**
	 * Sets registrar client's password
	 */
	void setPassword( DOMString password )
	{
		this->pw = password;
	};

	/**
	 * Gets registrar client's new password
	 */
	DOMString getNewPassword()
	{
		return this->newPW;
	};

	/**
	 * Sets registrar client's new password
	 */
	void setNewPassword( DOMString newPassword )
	{
		this->newPW = newPassword;
	};

	/**
	 * Gets credentials options
	 */
	EppCredsOptions * getOptions()
	{
		return this->options;
	};

	/**
	 * Sets credentials options
	 */
	void setOptions( EppCredsOptions options )
	{
		if( this->options == null )
		{
			this->options = new EppCredsOptions();
		}
		*(this->options) = options;
	};

	/**
	 * Converts the <code>EppCreds</code> object into an XML element
	 *
	 * @param doc the XML <code>DOMElement</code> object
	 * @param tag the tag/element name for the <code>EppCreds</code> object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );

	/**
	 * Converts an XML element into an <code>EppCreds</code> object. The
	 * caller of this method must make sure that the root node is of the
	 * EPP credsType.
	 *
	 * @param root root node for an <code>EppCreds</code> object in XML format
	 *
	 * @return an <code>EppCreds</code> object, or null if the node is invalid
	 */
	static EppCreds * fromXML( const DOMNode& root );

	/**
	 * Converts the <code>EppCreds</code> object into plain XML text string
	 * by using the default root tag name
	 *
	 * @return a text string representing the <code>EppCreds</code> object
	 *         in XML format
	 */
	DOMString toString()
	{
		return EppEntity::toString(DOMString("creds"));
	};
};

#endif     /* EPPCREDS_HPP */  /* } */
