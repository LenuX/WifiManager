package fr.mobiwide.wifly.ui;

import java.util.List;

import fr.mobiwide.wifly.R;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

 

	public class WifiTester extends Activity {
		
		private static final String TAG = "WifiTester";
		
		TextView mainText;
		WifiManager mainWifi;
		WifiInfo mWifiInfo;
		WifiReceiver receiverWifi;
		List<ScanResult> wifiList;
		StringBuilder sb = new StringBuilder();
		
		String ssid;
		String bssid;
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.text);
			mainText = (TextView) findViewById(R.id.textLabel);
			mainWifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
			
			mWifiInfo=mainWifi.getConnectionInfo();
			Log.i(TAG, "meuh Connec :"+ mWifiInfo.toString());

			receiverWifi = new WifiReceiver();
			registerReceiver(receiverWifi, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
			mainWifi.startScan();
			mainText.setText("\nStarting Scan...\n");
		}

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			menu.add(0, 0, 0, "Refresh");
			return super.onCreateOptionsMenu(menu);
		}

		@Override
		public boolean onMenuItemSelected(int featureId, MenuItem item) {
			mainWifi.startScan();
			mainText.setText("Starting Scan");
			return super.onMenuItemSelected(featureId, item);
		}

		@Override
		protected void onPause() {
			unregisterReceiver(receiverWifi);
			super.onPause();
		}

		@Override
		protected void onResume() {
			registerReceiver(receiverWifi, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
			super.onResume();
		}

		class WifiReceiver extends BroadcastReceiver {
			
			
			@Override
			public void onReceive(Context c, Intent intent) {
				
				sb = new StringBuilder();
				wifiList = mainWifi.getScanResults();
				
				for(int i = 0; i < wifiList.size(); i++){
					sb.append(new Integer(i+1).toString() + ".");
					sb.append((wifiList.get(i)).toString());
					sb.append("\n\n");
				}
				mainText.setText(sb);
			}
		}

	}

