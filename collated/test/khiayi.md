# khiayi
###### \java\seedu\address\model\book\AuthorTest.java
``` java
package seedu.address.model.book;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class AuthorTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Author(null));
    }

    @Test
    public void constructor_invalidAuthor_throwsIllegalArgumentException() {
        String invalidAuthor = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Author(invalidAuthor));
    }

    @Test
    public void isValidAuthor() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> Author.isValidAuthor(null));

        // invalid name
        assertFalse(Author.isValidAuthor("")); // empty string
        assertFalse(Author.isValidAuthor(" ")); // spaces only
        assertFalse(Author.isValidAuthor("^")); // only non-alphanumeric characters
        assertFalse(Author.isValidAuthor("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Author.isValidAuthor("peter jack")); // alphabets only
        assertTrue(Author.isValidAuthor("12345")); // numbers only
        assertTrue(Author.isValidAuthor("peter the 2nd")); // alphanumeric characters
        assertTrue(Author.isValidAuthor("Capital Tan")); // with capital letters
        assertTrue(Author.isValidAuthor("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
```
###### \java\seedu\address\model\book\AvailTest.java
``` java
package seedu.address.model.book;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class AvailTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Avail(null));
    }

    @Test
    public void constructor_invalidAvail_throwsIllegalArgumentException() {
        String invalidAvail = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Avail(invalidAvail));
    }

    @Test
    public void isValidAvail() {
        // null avail
        Assert.assertThrows(NullPointerException.class, () -> Avail.isValidAvail(null));

        // blank avail
        assertFalse(Avail.isValidAvail("")); // empty string
        assertFalse(Avail.isValidAvail(" ")); // spaces only

        // valid avail
        assertTrue(Avail.isValidAvail("Reserved"));  // Reserved
        assertTrue(Avail.isValidAvail("Borrowed"));  // Borrowed
        assertTrue(Avail.isValidAvail("Available"));  // Available
        assertTrue(Avail.isValidAvail("Borrowed and Reserved")); // Borrowed and Reserved
    }
}
```
###### \java\seedu\address\model\book\IsbnTest.java
``` java
package seedu.address.model.book;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class IsbnTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Isbn(null));
    }

    @Test
    public void constructor_invalidIsbn_throwsIllegalArgumentException() {
        String invalidIsbn = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Isbn(invalidIsbn));
    }

    @Test
    public void isValidIsbn() {
        // null isbn
        Assert.assertThrows(NullPointerException.class, () -> Isbn.isValidIsbn(null));

        // invalid isbn numbers
        assertFalse(Isbn.isValidIsbn("")); // empty string
        assertFalse(Isbn.isValidIsbn(" ")); // spaces only
        assertFalse(Isbn.isValidIsbn("91")); // less than 13 numbers
        assertFalse(Isbn.isValidIsbn("phone")); // non-numeric
        assertFalse(Isbn.isValidIsbn("978073669242a")); // alphabets within digits
        assertFalse(Isbn.isValidIsbn("9780736 692427")); // spaces within digits
        assertFalse(Isbn.isValidIsbn("97807366924271")); // more than 13 numbers

        // valid isbn numbers
        assertTrue(Isbn.isValidIsbn("9780736692427")); // 13 isbn numbers
    }
}
```
###### \java\seedu\address\model\book\TitleContainsKeywordsPredicateTest.java
``` java
package seedu.address.model.book;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.BookBuilder;

public class TitleContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TitleContainsKeywordsPredicate firstPredicate = new TitleContainsKeywordsPredicate(firstPredicateKeywordList);
        TitleContainsKeywordsPredicate secondPredicate = new TitleContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TitleContainsKeywordsPredicate firstPredicateCopy;
        firstPredicateCopy = new TitleContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different book -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        TitleContainsKeywordsPredicate predicate;
        predicate = new TitleContainsKeywordsPredicate(Collections.singletonList("Animal"));
        assertTrue(predicate.test(new BookBuilder().withTitle("Animal Breaking").build()));

        // Multiple keywords
        predicate = new TitleContainsKeywordsPredicate(Arrays.asList("Animal", "Breaking"));
        assertTrue(predicate.test(new BookBuilder().withTitle("Animal Breaking").build()));

        // Only one matching keyword
        predicate = new TitleContainsKeywordsPredicate(Arrays.asList("Breaking", "Carol"));
        assertTrue(predicate.test(new BookBuilder().withTitle("Animal Carol").build()));

        // Mixed-case keywords
        predicate = new TitleContainsKeywordsPredicate(Arrays.asList("aNimal", "bREAKING"));
        assertTrue(predicate.test(new BookBuilder().withTitle("Animal Breaking").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TitleContainsKeywordsPredicate predicate = new TitleContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new BookBuilder().withTitle("Animal").build()));

        // Non-matching keyword
        predicate = new TitleContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new BookBuilder().withTitle("Animal Breaking").build()));

        // Keywords match isbn, avail and address, but does not match name
        predicate = new TitleContainsKeywordsPredicate(Arrays.asList("9780736692427", "Borrowed", "Main", "Street"));
        assertFalse(predicate.test(new BookBuilder().withTitle("Animal").withIsbn("9780736692427")
            .withAvail("Borrowed").withAuthor("Main Street").build()));
    }
}
```
###### \java\seedu\address\model\book\TitleTest.java
``` java
package seedu.address.model.book;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class TitleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Title(null));
    }

    @Test
    public void constructor_invalidTitle_throwsIllegalArgumentException() {
        String invalidTitle = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Title(invalidTitle));
    }

    @Test
    public void isValidTitle() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> Title.isValidTitle(null));

        // invalid name
        assertFalse(Title.isValidTitle("")); // empty string
        assertFalse(Title.isValidTitle(" ")); // spaces only
        assertFalse(Title.isValidTitle("^")); // only non-alphanumeric characters
        assertFalse(Title.isValidTitle("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Title.isValidTitle("peter jack")); // alphabets only
        assertTrue(Title.isValidTitle("12345")); // numbers only
        assertTrue(Title.isValidTitle("peter the 2nd")); // alphanumeric characters
        assertTrue(Title.isValidTitle("Capital Tan")); // with capital letters
        assertTrue(Title.isValidTitle("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
```
###### \java\seedu\address\storage\XmlAdaptedBookTest.java
``` java
package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedBook.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalBooks.BREAKING;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.book.Author;
import seedu.address.model.book.Avail;
import seedu.address.model.book.Isbn;
import seedu.address.model.book.Title;
import seedu.address.testutil.Assert;

public class XmlAdaptedBookTest {
    private static final String INVALID_TITLE = "R@chel";
    private static final String INVALID_AUTHOR = "!!!!";
    private static final String INVALID_ISBN = "+651234";
    private static final String INVALID_AVAIL = "not sure";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_TITLE = BREAKING.getTitle().toString();
    private static final String VALID_AUTHOR = BREAKING.getAuthor().toString();
    private static final String VALID_ISBN = BREAKING.getIsbn().toString();
    private static final String VALID_AVAIL = BREAKING.getAvail().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = BREAKING.getTags().stream()
        .map(XmlAdaptedTag::new)
        .collect(Collectors.toList());

    @Test
    public void toModelType_validBookDetails_returnsBook() throws Exception {
        XmlAdaptedBook book = new XmlAdaptedBook(BREAKING);
        assertEquals(BREAKING, book.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedBook book =
            new XmlAdaptedBook(INVALID_TITLE, VALID_AUTHOR, VALID_ISBN, VALID_AVAIL, VALID_TAGS);
        String expectedMessage = Title.MESSAGE_TITLE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedBook book = new XmlAdaptedBook(null, VALID_AUTHOR, VALID_ISBN, VALID_AVAIL, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

    @Test
    public void toModelType_invalidIsbn_throwsIllegalValueException() {
        XmlAdaptedBook book =
            new XmlAdaptedBook(VALID_TITLE, VALID_AUTHOR, INVALID_ISBN, VALID_AVAIL, VALID_TAGS);
        String expectedMessage = Isbn.MESSAGE_ISBN_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

    @Test
    public void toModelType_nullIsbn_throwsIllegalValueException() {
        XmlAdaptedBook book = new XmlAdaptedBook(VALID_TITLE, VALID_AUTHOR, null, VALID_AVAIL, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Isbn.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

    @Test
    public void toModelType_invalidAvail_throwsIllegalValueException() {
        XmlAdaptedBook book =
            new XmlAdaptedBook(VALID_TITLE, VALID_AUTHOR, VALID_ISBN, INVALID_AVAIL, VALID_TAGS);
        String expectedMessage = Avail.MESSAGE_AVAIL_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

    @Test
    public void toModelType_nullAvail_throwsIllegalValueException() {
        XmlAdaptedBook book = new XmlAdaptedBook(VALID_TITLE, VALID_AUTHOR, VALID_ISBN, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Avail.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

    @Test
    public void toModelType_invalidAuthor_throwsIllegalValueException() {
        XmlAdaptedBook book =
            new XmlAdaptedBook(VALID_TITLE, INVALID_AUTHOR, VALID_ISBN, VALID_AVAIL, VALID_TAGS);
        String expectedMessage = Author.MESSAGE_AUTHOR_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

    @Test
    public void toModelType_nullAuthor_throwsIllegalValueException() {
        XmlAdaptedBook book = new XmlAdaptedBook(VALID_TITLE, null, VALID_ISBN, VALID_AVAIL, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Author.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedBook book =
            new XmlAdaptedBook(VALID_TITLE, VALID_AUTHOR, VALID_ISBN, VALID_AVAIL, invalidTags);
        Assert.assertThrows(IllegalValueException.class, book::toModelType);
    }

}
```
###### \java\seedu\address\testutil\TypicalBooks.java
``` java
package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_XVI;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_YOU;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AVAIL_XVI;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AVAIL_YOU;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ISBN_XVI;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ISBN_YOU;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_DYSTOPIA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FICTION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_XVI;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_YOU;

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
    public static final Book ANIMAL = new BookBuilder().withTitle("Animal Farm")
        .withAuthor("George Orwell")
        .withAvail("Available")
        .withIsbn("9780736692427")
        .withTags("political", "satire").build();
    public static final Book BREAKING = new BookBuilder().withTitle("Breaking Dawn")
        .withAuthor("Stephenie Meyer")
        .withAvail("Available")
        .withIsbn("9780316067928")
        .withTags("fiction").build();
    public static final Book CALIFORNIA = new BookBuilder().withTitle("California Girl")
        .withAuthor("Jefferson Parker")
        .withIsbn("9780060562373")
        .withAvail("Available")
        .withTags("unlabelled").build();
    public static final Book DELIRIUM = new BookBuilder().withTitle("Delirium")
        .withAuthor("Lauren Oliver")
        .withIsbn("9780061726835")
        .withAvail("Available").build();
    public static final Book EMMA = new BookBuilder().withTitle("Emma")
        .withAuthor("Jane Austen")
        .withIsbn("9780141439587")
        .withAvail("Available").build();
    public static final Book FATEFUL = new BookBuilder().withTitle("Fateful")
        .withAuthor("Claudia Gray")
        .withIsbn("9780062006202")
        .withAvail("Available").build();
    public static final Book GONE = new BookBuilder().withTitle("Gone Girl")
        .withAuthor("Gillian Flynn")
        .withIsbn("9780753827666")
        .withAvail("Available").build();

    // Manually added
    public static final Book HOLES = new BookBuilder().withTitle("Holes")
        .withAuthor("Louis Sachar")
        .withIsbn("9780439244190")
        .withAvail("Available").build();
    public static final Book INVISIBLE = new BookBuilder().withTitle("Invisible Man")
        .withAuthor("Ralph Ellison")
        .withIsbn("9780140023350")
        .withAvail("Available").build();

    // Manually added - Book's details found in {@code CommandTestUtil}
    public static final Book XVI = new BookBuilder().withTitle(VALID_TITLE_XVI)
        .withAuthor(VALID_AUTHOR_XVI)
        .withIsbn(VALID_ISBN_XVI)
        .withAvail(VALID_AVAIL_XVI)
        .withTags(VALID_TAG_DYSTOPIA).build();
    public static final Book YOU = new BookBuilder().withTitle(VALID_TITLE_YOU)
        .withAuthor(VALID_AUTHOR_YOU)
        .withIsbn(VALID_ISBN_YOU)
        .withAvail(VALID_AVAIL_YOU)
        .withTags(VALID_TAG_FICTION)
        .build();

    public static final String KEYWORD_MATCHING_GIRL = "Girl"; // A keyword that matches GIRL
    public static final String KEYWORD_MATCHING_BREAKING = "Breaking"; // A keyword that matches BREAKING

    private TypicalBooks() {
    } // prevents instantiation

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
        return new ArrayList<>(Arrays.asList(ANIMAL, BREAKING, CALIFORNIA, DELIRIUM, EMMA, FATEFUL, GONE));
    }
}
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.AUTHOR_DESC_XVI;
import static seedu.address.logic.commands.CommandTestUtil.AUTHOR_DESC_YOU;
import static seedu.address.logic.commands.CommandTestUtil.AVAIL_DESC_XVI;
import static seedu.address.logic.commands.CommandTestUtil.AVAIL_DESC_YOU;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AUTHOR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AVAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ISBN_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TITLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.ISBN_DESC_XVI;
import static seedu.address.logic.commands.CommandTestUtil.ISBN_DESC_YOU;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_DYSTOPIA;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FICTION;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_XVI;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_YOU;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_XVI;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_YOU;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AVAIL_XVI;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AVAIL_YOU;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ISBN_XVI;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ISBN_YOU;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_DYSTOPIA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_XVI;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_YOU;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ISBN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalBooks.ANIMAL;
import static seedu.address.testutil.TypicalBooks.CALIFORNIA;
import static seedu.address.testutil.TypicalBooks.HOLES;
import static seedu.address.testutil.TypicalBooks.INVISIBLE;
import static seedu.address.testutil.TypicalBooks.KEYWORD_MATCHING_GIRL;
import static seedu.address.testutil.TypicalBooks.XVI;
import static seedu.address.testutil.TypicalBooks.YOU;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.book.Author;
import seedu.address.model.book.Avail;
import seedu.address.model.book.Book;
import seedu.address.model.book.Isbn;
import seedu.address.model.book.Title;
import seedu.address.model.book.exceptions.DuplicateBookException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.BookBuilder;
import seedu.address.testutil.BookUtil;

//import seedu.address.logic.commands.Command;

public class AddCommandSystemTest extends CatalogueSystemTest {

    @Test
    public void add() throws Exception {
        Model model = getModel();

        //Log in first
        executeCommand("login admin admin");


        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a book without tags to a non-empty catalogue, command with leading spaces and trailing spaces
         * -> added
         */
        Book toAdd = XVI;
        String command = "   " + AddCommand.COMMAND_WORD + "  " + TITLE_DESC_XVI + "  " + "   " + AUTHOR_DESC_XVI
            + " " + ISBN_DESC_XVI + " "
            + AVAIL_DESC_XVI + "   " + TAG_DESC_DYSTOPIA + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding XVI to the list -> XVI deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding XVI to the list -> XVI added again */
        command = RedoCommand.COMMAND_WORD;
        model.addBook(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a book with all fields same as another book in the catalogue except name -> not added */
        toAdd = new BookBuilder().withTitle(VALID_TITLE_YOU).withAuthor(VALID_AUTHOR_XVI)
            .withIsbn(VALID_ISBN_XVI).withAvail(VALID_AVAIL_XVI)
            .withTags(VALID_TAG_DYSTOPIA).build();
        command = AddCommand.COMMAND_WORD + TITLE_DESC_YOU + AUTHOR_DESC_XVI + ISBN_DESC_XVI + AVAIL_DESC_XVI
            + TAG_DESC_DYSTOPIA;
        assertCommandFailure(command, toAdd);

        /* Case: add a book with all fields same as another book in the catalogue except isbn -> added */
        toAdd = new BookBuilder().withTitle(VALID_TITLE_XVI).withAuthor(VALID_AUTHOR_XVI)
            .withIsbn(VALID_ISBN_YOU).withAvail(VALID_AVAIL_XVI)
            .withTags(VALID_TAG_DYSTOPIA).build();
        command = AddCommand.COMMAND_WORD + TITLE_DESC_XVI + AUTHOR_DESC_XVI + ISBN_DESC_YOU + AVAIL_DESC_XVI
            + TAG_DESC_DYSTOPIA;
        assertCommandSuccess(command, toAdd);

        /* Case: add a book with all fields same as another book in the catalogue except name and ISBN -> added */
        toAdd = new BookBuilder().withTitle(VALID_TITLE_YOU).withAuthor(VALID_AUTHOR_XVI)
            .withIsbn("1111111111111").withAvail(VALID_AVAIL_XVI)
            .withTags(VALID_TAG_DYSTOPIA).build();
        command = AddCommand.COMMAND_WORD + TITLE_DESC_YOU + AUTHOR_DESC_XVI + " " + PREFIX_ISBN + "1111111111111"
            + AVAIL_DESC_XVI + TAG_DESC_DYSTOPIA;
        assertCommandSuccess(command, toAdd);

        /* Case: add a book with all fields same as another book in the catalogue except avail -> not added */
        toAdd = new BookBuilder().withTitle(VALID_TITLE_XVI).withAuthor(VALID_AUTHOR_XVI)
            .withIsbn(VALID_ISBN_XVI).withAvail(VALID_AVAIL_YOU)
            .withTags(VALID_TAG_DYSTOPIA).build();
        command = AddCommand.COMMAND_WORD + TITLE_DESC_XVI + AUTHOR_DESC_XVI + ISBN_DESC_XVI + AVAIL_DESC_YOU
            + TAG_DESC_DYSTOPIA;
        assertCommandFailure(command, toAdd);

        /* Case: add a book with all fields same as another book in the catalogue except avail and ISBN -> added */
        toAdd = new BookBuilder().withTitle(VALID_TITLE_XVI).withAuthor(VALID_AUTHOR_XVI)
            .withIsbn("2222222222222").withAvail(VALID_AVAIL_YOU)
            .withTags(VALID_TAG_DYSTOPIA).build();
        command = AddCommand.COMMAND_WORD + TITLE_DESC_XVI + AUTHOR_DESC_XVI + " " + PREFIX_ISBN + "2222222222222"
            + AVAIL_DESC_YOU + TAG_DESC_DYSTOPIA;
        assertCommandSuccess(command, toAdd);

        /* Case: add a book with all fields same as another book in the catalogue except author -> added */
        toAdd = new BookBuilder().withTitle(VALID_TITLE_XVI).withAuthor(VALID_AUTHOR_YOU)
            .withIsbn(VALID_ISBN_XVI).withAvail(VALID_AVAIL_XVI)
            .withTags(VALID_TAG_DYSTOPIA).build();
        command = AddCommand.COMMAND_WORD + TITLE_DESC_XVI + AUTHOR_DESC_YOU + ISBN_DESC_XVI + AVAIL_DESC_XVI
            + TAG_DESC_DYSTOPIA;
        assertCommandFailure(command, toAdd);

        /* Case: add a book with all fields same as another book in the catalogue except author and ISBN -> added */
        toAdd = new BookBuilder().withTitle(VALID_TITLE_XVI).withAuthor(VALID_AUTHOR_YOU)
            .withIsbn("3333333333333").withAvail(VALID_AVAIL_XVI)
            .withTags(VALID_TAG_DYSTOPIA).build();
        command = AddCommand.COMMAND_WORD + TITLE_DESC_XVI + AUTHOR_DESC_YOU + " " + PREFIX_ISBN + "3333333333333"
            + AVAIL_DESC_XVI + TAG_DESC_DYSTOPIA;
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty catalogue -> added */
        deleteAllBooks();
        assertCommandSuccess(ANIMAL);

        /* Case: add a book with tags, command with parameters in random order -> added */
        toAdd = YOU;
        command = AddCommand.COMMAND_WORD + AUTHOR_DESC_YOU + ISBN_DESC_YOU + TITLE_DESC_YOU
            + TAG_DESC_FICTION + AVAIL_DESC_YOU;
        assertCommandSuccess(command, toAdd);

        /* Case: add a book, missing tags -> added */
        assertCommandSuccess(HOLES);

        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */

        /* Case: filters the book list before adding -> added */
        showBooksWithTitle(KEYWORD_MATCHING_GIRL);
        assertCommandSuccess(INVISIBLE);

        /* ------------------------ Perform add operation while a book card is selected --------------------------- */

        /* Case: selects first card in the book list, add a book -> added, card selection remains unchanged */
        selectBook(Index.fromOneBased(1));
        assertCommandSuccess(CALIFORNIA);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate book -> rejected */
        command = BookUtil.getAddCommand(HOLES);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_BOOK);

        /* Case: add a duplicate book except with different tags -> rejected */
        // "friends" is an existing tag used in the default model, see TypicalBooks#ANIMAL
        // This test will fail if a new tag that is not in the model is used, see the bug documented in
        // Catalogue#addBook(Book)
        command = BookUtil.getAddCommand(HOLES) + " " + PREFIX_TAG.getPrefix() + "unlabelled";
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_BOOK);

        /* Case: missing name -> rejected */
        command = AddCommand.COMMAND_WORD + AUTHOR_DESC_XVI + ISBN_DESC_XVI + AVAIL_DESC_XVI;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing isbn -> rejected */
        command = AddCommand.COMMAND_WORD + TITLE_DESC_XVI + AUTHOR_DESC_XVI + AVAIL_DESC_XVI;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing avail -> rejected */
        command = AddCommand.COMMAND_WORD + TITLE_DESC_XVI + AUTHOR_DESC_XVI + ISBN_DESC_XVI;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing author -> rejected */
        command = AddCommand.COMMAND_WORD + TITLE_DESC_XVI + ISBN_DESC_XVI + AVAIL_DESC_XVI;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + BookUtil.getBookDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddCommand.COMMAND_WORD + INVALID_TITLE_DESC + AUTHOR_DESC_XVI + ISBN_DESC_XVI + AVAIL_DESC_XVI;
        assertCommandFailure(command, Title.MESSAGE_TITLE_CONSTRAINTS);

        /* Case: invalid isbn -> rejected */
        command = AddCommand.COMMAND_WORD + TITLE_DESC_XVI + AUTHOR_DESC_XVI + INVALID_ISBN_DESC + AVAIL_DESC_XVI;
        assertCommandFailure(command, Isbn.MESSAGE_ISBN_CONSTRAINTS);

        /* Case: invalid avail -> rejected */
        command = AddCommand.COMMAND_WORD + TITLE_DESC_XVI + AUTHOR_DESC_XVI + ISBN_DESC_XVI + INVALID_AVAIL_DESC;
        assertCommandFailure(command, Avail.MESSAGE_AVAIL_CONSTRAINTS);

        /* Case: invalid author -> rejected */
        command = AddCommand.COMMAND_WORD + TITLE_DESC_XVI + INVALID_AUTHOR_DESC + ISBN_DESC_XVI + AVAIL_DESC_XVI;
        assertCommandFailure(command, Author.MESSAGE_AUTHOR_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        command = AddCommand.COMMAND_WORD + TITLE_DESC_XVI + AUTHOR_DESC_XVI + ISBN_DESC_XVI + AVAIL_DESC_XVI
            + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_TAG_CONSTRAINTS);


    }
```
###### \java\systemtests\DeleteCommandSystemTest.java
``` java
        /* Case: filtered book list, delete index within bounds of catalogue and book list -> deleted */
        showBooksWithTitle(KEYWORD_MATCHING_BREAKING);
        Index index = INDEX_FIRST_BOOK;
        assertTrue(index.getZeroBased() < getModel().getFilteredBookList().size());
        assertCommandSuccess(index);

        /* Case: filtered book list, delete index within bounds of catalogue but out of bounds of book list
         * -> rejected
         */
        showBooksWithTitle(KEYWORD_MATCHING_BREAKING);
        int invalidIndex = getModel().getCatalogue().getBookList().size();
        command = DeleteCommand.COMMAND_WORD + " " + invalidIndex;
        assertCommandFailure(command, MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
```
###### \java\systemtests\EditCommandSystemTest.java
``` java
        /* Case: filtered book list, edit index within bounds of catalogue and book list -> edited */
        showBooksWithTitle(KEYWORD_MATCHING_GIRL);
        index = INDEX_FIRST_BOOK;
        assertTrue(index.getZeroBased() < getModel().getFilteredBookList().size());
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + TITLE_DESC_YOU;
        bookToEdit = getModel().getFilteredBookList().get(index.getZeroBased());
        editedBook = new BookBuilder(bookToEdit).withTitle(VALID_TITLE_YOU).build();
        assertCommandSuccess(command, index, editedBook);

        /* Case: filtered book list, edit index within bounds of catalogue but out of bounds of book list
         * -> rejected
         */
        showBooksWithTitle(KEYWORD_MATCHING_GIRL);
        int invalidIndex = getModel().getCatalogue().getBookList().size();
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + TITLE_DESC_YOU,
            Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);

        /* --------------------- Performing edit operation while a book card is selected -------------------------- */

        /* Case: selects first card in the book list, edit a book -> edited, card selection remains unchanged but
         * browser url changes
         */
        showAllBooks();
        index = INDEX_FIRST_BOOK;
        selectBook(index);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + TITLE_DESC_XVI + ISBN_DESC_XVI
            + AVAIL_DESC_XVI + AUTHOR_DESC_XVI + TAG_DESC_DYSTOPIA;
        // this can be misleading: card selection actually remains unchanged but the
        // browser's url is updated to reflect the new book's name
        assertCommandSuccess(command, index, XVI, index);
```
###### \java\systemtests\FindCommandSystemTest.java
``` java
        /* Case: find multiple books in catalogue, command with leading spaces and trailing spaces
         * -> 2 books found
         */
        String command = "   " + FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_GIRL + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, CALIFORNIA, GONE); // Two titles contains "Girl"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where book list is displaying the books we are finding
         * -> 2 books found
         */
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_GIRL;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find book where book list is not displaying the book we are finding -> 1 book found */
        command = FindCommand.COMMAND_WORD + " California";
        ModelHelper.setFilteredList(expectedModel, CALIFORNIA);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple books in catalogue, 2 keywords -> 2 books found */
        command = FindCommand.COMMAND_WORD + " California Gone";
        ModelHelper.setFilteredList(expectedModel, CALIFORNIA, GONE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple books in catalogue, 2 keywords in reversed order -> 2 books found */
        command = FindCommand.COMMAND_WORD + " Gone California";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple books in catalogue, 2 keywords with 1 repeat -> 2 books found */
        command = FindCommand.COMMAND_WORD + " Gone California Gone";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple books in catalogue, 2 matching keywords and 1 non-matching keyword
         * -> 2 books found
         */
        command = FindCommand.COMMAND_WORD + " Gone California NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();
```
###### \java\systemtests\FindCommandSystemTest.java
``` java
        /* Case: find same books in catalogue after deleting 1 of them -> 1 book found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertFalse(getModel().getCatalogue().getBookList().contains(CALIFORNIA));
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_GIRL;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, GONE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find book in catalogue, keyword is same as name but of different case -> 1 book found */
        command = FindCommand.COMMAND_WORD + " GoNe GiRl";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find book in catalogue, keyword is substring of name -> 0 books found */
        command = FindCommand.COMMAND_WORD + " Gon";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find book in catalogue, name is substring of keyword -> 0 books found */
        command = FindCommand.COMMAND_WORD + " Oliver";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find book not in catalogue -> 0 books found */
        command = FindCommand.COMMAND_WORD + " Mark";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find book in catalogue, keyword is substring of author -> 0 books found */
        command = FindCommand.COMMAND_WORD + " Lau";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find book in catalogue, author is substring of keyword -> 0 books found */
        command = FindCommand.COMMAND_WORD + " Lauren";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find book not in catalogue, author not in catalogue -> 0 books found */
        command = FindCommand.COMMAND_WORD + " Mark";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find isbn number of book in catalogue -> 0 books found */
        command = FindCommand.COMMAND_WORD + " " + DELIRIUM.getIsbn().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find availability of book in catalogue -> 0 books found */
        command = FindCommand.COMMAND_WORD + " " + DELIRIUM.getAvail().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tags of book in catalogue -> 0 books found */
        List<Tag> tags = new ArrayList<>(DELIRIUM.getTags());
        command = FindCommand.COMMAND_WORD + " " + tags.get(0).tagName;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find while a book is selected -> selected card deselected */
        showAllBooks();
        selectBook(Index.fromOneBased(1));
        assertFalse(getBookListPanel().getHandleToSelectedCard().getTitle().equals(DELIRIUM.getTitle().fullTitle));
        command = FindCommand.COMMAND_WORD + " Delirium";
        ModelHelper.setFilteredList(expectedModel, DELIRIUM);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find book in empty catalogue -> 0 books found */
        deleteAllBooks();
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_GIRL;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DELIRIUM);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNd Delirium";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }
```
