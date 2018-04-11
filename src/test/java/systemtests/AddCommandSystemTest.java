//@@author khiayi
package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.AUTHOR_DESC_XVI;
import static seedu.address.logic.commands.CommandTestUtil.AUTHOR_DESC_YOU;
import static seedu.address.logic.commands.CommandTestUtil.AVAIL_DESC_XVI;
import static seedu.address.logic.commands.CommandTestUtil.AVAIL_DESC_YOU;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AUTHOR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AVAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ISBN_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TITLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.ISBN_DESC_XVI;
import static seedu.address.logic.commands.CommandTestUtil.ISBN_DESC_YOU;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_DYSTOPIA;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FICTION;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_XVI;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_YOU;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_XVI;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_YOU;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AVAIL_XVI;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AVAIL_YOU;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ISBN_XVI;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ISBN_YOU;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_DYSTOPIA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_XVI;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_YOU;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ISBN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalBooks.ANIMAL;
import static seedu.address.testutil.TypicalBooks.CALIFORNIA;
import static seedu.address.testutil.TypicalBooks.HOLES;
import static seedu.address.testutil.TypicalBooks.INVISIBLE;
import static seedu.address.testutil.TypicalBooks.KEYWORD_MATCHING_GIRL;
import static seedu.address.testutil.TypicalBooks.XVI;
import static seedu.address.testutil.TypicalBooks.YOU;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.book.Author;
import seedu.address.model.book.Avail;
import seedu.address.model.book.Book;
import seedu.address.model.book.Isbn;
import seedu.address.model.book.Title;
import seedu.address.model.book.exceptions.DuplicateBookException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.BookBuilder;
import seedu.address.testutil.BookUtil;

//import seedu.address.logic.commands.Command;

public class AddCommandSystemTest extends CatalogueSystemTest {

    @Test
    public void add() throws Exception {
        Model model = getModel();

        //Log in first
        executeCommand("login admin admin");


        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a book without tags to a non-empty catalogue, command with leading spaces and trailing spaces
         * -> added
         */
        Book toAdd = XVI;
        String command = "   " + AddCommand.COMMAND_WORD + "  " + TITLE_DESC_XVI + "  " + "   " + AUTHOR_DESC_XVI
            + " " + ISBN_DESC_XVI + " "
            + AVAIL_DESC_XVI + "   " + TAG_DESC_DYSTOPIA + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding XVI to the list -> XVI deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding XVI to the list -> XVI added again */
        command = RedoCommand.COMMAND_WORD;
        model.addBook(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a book with all fields same as another book in the catalogue except name -> not added */
        toAdd = new BookBuilder().withTitle(VALID_TITLE_YOU).withAuthor(VALID_AUTHOR_XVI)
            .withIsbn(VALID_ISBN_XVI).withAvail(VALID_AVAIL_XVI)
            .withTags(VALID_TAG_DYSTOPIA).build();
        command = AddCommand.COMMAND_WORD + TITLE_DESC_YOU + AUTHOR_DESC_XVI + ISBN_DESC_XVI + AVAIL_DESC_XVI
            + TAG_DESC_DYSTOPIA;
        assertCommandFailure(command, toAdd);

        /* Case: add a book with all fields same as another book in the catalogue except isbn -> added */
        toAdd = new BookBuilder().withTitle(VALID_TITLE_XVI).withAuthor(VALID_AUTHOR_XVI)
            .withIsbn(VALID_ISBN_YOU).withAvail(VALID_AVAIL_XVI)
            .withTags(VALID_TAG_DYSTOPIA).build();
        command = AddCommand.COMMAND_WORD + TITLE_DESC_XVI + AUTHOR_DESC_XVI + ISBN_DESC_YOU + AVAIL_DESC_XVI
            + TAG_DESC_DYSTOPIA;
        assertCommandSuccess(command, toAdd);

        /* Case: add a book with all fields same as another book in the catalogue except name and ISBN -> added */
        toAdd = new BookBuilder().withTitle(VALID_TITLE_YOU).withAuthor(VALID_AUTHOR_XVI)
            .withIsbn("1111111111111").withAvail(VALID_AVAIL_XVI)
            .withTags(VALID_TAG_DYSTOPIA).build();
        command = AddCommand.COMMAND_WORD + TITLE_DESC_YOU + AUTHOR_DESC_XVI + " " + PREFIX_ISBN + "1111111111111"
            + AVAIL_DESC_XVI + TAG_DESC_DYSTOPIA;
        assertCommandSuccess(command, toAdd);

        /* Case: add a book with all fields same as another book in the catalogue except avail -> not added */
        toAdd = new BookBuilder().withTitle(VALID_TITLE_XVI).withAuthor(VALID_AUTHOR_XVI)
            .withIsbn(VALID_ISBN_XVI).withAvail(VALID_AVAIL_YOU)
            .withTags(VALID_TAG_DYSTOPIA).build();
        command = AddCommand.COMMAND_WORD + TITLE_DESC_XVI + AUTHOR_DESC_XVI + ISBN_DESC_XVI + AVAIL_DESC_YOU
            + TAG_DESC_DYSTOPIA;
        assertCommandFailure(command, toAdd);

        /* Case: add a book with all fields same as another book in the catalogue except avail and ISBN -> added */
        toAdd = new BookBuilder().withTitle(VALID_TITLE_XVI).withAuthor(VALID_AUTHOR_XVI)
            .withIsbn("2222222222222").withAvail(VALID_AVAIL_YOU)
            .withTags(VALID_TAG_DYSTOPIA).build();
        command = AddCommand.COMMAND_WORD + TITLE_DESC_XVI + AUTHOR_DESC_XVI + " " + PREFIX_ISBN + "2222222222222"
            + AVAIL_DESC_YOU + TAG_DESC_DYSTOPIA;
        assertCommandSuccess(command, toAdd);

        /* Case: add a book with all fields same as another book in the catalogue except author -> added */
        toAdd = new BookBuilder().withTitle(VALID_TITLE_XVI).withAuthor(VALID_AUTHOR_YOU)
            .withIsbn(VALID_ISBN_XVI).withAvail(VALID_AVAIL_XVI)
            .withTags(VALID_TAG_DYSTOPIA).build();
        command = AddCommand.COMMAND_WORD + TITLE_DESC_XVI + AUTHOR_DESC_YOU + ISBN_DESC_XVI + AVAIL_DESC_XVI
            + TAG_DESC_DYSTOPIA;
        assertCommandFailure(command, toAdd);

        /* Case: add a book with all fields same as another book in the catalogue except author and ISBN -> added */
        toAdd = new BookBuilder().withTitle(VALID_TITLE_XVI).withAuthor(VALID_AUTHOR_YOU)
            .withIsbn("3333333333333").withAvail(VALID_AVAIL_XVI)
            .withTags(VALID_TAG_DYSTOPIA).build();
        command = AddCommand.COMMAND_WORD + TITLE_DESC_XVI + AUTHOR_DESC_YOU + " " + PREFIX_ISBN + "3333333333333"
            + AVAIL_DESC_XVI + TAG_DESC_DYSTOPIA;
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty catalogue -> added */
        deleteAllBooks();
        assertCommandSuccess(ANIMAL);

        /* Case: add a book with tags, command with parameters in random order -> added */
        toAdd = YOU;
        command = AddCommand.COMMAND_WORD + AUTHOR_DESC_YOU + ISBN_DESC_YOU + TITLE_DESC_YOU
            + TAG_DESC_FICTION + AVAIL_DESC_YOU;
        assertCommandSuccess(command, toAdd);

        /* Case: add a book, missing tags -> added */
        assertCommandSuccess(HOLES);

        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */

        /* Case: filters the book list before adding -> added */
        showBooksWithTitle(KEYWORD_MATCHING_GIRL);
        assertCommandSuccess(INVISIBLE);

        /* ------------------------ Perform add operation while a book card is selected --------------------------- */

        /* Case: selects first card in the book list, add a book -> added, card selection remains unchanged */
        selectBook(Index.fromOneBased(1));
        assertCommandSuccess(CALIFORNIA);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate book -> rejected */
        command = BookUtil.getAddCommand(HOLES);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_BOOK);

        /* Case: add a duplicate book except with different tags -> rejected */
        // "friends" is an existing tag used in the default model, see TypicalBooks#ANIMAL
        // This test will fail if a new tag that is not in the model is used, see the bug documented in
        // Catalogue#addBook(Book)
        command = BookUtil.getAddCommand(HOLES) + " " + PREFIX_TAG.getPrefix() + "unlabelled";
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_BOOK);

        /* Case: missing name -> rejected */
        command = AddCommand.COMMAND_WORD + AUTHOR_DESC_XVI + ISBN_DESC_XVI + AVAIL_DESC_XVI;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing isbn -> rejected */
        command = AddCommand.COMMAND_WORD + TITLE_DESC_XVI + AUTHOR_DESC_XVI + AVAIL_DESC_XVI;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing avail -> rejected */
        command = AddCommand.COMMAND_WORD + TITLE_DESC_XVI + AUTHOR_DESC_XVI + ISBN_DESC_XVI;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing author -> rejected */
        command = AddCommand.COMMAND_WORD + TITLE_DESC_XVI + ISBN_DESC_XVI + AVAIL_DESC_XVI;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + BookUtil.getBookDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddCommand.COMMAND_WORD + INVALID_TITLE_DESC + AUTHOR_DESC_XVI + ISBN_DESC_XVI + AVAIL_DESC_XVI;
        assertCommandFailure(command, Title.MESSAGE_TITLE_CONSTRAINTS);

        /* Case: invalid isbn -> rejected */
        command = AddCommand.COMMAND_WORD + TITLE_DESC_XVI + AUTHOR_DESC_XVI + INVALID_ISBN_DESC + AVAIL_DESC_XVI;
        assertCommandFailure(command, Isbn.MESSAGE_ISBN_CONSTRAINTS);

        /* Case: invalid avail -> rejected */
        command = AddCommand.COMMAND_WORD + TITLE_DESC_XVI + AUTHOR_DESC_XVI + ISBN_DESC_XVI + INVALID_AVAIL_DESC;
        assertCommandFailure(command, Avail.MESSAGE_AVAIL_CONSTRAINTS);

        /* Case: invalid author -> rejected */
        command = AddCommand.COMMAND_WORD + TITLE_DESC_XVI + INVALID_AUTHOR_DESC + ISBN_DESC_XVI + AVAIL_DESC_XVI;
        assertCommandFailure(command, Author.MESSAGE_AUTHOR_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        command = AddCommand.COMMAND_WORD + TITLE_DESC_XVI + AUTHOR_DESC_XVI + ISBN_DESC_XVI + AVAIL_DESC_XVI
            + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_TAG_CONSTRAINTS);


    }
    //@@author
    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Model}, {@code Storage} and {@code BookListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code CatalogueSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see CatalogueSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Book toAdd) {
        assertCommandSuccess(BookUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Book)}. Executes {@code command}
     * instead.
     *
     * @see AddCommandSystemTest#assertCommandSuccess(Book)
     */
    private void assertCommandSuccess(String command, Book toAdd) {
        Model expectedModel = getModel();
        try {
            expectedModel.addBook(toAdd);
        } catch (DuplicateBookException dpe) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        }
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Book)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Model}, {@code Storage} and {@code BookListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     *
     * @see AddCommandSystemTest#assertCommandSuccess(String, Book)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code BookListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code CatalogueSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
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

    private void assertCommandFailure(String command, Book toAdd) {
        String expectedResultMessage = String.format(AddCommand.MESSAGE_DUPLICATE_BOOK, toAdd);

        assertCommandFailure(command, expectedResultMessage);
    }
}
