//@@author QiuHaohao
package seedu.address.logic.parser;

import org.junit.Test;
import seedu.address.logic.commands.LoginCommand;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

public class LoginCommandParserTest {
    private LoginCommandParser parser = new LoginCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        LoginCommand expectedFindCommand =
            new LoginCommand("admin", "admin");
        assertParseSuccess(parser, "admin admin", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n admin \n \t admin  \t", expectedFindCommand);
    }
}
