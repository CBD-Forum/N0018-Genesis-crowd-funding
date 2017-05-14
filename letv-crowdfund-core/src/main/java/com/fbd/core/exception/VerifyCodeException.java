/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: IUserDao.java 
 *
 * Created: [2014-12-3 10:44:54] by haolingfeng
 *
 * $Id$
 * 
 * $Revision$
 *
 * $Author$
 *
 * $Date$
 *
 * ============================================================ 
 * 
 * ProjectName: fbd 
 * 
 * Description: 
 * 
 * ==========================================================*/
package com.fbd.core.exception;

/**
 * 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 异常类
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
public class VerifyCodeException extends RuntimeException {
	/**
	 * 构造方法
	 * @param arg0 信息
	 * @param arg1 原因
	 */
	public VerifyCodeException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * 构造方法
	 * @param arg0 信息
	 */
	public VerifyCodeException(String arg0) {
		super(arg0);
	}

	/**
	 * 构造方法
	 * @param arg0 原因
	 */
	public VerifyCodeException(Throwable arg0) {
		super(arg0);
	}

}
