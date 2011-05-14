package fr.mobiwide.wifly;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import net.mobiwide.utils.FileUtils;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class HomeActivity extends Activity {
	
	private static final String TAG = "HomeActivity";
	
	private void loadFakeData () throws IOException {
		
		new File(getFilesDir() + "/profile").mkdir();
		
		InputStream in;
		in = getAssets().open("data/profil_free.xml");
		FileUtils.inputStreamToFile(in, getFilesDir() + "/profile/free.xml");
		
		in = getAssets().open("data/profil_aruba.xml");
		FileUtils.inputStreamToFile(in, getFilesDir() + "/profile/aruba.xml");
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		try {
			loadFakeData();
			Log.i(TAG, "Loading fake data .");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		
//		try {
//			InputStream inputStream = getAssets().open("data/profil_free.xml");
//			Wifi wifi = XmlParser.read(inputStream);
//			Log.i(TAG, wifi.toString());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
		//String dirPath = getFilesDir() + "";
	}

}
