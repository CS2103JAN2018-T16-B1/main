package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.book.Address;
import seedu.address.model.book.Availability;
import seedu.address.model.book.Book;
import seedu.address.model.book.Title;
import seedu.address.model.book.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Book objects.
 */
public class BookBuilder {

    public static final String DEFAULT_TITLE = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_AVAILABILITY = "Borrowed";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_TAGS = "friends";

    private Title title;
    private Phone phone;
    private Availability availability;
    private Address address;
    private Set<Tag> tags;

    public BookBuilder() {
        title = new Title(DEFAULT_TITLE);
        phone = new Phone(DEFAULT_PHONE);
        availability = new Availability(DEFAULT_AVAILABILITY);
        address = new Address(DEFAULT_ADDRESS);
        tags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
    }

    /**
     * Initializes the BookBuilder with the data of {@code bookToCopy}.
     */
    public BookBuilder(Book bookToCopy) {
        title = bookToCopy.getTitle();
        phone = bookToCopy.getPhone();
        availability = bookToCopy.getAvailability();
        address = bookToCopy.getAddress();
        tags = new HashSet<>(bookToCopy.getTags());
    }

    /**
     * Sets the {@code Title} of the {@code Book} that we are building.
     */
    public BookBuilder withName(String name) {
        this.title = new Title(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Book} that we are building.
     */
    public BookBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Book} that we are building.
     */
    public BookBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Book} that we are building.
     */
    public BookBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Availability} of the {@code Book} that we are building.
     */
    public BookBuilder withAvailability(String availability) {
        this.availability = new Availability(availability);
        return this;
    }

    public Book build() {
        return new Book(title, phone, availability, address, tags);
    }

}
