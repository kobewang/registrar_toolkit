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

#ifndef EPPGENERICNVPAIRS_HPP
#define EPPGENERICNVPAIRS_HPP
#include "EppEntity.hpp"
#include "EppObject.hpp"

struct pair_t
{
	DOMString name;
	DOMString value;
};

class EPP_EXPORT EppGenericNVPairs
{
public:

	EppGenericNVPairs()
	{
		vas = new OldValueVectorOf<pair_t>(3) ;
	}

	EppGenericNVPairs(DOMString _name, DOMString _value)
	{
		vas = new OldValueVectorOf<pair_t>(3);
		this->addGenericNVPair(_name, _value);
	}

	~EppGenericNVPairs()
	{
		if ( vas != null )
		{
			delete vas ;
			vas = null;
		}
	};

	EppGenericNVPairs( EppGenericNVPairs & pairs)
	{
		vas = new OldValueVectorOf<pair_t>(3);
		OldValueVectorOf<pair_t> &  allTuples = pairs.getGenericNVPairs();
		int sizeof_vec = allTuples.size();

		for( int i = 0;i<sizeof_vec;i++ )
		{
			pair_t v = allTuples.elementAt(i);
			addGenericNVPair(v.name, v.value);
		}

	}

	EppGenericNVPairs & operator=(EppGenericNVPairs & rhs)
	{
		if( this != &rhs )
		{
			delete vas;
			vas = new OldValueVectorOf<pair_t>(3);
			OldValueVectorOf<pair_t> &  allTuples = rhs.getGenericNVPairs();
			int sizeof_vec = allTuples.size();
			for( int i = 0;i<sizeof_vec;i++ )
			{
				pair_t v = allTuples.elementAt(i);
				addGenericNVPair(v.name, v.value);
			}
		}
		return *this;
	}

	void addGenericNVPair(DOMString _name, DOMString _value)
	{
		pair_t v;
		v.name = _name;
		v.value = _value;
		vas->addElement(v);
	}

	OldValueVectorOf<pair_t> & getGenericNVPairs()
	{
		return *(this->vas);
	}
	DOMElement* toXML( DOMDocument& doc, const DOMString& tag );
	static EppGenericNVPairs* fromXML( const DOMNode& root );

	DOMString toString()
	{
		return  "genericNVPairs";
	}

	int howMany()
	{
		if( vas == null )
		{
			return 0;
		}
		return vas->size();

	}
private:
	OldValueVectorOf<pair_t> * vas;
};
#endif
