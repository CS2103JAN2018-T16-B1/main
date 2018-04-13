package seedu.address.logic.commands;

import static org.junit.Assert.*;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.logic.commands.CommandTestUtil.showBookAtIndex;
import static seedu.address.model.book.Avail.RESERVED;
import static seedu.address.testutil.TypicalBooks.getTypicalCatalogue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_BOOK;

import java.util.Set;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.Author;
import seedu.address.model.book.Avail;
import seedu.address.model.book.Book;
import seedu.address.model.book.Isbn;
import seedu.address.model.book.Title;
import seedu.address.model.tag.Tag;

public class ReserveCommandTest {

    public Model model = new ModelManager(getTypicalCatalogue(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ModelManager expectedModel = new ModelManager(model.getCatalogue(), new UserPrefs());
        Book bookToReserve = model.getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased());
        Book reservedBook = createReservedBook(bookToReserve);
        ReserveCommand reserveCommand = prepareCommand(INDEX_FIRST_BOOK);
        expectedModel.reserveBook(bookToReserve, reservedBook);
        assertCommandSuccess(reserveCommand, model, String.format(ReserveCommand.MESSAGE_RESERVE_BOOK_SUCCESS, bookToReserve), expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBookList().size() + 1);
        ReserveCommand reserveCommand = prepareCommand(outOfBoundIndex);
        assertCommandFailure(reserveCommand, model, Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showBookAtIndex(model, INDEX_FIRST_BOOK);
        Index outOfBoundIndex = INDEX_SECOND_BOOK;
        // ensures that outOfBoundIndex is still in bounds of catalogue list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getCatalogue().getBookList().size());

        ReserveCommand reserveCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(reserveCommand, model, Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_successV2() throws Exception {
        showBookAtIndex(model, INDEX_FIRST_BOOK);

        Book bookToReserve = model.getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased());
        Book reservedBook = createReservedBook(bookToReserve);
        ReserveCommand reserveCommand = prepareCommand(INDEX_FIRST_BOOK);

        String expectedMessage = String.format(ReserveCommand.MESSAGE_RESERVE_BOOK_SUCCESS, bookToReserve);

        Model expectedModel = new ModelManager(model.getCatalogue(), new UserPrefs());
        expectedModel.reserveBook(bookToReserve, reservedBook);
        showNoBook(expectedModel);

        assertCommandSuccess(reserveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Book bookToReserve = model.getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased());
        Book reservedBook = createReservedBook(bookToReserve);
        ReserveCommand reserveCommand = prepareCommand(INDEX_FIRST_BOOK);
        Model expectedModel = new ModelManager(model.getCatalogue(), new UserPrefs());

        // borrow -> first book reserve
        reserveCommand.execute();
        undoRedoStack.push(reserveCommand);

        // undo -> reverts catalogue back to previous state and filtered book list to show all books
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first book reserved again
        expectedModel.reserveBook(bookToReserve, reservedBook);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBookList().size() + 1);
        ReserveCommand reserveCommand = prepareCommand(outOfBoundIndex);

        // execution failed -> reserveCommand not pushed into undoRedoStack
        assertCommandFailure(reserveCommand, model, Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Reserve a {@code Book} from a filtered list.
     * 2. Undo the reserve.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously reserved book in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the reserve. This ensures {@code RedoCommand} borrow the book object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameBookDeleted() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        ReserveCommand reserveCommand = prepareCommand(INDEX_FIRST_BOOK);
        Model expectedModel = new ModelManager(model.getCatalogue(), new UserPrefs());

        showBookAtIndex(model, INDEX_SECOND_BOOK);
        Book bookToReserve = model.getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased());
        Book reservedBook = createReservedBook(bookToReserve);
        // reserve -> deletes second book in unfiltered book list / first book in filtered book list
        reserveCommand.execute();
        undoRedoStack.push(reserveCommand);

        // undo -> reverts catalogue back to previous state and filtered book list to show all books
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.reserveBook(bookToReserve, reservedBook);
        assertNotEquals(bookToReserve, model.getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased()));
        // redo -> reserves same second book in unfiltered book list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        ReserveCommand reserveFirstCommand = prepareCommand(INDEX_FIRST_BOOK);
        ReserveCommand reserveSecondCommand = prepareCommand(INDEX_SECOND_BOOK);

        // same object -> returns true
        assertTrue(reserveFirstCommand.equals(reserveFirstCommand));

        // same values -> returns true
        ReserveCommand reserveFirstCommandCopy = prepareCommand(INDEX_FIRST_BOOK);
        assertTrue(reserveFirstCommand.equals(reserveFirstCommandCopy));

        // one command preprocessed when previously equal -> returns false
        reserveFirstCommandCopy.preprocessUndoableCommand();
        assertFalse(reserveFirstCommand.equals(reserveFirstCommandCopy));

        // different types -> returns false
        assertFalse(reserveFirstCommand.equals(1));

        // null -> returns false
        assertFalse(reserveFirstCommand.equals(null));

        // different book -> returns false
        assertFalse(reserveFirstCommand.equals(reserveSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoBook(Model model) {
        model.updateFilteredBookList(p -> false);
        assertTrue(model.getFilteredBookList().isEmpty());
    }

    /**
     * Returns a {@code ReserveCommand} with the parameter {@code index}.
     */
    private ReserveCommand prepareCommand(Index index) {
        ReserveCommand reserveCommand = new ReserveCommand(index);
        reserveCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return reserveCommand;
    }

    public Book createReservedBook(Book bookToBorrow) {
        assert bookToBorrow != null;
        Author updatedAuthor = bookToBorrow.getAuthor();
        Avail updatedAvail = new Avail(RESERVED);
        Isbn updatedIsbn = bookToBorrow.getIsbn();
        Title updatedTitle = bookToBorrow.getTitle();
        Set<Tag> updatedTags = bookToBorrow.getTags();
        return new Book(updatedTitle, updatedAuthor, updatedIsbn, updatedAvail, updatedTags);
    }


}