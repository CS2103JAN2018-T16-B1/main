package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalBooks.ALICE;
import static seedu.address.testutil.TypicalBooks.getTypicalCatalogue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.book.Book;
import seedu.address.model.tag.Tag;

public class CatalogueTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final Catalogue catalogue = new Catalogue();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), catalogue.getBookList());
        assertEquals(Collections.emptyList(), catalogue.getTagList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        catalogue.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyCatalogue_replacesData() {
        Catalogue newData = getTypicalCatalogue();
        catalogue.resetData(newData);
        assertEquals(newData, catalogue);
    }

    @Test
    public void resetData_withDuplicateBooks_throwsAssertionError() {
        // Repeat ALICE twice
        List<Book> newBooks = Arrays.asList(ALICE, ALICE);
        List<Tag> newTags = new ArrayList<>(ALICE.getTags());
        CatalogueStub newData = new CatalogueStub(newBooks, newTags);

        thrown.expect(AssertionError.class);
        catalogue.resetData(newData);
    }

    @Test
    public void getBookList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        catalogue.getBookList().remove(0);
    }

    @Test
    public void getTagList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        catalogue.getTagList().remove(0);
    }

    /**
     * A stub ReadOnlyCatalogue whose books and tags lists can violate interface constraints.
     */
    private static class CatalogueStub implements ReadOnlyCatalogue {
        private final ObservableList<Book> books = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();

        CatalogueStub(Collection<Book> books, Collection<? extends Tag> tags) {
            this.books.setAll(books);
            this.tags.setAll(tags);
        }

        @Override
        public ObservableList<Book> getBookList() {
            return books;
        }

        @Override
        public ObservableList<Tag> getTagList() {
            return tags;
        }
    }

}
