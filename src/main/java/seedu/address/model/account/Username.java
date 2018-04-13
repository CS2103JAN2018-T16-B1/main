//@@author QiuHaohao
package seedu.address.model.account;

import java.io.Serializable;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the username of an account
 */
public class Username implements Serializable {

    public static final String MESSAGE_USERNAME_CONSTRAINTS =
        "Username should be at least 5 characters long.";
    public static final String USERNAME_VALIDATION_REGEX = "\\w{5,}";

    private final String username;

    /**
     * Constructs a Username
     *
     * @param username
     */
    public Username(String username) {
        requireNonNull(username);
        checkArgument(isValidUsername(username), MESSAGE_USERNAME_CONSTRAINTS);

        this.username = username;
    }

    /**
     * Returns true if a given string is a valid Username.
     */
    public static boolean isValidUsername(String test) {
        return test.matches(USERNAME_VALIDATION_REGEX);
    }


    /**
     * Returns username.
     */
    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof Username // short circuit if same obj
            && this.username.equals(((Username) other).username) // check username
        );
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public String toString() {
        return username;
    }
}
