//@@author QiuHaohao
package seedu.address.model.account;

import org.junit.Test;
import seedu.address.testutil.Assert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MatricNumberTest {

    @Test
    public void isValidMatricNumber() {
        // null pointer
        Assert.assertThrows(NullPointerException.class, () -> MatricNumber.isValidMatricNumber(null));

        //invalid
        assertFalse(MatricNumber.isValidMatricNumber("")); // empty string
        assertFalse(MatricNumber.isValidMatricNumber("123"));
        assertFalse(MatricNumber.isValidMatricNumber("abc"));
        assertFalse(MatricNumber.isValidMatricNumber("!!!"));
        assertFalse(MatricNumber.isValidMatricNumber("!!!!!!"));
        assertFalse(MatricNumber.isValidMatricNumber("A1234567XX!"));
        assertFalse(MatricNumber.isValidMatricNumber("A123456723X!"));
        assertFalse(MatricNumber.isValidMatricNumber("1234567XX!"));

        //valid
        assertTrue(MatricNumber.isValidMatricNumber("A1234567Z"));
        assertTrue(MatricNumber.isValidMatricNumber("A9992567B"));
    }

    @Test
    public void getMatricNumber() {
        String matricNumberString = "A1234567Z";
        MatricNumber m = new MatricNumber(matricNumberString);
        assertEquals(matricNumberString, m.getMatricNumber());
    }

    @Test
    public void equals() {
        MatricNumber p1 = new MatricNumber("A1234567Z");
        MatricNumber p1copy = new MatricNumber("A1234567Z");
        MatricNumber p2 = new MatricNumber("A9992567B");

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
