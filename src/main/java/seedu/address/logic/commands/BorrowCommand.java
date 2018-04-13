package seedu.address.logic.commands;

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

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.book.Avail.BORROWED;

/**
 * Borrows a book
 */
public class BorrowCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "borrow";
    public static final String MESSAGE_SUCCESS = "New book borrowed: %1$s";
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

    private static Book createBorrowedBook(Book bookToBorrow) {
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
        return new CommandResult(String.format(MESSAGE_SUCCESS, bookToBorrow));
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

