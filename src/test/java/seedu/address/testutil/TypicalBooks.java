package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AVAILIABILITY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AVAILIABILITY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

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

    public static final Book ALICE = new BookBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withAvailability("Available")
            .withPhone("85355255")
            .withTags("friends").build();
    public static final Book BENSON = new BookBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withAvailability("Available").withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Book CARL = new BookBuilder().withName("Carl Kurz").withPhone("95352563")
            .withAvailability("Available").withAddress("wall street").build();
    public static final Book DANIEL = new BookBuilder().withName("Daniel Meier").withPhone("87652533")
            .withAvailability("Available").withAddress("10th street").build();
    public static final Book ELLE = new BookBuilder().withName("Elle Meyer").withPhone("9482224")
            .withAvailability("Available").withAddress("michegan ave").build();
    public static final Book FIONA = new BookBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withAvailability("Available").withAddress("little tokyo").build();
    public static final Book GEORGE = new BookBuilder().withName("George Best").withPhone("9482442")
            .withAvailability("Available").withAddress("4th street").build();

    // Manually added
    public static final Book HOON = new BookBuilder().withName("Hoon Meier").withPhone("8482424")
            .withAvailability("Available").withAddress("little india").build();
    public static final Book IDA = new BookBuilder().withName("Ida Mueller").withPhone("8482131")
            .withAvailability("Available").withAddress("chicago ave").build();

    // Manually added - Book's details found in {@code CommandTestUtil}
    public static final Book AMY = new BookBuilder().withName(VALID_TITLE_AMY).withPhone(VALID_PHONE_AMY)
            .withAvailability(VALID_AVAILIABILITY_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Book BOB = new BookBuilder().withName(VALID_TITLE_BOB).withPhone(VALID_PHONE_BOB)
            .withAvailability(VALID_AVAILIABILITY_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
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
