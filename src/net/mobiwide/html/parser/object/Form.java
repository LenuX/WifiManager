package net.mobiwide.html.parser.object;


import java.util.LinkedList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Form implements Parcelable{

		private String mMethod;
		private String mAction;
		private List<Input> mInputList;
	
		public Form(String method, String action){
			mMethod=method;
			mAction=action;
			mInputList=new LinkedList<Input>();
		}
		
		
		
		public String getRequest(){
			String request = null;
			
			return request;
		}

		
		@Override
		public String toString() {
			String s="Method:["+mMethod+"] Action:["+mAction+"] \n ListInput:[\n";
			for(Input input: mInputList){
				s+=input;
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

		public List<Input> getInputList() {
			return mInputList;
		}

		public void setInputList(List<Input> inputList) {
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
			
		}

	}
