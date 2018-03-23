package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstBook;
import static seedu.address.logic.commands.CommandTestUtil.showBookAtIndex;
import static seedu.address.testutil.TypicalBooks.getTypicalCatalogue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;

import org.junit.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.Book;
import seedu.address.model.book.exceptions.BookNotFoundException;

public class UndoableCommandTest {
    private final Model model = new ModelManager(getTypicalCatalogue(), new UserPrefs());
    private final DummyCommand dummyCommand = new DummyCommand(model);

    private Model expectedModel = new ModelManager(getTypicalCatalogue(), new UserPrefs());

    @Test
    public void executeUndo() throws Exception {
        dummyCommand.execute();
        deleteFirstBook(expectedModel);
        assertEquals(expectedModel, model);

        showBookAtIndex(model, INDEX_FIRST_BOOK);

        // undo() should cause the model's filtered list to show all books
        dummyCommand.undo();
        expectedModel = new ModelManager(getTypicalCatalogue(), new UserPrefs());
        assertEquals(expectedModel, model);
    }

    @Test
    public void redo() {
        showBookAtIndex(model, INDEX_FIRST_BOOK);

        // redo() should cause the model's filtered list to show all books
        dummyCommand.redo();
        deleteFirstBook(expectedModel);
        assertEquals(expectedModel, model);
    }

    /**
     * Deletes the first book in the model's filtered list.
     */
    class DummyCommand extends UndoableCommand {
        DummyCommand(Model model) {
            this.model = model;
        }

        @Override
        public CommandResult executeUndoableCommand() {
            Book bookToDelete = model.getFilteredBookList().get(0);
            try {
                model.deleteBook(bookToDelete);
            } catch (BookNotFoundException pnfe) {
                fail("Impossible: bookToDelete was retrieved from model.");
            }
            return new CommandResult("");
        }
    }
}
