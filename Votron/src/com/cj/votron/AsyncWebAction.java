/**
 * 
 */
package com.cj.votron;

import android.app.Activity;

/**
 * @author gvamos
 *
 * Interface for asynch web requests
 */
public interface AsyncWebAction {
	
	public void exec();
	public void followUp();
	public AsyncWebAction setQuery(String query);
	public AsyncWebAction setActivity(Activity activity);
	public String getStatus();
	public String getResult();
	public AsyncWebAction setLabel(String label);
	public String getLabel();
}
