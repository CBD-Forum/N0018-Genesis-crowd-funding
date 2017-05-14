package cn.com.itrus;

import java.io.UnsupportedEncodingException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.util.Vector;

import com.itrus.cert.Names;
import com.itrus.cert.X509Certificate;
import com.itrus.cryptorole.CryptoException;
import com.itrus.cryptorole.SignatureVerifyException;
import com.itrus.cryptorole.bc.RecipientBcImpl;
import com.itrus.cvm.CVM;
import com.itrus.svm.SignerAndEncryptedDigest;
import com.itrus.util.Base64;

public class Tools {

	public boolean verifyMessage(String oriData,String signData) throws UnsupportedEncodingException{
		boolean flag = false;
		RecipientBcImpl recipient = new RecipientBcImpl();
		X509Certificate cert = null;
		try {
			SignerAndEncryptedDigest ret = recipient.verifyAndParsePkcs7(oriData.getBytes("UTF-8"), Base64.decode(signData));
			cert = X509Certificate.getInstance(ret.getSigner());
		} catch (SignatureVerifyException e) {
			// TODO 自动生成的 catch 块
			System.out.println("签名验证失败。原文已遭篡改。");
			e.printStackTrace();
		} catch (CryptoException e) {
			// TODO 自动生成的 catch 块
			System.out.println("签名验证失败。");
			e.printStackTrace();
		} catch (CertificateEncodingException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		if(cert != null){
			int ret = CVM.verifyCertificate(cert);
			if (ret != CVM.VALID) {
				switch (ret) {
				case CVM.CVM_INIT_ERROR:
					System.out.println("CVM初始化错误，请检查配置文件或给CVM增加支持的CA。");
					break;
				case CVM.CRL_UNAVAILABLE:
					System.out.println("CRL不可用，未知状态。");
					break;
				case CVM.EXPIRED:
					System.out.println("证书已过期。");
					break;
				case CVM.ILLEGAL_ISSUER:
					System.out.println("非法颁发者。");
					break;
				case CVM.REVOKED:
					System.out.println("证书已吊销。");
					break;
				case CVM.UNKNOWN_ISSUER:
					System.out.println("不支持的颁发者。请检查cvm.xml配置文件");
					break;
				case CVM.REVOKED_AND_EXPIRED:
					System.out.println("证书被吊销且已过期。");
					break;
				}
			}else{
				System.out.println("证书序列号："+cert.getSerialNumber());
	 		 	Names subjects = cert.getSubjectNames();
	 		 	String name = subjects.getItem("CN");//姓名
	 		 	String email = subjects.getItem("E");//邮箱
	 		 	String SN = subjects.getItem("SN");//
	 		 	String uid = "";//系统账号
	 		 	System.out.println("姓名："+name);
	 		 	System.out.println("邮箱："+email);
	 		 	Vector<String> vec = subjects.getItems("OU");
	 		 	for(int i=0;i<vec.size();i++){
	 		 		if(vec.get(i).indexOf("UID:")>-1){
	 		 			uid = vec.get(i).substring(4, vec.get(i).length());
	 		 		}
	 		 	}
	 		 	System.out.println("系统账号："+uid);

				flag = true;
			}
		}

		return flag;
	}
	
	public boolean signFile(){
		boolean flag = false;
		
		return flag;
	}
	
	public static void main(String[] args) {
		String ori = "时间很晚了，good bye!";
		String signData = "MIIFdAYJKoZIhvcNAQcCoIIFZTCCBWECAQExDjAMBggqhkiG9w0CBQUAMAsGCSqGSIb3DQEHAaCCBAswggQHMIIC76ADAgECAhQUxUHEttgiqPkgUGTd/zKkH2UexzANBgkqhkiG9w0BAQUFADBsMSYwJAYDVQQDDB3lpKnlqIHor5rkv6FSU0HmtYvor5XnlKjmiLdDQTESMBAGA1UECwwJUlNB5rWL6K+VMSEwHwYDVQQKDBjlpKnlqIHor5rkv6HmtYvor5Xns7vnu58xCzAJBgNVBAYTAkNOMB4XDTE1MDUwNTEyMTMzNloXDTE2MDUwNDEyMTMzNlowYTEYMBYGA1UECgwP5aSp5aiB6K+a5L+hUlNBMQswCQYDVQQLDAJSQTEgMB4GCSqGSIb3DQEJARYRdGVzdEBpdHJ1cy5jb20uY24xFjAUBgNVBAMMDea1i+ivleivgeS5pjEwgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAOhhvQm2S8w+zBviOHh5rM5gpO1/1Y84943wyWRbHhlhkFmfcl9kKO5tkiG9eOYnIfRyjxQipFBDWWrkwNkPRWaCaSuKbc2mzwfwyc/6rcb5YgO/RodfCdzxMlS8X1VTKSlujfr3cFXlipY7gOaCqVUY+0EISjY2hwO2mFrDXB9lAgMBAAGjggEuMIIBKjAJBgNVHRMEAjAAMAsGA1UdDwQEAwIFoDBnBgNVHS4EYDBeMFygWqBYhlZodHRwOi8vaXRydXNkZW1vLmNvbS9Ub3BDQS9wdWJsaWMvaXRydXNjcmw/Q0E9MEM5QThGRTBBOTdGREZFMzFFRjFFN0IzREQwNEQ1QjIxRjM4NjY4MzBnBgNVHR8EYDBeMFygWqBYhlZodHRwOi8vaXRydXNkZW1vLmNvbS9Ub3BDQS9wdWJsaWMvaXRydXNjcmw/Q0E9MEM5QThGRTBBOTdGREZFMzFFRjFFN0IzREQwNEQ1QjIxRjM4NjY4MzAfBgNVHSMEGDAWgBRv2lIMgKi5NURg2FZB0+SvCQCIOjAdBgNVHQ4EFgQU3ediDf2I0YoSTIU0P/Ul2X5tbS4wDQYJKoZIhvcNAQEFBQADggEBAIzlxc/FFpOWW81Gc/XP3PYm1I52dJRkFzAjdMLyTUb3Rdh9Vs710/tkzY319tk6PwBu1IsYqJU1Vbo48s3WKCn3JeiOuFHX1pe5aiiMnO9TxxGqdvwVziWrjubdH5mStMKEd/ZGM0mhkn+1PcwzzWRbkjSCOxo2sJT6d7u2L4CVPcaXBaNhaEbgUeHHEsRc6kKg7lDSSPhSeLLyfYy6W8mXjJLmWawTq24oufNt57IWCJyKUlXudEfshIp3DDbCj+PVfIPvzdsuLPx9akSPza8GqPEH9DdXWl8KKfz5PcD5HOyv5DLIb7BzacAMMwT6rxviQ25T/9lFmuH5TsBzvDQxggEuMIIBKgIBATCBhDBsMSYwJAYDVQQDDB3lpKnlqIHor5rkv6FSU0HmtYvor5XnlKjmiLdDQTESMBAGA1UECwwJUlNB5rWL6K+VMSEwHwYDVQQKDBjlpKnlqIHor5rkv6HmtYvor5Xns7vnu58xCzAJBgNVBAYTAkNOAhQUxUHEttgiqPkgUGTd/zKkH2UexzAMBggqhkiG9w0CBQUAMA0GCSqGSIb3DQEBAQUABIGABfqm+mCfw2cNPvSX29zslp4PqMjagbYKBHwByhz0wcIiiFSnPjbQAxynN64yHZa9Nuz272Kqeki88YyurixKteo92A8YnyWHTCwvRDTQkd0DS2D8uedTL5WqGOjSdBRIzBtFrAc+lroNYxNmyptKEeloX3IvWEAYBjSRDXu9hRI=";
		
		Tools tool = new Tools();
		try {
			tool.verifyMessage(ori, signData);
		} catch (UnsupportedEncodingException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
	}
}
