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
#include <iostream>
#include "XercesString.hpp"
#include <cstddef>
#include <xercesc/util/ValueVectorOf.hpp>
#include <xercesc/util/KeyValuePair.hpp>

using namespace std;

int testConstructors()
{
	cout<<"\n--------------CONSTRUCTOR TESTs--------------------";
	XercesString s1;
	if ( 0 == s1.size() )
	{
		cout<<"\nPASS: Default constructor. S1 size = "<< s1.size()<<" S1 is ="<<s1;
	}
	else
	{
		cout<<"\nFAIL Default constructor\n";
	}

	XercesString s2("santosh");
	if ( 7 == s2.size() )
	{
		cout<<"\nPASS: const char* constructor. S2 size = "<< s2.size()<<" S2 is ="<<s2;
	}
	else
	{
		cout<<"\nFAIL const char* constructor\n";
	}

	char* c1;
	c1 = new char[16];
	strcpy(c1, "kalsangrah");
	XercesString s3(c1);

	if ( 10 == s3.size() )
	{
		cout<<"\nPASS: char* constructor. S3 size = "<< s3.size()<<" S3 is = "<<s3;
	}
	else
	{
		cout<<"\nFAIL char* constructor\n";
	}

	XercesString s4(s3);
	if ( 10 == s4.size() )
	{
		cout<<"\nPASS: copy constructor. S4 size = "<< s4.size()<<" S4 is = "<<s4;
	}
	else
	{
		cout<<"\nFAIL copy constructor\n";
	}

#ifdef DIRECT_XMLCh_SUPPORT
	XMLCh* test_wstr = XMLString::transcode("hello, how are you?");

	XercesString s5(test_wstr);
	if ( 19 == s5.size() )
	{
		cout<<"\nPASS: XMLCh* constructor. S5 size = "<< s5.size()<<" S5 is = "<<s5;
	}
	else
	{
		cout<<"\nFAIL XMLCh* constructor\n";
	}

	const XMLCh* ctest_wstr = XMLString::transcode("I am good, how are you?");

	XercesString s6(ctest_wstr);
	if ( 23 == s6.size() )
	{
		cout<<"\nPASS: const XMLCh* constructor. S6 size = "<< s6.size()<<" S6 is = "<<s6;
	}
	else
	{
		cout<<"\nFAIL const XMLCh* constructor\n";
	}
#endif	

	return 0;
}

int testAppend()
{
	cout<<"\n--------------APPEND TESTs--------------------";

	XMLCh* test_wstr = XMLString::transcode("hello, how are you?");
	XercesString s7;
	s7.append(test_wstr);
	if ( 19 == s7.size() )
	{
		cout<<"\nPASS: append XMLCh*. S7 size = "<< s7.size()<<" S7 is = "<<s7;
	}
	else
	{
		cout<<"\nFAIL append XMLCh\n";
	}

	const XMLCh* ctest_wstr = XMLString::transcode("I am good, how are you?");
	XercesString s8("TESTING, ");
	s8.append(ctest_wstr);
	if ( 32 == s8.size() )
	{
		cout<<"\nPASS: append const XMLCh*. S8 size = "<< s8.size()<<" S8 is = "<<s8;
	}
	else
	{
		cout<<"\nFAIL append const XMLCh*\n";
	}


	s8.append(", HI THERE");
	if ( 42 == s8.size() )
	{
		cout<<"\nPASS: append char*. S8 size = "<< s8.size()<<" S8 is = "<<s8;
	}
	else
	{
		cout<<"\nFAIL append char*\n";
	}

	s8.append(s7);
	if ( 61 == s8.size() )
	{
		cout<<"\nPASS: append XercesString. S8 size = "<< s8.size()<<" S8 is = "<<s8;
	}
	else
	{
		cout<<"\nFAIL append XercesString\n";
	}
}

int testOperatorAssign()
{
	cout<<"\n--------------Assignment operator TESTs--------------------";

	XercesString s22 ("this string be replaced by NULL string");
	s22 = NULL;

	if ( s22.size() == 0 )
	{
		cout<<"\nPASS: NULL Assignment. S22 size = "<< s22.size()<<" S22 is = "<<s22;
	}
	else
	{
		cout<<"\nFAIL NULL assign\n";
	}

	XercesString s23("this string would go to next bject") ;
	XercesString s24 = s23;
	if ( s24.size() == s23.size() )
	{
		cout<<"\nPASS: Assignment. S23 size = "<< s23.size()<<" S24 size = "<<s24.size()<<" S23 is = "<<s23 <<" S24 is = "<<s24;
	}
	else
	{
		cout<<"\nFAIL XercesString assign\n";
	}
}

int testEqual()
{
	cout<<"\n--------------testEqual TESTs--------------------";
	XercesString s23("this string would go to next bject") ;
	if ( s23.equals("this string would go to next bject") )
	{
		cout<<"\nPASS: Equals 1. S23 size = "<< s23.size()<<" S23 is = "<<s23;
	}
	else
	{
		cout<<"\nFAIL equal char*\n";
	}

	XercesString s25 = s23;
	if ( s23.equals(s25) )
	{
		cout<<"\nPASS: Equals. S23 size = "<< s23.size()<<" S25 size = "<<s25.size()<<" S23 is = "<<s23<<" S25 is = "<<s25;
	}
	else
	{
		cout<<"\nFAIL equal XercesString\n";
	}

}

int testOperatorPlus()
{
	cout<<"\n--------------operator+ TESTs--------------------";

	XercesString one ("one");
	const char* two = " two";
	XercesString three (" three");

	XercesString oneTwoThreeFour = one + two + three + " four";

	if ( 18 == oneTwoThreeFour.size() )
	{
		cout<<"\nPASS: operator+. oneTwoThreeFour size = "<< oneTwoThreeFour.size()<<" S27 is = "<<oneTwoThreeFour;
	}
	else
	{
		cout<<"\nFAIL operator+ \n";
	}

}

int testOperatorPlusEqual()
{
	cout<<"\n--------------operator+ TESTs--------------------";

	XercesString s27;
	XercesString s26 ("test test test 3 times");
	s27 += s26;
	s27 += ".Now it end here.";
	if ( 39 == s27.size() )
	{
		cout<<"\nPASS: operator+=. S27 size = "<< s27.size()<<" S27 is = "<<s27;
	}
	else
	{
		cout<<"\nFAIL operator+=\n";
	}

}

int testOperatorIsEqual()
{
	cout<<"\n--------------operator== TESTs--------------------";
	XercesString one;
	if ( one == NULLXS )
	{
		cout<<"\nPASS: 'bool operator== . one size = "<< one.size()<< " one is = "<<one;
	}
	else
	{
		cout<<"\nFAIL operator== one\n";
	}


	XercesString s9("Hello");
	XercesString s10(s9);
	if ( s9 == s10 )
	{
		cout<<"\nPASS: 'bool operator== (const XercesString& str) const'. S9 size = "<< s9.size() <<" S10 size = "<< s10.size() <<" S9 is = "<<s9<<" S10 is = "<<s10;
	}
	else
	{
		cout<<"\nFAIL operator== (const XercesString& str) const\n";
	}

	const char* ss = "char* equal test";
	int len = strlen(ss);
	XercesString s12(ss);
	if ( s12 == ss )
	{
		cout<<"\nPASS: 'bool operator== (const char* str) const'. S12 size = "<< s12.size() <<" char* size = "<< len<<" S12 is = "<<s12<<". char* is = "<<ss;
	}
	else
	{
		cout<<"\nFAIL operator== (const char* str) const \n";
	}

	const XMLCh* xss = XMLString::transcode("XMLCh* equal test");
	char* css = XMLString::transcode(xss);
	int xlen = strlen(css);

	XercesString s13(css);
	if ( s13 == xss )
	{
		cout<<"\nPASS: 'operator== (const XMLCh* str) const'. S13 size = "<< s13.size() <<" XMLCh* size = "<< xlen<<" S13 is = "<<s13<<" XMLCh* is = "<<css;
	}
	else
	{
		cout<<"\nFAIL operator== (const XMLCh* str) const \n";
	}

}

int testOperatorIsNotEqual()
{
	cout<<"\n--------------operator!= TESTs--------------------";

	XercesString one("one");
	if ( one != NULLXS ) //isNull() is better choice for NULL check.
	{
		cout<<"\nPASS: 'bool operator!= . one size = "<< one.size()<< " one is = "<<one;
	}
	else
	{
		cout<<"\nFAIL operator!= one\n";
	}


	XercesString s9("begin test");
	XercesString s11("BEGIN TEST");

	if ( s9 != s11 )
	{
		cout<<"\nPASS: NULL 'operator!= (const XercesString& str'. S9 size = "<< s9.size() <<" S11 size = "<< s11.size()<<" S9 is = "<<s9<<" S11 is = "<<s11;
	}
	XercesString s12;
	if ( s9 != s12 )
	{
		cout<<"\nPASS: 'operator!= (const XercesString& str'. S9 size = "<< s9.size() <<" S11 size = "<< s12.size()<<" S9 is = "<<s9<<" S12 is = "<<s12;
	}
	else
	{
		cout<<"\nFAIL empty string : operator!= (const XercesString& str\n";
	}

	const char* ss = "char* equal test";
	if ( s9 != ss )
	{
		cout<<"\nPASS: 'bool operator== (const char* str) const'. S9 size = "<< s9.size() <<" char* size = "<< strlen(ss) <<" S9 is = "<<s9<<" char* is = "<<ss;
	}
	else
	{
		cout<<"\nFAIL ator== (const char* str) const\n";
	}

	const XMLCh* xxss = XMLString::transcode("XMLCh* NOT equal test");
	char* xcss = XMLString::transcode(xxss);
	int xxlen = strlen(xcss);
	cout<<"\nPRINT XMLCh test, xxss = "<<xxss;
	XercesString s14("hello world");
	const XMLCh* xss = XMLString::transcode("XMLCh* not equal test");
	if ( s14 != xss )
	{
		cout<<"\nPASS: 'operator!= (const XMLCh* str)'. S13 size = "<< s14.size() <<" XMLCh* size = "<< xxlen<<" S14 is = "<<s14<<" XMLCh* is = "<<xcss;
	}
	else
	{
		cout<<"\nFAIL operator!= (const XMLCh* str)\n";
	}

}

int testOpeartorBracket()
{
	cout<<"\n--------------operator[] TESTs--------------------";

	XercesString s14("Hello world");
	XMLCh xh1 = s14[0];
	XercesString const s15("const hello world");
	XMLCh xh2 = s15[0];
	const XercesString s16("const const hello world");
	XMLCh xh3 = s16[0];

	XMLCh xh4 = xh1;
	XMLCh xh5 = xh2;
	XMLCh xh6 = xh3;
	XercesString s17(&xh4);
	XercesString s18(&xh5);
	XercesString s19(&xh6);
	cout<<"\nPASS: S17 size = "<< s17.size() <<" S18 size = "<< s18.size() <<" S19 size = "<< s19.size();
	cout<<"\nS17=" << s17 << " S18 =" << s18 << " S19=" <<s19<< "\n";


}

int testBeginEnd()
{
	cout<<"\n--------------BEGIN..END TESTs--------------------";
	XercesString s9("begin test");

	//	const XMLCh* beg = s9.begin();
	//	XercesString s10(beg);
	//	if (	(10 == s9.size()) &&
	//		(10 == s10.size()) )
	//	{
	//		cout<<"\nPASS: begin. S9 size = "<< s9.size() <<" S10 size = "<< s10.size();
	//		cout<<"\nS9 is = "<<s9;
	//		cout<<"\nS10 is = "<<s10;
	//	}	
	//
	//
	//	const XMLCh* end = s9.end();
	//	XercesString s11(end);
	//	if (	(10 == s9.size()) &&
	//		(0 == s11.size()) )
	//	{
	//		cout<<"\nPASS: begin. S9 size = "<< s9.size() <<" S11 size = "<< s11.size();
	//		cout<<"\nS9 is = "<<s9;
	//		cout<<"\nS11 is = "<<s11;
	//	}	


}

int testMisc()
{

	cout<<"\n--------------NULL TESTs--------------------";

	XercesString s20("hello world");
	if ( !s20.isNull() )
	{
		cout<<"\nPASS: 'isNull '. S20 size = "<< s20.size()<<" S20 is = "<<s20;
	}
	else
	{
		cout<<"\nFAIL isNull size non zero\n";
	}

	XercesString s21;
	if ( s21.isNull() )
	{
		cout<<"\nPASS: 'isNull '. S21 size = "<< s21.size()<<" S21 is = "<<s21;
	}
	else
	{
		cout<<"\nFAIL isNull size 0\n";
	}

	cout<<"\n--------------substring TESTs--------------------";
	XercesString s23("subStringTest");
	XercesString s26 = s23.substringData(0, 10);

	if ( 10 == s26.size() )
	{
		cout<<"\nPASS: substring . S26 size = "<< s26.size()<<" S26 is = "<<s26;
	}
	else
	{
		cout<<"\nFAIL: substring . S26 size = "<< s26.size()<<" S26 is = "<<s26;
	}

	XercesString tTest("  two space before this and 3 spaces after this   ");
	cout<<"\n Orig:"<<tTest;
	int len = tTest.size();

	tTest.trim();
	int len2 = tTest.size();
	cout<<"\n Orig len="<<len<<" now len="<<len2;
	if ( (len == len2) || (len2 == 0) )
	{
		assert(1);
	}
	cout<<"\n Now:"<<tTest;
}

int testValueVectorOfAdd(ValueVectorOf<DOMString>* ns)
{
	DOMString one("one");
	DOMString two("two");
	DOMString three("three");
	DOMString four("four");

	ns->addElement(one);
	ns->addElement(two);
	ns->addElement(three);
	ns->addElement(four)	;
}

int testValueVectorOfGet(ValueVectorOf<DOMString>* ns)
{

	for ( int i = 0; i < ns->size(); i++ )
	{
		std::cout<<"\n SANTOSH: " << i << "= "<<ns->elementAt(i);
	}
	std::cout<<"\n";
}

int testValueVectorOf()
{
	ValueVectorOf<DOMString>* ns;
	ns = new ValueVectorOf<DOMString>(2);
	testValueVectorOfAdd(ns);
	testValueVectorOfGet(ns);
}

int main (int argc, char* argv[])
{
	XMLPlatformUtils::Initialize();  // Initialize Xerces infrastructure
	testValueVectorOf();
	return 0;

	/*
		ValueVectorOf<DOMStringPtrPair*> * hashMap;
		hashMap = new ValueVectorOf<DOMStringPtrPair*>(5);

		DOMString* ptr1 = new DOMString("name");
		DOMString* ptr2 = new DOMString("santosh");

		DOMStringPtrPair v = DOMStringPtrPair(ptr1, ptr2);
		hashMap->addElement(&v);

		ptr1 = new DOMString("lastname");
		ptr2 = new DOMString("kalsangrah");
		DOMStringPtrPair r = DOMStringPtrPair(ptr1, ptr2);
		hashMap->addElement(&r);

		ptr1 = new DOMString("city");
		ptr2 = new DOMString("");
		DOMStringPtrPair r1 = DOMStringPtrPair(ptr1, ptr2);
		hashMap->addElement(&r1);

		DOMString key("city");

		if( hashMap != NULL)
		{
			for( unsigned int i = 0; i < hashMap->size(); i++ )
			{
				DOMStringPtrPair* v = hashMap->elementAt(i);
				if( key.equals(*(v->getKey())) )
				{
				
					cout<<"\n "<<i<<" "<<*v->getKey()<<"="<<*(v->getValue());
					DOMString value=*v->getValue();
				}
			}
		}
		cout<<"\n";
		if( hashMap != NULL)
		{
			for( unsigned int i = 0; i < hashMap->size(); i++ )
			{
				DOMStringPtrPair* v = hashMap->elementAt(i);
				DOMString* f = v->getKey();
				if (f)
					delete f;
				DOMString* s = v->getValue();
				if (s)
					delete s;
			}
			delete hashMap;
		}

		//return 0;
	 */
	testConstructors();
	testAppend();
	testOperatorAssign();
	testEqual();
	testOperatorPlus();
	testOperatorPlusEqual();

	testOperatorIsEqual();
	testOperatorIsNotEqual();

	testOpeartorBracket();
	testBeginEnd();

	testMisc();

	cout<<"\n--------------Ends TEST here--------------------\n";
	cout<<"\n";

	return 0;
}
