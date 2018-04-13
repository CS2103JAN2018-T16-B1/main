//@@author QiuHaohao
package seedu.address.model.account;

import org.junit.Test;
import seedu.address.model.account.exceptions.DuplicateAccountException;
import seedu.address.testutil.Assert;

import static org.junit.Assert.assertEquals;

public class UniqueAccountListTest {
    @Test
    public void add() throws DuplicateAccountException {
        UniqueAccountList accountList = new UniqueAccountList();

        Name name = new Name("Ryan");
        Credential credential = new Credential("student", "student2");
        MatricNumber matricNumber = new MatricNumber("A0123256X");
        PrivilegeLevel privilegeLevel = new PrivilegeLevel(1);

        Account student2 = new Account(name, credential, matricNumber, privilegeLevel);
        Account student = Account.createDefaultStudentAccount();
        Account admin = Account.createDefaultAdminAccount();

        accountList.add(student);
        accountList.add(admin);

        Assert.assertThrows(DuplicateAccountException.class, ()
            -> accountList.add(student2));
    }

    @Test
    public void searchByUsername() throws DuplicateAccountException {
        UniqueAccountList accountList = new UniqueAccountList();
        Account student = Account.createDefaultStudentAccount();
        Account admin = Account.createDefaultAdminAccount();

        accountList.add(student);
        accountList.add(admin);

        assertEquals(accountList.searchByUsername(new Username("student")), student);
    }
}
