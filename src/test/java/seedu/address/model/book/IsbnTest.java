package seedu.address.model.book;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class IsbnTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Isbn(null));
    }

    @Test
    public void constructor_invalidIsbn_throwsIllegalArgumentException() {
        String invalidIsbn = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Isbn(invalidIsbn));
    }

    @Test
    public void isValidIsbn() {
        // null isbn
        Assert.assertThrows(NullPointerException.class, () -> Isbn.isValidIsbn(null));

        // invalid isbn numbers
        assertFalse(Isbn.isValidIsbn("")); // empty string
        assertFalse(Isbn.isValidIsbn(" ")); // spaces only
        assertFalse(Isbn.isValidIsbn("91")); // less than 3 numbers
        assertFalse(Isbn.isValidIsbn("phone")); // non-numeric
        assertFalse(Isbn.isValidIsbn("9011p041")); // alphabets within digits
        assertFalse(Isbn.isValidIsbn("9312 1534")); // spaces within digits

        // valid isbn numbers
        assertTrue(Isbn.isValidIsbn("911")); // exactly 3 numbers
        assertTrue(Isbn.isValidIsbn("93121534"));
        assertTrue(Isbn.isValidIsbn("124293842033123")); // long isbn numbers
    }
}
