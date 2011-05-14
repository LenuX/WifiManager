package fr.mobiwide.wifly;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class HomeActivity extends Activity {
	
	private static final String TAG = "HomeActivity";

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
		
		Intent intent = new Intent(this, ProfileActivity.class);
    	startActivity(intent);
	}

}
