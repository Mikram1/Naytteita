package application;

import java.io.File;
import java.io.FileNotFoundException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import parser.RandomKUPW;
import parser.RandomSAPPW;
import parser.XMLParser;

public class SalasanageneraattoriController {

	private static final File KUpwFile = new File("KU-salasanat.txt");
	private static final File SAPpwFile = new File("SAP-salasanat.txt");
	
    @FXML
    private TextArea PW_Console;

    @FXML
    private TextField KU_amount;

    @FXML
    private TextField SAP_amount;

    @FXML
    void generatePW(ActionEvent event) {
    	//Ajetaan salasanojen generaatio eri ohjelmasta, otetaan ylˆs salasanojen m‰‰r‰t. KU-salasanat ekana, sitten SAP-salasanat.
    	int[] pituudet = XMLParser.mainCaller();
    	
    	//Laitetaan viesti uusista salasanoista siististi allekkain.
    	String generoituTeksti = "Uudet salasanat generoitu!";
    	String sapM‰‰r‰Teksti = "Uusia SAP-salasanoja: " + pituudet[0];
    	String KUM‰‰r‰Teksti = "Uusia KU-salasanoja: " + pituudet[1];
    	PW_Console.setText(String.format("%s%n%s%n%s", generoituTeksti, sapM‰‰r‰Teksti, KUM‰‰r‰Teksti));
    }

    //Uuden KU-salasanan arpominen & ilmaisu. 
    @FXML
    void newKU(ActionEvent event) {
    	try {
    		int KUamount = Integer.parseInt(KU_amount.getText());
    		String randomSalasana;
    		StringBuilder salasanaKetju = new StringBuilder();
    		for(int i = 0; i < KUamount; i++) {
    			randomSalasana = RandomKUPW.choose(KUpwFile);
    			salasanaKetju.append(randomSalasana + "\r\n");
    		}
			PW_Console.setText(salasanaKetju.toString());
		} 
    	catch (FileNotFoundException e) {
			PW_Console.setText("Virhe: Salasanatiedostoa ei lˆytynyt!" + "\r\n" + "Generoi salasanat uudelleen.");
		}
    	
    	catch (NumberFormatException e) {
    		PW_Console.setText("Virhe: Kielletty arvo!");
    	}
    	
    }

    //Uuden SAP-salasanan arpominen & ilmaisu. 
    @FXML
    void newSAP(ActionEvent event) {
    	try {
    		int SAPamount = Integer.parseInt(SAP_amount.getText());
    		String randomSalasana;
    		StringBuilder salasanaKetju = new StringBuilder();
    		for(int i = 0; i < SAPamount; i++) {
    			randomSalasana = RandomSAPPW.choose(SAPpwFile);
    			salasanaKetju.append((randomSalasana + "\r\n"));
    		}
    		
			PW_Console.setText(salasanaKetju.toString());
    	}
		catch (FileNotFoundException e) {
				PW_Console.setText("Virhe: Salasanatiedostoa ei lˆytynyt!" + "\r\n" + "Generoi salasanat uudelleen.");
		}
	    catch (NumberFormatException e) {
	    		PW_Console.setText("Virhe: Kielletty arvo!");
	    }
    }

}
