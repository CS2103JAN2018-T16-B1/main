package seedu.address.logic.commands;

import org.junit.Test;
import seedu.address.logic.commands.EditCommand.EditBookDescriptor;
import seedu.address.testutil.EditBookDescriptorBuilder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_XVI;
import static seedu.address.logic.commands.CommandTestUtil.DESC_YOU;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_YOU;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AVAIL_YOU;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ISBN_YOU;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FICTION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_YOU;

public class EditBookDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditBookDescriptor descriptorWithSameValues = new EditBookDescriptor(DESC_XVI);
        assertTrue(DESC_XVI.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_XVI.equals(DESC_XVI));

        // null -> returns false
        assertFalse(DESC_XVI.equals(null));

        // different types -> returns false
        assertFalse(DESC_XVI.equals(5));

        // different values -> returns false
        assertFalse(DESC_XVI.equals(DESC_YOU));

        // different name -> returns false
        EditBookDescriptor editedXvi = new EditBookDescriptorBuilder(DESC_XVI).withName(VALID_TITLE_YOU).build();
        assertFalse(DESC_XVI.equals(editedXvi));

        // different isbn -> returns false
        editedXvi = new EditBookDescriptorBuilder(DESC_XVI).withIsbn(VALID_ISBN_YOU).build();
        assertFalse(DESC_XVI.equals(editedXvi));

        // different availability -> returns false
        editedXvi = new EditBookDescriptorBuilder(DESC_XVI).withAvail(VALID_AVAIL_YOU).build();
        assertFalse(DESC_XVI.equals(editedXvi));

        // different address -> returns false
        editedXvi = new EditBookDescriptorBuilder(DESC_XVI).withAuthor(VALID_AUTHOR_YOU).build();
        assertFalse(DESC_XVI.equals(editedXvi));

        // different tags -> returns false
        editedXvi = new EditBookDescriptorBuilder(DESC_XVI).withTags(VALID_TAG_FICTION).build();
        assertFalse(DESC_XVI.equals(editedXvi));
    }
}
