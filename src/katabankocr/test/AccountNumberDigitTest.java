package katabankocr.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import katabankocr.AccountNumberDigit;

import org.junit.Test;

public class AccountNumberDigitTest {

	@Test
	public void testIsValid() {
		
		// create a list of valid digits
		List<AccountNumberDigit> validDigits = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			validDigits.add(new AccountNumberDigit(i));
		}
		
		// create some invalid digits
		List<AccountNumberDigit> invalidDigits = new ArrayList<>();
		invalidDigits.add(new AccountNumberDigit(-1));
		invalidDigits.add(new AccountNumberDigit(-123456));
		invalidDigits.add(new AccountNumberDigit(10));
		invalidDigits.add(new AccountNumberDigit(123456));
		
		// check the valid ones
		for (AccountNumberDigit validDigit : validDigits) {
			assertTrue(validDigit.isValid());
		}
		
		// and then the invalid ones
		for (AccountNumberDigit invalidDigit : invalidDigits) {
			assertFalse(invalidDigit.isValid());
		}
	}

	@Test
	public void testToString() {
		for (int i = 0; i < 10; i++) {
			assertTrue(new AccountNumberDigit(i).toString().equals(Integer.toString(i)));
		}
		
		assertTrue(new AccountNumberDigit(-1).toString().equals("?"));
		assertTrue(new AccountNumberDigit(-123456).toString().equals("?"));
		assertTrue(new AccountNumberDigit(10).toString().equals("?"));
		assertTrue(new AccountNumberDigit(123456).toString().equals("?"));
	}

}
