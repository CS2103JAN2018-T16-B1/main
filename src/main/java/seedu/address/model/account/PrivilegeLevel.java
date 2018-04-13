//@@author QiuHaohao
package seedu.address.model.account;

import java.io.Serializable;
import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the privilegeLevel of an account/a command
 */
public class PrivilegeLevel implements Comparable<PrivilegeLevel>, Serializable {

    public static final int PRIVILEGE_LEVEL_GUEST = 0;
    public static final int PRIVILEGE_LEVEL_STUDENT = 1;
    public static final int PRIVILEGE_LEVEL_LIBRARIAN = 2;
    public static final String MESSAGE_PRIVILEGE_LEVEL_CONSTRAINTS =
        "Privilege Level should be an integer from 0 to 2 inclusive.";
    private final int privilegeLevel;

    /**
     * Constructs a PrivilegeLevel
     *
     * @param privilegeLevel
     */
    public PrivilegeLevel(int privilegeLevel) {
        requireNonNull(privilegeLevel);
        checkArgument(isValidPrivilegeLevel(privilegeLevel), MESSAGE_PRIVILEGE_LEVEL_CONSTRAINTS);
        this.privilegeLevel = privilegeLevel;
    }

    /**
     * Returns true if a given string is a valid PrivilegeLevel
     */
    public static boolean isValidPrivilegeLevel(int test) {
        return test >= PRIVILEGE_LEVEL_GUEST
            && test <= PRIVILEGE_LEVEL_LIBRARIAN;
    }

    public int getPrivilegeLevel() {
        return privilegeLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PrivilegeLevel that = (PrivilegeLevel) o;
        return privilegeLevel == that.privilegeLevel;
    }

    @Override
    public int hashCode() {

        return Objects.hash(privilegeLevel);
    }


    @Override
    public int compareTo(PrivilegeLevel o) {
        return this.privilegeLevel - o.privilegeLevel;
    }
}
