package seedu.address.model.book.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate Book objects.
 */
public class DuplicateBookException extends DuplicateDataException {
    public DuplicateBookException() {
        super("Operation would result in duplicate books");
    }
}
