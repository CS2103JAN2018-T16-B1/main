package seedu.address.logic.commands;

import seedu.address.model.Catalogue;
import seedu.address.model.Model;
import seedu.address.model.account.PrivilegeLevel;

import static java.util.Objects.requireNonNull;

/**
 * Clears the catalogue.
 */
public class ClearCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Catalogue has been cleared!";
    public static final PrivilegeLevel PRIVILEGE_LEVEL = Model.PRIVILEGE_LEVEL_LIBRARIAN;

    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        model.resetData(new Catalogue());
        return new CommandResult(MESSAGE_SUCCESS);
    }

    //@@author QiuHaohao
    @Override
    public PrivilegeLevel getPrivilegeLevel() {
        return PRIVILEGE_LEVEL;
    }
}
