//@@author QiuHaohao
package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.model.account.Credential;
import seedu.address.model.account.PrivilegeLevel;

import static java.util.Objects.requireNonNull;

/**
 * Logs in as student or librarian.
 */
public class LoginCommand extends Command {
    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Login as student or librarian.\n"
        + "Parameters: USERNAME PASSWORD(both username and password should be at least 5 chars long)\n"
        + "Example: " + COMMAND_WORD + " MyUsername MyPassword";

    public static final String MESSAGE_LOGGED_IN_AS_STUTENT = "You are logged in as student";
    public static final String MESSAGE_LOGGED_IN_AS_LIBRARIAN = "You are logged in as librarian";
    public static final String MESSAGE_NOT_LOGGED_IN = "Wrong username/password, try again";

    public static final PrivilegeLevel PRIVILEGE_LEVEL = Model.PRIVILEGE_LEVEL_GUEST;


    private final Credential credential;


    public LoginCommand(String username, String password) {
        requireNonNull(username);
        requireNonNull(password);
        this.credential = new Credential(username, password);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof LoginCommand // instanceof handles nulls
            && credential.equals(((LoginCommand) other).credential));
    }

    @Override
    public CommandResult execute() {
        PrivilegeLevel newPrivilegeLevel = model.authenticate(this.credential);
        if (newPrivilegeLevel.equals(Model.PRIVILEGE_LEVEL_GUEST)) {
            return new CommandResult(MESSAGE_NOT_LOGGED_IN);
        }
        if (newPrivilegeLevel.equals(Model.PRIVILEGE_LEVEL_STUDENT)) {
            return new CommandResult(MESSAGE_LOGGED_IN_AS_STUTENT);
        }
        if (newPrivilegeLevel.equals(Model.PRIVILEGE_LEVEL_LIBRARIAN)) {
            return new CommandResult(MESSAGE_LOGGED_IN_AS_LIBRARIAN);
        }
        return new CommandResult(MESSAGE_NOT_LOGGED_IN);
    }

    @Override
    public PrivilegeLevel getPrivilegeLevel() {
        return PRIVILEGE_LEVEL;
    }
}
