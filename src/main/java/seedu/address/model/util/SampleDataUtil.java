package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.Catalogue;
import seedu.address.model.ReadOnlyCatalogue;
import seedu.address.model.book.Address;
import seedu.address.model.book.Availability;
import seedu.address.model.book.Book;
import seedu.address.model.book.Title;
import seedu.address.model.book.Phone;
import seedu.address.model.book.exceptions.DuplicateBookException;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code Catalogue} with sample data.
 */
public class SampleDataUtil {
    public static Book[] getSampleBooks() {
        return new Book[] {
            new Book(new Title("Alex Yeoh"), new Phone("87438807"), new Availability("Available"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Book(new Title("Bernice Yu"), new Phone("99272758"), new Availability("Available"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Book(new Title("Charlotte Oliveiro"), new Phone("93210283"), new Availability("Borrowed"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Book(new Title("David Li"), new Phone("91031282"), new Availability("Borrowed"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Book(new Title("Irfan Ibrahim"), new Phone("92492021"), new Availability("Borrowed"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Book(new Title("Roy Balakrishnan"), new Phone("92624417"), new Availability("Borrowed"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
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
