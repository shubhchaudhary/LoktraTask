package com.github.net;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.util.Log;


public class ConnectionChecker extends Activity {
	private static final int STATE_DISABLED = 0;
	private static final int STATE_ENABLED = 1;
	private static final int STATE_TURNING_ON = 2;
	private static final int STATE_TURNING_OFF = 3;
	private static final int STATE_UNKNOWN = 4;
	/**
	 * Listners for the Wi-fi status and airplane mode  
	 */

	public static class serviceStateReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			Globals.airplaneModeStatus = Settings.System.getInt(
					context.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0);
			if (intent.getAction().equalsIgnoreCase(Intent.ACTION_AIRPLANE_MODE_CHANGED)) {
				if (Globals.airplaneModeStatus == (Settings.System.getInt(
						context.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0))) {
					Log.d("AirplanMode", "AirplanMode ON Or Off");
				}
				Log.d(Globals.TAG,
						"Airplane Mode Service state changed-->"
								+ Settings.System.getInt(context.getContentResolver(),
								Settings.Global.AIRPLANE_MODE_ON, 0));
			}
			if (intent.getAction().equalsIgnoreCase(WifiManager.WIFI_STATE_CHANGED_ACTION)) {

			}
		}
	}

	/**
	 * Check for the data connection status
	 */
	public static void checkConnection(Context context) {
		ConnectivityManager connMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		android.net.NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		android.net.NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if(wifi != null){
			Globals.isWifiAvailable = wifi.isAvailable() && wifi.isConnected();
		}
		if(mobile != null){
			Globals.isMobileDataAvailable = mobile.isAvailable() && mobile.isConnected();
		}             
	}    

	public static boolean selfLearnig(Context context) {
		checkConnection(context);
		boolean status = false;
		if (Globals.airplaneModeStatus == (Settings.System.getInt(
				context.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0))) {
			status = true;
			if (Globals.isWifiAvailable) {
				status = true;
			} else {
				status = Globals.isMobileDataAvailable;
			}
		} else {
			status = false;
			Log.d(Globals.TAG, "Air Plane Mode is On No Data Connection");
		}
		return status;
	}
}

