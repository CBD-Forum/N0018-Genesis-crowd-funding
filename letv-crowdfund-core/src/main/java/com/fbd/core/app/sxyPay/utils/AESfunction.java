package com.fbd.core.app.sxyPay.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Encoder;



public class AESfunction {
	public static void main(String[] args) throws Exception {
		AESfunction aesfunction = new AESfunction();
		String enStr = aesfunction.Encrypt("6225768760065217", "MTIzNDU2NzgxMjM0");
		System.out.println(enStr);
	}
	// AES加密
    public static String Encrypt(String sSrc, String sKey) throws Exception {
        byte[] raw = sKey.getBytes("UTF-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("UTF-8"));
        String encryptedStr = new BASE64Encoder().encode(encrypted);
        
        return java.net.URLEncoder.encode(encryptedStr, "UTF-8");//64位编码
    }


}
