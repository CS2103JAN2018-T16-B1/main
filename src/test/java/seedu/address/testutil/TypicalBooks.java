package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AVAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AVAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ISBN_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ISBN_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.Catalogue;
import seedu.address.model.book.Book;
import seedu.address.model.book.exceptions.DuplicateBookException;

/**
 * A utility class containing a list of {@code Book} objects to be used in tests.
 */
public class TypicalBooks {

    public static final Book ALICE = new BookBuilder().withTitle("Alice Pauline")
            .withAuthor("Pauline Alice")
            .withAvail("Available")
            .withIsbn("85355255")
            .withTags("friends").build();
    public static final Book BENSON = new BookBuilder().withTitle("Benson Meier")
            .withAuthor("Meier Benson")
            .withAvail("Available")
            .withIsbn("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Book CARL = new BookBuilder().withTitle("Carl Kurz")
            .withAuthor("Kurz Carl")
            .withIsbn("95352563")
            .withAvail("Available").build();
    public static final Book DANIEL = new BookBuilder().withTitle("Daniel Meier")
            .withAuthor("Meier Daniel")
            .withIsbn("87652533")
            .withAvail("Available").build();
    public static final Book ELLE = new BookBuilder().withTitle("Elle Meyer")
            .withAuthor("Meyer Elle")
            .withIsbn("9482224")
            .withAvail("Available").build();
    public static final Book FIONA = new BookBuilder().withTitle("Fiona Kunz")
            .withAuthor("Kunz Fiona")
            .withIsbn("9482427")
            .withAvail("Available").build();
    public static final Book GEORGE = new BookBuilder().withTitle("George Best")
            .withAuthor("Best George")
            .withIsbn("9482442")
            .withAvail("Available").build();

    // Manually added
    public static final Book HOON = new BookBuilder().withTitle("Hoon Meier")
            .withAuthor("Meier Hoon")
            .withIsbn("8482424")
            .withAvail("Available").build();
    public static final Book IDA = new BookBuilder().withTitle("Ida Mueller")
            .withAuthor("Mueller Ida")
            .withIsbn("8482131")
            .withAvail("Available").build();

    // Manually added - Book's details found in {@code CommandTestUtil}
    public static final Book AMY = new BookBuilder().withTitle(VALID_TITLE_AMY)
            .withAuthor(VALID_AUTHOR_AMY)
            .withIsbn(VALID_ISBN_AMY)
            .withAvail(VALID_AVAIL_AMY)
            .withTags(VALID_TAG_FRIEND).build();
    public static final Book BOB = new BookBuilder().withTitle(VALID_TITLE_BOB)
            .withAuthor(VALID_AUTHOR_BOB)
            .withIsbn(VALID_ISBN_BOB)
            .withAvail(VALID_AVAIL_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalBooks() {} // prevents instantiation

    /**
     * Returns an {@code Catalogue} with all the typical books.
     */
    public static Catalogue getTypicalCatalogue() {
        Catalogue ab = new Catalogue();
        for (Book book : getTypicalBooks()) {
            try {
                ab.addBook(book);
            } catch (DuplicateBookException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Book> getTypicalBooks() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
