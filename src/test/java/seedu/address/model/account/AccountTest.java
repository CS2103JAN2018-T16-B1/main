//@@author QiuHaohao
package seedu.address.model.account;

import org.junit.Test;
import seedu.address.testutil.Assert;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AccountTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, ()
            -> new Account(null, null, null, null));
        Assert.assertThrows(NullPointerException.class, ()
            -> new Account(new Name("dummy"), null, null, null));
        Assert.assertThrows(NullPointerException.class, ()
            -> new Account(null, new Credential("dummy", "dummy"), null, null));
        Assert.assertThrows(NullPointerException.class, ()
            -> new Account(null, null, new MatricNumber("A1231231A"), null));
        Assert.assertThrows(NullPointerException.class, ()
            -> new Account(null, null, null, new PrivilegeLevel(0)));
    }

    @Test
    public void credentialMatchesTest() {
        Credential studentCredential = new Credential("student", "student");
        Credential adminCredential = new Credential("admin", "admin");
        Account studentAccount = Account.createDefaultStudentAccount();
        Account adminAccount = Account.createDefaultAdminAccount();
        assertTrue(studentAccount.credentialMatches(studentCredential));
        assertTrue(adminAccount.credentialMatches(adminCredential));
        assertFalse(studentAccount.credentialMatches(adminCredential));
        assertFalse(adminAccount.credentialMatches(studentCredential));
    }

    @Test
    public void equalsTest() {
        Account studentAccount = Account.createDefaultStudentAccount();
        Account studentAccountCopy = Account.createDefaultStudentAccount();
        Account adminAccount = Account.createDefaultAdminAccount();

        assertTrue(studentAccount.equals(studentAccount));
        assertTrue(studentAccount.equals(studentAccountCopy));
        assertFalse(studentAccount.equals(adminAccount));
        assertFalse(studentAccount.equals(null));
        assertFalse(studentAccount.equals(0));
    }

    @Test
    public void usernameMatches() {
        Name name = new Name("Ryan");
        Credential credential = new Credential("student", "student2");
        MatricNumber matricNumber = new MatricNumber("A0123256X");
        PrivilegeLevel privilegeLevel = new PrivilegeLevel(1);
        Account student2 = new Account(name, credential, matricNumber, privilegeLevel);
        Account student = Account.createDefaultStudentAccount();
        Account admin = Account.createDefaultAdminAccount();

        assertTrue(student2.usernameMatches(student));
        assertFalse(student2.usernameMatches(admin));
    }
}
