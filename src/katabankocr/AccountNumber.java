package katabankocr;

import java.util.List;

public class AccountNumber {
	
	private final static int NUM_ACCOUNT_NUMBER_DIGITS = 9;
	
	private boolean allDigitsLegible;
	private boolean validChecksum;
	private String strRep;

	public AccountNumber(List<AccountNumberDigit> digits) {
		
		// make sure we have the right number of digits
		if (digits.size() != NUM_ACCOUNT_NUMBER_DIGITS) {
			throw new IllegalArgumentException("There must be " + NUM_ACCOUNT_NUMBER_DIGITS + " digits in a valid account number.");
		}
		
		// see if it's valid
		allDigitsLegible = true;
		for (AccountNumberDigit digit : digits) {
			allDigitsLegible &= digit.isValid();
		}
		
		// valid checksum?
		validChecksum = false;
		try {
			validChecksum = calcChecksum(digits) == 0;
		} catch (ChecksumException e) {}
		
		// create the string representation
		StringBuilder sb = new StringBuilder();
		for (AccountNumberDigit digit : digits) {
			sb.append(digit.toString());
		}
		
		if (!allDigitsLegible) {
			sb.append(" ILL");
		} else if (!validChecksum) {
			sb.append(" ERR");
		}
		strRep = sb.toString();
	}
	
	public boolean allDigitsLegible() {
		return allDigitsLegible;
	}
	
	public boolean validChecksum() {
		return validChecksum;
	}
	
	public static int calcChecksum(List<AccountNumberDigit> digits) throws ChecksumException {
		int checksum = 0;
		int num = 9;
		for (AccountNumberDigit digit : digits) {
			
			// make sure the digit is valid or it's an error
			if (!digit.isValid()) {
				throw new ChecksumException();
			}
			
			checksum += num * digit.getValue();
			num--;
		}
		return checksum % 11;
	}

	@Override
	public String toString() {
		return strRep;
	}
}
