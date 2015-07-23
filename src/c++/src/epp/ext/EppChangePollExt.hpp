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

#if ! defined(EPPCHANGEPOLLEXT_HPP)    
#define       EPPCHANGEPOLLEXT_HPP 1

#include "EppExtension.hpp"
#include "EppEntity.hpp"
#include "EppReason.hpp"
#include "EppChangePollOperation.hpp"

/**
 * This <code>EppChangePollExt</code> class implements EPP change pool extension elements.
 */
class EPP_EXPORT EppChangePollExt : public EppExtension
{
public:
	EppChangePollExt();
	virtual ~EppChangePollExt();

	void setState(const DOMString& st);
	DOMString getState() const;
	
	void setOperation(EppChangePollOperation*  op);
	EppChangePollOperation* getOperation() const;

	void setDate(time_t dt);
	time_t getDate() const;

	void setSvTRID(const DOMString& trid);
	DOMString getSvTRID() const;

	void setWho(const DOMString& wh);
	DOMString getWho() const;

	void setReason(EppReason* reas);
	EppReason* getReason() const;

	/**
	 * Converts the <code>EppChangePollExt</code> object into an XML element
	 *
	 * @param doc the XML <code>Document</code> object
	 * @param tag the tag/element name for the <code>EppChangePollExt</code> object
	 *
	 * @return an <code>DOMElement</code> object
	 */
	virtual DOMElement* toXML(DOMDocument &doc, const DOMString &tag);

	/**
	 * Converts an XML element into an <code>EppChangePollExt</code> object. 
	 *
	 * @param root root node for an <code>EppChangePollExt</code> object in XML format
	 *
	 * @return an <code>EppChangePollExt</code> object, or null if the node is invalid
	 */
	static EppChangePollExt * fromXML( const DOMNode& root );
	
	DOMString toString()
	{
		return EppEntity::toString(DOMString("changePoll"));
	}
	
	virtual int getEntityType()
	{
		return EppEntity::TYPE_EppChangePollExt;
	}

private:
	DOMString state;
	EppChangePollOperation* operation;
	time_t    date;
	DOMString svTRID;
	DOMString who;
	EppReason* reason;
};
#endif 
