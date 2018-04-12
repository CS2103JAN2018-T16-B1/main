package seedu.address.model.book;

import seedu.address.commons.exceptions.DuplicateDataException;

public class DuplicateBookException extends DuplicateDataException {
    public DuplicateBookException() {
        super("Operation would result in duplicate book");
    }
}

