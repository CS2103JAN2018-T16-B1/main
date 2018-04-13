package seedu.address.testutil;

import seedu.address.model.account.Account;
import seedu.address.model.account.Credential;
import seedu.address.model.account.MatricNumber;
import seedu.address.model.account.Name;
import seedu.address.model.account.PrivilegeLevel;

/**
 * A utility class to help with building Account objects.
 */
public class AccountBuilder {

    public static final String DEFAULT_NAME = "Victor Tardieu";
    public static final String DEFAULT_MATRIC_NUMBER = "A1234567N";
    public static final String DEFAULT_PRIVILEGE_LEVEL = "2";
    public static final String DEFAULT_USERNAME = "victor";
    public static final String DEFAULT_PASSWORD = "victor123";

    private Name name;
    private Credential credential;
    private MatricNumber matricNumber;
    private PrivilegeLevel privilegeLevel;

    /**
     * Constructor for account with default values
     */
    public AccountBuilder() {
        name = new Name(DEFAULT_NAME);
        credential = new Credential(DEFAULT_USERNAME, DEFAULT_PASSWORD);
        matricNumber = new MatricNumber(DEFAULT_MATRIC_NUMBER);
        privilegeLevel = new PrivilegeLevel(Integer.parseInt(DEFAULT_PRIVILEGE_LEVEL));
    }

    /**
     * Sets the {@code Name} of the {@code Account} that we are building.
     *
     * @param name
     * @return
     */
    public AccountBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Credential} of the {@code Account} that we are building.
     *
     * @param username
     * @param password
     * @return
     */
    public AccountBuilder withCredential(String username, String password) {
        this.credential = new Credential(username, password);
        return this;
    }

    /**
     * Sets the {@code MatricNumber} of the {@code Account} that we are building.
     *
     * @param matricNumber
     * @return
     */
    public AccountBuilder withMatricNumber(String matricNumber) {
        this.matricNumber = new MatricNumber(matricNumber);
        return this;
    }

    /**
     * Sets the {@code PrivilegeLevel} of the {@code Account} that we are building.
     *
     * @param privilegeLevel
     * @return
     */
    public AccountBuilder withPrivilegeLevel(String privilegeLevel) {
        this.privilegeLevel = new PrivilegeLevel(Integer.parseInt(privilegeLevel));
        return this;
    }

    /**
     * Create an account
     *
     * @return
     */
    public Account build() {
        return new Account(name, credential, matricNumber, privilegeLevel);
    }
}
