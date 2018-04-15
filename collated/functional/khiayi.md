# khiayi
###### \java\seedu\address\logic\commands\AddCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AVAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ISBN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.account.PrivilegeLevel;
import seedu.address.model.book.Book;
import seedu.address.model.book.exceptions.DuplicateBookException;

//import static seedu.address.logic.parser.CliSyntax.;


/**
 * Adds a book to the catalogue.
 */
public class AddCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a book to the catalogue. "
        + "Parameters: "
        + PREFIX_TITLE + "TITLE "
        + PREFIX_AUTHOR + "AUTHOR "
        + PREFIX_ISBN + "ISBN "
        + PREFIX_AVAIL + "AVAIL "
        + "[" + PREFIX_TAG + "TAG]...\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_TITLE + "Animal Farm "
        + PREFIX_AUTHOR + "George Orwell "
        + PREFIX_ISBN + "9780736692427 "
        + PREFIX_AVAIL + "Borrowed "
        + PREFIX_TAG + "political "
        + PREFIX_TAG + "satire ";

    public static final String MESSAGE_SUCCESS = "New book added: %1$s";
    public static final String MESSAGE_DUPLICATE_BOOK = "This book already exists in the catalogue";

    public static final PrivilegeLevel PRIVILEGE_LEVEL = Model.PRIVILEGE_LEVEL_LIBRARIAN;

    private final Book toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Book}
     */
    public AddCommand(Book book) {
        requireNonNull(book);
        toAdd = book;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addBook(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateBookException e) {
            throw new CommandException(MESSAGE_DUPLICATE_BOOK);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof AddCommand // instanceof handles nulls
            && toAdd.equals(((AddCommand) other).toAdd));
    }

```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AVAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ISBN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BOOKS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.account.PrivilegeLevel;
import seedu.address.model.book.Author;
import seedu.address.model.book.Avail;
import seedu.address.model.book.Book;
import seedu.address.model.book.Isbn;
import seedu.address.model.book.Title;
import seedu.address.model.book.exceptions.BookNotFoundException;
import seedu.address.model.book.exceptions.DuplicateBookException;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing book in the catalogue.
 */
public class EditCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the book identified "
        + "by the index number used in the last book listing. "
        + "Existing values will be overwritten by the input values.\n"
        + "Parameters: INDEX (must be a positive integer) "
        + "[" + PREFIX_TITLE + "TITLE] "
        + "[" + PREFIX_AUTHOR + "AUTHOR] "
        + "[" + PREFIX_ISBN + "ISBN] "
        + "[" + PREFIX_AVAIL + "AVAIL] "
        + "[" + PREFIX_TAG + "TAG]...\n"
        + "Example: " + COMMAND_WORD + " 1 "
        + PREFIX_ISBN + "9780736692426 "
        + PREFIX_AVAIL + "Borrowed";

    public static final String MESSAGE_EDIT_BOOK_SUCCESS = "Edited Book: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_BOOK = "This book already exists in the catalogue.";

    public static final PrivilegeLevel PRIVILEGE_LEVEL = Model.PRIVILEGE_LEVEL_LIBRARIAN;

    private final Index index;
    private final EditBookDescriptor editBookDescriptor;

    private Book bookToEdit;
    private Book editedBook;

    /**
     * @param index              of the book in the filtered book list to edit
     * @param editBookDescriptor details to edit the book with
     */
    public EditCommand(Index index, EditBookDescriptor editBookDescriptor) {
        requireNonNull(index);
        requireNonNull(editBookDescriptor);

        this.index = index;
        this.editBookDescriptor = new EditBookDescriptor(editBookDescriptor);
    }

    /**
     * Creates and returns a {@code Book} with the details of {@code bookToEdit}
     * edited with {@code editBookDescriptor}.
     */
    private static Book createEditedBook(Book bookToEdit, EditBookDescriptor editBookDescriptor) {
        assert bookToEdit != null;

        Title updatedTitle = editBookDescriptor.getTitle().orElse(bookToEdit.getTitle());
        Isbn updatedIsbn = editBookDescriptor.getIsbn().orElse(bookToEdit.getIsbn());
        Avail updatedAvail = editBookDescriptor.getAvail().orElse(bookToEdit.getAvail());
        Author updatedAuthor = editBookDescriptor.getAuthor().orElse(bookToEdit.getAuthor());
        Set<Tag> updatedTags = editBookDescriptor.getTags().orElse(bookToEdit.getTags());

        return new Book(updatedTitle, updatedAuthor, updatedIsbn, updatedAvail, updatedTags);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updateBook(bookToEdit, editedBook);
        } catch (DuplicateBookException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_BOOK);
        } catch (BookNotFoundException pnfe) {
            throw new AssertionError("The target book cannot be missing");
        }
        model.updateFilteredBookList(PREDICATE_SHOW_ALL_BOOKS);
        return new CommandResult(String.format(MESSAGE_EDIT_BOOK_SUCCESS, editedBook));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Book> lastShownList = model.getFilteredBookList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
        }

        bookToEdit = lastShownList.get(index.getZeroBased());
        editedBook = createEditedBook(bookToEdit, editBookDescriptor);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
            && editBookDescriptor.equals(e.editBookDescriptor)
            && Objects.equals(bookToEdit, e.bookToEdit);
    }

```
###### \java\seedu\address\model\book\Author.java
``` java
package seedu.address.model.book;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Book's author in the catalogue.
 * Guarantees: immutable; is valid as declared in {@link #isValidAuthor(String)}
 */
public class Author {

    public static final String MESSAGE_AUTHOR_CONSTRAINTS =
        "Book author should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the author must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String AUTHOR_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String value;

    /**
     * Constructs an {@code Author}.
     *
     * @param author A valid author.
     */
    public Author(String author) {
        requireNonNull(author);
        checkArgument(isValidAuthor(author), MESSAGE_AUTHOR_CONSTRAINTS);
        this.value = author;
    }
```
###### \java\seedu\address\model\book\Avail.java
``` java
package seedu.address.model.book;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;


/**
 * Represents a Book's availability in the catalogue.
 * Guarantees: immutable; is valid as declared in {@link #isValidAvail(String)}
 */
public class Avail {
    public static final String AVAILABLE = "Available";
    public static final String BORROWED = "Borrowed";
    public static final String RESERVED = "Reserved";
    public static final String MESSAGE_AVAIL_CONSTRAINTS = "Book availability should be one of the following:\n "
        + "1. " + AVAILABLE + "\n"
        + "2. " + BORROWED + "\n"
        + "3. " + RESERVED + "\n";


    public final String value;

    /**
     * Constructs an {@code Avail}.
     *
     * @param avail A valid availability .
     */
    public Avail(String avail) {
        requireNonNull(avail);
        checkArgument(isValidAvail(avail), MESSAGE_AVAIL_CONSTRAINTS);
        this.value = avail;
    }

    /**
     * Returns if a given string is a valid book avail.
     */
    public static boolean isValidAvail(String test) {
        return test.equals(AVAILABLE)
            || test.equals(BORROWED)
            || test.equals(RESERVED);
    }

```
###### \java\seedu\address\model\book\Book.java
``` java
package seedu.address.model.book;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Book in the catalogue.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Book {

    private final Title title;
    private final Author author;
    private final Isbn isbn;
    private final Avail avail;

    private final UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Book(Title title, Author author, Isbn isbn, Avail avail, Set<Tag> tags) {
        requireAllNonNull(title, author, isbn, avail, tags);
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.avail = avail;
        // protect internal tags from changes in the arg list
        this.tags = new UniqueTagList(tags);
    }

    public Title getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    public Isbn getIsbn() {
        return isbn;
    }

    public Avail getAvail() {
        return avail;
    }

    /**
     * Returns true if this book's isbn is the same as the isbn provided
     *
     * @param isbn
     * @return
     */
    public boolean isbnMatches(Isbn isbn) {
        return this.isbn.equals(isbn);
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.toSet());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Book)) {
            return false;
        }

        Book otherBook = (Book) other;
        return otherBook.getTitle().equals(this.getTitle())
            && otherBook.getAuthor().equals(this.getAuthor())
            && otherBook.getIsbn().equals(this.getIsbn())
            && otherBook.getAvail().equals(this.getAvail());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, author, isbn, avail, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle())
            .append(" Author: ")
            .append(getAuthor())
            .append(" Isbn: ")
            .append(getIsbn())
            .append(" Avail: ")
            .append(getAvail())
            .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
```
###### \java\seedu\address\model\book\Isbn.java
``` java
package seedu.address.model.book;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Book's isbn number in the catalogue.
 * Guarantees: immutable; is valid as declared in {@link #isValidIsbn(String)}
 */
public class Isbn {

    public static final String MESSAGE_ISBN_CONSTRAINTS =
        "Isbn numbers can only contain numbers, and should be at 13 digits long";
    public static final String ISBN_VALIDATION_REGEX = "\\d{13}";
    public final String value;

    /**
     * Constructs a {@code Isbn}.
     *
     * @param isbn A valid isbn number.
     */
    public Isbn(String isbn) {
        requireNonNull(isbn);
        checkArgument(isValidIsbn(isbn), MESSAGE_ISBN_CONSTRAINTS);
        this.value = isbn;
    }
```
###### \java\seedu\address\model\book\Title.java
``` java
package seedu.address.model.book;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Book's name in the catalogue.
 * Guarantees: immutable; is valid as declared in {@link #isValidTitle(String)}
 */
public class Title {
```
###### \java\seedu\address\model\book\Title.java
``` java
    public static final String MESSAGE_TITLE_CONSTRAINTS =
        "Book titles should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the title must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String TITLE_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullTitle;

    /**
     * Constructs a {@code Title}.
     *
     * @param title A valid title.
     */
    public Title(String title) {
        requireNonNull(title);
        checkArgument(isValidTitle(title), MESSAGE_TITLE_CONSTRAINTS);
        this.fullTitle = title;
    }
```
