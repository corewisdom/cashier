
/**
*    
* 获取饿了么token类；
* 爱乐多账号:czald    password:ald160605
* [Product]
*    cr.cw.com
* [Copyright]
*     Copyright 2017 Zhongzhi Tech. Co. Ltd. All Rights Reserved
* [FileName]
*     GetToken.java
* [Author]
*	yuanguangjie
* [Date]
*	2017年11月20日 下午1:19:50  
*/
package com.cw.eleme.ifc.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import com.cw.cashier.controller.action.admin.AlipayAction;
import com.cw.util.log.CMLogFactory;
import com.cw.util.log.ICMLogger;
import com.mysql.jdbc.log.Log;

import net.sf.json.JSONObject;

/**
 * 请求token
 * 
 * @author yuanguangjie
 *
 */
public class GetToken {

	// 创建log
	static ICMLogger LOG = CMLogFactory.getLogger(GetToken.class);

	/**
	 * 请求token 获取认证返回的所有值：access_token、token_type、expires_in、refresh_token、scope
	 * 
	 * @param url
	 *            请求地址
	 * @param entityValue
	 *            body值（实体值请求参数）: code、redirect_url、client_id（key）
	 * @param header
	 *            头部值：Accept、Content-Type
	 * @param code
	 *            编码格式
	 * @param key
	 *            饿了么申请应用得到的key
	 * @param secret
	 *            饿了么申请应用得到的 secret
	 * @return
	 */
	public static JSONObject getToken(String url, Map<String, Object> entityValue, Map<String, Object> header,
			String code, String key, String secret) {
		// 返回结果
		JSONObject result = new JSONObject();
		try {
			// 1.创建 HttpClient 的实例
			BasicHttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 20 * 1000);
			HttpConnectionParams.setSoTimeout(httpParams, 20 * 1000);
			DefaultHttpClient client = new DefaultHttpClient(httpParams);

			// UrlEncodedFormEntity设置实体
			List<NameValuePair> fromParams = new ArrayList<NameValuePair>();
			fromParams.add(new BasicNameValuePair("grant_type", "authorization_code"));
			fromParams.add(new BasicNameValuePair("code", entityValue.get("code").toString()));
			fromParams.add(new BasicNameValuePair("redirect_uri", entityValue.get("redirect_uri").toString()));
			fromParams.add(new BasicNameValuePair("client_id", entityValue.get("client_id").toString()));

			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(fromParams, code);

			// 设置请求地址
			HttpPost post = new HttpPost(url);

			// 使用base64进行加密
			byte[] tokenByte = Base64.encodeBase64((key + ":" + secret).getBytes());

			// 将加密的信息转换为string
			String tokenStr = BaseEncodeUtils.bytesSub2String(tokenByte, 0, tokenByte.length);

			LOG.debug("====>>>饿了么：base64编码值：" + tokenStr.toString());

			// 设置头部
			post.setHeader("Accept", header.get("Accept").toString());
			post.setHeader("Content-Type", header.get("Content-Type").toString());

			// 把认证信息发到header中
			post.setHeader("Authorization", "Basic " + tokenStr);

			// 设置实体
			post.setEntity(entity);

			// 请求url地址
			HttpResponse response = client.execute(post);

			// 获取请求的状态值
			int statusCode = response.getStatusLine().getStatusCode();

			// 设置请求状态值给jsonObject
			result.put("statusCode", statusCode);
			// 根据状态值返回
			switch (statusCode) {
			case 200:// 请求状态成功
				// 获取请求的实体及结果
				result.put("entity", EntityUtils.toString(response.getEntity(), code));
				break;

			case 400:// 请求状态400
				// 获取请求的实体及结果
				result.put("entity", "请求失败，找不到!");
				break;

			case 404:
			case 405:
			case 505:// 请求状态错误
				// 获取请求的实体及结果
				result.put("entity", EntityUtils.toString(response.getEntity(), code));
				break;
			default:// 默认
				// 获取请求的实体及结果
				result.put("entity", "请求失败，请重试");
				break;
			}

			LOG.debug("======>>>>饿了么：请求token时，获取的状态值：" + statusCode + "，请求的结果：" + result);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error("=====>>>饿了么：设置请求实体参数错误:" + e.getMessage());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error("=====>>>饿了么：客户端发送错误:" + e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error("=====>>>饿了么：io流发送错误：" + e.getMessage());
		}
		return result;
	}

	/**
	 * 刷新token 获取认证返回的所有值：access_token、token_type、expires_in、refresh_token、scope
	 * 
	 * @param url
	 *            请求地址
	 * @param entityValue
	 *            body值（实体值请求参数）: refresh_token
	 * @param header
	 *            头部值：Accept、Content-Type
	 * @param code
	 *            编码格式
	 * @param key
	 *            饿了么申请应用得到的key
	 * @param secret
	 *            饿了么申请应用得到的 secret
	 * @return
	 */
	public static JSONObject getRefreshToken(String url, Map<String, Object> entityValue, Map<String, Object> header,
			String code, String key, String secret) {

		// 返回结果
		JSONObject result = new JSONObject();

		try {
			// 1.创建 HttpClient 的实例
			BasicHttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 20 * 1000);
			HttpConnectionParams.setSoTimeout(httpParams, 20 * 1000);
			DefaultHttpClient client = new DefaultHttpClient(httpParams);

			// UrlEncodedFormEntity设置实体
			List<NameValuePair> fromParams = new ArrayList<NameValuePair>();
			fromParams.add(new BasicNameValuePair("grant_type", "refresh_token"));
			fromParams.add(new BasicNameValuePair("refresh_token", entityValue.get("refresh_token").toString()));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(fromParams, code);

			// 设置请求地址
			HttpPost post = new HttpPost(url);

			// 使用base64进行加密
			byte[] tokenByte = Base64.encodeBase64((key + ":" + secret).getBytes());

			// 将加密的信息转换为string
			String tokenStr = BaseEncodeUtils.bytesSub2String(tokenByte, 0, tokenByte.length);

			LOG.debug("====>>>饿了么：base64编码值：" + tokenStr.toString());

			post.setHeader("Accept", header.get("Accept").toString());
			post.setHeader("Content-Type", header.get("Content-Type").toString());
			// 把认证信息发到header中
			post.setHeader("Authorization", "Basic " + tokenStr);

			// 设置实体
			post.setEntity(entity);

			// 请求url地址
			HttpResponse response = client.execute(post);

			// 获取请求的状态值
			int statusCode = response.getStatusLine().getStatusCode();

			// 设置请求状态值给jsonObject
			result.put("statusCode", response.getStatusLine().getStatusCode());
			// 根据状态值返回
			switch (statusCode) {
			case 200:// 请求状态成功
				// 获取请求的结果
				result.put("entity", EntityUtils.toString(response.getEntity(), code));
				break;

			case 400:// 请求状态400
				// 获取请求的结果
				result.put("entity", "请求失败，找不到");
				break;

			case 404:
			case 405:
			case 505:// 请求状态错误
				// 获取请求的结果
				result.put("entity", EntityUtils.toString(response.getEntity(), code));
				break;
			default:// 默认
				// 获取请求的结果
				result.put("entity", EntityUtils.toString(response.getEntity(), code));
				break;
			}

			LOG.debug("======>>>>请求刷新token时，获取的状态值：" + statusCode + "，请求的结果：" + result);

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error("=====>>>饿了么：设置请求实体参数错误:" + e.getMessage());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error("=====>>>饿了么：客户端发送错误:" + e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error("=====>>>饿了么：io流发送错误：" + e.getMessage());
		}
		return result;
	}

	public static void main(String[] args) {
		// 服务器地址：https://www.cwfoodmeet.com/
		// 实体值请求参数
		Map<String, Object> entity = new HashMap<String, Object>();
		entity.put("code", "017c15b2f216913a99d7c1e0533f3eb6");
		entity.put("redirect_uri", "https://18g95t2903.iask.in/cashier/eleme-call-back");
		entity.put("client_id", "2CgSMlYZSP");

		// 头部请求参数
		Map<String, Object> head = new HashMap<String, Object>();
		head.put("Accept", "application/json");
		head.put("Content-Type", "application/x-www-form-urlencoded");

		GetToken.getToken("https://open-api-sandbox.shop.ele.me/token", entity, head, "UTF-8", "2CgSMlYZSP",
				"9a6191f76fd5a2ad8ea7a830d7b7612e7df5c60a");

		// 实体值请求参数
		// Map<String, Object> entityValue = new HashMap<String, Object>();
		// entityValue.put("refresh_token", "eb667bfcd887d10e6fb47d15ce58d390");
		//
		// // 头部请求参数
		// Map<String, Object> header = new HashMap<String, Object>();
		// header.put("Accept", "application/json");
		// header.put("Content-Type", "application/x-www-form-urlencoded");
		//
		// GetToken.getRefreshToken("https://open-api-sandbox.shop.ele.me/token",
		// entityValue, header, "UTF-8",
		// "2CgSMlYZSP", "9a6191f76fd5a2ad8ea7a830d7b7612e7df5c60a");
	}
}
