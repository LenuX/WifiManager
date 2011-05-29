package fr.esil.wificonnector.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import net.esil.wificonnector.object.Form;
import net.esil.wificonnector.object.Input;

import org.apache.http.NameValuePair;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.rabgs.http.browser.Browser;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import fr.esil.wificonnector.EnvWifi;
import fr.esil.wificonnector.R;
import fr.esil.wificonnector.object.Wifi;
import fr.esil.wificonnector.parser.XmlParser;


public class EditProfileActivity extends Activity { 


	private static final String TAG = "EditProfileActivity";	// Cette activité récupere un Wifi depuis l'activitr
	LinearLayout linear;										// ProfileAcivity et le stock dans mWifi, on va s'en 
	LayoutInflater inflater;									// servir pour créer le formulaire Android permettant
	private Wifi mWifi;											// a l'utilisateur de le modifier.
	private Form mForm;
	private Boolean mCreate=false;
	private List<Input> inputList;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
        Bundle extras = getIntent().getExtras();		// Bundle permettant d'echanger des objets entre activités
        mWifi = extras.getParcelable("mWifi");		// On recupere l'objet choisi dans ProfileActivity 
        
        mCreate = extras.getBoolean("create",false);
        
        Log.i(TAG, mWifi.toString());
        
		linear = (LinearLayout) findViewById(R.id.layoutlist); 							// Layout declare en xml
		inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		try {
			//readFromHtml();
			mForm = mWifi.getForm();				// On recupere le Form
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		inputList = mForm.getInputList();
		
		TextView profileName = new TextView(this);				/// CREATION DU TITRE
		profileName.setText(mWifi.getSSID());profileName.setBackgroundColor(Color.GRAY);
		profileName.setTextColor(Color.BLACK);profileName.setHeight(25);
		linear.addView(profileName);
		
		for (Input input : inputList) {
			TextView label = inputLabel(input);			// La fonction crée un label en fct du type d'input
			linear.addView(label);						// On ajoute les labels a notre vue
			View view = inputToView(input);				
			linear.addView(view);						// On ajoute l'input apres l'avoir convertit en View
		}
		
		
	}


//	public void readFromHtml () throws IOException {
//		InputStream in = null;
//		in = getAssets().open("data/aruba.htm");
//		File dir = new File (getFilesDir() + "/" + Wifly.HTMLFILE_DIR);
//		dir.mkdir();
//		String pathToHtml = getFilesDir() + "/" + Wifly.HTMLFILE_DIR + "/aruba.html" ; 
//		FileUtils.inputStreamToFile(in, pathToHtml);
//		mForm = FormParser.parseForm(pathToHtml);
//		Log.i(TAG, mForm.toString());
//	}
	
	/**
	 * Fonction inputToView
	 * 
	 * La fonction va chercher de quel type est l'input pour ensuite le
	 * convertir en un champ exploitable pour un formulaire android
	 *
	 *
	 * @param Input
	 * @return View
	 */

	
	
	public View inputToView (final Input input) {
		
		if(input.getType().equals("text")){		// L'Input selectionne est de type text
			final EditText text = new EditText(this);		
			text.setHint("Entrer votre texte :)");
			if(input.getValue()!=null) text.setText(input.getValue());

	
			text.addTextChangedListener(new TextWatcher() { // Watcher pour effectuer des actions si le text est modifie
				
				public void onTextChanged(CharSequence s, int start, int before, int count) 
				{ 	}
				
				public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {		}		// Si l'user veut écrire, on vide la case
				
				@Override
				public void afterTextChanged(Editable s) {
					String mValue =(String)text.getText().toString();		// Si le contenu du Champ de text  est
					input.setValue(mValue);							// modifie on recupere la valeur dans Input.Value
					
				}
			});
			
			return text;
		}
		else if(input.getType().equals("password")){		// L'input est de type password

			final TextView password = new EditText(this);
			password.setHint("password");
			if(input.getValue()!=null) password.setText(input.getValue());
			
			password.addTextChangedListener(new TextWatcher() { // Watcher pour effectuer des actions si le text est modifie
				
				public void onTextChanged(CharSequence s, int start, int before, int count) 
				{ 	}
				
				public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {		}		// Si l'user veut écrire, on vide la case
				
				@Override
				public void afterTextChanged(Editable s) {
					String mValue =(String)password.getText().toString();		// Si le contenu du Champ de text  est
					input.setValue(mValue);							// modifie on recupere la valeur dans Input.Value
					
				}
			});
			return password;
		}
		else if(input.getType().equals("submit")){ // L'input est de type submit
			Button submit = new Button(this);
			if(input.getValue()==null){
				submit.setText("Submit");}			// On met du texte sur le bouton (le nom de l'input ou
			else{										// submit si le nom est null
				submit.setText(input.getValue());}
			
			submit.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View actuelView) // On defini l'action sur clik du bouton Submit 
				{	
					
					// On ecrit le profil
					File profil = new File( getFilesDir()+"/"+ EnvWifi.PROFILE_DIR +"/"+mWifi.getFileName());
					profil.delete();
					XmlParser.write(mWifi,profil);
					Log.i(TAG, getFilesDir()+"/"+ EnvWifi.PROFILE_DIR +"/"+mWifi.getFileName());
					
					if(mCreate){
							
							Log.i(TAG, "POST");
					//On emet la requete POST pour s'authentifier
					Browser mBrowser = new Browser(true);
					
					List<NameValuePair> postData = new LinkedList<NameValuePair>();
					
					for (Input input : mForm.getInputList()) {
						// beware value sent for checkbox
						BasicNameValuePair data = new BasicNameValuePair(input.getName(), input.getValue());
						postData.add(data);
						System.out.println(data);
					}
					
					if(mWifi.getSSID().equals("UNIVMED")){
						mBrowser.doHttpPost("https://securelogin.arubanetworks.com" +mForm.getAction(), postData);
						Log.i(TAG, "https://securelogin.arubanetworks.com" +mForm.getAction());		
						}

					else{
						mBrowser.doHttpPost(mForm.getAction(), postData);
						Log.i(TAG, mForm.getAction());
					}
					wifiNotify2(mWifi.getSSID()); // on affiche une notification
					
					Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
					startActivity(intent); // On retourne a la liste
	
				}
				}
			});
			return submit;
		}
		else if(input.getType().equals("checkbox")){		// L'input est un submit
			CheckBox checkbox = new CheckBox(this);
			
			checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() { // Listener pour observer
																				// les changements
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if(isChecked){
						input.setValue("checked");		// On met a jour la valeur de la checkbox
					}else{								// si elle est changé pas l'utilisateur
						input.setValue("unchecked");

					}
					
				}
			});
			
			if(input.getValue().equals("checked")){
				checkbox.setChecked(true);				// Initialisation des checkbox si elles 
				return checkbox;				// etaient deja coche sur le site
			}
			else{
				return checkbox;
			}		
		}
		else if(input.getType().equals("menu")){  // L'input est de type select ou menu
			
			
			Spinner spin = new Spinner(this);								// On instancie un spinner ( liste deroulante )
		    final ArrayList<String> mOptionsList = input.getOptionsList();	
		    
		    
//		    for(int i=0; i<input.getOptionsList().size();i++){
//				mOptionsList.add(input.getOptionsList().get(i));	// On recupere la liste des options du menu en local
//	
//		    }
		   
			ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,			// On cree un adapter pour le spinner et
		              android.R.layout.simple_spinner_item, mOptionsList);		// et on lui donne la liste des options a
		    																	// afficher				
		    
		    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Affichage vers le bas
		    spin.setAdapter(spinnerArrayAdapter);
		    spin.setSelection(mOptionsList.indexOf(input.getValue()));
		    
		    // On cree un listener, pour effectuer une action si l'user clik sur un item du spinner
		    
		    spin.setOnItemSelectedListener(new  OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View v,
						int position, long id) {							// On maj la valeur du menu , l'ancienne valeur
					input.setValue(mOptionsList.get(position));				// est remplacé par celle selectionnée
																					
				}

				public void onNothingSelected(AdapterView<?> arg0) {

				}
			});
		    
			return spin;
		}
		
		
		Button def = new Button(this);				// Si l'Input ne correspond a aucun type pris en charge 
		def.setText("Default: "+input.getType());	// on crée par défault un bouton, on affiche son type pour 
		return def;									// voir d'ou vient l'erreur.			
	}
	
	
	
	/**
	 * Fonction inputLabel
	 * 
	 * La fonction va generer des label pour chaque input afin d'afficher
	 * le "titre" de chaque champ dans le formulaire, on prendra le champ name de l'input
	 * ou son type si celui-ci est null.
	 *
	 * @param Input
	 * @return TextView
	 */
	
	public TextView inputLabel(Input input){
		
		TextView label = new TextView(this);			
		if(input.getName()!=null){
			label.setText(input.getName());
		}
		else{
			label.setText(input.getType());
		}
		return label;
	}
	
	public void wifiNotify2(String SSID){
		
		Log.i(TAG, "****** NOTIFY2");
		
		NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		nm.cancel(R.string.app_name);
		
		Notification notifWifi = new Notification(R.drawable.iconwc,"Wifi Connector",System.currentTimeMillis());
		
		Intent intent = new Intent( getApplicationContext(), ProfileActivity.class);
		
		PendingIntent clikintent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
		
		String text = "Vous etes connecte a Internet";
		
		notifWifi.setLatestEventInfo(getBaseContext(), "Wifi Connector :",text, clikintent);
		
		nm.notify(R.string.tag_notif, notifWifi);
	}
	
}
