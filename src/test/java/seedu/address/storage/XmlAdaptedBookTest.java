package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedBook.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalBooks.BENSON;

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

    private static final String VALID_TITLE = BENSON.getTitle().toString();
    private static final String VALID_AUTHOR = BENSON.getAuthor().toString();
    private static final String VALID_ISBN = BENSON.getIsbn().toString();
    private static final String VALID_AVAIL = BENSON.getAvail().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validBookDetails_returnsBook() throws Exception {
        XmlAdaptedBook book = new XmlAdaptedBook(BENSON);
        assertEquals(BENSON, book.toModelType());
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
