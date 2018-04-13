package seedu.address.model.util;

import seedu.address.model.Catalogue;
import seedu.address.model.ReadOnlyCatalogue;
import seedu.address.model.book.Author;
import seedu.address.model.book.Avail;
import seedu.address.model.book.Book;
import seedu.address.model.book.Isbn;
import seedu.address.model.book.Title;
import seedu.address.model.book.exceptions.DuplicateBookException;
import seedu.address.model.tag.Tag;

import java.util.HashSet;
import java.util.Set;

/**
 * Contains utility methods for populating {@code Catalogue} with sample data.
 */
public class SampleDataUtil {
    public static Book[] getSampleBooks() {
        return new Book[] {
            new Book(new Title("Animal Farm"), new Author("George Orwell"), new Isbn("9780736692427"),
                new Avail("Available"), getTagSet("political", "satire")),
            new Book(new Title("Breaking Dawn"), new Author("Stephenie Meyer"), new Isbn("9780316067928"),
                new Avail("Available"), getTagSet("fiction")),
            new Book(new Title("California Girl"), new Author("Jefferson Parker"), new Isbn("9780060562373"),
                new Avail("Borrowed"),
                getTagSet("fiction", "mystery")),
            new Book(new Title("Delirium"), new Author("Lauren Oliver"), new Isbn("9780061726835"),
                new Avail("Borrowed"), getTagSet("dystopian", "fiction")),
            new Book(new Title("Invisible Man"), new Author("Ralph Ellison"), new Isbn("9780140023350"),
                new Avail("Borrowed"),
                getTagSet("fiction")),
            new Book(new Title("Romeo and Juliet"), new Author("William Shakespeare"), new Isbn("9780743477116"),
                new Avail("Borrowed"),
                getTagSet("classics", "romance"))
        };
    }

    public static ReadOnlyCatalogue getSampleCatalogue() {
        try {
            Catalogue sampleAb = new Catalogue();
            for (Book sampleBook : getSampleBooks()) {
                sampleAb.addBook(sampleBook);
            }
            return sampleAb;
        } catch (DuplicateBookException e) {
            throw new AssertionError("sample data cannot contain duplicate books", e);
        }
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        HashSet<Tag> tags = new HashSet<>();
        for (String s : strings) {
            tags.add(new Tag(s));
        }

        return tags;
    }

}
