/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: StringUtil.java 
 *
 * Created: [2016-9-27 上午11:09:42] by haolingfeng
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
 * ProjectName: letv-crowdfund-core 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.core.app.blockChain.util;

import com.fbd.core.exception.ApplicationException;
import com.fbd.core.util.RandomUtil;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 字符串处理
 *
 * @author haolingfeng
 * @version 1.0
 *
 */

public class BlockChainStringUtil {
    /**
     * 间隔符
     */
    public static final String INTERVALSTR="_";
    /**
     * 
     * Description:获取唯一转让编号 (加密)
     *
     * @param 
     * @return String
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-9-27 上午11:10:47
     */
    public static String getUniqueTransferNoEnc(String value){
        if(value.indexOf(INTERVALSTR)!=-1){
            throw new ApplicationException("不允许存在间隔符"+INTERVALSTR);
        }
        StringBuffer sb=new StringBuffer();
        return sb.append(value).append(INTERVALSTR).append(RandomUtil.getRandom()).toString();
    }
    /**
     * 
     * Description:获取唯一转让编号 (解密)
     *
     * @param 
     * @return String
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-9-27 上午11:10:47
     */
    public static String getUniqueTransferNoDec(String value){
        String[] v=value.split(INTERVALSTR);
        if(v.length==2){
            return v[0];   
        }
        throw new ApplicationException("该字符串不在解析范围内");
    }
    public static void main(String[] args){
            System.out.println(getUniqueTransferNoEnc("测试+"));
            System.out.println(getUniqueTransferNoDec(getUniqueTransferNoEnc("测试sswwww")+"_"));   
    }
}
