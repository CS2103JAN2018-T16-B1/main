package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.account.Account;
import seedu.address.model.account.PrivilegeLevel;
import seedu.address.model.account.exceptions.DuplicateAccountException;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MATRICNUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIVILEGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

/**
 * Adds an account
 */
public class AddAccountCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "addAccount";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an account to the system. "
        + "Parameters: "
        + PREFIX_NAME + "NAME "
        + PREFIX_USERNAME + "USERNAME "
        + PREFIX_PASSWORD + "PASSWORD "
        + PREFIX_MATRICNUMBER + "MATRICULATION NUMBER "
        + PREFIX_PRIVILEGE + "PRIVILEGE LEVEL \n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_NAME + "John Doe "
        + PREFIX_USERNAME + "johndoe "
        + PREFIX_PASSWORD + "johndoe123 "
        + PREFIX_MATRICNUMBER + "A0123456B "
        + PREFIX_PRIVILEGE + "1";

    public static final String MESSAGE_SUCCESS = "New account added: %1$s";
    public static final String MESSAGE_DUPLICATE_ACCOUNT = "This account already exists in the system";

    public static final PrivilegeLevel PRIVILEGE_LEVEL = Model.PRIVILEGE_LEVEL_LIBRARIAN;

    private final Account toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Book}
     */
    public AddAccountCommand(Account account) {
        requireNonNull(account);
        toAdd = account;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addAccount(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateAccountException e) {
            throw new CommandException(MESSAGE_DUPLICATE_ACCOUNT);
        }
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public PrivilegeLevel getPrivilegeLevel() {
        return PRIVILEGE_LEVEL;
    }

}
