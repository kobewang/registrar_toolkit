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
package com.neulevel.epp.core.response;

import org.w3c.dom.*;
import com.neulevel.epp.core.*;
import com.neulevel.epp.xri.response.*;

/**
 * This <code>EppResponseData</code> class implements EPP Response Data entity.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.4 $ $Date: 2009/11/23 14:35:56 $
 */
abstract public class EppResponseData extends EppEntity
{
	/**
	 * Converts an XML element into an <code>EppResponseData</code> object.
	 * The caller of this method must make sure that the root node is the
	 * resData element of EPP responseType
	 *
	 * @param root root node for an <code>EppResponseData</code> object in
	 *             XML format
	 *
	 * @return an <code>EppResponseData</code> object, or null if the node
	 *         is invalid
	 */
	public static EppEntity fromXML( Node root )
	{
		NodeList list = root.getChildNodes();
		for( int i = 0; i < list.getLength(); i++ )
		{
			Node node = list.item(i);
			String prefix = node.getPrefix();
			String name = node.getLocalName();
			if( (prefix == null) || (name == null) )
			{
				continue;
			}
			if( prefix.equals("contact") )
			{
				if( name.equals("creData") )
				{
					return EppResponseDataCreateContact.fromXML(node);
				}
				else if( name.equals("infData") )
				{
					return EppResponseDataInfo.fromXML(node);
				}
				else if( name.equals("chkData") )
				{
					return EppResponseDataCheckContact.fromXML(node);
				}
				else if( name.equals("panData") )
				{
					return EppResponseDataPendingContact.fromXML(node);
				}
				else if( name.equals("trnData") )
				{
					return EppResponseDataTransferContact.fromXML(node);
				}
				else if( name.equals("transfer") )
				{
					return EppResponseDataPoll.fromXML(node);
				}
				/*
				 * other response data types
				 */
			}
			else if( prefix.equals("domain") )
			{
				if( name.equals("creData") )
				{
					return EppResponseDataCreateDomain.fromXML(node);
				}
				else if( name.equals("infData") )
				{
					return EppResponseDataInfo.fromXML(node);
				}
				else if( name.equals("chkData") )
				{
					return EppResponseDataCheckDomain.fromXML(node);
				}
				else if( name.equals("trnData") )
				{
					return EppResponseDataTransferDomain.fromXML(node);
				}
				else if( name.equals("renData") )
				{
					return EppResponseDataRenewDomain.fromXML(node);
				}
				else if( name.equals("panData") )
				{
					return EppResponseDataPendingDomain.fromXML(node);
				}
				else if( name.equals("transfer") )
				{
					return EppResponseDataPoll.fromXML(node);
				}
				/*
				 * other response data types
				 */
			}
			else if( prefix.equals("host") )
			{
				if( name.equals("creData") )
				{
					return EppResponseDataCreateHost.fromXML(node);
				}
				else if( name.equals("infData") )
				{
					return EppResponseDataInfo.fromXML(node);
				}
				else if( name.equals("chkData") )
				{
					return EppResponseDataCheckHost.fromXML(node);
				}
				else if( name.equals("panData") )
				{
					return EppResponseDataPendingHost.fromXML(node);
				}
				/*
				 * other response data types
				 */
			}
			else if( prefix.equals("svcsub") )
			{
				if( name.equals("creData") )
				{
					return EppResponseDataCreateSvcsub.fromXML(node);
				}
				else if( name.equals("infData") )
				{
					return EppResponseDataInfo.fromXML(node);
				}
				else if( name.equals("chkData") )
				{
					return EppResponseDataCheckSvcsub.fromXML(node);
				}
				else if( name.equals("trnData") )
				{
					return EppResponseDataTransferSvcsub.fromXML(node);
				}
				else if( name.equals("renData") )
				{
					return EppResponseDataRenewSvcsub.fromXML(node);
				}
				else if( name.equals("panData") )
				{
					return EppResponseDataPendingSvcsub.fromXML(node);
				}
				else if( name.equals("transfer") )
				{
					return EppResponseDataPoll.fromXML(node);
				}
				/*
				 * other response data types
				 */
			}
			else if( prefix.equals("xriAU") )
			{
				if( name.equals("creData") )
				{
					return EppResponseDataCreateXriAuthority.fromXML(node);
				}
				else if( name.equals("infData") )
				{
					return EppResponseDataInfo.fromXML(node);
				}
				else if( name.equals("chkData") )
				{
					return EppResponseDataCheckXriAuthority.fromXML(node);
				}
				else if( name.equals("trnData") )
				{
					return EppResponseDataTransferXriAuthority.fromXML(node);
				}
				else if( name.equals("panData") )
				{
					return EppResponseDataPendingXriAuthority.fromXML(node);
				}
				else if( name.equals("transfer") )
				{
					return EppResponseDataPoll.fromXML(node);
				}
				/*
				 * other response data types
				 */
			}
			else if( prefix.equals("xriINU") )
			{
				if( name.equals("creData") )
				{
					return EppResponseDataCreateXriNumber.fromXML(node);
				}
				else if( name.equals("infData") )
				{
					return EppResponseDataInfo.fromXML(node);
				}
				else if( name.equals("chkData") )
				{
					return EppResponseDataCheckXriNumber.fromXML(node);
				}
				else if( name.equals("renData") )
				{
					return EppResponseDataRenewXriNumber.fromXML(node);
				}
				else if( name.equals("panData") )
				{
					return EppResponseDataPendingXriNumber.fromXML(node);
				}
				/*
				 * other response data types
				 */
			}
			else if( prefix.equals("xriINA") )
			{
				if( name.equals("creData") )
				{
					return EppResponseDataCreateXriName.fromXML(node);
				}
				else if( name.equals("infData") )
				{
					return EppResponseDataInfo.fromXML(node);
				}
				else if( name.equals("chkData") )
				{
					return EppResponseDataCheckXriName.fromXML(node);
				}
				else if( name.equals("renData") )
				{
					return EppResponseDataRenewXriName.fromXML(node);
				}
				else if( name.equals("trnData") )
				{
					return EppResponseDataTransferXriName.fromXML(node);
				}
				else if( name.equals("panData") )
				{
					return EppResponseDataPendingXriName.fromXML(node);
				}
				else if( name.equals("transfer") )
				{
					return EppResponseDataPoll.fromXML(node);
				}
				/*
				 * other response data types
				 */
			}
			else if( prefix.equals("xriISV") )
			{
				if( name.equals("creData") )
				{
					return EppResponseDataCreateXriService.fromXML(node);
				}
				else if( name.equals("infData") )
				{
					return EppResponseDataInfo.fromXML(node);
				}
				else if( name.equals("chkData") )
				{
					return EppResponseDataCheckXriService.fromXML(node);
				}
				else if( name.equals("renData") )
				{
					return EppResponseDataRenewXriService.fromXML(node);
				}
				else if( name.equals("panData") )
				{
					return EppResponseDataPendingXriService.fromXML(node);
				}
				/*
				 * other response data types
				 */
			}
			else if( prefix.equals("xriCS") ) {
				if( name.equals("creData") )
				{
					return EppResponseDataCreateXriCodeString.fromXML(node);
				}
				else if( name.equals("infData") )
				{
					return EppResponseDataInfo.fromXML(node);
				}
				else if( name.equals("chkData") )
				{
					return EppResponseDataCheckXriCodeString.fromXML(node);
				}
			}
			/*
			 * other object types
			 */
		}

		return null;
	}

	public String toString()
	{
		return toString("resData");
	}
}
