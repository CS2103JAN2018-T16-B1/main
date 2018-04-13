package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAccounts.getTypicalAccountList;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.account.exceptions.DuplicateAccountException;

public class ClearAccountCommandTest {

    @Test
    public void execute_empty_accountList() throws DuplicateAccountException {
        Model model = new ModelManager();
        assertCommandSuccess(prepareCommand(model), model, ClearAccountCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_nonEmptyAccountList_success() throws DuplicateAccountException {
        Model model = new ModelManager();
        model = getTypicalAccountList();
        assertCommandSuccess(prepareCommand(model), model, ClearAccountCommand.MESSAGE_SUCCESS,
            model);
    }

    private ClearAccountCommand prepareCommand(Model model) throws DuplicateAccountException {
        ClearAccountCommand command = new ClearAccountCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
