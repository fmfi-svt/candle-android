package com.svt.candle;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import android.util.Log;

/**
 * Trieda,ktorá sťahuje dáta z internetu podľa nicku, volaná zvyčajne
 * konštruktorom DataStorageInternet.
 */

public class ThreadInternet extends Thread {
	String text = "";
	HttpURLConnection http = null;
	InputStream is = null;
	String nickUrl = "";
	int len = 0;

	public ThreadInternet(int len) {
		this.len = len;
	}
	//ak nezadame dlzku, nacita sa cela stranka
	public ThreadInternet() {
	}

	// always verify the host - dont check for certificate
	final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	};

	/**
	 * Trust every server - dont check for any certificate
	 */
	private static void trustAllHosts() {
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return new java.security.cert.X509Certificate[] {};
			}

			public void checkClientTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}
		} };

		// Install the all-trusting trust manager
		try {
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection
					.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Changing InputStream to string.
	 */
	public String readIt(InputStream stream, int len) throws IOException,
			UnsupportedEncodingException {
		Reader reader = null;
		reader = new InputStreamReader(stream, "UTF-8");
		char[] buffer = new char[len];
		reader.read(buffer);
		return new String(buffer);
	}

	/**
	 * Return string with data.
	 */
	public String getDataFromInternet() {
		return text;
	}

	public InputStream getIS(){
		return is;
	}
	
	/**
	 * Connect to the server and download data, make string from it.
	 * 
	 * Max length of sting is 2000 characters.
	 */
	@Override
	public void run() {
		try {
			URL url = new URL("http://davinci.fmph.uniba.sk/~filek1/rozvrh.xml");
			// bezpecnost este prekonzultujeme.
			if (url.getProtocol().toLowerCase().equals("https")) {
				trustAllHosts();
				HttpsURLConnection https = (HttpsURLConnection) url
						.openConnection();
				https.setHostnameVerifier(DO_NOT_VERIFY);
				http = https;
			} else {
				http = (HttpURLConnection) url.openConnection();
			}

			http.connect();
			is = http.getInputStream();
			text = readIt(is, len);

		} catch (Exception e) {
			Log.w("Debug", e.getMessage());
			text = Integer.toString(-1);
		}

	}
}
