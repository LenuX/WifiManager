package net.mobiwide.html.parser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Segment;
import net.htmlparser.jericho.Source;
import net.mobiwide.html.parser.object.Form;
import net.mobiwide.html.parser.object.Input;


public class FormParser {
	
	public static ArrayList<Input> inputs;
	public static Form form;
	
	// FONCTIONS {  //
	
	public static Form parseForm(String sourceUrlString){     // Construction de l'objet Form
		
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
		
		inputs = new ArrayList<Input>(50);
		form=displaySegments(source.getAllElements(HTMLElementName.FORM));
		
		System.out.println("*********************AFFICHAGE DU FORM*********************\n");
		System.out.print(form);
		
		return form;
	}
	                                                                                                                 
	public static Form displaySegments(List<? extends Segment> segments) {
			
		for (Element element : segments.get(0).getAllElements()) {
			
			if (isUsefull(element) != null){
				inputs.add(isUsefull(element));		
			}
			
		}
		form.setInputList(inputs);
		return form;
	}

	public static Input isUsefull(Element element) {
		
		if(element.getName() == "form"){
			form = new Form(element.getAttributeValue("method"),element.getAttributeValue("action"));
			return null;
		}
		
		else if (element.getName() == "input") {
				
				if(element.toString().contains("checked")){
					Input SI = new Input(element.getAttributeValue("name"),"checked", element.getAttributeValue("type"));				
					return SI;
				}
			
				Input SI = new Input(element.getAttributeValue("name"),element.getAttributeValue("value"), element.getAttributeValue("type"));				
				return SI;
			
		}
		
		else if (element.getName().equals("select")) {
			
			System.out.println("222");
			Input SI = new Input(element.getAttributeValue("name"),element.getAttributeValue("value"), "select", null);
			ArrayList<String> options = new ArrayList<String>();
	

				for (Element element1 : element.getChildElements()) {

					String value = element1.getAttributeValue("value");
					options.add(value);
					if (element1.toString().contains("SELECTED"))
						 SI.setValue(value);
				}
			SI.setOptionsList(options);
			return SI;
		}	
		
		else { 
			return null;
		}
	}
	
}
