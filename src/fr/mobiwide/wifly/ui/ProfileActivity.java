package fr.mobiwide.wifly.ui;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import net.mobiwide.utils.FileUtils;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import fr.mobiwide.wifly.EnvWifi;
import fr.mobiwide.wifly.object.Wifi;
import fr.mobiwide.wifly.parser.XmlParser;

public class ProfileActivity extends ListActivity implements OnItemClickListener {

	private static final String TAG = "ProfileActivity";
	
	private List<Wifi> mListWifi = new LinkedList<Wifi>();
	private Wifi mWifi;
	private MyListAdapter mAdapter;
	
	static final int DG_ACTION = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		loadListWifi();
		
		mAdapter = new MyListAdapter();
		
		setListAdapter(mAdapter);
		ListView listview = getListView();
		listview.setOnItemClickListener(this);
	}
	
	
	/**
	 * Fonction onCreateDialog
	 * 
	 * Cette fonction permet de gerer les actions et le comportement lors d'un clik 
	 * sur un des items de notre liste.
	 *
	 */
	
	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		
		switch (id) {
		case DG_ACTION:
			final CharSequence[] items = {"Remove", "Edit","Affiche"};
			dialog = new AlertDialog.Builder(ProfileActivity.this)
			.setTitle("Action")
			.setItems(items, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int item) {
					
					if (item == 0) {
						String filePath = getFilesDir() + "/" + EnvWifi.PROFILE_DIR + "/" + mWifi.getFileName();
						File xmlFile = new File(filePath);
						xmlFile.delete();
						mListWifi.remove(mWifi);
						mAdapter.notifyDataSetChanged();
					}
					else if (item == 1) {

						Intent intent = new Intent().setClass(ProfileActivity.this, EditProfileActivity.class);
						intent.putExtra("mWifi", mWifi);
						startActivity(intent);
					
					}
					else if (item == 2) {
						Toast.makeText(getApplicationContext(), mWifi.toString(), Toast.LENGTH_LONG).show();
					}

				}
			}).create();
			break;
		default:
			dialog = super.onCreateDialog(id);
			break;
		}
		
		return dialog;
	}
	
	/**
	 * Fonction loadListWifi
	 * 
	 * La fonction va recupere la liste de tout les profils existant dans le dossier profile
	 * et stoker leur noms dans un tableau de String qui nous permettra de presenter la liste.
	 * 
	 * Si le dossier profile n'existe pas, on le cree et le rempli avec deux "faux" profil
	 * utilises pour les tests. 
	 * 
	 * @param void
	 * @return void
	 */
	
	private void loadListWifi () {
		
		String profileDirPath = getFilesDir() + "/" + EnvWifi.PROFILE_DIR;
		File profileDir = new File(profileDirPath);
		
		if ( profileDir.isDirectory() ) {
			// Initialize
			mListWifi = new LinkedList<Wifi>();
			// retrieve file list
			String [] profiles = profileDir.list();
			if (profiles != null) {
				// FOR EACH PROFILE
			    for (int i=0; i<profiles.length; i++) { 
			        String filename = profiles[i];
			        Log.i(TAG, "Found file :" + filename + "--" + i + "/" + profiles.length);
			        File xmlFile = new File(profileDirPath + "/" + filename);
			        Wifi wifi = XmlParser.read(xmlFile);
			        mListWifi.add(wifi);
			    }
			}
			
		}
		
	}
	
	// Listeners
	
	@Override
	public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
		mWifi = mListWifi.get(position);
		showDialog(DG_ACTION);
	}
	
	/**
	 * Class to display ListView of Wifi .
	 *
	 */
	private class MyListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mListWifi.size();
		}

		@Override
		public Wifi getItem(int position) {
			return mListWifi.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView tv;
			if (convertView == null) {
				tv = (TextView) LayoutInflater.from(ProfileActivity.this)
					.inflate(android.R.layout.simple_list_item_1, parent, false);
			} else {
				tv = (TextView) convertView;
			}
			tv.setText(getItem(position).getDisplayName());
			Log.i(TAG, getItem(position).toString());
			return tv;
		}
		
	}



}
