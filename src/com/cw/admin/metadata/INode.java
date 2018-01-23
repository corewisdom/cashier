
/**
*    
* 请在这里说明本文件的功能、与其它文件的关系等信息
* [Product]
*    cr.cw.com
* [Copyright]
*     Copyright 2017 Zhongzhi Tech. Co. Ltd. All Rights Reserved
* [FileName]
*     INode.java
* [Author]
*	style-work
* [Date]
*	2017年11月12日 下午7:53:35  
*/
package com.cw.admin.metadata;

import java.util.List;

import com.cw.admin.client.value.AdminID;
import com.cw.admin.client.value.IDName;
import com.cw.cashier.util.exception.MetaDataException;
import com.cw.cashier.util.exception.PropertyNotFoundException;

/**
 * @author style-work
 *
 */
public interface INode {

	public abstract AdminID getAdminID();

	public abstract int getID();

	public abstract String getName();

	public abstract String getAPIName();

	public abstract String getQualifiedName();

	public abstract String getFullyQualifiedName();

	public abstract int getType();

	public abstract INode getParent(long nodeID) throws MetaDataException;

	public abstract List getChildren(long nodeID) throws MetaDataException;

	public abstract List getProperties(long PID) throws MetaDataException;

	public abstract IProperty getProperty(long sessionID, int nodeID)
			throws MetaDataException, PropertyNotFoundException;

	public abstract IDName getPropertyValue(long sessionID, int pID)
			throws MetaDataException, PropertyNotFoundException;

	public abstract IDName[] getEditableListValues(long sessionID, int listID) throws MetaDataException;

	public abstract void setPropertyValue(long sessionID, int nodeID, IDName value)
			throws MetaDataException, PropertyNotFoundException;

	public abstract void setPropertyValue(long sessionID, int pID, IDName[] values)
			throws MetaDataException, PropertyNotFoundException;

	public abstract void setEditableListValue(long sessionID, int listID, IDName[] values)
			throws MetaDataException, PropertyNotFoundException;

	public abstract boolean isEditable();

	public abstract IDName toIDName();

}
