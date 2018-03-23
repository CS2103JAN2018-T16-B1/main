package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.Catalogue;
import seedu.address.model.ReadOnlyCatalogue;
import seedu.address.model.book.Author;
import seedu.address.model.book.Avail;
import seedu.address.model.book.Book;
import seedu.address.model.book.Isbn;
import seedu.address.model.book.Title;
import seedu.address.model.book.exceptions.DuplicateBookException;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code Catalogue} with sample data.
 */
public class SampleDataUtil {
    public static Book[] getSampleBooks() {
        return new Book[] {
            new Book(new Title("Alex Yeoh"), new Author("Yeoh Alex"), new Isbn("87438807"), new Avail("Available"),
                    getTagSet("friends")),
            new Book(new Title("Bernice Yu"), new Author("Yu Bernice"), new Isbn("99272758"), new Avail("Available"),
                    getTagSet("colleagues", "friends")),
            new Book(new Title("Charlotte Oliveiro"), new Author("Oliveiro Charlotte"), new Isbn("93210283"),
                    new Avail("Borrowed"),
                    getTagSet("neighbours")),
            new Book(new Title("David Li"), new Author("Li David"), new Isbn("91031282"), new Avail("Borrowed"),
                    getTagSet("family")),
            new Book(new Title("Irfan Ibrahim"), new Author("Ibrahim Irfan"), new Isbn("92492021"),
                    new Avail("Borrowed"),
                    getTagSet("classmates")),
            new Book(new Title("Roy Balakrishnan"), new Author("Balakrishnan Roy"), new Isbn("92624417"),
                    new Avail("Borrowed"),
                    getTagSet("colleagues"))
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
