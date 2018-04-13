package seedu.address.logic.commands;

import org.junit.Before;
import org.junit.Test;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.Book;
import seedu.address.testutil.BookBuilder;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalBooks.getTypicalCatalogue;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalCatalogue(), new UserPrefs());
    }

    @Test
    public void execute_newBook_success() throws Exception {
        Book validBook = new BookBuilder().build();

        Model expectedModel = new ModelManager(model.getCatalogue(), new UserPrefs());
        expectedModel.addBook(validBook);

        assertCommandSuccess(prepareCommand(validBook, model), model,
            String.format(AddCommand.MESSAGE_SUCCESS, validBook), expectedModel);
    }

    @Test
    public void execute_duplicateBook_throwsCommandException() {
        Book bookInList = model.getCatalogue().getBookList().get(0);
        assertCommandFailure(prepareCommand(bookInList, model), model, AddCommand.MESSAGE_DUPLICATE_BOOK);
    }

    /**
     * Generates a new {@code AddCommand} which upon execution, adds {@code book} into the {@code model}.
     */
    private AddCommand prepareCommand(Book book, Model model) {
        AddCommand command = new AddCommand(book);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
