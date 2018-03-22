package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.AVAILIABILITY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.AVAILIABILITY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AVAILIABILITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TITLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
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
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.book.Address;
import seedu.address.model.book.Availability;
import seedu.address.model.book.Book;
import seedu.address.model.book.Title;
import seedu.address.model.book.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.BookBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Book expectedBook = new BookBuilder().withName(VALID_TITLE_BOB).withPhone(VALID_PHONE_BOB)
                .withAvailability(VALID_AVAILIABILITY_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TITLE_DESC_BOB + PHONE_DESC_BOB + AVAILIABILITY_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedBook));

        // multiple names - last name accepted
        assertParseSuccess(parser, TITLE_DESC_AMY + TITLE_DESC_BOB + PHONE_DESC_BOB + AVAILIABILITY_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedBook));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, TITLE_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB + AVAILIABILITY_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedBook));

        // multiple availability - last availability accepted
        assertParseSuccess(parser, TITLE_DESC_BOB + PHONE_DESC_BOB + AVAILIABILITY_DESC_AMY + AVAILIABILITY_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedBook));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, TITLE_DESC_BOB + PHONE_DESC_BOB + AVAILIABILITY_DESC_BOB + ADDRESS_DESC_AMY
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedBook));

        // multiple tags - all accepted
        Book expectedBookMultipleTags = new BookBuilder().withName(VALID_TITLE_BOB).withPhone(VALID_PHONE_BOB)
                .withAvailability(VALID_AVAILIABILITY_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();
        assertParseSuccess(parser, TITLE_DESC_BOB + PHONE_DESC_BOB + AVAILIABILITY_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddCommand(expectedBookMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Book expectedBook = new BookBuilder().withName(VALID_TITLE_AMY).withPhone(VALID_PHONE_AMY)
                .withAvailability(VALID_AVAILIABILITY_AMY).withAddress(VALID_ADDRESS_AMY).withTags().build();
        assertParseSuccess(parser, TITLE_DESC_AMY + PHONE_DESC_AMY + AVAILIABILITY_DESC_AMY + ADDRESS_DESC_AMY,
                new AddCommand(expectedBook));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_TITLE_BOB + PHONE_DESC_BOB + AVAILIABILITY_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, TITLE_DESC_BOB + VALID_PHONE_BOB + AVAILIABILITY_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing availability prefix
        assertParseFailure(parser, TITLE_DESC_BOB + PHONE_DESC_BOB + VALID_AVAILIABILITY_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, TITLE_DESC_BOB + PHONE_DESC_BOB + AVAILIABILITY_DESC_BOB + VALID_ADDRESS_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_TITLE_BOB + VALID_PHONE_BOB + VALID_AVAILIABILITY_BOB + VALID_ADDRESS_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_TITLE_DESC + PHONE_DESC_BOB + AVAILIABILITY_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Title.MESSAGE_TITLE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, TITLE_DESC_BOB + INVALID_PHONE_DESC + AVAILIABILITY_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid availability
        assertParseFailure(parser, TITLE_DESC_BOB + PHONE_DESC_BOB + INVALID_AVAILIABILITY_DESC + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Availability.MESSAGE_AVAILABILITY_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, TITLE_DESC_BOB + PHONE_DESC_BOB + AVAILIABILITY_DESC_BOB + INVALID_ADDRESS_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Address.MESSAGE_ADDRESS_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, TITLE_DESC_BOB + PHONE_DESC_BOB + AVAILIABILITY_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_TITLE_DESC + PHONE_DESC_BOB + AVAILIABILITY_DESC_BOB + INVALID_ADDRESS_DESC,
                Title.MESSAGE_TITLE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + TITLE_DESC_BOB + PHONE_DESC_BOB + AVAILIABILITY_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
