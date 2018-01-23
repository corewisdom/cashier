
/**
*    
* 请在这里说明本文件的功能、与其它文件的关系等信息
* [Product]
*    cr.cw.com
* [Copyright]
*     Copyright 2017 Zhongzhi Tech. Co. Ltd. All Rights Reserved
* [FileName]
*     AdminCacheMgr.java
* [Author]
*	style-work
* [Date]
*	2017年11月7日 上午11:38:45  
*/
package com.cw.server.cache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cw.admin.server.ADList;
import com.cw.admin.server.ADListItem;
import com.cw.admin.server.ADNode;
import com.cw.admin.server.ADNodeList;
import com.cw.util.collections.ColtIntObjectHashMap;
import com.cw.util.collections.IIntObjectHashMap;

/**
 * @author style-work
 *
 */
public class AdminCacheMgr {

	public static final Log LOG = LogFactory.getLog(AdminCacheMgr.class);
	private static AdminCacheMgr _instance = new AdminCacheMgr();

	private static ArrayList _emptyArrayList = new ArrayList();

	private static ADNodeList _emptyNodeList = new ADNodeList();

	private IIntObjectHashMap _cascadeListCache;
	private IIntObjectHashMap _listCache;
	private IIntObjectHashMap _nodeCache;
	private IIntObjectHashMap _nodeListCache;
	private IIntObjectHashMap _propCache;
	private HashMap _listNameCache;

	private AdminCacheMgr() {
		this._cascadeListCache = new ColtIntObjectHashMap();
		this._listCache = new ColtIntObjectHashMap();
		this._nodeCache = new ColtIntObjectHashMap();
		this._nodeListCache = new ColtIntObjectHashMap();
		this._propCache = new ColtIntObjectHashMap();
		this._listNameCache = new HashMap();
	}

	public static AdminCacheMgr getInstance() {

		return _instance;
	}

	public static ADNodeList getNodeList(int id) {
		Object list = getInstance()._nodeListCache.get(id);
		return ((list != null) ? (ADNodeList) list : _emptyNodeList);
	}

	public static ADList getList(int id) {
		Object list = getInstance()._listCache.get(id);
		return ((list != null) ? (ADList) list : null);
	}

	public static void removeNode(ADNode node) {
		getInstance()._nodeCache.remove(node.getID());
	}

	/**
	 * 缓存子节点
	 * 
	 * @param id
	 * @param nodeList
	 */
	public static void cacheNodeList(int id, ADNodeList nodeList) {
		getInstance()._nodeListCache.put(id, nodeList);
	}

	/**
	 * 根据节点ID 获取配置节点
	 * 
	 * @param Id
	 * @return
	 */

	public static ADNode getNode(int Id) {
		return ((ADNode) getInstance()._nodeCache.get(Id));
	}

	/**
	 * 缓存节点配置属性
	 * 
	 * @param node
	 */
	public static void cacheNode(ADNode node) {
		getInstance()._nodeCache.put(node.getID(), node);
	}

	public static ADList getList(String name) {
		Object list = getInstance()._listNameCache.get(name);
		return ((list != null) ? (ADList) list : null);
	}

	public static Collection getListCollection() {
		return Arrays.asList(getInstance()._listCache.getValues());
	}

	public static void removeListEntry(int objId) {
		getInstance()._listCache.remove(objId);
	}

	public static boolean containList(int id) {
		return getInstance()._listCache.containsKey(id);
	}

	public static void cacheList(ADList listObj) {
		getInstance()._listCache.put(listObj.getListID(), listObj);
		getInstance()._listNameCache.put(listObj.getAPIName(), listObj);
	}

	public static ADListItem getCascadeListItem(int id) {
		Object list = getInstance()._cascadeListCache.get(id);
		return ((list != null) ? (ADListItem) list : null);
	}

	public static void cacheCascadeListItem(int id, ADListItem listObj) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Cache cascased list item-- entry id: " + listObj.getID() + ", value: " + listObj.getValue());
		}
		AdminCacheMgr cache = getInstance();
		if (!(cache._cascadeListCache.containsKey(id)))
			cache._cascadeListCache.put(id, listObj);
	}

	public static void removeCascadeListItem(int id) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("remove cascased list item from cache-- entry id: " + id);
		}
		getInstance()._cascadeListCache.remove(id);
	}

	public static void cachePropList(int id, ArrayList propList) {
		getInstance()._propCache.put(id, propList);
	}

	public static ArrayList getPropList(int id) {
		Object list = getInstance()._propCache.get(id);
		return ((list != null) ? (ArrayList) list : _emptyArrayList);
	}

}
