/**
 * 
 */
package com.github.net;

public interface ServiceCallBacks {

	//List of callers  

	int GITHUB_CALL = 1;
	/**
	 * Call back method to pass the data to caller.
	 * @param data : response gathered from server.
	 */
	void onRequestComplete(Object data, int caller);
	
	
	/**
	 * Call back method in case the there is some error while fetching data from server
	 * @param errorString : Error string to let caller know. 
	 */
	void onError(String errorString, int caller);
	
	
	/**
	 * callback method in case service request got canceled 
	 * @param errorString: 
	 */
	void onRequestCancel(String errorString, int caller);






	
}
