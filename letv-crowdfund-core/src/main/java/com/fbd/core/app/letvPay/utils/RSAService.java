package com.fbd.core.app.letvPay.utils;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fbd.core.app.letvPay.common.LetvPayConstants;

/**
 * RSA加签，验签
 *
 */
//@Service
//@Lazy
public class RSAService {

    private static Logger  logger  = LoggerFactory.getLogger(RSAService.class);

    /**
     * 商户公钥验签
     * @param partnerPublicKey
     * @param str
     * @param signature
     * @param charset
     * @return
     */
    public boolean check(String partnerPublicKey, String str, String signature, String charset) {
        boolean result = false;
        if (logger.isDebugEnabled()) {
            logger.debug(" check content:" + str + " with signature:"
                         + signature);
        }

        try {
            result = RSA.verify(str, signature, partnerPublicKey, charset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 钱包私钥签名
     * @param walletPrivateKey
     * @param content
     * @param charset
     * @return
     */
    public String sign(String walletPrivateKey, String content, String charset) {
        String result = null;
        if (logger.isDebugEnabled()) {
            logger.debug("sign content:" + content);
        }

        // TODO：通过“商户配置”查询对应partnerId的MD5密钥,这里先用一个假的
        try {
            result = RSA.sign(content, walletPrivateKey, charset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * 使用公钥加密
     * @param src
     * @param charset
     * @return
     */
    public static String encryptPub(String src ,String charset){

        try {
            byte[] bytes = RSA.encryptByPublicKey(src.getBytes(),LetvPayConstants.Config.USP_PUBLIC_KEY);
            return Base64.encodeBase64String(bytes);
        } catch (Exception e) {
            logger.error("RSA encrypt failure:src=," + src + "charset="+charset);
        }
        return null;
    }

}
