package fr.mobiwide.wifly.object;

import android.os.Parcel;
import android.os.Parcelable;
import net.mobiwide.html.parser.object.Form;

public class Wifi implements Parcelable {

	private String mESSID;
	private String mSSID;
	private Form mForm;

	public Wifi(String ESSID, String SSID) {
		mESSID = ESSID;
		mSSID = SSID;
	}

	public Wifi(Parcel in)
	{
		this.mESSID = in.readString();
		this.mSSID = in.readString();
		this.mForm = in.readParcelable(getClass().getClassLoader());
	}

	public CharSequence getDisplayName() {
		return mESSID + "--" + mSSID;
	}

	public String getFileName() {
		// return mESSID + "_" + mSSID + ".xml";
		return mESSID + ".xml";
	}

	@Override
	public String toString() {
		String s = "WIFI [ essid:[" + mESSID + "] ssid:[" + mSSID + "] \n ";
		if (mForm != null) {
			s += " FORM:[" + mForm + "]";
		}

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

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(mESSID);
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
