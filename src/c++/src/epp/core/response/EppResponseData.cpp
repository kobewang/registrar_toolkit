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
#include "EppResponseData.hpp"
#include "EppResponseDataCreate.hpp"
#include "EppResponseDataCreateContact.hpp"
#include "EppResponseDataCreateDomain.hpp"
#include "EppResponseDataCreateHost.hpp"
#include "EppResponseDataCreateSvcsub.hpp"
#if defined(RTK_SUPPORT_XRI)
#include "EppResponseDataCreateXriAuthority.hpp"
#include "EppResponseDataCreateXriNumber.hpp"
#include "EppResponseDataCreateXriName.hpp"
#include "EppResponseDataCreateXriService.hpp"
#include "EppResponseDataCreateXriCodeString.hpp"
#endif
#include "EppResponseDataInfo.hpp"
#include "EppResponseDataCheck.hpp"
#include "EppResponseDataCheckContact.hpp"
#include "EppResponseDataCheckDomain.hpp"
#include "EppResponseDataCheckHost.hpp"
#include "EppResponseDataCheckSvcsub.hpp"
#if defined(RTK_SUPPORT_XRI)
#include "EppResponseDataCheckXriAuthority.hpp"
#include "EppResponseDataCheckXriNumber.hpp"
#include "EppResponseDataCheckXriName.hpp"
#include "EppResponseDataCheckXriService.hpp"
#include "EppResponseDataCheckXriCodeString.hpp"
#endif
#include "EppResponseDataPending.hpp"
#include "EppResponseDataPendingContact.hpp"
#include "EppResponseDataPendingDomain.hpp"
#include "EppResponseDataPendingHost.hpp"
#include "EppResponseDataPendingSvcsub.hpp"
#if defined(RTK_SUPPORT_XRI)
#include "EppResponseDataPendingXriAuthority.hpp"
#include "EppResponseDataPendingXriNumber.hpp"
#include "EppResponseDataPendingXriName.hpp"
#include "EppResponseDataPendingXriService.hpp"
#endif
#include "EppResponseDataPoll.hpp"
#include "EppResponseDataRenew.hpp"
#include "EppResponseDataRenewDomain.hpp"
#include "EppResponseDataRenewSvcsub.hpp"
#if defined(RTK_SUPPORT_XRI)
#include "EppResponseDataRenewXriNumber.hpp"
#include "EppResponseDataRenewXriName.hpp"
#include "EppResponseDataRenewXriService.hpp"
#endif
#include "EppResponseDataTransfer.hpp"
#include "EppResponseDataTransferContact.hpp"
#include "EppResponseDataTransferDomain.hpp"
#include "EppResponseDataTransferSvcsub.hpp"
#if defined(RTK_SUPPORT_XRI)
#include "EppResponseDataTransferXriAuthority.hpp"
#include "EppResponseDataTransferXriName.hpp"
#endif
#include "EppResponseDataNeustarTransaction.hpp"

EppResponseData * EppResponseData::fromXML( const DOMNode& root )
{
	DOMNodeList* list = root.getChildNodes();
	for( unsigned int i = 0; i < list->getLength(); i++ )
	{
		DOMNode* node = list->item(i);
		DOMString prefix = node->getPrefix();
		DOMString name = node->getLocalName();
#if 0
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
			/*
			 * other response data types
			 */
		}
#endif
		if( name.isNull() )
		{
			name = node->getNodeName();
		}

		if( name.isNull() )
		{
			continue;
		}
		if( name.equals("contact:creData") )
		{
			return EppResponseDataCreateContact::fromXML(*node);
		}
		else if( name.equals("contact:infData") )
		{
			return EppResponseDataInfo::fromXML(*node);
		}
		else if( name.equals("contact:chkData") )
		{
			return EppResponseDataCheckContact::fromXML(*node);
		}
		else if( name.equals("contact:panData") )
		{
			return EppResponseDataPendingContact::fromXML(*node);
		}
		else if( name.equals("contact:trnData") )
		{
			return EppResponseDataTransferContact::fromXML(*node);
		}
		else if( name.equals("contact:transfer") )
		{
			return EppResponseDataPoll::fromXML(*node);
		}
		else if( name.equals("domain:creData") )
		{
			return EppResponseDataCreateDomain::fromXML(*node);
		}
		else if( name.equals("domain:infData") )
		{
			return EppResponseDataInfo::fromXML(*node);
		}
		else if( name.equals("domain:chkData") )
		{
			return EppResponseDataCheckDomain::fromXML(*node);
		}
		else if( name.equals("domain:panData") )
		{
			return EppResponseDataPendingDomain::fromXML(*node);
		}
		else if( name.equals("domain:renData") )
		{
			return EppResponseDataRenewDomain::fromXML(*node);
		}
		else if( name.equals("domain:trnData") )
		{
			return EppResponseDataTransferDomain::fromXML(*node);
		}
		else if( name.equals("domain:transfer") )
		{
			return EppResponseDataPoll::fromXML(*node);
		}
		else if( name.equals("host:creData") )
		{
			return EppResponseDataCreateHost::fromXML(*node);
		}
		else if( name.equals("host:infData") )
		{
			return EppResponseDataInfo::fromXML(*node);
		}
		else if( name.equals("host:chkData") )
		{
			return EppResponseDataCheckHost::fromXML(*node);
		}
		else if( name.equals("host:panData") )
		{
			return EppResponseDataPendingHost::fromXML(*node);
		}
		else if( name.equals("svcsub:creData") )
		{
			return EppResponseDataCreateSvcsub::fromXML(*node);
		}
		else if( name.equals("svcsub:infData") )
		{
			return EppResponseDataInfo::fromXML(*node);
		}
		else if( name.equals("svcsub:chkData") )
		{
			return EppResponseDataCheckSvcsub::fromXML(*node);
		}
		else if( name.equals("svcsub:panData") )
		{
			return EppResponseDataPendingSvcsub::fromXML(*node);
		}
		else if( name.equals("svcsub:renData") )
		{
			return EppResponseDataRenewSvcsub::fromXML(*node);
		}
		else if( name.equals("svcsub:trnData") )
		{
			return EppResponseDataTransferSvcsub::fromXML(*node);
		}
		else if( name.equals("svcsub:transfer") )
		{
			return EppResponseDataPoll::fromXML(*node);
		}
#if defined(RTK_SUPPORT_XRI)		
		else if( name.equals("xriAU:chkData") )
		{
			return EppResponseDataCheckXriAuthority::fromXML(*node);
		}
		else if( name.equals("xriAU:creData") )
		{
			return EppResponseDataCreateXriAuthority::fromXML(*node);
		}
		else if( name.equals("xriAU:infData") )
		{
			return EppResponseDataInfo::fromXML(*node);
		}
		else if( name.equals("xriAU:panData") )
		{
			return EppResponseDataPendingXriAuthority::fromXML(*node);
		}
		else if( name.equals("xriAU:trnData") )
		{
			return EppResponseDataTransferXriAuthority::fromXML(*node);
		}
		else if( name.equals("xriINU:chkData") )
		{
			return EppResponseDataCheckXriNumber::fromXML(*node);
		}
		else if( name.equals("xriINU:creData") )
		{
			return EppResponseDataCreateXriNumber::fromXML(*node);
		}
		else if( name.equals("xriINU:infData") )
		{
			return EppResponseDataInfo::fromXML(*node);
		}
		else if( name.equals("xriINU:renData") )
		{
			return EppResponseDataRenewXriNumber::fromXML(*node);
		}
		else if( name.equals("xriINU:panData") )
		{
			return EppResponseDataPendingXriNumber::fromXML(*node);
		}
		else if( name.equals("xriINA:chkData") )
		{
			return EppResponseDataCheckXriName::fromXML(*node);
		}
		else if( name.equals("xriINA:creData") )
		{
			return EppResponseDataCreateXriName::fromXML(*node);
		}
		else if( name.equals("xriINA:infData") )
		{
			return EppResponseDataInfo::fromXML(*node);
		}
		else if( name.equals("xriINA:renData") )
		{
			return EppResponseDataRenewXriName::fromXML(*node);
		}
		else if( name.equals("xriINA:panData") )
		{
			return EppResponseDataPendingXriName::fromXML(*node);
		}
		else if( name.equals("xriINA:trnData") )
		{
			return EppResponseDataTransferXriName::fromXML(*node);
		}
		else if( name.equals("xriISV:chkData") )
		{
			return EppResponseDataCheckXriService::fromXML(*node);
		}
		else if( name.equals("xriISV:creData") )
		{
			return EppResponseDataCreateXriService::fromXML(*node);
		}
		else if( name.equals("xriISV:infData") )
		{
			return EppResponseDataInfo::fromXML(*node);
		}
		else if( name.equals("xriISV:renData") )
		{
			return EppResponseDataRenewXriService::fromXML(*node);
		}
		else if( name.equals("xriISV:panData") )
		{
			return EppResponseDataPendingXriService::fromXML(*node);
		}
#endif		
		else if( name.equals("neustar:trnData") )
		{
			return EppResponseDataNeustarTransaction::fromXML(*node);
		}
#if defined(RTK_SUPPORT_XRI)		
		else if( name.equals("xriCS:chkData") )
		{
			return EppResponseDataCheckXriCodeString::fromXML(*node);
		}
		else if( name.equals("xriCS:creData") )
		{
			return EppResponseDataCreateXriCodeString::fromXML(*node);
		}
		else if( name.equals("xriCS:infData") )
		{
			return EppResponseDataInfo::fromXML(*node);
		}
#endif
		/*
		 * other object types
		 */
	}

	return null;
}
