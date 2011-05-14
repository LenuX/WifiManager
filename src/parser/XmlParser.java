package parser;

import java.io.*;

import object.Form;
import object.SuperInput;
import object.Wifi;
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;

public class XmlParser {

//	public static void main(String[] args) {
//
//		Wifi myWifi = read(new File("data/profil_free.xml")); // Fonction qui lit un fichier .xml et construit le Wifi correspondant
//		//System.out.println(myWifi);
//
//		write(myWifi, new String ("data/write_profil_free.xml")); // Fonction qui convertit un Wifi en .xml 
//	}

	static Wifi read(File file) {
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

		List listInputs = rInputs.getChildren();// On crée une List contenant tous les input
		
		List<SuperInput> inputs = new LinkedList<SuperInput>();		// ArrayList pour stocker les inputs et les ajouter ensuite au Form

		// On crée un Iterator sur notre liste
		Iterator it = listInputs.iterator();

		while (it.hasNext()) {   						// On parcours la liste pour construire le form
			Element courant = (Element) it.next();		

			String name = courant.getAttributeValue("name");
			String value = courant.getAttributeValue("value");			// On recupere les champs pour l'Input
			String type = courant.getAttributeValue("type");
			ArrayList<String> optionsList = new ArrayList<String>();	// List des options vide

			if (type.equals("menu")) { // Si l'element courant est de type "menu" on parcoure ses fils : options

				List listOptions = courant.getChildren();
				Iterator it2 = listOptions.iterator();

				while (it2.hasNext()) {
					Element courantOptions = (Element) it2.next();
					optionsList.add(courantOptions.getAttributeValue("value")); // et on recupere les valeurs
				}

			}

			SuperInput input = new SuperInput(name, value, type, optionsList);
			inputs.add(input);
		}

		form.setInputList(inputs);  // On a finit de construire la liste d'input, on l'ajoute donc au form
		wifi.setForm(form);			// Et on ajoute le form au wifi

		return wifi;
	}

	static void write(Wifi wwifi, String file) {
		//Nous allons commencer notre arborescence en créant la racine XML "wifi"
		Element wifi = new Element("wifi");
		wifi.setAttribute(new Attribute("essid", wwifi.getESSID()));   // On ajoute les attributs de la balise wifi
		wifi.setAttribute(new Attribute("ssid", wwifi.getSSID()));
		
		//On crée un nouveau Document JDOM basé sur la racine que l'on vient de créer
		Document document = new Document(wifi);

		//On crée un nouvel Element form et on l'ajoute en tant qu'Element de wifi
		Element form = new Element("form");
		form.setAttribute(new Attribute("method", wwifi.getForm().getMethod()));
		form.setAttribute(new Attribute("action", wwifi.getForm().getAction()));
		wifi.addContent(form);

		Element inputs = new Element("inputs");
		form.addContent(inputs);
		
		
		for(int i=0; i<wwifi.getForm().getInputList().size();i++){
			Element input = new Element("input");
			SuperInput inputcourant=wwifi.getForm().getInputList().get(i);
			
			if(inputcourant.getType().equals("menu")){
				input.setAttribute(new Attribute("name", inputcourant.getName()));
				input.setAttribute(new Attribute("value", inputcourant.getValue()));
				input.setAttribute(new Attribute("type", inputcourant.getType()));
				inputs.addContent(input);
				
				for(int l=0; l<wwifi.getForm().getInputList().size();l++){
					Element option = new Element("option");
					option.setAttribute(new Attribute("value", inputcourant.getOptionsList().get(l)));
					input.addContent(option);
				}
			}
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
		   catch (java.io.IOException e){
			   e.printStackTrace();
		   }

	}

}
