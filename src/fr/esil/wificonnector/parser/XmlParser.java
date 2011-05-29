package fr.esil.wificonnector.parser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.esil.wificonnector.object.Form;
import net.esil.wificonnector.object.Input;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import fr.esil.wificonnector.object.Wifi;

public class XmlParser {

//	public static void main(String[] args) {		// main pour tester en local 
//
//		Wifi myWifi = read(new File("data/profil_free.xml")); // Fonction qui lit un fichier .xml et construit le Wifi correspondant
//		//System.out.println(myWifi);
//
//		write(myWifi, new String ("data/write_profil_free.xml")); // Fonction qui convertit un Wifi en .xml 
//	}

	
	/**
	 * Fonction read
	 * 
	 * Fonction qui parcours un fichier .xml avec le parser JDom et qui 
	 * construit un Wifi en remplissant ses champs. 
	 * 
	 * 
	 * @param File (xml)
	 * @return Wifi
	 */
	
	public static Wifi read(File file) {
		// On crée une instance de SAXBuilder
		SAXBuilder sxb = new SAXBuilder();
		Document document = null;


		try {
			// On crée un nouveau document JDOM avec en argument le fichier XML
			document = sxb.build(file);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

		// On initialise un nouvel élément racine avec l'élément racine du document (wifi).

		Element rWifi = document.getRootElement();

		// On construit un wifi en récuperant les champs ssid et avec un form vide

		Wifi wifi = new Wifi(rWifi.getAttributeValue("essid"), rWifi.getAttributeValue("ssid"));

		Element rForm = rWifi.getChild("form");  // On descend d'un niveau (form)


		// On construit un form en récuperant les champs method et action et avec une Liste d'inputs vide
		Form form = new Form(rForm.getAttributeValue("method"),rForm.getAttributeValue("action"));    

		Element rInputs = rForm.getChild("inputs"); 	// On descend d'un niveau (inputs)
		List<Element> listInputs = rInputs.getChildren();// On crée une List contenant tous les input
		List<Input> inputs = new LinkedList<Input>();		// ArrayList pour stocker les inputs et les ajouter ensuite au Form

		// On crée un Iterator sur notre liste
		Iterator<Element> it = listInputs.iterator();

		while (it.hasNext()) {   						// On parcours la liste pour construire le form
			Element courant = it.next();		

			String name = courant.getAttributeValue("name");
			String value = courant.getAttributeValue("value");			// On recupere les champs pour l'Input
			String type = courant.getAttributeValue("type");
			ArrayList<String> optionsList = new ArrayList<String>();	// List des options vide

			if (type.equals("menu")) { // Si l'element courant est de type "menu" on parcoure ses fils : options

				List<Element> listOptions = courant.getChildren();
				Iterator<Element> it2 = listOptions.iterator();

				while (it2.hasNext()) {
					Element courantOptions = it2.next();
					optionsList.add(courantOptions.getAttributeValue("value")); // et on recupere les valeurs
				}

			}

			Input input = new Input(name, value, type, optionsList);
			inputs.add(input);
		}

		form.setInputList(inputs);  // On a finit de construire la liste d'input, on l'ajoute donc au form
		wifi.setForm(form);			// Et on ajoute le form au wifi

		return wifi;
	}
	
	/**
	 * Fonction write
	 * 
	 * Fonction qui convertit un objet Wifi en une arborescence stocker 
	 * dans un fichier xml correspondant a un profil. 
	 * 
	 * 
	 * @param Wifi (Wifi qu'on veut ecrire) ,File (Fichier xml dans lequel on veut stoker le profil)
	 * @return void
	 */
	

	public static void write(Wifi mWifi, File file) {
		//Nous allons commencer notre arborescence en créant la racine XML "wifi"
		Element wifi = new Element("wifi");
		wifi.setAttribute(new Attribute("essid", mWifi.getBSSID()));   // On ajoute les attributs de la balise wifi
		wifi.setAttribute(new Attribute("ssid", mWifi.getSSID()));
		
		//On crée un nouveau Document JDOM basé sur la racine que l'on vient de créer
		Document document = new Document(wifi);

		//On crée un nouvel Element form et on l'ajoute en tant qu'Element de wifi
		Element form = new Element("form");
		
		// On ajoute les attributs method
		form.setAttribute(new Attribute("method", "defMethod"));
		if(mWifi.getForm().getMethod()!= null ){
			form.setAttribute(new Attribute("method", mWifi.getForm().getMethod()));
		}
		
		form.setAttribute(new Attribute("action","defAction" ));	// et action a l'element form
		if(mWifi.getForm().getMethod()!= null ){
			form.setAttribute(new Attribute("action", mWifi.getForm().getAction()));
		} 	
		wifi.addContent(form);

		//On crée un nouvel Element inputs et on l'ajoute en tant qu'Element de form
		Element inputs = new Element("inputs");		
		form.addContent(inputs);
		
		
		for(int i=0; i<mWifi.getForm().getInputList().size();i++){
			Element input = new Element("input");
			Input inputcourant=mWifi.getForm().getInputList().get(i);
			
			// Si le form contient un menu deroulant on ajoute les attributs de l'input et on descend d'un 
			// niveau pour ensuite ecrire la liste des options du menu dans la balise input de type menu 
			if(inputcourant.getType().equals("menu")){									
				input.setAttribute(new Attribute("name", inputcourant.getName()));
				input.setAttribute(new Attribute("value", inputcourant.getValue()));
				input.setAttribute(new Attribute("type", inputcourant.getType()));
				inputs.addContent(input);
				
				for(int l=0; l<inputcourant.getOptionsList().size();l++){
					Element option = new Element("option");
					option.setAttribute(new Attribute("value", inputcourant.getOptionsList().get(l)));
					input.addContent(option);
				}
			}
			// Si le form ne contient pas de menu on ajoute juste les attributs de chaque input
			else{
				input.setAttribute(new Attribute("name", inputcourant.getName()));
				input.setAttribute(new Attribute("value", inputcourant.getValue()));
				input.setAttribute(new Attribute("type", inputcourant.getType()));
				inputs.addContent(input);
			}
		}
		

		try
		   {
		      //On utilise ici un affichage classique avec getPrettyFormat()
		      XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
		      //Remarquez qu'il suffit simplement de créer une instance de FileOutputStream
		      //avec en argument le nom du fichier pour effectuer la sérialisation.
		      sortie.output(document, new FileOutputStream(file));
		   }
		   catch (IOException e){
			   e.printStackTrace();
		   }

	}

}
