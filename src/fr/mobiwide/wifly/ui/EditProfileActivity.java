package fr.mobiwide.wifly.ui;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import fr.mobiwide.wifly.R;
import fr.mobiwide.wifly.Wifly;
import fr.mobiwide.wifly.object.Wifi;

import net.mobiwide.html.parser.FormParser;
import net.mobiwide.html.parser.object.Form;
import net.mobiwide.html.parser.object.Input;
import net.mobiwide.utils.FileUtils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

public class EditProfileActivity extends Activity {


	private static final String TAG = "EditProfileActivity";
	LinearLayout linear;
	LayoutInflater inflater;

	private Wifi mWifi;
	private Form mForm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		linear = (LinearLayout) findViewById(R.id.layoutlist); 
		inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		try {
			readFromHtml();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//mForm = mWifi.getForm();
		List<Input> inputList = mForm.getInputList();
		
		for (Input input : inputList) {
			View view = inputToView(input);
			linear.addView(view);
		}
		
		
	}


	public void readFromHtml () throws IOException {
		//Form form;
		InputStream in = null;
		in = getAssets().open("data/aruba.htm");
		File dir = new File (getFilesDir() + "/" + Wifly.HTMLFILE_DIR);
		dir.mkdir();
		String pathToHtml = getFilesDir() + "/" + Wifly.HTMLFILE_DIR + "/aruba.html" ; 
		FileUtils.inputStreamToFile(in, pathToHtml);
		mForm = FormParser.parseFormFromFile(pathToHtml);
		Log.i(TAG, mForm.toString());
	}
	
	public View inputToView (Input input) {
		EditText editText = new EditText(this);
		return editText;
	}

}
