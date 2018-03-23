package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.AUTHOR_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.AUTHOR_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.AVAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.AVAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AUTHOR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AVAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ISBN_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TITLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.ISBN_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ISBN_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_BOB;
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
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.book.Author;
import seedu.address.model.book.Avail;
import seedu.address.model.book.Book;
import seedu.address.model.book.Isbn;
import seedu.address.model.book.Title;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.BookBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Book expectedBook = new BookBuilder().withTitle(VALID_TITLE_BOB)
                .withAuthor(VALID_AUTHOR_BOB)
                .withIsbn(VALID_ISBN_BOB)
                .withAvail(VALID_AVAIL_BOB)
                .withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TITLE_DESC_BOB + AUTHOR_DESC_BOB + ISBN_DESC_BOB
                + AVAIL_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedBook));

        // multiple names - last name accepted
        assertParseSuccess(parser, TITLE_DESC_AMY + TITLE_DESC_BOB + AUTHOR_DESC_BOB + ISBN_DESC_BOB
                + AVAIL_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedBook));

        // multiple isbns - last isbn accepted
        assertParseSuccess(parser, TITLE_DESC_BOB + AUTHOR_DESC_BOB + ISBN_DESC_AMY + ISBN_DESC_BOB
                + AVAIL_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedBook));

        // multiple avail - last avail accepted
        assertParseSuccess(parser, TITLE_DESC_BOB + AUTHOR_DESC_BOB + ISBN_DESC_BOB + AVAIL_DESC_AMY
                + AVAIL_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedBook));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, TITLE_DESC_BOB + AUTHOR_DESC_AMY
                + AUTHOR_DESC_BOB + ISBN_DESC_BOB + AVAIL_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedBook));

        // multiple tags - all accepted
        Book expectedBookMultipleTags = new BookBuilder().withTitle(VALID_TITLE_BOB).withAuthor(VALID_AUTHOR_BOB)
                .withIsbn(VALID_ISBN_BOB)
                .withAvail(VALID_AVAIL_BOB)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();
        assertParseSuccess(parser, TITLE_DESC_BOB + AUTHOR_DESC_BOB + ISBN_DESC_BOB + AVAIL_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddCommand(expectedBookMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Book expectedBook = new BookBuilder().withTitle(VALID_TITLE_AMY)
                .withAuthor(VALID_AUTHOR_AMY)
                .withIsbn(VALID_ISBN_AMY)
                .withAvail(VALID_AVAIL_AMY)
                .withTags().build();
        assertParseSuccess(parser, TITLE_DESC_AMY + AUTHOR_DESC_AMY + ISBN_DESC_AMY + AVAIL_DESC_AMY,
                new AddCommand(expectedBook));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_TITLE_BOB + AUTHOR_DESC_BOB + ISBN_DESC_BOB + AVAIL_DESC_BOB,
                expectedMessage);

        // missing isbn prefix
        assertParseFailure(parser, TITLE_DESC_BOB + AUTHOR_DESC_BOB + VALID_ISBN_BOB + AVAIL_DESC_BOB,
                expectedMessage);

        // missing avail prefix
        assertParseFailure(parser, TITLE_DESC_BOB + AUTHOR_DESC_BOB + ISBN_DESC_BOB + VALID_AVAIL_BOB,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, TITLE_DESC_BOB + VALID_AUTHOR_BOB + ISBN_DESC_BOB + AVAIL_DESC_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_TITLE_BOB + VALID_AUTHOR_BOB + VALID_ISBN_BOB + VALID_AVAIL_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_TITLE_DESC + AUTHOR_DESC_BOB + ISBN_DESC_BOB + AVAIL_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Title.MESSAGE_TITLE_CONSTRAINTS);

        // invalid isbn
        assertParseFailure(parser, TITLE_DESC_BOB + AUTHOR_DESC_BOB + INVALID_ISBN_DESC + AVAIL_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Isbn.MESSAGE_ISBN_CONSTRAINTS);

        // invalid avail
        assertParseFailure(parser, TITLE_DESC_BOB + AUTHOR_DESC_BOB + ISBN_DESC_BOB + INVALID_AVAIL_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Avail.MESSAGE_AVAIL_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, TITLE_DESC_BOB + INVALID_AUTHOR_DESC + ISBN_DESC_BOB + AVAIL_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Author.MESSAGE_AUTHOR_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, TITLE_DESC_BOB + AUTHOR_DESC_BOB + ISBN_DESC_BOB + AVAIL_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_TITLE_DESC + INVALID_AUTHOR_DESC + ISBN_DESC_BOB + AVAIL_DESC_BOB,
                Title.MESSAGE_TITLE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + TITLE_DESC_BOB + AVAIL_DESC_BOB
                        + AUTHOR_DESC_BOB + ISBN_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
