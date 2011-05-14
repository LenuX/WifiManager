package object;

import java.util.ArrayList;

public class Input {

	private String mName; 
	private String mValue;
	private String mType;
	private ArrayList<String> mOptionsList;
	

	public Input(String name, String value, String type) {
		mName = name;
		mValue = value;
		mType = type;
		mOptionsList=new ArrayList<String>();
		
		if (mType==null){ mType="text"; }
		
	}
	public Input(String name, String value, String type,ArrayList<String> optionsList) {
		mName = name;
		mValue = value;
		mType = type;
		mOptionsList = optionsList;

		if (mType==null){ mType="text"; }
	}
	
	
	@Override
	public String toString() {
		String s = "Input : Type [" + mType + "] Name [" + mName + "] , DefValue ["
				+ mValue + "] , options: {";

		for (String string : mOptionsList) {
			s += "[" + string + "]";
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
	

}
