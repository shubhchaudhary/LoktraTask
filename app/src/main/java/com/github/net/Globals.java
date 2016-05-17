package com.github.net;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Globals {

	public final static String TAG = "Mobijinn";

	// wifi and data connection constants
	public static int wifiStatus;

	public static String wifiStatusString;

	public static boolean isWifiAvailable;

	public static boolean isMobileDataAvailable;

	public static int airplaneModeStatus;
	Context context;

	public static int width;
	public static int height;
	private SharedPreferences sharedPref;
	private Editor editor;

	private static final String SHARED = "SAGO";
	private static final String LOGIN = "LOGIN";


	public Globals(Context context) {
		sharedPref = context.getSharedPreferences(SHARED, Context.MODE_PRIVATE);
		editor = sharedPref.edit();
		this.context = context;
	}

	public void setLogin(String status) {
		editor.putString(LOGIN, status);
		editor.commit();
	}

	public String getLogin() {
		return sharedPref.getString(LOGIN, "false");

	}
}
