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

#if        ! defined(USREGISTRANT_HPP)    /* { */
#define              USREGISTRANT_HPP        1

#if ! defined(EPP_EXPORT)    /* { */
#define       EPP_EXPORT
#endif     /* EPP_EXPORT) */ /* } */

/**
 * This <code>UsRegistrant</code> class defines various constants and methods
 * related to EPP Contact objects associated with dotUS domain names as
 * registrants. The Nexus information are described in <A
 * HREF="http://www.nic.us/policies/docs/ustld_nexus_requirements.pdf">
 * "The usTLD Nexus Requirements"</A>. In addition, <A
 * HREF="http://epp-ver-04.sourceforge.net/IETF/EPP-04/draft-liu-epp-ustld-00.txt">
 * "New EPP Parameters for the usTLD"</A> can be referred for details of
 * operations, restrictions and examples, although the XML samples are based
 * on EPP-06.
 * <P>
 * For a full list of two-letter country codes ("CC"), defined in ISO 3166,
 * the International Country Code Standard, please refer either the
 * <A HREF="http://www.iana.org/cctld/cctld.htm">IANA ccTLD Database</A> or
 * current <A HREF="http://www.din.de/gremien/nas/nabd/iso3166ma/index.html">ISO
 * 3166 Maintenance Agency</A> web site.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $
 */
class EPP_EXPORT UsRegistrant
{
public:
	/**
	 * The application purpose ("AppPurpose") parameter to be specified
	 * in the &lt;unspec&gt; element in a &lt;contact:create&gt; or
	 * &lt;contact:update&gt; command for a contact object used as
	 * the registrant of a dotUS domain name. It has the following possible
	 * string values:
	 * <P>
	 * <TABLE BORDER="1" CELLSPACEING="4" CELLPADDING="4">
	 * <TR><TD ALIGN="left" VALIGN="top"><B>P1</B></TD>
	 *     <TD ALIGN="left" VALIGN="top">Business use for profit</TD></TR>
	 * <TR><TD ALIGN="left" VALIGN="top"><B>P2</B></TD>
	 *     <TD ALIGN="left" VALIGN="top">Non-profit business, club,
	 *     association, religious orginization</TD></TR>
	 * <TR><TD ALIGN="left" VALIGN="top"><B>P3</B></TD>
	 *     <TD ALIGN="left" VALIGN="top">Personal use</TD></TR>
	 * <TR><TD ALIGN="left" VALIGN="top"><B>P4</B></TD>
	 *     <TD ALIGN="left" VALIGN="top">Educational purpose</TD></TR>
	 * <TR><TD ALIGN="left" VALIGN="top"><B>P5</B></TD>
	 *     <TD ALIGN="left" VALIGN="top">Government purpose</TD></TR>
	 * </TABLE>
	 */
	static const char * APPLICATION_PURPOSE;

	/**
	 * The nexus category ("NexusCategory") parameter to be specified
	 * in the &lt;unspec&gt; element in a &lt;contact:create&gt; or
	 * &lt;contact:update&gt; command for a contact object used as
	 * the registrant of a dotUS domain name. It has the following possible
	 * string values:
	 * <P>
	 * <TABLE BORDER="1" CELLSPACEING="4" CELLPADDING="4">
	 * <TR><TD ALIGN="left" VALIGN="top"><B>C11</B></TD>
	 *     <TD ALIGN="left" VALIGN="top" ROWSPAN="2"><NOBR>Nexus Category 1
	 *     </NOBR></TD>
	 *     <TD ALIGN="left" VALIGN="top">A natual person who is a US citizen
	 *     </TD></TR>
	 * <TR><TD ALIGN="left" VALIGN="top"><B>C12</B></TD>
	 *     <TD ALIGN="left" VALIGN="top">A natual person who is a permanent
	 *      resident</TD></TR>
	 * <TR><TD ALIGN="left" VALIGN="top"><B>C21</B></TD>
	 *     <TD ALIGN="left" VALIGN="top">Nexus Category 2</TD>
	 *     <TD ALIGN="left" VALIGN="top">An entity or organization that is
	 *     (i) incorporated within one of the fifty US states, the District
	 *     of Columbia, or any of the US possessions or territories, or
	 *     (ii) organized or otherwise constituted under the laws of a
	 *     state of the US, the District of Columbia or any of its
	 *     possessions and territories (including federal, state, or local
	 *     government of the US, or a political subdivision thereof, and
	 *     non-commercial organizations based in the US.)</TD></TR>
	 * <TR><TD ALIGN="left" VALIGN="top"><B>C31/CC</B></TD>
	 *     <TD ALIGN="left" VALIGN="top" ROWSPAN="2">Nexus Category 3</TD>
	 *     <TD ALIGN="left" VALIGN="top">A foreign organization that
	 *     regularly engages in lawful activities (sales of goods or
	 *     services or other business, commercial, or non-commercial,
	 *     including not for profit relations) in the United States. The
	 *     <I>CC</I> equals to the two-letter country code of the
	 *     organization.</TD></TR>
	 * <TR><TD ALIGN="left" VALIGN="top"><B>C32/CC</B></TD>
	 *     <TD ALIGN="left" VALIGN="top">An organization has an office or
	 *     other facility in the U.S., where <I>CC</I> equals to the
	 *     two-letter country code of the organization.</TD></TR>
	 * </TABLE>
	 */
	static const char * NEXUS_CATEGORY;
};

#endif  /* ! defined(USREGISTRANT_HPP) */ /* } */
