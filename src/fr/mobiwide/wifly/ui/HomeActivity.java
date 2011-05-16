package fr.mobiwide.wifly.ui;

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
		
		Button list = (Button) findViewById(R.id.buttonList);
		list.setOnClickListener(ButtonList);
		
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
		
		
//		Intent intent = new Intent(this, EditProfileActivity.class);
//    	startActivity(intent);
	}

}
