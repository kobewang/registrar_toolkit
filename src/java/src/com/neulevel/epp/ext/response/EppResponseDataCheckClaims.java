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
package com.neulevel.epp.ext.response;

import java.util.*;
import org.w3c.dom.*;

import com.neulevel.epp.core.*;
import com.neulevel.epp.ext.EppExtension;

/**
 * This <code>EppResponseDataCheck</code> class implements EPP Response Data
 * entity for EPP Command Check.
 * 
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.1 $ $Date: 2013/07/08 21:35:57 $
 */
public class EppResponseDataCheckClaims extends EppExtension {
	/**
	 * Status for checking the existence of an object in the registry
	 */
	public static final String UNKNOWN = " ";
	/**
	 * Status for checking the existence of an object in the registry
	 */
	public static final String FOUND = "1";
	/**
	 * Status for checking the existence of an object in the registry
	 */
	public static final String NOT_FOUND = "0";

	/**
	 * A <code>HashMap</code> storing key and value pairs
	 */
	protected HashMap<String, String> hashMap ;
	/**
	 * A <code>HashMap</code> storing key and claim key pairs
	 */
	protected HashMap<String, String> claimKeyHashMap ;

	private String _phase;
	
	public String phase() {
		return _phase;
	}
	public void phase(String _phase) {
		this._phase = _phase;
	}
	public EppResponseDataCheckClaims() {
		hashMap = new HashMap<String, String>();
		claimKeyHashMap = new HashMap<String, String>();
		_phase = new String();
		
	}
	/**
	 * Gets the <code>HashMap</code> associated with the result data of the
	 * check command - for backward compatibility, alias for getResultHashMap()
	 */
	public HashMap<String, String> getHashMap() {
		return this.hashMap;
	}

	/**
	 * Gets the <code>HashMap</code> associated with the result data of the
	 * check command
	 */
	public HashMap<String, String> getResultHashMap() {
		return this.hashMap;
	}

	/**
	 * Gets the <code>HashMap</code> associated with the claim key data of the
	 * check command
	 */
	public HashMap<String, String> getClaimKeyHashMap() {
		return this.claimKeyHashMap;
	}

	/**
	 * Adds an entry into the <code>HashMap</code> for indicating if the object
	 * exists in the registry or not - for backward compatibility
	 * 
	 * @param id
	 *            The Id associated with an EPP object, ROID for a contact
	 *            object, domain name for a domain object, host name for a host
	 *            object
	 * @param flag
	 *            one of the <code>FOUND</code>, <code>NOT_FOUND</code>,
	 *            <code>UNKNWON</code>, indicating if the object exists in the
	 *            registry or not, or the existence is unknown
	 */
	public void add(String id, String flag) {
		this.hashMap.put(id, flag);
	}

	/**
	 * Adds an entry into the <code>HashMap</code> for indicating if the object
	 * can be provisioned in the registry or not
	 * 
	 * @param id
	 *            The Id associated with an EPP object, ROID for a contact
	 *            object, domain name for a domain object, host name for a host
	 *            object
	 * @param flag
	 *            a boolean flag that indicates the availability of an object,
	 *            i.e.can it be provisioned or not.
	 */
	public void add(String id, boolean flag) {
		this.add(id, flag, null);
	}

	/**
	 * Adds an entry into the <code>HashMap</code> for indicating if the object
	 * can be provisioned in the registry or not
	 * 
	 * @param id
	 *            The Id associated with an EPP object, ROID for a contact
	 *            object, domain name for a domain object, host name for a host
	 *            object
	 * @param flag
	 *            a boolean flag that indicates the availability of an object,
	 *            i.e.can it be provisioned or not.
	 * @param claimKey
	 *            The claim key
	 */
	public void add(String id, boolean flag, String claimKey) {
		if (flag == true) {
			this.hashMap.put(id, NOT_FOUND);
		} else {
			this.hashMap.put(id, FOUND);
		}
		if (claimKey != null) {
			this.claimKeyHashMap.put(id, claimKey);
		}
	}

	/**
	 * Checks if an object id is in the <code>HashMap</code> - for backward
	 * compatibility
	 * 
	 * @return one of the <code>FOUND</code>, <code>NOT_FOUND</code>,
	 *         <code>UNKNWON</code>
	 */
	public String check(String id) {
		Object obj = this.hashMap.get(id);
		if ((obj != null) && (obj instanceof String)) {
			return (String) obj;
		}
		return UNKNOWN;
	}

	/**
	 * Checks if an object id is available for provisioning
	 */
	public boolean isAvailable(String id) {
		Object obj = this.hashMap.get(id);
		if ((obj != null) && (obj instanceof String)) {
			if (obj.equals(FOUND)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Gets the claim key for an object id
	 */
	public String getClaimkey(String id) {
		Object obj = this.claimKeyHashMap.get(id);
		if ((obj != null) && (obj instanceof String)) {
			return (String) obj;
		}
		return null;
	}

	/**
	 * Converts shared <code>EppResponseDataCheckClaims</code> components from
	 * XML format.
	 * 
	 * @param root
	 *            root node for the list of shared components
	 * @param tag
	 *            the XML tag for the object identifiers
	 */
	public static EppResponseDataCheckClaims fromXML(final Node root) {
		EppResponseDataCheckClaims claimsChk = new EppResponseDataCheckClaims();
		NodeList list = root.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (name == null) {
				continue;
			}
			if(name.equals("phase")) {
				String p = EppUtil.getText(node);
				claimsChk.phase(p);
			}
			if (name.equals("cd")) {
				NodeList clist = node.getChildNodes();
				if (clist == null) {
					continue;
				}
				String id = null;
				boolean flag = true;
				String claimKey = null;
				for (int j = 0; j < clist.getLength(); j++) {
					Node cnode = clist.item(j);
					String cname = cnode.getLocalName();
					if (cname == null) {
						continue;
					}
					if (cname.equals("name")) {
						id = EppUtil.getText(cnode);
						String cd = ((Element) cnode).getAttribute("exists");
						if ((cd != null)
								&& (cd.equals("0") || cd.equalsIgnoreCase("f") || cd
										.equalsIgnoreCase("false"))) {
							flag = false;
						} else {
							flag = true;
						}
					} else if (cname.equals("claimKey")) {
						claimKey = EppUtil.getText(cnode);
					}
				}
				if ((id != null) && (id.length() > 0)) {
					claimsChk.add(id, flag, claimKey);
				}
			}
		}
		return claimsChk;
	}

	@Override
	public Element toXML(Document doc, String tag) {

		Element cd;
		Element elm;
		Element body = EppUtil.createElementNS(doc, "launch",
				(tag == null) ? "chkData" : tag, false, "-1.0");
		
		if (null != this._phase && this._phase.length() > 0) {
			elm = doc.createElement("launch:phase");
			elm.appendChild(doc.createTextNode(this._phase));
			body.appendChild(elm);
		}
		
		Set keyset = this.hashMap.keySet();
		Iterator list = keyset.iterator();
		while (list.hasNext()) {
			cd = doc.createElement("launch:cd");
			Object o = list.next();
			Object x = this.hashMap.get(o);
			if ((o != null) && (o instanceof String) && (x != null)
					&& (x instanceof String)) {
				elm = doc.createElement("launch:name");
				if (x.equals(FOUND)) {
					elm.setAttribute("exists", "0");
				} else {
					elm.setAttribute("exists", "1");
				}
				elm.appendChild(doc.createTextNode((String) o));
				cd.appendChild(elm);
				x = this.claimKeyHashMap.get(o);
				if ((x != null) && (x instanceof String)) {
					String s = (String) x;
					if ((s != null) && (s.length() > 0)) {
						elm = doc.createElement("launch:claimKey");
						elm.appendChild(doc.createTextNode(s));
						cd.appendChild(elm);
					}
				}
				body.appendChild(cd);

			}
		}

		return body;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return toString("launch:chkData");
	}
	
}
