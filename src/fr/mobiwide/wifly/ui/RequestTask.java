package fr.mobiwide.wifly.ui;


import java.io.File;

import me.rabgs.http.Browser;
import net.mobiwide.html.parser.FormParser;
import net.mobiwide.html.parser.object.Form;
import net.mobiwide.utils.FileUtils;

import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.util.Log;
import fr.mobiwide.wifly.EnvWifi;
import fr.mobiwide.wifly.object.Wifi;

public class RequestTask extends AsyncTask<Void, Void, Boolean>{

	private static final String TAG = "RequestTask";
	private Wifi mWifi;
	private Form mForm;
	private WifiManager mainWifi;
	private WifiInfo mWifiInfo;
	private Context mContext;
	private Boolean taskCompleted=false;
	
	@Override
	protected Boolean doInBackground(Void... params) {

		try{  
			Log.i(TAG, "travail en background commencé");
			
			// On cree un Wifimanager et on recupere les infos de la connexion en cours
			mainWifi = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
			mWifiInfo=mainWifi.getConnectionInfo();
			
			// On ecrit les inofs dans un nouvelle objet Wifi
			mWifi = new Wifi(mWifiInfo.getBSSID(), mWifiInfo.getSSID());
			
			String getHttp="";
			String url ="http://www.google.com";
			DefaultHttpClient mHttpClient = Browser.mDefaultHttpClient("Mozilla/5.0 (X11; U; Linux x86_64; fr; rv:1.9.2.17) Gecko/20110422 Ubuntu/10.04 (lucid) Firefox/3.6.17");

			if(mWifiInfo.getSSID().equals("UNIVMED")){
				
				
				Browser.httpGetResponse("https://securelogin.arubanetworks.com/upload/custom/UNIVMED_CP/index.htm", true, mHttpClient);
				//Browser.httpGetResponse("http://www.google.com", true, mHttpClient);
				url="https://securelogin.arubanetworks.com/upload/custom/UNIVMED_CP/gauche.htm";
				Log.i(TAG, "ARUBA with redirect :");

				
			}
			
			// On emet une requete get pour recuperer la page d'authentification
			getHttp  = Browser.httpGetResponse(url, true, mHttpClient);
			Log.i(TAG, "URL Parser ::"+getHttp);
			
			// On sauvegarde cette page dans un fichier html dans le dossier "html"			
			
			String profileDirPath = mContext.getFilesDir() + "/" + EnvWifi.HTMLFILE_DIR;
			File profileDir = new File(profileDirPath);
			profileDir.mkdir();
			
			String htmlPath =mContext.getFilesDir()+"/"+EnvWifi.HTMLFILE_DIR+"/"+mWifiInfo.getSSID()+".html";
			FileUtils.writeStringToFile(getHttp, htmlPath);
			
			// On récupere le formulaire de la page html stokée dans un objet Form, avec le parser Jéricho
			mForm=FormParser.parseForm(htmlPath);
			
			// On attache le form a notre wifi
			mWifi.setForm(mForm);
			Log.i(TAG, "FINAL WIFI BUILD"+mWifi.toString());
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} 
		taskCompleted=true;
		return true;
	}
	
	@Override
	protected void onPostExecute(Boolean result) {

	}		
	
	public RequestTask(Context context){
		mContext=context;
		
	}
	
	public Boolean getTaskCompleted(){
		return taskCompleted;
	}

	public Wifi getWifi(){
		return mWifi;
	}
}
