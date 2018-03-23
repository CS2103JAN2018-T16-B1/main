package seedu.address.model.book;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class AvailTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Avail(null));
    }

    @Test
    public void constructor_invalidAvail_throwsIllegalArgumentException() {
        String invalidAvail = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Avail(invalidAvail));
    }

    @Test
    public void isValidAvail() {
        // null avail
        Assert.assertThrows(NullPointerException.class, () -> Avail.isValidAvail(null));

        // blank avail
        assertFalse(Avail.isValidAvail("")); // empty string
        assertFalse(Avail.isValidAvail(" ")); // spaces only

        // valid avail
        assertTrue(Avail.isValidAvail("Reserved"));  // Reserved
        assertTrue(Avail.isValidAvail("Borrowed"));  // Borrowed
        assertTrue(Avail.isValidAvail("Available"));  // Available
        assertTrue(Avail.isValidAvail("Borrowed and Reserved")); // Borrowed and Reserved
    }
}
