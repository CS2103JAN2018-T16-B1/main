package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BOOKS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Catalogue;
import seedu.address.model.ReadOnlyCatalogue;

/**
 * Represents a command which can be undone and redone.
 */
public abstract class UndoableCommand extends Command {
    private ReadOnlyCatalogue previousCatalogue;

    protected abstract CommandResult executeUndoableCommand() throws CommandException;

    /**
     * Stores the current state of {@code model#catalogue}.
     */
    private void saveCatalogueSnapshot() {
        requireNonNull(model);
        this.previousCatalogue = new Catalogue(model.getCatalogue());
    }

    /**
     * This method is called before the execution of {@code UndoableCommand}.
     * {@code UndoableCommand}s that require this preprocessing step should override this method.
     */
    protected void preprocessUndoableCommand() throws CommandException {}

    /**
     * Reverts the Catalogue to the state before this command
     * was executed and updates the filtered book list to
     * show all books.
     */
    protected final void undo() {
        requireAllNonNull(model, previousCatalogue);
        model.resetData(previousCatalogue);
        model.updateFilteredBookList(PREDICATE_SHOW_ALL_BOOKS);
    }

    /**
     * Executes the command and updates the filtered book
     * list to show all books.
     */
    protected final void redo() {
        requireNonNull(model);
        try {
            executeUndoableCommand();
        } catch (CommandException ce) {
            throw new AssertionError("The command has been successfully executed previously; "
                    + "it should not fail now");
        }
        model.updateFilteredBookList(PREDICATE_SHOW_ALL_BOOKS);
    }

    @Override
    public final CommandResult execute() throws CommandException {
        saveCatalogueSnapshot();
        preprocessUndoableCommand();
        return executeUndoableCommand();
    }
}
