package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.account.MatricNumber;
import seedu.address.model.account.Name;
import seedu.address.model.account.Password;
import seedu.address.model.account.PrivilegeLevel;
import seedu.address.model.account.Username;
import seedu.address.model.book.Author;
import seedu.address.model.book.Avail;
import seedu.address.model.book.Isbn;
import seedu.address.model.book.Title;
import seedu.address.model.tag.Tag;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.requireNonNull;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 * {@code ParserUtil} contains methods that take in {@code Optional} as parameters. However, it goes against Java's
 * convention (see https://stackoverflow.com/a/39005452) as {@code Optional} should only be used a return type.
 * Justification: The methods in concern receive {@code Optional} return values from other methods as parameters and
 * return {@code Optional} values based on whether the parameters were present. Therefore, it is redundant to unwrap the
 * initial {@code Optional} before passing to {@code ParserUtil} as a parameter and then re-wrap it into an
 * {@code Optional} return value inside {@code ParserUtil} methods.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INSUFFICIENT_PARTS = "Number of parts must be more than 1.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws IllegalValueException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws IllegalValueException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new IllegalValueException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }
    //============================== Account Level Parse Commands ===========================

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code name} is invalid.
     */
    public static Name parseAccountName(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<Name>} if {@code name} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Name> parseAccountName(Optional<String> name) throws IllegalValueException {
        requireNonNull(name);
        return name.isPresent() ? Optional.of(parseAccountName(name.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String matricNumber} into a {@code MatricNumber}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code matricNumber} is invalid.
     */
    public static MatricNumber parseAccountMatricNumber(String matricNumber) throws IllegalValueException {
        requireNonNull(matricNumber);
        String trimmedMatricNumber = matricNumber.trim();
        if (!MatricNumber.isValidMatricNumber(trimmedMatricNumber)) {
            throw new IllegalValueException(MatricNumber.MESSAGE_MATRIC_NUMBER_CONSTRAINTS);
        }
        return new MatricNumber(trimmedMatricNumber);
    }

    /**
     * Parses a {@code Optional<String> title} into an {@code Optional<Title>} if {@code title} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<MatricNumber> parseAccountMatricNumber(Optional<String> matricNumber)
        throws IllegalValueException {
        requireNonNull(matricNumber);
        return matricNumber.isPresent() ? Optional.of(parseAccountMatricNumber(matricNumber.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String username} into a {@code Username}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code username} is invalid.
     */
    public static Username parseAccountUsername(String username) throws IllegalValueException {
        requireNonNull(username);
        String trimmedUsername = username.trim();
        if (!Username.isValidUsername(trimmedUsername)) {
            throw new IllegalValueException(Username.MESSAGE_USERNAME_CONSTRAINTS);
        }
        return new Username(trimmedUsername);
    }

    /**
     * Parses a {@code Optional<String> username} into an {@code Optional<Username>} if {@code username} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Username> parseAccountUsername(Optional<String> username) throws IllegalValueException {
        requireNonNull(username);
        return username.isPresent() ? Optional.of(parseAccountUsername(username.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String password} into a {@code Password}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code password} is invalid.
     */
    public static Password parseAccountPassword(String password) throws IllegalValueException {
        requireNonNull(password);
        String trimmedPassword = password.trim();
        if (!Password.isValidPassword(trimmedPassword)) {
            throw new IllegalValueException(Password.MESSAGE_PASSWORD_CONSTRAINTS);
        }
        return new Password(trimmedPassword);
    }

    /**
     * Parses a {@code Optional<String> password} into an {@code Optional<Password>} if {@code password} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Password> parseAccountPassword(Optional<String> password) throws IllegalValueException {
        requireNonNull(password);
        return password.isPresent() ? Optional.of(parseAccountPassword(password.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String privilegeLevel} into a {@code PrivilegeLevel}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code privilegeLevel} is invalid.
     */
    public static PrivilegeLevel parseAccountPrivilegeLevel(String privilegeLevel) throws IllegalValueException {
        requireNonNull(privilegeLevel);
        int input = Integer.parseInt(privilegeLevel);

        if (!PrivilegeLevel.isValidPrivilegeLevel(input)) {
            throw new IllegalValueException(PrivilegeLevel.MESSAGE_PRIVILEGE_LEVEL_CONSTRAINTS);
        }
        return new PrivilegeLevel(input);
    }

    //============================== Book Level Parse Commands ==============================

    /**
     * Parses a {@code String title} into a {@code Title}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code title} is invalid.
     */
    public static Title parseTitle(String title) throws IllegalValueException {
        requireNonNull(title);
        String trimmedTitle = title.trim();
        if (!Title.isValidTitle(trimmedTitle)) {
            throw new IllegalValueException(Title.MESSAGE_TITLE_CONSTRAINTS);
        }
        return new Title(trimmedTitle);
    }

    /**
     * Parses a {@code Optional<String> title} into an {@code Optional<Title>} if {@code title} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Title> parseTitle(Optional<String> title) throws IllegalValueException {
        requireNonNull(title);
        return title.isPresent() ? Optional.of(parseTitle(title.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String isbn} into a {@code Isbn}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code isbn} is invalid.
     */
    public static Isbn parseIsbn(String isbn) throws IllegalValueException {
        requireNonNull(isbn);
        String trimmedIsbn = isbn.trim();
        if (!Isbn.isValidIsbn(trimmedIsbn)) {
            throw new IllegalValueException(Isbn.MESSAGE_ISBN_CONSTRAINTS);
        }
        return new Isbn(trimmedIsbn);
    }

    /**
     * Parses a {@code Optional<String> isbn} into an {@code Optional<Isbn>} if {@code isbn} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Isbn> parseIsbn(Optional<String> isbn) throws IllegalValueException {
        requireNonNull(isbn);
        return isbn.isPresent() ? Optional.of(parseIsbn(isbn.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String address} into an {@code Author}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code address} is invalid.
     */
    public static Author parseAuthor(String address) throws IllegalValueException {
        requireNonNull(address);
        String trimmedAuthor = address.trim();
        if (!Author.isValidAuthor(trimmedAuthor)) {
            throw new IllegalValueException(Author.MESSAGE_AUTHOR_CONSTRAINTS);
        }
        return new Author(trimmedAuthor);
    }

    /**
     * Parses a {@code Optional<String> author} into an {@code Optional<Author>} if {@code author} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Author> parseAuthor(Optional<String> author) throws IllegalValueException {
        requireNonNull(author);
        return author.isPresent() ? Optional.of(parseAuthor(author.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String avail} into an {@code Avail}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code avail} is invalid.
     */
    public static Avail parseAvail(String avail) throws IllegalValueException {
        requireNonNull(avail);
        String trimmedAvail = avail.trim();
        if (!Avail.isValidAvail(trimmedAvail)) {
            throw new IllegalValueException(Avail.MESSAGE_AVAIL_CONSTRAINTS);
        }
        return new Avail(trimmedAvail);
    }

    /**
     * Parses a {@code Optional<String> avail} into an {@code Optional<Avail>} if {@code avail} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Avail> parseAvail(Optional<String> avail) throws IllegalValueException {
        requireNonNull(avail);
        return avail.isPresent() ? Optional.of(parseAvail(avail.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws IllegalValueException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new IllegalValueException(Tag.MESSAGE_TAG_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws IllegalValueException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }
}
