# LeKhangTai
###### /java/seedu/address/logic/parser/BorrowCommandParserTest.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;

import org.junit.Test;

import seedu.address.logic.commands.BorrowCommand;


/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the BorrowCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the BorrowCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class BorrowCommandParserTest {

    private BorrowCommandParser parser = new BorrowCommandParser();

    @Test
    public void parse_validArgs_returnsBorrowCommand() {
        assertParseSuccess(parser, "1", new BorrowCommand(INDEX_FIRST_BOOK));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, BorrowCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/logic/parser/ReturnCommandParserTest.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;

import org.junit.Test;

import seedu.address.logic.commands.ReturnCommand;
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the BorrowCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the BorrowCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class ReturnCommandParserTest {

    private ReturnCommandParser parser = new ReturnCommandParser();

    @Test
    public void parse_validArgs_returnsReturnCommand() {
        assertParseSuccess(parser, "1", new ReturnCommand(INDEX_FIRST_BOOK));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReturnCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/logic/commands/BorrowCommandTest.java
``` java
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





/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code BorrowCommand}.
 */
public class BorrowCommandTest {

    private Model model = new ModelManager(getTypicalCatalogue(), new UserPrefs());

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
        // borrow -> borrows second book in unfiltered book list / first book in filtered book list
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
        BorrowCommand borrowSecondCommand = prepareCommand(INDEX_FIRST_BOOK);

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
        assertTrue(borrowFirstCommand.equals(borrowSecondCommand));
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

    /**
     *
     * @return duplicated book with Borrowed Availability
     */
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
```
###### /java/seedu/address/logic/commands/ReturnCommandTest.java
``` java
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.logic.commands.CommandTestUtil.showBookAtIndex;
import static seedu.address.model.book.Avail.AVAILABLE;
import static seedu.address.testutil.TypicalBooks.getTypicalCatalogue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_BOOK;

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





/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code ReturnCommand}.
 */
public class ReturnCommandTest {

    private Model model = new ModelManager(getTypicalCatalogue(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Book bookToReturn = model.getFilteredBookList().get(INDEX_THIRD_BOOK.getZeroBased());
        Book returnedBook = createReturnedBook(bookToReturn);
        ReturnCommand returnCommand = prepareCommand(INDEX_THIRD_BOOK);

        String expectedMessage = String.format(ReturnCommand.MESSAGE_RETURN_BOOK_SUCCESS, bookToReturn);

        ModelManager expectedModel = new ModelManager(model.getCatalogue(), new UserPrefs());
        expectedModel.returnBook(bookToReturn, returnedBook);

        assertCommandSuccess(returnCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBookList().size() + 1);
        ReturnCommand returnCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(returnCommand, model, Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
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
        Book bookToReturn = model.getFilteredBookList().get(INDEX_THIRD_BOOK.getZeroBased());
        Book returnedBook = createReturnedBook(bookToReturn);
        ReturnCommand returnCommand = prepareCommand(INDEX_THIRD_BOOK);
        Model expectedModel = new ModelManager(model.getCatalogue(), new UserPrefs());

        // return -> first book return
        returnCommand.execute();
        undoRedoStack.push(returnCommand);

        // undo -> reverts catalogue back to previous state and filtered book list to show all books
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first book deleted again
        expectedModel.returnBook(bookToReturn, returnedBook);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBookList().size() + 1);
        ReturnCommand returnCommand = prepareCommand(outOfBoundIndex);

        // execution failed -> deleteCommand not pushed into undoRedoStack
        assertCommandFailure(returnCommand, model, Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
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

    private ReturnCommand prepareCommand(Index index) {
        ReturnCommand returnCommand = new ReturnCommand(index);
        returnCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return returnCommand;
    }

    /**
     *Creates a duplicate book with a changed availability
     *
     */

    public Book createReturnedBook(Book bookToBorrow) {
        assert bookToBorrow != null;

        Title updatedTitle = bookToBorrow.getTitle();
        Isbn updatedIsbn = bookToBorrow.getIsbn();
        Avail updatedAvail = new Avail(AVAILABLE);
        Author updatedAuthor = bookToBorrow.getAuthor();
        Set<Tag> updatedTags = bookToBorrow.getTags();

        return new Book(updatedTitle, updatedAuthor, updatedIsbn, updatedAvail, updatedTags);
    }

}
```
