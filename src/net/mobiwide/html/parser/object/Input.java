package net.mobiwide.html.parser.object;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class Input implements Parcelable {

	private String mName; 
	private String mValue;
	private String mType;
	private ArrayList<String> mOptionsList;
	

	public Input(String name, String value, String type) {
		mName = name;
		mType = type;
		mValue = value;
		
		mOptionsList=new ArrayList<String>();
		
		if (mType==null){ mType="text"; }
		if (mType.equals("checkbox")&& mValue==null){ mValue="unchecked"; }
		
	}
	public Input(String name, String value, String type,ArrayList<String> optionsList) {
		mName = name;
		mValue = value;
		mType = type;
		mOptionsList = optionsList;

		if (mType==null){ mType="text"; }
		if (mType.equals("checkbox")&& mValue==null){ mValue="unchecked"; }

	}
	
	public Input(Parcel in)
	{
		this.mName = in.readString();
		this.mValue = in.readString();
		this.mType = in.readString();
		
		int lList = in.readInt();
		
		mOptionsList=new ArrayList<String>();
		
		for (int i = 0; i < lList; i++) {
			String string =  in.readString();
			mOptionsList.add(string);
		}		
	}
	
	
	@Override
	public String toString() {
		String s = "Input : Type [" + mType + "] Name [" + mName + "] , Value ["
				+ mValue + "] , options: {";
		
		if(mOptionsList!=null){
			for (String string : mOptionsList) {
				s += "[" + string + "]";
			}
		}
		
		s += "}\n";
		return s;

	}
	
	
	
	
	public String getName() {
		return mName;
	}
	public void setName(String name) {
		mName = name;
	}
	public String getValue() {
		return mValue;
	}
	public void setValue(String value) {
		mValue = value;
	}
	public String getType() {
		return mType;
	}
	public void setType(String type) {
		mType = type;
	}
	public ArrayList<String> getOptionsList() {
		return mOptionsList;
	}
	public void setOptionsList(ArrayList<String> optionsList) {
		mOptionsList = optionsList;
	}
	
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(mName);
		dest.writeString(mValue);
		dest.writeString(mType);
		dest.writeInt(mOptionsList.size());
		
		for (int j = 0; j < mOptionsList.size(); j++) {
			dest.writeString(mOptionsList.get(j));
		}
		
	}
	@SuppressWarnings({ "rawtypes" })
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

		public Input createFromParcel(Parcel in)
		{
			return new Input(in);
		}

		public Input[] newArray(int size)
		{
			return new Input[size];
		}

	};
	

}
