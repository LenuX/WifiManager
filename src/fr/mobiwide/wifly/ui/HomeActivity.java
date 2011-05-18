package fr.mobiwide.wifly.ui;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import net.mobiwide.utils.FileUtils;
import fr.mobiwide.wifly.EnvWifi;
import fr.mobiwide.wifly.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HomeActivity extends Activity {
	
	private static final String TAG = "HomeActivity";
	
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
		OnClickListener ButtonEdit = new OnClickListener()
		{
			@Override
			public void onClick(View actuelView)
			{
				// On met en place le passage entre les deux activités sur ce Listener
				Intent intent = new Intent(HomeActivity.this, EditProfileActivity.class);
				startActivity(intent);
			}
		};
		
		Button edit = (Button) findViewById(R.id.buttonEdit);
		edit.setOnClickListener(ButtonEdit);
		
		
		
		//On crée le Listener sur le Bouton
		OnClickListener ButtonLoad = new OnClickListener()
		{
			@Override
			public void onClick(View actuelView)
			{	
				String profileDirPath = getFilesDir() + "/" + EnvWifi.PROFILE_DIR;
				File profileDir = new File(profileDirPath);
				
				profileDir.mkdir();
				Log.i(TAG, "Loading fake data .");
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
	}

}
