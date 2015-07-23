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
package com.neulevel.epp.core;

import java.util.*;

/**
 * This <code>EppError</code> class defines EPP result/error codes and messages.
 * For a complete definitions of EPP error codes and messages, please refer
 * the EPP Specification, RFC 3730.
 *
 * <P><B>Warning</B>: EPP result code 1301 has been assigned with a fixed text
 * message in EPP-1.0.  Sucessful responeses for pending actions should use
 * EPP result code 1001.  Also, the meaning of EPP result code 2501 has been changed
 * in EPP-1.0.
<pre>
   EPP result codes are based on the theory of reply codes described in
   section 4.2.1 of [RFC2821].  EPP uses four decimal digits to describe
   the success or failure of each EPP command.  Each of the digits of
   the reply have special significance.

   The first digit denotes command success or failure.  The second digit
   denotes the response category, such as command syntax or security.
   The third and fourth digits provide explicit response detail within
   each response category.

   There are two values for the first digit of the reply code:

   1yzz   Positive completion reply.  The command has been accepted and
   processed by the system without error.

   2yzz   Negative completion reply.  The command was not accepted and
   the requested action did not occur.

   The second digit groups responses into one of six specific
   categories:

   x0zz   Protocol Syntax
   x1zz   Implementation-specific Rules
   x2zz   Security
   x3zz   Data Management
   x4zz   Server System
   x5zz   Connection Management

   The third and fourth digits provide response detail within the
   categories defined by the first and second digits.  Specific result
   codes are listed in the table below.

   Every EPP response MUST include a result code and a human-readable
   description of the result code.  The language used to represent the
   description MAY be identified using an instance of the "lang"
   attribute within the <msg> element.  If not specified, the default
   language is English, identified as "en".  A description of the
   structure of valid values for the "lang" attribute is described in
   [RFC3066].

   Response text MAY be translated into other languages, though the
   translation MUST preserve the meaning of the code as described here.
   Response code values MUST NOT be changed when translating text.

   Response text in the table below is enclosed in quotes to clearly
   mark the beginning and ending of each response string.  Quotes MUST
   NOT be used to delimit these strings when returning response text via
   the protocol.

   Successful command completion responses:

   Code    Response text in English
   ___________________________________

   1000    "Command completed successfully"
   This is the usual response code for a successfully completed command
   that is not addressed by any other 1xxx-series response code.

   1001    "Command completed successfully; action pending"
   This response code MUST be returned when responding to a command the
   requires offline activity before the requested action can be
   completed.  See section 2 for a description of other processing
   requirements.

   1300    "Command completed successfully; no messages"
   This response code MUST be returned when responding to a <poll>
   request command and the server message queue is empty.

   1301    "Command completed successfully; ack to dequeue"
   This response code MUST be returned when responding to a <poll>
   request command and a message has been retrieved from the server
   message queue.

   1500    "Command completed successfully; ending session"
   This response code MUST be returned when responding to a successful
   <logout> command.

   Command error responses:

   Code    Response text in English
   ___________________________________

   2000    "Unknown command"
   This response code MUST be returned when a server receives a command
   element that is not defined by EPP.

   2001    "Command syntax error"
   This response code MUST be returned when a server receives an
   improperly formed command element.

   2002    "Command use error"
   This response code MUST be returned when a server receives a properly
   formed command element, but the command can not be executed due to a
   sequencing or context error.  For example, a <logout> command can not
   be executed without having first completed a <login> command.

   2003    "Required parameter missing"
   This response code MUST be returned when a server receives a command
   for which a required parameter value has not been provided.

   2004    "Parameter value range error"
   This response code MUST be returned when a server receives a command
   parameter whose value is outside the range of values specified by the
   protocol.  The error value SHOULD be returned via a <value> element
   in the EPP response.

   2005    "Parameter value syntax error"
   This response code MUST be returned when a server receives a command
   containing a parameter whose value is improperly formed.  The error
   value SHOULD be returned via a <value> element in the EPP response.

   2100    "Unimplemented protocol version"
   This response code MUST be returned when a server receives a command
   element specifying a protocol version that is not implemented by the
   server.

   2101    "Unimplemented command"
   This response code MUST be returned when a server receives a valid
   EPP command element that is not implemented by the server.  For
   example, a <transfer> command can be unimplemented for certain object
   types.

   2102    "Unimplemented option"
   This response code MUST be returned when a server receives a valid
   EPP command element that contains a protocol option that is not
   implemented by the server.

   2103    "Unimplemented extension"
   This response code MUST be returned when a server receives a valid
   EPP command element that contains a protocol command extension that
   is not implemented by the server.

   2104    "Billing failure"
   This response code MUST be returned when a server attempts to execute
   a billable operation and the command can not be completed due to a
   client billing failure.

   2105    "Object is not eligible for renewal"
   This response code MUST be returned when a client attempts to <renew>
   an object that is not eligible for renewal in accordance with server
   policy.

   2106    "Object is not eligible for transfer"
   This response code MUST be returned when a client attempts to
   <transfer> an object that is not eligible for transfer in accordance
   with server policy.

   2200    "Authentication error"
   This response code MUST be returned when a server notes an error when
   validating client credentials.

   2201    "Authorization error"
   This response code MUST be returned when a server notes a client
   authorization error when executing a command.  This error is used to
   note that a client lacks privileges to execute the requested command.

   2202    "Invalid authorization information"
   This response code MUST be returned when a server receives invalid
   command authorization information required to confirm authorization
   to execute a command.  This error is used to note that a client has
   the privileges required to execute the requested command, but the
   authorization information provided by the client does not match the
   authorization information archived by the server.

   2300    "Object pending transfer"
   This response code MUST be returned when a server receives a command
   to transfer an object that is pending transfer due to an earlier
   transfer request.

   2301    "Object not pending transfer"
   This response code MUST be returned when a server receives a command
   to confirm, reject, or cancel the transfer an object when no command
   has been made to transfer the object.

   2302    "Object exists"
   This response code MUST be returned when a server receives a command
   to create an object that already exists in the repository.

   2303    "Object does not exist"
   This response code MUST be returned when a server receives a command
   to query or transform an object that does not exist in the
   repository.

   2304    "Object status prohibits operation"
   This response code MUST be returned when a server receives a command
   to transform an object that can not be completed due to server policy
   or business practices.  For example, a server can disallow <transfer>
   commands under terms and conditions that are matters of local policy,
   or the server might have received a <delete> command for an object
   whose status prohibits deletion.

   2305    "Object association prohibits operation"
   This response code MUST be returned when a server receives a command
   to transform an object that can not be completed due to dependencies
   on other objects that are associated with the target object.  For
   example, a server can disallow <delete> commands while an object has
   active associations with other objects.

   2306    "Parameter value policy error"
   This response code MUST be returned when a server receives a command
   containing a parameter value that is syntactically valid, but
   semantically invalid due to local policy.  For example, the server
   can support a subset of a range of valid protocol parameter values.
   The error value SHOULD be returned via a <value> element in the EPP
   response.

   2307    "Unimplemented object service"
   This response code MUST be returned when a server receives a command
   to operate on an object service that is not supported by the server.

   2308    "Data management policy violation"
   This response code MUST be returned when a server receives a command
   whose execution results in a violation of server data management
   policies.  For example, removing all attribute values or object
   associations from an object might be a violation of a server's data
   management policies.

   2400    "Command failed"
   This response code MUST be returned when a server is unable to
   execute a command due to an internal server error that is not related
   to the protocol.  The failure can be transient.  The server MUST keep
   any ongoing session active.

   2500    "Command failed; server closing connection"
   This response code MUST be returned when a server receives a command
   that can not be completed due to an internal server error that is not
   related to the protocol.  The failure is not transient, and will
   cause other commands to fail as well.  The server MUST end the active
   session and close the existing connection.

   2501    "Authentication error; server closing connection"
   This response code MUST be returned when a server notes an error when
   validating client credentials and a server-defined limit on the
   number of allowable failures has been exceeded.  The server MUST
   close the existing connection.

   2502    "Session limit exceeded; server closing connection"
   This response code MUST be returned when a server receives a <login>
   command, and the command can not be completed because the client has
   exceeded a system-defined limit on the number of sessions that the
   client can establish.  It might be possible to establish a session by
   ending existing unused sessions and closing inactive connections.

</pre>

 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:38 $
 */
public class EppError
{
	/**
	 * Result code 1000 for "Command completed successfully"
	 */
	public static final int    CODE_NO_ERROR = 1000;
	/**
	 * Result text for "Command completed successfully"
	 */
	public static final String TEXT_NO_ERROR = "Command completed successfully";
	/**
	 * Result code 1001 for "Command completed successfully; action pending"
	 */
	public static final int    CODE_ACTION_PENDING = 1001;
	/**
	 * Result text for "Command completed successfully; action pending"
	 */
	public static final String TEXT_ACTION_PENDING = "Command completed successfully; action pending";
	/**
	 * Result code 1300 for "Command completed successfully; no messages"
	 */
	public static final int    CODE_NO_ERROR_NO_MESSAGE = 1300;
	/**
	 * Result text for "Command completed successfully; no messages"
	 */
	public static final String TEXT_NO_ERROR_NO_MESSAGE = "Command completed successfully; no messages";
	/**
	 * Result code 1301 for "Command completed successfully; ack to dequeue"
	 */
	public static final int    CODE_NO_ERROR_MESSAGE_PRESENT = 1301;
	/**
	 * Result text for "Command completed successfully; ack to dequeue"
	 */
	public static final String TEXT_NO_ERROR_MESSAGE_PRESENT = "Command completed successfully; ack to dequeue";
	/**
	 * Result code 1302 for "Command completed successfully; nothing done"
	 * @deprecated for EPP-04 (06/29/2001)
	 */
	public static final int    CODE_NO_ERROR_NOTHING_DONE = 1302;
	/**
	 * Result text for "Command completed successfully; nothing done"
	 * @deprecated for EPP-04 (06/29/2001)
	 */
	public static final String TEXT_NO_ERROR_NOTHING_DONE = "Command completed successfully; nothing done";
	/**
	 * Result code 1500 for "Command completed successfully; ending session"
	 */
	public static final int    CODE_NO_ERROR_ENDING_SESSION = 1500;
	/**
	 * Result text for "Command completed successfully; ending session"
	 */
	public static final String TEXT_NO_ERROR_ENDING_SESSION = "Command completed successfully; ending session";

	/**
	 * Result code 2000 for "Unknown command"
	 */
	public static final int    CODE_UNKNOWN_COMMAND = 2000;
	/**
	 * Result text for "Unknown command"
	 */
	public static final String TEXT_UNKNOWN_COMMAND = "Unknown command";
	/**
	 * Result code 2001 for "Command syntax error"
	 */
	public static final int    CODE_COMMAND_SYNTAX_ERROR = 2001;
	/**
	 * Result text for "Command syntax error"
	 */
	public static final String TEXT_COMMAND_SYNTAX_ERROR = "Command syntax error";
	/**
	 * Result code 2002 for "Command use error"
	 */
	public static final int    CODE_COMMAND_USE_ERROR = 2002;
	/**
	 * Result text for "Command use error"
	 */
	public static final String TEXT_COMMAND_USE_ERROR = "Command use error";
	/**
	 * Result code 2003 for "Required parameter missing"
	 */
	public static final int    CODE_REQUIRED_PARAMETER_MISSING = 2003;
	/**
	 * Result text for "Required parameter missing"
	 */
	public static final String TEXT_REQUIRED_PARAMETER_MISSING = "Required parameter missing";
	/**
	 * Result code 2004 for "Parameter value range error"
	 */
	public static final int    CODE_PARAMETER_VALUE_RANGE_ERROR = 2004;
	/**
	 * Result text for "Parameter value range error"
	 */
	public static final String TEXT_PARAMETER_VALUE_RANGE_ERROR = "Parameter value range error";
	/**
	 * Result code 2005 for "Parameter value syntax error"
	 */
	public static final int    CODE_PARAMETER_VALUE_SYNTAX_ERROR = 2005;
	/**
	 * Result text for "Parameter value syntax error"
	 */
	public static final String TEXT_PARAMETER_VALUE_SYNTAX_ERROR = "Parameter value syntax error";
	/**
	 * Result code 2100 for "Unimplemented protocol version"
	 */
	public static final int    CODE_UNIMPLEMENTED_PROTOCOL_VERSION = 2100;
	/**
	 * Result text for "Unimplemented protocol version"
	 */
	public static final String TEXT_UNIMPLEMENTED_PROTOCOL_VERSION = "Unimplemented protocol version";
	/**
	 * Result code 2101 for "Unimplemented command"
	 */
	public static final int    CODE_UNIMPLEMENTED_COMMAND = 2101;
	/**
	 * Result text for "Unimplemented command"
	 */
	public static final String TEXT_UNIMPLEMENTED_COMMAND = "Unimplemented command";
	/**
	 * Result code 2102 for "Unimplemented option"
	 */
	public static final int    CODE_UNIMPLEMENTED_OPTION = 2102;
	/**
	 * Result text for "Unimplemented option"
	 */
	public static final String TEXT_UNIMPLEMENTED_OPTION = "Unimplemented option";
	/**
	 * Result code 2103 for "Unimplemented extension"
	 */
	public static final int    CODE_UNIMPLEMENTED_EXTENSION = 2103;
	/**
	 * Result text for "Unimplemented extension"
	 */
	public static final String TEXT_UNIMPLEMENTED_EXTENSION = "Unimplemented extension";
	/**
	 * Result code 2104 for "Billing failure"
	 */
	public static final int    CODE_BILLING_FAILURE = 2104;
	/**
	 * Result text for "Billing failure"
	 */
	public static final String TEXT_BILLING_FAILURE = "Billing failure";
	/**
	 * Result code 2105 for "Object is not eligible for renewal"
	 */
	public static final int    CODE_OBJECT_IS_NOT_ELIGIBLE_FOR_RENEWAL = 2105;
	/**
	 * Result text for "Object is not eligible for renewal"
	 */
	public static final String TEXT_OBJECT_IS_NOT_ELIGIBLE_FOR_RENEWAL = "Object is not eligible for renewal";
	/**
	 * Result code 2106 for "Object is not eligible for transfer"
	 */
	public static final int    CODE_OBJECT_IS_NOT_ELIGIBLE_FOR_TRANSFER = 2106;
	/**
	 * Result text for "Object is not eligible for transfer"
	 */
	public static final String TEXT_OBJECT_IS_NOT_ELIGIBLE_FOR_TRANSFER = "Object is not eligible for transfer";
	/**
	 * Result code 2200 for "Authentication error"
	 */
	public static final int    CODE_AUTHENTICATION_ERROR = 2200;
	/**
	 * Result text for "Authentication error"
	 */
	public static final String TEXT_AUTHENTICATION_ERROR = "Authentication error";
	/**
	 * Result code 2201 for "Authorization error"
	 */
	public static final int    CODE_AUTHORIZATION_ERROR = 2201;
	/**
	 * Result text for "Authorization error"
	 */
	public static final String TEXT_AUTHORIZATION_ERROR = "Authorization error";
	/**
	 * Result code 2202 for "Invalid authorization information"
	 */
	public static final int    CODE_INVALID_AUTHORIZATION_INFORMATION = 2202;
	/**
	 * Result text for "Invalid authorization information"
	 */
	public static final String TEXT_INVALID_AUTHORIZATION_INFORMATION = "Invalid authorization information";
	/**
	 * Result code 2300 for "Object pending transfer"
	 */
	public static final int    CODE_OBJECT_PENDING_TRANSFER = 2300;
	/**
	 * Result text for "Object pending transfer"
	 */
	public static final String TEXT_OBJECT_PENDING_TRANSFER = "Object pending transfer";
	/**
	 * Result code 2301 for "Object not pending transfer"
	 */
	public static final int    CODE_OBJECT_NOT_PENDING_TRANSFER = 2301;
	/**
	 * Result text for "Object not pending transfer"
	 */
	public static final String TEXT_OBJECT_NOT_PENDING_TRANSFER = "Object not pending transfer";
	/**
	 * Result code 2302 for "Object exists"
	 */
	public static final int    CODE_OBJECT_EXISTS = 2302;
	/**
	 * Result text for "Object exists"
	 */
	public static final String TEXT_OBJECT_EXISTS = "Object exists";
	/**
	 * Result code 2303 for "Object does not exist"
	 */
	public static final int    CODE_OBJECT_DOES_NOT_EXIST = 2303;
	/**
	 * Result text for "Object does not exist"
	 */
	public static final String TEXT_OBJECT_DOES_NOT_EXIST = "Object does not exist";
	/**
	 * Result code 2304 for "Object status prohibits operation"
	 */
	public static final int    CODE_OBJECT_STATUS_PROHIBITS_OPERATION = 2304;
	/**
	 * Result text for "Object status prohibits operation"
	 */
	public static final String TEXT_OBJECT_STATUS_PROHIBITS_OPERATION = "Object status prohibits operation";
	/**
	 * Result code 2305 for "Object association prohibits operation"
	 */
	public static final int    CODE_OBJECT_ASSOCIATION_PROHIBITS_OPERATION = 2305;
	/**
	 * Result text for "Object association prohibits operation"
	 */
	public static final String TEXT_OBJECT_ASSOCIATION_PROHIBITS_OPERATION = "Object association prohibits operation";
	/**
	 * Result code 2306 for "Parameter value policy error"
	 */
	public static final int    CODE_PARAMETER_VALUE_POLICY_ERROR = 2306;
	/**
	 * Result text for "Parameter value policy error"
	 */
	public static final String TEXT_PARAMETER_VALUE_POLICY_ERROR = "Parameter value policy error";
	/**
	 * Result code 2307 for "Unimplemented object service"
	 */
	public static final int    CODE_UNIMPLEMENTED_OBJECT_SERVICE = 2307;
	/**
	 * Result text for "Unimplemented object service"
	 */
	public static final String TEXT_UNIMPLEMENTED_OBJECT_SERVICE = "Unimplemented object service";
	/**
	 * Result code 2308 for "Data management policy violation"
	 */
	public static final int    CODE_DATA_MANAGEMENT_POLICY_VIOLATION = 2308;
	/**
	 * Result text for "Data management policy violation"
	 */
	public static final String TEXT_DATA_MANAGEMENT_POLICY_VIOLATION = "Data management policy violation";
	/**
	 * Result code 2400 for "Command failed"
	 */
	public static final int    CODE_COMMAND_FAILED = 2400;
	/**
	 * Result text for "Command failed"
	 */
	public static final String TEXT_COMMAND_FAILED = "Command failed";
	/**
	 * Result code 2500 for "Command failed; server ending session"
	 */
	public static final int    CODE_COMMAND_FAILED_SERVER_ENDING_SESSION = 2500;
	/**
	 * Result text for "Command failed; server ending session"
	 */
	public static final String TEXT_COMMAND_FAILED_SERVER_ENDING_SESSION = "Command failed; server ending session";
	/**
	 * Result code 2501 for "Authentication error; server closing connection"
	 */
	public static final int    CODE_AUTHENTICATION_ERROR_SERVER_CLOSING_CONNECTION = 2501;
	/**
	 * Result text for "Authentication error; server closing connection"
	 */
	public static final String TEXT_AUTHENTICATION_ERROR_SERVER_CLOSING_CONNECTION = "Authentication error; server closing connection";
	/**
	 * Result code 2502 for "Session limit exceeded; server closing connection"
	 */
	public static final int    CODE_SESSION_LIMIT_EXCEEDED_SERVER_CLOSING_CONNECTION = 2502;
	/**
	 * Result text for "Session limit exceeded; server closing connection"
	 */
	public static final String TEXT_SESSION_LIMIT_EXCEEDED_SERVER_CLOSING_CONNECTION = "Session limit exceeded; server closing connection";
	/*
	 * more to be defined
	 */

	private static HashMap codeMap;

	static
	{
		codeMap = new HashMap();
		codeMap.put(new Integer(CODE_NO_ERROR),
		                        TEXT_NO_ERROR);
		codeMap.put(new Integer(CODE_ACTION_PENDING),
		                        TEXT_ACTION_PENDING);
		codeMap.put(new Integer(CODE_NO_ERROR_NO_MESSAGE),
		                        TEXT_NO_ERROR_NO_MESSAGE);
		codeMap.put(new Integer(CODE_NO_ERROR_MESSAGE_PRESENT),
		                        TEXT_NO_ERROR_MESSAGE_PRESENT);
		codeMap.put(new Integer(CODE_NO_ERROR_NOTHING_DONE),
		                        TEXT_NO_ERROR_NOTHING_DONE);
		codeMap.put(new Integer(CODE_NO_ERROR_ENDING_SESSION),
		                        TEXT_NO_ERROR_ENDING_SESSION);
		codeMap.put(new Integer(CODE_UNKNOWN_COMMAND),
		                        TEXT_UNKNOWN_COMMAND);
		codeMap.put(new Integer(CODE_COMMAND_SYNTAX_ERROR),
		                        TEXT_COMMAND_SYNTAX_ERROR);
		codeMap.put(new Integer(CODE_COMMAND_USE_ERROR),
		                        TEXT_COMMAND_USE_ERROR);
		codeMap.put(new Integer(CODE_REQUIRED_PARAMETER_MISSING),
		                        TEXT_REQUIRED_PARAMETER_MISSING);
		codeMap.put(new Integer(CODE_PARAMETER_VALUE_RANGE_ERROR),
		                        TEXT_PARAMETER_VALUE_RANGE_ERROR);
		codeMap.put(new Integer(CODE_PARAMETER_VALUE_SYNTAX_ERROR),
		                        TEXT_PARAMETER_VALUE_SYNTAX_ERROR);
		codeMap.put(new Integer(CODE_UNIMPLEMENTED_PROTOCOL_VERSION),
		                        TEXT_UNIMPLEMENTED_PROTOCOL_VERSION);
		codeMap.put(new Integer(CODE_UNIMPLEMENTED_COMMAND),
		                        TEXT_UNIMPLEMENTED_COMMAND);
		codeMap.put(new Integer(CODE_UNIMPLEMENTED_OPTION),
		                        TEXT_UNIMPLEMENTED_OPTION);
		codeMap.put(new Integer(CODE_UNIMPLEMENTED_EXTENSION),
		                        TEXT_UNIMPLEMENTED_EXTENSION);
		codeMap.put(new Integer(CODE_BILLING_FAILURE),
		                        TEXT_BILLING_FAILURE);
		codeMap.put(new Integer(CODE_OBJECT_IS_NOT_ELIGIBLE_FOR_RENEWAL),
		                        TEXT_OBJECT_IS_NOT_ELIGIBLE_FOR_RENEWAL);
		codeMap.put(new Integer(CODE_OBJECT_IS_NOT_ELIGIBLE_FOR_TRANSFER),
		                        TEXT_OBJECT_IS_NOT_ELIGIBLE_FOR_TRANSFER);
		codeMap.put(new Integer(CODE_AUTHENTICATION_ERROR),
		                        TEXT_AUTHENTICATION_ERROR);
		codeMap.put(new Integer(CODE_AUTHORIZATION_ERROR),
		                        TEXT_AUTHORIZATION_ERROR);
		codeMap.put(new Integer(CODE_INVALID_AUTHORIZATION_INFORMATION),
		                        TEXT_INVALID_AUTHORIZATION_INFORMATION);
		codeMap.put(new Integer(CODE_OBJECT_PENDING_TRANSFER),
		                        TEXT_OBJECT_PENDING_TRANSFER);
		codeMap.put(new Integer(CODE_OBJECT_NOT_PENDING_TRANSFER),
		                        TEXT_OBJECT_NOT_PENDING_TRANSFER);
		codeMap.put(new Integer(CODE_OBJECT_EXISTS),
		                        TEXT_OBJECT_EXISTS);
		codeMap.put(new Integer(CODE_OBJECT_DOES_NOT_EXIST),
		                        TEXT_OBJECT_DOES_NOT_EXIST);
		codeMap.put(new Integer(CODE_OBJECT_STATUS_PROHIBITS_OPERATION),
		                        TEXT_OBJECT_STATUS_PROHIBITS_OPERATION);
		codeMap.put(new Integer(CODE_OBJECT_ASSOCIATION_PROHIBITS_OPERATION),
		                        TEXT_OBJECT_ASSOCIATION_PROHIBITS_OPERATION);
		codeMap.put(new Integer(CODE_PARAMETER_VALUE_POLICY_ERROR),
		                        TEXT_PARAMETER_VALUE_POLICY_ERROR);
		codeMap.put(new Integer(CODE_UNIMPLEMENTED_OBJECT_SERVICE),
		                        TEXT_UNIMPLEMENTED_OBJECT_SERVICE);
		codeMap.put(new Integer(CODE_DATA_MANAGEMENT_POLICY_VIOLATION),
		                        TEXT_DATA_MANAGEMENT_POLICY_VIOLATION);
		codeMap.put(new Integer(CODE_COMMAND_FAILED),
		                        TEXT_COMMAND_FAILED);
		codeMap.put(new Integer(CODE_COMMAND_FAILED_SERVER_ENDING_SESSION),
		                        TEXT_COMMAND_FAILED_SERVER_ENDING_SESSION);
		codeMap.put(new Integer(CODE_AUTHENTICATION_ERROR_SERVER_CLOSING_CONNECTION),
		                        TEXT_AUTHENTICATION_ERROR_SERVER_CLOSING_CONNECTION);
		codeMap.put(new Integer(CODE_SESSION_LIMIT_EXCEEDED_SERVER_CLOSING_CONNECTION),
		                        TEXT_SESSION_LIMIT_EXCEEDED_SERVER_CLOSING_CONNECTION);
	}

	/**
	 * Converts a result code into text, or null if the code is not defined
	 */
	public static String getText( int code )
	{
		Object object = codeMap.get(new Integer(code));
		if( (object != null) && (object instanceof String) )
		{
			return (String) object;
		}
		return null;
	}

	/**
	 * Checks if a result code is valid
	 */
	public static boolean isValid( int code )
	{
		return getText(code) != null;
	}

	/**
	 * Check if a result code is an error code
	 */
	public static boolean isError( int code )
	{
		return code >= 2000;
	}
}
/**
1000	"Command Completed successfully"
1001	"Command completed successfully; action pending"
1300	"Command completed successfully; no messages"
1301	"Command completed successfully; ack to dequeue"
1302	"Command completed successfully; nothing done"
1500	"Command completed successfully; ending session"
2000	"Unknown command"
2001	"Command syntax error"
2002	"Command use error"
2003	"Required parameter missing"
2003	"Required parameter missing"
2004	"Parameter value range error"
2005	"Parameter value syntax error"
2100	"Unimplemented protocol version"
2101	"Unimplemented command"
2102	"Unimplemented option"
2103	"Unimplemented extension"
2104	"Billing failure"
2105	"Object is not eligible for renewal"
2106	"Object is not eligible for transfer"
2200	"Authentication error"
2201	"Authorization error"
2202	"Invalid authorization information"
2300	"Object pending transfer"
2301	"Object not pending transfer"
2302	"Object exists"
2303	"Object does not exist"
2304	"Object status prohibits operation"
2305	"Object association prohibits operation"
2306	"Parameter value policy error"
2307	"Unimplemented object service"
2308	"Data management policy violation"
2400	"Command failed"
2500	"Command failed; server ending session"
2501	"Timeout; server ending session"
2501	"Authentication error; server closing connection"
2502	"Session limit exceeded; server closing connection"
*/
