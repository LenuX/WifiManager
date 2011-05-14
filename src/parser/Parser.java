package parser;

import net.htmlparser.jericho.*;
import java.util.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import object.*;


public class Parser {
	
	public static ArrayList<Input> inputs;

	public static void main(String[] args) throws Exception {
		
		Parser myparser= new Parser("data/freewifi.html");
		
	}
	
	// CONSTRUCTORS { //
	public Parser(String sourceUrlString) throws MalformedURLException, IOException{
		
		if (sourceUrlString.indexOf(':') == -1)	sourceUrlString = "file:" + sourceUrlString;
		Source source=null;
		try{
		source = new Source(new URL(sourceUrlString));
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		construct(source);
	}
	
	public Parser(InputStream sourceUrlString){
		Source source = null;
		try {
			source = new Source(sourceUrlString);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		construct(source);
	}
	
	//  } CONSTRUCTORS //	
	 
	// FONCTIONS {  //
	
	public void construct(Source source){     // Construction et affichage de la liste pour tt les constructeurs
		
		inputs = new ArrayList<Input>(50);
		displaySegments(source.getAllElements(HTMLElementName.FORM));
		System.out.println("*********************AFFICHAGE DE LA LISTE*********************\n");
		System.out.print(inputs);
	}
	
	public static void displaySegments(List<? extends Segment> segments) {

		for (Element element : segments.get(0).getAllElements()) {
			
			if (isUsefull(element) != null){
				inputs.add(isUsefull(element));		
			}
			
		}
	}

	public static Input isUsefull(Element element) {
		
		if (element.getName() == "input") {
			
			if (element.getAttributeValue("type") == null ) { 
				InputText IT = new InputText(element.getAttributeValue("name"),	element.getAttributeValue("value"));
				return IT;
			} 
			else if (element.getAttributeValue("type").equals("text")) { 
				InputText IT = new InputText(element.getAttributeValue("name"),	element.getAttributeValue("value"));
				return IT;
			} 
			else if (element.getAttributeValue("type").equals("password")) {
				InputPassword IP = new InputPassword(element.getAttributeValue("name"),element.getAttributeValue("value"));
				return IP;
			}
			else if (element.getAttributeValue("type").equals("hidden")) {
				InputHidden IH = new InputHidden(element.getAttributeValue("name"),element.getAttributeValue("value"));
				return IH;
			}
			else if(element.getAttributeValue("type").equals("checkbox")){
				if(element.toString().contains("checked")){
					InputCheckbox IC = new InputCheckbox(element.getAttributeValue("name"),"checked");
					return IC;
				}
				else{
					InputCheckbox IC = new InputCheckbox(element.getAttributeValue("name"),"unchecked");
					return IC;
				}
					
			}
			else if (element.getAttributeValue("type").equals("submit")) {
				InputSubmit IS = new InputSubmit(element.getAttributeValue("name"),element.getAttributeValue("value"));
				return IS;
			}	
			else{
				InputText IT = new InputText(element.getAttributeValue("name"),	element.getAttributeValue("value"));
				//System.out.println("(default creation)"+IT);
				return IT;
			}
		}
		else if (element.getName().equals("select")) {
			ArrayList<String> options = new ArrayList<String>();
			int indexOfdefvalue = 0;

			for (Element element1 : element.getChildElements()) {

				String value = element1.getAttributeValue("value");
				options.add(value);
				if (element1.toString().contains("SELECTED"))
					indexOfdefvalue = options.indexOf(value);
			}
			InputMenu IM = new InputMenu(element.getName(),
			options.get(indexOfdefvalue), options, indexOfdefvalue);
			return IM;
		}
//		else if (element.getName().equals("form")) {
//			Form form = new Form(name, value, method, action)
//		}
		return null;
	}
	
		
	public ArrayList<Input> getList(){
		return inputs;
	}

}
