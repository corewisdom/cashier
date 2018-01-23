
/**
*    
* 请在这里说明本文件的功能、与其它文件的关系等信息
* [Product]
*    cr.cw.com
* [Copyright]
*     Copyright 2017 Zhongzhi Tech. Co. Ltd. All Rights Reserved
* [FileName]
*     HttpHeader.java
* [Author]
*	yuanguangjie
* [Date]
*	2017年11月20日 上午10:52:31  
*/
package com.cw.eleme.ifc.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.Header;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * @author yuanguangjie
 *
 */
public class HttpHeader {
	public static void main(String[] args) throws UnsupportedEncodingException {

		String base64 = Base64.getEncoder()
				.encodeToString("2CgSMlYZSP:9a6191f76fd5a2ad8ea7a830d7b7612e7df5c60a".getBytes("UTF-8"));
		// 创建客户端
		CloseableHttpClient closeableHttpClient = HttpClients.createDefault();

		// 创建请求Get实例
		HttpGet http = new HttpGet(
				"https://open-api-sandbox.shop.ele.me/token?grant_type=authorization_code&code=f60ba06bae4a069a387d8a021cba4b9f&redirect_uri=https://open-api-sandbox.shop.ele.me/qrauthorize&client_id=2CgSMlYZSP");

		http.setHeader("Authorization", "Basic " + base64);
		http.setHeader("Content-Type", "application/x-www-form-urlencoded");

		try {
			// 客户端执行httpGet方法，返回响应
			CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(http);

			// 得到服务响应状态码
			if (closeableHttpResponse.getStatusLine().getStatusCode() == 200) {

				System.err.println("得到所有值：" + closeableHttpResponse.getEntity());

			} else {
				// 如果是其他状态码则做其他处理，这部分知识博主也还没有系统的学习，以后给大家补上
				// 对于服务器返回的各个状态码的含义希望大家能了解一下
				System.err.println("请求失败" + closeableHttpResponse);
			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
