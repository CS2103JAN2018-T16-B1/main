package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.CatalogueChangedEvent;
import seedu.address.model.book.Book;
import seedu.address.model.book.exceptions.BookNotFoundException;
import seedu.address.model.book.exceptions.DuplicateBookException;


/**
 * Represents the in-memory model of the catalogue data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Catalogue catalogue;
    private final FilteredList<Book> filteredBooks;

    /**
     * Initializes a ModelManager with the given catalogue and userPrefs.
     */
    public ModelManager(ReadOnlyCatalogue catalogue, UserPrefs userPrefs) {
        super();
        requireAllNonNull(catalogue, userPrefs);

        logger.fine("Initializing with catalogue: " + catalogue + " and user prefs " + userPrefs);

        this.catalogue = new Catalogue(catalogue);
        filteredBooks = new FilteredList<>(this.catalogue.getBookList());
    }

    public ModelManager() {
        this(new Catalogue(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyCatalogue newData) {
        catalogue.resetData(newData);
        indicateCatalogueChanged();
    }

    @Override
    public ReadOnlyCatalogue getCatalogue() {
        return catalogue;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateCatalogueChanged() {
        raise(new CatalogueChangedEvent(catalogue));
    }

    @Override
    public synchronized void deleteBook(Book target) throws BookNotFoundException {
        catalogue.removeBook(target);
        indicateCatalogueChanged();
    }

    @Override
    public synchronized void addBook(Book book) throws DuplicateBookException {
        catalogue.addBook(book);
        updateFilteredBookList(PREDICATE_SHOW_ALL_BOOKS);
        indicateCatalogueChanged();
    }

    @Override
    public void updateBook(Book target, Book editedBook)
            throws DuplicateBookException, BookNotFoundException {
        requireAllNonNull(target, editedBook);

        catalogue.updateBook(target, editedBook);
        indicateCatalogueChanged();
    }

    //=========== Filtered Book List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Book} backed by the internal list of
     * {@code catalogue}
     */
    @Override
    public ObservableList<Book> getFilteredBookList() {
        return FXCollections.unmodifiableObservableList(filteredBooks);
    }

    @Override
    public void updateFilteredBookList(Predicate<Book> predicate) {
        requireNonNull(predicate);
        filteredBooks.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return catalogue.equals(other.catalogue)
                && filteredBooks.equals(other.filteredBooks);
    }

}
