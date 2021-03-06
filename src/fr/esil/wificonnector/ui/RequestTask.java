package fr.esil.wificonnector.ui;


import java.io.File;

import net.esil.wificonnector.html.parser.FormParser;
import net.esil.wificonnector.object.Form;
import net.esil.wificonnector.utils.FileUtils;

import org.apache.http.impl.client.DefaultHttpClient;

import com.rabgs.http.browser.Browser;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import fr.esil.wificonnector.EnvWifi;
import fr.esil.wificonnector.object.Wifi;

public class RequestTask extends AsyncTask<Void, Void, Boolean>{

	private static final String TAG = "RequestTask";
	private Wifi mWifi;
	private Form mForm;
	private WifiManager mainWifi;
	private WifiInfo mWifiInfo;
	private Context mContext;
	private Boolean taskCompleted=false;
	private ProgressDialog mProgDialog;
	
	@Override
	protected void onPreExecute() {

		mProgDialog = new ProgressDialog(mContext);
		mProgDialog.setTitle("Creation du Formulaire");
		mProgDialog.setCancelable(false);
		mProgDialog.show();
	}
	
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
			Browser mBrowser = new Browser(true);
			
			if(mWifiInfo.getSSID().equals("UNIVMED")){
			
				mBrowser.doHttpGet("https://securelogin.arubanetworks.com/upload/custom/UNIVMED_CP/index.htm");
				//Browser.httpGetResponse("http://www.google.com", true, mHttpClient);
				url="https://securelogin.arubanetworks.com/upload/custom/UNIVMED_CP/gauche.htm";
				Log.i(TAG, "ARUBA with redirect :");	
			}
			//if(mWifiInfo.getSSID().equals("LeTrouviere")){
			if(mWifiInfo.getSSID().equals("RABGS")){	
				url="http://192.168.0.6/WifiManager/aruba.html";
				mBrowser.doHttpGet(url);
				//Browser.httpGetResponse("http://www.google.com", true, mHttpClient);
				
			}
			
			// On emet une requete get pour recuperer la page d'authentification
			getHttp  = mBrowser.doHttpGet(url);
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
		mProgDialog.cancel();
		if (result) {
			//On transmet l'ojet Wifi completement rempli a l'activité suivante en la démarant:
			Intent intent = new Intent(mContext, EditProfileActivity.class);
			intent.putExtra("mWifi", mWifi);
			intent.putExtra("create", true);
			mContext.startActivity(intent);
		}
		else {
			Log.e(TAG, "CRAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAP");
			Toast.makeText(mContext, "CRAP", Toast.LENGTH_SHORT);
		}
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
