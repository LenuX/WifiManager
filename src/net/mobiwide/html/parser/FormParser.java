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
	
	/**
	 * Fonction parseForm.
	 * 
	 * Permet de construire un Form a partir d'un fichier html. On va parcourir la page html et récuperer tout
	 * les champs utiles ( <form,input,select> ) pour les stocker dans l'objet Form.
	 * 
	 * Appelle la fonction displaySegments
	 * 
	 * @param String (Chemin de la ressource html dans lequel on veut stoker le profil)
	 * @return void
	 */
	
	public static Form parseForm(String sourceUrlString){    
		
		if (sourceUrlString.indexOf(':') == -1)	sourceUrlString = "file:" + sourceUrlString;
		Source source=null;
		try{
			// On construit la source du parser a partir du chemin donné en parametre
		source = new Source(new URL(sourceUrlString));   
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		inputs = new ArrayList<Input>(50); // On instancie la liste des inputs
		
		//Pour instancier notre Form, on donne a la fonction displaySegments la liste des Element a traiter, 
		//ici on s'interesse uniquement a ceux qui sont contenu dans une balise FORM
		form=displaySegments(source.getAllElements(HTMLElementName.FORM));
		
		return form;
	}
	
	
	
	/**
	 * Fonction displaySegments.
	 * 
	 * Permet de remplir la liste d'Input du Form, si l'Element est utile on l'ajoute a la liste, et
	 * finalement on set la liste crée a notre form
	 * 
	 * Apelle la fonction isUsefull
	 * 
	 * @param List ( liste d' element de la page html)
	 * @return Form ( Objet Form crée et rempli )
	 */
	                                                                                                                 
	public static Form displaySegments(List<? extends Segment> segments) {
			
		for (Element element : segments.get(0).getAllElements()) {
			
			if (isUsefull(element) != null){
				inputs.add(isUsefull(element));		
			}
		}
		form.setInputList(inputs);
		return form;
	}
	
	/**
	 * Fonction isUsefull.
	 * 
	 * Cette fonction traite les Element parcourus dans displaySegments, si l'Element est d'un type
	 * que l'on veut convserver, on instancie l'objet Input correspondant et on recupere les valeurs utiles,
	 * si l'input n'est pas d'un type que l'on veut traiter on retourne null.
	 * 
	 * On renvoi les Input crée pour le traiter ( on l'ajoute a la liste des inputs dans displaySegments )
	 * 
	 * @param Element ( Element de la liste des Element de la page html qu'on veut traiter)
	 * @return Input (Input correspondant a l'Element traiter ) 
	 */

	public static Input isUsefull(Element element) {
		
		// Si l'Element est de type form on en instancie un nouveau et on rempli ses champs method et action
		if(element.getName() == "form"){
			form = new Form(element.getAttributeValue("method"),element.getAttributeValue("action"));
			return null;
		}
		// Si l'Element est de type input on en instancie un nouveau et on rempli ses champs name,value et type
		// , traitement spécial pour les checkbox pour savoir si la balise est checker par default
		else if (element.getName() == "input") {
				
				if(element.toString().contains("checked")){
					Input SI = new Input(element.getAttributeValue("name"),"checked", element.getAttributeValue("type"));				
					return SI;
				}
			
				Input SI = new Input(element.getAttributeValue("name"),element.getAttributeValue("value"), element.getAttributeValue("type"));				
				return SI;
			
		}
		
		// Si l'Element type select on instancie un Input avec le constructeur avec ArrayList 
		// (pour y stocker les options du select) et on rempli ses champs name,value et de type "menu"
		
		else if (element.getName().equals("select")) {
			
			System.out.println("222");
			Input SI = new Input(element.getAttributeValue("name"),element.getAttributeValue("value"), "menu", null);
				// Liste qui contiendra les options du menu
			ArrayList<String> options = new ArrayList<String>();
	

			for (Element element1 : element.getChildElements()) {		// On descend d'un niveau et on parcours
																		// les fils du menu ( ses options)
				String value = element1.getAttributeValue("value");		// et on les ajoute a la liste
				options.add(value);										
					// Si l'element contient Selected il devient la valeur par default du menu
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
