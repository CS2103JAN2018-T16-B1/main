package systemtests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.AUTHOR_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.AUTHOR_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.AVAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.AVAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AUTHOR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AVAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ISBN_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TITLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.ISBN_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ISBN_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AVAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ISBN_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BOOKS;
import static seedu.address.testutil.TypicalBooks.AMY;
import static seedu.address.testutil.TypicalBooks.BOB;
import static seedu.address.testutil.TypicalBooks.KEYWORD_MATCHING_MEIER;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.book.Author;
import seedu.address.model.book.Avail;
import seedu.address.model.book.Book;
import seedu.address.model.book.Isbn;
import seedu.address.model.book.Title;
import seedu.address.model.book.exceptions.BookNotFoundException;
import seedu.address.model.book.exceptions.DuplicateBookException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.BookBuilder;
import seedu.address.testutil.BookUtil;

public class EditCommandSystemTest extends CatalogueSystemTest {

    @Test
    public void edit() throws Exception {
        Model model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_BOOK;
        String command = " " + EditCommand.COMMAND_WORD + "  " + index.getOneBased() + "  " + TITLE_DESC_BOB + "  "
                + ISBN_DESC_BOB + " " + AVAIL_DESC_BOB + "  " + AUTHOR_DESC_BOB + " " + TAG_DESC_HUSBAND + " ";
        Book editedBook = new BookBuilder().withTitle(VALID_TITLE_BOB).withIsbn(VALID_ISBN_BOB)
                .withAvail(VALID_AVAIL_BOB).withAuthor(VALID_AUTHOR_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertCommandSuccess(command, index, editedBook);

        /* Case: undo editing the last book in the list -> last book restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last book in the list -> last book edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updateBook(
                getModel().getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased()), editedBook);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit a book with new values same as existing values -> edited */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + TITLE_DESC_BOB + ISBN_DESC_BOB
                + AVAIL_DESC_BOB + AUTHOR_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandSuccess(command, index, BOB);

        /* Case: edit some fields -> edited */
        index = INDEX_FIRST_BOOK;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + TAG_DESC_FRIEND;
        Book bookToEdit = getModel().getFilteredBookList().get(index.getZeroBased());
        editedBook = new BookBuilder(bookToEdit).withTags(VALID_TAG_FRIEND).build();
        assertCommandSuccess(command, index, editedBook);

        /* Case: clear tags -> cleared */
        index = INDEX_FIRST_BOOK;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_TAG.getPrefix();
        editedBook = new BookBuilder(bookToEdit).withTags().build();
        assertCommandSuccess(command, index, editedBook);

        /* ------------------ Performing edit operation while a filtered list is being shown ------------------------ */

        /* Case: filtered book list, edit index within bounds of catalogue and book list -> edited */
        showBooksWithName(KEYWORD_MATCHING_MEIER);
        index = INDEX_FIRST_BOOK;
        assertTrue(index.getZeroBased() < getModel().getFilteredBookList().size());
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + TITLE_DESC_BOB;
        bookToEdit = getModel().getFilteredBookList().get(index.getZeroBased());
        editedBook = new BookBuilder(bookToEdit).withTitle(VALID_TITLE_BOB).build();
        assertCommandSuccess(command, index, editedBook);

        /* Case: filtered book list, edit index within bounds of catalogue but out of bounds of book list
         * -> rejected
         */
        showBooksWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getCatalogue().getBookList().size();
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + TITLE_DESC_BOB,
                Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);

        /* --------------------- Performing edit operation while a book card is selected -------------------------- */

        /* Case: selects first card in the book list, edit a book -> edited, card selection remains unchanged but
         * browser url changes
         */
        showAllBooks();
        index = INDEX_FIRST_BOOK;
        selectBook(index);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + TITLE_DESC_AMY + ISBN_DESC_AMY
                + AVAIL_DESC_AMY + AUTHOR_DESC_AMY + TAG_DESC_FRIEND;
        // this can be misleading: card selection actually remains unchanged but the
        // browser's url is updated to reflect the new book's name
        assertCommandSuccess(command, index, AMY, index);

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " 0" + TITLE_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " -1" + TITLE_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredBookList().size() + 1;
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + TITLE_DESC_BOB,
                Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + TITLE_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_BOOK.getOneBased(),
                EditCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid name -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_BOOK.getOneBased() + INVALID_TITLE_DESC,
                Title.MESSAGE_TITLE_CONSTRAINTS);

        /* Case: invalid isbn -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_BOOK.getOneBased() + INVALID_ISBN_DESC,
                Isbn.MESSAGE_ISBN_CONSTRAINTS);

        /* Case: invalid avail -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_BOOK.getOneBased() + INVALID_AVAIL_DESC,
                Avail.MESSAGE_AVAIL_CONSTRAINTS);

        /* Case: invalid address -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_BOOK.getOneBased() + INVALID_AUTHOR_DESC,
                Author.MESSAGE_AUTHOR_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_BOOK.getOneBased() + INVALID_TAG_DESC,
                Tag.MESSAGE_TAG_CONSTRAINTS);

        /* Case: edit a book with new values same as another book's values -> rejected */
        executeCommand(BookUtil.getAddCommand(BOB));
        assertTrue(getModel().getCatalogue().getBookList().contains(BOB));
        index = INDEX_FIRST_BOOK;
        assertFalse(getModel().getFilteredBookList().get(index.getZeroBased()).equals(BOB));
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + TITLE_DESC_BOB + ISBN_DESC_BOB
                + AVAIL_DESC_BOB + AUTHOR_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_BOOK);

        /* Case: edit a book with new values same as another book's values but with different tags -> rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + TITLE_DESC_BOB + ISBN_DESC_BOB
                + AVAIL_DESC_BOB + AUTHOR_DESC_BOB + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_BOOK);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Book, Index)} except that
     * the browser url and selected card remain unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see EditCommandSystemTest#assertCommandSuccess(String, Index, Book, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Book editedBook) {
        assertCommandSuccess(command, toEdit, editedBook, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the book at index {@code toEdit} being
     * updated to values specified {@code editedBook}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Book editedBook,
            Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        try {
            expectedModel.updateBook(
                    expectedModel.getFilteredBookList().get(toEdit.getZeroBased()), editedBook);
            expectedModel.updateFilteredBookList(PREDICATE_SHOW_ALL_BOOKS);
        } catch (DuplicateBookException | BookNotFoundException e) {
            throw new IllegalArgumentException(
                    "editedBook is a duplicate in expectedModel, or it isn't found in the model.");
        }

        assertCommandSuccess(command, expectedModel,
                String.format(EditCommand.MESSAGE_EDIT_BOOK_SUCCESS, editedBook), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code CatalogueSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see CatalogueSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see CatalogueSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredBookList(PREDICATE_SHOW_ALL_BOOKS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code CatalogueSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see CatalogueSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
