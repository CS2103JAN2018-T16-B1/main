package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditBookDescriptor;
import seedu.address.model.book.Address;
import seedu.address.model.book.Availability;
import seedu.address.model.book.Title;
import seedu.address.model.book.Book;
import seedu.address.model.book.Phone;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditBookDescriptor objects.
 */
public class EditBookDescriptorBuilder {

    private EditBookDescriptor descriptor;

    public EditBookDescriptorBuilder() {
        descriptor = new EditBookDescriptor();
    }

    public EditBookDescriptorBuilder(EditBookDescriptor descriptor) {
        this.descriptor = new EditBookDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditBookDescriptor} with fields containing {@code book}'s details
     */
    public EditBookDescriptorBuilder(Book book) {
        descriptor = new EditBookDescriptor();
        descriptor.setTitle(book.getTitle());
        descriptor.setPhone(book.getPhone());
        descriptor.setAvailability(book.getAvailability());
        descriptor.setAddress(book.getAddress());
        descriptor.setTags(book.getTags());
    }

    /**
     * Sets the {@code Title} of the {@code EditBookDescriptor} that we are building.
     */
    public EditBookDescriptorBuilder withName(String name) {
        descriptor.setTitle(new Title(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditBookDescriptor} that we are building.
     */
    public EditBookDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Availability} of the {@code EditBookDescriptor} that we are building.
     */
    public EditBookDescriptorBuilder withAvailability(String availability) {
        descriptor.setAvailability(new Availability(availability));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditBookDescriptor} that we are building.
     */
    public EditBookDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditBookDescriptor}
     * that we are building.
     */
    public EditBookDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditBookDescriptor build() {
        return descriptor;
    }
}
