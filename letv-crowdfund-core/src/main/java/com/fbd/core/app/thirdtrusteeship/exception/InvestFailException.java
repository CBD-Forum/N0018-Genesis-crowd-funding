/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: InvestFailException.java 
 *
 * Created: [2014-12-25 下午7:42:47] by haolingfeng
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
 * ProjectName: fbd-core 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.core.app.thirdtrusteeship.exception;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:投资失败需要流标的异常
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */

public class InvestFailException extends RuntimeException {
    /**
     * 构造方法
     * 
     * @param arg0
     *            信息
     * @param arg1
     *            原因
     */
    public InvestFailException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    /**
     * 构造方法
     * 
     * @param arg0
     *            信息
     */
    public InvestFailException(String arg0) {
        super(arg0);
    }

    /**
     * 构造方法
     * 
     * @param arg0
     *            原因
     */
    public InvestFailException(Throwable arg0) {
        super(arg0);
    }
}
