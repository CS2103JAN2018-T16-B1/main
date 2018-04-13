package seedu.address.logic.parser;

import org.junit.Test;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditBookDescriptor;
import seedu.address.model.book.Author;
import seedu.address.model.book.Avail;
import seedu.address.model.book.Isbn;
import seedu.address.model.book.Title;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditBookDescriptorBuilder;

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
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_XVI;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_YOU;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AVAIL_XVI;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AVAIL_YOU;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ISBN_XVI;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ISBN_YOU;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_DYSTOPIA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FICTION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_XVI;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_BOOK;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_TITLE_XVI, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + TITLE_DESC_XVI, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + TITLE_DESC_XVI, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 z/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_TITLE_DESC, Title.MESSAGE_TITLE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_AUTHOR_DESC, Author.MESSAGE_AUTHOR_CONSTRAINTS); // invalid author
        assertParseFailure(parser, "1" + INVALID_ISBN_DESC, Isbn.MESSAGE_ISBN_CONSTRAINTS); // invalid isbn
        assertParseFailure(parser, "1" + INVALID_AVAIL_DESC, Avail.MESSAGE_AVAIL_CONSTRAINTS); // invalid avail
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS); // invalid tag

        // invalid isbn followed by valid avail
        assertParseFailure(parser, "1" + INVALID_ISBN_DESC + AVAIL_DESC_XVI, Isbn.MESSAGE_ISBN_CONSTRAINTS);

        // valid isbn followed by invalid isbn. The test case for invalid isbn followed by valid isbn
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + ISBN_DESC_YOU + INVALID_ISBN_DESC, Isbn.MESSAGE_ISBN_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Book} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_DYSTOPIA + TAG_DESC_FICTION + TAG_EMPTY, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_DYSTOPIA + TAG_EMPTY + TAG_DESC_FICTION, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_DYSTOPIA + TAG_DESC_FICTION, Tag.MESSAGE_TAG_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_TITLE_DESC + VALID_AUTHOR_XVI
                + INVALID_AVAIL_DESC + VALID_ISBN_XVI,
            Title.MESSAGE_TITLE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_BOOK;
        String userInput = targetIndex.getOneBased() + TITLE_DESC_XVI + AUTHOR_DESC_XVI
            + ISBN_DESC_YOU + TAG_DESC_FICTION
            + AVAIL_DESC_XVI + TAG_DESC_DYSTOPIA;

        EditBookDescriptor descriptor = new EditBookDescriptorBuilder().withName(VALID_TITLE_XVI)
            .withAuthor(VALID_AUTHOR_XVI)
            .withIsbn(VALID_ISBN_YOU).withAvail(VALID_AVAIL_XVI)
            .withTags(VALID_TAG_FICTION, VALID_TAG_DYSTOPIA).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_BOOK;
        String userInput = targetIndex.getOneBased() + ISBN_DESC_YOU + AVAIL_DESC_XVI;

        EditBookDescriptor descriptor = new EditBookDescriptorBuilder().withIsbn(VALID_ISBN_YOU)
            .withAvail(VALID_AVAIL_XVI).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_BOOK;
        String userInput = targetIndex.getOneBased() + TITLE_DESC_XVI;
        EditBookDescriptor descriptor = new EditBookDescriptorBuilder().withName(VALID_TITLE_XVI).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // isbn
        userInput = targetIndex.getOneBased() + ISBN_DESC_XVI;
        descriptor = new EditBookDescriptorBuilder().withIsbn(VALID_ISBN_XVI).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // avail
        userInput = targetIndex.getOneBased() + AVAIL_DESC_XVI;
        descriptor = new EditBookDescriptorBuilder().withAvail(VALID_AVAIL_XVI).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + AUTHOR_DESC_XVI;
        descriptor = new EditBookDescriptorBuilder().withAuthor(VALID_AUTHOR_XVI).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_DYSTOPIA;
        descriptor = new EditBookDescriptorBuilder().withTags(VALID_TAG_DYSTOPIA).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_BOOK;
        String userInput = targetIndex.getOneBased() + AUTHOR_DESC_XVI + ISBN_DESC_XVI + AVAIL_DESC_XVI
            + TAG_DESC_DYSTOPIA + AUTHOR_DESC_XVI + ISBN_DESC_XVI
            + AVAIL_DESC_XVI + TAG_DESC_DYSTOPIA + AUTHOR_DESC_YOU
            + ISBN_DESC_YOU + AVAIL_DESC_YOU + TAG_DESC_FICTION;

        EditBookDescriptor descriptor = new EditBookDescriptorBuilder()
            .withAuthor(VALID_AUTHOR_YOU).withIsbn(VALID_ISBN_YOU)
            .withAvail(VALID_AVAIL_YOU).withTags(VALID_TAG_DYSTOPIA, VALID_TAG_FICTION)
            .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_BOOK;
        String userInput = targetIndex.getOneBased() + INVALID_ISBN_DESC + ISBN_DESC_YOU;
        EditBookDescriptor descriptor = new EditBookDescriptorBuilder().withIsbn(VALID_ISBN_YOU).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + AUTHOR_DESC_YOU + AVAIL_DESC_YOU + INVALID_ISBN_DESC
            + ISBN_DESC_YOU;
        descriptor = new EditBookDescriptorBuilder().withAuthor(VALID_AUTHOR_YOU)
            .withIsbn(VALID_ISBN_YOU).withAvail(VALID_AVAIL_YOU)
            .build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_BOOK;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditBookDescriptor descriptor = new EditBookDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
