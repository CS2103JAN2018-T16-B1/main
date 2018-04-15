# LeKhangTai
###### /java/seedu/address/logic/parser/ReserveCommandParser.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ReserveCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and create a new ReserveCommand object
 */
public class ReserveCommandParser implements Parser<ReserveCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ReserveCommand
     * and returns an ReserveCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ReserveCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ReserveCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReserveCommand.MESSAGE_USAGE));
        }
    }
}

```
###### /java/seedu/address/logic/parser/BorrowCommandParser.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.BorrowCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and create a new BorrowCommand object
 */

public class BorrowCommandParser implements Parser<BorrowCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the BorrowCommand
     * and returns an BorrowCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public BorrowCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new BorrowCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BorrowCommand.MESSAGE_USAGE));
        }
    }
}

```
###### /java/seedu/address/logic/parser/ReturnCommandParser.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ReturnCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and create a new ReturnCommand object
 */
public class ReturnCommandParser implements Parser<ReturnCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ReturnCommand
     * and returns an ReturnCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */

    public ReturnCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ReturnCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReturnCommand.MESSAGE_USAGE));
        }
    }

}

```
###### /java/seedu/address/logic/commands/ReturnCommand.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.address.model.book.Avail.AVAILABLE;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.account.PrivilegeLevel;
import seedu.address.model.book.Author;
import seedu.address.model.book.Avail;
import seedu.address.model.book.Book;
import seedu.address.model.book.Isbn;
import seedu.address.model.book.Title;
import seedu.address.model.book.exceptions.BookNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Un-mark a borrowed book to make it available for borrowing
 */

public class ReturnCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "return";

    public static final String MESSAGE_RETURN_BOOK_SUCCESS = "Book is returned: %1$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Returns book whose title matches the input \n"
        + "Parameters: INDEX (must be a positive integer) \n"
        + "Example: " + COMMAND_WORD + " Harry Potter and the Half Blood Prince";

    public static final String MESSAGE_BOOK_CANNOT_BE_RETURNED = "Book cannot be returned as it is already available";

    public static final PrivilegeLevel PRIVILEGE_LEVEL = Model.PRIVILEGE_LEVEL_LIBRARIAN;

    private final Index targetIndex;

    private Book bookToReturn;
    private Book returnedBook;

    public ReturnCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    /**
     * @param bookToBorrow
     * @return duplicated book with Available Availability
     */
    private static Book createReturnedBook(Book bookToBorrow) {
        assert bookToBorrow != null;

        Title updatedTitle = bookToBorrow.getTitle();
        Isbn updatedIsbn = bookToBorrow.getIsbn();
        Avail updatedAvail = new Avail(AVAILABLE);
        Author updatedAuthor = bookToBorrow.getAuthor();
        Set<Tag> updatedTags = bookToBorrow.getTags();

        return new Book(updatedTitle, updatedAuthor, updatedIsbn, updatedAvail, updatedTags);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(bookToReturn);
        try {
            model.returnBook(bookToReturn, returnedBook);
            return new CommandResult(String.format(MESSAGE_RETURN_BOOK_SUCCESS, bookToReturn));
        } catch (BookNotFoundException pnfe) {
            throw new CommandException(MESSAGE_BOOK_CANNOT_BE_RETURNED);
        }
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Book> lastShownList = model.getFilteredBookList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
        }

        bookToReturn = lastShownList.get(targetIndex.getZeroBased());
        returnedBook = createReturnedBook(bookToReturn);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ReturnCommand // instanceof handles nulls
            && this.targetIndex.equals(((ReturnCommand) other).targetIndex) // state check
            && Objects.equals(this.bookToReturn, ((ReturnCommand) other).bookToReturn));
    }

    @Override
    public PrivilegeLevel getPrivilegeLevel() {
        return PRIVILEGE_LEVEL;
    }
}

```
###### /java/seedu/address/logic/commands/BorrowCommand.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.address.model.book.Avail.BORROWED;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.account.PrivilegeLevel;
import seedu.address.model.book.Author;
import seedu.address.model.book.Avail;
import seedu.address.model.book.Book;
import seedu.address.model.book.Isbn;
import seedu.address.model.book.Title;
import seedu.address.model.book.exceptions.BookNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Borrows a book
 */
public class BorrowCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "borrow";
    public static final String MESSAGE_BORROW_BOOK_SUCCESS = "New book borrowed: %1$s";
    public static final String MESSAGE_FAILURE = "Book not available for borrowing!";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Borrows the book identified by the index number used in the last book listing.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1";
    public static final PrivilegeLevel PRIVILEGE_LEVEL = Model.PRIVILEGE_LEVEL_STUDENT;

    private final Index targetIndex;

    private Book bookToBorrow;
    private Book borrowedBook;


    public BorrowCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    /**
     * @param bookToBorrow
     * @return duplicated book with Borrowed Availability
     */
    public Book createBorrowedBook(Book bookToBorrow) {
        assert bookToBorrow != null;

        Title updatedTitle = bookToBorrow.getTitle();
        Isbn updatedIsbn = bookToBorrow.getIsbn();
        Avail updatedAvail = new Avail(BORROWED);
        Author updatedAuthor = bookToBorrow.getAuthor();
        Set<Tag> updatedTags = bookToBorrow.getTags();

        return new Book(updatedTitle, updatedAuthor, updatedIsbn, updatedAvail, updatedTags);
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);

        try {
            model.borrowBook(bookToBorrow, borrowedBook);
        } catch (BookNotFoundException pnfe) {
            throw new CommandException(MESSAGE_FAILURE);
        }
        return new CommandResult(String.format(MESSAGE_BORROW_BOOK_SUCCESS, bookToBorrow));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Book> lastShownList = model.getFilteredBookList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
        }
        bookToBorrow = lastShownList.get(targetIndex.getZeroBased());
        borrowedBook = createBorrowedBook(bookToBorrow);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BorrowCommand that = (BorrowCommand) o;
        return Objects.equals(targetIndex, that.targetIndex)
            && Objects.equals(bookToBorrow, that.bookToBorrow);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetIndex, bookToBorrow);
    }

    @Override
    public PrivilegeLevel getPrivilegeLevel() {
        return PRIVILEGE_LEVEL;
    }
}

```
###### /java/seedu/address/logic/commands/ReserveCommand.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.address.model.book.Avail.RESERVED;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.account.PrivilegeLevel;
import seedu.address.model.book.Author;
import seedu.address.model.book.Avail;
import seedu.address.model.book.Book;
import seedu.address.model.book.Isbn;
import seedu.address.model.book.Title;
import seedu.address.model.book.exceptions.BookNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Reserves a book
 */
public class ReserveCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "reserve";
    public static final String MESSAGE_RESERVE_BOOK_SUCCESS = "Book reserved: %1$s";
    public static final String MESSAGE_FAILURE = "Book not available for reserving!";
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Reserves the book identified by the index number used in the last book listing.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1";

    public static final PrivilegeLevel PRIVILEGE_LEVEL = Model.PRIVILEGE_LEVEL_STUDENT;

    private final Index targetIndex;

    private Book bookToReserve;
    private Book reservedBook;

    public ReserveCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    /**
     * @param bookToReserve
     * @return duplicated book with Reserved Availability
     */
    private static Book createReservedBook(Book bookToReserve) {
        assert bookToReserve != null;

        Title updatedTitle = bookToReserve.getTitle();
        Isbn updatedIsbn = bookToReserve.getIsbn();
        Avail updatedAvail = new Avail(RESERVED);
        Author updatedAuthor = bookToReserve.getAuthor();
        Set<Tag> updatedTags = bookToReserve.getTags();

        return new Book(updatedTitle, updatedAuthor, updatedIsbn, updatedAvail, updatedTags);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(bookToReserve);
        try {
            model.reserveBook(bookToReserve, reservedBook);
        } catch (BookNotFoundException pnfe) {
            throw new CommandException(MESSAGE_FAILURE);
        }
        return new CommandResult(String.format(MESSAGE_RESERVE_BOOK_SUCCESS, bookToReserve));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Book> lastShownList = model.getFilteredBookList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
        }
        bookToReserve = lastShownList.get(targetIndex.getZeroBased());
        reservedBook = createReservedBook(bookToReserve);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetIndex, bookToReserve);
    }

    @Override
    public PrivilegeLevel getPrivilegeLevel() {
        return PRIVILEGE_LEVEL;
    }
}

```
###### /java/seedu/address/model/Catalogue.java
``` java
    /**
     * @param target book that is selected by index to return
     * @param returnedBook duplicated book that will replace the original book
     * @throws BookNotFoundException
     */
    public void returnBook(Book target, Book returnedBook)
        throws BookNotFoundException {
        requireNonNull(returnedBook);

        Book syncedEditedBook = syncWithMasterTagList(returnedBook);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any book
        // in the book list.
        books.replaceReturnedBook(target, syncedEditedBook);
    }

    /**
     * @param target book that is selected by index to borrow
     * @param borrowedBook duplicated book that will replace the original book
     * @throws BookNotFoundException
     */
    public void borrowBook(Book target, Book borrowedBook)
        throws BookNotFoundException {
        requireNonNull(borrowedBook);

        Book syncedEditedBook = syncWithMasterTagList(borrowedBook);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any book
        // in the book list.
        books.replaceBorrowedBook(target, syncedEditedBook);
    }

    /**
     * @param target book that is selected by index to reserve
     * @param reservedBook duplicated book that will replace the original book
     * @throws BookNotFoundException
     */
    public void reserveBook(Book target, Book reservedBook)
        throws BookNotFoundException {
        requireNonNull(reservedBook);

        Book syncedEditedBook = syncWithMasterTagList(reservedBook);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any book
        // in the book list.
        books.replaceReservedBook(target, syncedEditedBook);
    }
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public synchronized void returnBook(Book target, Book returnedBook) throws BookNotFoundException {
        catalogue.returnBook(target, returnedBook);
        indicateCatalogueChanged();
    }
    @Override
    public synchronized void borrowBook(Book target, Book borrowedBook) throws BookNotFoundException {
        catalogue.borrowBook(target, borrowedBook);
        updateFilteredBookList(PREDICATE_SHOW_ALL_BOOKS);
        indicateCatalogueChanged();
    }
    @Override
    public synchronized void reserveBook(Book target, Book reservedBook) throws BookNotFoundException {
        catalogue.reserveBook(target, reservedBook);
        updateFilteredBookList(PREDICATE_SHOW_ALL_BOOKS);
        indicateCatalogueChanged();
    }
```
###### /java/seedu/address/model/book/UniqueBookList.java
``` java
    /**
     * @param target select the index book to be returned
     * @param returnedBook duplicated book that will replace original book
     * @throws BookNotFoundException
     */
    public void replaceReturnedBook(Book target, Book returnedBook) throws BookNotFoundException {
        requireNonNull(returnedBook);
        int index = internalList.indexOf(target);
        String status = target.getAvail().toString();
        switch (status) {
        case (BORROWED):
            internalList.set(index, returnedBook);
            break;
        case (RESERVED):
            internalList.set(index, returnedBook);
            break;

        default:
            throw new BookNotFoundException();
        }
    }

    /**
     * @param target select the index book to be borrowed
     * @param borrowedBook duplicated book that will replace original book
     * @throws BookNotFoundException
     */
    public void replaceBorrowedBook(Book target, Book borrowedBook) throws BookNotFoundException {
        requireNonNull(borrowedBook);

        int index = internalList.indexOf(target);
        String status = target.getAvail().toString();
        switch (status) {
        case (AVAILABLE):
            internalList.set(index, borrowedBook);
            break;

        default:
            throw new BookNotFoundException();
        }
    }

    /**
     * @param target select the index book to be reserved
     * @param reservedBook duplicated book that will replace original book
     * @throws BookNotFoundException
     */
    public void replaceReservedBook(Book target, Book reservedBook) throws BookNotFoundException {

        requireNonNull(reservedBook);
        int index = internalList.indexOf(target);
        String status = target.getAvail().toString();
        switch (status) {
        case (BORROWED):
            internalList.set(index, reservedBook);
            break;

        default:
            throw new BookNotFoundException();
        }
    }
```
