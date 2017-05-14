package com.fbd.core.app.signature.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.json.JSONObject;

public abstract class EspHttpClient {
	abstract public String postSignedJson(String requestUri, JSONObject reqData, String appSecret)
			throws IOException, NoSuchAlgorithmException, InvalidKeyException;

	public long copy(InputStream input, OutputStream output) throws IOException {
		byte[] buffer = new byte[1024 * 4];
		long count = 0;
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}
}
