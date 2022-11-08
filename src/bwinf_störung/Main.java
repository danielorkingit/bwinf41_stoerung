package bwinf_störung;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * BWINF-41 "Hüpfburg"
 * Developer: Daniel Orkin
 * Team-ID: 00376
 */

public class Main {

	public static void main(String[] args) throws IOException {
														
		Scanner scannerF = new Scanner(Paths.get("book.txt"), StandardCharsets.UTF_8.name());
		
		String rawText = scannerF.useDelimiter("\\A").next().replaceAll("[^A-Za-z0-9ßäÄüÜöÖ ]"," ");
		
		String[] content = rawText.toLowerCase().split("\\s+");
		
		scannerF.close();
		 
		System.out.println("Enter the text-passage. Replace the gaps with a underscore (_).");
		
		Scanner scannerI = new Scanner(System.in);
								
		String[] passage = scannerI.nextLine().toLowerCase().split("\\s+");

		searchPassage(passage, content);
	}
	
	public static boolean searchPassage(String[] passage, String[] text) {
		
		ArrayList<String> log = new ArrayList<>();
		
		int before = countUnderscoresBefore(passage);
		
		// only underscores error
		
		if (before == 999999999) {
			System.out.println("Enter a valid text-piece to search for. The usage of underscores only is not allowed."); // only underscores
			return false;
		}
		
		boolean noMatches = true;
				
		// search the first word in text-passage & ignore underscores before
		
		for (int i = 0; i<text.length ; i++) {
			if (text[i].equals(passage[before])) {
				if(compare(passage, text, i-before)) {
					String tp = buildTextPiece(text, i-before, passage.length);
					if (!log.contains(tp)) {
						log.add(tp);
						System.out.println(tp);
					}
					noMatches = false;
				}
			} 
		}
		
		// no matches error
		
		if (noMatches) {
			System.out.println("No matches found. Try another text-piece to search for.");
			return false;
		}
		
		return true;
		
	}
	
	// count underscores before if input is for instance: _ _ vor _ richtig etc.
	
	private static int countUnderscoresBefore(String[] passage) {
		int result = 0;
		for (String x : passage) {
			if (x.equals("_")) {
				result = result + 1;
			} else {
				break;
			}
		}
		if (result == passage.length) {
			return 999999999; // error-code -> only underscores in passage
		} else {
			return result;
		}
	}

	private static String buildTextPiece(String[] text, int index, int lenght) {
		
		ArrayList<String> result = new ArrayList<>();
		
		for (int i = 0 ; i<lenght ; i++) {
			result.add(text[i+index]);
		}
		
		return String.join(" ", result);
		
	}
	
	public static boolean compare(String[] passage, String[] text, int index) {
		boolean checker = true;
		for(int x = 0+index; x<passage.length+index; x++) {
			if (!text[x].equals(passage[x-index])) {
				if(!passage[x-index].equals("_")) { // Ignore underscores
					checker = false;
					break;
				}
			}
		}
		return checker;
		
	}

}

