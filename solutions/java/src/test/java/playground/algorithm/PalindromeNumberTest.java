package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PalindromeNumberTest {

    @Test
    void case_1() {
        PalindromeNumber solution = new PalindromeNumber();

        assertFalse(solution.isPalindrome(-121));
    }

    @Test
    void case_2() {
        PalindromeNumber solution = new PalindromeNumber();

        assertTrue(solution.isPalindrome(121));
    }

    @Test
    void case_3() {
        PalindromeNumber solution = new PalindromeNumber();

        assertTrue(solution.isPalindrome(1));
    }

    @Test
    void case_4() {
        PalindromeNumber solution = new PalindromeNumber();

        assertTrue(solution.isPalindrome(1000000001));
    }
}