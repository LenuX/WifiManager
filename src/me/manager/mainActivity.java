package me.manager;

import java.io.InputStream;
import java.util.ArrayList;

import object.Input;
import parser.Parser;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class mainActivity extends Activity {
    /** Called when the activity is first created. */
	
	private static final String LOG_TAG = "mainActivity";
	private TableLayout tableLayout;
	private TableRow tr1;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        tableLayout = (TableLayout) findViewById(R.id.mytablelayout);
        tr1=new TableRow(this);
        tr1.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
        tableLayout.addView(tr1);
        
        
        InputStream sourceUrlString = null;
        //String sourceUrlString = "http://www.google.fr";
		
		try {
			
			sourceUrlString=getAssets().open("data/freewifi.htm");
			Parser myparser = new Parser(sourceUrlString);
			
			ArrayList<Input> inputsAndroid = new ArrayList<Input>();
			inputsAndroid = myparser.getList();
			
			Log.i(LOG_TAG, "LISTE  :" + inputsAndroid ); 
			
			if(isGoogle(inputsAndroid)){
				Log.i(LOG_TAG, "Vous etes déja connecté");
			}
			else{
				Log.i(LOG_TAG, "Vous n'etes pas connecté, création du formulaire ->");
				makeForm(inputsAndroid);
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
    }	
	
    public boolean isGoogle(ArrayList<Input> inputsAndroid){
		
		for(int i=0; i < inputsAndroid.size();i++){
			
			String value = inputsAndroid.get(i).getValue();
			
			if( value != null && value.equalsIgnoreCase("J'ai de la chance") ){
				return true;
			}
		
		}
		return false;
    }
    
    public void makeForm(ArrayList<Input> inputsAndroid){
    	
    	
    	for(int i=0; i < inputsAndroid.size();i++){
    		
    	 	Log.i(LOG_TAG, "Construction d'un :" + inputsAndroid.get(i).getType());
    	 	
    		if(inputsAndroid.get(i).getType().equals("text")){
    						
    			TextView label = new TextView(this);
    			label.setText(inputsAndroid.get(i).getName());
    			tr1.addView(label);
    			
    			EditText text=new EditText(this);
    			tr1.addView(text);
    		}
    		else if(inputsAndroid.get(i).getType().equals("password")){
    			
    			TextView label = new TextView(this);
    			label.setText(inputsAndroid.get(i).getName());
    			tr1.addView(label);
    			
    			EditText pass=new EditText(this);
    			tr1.addView(pass);
    		}
    		else if(inputsAndroid.get(i).getType().equals("submit")){
    			
    			Button submit = new Button(this);
    			submit.setText(inputsAndroid.get(i).getValue());
    			tr1.addView(submit);

    		}
    		
    	}
    	
    	
    }
    
}