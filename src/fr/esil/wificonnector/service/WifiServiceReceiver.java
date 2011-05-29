package fr.esil.wificonnector.service;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

public class WifiServiceReceiver extends BroadcastReceiver {

	private String TAG = "WifiServiceReceiver";
	private WifiManager mManWifi;
	private WifiInfo mWifiInfo;

	@Override
	public void onReceive(Context context, Intent intent) {

		Log.i(TAG, "****** WIFI STATE CHANGED ******");

		mManWifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

		if (mManWifi.isWifiEnabled()) {

			mWifiInfo = mManWifi.getConnectionInfo();

			Log.i(TAG, mWifiInfo.getSupplicantState().toString());

			if (mWifiInfo.getSupplicantState()
					.equals(SupplicantState.COMPLETED)) {

				Log.i(TAG,
						"****** WIFI STATE IS CONNECTED ******"
								+ mWifiInfo.getSSID());

				if (mWifiInfo.getSSID() != null) {
					
					Log.i(TAG, "****** LAUCHING SERVICE WIFICONNECTOR ****** ");
					
					context.startService(new Intent()
							.setComponent(new ComponentName(context
									.getPackageName(), WifiService.class
									.getName())));
				}

			}

		}
		else{
			Log.i(TAG, "****** STOPING SERVICE WIFICONNECTOR ****** ");
			
			context.stopService(new Intent()
							.setComponent(new ComponentName(context
									.getPackageName(), WifiService.class
									.getName())));
		}

	}
}
