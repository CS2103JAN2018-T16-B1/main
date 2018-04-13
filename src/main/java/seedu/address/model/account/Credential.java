//@@author QiuHaohao
package seedu.address.model.account;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a set of username and password
 */
public class Credential implements Serializable {

    private Username username;
    private Password password;

    /**
     * Constructs a {@code Credential}
     *
     * @param username A valid username
     * @param password A valid password
     */
    public Credential(String username, String password) {
        this.username = new Username(username);
        this.password = new Password(password);
    }

    /**
     * Returns username
     */
    public Username getUsername() {
        return username;
    }

    /**
     * Returns password
     */
    public Password getPassword() {
        return password;
    }

    /**
     * Returns true if the username provided equals to this.username
     *
     * @param username
     * @return
     */
    public boolean usernameEquals(Username username) {
        return this.username.equals(username);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof Credential // short circuit if same obj
            && this.username.equals(((Credential) other).username) // check username
            && this.password.equals(((Credential) other).password) //check password
        );
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(username, password);
    }

    @Override
    public String toString() {
        return "Credential{"
            + "username=" + username
            + ", password=" + password
            + '}';
    }
}

