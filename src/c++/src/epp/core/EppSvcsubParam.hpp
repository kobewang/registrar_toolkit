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

#if ! defined(EPPSVCSUBPARAM_HPP)    /* { */
#define       EPPSVCSUBPARAM_HPP        1

#include "EppEntity.hpp"

/**
 * This <code>EppSvcsubParam</code> class implements EPP Svcsub Param entity.
 * Currently, only EPP Svcsub objects have Svcsub params defined:
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $
 */
class EPP_EXPORT EppSvcsubParam : public EppEntity
{
private:
	DOMString name;
	DOMString value;

public:

	/**
	 * Creates an <code>EppSvcsubParam</code> object
	 */
	EppSvcsubParam()
	{
		this->name  = NULLXS;
		this->value = NULLXS;
	};

	/**
	 * Creates an <code>EppSvcsubParam</code> object with the id of
	 * a svcsub name and a value string
	 */
	EppSvcsubParam( DOMString name, DOMString value )
	{
		this->name  = name;
		this->value = value;
	};

	/**
	 * Destructor
	 */
	~EppSvcsubParam() {};

	/**
	 * Gets the name of the parameter
	 */
	DOMString getName()
	{
		return this->name;
	};

	/**
	 * Sets the name of the parameter
	 */
	void setName( DOMString name )
	{
		this->name = name;
	};

	/**
	 * Gets the value of the parameter
	 */
	DOMString getValue()
	{
		return this->value;
	};

	/**
	 * Sets the value of the parameter
	 */
	void setValue( DOMString value )
	{
		this->value = value;
	};

	/**
	 * Gets a sub-list of parameter values, given a list of parameter values
	 * and a name string
	 *
	 * @param param a list of EppSvcsubParam objects
	 * @param name the name of parameters to be extracted from the list
	 *
	 * @return a list of parameter values, or null if not found
	 * @note the caller must free the returned list if not null
	 */
	static OldValueVectorOf<DOMString> * getParamValue( OldValueVectorOf<EppSvcsubParam> * param, DOMString name );

	/**
	 * Converts the <code>EppSvcsubParam</code> object into an XML element
	 *
	 * @param doc the XML <code>DOMElement</code> object
	 * @param tag the tag/element name for the <code>EppSvcsubParam</code>
	 *            object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );

	/**
	 * Converts an XML element into an <code>EppSvcsubParam</code> object.
	 * The caller of this method must make sure that the root node is of
	 * the EPP Svcsub Param type.
	 *
	 * @param root root node for an <code>EppSvcsubParam</code> object
	 *             in XML format
	 *
	 * @return an <code>EppSvcsubParam</code> object, or null if the node
	 *         is invalid
	 */
	static EppSvcsubParam * fromXML( const DOMNode& root );

	DOMString toString()
	{
		return EppEntity::toString(DOMString("param"));
	};
};

#endif     /* EPPSVCSUBPARAM_HPP */  /* } */
