package seedu.address.logic.parser;

import org.junit.Test;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.book.Author;
import seedu.address.model.book.Avail;
import seedu.address.model.book.Book;
import seedu.address.model.book.Isbn;
import seedu.address.model.book.Title;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.BookBuilder;

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
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
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
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FICTION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_XVI;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_YOU;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Book expectedBook = new BookBuilder().withTitle(VALID_TITLE_YOU)
            .withAuthor(VALID_AUTHOR_YOU)
            .withIsbn(VALID_ISBN_YOU)
            .withAvail(VALID_AVAIL_YOU)
            .withTags(VALID_TAG_DYSTOPIA).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TITLE_DESC_YOU + AUTHOR_DESC_YOU + ISBN_DESC_YOU
            + AVAIL_DESC_YOU + TAG_DESC_DYSTOPIA, new AddCommand(expectedBook));

        // multiple names - last name accepted
        assertParseSuccess(parser, TITLE_DESC_XVI + TITLE_DESC_YOU + AUTHOR_DESC_YOU + ISBN_DESC_YOU
            + AVAIL_DESC_YOU + TAG_DESC_DYSTOPIA, new AddCommand(expectedBook));

        // multiple isbns - last isbn accepted
        assertParseSuccess(parser, TITLE_DESC_YOU + AUTHOR_DESC_YOU + ISBN_DESC_XVI + ISBN_DESC_YOU
            + AVAIL_DESC_YOU + TAG_DESC_DYSTOPIA, new AddCommand(expectedBook));

        // multiple avail - last avail accepted
        assertParseSuccess(parser, TITLE_DESC_YOU + AUTHOR_DESC_YOU + ISBN_DESC_YOU + AVAIL_DESC_XVI
            + AVAIL_DESC_YOU + TAG_DESC_DYSTOPIA, new AddCommand(expectedBook));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, TITLE_DESC_YOU + AUTHOR_DESC_XVI
            + AUTHOR_DESC_YOU + ISBN_DESC_YOU + AVAIL_DESC_YOU + TAG_DESC_DYSTOPIA, new AddCommand(expectedBook));

        // multiple tags - all accepted
        Book expectedBookMultipleTags = new BookBuilder().withTitle(VALID_TITLE_YOU).withAuthor(VALID_AUTHOR_YOU)
            .withIsbn(VALID_ISBN_YOU)
            .withAvail(VALID_AVAIL_YOU)
            .withTags(VALID_TAG_DYSTOPIA, VALID_TAG_FICTION).build();
        assertParseSuccess(parser, TITLE_DESC_YOU + AUTHOR_DESC_YOU + ISBN_DESC_YOU + AVAIL_DESC_YOU
            + TAG_DESC_FICTION + TAG_DESC_DYSTOPIA, new AddCommand(expectedBookMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Book expectedBook = new BookBuilder().withTitle(VALID_TITLE_XVI)
            .withAuthor(VALID_AUTHOR_XVI)
            .withIsbn(VALID_ISBN_XVI)
            .withAvail(VALID_AVAIL_XVI)
            .withTags().build();
        assertParseSuccess(parser, TITLE_DESC_XVI + AUTHOR_DESC_XVI + ISBN_DESC_XVI + AVAIL_DESC_XVI,
            new AddCommand(expectedBook));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_TITLE_YOU + AUTHOR_DESC_YOU + ISBN_DESC_YOU + AVAIL_DESC_YOU,
            expectedMessage);

        // missing isbn prefix
        assertParseFailure(parser, TITLE_DESC_YOU + AUTHOR_DESC_YOU + VALID_ISBN_YOU + AVAIL_DESC_YOU,
            expectedMessage);

        // missing avail prefix
        assertParseFailure(parser, TITLE_DESC_YOU + AUTHOR_DESC_YOU + ISBN_DESC_YOU + VALID_AVAIL_YOU,
            expectedMessage);

        // missing address prefix
        assertParseFailure(parser, TITLE_DESC_YOU + VALID_AUTHOR_YOU + ISBN_DESC_YOU + AVAIL_DESC_YOU,
            expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_TITLE_YOU + VALID_AUTHOR_YOU + VALID_ISBN_YOU + VALID_AVAIL_YOU,
            expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_TITLE_DESC + AUTHOR_DESC_YOU + ISBN_DESC_YOU + AVAIL_DESC_YOU
            + TAG_DESC_FICTION + TAG_DESC_DYSTOPIA, Title.MESSAGE_TITLE_CONSTRAINTS);

        // invalid isbn
        assertParseFailure(parser, TITLE_DESC_YOU + AUTHOR_DESC_YOU + INVALID_ISBN_DESC + AVAIL_DESC_YOU
            + TAG_DESC_FICTION + TAG_DESC_DYSTOPIA, Isbn.MESSAGE_ISBN_CONSTRAINTS);

        // invalid avail
        assertParseFailure(parser, TITLE_DESC_YOU + AUTHOR_DESC_YOU + ISBN_DESC_YOU + INVALID_AVAIL_DESC
            + TAG_DESC_FICTION + TAG_DESC_DYSTOPIA, Avail.MESSAGE_AVAIL_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, TITLE_DESC_YOU + INVALID_AUTHOR_DESC + ISBN_DESC_YOU + AVAIL_DESC_YOU
            + TAG_DESC_FICTION + TAG_DESC_DYSTOPIA, Author.MESSAGE_AUTHOR_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, TITLE_DESC_YOU + AUTHOR_DESC_YOU + ISBN_DESC_YOU + AVAIL_DESC_YOU
            + INVALID_TAG_DESC + VALID_TAG_DYSTOPIA, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_TITLE_DESC + INVALID_AUTHOR_DESC + ISBN_DESC_YOU + AVAIL_DESC_YOU,
            Title.MESSAGE_TITLE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + TITLE_DESC_YOU + AVAIL_DESC_YOU
                + AUTHOR_DESC_YOU + ISBN_DESC_YOU + TAG_DESC_FICTION + TAG_DESC_DYSTOPIA,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
