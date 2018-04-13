//@@author khiayi
package seedu.address.model.book;

import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Book in the catalogue.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Book {

    private final Title title;
    private final Author author;
    private final Isbn isbn;
    private final Avail avail;

    private final UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Book(Title title, Author author, Isbn isbn, Avail avail, Set<Tag> tags) {
        requireAllNonNull(title, author, isbn, avail, tags);
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.avail = avail;
        // protect internal tags from changes in the arg list
        this.tags = new UniqueTagList(tags);
    }

    public Title getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    public Isbn getIsbn() {
        return isbn;
    }

    public Avail getAvail() {
        return avail;
    }

    /**
     * Returns true if this book's isbn is the same as the isbn provided
     *
     * @param isbn
     * @return
     */
    public boolean isbnMatches(Isbn isbn) {
        return this.isbn.equals(isbn);
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.toSet());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Book)) {
            return false;
        }

        Book otherBook = (Book) other;
        return otherBook.getTitle().equals(this.getTitle())
            && otherBook.getAuthor().equals(this.getAuthor())
            && otherBook.getIsbn().equals(this.getIsbn())
            && otherBook.getAvail().equals(this.getAvail());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, author, isbn, avail, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle())
            .append(" Author: ")
            .append(getAuthor())
            .append(" Isbn: ")
            .append(getIsbn())
            .append(" Avail: ")
            .append(getAvail())
            .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
