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
#include "BizLock.hpp"

const char * BizLock::ID                      = "BIZlock";
const char * BizLock::PARAM_DOMAIN            = "domain";
const char * BizLock::PARAM_DOMAIN_AUTHINFO   = "domainAuthInfo";
const char * BizLock::PARAM_BIZACCOUNT        = "bizAccount";
const char * BizLock::PARAM_USERID            = "userid";
const char * BizLock::PARAM_PASSWORD          = "password";
const char * BizLock::PARAM_LOCKTYPE          = "lockType";
const char * BizLock::VALUE_LOCKTYPE_SOFT     = "soft";
const char * BizLock::VALUE_LOCKTYPE_HARD     = "hard";
const char * BizLock::PARAM_LOCKSTATE         = "lockState";
const char * BizLock::VALUE_LOCKSTATE_ON      = "on";
const char * BizLock::VALUE_LOCKSTATE_OFF     = "off";
