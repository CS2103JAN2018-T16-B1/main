//@@author QiuHaohao
package seedu.address.model.account;

import org.junit.Test;
import seedu.address.testutil.Assert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PasswordTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Password(null));
    }

    @Test
    public void isValidPassword() {
        // null pointer
        Assert.assertThrows(NullPointerException.class, () -> Password.isValidPassword(null));

        //invalid
        assertFalse(Password.isValidPassword("")); // empty string
        assertFalse(Password.isValidPassword("123")); // too short
        assertFalse(Password.isValidPassword("abc")); // too short
        assertFalse(Password.isValidPassword("!!!")); // too short and non-word characters
        assertFalse(Password.isValidPassword("!!!!!!")); // non-word characters
        assertFalse(Password.isValidPassword("abcasj!")); // too short and non-word characters
        assertFalse(Password.isValidPassword(""));

        //valid
        assertTrue(Password.isValidPassword("abcde"));
        assertTrue(Password.isValidPassword("banana"));
        assertTrue(Password.isValidPassword("addressbook"));
        assertTrue(Password.isValidPassword("abcde123"));
        assertTrue(Password.isValidPassword("FHAIgasjd123987514"));
        assertTrue(Password.isValidPassword("123123123123"));


    }

    @Test
    public void getPassword() {
        String passwordString = "password";
        Password p = new Password(passwordString);
        assertEquals(passwordString, p.getPassword());
    }

    @Test
    public void equals() {
        Password p1 = new Password("password1");
        Password p1copy = new Password("password1");
        Password p2 = new Password("password2");

        //equal with itself
        assertTrue(p1.equals(p1));

        //equal with an other object with same state
        assertTrue(p1.equals(p1copy));

        //not equal with null
        assertFalse(p1.equals(null));

        //not equal with other type
        assertFalse(p1.equals(1));

        //not equal with same type with different state
        assertFalse(p1.equals(p2));
    }
}
