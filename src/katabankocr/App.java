package katabankocr;

import java.io.IOException;
import java.util.List;

public class App {

	public static void main(String[] args) {
		
		if (args.length != 1) {
			System.err.println("You must supply a path to a file to parse.");
			return;
		}

		// extract path to file
		String filePath = args[0];
		
		// parse the file
		List<AccountNumber> accountNumbers = null;
		try {
			accountNumbers = OCRFileParser.parse(filePath);
		} catch (IOException e) {
			System.err.println("There was a problem while parsing the file: " + e);
			return;
		}
		
		// print the account numbers to the screen for now
		for (AccountNumber accountNumber : accountNumbers) {
			System.out.println(accountNumber);
		}
	}

}
