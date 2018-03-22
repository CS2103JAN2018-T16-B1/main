package seedu.address.model.book;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class AvailabilityTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Availability(null));
    }

    @Test
    public void constructor_invalidAvailability_throwsIllegalArgumentException() {
        String invalidAvailability = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Availability(invalidAvailability));
    }

    @Test
    public void isValidAvailability() {
        // null availability
        Assert.assertThrows(NullPointerException.class, () -> Availability.isValidAvailability(null));

        // blank availability
        assertFalse(Availability.isValidAvailability("")); // empty string
        assertFalse(Availability.isValidAvailability(" ")); // spaces only

        // valid availability
        assertTrue(Availability.isValidAvailability("Reserved"));  // Reserved
        assertTrue(Availability.isValidAvailability("Borrowed"));  // Borrowed
        assertTrue(Availability.isValidAvailability("Available"));   // Available
        assertTrue(Availability.isValidAvailability("Borrowed and Reserved")); // Borrowed and Reserved
    }
}
