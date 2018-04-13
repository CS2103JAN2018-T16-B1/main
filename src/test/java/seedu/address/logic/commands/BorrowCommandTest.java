package seedu.address.logic.commands;

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

import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.logic.commands.CommandTestUtil.showBookAtIndex;
import static seedu.address.model.book.Avail.BORROWED;
import static seedu.address.testutil.TypicalBooks.getTypicalCatalogue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_BOOK;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code BorrowCommand}.
 */
public class BorrowCommandTest {

    public Model model = new ModelManager(getTypicalCatalogue(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Book bookToBorrow = model.getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased());
        Book borrowedBook = createBorrowedBook(bookToBorrow);
        BorrowCommand borrowCommand = prepareCommand(INDEX_FIRST_BOOK);

        String expectedMessage = String.format(BorrowCommand.MESSAGE_BORROW_BOOK_SUCCESS, bookToBorrow);

        ModelManager expectedModel = new ModelManager(model.getCatalogue(), new UserPrefs());
        expectedModel.borrowBook(bookToBorrow, borrowedBook);

        assertCommandSuccess(borrowCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBookList().size() + 1);
        BorrowCommand borrowCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(borrowCommand, model, Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showBookAtIndex(model, INDEX_FIRST_BOOK);

        Index outOfBoundIndex = INDEX_SECOND_BOOK;
        // ensures that outOfBoundIndex is still in bounds of catalogue list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getCatalogue().getBookList().size());

        BorrowCommand borrowCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(borrowCommand, model, Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Book bookToBorrow = model.getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased());
        Book borrowedBook = createBorrowedBook(bookToBorrow);
        BorrowCommand borrowCommand = prepareCommand(INDEX_FIRST_BOOK);
        Model expectedModel = new ModelManager(model.getCatalogue(), new UserPrefs());

        // borrow -> first book borrow
        borrowCommand.execute();
        undoRedoStack.push(borrowCommand);

        // undo -> reverts catalogue back to previous state and filtered book list to show all books
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first book deleted again
        expectedModel.borrowBook(bookToBorrow, borrowedBook);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBookList().size() + 1);
        BorrowCommand borrowCommand = prepareCommand(outOfBoundIndex);

        // execution failed -> deleteCommand not pushed into undoRedoStack
        assertCommandFailure(borrowCommand, model, Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Borrows a {@code Book} from a filtered list.
     * 2. Undo the borrowing.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously borrowed book in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the borrowing. This ensures {@code RedoCommand} borrow the book object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameBookDeleted() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        BorrowCommand borrowCommand = prepareCommand(INDEX_FIRST_BOOK);
        Model expectedModel = new ModelManager(model.getCatalogue(), new UserPrefs());

        showBookAtIndex(model, INDEX_SECOND_BOOK);
        Book bookToBorrow = model.getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased());
        Book borrowedBook = createBorrowedBook(bookToBorrow);
        // borrow -> deletes second book in unfiltered book list / first book in filtered book list
        borrowCommand.execute();
        undoRedoStack.push(borrowCommand);

        // undo -> reverts catalogue back to previous state and filtered book list to show all books
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.borrowBook(bookToBorrow, borrowedBook);
        assertNotEquals(bookToBorrow, model.getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased()));
        // redo -> borrows same second book in unfiltered book list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        BorrowCommand borrowFirstCommand = prepareCommand(INDEX_FIRST_BOOK);
        BorrowCommand borrowSecondCommand = prepareCommand(INDEX_SECOND_BOOK);

        // same object -> returns true
        assertTrue(borrowFirstCommand.equals(borrowFirstCommand));

        // same values -> returns true
        BorrowCommand borrowFirstCommandCopy = prepareCommand(INDEX_FIRST_BOOK);
        assertTrue(borrowFirstCommand.equals(borrowFirstCommandCopy));

        // one command preprocessed when previously equal -> returns false
        borrowFirstCommandCopy.preprocessUndoableCommand();
        assertFalse(borrowFirstCommand.equals(borrowFirstCommandCopy));

        // different types -> returns false
        assertFalse(borrowFirstCommand.equals(1));

        // null -> returns false
        assertFalse(borrowFirstCommand.equals(null));

        // different book -> returns false
        assertFalse(borrowFirstCommand.equals(borrowSecondCommand));
    }

    /**
     * Returns a {@code BorrowCommand} with the parameter {@code index}.
     */
    private BorrowCommand prepareCommand(Index index) {
        BorrowCommand borrowCommand = new BorrowCommand(index);
        borrowCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return borrowCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoBook(Model model) {
        model.updateFilteredBookList(p -> false);

        assertTrue(model.getFilteredBookList().isEmpty());
    }

    public Book createBorrowedBook(Book bookToBorrow) {
        assert bookToBorrow != null;

        Title updatedTitle = bookToBorrow.getTitle();
        Isbn updatedIsbn = bookToBorrow.getIsbn();
        Avail updatedAvail = new Avail(BORROWED);
        Author updatedAuthor = bookToBorrow.getAuthor();
        Set<Tag> updatedTags = bookToBorrow.getTags();

        return new Book(updatedTitle, updatedAuthor, updatedIsbn, updatedAvail, updatedTags);
    }

}
