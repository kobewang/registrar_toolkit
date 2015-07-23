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
package com.neulevel.epp.xri;

import java.util.*;
import org.w3c.dom.*;
import com.neulevel.epp.core.EppEntity;
import com.neulevel.epp.core.EppE164;
import com.neulevel.epp.core.EppContactData;
import com.neulevel.epp.core.EppUtil;

/**
 * This <code>EppXriSocialData</code> class defines social
 * information associated with XRI authority objects.  It
 * implements XRI socialDataType and chgSocialDataType defined
 * in the XRI authority schema file.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.3 $ $Date: 2010/08/20 21:33:25 $
 */
public class EppXriSocialData extends EppEntity
{
	private EppContactData postalInfo;
	private EppE164        voicePrimary;
	private EppE164        voiceSecondary;
	private boolean	       voiceNullified;
	private EppE164        fax;
	private boolean	       faxNullified;
	private EppE164        pager;
	private boolean	       pagerNullified;
	private String         emailPrimary;
	private String         emailSecondary;
	private boolean	       emailNullified;

	/**
	 * Creates an <code>EppXriSocialData</code> object
	 */
	public EppXriSocialData()
	{
		this.postalInfo     = null;
		this.voicePrimary   = null;
		this.voiceSecondary = null;
		this.fax            = null;
		this.pager          = null;
		this.emailPrimary   = null;
		this.emailSecondary = null;

		this.faxNullified   = false;
		this.pagerNullified = false;
		this.voiceNullified = false;
		this.emailNullified = false;
	}

	/**
	 * Sets up postal information of the XRI authority
	 */
	public void setPostalInfo( EppContactData postalInfo )
	{
		this.postalInfo = postalInfo;
	}

	/**
	 * Gets the postal information of the XRI authoruty
	 */
	public EppContactData getPostalInfo()
	{
		return this.postalInfo;
	}

	/**
	 * Returns true if voice numbers are to be nullified via an EPP update command
	 */
	public boolean isVoiceNullified()
	{
		return this.voiceNullified;
	}

	/**
	 * Gets the primary voice phone number
	 */
	public EppE164 getPrimaryVoice()
	{
		return this.voicePrimary;
	}

	/**
	 * Sets the primary voice phone number
	 */
	public void setPrimaryVoice( String voice )
	{
		this.voicePrimary = new EppE164(voice);
		this.voiceNullified = false;
	}

	/**
	 * Sets the primary voice phone number and extenstion
	 */
	public void setPrimaryVoice( String voice, String ext )
	{
		this.voicePrimary = new EppE164(voice, ext);
		this.voiceNullified = false;
	}

	/**
	 * Sets the primary voice phone number
	 */
	public void setPrimaryVoice( EppE164 voice )
	{
		this.voicePrimary = voice;
		this.voiceNullified = false;
	}

	/**
	 * Gets the secondary voice phone number
	 */
	public EppE164 getSecondaryVoice()
	{
		return this.voiceSecondary;
	}

	/**
	 * Sets the secondary voice phone number
	 */
	public void setSecondaryVoice( String voice )
	{
		this.voiceSecondary= new EppE164(voice);
		this.voiceNullified = false;
	}

	/**
	 * Sets the secondary voice phone number and extenstion
	 */
	public void setSecondaryVoice( String voice, String ext )
	{
		this.voiceSecondary= new EppE164(voice, ext);
		this.voiceNullified = false;
	}

	/**
	 * Sets the secondary voice phone number
	 */
	public void setSecondaryVoice( EppE164 voice )
	{
		this.voiceSecondary = voice;
		this.voiceNullified = false;
	}

	/**
	 * Nullifies the voice number via an EPP update command
	 */
	public void nullifyVoice()
	{
		this.voicePrimary = null;
		this.voiceSecondary = null;
		this.voiceNullified = true;
	}
	
	/**
	 * Returns true if fax is to be nullified via an EPP update command
	 */
	public boolean isFaxNullified()
	{
		return this.faxNullified;
	}

	/**
	 * Gets the fax number
	 */
	public EppE164 getFax()
	{
		return this.fax;
	}

	/**
	 * Sets the fax number
	 */
	public void setFax( String fax )
	{
		this.fax = new EppE164(fax);
		this.faxNullified = false;
	}

	/**
	 * Sets the fax number and extenstion
	 */
	public void setFax( String fax, String ext )
	{
		this.fax = new EppE164(fax, ext);
		this.faxNullified = false;
	}

	/**
	 * Sets the fax number
	 */
	public void setFax( EppE164 fax )
	{
		this.fax = fax;
		this.faxNullified = false;
	}

	/**
	 * Nullifies the fax number via an EPP update command
	 */
	public void nullifyFax()
	{
		this.fax = null;
		this.faxNullified = true;
	}

	/**
	 * Returns true if pager is to be nullified via an EPP update command
	 */
	public boolean isPagerNullified()
	{
		return this.pagerNullified;
	}

	/**
	 * Gets the pager number
	 */
	public EppE164 getPager()
	{
		return this.pager;
	}

	/**
	 * Sets the pager number
	 */
	public void setPager( String pager )
	{
		this.pager = new EppE164(pager);
		this.pagerNullified = false;
	}

	/**
	 * Sets the pager number and extenstion
	 */
	public void setPager( String pager, String ext )
	{
		this.pager = new EppE164(pager, ext);
		this.pagerNullified = false;
	}

	/**
	 * Sets the pager number
	 */
	public void setPager( EppE164 pager )
	{
		this.pager = pager;
		this.pagerNullified = false;
	}

	/**
	 * Nullifies the pager number via an EPP update command
	 */
	public void nullifyPager()
	{
		this.pager = null;
		this.pagerNullified = true;
	}

	/**
	 * Returns true if email addresses are to be nullified via an EPP update command
	 */
	public boolean isEmailNullified()
	{
		return this.emailNullified;
	}

	/**
	 * Gets the primary email address
	 */
	public String getPrimaryEmail()
	{
		return this.emailPrimary;
	}

	/**
	 * Sets the primary email address
	 */
	public void setPrimaryEmail( String email )
	{
		this.emailPrimary = email;
		this.emailNullified = false;
	}

	/**
	 * Gets the secondary email address
	 */
	public String getSecondaryEmail()
	{
		return this.emailSecondary;
	}

	/**
	 * Sets the secondary email address
	 */
	public void setSecondaryEmail( String email )
	{
		this.emailSecondary = email;
		this.emailNullified = false;
	}

	/**
	 * Nullifies the email addresses via an EPP update command
	 */
	public void nullifyEmail()
	{
		this.emailPrimary = null;
		this.emailSecondary = null;
		this.emailNullified = true;
	}

	/**
         * Converts the <code>EppXriSocialData</code> object into an XML element
         *
         * @param doc the XML <code>Document</code> object
         * @param tag the tag/element name for the <code>EppXriSocialData</code> object
         *
         * @return an <code>Element</code> object
         */
	public Element toXML( Document doc, String tag )
	{
		Element body = doc.createElement(tag);
		Element elm;
		if( this.postalInfo != null )
		{
			body.appendChild(this.postalInfo.toXML(doc, "postalInfo"));
		}
		if( this.voiceNullified == true )
		{
			elm = doc.createElement("voice");
			body.appendChild(elm);
		}
		else
		{
			if( this.voicePrimary != null )
			{
				body.appendChild(this.voicePrimary.toXML(doc, "voice"));
			}
			if( this.voiceSecondary != null )
			{
				body.appendChild(this.voiceSecondary.toXML(doc, "voice"));
			}
		}
		if( this.faxNullified == true )
		{
			elm = doc.createElement("fax");
			body.appendChild(elm);
		}
		else if( this.fax != null )
		{
			body.appendChild(this.fax.toXML(doc, "fax"));
		}
		if( this.pagerNullified == true )
		{
			elm = doc.createElement("pager");
			body.appendChild(elm);
		}
		else if( this.pager != null )
		{
			body.appendChild(this.pager.toXML(doc, "pager"));
		}
		if( this.emailNullified == true )
		{
			elm = doc.createElement("email");
			body.appendChild(elm);
		}
		else
		{
			if( this.emailPrimary != null )
			{
				elm = doc.createElement("email");
				elm.appendChild(doc.createTextNode(this.emailPrimary));
				body.appendChild(elm);
			}
			if( this.emailSecondary != null )
			{
				elm = doc.createElement("email");
				elm.appendChild(doc.createTextNode(this.emailSecondary));
				body.appendChild(elm);
			}
		}

		return body;
	}

	/**
	 * Converts an XML element into an <code>EppXriSocialData</code> object.
	 * The caller of this method must make sure that the root node is of
	 * the EPP XRI socialDataType or chgSocialDataType.
	 *
	 * @param root root node for an <code>EppXriSocialData</code> object in
	 *             XML format
	 *
	 * @return an <code>EppXriSocialData</code> object, or null if the node is
	 *         invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		EppXriSocialData data = null;
		int voiceCount = 0;
		int emailCount = 0;

		NodeList list = root.getChildNodes();
		for( int i = 0; i < list.getLength(); i++ )
		{
			Node node = list.item(i);
			String name = node.getLocalName();
			if( name == null )
			{
				continue;
			}
			if( name.equals("postalInfo") )
			{
				EppContactData postalInfo = (EppContactData) EppContactData.fromXML(node);
				if( postalInfo != null )
				{
					if( data == null )
					{
						data = new EppXriSocialData();
					}
					data.setPostalInfo(postalInfo);
				}
			}
			else if( name.equals("voice") && (voiceCount < 2) )
			{
				EppE164 voice = (EppE164) EppE164.fromXML(node);
				if( voice != null )
				{
					if( data == null )
					{
						data = new EppXriSocialData();
					}
					if((voice.getNumber() != null) && (voice.getNumber().length() > 0))
					{
						if( voiceCount == 0 )
						{
							data.setPrimaryVoice(voice);
						}
						else if( voiceCount == 1 )
						{
							data.setSecondaryVoice(voice);
						}
					}
					voiceCount++;
					if(    ((voice.getExtension() == null) || (voice.getExtension().length() == 0))
					    && ((voice.getNumber()    == null) || (voice.getNumber().length()    == 0)) )
					{
						//data.nullifyVoice();
					}
				}
			}
			else if( name.equals("fax") )
			{
				EppE164 fax = (EppE164) EppE164.fromXML(node);
				if( fax != null )
				{
					if( data == null )
					{
						data = new EppXriSocialData();
					}
					if( ((fax.getNumber()    == null) || (fax.getNumber().length()    == 0)) )
					{
						data.nullifyFax();
					}
					else
					{
						data.setFax(fax);
					}
				}
			}
			else if( name.equals("pager") )
			{
				EppE164 pager = (EppE164) EppE164.fromXML(node);
				if( pager != null )
				{
					if( data == null )
					{
						data = new EppXriSocialData();
					}
					if(  ((pager.getNumber()    == null) || (pager.getNumber().length()    == 0)) )
					{
						data.nullifyPager();
					}
					else
					{
						data.setPager(pager);
					}
				}
			}
			else if( name.equals("email") && (emailCount < 2) )
			{
				String email = EppUtil.getText(node);
				if( email != null )
				{
					if( data == null )
					{
						data = new EppXriSocialData();
					}
					if( emailCount == 0 )
					{
						data.setPrimaryEmail(email);
					}
					else if( emailCount == 1 )
					{
						data.setSecondaryEmail(email);
					}
					emailCount++;
				}
			}
		}
   	if((data.getPrimaryEmail() == null) && (data.getSecondaryEmail() == null))
   	{
     	 	data.nullifyEmail();
   	}
   	if ((data.getPrimaryVoice() == null) && (data.getSecondaryVoice() == null))
   	{
     	 	data.nullifyVoice();
   	}

		return data;
	}

	public String toString()
	{
		return toString("socialData");
	}
}
