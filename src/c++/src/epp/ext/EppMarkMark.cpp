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
#include "EppMarkMark.hpp"
#include "EppTrademarkData.hpp"
#include "EppCourtData.hpp"
#include "EppTreatyOrStatuteData.hpp"
#include "EppUtil.hpp"

EppMarkMark::EppMarkMark(const char* ns):EppMarkNameSpace(ns), _marks(2)
{
}

EppMarkMark::EppMarkMark(const EppMarkMark& _src):_marks(2)
{
	for( unsigned int i = 0; i < _src._marks.size(); i++ )
	{
		EppMarkData *_ptr = _src._marks.elementAt(i);
		this->_marks.addElement(_ptr);
	}
}

EppMarkMark::~EppMarkMark()
{
	this->cleanup();
	this->_marks.removeAllElements();
}

void EppMarkMark::cleanup()
{
	for( unsigned int i = 0; i < this->_marks.size(); i++ )
	{
		EppMarkData *_ptr = this->_marks.elementAt(i);
		delete _ptr;
	}
}

EppMarkMark& EppMarkMark::operator=(const EppMarkMark& _src)
{
	for( unsigned int i = 0; i < _src._marks.size(); i++ )
	{
		EppMarkData *_ptr = _src._marks.elementAt(i);
		this->_marks.addElement(_ptr);
	}
	return *this;
}

void EppMarkMark::addMark(EppMarkData* _d)
{
	this->_marks.addElement(_d);
}

OldValueVectorOf<EppMarkData*> EppMarkMark::getMark(void)
{
	return _marks;
}

DOMElement* EppMarkMark::toXML(DOMDocument &doc, const DOMString &tag)
{
	DOMElement* body = EppUtil::createElementNS(doc, getNameSpace().c_str(), (tag.isNull()) ? "mark" : tag, false, "-1.0");
	for( unsigned int i = 0; i < this->_marks.size(); i++ )
	{
		EppMarkData *_ptr = this->_marks.elementAt(i);
		if( NULL != _ptr )
		{
			DOMElement* elm = _ptr->toXML(doc, NULLXS);
			body->appendChild(elm);
		}
	}
	return body;
}

EppMarkMark* EppMarkMark::fromXML( const DOMNode& root, const char* ns)
{
	EppMarkMark *_ret    = new EppMarkMark(ns);
	DOMNodeList* list    = root.getChildNodes();

	int nsLen      = strlen(ns);

	if( list == 0 )
	{
		delete _ret;
		return NULL;
	}

	for( unsigned int i = 0; i < list->getLength(); i++ )
	{
		DOMNode* node = list->item(i);
		DOMString name = node->getLocalName();

		if( name.isNull() )
		{
			name = node->getNodeName();
		}

		if( name.isNull() )
		{
			continue;
		}
		if( name.substringData(0, nsLen + 1).equals((_ret->getNameSpace() + ":").c_str()) )
		{
			name = name.substringData(nsLen + 1, name.length() - ( nsLen + 1 ));
		}
		if( name.equals("trademark") )
		{
			EppTrademarkData *_tmd = EppTrademarkData::fromXML(*node, ns);
			if( NULL != _tmd )
			{
				_ret->_marks.addElement(_tmd);
			}
		}
		else if ( name.equals("court") )
		{
			EppCourtData *_cd = EppCourtData::fromXML(*node, ns);
			if( NULL != _cd )
			{
				_ret->_marks.addElement(_cd);
			}
		}
		else if ( name.equals("treatyOrStatute") )
		{
			EppTreatyOrStatuteData *_tsd = EppTreatyOrStatuteData::fromXML(*node, ns);
			if( NULL != _tsd )
			{
				_ret->_marks.addElement(_tsd );
			}
		}

	}
	return _ret;
}

DOMString EppMarkMark::toString()
{
	std::string str = getNameSpace();
	str.append(":mark");
	return DOMString(str.c_str());
}
#if 0
private:
ValueVectorOf<EppMarkData*> _marks;
};

#endif
