/*
 Copyright (c) 2005, The Board of Trustees of the Leland Stanford Junior University
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions are
 met:

 Redistributions of source code must retain the above copyright notice,
 this list of conditions and the following disclaimer.

 Redistributions in binary form must reproduce the above copyright
 notice, this list of conditions and the following disclaimer in the
 documentation and/or other materials provided with the distribution.

 Neither the name of Stanford University nor the names of its
 contributors may be used to endorse or promote products derived from
 this software without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 LIMITED TO, ANY IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package probeIt.util;

import java.io.*;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.net.*;
import java.util.ArrayList;
import java.awt.Image;
public class GetURLContents
{
	public static byte[] downloadFile(String url)
	  {//download file via http protocol and return contents as byte array
		
		try
		{
			ByteArrayOutputStream output = new ByteArrayOutputStream();
		
		  //initialize connection
	      URL yahoo = new URL(url);  
	      URLConnection fileLocation = yahoo.openConnection();
		  
	      BufferedInputStream bis = new BufferedInputStream(fileLocation.getInputStream());
	      byte[] buff = new byte[1024];
	      int bytesRead;
	      int totalBytes = 0;
	      
	      // Simple read/write loop
	      while(-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
	        output.write(buff, 0, bytesRead);
	      }
	      
	      //close inputstream
	      bis.close();
		  
		  return output.toByteArray();
	  
		} catch (MalformedURLException u){
			u.printStackTrace();
			return null;
			
		} catch (IOException i){
			
			i.printStackTrace();
			return null;
		} 
	  }
	public static String downloadText(String _uri) throws IOException,
			MalformedURLException
	{
		return GetURLContents.downloadText(new URL(_uri));
	}

	public static String downloadText(URL u) throws IOException
	{

		System.out.println(u.toString());
		URLConnection conn = u.openConnection();
		conn.setUseCaches(true);
		Object resp = conn.getContent();
		//String ct = conn.getContentType();
		InputStream body = (InputStream) resp;
		InputStreamReader isr = new InputStreamReader(body);
		LineNumberReader lr = new LineNumberReader(isr);
		StringBuffer ret = new StringBuffer();

		while (true)
		{
			String line = lr.readLine();
			// System.out.println("Line:" + line);
			if (line == null)
			{
				break;
			}
			// ret.append(line).append(System.getProperty("line.separator"));
			ret.append(line).append("\n");
		}
		return ret.toString();
	}
	
	public static String downloadRawChars(String _uri) throws IOException,
			MalformedURLException
	{
		return GetURLContents.downloadRawChars(new URL(_uri));
	}

	public static String downloadRawChars(URL u) throws IOException
	{

		URLConnection conn = u.openConnection();
		Object resp = conn.getContent();
		String ct = conn.getContentType();
		InputStream body = (InputStream) resp;
		StringBuffer ret = new StringBuffer();
		int currentChar;
		while ((currentChar = body.read()) != -1)
		{
			ret.append(Character.toString((char) currentChar));
		}
		return ret.toString();
	}

	public static String getContentType(String urlStr) throws IOException, MalformedURLException
	{
		String cType = null;
		URL url = new URL(urlStr);
		URLConnection conn = url.openConnection();
		cType = conn.getContentType();
		return cType;
	}

} /* END of GetURLContents */
