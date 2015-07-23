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
#include "EppResponseDataCheckClaims.hpp"
#include "EppUtil.hpp"

const char * EppResponseDataCheckClaims::UNKNOWN   = " ";
const char * EppResponseDataCheckClaims::FOUND     = "1";
const char * EppResponseDataCheckClaims::NOT_FOUND = "0";

EppResponseDataCheckClaims::EppResponseDataCheckClaims() : _phase()
{
	hashMap         = new ValueVectorOf<DOMStringPtrPair*>(MAX_NUM_OF_CLAIM_CHECKS);
	claimKeyHashMap = new ValueVectorOf<DOMStringPtrPair*>(MAX_NUM_OF_CLAIM_CHECKS);
}

DOMString EppResponseDataCheckClaims::check( DOMString id )
{
	if( this->hashMap != null )
	{
		for( unsigned int i = 0; i < this->hashMap->size(); i++ )
		{
			DOMStringPtrPair* v = this->hashMap->elementAt(i);
			if( id.equals(*v->getKey()) )
			{
				return *v->getValue();
			}
		}
	}
	return DOMString(EppResponseDataCheckClaims::UNKNOWN);
}

bool EppResponseDataCheckClaims::isAvailable( DOMString id )
{
	if( this->hashMap != null )
	{
		for( unsigned int i = 0; i < this->hashMap->size(); i++ )
		{
			DOMStringPtrPair* v = this->hashMap->elementAt(i);
			if( id.equals(*v->getKey()) )
			{
				DOMString* t = v->getValue();
				if( (t->isNotNull()) && t->equals(EppResponseDataCheckClaims::FOUND) )
				{
					return false;
				}
				return true;
			}
		}
	}
	return true;
}

DOMString EppResponseDataCheckClaims::getClaimKey( DOMString id )
{
	if( this->claimKeyHashMap != null )
	{
		for( unsigned int i = 0; i < this->claimKeyHashMap->size(); i++ )
		{
			DOMStringPtrPair* v = this->claimKeyHashMap->elementAt(i);
			if( id.equals(*v->getKey()) )
			{
				return *v->getValue();
			}
		}
	}
	return NULLXS;
}

DOMElement* EppResponseDataCheckClaims::toXML( DOMDocument& doc, const DOMString& tag )
{
	DOMElement* elm;
	DOMElement* cd;
	DOMElement* body;

	body = EppUtil::createElementNS(doc, "launch", (tag.isNull()) ? "chkData" : tag, false, "-1.0");

	if( (_phase.phase().length() > 0) )
	{
		DOMElement* elm = this->_phase.toXML(doc, "phase");
		body->appendChild(elm);
	}
	if( this->hashMap != null )
	{
		for( unsigned int i = 0; i < this->hashMap->size(); i++ )
		{
			DOMStringPtrPair* v = this->hashMap->elementAt(i);
			DOMString* key = v->getKey();

			if( key->isNotNull() && key->length() > 0 )
			{
				cd  = doc.createElement(XS("cd"));
				elm = doc.createElement(XS("name"));

				elm->appendChild(doc.createTextNode(*key));
				DOMString* t = v->getValue();

				if( (t->isNotNull()) && t->equals(EppResponseDataCheckClaims::FOUND) )
				{
					elm->setAttribute(XS("exists"), XS("0"));
				}
				else
				{
					elm->setAttribute(XS("exists"), XS("1"));
				}
				cd->appendChild(elm);
				DOMString id = this->getClaimKey(*key);
				if( id.isNotNull() && id.length() > 0 )
				{
					elm = doc.createElement(XS("claimKey"));
					elm->appendChild(doc.createTextNode(id));
					cd->appendChild(elm);
				}
				body->appendChild(cd);
			}
		}
	}
	return body;
}

EppResponseDataCheckClaims *  EppResponseDataCheckClaims::fromXML( const DOMNode& root)
{
	EppResponseDataCheckClaims * chkClaims = new EppResponseDataCheckClaims();

	DOMNodeList* list = root.getChildNodes();
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
		if ( (name.equals("phase")) || (name.equals("launch:phase")) )
		{
			EppLaunchPhase *_pptr = EppLaunchPhase::fromXML(*node);
			if ( null != _pptr )
			{
				chkClaims->_phase = *_pptr;
				delete _pptr;
			}
			_pptr = null;
		}
		else if(    name.equals("cd") || name.equals("launch:cd") )
		{
			DOMNodeList* clist = node->getChildNodes();
			if( clist == null )
			{
				continue;
			}
			DOMString cd;
			DOMString id;
			bool flag = true;
			DOMString claimKey;

			for( unsigned j = 0; j < clist->getLength(); j++ )
			{
				DOMNode* cnode = clist->item(j);
				DOMString cname = cnode->getLocalName();

				if( cname.isNull() )
				{
					cname = cnode->getNodeName();
				}
				if( cname.isNull() )
				{
					continue;
				}
				if( cname.equals("name") || cname.equals("launch:name") )
				{
					id = EppUtil::getText(*cnode);
					cd = ((DOMElement *) cnode)->getAttribute(XS("exists"));
					if(    (cd.isNotNull() )
						&& (    cd.equals("0")
						|| cd.equals("f")
						|| cd.equals("false") ) )
					{
						flag = false;
					}
					else
					{
						flag = true;
					}
				}
				else if( cname.equals("claimKey") || cname.equals("launch:claimKey") )
				{
					claimKey = EppUtil::getText(*cnode);
				}
			}

			if( id.isNotNull() && id.length() > 0 )
			{
				chkClaims->add(id, flag, claimKey);
			}
		}
	}
	return chkClaims;
}

DOMString EppResponseDataCheckClaims::toString()
{
	return EppEntity::toString(DOMString("launch:chkData"));
}

void EppResponseDataCheckClaims::phase(const EppLaunchPhase & s)
{
	_phase = s;
}

EppLaunchPhase EppResponseDataCheckClaims::phase()
{
	return _phase;
}

EppResponseDataCheckClaims::~EppResponseDataCheckClaims()
{

	if( this->hashMap != null )
	{
		for( unsigned int i = 0; i < this->hashMap->size(); i++ )
		{
			DOMStringPtrPair* v = this->hashMap->elementAt(i);
			DOMString* key = v->getKey();
			DOMString* value = v->getValue();
			if ( key )
			{
				delete key;
			}
			if ( value )
			{
				delete value;
			}
			delete v;
		}

		hashMap->removeAllElements();
		delete hashMap;
		hashMap = null;
	}

	if( this->claimKeyHashMap != null )
	{
		for( unsigned int i = 0; i < this->claimKeyHashMap->size(); i++ )
		{
			DOMStringPtrPair* v = this->claimKeyHashMap->elementAt(i);
			DOMString* key = v->getKey();
			DOMString* value = v->getValue();
			if ( key )
			{
				delete key;
			}
			if ( value )
			{
				delete value;
			}
			delete v;
		}

		claimKeyHashMap->removeAllElements();
		delete claimKeyHashMap;
		claimKeyHashMap = null;
	}
};

EppResponseDataCheckClaims::EppResponseDataCheckClaims(const EppResponseDataCheckClaims& src)
{
	_phase          = src._phase;
	hashMap         = src.hashMap;
	claimKeyHashMap = src.claimKeyHashMap;
}

EppResponseDataCheckClaims& EppResponseDataCheckClaims::operator=(const EppResponseDataCheckClaims& src)
{
	if ( this == &src )
	{
		return *this;
	}
	_phase          = src._phase;
	hashMap         = src.hashMap;
	claimKeyHashMap = src.claimKeyHashMap;
	return *this;
}
