package fr.mobiwide.wifly.ui;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import net.mobiwide.html.parser.FormParser;
import net.mobiwide.html.parser.object.Form;
import net.mobiwide.html.parser.object.Input;
import net.mobiwide.utils.FileUtils;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import fr.mobiwide.wifly.R;
import fr.mobiwide.wifly.Wifly;
import fr.mobiwide.wifly.object.Wifi;

public class EditProfileActivity extends Activity {


	private static final String TAG = "EditProfileActivity";
	LinearLayout linear;
	LayoutInflater inflater;
	Wifi mWifi;
	//private Wifi mWifi;
	private Form mForm;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
        Bundle extras = getIntent().getExtras();
        mWifi = extras.getParcelable("mWifi");
		
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
		
		TextView label = new TextView(this);
		label.setText("Creation du profil : +");
		linear.addView(label);
		
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
		mForm = FormParser.parseForm(pathToHtml);
		Log.i(TAG, mForm.toString());
	}
	
	public View inputToView (Input input) {
		
		if(input.getType().equals("text")){
			EditText text = new EditText(this);
			text.setText("Text");
			return text;
		}
		else if(input.getType().equals("password")){
			EditText password = new EditText(this);
			password.setText("Password");
			return password;
		}
		else if(input.getType().equals("submit")){
			Button submit = new Button(this);
			submit.setText("Submit");
			
			OnClickListener SubmitListener = new OnClickListener()
			{
				@Override
				public void onClick(View actuelView)
				{
					
					Toast.makeText(getApplicationContext() ,mWifi.toString(), Toast.LENGTH_LONG).show();
				}
			};
			
			submit.setOnClickListener(SubmitListener);
			return submit;
		}
		else if(input.getType().equals("checkbox")){
			CheckBox checkbox = new CheckBox(this);
			
			if(input.getValue().equals("checked")){
				checkbox.setChecked(true);
				return checkbox;
			}
			else{
				return checkbox;
			}		
		}
		else if(input.getType().equals("select")){
			
			Spinner spin = new Spinner(this);
		    ArrayList<String> mOptionsList = new ArrayList<String>();
		  
		    for(int i=0; i<input.getOptionsList().size();i++){
		    
				mOptionsList.add(input.getOptionsList().get(i));
				
		    }
		   
		    ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
		              android.R.layout.simple_spinner_item, mOptionsList);
		    
		    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		    spin.setAdapter(spinnerArrayAdapter);

			return spin;
		}
		
		
		Button def = new Button(this);
		def.setText("Default: "+input.getType());
		return def;
	}
}
