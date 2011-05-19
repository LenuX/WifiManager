package fr.mobiwide.wifly.ui;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import net.mobiwide.utils.FileUtils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import fr.mobiwide.wifly.EnvWifi;
import fr.mobiwide.wifly.R;
import fr.mobiwide.wifly.object.Wifi;

public class HomeActivity extends Activity {
	
	private static final String TAG = "HomeActivity";
	private Wifi mWifi;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
		setContentView(R.layout.home);
		
		
		
		//On crée le Listener sur le Bouton
		OnClickListener ButtonList = new OnClickListener()
		{
			@Override
			public void onClick(View actuelView)
			{
				// On met en place le passage entre les deux activités sur ce Listener
				Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
				startActivity(intent);
			}
		};
		
		Button list = (Button) findViewById(R.id.buttonList); // on creer un bouton  Lister
		list.setOnClickListener(ButtonList);		// on lui assigne le listener
		
		
		
		//On crée le Listener sur le Bouton
		OnClickListener ButtonNew = new OnClickListener()
		{
			@Override
			public void onClick(View actuelView)
			{
				// Async Task
				RequestTask task = new RequestTask(getApplicationContext());
				task.execute();
				
				//On recupere le wifi créer par la RequestTask:
				//mWifi = task.getWifi();
				//Log.i(TAG, "WIFI FINALE APRES RETOUR HOME" + mWifi.toString());
				
//				//On transmet l'ojet Wifi completement rempli a l'activité suivante en la démarant:
//				Intent intent = new Intent().setClass(HomeActivity.this, EditProfileActivity.class);
//				
//				intent.putExtra("mWifi", mWifi);
//				startActivityForResult(intent, activityID);
			}
		};
		
		Button newProfile = (Button) findViewById(R.id.buttonNew);	// Bouton Nouveau Profil
		newProfile.setOnClickListener(ButtonNew);
		
		
		
		//On crée le Listener sur le Bouton
		OnClickListener ButtonLoad = new OnClickListener()
		{
			@Override
			public void onClick(View actuelView)
			{	
				String profileDirPath = getFilesDir() + "/" + EnvWifi.PROFILE_DIR;
				File profileDir = new File(profileDirPath);
				
				profileDir.mkdir();
				Log.i(TAG, "Loading fake data -> path :"+profileDirPath);
				// Data for test 
				InputStream in;
					try {
							in = getAssets().open("data/profil_free.xml");
							FileUtils.inputStreamToFile(in, getFilesDir() + "/profile/freewifi.xml");
			
							in = getAssets().open("data/profil_aruba.xml");
							FileUtils.inputStreamToFile(in, getFilesDir() + "/profile/Univmed.xml");
					} catch (IOException e) {
						e.printStackTrace();
						}
				}
			};
			
			Button load = (Button) findViewById(R.id.buttonLoad);	// Bouton pour creer des faux profils, utiles pour
			load.setOnClickListener(ButtonLoad);					// les tests si la liste est vide .
			
			
			//On crée le Listener sur le Bouton
			OnClickListener ButtonWifi = new OnClickListener()
			{
				@Override
				public void onClick(View actuelView)
				{
					// On met en place le passage entre les deux activités sur ce Listener
					Intent intent = new Intent(HomeActivity.this, WifiTester.class);
					startActivity(intent);
				}
			};
			
			Button wifi = (Button) findViewById(R.id.buttonWifi); // on creer un bouton  Lister
			wifi.setOnClickListener(ButtonWifi);		// on lui assigne le listener
			
			
	} // onCreate
	

}
