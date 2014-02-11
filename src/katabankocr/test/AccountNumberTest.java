package katabankocr.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import katabankocr.AccountNumber;
import katabankocr.AccountNumberDigit;
import katabankocr.ChecksumException;

import org.junit.Test;

public class AccountNumberTest {

	@Test
	public void testAllDigitsLegible() {
		List<AccountNumberDigit> digits = new ArrayList<>();
		
		// make the first digit invalid
		digits.add(new AccountNumberDigit(-1));
		
		// and the rest valid
		for (int i = 2; i < 10; i++) {
			digits.add(new AccountNumberDigit(i));
		}
		
		AccountNumber accountNumber = new AccountNumber(digits);
		
		assertFalse(accountNumber.allDigitsLegible());
	}

	@Test
	public void testValidChecksum() {
		List<AccountNumberDigit> digits = new ArrayList<>();
		for (int i = 1; i < 10; i++) {
			digits.add(new AccountNumberDigit(i));
		}
		AccountNumber accountNumber = new AccountNumber(digits);
		
		assertTrue(accountNumber.validChecksum());
	}

	@Test
	public void testCalcChecksum1() {
		List<AccountNumberDigit> digits = new ArrayList<>();
		for (int i = 1; i < 10; i++) {
			digits.add(new AccountNumberDigit(i));
		}
		
		int checksum = -1;
		try {
			checksum = AccountNumber.calcChecksum(digits);
		} catch (ChecksumException e) {
			fail();
		}
		
		assertEquals(0, checksum);
	}

	@Test
	public void testCalcChecksum2() {
		List<AccountNumberDigit> digits = new ArrayList<>();
		AccountNumberDigit five = new AccountNumberDigit(5);
		for (int i = 1; i < 10; i++) {
			digits.add(five);
		}
		
		int checksum = -1;
		try {
			checksum = AccountNumber.calcChecksum(digits);
			System.out.println("Checksum val: " + checksum);
		} catch (ChecksumException e) {
			fail();
		}
		
		assertEquals(5, checksum);
	}
}
