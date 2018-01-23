
/**
*    
* 请在这里说明本文件的功能、与其它文件的关系等信息
* [Product]
*    cr.cw.com
* [Copyright]
*     Copyright 2017 Zhongzhi Tech. Co. Ltd. All Rights Reserved
* [FileName]
*     TestDome.java
* [Author]
*	yuanguangjie
* [Date]
*	2017年11月16日 下午5:00:39  
*/
package com.cw.eleme.ifc.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;

/**
 * 签名帮助
 * @author yuanguangjie
 *
 */
public class SignatureUtils {

	private static String concatParams(Map<String, String> params2) throws UnsupportedEncodingException {

		Object[] key_arr = params2.keySet().toArray();
		Arrays.sort(key_arr);
		String str = "";

		for (Object key : key_arr) {
			String val = params2.get(key);
			key = URLEncoder.encode(key.toString(), "UTF-8");
			val = URLEncoder.encode(val, "UTF-8");
			str += "&" + key + "=" + val;
		}

		return str.replaceFirst("&", "");
	}

	private static String byte2hex(byte[] b) {
		StringBuffer buf = new StringBuffer();
		int i;

		for (int offset = 0; offset < b.length; offset++) {
			i = b[offset];
			if (i < 0) {
				i += 256;
			}
			if (i < 16) {
				buf.append("0");
				buf.append(Integer.toHexString(i));
			}
		}

		return buf.toString();
	}

	public static String genSig(String pathUrl, Map<String, String> params, String consumerSecret)
			throws UnsupportedEncodingException, NoSuchAlgorithmException {

		String str = concatParams(params);
		str = pathUrl + "?" + str + consumerSecret;

		MessageDigest md = MessageDigest.getInstance("SHA1");
		return byte2hex(md.digest(byte2hex(str.getBytes("UTF-8")).getBytes()));
	}
 
}
