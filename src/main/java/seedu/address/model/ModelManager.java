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
import seedu.address.commons.events.model.AccountListChangedEvent;
import seedu.address.commons.events.model.CatalogueChangedEvent;
import seedu.address.model.account.Account;
import seedu.address.model.account.Credential;
import seedu.address.model.account.PrivilegeLevel;
import seedu.address.model.account.UniqueAccountList;
import seedu.address.model.account.exceptions.AccountNotFoundException;
import seedu.address.model.account.exceptions.DuplicateAccountException;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookAlreadyAvailableException;
import seedu.address.model.book.exceptions.BookNotFoundException;
import seedu.address.model.book.exceptions.DuplicateBookException;


/**
 * Represents the in-memory model of the catalogue data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Catalogue catalogue;
    private final UniqueAccountList accountList;
    private final FilteredList<Book> filteredBooks;
    private Account currentAccount;

    /**
     * Initializes a ModelManager with the given catalogue and userPrefs.
     */
    public ModelManager(ReadOnlyCatalogue catalogue, UserPrefs userPrefs) {
        super();
        requireAllNonNull(catalogue, userPrefs);

        logger.fine("Initializing with catalogue: " + catalogue + " and user prefs " + userPrefs);

        this.catalogue = new Catalogue(catalogue);
        filteredBooks = new FilteredList<>(this.catalogue.getBookList());
        this.accountList = new UniqueAccountList();
        this.currentAccount = Account.createGuestAccount();
        addFirstAccount();

    }

    //@@author QiuHaohao

    /**
     * Initializes a ModelManager with the given catalogue, accountList and userPrefs.
     */
    public ModelManager(ReadOnlyCatalogue catalogue, UniqueAccountList accountList, UserPrefs userPrefs) {
        super();
        requireAllNonNull(catalogue, accountList, userPrefs);

        logger.fine("Initializing with catalogue: " + catalogue
            + ", accountList: " + accountList
            + " and user prefs " + userPrefs);

        this.catalogue = new Catalogue(catalogue);
        filteredBooks = new FilteredList<>(this.catalogue.getBookList());
        this.accountList = accountList;
        this.currentAccount = Account.createGuestAccount();
    }
    //@@author

    public ModelManager() {
        this(new Catalogue(), new UserPrefs());
    }

    //@@author QiuHaohao

    /**
     * Adds an account to the AccountList
     *
     * @param account
     * @throws DuplicateAccountException
     */
    public void addAccount(Account account) throws DuplicateAccountException {
        accountList.add(account);
        indicateAccountListChanged();
    }

    /**
     * Deletes an account from the AccountList
     *
     * @param account
     * @throws AccountNotFoundException
     */
    public void deleteAccount(Account account) throws AccountNotFoundException {
        accountList.remove(account);
        indicateAccountListChanged();
    }

    /**
     * Replaces an account with a new one
     *
     * @param account
     * @param editedAccount
     * @throws DuplicateAccountException
     * @throws AccountNotFoundException
     */
    public void updateAccount(Account account, Account editedAccount)
        throws DuplicateAccountException, AccountNotFoundException {
        accountList.setAccount(account, account);
        indicateAccountListChanged();
    }

    /**
     * Adds the initial admin account to the accountList
     */
    private void addFirstAccount() {
        Account admin = Account.createDefaultAdminAccount();
        if (!this.accountList.contains(admin)) {
            try {
                this.accountList.add(admin);
            } catch (DuplicateAccountException e) {
                e.printStackTrace();
            }
        }
    }
    //@@author

    @Override
    public void resetData(ReadOnlyCatalogue newData) {
        catalogue.resetData(newData);
        indicateCatalogueChanged();
    }

    @Override
    public ReadOnlyCatalogue getCatalogue() {
        return catalogue;
    }

    /**
     * Raises an event to indicate the model has changed
     */
    private void indicateCatalogueChanged() {
        raise(new CatalogueChangedEvent(catalogue));
    }

    //@@author QiuHaohao

    /**
     * Raises an event to indicate the model has changed
     */
    private void indicateAccountListChanged() {
        raise(new AccountListChangedEvent(accountList));
    }
    //@@author

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
    public void returnBook(Book target, Book returnedBook) throws BookNotFoundException {
        catalogue.returnBook(target, returnedBook);
        indicateCatalogueChanged();
    }

    @Override
    public void updateBook(Book target, Book editedBook)
        throws DuplicateBookException, BookNotFoundException {
        requireAllNonNull(target, editedBook);

        catalogue.updateBook(target, editedBook);
        indicateCatalogueChanged();
    }

    @Override
    public void borrowBook(Book target, Book borrowedBook) throws BookNotFoundException {
        catalogue.borrowBook(target, borrowedBook);
        requireAllNonNull(target, borrowedBook);
        indicateCatalogueChanged();
    }

    @Override
    public void reserveBook(Book target, Book reservedBook) throws BookNotFoundException {
        catalogue.reserveBook(target,reservedBook);
        requireAllNonNull(target, reservedBook);
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

    //@@author QiuHaohao
    @Override
    public PrivilegeLevel authenticate(Credential c) {
        Account matched = accountList.authenticate(c);
        if (matched != null) {
            this.currentAccount = matched;
            return currentAccount.getPrivilegeLevel();
        }
        //if not found
        return PRIVILEGE_LEVEL_GUEST;
    }

    @Override
    public void logout() {
        currentAccount = Account.createGuestAccount();
    }

    @Override
    public PrivilegeLevel getPrivilegeLevel() {
        return this.currentAccount.getPrivilegeLevel();
    }
    //@@author

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
