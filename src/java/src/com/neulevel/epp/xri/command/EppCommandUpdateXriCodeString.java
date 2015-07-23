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
package com.neulevel.epp.xri.command;

import java.util.*;
import org.w3c.dom.*;
import com.neulevel.epp.core.*;
import com.neulevel.epp.core.command.*;
import com.neulevel.epp.core.EppEntity;
import com.neulevel.epp.core.EppObject;
import com.neulevel.epp.core.EppStatus;
import com.neulevel.epp.core.EppAuthInfo;
import com.neulevel.epp.core.EppContactData;
import com.neulevel.epp.core.EppUtil;
import com.neulevel.epp.core.EppPeriod;
import com.neulevel.epp.xri.EppXriCodeStringExt;
import com.neulevel.epp.xri.EppXriResolutionPattern;

public class EppCommandUpdateXriCodeString extends EppCommandUpdate {
	/**
	* No argument constructor.
	* @param
	* @return
	**/
	public EppCommandUpdateXriCodeString() {
		codeString					= null;
    addedStatus					= new Vector();
    removedStatus				= new Vector();
    addedRecPat					= new Vector();
    removedRecPat				= new Vector(3);
    changedResStartDate	= null;
    changedResEndDate		= null;
    changedTotalRUnits	= -1;
    changedTotalMUnits	= -1;
		totalRUnitsBase			= -1;
		totalMUnitsBase			= -1;
		actionResStartDate	= null;
		actionResEndDate		= null;
		actionTotalRUnits		= null;
		actionTotalMUnits		= null;
		CSExt								= null;
		rmCSExt							= null;
	}
	/**
	* Constructor accepting one argument.
	* @param csId String type representing codeString id to be modified.
	* @return
	**/
	public EppCommandUpdateXriCodeString( String csId){
		codeString					= csId;
    addedStatus					= new Vector();
    removedStatus				= new Vector();
    addedRecPat					= new Vector();
    removedRecPat				= new Vector(3);
    changedResStartDate	= null;
    changedResEndDate		= null;
    changedTotalRUnits	= -1;
    changedTotalMUnits	= -1;
		totalRUnitsBase			= -1;
		totalMUnitsBase			= -1;
		actionResStartDate	= null;
		actionResEndDate		= null;
		actionTotalRUnits		= null;
		actionTotalMUnits		= null;
		CSExt								= null;
		rmCSExt							= null;

	}
	/**
	* Contructor with 2 arguments.
	* @param csid String type representing codeString to be updated.
	* @param xid String client transaction id.
	* @return
	**/
	public EppCommandUpdateXriCodeString( String csId, String xid ) {
		codeString					= csId;
    addedStatus					= new Vector();
    removedStatus				= new Vector();
    addedRecPat					= new Vector();
    removedRecPat				= new Vector(3);
    changedResStartDate	= null;
    changedResEndDate		= null;
    changedTotalRUnits	= -1;
    changedTotalMUnits	= -1;
		totalRUnitsBase			= -1;
		totalMUnitsBase			= -1;
		actionResStartDate	= null;
		actionResEndDate		= null;
		actionTotalRUnits		= null;
		actionTotalMUnits		= null;
		CSExt								= null;
		rmCSExt							= null;
		clTRID = xid;
	}

	/**
	* Method to add new status to codeString.
	* @param nstatus object of type <code>EppStatus</code>
	* @return
	**/
	public void addStatus(EppStatus nstatus ) {
		if( this.addedStatus == null ) {
			return;
		}
		this.addedStatus.addElement(nstatus);
	}
	public void addStatus(String nstatus ) {
		if( this.addedStatus == null ) {
			return;
		}
		this.addedStatus.addElement(new EppStatus(nstatus));
	}
	/**
	* Method to retrive newly added status from codeString object.
	* @param
	* @return Vector of <code>EppStatus</code>.
	**/
	public Vector getNewStatus() {
		return this.addedStatus;
	}
	/**
	* Method to add status that should be removed from codeString object.
	* @param ostatus Object of type <code>EppStatus</code>.
	* @return
	**/
	public void removeStatus(EppStatus ostatus) {
		if( this.removedStatus == null )
			return;
		this.removedStatus.addElement(ostatus);
	}
	public void removeStatus(String ostatus) {
		if( this.removedStatus == null )
			return;
		this.removedStatus.addElement(new EppStatus(ostatus));
	}
	/**
	* Method to retrive vector of status objects that shall be removed from codeString object.
	* @param
	* @return Vector of <code>EppStatus</code> objects that shall be removed from codeString object.
	**/
	public Vector getRemovedStatus() {
		return this.removedStatus;
	}
	/**
	* Method to add new resolution pattern id to codeString.
	* @param np of type <code>EppXriResolutionPattern</code>.
	* @return
	**/
	public void addNewRecPattterns(EppXriResolutionPattern np) {
		if( this.addedRecPat == null )
			return;
		this.addedRecPat.addElement(np);
	}
	/**
	* Method to retrive vector of <code>EppXriResolutionPattern</code> that has to be added to codeString
	* @param
	* @return Vector of <code>EppXriResolutionPattern</code> objects that shall be added to codeString.
	**/
	public Vector getNewRecPatterns() {
		return this.addedRecPat;
	}
	/**
	* Method to add <code>EppXriResolutionPattern</code> objects to be removed from codeString.
	* @param op <code>EppXriResolutionPattern</code> to be removed.
	* @return
	**/
	public void addRemRecPattern(EppXriResolutionPattern op) {
		if( this.removedRecPat == null )
			return;
		this.removedRecPat.addElement(op);
	}
	/**
	* Method to retrive vector of removed pattern ids.
	* @param
	* @return Vector of <code>EppXriResolutionPattern</code> representing pattern ids to be removed.
	**/
	public Vector getRemovedRecPattern() {
		return this.removedRecPat;
	}
	/**
	* Method to set or delete resolution pattern start date.
	* @param edate Calendar value representing modified date or date to be removed
	* @param action String action to be performed and possible values could be "del" or "" (indicating update)
	* @return
	**/
	public void setResolutionStartDate(Calendar sdate, int action) {
		this.changedResStartDate = sdate;
		if( action == DELETE_ACTION)
		{
			actionResStartDate = "del";
		}
		else if (action == UPDATE_ACTION)
		{
			actionResStartDate = "";
		}
	}
	/**
	* Method to retrive resolution end date of codeString object
	* @param 
	* @return Calendar value of the date string
	**/
	public Calendar getResolutionStartDate() {
		return this.changedResStartDate;
	}
	/**
	* Method to set or delete resolution pattern end date.
	* @param edate Calendar value representing modified date or date to be removed
	* @param action String action to be performed and possible values could be "delete" or "" (indicating update)
	* @return
	**/
	public void setResolutionEndDate(Calendar edate, int action) {
		this.changedResEndDate = edate;
    if( action == DELETE_ACTION)
    {
      actionResEndDate = "del";
    }
    else if (action == UPDATE_ACTION)
    {
      actionResEndDate = "";
    }
	}
	/**
	* Method to retrive resolution pattern end date.
	* @param
	* @return Calendar
	**/
	public Calendar getResolutionEndDate() {
		return this.changedResEndDate;
	}
	/**
	* Method update total resolution units in EppXriCodeStjring object.
	* @param chg long value indicating changed value.
	* @param action String value indicating "action" to be preformed possible values are "inc", "dec" and "delete"
	* @param base long value received from info command or value that was previously provisioned into codeString.
	* @return
	**/
	public void updateTotalRUnits(long chg,int action, long base) {
		this.changedTotalRUnits = chg;
    if( action == DELETE_ACTION)
    {
      actionTotalRUnits = "del";
    }
    else if (action == INCREMENT_ACTION)
    {
      actionTotalRUnits = "inc";
    }
    else if (action == DECREMENT_ACTION)
    {
      actionTotalRUnits = "dec";
    }

		this.totalRUnitsBase = base;
	}
	/**
	* Method to retrive total Resolution units.
	* @param
	* @return long value of runits.
	**/
	public long getTotalRUnits() {
		return this.changedTotalRUnits;
	}
	/**
	* Method to update Mcaps in EppXriCodeString object provisioned ealier.
	* @param chg long value indicating changed value.
	* @param action String value indicating "action" to be preformed possible values are "inc", "dec" and "delete"
	* @param base long value received from info command or value that was previously provisioned into codeString.
	* @return
	**/
	public void updateTotalMCaps(long chg,int action, long base) {
		this.changedTotalMUnits = chg;
		this.totalMUnitsBase = base;
    if( action == DELETE_ACTION)
    {
      actionTotalMUnits = "del";
    }
    else if (action == INCREMENT_ACTION)
    {
      actionTotalMUnits = "inc";
    }
    else if (action == DECREMENT_ACTION)
    {
      actionTotalMUnits = "dec";
    }

	}
	/**
	* Method to retrive total monetory caps set previously.
	* @param
	* @return long value of mcaps.
	**/
	public long getTotalMCaps() {
		return this.changedTotalMUnits;
	}
	/**
	* Method to set the codeString.
	* @param new_cs String representing the codeString to update.
	* @return
	**/
	public void setCodeString(String new_cs ) {
		this.codeString = new_cs;
	}
	/**
	* Method to retrive codeString from the object.
	* @param
	* @return String codeString
	**/
	public String getCodeString() {
		return this.codeString;
	}
	/**
	* Method to retrive base runits which was previously populated.
	* @param
	* @return long value of base r units.
	**/
	public long getBaseRUnits() {
		return this.totalRUnitsBase;
	}
	/**
	* Method to retrive base mcaps.
	* @param
	* @return long base mcaps.
	**/
	public long getBaseMCaps() {
		return this.totalMUnitsBase;
	}
	/**
	* Method to get resolution start date.
	* @param
	* @return String resolution end date.
	**/
	public String getResolutionStartDateAction() {
		return this.actionResStartDate;
	}
	/**
	* Method to get previously populated resolution end date.
	* @param
	* @return String resolution end date.
	**/
	public String getResolutionEndDateAction() {
		return this.actionResEndDate;
	}
	/**
	* Mthod to retrive action to be performed on totalrunits.
	* @param
	* @return String action to be performed.
	**/
	public String getTotalRUnitsAction() {
		return this.actionTotalRUnits;
	}
	/**
	* Method to return action on totalmcaps.
	* @param
	* @return String representing action to be performed on totalmcap.
	**/
	public String getTotalMCapsAction() {
		return this.actionTotalMUnits;
	}

	public Element toXML( Document doc, String tag ) {
		Element elm;
		Element body = EppUtil.createElementNS(doc, "xriCS", tag);
		if( codeString != null ) {
			elm = doc.createElement("codeString");
			elm.appendChild(doc.createTextNode(this.codeString));
			body.appendChild(elm);
		}
		if(this.addedStatus.size() > 0 || this.addedRecPat.size() > 0 || this.CSExt != null) {
			elm = doc.createElement("add");
			for( int i=0;i<this.addedStatus.size();i++) {
				EppStatus stat = (EppStatus)this.addedStatus.elementAt(i);
				elm.appendChild( stat.toXML(doc,"status") );
			}
			for( int i=0;i<this.addedRecPat.size();i++) {
				EppXriResolutionPattern pattern = (EppXriResolutionPattern)this.addedRecPat.elementAt(i);
				elm.appendChild( pattern.toXML(doc,"resolutionIntervalRecurrencePattern"));
			}
			if( this.CSExt != null ) {
				elm.appendChild(CSExt.toXML(doc,"ext"));
			}
			body.appendChild(elm);
		}
		if(this.removedStatus.size()>0 || this.removedRecPat.size()>0 || this.rmCSExt != null ) {
			elm = doc.createElement("rem");
			for( int i=0;i<this.removedStatus.size();i++) {
				EppStatus item = (EppStatus)this.removedStatus.elementAt(i);
				elm.appendChild( item.toXML(doc,"status") );
			}
			for( int i=0;i<this.removedRecPat.size();i++) {
				EppXriResolutionPattern item = (EppXriResolutionPattern)this.removedRecPat.elementAt(i);
				Element rec = doc.createElement("resolutionIntervalRecurrencePattern");
				Element sub_elem = doc.createElement("recPatternId");
				sub_elem.appendChild( doc.createTextNode(item.getPatternId()) );
				rec.appendChild(sub_elem);
				elm.appendChild(rec);
			}
			if( this.rmCSExt != null ) {
				elm.appendChild(rmCSExt.toXML(doc,"ext"));
			}
			body.appendChild(elm);
		}
		if(this.changedResStartDate != null || this.changedResEndDate != null
			|| this.actionTotalRUnits != null || this.actionTotalMUnits != null ) {
			elm = doc.createElement("chg");
			if( this.actionTotalRUnits != null && this.actionTotalRUnits.length() > 0 ) {
				Element runits = doc.createElement("totalResolutionUnits");
				runits.setAttribute("action", this.actionTotalRUnits );
				if( this.totalRUnitsBase > 0 ) {
					runits.setAttribute("base", (new Long(this.totalRUnitsBase)).toString() );
				}
				runits.appendChild(doc.createTextNode( (new Long(this.changedTotalRUnits)).toString() ));
				elm.appendChild(runits);
			}
			if( this.actionTotalMUnits != null && this.actionTotalMUnits.length() > 0 ) {
				Element munits = doc.createElement("totalMonetaryCap");
				munits.setAttribute("action", this.actionTotalMUnits );
				if( this.totalMUnitsBase > 0 ) {
					munits.setAttribute("base", (new Long(this.totalMUnitsBase)).toString());
				}
				munits.appendChild(doc.createTextNode( (new Long(this.changedTotalMUnits)).toString() ));
				elm.appendChild(munits);
			}
			if(this.changedResStartDate != null ) {
				Element sdate = doc.createElement("resolutionStartDate");
				if( this.actionResStartDate != null && this.actionResStartDate.length() > 0 ) {
					sdate.setAttribute("action", this.actionResStartDate );
				}
				sdate.appendChild(EppUtil.createTextNode(doc,this.changedResStartDate));
				elm.appendChild(sdate);
			}
			if( this.changedResEndDate != null  ) {
				Element edate = doc.createElement("resolutionEndDate");
				if( this.actionResEndDate != null && this.actionResEndDate.length() > 0 ) {
					edate.setAttribute("action", this.actionResEndDate );
				}
				edate.appendChild(EppUtil.createTextNode(doc,this.changedResEndDate));
				elm.appendChild(edate);
			}
			body.appendChild(elm);
		}
		return toXMLCommon(doc, tag, body);

	}
	public static EppCommandUpdateXriCodeString fromXML( Node root ) {
		EppCommandUpdateXriCodeString cmd = null;
		NodeList list = root.getChildNodes();
		for(  int i = 0; i < list.getLength(); i++ ) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if( name == null ) {
				continue;
			}
			if( name.equals("codeString") || name.equals("xriCS:codeString") ) {
				String s = EppUtil.getText(node);
				if( (s != null) ) {
					if( cmd == null ) {
						cmd = new EppCommandUpdateXriCodeString(s);
						if( cmd == null ) {
							return cmd;
						}
					}
				}
			} else if( name.equals("add") || name.equals("xriCS:add") ) {
				if( cmd != null ) {
					cmd.objFromXml(node, true);
				}
			} else if( name.equals("rem") || name.equals("xriCS:rem") ) {
				if( cmd != null ) {
					cmd.objFromXml(node, false);
				}
			} else if( name.equals("chg") || name.equals("xriCS:chg") ) {
				if( cmd != null ) {
					NodeList l_list = node.getChildNodes();
					for(  int l_i = 0; l_i < l_list.getLength(); l_i++ ) {
						Node l_node = l_list.item(l_i);
						String l_name = l_node.getLocalName();
						if( l_name == null ) {
							l_name = l_node.getNodeName();
						}
						if( l_name == null ) {
							continue;
						}
						if( l_name.equals("totalResolutionUnits") || l_name.equals("xriCS:totalResolutionUnits") ) {
							String act_val = ( (Element)l_node).getAttribute("action");
							String base_val = ( (Element)l_node).getAttribute("base");
							if( act_val != null ) {
								cmd.actionTotalRUnits = act_val;
							}
							if( base_val != null ) {
								cmd.totalRUnitsBase = Long.parseLong(base_val.trim());
							}
							String val = EppUtil.getText(l_node);
							cmd.changedTotalRUnits = Long.parseLong(val.trim());
							
						} else if( l_name.equals("totalMonetaryCap") || l_name.equals("xriCS:totalMonetaryCap") ) {
							String act_val = ( (Element )l_node).getAttribute("action");
							String base_val = ( (Element )l_node).getAttribute("base");
							if( act_val != null ) {
								cmd.actionTotalMUnits = act_val;
							}
							if( base_val != null ) {
								cmd.totalMUnitsBase = Long.parseLong(base_val.trim());
							}
							String val = EppUtil.getText(l_node);
							cmd.changedTotalMUnits = Long.parseLong(val.trim());
	
						} else if( l_name.equals("resolutionStartDate") || l_name.equals("xriCS:resolutionStartDate") ) {
							String act_val = ( (Element )l_node).getAttribute("action");
							if( act_val != null ) {
								cmd.actionResStartDate = act_val;
							}
							cmd.changedResStartDate = EppUtil.getDate(l_node);
	
						} else if( l_name.equals("resolutionEndDate") || l_name.equals("xriCS:resolutionEndDate") ) {
							String act_val = ( (Element )l_node).getAttribute("action");
							if( act_val != null ) {
								cmd.actionResEndDate = act_val;
							}
							cmd.changedResEndDate = EppUtil.getDate(l_node);
						}
					}
				}
			}
		}
		return cmd;

	}
	public void addValueAddedService(EppXriCodeStringExt ext) {
    if( this.CSExt != null ) {
      this.CSExt = null;
    }
    this.CSExt = new EppXriCodeStringExt(ext);
  }
	public  EppXriCodeStringExt getValueAddedService(){
    return this.CSExt;
  }
  public void removeValueAddedService(EppXriCodeStringExt ext) {
    if( this.rmCSExt != null ) {
      this.rmCSExt = null;
    }
    this.rmCSExt = new EppXriCodeStringExt(ext);
  }
  public EppXriCodeStringExt getRemovedValueAddedService(){
    return this.rmCSExt;
  }

	private void objFromXml(Node root, boolean isadd ) {
		NodeList list = root.getChildNodes();
		for(  int i = 0; i < list.getLength(); i++ ) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if( name == null ) {
				continue;
			}
			if( name.equals("status") || name.equals("xriCS:status") ) {
				EppStatus stat  = (EppStatus)EppStatus.fromXML(node);
				if( stat != null ) {
					if( isadd == true ) {
						this.addStatus(stat);
					} else {
						this.removeStatus(stat);
					}
					stat = null;
				}
			} else if( name.equals("resolutionIntervalRecurrencePattern") || name.equals("xriCS:resolutionIntervalRecurrencePattern") ) {
				EppXriResolutionPattern ptr = (EppXriResolutionPattern)EppXriResolutionPattern.fromXML(node);
				if( ptr != null ) {
					if( isadd == true ) {
						this.addNewRecPattterns(ptr);
					} else {
						this.addRemRecPattern(ptr);
					}
					ptr = null;
				}
			} else if( name.equals("ext") || name.equals("xriCS:ext") ) {
				EppXriCodeStringExt ext_ptr = (EppXriCodeStringExt)EppXriCodeStringExt.fromXML(node);
				if( ext_ptr != null ) {
					if( isadd ) {
						this.addValueAddedService( ext_ptr );
					} else {
						this.removeValueAddedService( ext_ptr );
					}
					ext_ptr = null;
				}
			}
		}
	}
	private String		codeString;
	private Vector		addedStatus;
	private Vector		removedStatus;
	private Vector		addedRecPat;
	private Vector		removedRecPat;
	private Calendar		changedResStartDate;
	private Calendar		changedResEndDate;
	private long			changedTotalRUnits;
	private long			changedTotalMUnits;
	private long			totalRUnitsBase;
	private long			totalMUnitsBase;
	private String		actionResStartDate;
	private String		actionResEndDate;
	private String		actionTotalRUnits;
	private String		actionTotalMUnits;
	private EppXriCodeStringExt	CSExt;
	private EppXriCodeStringExt	rmCSExt;
	public final static int DELETE_ACTION = 2;
	public final static int DECREMENT_ACTION = 1;
	public final static int INCREMENT_ACTION = 0;
	public final static int UPDATE_ACTION = 3;
}
