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
#include "EppError.hpp"

const static struct
{
	int          code;
	const char * text;
} codeMap[] = {
	{ 1000,	"Command completed successfully" },
	{ 1001,	"Command completed successfully; action pending" },
	{ 1300,	"Command completed successfully; no messages" },
	{ 1301,	"Command completed successfully; ack to dequeue" },
	{ 1302,	"Command completed successfully; nothing done" },
	{ 1500,	"Command completed successfully; ending session" },
	{ 2000,	"Unknown command" },
	{ 2001,	"Command syntax error" },
	{ 2002,	"Command use error" },
	{ 2003,	"Required parameter missing" },
	{ 2004,	"Parameter value range error" },
	{ 2005,	"Parameter value syntax error" },
	{ 2100,	"Unimplemented protocol version" },
	{ 2101,	"Unimplemented command" },
	{ 2102,	"Unimplemented option" },
	{ 2103,	"Unimplemented extension" },
	{ 2104,	"Billing failure" },
	{ 2105,	"Object is not eligible for renewal" },
	{ 2106,	"Object is not eligible for transfer" },
	{ 2200,	"Authentication error" },
	{ 2201,	"Authorization error" },
	{ 2202,	"Invalid authorization information" },
	{ 2300,	"Object pending transfer" },
	{ 2301,	"Object not pending transfer" },
	{ 2302,	"Object exists" },
	{ 2303,	"Object does not exist" },
	{ 2304,	"Object status prohibits operation" },
	{ 2305,	"Object association prohibits operation" },
	{ 2306,	"Parameter value policy error" },
	{ 2307,	"Unimplemented object service" },
	{ 2308,	"Data management policy violation" },
	{ 2400,	"Command failed" },
	{ 2500,	"Command failed; server ending session" },
	{ 2501,	"Authentication error; server closing connection" },
	{ 2502,	"Session limit exceeded; server closing connection" },
	{ 0000, null }
};

const int    EppError::CODE_NO_ERROR = 1000 ;
const int    EppError::CODE_ACTION_PENDING = 1001 ;
const int    EppError::CODE_NO_ERROR_NO_MESSAGE = 1300 ;
const int    EppError::CODE_NO_ERROR_MESSAGE_PRESENT = 1301 ;
const int    EppError::CODE_NO_ERROR_NOTHING_DONE = 1302 ;
const int    EppError::CODE_NO_ERROR_ENDING_SESSION = 1500 ;
const int    EppError::CODE_UNKNOWN_COMMAND = 2000 ;
const int    EppError::CODE_COMMAND_SYNTAX_ERROR = 2001 ;
const int    EppError::CODE_COMMAND_USE_ERROR = 2002 ;
const int    EppError::CODE_REQUIRED_PARAMETER_MISSING = 2003 ;
const int    EppError::CODE_PARAMETER_VALUE_RANGE_ERROR = 2004 ;
const int    EppError::CODE_PARAMETER_VALUE_SYNTAX_ERROR = 2005 ;
const int    EppError::CODE_UNIMPLEMENTED_PROTOCOL_VERSION = 2100 ;
const int    EppError::CODE_UNIMPLEMENTED_COMMAND = 2101 ;
const int    EppError::CODE_UNIMPLEMENTED_OPTION = 2102 ;
const int    EppError::CODE_UNIMPLEMENTED_EXTENSION = 2103 ;
const int    EppError::CODE_BILLING_FAILURE = 2104 ;
const int    EppError::CODE_OBJECT_IS_NOT_ELIGIBLE_FOR_RENEWAL = 2105 ;
const int    EppError::CODE_OBJECT_IS_NOT_ELIGIBLE_FOR_TRANSFER = 2106 ;
const int    EppError::CODE_AUTHENTICATION_ERROR = 2200 ;
const int    EppError::CODE_AUTHORIZATION_ERROR = 2201 ;
const int    EppError::CODE_INVALID_AUTHORIZATION_INFORMATION = 2202 ;
const int    EppError::CODE_OBJECT_PENDING_TRANSFER = 2300 ;
const int    EppError::CODE_OBJECT_NOT_PENDING_TRANSFER = 2301 ;
const int    EppError::CODE_OBJECT_EXISTS = 2302 ;
const int    EppError::CODE_OBJECT_DOES_NOT_EXIST = 2303 ;
const int    EppError::CODE_OBJECT_STATUS_PROHIBITS_OPERATION = 2304 ;
const int    EppError::CODE_OBJECT_ASSOCIATION_PROHIBITS_OPERATION = 2305 ;
const int    EppError::CODE_PARAMETER_VALUE_POLICY_ERROR = 2306 ;
const int    EppError::CODE_UNIMPLEMENTED_OBJECT_SERVICE = 2307 ;
const int    EppError::CODE_DATA_MANAGEMENT_POLICY_VIOLATION = 2308 ;
const int    EppError::CODE_COMMAND_FAILED = 2400 ;
const int    EppError::CODE_COMMAND_FAILED_SERVER_ENDING_SESSION = 2500 ;
const int    EppError::CODE_AUTHENTICATION_ERROR_SERVER_CLOSING_CONNECTION = 2501 ;
const int    EppError::CODE_SESSION_LIMIT_EXCEEDED_SERVER_CLOSING_CONNECTION = 2502 ;

const char * EppError::TEXT_NO_ERROR = "Command completed successfully";
const char * EppError::TEXT_ACTION_PENDING = "Command completed successfully; action pending";
const char * EppError::TEXT_NO_ERROR_NO_MESSAGE = "Command completed successfully; no messages";
const char * EppError::TEXT_NO_ERROR_MESSAGE_PRESENT = "Command completed successfully; message present";
const char * EppError::TEXT_NO_ERROR_NOTHING_DONE = "Command completed successfully; nothing done";
const char * EppError::TEXT_NO_ERROR_ENDING_SESSION = "Command completed successfully; ending session";
const char * EppError::TEXT_UNKNOWN_COMMAND = "Unknown command";
const char * EppError::TEXT_COMMAND_SYNTAX_ERROR = "Command syntax error";
const char * EppError::TEXT_COMMAND_USE_ERROR = "Command use error";
const char * EppError::TEXT_REQUIRED_PARAMETER_MISSING = "Required parameter missing";
const char * EppError::TEXT_PARAMETER_VALUE_RANGE_ERROR = "Parameter value range error";
const char * EppError::TEXT_PARAMETER_VALUE_SYNTAX_ERROR = "Parameter value syntax error";
const char * EppError::TEXT_UNIMPLEMENTED_PROTOCOL_VERSION = "Unimplemented protocol version";
const char * EppError::TEXT_UNIMPLEMENTED_COMMAND = "Unimplemented command";
const char * EppError::TEXT_UNIMPLEMENTED_OPTION = "Unimplemented option";
const char * EppError::TEXT_UNIMPLEMENTED_EXTENSION = "Unimplemented extension";
const char * EppError::TEXT_BILLING_FAILURE = "Billing failure";
const char * EppError::TEXT_OBJECT_IS_NOT_ELIGIBLE_FOR_RENEWAL = "Object is not eligible for renewal";
const char * EppError::TEXT_OBJECT_IS_NOT_ELIGIBLE_FOR_TRANSFER = "Object is not eligible for transfer";
const char * EppError::TEXT_AUTHENTICATION_ERROR = "Authentication error";
const char * EppError::TEXT_AUTHORIZATION_ERROR = "Authorization error";
const char * EppError::TEXT_INVALID_AUTHORIZATION_INFORMATION = "Invalid authorization information";
const char * EppError::TEXT_OBJECT_PENDING_TRANSFER = "Object pending transfer";
const char * EppError::TEXT_OBJECT_NOT_PENDING_TRANSFER = "Object not pending transfer";
const char * EppError::TEXT_OBJECT_EXISTS = "Object exists";
const char * EppError::TEXT_OBJECT_DOES_NOT_EXIST = "Object does not exist";
const char * EppError::TEXT_OBJECT_STATUS_PROHIBITS_OPERATION = "Object status prohibits operation";
const char * EppError::TEXT_OBJECT_ASSOCIATION_PROHIBITS_OPERATION = "Object association prohibits operation";
const char * EppError::TEXT_PARAMETER_VALUE_POLICY_ERROR = "Parameter value policy error";
const char * EppError::TEXT_UNIMPLEMENTED_OBJECT_SERVICE = "Unimplemented object service";
const char * EppError::TEXT_DATA_MANAGEMENT_POLICY_VIOLATION = "Data management policy violation";
const char * EppError::TEXT_COMMAND_FAILED = "Command failed";
const char * EppError::TEXT_COMMAND_FAILED_SERVER_ENDING_SESSION = "Command failed; server ending session";
const char * EppError::TEXT_AUTHENTICATION_ERROR_SERVER_CLOSING_CONNECTION = "Authentication error; server closing connection";
const char * EppError::TEXT_SESSION_LIMIT_EXCEEDED_SERVER_CLOSING_CONNECTION = "Session limit exceeded; server closing connection";

const char * EppError::getText( int code )
{
	for( int i = 0; codeMap[i].text != null; i++ )
	{
		if( code == codeMap[i].code )
		{
			return codeMap[i].text;
		}
	}
	return null;
}

bool EppError::isValid( int code )
{
	return getText(code) != null;
}

bool EppError::isError( int code )
{
	return code >= 2000;
}
