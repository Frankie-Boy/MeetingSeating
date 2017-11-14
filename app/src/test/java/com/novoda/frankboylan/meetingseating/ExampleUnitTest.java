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
}
