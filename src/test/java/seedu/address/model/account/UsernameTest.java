//@@author QiuHaohao
package seedu.address.model.account;

import org.junit.Test;
import seedu.address.testutil.Assert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UsernameTest {


    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Username(null));
    }

    @Test
    public void isValidUsername() {
        // null pointer
        Assert.assertThrows(NullPointerException.class, () -> Username.isValidUsername(null));

        //invalid
        assertFalse(Username.isValidUsername("")); // empty string
        assertFalse(Username.isValidUsername("123")); // too short
        assertFalse(Username.isValidUsername("abc")); // too short
        assertFalse(Username.isValidUsername("!!!")); // too short and non-word characters
        assertFalse(Username.isValidUsername("!!!!!!")); // non-word characters
        assertFalse(Username.isValidUsername("abcasj!")); // too short and non-word characters
        assertFalse(Username.isValidUsername(""));

        //valid
        assertTrue(Username.isValidUsername("abcde"));
        assertTrue(Username.isValidUsername("banana"));
        assertTrue(Username.isValidUsername("addressbook"));
        assertTrue(Username.isValidUsername("abcde123"));
        assertTrue(Username.isValidUsername("FHAIgasjd123987514"));
        assertTrue(Username.isValidUsername("123123123123"));

    }

    @Test
    public void getUsername() {
        String usernameString = "username";
        Username p = new Username(usernameString);
        assertEquals(usernameString, p.getUsername());
    }

    @Test
    public void equals() {
        Username p1 = new Username("username1");
        Username p1copy = new Username("username1");
        Username p2 = new Username("username2");

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
