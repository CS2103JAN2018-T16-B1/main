package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Catalogue;
import seedu.address.model.book.Book;
import seedu.address.model.book.exceptions.DuplicateBookException;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building Catalogue objects.
 * Example usage: <br>
 *     {@code Catalogue ab = new CatalogueBuilder().withBook("John", "Doe").withTag("Friend").build();}
 */
public class CatalogueBuilder {

    private Catalogue catalogue;

    public CatalogueBuilder() {
        catalogue = new Catalogue();
    }

    public CatalogueBuilder(Catalogue catalogue) {
        this.catalogue = catalogue;
    }

    /**
     * Adds a new {@code Book} to the {@code Catalogue} that we are building.
     */
    public CatalogueBuilder withBook(Book book) {
        try {
            catalogue.addBook(book);
        } catch (DuplicateBookException dpe) {
            throw new IllegalArgumentException("book is expected to be unique.");
        }
        return this;
    }

    /**
     * Parses {@code tagName} into a {@code Tag} and adds it to the {@code Catalogue} that we are building.
     */
    public CatalogueBuilder withTag(String tagName) {
        try {
            catalogue.addTag(new Tag(tagName));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tagName is expected to be valid.");
        }
        return this;
    }

    public Catalogue build() {
        return catalogue;
    }
}
