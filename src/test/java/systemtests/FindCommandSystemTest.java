package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.address.commons.core.Messages.MESSAGE_BOOKS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalBooks.CALIFORNIA;
import static seedu.address.testutil.TypicalBooks.DELIRIUM;
import static seedu.address.testutil.TypicalBooks.GONE;
import static seedu.address.testutil.TypicalBooks.KEYWORD_MATCHING_GIRL;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

public class FindCommandSystemTest extends CatalogueSystemTest {

    @Test
    public void find() {
        //@@author QiuHaohao
        executeCommand("login admin admin");
        //@@author khiayi
        /* Case: find multiple books in catalogue, command with leading spaces and trailing spaces
         * -> 2 books found
         */
        String command = "   " + FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_GIRL + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, CALIFORNIA, GONE); // Two titles contains "Girl"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where book list is displaying the books we are finding
         * -> 2 books found
         */
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_GIRL;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find book where book list is not displaying the book we are finding -> 1 book found */
        command = FindCommand.COMMAND_WORD + " California";
        ModelHelper.setFilteredList(expectedModel, CALIFORNIA);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple books in catalogue, 2 keywords -> 2 books found */
        command = FindCommand.COMMAND_WORD + " California Gone";
        ModelHelper.setFilteredList(expectedModel, CALIFORNIA, GONE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple books in catalogue, 2 keywords in reversed order -> 2 books found */
        command = FindCommand.COMMAND_WORD + " Gone California";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple books in catalogue, 2 keywords with 1 repeat -> 2 books found */
        command = FindCommand.COMMAND_WORD + " Gone California Gone";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple books in catalogue, 2 matching keywords and 1 non-matching keyword
         * -> 2 books found
         */
        command = FindCommand.COMMAND_WORD + " Gone California NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();
        //@@author
        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);
        //@@author khiayi
        /* Case: find same books in catalogue after deleting 1 of them -> 1 book found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertFalse(getModel().getCatalogue().getBookList().contains(CALIFORNIA));
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_GIRL;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, GONE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find book in catalogue, keyword is same as name but of different case -> 1 book found */
        command = FindCommand.COMMAND_WORD + " GoNe GiRl";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find book in catalogue, keyword is substring of name -> 0 books found */
        command = FindCommand.COMMAND_WORD + " Gon";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find book in catalogue, name is substring of keyword -> 0 books found */
        command = FindCommand.COMMAND_WORD + " Oliver";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find book not in catalogue -> 0 books found */
        command = FindCommand.COMMAND_WORD + " Mark";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find book in catalogue, keyword is substring of author -> 0 books found */
        command = FindCommand.COMMAND_WORD + " Lau";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find book in catalogue, author is substring of keyword -> 0 books found */
        command = FindCommand.COMMAND_WORD + " Lauren";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find book not in catalogue, author not in catalogue -> 0 books found */
        command = FindCommand.COMMAND_WORD + " Mark";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find isbn number of book in catalogue -> 0 books found */
        command = FindCommand.COMMAND_WORD + " " + DELIRIUM.getIsbn().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find availability of book in catalogue -> 0 books found */
        command = FindCommand.COMMAND_WORD + " " + DELIRIUM.getAvail().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tags of book in catalogue -> 0 books found */
        List<Tag> tags = new ArrayList<>(DELIRIUM.getTags());
        command = FindCommand.COMMAND_WORD + " " + tags.get(0).tagName;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find while a book is selected -> selected card deselected */
        showAllBooks();
        selectBook(Index.fromOneBased(1));
        assertFalse(getBookListPanel().getHandleToSelectedCard().getTitle().equals(DELIRIUM.getTitle().fullTitle));
        command = FindCommand.COMMAND_WORD + " Delirium";
        ModelHelper.setFilteredList(expectedModel, DELIRIUM);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find book in empty catalogue -> 0 books found */
        deleteAllBooks();
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_GIRL;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DELIRIUM);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNd Delirium";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }
    //@@author
    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_BOOKS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code CatalogueSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     *
     * @see CatalogueSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
            MESSAGE_BOOKS_LISTED_OVERVIEW, expectedModel.getFilteredBookList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code CatalogueSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
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
}
