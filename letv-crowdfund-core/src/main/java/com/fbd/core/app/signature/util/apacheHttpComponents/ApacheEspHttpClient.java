package com.fbd.core.app.signature.util.apacheHttpComponents;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import com.fbd.core.app.signature.util.Base64Encoder;
import com.fbd.core.app.signature.util.EspHttpClient;

public class ApacheEspHttpClient extends EspHttpClient {
	private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
	private static final String CHARSET = "UTF-8";

	public String postSignedJson(String requestUri, JSONObject reqData, String appSecret)
			throws IOException, NoSuchAlgorithmException, InvalidKeyException {
		System.setProperty("jsse.enableSNIExtension", "false");

		CloseableHttpClient httpClient = HttpClients.createDefault();
		Key signingKey = new SecretKeySpec(appSecret.getBytes(), HMAC_SHA1_ALGORITHM);
		Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
		mac.init(signingKey);
		byte[] rawData = reqData.toString().getBytes(CHARSET);
		byte[] rawHmac = mac.doFinal(rawData);
		HttpPost httpPost = new HttpPost(requestUri);
		httpPost.addHeader("Content-Type", "application/json; charset=utf-8");
		httpPost.addHeader("Accept", "application/json; charset=utf-8");
		httpPost.addHeader("Content-Signature", "HMAC-SHA1 " + new String(new Base64Encoder().encode(rawHmac)));
		ByteArrayEntity reqEntity = new ByteArrayEntity(rawData);
		httpPost.setEntity(reqEntity);
		CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		copy(httpResponse.getEntity().getContent(), outputStream);
		String respStr = new String(outputStream.toByteArray(), CHARSET);
		if (httpResponse.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {
			return respStr;
		} else {
			throw new IOException(httpResponse.getStatusLine().toString() + " ERROR\nResponseText=" + respStr);
		}
	}
}
