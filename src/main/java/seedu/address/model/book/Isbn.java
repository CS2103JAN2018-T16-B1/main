//@@author khiayi
package seedu.address.model.book;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Book's isbn number in the catalogue.
 * Guarantees: immutable; is valid as declared in {@link #isValidIsbn(String)}
 */
public class Isbn {

    public static final String MESSAGE_ISBN_CONSTRAINTS =
        "Isbn numbers can only contain numbers, and should be at 13 digits long";
    public static final String ISBN_VALIDATION_REGEX = "\\d{13}";
    public final String value;

    /**
     * Constructs a {@code Isbn}.
     *
     * @param isbn A valid isbn number.
     */
    public Isbn(String isbn) {
        requireNonNull(isbn);
        checkArgument(isValidIsbn(isbn), MESSAGE_ISBN_CONSTRAINTS);
        this.value = isbn;
    }
    //@@author
    /**
     * Returns true if a given string is a valid book isbn number.
     */
    public static boolean isValidIsbn(String test) {
        return test.matches(ISBN_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Isbn // instanceof handles nulls
            && this.value.equals(((Isbn) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
