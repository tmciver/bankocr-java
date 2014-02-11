package katabankocr;

public class AccountNumberDigit {

	private int val;
	private boolean isValid;
	
	private String valString;

	public AccountNumberDigit(int val) {
		this.val = val;
		
		valString = "?";
		isValid = false;
		if (val >= 0 && val < 10) {
			isValid = true;
			valString = Integer.toString(val);
		}
	}

	public int getValue() {
		return val;
	}

	public boolean isValid() {
		return isValid;
	}

	@Override
	public String toString() {
		return valString;
	}
}
