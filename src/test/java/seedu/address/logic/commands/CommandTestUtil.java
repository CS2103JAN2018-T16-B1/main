package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AVAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ISBN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Catalogue;
import seedu.address.model.Model;
import seedu.address.model.book.Book;
import seedu.address.model.book.TitleContainsKeywordsPredicate;
import seedu.address.model.book.exceptions.BookNotFoundException;
import seedu.address.testutil.EditBookDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_TITLE_XVI = "XVI";
    public static final String VALID_TITLE_YOU = "You";
    public static final String VALID_ISBN_XVI = "9780142417713";
    public static final String VALID_ISBN_YOU = "9781476785592";
    public static final String VALID_AVAIL_XVI = "Available";
    public static final String VALID_AVAIL_YOU = "Borrowed";
    public static final String VALID_AUTHOR_XVI = "Julia Karr";
    public static final String VALID_AUTHOR_YOU = "Caroline Kepnes";
    public static final String VALID_TAG_DYSTOPIA = "dystopia";
    public static final String VALID_TAG_FICTION = "fiction";


    public static final String TITLE_DESC_XVI = " " + PREFIX_TITLE + VALID_TITLE_XVI;
    public static final String TITLE_DESC_YOU = " " + PREFIX_TITLE + VALID_TITLE_YOU;
    public static final String ISBN_DESC_XVI = " " + PREFIX_ISBN + VALID_ISBN_XVI;
    public static final String ISBN_DESC_YOU = " " + PREFIX_ISBN + VALID_ISBN_YOU;
    public static final String AVAIL_DESC_XVI = " " + PREFIX_AVAIL + VALID_AVAIL_XVI;
    public static final String AVAIL_DESC_YOU = " " + PREFIX_AVAIL + VALID_AVAIL_YOU;
    public static final String AUTHOR_DESC_XVI = " " + PREFIX_AUTHOR + VALID_AUTHOR_XVI;
    public static final String AUTHOR_DESC_YOU = " " + PREFIX_AUTHOR + VALID_AUTHOR_YOU;
    public static final String TAG_DESC_DYSTOPIA = " " + PREFIX_TAG + VALID_TAG_DYSTOPIA;
    public static final String TAG_DESC_FICTION = " " + PREFIX_TAG + VALID_TAG_FICTION;

    public static final String INVALID_TITLE_DESC = " " + PREFIX_TITLE + "James&"; // '&' not allowed in titles
    public static final String INVALID_AUTHOR_DESC = " " + PREFIX_AUTHOR + "^"; // ^ not allowed for author
    public static final String INVALID_ISBN_DESC = " " + PREFIX_ISBN + "978031606792a"; // 'a' not allowed in isbns
    public static final String INVALID_AVAIL_DESC = " " + PREFIX_AVAIL + "not sure"; // must be as stated
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditBookDescriptor DESC_XVI;
    public static final EditCommand.EditBookDescriptor DESC_YOU;

    static {
        DESC_XVI = new EditBookDescriptorBuilder().withName(VALID_TITLE_XVI).withAuthor(VALID_AUTHOR_XVI)
            .withIsbn(VALID_ISBN_XVI).withAvail(VALID_AVAIL_XVI)
            .withTags(VALID_TAG_DYSTOPIA).build();
        DESC_YOU = new EditBookDescriptorBuilder().withName(VALID_TITLE_YOU).withAuthor(VALID_AUTHOR_YOU)
            .withIsbn(VALID_ISBN_YOU).withAvail(VALID_AVAIL_YOU)
            .withTags(VALID_TAG_FICTION).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute();
            System.out.println(result.feedbackToUser);
            assertEquals(expectedMessage, result.feedbackToUser);

            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the catalogue and the filtered book list in the {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        Catalogue expectedCatalogue = new Catalogue(actualModel.getCatalogue());
        List<Book> expectedFilteredList = new ArrayList<>(actualModel.getFilteredBookList());

        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedCatalogue, actualModel.getCatalogue());
            assertEquals(expectedFilteredList, actualModel.getFilteredBookList());
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the book at the given {@code targetIndex} in the
     * {@code model}'s catalogue.
     */
    public static void showBookAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredBookList().size());

        Book book = model.getFilteredBookList().get(targetIndex.getZeroBased());
        final String[] splitName = book.getTitle().fullTitle.split("\\s+");
        model.updateFilteredBookList(new TitleContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredBookList().size());
    }

    /**
     * Deletes the first book in {@code model}'s filtered list from {@code model}'s catalogue.
     */
    public static void deleteFirstBook(Model model) {
        Book firstBook = model.getFilteredBookList().get(0);
        try {
            model.deleteBook(firstBook);
        } catch (BookNotFoundException pnfe) {
            throw new AssertionError("Book in filtered list must exist in model.", pnfe);
        }
    }

    /**
     * Returns an {@code UndoCommand} with the given {@code model} and {@code undoRedoStack} set.
     */
    public static UndoCommand prepareUndoCommand(Model model, UndoRedoStack undoRedoStack) {
        UndoCommand undoCommand = new UndoCommand();
        undoCommand.setData(model, new CommandHistory(), undoRedoStack);
        return undoCommand;
    }

    /**
     * Returns a {@code RedoCommand} with the given {@code model} and {@code undoRedoStack} set.
     */
    public static RedoCommand prepareRedoCommand(Model model, UndoRedoStack undoRedoStack) {
        RedoCommand redoCommand = new RedoCommand();
        redoCommand.setData(model, new CommandHistory(), undoRedoStack);
        return redoCommand;
    }
}
