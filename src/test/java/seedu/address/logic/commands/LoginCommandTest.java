//@@author QiuHaohao
package seedu.address.logic.commands;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.account.Account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LoginCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void equals() {
        LoginCommand loginStudentCommand = new LoginCommand("student", "student");
        LoginCommand loginAdminCommand = new LoginCommand("admin", "admin");

        // same object -> returns true
        assertTrue(loginStudentCommand.equals(loginStudentCommand));

        // same values -> returns true
        LoginCommand loginStudentCommandCopy = new LoginCommand("student", "student");
        assertTrue(loginStudentCommand.equals(loginStudentCommandCopy));

        // different types -> returns false
        assertFalse(loginStudentCommand.equals(1));

        // null -> returns false
        assertFalse(loginStudentCommand.equals(null));

        // different person -> returns false
        assertFalse(loginStudentCommand.equals(loginAdminCommand));
    }

    @Test
    public void constructor_nullUsername_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new LoginCommand(null, "admin");
    }

    @Test
    public void constructor_nullPassword_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new LoginCommand("admin", null);
    }

    @Test
    public void execute_loginAsStudent_loginSuccessful() throws Exception {
        Model model = new ModelManager();
        Account student = Account.createDefaultStudentAccount();
        model.addAccount(student);
        LoginCommand studentLogin = new LoginCommand("student", "student");
        studentLogin.setData(model, null, null);
        CommandResult commandResult = studentLogin.execute();

        assertEquals(LoginCommand.MESSAGE_LOGGED_IN_AS_STUTENT, commandResult.feedbackToUser);
        assertEquals(model.getPrivilegeLevel(), Model.PRIVILEGE_LEVEL_STUDENT);
    }

    @Test
    public void execute_loginAsLibrarian_loginSuccessful() {
        Model model = new ModelManager();
        LoginCommand studentLogin = new LoginCommand("admin", "admin");
        studentLogin.setData(model, null, null);
        CommandResult commandResult = studentLogin.execute();

        assertEquals(LoginCommand.MESSAGE_LOGGED_IN_AS_LIBRARIAN, commandResult.feedbackToUser);
        assertEquals(model.getPrivilegeLevel(), Model.PRIVILEGE_LEVEL_LIBRARIAN);
    }
}
