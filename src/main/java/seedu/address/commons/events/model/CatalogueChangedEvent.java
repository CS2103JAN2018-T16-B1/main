package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyCatalogue;

/** Indicates the Catalogue in the model has changed*/
public class CatalogueChangedEvent extends BaseEvent {

    public final ReadOnlyCatalogue data;

    public CatalogueChangedEvent(ReadOnlyCatalogue data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of books " + data.getBookList().size() + ", number of tags " + data.getTagList().size();
    }
}
