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
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.w3c.dom.Node;

import com.neulevel.epp.core.EppError;
import com.neulevel.epp.core.EppParser;
import com.neulevel.epp.core.EppTransactionId;
import com.neulevel.epp.core.response.EppReason;
import com.neulevel.epp.core.response.EppResponse;
import com.neulevel.epp.core.response.EppResult;
import com.neulevel.epp.core.response.EppValueReason;
import com.neulevel.epp.ext.EppChangePollExt;
import com.neulevel.epp.ext.EppChangePollOperation;


public class EppPollResponseTest {

	public static void main(String[] args) {
		Node epp;
		EppResponse res;
		EppResponse newres;
		EppResult result;
		EppTransactionId xid;

		res = new EppResponse();
		result = new EppResult(EppError.CODE_NO_ERROR, EppError.TEXT_NO_ERROR);
		result.addValue("Value 1");
		result.addValue("Value 2");
		result.addValue("Value 3");
		result.addValue("Value 4");
		result.addValue(new EppValueReason("Value 5", "Reason 5"));
		result.addValue("Value 6");
		result.addValue(new EppValueReason("Value 7", "Reason 7"));
		res.addResult(result);
		xid = new EppTransactionId("ABC-DEF-12345", "SERVER-ID-9999");
		res.setTransactionId(xid);
		res.setMessageId("ABC123XYZ");
		res.setMessageQueued(99);
		res.setMessageQueueUpdated(Calendar.getInstance());

		EppReason reason = new EppReason("reason", "en");

		EppChangePollOperation pollOperation = new EppChangePollOperation("delete", "purge");

		EppChangePollExt pollExt = new EppChangePollExt("before",pollOperation, new GregorianCalendar(), "neustar", "abc-1234",reason);

		res.addExtension(pollExt);

		System.out.println(res);

		EppParser parser = new EppParser(res.toString(),"D://gitrepo//rtk_tld//src//java//xsd");
		if (parser.hasError()) {
			System.out.println(parser.getResult());
			System.exit(1);
		}

		epp = parser.getRootNode();
		newres = (EppResponse) EppResponse.fromXML(epp);
		if (newres == null) {
			System.out.println("Error in fromXML");
			System.exit(1);
		}
		System.out.println(newres);

		System.out.println(newres.toString().equals(res.toString()));
	}
}
