package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Catalogue;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyCatalogue;
import seedu.address.model.book.Book;
import seedu.address.model.book.exceptions.DuplicateBookException;
import seedu.address.model.book.exceptions.BookNotFoundException;
import seedu.address.testutil.BookBuilder;

public class AddCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_bookAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingBookAdded modelStub = new ModelStubAcceptingBookAdded();
        Book validBook = new BookBuilder().build();

        CommandResult commandResult = getAddCommandForBook(validBook, modelStub).execute();

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validBook), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validBook), modelStub.booksAdded);
    }

    @Test
    public void execute_duplicateBook_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateBookException();
        Book validBook = new BookBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_BOOK);

        getAddCommandForBook(validBook, modelStub).execute();
    }

    @Test
    public void equals() {
        Book alice = new BookBuilder().withName("Alice").build();
        Book bob = new BookBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different book -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * Generates a new AddCommand with the details of the given book.
     */
    private AddCommand getAddCommandForBook(Book book, Model model) {
        AddCommand command = new AddCommand(book);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addBook(Book book) throws DuplicateBookException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyCatalogue newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyCatalogue getCatalogue() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deleteBook(Book target) throws BookNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updateBook(Book target, Book editedBook)
                throws DuplicateBookException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Book> getFilteredBookList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredBookList(Predicate<Book> predicate) {
            fail("This method should not be called.");
        }
    }

    /**
     * A Model stub that always throw a DuplicateBookException when trying to add a book.
     */
    private class ModelStubThrowingDuplicateBookException extends ModelStub {
        @Override
        public void addBook(Book book) throws DuplicateBookException {
            throw new DuplicateBookException();
        }

        @Override
        public ReadOnlyCatalogue getCatalogue() {
            return new Catalogue();
        }
    }

    /**
     * A Model stub that always accept the book being added.
     */
    private class ModelStubAcceptingBookAdded extends ModelStub {
        final ArrayList<Book> booksAdded = new ArrayList<>();

        @Override
        public void addBook(Book book) throws DuplicateBookException {
            requireNonNull(book);
            booksAdded.add(book);
        }

        @Override
        public ReadOnlyCatalogue getCatalogue() {
            return new Catalogue();
        }
    }

}
