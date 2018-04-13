package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.model.book.TitleContainsKeywordsPredicate;
import seedu.address.testutil.CatalogueBuilder;

import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BOOKS;
import static seedu.address.testutil.TypicalBooks.ANIMAL;
import static seedu.address.testutil.TypicalBooks.BREAKING;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getFilteredBookList_modifyList_throwsUnsupportedOperationException() {
        ModelManager modelManager = new ModelManager();
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredBookList().remove(0);
    }

    @Test
    public void equals() {
        Catalogue catalogue = new CatalogueBuilder().withBook(ANIMAL).withBook(BREAKING).build();
        Catalogue differentCatalogue = new Catalogue();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        ModelManager modelManager = new ModelManager(catalogue, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(catalogue, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different catalogue -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentCatalogue, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ANIMAL.getTitle().fullTitle.split("\\s+");
        modelManager.updateFilteredBookList(new TitleContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(catalogue, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredBookList(PREDICATE_SHOW_ALL_BOOKS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setCatalogueBookTitle("differentName");
        assertTrue(modelManager.equals(new ModelManager(catalogue, differentUserPrefs)));
    }
}
