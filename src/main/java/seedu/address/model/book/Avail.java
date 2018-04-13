//@@author khiayi
package seedu.address.model.book;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;


/**
 * Represents a Book's availability in the catalogue.
 * Guarantees: immutable; is valid as declared in {@link #isValidAvail(String)}
 */
public class Avail {
    public static final String AVAILABLE = "Available";
    public static final String BORROWED = "Borrowed";
    public static final String RESERVED = "Reserved";
    public static final String BORROWED_AND_RESERVED = "Borrowed and Reserved";
    public static final String MESSAGE_AVAIL_CONSTRAINTS = "Book availability should be one of the following:\n "
        + "1. " + AVAILABLE + "\n"
        + "2. " + BORROWED + "\n"
        + "3. " + RESERVED + "\n"
        + "4. " + BORROWED_AND_RESERVED + "\n";


    public final String value;

    /**
     * Constructs an {@code Avail}.
     *
     * @param avail A valid availability .
     */
    public Avail(String avail) {
        requireNonNull(avail);
        checkArgument(isValidAvail(avail), MESSAGE_AVAIL_CONSTRAINTS);
        this.value = avail;
    }

    /**
     * Returns if a given string is a valid book avail.
     */
    public static boolean isValidAvail(String test) {
        return test.equals(AVAILABLE)
            || test.equals(BORROWED)
            || test.equals(RESERVED) || test.equals(BORROWED_AND_RESERVED);
    }

    public String getValue() {
        return value;
    }

    //@@author
    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Avail // instanceof handles nulls
            && this.value.equals(((Avail) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
