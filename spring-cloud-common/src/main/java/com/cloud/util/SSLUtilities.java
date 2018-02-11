package com.cloud.util;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
/**
 * @author summer
 */
public final class SSLUtilities {
	private static HostnameVerifier _hostnameVerifier;
	private static TrustManager[] _trustManagers;
	public static void trustAllHostnames() {
		if (_hostnameVerifier == null) {
			_hostnameVerifier = new FakeHostnameVerifier();
		}
		HttpsURLConnection.setDefaultHostnameVerifier(_hostnameVerifier);

	}
	public static void trustAllHttpsCertificates() {
		SSLContext context;
		if (_trustManagers == null) {
			_trustManagers = new TrustManager[] { new FakeX509TrustManager() };
		}
		try {
			context = SSLContext.getInstance("SSL");
			context.init(null, _trustManagers, new SecureRandom());
		} catch (GeneralSecurityException gse) {
			throw new IllegalStateException(gse.getMessage());
		}
		HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
	}
	public static class FakeHostnameVerifier implements HostnameVerifier {
		@Override
		public boolean verify(String hostname, javax.net.ssl.SSLSession session) {
			return (true);
		}
	}
	
	public static boolean isDeprecatedSSLProtocol() {
		return ("com.sun.net.ssl.internal.www.protocol".equals(System.getProperty("java.protocol.handler.pkgs")));
	}
}
