package fr.mobiwide.wifly.object;

import net.mobiwide.html.parser.object.Form;

public class Wifi {

	private String mESSID;
	private String mSSID;
	private Form mForm;
	
	public Wifi(String ESSID, String SSID){
		mESSID=ESSID;
		mSSID=SSID;
	}

	public CharSequence getDisplayName() {
		return mESSID + "--" + mSSID;
	}
	
	public String getFileName() {
		//return mESSID + "_" + mSSID  + ".xml";
		return mESSID + ".xml";
	}
	
	@Override
	public String toString() {
		String s="WIFI [ essid:["+mESSID+"] ssid:["+mSSID+"] \n FORM:["+mForm+"]";	
	return s;
	}
	
	
	public String getESSID() {
		return mESSID;
	}

	public void setESSID(String eSSID) {
		mESSID = eSSID;
	}

	public String getSSID() {
		return mSSID;
	}

	public void setSSID(String sSID) {
		mSSID = sSID;
	}

	public Form getForm() {
		return mForm;
	}

	public void setForm(Form form) {
		mForm = form;
	}


	
	
}
