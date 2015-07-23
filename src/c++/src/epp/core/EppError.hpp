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

#if ! defined(EPPERROR_HPP)    /* { */
#define       EPPERROR_HPP        1

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
 * @version $Revision: 1.2 $ $Date: 2006/03/01 01:35:37 $
 */
class EPP_EXPORT EppError
{
public:
	/**
	 * Result code 1000 for "Command Completed successfully"
	 */
	static const int    CODE_NO_ERROR;
	/**
	 * Result text for "Command Completed successfully"
	 */
	static const char * TEXT_NO_ERROR;
	/**
	 * Result code 1001 for ""Command completed successfully; action pending"
	 */
	static const int    CODE_ACTION_PENDING;
	/**
	 * Result text 1001 for ""Command completed successfully; action pending"
	 */
	static const char * TEXT_ACTION_PENDING;
	/**
	 * Result code 1300 for "Command completed successfully; no messages"
	 */
	static const int    CODE_NO_ERROR_NO_MESSAGE;
	/**
	 * Result text for "Command completed successfully; no messages"
	 */
	static const char * TEXT_NO_ERROR_NO_MESSAGE;
	/**
	 * Result code 1301 for "Command completed successfully; ack to dequeue"
	 */
	static const int    CODE_NO_ERROR_MESSAGE_PRESENT;
	/**
	 * Result text for "Command completed successfully; ack to dequeue"
	 */
	static const char * TEXT_NO_ERROR_MESSAGE_PRESENT;
	/**
	 * Result code 1302 for "Command completed successfully; nothing done"
	 * @deprecated for EPP-04 (06/29/2001)
	 */
	static const int    CODE_NO_ERROR_NOTHING_DONE;
	/**
	 * Result text for "Command completed successfully; nothing done"
	 * @deprecated for EPP-04 (06/29/2001)
	 */
	static const char * TEXT_NO_ERROR_NOTHING_DONE;
	/**
	 * Result code 1500 for "Command completed successfully; ending session"
	 */
	static const int    CODE_NO_ERROR_ENDING_SESSION;
	/**
	 * Result text for "Command completed successfully; ending session"
	 */
	static const char * TEXT_NO_ERROR_ENDING_SESSION;

	/**
	 * Result code 2000 for "Unknown command"
	 */
	static const int    CODE_UNKNOWN_COMMAND;
	/**
	 * Result text for "Unknown command"
	 */
	static const char * TEXT_UNKNOWN_COMMAND;
	/**
	 * Result code 2001 for "Command syntax error"
	 */
	static const int    CODE_COMMAND_SYNTAX_ERROR;
	/**
	 * Result text for "Command syntax error"
	 */
	static const char * TEXT_COMMAND_SYNTAX_ERROR;
	/**
	 * Result code 2002 for "Command use error"
	 */
	static const int    CODE_COMMAND_USE_ERROR;
	/**
	 * Result text for "Command use error"
	 */
	static const char * TEXT_COMMAND_USE_ERROR;
	/**
	 * Result code 2003 for "Required parameter missing"
	 */
	static const int    CODE_REQUIRED_PARAMETER_MISSING;
	/**
	 * Result text for "Required parameter missing"
	 */
	static const char * TEXT_REQUIRED_PARAMETER_MISSING;
	/**
	 * Result code 2004 for "Parameter value range error"
	 */
	static const int    CODE_PARAMETER_VALUE_RANGE_ERROR;
	/**
	 * Result text for "Parameter value range error"
	 */
	static const char * TEXT_PARAMETER_VALUE_RANGE_ERROR;
	/**
	 * Result code 2005 for "Parameter value syntax error"
	 */
	static const int    CODE_PARAMETER_VALUE_SYNTAX_ERROR;
	/**
	 * Result text for "Parameter value syntax error"
	 */
	static const char * TEXT_PARAMETER_VALUE_SYNTAX_ERROR;
	/**
	 * Result code 2100 for "Unimplemented protocol version"
	 */
	static const int    CODE_UNIMPLEMENTED_PROTOCOL_VERSION;
	/**
	 * Result text for "Unimplemented protocol version"
	 */
	static const char * TEXT_UNIMPLEMENTED_PROTOCOL_VERSION;
	/**
	 * Result code 2101 for "Unimplemented command"
	 */
	static const int    CODE_UNIMPLEMENTED_COMMAND;
	/**
	 * Result text for "Unimplemented command"
	 */
	static const char * TEXT_UNIMPLEMENTED_COMMAND;
	/**
	 * Result code 2102 for "Unimplemented option"
	 */
	static const int    CODE_UNIMPLEMENTED_OPTION;
	/**
	 * Result text for "Unimplemented option"
	 */
	static const char * TEXT_UNIMPLEMENTED_OPTION;
	/**
	 * Result code 2103 for "Unimplemented extension"
	 */
	static const int    CODE_UNIMPLEMENTED_EXTENSION;
	/**
	 * Result text for "Unimplemented extension"
	 */
	static const char * TEXT_UNIMPLEMENTED_EXTENSION;
	/**
	 * Result code 2104 for "Billing failure"
	 */
	static const int    CODE_BILLING_FAILURE;
	/**
	 * Result text for "Billing failure"
	 */
	static const char * TEXT_BILLING_FAILURE;
	/**
	 * Result code 2105 for "Object is not eligible for renewal"
	 */
	static const int    CODE_OBJECT_IS_NOT_ELIGIBLE_FOR_RENEWAL;
	/**
	 * Result text for "Object is not eligible for renewal"
	 */
	static const char * TEXT_OBJECT_IS_NOT_ELIGIBLE_FOR_RENEWAL;
	/**
	 * Result code 2106 for "Object is not eligible for transfer"
	 */
	static const int    CODE_OBJECT_IS_NOT_ELIGIBLE_FOR_TRANSFER;
	/**
	 * Result text for "Object is not eligible for transfer"
	 */
	static const char * TEXT_OBJECT_IS_NOT_ELIGIBLE_FOR_TRANSFER;
	/**
	 * Result code 2200 for "Authentication error"
	 */
	static const int    CODE_AUTHENTICATION_ERROR;
	/**
	 * Result text for "Authentication error"
	 */
	static const char * TEXT_AUTHENTICATION_ERROR;
	/**
	 * Result code 2201 for "Authorization error"
	 */
	static const int    CODE_AUTHORIZATION_ERROR;
	/**
	 * Result text for "Authorization error"
	 */
	static const char * TEXT_AUTHORIZATION_ERROR;
	/**
	 * Result code 2202 for "Invalid authorization information"
	 */
	static const int    CODE_INVALID_AUTHORIZATION_INFORMATION;
	/**
	 * Result text for "Invalid authorization information"
	 */
	static const char * TEXT_INVALID_AUTHORIZATION_INFORMATION;
	/**
	 * Result code 2300 for "Object pending transfer"
	 */
	static const int    CODE_OBJECT_PENDING_TRANSFER;
	/**
	 * Result text for "Object pending transfer"
	 */
	static const char * TEXT_OBJECT_PENDING_TRANSFER;
	/**
	 * Result code 2301 for "Object not pending transfer"
	 */
	static const int    CODE_OBJECT_NOT_PENDING_TRANSFER;
	/**
	 * Result text for "Object not pending transfer"
	 */
	static const char * TEXT_OBJECT_NOT_PENDING_TRANSFER;
	/**
	 * Result code 2302 for "Object exists"
	 */
	static const int    CODE_OBJECT_EXISTS;
	/**
	 * Result text for "Object exists"
	 */
	static const char * TEXT_OBJECT_EXISTS;
	/**
	 * Result code 2303 for "Object does not exist"
	 */
	static const int    CODE_OBJECT_DOES_NOT_EXIST;
	/**
	 * Result text for "Object does not exist"
	 */
	static const char * TEXT_OBJECT_DOES_NOT_EXIST;
	/**
	 * Result code 2304 for "Object status prohibits operation"
	 */
	static const int    CODE_OBJECT_STATUS_PROHIBITS_OPERATION;
	/**
	 * Result text for "Object status prohibits operation"
	 */
	static const char * TEXT_OBJECT_STATUS_PROHIBITS_OPERATION;
	/**
	 * Result code 2305 for "Object association prohibits operation"
	 */
	static const int    CODE_OBJECT_ASSOCIATION_PROHIBITS_OPERATION;
	/**
	 * Result text for "Object association prohibits operation"
	 */
	static const char * TEXT_OBJECT_ASSOCIATION_PROHIBITS_OPERATION;
	/**
	 * Result code 2306 for "Parameter value policy error"
	 */
	static const int    CODE_PARAMETER_VALUE_POLICY_ERROR;
	/**
	 * Result text for "Parameter value policy error"
	 */
	static const char * TEXT_PARAMETER_VALUE_POLICY_ERROR;
	/**
	 * Result code 2307 for "Unimplemented object service"
	 */
	static const int    CODE_UNIMPLEMENTED_OBJECT_SERVICE;
	/**
	 * Result text for "Unimplemented object service"
	 */
	static const char * TEXT_UNIMPLEMENTED_OBJECT_SERVICE;
	/**
	 * Result code 2308 for "Data management policy violation"
	 */
	static const int    CODE_DATA_MANAGEMENT_POLICY_VIOLATION;
	/**
	 * Result text for "Data management policy violation"
	 */
	static const char * TEXT_DATA_MANAGEMENT_POLICY_VIOLATION;
	/**
	 * Result code 2400 for "Command failed"
	 */
	static const int    CODE_COMMAND_FAILED;
	/**
	 * Result text for "Command failed"
	 */
	static const char * TEXT_COMMAND_FAILED;
	/**
	 * Result code 2500 for "Command failed; server ending session"
	 */
	static const int    CODE_COMMAND_FAILED_SERVER_ENDING_SESSION;
	/**
	 * Result text for "Command failed; server ending session"
	 */
	static const char * TEXT_COMMAND_FAILED_SERVER_ENDING_SESSION;
	/**
	 * Result code 2501 for "Authentication error; server closing connection"
	 */
	static const int    CODE_AUTHENTICATION_ERROR_SERVER_CLOSING_CONNECTION;
	/**
	 * Result text for "Authentication error; server closing connection"
	 */
	static const char * TEXT_AUTHENTICATION_ERROR_SERVER_CLOSING_CONNECTION;
	/**
	 * Result code 2502 for "Session limit exceeded; server closing connection"
	 */
	static const int    CODE_SESSION_LIMIT_EXCEEDED_SERVER_CLOSING_CONNECTION;
	/**
	 * Result text for "Session limit exceeded; server closing connection"
	 */
	static const char * TEXT_SESSION_LIMIT_EXCEEDED_SERVER_CLOSING_CONNECTION;
	/*
	 * more to be defined
	 */

	/**
	 * Converts a result code into text, or null if the code is not defined
	 */
	static const char * getText( int code );

	/**
	 * Checks if a result code is valid
	 */
	static bool isValid( int code );

	/**
	 * Check if a result code is an error code
	 */
	static bool isError( int code );
};

#endif     /* EPPERROR_HPP */  /* } */
