package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.account.Account;
import seedu.address.model.account.Credential;
import seedu.address.model.account.PrivilegeLevel;
import seedu.address.model.account.UniqueAccountList;
import seedu.address.model.account.exceptions.AccountNotFoundException;
import seedu.address.model.account.exceptions.DuplicateAccountException;
import seedu.address.model.book.Book;
import seedu.address.model.book.exceptions.BookNotFoundException;
import seedu.address.model.book.exceptions.DuplicateBookException;

import java.util.function.Predicate;


/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Book> PREDICATE_SHOW_ALL_BOOKS = unused -> true;

    PrivilegeLevel PRIVILEGE_LEVEL_GUEST = new PrivilegeLevel(0);
    PrivilegeLevel PRIVILEGE_LEVEL_STUDENT = new PrivilegeLevel(1);
    PrivilegeLevel PRIVILEGE_LEVEL_LIBRARIAN = new PrivilegeLevel(2);

    /**
     * Clears existing backing model and replaces with the provided new data.
     */
    void resetData(ReadOnlyCatalogue newData);

    /**
     * Clears existing backing model and replaces with the provided new data.
     */
    void resetAccount(UniqueAccountList newData);

    /**
     * Returns the Catalogue
     */
    ReadOnlyCatalogue getCatalogue();

    /**
     * Deletes the given book.
     */
    void deleteBook(Book target) throws BookNotFoundException;

    /**
     * Adds the given book
     */
    void addBook(Book book) throws DuplicateBookException;

    /**
     * Replaces the given book {@code target} with {@code editedBook}.
     *
     * @throws DuplicateBookException if updating the book's details causes the book to be equivalent to
     *                                another existing book in the list.
     * @throws BookNotFoundException  if {@code target} could not be found in the list.
     */
    void updateBook(Book target, Book editedBook)
        throws DuplicateBookException, BookNotFoundException;

    void returnBook(Book target, Book returnedBook) throws BookNotFoundException;

    void borrowBook(Book target, Book borrowedBook) throws BookNotFoundException;

    void reserveBook(Book target, Book reservedBook) throws BookNotFoundException;

    /**
     * Returns an unmodifiable view of the filtered book list
     */
    ObservableList<Book> getFilteredBookList();

    /**
     * Updates the filter of the filtered book list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredBookList(Predicate<Book> predicate);

    //@@author QiuHaohao
    void addAccount(Account account) throws DuplicateAccountException;

    void deleteAccount(Account account) throws AccountNotFoundException;

    void updateAccount(Account account, Account editedAccount)
        throws DuplicateAccountException, AccountNotFoundException;

    PrivilegeLevel authenticate(Credential credential);

    void logout();

    PrivilegeLevel getPrivilegeLevel();
    //@@author
}
