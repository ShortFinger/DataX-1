/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.wgzhao.datax.common.exception;

import com.wgzhao.datax.common.spi.ErrorCode;

import java.io.PrintWriter;
import java.io.StringWriter;

public class DataXException
        extends RuntimeException
{

    private static final long serialVersionUID = 1L;

    private final transient ErrorCode errorCode;

    public DataXException(ErrorCode errorCode, String errorMessage)
    {
        super(errorCode.toString() + " - " + errorMessage);
        this.errorCode = errorCode;
    }

    private DataXException(ErrorCode errorCode, String errorMessage, Throwable cause)
    {
        super(errorCode.toString() + " - " + getMessage(errorMessage) + " - " + getMessage(cause), cause);

        this.errorCode = errorCode;
    }

    public static DataXException asDataXException(ErrorCode errorCode, String message)
    {
        return new DataXException(errorCode, message);
    }

    public static DataXException asDataXException(ErrorCode errorCode, String message, Throwable cause)
    {
        if (cause instanceof DataXException) {
            return (DataXException) cause;
        }
        return new DataXException(errorCode, message, cause);
    }

    public static DataXException asDataXException(ErrorCode errorCode, Throwable cause)
    {
        if (cause instanceof DataXException) {
            return (DataXException) cause;
        }
        return new DataXException(errorCode, getMessage(cause), cause);
    }

    private static String getMessage(Object obj)
    {
        if (obj == null) {
            return "";
        }

        if (obj instanceof Throwable) {
            StringWriter str = new StringWriter();
            PrintWriter pw = new PrintWriter(str);
            ((Throwable) obj).printStackTrace(pw);
            return str.toString();
        }
        else {
            return obj.toString();
        }
    }

    public ErrorCode getErrorCode()
    {
        return this.errorCode;
    }
}
