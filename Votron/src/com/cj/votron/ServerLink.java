package com.cj.votron;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.util.Log;

public class ServerLink {

	static ServerLink instance = new ServerLink();

	public static ServerLink getInstance() { return instance; }

	String getPageText(String uri) {
		System.out.println(uri);
		HttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();
		HttpGet httpGet = new HttpGet(uri);
		String text = null;
		try {
      Log.d("getPageText","about to execute httpClient");
			HttpResponse response = httpClient.execute(httpGet, localContext);
			Log.d("getPageText","about to get entity");
			HttpEntity entity = response.getEntity();
			Log.d("getPageText","about to get ascii content");
			text = getASCIIContentFromEntity(entity);

		} catch (Exception e) {
			System.out.println("DBG: Exception:"+e.getCause().toString());
			return e.getLocalizedMessage();
		}
		System.out.println("DBG: It's alive!");
		return text;
	}

	String getASCIIContentFromEntity(HttpEntity entity)
	    throws IllegalStateException, IOException {

		InputStream in = entity.getContent();
		StringBuffer out = new StringBuffer();
		int n = 1;
		while (n > 0) {
			byte[] b = new byte[4096];
			n = in.read(b);
			if (n > 0)
				out.append(new String(b, 0, n));
		}
		return out.toString();
	}

	String getText(String uri) {
		return "uri foo ";
	}
}
