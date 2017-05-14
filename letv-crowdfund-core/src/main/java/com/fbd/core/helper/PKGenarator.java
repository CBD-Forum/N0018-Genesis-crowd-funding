package com.fbd.core.helper;

import java.util.Date;
import java.util.Random;
import java.util.UUID;
import com.fbd.core.app.sxyPay.common.SxyPayConstants;
import com.fbd.core.util.DateUtil;
import com.fbd.core.util.securityCode.SecurityCode;

public class PKGenarator {
    /**
     * 获得一个UUID
     * 
     * @return String UUID
     */
    public static String getId() {
        String s = UUID.randomUUID().toString();
        // 去掉“-”符号
        return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18)
                + s.substring(19, 23) + s.substring(24);
    }

    /**
     * 获得指定数目的UUID
     * 
     * @param number
     *            int 需要获得的UUID数量
     * @return String[] UUID数组
     */
    public synchronized static String[] getId(int number) {
        if (number < 1) {
            return null;
        }
        String[] ss = new String[number];
        for (int i = 0; i < number; i++) {
            ss[i] = getId();
        }
        return ss;
    }

    /**
     * 
     * Description: 生成订单号 年月日+8位随机数
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-22 下午5:12:01
     */
    public static String getOrderId() {
        Date now = new Date();
        String sdate = DateUtil.date2Str(now, "yyyyMMdd");
        double random = Math.random();
        String sRandom = String.valueOf((int) (random * 89999999 + 10000000));
        return sdate + sRandom;
    }
    
    /**
     * 
     * Description: 生成充值订单号 年月日+8位随机数
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-22 下午5:12:01
     */
    public static String getRechargeOrderId() {
        Date now = new Date();
        String sdate = DateUtil.date2Str(now, "yyyyMMdd");
        double random = Math.random();
        String sRandom = String.valueOf((int) (random * 89999 + 10000));
        return sdate+"-"+SxyPayConstants.Config.SXY_SHBH+"-"+sRandom;
    }
    
    /**
     * 
     * Description: 生成提现订单号 年月日+8位随机数
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-22 下午5:12:01
     */
    public static String getWithDrawOrderId() {
        Date now = new Date();
        String sdate = DateUtil.date2Str(now, "yyyyMMdd");
        double random = Math.random();
        String sRandom = String.valueOf((int) (random * 899999 + 100000));
        return SxyPayConstants.Config.SXY_SHBH+"-"+sdate+"-"+sRandom;
    }

    /**
     * 
     * Description: 生成投资编号 年月日+8位随机数
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-22 下午5:12:01
     */
    public static String getInvestId() {
        return getOrderId();
    }

    /**
     * 
     * Description: 生成项目编号 年月日+8位随机数
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-22 下午5:12:01
     */
    public static String getLoanId() {
        return getOrderId();
    }
    
    /**
     * 
     * Description: 生成项目编号 年月日+8位随机数
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-22 下午5:12:01
     */
    public static String getGroupNo() {
        return getOrderId();
    }
    
    /**
     * 
     * Description:自动生成用户id(4位安全码+6为随机数) 
     *
     * @param 
     * @return String
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-6-19 上午11:12:37
     */
    public static String getUserId(){
        String securityCode = SecurityCode.getSecurityCode();
        String sRandom = String.valueOf((int) (Math.random() * 899999 + 100000));
        return securityCode + sRandom;
    }
    
    /**
     * 生成用户名
     * @param Prefix  前缀
     * @param mobile  手机号码
     * @return
     */
    public static String getUserId(String Prefix,String mobile){
        String last4DigitalByMobile = mobile.substring(7);
        String result = Prefix+getRandomString(4)+last4DigitalByMobile;
        return result;
    }
    
    public static String getRandomString(int length){  
        String str="abcdefghijklmnopqrstuvwxyz";  
        Random random=new Random();  
        StringBuffer sb=new StringBuffer();  
        for(int i=0;i<length;i++){  
            int number =random.nextInt(26);  
            sb.append(str.charAt(number));  
        }  
        return sb.toString();  
    } 

    public static void main(String[] args) {
        // String[] ss = getId(10);
        // for (int i = 0; i < ss.length; i++) {
        // System.out.println(ss[i]);
        // }
        System.out.println(getWithDrawOrderId());
    }
    
    /**
     * 
     * Description: 生成挂牌编号 年月日+8位随机数
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-22 下午5:12:01
     */
    public static String getTransferId() {
        return getOrderId();
    }

}
