package com.fbd.core.app.signature.util.nativeHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONObject;
import com.fbd.core.app.signature.util.Base64Encoder;
import com.fbd.core.app.signature.util.EspHttpClient;

public class NativeEspHttpClient extends EspHttpClient {
	private static final int[] DEFAULT_ACCEPTABLE_CODES = new int[] { HttpURLConnection.HTTP_OK,
			HttpURLConnection.HTTP_NOT_MODIFIED, HttpURLConnection.HTTP_MOVED_TEMP, HttpURLConnection.HTTP_MOVED_PERM,
			HttpURLConnection.HTTP_ACCEPTED };
	private int connectionTimeout = 1000 * 60;
	private int readTimeout = 1000 * 60;
	private boolean followRedirects = true;

	public String HMAC_SHA1_ALGORITHM = "HmacSHA1";

	public String postSignedJson(String requestUri, JSONObject reqData, String appSecret)
			throws IOException, NoSuchAlgorithmException, InvalidKeyException {
		System.setProperty("jsse.enableSNIExtension", "false");
		
		Key signingKey = new SecretKeySpec(appSecret.getBytes(), HMAC_SHA1_ALGORITHM);
		Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
		mac.init(signingKey);
		String jsonStr = reqData.toString();
		byte[] rawData = jsonStr.getBytes("utf-8");
		byte[] rawHmac = mac.doFinal(rawData);

		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json; charset=utf-8");
		headers.put("Accept", "application/json; charset=utf-8");
		headers.put("Content-Signature", "HMAC-SHA1 " + new String(new Base64Encoder().encode(rawHmac)));

		return new String(call(requestUri, "POST", headers, rawData), "utf-8");
	}

	public byte[] call(String url, String method, Map<String, String> headers, byte[] input) throws IOException {
		HttpURLConnection connection = null;
		try {
			final URL jsonServiceURL = new URL(url);
			connection = (HttpURLConnection) jsonServiceURL.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod(method);
			connection.setReadTimeout(readTimeout);
			connection.setConnectTimeout(connectionTimeout);
			connection.setInstanceFollowRedirects(followRedirects);

			for (Map.Entry<String, String> header : headers.entrySet()) {
				connection.setRequestProperty(header.getKey(), header.getValue());
			}

			if (input != null && input.length > 0) {
				connection.setRequestProperty("Content-Length", Integer.toString(input.length));
				DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
				dataOutputStream.write(input);
				dataOutputStream.flush();
				dataOutputStream.close();
			}

			final int responseCode = connection.getResponseCode();

			boolean acceptable = false;
			for (final int acceptableCode : DEFAULT_ACCEPTABLE_CODES) {
				if (responseCode == acceptableCode) {
					acceptable = true;
					break;
				}
			}

			if (acceptable) {
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				copy(connection.getInputStream(), output);
				return output.toByteArray();
			} else {
				throw new IOException(responseCode + " " + connection.getResponseMessage());
			}
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}
}
