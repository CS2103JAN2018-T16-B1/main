package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.account.Account;
import seedu.address.model.account.exceptions.DuplicateAccountException;

/**
 * A utility class containing a list of {@code Account} objects to be used in tests.
 */
public class TypicalAccounts {

    public static final Account HARRY = new AccountBuilder()
        .withName("Harry Potter")
        .withCredential("harry123", "harry123")
        .withMatricNumber("A1234567H")
        .withPrivilegeLevel("1").build();

    public static final Account JERRY = new AccountBuilder()
        .withName("Jerry Morgan ")
        .withCredential("jerry123", "jack123")
        .withMatricNumber("A1234567J")
        .withPrivilegeLevel("2").build();

    public static final Account TOM = new AccountBuilder()
        .withName("Tom Hanks")
        .withCredential("tom123", "tom123")
        .withMatricNumber("A1234567T")
        .withPrivilegeLevel("1").build();

    public static final Account EMMA = new AccountBuilder()
        .withName("Emma Thorne")
        .withCredential("emma123", "emma123")
        .withMatricNumber("A1234567E")
        .withPrivilegeLevel("2").build();

    public static final Account LARY = new AccountBuilder()
        .withName("Lary Knot")
        .withCredential("lary123", "lary123")
        .withMatricNumber("A1234567L")
        .withPrivilegeLevel("1").build();

    public static final Account MARIE = new AccountBuilder()
        .withName("Marie Johnson")
        .withCredential("marie", "marie123")
        .withMatricNumber("A1234567M")
        .withPrivilegeLevel("1").build();

    public static final Account NICOLE = new AccountBuilder()
        .withName("Nicole Soley")
        .withCredential("nicole", "nicole123")
        .withMatricNumber("A1234567N")
        .withPrivilegeLevel("1").build();

    private TypicalAccounts() {
    } // prevents instantiation

    public static Model getTypicalAccountList() {
        Model model = new ModelManager();
        for (Account account : getTypicalAccounts()) {
            try {
                model.addAccount(account);
            } catch (DuplicateAccountException e) {
                throw new AssertionError("not possible");
            }
        }
        return model;
    }


    public static List<Account> getTypicalAccounts() {
        return new ArrayList<>(Arrays.asList(HARRY, JERRY, TOM, EMMA, LARY, MARIE, NICOLE));
    }
}
