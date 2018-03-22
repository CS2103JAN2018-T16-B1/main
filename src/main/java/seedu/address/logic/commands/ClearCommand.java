package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Catalogue;

/**
 * Clears the catalogue.
 */
public class ClearCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Catalogue has been cleared!";


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        model.resetData(new Catalogue());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
