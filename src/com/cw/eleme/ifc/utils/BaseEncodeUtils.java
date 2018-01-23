
/**
*    
*  base64解码
* [Product]
*    cr.cw.com
* [Copyright]
*     Copyright 2017 Zhongzhi Tech. Co. Ltd. All Rights Reserved
* [FileName]
*     BaseEncodeUtils.java
* [Author]
*	yuanguangjie
* [Date]
*	2017年11月20日 上午8:32:45  
*/
package com.cw.eleme.ifc.utils;

/**
 * @author yuanguangjie
 *
 */
public class BaseEncodeUtils {

	/**
	 * 字节合并
	 * 
	 * @param src
	 * @param start
	 * @param src_size
	 * @return
	 */
	public static String bytesSub2String(byte[] src, int start, int src_size) {
		byte[] resBytes = new byte[src_size];
		System.arraycopy(src, start, resBytes, 0, src_size);
		// System.out.println(" len ==" +resBytes.length
		// + " sub_bytes = " + bytes2Int1(resBytes));
		return bytes2String(resBytes);
	}

	/**
	 * 字节转为字符串
	 * 
	 * @param b
	 * @return
	 */
	public static String bytes2String(byte b[]) {
		String result_str = new String(b);
		return result_str;
	}
}
