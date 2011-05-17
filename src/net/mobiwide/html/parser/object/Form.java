package net.mobiwide.html.parser.object;


import android.os.Parcel;
import android.os.Parcelable;

public class Form implements Parcelable{

		private String mMethod;
		private String mAction;
		private Parcelable[] mInputList;
	
		public Form(String method, String action){
			mMethod=method;
			mAction=action;
			mInputList=null;
		}
		
		public Form(Parcel in)
		{
			this.mMethod = in.readString();
			this.mAction = in.readString();
			this.mInputList = in.readParcelableArray(getClass().getClassLoader());
			
		
		}

		public String getRequest(){
			String request = null;
			
			return request;
		}

		
		@Override
		public String toString() {
			String s="Method:["+mMethod+"] Action:["+mAction+"] \n ListInput:[\n";
			if (mInputList != null) {
				for(Parcelable input: mInputList){
					s+=input;
				}
			}	
			s+="]";
		return s;
		}
		
		

		public String getMethod() {
			return mMethod;
		}

		public void setMethod(String method) {
			mMethod = method;
		}

		public String getAction() {
			return mAction;
		}

		public void setAction(String action) {
			mAction = action;
		}

		public Parcelable[] getInputList() {
			return mInputList;
		}

		public void setInputList(Parcelable[] inputList) {
			mInputList = inputList;
		}



		@Override
		public int describeContents() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			// TODO Auto-generated method stub
			dest.writeString(mMethod);
			dest.writeString(mAction);
			dest.writeParcelableArray(mInputList,PARCELABLE_WRITE_RETURN_VALUE);	
		}

		@SuppressWarnings({ "rawtypes" })
		public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

			public Form createFromParcel(Parcel in)
			{
				return new Form(in);
			}

			public Form[] newArray(int size)
			{
				return new Form[size];
			}

		};

}
