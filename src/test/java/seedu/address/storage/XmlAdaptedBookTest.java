package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedBook.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalBooks.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.book.Address;
import seedu.address.model.book.Availability;
import seedu.address.model.book.Title;
import seedu.address.model.book.Phone;
import seedu.address.testutil.Assert;

public class XmlAdaptedBookTest {
    private static final String INVALID_TITLE = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_AVAILABILITY = "not sure";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_TITLE = BENSON.getTitle().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_AVAILABILITY = BENSON.getAvailability().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
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
                new XmlAdaptedBook(INVALID_TITLE, VALID_PHONE, VALID_AVAILABILITY, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Title.MESSAGE_TITLE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedBook book = new XmlAdaptedBook(null, VALID_PHONE, VALID_AVAILABILITY, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        XmlAdaptedBook book =
                new XmlAdaptedBook(VALID_TITLE, INVALID_PHONE, VALID_AVAILABILITY, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_PHONE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        XmlAdaptedBook book = new XmlAdaptedBook(VALID_TITLE, null, VALID_AVAILABILITY, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

    @Test
    public void toModelType_invalidAvailability_throwsIllegalValueException() {
        XmlAdaptedBook book =
                new XmlAdaptedBook(VALID_TITLE, VALID_PHONE, INVALID_AVAILABILITY, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Availability.MESSAGE_AVAILABILITY_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

    @Test
    public void toModelType_nullAvailability_throwsIllegalValueException() {
        XmlAdaptedBook book = new XmlAdaptedBook(VALID_TITLE, VALID_PHONE, null, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Availability.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        XmlAdaptedBook book =
                new XmlAdaptedBook(VALID_TITLE, VALID_PHONE, VALID_AVAILABILITY, INVALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Address.MESSAGE_ADDRESS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        XmlAdaptedBook book = new XmlAdaptedBook(VALID_TITLE, VALID_PHONE, VALID_AVAILABILITY, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedBook book =
                new XmlAdaptedBook(VALID_TITLE, VALID_PHONE, VALID_AVAILABILITY, VALID_ADDRESS, invalidTags);
        Assert.assertThrows(IllegalValueException.class, book::toModelType);
    }

}
