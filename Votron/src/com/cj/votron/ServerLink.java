package com.cj.votron;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
		
		public Fetch(String query, Activity activity, String label){
			this.query = query;
			this.activity = activity;
			this.label = label;
		}

		/* (non-Javadoc)
		 * @see com.cj.votron.AsyncWebAction#exec()
		 */
		@Override
		public void exec() {
						
//			HttpClient httpClient = new DefaultHttpClient();
//			HttpContext localContext = new BasicHttpContext();
//			HttpGet httpGet = new HttpGet(query);
			try {
				HttpURLConnection con = 
						(HttpURLConnection) new URL(url).openConnection();
				result = readStream(con.getInputStream());
				
//				HttpResponse response = httpClient.execute(httpGet, localContext);
//				HttpEntity entity = response.getEntity();
//				result = getASCIIContentFromEntity(entity);
			} catch (Exception e) {
				crash("Error:  Exec crashed", e);
			}
			return;
		}
		
		private String readStream(InputStream in) {
			  BufferedReader reader = null;
			  StringBuilder sb = new StringBuilder();
			  try {
			    reader = new BufferedReader(new InputStreamReader(in));
			    String line = "";
			    while ((line = reader.readLine()) != null) {
			    	sb.append(line);
			    }
			  } catch (IOException e) {
			    e.printStackTrace();
			  } finally {
			    if (reader != null) {
			      try {
			        reader.close();
			      } catch (IOException e) {
			        e.printStackTrace();
			        }
			    }
			  }
			  return sb.toString();
			} 

		/* (non-Javadoc)
		 * @see com.cj.votron.AsyncWebAction#followUp()
		 */
		@Override
		public void followUp() {
			System.out.println("DBG: Follow up");
			System.out.println(result);
			Config.getInstance().setParam(label, result);
			DebugActivity.displayBuffer = result;
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
	}

	String buffer;
	Activity currentActivity;
	private final static String URL_TARGET = "";
	static ServerLink instance = new ServerLink();

	public static ServerLink getInstance() { return instance; }

	private static final String DBPEDIAQ = "http://dbpedia.org/sparql?default-graph-uri=http%3A%2F%2Fdbpedia.org&query=";
	private static final String DPEDIA_JSONSPEC = "&format=json";

	void getDbpediaQuery(String query, Activity activity) {
		Fetch fetch = new Fetch(query, activity, "dbpedia");
		asyncAction(activity,fetch);
		return;
	}
	
	void asyncAction(final Activity activity, final Fetch fetch) {
		new AsyncTask<Context, Void, Void>() {
			@Override
			protected Void doInBackground(Context... backgroundListOfParameters) {
				try {
					fetch.exec();
					fetch.followUp();
				} catch (Exception e) {
					crash("Error: Async crashed",e);
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


	private void debug(String msg) {
		// TODO: FOR DEBUGGING
		// Log.d(getClass().getName(), msg);
	}
}
