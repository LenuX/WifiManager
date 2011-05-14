package object;


import java.util.List;

public class Form {

		private String mMethod;
		private String mAction;
		private List<SuperInput> mInputList;
	
		public Form(String method, String action){
			mMethod=method;
			mAction=action;
			
		}
		
		
		@Override
		public String toString() {
			String s="Method:["+mMethod+"] Action:["+mAction+"] \n ListInput:[\n";
			for(SuperInput input: mInputList){
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

		public List<SuperInput> getInputList() {
			return mInputList;
		}

		public void setInputList(List<SuperInput> inputList) {
			mInputList = inputList;
		}


	}
