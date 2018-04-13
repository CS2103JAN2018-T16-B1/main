//@@author QiuHaohao
package seedu.address.model.account;

import java.io.Serializable;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * Represents an account in the accountBook
 */
public class Account implements Serializable {
    private final Name name;
    private final Credential credential;
    private final MatricNumber matricNumber;
    private final PrivilegeLevel privilegeLevel;

    /**
     * Constructs an Account
     *
     * @param name
     * @param credential
     * @param matricNumber
     * @param privilegeLevel
     */
    public Account(Name name, Credential credential, MatricNumber matricNumber, PrivilegeLevel privilegeLevel) {
        requireNonNull(name);
        requireNonNull(credential);
        requireNonNull(matricNumber);
        requireNonNull(privilegeLevel);
        this.name = name;
        this.credential = credential;
        this.matricNumber = matricNumber;
        this.privilegeLevel = privilegeLevel;
    }

    /**
     * Returns a sample guest account
     *
     * @return
     */
    public static final Account createGuestAccount() {
        Name name = new Name("Guest");
        Credential credential = new Credential("Guest", "Guest");
        MatricNumber matricNumber = new MatricNumber("A0000000X");
        PrivilegeLevel privilegeLevel = new PrivilegeLevel(0);
        Account guest = new Account(name, credential, matricNumber, privilegeLevel);
        return guest;
    }

    /**
     * Returns a sample admin account
     *
     * @return
     */
    public static final Account createDefaultAdminAccount() {
        Name name = new Name("Alice");
        Credential credential = new Credential("admin", "admin");
        MatricNumber matricNumber = new MatricNumber("A0123456X");
        PrivilegeLevel privilegeLevel = new PrivilegeLevel(2);
        Account admin = new Account(name, credential, matricNumber, privilegeLevel);
        return admin;
    }

    /**
     * Returns a sample student account
     *
     * @return
     */
    public static final Account createDefaultStudentAccount() {
        Name name = new Name("Bob");
        Credential credential = new Credential("student", "student");
        MatricNumber matricNumber = new MatricNumber("A0123456X");
        PrivilegeLevel privilegeLevel = new PrivilegeLevel(1);
        Account student = new Account(name, credential, matricNumber, privilegeLevel);
        return student;
    }

    /**
     * Returns the name of the account
     *
     * @return
     */
    public Name getName() {
        return name;
    }

    /**
     * Returns the credential
     *
     * @return
     */
    public Credential getCredential() {
        return credential;
    }

    /**
     * Returns the MatricNumber
     *
     * @return
     */
    public MatricNumber getMatricNumber() {
        return matricNumber;
    }

    /**
     * Returns the privilegeLevel of this account
     *
     * @return
     */
    public PrivilegeLevel getPrivilegeLevel() {
        return privilegeLevel;
    }

    /**
     * Returns a boolean indicating whether a given credential matches with that of this account
     *
     * @param c
     * @return
     */
    public boolean credentialMatches(Credential c) {
        return c.equals(this.credential);
    }

    /**
     * Returns true if this account's username is the same as the username provided
     *
     * @param username
     * @return
     */
    public boolean usernameMatches(Username username) {
        return this.credential.usernameEquals(username);
    }

    /**
     * Returns true if this account's username is the same as that of the credential provided
     *
     * @param c
     * @return
     */
    public boolean usernameMatches(Credential c) {
        return usernameMatches(c.getUsername());
    }

    /**
     * Returns true if this account's username is the same as that of the account provided
     *
     * @param a
     * @return
     */
    public boolean usernameMatches(Account a) {
        return usernameMatches(a.getCredential());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Account account = (Account) o;
        return Objects.equals(name, account.name)
            && Objects.equals(credential, account.credential)
            && Objects.equals(matricNumber, account.matricNumber)
            && Objects.equals(privilegeLevel, account.privilegeLevel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, credential, matricNumber, privilegeLevel);
    }

    @Override
    public String toString() {
        return this.credential.getUsername().toString();
    }
}
