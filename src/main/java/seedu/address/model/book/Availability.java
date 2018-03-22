package seedu.address.model.book;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Book's availability in the catalogue.
 * Guarantees: immutable; is valid as declared in {@link #isValidAvailability(String)}
 */
public class Availability {
    public static final String available = "Available";
    public static final String borrowed = "Borrowed";
    public static final String reserved = "Reserved";
    public static final String borrowedAndReserved = "Borrowed and Reserved";
    public static final String MESSAGE_AVAILABILITY_CONSTRAINTS = "Book availability should be one of the following:\n "
            + "1. " + available + "\n"
            + "2. " + borrowed + "\n"
            + "3. " + reserved + "\n"
            + "4. " + borrowedAndReserved + "\n";

    public final String value;

    /**
     * Constructs an {@code Availability}.
     *
     * @param availability A valid availability address.
     */
    public Availability(String availability) {
        requireNonNull(availability);
        checkArgument(isValidAvailability(availability), MESSAGE_AVAILABILITY_CONSTRAINTS);
        this.value = availability;
    }

    /**
     * Returns if a given string is a valid book availability.
     */
    public static boolean isValidAvailability(String test) {
        return test.equals(available) || test.equals(borrowed) || test.equals(reserved) || test.equals(borrowedAndReserved);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Availability // instanceof handles nulls
                && this.value.equals(((Availability) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
