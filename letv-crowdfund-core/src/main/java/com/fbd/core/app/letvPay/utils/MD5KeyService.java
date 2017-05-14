package com.fbd.core.app.letvPay.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MD5加签，验签
 *
 */
//@Service
//@Lazy
public class MD5KeyService {

    private static Logger       logger  = LoggerFactory.getLogger(MD5KeyService.class);

    /**
     * 验签
     * @param md5Key
     * @param str
     * @param signature
     * @param charset
     * @return
     */
    public boolean check(String md5Key, String str, String signature, String charset) {
        boolean result = false;
        if (logger.isDebugEnabled()) {
            logger.debug(" check content:" + str + " with signature:"
                         + signature);
        }

        try {
            result = MD5.verify(str, signature, md5Key, charset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 签名
     * @param md5Key
     * @param content
     * @param charset
     * @return
     */
    public String sign(String md5Key, String content, String charset) {
        String result = null;
        if (logger.isDebugEnabled()) {
            logger.debug(" sign content:" + content);
        }

        // TODO：通过“商户配置”查询对应partnerId的MD5密钥,这里先用一个假的
        try {
            result = MD5.sign(content, md5Key, charset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
