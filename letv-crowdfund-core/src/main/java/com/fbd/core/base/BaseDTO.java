package com.fbd.core.base;

import java.io.Serializable;
/**
 * 
 * 可以缓冲的DTO对象。
 * 
 * @author: brz
 * @version: 1.00
 * @date: 2009-3-30
 */
public class BaseDTO {
	/**
	 * 对象唯一标识。
	 */
	protected Serializable OID = null;

	/**
	 * 
	 * 返回Id标识。
	 * 
	 * @author: brz
	 * @date:2008-3-30
	 * 
	 */
	public Serializable getId() {
		return OID;
	}

	/**
	 * 
	 * 设置Id标识。
	 * 
	 * @author: brz
	 * @date:2008-3-30
	 * 
	 */
	public void setId(Serializable oid) {
		OID = oid;
	}

}