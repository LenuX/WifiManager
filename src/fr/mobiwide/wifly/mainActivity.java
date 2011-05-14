package fr.mobiwide.wifly;

import java.util.ArrayList;
import object.Input;
import parser.Parser;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class mainActivity extends Activity {
    /** Called when the activity is first created. */
	
	private static final String LOG_TAG = "mainActivity";
	LinearLayout linear;
	LayoutInflater inflater;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        linear = (LinearLayout) findViewById(R.id.layoutlist); 
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      
        
        //InputStream sourceUrlString = null;
        String sourceUrlString = "http://www.google.fr";
		
		try {
			
			//sourceUrlString=getAssets().open("data/freewifi.htm");
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
		
		//On crée le Listener sur le Bouton
		OnClickListener ButtonList = new OnClickListener()
		{
		 @Override
		 public void onClick(View actuelView)
		 {
		 // On met en place le passage entre les deux activités sur ce Listener
		 Intent intent = new Intent(mainActivity.this,pickActivity.class);
		 startActivity(intent);
		 }
		};
		 
		//On récupere le bouton souhaité et on lui affecte le Listener
		Button bouton = (Button) findViewById(R.id.buttonList);
		bouton.setOnClickListener(ButtonList);
		
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

    	 	
    		if(inputsAndroid.get(i).getType().equals("text")){
 
    			Log.i(LOG_TAG, "Construction d'un :" + inputsAndroid.get(i).getType());
    			
     		     View text = inflater.inflate(R.layout.text, linear,false);
     		     TextView label = (TextView) text.findViewById(R.id.textLabel);
    		     linear.addView(text);
    		}
    		else if(inputsAndroid.get(i).getType().equals("password")){
    			
    			Log.i(LOG_TAG, "Construction d'un :" + inputsAndroid.get(i).getType());
    			
    			 View password = inflater.inflate(R.layout.password, linear,false);
     		     TextView label = (TextView) password.findViewById(R.id.textPassword);
    		     linear.addView(password);
    		}
    		else if(inputsAndroid.get(i).getType().equals("submit")){
    			
    			Log.i(LOG_TAG, "Construction d'un: " + inputsAndroid.get(i).getType());
    			
    			View submit = inflater.inflate(R.layout.submit, linear,false);
   		     	linear.addView(submit);
    		}
    		else {
    			Log.i(LOG_TAG, "Type non pris en charge" + inputsAndroid.get(i).getType());
			}
    	}
    	
    	
    }
    
}