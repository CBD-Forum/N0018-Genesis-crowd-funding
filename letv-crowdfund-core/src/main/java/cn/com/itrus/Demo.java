package cn.com.itrus;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;
//import com.itrus.itrusutil.ItrusUtil;
import com.itrus.util.Base64;


public class Demo {
	
	/**
	 * 签名方法
	 * @param pfx pfx证书文件名
	 * @param password pfx证书密码
	 * @param message	签名原文
	 * @return 签名返回报文
	 */
	public String signMessage(String message,String pfx,String password){
		JSONObject json = new JSONObject();
		try {
			json.put("pfx", pfx);
			json.put("password", password);
			json.put("Message", message);
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
//		ItrusUtil util = new ItrusUtil();
////		String result = util.getItrusUtilHttpPort().signature(json.toString());
//		return result;
		return "";
	}
	
	/**
	 * 验证签名
	 * @param oriData   原文
	 * @param signData    签名结果
	 * @return   验签结果
	 */
	public String verifyMessage(String oriData,String signData){
		JSONObject json = new JSONObject();
		try {
			json.put("Message", oriData);
			json.put("signMessage", signData);
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
//		ItrusUtil util = new ItrusUtil();
//		String result = util.getItrusUtilHttpPort().verify(json.toString());
//		return result;
		return "";
	}
	
	/**
	 * 读取pfx证书并转换成base64编码
	 * @param path pfx路径 
	 * @return base64编码的pfx证书
	 */
	public String getBase64Pfx(String path){
		String pfxCert = "";
		try {
			File file = new File(path);
			FileInputStream fis = new FileInputStream(file);
			byte[] bys = new byte[fis.available()];
			fis.read(bys);
			fis.close();
			pfxCert = Base64.encode(bys,false);			
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return pfxCert;
	}

	public static void main(String[] args) throws JSONException {
		// TODO 自动生成的方法存根
		Demo demo = new Demo();
		String oriData = "今天十个好天气";
		String signResult = demo.signMessage(oriData,"wyTest.pfx","password");
		System.out.println("签名方法返回："+signResult);
		String verifyResult = demo.verifyMessage(oriData, new JSONObject(signResult).getString("result"));
		System.out.println("验证签名返回："+verifyResult);
		
	}

}
