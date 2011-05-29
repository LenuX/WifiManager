package fr.esil.wificonnector.service;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import net.esil.wificonnector.object.Form;
import net.esil.wificonnector.object.Input;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.rabgs.http.browser.Browser;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.util.Log;
import fr.esil.wificonnector.EnvWifi;
import fr.esil.wificonnector.R;
import fr.esil.wificonnector.object.Wifi;
import fr.esil.wificonnector.parser.XmlParser;
import fr.esil.wificonnector.ui.HomeActivity;
import fr.esil.wificonnector.ui.ProfileActivity;

public class WifiService extends Service {

	private String TAG = "WifiService";
	private WifiManager mManWifi;
	private WifiInfo mWifiInfo;
	private Form mForm;
	private Wifi mWifi;

	@Override
	public void onCreate() {
		
		Log.i(TAG, "****** Service ON CREATE");
		
		mManWifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		mWifiInfo = mManWifi.getConnectionInfo();
		
		// On regarde si un profil existe pour ce ssid
		
		if( isProfilExist()){
			
			
			File xmlFile = new File(getFilesDir() + "/" + EnvWifi.PROFILE_DIR +"/"+ mWifiInfo.getSSID() + ".xml");
	        mWifi = XmlParser.read(xmlFile);
			mForm=mWifi.getForm();
			
				Log.i(TAG, "Profil deja existant -> post ");
				//On emet la requete POST pour s'authentifier
				Browser mBrowser = new Browser(true);
				
				List<NameValuePair> postData = new LinkedList<NameValuePair>();
				
				for (Input input : mForm.getInputList()) {
					// beware value sent for checkbox
					BasicNameValuePair data = new BasicNameValuePair(input.getName(), input.getValue());
					postData.add(data);
					System.out.println(data);
				}
				
				if(mWifi.getSSID().equals("UNIVMED")){
					mBrowser.doHttpPost("https://securelogin.arubanetworks.com" +mForm.getAction(), postData);
					Log.i(TAG, "https://securelogin.arubanetworks.com" +mForm.getAction());		
				}
				
				mBrowser.doHttpPost(mForm.getAction(), postData);
				Log.i(TAG, "https://securelogin.arubanetworks.com" +mForm.getAction());		
				
				wifiNotify2(mWifiInfo.getSSID());
			
		}
		else{		// pas de profil, on propose d'en creer un nouveau
			
			// Creation et envoi de la notification :
			wifiNotify1(mWifiInfo.getSSID());
		}
		

		


	}

					

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	public void wifiNotify1(String SSID){
		
		Log.i(TAG, "****** NOTIFY");
		
		NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		nm.cancel(R.string.app_name);
		
		Notification notifWifi = new Notification(R.drawable.iconwc,"Wifi Connector :",System.currentTimeMillis());
		
		Intent intent = new Intent( getApplicationContext(), HomeActivity.class);
		
		PendingIntent clikintent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
		
		String text = "Vous etes connecte au reseau :" + SSID;
		
		notifWifi.setLatestEventInfo(getBaseContext(), "Wifi Connector",text, clikintent);
		
		nm.notify(R.string.tag_notif, notifWifi);
	}
	
	public void wifiNotify2(String SSID){
		
		Log.i(TAG, "****** NOTIFY2");
		
		NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		nm.cancel(R.string.app_name);
		
		Notification notifWifi = new Notification(R.drawable.iconwc,"Wifi Connector",System.currentTimeMillis());
		
		Intent intent = new Intent( getApplicationContext(), ProfileActivity.class);
		
		PendingIntent clikintent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
		
		String text = "Vous etes connecte a Internet";
		
		notifWifi.setLatestEventInfo(getBaseContext(), "Wifi Connector :",text, clikintent);
		
		nm.notify(R.string.tag_notif, notifWifi);
	}
	
private boolean isProfilExist () {
		
		String profileDirPath = getFilesDir() + "/" + EnvWifi.PROFILE_DIR;
		File profileDir = new File(profileDirPath);
		
		if ( profileDir.isDirectory() ) {

			String [] profiles = profileDir.list();
			if (profiles != null) {
				// FOR EACH PROFILE
			    for (int i=0; i<profiles.length; i++) { 
			        String filename = profiles[i];
			        
			        if(filename.equals(mWifiInfo.getSSID()+".xml")){
			        	return true;
			        }
			    }
			}
			
		}
		return false;
		
	}

}
