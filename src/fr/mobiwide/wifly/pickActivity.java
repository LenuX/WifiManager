package fr.mobiwide.wifly;


import object.Wifi;
import parser.XmlParser;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class pickActivity extends ListActivity{

	
	private String[] mStrings = {
            "FreeWifi", "Univmed", "Le Trouviere", "Neufwifi", "SFR Wifi Public",
            	};

	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//getFilesDir()
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
	     
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mStrings);
	       
	    setListAdapter(adapter);
	}
	
	
}
