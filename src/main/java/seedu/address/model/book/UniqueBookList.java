package seedu.address.model.book;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.book.Avail.AVAILABLE;
import static seedu.address.model.book.Avail.BORROWED;
import static seedu.address.model.book.Avail.BORROWED_AND_RESERVED;
import static seedu.address.model.book.Avail.RESERVED;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.book.exceptions.BookNotFoundException;
import seedu.address.model.book.exceptions.DuplicateBookException;

/**
 * A list of books that enforces uniqueness between its elements and does not allow nulls.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Book#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueBookList implements Iterable<Book> {

    private final ObservableList<Book> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent book as the given argument.
     */
    public boolean contains(Book toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Returns true if there is a book with the same isbn provided
     *
     * @param p
     * @return
     */
    public boolean containsIsbn(Isbn p) {
        for (Book b : internalList) {
            if (b.isbnMatches(p)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if there is a book with a isbn that is the
     * same as that of the book provided
     *
     * @param toCheck
     * @return
     */
    public boolean containsSameIsbn(Book toCheck) {
        requireNonNull(toCheck);
        return containsIsbn(toCheck.getIsbn());
    }

    /**
     * Adds a book to the list.
     *
     * @throws DuplicateBookException if the book to add is a duplicate of an existing book in the list.
     */
    public void add(Book toAdd) throws DuplicateBookException {
        requireNonNull(toAdd);
        if (contains(toAdd) || containsSameIsbn(toAdd)) {
            throw new DuplicateBookException();
        }
        internalList.add(toAdd);
    }


    /**
     * Replaces the book {@code target} in the list with {@code editedBook}.
     *
     * @throws DuplicateBookException if the replacement is equivalent to another existing book in the list.
     * @throws BookNotFoundException  if {@code target} could not be found in the list.
     */
    public void setBook(Book target, Book editedBook)
        throws DuplicateBookException, BookNotFoundException {
        requireNonNull(editedBook);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new BookNotFoundException();
        }

        if (!target.equals(editedBook) && internalList.contains(editedBook)) {
            throw new DuplicateBookException();
        }

        internalList.set(index, editedBook);
    }

    /**
     * Removes the equivalent book from the list.
     *
     * @throws BookNotFoundException if no such book could be found in the list.
     */
    public boolean remove(Book toRemove) throws BookNotFoundException {
        requireNonNull(toRemove);
        final boolean bookFoundAndDeleted = internalList.remove(toRemove);
        if (!bookFoundAndDeleted) {
            throw new BookNotFoundException();
        }
        return bookFoundAndDeleted;
    }

    /**
     * Returns a book
     * @param target, returnedBook
     * @return
     * @throws BookNotFoundException
     */
    public void replaceReturnedBook(Book target, Book returnedBook)
            throws BookNotFoundException {
        requireNonNull(returnedBook);

        int index = internalList.indexOf(target);
        String status = target.getAvail().getValue();

        switch (status){

            case(BORROWED):
                internalList.set(index, returnedBook);
                break;

            case(RESERVED):
                internalList.set(index, returnedBook);
                break;

            default:
                throw new BookNotFoundException();

        }


    }

    /**
     * Borrows a book
     * @param target, borrowedBook
     * @return
     * @throws BookNotFoundException
     */
    public void replaceBorrowedBook(Book target, Book borrowedBook)
            throws BookNotFoundException {
        requireNonNull(borrowedBook);

        int index = internalList.indexOf(target);
        String status = target.getAvail().getValue();

        switch (status) {

            case (AVAILABLE):
                internalList.set(index, borrowedBook);
                break;

            default:
                throw new BookNotFoundException();

        }
    }

    /**
     * Reserves a book
     * @param target, reservedBook
     * @return
     * @throws BookNotFoundException
     */
    public void replaceReservedBook(Book target, Book reservedBook)
            throws BookNotFoundException {
        requireNonNull(reservedBook);
        int index = internalList.indexOf(target);
        String status = target.getAvail().getValue();

        switch (status) {

            case (BORROWED):
                internalList.set(index, reservedBook);
                break;

            default:
                throw new BookNotFoundException();

        }
    }

    public void setBooks(UniqueBookList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setBooks(List<Book> books) throws DuplicateBookException {
        requireAllNonNull(books);
        final UniqueBookList replacement = new UniqueBookList();
        for (final Book book : books) {
            replacement.add(book);
        }
        setBooks(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Book> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Book> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof UniqueBookList // instanceof handles nulls
            && this.internalList.equals(((UniqueBookList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
