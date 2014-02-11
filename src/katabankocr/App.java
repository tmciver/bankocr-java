package katabankocr;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class App {

	public static void main(String[] args) throws IOException {
		
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
		
		// print the account numbers to an output file
		String outFile = filePath + ".out";
		//BufferedWriter out = new BufferedWriter(new FileWriter(outFile));
		PrintWriter out = new PrintWriter(outFile);
		for (AccountNumber accountNumber : accountNumbers) {
			out.println(accountNumber.toString());
		}
		out.close();
	}

}
