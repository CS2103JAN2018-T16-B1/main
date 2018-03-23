package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.book.Book;
import seedu.address.model.book.exceptions.BookNotFoundException;
import seedu.address.model.book.exceptions.DuplicateBookException;


/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Book> PREDICATE_SHOW_ALL_BOOKS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyCatalogue newData);

    /** Returns the Catalogue */
    ReadOnlyCatalogue getCatalogue();

    /** Deletes the given book. */
    void deleteBook(Book target) throws BookNotFoundException;

    /** Adds the given book */
    void addBook(Book book) throws DuplicateBookException;

    /**
     * Replaces the given book {@code target} with {@code editedBook}.
     *
     * @throws DuplicateBookException if updating the book's details causes the book to be equivalent to
     *      another existing book in the list.
     * @throws BookNotFoundException if {@code target} could not be found in the list.
     */
    void updateBook(Book target, Book editedBook)
            throws DuplicateBookException, BookNotFoundException;

    /** Returns an unmodifiable view of the filtered book list */
    ObservableList<Book> getFilteredBookList();

    /**
     * Updates the filter of the filtered book list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredBookList(Predicate<Book> predicate);

}
