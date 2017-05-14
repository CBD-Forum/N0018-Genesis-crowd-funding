package cn.com.itrus;

import cn.com.itrus.raapi.CertInfo;
import cn.com.itrus.raapi.RaServiceUnavailable_Exception;
import cn.com.itrus.raapi.UserAPIService;
import cn.com.itrus.raapi.UserAPIServicePortType;
import cn.com.itrus.raapi.UserInfo;

public class EnrollDemo {

	public static void main(String[] args) {
		CertInfo certInfo = null;
    	String certSignBufP7 = null;
		String userName = "12121212121212";
		String userEmail = "12@12.com";
		String certReqBuf = "MIICXzCCAUcCAQAwGjEYMBYGA1UEAxMPQ049aXRydXNfZW5yb2xsMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtSaSADrTi10519vqzl6ukAVtIIh6+Z5Km43VDw6OWXggK74N0/hsQIrOJ2J193QkQQmwoqofbAypuh7eNgv3L3yKO7GwDpXF8vVOAzlTNb5YsPa5kx377kXXiToPyC5s3ZauGRV4cyic3R9wbx8fFlg9XBIL3ocANPANeY6WLfFdzLXt6YJtVRgo5I7mc4bwaZ8wNFLzZo9WqSui/lpmy7pKYP833rv2hfIDhX2nO8Z3H/gL/dIoB1VLtrDhddhe7T3AoHznXQK6jUpM8PcW5KfcONN9aEGtRKx0iSrbKReffx8A7r2FIpJMlC12KvXRhFbNrEyTmjlYRXQlWsD2OQIDAQABoAAwDQYJKoZIhvcNAQEFBQADggEBACqIaN3hZa9DkM2+qfVDYV/JGKDRkWZ7WIbVQWaGx+53TJUy4P8PHQJ7eaUDFyQf+fu+LagIJXpDIobfqcZpatn3qVe5xaYB4OeHO3Lp2dWjqWB8jv9TDT4qYs1ynOxfmMpaIvMxjffVPg2KhqqFDgx1+Yu02eiQn1cNgqYL7WIjDXr+6iXovmSK0NFYK28TFMetJflPx443TzPCecEei3t3K53wwHmA9usDSCare0/ieV6ds/q6j1ltFw3nQvyutKBU00i80JCCj2yOAzBYkNMtmrVAM0IQGJjtbuN64DJrApG8J0Y+Ub2o78vQyit0Y29fMpiEQ4uDhvcKtX7wp2A=";
		Config conf = new Config();
		
		UserInfo userInfo = new UserInfo();
		userInfo.setUserName(userName);
		userInfo.setUserEmail(userEmail);
		
		UserAPIService service = new UserAPIService();
		UserAPIServicePortType userApi = service.getUserAPIServicePort();
		
		try {
			certInfo = userApi.enrollCertAA(userInfo, certReqBuf, conf.getAccountHash(), conf.getPasswprd(), null, null);
		} catch (RaServiceUnavailable_Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		certSignBufP7 =certInfo.getCertSignBufP7();
		System.out.println(certSignBufP7);

	}

}
