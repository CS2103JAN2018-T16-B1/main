package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertFalse;

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
import seedu.address.model.account.Account;
import seedu.address.model.account.Credential;
import seedu.address.model.account.PrivilegeLevel;
import seedu.address.model.account.UniqueAccountList;
import seedu.address.model.account.exceptions.DuplicateAccountException;
import seedu.address.model.book.Book;
import seedu.address.model.book.exceptions.BookNotFoundException;
import seedu.address.model.book.exceptions.DuplicateBookException;
import seedu.address.testutil.AccountBuilder;

public class AddAccountCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullAccount_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddAccountCommand(null);
    }

    @Test
    public void execute_accountAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingAccountAdded modelStub = new ModelStubAcceptingAccountAdded();
        Account validAccount = new AccountBuilder().build();

        CommandResult commandResult = getAddCommandForAccount(validAccount, modelStub).execute();

        assertEquals(String.format(AddAccountCommand.MESSAGE_SUCCESS, validAccount), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validAccount), modelStub.accountsAdded);
    }

    @Test
    public void execute_duplicateAccount_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateAccountException();
        Account validAccount = new AccountBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddAccountCommand.MESSAGE_DUPLICATE_ACCOUNT);

        getAddCommandForAccount(validAccount, modelStub).execute();
    }

    @Test
    public void equals() {
        Account alice = new AccountBuilder().withName("Alice").build();
        Account bob = new AccountBuilder().withName("Bob").build();
        AddAccountCommand addAliceCommand = new AddAccountCommand(alice);
        AddAccountCommand addBobCommand = new AddAccountCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different account -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * Generates a new AddAccountCommand with the details of the given account.
     */
    private AddAccountCommand getAddCommandForAccount(Account account, Model model) {
        AddAccountCommand command = new AddAccountCommand(account);
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
        public void resetAccount(UniqueAccountList newData) {

        }

        @Override
        public ReadOnlyCatalogue getCatalogue() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deleteBook(Book target) {
            fail("This method should not be called.");
        }

        @Override
        public void updateBook(Book target, Book editedBook) {
            fail("This method should not be called.");
        }

        @Override
        public void returnBook(Book target, Book returnedBook) throws BookNotFoundException {

        }

        @Override
        public void borrowBook(Book target, Book borrowedBook) throws BookNotFoundException {

        }

        @Override
        public void reserveBook(Book target, Book reservedBook) throws BookNotFoundException {

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

        @Override
        public PrivilegeLevel authenticate(Credential c) {
            return Model.PRIVILEGE_LEVEL_GUEST;
        }

        @Override
        public void logout() {

        }

        @Override
        public PrivilegeLevel getPrivilegeLevel() {
            return Model.PRIVILEGE_LEVEL_GUEST;
        }

        public void addAccount(Account account) throws DuplicateAccountException {

        }

        public void deleteAccount(Account account) {

        }

        public void updateAccount(Account account, Account editedAccount) {

        }
    }

    /**
     * A Model stub that always throw a DuplicateAccountException when trying to add a account.
     */
    private class ModelStubThrowingDuplicateAccountException extends ModelStub {
        @Override
        public void addAccount(Account account) throws DuplicateAccountException {
            throw new DuplicateAccountException();
        }

        @Override

        public ReadOnlyCatalogue getCatalogue() {
            return new Catalogue();
        }
    }

    /**
     * A Model stub that always accept the account being added.
     */
    private class ModelStubAcceptingAccountAdded extends ModelStub {
        final ArrayList<Account> accountsAdded = new ArrayList<>();

        @Override
        public void addAccount(Account account) {
            requireNonNull(account);
            accountsAdded.add(account);
        }

        @Override
        public ReadOnlyCatalogue getCatalogue() {
            return new Catalogue();
        }
    }

}
