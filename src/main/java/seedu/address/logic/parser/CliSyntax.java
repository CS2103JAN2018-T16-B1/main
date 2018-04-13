package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_TITLE = new Prefix("t/");
    public static final Prefix PREFIX_AUTHOR = new Prefix("a/");
    public static final Prefix PREFIX_ISBN = new Prefix("i/");
    public static final Prefix PREFIX_AVAIL = new Prefix("av/");
    public static final Prefix PREFIX_TAG = new Prefix("tag/");

    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_USERNAME = new Prefix("u/");
    public static final Prefix PREFIX_MATRICNUMBER = new Prefix("m/");
    public static final Prefix PREFIX_PASSWORD = new Prefix("p/");
    public static final Prefix PREFIX_PRIVILEGE = new Prefix("l/");

}
