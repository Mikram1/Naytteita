package parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

/**
 * Arpoo satunnaisen salasanan KU-salasanalistauksesta. 
 * Ehtoina KU-salasanalle on, ett‰ salasana on v‰hint‰‰n 15 merkki‰ pitk‰.
 * @author Mikko Toitturi
 */
public class RandomKUPW {

	static File pwFile = new File("KU-salasanat.txt");

	public static void main(String[] args) throws FileNotFoundException {
		System.out.println(choose(pwFile));
	}
	
	/**
	 * Valitsee satunnaisen rivin .txt-tiedostosta.
	 */
	public static String choose(File f) throws FileNotFoundException
	  {		
	     String result = null;
	     Random rand = new Random();
	     int n = 0;
	     for(Scanner sc = new Scanner(f); sc.hasNext(); )
	     {	    	 
	        ++n;
	        String line = sc.nextLine();
	        if(rand.nextInt(n) == 0)
	           result = line;         
	     }
	     return result;      
	  }
}
