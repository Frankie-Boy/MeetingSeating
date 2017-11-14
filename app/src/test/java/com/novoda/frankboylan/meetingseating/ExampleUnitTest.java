package com.novoda.frankboylan.meetingseating;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void emailSimpleReturnsTrue() {
        CreateAccountActivity newAcct = new CreateAccountActivity();
        assertEquals(newAcct.emailIsValid("francisco"), true);
    }

    @Test
    public void emailIllegalCharacterReturnsFalse() {
        CreateAccountActivity newAcct = new CreateAccountActivity();
        assertEquals(newAcct.emailIsValid("fr@ncisco"), false);
    }

    @Test
    public void emailTooShortReturnsFalse() {
        CreateAccountActivity newAcct = new CreateAccountActivity();
        assertEquals(newAcct.emailIsValid("1"), false);
    }

    @Test
    public void emailTooLongReturnsFalse() {
        CreateAccountActivity newAcct = new CreateAccountActivity();
        assertEquals(newAcct.emailIsValid("1234567891011121314151617"), false);
    }
}
