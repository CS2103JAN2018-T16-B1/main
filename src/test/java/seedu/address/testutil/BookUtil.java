package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AVAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ISBN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.book.Book;

/**
 * A utility class for Book.
 */
public class BookUtil {

    /**
     * Returns an add command string for adding the {@code book}.
     */
    public static String getAddCommand(Book book) {
        return AddCommand.COMMAND_WORD + " " + getBookDetails(book);
    }

    /**
     * Returns the part of command string for the given {@code book}'s details.
     */
    public static String getBookDetails(Book book) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_TITLE + book.getTitle().fullTitle + " ");
        sb.append(PREFIX_ISBN + book.getIsbn().value + " ");
        sb.append(PREFIX_AVAIL + book.getAvail().getValue() + " ");
        sb.append(PREFIX_AUTHOR + book.getAuthor().value + " ");
        book.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }
}
