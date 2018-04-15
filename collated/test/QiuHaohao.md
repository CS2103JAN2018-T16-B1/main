# QiuHaohao
###### /java/systemtests/FindCommandSystemTest.java
``` java
        executeCommand("login admin admin");
```
###### /java/seedu/address/logic/parser/LoginCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.LoginCommand;

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
```
###### /java/seedu/address/logic/commands/LoginCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.account.Account;

public class LoginCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void equals() {
        LoginCommand loginStudentCommand = new LoginCommand("student", "student");
        LoginCommand loginAdminCommand = new LoginCommand("admin", "admin");

        // same object -> returns true
        assertTrue(loginStudentCommand.equals(loginStudentCommand));

        // same values -> returns true
        LoginCommand loginStudentCommandCopy = new LoginCommand("student", "student");
        assertTrue(loginStudentCommand.equals(loginStudentCommandCopy));

        // different types -> returns false
        assertFalse(loginStudentCommand.equals(1));

        // null -> returns false
        assertFalse(loginStudentCommand.equals(null));

        // different person -> returns false
        assertFalse(loginStudentCommand.equals(loginAdminCommand));
    }

    @Test
    public void constructor_nullUsername_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new LoginCommand(null, "admin");
    }

    @Test
    public void constructor_nullPassword_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new LoginCommand("admin", null);
    }

    @Test
    public void execute_loginAsStudent_loginSuccessful() throws Exception {
        Model model = new ModelManager();
        Account student = Account.createDefaultStudentAccount();
        model.addAccount(student);
        LoginCommand studentLogin = new LoginCommand("student", "student");
        studentLogin.setData(model, null, null);
        CommandResult commandResult = studentLogin.execute();

        assertEquals(LoginCommand.MESSAGE_LOGGED_IN_AS_STUTENT, commandResult.feedbackToUser);
        assertEquals(model.getPrivilegeLevel(), Model.PRIVILEGE_LEVEL_STUDENT);
    }

    @Test
    public void execute_loginAsLibrarian_loginSuccessful() {
        Model model = new ModelManager();
        LoginCommand studentLogin = new LoginCommand("admin", "admin");
        studentLogin.setData(model, null, null);
        CommandResult commandResult = studentLogin.execute();

        assertEquals(LoginCommand.MESSAGE_LOGGED_IN_AS_LIBRARIAN, commandResult.feedbackToUser);
        assertEquals(model.getPrivilegeLevel(), Model.PRIVILEGE_LEVEL_LIBRARIAN);
    }
}
```
###### /java/seedu/address/logic/commands/LogoutCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//import static org.junit.Assert.assertEquals;
//import org.junit.Rule;
//import org.junit.rules.ExpectedException;
//import seedu.address.model.ModelManager;
//import seedu.address.model.Model;


public class LogoutCommandTest {
    @Test
    public void equals() {
        LogoutCommand logoutCommand1 = new LogoutCommand();
        LogoutCommand logoutCommand2 = new LogoutCommand();

        // same object -> returns true
        assertTrue(logoutCommand1.equals(logoutCommand1));

        // same values -> returns true
        assertTrue(logoutCommand1.equals(logoutCommand2));

        // different types -> returns false
        assertFalse(logoutCommand1.equals(1));

        // null -> returns false
        assertFalse(logoutCommand1.equals(null));
    }
}
```
###### /java/seedu/address/model/account/UsernameTest.java
``` java
package seedu.address.model.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class UsernameTest {


    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Username(null));
    }

    @Test
    public void isValidUsername() {
        // null pointer
        Assert.assertThrows(NullPointerException.class, () -> Username.isValidUsername(null));

        //invalid
        assertFalse(Username.isValidUsername("")); // empty string
        assertFalse(Username.isValidUsername("123")); // too short
        assertFalse(Username.isValidUsername("abc")); // too short
        assertFalse(Username.isValidUsername("!!!")); // too short and non-word characters
        assertFalse(Username.isValidUsername("!!!!!!")); // non-word characters
        assertFalse(Username.isValidUsername("abcasj!")); // too short and non-word characters
        assertFalse(Username.isValidUsername(""));

        //valid
        assertTrue(Username.isValidUsername("abcde"));
        assertTrue(Username.isValidUsername("banana"));
        assertTrue(Username.isValidUsername("addressbook"));
        assertTrue(Username.isValidUsername("abcde123"));
        assertTrue(Username.isValidUsername("FHAIgasjd123987514"));
        assertTrue(Username.isValidUsername("123123123123"));

    }

    @Test
    public void getUsername() {
        String usernameString = "username";
        Username p = new Username(usernameString);
        assertEquals(usernameString, p.getUsername());
    }

    @Test
    public void equals() {
        Username p1 = new Username("username1");
        Username p1copy = new Username("username1");
        Username p2 = new Username("username2");

        //equal with itself
        assertTrue(p1.equals(p1));

        //equal with an other object with same state
        assertTrue(p1.equals(p1copy));

        //not equal with null
        assertFalse(p1.equals(null));

        //not equal with other type
        assertFalse(p1.equals(1));

        //not equal with same type with different state
        assertFalse(p1.equals(p2));
    }
}
```
###### /java/seedu/address/model/account/AccountTest.java
``` java
package seedu.address.model.account;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class AccountTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, ()
            -> new Account(null, null, null, null));
        Assert.assertThrows(NullPointerException.class, ()
            -> new Account(new Name("dummy"), null, null, null));
        Assert.assertThrows(NullPointerException.class, ()
            -> new Account(null, new Credential("dummy", "dummy"), null, null));
        Assert.assertThrows(NullPointerException.class, ()
            -> new Account(null, null, new MatricNumber("A1231231A"), null));
        Assert.assertThrows(NullPointerException.class, ()
            -> new Account(null, null, null, new PrivilegeLevel(0)));
    }

    @Test
    public void credentialMatchesTest() {
        Credential studentCredential = new Credential("student", "student");
        Credential adminCredential = new Credential("admin", "admin");
        Account studentAccount = Account.createDefaultStudentAccount();
        Account adminAccount = Account.createDefaultAdminAccount();
        assertTrue(studentAccount.credentialMatches(studentCredential));
        assertTrue(adminAccount.credentialMatches(adminCredential));
        assertFalse(studentAccount.credentialMatches(adminCredential));
        assertFalse(adminAccount.credentialMatches(studentCredential));
    }

    @Test
    public void equalsTest() {
        Account studentAccount = Account.createDefaultStudentAccount();
        Account studentAccountCopy = Account.createDefaultStudentAccount();
        Account adminAccount = Account.createDefaultAdminAccount();

        assertTrue(studentAccount.equals(studentAccount));
        assertTrue(studentAccount.equals(studentAccountCopy));
        assertFalse(studentAccount.equals(adminAccount));
        assertFalse(studentAccount.equals(null));
        assertFalse(studentAccount.equals(0));
    }

    @Test
    public void usernameMatches() {
        Name name = new Name("Ryan");
        Credential credential = new Credential("student", "student2");
        MatricNumber matricNumber = new MatricNumber("A0123256X");
        PrivilegeLevel privilegeLevel = new PrivilegeLevel(1);
        Account student2 = new Account(name, credential, matricNumber, privilegeLevel);
        Account student = Account.createDefaultStudentAccount();
        Account admin = Account.createDefaultAdminAccount();

        assertTrue(student2.usernameMatches(student));
        assertFalse(student2.usernameMatches(admin));
    }
}
```
###### /java/seedu/address/model/account/MatricNumberTest.java
``` java
package seedu.address.model.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class MatricNumberTest {

    @Test
    public void isValidMatricNumber() {
        // null pointer
        Assert.assertThrows(NullPointerException.class, () -> MatricNumber.isValidMatricNumber(null));

        //invalid
        assertFalse(MatricNumber.isValidMatricNumber("")); // empty string
        assertFalse(MatricNumber.isValidMatricNumber("123"));
        assertFalse(MatricNumber.isValidMatricNumber("abc"));
        assertFalse(MatricNumber.isValidMatricNumber("!!!"));
        assertFalse(MatricNumber.isValidMatricNumber("!!!!!!"));
        assertFalse(MatricNumber.isValidMatricNumber("A1234567XX!"));
        assertFalse(MatricNumber.isValidMatricNumber("A123456723X!"));
        assertFalse(MatricNumber.isValidMatricNumber("1234567XX!"));

        //valid
        assertTrue(MatricNumber.isValidMatricNumber("A1234567Z"));
        assertTrue(MatricNumber.isValidMatricNumber("A9992567B"));
    }

    @Test
    public void getMatricNumber() {
        String matricNumberString = "A1234567Z";
        MatricNumber m = new MatricNumber(matricNumberString);
        assertEquals(matricNumberString, m.getMatricNumber());
    }

    @Test
    public void equals() {
        MatricNumber p1 = new MatricNumber("A1234567Z");
        MatricNumber p1copy = new MatricNumber("A1234567Z");
        MatricNumber p2 = new MatricNumber("A9992567B");

        //equal with itself
        assertTrue(p1.equals(p1));

        //equal with an other object with same state
        assertTrue(p1.equals(p1copy));

        //not equal with null
        assertFalse(p1.equals(null));

        //not equal with other type
        assertFalse(p1.equals(1));

        //not equal with same type with different state
        assertFalse(p1.equals(p2));
    }
}
```
###### /java/seedu/address/model/account/CredentialTest.java
``` java
package seedu.address.model.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//import javax.jws.soap.SOAPBinding;

public class CredentialTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Credential(null, null));
        Assert.assertThrows(NullPointerException.class, () -> new Credential("username", null));
        Assert.assertThrows(NullPointerException.class, () -> new Credential(null, "password"));
    }


    @Test
    public void getPassword_and_getUsername() {
        String passwordString = "password";
        Password p = new Password(passwordString);
        String usernameString = "username";
        Username u = new Username(usernameString);
        Credential c = new Credential(usernameString, passwordString);
        assertEquals(c.getPassword(), p);
        assertEquals(c.getUsername(), u);
    }

    @Test
    public void equals() {
        String u1 = "username1";
        String u1copy = "username1";
        String u2 = "username2";
        String p1 = "password1";
        String p1copy = "password1";
        String p2 = "password2";
        Credential c1 = new Credential(u1, p1);
        Credential c1copy = new Credential(u1copy, p1copy);
        Credential c2 = new Credential(u2, p2);

        //equal with itself
        assertTrue(c1.equals(c1));

        //equal with an other object with same state
        assertTrue(c1.equals(c1copy));

        //not equal with null
        assertFalse(c1.equals(null));

        //not equal with other type
        assertFalse(c1.equals(1));

        //not equal with same type with different state
        assertFalse(c1.equals(c2));
    }

    @Test
    public void usernameEquals() {
        String u1 = "username1";
        String u2 = "username2";
        String p1 = "password1";
        Credential u1p1 = new Credential(u1, p1);
        Credential u1p2 = new Credential(u1, p1);
        Credential u2p1 = new Credential(u2, p1);
        Username username1 = u1p1.getUsername();
        Username username2 = u2p1.getUsername();

        assertTrue(u1p1.usernameEquals(username1));
        assertTrue(u1p2.usernameEquals(username1));
        assertFalse(u1p1.usernameEquals(username2));
    }
}
```
###### /java/seedu/address/model/account/UniqueAccountListTest.java
``` java
package seedu.address.model.account;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.model.account.exceptions.DuplicateAccountException;
import seedu.address.testutil.Assert;

public class UniqueAccountListTest {
    @Test
    public void add() throws DuplicateAccountException {
        UniqueAccountList accountList = new UniqueAccountList();

        Name name = new Name("Ryan");
        Credential credential = new Credential("student", "student2");
        MatricNumber matricNumber = new MatricNumber("A0123256X");
        PrivilegeLevel privilegeLevel = new PrivilegeLevel(1);

        Account student2 = new Account(name, credential, matricNumber, privilegeLevel);
        Account student = Account.createDefaultStudentAccount();
        Account admin = Account.createDefaultAdminAccount();

        accountList.add(student);
        accountList.add(admin);

        Assert.assertThrows(DuplicateAccountException.class, ()
            -> accountList.add(student2));
    }

    @Test
    public void searchByUsername() throws DuplicateAccountException {
        UniqueAccountList accountList = new UniqueAccountList();
        Account student = Account.createDefaultStudentAccount();
        Account admin = Account.createDefaultAdminAccount();

        accountList.add(student);
        accountList.add(admin);

        assertEquals(accountList.searchByUsername(new Username("student")), student);
    }
}
```
###### /java/seedu/address/model/account/PrivilegeLevelTest.java
``` java
package seedu.address.model.account;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class PrivilegeLevelTest {

    @Test
    public void constructor_invalidPrivilegeLevel_throwsIllegalArgumentException() {
        final int invalidPrivilegeLevel1 = 3;
        final int invalidPrivilegeLevel2 = -1;
        Assert.assertThrows(IllegalArgumentException.class, () -> new PrivilegeLevel(invalidPrivilegeLevel1));
        Assert.assertThrows(IllegalArgumentException.class, () -> new PrivilegeLevel(invalidPrivilegeLevel2));
    }

    @Test
    public void isValidPrivilegeLevel_test() {
        assertTrue(PrivilegeLevel.isValidPrivilegeLevel(0));
        assertTrue(PrivilegeLevel.isValidPrivilegeLevel(1));
        assertTrue(PrivilegeLevel.isValidPrivilegeLevel(2));
        assertFalse(PrivilegeLevel.isValidPrivilegeLevel(3));
        assertFalse(PrivilegeLevel.isValidPrivilegeLevel(-1));
    }

    @Test
    public void equals() {
        PrivilegeLevel p0 = new PrivilegeLevel(0);
        PrivilegeLevel p0copy = new PrivilegeLevel(0);
        PrivilegeLevel p1 = new PrivilegeLevel(1);
        PrivilegeLevel p2 = new PrivilegeLevel(2);

        //equal with itself
        assertTrue(p1.equals(p1));

        //equal with an other object with same state
        assertTrue(p0.equals(p0copy));

        //not equal with null
        assertFalse(p1.equals(null));

        //not equal with other type
        assertFalse(p1.equals(1));

        //not equal with same type with different state
        assertFalse(p1.equals(p2));
    }


    @Test
    public void compareTo() {
        PrivilegeLevel p0 = new PrivilegeLevel(0);
        PrivilegeLevel p1 = new PrivilegeLevel(1);
        PrivilegeLevel p2 = new PrivilegeLevel(2);

        assertTrue(p0.compareTo(p1) < 0);
        assertTrue(p1.compareTo(p2) < 0);
        assertTrue(p1.compareTo(p0) > 0);
        assertTrue(p1.compareTo(p1) == 0);
    }
}
```
###### /java/seedu/address/model/account/PasswordTest.java
``` java
package seedu.address.model.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class PasswordTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Password(null));
    }

    @Test
    public void isValidPassword() {
        // null pointer
        Assert.assertThrows(NullPointerException.class, () -> Password.isValidPassword(null));

        //invalid
        assertFalse(Password.isValidPassword("")); // empty string
        assertFalse(Password.isValidPassword("123")); // too short
        assertFalse(Password.isValidPassword("abc")); // too short
        assertFalse(Password.isValidPassword("!!!")); // too short and non-word characters
        assertFalse(Password.isValidPassword("!!!!!!")); // non-word characters
        assertFalse(Password.isValidPassword("abcasj!")); // too short and non-word characters
        assertFalse(Password.isValidPassword(""));

        //valid
        assertTrue(Password.isValidPassword("abcde"));
        assertTrue(Password.isValidPassword("banana"));
        assertTrue(Password.isValidPassword("addressbook"));
        assertTrue(Password.isValidPassword("abcde123"));
        assertTrue(Password.isValidPassword("FHAIgasjd123987514"));
        assertTrue(Password.isValidPassword("123123123123"));


    }

    @Test
    public void getPassword() {
        String passwordString = "password";
        Password p = new Password(passwordString);
        assertEquals(passwordString, p.getPassword());
    }

    @Test
    public void equals() {
        Password p1 = new Password("password1");
        Password p1copy = new Password("password1");
        Password p2 = new Password("password2");

        //equal with itself
        assertTrue(p1.equals(p1));

        //equal with an other object with same state
        assertTrue(p1.equals(p1copy));

        //not equal with null
        assertFalse(p1.equals(null));

        //not equal with other type
        assertFalse(p1.equals(1));

        //not equal with same type with different state
        assertFalse(p1.equals(p2));
    }
}
```
