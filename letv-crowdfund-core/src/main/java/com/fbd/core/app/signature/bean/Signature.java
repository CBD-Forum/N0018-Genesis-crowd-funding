/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: Signature.java 
 *
 * Created: [2016-9-26 下午3:45:15] by haolingfeng
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
 * ProjectName: letv-signature-core 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.core.app.signature.bean;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 * @author zhangweilong
 * @version 1.0
 */

public class Signature {
    
    
    //签章合同Base64编码
    private String doc;
    //签章页数
    private String pageNum;
    //签章位置 X坐标
    private float positionX;
    //签章位置Y轴坐标
    private float positionY;
    //签章关键字
    private String keyword;
    //签章方式  0-通过坐标   1-通过关键字
    private String signType;  
    
    
    
    /**
     * @return the doc
     */
    public String getDoc() {
        return doc;
    }
    /**
     * @param doc the doc to set
     */
    public void setDoc(String doc) {
        this.doc = doc;
    }
    /**
     * @return the pageNum
     */
    public String getPageNum() {
        return pageNum;
    }
    /**
     * @param pageNum the pageNum to set
     */
    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }
    /**
     * @return the positionX
     */
    public float getPositionX() {
        return positionX;
    }
    /**
     * @param positionX the positionX to set
     */
    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }
    /**
     * @return the positionY
     */
    public float getPositionY() {
        return positionY;
    }
    /**
     * @param positionY the positionY to set
     */
    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }
    /**
     * @return the keyword
     */
    public String getKeyword() {
        return keyword;
    }
    /**
     * @param keyword the keyword to set
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    /**
     * @return the signType
     */
    public String getSignType() {
        return signType;
    }
    /**
     * @param signType the signType to set
     */
    public void setSignType(String signType) {
        this.signType = signType;
    }
    
    
}
