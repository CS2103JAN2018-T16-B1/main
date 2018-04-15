//@@author LeKhangTai
package seedu.address.logic.parser;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ReturnCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and create a new ReturnCommand object
 */
public class ReturnCommandParser implements Parser<ReturnCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ReturnCommand
     * and returns an ReturnCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */

    public ReturnCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ReturnCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReturnCommand.MESSAGE_USAGE));
        }
    }

}

