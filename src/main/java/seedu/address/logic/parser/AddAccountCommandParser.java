package seedu.address.logic.parser;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddAccountCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.account.Account;
import seedu.address.model.account.Credential;
import seedu.address.model.account.MatricNumber;
import seedu.address.model.account.Name;
import seedu.address.model.account.Password;
import seedu.address.model.account.PrivilegeLevel;
import seedu.address.model.account.Username;

import java.util.stream.Stream;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MATRICNUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIVILEGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

/**
 * Parses input arguments and creates a new AddAccountCommand object
 */
public class AddAccountCommandParser implements Parser<AddAccountCommand> {
    /**
     * Returns true if none of the prefixes contains empty(@code Optional) value in the given
     * (@code ArgumentMultimap).
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch((prefix -> argumentMultimap.getValue(prefix).isPresent()));
    }

    /**
     * +     * Parses the given (@code String) of arguments in the context of the AddAccountCommand
     * +     * and returns an AddAccountCommand object for execution.
     * +     *
     * +     * @throws ParseException if the user input does not conform to the expected format
     * +
     */
    @Override
    public AddAccountCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_USERNAME, PREFIX_PASSWORD, PREFIX_MATRICNUMBER,
                PREFIX_PRIVILEGE);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_USERNAME, PREFIX_PASSWORD, PREFIX_MATRICNUMBER,
            PREFIX_PRIVILEGE) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAccountCommand.MESSAGE_USAGE));
        }
        try {
            Name name = ParserUtil.parseAccountName(argMultimap.getValue(PREFIX_NAME).get());
            Username username = ParserUtil.parseAccountUsername(argMultimap.getValue(PREFIX_USERNAME).get());
            Password password = ParserUtil.parseAccountPassword(argMultimap.getValue(PREFIX_PASSWORD).get());
            Credential credential = new Credential(username.getUsername(), password.getPassword());
            MatricNumber matricNumber = ParserUtil.parseAccountMatricNumber
                (argMultimap.getValue(PREFIX_MATRICNUMBER).get());
            PrivilegeLevel privilegeLevel = ParserUtil.parseAccountPrivilegeLevel
                (argMultimap.getValue(PREFIX_PRIVILEGE).get());

            Account account = new Account(name, credential, matricNumber, privilegeLevel);

            return new AddAccountCommand(account);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }
}
