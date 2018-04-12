package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.book.Avail.BORROWED;
import static seedu.address.model.book.Avail.RESERVED;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.account.PrivilegeLevel;
import seedu.address.model.book.*;
import seedu.address.model.book.exceptions.BookNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Reserves a book
 */
public class ReserveCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "reserve";
    public static final String MESSAGE_SUCCESS = "Book reserved: %1$s";
    public static final String MESSAGE_FAILURE = "Book not available for reserving!";
    public static final String MESSAGE_ALREADY_AVAILABLE = "Do not need to reserve book as book is already available.";
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
            model.reserveBook(bookToReserve,reservedBook);
        } catch (BookNotFoundException pnfe) {
            throw new CommandException(MESSAGE_FAILURE);
        }
         catch (BookAlreadyAvailableException abc){
            throw new CommandException(MESSAGE_ALREADY_AVAILABLE);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, bookToReserve));
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
