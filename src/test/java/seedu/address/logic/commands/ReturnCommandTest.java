package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.logic.commands.CommandTestUtil.showBookAtIndex;
import static seedu.address.testutil.TypicalBooks.getTypicalCatalogue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_BOOK;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.Book;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code ReturnCommand}.
 */
public class ReturnCommandTest {

    private Model model = new ModelManager(getTypicalCatalogue(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Book bookToReturn = model.getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased());
        ReturnCommand returnCommand = prepareCommand(INDEX_FIRST_BOOK);

        String expectedMessage = String.format(ReturnCommand.MESSAGE_RETURN_BOOK_SUCCESS, bookToReturn);
        ModelManager expectedModel = new ModelManager(model.getCatalogue(), new UserPrefs());
        expectedModel.returnBook(bookToReturn);
        assertCommandSuccess(returnCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBookList().size() + 1);
        ReturnCommand returnCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(returnCommand, model, Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showBookAtIndex(model, INDEX_FIRST_BOOK);

        Book bookToReturn = model.getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased());
        ReturnCommand returnCommand = prepareCommand(INDEX_FIRST_BOOK);

        String expectedMessage = String.format(ReturnCommand.MESSAGE_RETURN_BOOK_SUCCESS, bookToReturn);

        Model expectedModel = new ModelManager(model.getCatalogue(), new UserPrefs());
        expectedModel.returnBook(bookToReturn);

        assertCommandSuccess(returnCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showBookAtIndex(model, INDEX_FIRST_BOOK);

        Index outOfBoundIndex = INDEX_SECOND_BOOK;
        // ensures that outOfBoundIndex is still in bounds of catalogue list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getCatalogue().getBookList().size());

        ReturnCommand returnCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(returnCommand, model, Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Book bookToReturn = model.getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased());
        ReturnCommand returnCommand = prepareCommand(INDEX_FIRST_BOOK);
        Model expectedModel = new ModelManager(model.getCatalogue(), new UserPrefs());

        // return -> first book returned
        returnCommand.execute();
        undoRedoStack.push(returnCommand);

        // undo -> reverts catalogue back to previous state and filtered book list to show all books
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first book returned again
        expectedModel.returnBook(bookToReturn);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBookList().size() + 1);
        ReturnCommand returnCommand = prepareCommand(outOfBoundIndex);

        // execution failed -> returnCommand not pushed into undoRedoStack
        assertCommandFailure(returnCommand, model, Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Returns a {@code Book} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted book in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} returns the book object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameBookReturned() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        ReturnCommand returnCommand = prepareCommand(INDEX_FIRST_BOOK);
        Model expectedModel = new ModelManager(model.getCatalogue(), new UserPrefs());

        showBookAtIndex(model, INDEX_SECOND_BOOK);
        Book bookToReturn = model.getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased());
        // return -> returns second book in unfiltered book list / first book in filtered book list
        returnCommand.execute();
        undoRedoStack.push(returnCommand);

        // undo -> reverts catalogue back to previous state and filtered book list to show all books
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.returnBook(bookToReturn);
        assertNotEquals(bookToReturn, model.getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased()));
        // redo -> returns same second book in unfiltered book list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        ReturnCommand returnFirstCommand = prepareCommand(INDEX_FIRST_BOOK);
        ReturnCommand returnSecondCommand = prepareCommand(INDEX_SECOND_BOOK);

        // same object -> returns true
        assertTrue(returnFirstCommand.equals(returnFirstCommand));

        // same values -> returns true
        ReturnCommand returnFirstCommandCopy = prepareCommand(INDEX_FIRST_BOOK);
        assertTrue(returnFirstCommand.equals(returnFirstCommandCopy));

        // one command preprocessed when previously equal -> returns false
        returnFirstCommandCopy.preprocessUndoableCommand();
        assertFalse(returnFirstCommand.equals(returnFirstCommandCopy));

        // different types -> returns false
        assertFalse(returnFirstCommand.equals(1));

        // null -> returns false
        assertFalse(returnFirstCommand.equals(null));

        // different book -> returns false
        assertFalse(returnFirstCommand.equals(returnSecondCommand));
    }

    /**
     * Returns a {@code ReturnCommand} with the parameter {@code index}.
     */
    private ReturnCommand prepareCommand(Index index) {
        ReturnCommand returnCommand = new ReturnCommand(index);
        returnCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return returnCommand;
    }
}
