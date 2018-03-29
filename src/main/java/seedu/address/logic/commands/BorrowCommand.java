package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.book.Book;
import seedu.address.model.book.exceptions.BookNotFoundException;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class BorrowCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "borrow";
    public static final String MESSAGE_SUCCESS = "Book borrowed : %l%s";
    public static final String MESSAGE_FAILURE = "Book not available for borrowing!";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Borrows the book identified by the index number used in the last book listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final int PRIVILEGE_LEVEL = Model.PRIVILEGE_LEVEL_LIBRARIAN;

    private final Index targetIndex;

    private Book bookToBorrow;

    public BorrowCommand (Index targetIndex) {this.targetIndex = targetIndex;}

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(bookToBorrow);
        try {
            model.borrowBook(bookToBorrow);
        } catch (BookNotFoundException pnfe) { //Find out what pnfe is from deleteCommand
            throw new CommandException(MESSAGE_FAILURE);
        }
        return new CommandResult(String.format (MESSAGE_SUCCESS, bookToBorrow));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Book> lastShownList = model.getFilteredBookList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
        }

        bookToBorrow = lastShownList.get(targetIndex.getZeroBased());
    }

    @Override
    public int getPrivilegeLevel() {
        return PRIVILEGE_LEVEL;
    }
}
