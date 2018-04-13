//@@author QiuHaohao
package seedu.address.logic.commands;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

//import static org.junit.Assert.assertEquals;
//import org.junit.Rule;
//import org.junit.rules.ExpectedException;
//import seedu.address.model.ModelManager;
//import seedu.address.model.Model;


public class LogoutCommandTest {
    @Test
    public void equals() {
        LogoutCommand logoutCommand1 = new LogoutCommand();
        LogoutCommand logoutCommand2 = new LogoutCommand();

        // same object -> returns true
        assertTrue(logoutCommand1.equals(logoutCommand1));

        // same values -> returns true
        assertTrue(logoutCommand1.equals(logoutCommand2));

        // different types -> returns false
        assertFalse(logoutCommand1.equals(1));

        // null -> returns false
        assertFalse(logoutCommand1.equals(null));
    }
}
