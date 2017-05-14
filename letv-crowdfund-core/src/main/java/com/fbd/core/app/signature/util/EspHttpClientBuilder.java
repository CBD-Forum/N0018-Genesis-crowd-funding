package com.fbd.core.app.signature.util;

import com.fbd.core.app.signature.util.apacheHttpComponents.ApacheEspHttpClient;
import com.fbd.core.app.signature.util.nativeHttpClient.NativeEspHttpClient;

public class EspHttpClientBuilder {
	public static EspHttpClient getHttpClient() {
		return getHttpClient("native");
	}

	public static EspHttpClient getHttpClient(String provider) {
		if ("apache".equalsIgnoreCase(provider)) {
			return new ApacheEspHttpClient();
		} else {
			return new NativeEspHttpClient();
		}
	}
}
