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
import com.neulevel.epp.core.EppObject;
import com.neulevel.epp.core.EppStatus;
import com.neulevel.epp.core.EppAuthInfo;
import com.neulevel.epp.core.EppContactData;
import com.neulevel.epp.core.EppUtil;
import com.neulevel.epp.core.EppPeriod;
import com.neulevel.epp.xri.EppXriCodeStringExt;
import com.neulevel.epp.xri.EppXriResolutionPattern;

public class EppXriCodeString extends EppObject {
	/**
		* Creates an <code>EppXriCodeString</code> object
	**/
		public EppXriCodeString() {
			parentIname = null;
			codeString = null;
			ROID = null;
			authId = null;
			UsedRUnits = 0;
			totalRUnits = 0;
			usedMcap = 0;
			totalMcap = 0;
			rStartDate = null;
			rEndDate = null;
			rPattern = new Vector();
			period = null;
			CSExt = null;
			sTotalRUnits = false;
			sTotalMcap = false;
			sUsedRUnits = false;
			sUsedMcap = false;
		}

		/**
		* 
		**/
		public String toString() {
			return  toString("codeString");
		}
		/**
		* Interface to set the Parent IName of the <code>CodeString</code> object.
		* @param iname parent iname for <code>EppXriCodeString</code>
		* @return returns nothing.
		**/
		public void setParentIName(String iname){
			this.parentIname = iname;
		}
		/**
		* Interface to set <code>codeString url</code> to <code>EppXriCodeString</code>.
		* @param cs String parameter for setting codeString
		* @return returns nothing "void"
		**/
		public void setCodeString(String cs){
			this.codeString = cs;
		}
		/**
		* Interface for setting <code>roid</code> to <code>EppXriCodeString</code>
		* @param roid parameter to set roid to <code>EppXriCodeString</code>
		* @return Returns void.
		**/
		public void setRoid(String roid){
			this.ROID = roid;
		}
		/**
		* Method to set authority id for <code>EppXriCodeString</code>.
		* @param auth_id String object representing AuthId for this <code>EppXriCodeString</code> object.
		* @return returns void.
		**/
		public void setAuthId(String auth_id){
			this.authId = auth_id;
		}
		/**
		* Method to set Used RUnits of this instance of <code>EppXriCodeString</code>.
		* @param used_runits long value representing used runits.
		* @return Returns void.
		**/
		public void setUsedRUnits(long ur){
			sUsedRUnits = true;
			this.UsedRUnits = ur;
		}
		/**
		* Method to set total RUnits for this instance of <code>EppXriCodeString</code>.
		* @param total_runits long value for total runits.
		* @return Returns void.
		**/
		public void setTotalRUnits(long tr){
			sTotalRUnits = true;
			this.totalRUnits = tr;
		}
		/**
		* Method to set Used MCaps for this instance of <code>EppXriCodeString</code>
		* @param  used_mcap long value for used MCaps
		* @return  Returns void.
		**/
		public void setUsedMCap(long um){
			sUsedMcap = true;
			this.usedMcap = um;
		}
		/**
		* Method to set Total MCaps value of <code>EppXriCodeString</code>.
		* @param  total_mcap long value of Total MCaps.
		* @return Returns void.
		**/
		public void setTotalMCap(long tm){
			sTotalMcap = true;
			this.totalMcap = tm;
		}
		/**
		* Method to set Resolution Start date of <code>EppXriCodeString</code> object;
		* @param  rsd String value representing Resolution start date.
		* @return Returns void.
		**/
		public void setResStartDate(Calendar rsd){
			this.rStartDate = rsd;
		}
		/**
		* Method to set Resolution End Date of <code>EppXriCodeString</code> object.
		* @param esd String representation of date and time for resolution end date.
		* @return Returns void.
		**/
		public void setResEndDate(Calendar esd){
			this.rEndDate = esd;
		}
		/**
		* Method to add resolution object to <code>EppXriCodeString</code> object.
		* @param  pt Object of <code>EppXriResolutionPattern</code>
		* @return Returns void.
		**/
		public void addResPattern(EppXriResolutionPattern pt){
			this.rPattern.addElement(pt);
		}
		/**
		* Method to set period of <code>EppXriCodeString</code> object. Units of the period could be months or years
		* and XRP server shall convert it to years while provisioning.
		* @param p Object of type <code>EppPeriod</code>
		* @return Returns void.
		**/
		public void setPeriod(EppPeriod p){
			if( this.period != null ) {
				this.period = null;
			}
			this.period = p;
		}
		/**
		* Method to add array of value added services to <code>EppXriCodeString</code>.
		* @param  vas object of type <code>EppXriCodeStringExt</code>
		* @return  returns void.
		**/
		public void addValueAddedService(EppXriCodeStringExt vas){
			this.CSExt = vas;
		}

		/**
		* Interface to return Parent IName of <code>EppXriCodeString</code>.
		* @param
		* @return String returns string value of Parent IName.
		**/
		public String getParentIName(){
			return this.parentIname;
		}
		/**
		* Interface to get code string from <code>EppXriCodeString</code>.
		* @param 
		* @return String
		**/
		public String getCodeString(){
			return this.codeString;
		}
		/**
		* Method to get ROID from <code>EppXriCodeString</code> object.
		* @param 
		* @return String.
		**/
		public String getRoid(){
			return this.ROID;
		}
		/**
		* Method to get associated statues of <code>EppXriCodeString</code> object.
		* @param 
		* @return Vector of <code>EppStatus</code> objects.
		**/
		public Vector getCodeStringStatus() {
			return this.getStatus();
		}
		/**
		* Method to get authid from <code>EppXriCodeString</code> object.
		* @param 
		* @return String.
		**/
		public String getAuthId(){
			return this.authId;
		}
		/**
		* Method to retrive UsedRUnits from <code>EppXriCodeString</code> object.
		* @param 
		* @return long.
		**/
		public long getUsedRUnits() {
			return this.UsedRUnits;
		}
		/**
		* Method to get Total RUnits from <code>EppXriCodeString</code> object.
		* @param 
		* @return long.
		**/
		public long getTotalRUnits(){
			return this.totalRUnits;
		}

		/**
		* Method to get Used MCaps from <code>EppXriCodeString</code> object.
		* @param 
		* @return long.
		**/
		public long getUsedMCap(){
			return this.usedMcap;
		}
		/**
		* Method to retrives total MCaps from <code>EppXriCodeString</code> object.
		* @param 
		* @return long
		**/
		public long getTotalMCap(){
			return this.totalMcap;
		}
		/**
		* Method to get resolution start date from <code>EppXriCodeString</code> object.
		* @param 
		* @return Calendar
		**/
		public Calendar getResStartDate(){
			return this.rStartDate;
		}
		/**
		* Method to retrive End resolution date associated with this <code>EppXriCodeString</code> object.
		* @param 
		* @return Calendar.
		**/
		public Calendar getResEndDate(){
			return this.rEndDate;
		}
		/**
		* Method to get resolution pattern for this object of <code>EppXriCodeString</code>.
		* @param 
		* @return Vector of <code>EppXriResolution</code>.
		**/
		public Vector getResPattern(){
			return this.rPattern;
		}
		/**
		* Method to get <code>EppPeriod</code> object from <code>EppXriCodeString</code> object.
		* @param 
		* @return EppPeriod.
		**/
		public EppPeriod getPeriod(){
			return this.period;
		}
		/**
		* Method to get value added services associated with <code>EppXriCodeString</code> object.
		* @param 
		* @return EppXriCodeStringExt.
		**/
		public EppXriCodeStringExt getValueAddedService(){
			return this.CSExt;
		}
		/**
		*
		* @param 
		* @return 
		**/
		public Element toXML( Document doc, String tag ) {
			Element elm;
			Element body = EppUtil.createElementNS(doc, "xriCS", tag);
		
			if( this.codeString != null && !this.codeString.equals("") && this.codeString.length() > 0 ) {
				elm = doc.createElement("codeString");
				elm.appendChild(doc.createTextNode(this.codeString));
				body.appendChild(elm);
			}
			if( this.ROID != null &&!this.ROID.equals("") && this.ROID.length() > 0 ) {
				elm = doc.createElement("roid");
				elm.appendChild(doc.createTextNode(this.ROID));
				body.appendChild(elm);
			}
			Vector status = getStatus();
			if( status != null ) {
		    for(int i = 0; i< status.size(); i++ ) {
					elm = ((EppStatus)status.elementAt(i)).toXML(doc,"status");
					body.appendChild(elm);
				}
			}
		
			if( parentIname != null && !parentIname.equals("") && parentIname.length() > 0 ) {
				elm = doc.createElement("parentIname");
				elm.appendChild(doc.createTextNode(this.parentIname));
				body.appendChild(elm);
		  }
		
			if( this.authId != null && !this.authId.equals("") && this.authId.length() > 0 ) {
				elm = doc.createElement("authId");
				elm.appendChild(doc.createTextNode(this.authId));
				body.appendChild(elm);
			}
			
			if(this.sUsedRUnits)
			{
				elm = doc.createElement("usedResolutionUnits");
				elm.appendChild(doc.createTextNode((new Long(this.UsedRUnits)).toString()));
				body.appendChild(elm);
			}
			if(this.sTotalRUnits)
			{
				elm = doc.createElement("totalResolutionUnits");
				elm.appendChild(doc.createTextNode((new Long(this.totalRUnits)).toString()));
				body.appendChild(elm);
			}
			if(this.sUsedMcap )
			{
				elm = doc.createElement("usedMonetaryCap");
				elm.appendChild(doc.createTextNode((new Long(this.usedMcap)).toString()));
				body.appendChild(elm);
			}
			if(this.sTotalMcap)
			{
				elm = doc.createElement("totalMonetaryCap");
				elm.appendChild(doc.createTextNode((new Long(this.totalMcap)).toString()));
				body.appendChild(elm);
			}
		
			if( this.rStartDate != null  ) {
				elm = doc.createElement("resolutionStartDate");
				elm.appendChild(EppUtil.createTextNode(doc, this.rStartDate));
				body.appendChild(elm);
			}
		
			if( this.rEndDate != null  ) {
				elm = doc.createElement("resolutionEndDate");
				elm.appendChild(EppUtil.createTextNode(doc,this.rEndDate));
				body.appendChild(elm);
			}
		
			if( (this.rPattern != null) && (this.rPattern.size() > 0) ) {
		    for(int i = 0; i< rPattern.size(); i++ ) {
					elm = ((EppXriResolutionPattern)rPattern.elementAt(i)).toXML(doc,"resolutionIntervalRecurrencePattern");
					if(elm != null) {
						body.appendChild(elm);
					}
				}
			}
		
			if( this.period != null ) {
				elm = this.period.toXML(doc,"period");
				body.appendChild(elm);
			}
		
			if( CSExt != null ) {
				elm = CSExt.toXML(doc,"ext");
				if(elm != null) {
					body.appendChild(elm);
				}
			}
			toXMLCommon(doc, body);
		
			return body;


		}
		/**
		*
		* @param 
		* @return 
		**/
		public static EppXriCodeString fromXML( Node root ) {
			EppXriCodeString cs = new EppXriCodeString();
			NodeList list = root.getChildNodes();
			if( list == null ) {
				cs = null;
				return null;
			}
			for( int i = 0; i < list.getLength(); i++ ) {
				Node node = list.item(i);
				String name = node.getLocalName();
				if( name == null ) {
					continue;
				}
		    if(name.equals("parentIname") ) {
					cs.setParentIName(EppUtil.getText(node));
				} else if(name.equals("codeString"))  {
					cs.setCodeString(EppUtil.getText(node));
				} else if(name.equals("roid") ) {
					cs.setRoid(EppUtil.getText(node));
				} 
/*
else if(name.equals("status") ) {
					EppStatus s = (EppStatus)EppStatus.fromXML(node);
					if( s != null ) {
						cs.setStatus(s);
					}
				} 
*/
else if(name.equals("authId") ) {
					cs.setAuthId(EppUtil.getText(node));
				} else if(name.equals("usedResolutionUnits") ) {
					String ptr = EppUtil.getText(node);
					cs.setUsedRUnits(Long.parseLong(ptr.trim()));
				} else if(name.equals("totalResolutionUnits") ) {
					String ptr = EppUtil.getText(node);
					cs.setTotalRUnits(Long.parseLong(ptr.trim()));
				} else if(name.equals("usedMonetaryCap") ) {
					String ptr = EppUtil.getText(node);
					cs.setUsedMCap(Long.parseLong(ptr.trim()));
				} else if(name.equals("totalMonetaryCap") ) {
					String ptr = EppUtil.getText(node);
					cs.setTotalMCap(Long.parseLong(ptr.trim()));
				} else if(name.equals("resolutionStartDate") ) {
					cs.setResStartDate(EppUtil.getDate(node));
				} else if(name.equals("resolutionEndDate") ) {
					cs.setResEndDate(EppUtil.getDate(node));
				} else if(name.equals("resolutionIntervalRecurrencePattern") ) {
					cs.addResPattern((EppXriResolutionPattern)EppXriResolutionPattern.fromXML(node));
				} else if(name.equals("ext") ) {
					cs.addValueAddedService((EppXriCodeStringExt)EppXriCodeStringExt.fromXML(node));
				} else if(name.equals("period") ) {
					EppPeriod p = (EppPeriod)EppPeriod.fromXML(node);
					if( p != null ) {
						cs.setPeriod(p);
					}
				} 
				else {
					cs.fromXMLCommon(node,name);
				}
			}
			return cs;

		}
		public boolean isSetTotalRUnits(){
			return sTotalRUnits;
		}
		public boolean isSetUsedRUnits(){
			return sUsedRUnits;
		}
		public boolean isSetTotalMcap(){
			return sTotalMcap;
		}
		public boolean isSetUsedMcap(){
			return sUsedMcap;
		}

/*	Privates from this class. */
		private String parentIname;
		private String codeString;
		private String ROID;
		private String authId;
		private long UsedRUnits;
		private long totalRUnits;
		private long usedMcap;
		private long totalMcap;
		private Calendar rStartDate;
		private Calendar rEndDate;
		private Vector rPattern;
		private EppPeriod period;
		private EppXriCodeStringExt CSExt;
		private boolean sTotalRUnits , sTotalMcap , sUsedRUnits , sUsedMcap;
}
