package fr.mobiwide.wifly.ui;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import fr.mobiwide.wifly.R;
import fr.mobiwide.wifly.Wifly;

public class EditProfileActivity extends Activity {


	private static final String TAG = "EditProfileActivity";
	LinearLayout linear;
	LayoutInflater inflater;

	//private Wifi mWifi;
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
			
			RadioGroup rg = new RadioGroup(this);
			
			for(int i=0; i<input.getOptionsList().size();i++){
				
				
				RadioButton rb = new RadioButton(this);
				rb.setText(input.getOptionsList().get(i));
			
				if(input.getValue().equals(input.getOptionsList().get(i))){
					
					rb.setChecked(true);
				}
				rg.addView(rb);
			}
			return rg;
		}
		Button def = new Button(this);
		def.setText(input.getType());
		return def;
	}
}
