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
package com.neulevel.epp.transport;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * This <code>EppSessionUtil</code> class contains some utility methods for
 * handling message exchanges between an EPP Server and an EPP Client.
 *
 * @author Ning Zhang ning.zhang@neustar.com
 * @version $Revision: 1.4 $ $Date: 2008/11/13 17:06:52 $
 */
public class EppMessageUtil
{
	private static final int BUFSIZ = 8192;

	private static boolean writeMessageSize = true;
	private static boolean fetchMessageSize = true;

	/**
	 * Property for handling the size of an EPP message.
	 *
	 * This property could be used to enable/disable the processing of EPP message
	 * length, specified for backward compatibility for EPP TCP transport mapping.
	 *
	 * Two boolean flags can be specified via the property, each of them can be
	 * separated by a delimit char ",".
	 *
	 * <UL>
	 * <LI>Flag for generating message length in outbounding messages:
	 *     <UL><LI>+write: true</LI><LI>-write: false</LI></UL></LI>
	 * <LI>Flag for fetching message length in inbounding messages:
	 *     <UL><LI>+fetch: true</LI><LI>-fetch: false</LI></UL></LI>
	 * </UL>
	 *
	 * For example:
	 *
	 * <P>
	 * -Dcom.neulevel.epp.transport.EppMessageUtil.eppMessageSize=+output,-fetch
	 *
	 * <P>
	 * The default values of the property is: +output,+fetch
	 */
	public static final String eppMessageSize = "com.neulevel.epp.transport.EppMessageUtil.eppMessageSize";

	static
	{
		String flags = System.getProperty(eppMessageSize);
		if( flags != null )
		{
			StringTokenizer tokens = new StringTokenizer(flags, ",");
			while( tokens.hasMoreTokens() )
			{
				String token = tokens.nextToken();
				if( token == null )
				{
					continue;
				}
				if( token.equalsIgnoreCase("+write") )
				{
					writeMessageSize = true;
				}
				else if( token.equalsIgnoreCase("-write") )
				{
					writeMessageSize = false;
				}
				else if( token.equalsIgnoreCase("+fetch") )
				{
					fetchMessageSize = true;
				}
				else if( token.equalsIgnoreCase("-fetch") )
				{
					fetchMessageSize = false;
				}
			}
		}
	}

	/**
	 * Sets the flags for handling EPP message size
	 */
	public static void setEppMessageSizeFlags( boolean write, boolean fetch )
	{
		writeMessageSize = write;
		fetchMessageSize = fetch;
	}

	/**
	 * Gets the flag for outputing the size of an outgoing EPP message
	 */
	public static boolean getEppMessageSizeWriteFlag()
	{
		return writeMessageSize;
	}

	/**
	 * Gets the flag for extracting the size of an incoming EPP message
	 */
	public static boolean getEppMessageSizeFetchFlag()
	{
		return fetchMessageSize;
	}

	/**
	 * Timeout value in 1/10 seconds
	 */
	private static int   timeout = 0;

	/**
	 * Sets the timeout values in seconds. The default value is 0, which
	 * means there will be no timeout.
	 */
	public static void setTimeout( int seconds )
	{
		if( seconds > 0 )
		{
			EppMessageUtil.timeout = seconds * 10;
		}
	}

	/**
	 * Gets the timeout values in seconds. The default value is 0, which
	 * means there will be no timeout.
	 */
	public static int getTimeout()
	{
		return EppMessageUtil.timeout / 10;
	}

	/**
	 * Sends out an outgoing EPP message
	 *
	 * @param out the <code>OutputStream</code> object to which the EPP
	 *            message payload is sent
	 *
	 * @param str    the string to be sent out
	 */
	public static void putEppPayload( OutputStream out, String str ) throws IOException
	{
		if( writeMessageSize == true )
		{
			byte[] bytes = str.getBytes();
			byte[] size  = new byte[4];
			int bytes_length = bytes.length + 4;
			size[3] = (byte) ( bytes_length & 0x0000FF        );
			size[2] = (byte) ((bytes_length & 0x00FF00) >>   8);
			size[1] = (byte) ((bytes_length & 0xFF0000) >>  16);
			size[0] = (byte) ( bytes_length             >>> 24);
			out.write(size);
			out.write(bytes);
		}
		else
		{
			out.write(str.getBytes());
		}
		out.flush();
	}

	/**
	 * Sends a string over a socket and reads a string back from
	 * the socket
	 *
	 * @param socket the socket used for sending the string
	 * @param str    the string to be sent over the socket
	 *
	 * @return a string containing the EPP message payload, or null
	 *         if there is any error associated with the connection to
	 *         the server
	 */
	public static String send( Socket socket, String str ) throws IOException
	{
		socket.setSoTimeout(EppMessageUtil.timeout*1000);
		OutputStream out = socket.getOutputStream();
		EppMessageUtil.putEppPayload(out, str);
		return EppMessageUtil.getEppPayload(socket.getInputStream());
	}

	/**
	 * Gets the EPP message payload size from an input stream
	 *
	 * @param in the <code>InputStream</code> object from which the EPP
	 *           message payload is retrieved
	 *
	 * @return an integer indicates the size of the EPP payload
	 */
	private static int getEppPayloadSize( InputStream in ) throws IOException
	{
		int size = 0;
		int n;
		for( int i = 0; i < 4; i++ )
		{
			int loop = 0;
			n = in.read();
			if (n == -1)//end of stream is reached from JDK1.6 documentaion
			{
            System.out.println("***Connection with server lost.Throwing Exception.***");
				throw new IOException("Connection with server lost.Throwing Exception.");
			}
			size = size << 8;
			size |= n & 0xFF;
		}

		size -= 4;
		return size;
	}

	/**
	 * Gets the EPP message payload from an input stream
	 *
	 * @param in the <code>InputStream</code> object from which the EPP
	 *           message payload is retrieved
	 *
	 * @return a string containing the EPP message payload, or null
	 *         if there is any error associated with the connection to
	 *         the server
	 */
	public static String getEppPayload( InputStream in ) throws IOException
	{
		byte[] buf = new byte[BUFSIZ];
		int i = 0;
		int n;
		boolean endFound = false;
		boolean eppFound = false;
		int     eppSize  = 0;

		if( fetchMessageSize == true )
		{
			eppSize = EppMessageUtil.getEppPayloadSize(in);
			if( eppSize <= 0 )
			{
				throw new IOException("Error in eppGetPayload() message size is invalid " + eppSize);
			}
		}

		while( ! endFound )
		{
			int loop = 0;
			n = in.read();
			if (n == -1)//end of stream is reached from JDK1.6 documentaion
			{
            System.out.println("***Connection with server lost.Throwing Exception.***");
				throw new IOException("Connection with server lost.Throwing Exception.");
			}

			// EPP payload rarely exceeds 8KB, so this
			// doubling-up should not occur very often

			if( i == buf.length )
			{
				byte[] newbuf = new byte[buf.length + BUFSIZ];
				for( i = 0; i < buf.length; i++ )
				{
					newbuf[i] = buf[i];
				}
				buf = newbuf;
			}
			buf[i++] = (byte) n;

			// check if we have got the "</epp" tag

			if(    ! eppFound
			    && (i >= 5)
			    && (buf[i - 5] == '<')
			    && (buf[i - 4] == '/')
			    && (buf[i - 3] == 'e')
			    && (buf[i - 2] == 'p')
			    && (buf[i - 1] == 'p') )
			{
				eppFound = true;
			} 
			if( eppFound && (buf[i - 1] == '>') )
			{
				endFound = true;
			}

			// check if we have reached the size

			if( fetchMessageSize == true )
			{
				if( i == eppSize )
				{
					endFound = true;
				}
				else
				{
					endFound = false;
				}
			}
		}

		// ok, we have got a buffer, either with a full EPP payload, or
		// end of the input stream

		return new String(buf, 0, i).trim();
	}
}
