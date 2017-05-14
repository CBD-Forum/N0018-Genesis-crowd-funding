/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: RandomUtil.java 
 *
 * Created: [2016-9-27 上午10:51:29] by haolingfeng
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

package com.fbd.core.util;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 获取随机数
 *
 * @author haolingfeng
 * @version 1.0
 *
 */

public class RandomUtil {
    public static int getRandom(){
        return (int)(Math.random()*9999)+1;
    }
    public static void main(String[] args){
        for(int i=0;i<100;i++){
            System.out.println(getRandom());
        }
    }
}
