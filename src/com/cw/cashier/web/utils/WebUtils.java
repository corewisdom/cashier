package com.cw.cashier.web.utils;

import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

/**
 * web操作帮助类
 * 
 * @author stle
 *
 */
public class WebUtils {
	/***
	 * 根据输入的键名 获取值
	 * 
	 * @param request
	 * @param name
	 * @return
	 */
	public static String getRequestParameter(HttpServletRequest request, String name) {
		String value = request.getParameter(name);
		if ((value == null) || (value.length() <= 0)) {
			Object tmp = request.getAttribute(name);
			value = (tmp == null) ? "" : tmp.toString();
		}
		return value;
	}

	public static String getRequestAttribute(HttpServletRequest request, String name) {
		Object obj = request.getAttribute(name);
		String value = (obj == null) ? "" : obj.toString();
		if ((value == null) || (value.length() <= 0)) {
			Object tmp = request.getParameter(name);
			value = (tmp == null) ? "" : tmp.toString();
		}
		return value;
	}

	public static boolean getBoolParam(HttpServletRequest request, String webKey) {
		boolean b = false;
		String s = request.getParameter(webKey);
		if (s != null) {
			b = 0 == "true".compareToIgnoreCase(s);
		} else {
			s = (String) request.getAttribute(webKey);
			if (s != null) {
				b = 0 == "true".compareToIgnoreCase(s);
			}
		}
		return b;
	}

	public static final int getRequestAttributeAsInt(HttpServletRequest request, String name) {
		return getRequestAttributeAsInt(request, name, 0);
	}

	/**
	 * 获取属性转换整型
	 * 
	 * @param request
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public static final int getRequestAttributeAsInt(HttpServletRequest request, String name, int defaultValue) {
		String value = getRequestAttribute(request, name);
		return ConvertUtils.convertInt(value, defaultValue);
	}

	/**
	 * 根据参数名字查询 转换成整型数组 页面值为字符数组
	 * 
	 * @param request
	 * @param webKey
	 * @return
	 */
	public static int[] getNumArrayParam(HttpServletRequest request, String webKey) {
		int[] nums = null;
		boolean redirected = "true".equals(request.getAttribute("redirected"));
		String value = null;
		try {
			if (redirected) {
				value = (String) request.getAttribute(webKey);
			} else {
				value = request.getParameter(webKey);
			}

			StringTokenizer st = new StringTokenizer(value, ",");
			int count = st.countTokens();
			nums = new int[count];
			for (int i = 0; i < count; ++i) {
				int iValue = Integer.parseInt(st.nextToken());
				nums[i] = iValue;
			}
		} catch (Exception e) {
			return null;
		}
		return nums;
	}

	public static int[] getNumArrayParam(String key) {
		int[] nums = null;
		try {
			StringTokenizer st = new StringTokenizer(key, ",");
			int count = st.countTokens();
			nums = new int[count];
			for (int i = 0; i < count; ++i) {
				int iValue = Integer.parseInt(st.nextToken());
				nums[i] = iValue;
			}
		} catch (Exception e) {
			return null;
		}
		return nums;
	}

	/**
	 * 根据参数名字查询 转换成整型数组 页面值为字符数组
	 * 
	 * @param request
	 * @param webKey
	 * @return
	 */
	public static int[] getNumArrayByCheckBox(HttpServletRequest request, String webKey) {
		int[] nums = null;
		String[] value = null;
		try {

			value = request.getParameterValues(webKey);
			int count = value.length;
			nums = new int[count];
			for (int i = 0; i < count; ++i) {
				int iValue = Integer.parseInt(value[i]);
				nums[i] = iValue;
			}
		} catch (Exception e) {
			return null;
		}
		return nums;
	}
}
