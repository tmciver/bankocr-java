package katabankocr;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OCRFileParser {
	
	private static final int DIGITS_PER_ACCOUNT_NUMBER = 9;
	private static final int DIGIT_WIDTH_IN_CHARS = 3;
	
	private static final Map<String, Integer> digitMap = new HashMap<String, Integer>() {{
		put(" _ " +
			"| |" +
			"|_|",   0);
		put("   " +
			"  |" +
			"  |",   1);
		put(" _ " +
			" _|" +
			"|_ ",   2);
		put(" _ " +
			" _|" +
			" _|",   3);
		put("   " +
			"|_|" +
			"  |",   4);
		put(" _ " +
			"|_ " +
			" _|",   5);
		put(" _ " +
			"|_ " +
			"|_|",   6);
		put(" _ " +
			"  |" +
			"  |",   7);
		put(" _ " +
			"|_|" +
			"|_|",   8);
		put(" _ " +
			"|_|" +
			" _|",   9);
	}};

	private OCRFileParser() {}

	public static List<AccountNumber> parse(String filePath) throws IOException {
		return parse(new BufferedReader(new FileReader(filePath)));
	}
	
	private static List<AccountNumber> parse(BufferedReader reader) throws IOException {
		List<AccountNumber> accountNumbers = parseAccountNumbers(reader);
		return accountNumbers;
	}

	private static List<AccountNumber> parseAccountNumbers(BufferedReader reader) throws IOException {
		List<AccountNumber> accountNumbers = new ArrayList<>();
		return parseAccountNumbers(reader, accountNumbers);
	}

	private static List<AccountNumber> parseAccountNumbers(
			BufferedReader reader, List<AccountNumber> accountNumbers) throws IOException {
		
		AccountNumber accountNumber = parseAccountNumber(reader);
		
		if (accountNumber == null) {
			return accountNumbers;
		}
		
		accountNumbers.add(accountNumber);

		return parseAccountNumbers(reader, accountNumbers);
	}

	private static AccountNumber parseAccountNumber(BufferedReader reader) throws IOException {
		// read 4 lines from the reader
		String line1 = reader.readLine();
		String line2 = reader.readLine();
		String line3 = reader.readLine();
		String line4 = reader.readLine();
		
		// if any of the first three lines are null, we're at the end of the file
		if (line1 == null ||
			line2 == null ||
			line3 == null) {
			return null;
		}
		
		// convert the lines to digit strings
		String[] digitStrings = linesToDigitStrings(line1, line2, line3);
		
		// convert the digitStrings to digits
		List<AccountNumberDigit> digits = new ArrayList<>();
		for (String digitString : digitStrings) {
			digits.add(digitStringToDigit(digitString));
		}
		
		return new AccountNumber(digits);
	}
	
	private static String[] linesToDigitStrings(String line1, String line2, String line3) {
		
		// make sure each line has the correct number of characters
		int expectedLength = 27;
		if (line1.length() != expectedLength ||
			line2.length() != expectedLength ||
			line3.length() != expectedLength) {
			throw new IllegalArgumentException("One of the input lines did not have the requisite " + expectedLength + " characters.");
		}
		
		String[] line1Segments = segmentLine(line1);
		String[] line2Segments = segmentLine(line2);
		String[] line3Segments = segmentLine(line3);
		
		
		String[] digitStrings = new String[DIGITS_PER_ACCOUNT_NUMBER];
		for (int i = 0; i < DIGITS_PER_ACCOUNT_NUMBER; i++) {
			digitStrings[i] = line1Segments[i] + line2Segments[i] + line3Segments[i];
		}
		
		return digitStrings;
	}

	private static String[] segmentLine(String line) {
		String[] parts = new String[DIGITS_PER_ACCOUNT_NUMBER];
        int len = line.length();
        int partNum = 0;
        for (int i = 0; i < len; i += DIGIT_WIDTH_IN_CHARS) {
            parts[partNum++] = line.substring(i, Math.min(len, i + DIGIT_WIDTH_IN_CHARS));
        }
        return parts;
	}
	
	private static AccountNumberDigit digitStringToDigit(String digitString) {
		Integer val = digitMap.get(digitString);
		if (val == null) {
			val = -1;
		}
		return new AccountNumberDigit(val);
	}
}
