package seedu.address.model.book;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Book in the catalogue.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Book {

    private final Title title;

    private final Phone phone;
    private final Availability availability;
    private final Address address;

    private final UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Book(Title title, Phone phone, Availability availability, Address address, Set<Tag> tags) {
        requireAllNonNull(title, phone, availability, address, tags);
        this.title = title;
        this.phone = phone;
        this.availability = availability;
        this.address = address;
        // protect internal tags from changes in the arg list
        this.tags = new UniqueTagList(tags);
    }

    public Title getTitle() {
        return title;
    }

    public Phone getPhone() {
        return phone;
    }

    public Availability getAvailability() {
        return availability;
    }

    public Address getAddress() {
        return address;
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
                && otherBook.getPhone().equals(this.getPhone())
                && otherBook.getAvailability().equals(this.getAvailability())
                && otherBook.getAddress().equals(this.getAddress());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, phone, availability, address, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Availability: ")
                .append(getAvailability())
                .append(" Address: ")
                .append(getAddress())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
