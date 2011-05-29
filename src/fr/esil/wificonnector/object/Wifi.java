package fr.esil.wificonnector.object;

import net.esil.wificonnector.object.Form;
import android.os.Parcel;
import android.os.Parcelable;


public class Wifi implements Parcelable {

	private String mBSSID;
	private String mSSID;
	private Form mForm;

	public Wifi(String ESSID, String SSID) {
		mBSSID = ESSID;
		mSSID = SSID;
	}

	public Wifi(Parcel in)
	{
		this.mBSSID = in.readString();
		this.mSSID = in.readString();
		this.mForm = in.readParcelable(getClass().getClassLoader());
	}

	public CharSequence getDisplayName() {
		return mSSID;
	}

	public String getFileName() {
		return mSSID + ".xml";
	}

	@Override
	public String toString() {
		String s = "WIFI [ BSSID:[" + mBSSID + "] SSID:[" + mSSID + "] \n ";
		if (mForm != null) {
			s += " FORM:[" + mForm + "]";
		}

		return s;
	}

	public String getBSSID() {
		return mBSSID;
	}

	public void setBSSID(String BSSID) {
		mBSSID = BSSID;
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

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(mBSSID);
		dest.writeString(mSSID);
		dest.writeParcelable(mForm, PARCELABLE_WRITE_RETURN_VALUE);
	}

	@SuppressWarnings({ "rawtypes" })
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

		public Wifi createFromParcel(Parcel in)
		{
			return new Wifi(in);
		}

		public Wifi[] newArray(int size)
		{
			return new Wifi[size];
		}

	};

}
