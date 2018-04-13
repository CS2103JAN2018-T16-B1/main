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
import static seedu.address.model.book.Avail.AVAILABLE;

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

