package com.fbd.core.app.letvPay.utils;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fbd.core.app.letvPay.common.LetvPayConstants;


/**
 * @author
 * @version
 * 验签
 */
public class VerifyClient {

	private static final Logger logger = LoggerFactory.getLogger(VerifyClient.class);

	/**
     * 验签
     * @param tradeInfo
     * @param charset
     * @param formattedParameters
     * @throws CommonException
     */
    public static Boolean verifyBasic(String charset,Map<String, String> formattedParameters) throws Exception {
        //拼接签名字符串
        String signContent = Core.createLinkString(Core.paraFilter(formattedParameters),
            false);

        //传过来的签名
        String signMsg = formattedParameters.get("sign");
        if (logger.isInfoEnabled()) {
            logger.info("verify signature: { content:" + signContent + ", signMsg:"+ signMsg+ "}");
        }

        String type = formattedParameters.get("sign_type");
        boolean result = false;
        if("MD5".equals(type)){
            result = MD5.verify(signContent, signMsg, LetvPayConstants.Config.MD5KEY, charset);
        }else if("RSA".equals(type)){
            result = RSA.verify(signContent, signMsg, LetvPayConstants.Config.USP_PUBLIC_KEY, charset);
        }else{
            throw new Exception("签名方式不支持");
        }

        return result;
    }


}
