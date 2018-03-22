package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.BookCard;

/**
 * Represents a selection change in the Book List Panel
 */
public class BookPanelSelectionChangedEvent extends BaseEvent {


    private final BookCard newSelection;

    public BookPanelSelectionChangedEvent(BookCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public BookCard getNewSelection() {
        return newSelection;
    }
}
