package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAccounts.getTypicalAccountList;
import static seedu.address.testutil.TypicalAccounts.getTypicalAccounts;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.account.Account;
import seedu.address.testutil.AccountBuilder;

public class AddAccountCommandIntegrationTest {
    private Model model;

    @Before
    public void setUp() {
        model = getTypicalAccountList();
    }

    @Test
    public void execute_newAccount_success() throws Exception {
        Account validAccount = new AccountBuilder().build();

        Model expectedModel = new ModelManager(model.getCatalogue(), new UserPrefs());
        expectedModel.addAccount(validAccount);

        assertCommandSuccess(prepareCommand(validAccount, model), model,
            String.format(AddAccountCommand.MESSAGE_SUCCESS, validAccount), expectedModel);
    }

    @Test
    public void execute_duplicateAccount_throwsCommandException() {
        Account accountInList = getTypicalAccounts().get(0);
        assertCommandFailure(prepareCommand(accountInList, model), model, AddAccountCommand.MESSAGE_DUPLICATE_ACCOUNT);
    }

    /**
     * Generates a new {@code AddAccountCommand} which upon execution, adds {@code account} into the {@code model}.
     */
    private AddAccountCommand prepareCommand(Account account, Model model) {
        AddAccountCommand command = new AddAccountCommand(account);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
