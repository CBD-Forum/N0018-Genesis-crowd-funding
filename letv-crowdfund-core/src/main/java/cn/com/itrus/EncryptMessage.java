package cn.com.itrus;



import java.io.FileNotFoundException;
import java.security.cert.CertificateException;

import org.bouncycastle.util.encoders.Base64;

import com.itrus.cryptorole.Sender;
import com.itrus.cryptorole.bc.SenderBcImpl;
import com.itrus.security.cert.X509Certificate;



public class EncryptMessage {
	
	public String encryptMess(String oriData) throws Exception{
		X509Certificate cert = X509Certificate.getInstanceFromFile("D:\\test.cer");
		Sender sender = new SenderBcImpl();
		sender.addRecipientCert(cert);
		String encMess = Base64.encode(sender.encryptMessage(oriData.getBytes())).toString();
		return encMess;
	}

	public static void main(String[] args) throws Exception {
		// TODO 自动生成的方法存根
		EncryptMessage enc = new EncryptMessage();
		String encMess = enc.encryptMess("张三李四王二麻子！");
		System.out.println(encMess);
	}

}
