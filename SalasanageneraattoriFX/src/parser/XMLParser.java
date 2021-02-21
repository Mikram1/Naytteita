package parser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;


/**
 * Erittelee KOTUS-sanalistasta (vastaa sanakirjaa) eri salasanoihin sopivat sanat pituuden ja selvyyden perusteella.
 * KOTUS-sanalista on xml-muodossa, joten tekee erilliset .txt-tiedostot sopivista KU- ja SAP-salasanoista.
 * KU- ja SAP-salasanojen vaatimukset eritelty omissa aliohjelmissaan.
 * @author Mikko Toitturi
 */
public class XMLParser {

	static List<String> sanalista = new ArrayList<String>();		//Kaikki l�pik�yt�v�t sanat
	static List<String> kasisana = new ArrayList<String>(); 		//SAP-salasanoiksi kelpuutetut sanat
	static List<String> viistoistsana = new ArrayList<String>();	//KU-salasanoiksi kelpuutetut sanat
	
	public static void main(String[] args) {
		
		//Listojen alustus per�kk�isi� kutsumisia varten
		sanalista = new ArrayList<String>();
		kasisana = new ArrayList<String>();
		viistoistsana = new ArrayList<String>();
		
		File xmlFile = new File("src/parser/kotus-sanalista_v1.xml");
		parseToList(xmlFile);
		
		kasisana = parseToEight(sanalista);
		System.out.println("Valmis, SAP-salasanoissa on " + kasisana.size() + " sanaa.");
		
		viistoistsana = parseToFifteen(sanalista);
		System.out.println("Valmis, KU-salasanoissa on " + viistoistsana.size() + " sanaa.");
		try {
			FileWriter writer = new FileWriter("SAP-salasanat.txt");
			for(String s:kasisana) writer.write(s + System.lineSeparator());
			writer.close();
			
			FileWriter writer2 = new FileWriter("KU-salasanat.txt");
			for(String s:viistoistsana) writer2.write(s + System.lineSeparator());
			writer2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Tekee sy�tetyst� xml-tiedostosta String-pohjaisen listan.
	 * @param parseFile
	 */
	public static void parseToList(File parseFile) {
		try {
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		    Document doc = dBuilder.parse(parseFile);
		    
		    doc.getDocumentElement().normalize();
		    
		    NodeList wordNodes = doc.getElementsByTagName("s");
		    for(int i = 0;  i < wordNodes.getLength(); i++) {
		    	String currentText = wordNodes.item(i).getTextContent();
		    	sanalista.add(currentText);
		    }
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Rajaa listan sanat 6-9 pituisiin sanoihin SAP-salasanoja varten. 
	 * Lis�� v�h. 1 numeron ja tekee ison kirjaimen, jotta salasanavaatimus t�yttyy. (v�h. 8 kirjainta, v�h. 1 numero, v�h. 1 iso kirjain).
	 * @param sLista lista, jota l�hdet��n rajaamaan. 
	 * @return rajattu lista
	 */
	public static List<String> parseToEight (List<String> sLista) {
		List<String> result = new ArrayList<String>();
		
		
		for(int i = 0; i < sLista.size(); i++) {
			String currentWord = sLista.get(i);
			//Rajaa listan sanat 6-9 merkin pituisiin. 
			if(currentWord.length() > 9 || currentWord.length() < 6) continue;
			//Poistaa ��kk�si� sis�lt�v�t sanat
			else if(currentWord.contains("�") || currentWord.contains("�") || currentWord.contains("�")) continue;
			//Poistaa v�liviivan tai v�lily�nnin sis�lt�v�t sanat
			else if(currentWord.contains("-") || currentWord.contains(" ")) continue;
			//Lis�� ison alkukirjaimen
			currentWord = currentWord.substring(0, 1).toUpperCase() + currentWord.substring(1);
			
			//Lis�� numeron
			int wordLength = currentWord.length();
			int addedDigits;
			if(wordLength >= 8) addedDigits = 1;
			else addedDigits = (8 - wordLength);
			
			for(int j = 0; j < addedDigits; j++) {
				Random rn = new Random();
				int randomNo = rn.nextInt(9);
				currentWord = currentWord.substring(0, currentWord.length()) + randomNo;
			}
			result.add(currentWord);
		}
		
		return result;
	}
	
	/**
	 * Palauttaa v�h 15 merkki� pitk�n salasanan KU-salasanaa varten.
	 * KU-salasanalle vaatimus on v�h. 15 merkin pituus.
	 * @param sLista Lista k�ytett�vist� sanoista
	 * @return v�h 15 merkki� pitk�n salasana
	 */
	public static List<String> parseToFifteen (List<String> sLista) {
		List<String> result = new ArrayList<String>();
		
		
		for(int i = 0; i < sLista.size(); i++) {
			String currentWord = sLista.get(i);
			//Rajaa listan sanat 14-19 merkin pituisiin. 
			if(currentWord.length() < 15 || currentWord.length() > 19) continue;
			//Poistaa ��kk�si� sis�lt�v�t sanat
			else if(currentWord.contains("�") || currentWord.contains("�") || currentWord.contains("�")) continue;
			//Poistaa v�liviivan tai v�lily�nnin sis�lt�v�t sanat
			else if(currentWord.contains("-") || currentWord.contains(" ")) continue;
			//Lis�� ison alkukirjaimen
			currentWord = currentWord.substring(0, 1).toUpperCase() + currentWord.substring(1);
			
			//Lis�� numeron
			int wordLength = currentWord.length();
			int addedDigits;
			if(wordLength >= 15) addedDigits = 0;
			else addedDigits = (15 - wordLength);
			
			for(int j = 0; j < addedDigits; j++) {
				Random rn = new Random();
				int randomNo = rn.nextInt(9);
				currentWord = currentWord.substring(0, currentWord.length()) + randomNo;
			}
			result.add(currentWord);
		}
		
		return result;
	}
	
	/**
	 * Kutsuu main-funktiota. Tehty toisesta paketista kutsumista varten (application-paketin alta).
	 */
	public static int[] mainCaller() {
		main(null);
		int kasisanaPituus = kasisana.size();
		int viistoistsanaPituus = viistoistsana.size();
		
		int[] listojenPituudet = {kasisanaPituus, viistoistsanaPituus};
		return listojenPituudet;
	}

}
