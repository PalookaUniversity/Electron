package com.cj.votron;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

public class ServerLink {
	
	void crash(String msg, Throwable t){
		System.out.println(msg);
		t.printStackTrace();
		throw new RuntimeException(msg, t);
	}
	
	public class Fetch implements AsyncWebAction {
		
		String url;
		String result;
		String status = "OK";
		String query;
		Activity activity;
		String label;

		/* (non-Javadoc)
		 * @see com.cj.votron.AsyncWebAction#exec()
		 */
		@Override
		public void exec() {
			HttpClient httpClient = new DefaultHttpClient();
			HttpContext localContext = new BasicHttpContext();
			HttpGet httpGet = new HttpGet(query);
			try {
				System.out.println("DBG getPageText" + "about to execute httpClient");
				if (httpClient == null) {
					crash("WTF?  No httpClient?",null);
				}
				HttpResponse response = httpClient.execute(httpGet, localContext);
				if (response == null) {
					crash("WTF?  No response?",null);
				}
				System.out.println("DBG getPageText" + "about to get entity");
				HttpEntity entity = response.getEntity();
				if (entity == null) {
					crash("WTF?  No http entity?",null);
				}
				System.out.println("DBG getPageText" + "about to get ascii content");
				result = getASCIIContentFromEntity(entity);

			} catch (Exception e) {
				System.out.println("Error!");
				System.out.println("DBG: Exception:" + e.getMessage());
				status =  e.getLocalizedMessage();
			}
			System.out.println("DBG: exec complete!");
			return;

		}

		/* (non-Javadoc)
		 * @see com.cj.votron.AsyncWebAction#followUp()
		 */
		@Override
		public void followUp() {
			System.out.println("DBG: Follow up");
			System.out.println(result);
			Config.getInstance().setParam(label, result);
		}

		/* (non-Javadoc)
		 * @see com.cj.votron.AsyncWebAction#setQuery()
		 */
		@Override
		public AsyncWebAction setQuery(String query) {
			this.query = query;
			return this;
		}

		/* (non-Javadoc)
		 * @see com.cj.votron.AsyncWebAction#getStatus()
		 */
		@Override
		public String getStatus() { return status; }

		/* (non-Javadoc)
		 * @see com.cj.votron.AsyncWebAction#getResult()
		 */
		@Override
		public String getResult() { return result; }

		@Override
		public AsyncWebAction setActivity(Activity activity) {
			this.activity = activity;
			return this;
		}

		@Override
		public AsyncWebAction setLabel(String label) {
			this.label = label;
			return this;
		}

		@Override
		public String getLabel() { return label; }
	}

	String buffer;
	Activity currentActivity;
	private final static String URL_TARGET = "";

	static ServerLink instance = new ServerLink();

	public static ServerLink getInstance() {
		return instance;
	}

	private static final String DBPEDIAQ = "http://dbpedia.org/sparql?default-graph-uri=http%3A%2F%2Fdbpedia.org&query=";
	private static final String DPEDIA_JSONSPEC = "&format=json";

	void getDbpediaQuery(String query, Activity activity) {
		Fetch fetch = new Fetch();
		fetch.setQuery(DBPEDIAQ + query + DPEDIA_JSONSPEC).setLabel("dbpedia").setActivity(activity);
		asyncAction(activity,fetch);
		return;
	}


	
	void asyncAction(final Activity activity, final Fetch fetch) {
		new AsyncTask<Context, Void, Void>() {
			@Override
			protected Void doInBackground(Context... backgroundListOfParameters) {
				debug("async task started");
				try {
					System.out.println("DBG Async in BG");
					fetch.exec();
					fetch.followUp();
					System.out.println("DBG Async BG complete");

				} catch (Exception e) {
					System.out.println("Async crashed");					
					System.out.println(e.getMessage());
					e.printStackTrace();
					debug("error in doInBackground: " + e.getMessage());
				}
				return null;
			}
		}.execute();
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

	void doHttpGet(final Activity activity, final String target) {
		new AsyncTask<Context, Void, Void>() {
			@Override
			protected Void doInBackground(Context... backgroundListOfParameters) {
				debug("async task started");
				try {
					String result = restCollect(target);
					Config.getInstance().setParam(target, result);
					// Do something with the result...
					// Save result in config data
					// Use currentActivity to display 
				} catch (Exception e) {
					debug("error in doInBackground: " + e.getMessage());
				}
				return null;
			}
		}.execute();
	}

	private void debug(String msg) {
		// TODO: FOR DEBUGGING
		// Log.d(getClass().getName(), msg);
	}

	private HttpClient client = new DefaultHttpClient();

	void setHttpClient(HttpClient c) {
		client = c;
	}

	private String restCollect(String targetUrl) {
		Log.d(URL_TARGET, "going to " + targetUrl);
		HttpGet httpGet = new HttpGet(targetUrl);
		HttpResponse response = null;
		Integer httpStatus = null;
		String result = "NO DATA";
		try {
			response = client.execute(httpGet);
			httpStatus = response.getStatusLine().getStatusCode();
			result = EntityUtils.toString(response.getEntity());

			Log.d(URL_TARGET, "got as a result: " + result);

		} catch (IOException e) {
			Log.d(URL_TARGET, "sendToCj: something bad: " + e.getMessage());
			result = "Error: " + e.getMessage();
		}
		Log.d(URL_TARGET, "status is " + httpStatus);
		return result;
	}
}
