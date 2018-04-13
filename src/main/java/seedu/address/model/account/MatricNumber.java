//@@author QiuHaohao
package seedu.address.model.account;

import java.io.Serializable;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a set of username and password
 */
public class MatricNumber implements Serializable {
    public static final String MESSAGE_MATRIC_NUMBER_CONSTRAINTS =
        "Matriculation number should start with \"A\", followed by 7 digits and end with uppercase letter.";

    public static final String MATRIC_NUMBER_VALIDATION_REGEX = "A[0-9]{7}[A-Z]";


    private final String matricNumber;

    /**
     * Constructs a {@code Credential}
     *
     * @param matricNumber A valid matric number
     */
    public MatricNumber(String matricNumber) {
        requireNonNull(matricNumber);
        checkArgument(isValidMatricNumber(matricNumber), MESSAGE_MATRIC_NUMBER_CONSTRAINTS);
        this.matricNumber = matricNumber;
    }

    /**
     * Returns true if a given string is a valid MatricNumber.
     */
    public static boolean isValidMatricNumber(String test) {
        return test.matches(MATRIC_NUMBER_VALIDATION_REGEX);
    }

    /**
     * Returns MatricNumber.
     */
    public String getMatricNumber() {
        return matricNumber;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof MatricNumber // short circuit if same obj
            && this.getMatricNumber().equals(((MatricNumber) other).getMatricNumber()) //check status
        );
    }

    @Override
    public String toString() {
        return matricNumber;
    }

    @Override
    public int hashCode() {
        return matricNumber.hashCode();
    }
}
